package org.jawbts.noglerr.mixin;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.FallingBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jawbts.noglerr.minecraft.extension.entity.ShowerFallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(FallingBlockEntityRenderer.class)
public abstract class FallingBlockEntityRendererMixin extends EntityRenderer<FallingBlockEntity> {
    protected FallingBlockEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(at = @At("HEAD"), method = "render*", cancellable = true)
    private void render(FallingBlockEntity fallingBlockEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (fallingBlockEntity instanceof ShowerFallingBlockEntity) {
            BlockState blockState = fallingBlockEntity.getBlockState();
            if (blockState.getRenderType() != BlockRenderType.MODEL) {
                return;
            }
            World world = fallingBlockEntity.getWorldClient();
            matrixStack.push();
            BlockPos blockPos = new BlockPos(fallingBlockEntity.getX(), fallingBlockEntity.getBoundingBox().maxY, fallingBlockEntity.getZ());
            matrixStack.translate(-0.25F, 0.25F, -0.25F);
            matrixStack.scale(0.5F, 0.5F, 0.5F);
            BlockRenderManager blockRenderManager = MinecraftClient.getInstance().getBlockRenderManager();
            blockRenderManager.getModelRenderer().render(world, blockRenderManager.getModel(blockState), blockState, blockPos, matrixStack, vertexConsumerProvider.getBuffer(RenderLayers.getMovingBlockLayer(blockState)), false, new Random(), blockState.getRenderingSeed(fallingBlockEntity.getFallingBlockPos()), OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
            super.render(fallingBlockEntity, f, g, matrixStack, vertexConsumerProvider, i);
            ci.cancel();
        }
    }
}
