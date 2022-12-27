package xyz.jawbts.noglerr;

import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.HashMap;

public class Util {
    public static boolean getBoolFromMap(String key, HashMap<String, Boolean> map) {
        Object ans = map.get(key);
        if(ans == null) {
            map.put(key, false);
            return false;
        }
        return (boolean) ans;
    }

    public static Text showFunctionStatusBool(String function) {
        return new TranslatableText(function + " :  %b".formatted(Util.getBoolFromMap(function, StaticValueManager.Saver)));
    }
}
