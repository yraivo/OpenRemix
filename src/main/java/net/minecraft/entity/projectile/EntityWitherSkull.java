package net.minecraft.entity.projectile;

import net.minecraft.block.state.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityWitherSkull extends EntityFireball {
    private static String biE;

    public boolean canBeCollidedWith() {
        return false;
    }

    protected void entityInit() {
        this.dataWatcher.addObject(10, (byte) 0);
    }

    // region String decoding support

    // endregion

    public boolean isInvulnerable() {
        return this.dataWatcher.getWatchableObjectByte(10) == 1;
    }

    public boolean isBurning() {
        return false;
    }

    public EntityWitherSkull(World worldValue) {
        super(worldValue);
        this.setSize(0.3125F, 0.3125F);
    }

    // region String decoding support

    // endregion

    protected void onImpact(RayTraceResult rayTraceResult) {
        TileEntity.lz();

        if (!this.world.isRemote) {
            if (rayTraceResult.entityHit != null) {
                {
                    if (this.shootingEntity != null) {
                        if (!(!rayTraceResult.entityHit.attackEntityFrom(
                                DamageSource.causeMobDamage(this.shootingEntity), 8.0F))) {

                            if (!rayTraceResult.entityHit.isEntityAlive()) {
                                this.shootingEntity.heal(5.0F);


                            } else {

                                this.applyEnchantments(
                                        this.shootingEntity, rayTraceResult.entityHit);


                            }
                        }
                    } else {

                        rayTraceResult.entityHit.attackEntityFrom(DamageSource.magic, 5.0F);
                    }
                }

                if (rayTraceResult.entityHit instanceof LivingEntity) {
                    byte b0;
                    {
                        b0 = 0;
                        if (this.world.getDifficulty() == EnumDifficulty.NORMAL) {
                            b0 = 10;

                            
                        } else {

                            if (this.world.getDifficulty() == EnumDifficulty.HARD) {
                                b0 = 40;
                            }
                        }
                    }

                    if (b0 > 0) {
                        ((LivingEntity) rayTraceResult.entityHit)
                                .addPotionEffect(new PotionEffect(Potion.WITHER.id, 20 * b0, 1));
                    }
                }
            }

            this.world.newExplosion(
                    this,
                    this.posX,
                    this.posY,
                    this.posZ,
                    1.0F,
                    false,
                    this.world.getGameRules().getGameRuleBooleanValue(biE));
            this.setDead();
        }
    }

    public float getExplosionResistance(
            Explosion explosion, World worldValue, BlockPos blockPos, BlockState blockState) {
        float explosionResistance =
                super.getExplosionResistance(explosion, worldValue, blockPos, blockState);
        if (this.isInvulnerable()
                && blockState.getBlock() != Blocks.bedrock
                && blockState.getBlock() != Blocks.end_portal
                && blockState.getBlock() != Blocks.end_portal_frame
                && blockState.getBlock() != Blocks.command_block) {
            explosionResistance = Math.min(0.8F, explosionResistance);
        }

        return explosionResistance;
    }

    public EntityWitherSkull(
            World worldValue, double d0, double d1, double d2, double d3, double d4, double d5) {
        super(worldValue, d0, d1, d2, d3, d4, d5);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityWitherSkull(
            World worldValue, LivingEntity livingEntity, double d0, double d1, double d2) {
        super(worldValue, livingEntity, d0, d1, d2);
        this.setSize(0.3125F, 0.3125F);
    }

    public void setInvulnerable(boolean flag) {
        this.dataWatcher.updateObject(10, (byte) (flag ? 1 : 0));
    }

    protected float getMotionFactor() {
        return this.isInvulnerable() ? 0.73F : super.getMotionFactor();
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return false;
    }

    static {
        biE = "mobGriefing";
    }
}
