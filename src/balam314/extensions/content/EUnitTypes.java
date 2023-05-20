package balam314.extensions.content;

import arc.struct.*;
import mindustry.ai.types.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.meta.*;

public class EUnitTypes implements ContentList {
	//ground
	public static UnitType dynasty, morphnus, macrinus, blackout, hexadeca, musculus, syrinx, flarogus, delta, epsilon;

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

		delta = new UnitType("delta"){{
			aiController = BuilderAI::new;
			isEnemy = false;

			lowAltitude = true;
			flying = true;
			mineSpeed = 9.5f;
			mineTier = 2;
			buildSpeed = 1.5f;
			drag = 0.05f;
			speed = 4.1f;
			rotateSpeed = 19f;
			accel = 0.16f;
			itemCapacity = 90;
			health = 350f;
			engineOffset = 6f;
			hitSize = 11f;
			constructor = UnitEntity::create;

			weapons.add(new Weapon("delta-weapon"){{
				top = false;
				reload = 15f;
				x = 0f;
				mirror = false;
				y = 2f;
				shoot = new ShootSpread(){{
					shots = 3;
					shotDelay = 1f;
					spread = 2f;
				}};

				inaccuracy = 0f;
				ejectEffect = Fx.casing1;

				bullet = new BasicBulletType(){{
					speed = 4f;
					damage = 13f;
					width = 7.5f;
					height = 14f;
					lifetime = 70f;
					shootEffect = Fx.shootSmall;
					smokeEffect = Fx.shootSmallSmoke;
					buildingDamageMultiplier = 0.01f;
					homingPower = 0.04f;
				}};
			}});
		}};

		

	}

}
