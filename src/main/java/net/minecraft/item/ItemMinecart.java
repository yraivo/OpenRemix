package net.minecraft.item;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockRailBaseEnumRailDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecartEnumMinecartType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.item.ItemMinecartDispenserMinecartBehaviorBehaviorDefaultDispenseItem;

public class ItemMinecart extends Item {
    private EntityMinecartEnumMinecartType minecartType;
    private static final IBehaviorDispenseItem dispenserMinecartBehavior =
            new ItemMinecartDispenserMinecartBehaviorBehaviorDefaultDispenseItem();

    public ItemMinecart(EntityMinecartEnumMinecartType buQValue) {
        this.maxStackSize = 1;
        this.minecartType = buQValue;
        this.setCreativeTab(CreativeTabs.tabTransport);
        BlockDispenser.dispenseBehaviorRegistry.putObject(
                this, net.minecraft.item.ItemMinecart.dispenserMinecartBehavior);
    }

    public static EntityMinecartEnumMinecartType access$000(ItemMinecart itemMinecart) {
        return itemMinecart.minecartType;
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        BlockState blockState = world.getBlockState(blockPos);
        if (BlockRailBase.isRailBlock(blockState)) {
            if (!world.isRemote) {
                BlockRailBaseEnumRailDirection blockRailBaseEnumRailDirection =
                        blockState.getBlock() instanceof BlockRailBase
                                ? (BlockRailBaseEnumRailDirection)
                                        blockState.getValue(
                                                ((BlockRailBase) blockState.getBlock())
                                                        .getShapeProperty())
                                : BlockRailBaseEnumRailDirection.NORTH_SOUTH;
                double d0 = 0.0;
                if (blockRailBaseEnumRailDirection.isAscending()) {
                    d0 = 0.5;
                }

                EntityMinecart entityMinecart =
                        EntityMinecart.createMinecart(
                                world,
                                blockPos.getX() + 0.5,
                                blockPos.getY() + 0.0625 + d0,
                                blockPos.getZ() + 0.5,
                                this.minecartType);
                if (itemStack.hasDisplayName()) {
                    entityMinecart.setCustomNameTag(itemStack.getDisplayName());
                }

                world.spawnEntityInWorld(entityMinecart);
            }

            itemStack.stackSize--;
            return true;
        } else {
            return false;
        }
    }
}
