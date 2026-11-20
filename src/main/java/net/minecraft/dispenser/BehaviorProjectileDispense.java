package net.minecraft.dispenser;

import net.minecraft.block.BlockDispenser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public abstract class BehaviorProjectileDispense extends BehaviorDefaultDispenseItem {
    protected float aN() {
        return 1.1F;
    }

    public ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        BehaviorDefaultDispenseItem.method_da2ac5c94686ee11e9e6();
        World world = iBlockSource.getWorld();
        IPosition dispensePosition = BlockDispenser.getDispensePosition(iBlockSource);
        Facing facing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
        IProjectile iProjectile = this.getProjectileEntity(world, dispensePosition);
        iProjectile.setThrowableHeading(
                facing.getFrontOffsetX(),
                facing.getFrontOffsetY() + 0.1F,
                facing.getFrontOffsetZ(),
                this.aN(),
                this.aK());
        world.spawnEntityInWorld((Entity) iProjectile);
        itemStack.splitStack(1);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            BehaviorDefaultDispenseItem.b(new Entity[4]);
        }

        return itemStack;
    }

    protected void playDispenseSound(IBlockSource iBlockSource) {
        iBlockSource.getWorld().playAuxSFX(1002, iBlockSource.getBlockPos(), 0);
    }

    protected float aK() {
        return 6.0F;
    }

    protected abstract IProjectile getProjectileEntity(World world, IPosition iPosition);
}
