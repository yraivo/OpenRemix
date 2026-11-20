package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesCrossing extends StructureStrongholdPiecesStronghold {
    private boolean ie;

    private boolean f;

    private boolean hZ;

    private boolean sE;

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("leftLow", this.f);
        compoundTag.setBoolean("leftHigh", this.hZ);
        compoundTag.setBoolean("rightLow", this.ie);
        compoundTag.setBoolean("rightHigh", this.sE);
    }


    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        int i = 3;
        int j = 5;
        if (this.coordBaseMode == Facing.WEST || this.coordBaseMode == Facing.NORTH) {
            i = 8 - i;
            j = 8 - j;
        }

        this.getNextComponentNormal(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 5, 1);
        if (this.f) {
            this.getNextComponentX(
                    (StructureStrongholdPiecesStairs2) structureComponent, list, random, i, 1);
        }

        if (this.hZ) {
            this.getNextComponentX(
                    (StructureStrongholdPiecesStairs2) structureComponent, list, random, j, 7);
        }

        if (this.ie) {
            this.getNextComponentZ(
                    (StructureStrongholdPiecesStairs2) structureComponent, list, random, i, 1);
        }

        if (this.sE) {
            this.getNextComponentZ(
                    (StructureStrongholdPiecesStairs2) structureComponent, list, random, j, 7);
        }
    }

    public StructureStrongholdPiecesCrossing() {}

    public StructureStrongholdPiecesCrossing(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
        this.f = random.nextBoolean();
        this.hZ = random.nextBoolean();
        this.ie = random.nextBoolean();
        this.sE = remix.compat.compiler.JvmBooleanSlots.fromInt((random.nextInt(3) > 0 ? 1 : 0));
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.f = compoundTag.getBoolean("leftLow");
        this.hZ = compoundTag.getBoolean("leftHigh");
        this.ie = compoundTag.getBoolean("rightLow");
        this.sE = compoundTag.getBoolean("rightHigh");
    }

    public static StructureStrongholdPiecesCrossing createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -4, -3, 0, 10, 9, 11, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesCrossing(l, random, structureBoundingBox, facing)
                : null;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }

        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                9,
                8,
                10,
                true,
                random,
                StructureStrongholdPieces.access$200());
        this.placeDoor(world, random, structureBoundingBox, this.bNb, 4, 3, 0);
        if (this.f) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    3,
                    1,
                    0,
                    5,
                    3,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
        }

        if (this.ie) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    9,
                    3,
                    1,
                    9,
                    5,
                    3,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
        }

        if (this.hZ) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    5,
                    7,
                    0,
                    7,
                    9,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
        }

        if (this.sE) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    9,
                    5,
                    7,
                    9,
                    7,
                    9,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                1,
                10,
                7,
                3,
                10,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                1,
                2,
                1,
                8,
                2,
                6,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                5,
                4,
                4,
                9,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                8,
                1,
                5,
                8,
                4,
                9,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                1,
                4,
                7,
                3,
                4,
                9,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                5,
                3,
                3,
                6,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                3,
                4,
                3,
                3,
                4,
                Blocks.stone_slab.getDefaultState(),
                Blocks.stone_slab.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                4,
                6,
                3,
                4,
                6,
                Blocks.stone_slab.getDefaultState(),
                Blocks.stone_slab.getDefaultState(),
                false);
        this.fillWithRandomizedBlocks(
                world,
                structureBoundingBox,
                5,
                1,
                7,
                7,
                1,
                8,
                false,
                random,
                StructureStrongholdPieces.access$200());
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                1,
                9,
                7,
                1,
                9,
                Blocks.stone_slab.getDefaultState(),
                Blocks.stone_slab.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                2,
                7,
                7,
                2,
                7,
                Blocks.stone_slab.getDefaultState(),
                Blocks.stone_slab.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                5,
                7,
                4,
                5,
                9,
                Blocks.stone_slab.getDefaultState(),
                Blocks.stone_slab.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                5,
                7,
                8,
                5,
                9,
                Blocks.stone_slab.getDefaultState(),
                Blocks.stone_slab.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                5,
                7,
                7,
                5,
                9,
                Blocks.double_stone_slab.getDefaultState(),
                Blocks.double_stone_slab.getDefaultState(),
                false);
        this.setBlockState(world, Blocks.torch.getDefaultState(), 6, 5, 6, structureBoundingBox);
        return true;
    }
}
