package net.minecraft.tileentity;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;

import java.util.List;

public class TileEntityPiston extends TileEntity implements IUpdatePlayerListBox {

    private List field_174933_k = Lists.newArrayList();
    private BlockState pistonState;

    private boolean shouldHeadBeRendered;
    private float progress;
    private float lastProgress;

    private boolean extending;
    private Facing pistonFacing;

    public void update() {
        MobSpawnerBaseLogic.GU();

        this.lastProgress = this.progress;

        if (this.lastProgress >= 1.0F) {
            this.launchWithSlimeBlock(1.0F, 0.25F);
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();
            if (this.worldObj.getBlockState(this.pos).getBlock() != Blocks.piston_extension) {
                return;
            }

            this.worldObj.setBlockState(this.pos, this.pistonState, 3);
            this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());

            return;
        }

        this.progress += 0.5F;
        if (this.progress >= 1.0F) {
            this.progress = 1.0F;
        }

        if (this.extending) {
            this.launchWithSlimeBlock(this.progress, this.progress - this.lastProgress + 0.0625F);
        }
    }

    public BlockState getPistonState() {
        return this.pistonState;
    }

    public boolean shouldPistonHeadBeRendered() {
        return this.shouldHeadBeRendered;
    }

    public TileEntityPiston() {}

    public float getOffsetX(float f) {
        return this.extending
                ? (this.getProgress(f) - 1.0F) * this.pistonFacing.getFrontOffsetX()
                : (1.0F - this.getProgress(f)) * this.pistonFacing.getFrontOffsetX();
    }

    public Facing getFacing() {
        return this.pistonFacing;
    }

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.pistonState =
                Block.getBlockById(compoundTag.getInteger("blockId"))
                        .getStateFromMeta(compoundTag.getInteger("blockData"));
        this.pistonFacing = Facing.getFront(compoundTag.getInteger("facing"));
        this.lastProgress = this.progress = compoundTag.getFloat("progress");
        this.extending = compoundTag.getBoolean("extending");
    }

    public float getOffsetZ(float f) {
        return this.extending
                ? (this.getProgress(f) - 1.0F) * this.pistonFacing.getFrontOffsetZ()
                : (1.0F - this.getProgress(f)) * this.pistonFacing.getFrontOffsetZ();
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.setInteger("blockId", Block.getIdFromBlock(this.pistonState.getBlock()));
        compoundTag.setInteger(
                "blockData", this.pistonState.getBlock().getMetaFromState(this.pistonState));
        compoundTag.setInteger("facing", this.pistonFacing.getIndex());
        compoundTag.setFloat("progress", this.lastProgress);
        compoundTag.setBoolean("extending", this.extending);
    }

    public float getProgress(float f) {
        if (f > 1.0F) {
            f = 1.0F;
        }

        return this.lastProgress + (this.progress - this.lastProgress) * f;
    }

    public void clearPistonTileEntity() {
        if (this.lastProgress < 1.0F && this.worldObj != null) {
            this.lastProgress = this.progress = 1.0F;
            this.worldObj.removeTileEntity(this.pos);
            this.invalidate();
            if (this.worldObj.getBlockState(this.pos).getBlock() == Blocks.piston_extension) {
                this.worldObj.setBlockState(this.pos, this.pistonState, 3);
                this.worldObj.notifyBlockOfStateChange(this.pos, this.pistonState.getBlock());
            }
        }
    }

    private void launchWithSlimeBlock(float f, float f1) {
        Entity[] entityArray;
        {
            entityArray = MobSpawnerBaseLogic.GU();
            if (this.extending) {
                f = 1.0F - f;

            } else {

                f--;
            }
        }

        BoundingBox boundingBox =
                Blocks.piston_extension.getBoundingBox(
                        this.worldObj, this.pos, this.pistonState, f, this.pistonFacing);
        if (boundingBox != null) {
            List entitiesWithinAABBExcludingEntity =
                    this.worldObj.getEntitiesWithinAABBExcludingEntity((Entity) null, boundingBox);
            if (!entitiesWithinAABBExcludingEntity.isEmpty()) {
                this.field_174933_k.addAll(entitiesWithinAABBExcludingEntity);

                for (java.util.Iterator<?> __recoveryIterator171_9071 =
                                (this.field_174933_k).iterator();
                        (__recoveryIterator171_9071).hasNext(); ) {
                    Entity entity = (Entity) (__recoveryIterator171_9071).next();
                    {
                        if (this.pistonState.getBlock() == Blocks.slime_block && this.extending) {
                            switch (this.pistonFacing.getAxis()) {
                                case X:
                                    entity.motionX = this.pistonFacing.getFrontOffsetX();

                                    break;

                                case Y:
                                    entity.motionY = this.pistonFacing.getFrontOffsetY();

                                    break;

                                case Z:
                                    entity.motionZ = this.pistonFacing.getFrontOffsetZ();
                            }

                        } else {

                            entity.moveEntity(
                                    f1 * this.pistonFacing.getFrontOffsetX(),
                                    f1 * this.pistonFacing.getFrontOffsetY(),
                                    f1 * this.pistonFacing.getFrontOffsetZ());
                        }
                    }
                }

                this.field_174933_k.clear();
            }
        }
    }

    public float getOffsetY(float f) {
        return this.extending
                ? (this.getProgress(f) - 1.0F) * this.pistonFacing.getFrontOffsetY()
                : (1.0F - this.getProgress(f)) * this.pistonFacing.getFrontOffsetY();
    }


    public int getBlockMetadata() {
        return 0;
    }

    public TileEntityPiston(
            BlockState bBJValue,
            Facing bBLValue,
            boolean extendingValue,
            boolean shouldHeadBeRenderedValue) {
        this.pistonState = bBJValue;
        this.pistonFacing = bBLValue;
        this.extending = extendingValue;
        this.shouldHeadBeRendered = shouldHeadBeRenderedValue;
    }

    public boolean isExtending() {
        return this.extending;
    }
}
