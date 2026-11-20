package net.minecraft.world.gen.structure;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureVillagePiecesVillage;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesHouse2 extends StructureVillagePiecesVillage {

    private static List villageBlacksmithChestContents;

    private boolean hasMadeChest;

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.hasMadeChest = compoundTag.getBoolean("Chest");
    }

    public StructureVillagePiecesHouse2(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(structureVillagePiecesStart, i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }

    protected int method_eea4c703766e71e0df14(int i, int j) {
        return 3;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.ef < 0) {
            this.ef = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.ef < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 6 - 1, 0);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                0,
                9,
                4,
                6,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                9,
                0,
                6,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                4,
                0,
                9,
                4,
                6,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                5,
                0,
                9,
                5,
                6,
                Blocks.stone_slab.getDefaultState(),
                Blocks.stone_slab.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                5,
                1,
                8,
                5,
                5,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
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
                0,
                1,
                0,
                0,
                4,
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
                4,
                0,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                6,
                0,
                4,
                6,
                Blocks.log.getDefaultState(),
                Blocks.log.getDefaultState(),
                false);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 3, 3, 1, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                1,
                2,
                3,
                3,
                2,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                3,
                5,
                3,
                3,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                1,
                0,
                3,
                5,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                1,
                1,
                6,
                5,
                3,
                6,
                Blocks.planks.getDefaultState(),
                Blocks.planks.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                5,
                1,
                0,
                5,
                3,
                0,
                Blocks.oak_fence.getDefaultState(),
                Blocks.oak_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                9,
                1,
                0,
                9,
                3,
                0,
                Blocks.oak_fence.getDefaultState(),
                Blocks.oak_fence.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                6,
                1,
                4,
                9,
                4,
                6,
                Blocks.cobblestone.getDefaultState(),
                Blocks.cobblestone.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.flowing_lava.getDefaultState(), 7, 1, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.flowing_lava.getDefaultState(), 8, 1, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.iron_bars.getDefaultState(), 9, 2, 5, structureBoundingBox);
        this.setBlockState(
                world, Blocks.iron_bars.getDefaultState(), 9, 2, 4, structureBoundingBox);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                7,
                2,
                4,
                8,
                2,
                5,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.cobblestone.getDefaultState(), 6, 1, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.furnace.getDefaultState(), 6, 2, 3, structureBoundingBox);
        this.setBlockState(world, Blocks.furnace.getDefaultState(), 6, 3, 3, structureBoundingBox);
        this.setBlockState(
                world, Blocks.double_stone_slab.getDefaultState(), 8, 1, 1, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 2, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 0, 2, 4, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 2, 2, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.glass_pane.getDefaultState(), 4, 2, 6, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 2, 1, 4, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.wooden_pressure_plate.getDefaultState(),
                2,
                2,
                4,
                structureBoundingBox);
        this.setBlockState(world, Blocks.planks.getDefaultState(), 1, 1, 5, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.oak_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.oak_stairs, 3)),
                2,
                1,
                5,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.oak_stairs.getStateFromMeta(
                        this.getMetadataWithOffset(Blocks.oak_stairs, 1)),
                1,
                1,
                4,
                structureBoundingBox);
        if (!this.hasMadeChest
                && structureBoundingBox.isVecInside(
                        new BlockPos(
                                this.getXWithOffset(5, 5),
                                this.getYWithOffset(1),
                                this.getZWithOffset(5, 5)))) {
            this.hasMadeChest = true;
            this.generateChestContents(
                    world, structureBoundingBox, random, 5, 1, 5, net.minecraft.world.gen.structure.StructureVillagePiecesHouse2.villageBlacksmithChestContents, 3 + random.nextInt(6));
        }

        int j = 6;

        while (j <= 8) {
            if (this.getBlockStateFromPos(world, j, 0, -1, structureBoundingBox)
                                    .getBlock()
                                    .getMaterial()
                            == Material.AIR
                    && this.getBlockStateFromPos(world, j, -1, -1, structureBoundingBox)
                                    .getBlock()
                                    .getMaterial()
                            != Material.AIR) {
                this.setBlockState(
                        world,
                        Blocks.stone_stairs.getStateFromMeta(
                                this.getMetadataWithOffset(Blocks.stone_stairs, 3)),
                        j,
                        0,
                        -1,
                        structureBoundingBox);
            }

            j++;
            if (i != 0) {
                break;
            }
        }

        j = 0;

        label43:
        while (j < 7) {
            int k = 0;

            while (true) {
                if (k < 10) {
                    this.clearCurrentPositionBlocksUpwards(world, k, 6, j, structureBoundingBox);
                    this.replaceAirAndLiquidDownwards(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            k,
                            -1,
                            j,
                            structureBoundingBox);
                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    break label43;
                }
                break;
            }
        }

        this.spawnVillagers(world, structureBoundingBox, 7, 1, 1, 1);
        return true;
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Chest", this.hasMadeChest);
    }

    public StructureVillagePiecesHouse2() {}

    static {
        net.minecraft.world.gen.structure.StructureVillagePiecesHouse2.villageBlacksmithChestContents =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
                            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
                            new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
                            new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
                            new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
                            new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5),
                            new WeightedRandomChestContent(
                                    Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5),
                            new WeightedRandomChestContent(
                                    Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5),
                            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
                            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
                        });
    }

    public static StructureVillagePiecesHouse2 createPiece(
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
                        i, j, k, 0, 0, 0, 10, 6, 7, facing);
        return canVillageGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureVillagePiecesHouse2(
                        structureVillagePiecesStart, l, random, structureBoundingBox, facing)
                : null;
    }
}
