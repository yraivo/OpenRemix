package net.minecraft.entity.ai;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTallGrassEnumType;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class EntityAIEatGrass extends EntityAIBase {
    int eatingGrassTimer;
    private static Predicate field_179505_b;
    private World entityWorld;

    private EntityLiving grassEaterEntity;

    static {
        net.minecraft.entity.ai.EntityAIEatGrass.field_179505_b =
                BlockStateHelper.forBlock(Blocks.tallgrass)
                        .where(
                                BlockTallGrass.TYPE,
                                Predicates.equalTo(BlockTallGrassEnumType.GRASS));
    }

    public void startExecuting() {
        this.eatingGrassTimer = 40;
        this.entityWorld.setEntityState(this.grassEaterEntity, (byte) 10);
        this.grassEaterEntity.getNavigator().clearPathEntity();
    }

    public boolean continueExecuting() {
        return this.eatingGrassTimer > 0;
    }

    public int getEatingGrassTimer() {
        return this.eatingGrassTimer;
    }

    public EntityAIEatGrass(EntityLiving bheValue) {
        this.grassEaterEntity = bheValue;
        this.entityWorld = bheValue.world;
        this.setMutexBits(7);
    }

    public void updateTask() {
        String text = EntityAIBase.cJ();
        this.eatingGrassTimer = Math.max(0, this.eatingGrassTimer - 1);
        String textValue = text;
        if (this.eatingGrassTimer == 4) {
            BlockPos blockPos =
                    new BlockPos(
                            this.grassEaterEntity.posX,
                            this.grassEaterEntity.posY,
                            this.grassEaterEntity.posZ);
            if (net.minecraft.entity.ai.EntityAIEatGrass.field_179505_b.apply(
                    this.entityWorld.getBlockState(blockPos))) {
                if (this.entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.entityWorld.destroyBlock(blockPos, false);
                }

                this.grassEaterEntity.eatGrassBonus();
                if (textValue != null) {
                    return;
                }
            }

            BlockPos blockPosValue = blockPos.offsetDown();
            if (this.entityWorld.getBlockState(blockPosValue).getBlock() == Blocks.grass) {
                if (this.entityWorld.getGameRules().getGameRuleBooleanValue("mobGriefing")) {
                    this.entityWorld.playAuxSFX(
                            2001, blockPosValue, Block.getIdFromBlock(Blocks.grass));
                    this.entityWorld.setBlockState(blockPosValue, Blocks.dirt.getDefaultState(), 2);
                }

                this.grassEaterEntity.eatGrassBonus();
            }
        }
    }

    public void resetTask() {
        this.eatingGrassTimer = 0;
    }

    public boolean shouldExecute() {
        if (this.grassEaterEntity.getRNG().nextInt(this.grassEaterEntity.isChild() ? 50 : 1000)
                != 0) {
            return false;
        }

        BlockPos blockPos =
                new BlockPos(
                        this.grassEaterEntity.posX,
                        this.grassEaterEntity.posY,
                        this.grassEaterEntity.posZ);
        return net.minecraft.entity.ai.EntityAIEatGrass.field_179505_b.apply(this.entityWorld.getBlockState(blockPos))
                ? true
                : this.entityWorld.getBlockState(blockPos.offsetDown()).getBlock() == Blocks.grass;
    }
}
