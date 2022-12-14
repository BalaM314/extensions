package balam314.extensions.world.blocks.production;

import arc.math.Mathf;
import mindustry.world.blocks.production.Drill;

@Deprecated
/** This functionality was merged into Drill in >v139 */
public class FastDrill extends Drill {
	/** Chance of the drill effect being displayed. */
	public float drillEffectChance = 1f;
	public FastDrill(String name) {
		super(name);
	}
	public class FastDrillBuild extends DrillBuild {
		@Override
		public void updateTile(){
			if(timer(timerDump, dumpTime)){
				dump(dominantItem != null && items.has(dominantItem) ? dominantItem : null);
			}

			if(dominantItem == null){
				return;
			}

			timeDrilled += warmup * delta();

			float delay = getDrillTime(dominantItem);

			if(items.total() < itemCapacity && dominantItems > 0 && efficiency > 0){
				float speed = Mathf.lerp(1f, liquidBoostIntensity, optionalEfficiency) * efficiency;

				lastDrillSpeed = (speed * dominantItems * warmup) / delay;
				warmup = Mathf.approachDelta(warmup, speed, warmupSpeed);
				progress += delta() * dominantItems * speed * warmup;

				if(Mathf.chanceDelta(updateEffectChance * warmup))
					updateEffect.at(x + Mathf.range(size * 2f), y + Mathf.range(size * 2f));
			}else{
				lastDrillSpeed = 0f;
				warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
				return;
			}

			if(dominantItems > 0 && progress >= delay && items.total() < itemCapacity){
				offload(dominantItem);

				progress %= delay;

				if(wasVisible && Mathf.chanceDelta(updateEffectChance * warmup)) drillEffect.at(x + Mathf.range(drillEffectRnd), y + Mathf.range(drillEffectRnd), dominantItem.color);
			}
		}
	}


}
