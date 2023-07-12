package org.jawbts.noglerr;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoGLErr implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("NoGlErr");

    public static final String MOD_ID = "noglerr";
    public static final String MOD_NAME = "NoGLErr";

    @Override
    public void onInitialize() {
        LOGGER.info("NoGlErr Loded");
    }
}
