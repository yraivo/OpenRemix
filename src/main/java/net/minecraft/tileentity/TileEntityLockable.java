package net.minecraft.tileentity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.LockCode;

public abstract class TileEntityLockable extends TileEntity
        implements IInteractionObject, ILockableContainer {
    private LockCode code = LockCode.EMPTY_CODE;

    public LockCode getLockCode() {
        return this.code;
    }

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.hasCustomName()
                        ? new ChatComponentText(this.getName())
                        : new ChatComponentTranslation(this.getName(), new Object[0]));
    }

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.code = net.minecraft.world.LockCode.fromNBT(compoundTag);
    }

    public boolean isLocked() {
        return this.code != null && !this.code.isEmpty();
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        if (this.code != null) {
            this.code.toNBT(compoundTag);
        }
    }

    public void setLockCode(LockCode bBnValue) {
        this.code = bBnValue;
    }
}
