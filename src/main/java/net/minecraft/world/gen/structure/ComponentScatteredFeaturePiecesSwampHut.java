package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockFlowerPotEnumFlowerType;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.ComponentScatteredFeaturePiecesFeature;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import java.util.Random;

public class ComponentScatteredFeaturePiecesSwampHut extends ComponentScatteredFeaturePiecesFeature {

    private boolean hasWitch;

    @Override
    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Witch", this.hasWitch);
    }

    @Override
    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.hasWitch = compoundTag.getBoolean("Witch");
    }

    public ComponentScatteredFeaturePiecesSwampHut(Random random, int i, int j) {
        super(random, i, 64, j, 7, 5, 9);
    }

    public ComponentScatteredFeaturePiecesSwampHut() {}


    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (!this.method_0e94e718e78ead49f0b8(world, structureBoundingBox, 0)) {
            return false;
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                1,
                5,
                1,
                7,
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                4,
                2,
                5,
                4,
                7,
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                1,
                0,
                4,
                1,
                0,
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                2,
                2,
                3,
                3,
                2,
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                3,
                1,
                3,
                6,
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                2,
                3,
                5,
                3,
                6,
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                2,
                7,
                4,
                3,
                7,
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                Blocks.planks.getStateFromMeta(BlockPlanksEnumType.SPRUCE.getMetadata()),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                2,
                1,
                3,
                2,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                0,
                2,
                5,
                3,
                2,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                7,
                1,
                3,
                7,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                0,
                7,
                5,
                3,
                7,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 2, 3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 3, 3, 7, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 1, 3, 4, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 5, 3, 4, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 5, 3, 5, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.flower_pot
                        .getDefaultState()
                        .withProperty(
                                BlockFlowerPot.CONTENTS, BlockFlowerPotEnumFlowerType.MUSHROOM_RED),
                1,
                3,
                5,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.crafting_table.getDefaultState(), 3, 2, 6, structureBoundingBox);
        this.setBlockState(world, Blocks.cauldron.getDefaultState(), 4, 2, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 1, 2, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 5, 2, 1, structureBoundingBox);
        int j = this.getMetadataWithOffset(Blocks.oak_stairs, 3);
        int k = this.getMetadataWithOffset(Blocks.oak_stairs, 1);
        int l = this.getMetadataWithOffset(Blocks.oak_stairs, 0);
        int metadataWithOffset = this.getMetadataWithOffset(Blocks.oak_stairs, 2);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                4,
                1,
                6,
                4,
                1,
                Blocks.spruce_stairs.getStateFromMeta(j),
                Blocks.spruce_stairs.getStateFromMeta(j),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                4,
                2,
                0,
                4,
                7,
                Blocks.spruce_stairs.getStateFromMeta(l),
                Blocks.spruce_stairs.getStateFromMeta(l),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                4,
                2,
                6,
                4,
                7,
                Blocks.spruce_stairs.getStateFromMeta(k),
                Blocks.spruce_stairs.getStateFromMeta(k),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                4,
                8,
                6,
                4,
                8,
                Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset),
                Blocks.spruce_stairs.getStateFromMeta(metadataWithOffset),
                false);
        int j1 = 2;

        label31:
        while (j1 <= 7) {
            byte b0 = 1;

            while (true) {
                if (b0 <= 5) {
                    this.replaceAirAndLiquidDownwards(
                            world, Blocks.log.getDefaultState(), b0, -1, j1, structureBoundingBox);
                    b0 += 4;
                    if (i == 0) {
                        continue;
                    }
                }

                j1 += 5;
                if (i != 0) {
                    break label31;
                }
                break;
            }
        }

        if (!this.hasWitch) {
            j1 = this.getXWithOffset(2, 5);
            int l1 = this.getYWithOffset(2);
            int k1 = this.getZWithOffset(2, 5);
            if (structureBoundingBox.isVecInside(new BlockPos(j1, l1, k1))) {
                this.hasWitch = true;
                EntityWitch entityWitch = new EntityWitch(world);
                entityWitch.setLocationAndAngles(j1 + 0.5, l1, k1 + 0.5, 0.0F, 0.0F);
                entityWitch.onInitialSpawn(
                        world.getDifficultyForLocation(new BlockPos(j1, l1, k1)),
                        (IEntityLivingData) null);
                world.spawnEntityInWorld(entityWitch);
            }
        }

        return true;
    }
}
