package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePiecesVillage;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesHouse4Garden extends StructureVillagePiecesVillage {

    private boolean isRoofAccessible;

    public StructureVillagePiecesHouse4Garden(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(structureVillagePiecesStart, i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
        this.isRoofAccessible = random.nextBoolean();
    }

    public static StructureVillagePiecesHouse4Garden createPiece(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, 0, 0, 0, 5, 6, 5, facing);
        return StructureComponent.findIntersecting(list, structureBoundingBox) != null
                ? null
                : new StructureVillagePiecesHouse4Garden(
                        structureVillagePiecesStart, l, random, structureBoundingBox, facing);
    }


    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Terrace", this.isRoofAccessible);
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.isRoofAccessible = compoundTag.getBoolean("Terrace");
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.ef < 0) {
            this.ef = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.ef < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 6 - 1, 0);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                4,
                0,
                4,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                4,
                0,
                4,
                4,
                4,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                4,
                1,
                3,
                4,
                3,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 0, 1, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 0, 2, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 0, 3, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 4, 1, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 4, 2, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 4, 3, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 0, 1, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 0, 2, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 0, 3, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 4, 1, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 4, 2, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 4, 3, 4, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                1,
                0,
                3,
                3,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                1,
                4,
                3,
                3,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                4,
                3,
                3,
                4,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 2, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 2, 2, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 1, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 2, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 3, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 2, 3, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 3, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 2, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 1, 0, structureBoundingBox);
        if (this.getBlockStateFromPos(world, 2, 0, -1, structureBoundingBox)
                                .getBlock()
                                .getMaterial()
                        == Material.AIR
                && this.getBlockStateFromPos(world, 2, -1, -1, structureBoundingBox)
                                .getBlock()
                                .getMaterial()
                        != Material.AIR) {
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(
                            this.getMetadataWithOffset(Blocks.stone_stairs, 3)),
                    2,
                    0,
                    -1,
                    structureBoundingBox);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                1,
                3,
                3,
                3,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        if (this.isRoofAccessible) {
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 0, 5, 0, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 1, 5, 0, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 2, 5, 0, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 3, 5, 0, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 4, 5, 0, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 0, 5, 4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 1, 5, 4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 2, 5, 4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 3, 5, 4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 4, 5, 4, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 4, 5, 1, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 4, 5, 2, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 4, 5, 3, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 0, 5, 1, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 0, 5, 2, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), 0, 5, 3, structureBoundingBox);
        }

        if (this.isRoofAccessible) {
            int j = this.getMetadataWithOffset(Blocks.ladder, 3);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(j), 3, 1, 3, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(j), 3, 2, 3, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(j), 3, 3, 3, structureBoundingBox);
            this.setBlockState(
                    world, Blocks.ladder.getStateFromMeta(j), 3, 4, 3, structureBoundingBox);
        }

        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode),
                2,
                3,
                1,
                structureBoundingBox);
        int l = 0;

        label43:
        while (l < 5) {
            int k = 0;

            while (true) {
                if (k < 5) {
                    this.clearCurrentPositionBlocksUpwards(world, k, 6, l, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            k,
                            -1,
                            l,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                l++;
                if (i == 0) {
                    break label43;
                }
                break;
            }
        }

        this.spawnVillagers(world, structureBoundingBox, 1, 1, 2, 1);
        return true;
    }

    public StructureVillagePiecesHouse4Garden() {}
}
