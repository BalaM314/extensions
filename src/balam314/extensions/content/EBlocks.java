package balam314.extensions.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import balam314.extensions.world.blocks.defense.turrets.*;
import balam314.extensions.world.blocks.production.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.pattern.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.liquid.*;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.*;
import mindustry.world.blocks.units.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;
import mindustry.world.meta.*;

import static mindustry.type.ItemStack.with;

public class EBlocks implements ContentList {

	public static Block

	iridiumOre,

	impactDrill, decayAccelerator, quadWeaver, advancedCoolantMixer, fluxedOvercharger,

	impactPump, liquidVault,

	iridiumConveyor, allocator,

	iridiumWall, iridiumWallLarge, radiantWall, radiantWallLarge, radiantWallHuge,

	surgeDuo, riptide, ray, electron, muon, tauon, neutrino, radiance, overchargedShockMine,

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
			variants = 3;
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
			consumePower(12f);
			itemCapacity = 30;
		}};
		decayAccelerator = new DetonatingCrafter("decay-accelerator"){{
			requirements(Category.crafting, with(Items.surgeAlloy, 75, Items.phaseFabric, 100, Items.thorium, 150, Items.plastanium, 200, Items.silicon, 200));
			craftEffect = Fx.greenCloud;
			outputItem = new ItemStack(EItems.protactinium, 2);
			craftTime = 180f;
			size = 3;
			hasPower = true;

			explosionRadius = 6;
			explosionCauserName = "radiation";

			ambientSound = Sounds.pulse;
			ambientSoundVolume = 0.02f;

			consumeItems(with(Items.thorium, 5));
			antiDetonationCons = consumeItem(Items.phaseFabric, 2);
			consumePower(15f);
			itemCapacity = 30;
		}};
		fluxedOvercharger = new SelfDamagingCrafter("fluxed-overcharger"){{
			requirements(Category.crafting, with(EItems.iridium, 250, Items.thorium, 165, Items.plastanium, 100, Items.silicon, 200, EItems.protactinium, 50));
			craftEffect = Fx.lightning;
			outputItem = new ItemStack(EItems.radiantAlloy, 1);
			craftTime = 120f;
			size = 4;
			hasPower = true;

			explodeOnDestruction = true;
			explodeIfExternalDestruction = true;
			explosionRadius = 9;
			explosionEffect = Fx.massiveExplosion;
			health = 1240;

			ambientSound = Sounds.electricHum;
			ambientSoundVolume = 0.02f;

			consumeItems(with(Items.surgeAlloy, 2, EItems.iridium, 5, EItems.protactinium, 3, Items.sporePod, 1));
			consumeLiquid(ELiquids.advancedCoolant, 18f / 60f);
			consumePower(32f);
			itemCapacity = 30;
			liquidCapacity = 90f;
		}};
		advancedCoolantMixer = new GenericCrafter("advanced-coolant-mixer"){{
			requirements(Category.crafting, with(Items.metaglass, 95, Items.silicon, 150, EItems.iridium, 65, Items.titanium, 100));
			outputLiquid = new LiquidStack(ELiquids.advancedCoolant, 0.4f);
			craftTime = 30f;
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
			consumeLiquid(mindustry.content.Liquids.cryofluid, 0.6f);
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
			//TODO should this even exist? High speed conveyors aren't stable especially if overdrived
			requirements(Category.distribution, with(EItems.iridium, 1, Items.titanium, 1, Items.metaglass, 1));
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
			size = 2;
			tendrils = 18;
			length = 13;
			damage = 100;
			//TODO change this to an EffectField class and make it damage all enemies within 10 blocks
		}};

		surgeDuo = new ItemTurret("surge-duo"){{
			requirements(Category.turret, with(Items.copper, 35, Items.surgeAlloy, 69));
			ammo(
				Items.surgeAlloy, new LaserBoltBulletType(7.5f, 24){{
					width = 4f;
					height = 18f;
					lifetime = 60f;
					ammoMultiplier = 15;
					backColor = Pal.bulletYellowBack;
					frontColor = Pal.bulletYellow;
					hitEffect = Fx.hitLaserColor;
					despawnEffect = Fx.hitLaserColor;
					hitColor = Pal.bulletYellow;
				}}
			);

			shoot = new ShootAlternate(3.5f);

			shootY = 3f;
			reload = 20f;
			range = 110;
			shootCone = 15f;
			ammoUseEffect = Fx.casing1;
			health = 500;
			inaccuracy = 2f;
			rotateSpeed = 10f;
			coolant = consumeCoolant(0.1f);
			researchCostMultiplier = 0.05f;

			limitRange();
		}};
		riptide = new LiquidTurret("riptide"){{
			requirements(Category.turret, with(Items.metaglass, 300, Items.plastanium, 200, Items.thorium, 350, EItems.iridium, 200, Items.lead, 600));
			ammo(
				Liquids.water, new LiquidBulletType(Liquids.water){{
					lifetime = 39.1f;
					speed = 9f;
					knockback = 1.7f;
					puddleSize = 8f;
					orbSize = 7f;
					drag = 0.001f;
					ammoMultiplier = 0.4f;
					statusDuration = 60f * 4f;
					damage = 1.3f;
					layer = Layer.bullet - 2f;
				}},
				Liquids.slag,  new LiquidBulletType(Liquids.slag){{
					lifetime = 39.1f;
					speed = 9f;
					knockback = 1.3f;
					puddleSize = 8f;
					orbSize = 7f;
					damage = 9.5f;
					drag = 0.001f;
					ammoMultiplier = 0.4f;
					statusDuration = 60f * 4f;
				}},
				Liquids.cryofluid, new LiquidBulletType(Liquids.cryofluid){{
					lifetime = 39.1f;
					speed = 9f;
					knockback = 1.3f;
					puddleSize = 8f;
					orbSize = 7f;
					drag = 0.001f;
					ammoMultiplier = 0.4f;
					statusDuration = 60f * 4f;
					damage = 1.3f;
				}},
				Liquids.oil, new LiquidBulletType(Liquids.oil){{
					lifetime = 39.1f;
					speed = 9f;
					knockback = 1.3f;
					puddleSize = 8f;
					orbSize = 7f;
					drag = 0.001f;
					ammoMultiplier = 0.4f;
					statusDuration = 60f * 4f;
					damage = 1.3f;
					layer = Layer.bullet - 2f;
				}},
				ELiquids.advancedCoolant, new LiquidBulletType(ELiquids.advancedCoolant){{
					lifetime = 39.1f;
					speed = 9f;
					knockback = 1.3f;
					puddleSize = 8f;
					orbSize = 7f;
					drag = 0.001f;
					ammoMultiplier = 2.2f;
					statusDuration = 60f * 6f;
					damage = 1.7f;
				}},
				ELiquids.protactiniumPlasma, new LiquidBulletType(ELiquids.protactiniumPlasma){{
					lifetime = 39.1f;
					speed = 9f;
					knockback = 1.3f;
					puddleSize = 8f;
					orbSize = 7f;
					damage = 20.75f;
					drag = 0.001f;
					ammoMultiplier = 4f;
					statusDuration = 60f * 10f;
					boilTime = Float.MAX_VALUE;
				}}
			);
			size = 4;
			reload = 2f;
			shoot.shots = 2;
			velocityRnd = 0.1f;
			inaccuracy = 4f;
			recoil = 1f;
			shootCone = 45f;
			liquidCapacity = 60f;
			shootEffect = Fx.shootLiquid;
			range = 340f;
			scaledHealth = 250;
			flags = EnumSet.of(BlockFlag.turret, BlockFlag.extinguisher);
			consumePower(2f);
		}};
		ray = new AdvancedPointDefenseTurret("ray"){{
			requirements(Category.turret, with(Items.copper, 750, Items.silicon, 700, EItems.iridium, 600, EItems.protactinium, 300, Items.surgeAlloy, 200));

			scaledHealth = 150;
			range = 250f;
			hasPower = true;
			consumePower(19f);
			size = 4;
			shootLength = 5f;
			bulletDamage = 65f;
			reload = 4f;
			envEnabled |= Env.space;
		}};
		electron = new AdvancedContinuousLaserTurret("electron"){{
			requirements(Category.turret, with(Items.copper, 1600, Items.silicon, 700, EItems.iridium, 550, Items.surgeAlloy, 425, EItems.protactinium, 200, Items.thorium, 800));
			shootEffect = Fx.none;
			overheatEffect = new Effect(60f, e -> {
				Angles.randLenVectors(e.id, 24, 1f + e.fin() * 56f, (x, y) -> {
					float size = 5f + e.fout() * 12f;
					Draw.color(Color.lightGray, Color.gray, e.fin());
					Fill.circle(e.x + x, e.y + y, size/2f);
				});
			});


			shootCone = 40f;
			recoil = 4f;
			size = 5;
			shake = 2f;
			range = 295f;
			reload = 60f;
			firingMoveFrac = 0.5f;
			shootSound = Sounds.laserbig;
			loopSound = Sounds.beam;
			loopSoundVolume = 2f;
			envEnabled |= Env.space;

			shoot = new ShootPattern(){{
				firstShotDelay = 15f;
			}};

			shootType = new ContinuousLaserBulletType(141){{
				length = 300f;
				hitEffect = new Effect(12, e -> {
					Draw.color(Color.valueOf("a0ffff"));
					Lines.stroke(e.fout() * 2f);

					Angles.randLenVectors(e.id, 6, e.finpow() * 18f, (x, y) -> {
						float ang = Mathf.angle(x, y);
						Lines.lineAngle(e.x + x, e.y + y, ang, e.fout() * 4 + 1f);
					});
				});
				hitColor = Color.valueOf("a0ffff");
				status = StatusEffects.melting;
				drawSize = 480f;
				colors = new Color[]{Color.valueOf("b0f0ff55"), Color.valueOf("b0f0ffaa"), Color.valueOf("b0f0ffff"), Color.white};

				incendChance = 0.7f;
				incendSpread = 5f;
				incendAmount = 1;
				ammoMultiplier = 1f;
				chargeEffect = new Effect(15f, 100f, e -> {
					Draw.color(Color.valueOf("cffff9"));
					Lines.stroke(e.fin() * 2f);
					Lines.circle(e.x, e.y, 4f + e.fout() * 60f);

					Fill.circle(e.x, e.y, e.fin() * 20);

					Angles.randLenVectors(e.id, 20, 40f * e.fout(), (x, y) -> {
						Fill.circle(e.x + x, e.y + y, e.fin() * 5f);
						Drawf.light(e.x + x, e.y + y, e.fin() * 15f, Color.valueOf("cffff9"), 0.7f);
					});

					Draw.color();

					Fill.circle(e.x, e.y, e.fin() * 10);
					Drawf.light(e.x, e.y, e.fin() * 20f, Color.valueOf("cffff9"), 0.7f);
				}).followParent(true).rotWithParent(true);
			}};

			coolantTypes = ObjectMap.of(
				ELiquids.advancedCoolant, 1.5f,
				Liquids.cryofluid, 1.0f
			);

			liquidCapacity = 150f;
			coolant = consume(new ConsumeLiquidFilter(liquid -> coolantTypes.get(liquid) != null, 0.8f));

			scaledHealth = 250;
			consumePower(55f);
		}};
		muon = new ItemTurret("muon"){{
			requirements(Category.turret, with(Items.copper, 1400, Items.silicon, 800, EItems.iridium, 550, Items.surgeAlloy, 425, EItems.protactinium, 200, Items.thorium, 800));

			ammo(
				EItems.iridium, new ArtilleryBulletType(12.5f, 800, "shell"){{
					shootEffect = new Effect(24f, e -> {
						e.scaled(10f, b -> {
							Draw.color(Color.white, Color.valueOf("eedddd"), b.fin());
							Lines.stroke(b.fout() * 3f + 0.2f);
							Lines.circle(b.x, b.y, b.fin() * 50f);
						});

						Draw.color(Color.valueOf("eedddd"));

						for(int i : Mathf.signs){
							Drawf.tri(e.x, e.y, 13f * e.fout(), 85f, e.rotation + 90f * i);
							Drawf.tri(e.x, e.y, 13f * e.fout(), 50f, e.rotation + 20f * i);
						}

						Drawf.light(e.x, e.y, 180f, Color.valueOf("eedddd"), 0.9f * e.fout());
					});
					smokeEffect = Fx.smokeCloud;
					hitEffect = Fx.instHit;

					knockback = 2f;
					lifetime = 72.5f;
					height = 19f;
					width = 17f;
					backColor = hitColor = trailColor = Color.valueOf("eedddd");
					frontColor = Color.white;
					ammoMultiplier = 1f;
					hitSound = Sounds.titanExplosion;

					trailLength = 32;
					trailWidth = 3.35f;
					trailSinScl = 2.5f;
					trailSinMag = 0.5f;
					trailEffect = Fx.instTrail;
					despawnShake = 7f;

					trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
					shrinkX = 0.2f;
					shrinkY = 0.1f;
					buildingDamageMultiplier = 0.1f;
				}},
				Items.blastCompound, new ArtilleryBulletType(12.5f, 300, "shell"){{
					shootEffect = new Effect(24f, e -> {
						e.scaled(10f, b -> {
							Draw.color(Color.white, Color.valueOf("eedddd"), b.fin());
							Lines.stroke(b.fout() * 3f + 0.2f);
							Lines.circle(b.x, b.y, b.fin() * 50f);
						});

						Draw.color(Color.valueOf("eedddd"));

						for(int i : Mathf.signs){
							Drawf.tri(e.x, e.y, 13f * e.fout(), 85f, e.rotation + 90f * i);
							Drawf.tri(e.x, e.y, 13f * e.fout(), 50f, e.rotation + 20f * i);
						}

						Drawf.light(e.x, e.y, 180f, Color.valueOf("eedddd"), 0.9f * e.fout());
					});
					smokeEffect = Fx.smokeCloud;
					hitEffect = Fx.instHit;

					knockback = 2f;
					lifetime = 72.5f;
					height = 19f;
					width = 17f;
					splashDamageRadius = 55f;
					splashDamage = 600f;
					scaledSplashDamage = true;
					backColor = hitColor = trailColor = Color.valueOf("ffaaaa");
					frontColor = Color.white;
					ammoMultiplier = 1f;
					hitSound = Sounds.titanExplosion;
					status = StatusEffects.blasted;

					trailLength = 32;
					trailWidth = 3.35f;
					trailSinScl = 2.5f;
					trailSinMag = 0.5f;
					trailEffect = new Effect(30, e -> {
						for(int i = 0; i < 2; i++){
							Draw.color(i == 0 ? Color.valueOf("eedddd") : Color.valueOf("ffeeee"));

							float m = i == 0 ? 1f : 0.5f;

							float rot = e.rotation + 180f;
							float w = 15f * e.fout() * m;
							Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, 15f)) * m, rot);
							Drawf.tri(e.x, e.y, w, 10f * m, rot + 180f);
						}

						Drawf.light(e.x, e.y, 60f, Color.valueOf("eedddd"), 0.6f * e.fout());
					});
					despawnShake = 7f;

					trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
					shrinkX = 0.2f;
					shrinkY = 0.1f;
					buildingDamageMultiplier = 0.1f;
				}}
			);

			shootSound = Sounds.mediumCannon;
			shoot = new ShootAlternate(16){{
				shots = 2;
			}};
			ammoPerShot = 4;
			maxAmmo = ammoPerShot * 3;
			targetAir = false;
			shake = 4f;
			recoil = 1f;
			reload = 60f * 2.3f;
			shootY = 7f;
			rotateSpeed = 1.4f;
			minWarmup = 0.85f;
			shootWarmupSpeed = 0.07f;

			shootWarmupSpeed = 0.08f;

			outlineColor = Pal.darkOutline;

			consumeLiquid(Liquids.water, 5f / 60f);

			scaledHealth = 180;
			range = 450f;
			size = 5;
			limitRange();
		}};
		tauon = new ItemTurret("tauon"){{
			requirements(Category.turret, with(Items.lead, 1400, Items.metaglass, 900, EItems.iridium, 550, Items.phaseFabric, 325, EItems.protactinium, 225, Items.plastanium, 800));
			ammo(
					Items.scrap, new BasicBulletType(7f, 200){{
						hitSize = 4;
						width = 22f;
						height = 28f;
						shootEffect = Fx.shootBig;
						pierceCap = 5;
						pierceBuilding = true;
						knockback = 0.7f;
						lifetime = 35f;
						splashDamage = 50;
						splashDamageRadius = 5;
						ammoMultiplier = 2;
					}},
					Items.sporePod, new BasicBulletType(7f, 100){{
						hitSize = 4;
						width = 15f;
						height = 36f;
						shootEffect = Fx.shootBig;
						frontColor = Pal.reactorPurple;
						backColor = Pal.reactorPurple2;
						status = StatusEffects.burning;
						hitEffect = new MultiEffect(Fx.hitBulletBig, Fx.fireHit);
						makeFire = true;
						pierceCap = 3;
						pierceBuilding = true;
						knockback = 0.4f;
						ammoMultiplier = 3;
						splashDamage = 120f;
						splashDamageRadius = 25f;
						lifetime = 35f;
					}}
			);
			reload = 7.5f;
			recoilTime = reload * 2f;
			coolantMultiplier = 0.5f;
			ammoUseEffect = Fx.casing3;
			range = 245f;
			inaccuracy = 0f;
			recoil = 3f;
			shoot = new ShootSpread(5, 1f);
			shake = 2f;
			size = 5;
			shootCone = 28f;
			shootSound = Sounds.shootBig;

			scaledHealth = 200;
			coolant = consumeCoolant(1.2f);

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

			itemConsumer = consumeItem(EItems.protactinium).boost();
			consumePower(40f);
			//TODO nerf shield health and block crash damage
		}};
		protactiniumMender = new MendProjector("protactinium-mender"){{
			requirements(Category.effect, with(EItems.protactinium, 30, EItems.iridium, 50, Items.lead, 100, Items.silicon, 150));
			size = 3;
			reload = 120f;
			range = 165f;
			phaseRangeBoost = 20f;
			healPercent = 3.5f;
			phaseBoost = 4.5f;
			health = 1500;

			useTime = 350f;
			consumeItems(with(EItems.protactinium, 1, Items.phaseFabric, 2)).boost();
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
			consumeItems(with(EItems.radiantAlloy, 1, EItems.protactinium, 2, Items.phaseFabric, 5));
		}};
		coreNexus = new CoreBlock("core-nexus"){{
			requirements(Category.effect, with(Items.surgeAlloy, 1000, Items.plastanium, 1500, EItems.iridium, 3000, Items.metaglass, 8000, Items.copper, 14000, Items.silicon, 13000));

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
			requirements(Category.effect, with(EItems.radiantAlloy, 2000, EItems.protactinium, 4000, Items.phaseFabric, 5500, EItems.iridium, 11000, Items.copper, 18000, Items.silicon, 20000));

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
		}};
		radiantNode = new PowerNode("radiant-node"){{
			requirements(Category.power, with(EItems.radiantAlloy, 25, Items.surgeAlloy, 50, EItems.protactinium, 75, Items.plastanium, 100, Items.silicon, 150));
			size = 3;
			maxNodes = 8;
			laserRange = 80f;
			schematicPriority = -15;
		}};
		reinforcedBattery = new Battery("reinforced-battery"){{
			requirements(Category.power, with(EItems.iridium, 100, Items.plastanium, 125, Items.lead, 250, Items.silicon, 200));
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
			heating = 0.02f;
			explosionRadius = 1;
			itemCapacity = 45;

			fuelItem = EItems.protactinium;
			consumeItem(EItems.protactinium);
			consumeLiquid(ELiquids.advancedCoolant, heating / coolantPower).update(false);
		}};
		advancedSolarPanel = new SolarGenerator("advanced-solar-generator"){{
			requirements(Category.power, with(EItems.protactinium, 45, EItems.iridium, 56, Items.silicon, 180, Items.lead, 150));
			size = 5;
			powerProduction = 2.4f;
		}};

		repairField = new RepairTower("repair-field"){{
			requirements(Category.units, with(EItems.iridium, 120, Items.silicon, 180, Items.plastanium, 110, EItems.protactinium, 50));
			size = 3;
			healAmount = 10f;
			range = 120f;

			circleSpeed = 45f;
			circleStroke = 8f;
			squareSpinScl = 0.1f;
			consumePowerCond(5, (RepairTowerBuild b) -> b.targets.size != 0);
			//TODO boost
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

			size = 6;
			consumePower(10801f / 60f);
			consumeItems(with(EItems.radiantAlloy, 600, Items.sporePod, 1000));
			consumeLiquid(Liquids.oil, 0.1f);

			constructTime = 60f * 60f * 12;
			liquidCapacity = 120f;

			upgrades.addAll(
				new UnitType[]{UnitTypes.eclipse, EUnitTypes.flarogus}
			);
		}};




	}



	
	
	
}
