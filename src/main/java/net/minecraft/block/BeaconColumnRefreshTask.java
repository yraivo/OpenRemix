package net.minecraft.block;

import net.minecraft.block.state.BlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

final class BeaconColumnRefreshTask implements Runnable {
    World field_180358_a;
    BlockPos field_180357_b;

    BeaconColumnRefreshTask(World world, BlockPos blockPos) {
        this.field_180358_a = world;
        this.field_180357_b = blockPos;
    }

    @Override
    public void run() {
        int k = BlockPlanksEnumType.getInvertedBlockFlowToken();
        Chunk chunkFromBlockCoords =
                this.field_180358_a.getChunkFromBlockCoords(this.field_180357_b);
        int i = k;
        int j = this.field_180357_b.getY() - 1;

        while (j >= 0) {
            BlockPos blockPos =
                    new BlockPos(this.field_180357_b.getX(), j, this.field_180357_b.getZ());
            if (!chunkFromBlockCoords.canSeeSky(blockPos) && i == 0) {
                break;
            }

            BlockState blockState = this.field_180358_a.getBlockState(blockPos);
            if (blockState.getBlock() == Blocks.beacon) {
                WorldServer receiver171 = (WorldServer) this.field_180358_a;
                final World world = this.field_180358_a;
                receiver171.addScheduledTask(() -> {
                    TileEntity tileEntity = world.getTileEntity(blockPos);
                    if (tileEntity instanceof TileEntityBeacon) {
                        ((TileEntityBeacon) tileEntity).updateBeacon();
                        world.addBlockEvent(blockPos, Blocks.beacon, 1, 0);
                    }
                });
            }

            j--;
            if (i != 0) {
                break;
            }
        }
    }
}
