package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemExpBottle extends Item {
    private static String kE;

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (!playerEntity.capabilities.isCreativeMode) {
            itemStack.stackSize--;
        }

        world.playSoundAtEntity(
                playerEntity, kE, 0.5F, 0.4F / (net.minecraft.item.Item.itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityExpBottle(world, playerEntity));
        }

        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }

    // region String decoding support

    // endregion

    static {
        kE = "random.bow";
    }

    public ItemExpBottle() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    // region String decoding support

    // endregion

    public boolean hasEffect(ItemStack itemStack) {
        return true;
    }
}
