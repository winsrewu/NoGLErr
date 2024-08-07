package org.jawbts.noglerr.commands;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.command.EntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.NumberRange;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.Util;
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
    private final List<Predicate<Entity>> predicates;
    private final NumberRange.DoubleRange distance;
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
        predicates = ((EntitySelectorAccessor) entitySelector).getPredicates();
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
                if (clientPlayer.getName().getLiteralString().equalsIgnoreCase(playerName)) {
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
        Box boxIn = getOffsetBox(vec3d);
        Predicate<Entity> predicate;

        if (senderOnly) {
            predicate = getPositionPredicate(vec3d, boxIn, null);
            return predicate.test(mc.player) ? List.of(mc.player) : List.of();
        }

        predicate = getPositionPredicate(vec3d, boxIn, null);

        List<Entity> ansList = new ArrayList<>();
        for (Entity entity : mc.world.getEntities()) {
            if (predicate.test(entity) && !VarEntityHandler.isVarEntity(entity)) {
                ansList.add(entity);
            }
        }

        return getEntities(vec3d, ansList);
    }

    public List<AbstractClientPlayerEntity> getPlayers(MinecraftClient mc) {
        if (mc == null || mc.world == null) {
            return Collections.emptyList();
        }
        List<AbstractClientPlayerEntity> list = mc.world.getPlayers();
        if (playerName != null) {
            for (AbstractClientPlayerEntity clientPlayer : list) {
                if (clientPlayer.getName().getLiteralString().equalsIgnoreCase(playerName)) {
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
        Box boxIn = getOffsetBox(vec3d);
        Predicate<Entity> predicate = getPositionPredicate(vec3d, boxIn, null);
        if (senderOnly) {
            ClientPlayerEntity clientPlayer = mc.player;
            if (predicate.test(clientPlayer)) {
                return List.of(clientPlayer);
            }
            return List.of();
        }

        List<AbstractClientPlayerEntity> ansList = new ArrayList<>();
        for (AbstractClientPlayerEntity clientPlayer : list) {
            if (predicate.test(clientPlayer)) {
                ansList.add(clientPlayer);
            }
        }

        return getEntities(vec3d, ansList);
    }

    private Predicate<Entity> getPositionPredicate(Vec3d pos, @Nullable Box box, @Nullable FeatureSet enabledFeatures) {
        boolean bl = enabledFeatures != null;
        boolean bl2 = box != null;
        boolean bl3 = !this.distance.isDummy();
        int i = (bl ? 1 : 0) + (bl2 ? 1 : 0) + (bl3 ? 1 : 0);
        Object list;
        if (i == 0) {
            list = this.predicates;
        } else {
            List<Predicate<Entity>> list2 = new ObjectArrayList(this.predicates.size() + i);
            list2.addAll(this.predicates);
            if (bl) {
                list2.add((entity) -> entity.getType().isEnabled(enabledFeatures));
            }

            if (bl2) {
                list2.add((entity) -> box.intersects(entity.getBoundingBox()));
            }

            if (bl3) {
                list2.add((entity) -> this.distance.testSqrt(entity.squaredDistanceTo(pos)));
            }

            list = list2;
        }

        return Util.allOf((List)list);
    }

    @Nullable
    private Box getOffsetBox(Vec3d offset) {
        return box != null ? box.offset(offset) : null;
    }

    private <T extends Entity> List<T> getEntities(Vec3d pos, List<T> entities) {
        if (entities.size() > 1) {
            this.sorter.accept(pos, entities);
        }

        return entities.subList(0, Math.min(this.limit, entities.size()));
    }
}
