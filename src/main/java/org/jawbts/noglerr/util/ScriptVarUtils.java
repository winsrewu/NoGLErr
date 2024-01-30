package org.jawbts.noglerr.util;

import org.jawbts.noglerr.client.NoglerrClient;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ScriptVarUtils {
    private static final File CONFIG_DIR = new File("./config/noglerr/var_scripts");

    public static File getConfigDir() {
        if ((CONFIG_DIR.exists() && CONFIG_DIR.isDirectory()) || CONFIG_DIR.mkdirs())
            return CONFIG_DIR;
        NoglerrClient.LOGGER.error("Error while opening folder. '{}", CONFIG_DIR.getAbsolutePath());
        return null;
    }

    public static File[] getVarFiles() {
        return Objects.requireNonNull(getConfigDir()).listFiles(pathname -> {
            if (!(pathname.isFile() && pathname.canRead())) return false;
            return pathname.getName().endsWith(".js");
        });
    }

    public static boolean openFolder() {
        try {
            // TODO only support windows
            Runtime.getRuntime().exec("explorer " + Objects.requireNonNull(getConfigDir()).getAbsolutePath());

            // it's headless mod, don't use it.
            // Desktop.getDesktop().open(getConfigDir());
        } catch (IOException e) {
            NoglerrClient.LOGGER.error("Err while opening folder. '{}", CONFIG_DIR.getAbsolutePath(), e);
            return false;
        }
        return true;
    }
}
