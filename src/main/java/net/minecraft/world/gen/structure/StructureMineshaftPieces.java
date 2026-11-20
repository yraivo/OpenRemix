package net.minecraft.world.gen.structure;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Random;

public class StructureMineshaftPieces {

    private static List CHEST_CONTENT_WEIGHT_LIST;

    private static StructureComponent findNextPiece(
            StructureComponent structureComponent,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        if (l > 8) {
            return null;
        }

        if (Math.abs(i - structureComponent.getBoundingBox().minX) <= 80
                && Math.abs(k - structureComponent.getBoundingBox().minZ) <= 80) {
            StructureComponent structureComponentValue =
                    findAndCreatePiece(list, random, i, j, k, facing, l + 1);
            if (structureComponentValue != null) {
                list.add(structureComponentValue);
                structureComponentValue.buildComponent(structureComponent, list, random);
            }

            return structureComponentValue;
        } else {
            return null;
        }
    }

    static StructureComponent method_6267762401a35bb3f241(
            StructureComponent structureComponent,
            List list,
            Random random,
            int i,
            int j,
            int k,
            Facing facing,
            int l) {
        return findNextPiece(structureComponent, list, random, i, j, k, facing, l);
    }

    public static void registerStructurePieces() {
        MapGenStructureIO.registerStructureComponent(
                StructureMineshaftPiecesCorridor.class, "MSCorridor");
        MapGenStructureIO.registerStructureComponent(
                StructureMineshaftPiecesCross.class, "MSCrossing");
        MapGenStructureIO.registerStructureComponent(StructureMineshaftPiecesRoom.class, "MSRoom");
        MapGenStructureIO.registerStructureComponent(StructureMineshaftPiecesStairs.class, "MSStairs");
    }

    private static StructureComponent findAndCreatePiece(
            List list, Random random, int i, int j, int k, Facing facing, int l) {
        int i1 = random.nextInt(100);
        if (i1 >= 80) {
            StructureBoundingBox structureBoundingBox =
                    StructureMineshaftPiecesCross.findPieceBox(
                            list, random, i, j, k, facing);
            if (structureBoundingBox != null) {
                return new StructureMineshaftPiecesCross(l, random, structureBoundingBox, facing);
            }
        } else if (i1 >= 70) {
            StructureBoundingBox structureBoundingBoxValue =
                    StructureMineshaftPiecesStairs.findPieceBox(
                            list, random, i, j, k, facing);
            if (structureBoundingBoxValue != null) {
                return new StructureMineshaftPiecesStairs(l, random, structureBoundingBoxValue, facing);
            }
        } else {
            StructureBoundingBox structureBoundingBoxValue2 =
                    StructureMineshaftPiecesCorridor.findPieceBox(
                            list, random, i, j, k, facing);
            if (structureBoundingBoxValue2 != null) {
                return new StructureMineshaftPiecesCorridor(
                        l, random, structureBoundingBoxValue2, facing);
            }
        }

        return null;
    }

    static List fN() {
        return net.minecraft.world.gen.structure.StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST;
    }

    static {
        net.minecraft.world.gen.structure.StructureMineshaftPieces.CHEST_CONTENT_WEIGHT_LIST =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10),
                            new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5),
                            new WeightedRandomChestContent(Items.redstone, 0, 4, 9, 5),
                            new WeightedRandomChestContent(
                                    Items.dye, EnumDyeColor.BLUE.getDyeColorDamage(), 4, 9, 5),
                            new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3),
                            new WeightedRandomChestContent(Items.coal, 0, 3, 8, 10),
                            new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15),
                            new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 1),
                            new WeightedRandomChestContent(
                                    Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1),
                            new WeightedRandomChestContent(Items.melon_seeds, 0, 2, 4, 10),
                            new WeightedRandomChestContent(Items.pumpkin_seeds, 0, 2, 4, 10),
                            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3),
                            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1)
                        });
    }
}
