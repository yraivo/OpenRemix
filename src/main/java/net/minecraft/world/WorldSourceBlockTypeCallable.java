package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import java.util.concurrent.Callable;

public class WorldSourceBlockTypeCallable implements Callable {

    Block aXz;

    World awf;

    public WorldSourceBlockTypeCallable(World awfValue, Block aXzValue) {
        this.awf = awfValue;
        this.aXz = aXzValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        try {
            return String.format(
                    "ID #%d (%s // %s)",
                    Block.getIdFromBlock(this.aXz),
                    this.aXz.getUnlocalizedName(),
                    this.aXz.getClass().getCanonicalName());
        } catch (Throwable throwable) {
            return "ID #" + Block.getIdFromBlock(this.aXz);
        }
    }


}
