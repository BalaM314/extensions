package balam314.extensions.register;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;

public class Items implements ContentList {

	public static Item copper = mindustry.content.Items.copper;
	public static Item lead = mindustry.content.Items.lead;
	public static Item metaglass = mindustry.content.Items.metaglass;
	public static Item graphite = mindustry.content.Items.graphite;
	public static Item sand = mindustry.content.Items.sand;
	public static Item coal = mindustry.content.Items.coal;
	public static Item titanium = mindustry.content.Items.titanium;
	public static Item thorium = mindustry.content.Items.thorium;
	public static Item scrap = mindustry.content.Items.scrap;
	public static Item silicon = mindustry.content.Items.silicon;
	public static Item plastanium = mindustry.content.Items.plastanium;
	public static Item phaseFabric = mindustry.content.Items.phaseFabric;
	public static Item surgeAlloy = mindustry.content.Items.surgeAlloy;
	public static Item sporePod = mindustry.content.Items.sporePod;
	public static Item blastCompound = mindustry.content.Items.blastCompound;
	public static Item pyratite = mindustry.content.Items.pyratite;

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
