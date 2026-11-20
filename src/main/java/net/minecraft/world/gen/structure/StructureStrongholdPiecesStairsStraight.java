package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesStairsStraight extends StructureStrongholdPiecesStronghold {
    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
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
                7,
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
                7);
        int j = this.getMetadataWithOffset(Blocks.stone_stairs, 2);
        int k = 0;

        while (k < 6) {
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(j),
                    1,
                    6 - k,
                    1 + k,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(j),
                    2,
                    6 - k,
                    1 + k,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(j),
                    3,
                    6 - k,
                    1 + k,
                    structureBoundingBox);
            if (k < 5) {
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        1,
                        5 - k,
                        1 + k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        2,
                        5 - k,
                        1 + k,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.stonebrick.getDefaultState(),
                        3,
                        5 - k,
                        1 + k,
                        structureBoundingBox);
            }

            k++;
            if (i == 0) {
                break;
            }
        }

        return true;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
    }

    public StructureStrongholdPiecesStairsStraight() {}

    public static StructureStrongholdPiecesStairsStraight createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -7, 0, 5, 11, 8, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesStairsStraight(l, random, structureBoundingBox, facing)
                : null;
    }

    public StructureStrongholdPiecesStairsStraight(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
    }
}
