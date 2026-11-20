package net.minecraft.entity.item;

import net.minecraft.block.BlockRailBase;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class EntityMinecartTNT extends EntityMinecart {

    private int minecartTNTFuse = -1;

    public EntityMinecartEnumMinecartType getMinecartType() {
        return EntityMinecartEnumMinecartType.TNT;
    }

    public void ignite() {
        this.minecartTNTFuse = 80;
        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte) 10);
            if (!this.isSlient()) {
                this.world.playSoundAtEntity(this, "game.tnt.primed", 1.0F, 1.0F);
            }
        }
    }

    public EntityMinecartTNT(World worldValue) {
        super(worldValue);
    }

    public BlockState getDefaultDisplayTile() {
        return Blocks.tnt.getDefaultState();
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        Entity entity = damageSource.getSourceOfDamage();
        if (entity instanceof EntityArrow) {
            EntityArrow entityArrow = (EntityArrow) entity;
            if (entityArrow.isBurning()) {
                this.explodeCart(
                        entityArrow.motionX * entityArrow.motionX
                                + entityArrow.motionY * entityArrow.motionY
                                + entityArrow.motionZ * entityArrow.motionZ);
            }
        }

        return super.attackEntityFrom(damageSource, f);
    }

    public void onUpdate() {
        {
            boolean flag1 = EntityMinecartEnumMinecartType.d();
            super.onUpdate();
            boolean flag = flag1;
            if (this.minecartTNTFuse > 0) {
                this.minecartTNTFuse--;
                this.world.spawnParticle(
                        EnumParticleTypes.SMOKE_NORMAL,
                        this.posX,
                        this.posY + 0.5,
                        this.posZ,
                        0.0,
                        0.0,
                        0.0,
                        new int[0]);
                if (!(!flag)) {

                    if (this.minecartTNTFuse == 0) {
                        this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
                    }
                }
            } else {

                if (this.minecartTNTFuse == 0) {
                    this.explodeCart(this.motionX * this.motionX + this.motionZ * this.motionZ);
                }
            }
        }

        if (this.collidedHorizontally) {
            double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;
            if (d0 >= 0.01F) {
                this.explodeCart(d0);
            }
        }
    }


    protected void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("TNTFuse", this.minecartTNTFuse);
    }

    public void onActivatorRailPass(int i, int j, int k, boolean flag) {
        if (flag && this.minecartTNTFuse < 0) {
            this.ignite();
        }
    }

    public int getFuseTicks() {
        return this.minecartTNTFuse;
    }

    public float getExplosionResistance(
            Explosion explosion, World worldValue, BlockPos blockPos, BlockState blockState) {
        return !this.isIgnited()
                        || !BlockRailBase.isRailBlock(blockState)
                                && !BlockRailBase.isRailBlock(worldValue, blockPos.offsetUp())
                ? super.getExplosionResistance(explosion, worldValue, blockPos, blockState)
                : 0.0F;
    }

    public void fall(float f, float f1) {
        if (f >= 3.0F) {
            float f2 = f / 10.0F;
            this.explodeCart(f2 * f2);
        }

        super.fall(f, f1);
    }

    public void handleHealthUpdate(byte b0) {
        boolean flag = EntityMinecartEnumMinecartType.d();
        if (b0 == 10) {
            this.ignite();
            if (!flag) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    public boolean verifyExplosion(
            Explosion explosion,
            World worldValue,
            BlockPos blockPos,
            BlockState blockState,
            float f) {
        return !this.isIgnited()
                        || !BlockRailBase.isRailBlock(blockState)
                                && !BlockRailBase.isRailBlock(worldValue, blockPos.offsetUp())
                ? super.verifyExplosion(explosion, worldValue, blockPos, blockState, f)
                : false;
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        if (compoundTag.hasKey("TNTFuse", 99)) {
            this.minecartTNTFuse = compoundTag.getInteger("TNTFuse");
        }
    }

    public boolean isIgnited() {
        return this.minecartTNTFuse > -1;
    }

    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        double d0 = this.motionX * this.motionX + this.motionZ * this.motionZ;
        if (!damageSource.isExplosion()) {
            this.entityDropItem(new ItemStack(Blocks.tnt, 1), 0.0F);
        }

        if (damageSource.isFireDamage() || damageSource.isExplosion() || d0 >= 0.01F) {
            this.explodeCart(d0);
        }
    }

    public EntityMinecartTNT(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
    }

    protected void explodeCart(double d0) {
        if (!this.world.isRemote) {
            double d1 = Math.sqrt(d0);
            if (d1 > 5.0) {
                d1 = 5.0;
            }

            this.world.createExplosion(
                    this,
                    this.posX,
                    this.posY,
                    this.posZ,
                    (float) (4.0 + this.rand.nextDouble() * 1.5 * d1),
                    true);
            this.setDead();
        }
    }
}
