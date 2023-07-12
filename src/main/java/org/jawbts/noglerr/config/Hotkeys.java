package org.jawbts.noglerr.config;

import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.util.StringUtils;
import org.spongepowered.include.com.google.common.collect.ImmutableList;

import java.util.List;

public class Hotkeys {
    public static final ConfigHotkey MENU_OPEN_KEY = new ConfigHotkey("noglerr.hotkeys.openConfigScreen", "N,G,C",
            StringUtils.translate("noglerr.comments.gui.openConfigScreen"));

    public static final List<ConfigHotkey> GENERIC_HOTKEY_LIST = ImmutableList.of(
            MENU_OPEN_KEY
    );
}
