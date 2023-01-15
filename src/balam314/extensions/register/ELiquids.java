package balam314.extensions.register;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.type.Liquid;

public class ELiquids implements ContentList {

	public static Liquid advancedCoolant, protactiniumPlasma;

	@Override
	public void load(){
		advancedCoolant = new Liquid("advanced-coolant", Color.valueOf("CCCCFF")){{
			temperature = 0.05f;
			viscosity = 0.2f;
			heatCapacity = 1.3f;
			effect = EStatusEffects.frozen;
			lightColor = Color.valueOf("5522FF40");
		}};

		protactiniumPlasma = new Liquid("protactinium-plasma", Color.valueOf("7BF920")){{
			temperature = 2f;
			viscosity = 0.1f;
			explosiveness = 7f;
			effect = EStatusEffects.irradiated;
			lightColor = Color.valueOf("7BF920");
			gas = true;
		}};
	}
}
