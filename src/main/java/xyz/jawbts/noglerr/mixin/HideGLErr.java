package xyz.jawbts.noglerr.mixin;

import net.minecraft.client.gl.GlDebug;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlDebug.class)
public class HideGLErr {
    @Shadow private static final Logger LOGGER = LogManager.getLogger();
    private static final Logger LOGGER2 = LogManager.getLogger("noglerr");

    @Inject(at = @At("HEAD"), method = "info(IIIIIJJ)V")
    private static void info(int source, int type, int id, int severity, int messageLength, long message, long l, CallbackInfo callbackInfo) {
        LOGGER.info("AH!GetIt!");
        LOGGER2.info("No!!!");
    }

    @Inject(at = @At("HEAD"), method = "enableDebug(IZ)V")
    private static void enableDebug(int verbosity, boolean sync, CallbackInfo callbackInfo) {
        verbosity = 0;
    }

    @Inject(at = @At("HEAD"), method = "getSource(I)Ljava/lang/String;")
    private static void getSource(int opcode, CallbackInfoReturnable<String> cir) {
        cir.setReturnValue("FFFF");
    }
}