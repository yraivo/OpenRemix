package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBow extends Item {
    public static String[] bowPullIconNameArray;

    public int getMaxItemUseDuration(ItemStack itemStack) {
        return 72000;
    }

    public ItemStack onItemUseFinish(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        return itemStack;
    }

    public EnumAction getItemUseAction(ItemStack itemStack) {
        return EnumAction.BOW;
    }

    public int getItemEnchantability() {
        return 1;
    }

    public ItemBow() {
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (playerEntity.capabilities.isCreativeMode
                || playerEntity.inventory.hasItem(Items.arrow)) {
            playerEntity.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }

        return itemStack;
    }

    static {
        net.minecraft.item.ItemBow.bowPullIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2"};
    }

    public void onPlayerStoppedUsing(
            ItemStack itemStack, World world, PlayerEntity playerEntity, int i) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        int j =
                !playerEntity.capabilities.isCreativeMode
                                && EnchantmentHelper.getEnchantmentLevel(
                                                Enchantment.infinity.effectId, itemStack)
                                        <= 0
                        ? 0
                        : 1;
        if (j != 0 || playerEntity.inventory.hasItem(Items.arrow)) {
            int k = this.getMaxItemUseDuration(itemStack) - i;
            float f = k / 20.0F;
            f = (f * f + f * 2.0F) / 3.0F;
            if (f < 0.1) {
                return;
            }

            if (f > 1.0F) {
                f = 1.0F;
            }

            EntityArrow entityArrow = new EntityArrow(world, playerEntity, f * 2.0F);
            if (f == 1.0F) {
                entityArrow.setIsCritical(true);
            }

            int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
            if (l > 0) {
                entityArrow.setDamage(entityArrow.getDamage() + l * 0.5 + 0.5);
            }

            int enchantmentLevel =
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
            if (enchantmentLevel > 0) {
                entityArrow.setKnockbackStrength(enchantmentLevel);
            }

            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0) {
                entityArrow.setFire(100);
            }

            {
                itemStack.damageItem(1, playerEntity);
                world.playSoundAtEntity(
                        playerEntity,
                        "random.bow",
                        1.0F,
                        1.0F / (net.minecraft.item.Item.itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                if (j != 0) {
                    entityArrow.canBePickedUp = 2;
                    if (!(text == null)) {

                        playerEntity.inventory.consumeInventoryItem(Items.arrow);
                    }
                } else {

                    playerEntity.inventory.consumeInventoryItem(Items.arrow);
                }
            }

            playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            if (!world.isRemote) {
                world.spawnEntityInWorld(entityArrow);
            }
        }
    }
}
