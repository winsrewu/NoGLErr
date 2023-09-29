package org.jawbts.noglerr.tweak;

import com.google.gson.JsonSyntaxException;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.text.Text;
import org.jawbts.noglerr.event.ClientTickHandler;

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

    // 检查1tick内只需调用一次的是否会调用多次
    // 每gt重置
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

    // 玩家是否准备好接受消息
    // 玩家是否在游戏, 没开任何gui
    private static boolean tempReadyState = false;
    public static boolean playerReadyCheck() {
        if (TickUpdateChecker.check("READY_CHECK")) {
            return tempReadyState;
        }
        TickUpdateChecker.mark("READY_CHECK");

        tempReadyState = ClientTickHandler.mc != null && ClientTickHandler.mc.currentScreen == null;
        return tempReadyState;
    }

    // 玩家是否在游戏里
    // 和gui没关系 开不开无所谓
    private static boolean tempGameReadyState = false;
    public static boolean gameReadyCheck() {
        if (TickUpdateChecker.check("G_READY_CHECK")) {
            return tempGameReadyState;
        }
        TickUpdateChecker.mark("G_READY_CHECK");

        tempGameReadyState = ClientTickHandler.mc != null && ClientTickHandler.mc.player != null;
        return tempGameReadyState;
    }
}
