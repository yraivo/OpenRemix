package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureNetherBridgePiecesCrossing3 extends StructureNetherBridgePiece {
    public StructureNetherBridgePiecesCrossing3(
            int p171_0, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(p171_0);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    public StructureNetherBridgePiecesCrossing3() {}

    protected StructureNetherBridgePiecesCrossing3(Random random, int i, int j) {
        this(random, i, j, StructureComponent.cR());
    }

    private StructureNetherBridgePiecesCrossing3(
            Random random, int i, int j, int initialFlowToken) {
        super(0);

        int l = initialFlowToken;

        int k = l;
        this.coordBaseMode = FacingPlane.HORIZONTAL.random(random);
        switch (this.coordBaseMode) {
            case NORTH:
            case SOUTH:
                this.boundingBox = new StructureBoundingBox(i, 64, j, i + 19 - 1, 73, j + 19 - 1);
                if (k != 0) {
                    break;
                }
            default:
                this.boundingBox = new StructureBoundingBox(i, 64, j, i + 19 - 1, 73, j + 19 - 1);
        }
    }

    public static StructureNetherBridgePiecesCrossing3 method_da3abdc77193aa845142(
            List list,
            Random random,
            int p171_2,
            int p171_3,
            int p171_4,
            Facing facing,
            int p171_6) {
        net.minecraft.world.gen.structure.StructureBoundingBox structureBoundingBox =
                net.minecraft.world.gen.structure.StructureBoundingBox.getComponentToAddBoundingBox(
                        p171_2, p171_3, p171_4, -8, -3, 0, 19, 10, 19, facing);
        return net.minecraft.world.gen.structure.StructureNetherBridgePiece.isAboveGround(
                                structureBoundingBox)
                        && net.minecraft.world.gen.structure.StructureComponent.findIntersecting(
                                        list, structureBoundingBox)
                                == null
                ? new net.minecraft.world.gen.structure.StructureNetherBridgePiecesCrossing3(
                        p171_6, random, structureBoundingBox, facing)
                : null;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                3,
                0,
                11,
                4,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int i1 = StructureComponent.cR();
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                3,
                7,
                18,
                4,
                11,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                8,
                5,
                0,
                10,
                7,
                18,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                8,
                18,
                7,
                10,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                5,
                0,
                7,
                5,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                5,
                11,
                7,
                5,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                11,
                5,
                0,
                11,
                5,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                11,
                5,
                11,
                11,
                5,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int i = i1;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                7,
                7,
                5,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                11,
                5,
                7,
                18,
                5,
                7,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                11,
                7,
                5,
                11,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                11,
                5,
                11,
                18,
                5,
                11,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                2,
                0,
                11,
                2,
                5,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                2,
                13,
                11,
                2,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                0,
                0,
                11,
                1,
                3,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                0,
                15,
                11,
                1,
                18,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        int j = 7;

        label40:
        while (j <= 11) {
            int k = 0;

            while (true) {
                if (k <= 2) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            k,
                            structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            18 - k,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    break label40;
                }
                break;
            }
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                2,
                7,
                5,
                2,
                11,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                13,
                2,
                7,
                18,
                2,
                11,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                7,
                3,
                1,
                11,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                15,
                0,
                7,
                18,
                1,
                11,
                Blocks.nether_brick.getDefaultState(),
                Blocks.nether_brick.getDefaultState(),
                false);
        j = 0;

        while (j <= 2) {
            int l = 7;

            while (true) {
                if (l <= 11) {
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            j,
                            -1,
                            l,
                            structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.nether_brick.getDefaultState(),
                            18 - j,
                            -1,
                            l,
                            structureBoundingBox);
                    l++;
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

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (net.minecraft.world.gen.structure.StructureNetherBridgePiecesStart)
                        structureComponent,
                list,
                random,
                8,
                3,
                false);
        this.getNextComponentX(
                (net.minecraft.world.gen.structure.StructureNetherBridgePiecesStart)
                        structureComponent,
                list,
                random,
                3,
                8,
                false);
        this.getNextComponentZ(
                (net.minecraft.world.gen.structure.StructureNetherBridgePiecesStart)
                        structureComponent,
                list,
                random,
                3,
                8,
                false);
    }
}
