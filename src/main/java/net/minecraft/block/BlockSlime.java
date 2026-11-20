package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.movement.TerrainModule;

public class BlockSlime extends BlockBreakable {
    public void onLanded(World world, Entity entity) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (entity.isSneaking()) {
            super.onLanded(world, entity);
            if (i == 0) {
                return;
            }
        }

        if (entity.motionY < 0.0) {
            ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
            TerrainModule terrain = ClientServices.moduleManager.terrain;
            entity.motionY =
                    -entity.motionY
                            * (terrain.isEnabled() && terrain.slimeFlySetting.asBoolean()
                                    ? 1.5
                                    : 1.0);
        }
    }

    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity) {
        if (java.lang.Math.abs(entity.motionY) < 0.1 && !entity.isSneaking()) {
            double d0 = 0.4 + java.lang.Math.abs(entity.motionY) * 0.2;
            entity.motionX *= d0;
            entity.motionZ *= d0;
        }

        super.onEntityCollidedWithBlock(world, blockPos, entity);
    }

    public BlockSlime() {
        super(Material.clay, false);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.slipperiness = 0.8F;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.TRANSLUCENT;
    }

    public void onFallenUpon(World world, BlockPos blockPos, Entity entity, float f) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (entity.isSneaking()) {
            super.onFallenUpon(world, blockPos, entity, f);
            if (i == 0) {
                return;
            }
        }

        entity.fall(f, 0.0F);
    }
}
