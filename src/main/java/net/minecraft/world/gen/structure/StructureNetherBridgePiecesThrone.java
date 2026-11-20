package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesThrone extends StructureNetherBridgePiece {
    private boolean hasSpawner;

    public StructureNetherBridgePiecesThrone(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                6,
                7,
                7,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                0,
                5,
                1,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                1,
                5,
                2,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                2,
                5,
                3,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                4,
                3,
                5,
                4,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int l = StructureComponent.f();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                0,
                1,
                4,
                2,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                2,
                0,
                5,
                4,
                2,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                5,
                2,
                1,
                5,
                3,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int i = l;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                5,
                2,
                5,
                5,
                3,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                3,
                0,
                5,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                5,
                3,
                6,
                5,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                5,
                8,
                5,
                5,
                8,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.nether_brick_fence.getDefaultState(), 1, 6, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.nether_brick_fence.getDefaultState(), 5, 6, 3, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                6,
                3,
                0,
                6,
                8,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                6,
                3,
                6,
                6,
                8,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                6,
                8,
                5,
                7,
                8,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                2,
                8,
                8,
                4,
                8,
                8,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        if (!this.hasSpawner) {
            BlockPos blockPos =
                    new BlockPos(
                            this.getXWithOffset(3, 5),
                            this.getYWithOffset(5),
                            this.getZWithOffset(3, 5));
            if (structureBoundingBox.isVecInside(blockPos)) {
                this.hasSpawner = true;
                world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), 2);
                TileEntity tileEntity = world.getTileEntity(blockPos);
                if (tileEntity instanceof TileEntityMobSpawner) {
                    ((TileEntityMobSpawner) tileEntity)
                            .getSpawnerBaseLogic()
                            .setEntityName("Blaze");
                }
            }
        }

        int j = 0;

        while (j <= 6) {
            int k = 0;

            while (true) {
                if (k <= 6) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            k,
                            structureBoundingBox);
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    public StructureNetherBridgePiecesThrone() {}

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.hasSpawner = compoundTag.getBoolean("Mob");
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Mob", this.hasSpawner);
    }


    public static StructureNetherBridgePiecesThrone method_731cbdded6c8783c2709(
            List list, Random random, int i, int j, int k, int l, Facing facing) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -2, 0, 0, 7, 8, 9, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesThrone(l, random, structureBoundingBox, facing)
                : null;
    }
}
