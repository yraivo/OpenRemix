package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class EntityEnderPearl extends EntityThrowable {
    private static String biE;

    public EntityEnderPearl(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
    }

    static {
        biE = "doMobSpawning";
    }

    public void onUpdate() {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        LivingEntity livingEntity = this.getThrower();
        boolean flag = flag1;
        if (livingEntity != null
                && livingEntity instanceof PlayerEntity
                && !livingEntity.isEntityAlive()) {
            this.setDead();
            if (flag) {
                return;
            }
        }

        super.onUpdate();
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        LivingEntity livingEntity = this.getThrower();
        boolean flag = flag1;
        if (rayTraceResult.entityHit != null) {
            rayTraceResult.entityHit.attackEntityFrom(
                    DamageSource.causeThrownDamage(this, livingEntity), 0.0F);
        }

        int i = 0;

        while (i < 32) {
            this.world.spawnParticle(
                    EnumParticleTypes.PORTAL,
                    this.posX,
                    this.posY + this.rand.nextDouble() * 2.0,
                    this.posZ,
                    this.rand.nextGaussian(),
                    0.0,
                    this.rand.nextGaussian(),
                    new int[0]);
            i++;
            if (flag) {
                break;
            }
        }

        if (!this.world.isRemote) {
            if (livingEntity instanceof EntityPlayerMP) {
                EntityPlayerMP entityPlayerMP = (EntityPlayerMP) livingEntity;
                if (entityPlayerMP.playerNetServerHandler.getNetworkManager().isChannelOpen()
                        && entityPlayerMP.world == this.world
                        && !entityPlayerMP.isPlayerSleeping()) {
                    if (this.rand.nextFloat() < 0.05F
                            && this.world.getGameRules().getGameRuleBooleanValue(biE)) {
                        EntityEndermite entityEndermite = new EntityEndermite(this.world);
                        entityEndermite.setSpawnedByPlayer(true);
                        entityEndermite.setLocationAndAngles(
                                livingEntity.posX,
                                livingEntity.posY,
                                livingEntity.posZ,
                                livingEntity.rotationYaw,
                                livingEntity.rotationPitch);
                        this.world.spawnEntityInWorld(entityEndermite);
                    }

                    if (livingEntity.isRiding()) {
                        livingEntity.mountEntity((Entity) null);
                    }

                    livingEntity.setPositionAndUpdate(this.posX, this.posY, this.posZ);
                    livingEntity.fallDistance = 0.0F;
                    livingEntity.attackEntityFrom(DamageSource.fall, 5.0F);
                }
            }

            this.setDead();
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public EntityEnderPearl(World worldValue, LivingEntity livingEntity) {
        super(worldValue, livingEntity);
    }
}
