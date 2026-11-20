package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesWeapons {

    private static String[] packedTextFragments;
    private String[][] recipePatterns;

    private Object[][] recipeItems;

    public RecipesWeapons() {
        this.recipePatterns =
                new String[][] {
                    {packedTextFragments[0], packedTextFragments[1], packedTextFragments[2]}
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
                        Items.wooden_sword,
                        Items.stone_sword,
                        Items.iron_sword,
                        Items.diamond_sword,
                        Items.golden_sword
                    }
                };
    }

    static {
        net.minecraft.item.crafting.RecipesWeapons.packedTextFragments = new String[7];
        net.minecraft.item.crafting.RecipesWeapons.packedTextFragments[0] = new String("X");
        net.minecraft.item.crafting.RecipesWeapons.packedTextFragments[1] = new String("X");
        net.minecraft.item.crafting.RecipesWeapons.packedTextFragments[2] = new String("#");
        net.minecraft.item.crafting.RecipesWeapons.packedTextFragments[3] = new String("X");
        net.minecraft.item.crafting.RecipesWeapons.packedTextFragments[4] = new String("#");
        net.minecraft.item.crafting.RecipesWeapons.packedTextFragments[5] = new String("Y");
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
                new ItemStack(Items.bow, 1),
                new Object[] {" #X", "# X", " #X", 'X', Items.string, '#', Items.stick});
        craftingManager.addRecipe(
                new ItemStack(Items.arrow, 4),
                new Object[] {
                    packedTextFragments[3],
                    packedTextFragments[4],
                    packedTextFragments[5],
                    'Y',
                    Items.feather,
                    'X',
                    Items.flint,
                    '#',
                    Items.stick
                });
    }
}
