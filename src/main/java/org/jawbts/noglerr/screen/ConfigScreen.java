package org.jawbts.noglerr.screen;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.IConfigBase;
import fi.dy.masa.malilib.gui.GuiConfigsBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.util.math.MatrixStack;
import org.jawbts.noglerr.config.Category;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.config.Hotkeys;

import java.util.Collections;
import java.util.List;

import static org.jawbts.noglerr.NoGLErr.MOD_ID;

public class ConfigScreen extends GuiConfigsBase {
    private static Category currentTab = Category.GENERIC;

    public ConfigScreen() {
        super(10, 50, MOD_ID, null, StringUtils.translate("noglerr.gui.title"));
    }

    @Override
    public void initGui() {
        super.initGui();
        this.clearOptions();

        int x = 10, y = 26;

        // tab buttons are set
        for (Category category : Category.values()) {
            ButtonGeneric tabButton = new TabButton(category, x, y, -1, 20, category.getDisplayName());
            tabButton.setEnabled(true);
            this.addButton(tabButton, (buttonBase, i) -> {
                this.onSettingsChanged();

                // reload the GUI when tab button is clicked
                currentTab = ((TabButton) buttonBase).category;
                this.reCreateListWidget();

                //noinspection ConstantConditions
                this.getListWidget().resetScrollbarPosition();
                this.initGui();
            });
            x += tabButton.getWidth() + 2;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public List<ConfigOptionWrapper> getConfigs() {
        List<? extends IConfigBase> configs;
        Category tab = currentTab;

        if (tab == Category.GENERIC) {
            configs = Hotkeys.GENERIC_HOTKEY_LIST;
        } else if (tab == Category.DETAILED) {
            configs = Configs.Detailed.OPTIONS;
        } else if (tab == Category.TOGGLES) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.BOOLEAN, ImmutableList.copyOf(Configs.Toggles.OPTIONS));
        } else if (tab == Category.TOGGLE_HOTKEYS) {
            configs = ConfigUtils.createConfigWrapperForType(ConfigType.HOTKEY, ImmutableList.copyOf(Configs.Toggles.OPTIONS));
        } else {
            return Collections.emptyList();
        }

        return ConfigOptionWrapper.createFor(configs);
    }

    public static class TabButton extends ButtonGeneric {
        private final Category category;

        public TabButton(Category category, int x, int y, int width, int height, String text, String... hoverStrings) {
            super(x, y, width, height, text, hoverStrings);
            this.category = category;
        }
    }
}