package balam314.extensions.util;

import arc.Core;
import arc.graphics.g2d.TextureAtlas;
import arc.util.Log;
import arc.util.Nullable;
import balam314.extensions.Extensions;
import mindustry.Vars;

public class Util {
    private int spammyDebugTimer = 0;
    /** Used for debugging things that could be spammy.*/
    public void spammyDebug(String message){
        if(Extensions.mode != Mode.debug) return;
        if(spammyDebugTimer > 0){
            spammyDebugTimer--;
        }
        if(!Vars.net.client()) {
            if(Core.input.shift() && spammyDebugTimer == 0){
                Log.info(message);
                spammyDebugTimer = 10;
            }
        }
    }
    public void log(String message){
        Log.info(String.format("[%s] %s", Extensions.name, message));
    }
    public void debug(String message){
        Log.debug(String.format("[%s] %s", Extensions.name, message));
    }

    /** Gets a texture region with modID.*/
    public TextureAtlas.AtlasRegion getResource(String resourceName, @Nullable String modid){
        if(modid == null) {
            return Core.atlas.find(Extensions.modID + "-" + resourceName);
        } else if (modid == "") {
            return Core.atlas.find(resourceName);
        } else {
            return Core.atlas.find(modid + "-" + resourceName);
        }
    }
}
