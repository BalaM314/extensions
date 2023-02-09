package balam314.extensions.content;

import arc.*;
import arc.graphics.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.type.*;

import static mindustry.Vars.*;
import static mindustry.content.StatusEffects.*;

public class EStatusEffects implements ContentList {
	public static StatusEffect frozen, drenched, irradiated, radiated;

	@Override
	public void load(){
		frozen = new StatusEffect("frozen"){{
			color = Color.valueOf("2e9dbc");
			speedMultiplier = 0.4f;
			healthMultiplier = 0.75f;
			effect = Fx.freezing;
			transitionDamage = 36f;

			init(() -> {
				opposite(melting, burning);

				affinity(blasted, (unit, result, time) -> {
					unit.damagePierce(transitionDamage);
				});
			});
		}};

		drenched = new StatusEffect("drenched"){{
			color = Color.valueOf("304EA8");
			speedMultiplier = 0.8f;
			reloadMultiplier = 0.7f;
			effect = Fx.wet;
			effectChance = 0.09f;
			transitionDamage = 32;
			damage = 0.05f;

			init(() -> {
				affinity(shocked, (unit, result, time) -> {
					unit.damagePierce(transitionDamage);
					if(unit.team == state.rules.waveTeam){
						Events.fire(EventType.Trigger.shock);
						//how bout I do it again
						Events.fire(EventType.Trigger.shock);
					}
				});
				opposite(burning, melting);
			});
		}};

		irradiated = new StatusEffect("irradiated"){{
			color = Color.valueOf("7bf920");
			effect = Fx.greenCloud;
			effectChance = 0.09f;
			transitionDamage = 40;
			damage = 1.6f;

			init(() -> {
				opposite(radiated);
			});
		}};

		radiated = new StatusEffect("radiated"){{
			color = Color.valueOf("20f97E");
			effect = Fx.greenCloud;
			effectChance = 0.09f;
			healthMultiplier = 1.1f;
			speedMultiplier = 1.2f;
			init(() -> {
				opposite(irradiated);
			});
		}};
	}

}
