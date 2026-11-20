package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class ItemHangingEntity extends Item {
    private Class hangingEntityClass;

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (facing == Facing.DOWN) {
            return false;
        }

        if (facing == Facing.UP) {
            return false;
        }

        BlockPos blockPosValue = blockPos.offset(facing);
        if (!playerEntity.canPlayerEdit(blockPosValue, facing, itemStack)) {
            return false;
        }

        EntityHanging entityHanging = this.createEntity(world, blockPosValue, facing);
        if (entityHanging != null && entityHanging.onValidSurface()) {
            if (!world.isRemote) {
                world.spawnEntityInWorld(entityHanging);
            }

            itemStack.stackSize--;
        }

        return true;
    }

    private EntityHanging createEntity(World world, BlockPos blockPos, Facing facing) {
        return (EntityHanging)
                (this.hangingEntityClass == EntityPainting.class
                        ? new EntityPainting(world, blockPos, facing)
                        : (this.hangingEntityClass == EntityItemFrame.class
                                ? new EntityItemFrame(world, blockPos, facing)
                                : null));
    }

    public ItemHangingEntity(Class oclass) {
        this.hangingEntityClass = oclass;
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
}
