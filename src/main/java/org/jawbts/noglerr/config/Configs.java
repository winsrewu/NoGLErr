package org.jawbts.noglerr.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.config.options.ConfigString;
import fi.dy.masa.malilib.util.StringUtils;

import java.util.List;

public class Configs {

    public static class Generic {
        public static final ConfigBoolean ENABLE_AUTO_UPDATE_CHECK = new ConfigBoolean("noglerr.tweaks.enableAutoUpdateCheck", true,
                StringUtils.translate("noglerr.comments.gui.enableAutoUpdateCheck"));
        public static final ConfigBoolean SILENT_UPDATE_CHECK = new ConfigBoolean("noglerr.tweaks.silentUpdateCheck", true,
                StringUtils.translate("noglerr.comments.gui.silentUpdate"));
        public static final ConfigBoolean ENABLE_NASHORN_ENGINE = new ConfigBoolean("noglerr.tweaks.enableNashornEngine", false,
                StringUtils.translate("noglerr.comments.gui.enableNashornEngine"));

        public static final List<IConfigBase> OPTIONS = ImmutableList.of(
                ENABLE_AUTO_UPDATE_CHECK,
                SILENT_UPDATE_CHECK,
                ENABLE_NASHORN_ENGINE
        );
    }

    public static class Detailed {
        public static final ConfigString SHOW_ENTITY_DETAIL_CONFIG = new ConfigString("noglerr.tweaks.showEntityDetailConfig", "[]",
                StringUtils.translate("noglerr.comments.gui.showEntityDetailConfig"));
        public static final ConfigString SHOW_ENTITY_DETAIL_VAR_CONFIG = new ConfigString("noglerr.tweaks.showEntityDetailVarConfig", "[]",
                StringUtils.translate("noglerr.comments.gui.showEntityDetailVarConfig"));

        public static final ConfigString SHOW_ENTITY_DETAIL_TARGET_CONFIG = new ConfigString("noglerr.tweaks.showEntityDetailTargetConfig", "[]",
                StringUtils.translate("noglerr.comments.gui.showEntityDetailTargetConfig"));

        public static final ConfigString VOICE_TO_TEXT_FORMAT = new ConfigString("noglerr.tweaks.voiceToTextFormat", "-%s",
                StringUtils.translate("noglerr.comments.gui.voiceToTextFormat"));
        public static final ConfigString VOICE_TO_TEXT_PATH = new ConfigString("noglerr.tweaks.voiceToTextPath", "",
                StringUtils.translate("noglerr.comments.gui.voiceToTextPath"));
        public static final ConfigInteger MICROPHONE_SAMPLE_RATE = new ConfigInteger("noglerr.tweaks.microphoneSampleRate", 60000,
                StringUtils.translate("noglerr.comments.gui.microphoneSampleRate"));
        public static final ConfigInteger MICROPHONE_SAMPLE_SIZE_IN_BITS = new ConfigInteger("noglerr.tweaks.microphoneSampleSizeInBits", 16,
                StringUtils.translate("noglerr.comments.gui.microphoneSampleSizeInBits"));
        public static final ConfigInteger MICROPHONE_FRAME_SIZE = new ConfigInteger("noglerr.tweaks.microphoneFrameSize", 4,
                StringUtils.translate("noglerr.comments.gui.microphoneFrameSize"));
        public static final ConfigInteger MICROPHONE_FRAME_RATE = new ConfigInteger("noglerr.tweaks.microphoneFrameRate", 44100,
                StringUtils.translate("noglerr.comments.gui.microphoneFrameRate"));
        public static final ConfigInteger MICROPHONE_CHANNELS = new ConfigInteger("noglerr.tweaks.microphoneChannels", 2,
                StringUtils.translate("noglerr.comments.gui.microphoneChannels"));

        public static final List<IConfigBase> OPTIONS = ImmutableList.of(
                VOICE_TO_TEXT_FORMAT,
                VOICE_TO_TEXT_PATH,
                SHOW_ENTITY_DETAIL_CONFIG,
                SHOW_ENTITY_DETAIL_VAR_CONFIG,
                SHOW_ENTITY_DETAIL_TARGET_CONFIG,
                MICROPHONE_SAMPLE_RATE,
                MICROPHONE_SAMPLE_SIZE_IN_BITS,
                MICROPHONE_FRAME_SIZE,
                MICROPHONE_FRAME_RATE,
                MICROPHONE_CHANNELS
        );
    }

    public static class Toggles {
        public static final ConfigBooleanHotkeyed SHOW_VFX = new ConfigBooleanHotkeyed("noglerr.tweaks.showVFX", false, "",
                StringUtils.translate("noglerr.comments.gui.showVisualEffects"));
        public static final ConfigBooleanHotkeyed SHOW_ENTITY_DETAIL = new ConfigBooleanHotkeyed("noglerr.tweaks.showEntityDetail", false, "",
                StringUtils.translate("noglerr.comments.gui.showEntityDetail"));
        public static final ConfigBooleanHotkeyed DEBUG = new ConfigBooleanHotkeyed("DEBUG", false, "", "DEBUG");

        public static final ConfigBooleanHotkeyed VOICE_TO_TEXT = new ConfigBooleanHotkeyed("noglerr.tweaks.voiceToText", false, "",
                StringUtils.translate("noglerr.tweaks.voiceToText"));

        public static final ConfigBooleanHotkeyed VOICE_TO_TEXT_REMOVE_SPACE = new ConfigBooleanHotkeyed("noglerr.tweaks.voiceToTextRemoveSpace", false, "",
                StringUtils.translate("noglerr.comments.gui.voiceToTextRemoveSpace"));

        public static final ConfigBooleanHotkeyed VOICE_TO_TEXT_NO_HISTORY = new ConfigBooleanHotkeyed("noglerr.tweaks.voiceToTextNoHistory", false, "",
                StringUtils.translate("noglerr.comments.gui.voiceToTextNoHistory"));

        public static final ConfigBooleanHotkeyed MICROPHONE_SWITCH = new ConfigBooleanHotkeyed("noglerr.tweaks.microphoneSwitch", false, "",
                StringUtils.translate("noglerr.comments.gui.microphoneSwitch"));

        public static final ConfigBooleanHotkeyed BOW_ASSISTANT = new ConfigBooleanHotkeyed("noglerr.tweaks.bowAssistant", false, "",
                StringUtils.translate("noglerr.comments.gui.bowAssistant"));

        public static final List<IHotkeyTogglable> OPTIONS = ImmutableList.of(
                MICROPHONE_SWITCH,
                VOICE_TO_TEXT,
                VOICE_TO_TEXT_REMOVE_SPACE,
                VOICE_TO_TEXT_NO_HISTORY,
                SHOW_ENTITY_DETAIL,
                SHOW_VFX,
                DEBUG,
                BOW_ASSISTANT
        );
    }

    public static class Secrets {
        public static final ConfigString VOSK_PASSWORD = new ConfigString("noglerr.tweaks.voskPassword", "",
                StringUtils.translate("noglerr.comments.gui.voskPassword"));
        public static final ConfigString VOSK_IP = new ConfigString("noglerr.tweaks.voskIp", "127.0.0.1:25550",
                StringUtils.translate("noglerr.comments.gui.voskIp"));

        public static final List<IConfigBase> OPTIONS = ImmutableList.of(
                VOSK_PASSWORD,
                VOSK_IP
        );
    }
}