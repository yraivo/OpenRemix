package net.minecraft.tileentity;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TileEntityNote extends TileEntity {

    public byte note;
    public boolean previousRedstoneState;

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.note = compoundTag.getByte("note");
        this.note = (byte) MathHelper.clamp(this.note, 0, 24);
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.setByte("note", this.note);
    }

    public void changePitch() {
        this.note = (byte) ((this.note + 1) % 25);
        this.markDirty();
    }

    public void triggerNote(World world, BlockPos blockPos) {
        if (world.getBlockState(blockPos.offsetUp()).getBlock().getMaterial() == Material.AIR) {
            Material material = world.getBlockState(blockPos.offsetDown()).getBlock().getMaterial();
            byte b0 = 0;
            if (material == Material.rock) {
                b0 = 1;
            }

            if (material == Material.sand) {
                b0 = 2;
            }

            if (material == Material.glass) {
                b0 = 3;
            }

            if (material == Material.wood) {
                b0 = 4;
            }

            world.addBlockEvent(blockPos, Blocks.noteblock, b0, this.note);
        }
    }


}
