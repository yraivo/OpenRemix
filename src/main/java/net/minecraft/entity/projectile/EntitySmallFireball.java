package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class EntitySmallFireball extends EntityFireball {
    private static String biE;

    // region String decoding support

    // endregion

    public EntitySmallFireball(World worldValue) {
        super(worldValue);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntitySmallFireball(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(worldValue, d0, d1, d2, d3, d4, d5);
        this.setSize(0.3125F, 0.3125F);
    }

    public boolean canBeCollidedWith() {
        return false;
    }

    public EntitySmallFireball(
            World worldValue, LivingEntity livingEntity, double d0, double d1, double d2) {
        super(worldValue, livingEntity, d0, d1, d2);
        this.setSize(0.3125F, 0.3125F);
    }

    static {
        biE = "mobGriefing";
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return false;
    }

    protected void onImpact(RayTraceResult rayTraceResult) {
        TileEntity.lz();

        if (!this.world.isRemote) {
            {
                if (rayTraceResult.entityHit != null) {
                    boolean flag =
                            rayTraceResult.entityHit.attackEntityFrom(
                                    DamageSource.causeFireballDamage(this, this.shootingEntity),
                                    5.0F);
                    if (!(!flag)) {

                        this.applyEnchantments(this.shootingEntity, rayTraceResult.entityHit);
                        if (!(rayTraceResult.entityHit.isImmuneToFire())) {

                            rayTraceResult.entityHit.setFire(5);
                        }
                    }
                } else {

                    byte b0 = 1;
                    if (this.shootingEntity != null
                            && this.shootingEntity instanceof EntityLiving) {
                        b0 =
                                remix.compat.compiler.JvmBooleanSlots.toByte(
                                        this.world.getGameRules().getGameRuleBooleanValue(biE));
                    }

                    if (b0 != 0) {
                        BlockPos blockPos =
                                rayTraceResult.getBlockPos().offset(rayTraceResult.sideHit);
                        if (this.world.isAirBlock(blockPos)) {
                            this.world.setBlockState(blockPos, Blocks.fire.getDefaultState());
                        }
                    }
                }
            }

            this.setDead();
        }
    }

    // region String decoding support

    // endregion
}
