package balam314.extensions.register;

import balam314.extensions.world.blocks.production.BoostableCrafter;
import balam314.extensions.world.blocks.production.DetonatingCrafter;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.distribution.Router;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.NuclearReactor;
import mindustry.world.blocks.power.PowerNode;
import mindustry.world.blocks.power.SolarGenerator;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.blocks.storage.StorageBlock;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.RepairTower;
import mindustry.world.draw.DrawDefault;
import mindustry.world.draw.DrawLiquidTile;
import mindustry.world.draw.DrawMulti;
import mindustry.world.draw.DrawRegion;

import static mindustry.type.ItemStack.with;

public class EBlocks implements ContentList {

	public static Block

	iridiumOre,

	impactDrill, decayAccelerator, quadWeaver, advancedCoolantMixer,

	impactPump, liquidVault,

	iridiumConveyor, allocator,

	iridiumWall, iridiumWallLarge, radiantWall, radiantWallLarge, radiantWallHuge,

	surgeDuo, riptide, electron, muon, tauon, neutrino, radiance, overchargedShockMine,

	radiantShield, protactiniumMender, radiantDome, coreNexus, coreRadiant, crypt,

	protactiniumNode, radiantNode, reinforcedBattery, advancedReactor, advancedSolarPanel, /*radiantReactor, */

	pentativeReconstructor, sussifyingReconstructor, repairField
	;

	@Override
	public void load() {

		iridiumOre = new OreBlock(EItems.iridium){{
			oreDefault = true;
			oreThreshold = 0.891f;
			oreScale = 27.2f;
		}};

		impactDrill = new Drill("impact-drill"){{
			requirements(Category.production, with(EItems.iridium, 65, Items.silicon, 120, Items.titanium, 100, Items.thorium, 150));
			drillTime = 240;
			size = 5;
			drawRim = true;
			hasPower = true;
			tier = 6;
			updateEffect = Fx.pulverizeRed;
			updateEffectChance = 0.03f;
			drillEffect = Fx.mineHuge;
			rotateSpeed = 8f;
			warmupSpeed = 0.005f;
			itemCapacity = 40;

			//specific value for funny number
			liquidBoostIntensity = 1.7733f;

			consumePower(8f);
			consumeLiquid(mindustry.content.Liquids.cryofluid, 0.1f).boost();
		}};
		quadWeaver = new BoostableCrafter("quad-weaver"){{
			requirements(Category.crafting, with(EItems.protactinium, 50, EItems.iridium, 80, Items.silicon, 160, Items.lead, 210, Items.thorium, 175));
			craftEffect = Fx.smeltsmoke;
			outputItem = new ItemStack(Items.phaseFabric, 4);
			craftTime = 240f;
			boostAmount = 1f;
			size = 3;
			hasPower = true;

			ambientSound = Sounds.techloop;
			ambientSoundVolume = 0.02f;

			consumeItems(with(Items.thorium, 5, Items.sand, 9));
			consumeItem(EItems.protactinium).boost();
			itemCapacity = 30;
		}};
		decayAccelerator = new DetonatingCrafter("decay-accelerator"){{
			requirements(Category.crafting, with(Items.surgeAlloy, 75, Items.phaseFabric, 100, Items.thorium, 150, Items.plastanium, 200, Items.silicon, 200));
			craftEffect = Fx.greenCloud;
			outputItem = new ItemStack(EItems.protactinium, 2);
			craftTime = 180f;
			size = 3;
			hasPower = true;

			explosionRadius = 4;
			explosionCauserName = "radiation";

			ambientSound = Sounds.pulse;
			ambientSoundVolume = 0.02f;

			consumeItems(with(Items.thorium, 5, Items.surgeAlloy, 1));
			antiDetonationItem = consumeItem(Items.phaseFabric, 3).optional(true, false);
			consumePower(15f);
			itemCapacity = 30;
		}};
		advancedCoolantMixer = new GenericCrafter("advanced-coolant-mixer"){{
			requirements(Category.crafting, with(Items.metaglass, 95, Items.silicon, 150, EItems.iridium, 65, Items.titanium, 100));
			outputLiquid = new LiquidStack(ELiquids.advancedCoolant, 0.2f);
			craftTime = 60f;
			size = 3;
			hasPower = true;
			hasItems = true;
			hasLiquids = true;
			rotate = false;
			solid = true;
			outputsLiquid = true;
			drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(Liquids.cryofluid){{drawLiquidLight = true;}}, new DrawLiquidTile(ELiquids.advancedCoolant){{drawLiquidLight = true;}}, new DrawDefault());

			consumePower(5f);
			consumeItem(EItems.iridium);
			consumeLiquid(mindustry.content.Liquids.cryofluid, 0.5f);
		}};

		impactPump = new Pump("impact-pump"){{
			requirements(Category.liquid, with(Items.titanium, 100, Items.metaglass, 120, Items.silicon, 150, Items.thorium, 50, EItems.iridium, 35));
			pumpAmount = 0.35f;
			consumePower(5f);
			liquidCapacity = 100f;
			hasPower = true;
			size = 4;
		}};
		liquidVault = new LiquidRouter("liquid-vault"){{
			requirements(Category.liquid, with(Items.thorium, 30, Items.titanium, 50, Items.metaglass, 75));
			size = 4;
			liquidCapacity = 4000f;
			health = 1200;
		}};

		iridiumConveyor = new Conveyor("iridium-conveyor"){{
			requirements(Category.distribution, with(EItems.iridium, 2, Items.titanium, 2, Items.plastanium, 2, Items.silicon, 1));
			health = 300;
			speed = 0.26f;
			displayedSpeed = 36f;
		}};
		allocator = new Router("allocator"){{
			requirements(Category.distribution, with(EItems.iridium, 9, Items.lead, 9, Items.copper, 9));
			size = 3;
		}};


		final int wallHealthMultiplier = 4;
		iridiumWall = new Wall("iridium-wall"){{
			requirements(Category.defense, with(EItems.iridium, 6));
			health = wallHealthMultiplier * 225;
		}};
		iridiumWallLarge = new Wall("iridium-wall-large"){{
			requirements(Category.defense, with(EItems.iridium, 24));
			health = wallHealthMultiplier * 4 * 225;
			size = 2;
		}};
		radiantWall = new Wall("radiant-wall"){{
			requirements(Category.defense, with(EItems.radiantAlloy, 5, Items.surgeAlloy, 8));
			health = wallHealthMultiplier * 380;
		}};
		radiantWallLarge = new Wall("radiant-wall-large"){{
			requirements(Category.defense, with(EItems.radiantAlloy, 20, Items.surgeAlloy, 24));
			health = wallHealthMultiplier * 4 * 380;
			size = 2;
		}};
		radiantWallHuge = new Wall("radiant-wall-huge"){{
			requirements(Category.defense, with(EItems.radiantAlloy, 45, Items.surgeAlloy, 72));
			health = wallHealthMultiplier * 9 * 380;
			size = 3;
		}};

		overchargedShockMine = new ShockMine("overcharged-shock-mine"){{
			requirements(Category.effect, with(Items.surgeAlloy, 99, Items.copper, 49, Items.silicon, 99, EItems.radiantAlloy, 5));
			damage = 2;
			size = 2;
			//TODO change this to an EffectField class and make it damage all enemies within 10 blocks
		}};

		radiantShield = new ForceProjector("radiant-shield"){{
			requirements(Category.effect, with(EItems.radiantAlloy, 25, Items.plastanium, 75, Items.lead, 250, Items.titanium, 200, Items.silicon, 300));
			size = 4;
			radius = 180f;
			phaseRadiusBoost = 80f;
			shieldHealth = 2000f;
			phaseShieldBoost = 1000f;
			cooldownNormal = 4.0f;
			cooldownLiquid = 2.0f;
			cooldownBrokenBase = 1.0f;

			consumeItem(EItems.protactinium).boost();
			consumePower(40f);
			//TODO nerf shield health and block crash damage
		}};
		protactiniumMender = new MendProjector("protactinium-mender"){{
			requirements(Category.effect, with(EItems.protactinium, 30, EItems.iridium, 50, Items.lead, 100, Items.silicon, 150));
			size = 3;
			reload = 120f;
			range = 165f;
			phaseRangeBoost = 20f;
			healPercent = 4.5f;
			phaseBoost = 4.0f;
			health = 1500;

			consumePower(15f);
		}};
		radiantDome = new OverdriveProjector("radiant-dome"){{
			requirements(Category.effect, with(EItems.radiantAlloy, 75, Items.surgeAlloy, 175, EItems.protactinium, 250, EItems.iridium, 450, Items.silicon, 1500));
			consumePower(50f);
			size = 4;
			range = 320f;
			speedBoost = 3.5f;
			useTime = 150f;
			hasBoost = false;
			itemCapacity = 40;
			consumeItems(with(EItems.radiantAlloy, 1, Items.surgeAlloy, 2, Items.silicon, 5));
		}};
		coreNexus = new CoreBlock("core-nexus"){{
			requirements(Category.effect, with(Items.surgeAlloy, 1000, Items.plastanium, 1500, EItems.iridium, 3000, Items.metaglass, 8000, Items.silicon, 13000));

			unitType = UnitTypes.gamma;//TODO delta
			health = 9000;
			itemCapacity = 21000;
			size = 6;
			thrusterLength = 48/4f;

			unitCapModifier = 36;
			researchCostMultiplier = 0.06f;
			//TODO mend effect
		}};
		coreRadiant = new CoreBlock("core-radiant"){{
			requirements(Category.effect, with(EItems.radiantAlloy, 2000, EItems.protactinium, 4000, Items.phaseFabric, 5500, EItems.iridium, 11000, Items.silicon, 20000));

			unitType = UnitTypes.gamma;//TODO epsilon
			health = 36000;
			itemCapacity = 48000;
			size = 7;
			thrusterLength = 56/4f;

			unitCapModifier = 48;
			researchCostMultiplier = 0.04f;
			//TODO mend & repair effect, damage effect
		}};
		crypt = new StorageBlock("crypt"){{
			requirements(Category.effect, with(Items.titanium, 750, Items.thorium, 400, EItems.iridium, 100));
			size = 4;
			itemCapacity = 4000;
			health = 1200;
		}};

		protactiniumNode = new PowerNode("protactinium-node"){{
			requirements(Category.power, with(EItems.protactinium, 10, Items.titanium, 10, Items.lead, 20, Items.silicon, 5));
			size = 1;
			maxNodes = 20;
			laserRange = 15f;
			consumeItem(EItems.protactinium).boost();
		}};
		radiantNode = new PowerNode("radiant-node"){{
			requirements(Category.power, with(EItems.radiantAlloy, 25, Items.surgeAlloy, 50, EItems.protactinium, 75, Items.plastanium, 100, Items.silicon, 150));
			size = 3;
			maxNodes = 8;
			laserRange = 80f;
			schematicPriority = -15;
		}};
		reinforcedBattery = new Battery("reinforced-battery"){{
			requirements(Category.power, with(EItems.iridium, 100, Items.titanium, 125, Items.lead, 250, Items.silicon, 200));
			size = 4;
			consumePowerBuffered(200000f);
			baseExplosiveness = 0f;
		}};
		advancedReactor = new NuclearReactor("advanced-reactor"){{
			requirements(Category.power, with(Items.surgeAlloy, 75, EItems.protactinium, 250, EItems.iridium, 400, Items.lead, 600, Items.silicon, 1000, Items.metaglass, 250));
			ambientSound = Sounds.hum;
			ambientSoundVolume = 0.24f;
			size = 4;
			health = 2500;
			itemDuration = 320f;
			powerProduction = 75f;
			consumeItem(EItems.protactinium);
			heating = 0.02f;
			consumeLiquid(ELiquids.advancedCoolant, heating / coolantPower);
			explosionRadius = 1;
		}};
		advancedSolarPanel = new SolarGenerator("advanced-solar-generator"){{
			requirements(Category.power, with(EItems.protactinium, 45, EItems.iridium, 56, Items.silicon, 180, Items.lead, 150));
			size = 4;
			powerProduction = 1.6f;
		}};

		repairField = new RepairTower("repair-field"){{
			requirements(Category.units, with(EItems.iridium, 100, Items.silicon, 180, Items.plastanium, 110, EItems.protactinium, 20));
			size = 3;
		}};
		pentativeReconstructor = new Reconstructor("pentative-reconstructor"){{
			requirements(Category.units, with(EItems.radiantAlloy, 500, EItems.protactinium, 3000, Items.surgeAlloy, 3200, EItems.iridium, 4000, Items.phaseFabric, 3500, Items.silicon, 12000, Items.thorium, 12000));

			size = 11;
			consumePower(180f);
			consumeItems(with(EItems.radiantAlloy, 200, EItems.protactinium, 1500, EItems.iridium, 3000, Items.silicon, 4000, Items.surgeAlloy, 1600, Items.metaglass, 4000, Items.phaseFabric, 2500));
			consumeLiquid(ELiquids.advancedCoolant, 4f);
			consumeLiquid(mindustry.content.Liquids.cryofluid, 6f);

			constructTime = 60f * 60f * 12;
			liquidCapacity = 1440f;

			upgrades.addAll(
					new UnitType[]{UnitTypes.toxopid, UnitTypes.flare},
					new UnitType[]{UnitTypes.eclipse, UnitTypes.flare},
					new UnitType[]{UnitTypes.reign, UnitTypes.flare},
					new UnitType[]{UnitTypes.omura, UnitTypes.flare},
					new UnitType[]{UnitTypes.oct, UnitTypes.flare},
					new UnitType[]{UnitTypes.corvus, UnitTypes.flare},
					new UnitType[]{UnitTypes.navanax, UnitTypes.flare}
			);
			//TODO t6 units instead of flare
		}};
		sussifyingReconstructor = new Reconstructor("sussifying-reconstructor"){{
			requirements(Category.units, with(EItems.radiantAlloy, 50, EItems.protactinium, 300, Items.surgeAlloy, 320, EItems.iridium, 400, Items.phaseFabric, 350, Items.silicon, 1200, Items.thorium, 1200));

			size = 3;
			consumePower(10801f / 60f);
			consumeItems(with(EItems.radiantAlloy, 2));
			consumeLiquid(Liquids.oil, 0.1f);

			constructTime = 60f * 60f * 12;
			liquidCapacity = 1440f;

			upgrades.addAll(
				new UnitType[]{UnitTypes.eclipse, UnitTypes.flare}//TODO flarogus
			);
		}};




	}



	
	
	
}
