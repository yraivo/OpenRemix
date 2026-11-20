package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.PlayerEntity;

public class ItemSaddle extends Item {

    private static String kE;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        kE = "mob.horse.leather";
    }

    public boolean itemInteractionForEntity(
            ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity) {
        if (livingEntity instanceof EntityPig) {
            EntityPig entityPig = (EntityPig) livingEntity;
            if (!entityPig.getSaddled() && !entityPig.isChild()) {
                entityPig.setSaddled(true);
                entityPig.world.playSoundAtEntity(entityPig, kE, 0.5F, 1.0F);
                itemStack.stackSize--;
            }

            return true;
        } else {
            return false;
        }
    }

    public ItemSaddle() {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }

    public boolean hitEntity(
            ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntityValue) {
        this.itemInteractionForEntity(itemStack, (PlayerEntity) null, livingEntity);
        return true;
    }
}
