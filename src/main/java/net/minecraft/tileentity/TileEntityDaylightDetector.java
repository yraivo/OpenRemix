package net.minecraft.tileentity;

import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityDaylightDetector extends TileEntity implements IUpdatePlayerListBox {
    public void update() {
        if (this.worldObj != null && !this.worldObj.isRemote) {
            long totalWorldTime = this.worldObj.getTotalWorldTime();

            totalWorldTime %= 20L;

            if (totalWorldTime == 0L) {
                this.blockType = this.getBlockType();
                if (this.blockType instanceof BlockDaylightDetector) {
                    ((BlockDaylightDetector) this.blockType)
                            .updatePower(this.worldObj, this.pos);
                }
            }
        }
    }
}
