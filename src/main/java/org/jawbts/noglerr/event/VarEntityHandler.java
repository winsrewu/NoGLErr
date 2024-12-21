package org.jawbts.noglerr.event;

import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import org.jawbts.noglerr.tweak.Utils;
import org.jawbts.noglerr.tweak.var.TargetDataHandler;
import org.jawbts.noglerr.tweak.var.TargetManager;
import org.jawbts.noglerr.tweak.var.TextDataHandler;
import org.jawbts.noglerr.tweak.var.TextManager;
import org.jawbts.noglerr.util.PlayerMessageSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VarEntityHandler {
    private static final Map<Entity, Entity> varEntityMap = new HashMap<>();
    private static final List<Entity> varEntityList = new ArrayList<>();

    private static void addVarEntity(Entity entity, ClientWorld world) {
        int entityCounter = EntityHandler.getInstance().getNextEntityId(world);

        ArmorStandEntity armorStand = new ArmorStandEntity(entity.world, entity.getX(), entity.getY() - 1.5, entity.getZ());
        armorStand.setCustomNameVisible(true);
        armorStand.setId(entityCounter);
        armorStand.setInvisible(true);
        world.addEntity(entityCounter, armorStand);

        varEntityMap.put(entity, armorStand);
        varEntityList.add(armorStand);
        EntityHandler.getInstance().addEntity(armorStand);
    }

    public static void handleEntityDetail(MinecraftClient mc, ClientPlayerEntity player, ClientWorld world) {
        for (TargetDataHandler targetDataHandler : TargetManager.getInstance().getHandlerList()) {
            TextDataHandler textDataHandler = TextManager.getInstance().getHandler(targetDataHandler.getName());

            try {
                for (Entity entity : targetDataHandler.getEntities()) {
                    if (!varEntityMap.containsKey(entity)) addVarEntity(entity, world);
                    else if (!varEntityMap.get(entity).getEntityWorld().equals(entity.getEntityWorld())) {
                        varEntityMap.remove(entity);
                        varEntityList.remove(varEntityMap.get(entity));
                        EntityHandler.getInstance().removeEntity(varEntityMap.get(entity));
                        addVarEntity(entity, world);
                    }

                    Entity varShower = varEntityMap.get(entity);
                    if (varShower == null) {
                        varEntityMap.remove(entity);
                        varEntityList.remove(null);
                        EntityHandler.getInstance().removeEntity(null);
                        continue;
                    }

                    varShower.ignoreCameraFrustum = player.squaredDistanceTo(varShower) < 100;

                    varShower.setPos(entity.getX(), entity.getY() - 1.7 + entity.getHeight(), entity.getZ());
                    if (textDataHandler == null) {
                        varShower.setCustomName(Utils.createText("noglerr.command.textNotExists", "red"));
                        continue;
                    }

                    try {
                        varShower.setCustomName(Utils.createTextFromJsonOrString(textDataHandler.getTreatedData(entity, world, player)));
                    } catch (JsonSyntaxException e) {
                        varShower.setCustomName(Utils.createText("noglerr.command.textNotJson", "red"));
                    }
                }
            } catch (CommandSyntaxException e) {
                PlayerMessageSender.getInstance().add("red", e.getContext());
            }

            List<Entity> needDel = new ArrayList<>();
            for (Entity e1 : varEntityList) {
                for (Map.Entry<Entity, Entity> entry : varEntityMap.entrySet()) {
                    if (!entry.getValue().equals(e1)) {
                        continue;
                    }
                    if (entry.getKey() == null || !entry.getKey().isAlive()) {
                        varEntityMap.remove(entry.getKey());
                        needDel.add(e1);
                        e1.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
                        if (mc.world != null) {
                            mc.world.removeEntity(e1.getId(), Entity.RemovalReason.UNLOADED_WITH_PLAYER);
                        }
                        break;
                    }
                }
            }

            for (Entity e : needDel) {
                varEntityList.remove(e);
                EntityHandler.getInstance().removeEntity(e);
            }
        }
    }

    public static void clearAll() {
        if (varEntityList.isEmpty()) {
            return;
        }

        for (Entity e : varEntityList) {
            e.setRemoved(Entity.RemovalReason.UNLOADED_WITH_PLAYER);
            if (MinecraftClient.getInstance().world != null) {
                MinecraftClient.getInstance().world.removeEntity(e.getId(), Entity.RemovalReason.UNLOADED_WITH_PLAYER);
            }
            EntityHandler.getInstance().removeEntity(e);
        }
        varEntityMap.clear();
        varEntityList.clear();
    }
}
