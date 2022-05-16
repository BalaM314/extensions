package balam314.extensions;

import arc.util.*;
import arc.util.Log.*;
import balam314.extensions.register.Blocks;
import balam314.extensions.register.Items;
import balam314.extensions.util.Mode;
import balam314.extensions.util.Util;
import balam314.extensions.register.Liquids;
import mindustry.Vars;
import mindustry.ctype.ContentList;
import mindustry.mod.*;
import mindustry.world.Block;

public class Extensions extends Mod{

    public static final String githubURL = "https://github.com/BalaM314/extensions";
    public static final String name = "Extensions";
    public static final String modID = "extensions";
    public static final Util util = new Util();

    public static final Mode mode = Mode.normal;

    public Extensions(){
        util.log("Starting...");

        if(mode == Mode.debug){
            Log.level = LogLevel.debug;
        } else {
            Log.level = LogLevel.info;
        }

    }

    @Override
    public void loadContent(){
        util.log("Loading content.");

        ContentList[] allContent = {new Items(), new Liquids(), new Blocks()};
        for(ContentList list : allContent) {
            list.load();
            util.log("Loaded " + list.getClass().getName());
        }
        util.log("Content loaded!");

        if(mode == Mode.debug && false) {
            Log.debug("Logging blocks...");
            Log.debug("--------------");
            for (Block block : Vars.content.blocks()) {
                if (block.name.startsWith(modID))
                    Log.debug(block.name + "\t" + block.description);
            }
            Log.debug("--------------");
        }

    }

}
