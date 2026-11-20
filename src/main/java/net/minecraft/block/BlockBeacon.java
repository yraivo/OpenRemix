package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class BlockBeacon extends BlockContainer {
    public int getRenderType() {
        return 3;
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float p171_5,
            float p171_6,
            float p171_7) {
        if (world.isRemote) {
            return true;
        }

        net.minecraft.tileentity.TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof net.minecraft.tileentity.TileEntityBeacon) {
            playerEntity.displayGUIChest((net.minecraft.tileentity.TileEntityBeacon) tileEntity);
        }

        return true;
    }

    public BlockBeacon() {
        super(Material.glass);
        this.setHardness(3.0F);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        net.minecraft.tileentity.TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof net.minecraft.tileentity.TileEntityBeacon) {
            ((net.minecraft.tileentity.TileEntityBeacon) tileEntity).updateBeacon();
            world.addBlockEvent(blockPos, this, 1, 0);
        }
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.CUTOUT;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityBeacon();
    }

    public static void updateColorAsync(World world, BlockPos blockPos) {
        net.minecraft.util.HttpUtil.field_180193_a.submit(new net.minecraft.block.BeaconColumnRefreshTask(world, blockPos));
    }

    public boolean isFullCube() {
        return false;
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        super.onBlockPlacedBy(world, blockPos, blockState, livingEntity, itemStack);
        if (itemStack.hasDisplayName()) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityBeacon) {
                ((TileEntityBeacon) tileEntity).setName(itemStack.getDisplayName());
            }
        }
    }

    public boolean isOpaqueCube() {
        return false;
    }
}
