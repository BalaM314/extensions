package balam314.extensions.world.blocks.defense.turrets;

import arc.math.*;
import arc.math.geom.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.turrets.*;

/** Point defense turret, but shoots all projectiles. */
public class AdvancedPointDefenseTurret extends PointDefenseTurret {
	public AdvancedPointDefenseTurret(String name) {
		super(name);
	}

	public class AdvancedPointDefenceBuild extends PointDefenseBuild {
		@Override
		public void updateTile(){

			//retarget
			if(timer(timerTarget, retargetTime)){
				target = Groups.bullet.intersect(x - range, y - range, range*2, range*2).min(b -> b.team != team, b -> b.dst2(this));
			}

			//pooled bullets
			if(target != null && !target.isAdded()){
				target = null;
			}

			if(coolant != null){
				updateCooling();
			}

			//look at target
			if(target != null && target.within(this, range) && target.team != team && target.type() != null){
				float dest = angleTo(target);
				rotation = Angles.moveToward(rotation, dest, rotateSpeed * edelta());
				reloadCounter += edelta();

				//shoot when possible
				if(Angles.within(rotation, dest, shootCone) && reloadCounter >= reload){
					if(target.damage() > bulletDamage){
						target.damage(target.damage() - bulletDamage);
					}else{
						target.remove();
					}

					Tmp.v1.trns(rotation, shootLength);

					beamEffect.at(x + Tmp.v1.x, y + Tmp.v1.y, rotation, color, new Vec2().set(target));
					shootEffect.at(x + Tmp.v1.x, y + Tmp.v1.y, rotation, color);
					hitEffect.at(target.x, target.y, color);
					shootSound.at(x + Tmp.v1.x, y + Tmp.v1.y, Mathf.random(0.9f, 1.1f));
					reloadCounter = 0;
				}
			}
		}
	}
}
