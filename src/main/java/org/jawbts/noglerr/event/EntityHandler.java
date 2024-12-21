package org.jawbts.noglerr.event;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.List;

public class EntityHandler {
    private static final EntityHandler INSTANCE = new EntityHandler();
    private static int entityCounter;
    private List<Entity> noglerrEntities;
    private EntityHandler() {
        noglerrEntities = new ArrayList<>();
        entityCounter = 0;
    }

    public static EntityHandler getInstance() {
        return INSTANCE;
    }

    public boolean isNoglerrEntity(Entity e) {
        return noglerrEntities.contains(e);
    }

    public void addEntity(Entity e) {
        noglerrEntities.add(e);
    }

    public void removeEntity(Entity e) {
        noglerrEntities.remove(e);
    }

    public int getNextEntityId(ClientWorld world) {
        while (world.getEntityById(entityCounter) != null) {
            entityCounter++;
        }
        return entityCounter;
    }

    public void tick32() {
        noglerrEntities = new ArrayList<>(noglerrEntities.stream().filter(Entity::isAlive).toList());
    }
}
