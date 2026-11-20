package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block implements ITileEntityProvider {
    private static Entity[] ahe;

    public static void b(Entity[] aheValue) {
        ahe = aheValue;
    }

    public int getRenderType() {
        return -1;
    }

    static {
        if (fa() == null) {
            b(new Entity[4]);
        }
    }

    protected BlockContainer(Material material) {
        super(material);
        this.isBlockContainer = true;
    }

    public boolean onBlockEventReceived(
            World world, BlockPos blockPos, BlockState blockState, int i, int j) {
        super.onBlockEventReceived(world, blockPos, blockState, i, j);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity != null && tileEntity.receiveClientEvent(i, j);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {
        super.breakBlock(world, blockPos, blockState);
        world.removeTileEntity(blockPos);
    }

    public static Entity[] fa() {
        return ahe;
    }
}
