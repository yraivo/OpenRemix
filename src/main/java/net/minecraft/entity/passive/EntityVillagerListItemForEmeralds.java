package net.minecraft.entity.passive;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

class EntityVillagerListItemForEmeralds implements EntityVillagerITradeList {
    public EntityVillagerPriceInfo bkS;
    public ItemStack uK;

    public void modifyMerchantRecipeList(MerchantRecipeList merchantRecipeList, Random random) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int i = 1;
        String textValue = text;
        if (this.bkS != null) {
            i = this.bkS.getPrice(random);
        }

        ItemStack itemStack;
        ItemStack itemStackValue;
        {
            if (i < 0) {
                itemStack = new ItemStack(Items.emerald, 1, 0);
                itemStackValue = new ItemStack(this.uK.getItem(), -i, this.uK.getItemDamage());
                if (!(textValue == null)) {

                    itemStack = new ItemStack(Items.emerald, i, 0);
                    itemStackValue = new ItemStack(this.uK.getItem(), 1, this.uK.getItemDamage());
                }
            } else {

                itemStack = new ItemStack(Items.emerald, i, 0);
                itemStackValue = new ItemStack(this.uK.getItem(), 1, this.uK.getItemDamage());
            }
        }

        merchantRecipeList.add(new MerchantRecipe(itemStack, itemStackValue));
    }

    public EntityVillagerListItemForEmeralds(ItemStack itemStack, EntityVillagerPriceInfo bkSValue) {
        this.uK = itemStack;
        this.bkS = bkSValue;
    }

    public EntityVillagerListItemForEmeralds(Item item, EntityVillagerPriceInfo bkSValue) {
        this.uK = new ItemStack(item);
        this.bkS = bkSValue;
    }
}
