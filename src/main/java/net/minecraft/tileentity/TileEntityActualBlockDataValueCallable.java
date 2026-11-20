package net.minecraft.tileentity;

import net.minecraft.block.state.BlockState;
import net.minecraft.tileentity.TileEntity;

import java.util.concurrent.Callable;

public class TileEntityActualBlockDataValueCallable implements Callable {

    private static String[] packedTextFragments;
    TileEntity bBS;

    public String getCrashDetail() {
        BlockState blockState = this.bBS.worldObj.getBlockState(this.bBS.pos);
        int metaFromState = blockState.getBlock().getMetaFromState(blockState);
        if (metaFromState < 0) {
            return "Unknown? (Got " + metaFromState + packedTextFragments[0];
        }

        String text =
                String.format("%4s", Integer.toBinaryString(metaFromState))
                        .replace(packedTextFragments[1], packedTextFragments[2]);
        return String.format("%1$d / 0x%1$X / 0b%2$s", metaFromState, text);
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public TileEntityActualBlockDataValueCallable(TileEntity bBSValue) {
        this.bBS = bBSValue;
    }

    static {
        net.minecraft.tileentity.TileEntityActualBlockDataValueCallable.packedTextFragments = new String[4];
        net.minecraft.tileentity.TileEntityActualBlockDataValueCallable.packedTextFragments[0] = new String(")");
        net.minecraft.tileentity.TileEntityActualBlockDataValueCallable.packedTextFragments[1] = new String(" ");
        net.minecraft.tileentity.TileEntityActualBlockDataValueCallable.packedTextFragments[2] = new String("0");
    }
}
