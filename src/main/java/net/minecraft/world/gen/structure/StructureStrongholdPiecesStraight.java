package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesStraight extends StructureStrongholdPiecesStronghold {

    private boolean expandsX;
    private boolean expandsZ;

    public StructureStrongholdPiecesStraight() {}

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
                4,
                4,
                6,
                true,
                random,
                StructureStrongholdPieces.access$200());
        this.placeDoor(world, random, structureBoundingBox, this.bNb, 1, 1, 0);
        this.placeDoor(
                world,
                random,
                structureBoundingBox,
                StructureStrongholdPiecesStrongholdDoor.OPENING,
                1,
                1,
                6);
        this.randomlyPlaceBlock(
                world, structureBoundingBox, random, 0.1F, 1, 2, 1, Blocks.torch.getDefaultState());
        this.randomlyPlaceBlock(
                world, structureBoundingBox, random, 0.1F, 3, 2, 1, Blocks.torch.getDefaultState());
        this.randomlyPlaceBlock(
                world, structureBoundingBox, random, 0.1F, 1, 2, 5, Blocks.torch.getDefaultState());
        this.randomlyPlaceBlock(
                world, structureBoundingBox, random, 0.1F, 3, 2, 5, Blocks.torch.getDefaultState());
        if (this.expandsX) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    1,
                    2,
                    0,
                    3,
                    4,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
        }

        if (this.expandsZ) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    4,
                    1,
                    2,
                    4,
                    3,
                    4,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
        }

        return true;
    }

    public static StructureStrongholdPiecesStraight createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -1, 0, 5, 5, 7, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesStraight(l, random, structureBoundingBox, facing)
                : null;
    }

    public StructureStrongholdPiecesStraight(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
        this.expandsX =
                remix.compat.compiler.JvmBooleanSlots.fromInt((random.nextInt(2) == 0 ? 1 : 0));
        this.expandsZ =
                remix.compat.compiler.JvmBooleanSlots.fromInt((random.nextInt(2) == 0 ? 1 : 0));
    }


    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
        if (this.expandsX) {
            this.getNextComponentX(
                    (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 2);
        }

        if (this.expandsZ) {
            this.getNextComponentZ(
                    (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 2);
        }
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.expandsX = compoundTag.getBoolean("Left");
        this.expandsZ = compoundTag.getBoolean("Right");
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Left", this.expandsX);
        compoundTag.setBoolean("Right", this.expandsZ);
    }
}
