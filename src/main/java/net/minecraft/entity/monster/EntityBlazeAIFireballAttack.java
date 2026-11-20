package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

class EntityBlazeAIFireballAttack extends EntityAIBase {
    private int field_179467_b;
    EntityBlaze bjI;
    private int field_179468_c;
    private EntityBlaze blaze;

    public boolean shouldExecute() {
        LivingEntity livingEntity = this.blaze.getAttackTarget();
        return livingEntity != null && livingEntity.isEntityAlive();
    }

    public void startExecuting() {
        this.field_179467_b = 0;
    }

    public EntityBlazeAIFireballAttack(EntityBlaze bjIValue) {
        this.bjI = bjIValue;
        this.blaze = this.bjI;
        this.setMutexBits(3);
    }

    public void updateTask() {
        label53:
        {
            int k = EntitySpiderGroupData.method_7eec03da329a017ec143();
            this.field_179468_c--;
            LivingEntity livingEntity = this.blaze.getAttackTarget();
            int i = k;
            double distanceSqToEntity = this.blaze.getDistanceSqToEntity(livingEntity);
            if (distanceSqToEntity < 4.0) {
                if (this.field_179468_c <= 0) {
                    this.field_179468_c = 20;
                    this.blaze.attackEntityAsMob(livingEntity);
                }

                this.blaze
                        .getMoveHelper()
                        .setMoveTo(livingEntity.posX, livingEntity.posY, livingEntity.posZ, 1.0);
                if (i != 0) {
                    break label53;
                }
            }

            if (distanceSqToEntity < 256.0) {
                double deltaPosX = livingEntity.posX - this.blaze.posX;
                double d2 =
                        livingEntity.getEntityBoundingBox().minY
                                + livingEntity.height / 2.0F
                                - (this.blaze.posY + this.blaze.height / 2.0F);
                double deltaPosZ = livingEntity.posZ - this.blaze.posZ;
                if (this.field_179468_c <= 0) {
                    label55:
                    {
                        this.field_179467_b++;
                        if (this.field_179467_b == 1) {
                            this.field_179468_c = 60;
                            this.blaze.setOnFire(true);
                            if (i != 0) {
                                break label55;
                            }
                        }

                        if (this.field_179467_b <= 4) {
                            this.field_179468_c = 6;
                            if (i != 0) {
                                break label55;
                            }
                        }

                        this.field_179468_c = 100;
                        this.field_179467_b = 0;
                        this.blaze.setOnFire(false);
                    }

                    if (this.field_179467_b > 1) {
                        float f = MathHelper.sqrt(MathHelper.sqrt(distanceSqToEntity)) * 0.5F;
                        this.blaze.world.playAuxSFXAtEntity(
                                (PlayerEntity) null,
                                1009,
                                new BlockPos(
                                        (int) this.blaze.posX,
                                        (int) this.blaze.posY,
                                        (int) this.blaze.posZ),
                                0);
                        int j = 0;

                        while (j < 1) {
                            EntitySmallFireball entitySmallFireball =
                                    new EntitySmallFireball(
                                            this.blaze.world,
                                            this.blaze,
                                            deltaPosX + this.blaze.getRNG().nextGaussian() * f,
                                            d2,
                                            deltaPosZ + this.blaze.getRNG().nextGaussian() * f);
                            entitySmallFireball.posY =
                                    this.blaze.posY + this.blaze.height / 2.0F + 0.5;
                            this.blaze.world.spawnEntityInWorld(entitySmallFireball);
                            j++;
                            if (i == 0) {
                                break;
                            }
                        }
                    }
                }

                this.blaze.getLookHelper().setLookPositionWithEntity(livingEntity, 10.0F, 10.0F);
                if (i != 0) {
                    break label53;
                }
            }

            this.blaze.getNavigator().clearPathEntity();
            this.blaze
                    .getMoveHelper()
                    .setMoveTo(livingEntity.posX, livingEntity.posY, livingEntity.posZ, 1.0);
        }

        super.updateTask();
    }

    public void resetTask() {
        this.blaze.setOnFire(false);
    }
}
