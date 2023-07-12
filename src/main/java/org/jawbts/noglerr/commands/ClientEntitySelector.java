package org.jawbts.noglerr.commands;

import com.google.common.collect.Lists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.NumberRange;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.jawbts.noglerr.event.VarEntityHandler;
import org.jawbts.noglerr.mixin.EntitySelectorAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class ClientEntitySelector {
    private final int limit;
    private final boolean includesNonPlayers;
    private final boolean localWorldOnly;
    private final Predicate<Entity> basePredicate;
    private final NumberRange.FloatRange distance;
    private final Function<Vec3d, Vec3d> positionOffset;
    @Nullable
    private final Box box;
    private final BiConsumer<Vec3d, List<? extends Entity>> sorter;
    private final boolean senderOnly;
    @Nullable
    private final String playerName;
    @Nullable
    private final UUID uuid;
    private final TypeFilter<Entity, ?> entityFilter;
    private final boolean usesAt;

    public ClientEntitySelector(EntitySelector entitySelector) {
        limit = entitySelector.getLimit();
        includesNonPlayers = entitySelector.includesNonPlayers();
        localWorldOnly = entitySelector.isLocalWorldOnly();
        basePredicate = ((EntitySelectorAccessor) entitySelector).getBasePredicate();
        distance = ((EntitySelectorAccessor) entitySelector).getDistance();
        positionOffset = ((EntitySelectorAccessor) entitySelector).getPositionOffset();
        box = ((EntitySelectorAccessor) entitySelector).getBox();
        sorter = ((EntitySelectorAccessor) entitySelector).getSorter();
        senderOnly = entitySelector.isSenderOnly();
        playerName = ((EntitySelectorAccessor) entitySelector).getPlayerName();
        uuid = ((EntitySelectorAccessor) entitySelector).getUuid();
        entityFilter = ((EntitySelectorAccessor) entitySelector).getEntityFilter();
        usesAt = entitySelector.usesAt();
    }

    public List<? extends Entity> getEntities(MinecraftClient mc) {
        if (mc == null || mc.world == null) {
            return Collections.emptyList();
        }
        if (!this.includesNonPlayers) {
            return getPlayers(mc);
        }
        if (playerName != null) {
            for (AbstractClientPlayerEntity clientPlayer : mc.world.getPlayers()) {
                if (clientPlayer.getName().asString().equalsIgnoreCase(playerName)) {
                    return Lists.newArrayList(clientPlayer);
                }
                return Collections.emptyList();
            }
        }
        if (uuid != null) {
            for (Entity entity : mc.world.getEntities()) {
                if (entity.getUuid() == uuid) {
                    return Lists.newArrayList(entity);
                }
            }
        }

        if (mc.player == null) {
            return Collections.emptyList();
        }

        Vec3d vec3d = positionOffset.apply(mc.player.getPos());
        Predicate<Entity> predicate = getPositionPredicate(vec3d);

        List<Entity> ansList = new ArrayList<>();
        for (Entity entity : mc.world.getEntities()) {
            if (predicate.test(entity) && !VarEntityHandler.isVarEntity(entity)) {
                ansList.add(entity);
            }
        }

        return ansList;
    }

    public List<AbstractClientPlayerEntity> getPlayers(MinecraftClient mc) {
        if (mc == null || mc.world == null) {
            return Collections.emptyList();
        }
        List<AbstractClientPlayerEntity> list = mc.world.getPlayers();
        if (playerName != null) {
            for (AbstractClientPlayerEntity clientPlayer : list) {
                if (clientPlayer.getName().asString().equalsIgnoreCase(playerName)) {
                    return Lists.newArrayList(clientPlayer);
                }
                return Collections.emptyList();
            }
        }
        if (uuid != null) {
            for (AbstractClientPlayerEntity clientPlayer : list) {
                if (clientPlayer.getUuid() == uuid) {
                    return Lists.newArrayList(clientPlayer);
                }
                return Collections.emptyList();
            }
        }
        if (mc.player == null) {
            return Collections.emptyList();
        }

        Vec3d vec3d = positionOffset.apply(mc.player.getPos());
        Predicate<Entity> predicate = getPositionPredicate(vec3d);
        if (senderOnly) {
            ClientPlayerEntity clientPlayer = mc.player;
            if (predicate.test(clientPlayer)) {
                return Lists.newArrayList(clientPlayer);
            }
            return Collections.emptyList();
        }

        List<AbstractClientPlayerEntity> ansList = new ArrayList<>();
        for (AbstractClientPlayerEntity clientPlayer : list) {
            if (predicate.test(clientPlayer)) {
                ansList.add(clientPlayer);
            }
        }

        return ansList;
    }

    private Predicate<Entity> getPositionPredicate(Vec3d vec3d) {
        Predicate<Entity> predicate = basePredicate;
        if (box != null) {
            Box box = this.box.offset(vec3d);
            predicate = predicate.and(entity -> box.intersects(entity.getBoundingBox()));
        }

        if (!distance.isDummy()) {
            predicate = predicate.and(entity -> this.distance.testSqrt(entity.squaredDistanceTo(vec3d)));
        }

        return predicate;
    }

    private void appendEntitiesFromWorld(List<Entity> list, ClientWorld clientWorld, Vec3d vec3d, Predicate<Entity> predicate) {
        list.addAll(clientWorld.getEntitiesByType(entityFilter, box == null ? null : box.offset(vec3d), predicate));
    }
}
