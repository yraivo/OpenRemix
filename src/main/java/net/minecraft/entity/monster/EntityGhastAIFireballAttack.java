package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

class EntityGhastAIFireballAttack extends EntityAIBase {
    private EntityGhast parentEntity;
    EntityGhast bjP;
    public int attackTimer;

    public void startExecuting() {
        this.attackTimer = 0;
    }

    EntityGhastAIFireballAttack(EntityGhast bjPValue) {
        this.bjP = bjPValue;
        this.parentEntity = this.bjP;
    }

    public void resetTask() {
        this.parentEntity.setAttacking(false);
    }

    public void updateTask() {
        {
            LivingEntity livingEntity = this.parentEntity.getAttackTarget();
            int j = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
            double d0 = 64.0;
            int i = j;
            if (livingEntity.getDistanceSqToEntity(this.parentEntity) < d0 * d0
                    && this.parentEntity.canEntityBeSeen(livingEntity)) {
                World world = this.parentEntity.world;
                this.attackTimer++;
                if (this.attackTimer == 10) {
                    world.playAuxSFXAtEntity(
                            (PlayerEntity) null, 1007, new BlockPos(this.parentEntity), 0);
                }

                if (this.attackTimer == 20) {
                    double d1 = 4.0;
                    Vec3 vec3 = this.parentEntity.getLook(1.0F);
                    double d2 = livingEntity.posX - (this.parentEntity.posX + vec3.x * d1);
                    double d3 =
                            livingEntity.getEntityBoundingBox().minY
                                    + livingEntity.height / 2.0F
                                    - (0.5
                                            + this.parentEntity.posY
                                            + this.parentEntity.height / 2.0F);
                    double d4 = livingEntity.posZ - (this.parentEntity.posZ + vec3.z * d1);
                    world.playAuxSFXAtEntity(
                            (PlayerEntity) null, 1008, new BlockPos(this.parentEntity), 0);
                    EntityLargeFireball entityLargeFireball =
                            new EntityLargeFireball(world, this.parentEntity, d2, d3, d4);
                    entityLargeFireball.explosionPower = this.parentEntity.getFireballStrength();
                    entityLargeFireball.posX = this.parentEntity.posX + vec3.x * d1;
                    entityLargeFireball.posY =
                            this.parentEntity.posY + this.parentEntity.height / 2.0F + 0.5;
                    entityLargeFireball.posZ = this.parentEntity.posZ + vec3.z * d1;
                    world.spawnEntityInWorld(entityLargeFireball);
                    this.attackTimer = -40;
                }

                if (!(i == 0)) {

                    if (this.attackTimer > 0) {
                        this.attackTimer--;
                    }
                }
            } else {

                if (this.attackTimer > 0) {
                    this.attackTimer--;
                }
            }
        }

        this.parentEntity.setAttacking(this.attackTimer > 10);
    }

    public boolean shouldExecute() {
        return this.parentEntity.getAttackTarget() != null;
    }
}
