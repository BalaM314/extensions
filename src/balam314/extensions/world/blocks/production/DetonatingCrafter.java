package balam314.extensions.world.blocks.production;

import balam314.extensions.register.EItems;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.gen.Sounds;
import mindustry.type.Item;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.Vars;
import mindustry.world.meta.Stat;


/**A crafter that detonates if it has insufficient supply of an item. TODO finish*/
public class DetonatingCrafter extends GenericCrafter {
	public Item antiDetonationItem = Items.phaseFabric;
	public int explosionRadius = 3;
	public int explosionDamage = 100;
	public Effect explodeEffect = Fx.reactorExplosion;//TODO this is probably a bit much




	public DetonatingCrafter(String name){
		super(name);
	}

	@Override
	public void setStats() {
		super.setStats();
		stats.add(Stat.boostEffect, antiDetonationItem);
	}

	public class DetonatingCrafterBuild extends GenericCrafterBuild {

		public float badThingThatCausesDetonation = 0;

		@Override
		public void updateTile() {
			super.updateTile();
		}

		@Deprecated
		public void detonate(){
			this.kablooey();
		}

		public void kablooey(){

			Sounds.explosion.at(this);
			Damage.damage(x, y, explosionRadius * Vars.tilesize, explosionDamage * 4);
			Effect.shake(2f, 16f, x, y);

			explodeEffect.at(x, y);

			this.kill();
		}
	}
}
