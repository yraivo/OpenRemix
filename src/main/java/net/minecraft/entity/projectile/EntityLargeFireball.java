package net.minecraft.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball {
    public int explosionPower = 1;

    public EntityLargeFireball(
            World worldValue, LivingEntity livingEntity, double d0, double d1, double d2) {
        super(worldValue, livingEntity, d0, d1, d2);
    }

    public EntityLargeFireball(World worldValue) {
        super(worldValue);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        if (compoundTag.hasKey("ExplosionPower", 99)) {
            this.explosionPower = compoundTag.getInteger("ExplosionPower");
        }
    }

    public EntityLargeFireball(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(worldValue, d0, d1, d2, d3, d4, d5);
    }


    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("ExplosionPower", this.explosionPower);
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        if (!this.world.isRemote) {
            if (rayTraceResult.entityHit != null) {
                rayTraceResult.entityHit.attackEntityFrom(
                        DamageSource.causeFireballDamage(this, this.shootingEntity), 6.0F);
                this.applyEnchantments(this.shootingEntity, rayTraceResult.entityHit);
            }

            boolean gameRuleBooleanValue =
                    this.world.getGameRules().getGameRuleBooleanValue("mobGriefing");
            this.world.newExplosion(
                    (Entity) null,
                    this.posX,
                    this.posY,
                    this.posZ,
                    this.explosionPower,
                    gameRuleBooleanValue,
                    gameRuleBooleanValue);
            this.setDead();
        }
    }
}
