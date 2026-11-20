package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemLead extends Item {
    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        Block block = world.getBlockState(blockPos).getBlock();
        if (block instanceof BlockFence) {
            if (world.isRemote) {
                return true;
            }

            attachToFence(playerEntity, world, blockPos);
            return true;
        } else {
            return false;
        }
    }

    public ItemLead() {
        this.setCreativeTab(CreativeTabs.tabTools);
    }

    public static boolean attachToFence(PlayerEntity playerEntity, World world, BlockPos blockPos) {
        EntityLeashKnot entityLeashKnot = EntityLeashKnot.getKnotForPosition(world, blockPos);
        byte b0 = 0;
        String text = ItemStack.getLegacyItemStackFlowMarker();
        double d0 = 7.0;
        int x = blockPos.getX();
        String textValue = text;
        int j = blockPos.getY();
        int k = blockPos.getZ();

        for (java.util.Iterator<?> __recoveryIterator171_2004 =
                        (world.getEntitiesWithinAABB(
                                        EntityLiving.class,
                                        new BoundingBox(
                                                x - d0, j - d0, k - d0, x + d0, j + d0, k + d0)))
                                .iterator();
                (__recoveryIterator171_2004).hasNext(); ) {
            EntityLiving entityLiving = (EntityLiving) (__recoveryIterator171_2004).next();
            if (entityLiving.getLeashed() && entityLiving.getLeashedToEntity() == playerEntity) {
                if (entityLeashKnot == null) {
                    entityLeashKnot = EntityLeashKnot.createKnot(world, blockPos);
                }

                entityLiving.setLeashedToEntity(entityLeashKnot, true);
                b0 = 1;
            }

            if (textValue != null) {
                break;
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }
}
