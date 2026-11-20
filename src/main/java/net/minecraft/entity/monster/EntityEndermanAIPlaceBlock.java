package net.minecraft.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

class EntityEndermanAIPlaceBlock extends EntityAIBase {
    private EntityEnderman enderman;

    private static String e;
    EntityEnderman bjM;

    EntityEndermanAIPlaceBlock(EntityEnderman bjMValue) {
        this.bjM = bjMValue;
        this.enderman = this.bjM;
    }

    // region String decoding support

    // endregion

    private boolean func_179474_a(
            World world, BlockPos blockPos, Block block, Block blockValue, Block blockValue2) {
        return !block.canPlaceBlockAt(world, blockPos)
                ? false
                : (blockValue.getMaterial() != Material.AIR
                        ? false
                        : (blockValue2.getMaterial() == Material.AIR
                                ? false
                                : blockValue2.isFullCube()));
    }

    public boolean shouldExecute() {
        return !this.enderman.world.getGameRules().getGameRuleBooleanValue(e)
                ? false
                : (this.enderman.getHeldBlockState().getBlock().getMaterial() == Material.AIR
                        ? false
                        : this.enderman.getRNG().nextInt(2000) == 0);
    }

    // region String decoding support

    // endregion

    public void updateTask() {
        Random random = this.enderman.getRNG();
        World world = this.enderman.world;
        int i = MathHelper.floor(this.enderman.posX - 1.0 + random.nextDouble() * 2.0);
        int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 2.0);
        int k = MathHelper.floor(this.enderman.posZ - 1.0 + random.nextDouble() * 2.0);
        BlockPos blockPos = new BlockPos(i, j, k);
        Block block = world.getBlockState(blockPos).getBlock();
        Block blockValue = world.getBlockState(blockPos.offsetDown()).getBlock();
        if (this.func_179474_a(
                world, blockPos, this.enderman.getHeldBlockState().getBlock(), block, blockValue)) {
            world.setBlockState(blockPos, this.enderman.getHeldBlockState(), 3);
            this.enderman.setHeldBlockState(Blocks.air.getDefaultState());
        }
    }

    static {
        e = "mobGriefing";
    }
}
