package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesLeftTurn extends StructureStrongholdPiecesStronghold {
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
                4,
                4,
                true,
                random,
                StructureStrongholdPieces.access$200());
        this.placeDoor(world, random, structureBoundingBox, this.bNb, 1, 1, 0);
        if (this.coordBaseMode != Facing.NORTH && this.coordBaseMode != Facing.EAST) {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    4,
                    1,
                    1,
                    4,
                    3,
                    3,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
            if (i != 0) {
                return true;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                1,
                0,
                3,
                3,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        return true;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        int i = StructureComponent.f();
        if (this.coordBaseMode != Facing.NORTH && this.coordBaseMode != Facing.EAST) {
            this.getNextComponentZ(
                    (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
            if (i == 0) {
                return;
            }
        }

        this.getNextComponentX(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
    }

    public StructureStrongholdPiecesLeftTurn() {}

    public static StructureStrongholdPiecesLeftTurn createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -1, 0, 5, 5, 5, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesLeftTurn(l, random, structureBoundingBox, facing)
                : null;
    }

    public StructureStrongholdPiecesLeftTurn(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
    }
}
