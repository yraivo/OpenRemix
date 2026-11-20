package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesTools {
    private static String[] packedTextFragments;

    private Object[][] recipeItems;

    private String[][] recipePatterns;

    public RecipesTools() {
        this.recipePatterns =
                new String[][] {
                    {"XXX", " # ", " # "},
                    {packedTextFragments[0], packedTextFragments[1], packedTextFragments[2]},
                    {"XX", "X#", " #"},
                    {"XX", " #", " #"}
                };
        this.recipeItems =
                new Object[][] {
                    {
                        Blocks.planks,
                        Blocks.cobblestone,
                        Items.iron_ingot,
                        Items.diamond,
                        Items.gold_ingot
                    },
                    {
                        Items.wooden_pickaxe,
                        Items.stone_pickaxe,
                        Items.iron_pickaxe,
                        Items.diamond_pickaxe,
                        Items.golden_pickaxe
                    },
                    {
                        Items.wooden_shovel,
                        Items.stone_shovel,
                        Items.iron_shovel,
                        Items.diamond_shovel,
                        Items.golden_shovel
                    },
                    {
                        Items.wooden_axe,
                        Items.stone_axe,
                        Items.iron_axe,
                        Items.diamond_axe,
                        Items.golden_axe
                    },
                    {
                        Items.wooden_hoe,
                        Items.stone_hoe,
                        Items.iron_hoe,
                        Items.diamond_hoe,
                        Items.golden_hoe
                    }
                };
    }

    public void addRecipes(CraftingManager craftingManager) {
        for (int i = 0; i < this.recipeItems[0].length; i++) {
            Object object = this.recipeItems[0][i];

            for (int j = 0; j < this.recipeItems.length - 1; j++) {
                Item item = (Item) this.recipeItems[j + 1][i];
                craftingManager.addRecipe(
                        new ItemStack(item),
                        new Object[] {this.recipePatterns[j], '#', Items.stick, 'X', object});
            }
        }

        craftingManager.addRecipe(
                new ItemStack(Items.shears), new Object[] {" #", "# ", '#', Items.iron_ingot});
    }

    static {
        net.minecraft.item.crafting.RecipesTools.packedTextFragments = new String[5];
        net.minecraft.item.crafting.RecipesTools.packedTextFragments[0] = new String("X");
        net.minecraft.item.crafting.RecipesTools.packedTextFragments[1] = new String("#");
        net.minecraft.item.crafting.RecipesTools.packedTextFragments[2] = new String("#");
    }
}
