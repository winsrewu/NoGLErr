package org.jawbts.noglerr.tweak.assistant;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;
import org.jawbts.noglerr.tweak.EntityTracer;
import org.jawbts.noglerr.util.PlayerMessageSender;

import java.util.Optional;

public abstract class ProjectileEntityAssistant {
    ProjectileEntityAssistantManager manager;
    int maxIterations;
    double minAnsDiff;
    final double modifierZ;

    public ProjectileEntityAssistant(ProjectileEntityAssistantManager manager, int maxIterations, double minAnsDiff,
                                     double modifierZ) {
        this.manager = manager;
        this.maxIterations = maxIterations;
        this.minAnsDiff = minAnsDiff;
        this.modifierZ = modifierZ;
    }

    public ClientPlayerEntity getPlayer() {
        return MinecraftClient.getInstance().player;
    }

    private Vec3d getPlayerVelocity() {
        EntityTracer tracer = EntityTracer.getTracer(getPlayer());
        return tracer.getVelocity();
    }

    double getTFromW(double w, double theta) {
        double thetaRad = theta * Math.PI / 180;
        return -5 * Math.log1p(-w / (modifierZ * 100) / Math.cos(thetaRad));
    }

    double getYFromW(double w, double theta, double y) {
        double thetaRad = theta * Math.PI / 180;
        return w * Math.tan(thetaRad) + 5 * w / 3 / Math.cos(thetaRad)
                + (double) 5 / 3 * (modifierZ * 100) * Math.log1p(-w / (modifierZ * 100) / Math.cos(thetaRad)) - y;
    }

    double getDiffedAngle(double x, double diff) {
        return x + diff >= 90 ? x - diff : x + diff;
    }

    Optional<Pair<Double, Double>> solveTheta(double w, double y, double startFromDeg) {
        double xK1 = startFromDeg;
        double xK2;
        for (int iteration = 0; iteration < maxIterations; iteration++) {
            double yK1 = getYFromW(w, xK1, y);
            if (Math.abs(yK1) < minAnsDiff) {
                return Optional.of(new Pair<>(xK1, w));
            }

            xK2 = getDiffedAngle(xK1, minAnsDiff / 100);
            double yK2 = getYFromW(w, xK2, y);

            xK1 = xK1 - yK1 / ((yK1 - yK2) / (xK1 - xK2));
        }
        return Optional.empty();
    }

    Optional<Pair<Double, Double>> solveTheta(double delta) {
        return manager.getTargetPos().map(
                targetPos -> manager.getTargetVelocity().orElse(new Vec3d(0, 0, 0))
                                .subtract(getPlayerVelocity())
                                .multiply(delta * 20).add(targetPos)
        ).flatMap(targetPos -> solveTheta(targetPos.add(getPlayer().getPos().multiply(-1)).horizontalLength(),
                targetPos.y - getPlayer().getEyeY(), -getPlayer().getPitch()));
    }

    Optional<Pair<Double, Double>> solveTheta() {
        for (double delta = 0; delta <= 100; delta += 0.01){
            Optional<Pair<Double, Double>> thetaOpt = solveTheta(delta);
            if (thetaOpt.isPresent() // 0.4 is the diff, I don't know why it's 0.4, but it works
                    && (Math.abs(getTFromW(thetaOpt.get().getRight(), thetaOpt.get().getLeft()) + 0.4 - delta) <= 0.1
                            || manager.getTargetVelocity().map(v -> v.subtract(getPlayerVelocity()))
                                .orElse(new Vec3d(0, 0, 1)).length() < 0.01)) {
                double finalDelta = delta;
                Vec3d finalTargetPos = manager.getTargetPos().map(
                        targetPos -> manager.getTargetVelocity().orElse(new Vec3d(0, 0, 0))
                                .subtract(getPlayerVelocity())
                                .multiply(finalDelta * 20).add(targetPos)
                ).orElseThrow();
                double theta = Math.atan2(finalTargetPos.z - getPlayer().getZ(),
                        finalTargetPos.x - getPlayer().getX()) * 180 / Math.PI + 360 - 90;
                return Optional.of(new Pair<>(thetaOpt.get().getLeft(), theta % 360));
            }
        }
        return Optional.empty();
    }

    abstract public boolean isEnabled();

    abstract public void tick();

    public Optional<Vec3d> processTargetPos(Vec3d targetPos) {
        if (targetPos == null) return Optional.empty();
        return Optional.of(targetPos);
    }
}
