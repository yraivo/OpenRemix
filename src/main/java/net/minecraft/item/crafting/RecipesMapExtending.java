package net.minecraft.item.crafting;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class RecipesMapExtending extends ShapedRecipes {
    private static String g;

    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        boolean flag = ShapedRecipes.method_b417d7500ec59d69e373();
        if (!super.matches(inventoryCrafting, world)) {
            return false;
        }

        ItemStack itemStack = null;
        int i = 0;

        while (i < inventoryCrafting.getSizeInventory() && itemStack == null) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(i);
            if (itemStackValue != null && itemStackValue.getItem() == Items.filled_map) {
                itemStack = itemStackValue;
            }

            i++;
            if (!flag) {
                break;
            }
        }

        if (itemStack == null) {
            return false;
        }

        MapData mapData = Items.filled_map.getMapData(itemStack, world);
        return mapData == null ? false : mapData.scale < 4;
    }

    public RecipesMapExtending() {
        super(
                3,
                3,
                new ItemStack[] {
                    new ItemStack(Items.paper),
                    new ItemStack(Items.paper),
                    new ItemStack(Items.paper),
                    new ItemStack(Items.paper),
                    new ItemStack(Items.filled_map, 0, 32767),
                    new ItemStack(Items.paper),
                    new ItemStack(Items.paper),
                    new ItemStack(Items.paper),
                    new ItemStack(Items.paper)
                },
                new ItemStack(Items.map, 0, 0));
    }

    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        boolean flag1 = ShapedRecipes.method_b417d7500ec59d69e373();
        ItemStack itemStack = null;
        boolean flag = flag1;
        int i = 0;

        while (i < inventoryCrafting.getSizeInventory() && itemStack == null) {
            ItemStack itemStackValue = inventoryCrafting.getStackInSlot(i);
            if (itemStackValue != null && itemStackValue.getItem() == Items.filled_map) {
                itemStack = itemStackValue;
            }

            i++;
            if (!flag) {
                break;
            }
        }

        itemStack = itemStack.copy();
        itemStack.stackSize = 1;
        if (itemStack.getTag() == null) {
            itemStack.setTag(new CompoundTag());
        }

        itemStack.getTag().setBoolean(g, true);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ShapedRecipes.method_a8745b073fa8adfa1857(!flag);
        }

        return itemStack;
    }

    static {
        g = "map_is_scaling";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
