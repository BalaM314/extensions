package balam314.extensions.register;

import arc.graphics.Color;
import mindustry.content.StatusEffects;
import mindustry.ctype.ContentList;
import mindustry.type.Liquid;

public class Liquids implements ContentList {

	public static Liquid advancedCoolant;

	@Override
	public void load(){
		advancedCoolant = new Liquid("advanced-coolant", Color.valueOf("CCCCFF")){{
			temperature = 0.05f;
			viscosity = 0.2f;
			heatCapacity = 1.3f;
			effect = StatusEffects.freezing;//TODO change to frozen
			lightColor = Color.valueOf("5522FF40");
		}};
	}
}
