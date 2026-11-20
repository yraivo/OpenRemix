package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemEgg extends Item {

    private static String kE;

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (!playerEntity.capabilities.isCreativeMode) {
            itemStack.stackSize--;
        }

        world.playSoundAtEntity(
                playerEntity, kE, 0.5F, 0.4F / (net.minecraft.item.Item.itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityEgg(world, playerEntity));
        }

        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }

    public ItemEgg() {
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        kE = "random.bow";
    }
}
