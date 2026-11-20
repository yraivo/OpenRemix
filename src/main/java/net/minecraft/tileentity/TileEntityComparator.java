package net.minecraft.tileentity;

import net.minecraft.nbt.CompoundTag;

public class TileEntityComparator extends TileEntity {

    private int outputSignal;

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.outputSignal = compoundTag.getInteger("OutputSignal");
    }

    public int getOutputSignal() {
        return this.outputSignal;
    }


    public void setOutputSignal(int outputSignalValue) {
        this.outputSignal = outputSignalValue;
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.setInteger("OutputSignal", this.outputSignal);
    }
}
