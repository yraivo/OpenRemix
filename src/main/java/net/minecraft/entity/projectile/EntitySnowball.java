package net.minecraft.entity.projectile;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class EntitySnowball extends EntityThrowable {
    public EntitySnowball(World worldValue) {
        super(worldValue);
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        TileEntity.lz();

        if (rayTraceResult.entityHit != null) {
            byte b0 = 0;
            if (rayTraceResult.entityHit instanceof EntityBlaze) {
                b0 = 3;
            }

            rayTraceResult.entityHit.attackEntityFrom(
                    DamageSource.causeThrownDamage(this, this.getThrower()), b0);
        }

        int i = 0;

        while (i < 8) {
            this.world.spawnParticle(
                    EnumParticleTypes.SNOWBALL,
                    this.posX,
                    this.posY,
                    this.posZ,
                    0.0,
                    0.0,
                    0.0,
                    new int[0]);
            i++;
        }

        if (!this.world.isRemote) {
            this.setDead();
        }
    }

    public EntitySnowball(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
    }

    public EntitySnowball(World worldValue, LivingEntity livingEntity) {
        super(worldValue, livingEntity);
    }
}
