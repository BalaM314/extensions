package balam314.extensions.world.blocks.production;

import balam314.extensions.register.EItems;
import mindustry.content.Items;
import mindustry.type.Item;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class BoostableCrafter extends GenericCrafter {

	public float boostAmount = 0.5f;
	public Item boostItem = Items.phaseFabric;
	public float boostUseTime = 300f;

	public BoostableCrafter(String name) {
		super(name);
	}

	public void setStats(){
		super.setStats();
		stats.add(Stat.speedIncrease, boostAmount, StatUnit.percent);
	}

	public class BoostableCrafterBuild extends GenericCrafterBuild {
		public boolean isBoosted = false;
		@Override
		public float getProgressIncrease(float base){
			return super.getProgressIncrease(base) * (isBoosted ? (1f + boostAmount) : 1f);
		}

	}

}
