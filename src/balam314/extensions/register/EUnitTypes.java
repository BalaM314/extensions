package balam314.extensions.register;

import arc.struct.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.type.UnitType.*;
import mindustry.world.meta.*;

public class EUnitTypes implements ContentList {
	//ground
	public static UnitType dynasty, morphnus, macrinus, blackout, hexadeca, delta, epsilon, musculus, syrinx, flarogus;

	@Override
	public void load(){
		//this is going to be very difficult
		flarogus = new UnitType("flarogus"){{
			speed = 2.8f;
			accel = 0.03f;
			drag = 0.03f;
			flying = true;
			health = 69420;
			engineSize = 0;
			engines = Seq.with(
				new UnitEngine(-2, -8, 4, -90),
				new UnitEngine(2, -8, 4, -90)
			);
			targetAir = true;
			targetGround = true;
			targetFlags = new BlockFlag[]{BlockFlag.generator, null};
			hitSize = 9;
			itemCapacity = 100;
			constructor = UnitEntity::create;
			outlines = false;

			weapons.add(new Weapon(){{
				y = 0f;
				x = 0f;
				reload = 10f;
				ejectEffect = Fx.casing1;
				shootCone = 90f;
				bullet = new BasicBulletType(7.5f, 10){{
					width = 12f;
					height = 20f;
					lifetime = 45f;
					shootEffect = Fx.shootBig;
					smokeEffect = Fx.shootBigSmoke;
				}};
				shootSound = Sounds.pew;
				shoot = new ShootSpread(20, 2);
				mirror = false;
			}});
		}};
	}

}
