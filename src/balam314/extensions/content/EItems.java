package balam314.extensions.content;

import arc.graphics.*;
import mindustry.type.*;

public class EItems implements ContentList {
	
	public static Item iridium, protactinium, radiantAlloy;

	
	@Override
	public void load() {
		iridium = new Item("iridium", Color.valueOf("D5D5D5FF")){{
			hardness = 5;
			cost = 1.5f;
		}};
		protactinium = new Item("protactinium", Color.valueOf("19FC87FF")){{
			explosiveness = 1.4f;
			radioactivity = 2.0f;
			cost = 2.8f;
		}};
		radiantAlloy = new Item("radiant-alloy", Color.valueOf("F8F810FF")){{
			explosiveness = 2.0f;
			radioactivity = 0.2f;
			charge = 1.5f;
			cost = 2.5f;
		}};

	}


}
