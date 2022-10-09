package balam314.extensions.world.blocks.units;

import arc.struct.EnumSet;
import mindustry.gen.Building;
import mindustry.world.Block;
import mindustry.world.blocks.units.RepairTurret;
import mindustry.world.consumers.ConsumeCoolant;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class RepairField extends Block {

	public float repairRadius = 50f;
	public float repairSpeed = 0.3f;
	public float powerUse;
	public boolean requiresCoolant = false;
	public boolean acceptCoolant = false;
	public float coolantUsed = 0.5f;
	public float coolantMultiplier = 1f;


	public RepairField(String name) {
		super(name);

		update = true;
		solid = true;
		flags = EnumSet.of(BlockFlag.repair);
		hasPower = true;
		outlineIcon = true;
		group = BlockGroup.projectors;

		envEnabled |= Env.space;
	}

	@Override
	public void init(){
		if(acceptCoolant){
			hasLiquids = true;
			consume(new ConsumeCoolant(coolantUsed)).optional(!requiresCoolant, !requiresCoolant);
		}

		consumePower(powerUse);
		clipSize = Math.max(clipSize, (repairRadius + tilesize) * 2);
		super.init();
	}

	@Override
	public void setStats(){
		super.setStats();
		stats.add(Stat.range, repairRadius / tilesize, StatUnit.blocks);
		stats.add(Stat.repairSpeed, repairSpeed * 60f, StatUnit.perSecond);

		if(acceptCoolant){
			stats.add(Stat.booster, StatValues.strengthBoosters(coolantMultiplier, this::consumesLiquid));
		}
	}

	public class RepairFieldBuild extends Building {

	}
}
