package org.jawbts.noglerr.config;

import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.config.options.ConfigBooleanHotkeyed;
import fi.dy.masa.malilib.config.options.ConfigString;
import fi.dy.masa.malilib.util.StringUtils;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

import java.util.List;

public class Configs {

    public static class Generic {
    }

    public static class Detailed {
        public static final ConfigString SHOW_ENTITY_DETAIL_CONFIG = new ConfigString("noglerr.tweaks.showEntityDetailConfig", "[]",
                StringUtils.translate("noglerr.comments.gui.showEntityDetailConfig"));
        public static final ConfigString SHOW_ENTITY_DETAIL_VAR_CONFIG = new ConfigString("noglerr.tweaks.showEntityDetailVarConfig", "[]",
                StringUtils.translate("noglerr.comments.gui.showEntityDetailVarConfig"));

        public static final ConfigString SHOW_ENTITY_DETAIL_TARGET_CONFIG = new ConfigString("noglerr.tweaks.showEntityDetailTargetConfig", "[]",
                StringUtils.translate("noglerr.comments.gui.showEntityDetailTargetConfig"));

        public static final List<IConfigBase> OPTIONS = ImmutableList.of(
                SHOW_ENTITY_DETAIL_CONFIG,
                SHOW_ENTITY_DETAIL_VAR_CONFIG,
                SHOW_ENTITY_DETAIL_TARGET_CONFIG
        );
    }

    public static class Toggles {
        public static final ConfigBooleanHotkeyed SHOW_VFX = new ConfigBooleanHotkeyed("noglerr.tweaks.showVFX", false, "",
                StringUtils.translate("noglerr.comments.gui.showVisualEffects"));
        public static final ConfigBooleanHotkeyed SHOW_ENTITY_DETAIL = new ConfigBooleanHotkeyed("noglerr.tweaks.showEntityDetail", false, "",
                StringUtils.translate("noglerr.comments.gui.showEntityDetail"));
        public static final ConfigBooleanHotkeyed DEBUG = new ConfigBooleanHotkeyed("DEBUG", false, "", "DEBUG");

        public static final List<IHotkeyTogglable> OPTIONS = ImmutableList.of(
                SHOW_ENTITY_DETAIL,
                SHOW_VFX,
                DEBUG
        );
    }
}