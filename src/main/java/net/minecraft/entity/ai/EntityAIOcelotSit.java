package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockBedEnumPartType;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIOcelotSit extends EntityAIMoveToBlock {
    private EntityOcelot bkk;

    public void startExecuting() {
        super.startExecuting();
        this.bkk.getAISit().setSitting(false);
    }

    public void updateTask() {
        String text = EntityAIBase.cJ();
        super.updateTask();
        String textValue = text;
        this.bkk.getAISit().setSitting(false);
        if (!this.getIsAboveDestination()) {
            this.bkk.setSitting(false);
            if (textValue != null) {
                return;
            }
        }

        if (!this.bkk.isSitting()) {
            this.bkk.setSitting(true);
        }
    }

    public boolean continueExecuting() {
        return super.continueExecuting();
    }

    public boolean shouldExecute() {
        return this.bkk.isTamed() && !this.bkk.isSitting() && super.shouldExecute();
    }

    public EntityAIOcelotSit(EntityOcelot bkkValue, double d0) {
        super(bkkValue, d0, 8);
        this.bkk = bkkValue;
    }

    protected boolean shouldMoveTo(World world, BlockPos blockPos) {
        String text = EntityAIBase.cJ();
        if (!world.isAirBlock(blockPos.offsetUp())) {
            return false;
        }

        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block == Blocks.chest) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityChest
                    && ((TileEntityChest) tileEntity).numPlayersUsing < 1) {
                return true;
            }

            if (text != null) {
                return false;
            }
        }

        return block == Blocks.lit_furnace
                ? true
                : block == Blocks.bed
                        && blockState.getValue(BlockBed.PART_PROP) != BlockBedEnumPartType.HEAD;
    }

    public void resetTask() {
        super.resetTask();
        this.bkk.setSitting(false);
    }
}
