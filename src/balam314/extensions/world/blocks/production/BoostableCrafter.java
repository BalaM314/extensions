package balam314.extensions.world.blocks.production;

import mindustry.world.blocks.production.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

/** A crafter that can be boosted by optional consumers. */
public class BoostableCrafter extends GenericCrafter {

	public float boostAmount = 0.5f;

	public BoostableCrafter(String name) {
		super(name);
	}

	public void setStats() {
		super.setStats();
		stats.add(Stat.speedMultiplier, 1f + boostAmount, StatUnit.timesSpeed);
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
