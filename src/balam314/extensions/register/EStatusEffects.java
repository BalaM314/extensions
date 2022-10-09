package balam314.extensions.register;

import arc.Events;
import arc.graphics.Color;
import mindustry.content.Fx;
import mindustry.game.EventType;
import mindustry.type.StatusEffect;

import static mindustry.Vars.state;
import static mindustry.content.StatusEffects.*;

public class EStatusEffects implements ContentList {
	public StatusEffect frozen, drenched, irradiated, radiant;

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
	}

}
