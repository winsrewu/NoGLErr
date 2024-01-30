package org.jawbts.noglerr.tweak.var.javascript.proxy;

import net.minecraft.block.BlockState;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.*;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityChangeListener;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.event.listener.EntityGameEventHandler;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
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

    public void updateTrackedPosition(Vec3d pos) {
        entity.updateTrackedPosition(pos);
    }

    public Vec3d getTrackedPosition() {
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

    public Set<String> getScoreboardTags() {
        return entity.getScoreboardTags();
    }

    public boolean addScoreboardTag(String tag) {
        return entity.addScoreboardTag(tag);
    }

    public boolean removeScoreboardTag(String tag) {
        return entity.removeScoreboardTag(tag);
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

    public boolean isInRange(net.minecraft.entity.Entity other, double radius) {
        return entity.isInRange(other, radius);
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

    public void resetNetherPortalCooldown() {
        entity.resetNetherPortalCooldown();
    }

    public boolean hasNetherPortalCooldown() {
        return entity.hasNetherPortalCooldown();
    }

    public int getMaxNetherPortalTime() {
        return entity.getMaxNetherPortalTime();
    }

    public void setOnFireFromLava() {
        entity.setOnFireFromLava();
    }

    public void setOnFireFor(int seconds) {
        entity.setOnFireFor(seconds);
    }

    public void setFireTicks(int ticks) {
        entity.setFireTicks(ticks);
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

    public boolean isOnGround() {
        return entity.isOnGround();
    }

    public void move(MovementType movementType, Vec3d movement) {
        entity.move(movementType, movement);
    }

    public BlockPos getLandingPos() {
        return entity.getLandingPos();
    }

    public void emitGameEvent(GameEvent event, @Nullable net.minecraft.entity.Entity entity, BlockPos pos) {
        this.entity.emitGameEvent(event, entity, pos);
    }

    public void emitGameEvent(GameEvent event, @Nullable net.minecraft.entity.Entity entity) {
        this.entity.emitGameEvent(event, entity);
    }

    public void emitGameEvent(GameEvent event, BlockPos pos) {
        entity.emitGameEvent(event, pos);
    }

    public void emitGameEvent(GameEvent event) {
        entity.emitGameEvent(event);
    }

    public void playSound(SoundEvent sound, float volume, float pitch) {
        entity.playSound(sound, volume, pitch);
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

    public boolean isSubmergedInWater() {
        return entity.isSubmergedInWater();
    }

    public void updateSwimming() {
        entity.updateSwimming();
    }

    public boolean shouldSpawnSprintingParticles() {
        return entity.shouldSpawnSprintingParticles();
    }

    public boolean isSubmergedIn(Tag<Fluid> fluidTag) {
        return entity.isSubmergedIn(fluidTag);
    }

    public boolean isInLava() {
        return entity.isInLava();
    }

    public void updateVelocity(float speed, Vec3d movementInput) {
        entity.updateVelocity(speed, movementInput);
    }

    public float getBrightnessAtEyes() {
        return entity.getBrightnessAtEyes();
    }

    public void updatePositionAndAngles(double x, double y, double z, float yaw, float pitch) {
        entity.updatePositionAndAngles(x, y, z, yaw, pitch);
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

    public void addVelocity(double deltaX, double deltaY, double deltaZ) {
        entity.addVelocity(deltaX, deltaY, deltaZ);
    }

    public boolean damage(DamageSource source, float amount) {
        return entity.damage(source, amount);
    }

    public Vec3d getRotationVec(float tickDelta) {
        return entity.getRotationVec(tickDelta);
    }

    public float getPitch(float tickDelta) {
        return entity.getPitch(tickDelta);
    }

    public float getYaw(float tickDelta) {
        return entity.getYaw(tickDelta);
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

    public boolean collides() {
        return entity.collides();
    }

    public boolean isPushable() {
        return entity.isPushable();
    }

    public void updateKilledAdvancementCriterion(net.minecraft.entity.Entity killer, int score, DamageSource damageSource) {
        entity.updateKilledAdvancementCriterion(killer, score, damageSource);
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

    public double getHeightOffset() {
        return entity.getHeightOffset();
    }

    public double getMountedHeightOffset() {
        return entity.getMountedHeightOffset();
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

    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps, boolean interpolate) {
        entity.updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolationSteps, interpolate);
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

    public Vec2f getRotationClient() {
        return entity.getRotationClient();
    }

    public Vec3d getRotationVecClient() {
        return entity.getRotationVecClient();
    }

    public void setInNetherPortal(BlockPos pos) {
        entity.setInNetherPortal(pos);
    }

    public int getDefaultNetherPortalCooldown() {
        return entity.getDefaultNetherPortalCooldown();
    }

    public void setVelocityClient(double x, double y, double z) {
        entity.setVelocityClient(x, y, z);
    }

    public void handleStatus(byte status) {
        entity.handleStatus(status);
    }

    public void animateDamage() {
        entity.animateDamage();
    }

    public Iterable<ItemStack> getItemsHand() {
        return entity.getItemsHand();
    }

    public Iterable<ItemStack> getArmorItems() {
        return entity.getArmorItems();
    }

    public Iterable<ItemStack> getItemsEquipped() {
        return entity.getItemsEquipped();
    }

    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        entity.equipStack(slot, stack);
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

    public boolean canBeRiddenInWater() {
        return entity.canBeRiddenInWater();
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

    public boolean shouldLeaveSwimmingPose() {
        return entity.shouldLeaveSwimmingPose();
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

    @Nullable
    public EntityGameEventHandler getGameEventHandler() {
        return entity.getGameEventHandler();
    }

    @Nullable
    public AbstractTeam getScoreboardTeam() {
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

    public boolean isFreezing() {
        return entity.isFreezing();
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

    public void onKilledOther(ServerWorld world, LivingEntity other) {
        entity.onKilledOther(world, other);
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
    public net.minecraft.entity.Entity moveToWorld(ServerWorld destination) {
        return entity.moveToWorld(destination);
    }

    public boolean canUsePortals() {
        return entity.canUsePortals();
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

    public String getEntityName() {
        return entity.getEntityName();
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

    public void teleport(double destX, double destY, double destZ) {
        entity.teleport(destX, destY, destZ);
    }

    public void requestTeleportAndDismount(double destX, double destY, double destZ) {
        entity.requestTeleportAndDismount(destX, destY, destZ);
    }

    public void requestTeleport(double destX, double destY, double destZ) {
        entity.requestTeleport(destX, destY, destZ);
    }

    public boolean shouldRenderName() {
        return entity.shouldRenderName();
    }

    public void onTrackedDataSet(TrackedData<?> data) {
        entity.onTrackedDataSet(data);
    }

    public void calculateDimensions() {
        entity.calculateDimensions();
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

    public Vec3d getLeashOffset() {
        return entity.getLeashOffset();
    }

    public StackReference getStackReference(int mappedIndex) {
        return entity.getStackReference(mappedIndex);
    }

    public void sendSystemMessage(Text message, UUID sender) {
        entity.sendSystemMessage(message, sender);
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

    public boolean isImmuneToExplosion() {
        return entity.isImmuneToExplosion();
    }

    public void applyDamageEffects(LivingEntity attacker, net.minecraft.entity.Entity target) {
        entity.applyDamageEffects(attacker, target);
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

    @Nullable
    public net.minecraft.entity.Entity getPrimaryPassenger() {
        return entity.getPrimaryPassenger();
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

    public boolean hasPassengerType(Predicate<net.minecraft.entity.Entity> predicate) {
        return entity.hasPassengerType(predicate);
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

    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        return entity.updatePassengerForDismount(passenger);
    }

    @Nullable
    public net.minecraft.entity.Entity getVehicle() {
        return entity.getVehicle();
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

    public boolean updateMovementInFluid(Tag<Fluid> tag, double d) {
        return entity.updateMovementInFluid(tag, d);
    }

    public boolean isRegionUnloaded() {
        return entity.isRegionUnloaded();
    }

    public double getFluidHeight(Tag<Fluid> fluid) {
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

    public Packet<?> createSpawnPacket() {
        return entity.createSpawnPacket();
    }

    public EntityDimensions getDimensions(EntityPose pose) {
        return entity.getDimensions(pose);
    }

    public Vec3d getPos() {
        return entity.getPos();
    }

    public BlockPos getBlockPos() {
        return entity.getBlockPos();
    }

    public BlockState getBlockStateAtPos() {
        return entity.getBlockStateAtPos();
    }

    public BlockPos getCameraBlockPos() {
        return entity.getCameraBlockPos();
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

    public Vec3d method_30951(float f) {
        return entity.method_30951(f);
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

    public float getYaw() {
        return entity.getYaw();
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

    public void setListener(EntityChangeListener listener) {
        entity.setListener(listener);
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

    public boolean cannotBeSilenced() {
        return entity.cannotBeSilenced();
    }

    private final net.minecraft.entity.Entity entity;
    public Entity(net.minecraft.entity.Entity entity) {
        this.entity = entity;
    }

    public String getNbt() {
        return writeNbt(new NbtCompound()).asString();
    }
}
