package balam314.extensions.world.blocks.production;

import arc.audio.*;
import arc.math.*;
import balam314.extensions.world.meta.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

import static mindustry.Vars.*;


/**A crafter that damages itself while running. (obviously you will need to place menders around it) */
public class SelfDamagingCrafter extends GenericCrafter {

	public float healthLossPerSecondFrac = 0.1f;
	public float healthLoss;
	public boolean loseHealthIfInactive = false;

	public boolean explodeOnDestruction = false;
	/** If true, building will explode if something else blows it up. */
	public boolean explodeIfExternalDestruction = true;
	public int explosionRadius = 3;
	public int explosionDamage = 100;
	public Effect explosionEffect = Fx.massiveExplosion;
	public float explosionShake = 0f;
	public float explosionShakeDuration = 12f;
	public Sound explosionSound = Sounds.explosion;


	public SelfDamagingCrafter(String name){
		super(name);
	}

	@Override
	public void init() {
		super.init();
		if(healthLoss == 0)
			healthLoss = (healthLossPerSecondFrac * this.health) / 60f;
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(EStats.healthLoss, healthLoss * 60f, StatUnit.perSecond);
	}

	public class SelfDamagingCrafterBuild extends GenericCrafterBuild {

		@Override
		public void updateTile() {
			if(loseHealthIfInactive || this.efficiency > 0){
				this.damagePassive(healthLoss * edelta());
			}
			super.updateTile();
		}

		/**Like damage(), but doesn't update lastDamageTime. */
		public void damagePassive(float damage){
			if(dead()) return;

			float dm = state.rules.blockHealth(team);
			if(Mathf.zero(dm)){
				damage = health + 1;
			}else{
				damage /= dm;
			}

			//TODO handle this better on the client.
			if(!net.client()){
				health -= handleDamage(damage);
			}

			healthChanged();

			if(health <= 0){
				Call.buildDestroyed(self());
			}
		}

		@Override
		public void onDestroyed(){
			super.onDestroyed();

			if(explodeOnDestruction && (this.efficiency > 0 || explodeIfExternalDestruction)){
				//Only explode if running
				createExplosion();
			}
		}

		public void createExplosion(){
			if(explosionDamage > 0){
				Damage.damage(x, y, explosionRadius * tilesize, explosionDamage);
			}

			explosionEffect.at(this);
			explosionSound.at(this);

			if(explosionShake > 0){
				Effect.shake(explosionShake, explosionShakeDuration, this);
			}
		}
	}
}
