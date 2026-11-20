package net.minecraft.world.gen.structure;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePiecesVillage;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesField1 extends StructureVillagePiecesVillage {
    private Block cropTypeA;

    private Block cropTypeD;
    private Block cropTypeC;

    private Block cropTypeB;

    private Block method_903870cf4c7ca1fd93c4(Random random) {
        switch (random.nextInt(5)) {
            case 0:
                return Blocks.carrots;
            case 1:
                return Blocks.potatoes;
            default:
                return Blocks.wheat;
        }
    }

    public StructureVillagePiecesField1() {}

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.cropTypeA = Block.getBlockById(compoundTag.getInteger("CA"));
        this.cropTypeB = Block.getBlockById(compoundTag.getInteger("CB"));
        this.cropTypeC = Block.getBlockById(compoundTag.getInteger("CC"));
        this.cropTypeD = Block.getBlockById(compoundTag.getInteger("CD"));
    }

    public static StructureVillagePiecesField1 createPiece(
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
                        i, j, k, 0, 0, 0, 13, 4, 9, facing);
        return canVillageGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureVillagePiecesField1(
                        structureVillagePiecesStart, l, random, structureBoundingBox, facing)
                : null;
    }


    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.ef < 0) {
            this.ef = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.ef < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 4 - 1, 0);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                0,
                12,
                4,
                8,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                1,
                2,
                0,
                7,
                Blocks.farmland.getDefaultState(),
                Blocks.farmland.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                0,
                1,
                5,
                0,
                7,
                Blocks.farmland.getDefaultState(),
                Blocks.farmland.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                0,
                1,
                8,
                0,
                7,
                Blocks.farmland.getDefaultState(),
                Blocks.farmland.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                10,
                0,
                1,
                11,
                0,
                7,
                Blocks.farmland.getDefaultState(),
                Blocks.farmland.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                0,
                0,
                8,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                0,
                0,
                6,
                0,
                8,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                12,
                0,
                0,
                12,
                0,
                8,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                0,
                11,
                0,
                0,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                0,
                8,
                11,
                0,
                8,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                0,
                1,
                3,
                0,
                7,
                Blocks.water.getDefaultState(),
                Blocks.water.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                0,
                1,
                9,
                0,
                7,
                Blocks.water.getDefaultState(),
                Blocks.water.getDefaultState(),
                false);
        int j = 1;

        while (j <= 7) {
            this.setBlockState(
                    world,
                    this.cropTypeA.getStateFromMeta(MathHelper.nextIntInclusive(random, 2, 7)),
                    1,
                    1,
                    j,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    this.cropTypeA.getStateFromMeta(MathHelper.nextIntInclusive(random, 2, 7)),
                    2,
                    1,
                    j,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    this.cropTypeB.getStateFromMeta(MathHelper.nextIntInclusive(random, 2, 7)),
                    4,
                    1,
                    j,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    this.cropTypeB.getStateFromMeta(MathHelper.nextIntInclusive(random, 2, 7)),
                    5,
                    1,
                    j,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    this.cropTypeC.getStateFromMeta(MathHelper.nextIntInclusive(random, 2, 7)),
                    7,
                    1,
                    j,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    this.cropTypeC.getStateFromMeta(MathHelper.nextIntInclusive(random, 2, 7)),
                    8,
                    1,
                    j,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    this.cropTypeD.getStateFromMeta(MathHelper.nextIntInclusive(random, 2, 7)),
                    10,
                    1,
                    j,
                    structureBoundingBox);
            this.setBlockState(
                    world,
                    this.cropTypeD.getStateFromMeta(MathHelper.nextIntInclusive(random, 2, 7)),
                    11,
                    1,
                    j,
                    structureBoundingBox);
            j++;
            if (i == 0) {
                break;
            }
        }

        j = 0;

        while (j < 9) {
            int k = 0;

            while (true) {
                if (k < 13) {
                    this.clearCurrentPositionBlocksUpwards(world, k, 4, j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world, Blocks.dirt.getDefaultState(), k, -1, j, structureBoundingBox);
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

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setInteger("CA", Block.blockRegistry.getIDForObject(this.cropTypeA));
        compoundTag.setInteger("CB", Block.blockRegistry.getIDForObject(this.cropTypeB));
        compoundTag.setInteger("CC", Block.blockRegistry.getIDForObject(this.cropTypeC));
        compoundTag.setInteger("CD", Block.blockRegistry.getIDForObject(this.cropTypeD));
    }

    public StructureVillagePiecesField1(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(structureVillagePiecesStart, i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
        this.cropTypeA = this.method_903870cf4c7ca1fd93c4(random);
        this.cropTypeB = this.method_903870cf4c7ca1fd93c4(random);
        this.cropTypeC = this.method_903870cf4c7ca1fd93c4(random);
        this.cropTypeD = this.method_903870cf4c7ca1fd93c4(random);
    }
}
