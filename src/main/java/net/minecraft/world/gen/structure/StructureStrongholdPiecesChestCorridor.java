package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockStoneSlabEnumType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class StructureStrongholdPiecesChestCorridor extends StructureStrongholdPiecesStronghold {

    private boolean hasMadeChest;

    private static List strongholdChestContents;

    static {
        net.minecraft.world.gen.structure.StructureStrongholdPiecesChestCorridor.strongholdChestContents =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 1, 10),
                            new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3),
                            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
                            new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
                            new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
                            new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
                            new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15),
                            new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
                        });
    }

    public static StructureStrongholdPiecesChestCorridor createPiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        StructureBoundingBox structureBoundingBox =
                StructureBoundingBox.getComponentToAddBoundingBox(
                        i, j, k, -1, -1, 0, 5, 5, 7, facing);
        return canStrongholdGoDeeper(structureBoundingBox)
                        && StructureComponent.findIntersecting(list, structureBoundingBox) == null
                ? new StructureStrongholdPiecesChestCorridor(l, random, structureBoundingBox, facing)
                : null;
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        super.readStructureFromNBT(compoundTag);
        this.hasMadeChest = compoundTag.getBoolean("Chest");
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        this.getNextComponentNormal(
                (StructureStrongholdPiecesStairs2) structureComponent, list, random, 1, 1);
    }

    public StructureStrongholdPiecesChestCorridor(
            int i, Random random, StructureBoundingBox bMdValue, Facing aAcValue) {
        super(i);
        this.coordBaseMode = aAcValue;
        this.bNb = this.getRandomDoor(random);
        this.boundingBox = bMdValue;
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
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                3,
                1,
                2,
                3,
                1,
                4,
                Blocks.stonebrick.getDefaultState(),
                Blocks.stonebrick.getDefaultState(),
                false);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(
                        BlockStoneSlabEnumType.SMOOTHBRICK.getMetadata()),
                3,
                1,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(
                        BlockStoneSlabEnumType.SMOOTHBRICK.getMetadata()),
                3,
                1,
                5,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(
                        BlockStoneSlabEnumType.SMOOTHBRICK.getMetadata()),
                3,
                2,
                2,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.stone_slab.getStateFromMeta(
                        BlockStoneSlabEnumType.SMOOTHBRICK.getMetadata()),
                3,
                2,
                4,
                structureBoundingBox);
        int j = 2;

        while (j <= 4) {
            this.setBlockState(
                    world,
                    Blocks.stone_slab.getStateFromMeta(
                            BlockStoneSlabEnumType.SMOOTHBRICK.getMetadata()),
                    2,
                    1,
                    j,
                    structureBoundingBox);
            j++;
            if (i != 0) {
                break;
            }
        }

        if (!this.hasMadeChest
                && structureBoundingBox.isVecInside(
                        new BlockPos(
                                this.getXWithOffset(3, 3),
                                this.getYWithOffset(2),
                                this.getZWithOffset(3, 3)))) {
            this.hasMadeChest = true;
            this.generateChestContents(
                    world,
                    structureBoundingBox,
                    random,
                    3,
                    2,
                    3,
                    WeightedRandomChestContent.func_177629_a(
                            net.minecraft.world.gen.structure.StructureStrongholdPiecesChestCorridor.strongholdChestContents,
                            new WeightedRandomChestContent[] {
                                Items.enchanted_book.getRandomEnchantedBook(random)
                            }),
                    2 + random.nextInt(2));
        }

        return true;
    }

    public StructureStrongholdPiecesChestCorridor() {}

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        super.writeStructureToNBT(compoundTag);
        compoundTag.setBoolean("Chest", this.hasMadeChest);
    }
}
