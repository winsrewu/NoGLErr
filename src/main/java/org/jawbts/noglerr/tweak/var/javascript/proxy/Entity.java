package org.jawbts.noglerr.tweak.var.javascript.proxy;


import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Portal;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PositionFlag;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.EntityTrackerEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockLocating;
import net.minecraft.world.BlockView;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityChangeListener;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class Entity {
    public boolean collidesWithStateAtPos(BlockPos pos, BlockState state) {
        return entity.collidesWithStateAtPos(pos, state);
    }

    public int getTeamColorValue() {
        return entity.getTeamColorValue();
    }

    public boolean isSpectator() {
        return entity.isSpectator();
    }

    public void detach() {
        entity.detach();
    }

    public void updateTrackedPosition(double x, double y, double z) {
        entity.updateTrackedPosition(x, y, z);
    }

    public TrackedPosition getTrackedPosition() {
        return entity.getTrackedPosition();
    }

    public EntityType<?> getType() {
        return entity.getType();
    }

    public int getId() {
        return entity.getId();
    }

    public void setId(int id) {
        entity.setId(id);
    }

    public Set<String> getCommandTags() {
        return entity.getCommandTags();
    }

    public boolean addCommandTag(String tag) {
        return entity.addCommandTag(tag);
    }

    public boolean removeCommandTag(String tag) {
        return entity.removeCommandTag(tag);
    }

    public void kill() {
        entity.kill();
    }

    public void discard() {
        entity.discard();
    }

    public DataTracker getDataTracker() {
        return entity.getDataTracker();
    }

    public void remove(net.minecraft.entity.Entity.RemovalReason reason) {
        entity.remove(reason);
    }

    public void onRemoved() {
        entity.onRemoved();
    }

    public void setPose(EntityPose pose) {
        entity.setPose(pose);
    }

    public EntityPose getPose() {
        return entity.getPose();
    }

    public boolean isInPose(EntityPose pose) {
        return entity.isInPose(pose);
    }

    public boolean isInRange(net.minecraft.entity.Entity entity, double radius) {
        return this.entity.isInRange(entity, radius);
    }

    public boolean isInRange(net.minecraft.entity.Entity entity, double horizontalRadius, double verticalRadius) {
        return this.entity.isInRange(entity, horizontalRadius, verticalRadius);
    }

    public void setPosition(Vec3d pos) {
        entity.setPosition(pos);
    }

    public void setPosition(double x, double y, double z) {
        entity.setPosition(x, y, z);
    }

    public void changeLookDirection(double cursorDeltaX, double cursorDeltaY) {
        entity.changeLookDirection(cursorDeltaX, cursorDeltaY);
    }

    public void tick() {
        entity.tick();
    }

    public void baseTick() {
        entity.baseTick();
    }

    public void setOnFire(boolean onFire) {
        entity.setOnFire(onFire);
    }

    public void attemptTickInVoid() {
        entity.attemptTickInVoid();
    }

    public void resetPortalCooldown() {
        entity.resetPortalCooldown();
    }

    public void setPortalCooldown(int portalCooldown) {
        entity.setPortalCooldown(portalCooldown);
    }

    public int getPortalCooldown() {
        return entity.getPortalCooldown();
    }

    public boolean hasPortalCooldown() {
        return entity.hasPortalCooldown();
    }

    public void setOnFireFromLava() {
        entity.setOnFireFromLava();
    }

    public void setOnFireFor(float seconds) {
        entity.setOnFireFor(seconds);
    }

    public void setOnFireForTicks(int ticks) {
        entity.setOnFireForTicks(ticks);
    }

    public void setFireTicks(int fireTicks) {
        entity.setFireTicks(fireTicks);
    }

    public int getFireTicks() {
        return entity.getFireTicks();
    }

    public void extinguish() {
        entity.extinguish();
    }

    public boolean doesNotCollide(double offsetX, double offsetY, double offsetZ) {
        return entity.doesNotCollide(offsetX, offsetY, offsetZ);
    }

    public void setOnGround(boolean onGround) {
        entity.setOnGround(onGround);
    }

    public void setOnGround(boolean onGround, Vec3d movement) {
        entity.setOnGround(onGround, movement);
    }

    public boolean isSupportedBy(BlockPos pos) {
        return entity.isSupportedBy(pos);
    }

    public boolean isOnGround() {
        return entity.isOnGround();
    }

    public void move(MovementType movementType, Vec3d movement) {
        entity.move(movementType, movement);
    }

    public void extinguishWithSound() {
        entity.extinguishWithSound();
    }

    @Deprecated
    public BlockPos getLandingPos() {
        return entity.getLandingPos();
    }

    public BlockPos getVelocityAffectingPos() {
        return entity.getVelocityAffectingPos();
    }

    public BlockPos getSteppingPos() {
        return entity.getSteppingPos();
    }

    public BlockPos getWorldSpawnPos(ServerWorld world, BlockPos basePos) {
        return entity.getWorldSpawnPos(world, basePos);
    }

    public void emitGameEvent(RegistryEntry<GameEvent> event, @Nullable net.minecraft.entity.Entity entity) {
        this.entity.emitGameEvent(event, entity);
    }

    public void emitGameEvent(RegistryEntry<GameEvent> event) {
        entity.emitGameEvent(event);
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        entity.playSound(sound, volume, pitch);
    }

    public void playSoundIfNotSilent(SoundEvent event) {
        entity.playSoundIfNotSilent(event);
    }

    public boolean isSilent() {
        return entity.isSilent();
    }

    public void setSilent(boolean silent) {
        entity.setSilent(silent);
    }

    public boolean hasNoGravity() {
        return entity.hasNoGravity();
    }

    public void setNoGravity(boolean noGravity) {
        entity.setNoGravity(noGravity);
    }

    public double getFinalGravity() {
        return entity.getFinalGravity();
    }

    public boolean occludeVibrationSignals() {
        return entity.occludeVibrationSignals();
    }

    public boolean isFireImmune() {
        return entity.isFireImmune();
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return entity.handleFallDamage(fallDistance, damageMultiplier, damageSource);
    }

    public boolean isTouchingWater() {
        return entity.isTouchingWater();
    }

    public boolean isTouchingWaterOrRain() {
        return entity.isTouchingWaterOrRain();
    }

    public boolean isWet() {
        return entity.isWet();
    }

    public boolean isInsideWaterOrBubbleColumn() {
        return entity.isInsideWaterOrBubbleColumn();
    }

    public boolean isInFluid() {
        return entity.isInFluid();
    }

    public boolean isSubmergedInWater() {
        return entity.isSubmergedInWater();
    }

    public void updateSwimming() {
        entity.updateSwimming();
    }

    public BlockState getSteppingBlockState() {
        return entity.getSteppingBlockState();
    }

    public boolean shouldSpawnSprintingParticles() {
        return entity.shouldSpawnSprintingParticles();
    }

    public boolean isSubmergedIn(TagKey<Fluid> fluidTag) {
        return entity.isSubmergedIn(fluidTag);
    }

    public boolean isInLava() {
        return entity.isInLava();
    }

    public void updateVelocity(float speed, Vec3d movementInput) {
        entity.updateVelocity(speed, movementInput);
    }

    @Deprecated
    public float getBrightnessAtEyes() {
        return entity.getBrightnessAtEyes();
    }

    public void updatePositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        entity.updatePositionAndAngles(x, y, z, yaw, pitch);
    }

    public void setAngles(float yaw, float pitch) {
        entity.setAngles(yaw, pitch);
    }

    public void updatePosition(double x, double y, double z) {
        entity.updatePosition(x, y, z);
    }

    public void refreshPositionAfterTeleport(Vec3d pos) {
        entity.refreshPositionAfterTeleport(pos);
    }

    public void refreshPositionAfterTeleport(double x, double y, double z) {
        entity.refreshPositionAfterTeleport(x, y, z);
    }

    public void refreshPositionAndAngles(BlockPos pos, float yaw, float pitch) {
        entity.refreshPositionAndAngles(pos, yaw, pitch);
    }

    public void refreshPositionAndAngles(Vec3d pos, float yaw, float pitch) {
        entity.refreshPositionAndAngles(pos, yaw, pitch);
    }

    public void refreshPositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        entity.refreshPositionAndAngles(x, y, z, yaw, pitch);
    }

    public void resetPosition() {
        entity.resetPosition();
    }

    public float distanceTo(net.minecraft.entity.Entity entity) {
        return this.entity.distanceTo(entity);
    }

    public double squaredDistanceTo(double x, double y, double z) {
        return entity.squaredDistanceTo(x, y, z);
    }

    public double squaredDistanceTo(net.minecraft.entity.Entity entity) {
        return this.entity.squaredDistanceTo(entity);
    }

    public double squaredDistanceTo(Vec3d vector) {
        return entity.squaredDistanceTo(vector);
    }

    public void onPlayerCollision(PlayerEntity player) {
        entity.onPlayerCollision(player);
    }

    public void pushAwayFrom(net.minecraft.entity.Entity entity) {
        this.entity.pushAwayFrom(entity);
    }

    public void addVelocity(Vec3d velocity) {
        entity.addVelocity(velocity);
    }

    public void addVelocity(double deltaX, double deltaY, double deltaZ) {
        entity.addVelocity(deltaX, deltaY, deltaZ);
    }

    public boolean damage(DamageSource source, float amount) {
        return entity.damage(source, amount);
    }

    public Vec3d getRotationVec(float tickDelta) {
        return entity.getRotationVec(tickDelta);
    }

    public Direction getFacing() {
        return entity.getFacing();
    }

    public float getPitch(float tickDelta) {
        return entity.getPitch(tickDelta);
    }

    public float getYaw(float tickDelta) {
        return entity.getYaw(tickDelta);
    }

    public Vec3d getRotationVector(float pitch, float yaw) {
        return entity.getRotationVector(pitch, yaw);
    }

    public Vec3d getOppositeRotationVector(float tickDelta) {
        return entity.getOppositeRotationVector(tickDelta);
    }

    public Vec3d getEyePos() {
        return entity.getEyePos();
    }

    public Vec3d getCameraPosVec(float tickDelta) {
        return entity.getCameraPosVec(tickDelta);
    }

    public Vec3d getClientCameraPosVec(float tickDelta) {
        return entity.getClientCameraPosVec(tickDelta);
    }

    public Vec3d getLerpedPos(float delta) {
        return entity.getLerpedPos(delta);
    }

    public HitResult raycast(double maxDistance, float tickDelta, boolean includeFluids) {
        return entity.raycast(maxDistance, tickDelta, includeFluids);
    }

    public boolean canBeHitByProjectile() {
        return entity.canBeHitByProjectile();
    }

    public boolean canHit() {
        return entity.canHit();
    }

    public boolean isPushable() {
        return entity.isPushable();
    }

    public void updateKilledAdvancementCriterion(net.minecraft.entity.Entity entityKilled, int score, DamageSource damageSource) {
        entity.updateKilledAdvancementCriterion(entityKilled, score, damageSource);
    }

    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return entity.shouldRender(cameraX, cameraY, cameraZ);
    }

    public boolean shouldRender(double distance) {
        return entity.shouldRender(distance);
    }

    public boolean saveSelfNbt(NbtCompound nbt) {
        return entity.saveSelfNbt(nbt);
    }

    public boolean saveNbt(NbtCompound nbt) {
        return entity.saveNbt(nbt);
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        return entity.writeNbt(nbt);
    }

    public void readNbt(NbtCompound nbt) {
        entity.readNbt(nbt);
    }

    @Nullable
    public ItemEntity dropItem(ItemConvertible item) {
        return entity.dropItem(item);
    }

    @Nullable
    public ItemEntity dropItem(ItemConvertible item, int yOffset) {
        return entity.dropItem(item, yOffset);
    }

    @Nullable
    public ItemEntity dropStack(ItemStack stack) {
        return entity.dropStack(stack);
    }

    @Nullable
    public ItemEntity dropStack(ItemStack stack, float yOffset) {
        return entity.dropStack(stack, yOffset);
    }

    public boolean isAlive() {
        return entity.isAlive();
    }

    public boolean isInsideWall() {
        return entity.isInsideWall();
    }

    public ActionResult interact(PlayerEntity player, Hand hand) {
        return entity.interact(player, hand);
    }

    public boolean collidesWith(net.minecraft.entity.Entity other) {
        return entity.collidesWith(other);
    }

    public boolean isCollidable() {
        return entity.isCollidable();
    }

    public void tickRiding() {
        entity.tickRiding();
    }

    public void updatePassengerPosition(net.minecraft.entity.Entity passenger) {
        entity.updatePassengerPosition(passenger);
    }

    public void onPassengerLookAround(net.minecraft.entity.Entity passenger) {
        entity.onPassengerLookAround(passenger);
    }

    public Vec3d getVehicleAttachmentPos(net.minecraft.entity.Entity vehicle) {
        return entity.getVehicleAttachmentPos(vehicle);
    }

    public Vec3d getPassengerRidingPos(net.minecraft.entity.Entity passenger) {
        return entity.getPassengerRidingPos(passenger);
    }

    public boolean startRiding(net.minecraft.entity.Entity entity) {
        return this.entity.startRiding(entity);
    }

    public boolean isLiving() {
        return entity.isLiving();
    }

    public boolean startRiding(net.minecraft.entity.Entity entity, boolean force) {
        return this.entity.startRiding(entity, force);
    }

    public void removeAllPassengers() {
        entity.removeAllPassengers();
    }

    public void dismountVehicle() {
        entity.dismountVehicle();
    }

    public void stopRiding() {
        entity.stopRiding();
    }

    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps) {
        entity.updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolationSteps);
    }

    public double getLerpTargetX() {
        return entity.getLerpTargetX();
    }

    public double getLerpTargetY() {
        return entity.getLerpTargetY();
    }

    public double getLerpTargetZ() {
        return entity.getLerpTargetZ();
    }

    public float getLerpTargetPitch() {
        return entity.getLerpTargetPitch();
    }

    public float getLerpTargetYaw() {
        return entity.getLerpTargetYaw();
    }

    public void updateTrackedHeadRotation(float yaw, int interpolationSteps) {
        entity.updateTrackedHeadRotation(yaw, interpolationSteps);
    }

    public float getTargetingMargin() {
        return entity.getTargetingMargin();
    }

    public Vec3d getRotationVector() {
        return entity.getRotationVector();
    }

    public Vec3d getHandPosOffset(Item item) {
        return entity.getHandPosOffset(item);
    }

    public Vec2f getRotationClient() {
        return entity.getRotationClient();
    }

    public Vec3d getRotationVecClient() {
        return entity.getRotationVecClient();
    }

    public void tryUsePortal(Portal portal, BlockPos pos) {
        entity.tryUsePortal(portal, pos);
    }

    public int getDefaultPortalCooldown() {
        return entity.getDefaultPortalCooldown();
    }

    public void setVelocityClient(double x, double y, double z) {
        entity.setVelocityClient(x, y, z);
    }

    public void onDamaged(DamageSource damageSource) {
        entity.onDamaged(damageSource);
    }

    public void handleStatus(byte status) {
        entity.handleStatus(status);
    }

    public void animateDamage(float yaw) {
        entity.animateDamage(yaw);
    }

    public boolean isOnFire() {
        return entity.isOnFire();
    }

    public boolean hasVehicle() {
        return entity.hasVehicle();
    }

    public boolean hasPassengers() {
        return entity.hasPassengers();
    }

    public boolean shouldDismountUnderwater() {
        return entity.shouldDismountUnderwater();
    }

    public boolean shouldControlVehicles() {
        return entity.shouldControlVehicles();
    }

    public void setSneaking(boolean sneaking) {
        entity.setSneaking(sneaking);
    }

    public boolean isSneaking() {
        return entity.isSneaking();
    }

    public boolean bypassesSteppingEffects() {
        return entity.bypassesSteppingEffects();
    }

    public boolean bypassesLandingEffects() {
        return entity.bypassesLandingEffects();
    }

    public boolean isSneaky() {
        return entity.isSneaky();
    }

    public boolean isDescending() {
        return entity.isDescending();
    }

    public boolean isInSneakingPose() {
        return entity.isInSneakingPose();
    }

    public boolean isSprinting() {
        return entity.isSprinting();
    }

    public void setSprinting(boolean sprinting) {
        entity.setSprinting(sprinting);
    }

    public boolean isSwimming() {
        return entity.isSwimming();
    }

    public boolean isInSwimmingPose() {
        return entity.isInSwimmingPose();
    }

    public boolean isCrawling() {
        return entity.isCrawling();
    }

    public void setSwimming(boolean swimming) {
        entity.setSwimming(swimming);
    }

    public boolean isGlowingLocal() {
        return entity.isGlowingLocal();
    }

    public void setGlowing(boolean glowing) {
        entity.setGlowing(glowing);
    }

    public boolean isGlowing() {
        return entity.isGlowing();
    }

    public boolean isInvisible() {
        return entity.isInvisible();
    }

    public boolean isInvisibleTo(PlayerEntity player) {
        return entity.isInvisibleTo(player);
    }

    public boolean isOnRail() {
        return entity.isOnRail();
    }

    public void updateEventHandler(BiConsumer<EntityGameEventHandler<?>, ServerWorld> callback) {
        entity.updateEventHandler(callback);
    }

    @Nullable
    public Team getScoreboardTeam() {
        return entity.getScoreboardTeam();
    }

    public boolean isTeammate(net.minecraft.entity.Entity other) {
        return entity.isTeammate(other);
    }

    public boolean isTeamPlayer(AbstractTeam team) {
        return entity.isTeamPlayer(team);
    }

    public void setInvisible(boolean invisible) {
        entity.setInvisible(invisible);
    }

    public int getMaxAir() {
        return entity.getMaxAir();
    }

    public int getAir() {
        return entity.getAir();
    }

    public void setAir(int air) {
        entity.setAir(air);
    }

    public int getFrozenTicks() {
        return entity.getFrozenTicks();
    }

    public void setFrozenTicks(int frozenTicks) {
        entity.setFrozenTicks(frozenTicks);
    }

    public float getFreezingScale() {
        return entity.getFreezingScale();
    }

    public boolean isFrozen() {
        return entity.isFrozen();
    }

    public int getMinFreezeDamageTicks() {
        return entity.getMinFreezeDamageTicks();
    }

    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        entity.onStruckByLightning(world, lightning);
    }

    public void onBubbleColumnSurfaceCollision(boolean drag) {
        entity.onBubbleColumnSurfaceCollision(drag);
    }

    public void onBubbleColumnCollision(boolean drag) {
        entity.onBubbleColumnCollision(drag);
    }

    public boolean onKilledOther(ServerWorld world, LivingEntity other) {
        return entity.onKilledOther(world, other);
    }

    public void limitFallDistance() {
        entity.limitFallDistance();
    }

    public void onLanding() {
        entity.onLanding();
    }

    public void slowMovement(BlockState state, Vec3d multiplier) {
        entity.slowMovement(state, multiplier);
    }

    public Text getName() {
        return entity.getName();
    }

    public boolean isPartOf(net.minecraft.entity.Entity entity) {
        return this.entity.isPartOf(entity);
    }

    public float getHeadYaw() {
        return entity.getHeadYaw();
    }

    public void setHeadYaw(float headYaw) {
        entity.setHeadYaw(headYaw);
    }

    public void setBodyYaw(float bodyYaw) {
        entity.setBodyYaw(bodyYaw);
    }

    public boolean isAttackable() {
        return entity.isAttackable();
    }

    public boolean handleAttack(net.minecraft.entity.Entity attacker) {
        return entity.handleAttack(attacker);
    }

    public boolean isInvulnerableTo(DamageSource damageSource) {
        return entity.isInvulnerableTo(damageSource);
    }

    public boolean isInvulnerable() {
        return entity.isInvulnerable();
    }

    public void setInvulnerable(boolean invulnerable) {
        entity.setInvulnerable(invulnerable);
    }

    public void copyPositionAndRotation(net.minecraft.entity.Entity entity) {
        this.entity.copyPositionAndRotation(entity);
    }

    public void copyFrom(net.minecraft.entity.Entity original) {
        entity.copyFrom(original);
    }

    @Nullable
    public net.minecraft.entity.Entity teleportTo(TeleportTarget teleportTarget) {
        return entity.teleportTo(teleportTarget);
    }

    public void addPortalChunkTicketAt(BlockPos pos) {
        entity.addPortalChunkTicketAt(pos);
    }

    public Vec3d positionInPortal(Direction.Axis portalAxis, BlockLocating.Rectangle portalRect) {
        return entity.positionInPortal(portalAxis, portalRect);
    }

    public boolean canUsePortals(boolean allowVehicles) {
        return entity.canUsePortals(allowVehicles);
    }

    public boolean canTeleportBetween(World from, World to) {
        return entity.canTeleportBetween(from, to);
    }

    public float getEffectiveExplosionResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, float max) {
        return entity.getEffectiveExplosionResistance(explosion, world, pos, blockState, fluidState, max);
    }

    public boolean canExplosionDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float explosionPower) {
        return entity.canExplosionDestroyBlock(explosion, world, pos, state, explosionPower);
    }

    public int getSafeFallDistance() {
        return entity.getSafeFallDistance();
    }

    public boolean canAvoidTraps() {
        return entity.canAvoidTraps();
    }

    public void populateCrashReport(CrashReportSection section) {
        entity.populateCrashReport(section);
    }

    public boolean doesRenderOnFire() {
        return entity.doesRenderOnFire();
    }

    public void setUuid(UUID uuid) {
        entity.setUuid(uuid);
    }

    public UUID getUuid() {
        return entity.getUuid();
    }

    public String getUuidAsString() {
        return entity.getUuidAsString();
    }

    public String getNameForScoreboard() {
        return entity.getNameForScoreboard();
    }

    public boolean isPushedByFluids() {
        return entity.isPushedByFluids();
    }

    public Text getDisplayName() {
        return entity.getDisplayName();
    }

    public void setCustomName(@Nullable Text name) {
        entity.setCustomName(name);
    }

    @Nullable
    public Text getCustomName() {
        return entity.getCustomName();
    }

    public boolean hasCustomName() {
        return entity.hasCustomName();
    }

    public void setCustomNameVisible(boolean visible) {
        entity.setCustomNameVisible(visible);
    }

    public boolean isCustomNameVisible() {
        return entity.isCustomNameVisible();
    }

    public boolean teleport(ServerWorld world, double destX, double destY, double destZ, Set<PositionFlag> flags, float yaw, float pitch) {
        return entity.teleport(world, destX, destY, destZ, flags, yaw, pitch);
    }

    public void requestTeleportAndDismount(double destX, double destY, double destZ) {
        entity.requestTeleportAndDismount(destX, destY, destZ);
    }

    public void requestTeleport(double destX, double destY, double destZ) {
        entity.requestTeleport(destX, destY, destZ);
    }

    public void requestTeleportOffset(double offsetX, double offsetY, double offsetZ) {
        entity.requestTeleportOffset(offsetX, offsetY, offsetZ);
    }

    public boolean shouldRenderName() {
        return entity.shouldRenderName();
    }

    public void onDataTrackerUpdate(List<DataTracker.SerializedEntry<?>> entries) {
        entity.onDataTrackerUpdate(entries);
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        entity.onTrackedDataSet(data);
    }

    public void calculateDimensions() {
        entity.calculateDimensions();
    }

    public boolean recalculateDimensions(EntityDimensions previous) {
        return entity.recalculateDimensions(previous);
    }

    public Direction getHorizontalFacing() {
        return entity.getHorizontalFacing();
    }

    public Direction getMovementDirection() {
        return entity.getMovementDirection();
    }

    public boolean canBeSpectated(ServerPlayerEntity spectator) {
        return entity.canBeSpectated(spectator);
    }

    public Box getBoundingBox() {
        return entity.getBoundingBox();
    }

    public Box getVisibilityBoundingBox() {
        return entity.getVisibilityBoundingBox();
    }

    public void setBoundingBox(Box boundingBox) {
        entity.setBoundingBox(boundingBox);
    }

    public float getEyeHeight(EntityPose pose) {
        return entity.getEyeHeight(pose);
    }

    public float getStandingEyeHeight() {
        return entity.getStandingEyeHeight();
    }

    public Vec3d getLeashOffset(float tickDelta) {
        return entity.getLeashOffset(tickDelta);
    }

    public StackReference getStackReference(int mappedIndex) {
        return entity.getStackReference(mappedIndex);
    }

    public void sendMessage(Text message) {
        entity.sendMessage(message);
    }

    public World getEntityWorld() {
        return entity.getEntityWorld();
    }

    @Nullable
    public MinecraftServer getServer() {
        return entity.getServer();
    }

    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        return entity.interactAt(player, hitPos, hand);
    }

    public boolean isImmuneToExplosion(Explosion explosion) {
        return entity.isImmuneToExplosion(explosion);
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        entity.onStartedTrackingBy(player);
    }

    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        entity.onStoppedTrackingBy(player);
    }

    public float applyRotation(BlockRotation rotation) {
        return entity.applyRotation(rotation);
    }

    public float applyMirror(BlockMirror mirror) {
        return entity.applyMirror(mirror);
    }

    public boolean entityDataRequiresOperator() {
        return entity.entityDataRequiresOperator();
    }

    public ProjectileDeflection getProjectileDeflection(ProjectileEntity projectile) {
        return entity.getProjectileDeflection(projectile);
    }

    @Nullable
    public LivingEntity getControllingPassenger() {
        return entity.getControllingPassenger();
    }

    public boolean hasControllingPassenger() {
        return entity.hasControllingPassenger();
    }

    public List<net.minecraft.entity.Entity> getPassengerList() {
        return entity.getPassengerList();
    }

    @Nullable
    public net.minecraft.entity.Entity getFirstPassenger() {
        return entity.getFirstPassenger();
    }

    public boolean hasPassenger(net.minecraft.entity.Entity passenger) {
        return entity.hasPassenger(passenger);
    }

    public boolean hasPassenger(Predicate<net.minecraft.entity.Entity> predicate) {
        return entity.hasPassenger(predicate);
    }

    public Stream<net.minecraft.entity.Entity> streamSelfAndPassengers() {
        return entity.streamSelfAndPassengers();
    }

    public Stream<net.minecraft.entity.Entity> streamPassengersAndSelf() {
        return entity.streamPassengersAndSelf();
    }

    public Iterable<net.minecraft.entity.Entity> getPassengersDeep() {
        return entity.getPassengersDeep();
    }

    public int getPlayerPassengers() {
        return entity.getPlayerPassengers();
    }

    public boolean hasPlayerRider() {
        return entity.hasPlayerRider();
    }

    public net.minecraft.entity.Entity getRootVehicle() {
        return entity.getRootVehicle();
    }

    public boolean isConnectedThroughVehicle(net.minecraft.entity.Entity entity) {
        return this.entity.isConnectedThroughVehicle(entity);
    }

    public boolean hasPassengerDeep(net.minecraft.entity.Entity passenger) {
        return entity.hasPassengerDeep(passenger);
    }

    public boolean isLogicalSideForUpdatingMovement() {
        return entity.isLogicalSideForUpdatingMovement();
    }

    public boolean canMoveVoluntarily() {
        return entity.canMoveVoluntarily();
    }

    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        return entity.updatePassengerForDismount(passenger);
    }

    @Nullable
    public net.minecraft.entity.Entity getVehicle() {
        return entity.getVehicle();
    }

    @Nullable
    public net.minecraft.entity.Entity getControllingVehicle() {
        return entity.getControllingVehicle();
    }

    public PistonBehavior getPistonBehavior() {
        return entity.getPistonBehavior();
    }

    public SoundCategory getSoundCategory() {
        return entity.getSoundCategory();
    }

    public ServerCommandSource getCommandSource() {
        return entity.getCommandSource();
    }

    public boolean hasPermissionLevel(int permissionLevel) {
        return entity.hasPermissionLevel(permissionLevel);
    }

    public boolean shouldReceiveFeedback() {
        return entity.shouldReceiveFeedback();
    }

    public boolean shouldTrackOutput() {
        return entity.shouldTrackOutput();
    }

    public boolean shouldBroadcastConsoleToOps() {
        return entity.shouldBroadcastConsoleToOps();
    }

    public void lookAt(EntityAnchorArgumentType.EntityAnchor anchorPoint, Vec3d target) {
        entity.lookAt(anchorPoint, target);
    }

    public float lerpYaw(float delta) {
        return entity.lerpYaw(delta);
    }

    public boolean updateMovementInFluid(TagKey<Fluid> tag, double speed) {
        return entity.updateMovementInFluid(tag, speed);
    }

    public boolean isRegionUnloaded() {
        return entity.isRegionUnloaded();
    }

    public double getFluidHeight(TagKey<Fluid> fluid) {
        return entity.getFluidHeight(fluid);
    }

    public double getSwimHeight() {
        return entity.getSwimHeight();
    }

    public float getWidth() {
        return entity.getWidth();
    }

    public float getHeight() {
        return entity.getHeight();
    }

    public Packet<ClientPlayPacketListener> createSpawnPacket(EntityTrackerEntry entityTrackerEntry) {
        return entity.createSpawnPacket(entityTrackerEntry);
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        return entity.getDimensions(pose);
    }

    public EntityAttachments getAttachments() {
        return entity.getAttachments();
    }

    public Vec3d getPos() {
        return entity.getPos();
    }

    public Vec3d getSyncedPos() {
        return entity.getSyncedPos();
    }

    public BlockPos getBlockPos() {
        return entity.getBlockPos();
    }

    public BlockState getBlockStateAtPos() {
        return entity.getBlockStateAtPos();
    }

    public ChunkPos getChunkPos() {
        return entity.getChunkPos();
    }

    public Vec3d getVelocity() {
        return entity.getVelocity();
    }

    public void setVelocity(Vec3d velocity) {
        entity.setVelocity(velocity);
    }

    public void addVelocityInternal(Vec3d velocity) {
        entity.addVelocityInternal(velocity);
    }

    public void setVelocity(double x, double y, double z) {
        entity.setVelocity(x, y, z);
    }

    public int getBlockX() {
        return entity.getBlockX();
    }

    public double getX() {
        return entity.getX();
    }

    public double offsetX(double widthScale) {
        return entity.offsetX(widthScale);
    }

    public double getParticleX(double widthScale) {
        return entity.getParticleX(widthScale);
    }

    public int getBlockY() {
        return entity.getBlockY();
    }

    public double getY() {
        return entity.getY();
    }

    public double getBodyY(double heightScale) {
        return entity.getBodyY(heightScale);
    }

    public double getRandomBodyY() {
        return entity.getRandomBodyY();
    }

    public double getEyeY() {
        return entity.getEyeY();
    }

    public int getBlockZ() {
        return entity.getBlockZ();
    }

    public double getZ() {
        return entity.getZ();
    }

    public double offsetZ(double widthScale) {
        return entity.offsetZ(widthScale);
    }

    public double getParticleZ(double widthScale) {
        return entity.getParticleZ(widthScale);
    }

    public void setPos(double x, double y, double z) {
        entity.setPos(x, y, z);
    }

    public void checkDespawn() {
        entity.checkDespawn();
    }

    public Vec3d getLeashPos(float delta) {
        return entity.getLeashPos(delta);
    }

    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        entity.onSpawnPacket(packet);
    }

    @Nullable
    public ItemStack getPickBlockStack() {
        return entity.getPickBlockStack();
    }

    public void setInPowderSnow(boolean inPowderSnow) {
        entity.setInPowderSnow(inPowderSnow);
    }

    public boolean canFreeze() {
        return entity.canFreeze();
    }

    public boolean shouldEscapePowderSnow() {
        return entity.shouldEscapePowderSnow();
    }

    public float getYaw() {
        return entity.getYaw();
    }

    public float getBodyYaw() {
        return entity.getBodyYaw();
    }

    public void setYaw(float yaw) {
        entity.setYaw(yaw);
    }

    public float getPitch() {
        return entity.getPitch();
    }

    public void setPitch(float pitch) {
        entity.setPitch(pitch);
    }

    public boolean canSprintAsVehicle() {
        return entity.canSprintAsVehicle();
    }

    public float getStepHeight() {
        return entity.getStepHeight();
    }

    public void onExplodedBy(@Nullable net.minecraft.entity.Entity entity) {
        this.entity.onExplodedBy(entity);
    }

    public boolean isRemoved() {
        return entity.isRemoved();
    }

    @Nullable
    public net.minecraft.entity.Entity.RemovalReason getRemovalReason() {
        return entity.getRemovalReason();
    }

    public void setRemoved(net.minecraft.entity.Entity.RemovalReason reason) {
        entity.setRemoved(reason);
    }

    public void setChangeListener(EntityChangeListener changeListener) {
        entity.setChangeListener(changeListener);
    }

    public boolean shouldSave() {
        return entity.shouldSave();
    }

    public boolean isPlayer() {
        return entity.isPlayer();
    }

    public boolean canModifyAt(World world, BlockPos pos) {
        return entity.canModifyAt(world, pos);
    }

    public World getWorld() {
        return entity.getWorld();
    }

    public DamageSources getDamageSources() {
        return entity.getDamageSources();
    }

    public DynamicRegistryManager getRegistryManager() {
        return entity.getRegistryManager();
    }

    public Random getRandom() {
        return entity.getRandom();
    }

    public Vec3d getMovement() {
        return entity.getMovement();
    }

    @Nullable
    public ItemStack getWeaponStack() {
        return entity.getWeaponStack();
    }

    public boolean cannotBeSilenced() {
        return entity.cannotBeSilenced();
    }

    public Text getStyledDisplayName() {
        return entity.getStyledDisplayName();
    }

    public <A> @Nullable A getAttached(AttachmentType<A> type) {
        return entity.getAttached(type);
    }

    public <A> A getAttachedOrThrow(AttachmentType<A> type) {
        return entity.getAttachedOrThrow(type);
    }

    public <A> A getAttachedOrSet(AttachmentType<A> type, A defaultValue) {
        return entity.getAttachedOrSet(type, defaultValue);
    }

    public <A> A getAttachedOrCreate(AttachmentType<A> type, Supplier<A> initializer) {
        return entity.getAttachedOrCreate(type, initializer);
    }

    public <A> A getAttachedOrCreate(AttachmentType<A> type) {
        return entity.getAttachedOrCreate(type);
    }

    @Contract("_, !null -> !null")
    public <A> A getAttachedOrElse(AttachmentType<A> type, @Nullable A defaultValue) {
        return entity.getAttachedOrElse(type, defaultValue);
    }

    public <A> A getAttachedOrGet(AttachmentType<A> type, Supplier<A> defaultValue) {
        return entity.getAttachedOrGet(type, defaultValue);
    }

    public <A> @Nullable A setAttached(AttachmentType<A> type, @Nullable A value) {
        return entity.setAttached(type, value);
    }

    public boolean hasAttached(AttachmentType<?> type) {
        return entity.hasAttached(type);
    }

    public <A> @Nullable A removeAttached(AttachmentType<A> type) {
        return entity.removeAttached(type);
    }

    public <A> @Nullable A modifyAttached(AttachmentType<A> type, UnaryOperator<A> modifier) {
        return entity.modifyAttached(type, modifier);
    }

    private final net.minecraft.entity.Entity entity;
    public Entity(net.minecraft.entity.Entity entity) {
        this.entity = entity;
    }

    public String getNbt() {
        return writeNbt(new NbtCompound()).asString();
    }
}
