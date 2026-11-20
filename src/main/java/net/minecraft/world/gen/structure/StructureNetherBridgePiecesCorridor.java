package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesCorridor extends StructureNetherBridgePiece {
    private boolean f;

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Chest", this.f);
    }

    public StructureNetherBridgePiecesCorridor(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
        this.f = remix.compat.compiler.JvmBooleanSlots.fromInt((random.nextInt(3) == 0 ? 1 : 0));
    }


    public static StructureNetherBridgePiecesCorridor method_1e968fb12ff85b942a1e(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, 0, 0, 5, 7, 5, facing);
        return isAboveGround(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureNetherBridgePiecesCorridor(l, random, structureBoundingBox, facing)
                : null;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                4,
                1,
                4,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                4,
                5,
                4,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                2,
                0,
                4,
                5,
                4,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int l = StructureComponent.cR();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                3,
                1,
                4,
                4,
                1,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        int i = l;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                3,
                3,
                4,
                4,
                3,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                0,
                0,
                5,
                0,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                4,
                3,
                5,
                4,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                4,
                1,
                4,
                4,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                3,
                4,
                3,
                4,
                4,
                Blocks.nether_brick_fence.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        if (this.f
                && structureBoundingBox.isVecInside(
                        new BlockPos(
                                this.getXWithOffset(3, 3),
                                this.getYWithOffset(2),
                                this.getZWithOffset(3, 3)))) {
            this.f = false;
            this.generateChestContents(
                    world,
                    structureBoundingBox,
                    random,
                    3,
                    2,
                    3,
                    net.minecraft.world.gen.structure.StructureNetherBridgePiece
                            .netherFortressChestContents,
                    2 + random.nextInt(4));
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                6,
                0,
                4,
                6,
                4,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int j = 0;

        while (j <= 4) {
            int k = 0;

            while (true) {
                if (k <= 4) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            k,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    return true;
                }
                break;
            }
        }

        return true;
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.f = compoundTag.getBoolean("Chest");
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentX(
                (StructureNetherBridgePiecesStart) structureComponent, list, random, 0, 1, true);
    }

    public StructureNetherBridgePiecesCorridor() {}
}
