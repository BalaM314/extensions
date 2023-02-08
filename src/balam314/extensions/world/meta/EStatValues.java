package balam314.extensions.world.meta;

import arc.Core;
import arc.func.Floatf;
import arc.func.Func;
import arc.struct.ObjectMap;
import arc.util.Scaling;
import arc.util.Strings;
import mindustry.gen.Tex;
import mindustry.type.Liquid;
import mindustry.ui.ItemImage;
import mindustry.ui.LiquidDisplay;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValue;

import static mindustry.Vars.content;

public class EStatValues {
	public static StatValue liquidDpsBoost(ObjectMap<Liquid, Float> damageForLiquid, float amount){
		return table -> {
			table.row();
			table.table(c -> {
				for(Liquid liquid : content.liquids()){
					Float result = damageForLiquid.get(liquid);
					if(result == null) continue;

					c.add(new LiquidDisplay(liquid, amount * 60f, true)).left();
					c.table(Tex.underline, bt -> {
						bt.left().defaults().padRight(3).left();
						bt.add(Core.bundle.format("bullet.damage", Strings.autoFixed(result * 100, 1) + "%") + StatUnit.perSecond.localized());
					}).left().padTop(-9);
					c.row();
				}
			}).colspan(table.getColumns());
			table.row();
		};
	}
}
