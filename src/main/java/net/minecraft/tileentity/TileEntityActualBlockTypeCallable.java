package net.minecraft.tileentity;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

import java.util.concurrent.Callable;

public class TileEntityActualBlockTypeCallable implements Callable {

    TileEntity bBS;

    public TileEntityActualBlockTypeCallable(TileEntity bBSValue) {
        this.bBS = bBSValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }


    public String getCrashDetail() {
        int idFromBlock =
                Block.getIdFromBlock(this.bBS.worldObj.getBlockState(this.bBS.pos).getBlock());

        try {
            return String.format(
                    "ID #%d (%s // %s)",
                    idFromBlock,
                    Block.getBlockById(idFromBlock).getUnlocalizedName(),
                    Block.getBlockById(idFromBlock).getClass().getCanonicalName());
        } catch (Throwable throwable) {
            return "ID #" + idFromBlock;
        }
    }
}
