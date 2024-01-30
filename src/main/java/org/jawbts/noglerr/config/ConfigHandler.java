package org.jawbts.noglerr.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigHandler;
import fi.dy.masa.malilib.util.JsonUtils;
import org.jawbts.noglerr.tweak.var.TargetManager;
import org.jawbts.noglerr.tweak.var.TextManager;
import org.jawbts.noglerr.tweak.var.VarManager;
import org.jawbts.noglerr.tweak.voice.Vosk;
import org.jawbts.noglerr.util.PlayerMessageSender;

import java.io.File;

public class ConfigHandler implements IConfigHandler {
    private static final String FILE_PATH = "./config/noglerr.json";
    private static final File CONFIG_DIR = new File("./config");

    public static void loadFile() {
        File settingFile = new File(FILE_PATH);
        if (!settingFile.isFile() || !settingFile.exists() || !settingFile.canRead()) {
            return;
        }

        JsonElement jsonElement = JsonUtils.parseJsonFile(settingFile);

        if (jsonElement == null) {
            return;
        }
        if (!jsonElement.isJsonObject()) {
            return;
        }

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ConfigUtils.readConfigBase(jsonObject, "generic", Configs.Generic.OPTIONS);
        ConfigUtils.readConfigBase(jsonObject, "detailed", Configs.Detailed.OPTIONS);
        ConfigUtils.readConfigBase(jsonObject, "genericHotKey", Hotkeys.GENERIC_HOTKEY_LIST);

        ConfigUtils.readHotkeyToggleOptions(jsonObject, "togglesHotkey", "toggles", Configs.Toggles.OPTIONS);
    }

    private static void saveFile() {
        if ((CONFIG_DIR.exists() && CONFIG_DIR.isDirectory()) || CONFIG_DIR.mkdirs()) {
            JsonObject jsonObject = new JsonObject();

            ConfigUtils.writeConfigBase(jsonObject, "generic", Configs.Generic.OPTIONS);
            ConfigUtils.writeConfigBase(jsonObject, "detailed", Configs.Detailed.OPTIONS);
            ConfigUtils.writeConfigBase(jsonObject, "genericHotKey", Hotkeys.GENERIC_HOTKEY_LIST);

            ConfigUtils.writeHotkeyToggleOptions(jsonObject, "togglesHotkey", "toggles", Configs.Toggles.OPTIONS);

            JsonUtils.writeJsonToFile(jsonObject, new File(FILE_PATH));
        }
    }

    public static void onChanged() {
        if (!TargetManager.getEditor().setListFromJson(Configs.Detailed.SHOW_ENTITY_DETAIL_TARGET_CONFIG.getStringValue())) {
            PlayerMessageSender.getInstance().add("red", "noglerr.command.readTargetListFailed");
        }
        if (!VarManager.getEditor().setListFromJson(Configs.Detailed.SHOW_ENTITY_DETAIL_VAR_CONFIG.getStringValue())) {
            PlayerMessageSender.getInstance().add("red", "noglerr.command.readVarListFailed");
        }
        if (!TextManager.getEditor().setListFromJson(Configs.Detailed.SHOW_ENTITY_DETAIL_CONFIG.getStringValue())) {
            PlayerMessageSender.getInstance().add("red", "noglerr.command.readTextListFailed");
        }
    }

    @Override
    public void load() {
        loadFile();
        onChanged();
    }

    @Override
    public void save() {
        saveFile();
    }
}