package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.command.IEntitySelector;
import net.minecraft.command.IEntitySelectorArmoredMob;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;

import com.google.common.base.Predicates;

import java.util.List;

public final class ItemArmorDispenserBehaviorBehaviorDefaultDispenseItem extends BehaviorDefaultDispenseItem {
    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        BlockPos blockPos =
                iBlockSource
                        .getBlockPos()
                        .offset(BlockDispenser.getFacing(iBlockSource.getBlockMetadata()));
        int x = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        BoundingBox boundingBox = new BoundingBox(x, j, k, x + 1, j + 1, k + 1);
        List entitiesWithinAABB =
                iBlockSource
                        .getWorld()
                        .getEntitiesWithinAABB(
                                LivingEntity.class,
                                boundingBox,
                                Predicates.and(
                                        IEntitySelector.NOT_SPECTATING,
                                        new IEntitySelectorArmoredMob(itemStack)));
        if (entitiesWithinAABB.size() > 0) {
            LivingEntity livingEntity = (LivingEntity) entitiesWithinAABB.get(0);
            int l = livingEntity instanceof PlayerEntity ? 1 : 0;
            int armorPosition = EntityLiving.getArmorPosition(itemStack);
            ItemStack itemStackValue = itemStack.copy();
            itemStackValue.stackSize = 1;
            livingEntity.setCurrentItemOrArmor(armorPosition - l, itemStackValue);
            if (livingEntity instanceof EntityLiving) {
                ((EntityLiving) livingEntity).setEquipmentDropChance(armorPosition, 2.0F);
            }

            itemStack.stackSize--;
            return itemStack;
        } else {
            return super.dispenseStack(iBlockSource, itemStack);
        }
    }
}
