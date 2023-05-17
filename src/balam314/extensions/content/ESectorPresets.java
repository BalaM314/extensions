package balam314.extensions.content;

import mindustry.type.*;
import static mindustry.content.Planets.*;

public class ESectorPresets implements ContentList {
	public static SectorPreset
	meteor;
	@Override
	public void load(){
		meteor = new SectorPreset("meteor", serpulo, 120){{
			captureWave = 50;
			difficulty = 9;
		}};
	}
}