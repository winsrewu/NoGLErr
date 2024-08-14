package org.jawbts.noglerr.config;


import fi.dy.masa.malilib.util.StringUtils;

public enum Category {
    GENERIC("noglerr.gui.title.generic"),
    DETAILED("noglerr.gui.title.detailed"),
    TOGGLE_HOTKEYS("noglerr.gui.title.toggleHotkeys"),
    TOGGLES("noglerr.gui.title.toggles"),
    SECRETS("noglerr.gui.title.secrets");

    private final String translationKey;

    Category(String translationKey) {
        this.translationKey = translationKey;
    }

    public String getDisplayName() {
        return StringUtils.translate(this.translationKey);
    }

}