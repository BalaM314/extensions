package balam314.extensions.world.blocks.production;

import arc.util.Strings;
import balam314.extensions.register.EItems;
import balam314.extensions.util.Util;
import mindustry.content.Items;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.consumers.Consume;
import mindustry.world.consumers.ConsumeItems;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

import java.util.Arrays;

import static balam314.extensions.Extensions.util;
import static mindustry.type.ItemStack.with;

public class BoostableCrafter extends GenericCrafter {

	public float boostAmount = 0.5f;

	public BoostableCrafter(String name) {
		super(name);
	}

	public void setStats() {
		super.setStats();
		stats.add(Stat.speedIncrease, (1f + boostAmount) * 100f, StatUnit.percent);
	}

	public class BoostableCrafterBuild extends GenericCrafterBuild {
		@Override
		public float efficiencyScale() {
			float minEfficiency = 1f;
			for(Consume cons : block.optionalConsumers){
				minEfficiency = Math.min(minEfficiency, cons.efficiency(self()));
			}
			return 1 + (minEfficiency * boostAmount);
		}

	}

}
