package net.minecraft.item.crafting;

import net.minecraft.block.BlockDoublePlantEnumPlantType;
import net.minecraft.block.BlockFlowerEnumFlowerType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesDyes {

    public void addRecipes(CraftingManager craftingManager) {
        for (int i = 0; i < 16; i++) {
            craftingManager.addShapelessRecipe(
                    new ItemStack(Blocks.wool, 1, i),
                    new Object[] {
                        new ItemStack(Items.dye, 1, 15 - i),
                        new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0)
                    });
            craftingManager.addRecipe(
                    new ItemStack(Blocks.stained_hardened_clay, 8, 15 - i),
                    new Object[] {
                        "###",
                        "#X#",
                        "###",
                        '#',
                        new ItemStack(Blocks.hardened_clay),
                        'X',
                        new ItemStack(Items.dye, 1, i)
                    });
            craftingManager.addRecipe(
                    new ItemStack(Blocks.stained_glass, 8, 15 - i),
                    new Object[] {
                        "###",
                        "#X#",
                        "###",
                        '#',
                        new ItemStack(Blocks.glass),
                        'X',
                        new ItemStack(Items.dye, 1, i)
                    });
            craftingManager.addRecipe(
                    new ItemStack(Blocks.stained_glass_pane, 16, i),
                    new Object[] {"###", "###", '#', new ItemStack(Blocks.stained_glass, 1, i)});
        }

        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.yellow_flower, 1, BlockFlowerEnumFlowerType.DANDELION.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Blocks.red_flower, 1, BlockFlowerEnumFlowerType.POPPY.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 3, EnumDyeColor.WHITE.getDyeColorDamage()),
                new Object[] {Items.bone});
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.ORANGE.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.LIME.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.GRAY.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.SILVER.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.GRAY.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 3, EnumDyeColor.SILVER.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.LIGHT_BLUE.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.CYAN.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.PURPLE.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.PURPLE.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 3, EnumDyeColor.MAGENTA.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 4, EnumDyeColor.MAGENTA.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()),
                    new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeColorDamage())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.LIGHT_BLUE.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.red_flower, 1, BlockFlowerEnumFlowerType.BLUE_ORCHID.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.MAGENTA.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(Blocks.red_flower, 1, BlockFlowerEnumFlowerType.ALLIUM.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.red_flower, 1, BlockFlowerEnumFlowerType.HOUSTONIA.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.red_flower, 1, BlockFlowerEnumFlowerType.RED_TULIP.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.ORANGE.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.red_flower, 1, BlockFlowerEnumFlowerType.ORANGE_TULIP.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.red_flower, 1, BlockFlowerEnumFlowerType.WHITE_TULIP.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.red_flower, 1, BlockFlowerEnumFlowerType.PINK_TULIP.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 1, EnumDyeColor.SILVER.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.red_flower, 1, BlockFlowerEnumFlowerType.OXEYE_DAISY.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.YELLOW.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.double_plant,
                            1,
                            BlockDoublePlantEnumPlantType.SUNFLOWER.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.double_plant, 1, BlockDoublePlantEnumPlantType.SYRINGA.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.RED.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.double_plant, 1, BlockDoublePlantEnumPlantType.ROSE.getMeta())
                });
        craftingManager.addShapelessRecipe(
                new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeColorDamage()),
                new Object[] {
                    new ItemStack(
                            Blocks.double_plant, 1, BlockDoublePlantEnumPlantType.PAEONIA.getMeta())
                });

        for (int j = 0; j < 16; j++) {
            craftingManager.addRecipe(
                    new ItemStack(Blocks.carpet, 3, j),
                    new Object[] {"##", '#', new ItemStack(Blocks.wool, 1, j)});
        }
    }


}
