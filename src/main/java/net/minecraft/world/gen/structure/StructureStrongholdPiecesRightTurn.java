package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStrongholdPieces;
import net.minecraft.world.gen.structure.StructureStrongholdPiecesLeftTurn;
import net.minecraft.world.gen.structure.StructureStrongholdPiecesStairs2;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesRightTurn extends StructureStrongholdPiecesLeftTurn {
    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
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
                    0,
                    1,
                    1,
                    0,
                    3,
                    3,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
            if (i == 0) {
                return true;
            }
        }

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
        return true;
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        int i = StructureComponent.cR();
        if (this.coordBaseMode != Facing.NORTH && this.coordBaseMode != Facing.EAST) {
            this.getNextComponentX(
                    (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
            if (i != 0) {
                return;
            }
        }

        this.getNextComponentZ(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
    }
}
