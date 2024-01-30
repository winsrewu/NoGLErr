package org.jawbts.noglerr.tweak.var.javascript.proxy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.TagManager;
import net.minecraft.util.TypeFilter;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.*;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.*;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import net.minecraft.world.level.ColorResolver;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class World {
    public boolean isClient() {
        return world.isClient();
    }

    @Nullable
    public MinecraftServer getServer() {
        return world.getServer();
    }

    public boolean isInBuildLimit(BlockPos pos) {
        return world.isInBuildLimit(pos);
    }

    public WorldChunk getWorldChunk(BlockPos pos) {
        return world.getWorldChunk(pos);
    }

    public WorldChunk getChunk(int i, int j) {
        return world.getChunk(i, j);
    }

    @Nullable
    public Chunk getChunk(int chunkX, int chunkZ, ChunkStatus leastStatus, boolean create) {
        return world.getChunk(chunkX, chunkZ, leastStatus, create);
    }

    public boolean setBlockState(BlockPos pos, BlockState state, int flags) {
        return world.setBlockState(pos, state, flags);
    }

    public boolean setBlockState(BlockPos pos, BlockState state, int flags, int maxUpdateDepth) {
        return world.setBlockState(pos, state, flags, maxUpdateDepth);
    }

    public void onBlockChanged(BlockPos pos, BlockState oldBlock, BlockState newBlock) {
        world.onBlockChanged(pos, oldBlock, newBlock);
    }

    public boolean removeBlock(BlockPos pos, boolean move) {
        return world.removeBlock(pos, move);
    }

    public boolean breakBlock(BlockPos pos, boolean drop, @Nullable Entity breakingEntity, int maxUpdateDepth) {
        return world.breakBlock(pos, drop, breakingEntity, maxUpdateDepth);
    }

    public void addBlockBreakParticles(BlockPos pos, BlockState state) {
        world.addBlockBreakParticles(pos, state);
    }

    public boolean setBlockState(BlockPos pos, BlockState state) {
        return world.setBlockState(pos, state);
    }

    public void updateListeners(BlockPos pos, BlockState oldState, BlockState newState, int flags) {
        world.updateListeners(pos, oldState, newState, flags);
    }

    public void scheduleBlockRerenderIfNeeded(BlockPos pos, BlockState old, BlockState updated) {
        world.scheduleBlockRerenderIfNeeded(pos, old, updated);
    }

    public void updateNeighborsAlways(BlockPos pos, Block block) {
        world.updateNeighborsAlways(pos, block);
    }

    public void updateNeighborsExcept(BlockPos pos, Block sourceBlock, Direction direction) {
        world.updateNeighborsExcept(pos, sourceBlock, direction);
    }

    public void updateNeighbor(BlockPos pos, Block sourceBlock, BlockPos neighborPos) {
        world.updateNeighbor(pos, sourceBlock, neighborPos);
    }

    public int getTopY(Heightmap.Type heightmap, int x, int z) {
        return world.getTopY(heightmap, x, z);
    }

    public LightingProvider getLightingProvider() {
        return world.getLightingProvider();
    }

    public BlockState getBlockState(BlockPos pos) {
        return world.getBlockState(pos);
    }

    public FluidState getFluidState(BlockPos pos) {
        return world.getFluidState(pos);
    }

    public boolean isDay() {
        return world.isDay();
    }

    public boolean isNight() {
        return world.isNight();
    }

    public void playSound(@Nullable PlayerEntity player, BlockPos pos, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        world.playSound(player, pos, sound, category, volume, pitch);
    }

    public void playSound(@Nullable PlayerEntity except, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        world.playSound(except, x, y, z, sound, category, volume, pitch);
    }

    public void playSoundFromEntity(@Nullable PlayerEntity except, Entity entity, SoundEvent sound, SoundCategory category, float volume, float pitch) {
        world.playSoundFromEntity(except, entity, sound, category, volume, pitch);
    }

    public void playSound(double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, boolean useDistance) {
        world.playSound(x, y, z, sound, category, volume, pitch, useDistance);
    }

    public void addParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        world.addParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
    }

    public void addParticle(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        world.addParticle(parameters, alwaysSpawn, x, y, z, velocityX, velocityY, velocityZ);
    }

    public void addImportantParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        world.addImportantParticle(parameters, x, y, z, velocityX, velocityY, velocityZ);
    }

    public void addImportantParticle(ParticleEffect parameters, boolean alwaysSpawn, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        world.addImportantParticle(parameters, alwaysSpawn, x, y, z, velocityX, velocityY, velocityZ);
    }

    public float getSkyAngleRadians(float tickDelta) {
        return world.getSkyAngleRadians(tickDelta);
    }

    public void addBlockEntityTicker(BlockEntityTickInvoker ticker) {
        world.addBlockEntityTicker(ticker);
    }

    public <T extends Entity> void tickEntity(Consumer<T> tickConsumer, T entity) {
        world.tickEntity(tickConsumer, entity);
    }

    public Explosion createExplosion(@Nullable Entity entity, double x, double y, double z, float power, Explosion.DestructionType destructionType) {
        return world.createExplosion(entity, x, y, z, power, destructionType);
    }

    public Explosion createExplosion(@Nullable Entity entity, double x, double y, double z, float power, boolean createFire, Explosion.DestructionType destructionType) {
        return world.createExplosion(entity, x, y, z, power, createFire, destructionType);
    }

    public Explosion createExplosion(@Nullable Entity entity, @Nullable DamageSource damageSource, @Nullable ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, Explosion.DestructionType destructionType) {
        return world.createExplosion(entity, damageSource, behavior, x, y, z, power, createFire, destructionType);
    }

    public String asString() {
        return world.asString();
    }

    @Nullable
    public BlockEntity getBlockEntity(BlockPos pos) {
        return world.getBlockEntity(pos);
    }

    public void addBlockEntity(BlockEntity blockEntity) {
        world.addBlockEntity(blockEntity);
    }

    public void removeBlockEntity(BlockPos pos) {
        world.removeBlockEntity(pos);
    }

    public boolean canSetBlock(BlockPos pos) {
        return world.canSetBlock(pos);
    }

    public boolean isDirectionSolid(BlockPos pos, Entity entity, Direction direction) {
        return world.isDirectionSolid(pos, entity, direction);
    }

    public boolean isTopSolid(BlockPos pos, Entity entity) {
        return world.isTopSolid(pos, entity);
    }

    public void calculateAmbientDarkness() {
        world.calculateAmbientDarkness();
    }

    public void setMobSpawnOptions(boolean spawnMonsters, boolean spawnAnimals) {
        world.setMobSpawnOptions(spawnMonsters, spawnAnimals);
    }

    public void close() throws IOException {
        world.close();
    }

    @Nullable
    public BlockView getChunkAsView(int chunkX, int chunkZ) {
        return world.getChunkAsView(chunkX, chunkZ);
    }

    public List<Entity> getOtherEntities(@Nullable Entity except, Box box, Predicate<? super Entity> predicate) {
        return world.getOtherEntities(except, box, predicate);
    }

    public <T extends Entity> List<T> getEntitiesByType(TypeFilter<Entity, T> filter, Box box, Predicate<? super T> predicate) {
        return world.getEntitiesByType(filter, box, predicate);
    }

    @Nullable
    public Entity getEntityById(int id) {
        return world.getEntityById(id);
    }

    public void markDirty(BlockPos pos) {
        world.markDirty(pos);
    }

    public int getSeaLevel() {
        return world.getSeaLevel();
    }

    public int getReceivedStrongRedstonePower(BlockPos pos) {
        return world.getReceivedStrongRedstonePower(pos);
    }

    public boolean isEmittingRedstonePower(BlockPos pos, Direction direction) {
        return world.isEmittingRedstonePower(pos, direction);
    }

    public int getEmittedRedstonePower(BlockPos pos, Direction direction) {
        return world.getEmittedRedstonePower(pos, direction);
    }

    public boolean isReceivingRedstonePower(BlockPos pos) {
        return world.isReceivingRedstonePower(pos);
    }

    public int getReceivedRedstonePower(BlockPos pos) {
        return world.getReceivedRedstonePower(pos);
    }

    public void disconnect() {
        world.disconnect();
    }

    public long getTime() {
        return world.getTime();
    }

    public long getTimeOfDay() {
        return world.getTimeOfDay();
    }

    public boolean canPlayerModifyAt(PlayerEntity player, BlockPos pos) {
        return world.canPlayerModifyAt(player, pos);
    }

    public void sendEntityStatus(Entity entity, byte status) {
        world.sendEntityStatus(entity, status);
    }

    public void addSyncedBlockEvent(BlockPos pos, Block block, int type, int data) {
        world.addSyncedBlockEvent(pos, block, type, data);
    }

    public WorldProperties getLevelProperties() {
        return world.getLevelProperties();
    }

    public GameRules getGameRules() {
        return world.getGameRules();
    }

    public float getThunderGradient(float delta) {
        return world.getThunderGradient(delta);
    }

    public void setThunderGradient(float thunderGradient) {
        world.setThunderGradient(thunderGradient);
    }

    public float getRainGradient(float delta) {
        return world.getRainGradient(delta);
    }

    public void setRainGradient(float rainGradient) {
        world.setRainGradient(rainGradient);
    }

    public boolean isThundering() {
        return world.isThundering();
    }

    public boolean isRaining() {
        return world.isRaining();
    }

    public boolean hasRain(BlockPos pos) {
        return world.hasRain(pos);
    }

    public boolean hasHighHumidity(BlockPos pos) {
        return world.hasHighHumidity(pos);
    }

    @Nullable
    public MapState getMapState(String id) {
        return world.getMapState(id);
    }

    public void putMapState(String id, MapState state) {
        world.putMapState(id, state);
    }

    public int getNextMapId() {
        return world.getNextMapId();
    }

    public void syncGlobalEvent(int eventId, BlockPos pos, int data) {
        world.syncGlobalEvent(eventId, pos, data);
    }

    public CrashReportSection addDetailsToCrashReport(CrashReport report) {
        return world.addDetailsToCrashReport(report);
    }

    public void setBlockBreakingInfo(int entityId, BlockPos pos, int progress) {
        world.setBlockBreakingInfo(entityId, pos, progress);
    }

    public void addFireworkParticle(double x, double y, double z, double velocityX, double velocityY, double velocityZ, @Nullable NbtCompound nbt) {
        world.addFireworkParticle(x, y, z, velocityX, velocityY, velocityZ, nbt);
    }

    public Scoreboard getScoreboard() {
        return world.getScoreboard();
    }

    public void updateComparators(BlockPos pos, Block block) {
        world.updateComparators(pos, block);
    }

    public LocalDifficulty getLocalDifficulty(BlockPos pos) {
        return world.getLocalDifficulty(pos);
    }

    public int getAmbientDarkness() {
        return world.getAmbientDarkness();
    }

    public void setLightningTicksLeft(int lightningTicksLeft) {
        world.setLightningTicksLeft(lightningTicksLeft);
    }

    public WorldBorder getWorldBorder() {
        return world.getWorldBorder();
    }

    public void sendPacket(Packet<?> packet) {
        world.sendPacket(packet);
    }

    public DimensionType getDimension() {
        return world.getDimension();
    }

    public RegistryKey<net.minecraft.world.World> getRegistryKey() {
        return world.getRegistryKey();
    }

    public Random getRandom() {
        return world.getRandom();
    }

    public boolean testBlockState(BlockPos pos, Predicate<BlockState> state) {
        return world.testBlockState(pos, state);
    }

    public boolean testFluidState(BlockPos pos, Predicate<FluidState> state) {
        return world.testFluidState(pos, state);
    }

    public RecipeManager getRecipeManager() {
        return world.getRecipeManager();
    }

    public TagManager getTagManager() {
        return world.getTagManager();
    }

    public BlockPos getRandomPosInChunk(int x, int y, int z, int i) {
        return world.getRandomPosInChunk(x, y, z, i);
    }

    public boolean isSavingDisabled() {
        return world.isSavingDisabled();
    }

    public Profiler getProfiler() {
        return world.getProfiler();
    }

    public Supplier<Profiler> getProfilerSupplier() {
        return world.getProfilerSupplier();
    }

    public BiomeAccess getBiomeAccess() {
        return world.getBiomeAccess();
    }

    public boolean isDebugWorld() {
        return world.isDebugWorld();
    }

    public long getLunarTime() {
        return world.getLunarTime();
    }

    public TickScheduler<Block> getBlockTickScheduler() {
        return world.getBlockTickScheduler();
    }

    public TickScheduler<Fluid> getFluidTickScheduler() {
        return world.getFluidTickScheduler();
    }

    public Difficulty getDifficulty() {
        return world.getDifficulty();
    }

    public ChunkManager getChunkManager() {
        return world.getChunkManager();
    }

    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return world.isChunkLoaded(chunkX, chunkZ);
    }

    public void updateNeighbors(BlockPos pos, Block block) {
        world.updateNeighbors(pos, block);
    }

    public void syncWorldEvent(@Nullable PlayerEntity player, int eventId, BlockPos pos, int data) {
        world.syncWorldEvent(player, eventId, pos, data);
    }

    public int getLogicalHeight() {
        return world.getLogicalHeight();
    }

    public void syncWorldEvent(int eventId, BlockPos pos, int data) {
        world.syncWorldEvent(eventId, pos, data);
    }

    public void emitGameEvent(@Nullable Entity entity, GameEvent event, BlockPos pos) {
        world.emitGameEvent(entity, event, pos);
    }

    public void emitGameEvent(GameEvent event, BlockPos pos) {
        world.emitGameEvent(event, pos);
    }

    public void emitGameEvent(GameEvent event, Entity emitter) {
        world.emitGameEvent(event, emitter);
    }

    public void emitGameEvent(@Nullable Entity entity, GameEvent event, Entity emitter) {
        world.emitGameEvent(entity, event, emitter);
    }

    public <T extends BlockEntity> Optional<T> getBlockEntity(BlockPos pos, BlockEntityType<T> type) {
        return world.getBlockEntity(pos, type);
    }

    public Stream<VoxelShape> getEntityCollisions(@Nullable Entity entity, Box box, Predicate<Entity> predicate) {
        return world.getEntityCollisions(entity, box, predicate);
    }

    public boolean intersectsEntities(@Nullable Entity entity, VoxelShape shape) {
        return world.intersectsEntities(entity, shape);
    }

    public BlockPos getTopPosition(Heightmap.Type heightmap, BlockPos pos) {
        return world.getTopPosition(heightmap, pos);
    }

    public DynamicRegistryManager getRegistryManager() {
        return world.getRegistryManager();
    }

    public Optional<RegistryKey<Biome>> getBiomeKey(BlockPos pos) {
        return world.getBiomeKey(pos);
    }

    public <T extends Entity> List<T> getEntitiesByClass(Class<T> entityClass, Box box, Predicate<? super T> predicate) {
        return world.getEntitiesByClass(entityClass, box, predicate);
    }

    public List<? extends PlayerEntity> getPlayers() {
        return world.getPlayers();
    }

    public List<Entity> getOtherEntities(@Nullable Entity except, Box box) {
        return world.getOtherEntities(except, box);
    }

    public <T extends Entity> List<T> getNonSpectatingEntities(Class<T> entityClass, Box box) {
        return world.getNonSpectatingEntities(entityClass, box);
    }

    @Nullable
    public PlayerEntity getClosestPlayer(double x, double y, double z, double maxDistance, @Nullable Predicate<Entity> targetPredicate) {
        return world.getClosestPlayer(x, y, z, maxDistance, targetPredicate);
    }

    @Nullable
    public PlayerEntity getClosestPlayer(Entity entity, double maxDistance) {
        return world.getClosestPlayer(entity, maxDistance);
    }

    @Nullable
    public PlayerEntity getClosestPlayer(double x, double y, double z, double maxDistance, boolean ignoreCreative) {
        return world.getClosestPlayer(x, y, z, maxDistance, ignoreCreative);
    }

    public boolean isPlayerInRange(double x, double y, double z, double range) {
        return world.isPlayerInRange(x, y, z, range);
    }

    @Nullable
    public PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, LivingEntity entity) {
        return world.getClosestPlayer(targetPredicate, entity);
    }

    @Nullable
    public PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, LivingEntity entity, double x, double y, double z) {
        return world.getClosestPlayer(targetPredicate, entity, x, y, z);
    }

    @Nullable
    public PlayerEntity getClosestPlayer(TargetPredicate targetPredicate, double x, double y, double z) {
        return world.getClosestPlayer(targetPredicate, x, y, z);
    }

    @Nullable
    public <T extends LivingEntity> T getClosestEntity(Class<? extends T> entityClass, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z, Box box) {
        return world.getClosestEntity(entityClass, targetPredicate, entity, x, y, z, box);
    }

    @Nullable
    public <T extends LivingEntity> T getClosestEntity(List<? extends T> entityList, TargetPredicate targetPredicate, @Nullable LivingEntity entity, double x, double y, double z) {
        return world.getClosestEntity(entityList, targetPredicate, entity, x, y, z);
    }

    public List<PlayerEntity> getPlayers(TargetPredicate targetPredicate, LivingEntity entity, Box box) {
        return world.getPlayers(targetPredicate, entity, box);
    }

    public <T extends LivingEntity> List<T> getTargets(Class<T> entityClass, TargetPredicate targetPredicate, LivingEntity targetingEntity, Box box) {
        return world.getTargets(entityClass, targetPredicate, targetingEntity, box);
    }

    @Nullable
    public PlayerEntity getPlayerByUuid(UUID uuid) {
        return world.getPlayerByUuid(uuid);
    }

    public Biome getBiome(BlockPos pos) {
        return world.getBiome(pos);
    }

    public Stream<BlockState> getStatesInBoxIfLoaded(Box box) {
        return world.getStatesInBoxIfLoaded(box);
    }

    public int getColor(BlockPos pos, ColorResolver colorResolver) {
        return world.getColor(pos, colorResolver);
    }

    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        return world.getBiomeForNoiseGen(biomeX, biomeY, biomeZ);
    }

    public Biome getGeneratorStoredBiome(int biomeX, int biomeY, int biomeZ) {
        return world.getGeneratorStoredBiome(biomeX, biomeY, biomeZ);
    }

    public int getBottomY() {
        return world.getBottomY();
    }

    public int getHeight() {
        return world.getHeight();
    }

    public boolean isAir(BlockPos pos) {
        return world.isAir(pos);
    }

    public boolean isSkyVisibleAllowingSea(BlockPos pos) {
        return world.isSkyVisibleAllowingSea(pos);
    }

    @Deprecated
    public float getBrightness(BlockPos pos) {
        return world.getBrightness(pos);
    }

    public int getStrongRedstonePower(BlockPos pos, Direction direction) {
        return world.getStrongRedstonePower(pos, direction);
    }

    public Chunk getChunk(BlockPos pos) {
        return world.getChunk(pos);
    }

    public Chunk getChunk(int chunkX, int chunkZ, ChunkStatus status) {
        return world.getChunk(chunkX, chunkZ, status);
    }

    public boolean isWater(BlockPos pos) {
        return world.isWater(pos);
    }

    public boolean containsFluid(Box box) {
        return world.containsFluid(box);
    }

    public int getLightLevel(BlockPos pos) {
        return world.getLightLevel(pos);
    }

    public int getLightLevel(BlockPos pos, int ambientDarkness) {
        return world.getLightLevel(pos, ambientDarkness);
    }

    @Deprecated
    public boolean isPosLoaded(int x, int z) {
        return world.isPosLoaded(x, z);
    }

    @Deprecated
    public boolean isChunkLoaded(BlockPos pos) {
        return world.isChunkLoaded(pos);
    }

    @Deprecated
    public boolean isRegionLoaded(BlockPos min, BlockPos max) {
        return world.isRegionLoaded(min, max);
    }

    @Deprecated
    public boolean isRegionLoaded(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return world.isRegionLoaded(minX, minY, minZ, maxX, maxY, maxZ);
    }

    @Deprecated
    public boolean isRegionLoaded(int minX, int minZ, int maxX, int maxZ) {
        return world.isRegionLoaded(minX, minZ, maxX, maxZ);
    }

    public float getBrightness(Direction direction, boolean shaded) {
        return world.getBrightness(direction, shaded);
    }

    public int getLightLevel(LightType type, BlockPos pos) {
        return world.getLightLevel(type, pos);
    }

    public int getBaseLightLevel(BlockPos pos, int ambientDarkness) {
        return world.getBaseLightLevel(pos, ambientDarkness);
    }

    public boolean isSkyVisible(BlockPos pos) {
        return world.isSkyVisible(pos);
    }

    public int getLuminance(BlockPos pos) {
        return world.getLuminance(pos);
    }

    public int getMaxLightLevel() {
        return world.getMaxLightLevel();
    }

    public Stream<BlockState> getStatesInBox(Box box) {
        return world.getStatesInBox(box);
    }

    public BlockHitResult raycast(BlockStateRaycastContext context) {
        return world.raycast(context);
    }

    public BlockHitResult raycast(RaycastContext context) {
        return world.raycast(context);
    }

    @Nullable
    public BlockHitResult raycastBlock(Vec3d start, Vec3d end, BlockPos pos, VoxelShape shape, BlockState state) {
        return world.raycastBlock(start, end, pos, shape, state);
    }

    public double getDismountHeight(VoxelShape blockCollisionShape, Supplier<VoxelShape> belowBlockCollisionShapeGetter) {
        return world.getDismountHeight(blockCollisionShape, belowBlockCollisionShapeGetter);
    }

    public double getDismountHeight(BlockPos pos) {
        return world.getDismountHeight(pos);
    }

    public int getTopY() {
        return world.getTopY();
    }

    public int countVerticalSections() {
        return world.countVerticalSections();
    }

    public int getBottomSectionCoord() {
        return world.getBottomSectionCoord();
    }

    public int getTopSectionCoord() {
        return world.getTopSectionCoord();
    }

    public boolean isOutOfHeightLimit(BlockPos pos) {
        return world.isOutOfHeightLimit(pos);
    }

    public boolean isOutOfHeightLimit(int y) {
        return world.isOutOfHeightLimit(y);
    }

    public int getSectionIndex(int y) {
        return world.getSectionIndex(y);
    }

    public int sectionCoordToIndex(int coord) {
        return world.sectionCoordToIndex(coord);
    }

    public int sectionIndexToCoord(int index) {
        return world.sectionIndexToCoord(index);
    }

    public boolean canPlace(BlockState state, BlockPos pos, ShapeContext context) {
        return world.canPlace(state, pos, context);
    }

    public boolean intersectsEntities(Entity entity) {
        return world.intersectsEntities(entity);
    }

    public boolean isSpaceEmpty(Box box) {
        return world.isSpaceEmpty(box);
    }

    public boolean isSpaceEmpty(Entity entity) {
        return world.isSpaceEmpty(entity);
    }

    public boolean isSpaceEmpty(Entity entity, Box box) {
        return world.isSpaceEmpty(entity, box);
    }

    public boolean isSpaceEmpty(@Nullable Entity entity, Box box, Predicate<Entity> filter) {
        return world.isSpaceEmpty(entity, box, filter);
    }

    public Stream<VoxelShape> getCollisions(@Nullable Entity entity, Box box, Predicate<Entity> predicate) {
        return world.getCollisions(entity, box, predicate);
    }

    public Stream<VoxelShape> getBlockCollisions(@Nullable Entity entity, Box box) {
        return world.getBlockCollisions(entity, box);
    }

    public boolean hasBlockCollision(@Nullable Entity entity, Box box, BiPredicate<BlockState, BlockPos> predicate) {
        return world.hasBlockCollision(entity, box, predicate);
    }

    public Stream<VoxelShape> getBlockCollisions(@Nullable Entity entity, Box box, BiPredicate<BlockState, BlockPos> predicate) {
        return world.getBlockCollisions(entity, box, predicate);
    }

    public Optional<Vec3d> findClosestCollision(@Nullable Entity entity, VoxelShape shape, Vec3d target, double x, double y, double z) {
        return world.findClosestCollision(entity, shape, target, x, y, z);
    }

    public Biome getBiomeForNoiseGen(ChunkPos chunkPos) {
        return world.getBiomeForNoiseGen(chunkPos);
    }

    public boolean breakBlock(BlockPos pos, boolean drop) {
        return world.breakBlock(pos, drop);
    }

    public boolean breakBlock(BlockPos pos, boolean drop, @Nullable Entity breakingEntity) {
        return world.breakBlock(pos, drop, breakingEntity);
    }

    public boolean spawnEntity(Entity entity) {
        return world.spawnEntity(entity);
    }

    public float getMoonSize() {
        return world.getMoonSize();
    }

    public float getSkyAngle(float tickDelta) {
        return world.getSkyAngle(tickDelta);
    }

    public int getMoonPhase() {
        return world.getMoonPhase();
    }

    private final net.minecraft.world.World world;
    public World(net.minecraft.world.World world) {
        this.world = world;
    }


}
