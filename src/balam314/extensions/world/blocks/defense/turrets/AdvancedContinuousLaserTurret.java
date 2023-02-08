package balam314.extensions.world.blocks.defense.turrets;

import arc.graphics.Color;
import arc.math.*;
import arc.struct.*;
import arc.util.*;
import balam314.extensions.world.meta.EStatValues;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.bullet.*;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

/** A turret that fires a continuous beam. Liquid coolant is consumed constantly. Optionally takes damage if coolant runs out. */
public class AdvancedContinuousLaserTurret extends PowerTurret {
	public float firingMoveFrac = 0.25f;
	public Effect overheatEffect = Fx.heatReactorSmoke;
	//Damage taken on overheating, as a fraction of health
	public float overheatDamageFrac = 0.6f;
	public ObjectMap<Liquid, Float> coolantTypes = new ObjectMap<>();
	//Time that the beam lasts after the controller stops firing
	public float bulletPersistence = 30f;

	public AdvancedContinuousLaserTurret(String name){
		super(name);
	}

	@Override
	public void init() {
		super.init();
	}

	@Override
	public void setStats(){
		super.setStats();

		stats.remove(Stat.booster);
		stats.add(Stat.input, EStatValues.liquidDpsBoost(coolantTypes, coolant.amount));
	}

	public class AdvancedContinuousLaserTurretBuild extends PowerTurretBuild{
		public BulletEntry entry;
		public Liquid coolantUsed;

		@Override
		protected void updateCooling() {
			coolant.update(this);
			coolantUsed = coolant instanceof ConsumeLiquidFilter filter ? filter.getConsumed(this) : null;

		}

		@Override
		public boolean shouldConsume(){
			//still consumes power when bullet is around
			return entry != null || isActive() || isShooting();
		}

		@Override
		public void updateTile() {
			super.updateTile();

			if (entry == null || entry.bullet == null) return;

			if (coolantUsed == null) {
				entry.bullet.remove();
				entry = null;
				overheat();
			} else if (entry.life <= 0f) {
				entry.bullet.remove();
				entry = null;
			} else if (!entry.bullet.isAdded() || entry.bullet.type == null || entry.bullet.owner != this) {
				entry = null;
			} else if(entry.bullet != null){
				float
						bulletX = x + Angles.trnsx(rotation - 90, shootX + entry.x, shootY + entry.y),
						bulletY = y + Angles.trnsy(rotation - 90, shootX + entry.x, shootY + entry.y),
						angle = rotation + entry.rotation;

				entry.bullet.rotation(angle);
				entry.bullet.set(bulletX, bulletY);
				entry.bullet.time = entry.bullet.type.lifetime * entry.bullet.type.optimalLifeFract;
				entry.bullet.keepAlive = true;
				entry.life -= Time.delta / Math.max(efficiency, 0.00001f);

				Float damageMultiplier = coolantTypes.get(coolantUsed);
				entry.bullet.damage = entry.bullet.type.damage * (damageMultiplier != null ? damageMultiplier : 1);

				wasShooting = true;
				heat = 1f;
				curRecoil = 1f;
			}
		}

		public void overheat(){
			overheatEffect.at(this.x, this.y);
			charge = 0;
			this.damage(maxHealth * overheatDamageFrac);
		}

		@Override
		public float progress(){
			return 1f - Mathf.clamp(reloadCounter / reload);
		}

		@Override
		protected void updateReload(){
			//no reload
		}

		@Override
		protected void updateShooting(){
			if(entry != null){
				entry.life = bulletPersistence;
			} else if(efficiency > 0 && coolantUsed != null && !charging() && shootWarmup >= minWarmup){
				BulletType type = peekAmmo();

				shoot(type);

				reloadCounter = reload;
			}
		}

		@Override
		protected void turnToTarget(float targetRot){
			//Move slower if firing
			rotation = Angles.moveToward(rotation, targetRot, efficiency * rotateSpeed * delta() * (entry != null ? firingMoveFrac : 1f));
		}

		@Override
		protected void handleBullet(@Nullable Bullet bullet, float offsetX, float offsetY, float angleOffset){
			if(bullet != null){
				entry = new BulletEntry(bullet, offsetX, offsetY, angleOffset, bulletPersistence);
			}
		}

		@Override
		public float activeSoundVolume(){
			return 1f;
		}

		@Override
		public boolean shouldActiveSound(){
			return entry != null;
		}
	}
}
