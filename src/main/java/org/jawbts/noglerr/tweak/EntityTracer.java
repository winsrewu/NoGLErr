package org.jawbts.noglerr.tweak;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.jawbts.noglerr.util.PlayerMessageSender;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class EntityTracer {
    private static final Queue<EntityTracer> tracers = new ArrayDeque<>();
    private final Entity entity;
    private final List<Vec3d> positions = new ArrayList<>();

    private EntityTracer(Entity entity) {
        this.entity = entity;
        tracers.add(this);
    }

    public Entity getEntity() {
        return entity;
    }

    public static void tickAll() {
        tracers.removeIf(t -> t.entity.isRemoved());
        for (EntityTracer tracer : tracers) {
            tracer.tick();
        }
    }

    public static EntityTracer getTracer(Entity entity) {
        if (tracers.stream().anyMatch(t -> t.entity == entity)) {
            return tracers.stream().filter(t -> t.entity == entity).findFirst().orElseThrow();
        }
        return new EntityTracer(entity);
    }

    public void tick() {
        positions.add(entity.getPos());
        if (positions.size() > 100) {
            positions.remove(0);
        }
    }

    public Vec3d getVelocity() {
        if (positions.size() < 2) {
            return Vec3d.ZERO;
        }
        Vec3d lastPos = positions.get(positions.size() - 2);
        Vec3d currentPos = positions.get(positions.size() - 1);
        return currentPos.subtract(lastPos);
    }
}
