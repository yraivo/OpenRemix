package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingSpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenBaseSpawnListEntry;
import net.minecraft.world.chunk.Chunk;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Random;
import java.util.Set;

public final class SpawnerAnimals {
    private static final int MOB_COUNT_DIV = (int) Math.pow(17.0, 2.0);
    private final Set eligibleChunksForSpawning = Sets.newHashSet();

    public static void performWorldGenSpawning(
            World world, BiomeGenBase biomeGenBase, int i, int j, int k, int l, Random random) {
        World.getLegacyWorldFlowMarker();

        List spawnableList = biomeGenBase.getSpawnableList(EnumCreatureType.CREATURE);

        if (!spawnableList.isEmpty()) {
            while (random.nextFloat() < biomeGenBase.getSpawningChance()) {
                BiomeGenBaseSpawnListEntry randomItem =
                        (BiomeGenBaseSpawnListEntry)
                                WeightedRandom.getRandomItem(world.rand, spawnableList);
                int i1 =
                        randomItem.minGroupCount
                                + random.nextInt(
                                        1 + randomItem.maxGroupCount - randomItem.minGroupCount);
                IEntityLivingData iEntityLivingData = null;
                int j1 = i + random.nextInt(k);
                int k1 = j + random.nextInt(l);
                int l1 = j1;
                int i2 = k1;
                int j2 = 0;

                while (true) {
                    if (j2 < i1) {
                        byte b0 = 0;
                        int k2 = 0;

                        while (b0 == 0 && k2 < 4) {
                            label71:
                            {
                                BlockPos blockPos =
                                        world.getTopSolidOrLiquidBlock(new BlockPos(j1, 0, k1));
                                if (canCreatureTypeSpawnAtLocation(
                                        EntityLivingSpawnPlacementType.ON_GROUND,
                                        world,
                                        blockPos)) {
                                    EntityLiving entityLiving;
                                    try {
                                        entityLiving =
                                                (EntityLiving)
                                                        randomItem
                                                                .entityClass
                                                                .getConstructor(World.class)
                                                                .newInstance(world);
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                        break label71;
                                    }

                                    entityLiving.setLocationAndAngles(
                                            j1 + 0.5F,
                                            blockPos.getY(),
                                            k1 + 0.5F,
                                            random.nextFloat() * 360.0F,
                                            0.0F);
                                    world.spawnEntityInWorld(entityLiving);
                                    iEntityLivingData =
                                            entityLiving.onInitialSpawn(
                                                    world.getDifficultyForLocation(
                                                            new BlockPos(entityLiving)),
                                                    iEntityLivingData);
                                    b0 = 1;
                                }

                                j1 += random.nextInt(5) - random.nextInt(5);
                                k1 += random.nextInt(5) - random.nextInt(5);

                                while (j1 < i || j1 >= i + k || k1 < j || k1 >= j + k) {
                                    j1 = l1 + random.nextInt(5) - random.nextInt(5);
                                    k1 = i2 + random.nextInt(5) - random.nextInt(5);
                                }
                            }

                            k2++;
                        }

                        j2++;

                        continue;
                    }

                    break;
                }
            }
        }
    }

    protected static BlockPos getRandomChunkPosition(World world, int i, int j) {
        Chunk chunkFromChunkCoords = world.getChunkFromChunkCoords(i, j);
        int k = i * 16 + world.rand.nextInt(16);
        int l = j * 16 + world.rand.nextInt(16);
        int i1 =
                MathHelper.roundUpToMultiple(
                        chunkFromChunkCoords.getHeight(new BlockPos(k, 0, l)) + 1, 16);
        int j1 =
                world.rand.nextInt(
                        i1 > 0 ? i1 : chunkFromChunkCoords.getTopFilledSegment() + 16 - 1);
        return new BlockPos(k, j1, l);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public int findChunksForSpawning(
            WorldServer worldServer, boolean flag, boolean flag1, boolean flag2) {
        int[] aint = World.getLegacyWorldFlowMarker();
        if (!flag && !flag1) {
            return 0;
        }

        this.eligibleChunksForSpawning.clear();
        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_5466 =
                        (worldServer.playerEntities).iterator();
                (__recoveryIterator171_5466).hasNext(); ) {
            PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_5466).next();
            if (!playerEntity.isSpectator()) {
                int l = MathHelper.floor(playerEntity.posX / 16.0);
                int j = MathHelper.floor(playerEntity.posZ / 16.0);
                byte b0 = 8;
                int i1 = -b0;

                while (i1 <= b0) {
                    int k = -b0;

                    while (k <= b0) {
                        int j1 = i1 != -b0 && i1 != b0 && k != -b0 && k != b0 ? 0 : 1;
                        ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(i1 + l, k + j);
                        if (!this.eligibleChunksForSpawning.contains(chunkCoordIntPair)) {
                            i++;
                            if (j1 == 0
                                    && worldServer.getWorldBorder().contains(chunkCoordIntPair)) {
                                this.eligibleChunksForSpawning.add(chunkCoordIntPair);
                            }
                        }

                        k++;
                    }

                    i1++;
                }
            }
        }

        int i4 = 0;
        BlockPos blockPos = worldServer.getSpawnPoint();

        for (EnumCreatureType enumCreatureType : EnumCreatureType.values()) {
            if ((!enumCreatureType.method_d87039b07bd6961907f2() || flag1)
                    && (enumCreatureType.method_d87039b07bd6961907f2() || flag)
                    && (!enumCreatureType.d() || flag2)) {
                int l3 =
                        worldServer.countEntities(
                                enumCreatureType.getCreatureClass());
                int j4 = enumCreatureType.getMaxNumberOfCreature() * i / net.minecraft.world.SpawnerAnimals.MOB_COUNT_DIV;
                if (l3 <= j4) {
                    label134:
                    for (java.util.Iterator<?> __recoveryIterator171_7547 = (this.eligibleChunksForSpawning).iterator();
                            (__recoveryIterator171_7547).hasNext(); ) {
                        ChunkCoordIntPair chunkCoordIntPairValue =
                                (ChunkCoordIntPair) (__recoveryIterator171_7547).next();
                        BlockPos blockPosValue =
                                getRandomChunkPosition(
                                        worldServer,
                                        chunkCoordIntPairValue.chunkXPos,
                                        chunkCoordIntPairValue.chunkZPos);
                        int k1 = blockPosValue.getX();
                        int l1 = blockPosValue.getY();
                        int z = blockPosValue.getZ();
                        Block block = worldServer.getBlockState(blockPosValue).getBlock();
                        if (!block.isNormalCube()) {
                            int j2 = 0;
                            int k2 = 0;

                            while (k2 < 3) {
                                int l2 = k1;
                                int i3 = l1;
                                int j3 = z;
                                byte b1 = 6;
                                BiomeGenBaseSpawnListEntry biomeGenBaseSpawnListEntry = null;
                                IEntityLivingData iEntityLivingData = null;
                                int k3 = 0;

                                while (k3 < 4) {
                                    l2 +=
                                            worldServer.rand.nextInt(b1)
                                                    - worldServer.rand.nextInt(b1);
                                    i3 += worldServer.rand.nextInt(1) - worldServer.rand.nextInt(1);
                                    j3 +=
                                            worldServer.rand.nextInt(b1)
                                                    - worldServer.rand.nextInt(b1);
                                    BlockPos blockPosValue2 = new BlockPos(l2, i3, j3);
                                    float f = l2 + 0.5F;
                                    float f1 = j3 + 0.5F;
                                    if (!worldServer.isAnyPlayerWithinRangeAt(f, i3, f1, 24.0)
                                            && blockPos.distanceSq(f, i3, f1) >= 576.0) {
                                        if (biomeGenBaseSpawnListEntry == null) {
                                            biomeGenBaseSpawnListEntry =
                                                    worldServer.getSpawnListEntryForTypeAt(
                                                            enumCreatureType, blockPosValue2);
                                            if (biomeGenBaseSpawnListEntry == null
                                                    && aint != null) {
                                                break;
                                            }
                                        }

                                        if (worldServer.canCreatureTypeSpawnHere(
                                                        enumCreatureType,
                                                        biomeGenBaseSpawnListEntry,
                                                        blockPosValue2)
                                                && canCreatureTypeSpawnAtLocation(
                                                        EntitySpawnPlacementRegistry
                                                                .getPlacementForEntity(
                                                                        biomeGenBaseSpawnListEntry
                                                                                .entityClass),
                                                        worldServer,
                                                        blockPosValue2)) {
                                            EntityLiving entityLiving;
                                            try {
                                                entityLiving =
                                                        (EntityLiving)
                                                                biomeGenBaseSpawnListEntry
                                                                        .entityClass
                                                                        .getConstructor(World.class)
                                                                        .newInstance(worldServer);
                                            } catch (Exception exception) {
                                                exception.printStackTrace();
                                                return i4;
                                            }

                                            entityLiving.setLocationAndAngles(
                                                    f,
                                                    i3,
                                                    f1,
                                                    worldServer.rand.nextFloat() * 360.0F,
                                                    0.0F);
                                            if (entityLiving.getCanSpawnHere()
                                                    && entityLiving.handleLavaMovement()) {
                                                iEntityLivingData =
                                                        entityLiving.onInitialSpawn(
                                                                worldServer
                                                                        .getDifficultyForLocation(
                                                                                new BlockPos(
                                                                                        entityLiving)),
                                                                iEntityLivingData);
                                                if (entityLiving.handleLavaMovement()) {
                                                    j2++;
                                                    worldServer.spawnEntityInWorld(entityLiving);
                                                }

                                                if (j2 >= entityLiving.L() && aint != null) {
                                                    continue label134;
                                                }
                                            }

                                            i4 += j2;
                                        }
                                    }

                                    k3++;
                                }

                                k2++;
                            }
                        }
                    }
                }
            }
        }

        return i4;
    }

    public static boolean canCreatureTypeSpawnAtLocation(
            EntityLivingSpawnPlacementType entityLivingSpawnPlacementType,
            World world,
            BlockPos blockPos) {
        if (!world.getWorldBorder().contains(blockPos)) {
            return false;
        }

        Block block = world.getBlockState(blockPos).getBlock();
        if (entityLivingSpawnPlacementType == EntityLivingSpawnPlacementType.IN_WATER) {
            return block.getMaterial().isLiquid()
                    && world.getBlockState(blockPos.offsetDown())
                            .getBlock()
                            .getMaterial()
                            .isLiquid()
                    && !world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
        }

        BlockPos blockPosValue = blockPos.offsetDown();
        if (!World.doesBlockHaveSolidTopSurface(world, blockPosValue)) {
            return false;
        }

        Block blockValue = world.getBlockState(blockPosValue).getBlock();
        int i = blockValue != Blocks.bedrock && blockValue != Blocks.barrier ? 1 : 0;
        return i != 0
                && !block.isNormalCube()
                && !block.getMaterial().isLiquid()
                && !world.getBlockState(blockPos.offsetUp()).getBlock().isNormalCube();
    }
}
