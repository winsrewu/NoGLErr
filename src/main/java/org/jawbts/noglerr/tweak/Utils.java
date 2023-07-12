package org.jawbts.noglerr.tweak;

import com.google.gson.JsonSyntaxException;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static String escapeString(String input) {
        input = input.replaceAll("\\\\", "");
        return input.replaceAll("\"", "\\\\\"");
    }

    public static Text createText(String s, String color) {
        return Text.Serializer.fromJson("{\"color\":\"" + color + "\",\"text\":\"" + StringUtils.translate(s) + "\"}");
    }

    public static Text createTextFromJsonOrString(String s) {
        try {
            return Text.Serializer.fromJson(s);
        } catch (JsonSyntaxException e) {
            return Text.of(s);
        }
    }

    public static class TickUpdateChecker {
        private static final HashMap<String, Boolean> updateChecker = new HashMap<>();

        public static boolean check(String s) {
            if (!updateChecker.containsKey(s)) {
                updateChecker.put(s, false);
                return false;
            }
            return updateChecker.get(s);
        }

        public static void mark(String s) {
            if (!updateChecker.containsKey(s)) {
                updateChecker.put(s, true);
                return;
            }
            updateChecker.replace(s, true);
        }

        public static void tick() {
            for (Map.Entry<String, Boolean> entry : updateChecker.entrySet()) {
                entry.setValue(false);
            }
        }
    }
}
