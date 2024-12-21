package org.jawbts.noglerr.tweak.assistant;

import fi.dy.masa.malilib.util.EntityUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jawbts.noglerr.config.Configs;
import org.jawbts.noglerr.event.ClientTickHandler;
import org.jawbts.noglerr.event.EntityHandler;
import org.jawbts.noglerr.minecraft.extension.entity.ShowerFallingBlockEntity;
import org.jawbts.noglerr.tweak.EntityTracer;

import java.util.Optional;

public class ProjectileEntityAssistantManager {
    private static final ProjectileEntityAssistantManager INSTANCE = new ProjectileEntityAssistantManager();
    private ProjectileEntityAssistant activeAssistant;
    private boolean changeTarget;
    private HitResult target;
    private Entity targetBlockEntityShower;
    private Entity targetEntityShower;
    private EntityTracer targetEntityTracer;
    private String message;
    private float targetPitch;
    private float targetYaw;

    public ProjectileEntityAssistantManager() {
        activeAssistant = null;
        changeTarget = false;
        targetBlockEntityShower = null;
        targetEntityShower = null;
        message = "No target found";
    }

    public static ProjectileEntityAssistantManager getInstance() {
        return INSTANCE;
    }

    public boolean onLeftClick() {
        if (ClientTickHandler.player == null || !Configs.Toggles.BOW_ASSISTANT.getBooleanValue()) return false;
        boolean flag = ClientTickHandler.player.getMainHandStack().getItem() == Items.BOW;
        flag |= ClientTickHandler.player.getMainHandStack().getItem() == Items.ENDER_PEARL;
        if (!flag) return false;

        changeTarget = true;
        return true;
    }

    private void removeTargetBlockEntityShower(MinecraftClient mc) {
        if (targetBlockEntityShower == null || mc.world == null) return;

        targetBlockEntityShower.onRemoved();
        targetBlockEntityShower.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        mc.world.removeEntity(targetBlockEntityShower.getId(), Entity.RemovalReason.UNLOADED_WITH_PLAYER);
        targetBlockEntityShower = null;
    }

    private void generateTargetBlockEntityShower(MinecraftClient mc) {
        if (target == null || mc.world == null || mc.player == null || targetBlockEntityShower != null || target.getType() != HitResult.Type.BLOCK)
            return;

        BlockPos blockPos = ((BlockHitResult) target).getBlockPos();
        BlockState blockState = mc.world.getBlockState(blockPos);
        targetBlockEntityShower = new ShowerFallingBlockEntity(mc.world, blockPos.getX() + 0.5, blockPos.getY() + 0.01, blockPos.getZ() + 0.5, blockState, mc.player);
        mc.world.addEntity(EntityHandler.getInstance().getNextEntityId(mc.world), targetBlockEntityShower);
    }

    public void updateTarget(MinecraftClient mc) {
        if (mc.world == null) return;

        Entity cameraEntity = EntityUtils.getCameraEntity();
        if (cameraEntity == null) {
            return;
        }
        if (mc.world == null) {
            return;
        }

        HitResult target = cameraEntity.raycast(1000, 1, false);
        Vec3d vec3d = cameraEntity.getCameraPosVec(1);
        double e = 9;
        if (target != null) {
            e = target.getPos().squaredDistanceTo(vec3d);
        }
        Vec3d vec3d2 = cameraEntity.getRotationVec(1.0f);
        Vec3d vec3d3 = vec3d.add(vec3d2.x * 1000, vec3d2.y * 1000, vec3d2.z * 1000);
        Box box = cameraEntity.getBoundingBox().stretch(vec3d2.multiply(1000)).expand(1.0, 1.0, 1.0);
        EntityHitResult entityHitResult = ProjectileUtil.raycast(cameraEntity, vec3d, vec3d3, box, entity -> !entity.isSpectator() && !EntityHandler.getInstance().isNoglerrEntity(entity), e);
        if (entityHitResult != null) {
            Vec3d vec3d4 = entityHitResult.getPos();
            double g = vec3d.squaredDistanceTo(vec3d4);
            if (g < e || target == null) {
                target = entityHitResult;
            }
        }

        this.target = target;
    }

    public void tick(ClientPlayerEntity player, MinecraftClient mc) {
        if (mc.world == null || player == null) return;

        boolean assistantActive = Configs.Toggles.BOW_ASSISTANT.getBooleanValue();
        if (!assistantActive ||
                (target != null
                        && target.getType() == HitResult.Type.ENTITY
                        && ((EntityHitResult) target).getEntity().isRemoved())
        ) {
            message = "";
            removeTargetBlockEntityShower(mc);
            targetYaw = Float.NaN;
            targetPitch = Float.NaN;
            targetEntityShower = null;
            target = null;
            return;
        }

        if (changeTarget) {
            changeTarget = false;

            removeTargetBlockEntityShower(mc);
            updateTarget(mc);

            switch (target.getType()) {
                case MISS, BLOCK:
                    targetEntityShower = null;
                    break;
                case ENTITY:
                    targetEntityShower = ((EntityHitResult) target).getEntity();
                    targetEntityTracer = EntityTracer.getTracer(targetEntityShower);
                    break;
            }

            if (player.getMainHandStack().getItem() == Items.BOW) {
                activeAssistant = new BowAssistant(this, 10, 0.1);
            } else if (player.getMainHandStack().getItem() == Items.ENDER_PEARL) {
                activeAssistant = new EnderPearlAssistant(this, 10, 0.1);
            }
        }

        if (activeAssistant != null) activeAssistant.solveTheta().ifPresentOrElse(
                pair -> {
                    double playerPitch = -player.getPitch();
                    double playerYaw = (player.getYaw() % 360 + 360) % 360;

                    boolean pitchCaptured = Math.abs(playerPitch - pair.getLeft()) < 5;
                    boolean yawCaptured = Math.abs(playerYaw - pair.getRight()) < 5;
                    if (pitchCaptured) {
                        targetPitch = -pair.getLeft().floatValue();
                    } else {
                        targetPitch = Float.NaN;
                    }
                    if (yawCaptured) {
                        targetYaw = (float) (player.getYaw() - player.getYaw() % 360 + pair.getRight());
                    } else {
                        targetYaw = Float.NaN;
                    }
                    message = "Pitch: " + pair.getLeft() + " " + (pitchCaptured && yawCaptured ? "Captured" : -player.getPitch())
                            + " Yaw: " + " " + pair.getRight() + " "
                            + (pitchCaptured && yawCaptured ? "Captured" : (player.getYaw() % 360 + 360) % 360);
                },
                () -> message = "No target found"
        );

        // PlayerMessageSender.getInstance().add(message);

        generateTargetBlockEntityShower(mc);

        updateActionBar(mc, message);
    }

    public Entity getTargetEntityShower() {
        return targetEntityShower;
    }

    public Optional<Vec3d> getTargetPos() {
        if (target == null || activeAssistant == null) {
            return Optional.empty();
        }
        Vec3d result = switch (target.getType()) {
            case MISS -> null;
            case BLOCK -> {
                BlockPos blockPos = ((BlockHitResult) target).getBlockPos();
                yield new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
            }
            case ENTITY -> ((EntityHitResult) target).getEntity()
                    .getPos().add(0, 0, 0);
        };
        return activeAssistant.processTargetPos(result);
    }

    public Optional<Vec3d> getTargetVelocity() {
        if (target == null || target.getType() == HitResult.Type.MISS) return Optional.empty();
        if (target.getType() == HitResult.Type.BLOCK) return Optional.of(new Vec3d(0, 0, 0));
        return Optional.of(targetEntityTracer.getVelocity());
    }

    private void updateActionBar(MinecraftClient mc, String message) {
        if (message == null || message.isEmpty()) return;
        mc.inGameHud.setOverlayMessage(new LiteralText(message), false);
    }

    public float getTargetPitch() {
        return targetPitch;
    }

    public float getTargetYaw() {
        return targetYaw;
    }

    public boolean isCaptured() {
        return activeAssistant != null && activeAssistant.isEnabled()
                && target != null && target.getType() != HitResult.Type.MISS
                && !Float.isNaN(getTargetPitch()) && !Float.isNaN(getTargetYaw());
    }
}
