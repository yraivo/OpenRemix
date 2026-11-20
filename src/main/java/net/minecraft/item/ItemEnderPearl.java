package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemEnderPearl extends Item {
    private static String kE;

    // region String decoding support

    // endregion

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (playerEntity.capabilities.isCreativeMode) {
            return itemStack;
        }

        itemStack.stackSize--;
        world.playSoundAtEntity(
                playerEntity, kE, 0.5F, 0.4F / (net.minecraft.item.Item.itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityEnderPearl(world, playerEntity));
        }

        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }

    static {
        kE = "random.bow";
    }

    // region String decoding support

    // endregion

    public ItemEnderPearl() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }
}
