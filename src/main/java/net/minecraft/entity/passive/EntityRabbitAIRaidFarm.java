package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCarrot;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.ai.EntityAIMoveToBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

class EntityRabbitAIRaidFarm extends EntityAIMoveToBlock {
    private static String kE;
    private boolean field_179499_e;
    EntityRabbit field_179500_c;

    private boolean field_179498_d;

    public void resetTask() {
        super.resetTask();
    }

    public void startExecuting() {
        super.startExecuting();
    }

    // region String decoding support

    // endregion

    public void updateTask() {
        super.updateTask();
        this.field_179500_c
                .getLookHelper()
                .setLookPosition(
                        this.destinationBlock.getX() + 0.5,
                        this.destinationBlock.getY() + 1,
                        this.destinationBlock.getZ() + 0.5,
                        10.0F,
                        this.field_179500_c.getVerticalFaceSpeed());
        if (this.getIsAboveDestination()) {
            World world = this.field_179500_c.world;
            BlockPos blockPos = this.destinationBlock.offsetUp();
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (this.field_179499_e
                    && block instanceof BlockCarrot
                    && (Integer) blockState.getValue(BlockCarrot.AGE) == 7) {
                world.setBlockState(blockPos, Blocks.air.getDefaultState(), 2);
                world.destroyBlock(blockPos, true);
                this.field_179500_c.createEatingParticles();
            }

            this.field_179499_e = false;
            this.runDelay = 10;
        }
    }

    protected boolean shouldMoveTo(World world, BlockPos blockPos) {
        Block block = world.getBlockState(blockPos).getBlock();
        if (block == Blocks.farmland) {
            blockPos = blockPos.offsetUp();
            BlockState blockState = world.getBlockState(blockPos);
            block = blockState.getBlock();
            if (block instanceof BlockCarrot
                    && (Integer) blockState.getValue(BlockCarrot.AGE) == 7
                    && this.field_179498_d
                    && !this.field_179499_e) {
                this.field_179499_e = true;
                return true;
            }
        }

        return false;
    }

    public boolean shouldExecute() {
        if (this.runDelay <= 0) {
            if (!this.field_179500_c.world.getGameRules().getGameRuleBooleanValue(kE)) {
                return false;
            }

            this.field_179499_e = false;
            this.field_179498_d = EntityRabbit.access$000(this.field_179500_c);
        }

        return super.shouldExecute();
    }

    public EntityRabbitAIRaidFarm(EntityRabbit bibValue) {
        super(bibValue, 0.7F, 16);
        this.field_179500_c = bibValue;
        this.field_179499_e = false;
    }

    static {
        kE = "mobGriefing";
    }

    public boolean continueExecuting() {
        return this.field_179499_e && super.continueExecuting();
    }

    // region String decoding support

    // endregion
}
