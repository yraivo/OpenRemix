package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.world.World;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.movement.NoSlowDownModule;

public class BlockSoulSand extends Block {
    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        entity.motionX *= 0.4;
        entity.motionZ *= 0.4;
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        NoSlowDownModule noSlowDown = ClientServices.moduleManager.noSlowDown;
        if (noSlowDown.isEnabled() && noSlowDown.soulsandSetting.asBoolean()) {
            entity.motionX *= 2.5;
            entity.motionZ *= 2.5;
        }
    }

    public BlockSoulSand() {
        super(Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        float f = 0.125F;
        return new BoundingBox(
                blockPos.getX(),
                blockPos.getY(),
                blockPos.getZ(),
                blockPos.getX() + 1,
                blockPos.getY() + 1 - f,
                blockPos.getZ() + 1);
    }
}
