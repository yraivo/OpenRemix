package net.minecraft.entity.monster;

import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockSilverfishEnumType;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

class EntitySilverfishAIHideInStone extends EntityAIWander {
    private Facing facing;
    private boolean field_179484_c;
    EntitySilverfish field_179485_a;

    public boolean shouldExecute() {
        if (this.field_179485_a.getAttackTarget() != null) {
            return false;
        }

        if (!this.field_179485_a.getNavigator().noPath()) {
            return false;
        }

        Random random = this.field_179485_a.getRNG();
        if (random.nextInt(10) == 0) {
            this.facing = Facing.random(random);
            BlockPos blockPos =
                    new BlockPos(
                                    this.field_179485_a.posX,
                                    this.field_179485_a.posY + 0.5,
                                    this.field_179485_a.posZ)
                            .offset(this.facing);
            BlockState blockState = this.field_179485_a.world.getBlockState(blockPos);
            if (BlockSilverfish.canContainSilverfish(blockState)) {
                this.field_179484_c = true;
                return true;
            }
        }

        this.field_179484_c = false;
        return super.shouldExecute();
    }

    public EntitySilverfishAIHideInStone(EntitySilverfish bjEValue) {
        super(bjEValue, 1.0, 10);
        this.field_179485_a = bjEValue;
        this.setMutexBits(1);
    }

    public boolean continueExecuting() {
        return this.field_179484_c ? false : super.continueExecuting();
    }

    public void startExecuting() {
        int i = EntitySpiderGroupData.method_7eec03da329a017ec143();
        if (!this.field_179484_c) {
            super.startExecuting();
            if (i != 0) {
                return;
            }
        }

        World world = this.field_179485_a.world;
        BlockPos blockPos =
                new BlockPos(
                                this.field_179485_a.posX,
                                this.field_179485_a.posY + 0.5,
                                this.field_179485_a.posZ)
                        .offset(this.facing);
        BlockState blockState = world.getBlockState(blockPos);
        if (BlockSilverfish.canContainSilverfish(blockState)) {
            world.setBlockState(
                    blockPos,
                    Blocks.monster_egg
                            .getDefaultState()
                            .withProperty(
                                    BlockSilverfish.VARIANT_PROP,
                                    BlockSilverfishEnumType.forModelBlock(
                                            blockState)),
                    3);
            this.field_179485_a.spawnExplosionParticle();
            this.field_179485_a.setDead();
        }
    }
}
