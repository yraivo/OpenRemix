package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesRoomCrossing extends StructureStrongholdPiecesStronghold {

    protected int roomType;

    private static List strongholdRoomCrossingChestContents;

    static {
        net.minecraft.world.gen.structure.StructureStrongholdPiecesRoomCrossing.strongholdRoomCrossingChestContents =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
                            new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
                            new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
                            new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10),
                            new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
                            new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
                            new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1)
                        });
    }

    public StructureStrongholdPiecesRoomCrossing(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
        this.roomType = random.nextInt(5);
    }

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
                10,
                6,
                10,
                true,
                random,
                StructureStrongholdPieces.access$200());
        this.placeDoor(world, random, structureBoundingBox, this.bNb, 4, 1, 0);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                4,
                1,
                10,
                6,
                3,
                10,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                1,
                4,
                0,
                3,
                6,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                10,
                1,
                4,
                10,
                3,
                6,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        switch (this.roomType) {
            case 0:
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.torch.getDefaultState(), 4, 3, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.torch.getDefaultState(), 6, 3, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.torch.getDefaultState(), 5, 3, 4, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.torch.getDefaultState(), 5, 3, 6, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stone_slab.getDefaultState(), 4, 1, 4, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stone_slab.getDefaultState(), 4, 1, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stone_slab.getDefaultState(), 4, 1, 6, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stone_slab.getDefaultState(), 6, 1, 4, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stone_slab.getDefaultState(), 6, 1, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stone_slab.getDefaultState(), 6, 1, 6, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stone_slab.getDefaultState(), 5, 1, 4, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stone_slab.getDefaultState(), 5, 1, 6, structureBoundingBox);
                if (i == 0) {
                    break;
                }
            case 1:
                int j = 0;

                while (j < 5) {
                    this.setBlockState(
                            world,
                            Blocks.stonebrick.getDefaultState(),
                            3,
                            1,
                            3 + j,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.stonebrick.getDefaultState(),
                            7,
                            1,
                            3 + j,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.stonebrick.getDefaultState(),
                            3 + j,
                            1,
                            3,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.stonebrick.getDefaultState(),
                            3 + j,
                            1,
                            7,
                            structureBoundingBox);
                    j++;
                    if (i != 0) {
                        break;
                    }
                }

                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 5, 1, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 5, 2, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.stonebrick.getDefaultState(), 5, 3, 5, structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.flowing_water.getDefaultState(),
                        5,
                        4,
                        5,
                        structureBoundingBox);
                if (i == 0) {
                    break;
                }
            case 2:
                int k = 1;

                while (k <= 9) {
                    this.setBlockState(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            1,
                            3,
                            k,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            9,
                            3,
                            k,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                k = 1;

                while (k <= 9) {
                    this.setBlockState(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            k,
                            3,
                            1,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            k,
                            3,
                            9,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                this.setBlockState(
                        world, Blocks.cobblestone.getDefaultState(), 5, 1, 4, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.cobblestone.getDefaultState(), 5, 1, 6, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.cobblestone.getDefaultState(), 5, 3, 4, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.cobblestone.getDefaultState(), 5, 3, 6, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.cobblestone.getDefaultState(), 4, 1, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.cobblestone.getDefaultState(), 6, 1, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.cobblestone.getDefaultState(), 4, 3, 5, structureBoundingBox);
                this.setBlockState(
                        world, Blocks.cobblestone.getDefaultState(), 6, 3, 5, structureBoundingBox);
                k = 1;

                while (k <= 3) {
                    this.setBlockState(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            4,
                            k,
                            4,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            6,
                            k,
                            4,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            4,
                            k,
                            6,
                            structureBoundingBox);
                    this.setBlockState(
                            world,
                            Blocks.cobblestone.getDefaultState(),
                            6,
                            k,
                            6,
                            structureBoundingBox);
                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                this.setBlockState(
                        world, Blocks.torch.getDefaultState(), 5, 3, 5, structureBoundingBox);
                k = 2;

                while (k <= 8) {
                    this.setBlockState(
                            world, Blocks.planks.getDefaultState(), 2, 3, k, structureBoundingBox);
                    this.setBlockState(
                            world, Blocks.planks.getDefaultState(), 3, 3, k, structureBoundingBox);
                    if (k <= 3 || k >= 7) {
                        this.setBlockState(
                                world,
                                Blocks.planks.getDefaultState(),
                                4,
                                3,
                                k,
                                structureBoundingBox);
                        this.setBlockState(
                                world,
                                Blocks.planks.getDefaultState(),
                                5,
                                3,
                                k,
                                structureBoundingBox);
                        this.setBlockState(
                                world,
                                Blocks.planks.getDefaultState(),
                                6,
                                3,
                                k,
                                structureBoundingBox);
                    }

                    this.setBlockState(
                            world, Blocks.planks.getDefaultState(), 7, 3, k, structureBoundingBox);
                    this.setBlockState(
                            world, Blocks.planks.getDefaultState(), 8, 3, k, structureBoundingBox);
                    k++;
                    if (i != 0) {
                        break;
                    }
                }

                this.setBlockState(
                        world,
                        Blocks.ladder.getStateFromMeta(
                                this.getMetadataWithOffset(Blocks.ladder, Facing.WEST.getIndex())),
                        9,
                        1,
                        3,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.ladder.getStateFromMeta(
                                this.getMetadataWithOffset(Blocks.ladder, Facing.WEST.getIndex())),
                        9,
                        2,
                        3,
                        structureBoundingBox);
                this.setBlockState(
                        world,
                        Blocks.ladder.getStateFromMeta(
                                this.getMetadataWithOffset(Blocks.ladder, Facing.WEST.getIndex())),
                        9,
                        3,
                        3,
                        structureBoundingBox);
                this.generateChestContents(
                        world,
                        structureBoundingBox,
                        random,
                        3,
                        4,
                        8,
                        WeightedRandomChestContent.func_177629_a(
                                net.minecraft.world.gen.structure.StructureStrongholdPiecesRoomCrossing.strongholdRoomCrossingChestContents,
                                new WeightedRandomChestContent[] {
                                    Items.enchanted_book.getRandomEnchantedBook(random)
                                }),
                        1 + random.nextInt(4));
        }

        return true;
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setInteger("Type", this.roomType);
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.roomType = compoundTag.getInteger("Type");
    }

    public StructureStrongholdPiecesRoomCrossing() {}

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 4, 1);
        this.getNextComponentX(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 4);
        this.getNextComponentZ(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 4);
    }

    public static StructureStrongholdPiecesRoomCrossing createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -4, -1, 0, 11, 7, 11, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesRoomCrossing(l, random, structureBoundingBox, facing)
                : null;
    }
}
