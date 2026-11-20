package net.minecraft.tileentity;

import net.minecraft.tileentity.TileEntity;

import java.util.concurrent.Callable;

public class TileEntityNameCallable implements Callable {
    private static String b;

    TileEntity bBS;

    public String getCrashDetail() {
        return (String) TileEntity.access$000().get(this.bBS.getClass())
                + b
                + this.bBS.getClass().getCanonicalName();
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    // region String decoding support

    // endregion

    static {
        b = " // ";
    }

    public TileEntityNameCallable(TileEntity bBSValue) {
        this.bBS = bBSValue;
    }

    // region String decoding support

    // endregion
}
