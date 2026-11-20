package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemFishingRod extends Item {
    private static String kE;

    public boolean isItemTool(ItemStack itemStack) {
        return super.isItemTool(itemStack);
    }

    // region String decoding support

    // endregion

    public boolean shouldRotateAroundWhenRendering() {
        return true;
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (playerEntity.fishEntity != null) {
            int i = playerEntity.fishEntity.method_7eec03da329a017ec143();
            itemStack.damageItem(i, playerEntity);
            playerEntity.swingItem();
            if (text == null) {
                return itemStack;
            }
        }

        world.playSoundAtEntity(
                playerEntity, kE, 0.5F, 0.4F / (net.minecraft.item.Item.itemRand.nextFloat() * 0.4F + 0.8F));
        if (!world.isRemote) {
            world.spawnEntityInWorld(new EntityFishHook(world, playerEntity));
        }

        playerEntity.swingItem();
        playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        return itemStack;
    }

    static {
        kE = "random.bow";
    }

    public ItemFishingRod() {
        this.setMaxDamage(64);
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public int getItemEnchantability() {
        return 1;
    }

    public boolean isFull3D() {
        return true;
    }

    // region String decoding support

    // endregion
}
