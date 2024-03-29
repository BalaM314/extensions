package balam314.extensions.world.blocks.production;

import arc.audio.*;
import arc.graphics.*;
import arc.math.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.consumers.*;
import mindustry.world.blocks.production.*;

import static mindustry.Vars.*;


/**A crafter that detonates if it has insufficient supply of an ingredient. */
public class DetonatingCrafter extends GenericCrafter {
	/** The consumer that will cause detonation if not satisfied. */
	public Consume antiDetonationCons;
	/** If true, building will explode even if it wasn't running. */
	public boolean explodeIfNotRunning = false;
	/** If true, building will explode if something else blows it up. */
	public boolean explodeIfExternalDestruction = true;

	public int explosionRadius = 3;
	public int explosionDamage = 100;
	public Effect explosionEffect = Fx.massiveExplosion;
	/** Time to detonate in ticks */
	public float explosionTime = 2f * 60f;
	public float explosionShake = 0f;
	public float explosionShakeDuration = 12f;
	public Sound explosionSound = Sounds.explosion;
	public String explosionCauserName = "sus-level";
	public Color explosionBarColor = Color.lime;
	public int explosionPuddles = 0;
	public float explosionPuddleRange = tilesize * 5f;
	public float explosionPuddleAmount = 100f;
	public Liquid explosionPuddleLiquid = null;


	public DetonatingCrafter(String name){
		super(name);
	}

	@Override
	public void init(){
		this.antiDetonationCons.optional(true, false);
		super.init();
	}

	@Override
	public void setStats() {
		super.setStats();
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar(explosionCauserName, (DetonatingCrafterBuild b) -> new Bar("bar." + explosionCauserName, explosionBarColor, () -> b.explosionTimer / (explosionTime)));
	}

	public class DetonatingCrafterBuild extends GenericCrafterBuild {

		public float explosionTimer = 0f;

		@Override
		public void updateTile() {
			float antidetonationEfficiency = antiDetonationCons.efficiency(this);
			if(antidetonationEfficiency == 1f){
				explosionTimer --;
				if(explosionTimer < 0) explosionTimer = 0;
			} else if(this.efficiency > 0){
				explosionTimer += (1 - antidetonationEfficiency) * edelta();
			}
			if(explosionTimer >= explosionTime) this.kill();
			super.updateTile();
		}

		@Override
		public void onDestroyed(){
			super.onDestroyed();

			if(
				Vars.state.rules.reactorExplosions && 
				((explosionTimer >= explosionTime) || explodeIfExternalDestruction) &&
				(this.efficiency > 0 || explodeIfNotRunning) //Only explode if running
			){
				createExplosion();
			}
		}

		public void createExplosion(){
			if(explosionDamage > 0){
				Damage.damage(x, y, explosionRadius * tilesize, explosionDamage);
			}

			explosionEffect.at(this);
			explosionSound.at(this);

			if(explosionPuddleLiquid != null){
				for(int i = 0; i < explosionPuddles; i ++){
					Tmp.v1.trns(Mathf.random(360f), Mathf.random(explosionPuddleRange));
					Tile tile = world.tileWorld(x + Tmp.v1.x, y + Tmp.v1.y);
					Puddles.deposit(tile, explosionPuddleLiquid, explosionPuddleAmount);
				}
			}

			if(explosionShake > 0){
				Effect.shake(explosionShake, explosionShakeDuration, this);
			}
		}
	}
}
