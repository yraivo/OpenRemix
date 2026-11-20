package net.minecraft.world.gen.structure;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePiecesVillage;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesWoodHut extends StructureVillagePiecesVillage {
    private static String[] packedTextFragments;
    private int tablePosition;
    private boolean isTallHouse;

    public StructureVillagePiecesWoodHut() {}

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setInteger(packedTextFragments[0], this.tablePosition);
        compoundTag.setBoolean(packedTextFragments[1], this.isTallHouse);
    }

    // region String decoding support

    // endregion

    public static StructureVillagePiecesWoodHut createPiece(
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
                        i, j, k, 0, 0, 0, 4, 6, 5, facing);
        return canVillageGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureVillagePiecesWoodHut(
                        structureVillagePiecesStart, l, random, structureBoundingBox, facing)
                : null;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.world.gen.structure.StructureVillagePiecesWoodHut.packedTextFragments[0] = new String("T");
        net.minecraft.world.gen.structure.StructureVillagePiecesWoodHut.packedTextFragments[1] = new String("C");
        net.minecraft.world.gen.structure.StructureVillagePiecesWoodHut.packedTextFragments[2] = new String("T");
        net.minecraft.world.gen.structure.StructureVillagePiecesWoodHut.packedTextFragments[3] = new String("C");
    }

    // endregion

    static {
        packedTextFragments = new String[4];
        unpackLiteralFragments();
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.tablePosition = compoundTag.getInteger(packedTextFragments[2]);
        this.isTallHouse = compoundTag.getBoolean(packedTextFragments[3]);
    }

    // region String decoding support

    // endregion

    public StructureVillagePiecesWoodHut(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(structureVillagePiecesStart, i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
        this.isTallHouse = random.nextBoolean();
        this.tablePosition = random.nextInt(3);
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.cR();
        if (this.ef < 0) {
            this.ef = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.ef < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 6 - 1, 0);
        }

        label47:
        {
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    1,
                    1,
                    1,
                    3,
                    5,
                    4,
                    Blocks.air.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    0,
                    0,
                    0,
                    3,
                    0,
                    4,
                    Blocks.cobblestone.getDefaultState(),
                    Blocks.cobblestone.getDefaultState(),
                    false);
            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    1,
                    0,
                    1,
                    2,
                    0,
                    3,
                    Blocks.dirt.getDefaultState(),
                    Blocks.dirt.getDefaultState(),
                    false);
            if (this.isTallHouse) {
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        1,
                        4,
                        1,
                        2,
                        4,
                        3,
                        Blocks.log.getDefaultState(),
                        Blocks.log.getDefaultState(),
                        false);
                if (i != 0) {
                    break label47;
                }
            }

            this.fillWithBlocks(
                    world,
                    structureBoundingBox,
                    1,
                    5,
                    1,
                    2,
                    5,
                    3,
                    Blocks.log.getDefaultState(),
                    Blocks.log.getDefaultState(),
                    false);
        }

        this.setBlockState(world, Blocks.log.getDefaultState(), 1, 4, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 2, 4, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 1, 4, 4, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 2, 4, 4, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 1, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 2, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 0, 4, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 1, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 2, structureBoundingBox);
        this.setBlockState(world, Blocks.log.getDefaultState(), 3, 4, 3, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                0,
                0,
                3,
                0,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                1,
                0,
                3,
                3,
                0,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                4,
                0,
                3,
                4,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                1,
                4,
                3,
                3,
                4,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                1,
                0,
                3,
                3,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                1,
                1,
                3,
                3,
                3,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                0,
                2,
                3,
                0,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                4,
                2,
                3,
                4,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 3, 2, 2, structureBoundingBox);
        if (this.tablePosition > 0) {
            this.setBlockState(
                    world, Blocks.oak_fence.getDefaultState(), this.tablePosition, 1, 3, structureBoundingBox);
            this.setBlockState(
                    world,
                    Blocks.wooden_pressure_plate.getDefaultState(),
                    this.tablePosition,
                    2,
                    3,
                    structureBoundingBox);
        }

        this.setBlockState(world, Blocks.air.getDefaultState(), 1, 1, 0, structureBoundingBox);
        this.setBlockState(world, Blocks.air.getDefaultState(), 1, 2, 0, structureBoundingBox);
        this.placeDoorCurrentPosition(
                world,
                structureBoundingBox,
                random,
                1,
                1,
                0,
                Facing.getHorizontal(this.getMetadataWithOffset(Blocks.oak_door, 1)));
        if (this.getBlockStateFromPos(world, 1, 0, -1, structureBoundingBox)
                                .getBlock()
                                .getMaterial()
                        == Material.AIR
                && this.getBlockStateFromPos(world, 1, -1, -1, structureBoundingBox)
                                .getBlock()
                                .getMaterial()
                        != Material.AIR) {
            this.setBlockState(
                    world,
                    Blocks.stone_stairs.getStateFromMeta(
                            this.getMetadataWithOffset(Blocks.stone_stairs, 3)),
                    1,
                    0,
                    -1,
                    structureBoundingBox);
        }

        int j = 0;

        label41:
        while (j < 5) {
            int k = 0;

            while (true) {
                if (k < 4) {
                    this.clearCurrentPositionBlocksUpwards(world, k, 6, j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            k,
                            -1,
                            j,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    break label41;
                }
                break;
            }
        }

        this.spawnVillagers(world, structureBoundingBox, 1, 1, 2, 1);
        return true;
    }
}
