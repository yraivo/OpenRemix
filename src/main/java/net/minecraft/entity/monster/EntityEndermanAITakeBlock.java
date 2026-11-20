package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

class EntityEndermanAITakeBlock extends EntityAIBase {

    private EntityEnderman enderman;
    private static String e;
    EntityEnderman bjM;

    static {
        e = "mobGriefing";
    }

    public boolean shouldExecute() {
        return !this.enderman.world.getGameRules().getGameRuleBooleanValue(e)
                ? false
                : (this.enderman.getHeldBlockState().getBlock().getMaterial() != Material.AIR
                        ? false
                        : this.enderman.getRNG().nextInt(20) == 0);
    }

    // region String decoding support

    // endregion

    EntityEndermanAITakeBlock(EntityEnderman bjMValue) {
        this.bjM = bjMValue;
        this.enderman = this.bjM;
    }

    public void updateTask() {
        Random random = this.enderman.getRNG();
        World world = this.enderman.world;
        int i = MathHelper.floor(this.enderman.posX - 2.0 + random.nextDouble() * 4.0);
        int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 3.0);
        int k = MathHelper.floor(this.enderman.posZ - 2.0 + random.nextDouble() * 4.0);
        BlockPos blockPos = new BlockPos(i, j, k);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (EntityEnderman.access$300().contains(block)) {
            this.enderman.setHeldBlockState(blockState);
            world.setBlockState(blockPos, Blocks.air.getDefaultState());
        }
    }

    // region String decoding support

    // endregion
}
