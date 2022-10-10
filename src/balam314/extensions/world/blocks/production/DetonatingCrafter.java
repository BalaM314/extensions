package balam314.extensions.world.blocks.production;

import arc.audio.Sound;
import arc.graphics.Color;
import arc.math.Mathf;
import mindustry.Vars;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.Consume;

import static mindustry.Vars.tilesize;


/**A crafter that detonates if it has insufficient supply of an item. */
public class DetonatingCrafter extends GenericCrafter {
	public Consume antiDetonationItem;
	public int explosionRadius = 3;
	public int explosionDamage = 100;
	public Effect explodeEffect = Fx.massiveExplosion;
	/** Time to detonate*/
	public float explosionTime = 2f;
	public float explosionShake = 0f;
	public float explosionShakeDuration = 12f;
	public Sound explodeSound = Sounds.explosion;
	public String explosionCauserName = "Sus level";
	public Color explosionBarColor = Color.lime;




	public DetonatingCrafter(String name){
		super(name);
	}

	@Override
	public void setStats() {
		super.setStats();
		//TODO add anti detonation item
	}

	@Override
	public void setBars() {
		super.setBars();
		addBar(explosionCauserName, (DetonatingCrafterBuild b) -> new Bar("bar." + explosionCauserName, explosionBarColor, () -> b.explosionTimer / (explosionTime * 60f)));
	}

	public class DetonatingCrafterBuild extends GenericCrafterBuild {

		public float explosionTimer = 0f;

		@Override
		public void updateTile() {
			float antidetonationEfficiency = antiDetonationItem.efficiency(this);
			if(antidetonationEfficiency == 1f){
				explosionTimer --;
				if(explosionTimer < 0) explosionTimer = 0;
			} else if(this.efficiency > 0){
				explosionTimer += (1 - antidetonationEfficiency) * this.efficiency;
			}
			if(explosionTimer >= explosionTime * 60f) this.kill();
			super.updateTile();
		}

		@Override
		public void onDestroyed(){
			super.onDestroyed();

			if(Vars.state.rules.reactorExplosions){
				createExplosion();
			}
		}

		public void createExplosion(){
			if(explosionDamage > 0){
				Damage.damage(x, y, explosionRadius * tilesize, explosionDamage);
			}

			explodeEffect.at(this);
			explodeSound.at(this);

			if(explosionShake > 0){
				Effect.shake(explosionShake, explosionShakeDuration, this);
			}
		}
	}
}
