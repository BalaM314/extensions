package balam314.extensions.register;

import mindustry.entities.bullet.LiquidBulletType;
import mindustry.world.blocks.defense.turrets.LiquidTurret;

import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.Liquids.*;
import static balam314.extensions.register.EBlocks.*;
import static balam314.extensions.register.EItems.*;
import static balam314.extensions.register.ELiquids.*;

/**Contains code that must be run after initialization, such as updating ammo types for vanilla turrets.  */
public class PostInit {

	public static void load(){
		((LiquidTurret)tsunami).ammoTypes.put(advancedCoolant, new LiquidBulletType(ELiquids.advancedCoolant){{
			lifetime = 49f;
			speed = 4f;
			knockback = 1.3f;
			puddleSize = 8f;
			orbSize = 4f;
			drag = 0.001f;
			ammoMultiplier = 2.2f;
			statusDuration = 60f * 5f;
			damage = 0.2f;
		}});
	}
}