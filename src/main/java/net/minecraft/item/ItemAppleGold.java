package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.List;

public class ItemAppleGold extends ItemFood {
    protected void onFoodEaten(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (!world.isRemote) {
            playerEntity.addPotionEffect(new PotionEffect(Potion.ABSORPTION.id, 2400, 0));
        }

        if (itemStack.getItemDamage() > 0) {
            if (world.isRemote) {
                return;
            }

            playerEntity.addPotionEffect(new PotionEffect(Potion.REGENERATION.id, 600, 4));
            playerEntity.addPotionEffect(new PotionEffect(Potion.RESISTANCE.id, 6000, 0));
            playerEntity.addPotionEffect(new PotionEffect(Potion.FIRE_RESISTANCE.id, 6000, 0));
            if (text == null) {
                return;
            }
        }

        super.onFoodEaten(itemStack, world, playerEntity);
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
    }

    public ItemAppleGold(int i, float f, boolean flag) {
        super(i, f, flag);
        this.setHasSubtypes(true);
    }

    public boolean hasEffect(ItemStack itemStack) {
        return itemStack.getItemDamage() > 0;
    }

    public EnumRarity getRarity(ItemStack itemStack) {
        return itemStack.getItemDamage() == 0 ? EnumRarity.RARE : EnumRarity.EPIC;
    }
}
