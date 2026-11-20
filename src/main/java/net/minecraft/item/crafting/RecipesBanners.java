package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class RecipesBanners {

    void addRecipes(CraftingManager craftingManager) {
        for (EnumDyeColor enumDyeColor : EnumDyeColor.values()) {
            craftingManager.addRecipe(
                    new ItemStack(Items.banner, 1, enumDyeColor.getDyeColorDamage()),
                    new Object[] {
                        "###",
                        "###",
                        " | ",
                        '#',
                        new ItemStack(Blocks.wool, 1, enumDyeColor.getMetadata()),
                        '|',
                        Items.stick
                    });
        }

        craftingManager.addRecipe(new RecipesBannersRecipeDuplicatePattern(null));
        craftingManager.addRecipe(new RecipesBannersRecipeAddPattern(null));
    }


}
