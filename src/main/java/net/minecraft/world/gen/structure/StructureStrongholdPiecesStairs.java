package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockStoneSlabEnumType;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesStairs extends StructureStrongholdPiecesStronghold {

    private boolean f;

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        if (this.f) {
            StructureStrongholdPieces.access$102(StructureStrongholdPiecesCrossing.class);
        }

        this.getNextComponentNormal(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
    }

    public StructureStrongholdPiecesStairs(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.f = false;
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
    }

    @Override
    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Source", this.f);
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
                4,
                10,
                4,
                true,
                random,
                StructureStrongholdPieces.access$200());
        this.placeDoor(world, random, structureBoundingBox, this.bNb, 1, 7, 0);
        this.placeDoor(
                world,
                random,
                structureBoundingBox,
                StructureStrongholdPiecesStrongholdDoor.OPENING,
                1,
                1,
                4);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 2, 6, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 1, 5, 1, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(BlockStoneSlabEnumType.STONE.getMetadata()),
                1,
                6,
                1,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 1, 5, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 1, 4, 3, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(BlockStoneSlabEnumType.STONE.getMetadata()),
                1,
                5,
                3,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 2, 4, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 3, 3, 3, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(BlockStoneSlabEnumType.STONE.getMetadata()),
                3,
                4,
                3,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 3, 3, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 3, 2, 1, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(BlockStoneSlabEnumType.STONE.getMetadata()),
                3,
                3,
                1,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 2, 2, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 1, 1, 1, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(BlockStoneSlabEnumType.STONE.getMetadata()),
                1,
                2,
                1,
                structureBoundingBox);
        this.setBlockState(
                world, Blocks.stonebrick.getDefaultState(), 1, 1, 2, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(BlockStoneSlabEnumType.STONE.getMetadata()),
                1,
                1,
                3,
                structureBoundingBox);
        return true;
    }

    public StructureStrongholdPiecesStairs() {}


    public StructureStrongholdPiecesStairs(int i, Random random, int j, int k) {
        this(i, random, j, k, StructureComponent.cR());
    }

    private StructureStrongholdPiecesStairs(
            int i, Random random, int j, int k, int initialFlowToken) {
        super(i);

        int i1 = initialFlowToken;

        int l = i1;
        this.f = true;
        this.coordBaseMode = FacingPlane.HORIZONTAL.random(random);
        this.bNb = StructureStrongholdPiecesStrongholdDoor.OPENING;
        switch (this.coordBaseMode) {
            case NORTH:
            case SOUTH:
                this.boundingBox = new StructureBoundingBox(j, 64, k, j + 5 - 1, 74, k + 5 - 1);
                if (l != 0) {
                    break;
                }
            default:
                this.boundingBox = new StructureBoundingBox(j, 64, k, j + 5 - 1, 74, k + 5 - 1);
        }
    }

    @Override
    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.f = compoundTag.getBoolean("Source");
    }

    public static StructureStrongholdPiecesStairs createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -7, 0, 5, 11, 5, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesStairs(l, random, structureBoundingBox, facing)
                : null;
    }
}
