package net.minecraft.inventory;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class InventoryHelper {
    private static final Random RANDOM = new Random();

    public static void dropInventoryItems(World world, BlockPos blockPos, IInventory iInventory) {
        func_180174_a(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), iInventory);
    }

    public static void func_180176_a(World world, Entity entity, IInventory iInventory) {
        net.minecraft.inventory.InventoryHelper.func_180174_a(
                world, entity.posX, entity.posY, entity.posZ, iInventory);
    }

    private static void spawnItemStack(
            World world, double d0, double d1, double d2, ItemStack itemStack) {
        int k = Container.cR();
        float f = net.minecraft.inventory.InventoryHelper.RANDOM.nextFloat() * 0.8F + 0.1F;
        float f1 = net.minecraft.inventory.InventoryHelper.RANDOM.nextFloat() * 0.8F + 0.1F;
        int i = k;
        float f2 = net.minecraft.inventory.InventoryHelper.RANDOM.nextFloat() * 0.8F + 0.1F;

        while (itemStack.stackSize > 0) {
            int j = net.minecraft.inventory.InventoryHelper.RANDOM.nextInt(21) + 10;
            if (j > itemStack.stackSize) {
                j = itemStack.stackSize;
            }

            itemStack.stackSize -= j;
            EntityItem entityItem =
                    new EntityItem(
                            world,
                            d0 + f,
                            d1 + f1,
                            d2 + f2,
                            new ItemStack(itemStack.getItem(), j, itemStack.getItemDamage()));
            if (itemStack.hasTagCompound()) {
                entityItem.getEntityItem().setTag((CompoundTag) itemStack.getTag().copy());
            }

            float f3 = 0.05F;
            entityItem.motionX = net.minecraft.inventory.InventoryHelper.RANDOM.nextGaussian() * f3;
            entityItem.motionY = net.minecraft.inventory.InventoryHelper.RANDOM.nextGaussian() * f3 + 0.2F;
            entityItem.motionZ = net.minecraft.inventory.InventoryHelper.RANDOM.nextGaussian() * f3;
            world.spawnEntityInWorld(entityItem);
            if (i != 0) {
                break;
            }
        }
    }

    private static void func_180174_a(
            World world, double d0, double d1, double d2, IInventory iInventory) {
        int k = Container.aW();
        int j = 0;
        int i = k;

        while (j < iInventory.getSizeInventory()) {
            ItemStack itemStack = iInventory.getStackInSlot(j);
            if (itemStack != null) {
                spawnItemStack(world, d0, d1, d2, itemStack);
            }

            j++;
            if (i == 0) {
                break;
            }
        }
    }
}
