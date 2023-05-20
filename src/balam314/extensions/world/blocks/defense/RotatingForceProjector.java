package balam314.extensions.world.blocks.defense;

import arc.func.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.math.geom.*;
import mindustry.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.world.blocks.defense.*;


public class RotatingForceProjector extends ForceProjector {
	/** Rotation in degrees per frame. */
	public float shieldRotateSpeed = 0.5f;
	protected static final Cons<Bullet> shieldConsumer = bullet -> {
		if(bullet.team != paramEntity.team && bullet.type.absorbable && Intersector.isInRegularPolygon(((RotatingForceProjector)(paramEntity.block)).sides, paramEntity.x, paramEntity.y, paramEntity.realRadius(), ((RotatingForceBuild)paramEntity).shieldRotation, bullet.x, bullet.y)){
			bullet.absorb();
			paramEffect.at(bullet);
			paramEntity.hit = 1f;
			paramEntity.buildup += bullet.damage;
		}
	};

	public RotatingForceProjector(String name){
		super(name);
	}

	public class RotatingForceBuild extends ForceBuild {
		public float shieldRotation = 0f;
		@Override
		public void updateTile(){
			this.shieldRotation = (this.shieldRotation + shieldRotateSpeed * getRotateSpeedMultiplier()) % 360f;
			super.updateTile();
		}

		@Override
		public void drawShield(){
			if(!broken){
				float radius = realRadius();

				Draw.color(team.color, Color.white, Mathf.clamp(hit));

				if(Vars.renderer.animateShields){
					Draw.z(Layer.shields + 0.001f * hit);
					Fill.poly(x, y, sides, radius, shieldRotation);
				} else {
					Draw.z(Layer.shields);
					Lines.stroke(1.5f);
					Draw.alpha(0.09f + Mathf.clamp(0.08f * hit));
					Fill.poly(x, y, sides, radius, shieldRotation);
					Draw.alpha(1f);
					Lines.poly(x, y, sides, radius, shieldRotation);
					Draw.reset();
				}
			}

			Draw.reset();
		}

		public float getRotateSpeedMultiplier(){
			return (1f + (phaseHeat * 2f)) * efficiency;
		}
	}
}