package net.minecraft.world;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Explosion {
    private static String mC;

    private final Random explosionRNG = new Random();
    private float explosionSize;
    private World worldObj;
    private Map playerKnockbackMap;
    private boolean isSmoking;
    private final List affectedBlockPositions = Lists.newArrayList();
    private double explosionY;
    private double explosionX;
    private double explosionZ;
    private boolean isFlaming;
    private Entity exploder;

    public void clearAffectedBlockPositions() {
        this.affectedBlockPositions.clear();
    }

    public void doExplosionB(boolean flag) {
        int[] aint;
        {
            int[] aint1 = World.getLegacyWorldFlowMarker();
            this.worldObj.playSoundEffect(
                    this.explosionX,
                    this.explosionY,
                    this.explosionZ,
                    mC,
                    4.0F,
                    (1.0F
                                    + (this.worldObj.rand.nextFloat()
                                                    - this.worldObj.rand.nextFloat())
                                            * 0.2F)
                            * 0.7F);
            aint = aint1;
            if (this.explosionSize >= 2.0F && this.isSmoking) {
                this.worldObj.spawnParticle(
                        EnumParticleTypes.EXPLOSION_HUGE,
                        this.explosionX,
                        this.explosionY,
                        this.explosionZ,
                        1.0,
                        0.0,
                        0.0,
                        new int[0]);

                
            } else {

                this.worldObj.spawnParticle(
                        EnumParticleTypes.EXPLOSION_LARGE,
                        this.explosionX,
                        this.explosionY,
                        this.explosionZ,
                        1.0,
                        0.0,
                        0.0,
                        new int[0]);
            }
        }

        if (this.isSmoking) {
            for (java.util.Iterator<?> __recoveryIterator171_2192 =
                            (this.affectedBlockPositions).iterator();
                    (__recoveryIterator171_2192).hasNext(); ) {
                BlockPos blockPos = (BlockPos) (__recoveryIterator171_2192).next();
                Block block = this.worldObj.getBlockState(blockPos).getBlock();
                if (flag) {
                    double d0 = blockPos.getX() + this.worldObj.rand.nextFloat();
                    double d1 = blockPos.getY() + this.worldObj.rand.nextFloat();
                    double d2 = blockPos.getZ() + this.worldObj.rand.nextFloat();
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5 / (d6 / this.explosionSize + 0.1);
                    d7 *= this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F;
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.worldObj.spawnParticle(
                            EnumParticleTypes.EXPLOSION_NORMAL,
                            (d0 + this.explosionX * 1.0) / 2.0,
                            (d1 + this.explosionY * 1.0) / 2.0,
                            (d2 + this.explosionZ * 1.0) / 2.0,
                            d3,
                            d4,
                            d5,
                            new int[0]);
                    this.worldObj.spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
                }

                if (block.getMaterial() != Material.AIR) {
                    if (block.canDropFromExplosion(this)) {
                        block.dropBlockAsItemWithChance(
                                this.worldObj,
                                blockPos,
                                this.worldObj.getBlockState(blockPos),
                                1.0F / this.explosionSize,
                                0);
                    }

                    this.worldObj.setBlockState(blockPos, Blocks.air.getDefaultState(), 3);
                    block.onBlockDestroyedByExplosion(this.worldObj, blockPos, this);
                }
            }
        }

        if (this.isFlaming) {
            for (java.util.Iterator<?> __recoveryIterator171_4507 =
                            (this.affectedBlockPositions).iterator();
                    (__recoveryIterator171_4507).hasNext(); ) {
                BlockPos blockPosValue = (BlockPos) (__recoveryIterator171_4507).next();
                if (this.worldObj.getBlockState(blockPosValue).getBlock().getMaterial()
                                == Material.AIR
                        && this.worldObj
                                .getBlockState(blockPosValue.offsetDown())
                                .getBlock()
                                .isFullBlock()
                        && this.explosionRNG.nextInt(3) == 0) {
                    this.worldObj.setBlockState(blockPosValue, Blocks.fire.getDefaultState());
                }
            }
        }
    }

    // region String decoding support

    // endregion

    public Map getPlayerKnockbackMap() {
        return this.playerKnockbackMap;
    }

    public Explosion(
            World bhwValue,
            Entity bFJValue,
            double explosionXValue,
            double explosionYValue,
            double explosionZValue,
            float explosionSizeValue,
            boolean isFlamingValue,
            boolean isSmokingValue) {
        this.playerKnockbackMap = Maps.newHashMap();
        this.worldObj = bhwValue;
        this.exploder = bFJValue;
        this.explosionSize = explosionSizeValue;
        this.explosionX = explosionXValue;
        this.explosionY = explosionYValue;
        this.explosionZ = explosionZValue;
        this.isFlaming = isFlamingValue;
        this.isSmoking = isSmokingValue;
    }

    public void doExplosionA() {
        HashSet hashset = Sets.newHashSet();

        World.getLegacyWorldFlowMarker();

        int k = 0;

        while (k < 16) {
            int i = 0;

            while (true) {
                if (i < 16) {
                    int j = 0;

                    while (j < 16) {
                        if (k == 0 || k == 15 || i == 0 || i == 15 || j == 0 || j == 15) {
                            double d0 = k / 15.0F * 2.0F - 1.0F;
                            double d1 = i / 15.0F * 2.0F - 1.0F;
                            double d2 = j / 15.0F * 2.0F - 1.0F;
                            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                            d0 /= d3;
                            d1 /= d3;
                            d2 /= d3;
                            float f =
                                    this.explosionSize
                                            * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                            double explosionXValue = this.explosionX;
                            double explosionYValue = this.explosionY;
                            double explosionZValue = this.explosionZ;

                            while (f > 0.0F) {
                                BlockPos blockPos =
                                        new BlockPos(
                                                explosionXValue, explosionYValue, explosionZValue);
                                BlockState blockState = this.worldObj.getBlockState(blockPos);
                                if (blockState.getBlock().getMaterial() != Material.AIR) {
                                    float f2 =
                                            this.exploder != null
                                                    ? this.exploder.getExplosionResistance(
                                                            this,
                                                            this.worldObj,
                                                            blockPos,
                                                            blockState)
                                                    : blockState
                                                            .getBlock()
                                                            .getExplosionResistance((Entity) null);
                                    f -= (f2 + 0.3F) * 0.3F;
                                }

                                if (f > 0.0F
                                        && (this.exploder == null
                                                || this.exploder.verifyExplosion(
                                                        this,
                                                        this.worldObj,
                                                        blockPos,
                                                        blockState,
                                                        f))) {
                                    hashset.add(blockPos);
                                }

                                explosionXValue += d0 * 0.3F;
                                explosionYValue += d1 * 0.3F;
                                explosionZValue += d2 * 0.3F;
                                f -= 0.22500001F;
                            }
                        }

                        j++;
                    }

                    i++;

                    continue;
                }

                k++;

                break;
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        float f3 = this.explosionSize * 2.0F;
        int j1 = MathHelper.floor(this.explosionX - f3 - 1.0);
        int k1 = MathHelper.floor(this.explosionX + f3 + 1.0);
        int l1 = MathHelper.floor(this.explosionY - f3 - 1.0);
        int l = MathHelper.floor(this.explosionY + f3 + 1.0);
        int i2 = MathHelper.floor(this.explosionZ - f3 - 1.0);
        int i1 = MathHelper.floor(this.explosionZ + f3 + 1.0);
        List entitiesWithinAABBExcludingEntity =
                this.worldObj.getEntitiesWithinAABBExcludingEntity(
                        this.exploder, new BoundingBox(j1, l1, i2, k1, l, i1));
        Vec3 vec3 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);
        int j2 = 0;

        while (j2 < entitiesWithinAABBExcludingEntity.size()) {
            Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(j2);
            if (!entity.isImmuneToExplosions()) {
                double d12 =
                        entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / f3;
                if (d12 <= 1.0) {
                    double d5 = entity.posX - this.explosionX;
                    double d7 = entity.posY + entity.getEyeHeight() - this.explosionY;
                    double d9 = entity.posZ - this.explosionZ;
                    double d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                    if (d13 != 0.0) {
                        d5 /= d13;
                        d7 /= d13;
                        d9 /= d13;
                        double d14 =
                                this.worldObj.getBlockDensity(
                                        vec3, entity.getEntityBoundingBox());
                        double d10 = (1.0 - d12) * d14;
                        entity.attackEntityFrom(
                                DamageSource.setExplosionSource(this),
                                (int) ((d10 * d10 + d10) / 2.0 * 8.0 * f3 + 1.0));
                        double d11 = EnchantmentProtection.method_f0e3644d9b9839060196(entity, d10);
                        entity.motionX += d5 * d11;
                        entity.motionY += d7 * d11;
                        entity.motionZ += d9 * d11;
                        if (entity instanceof PlayerEntity) {
                            this.playerKnockbackMap.put(
                                    (PlayerEntity) entity, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                        }
                    }
                }
            }

            j2++;
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            World.setLegacyWorldFlowMarker(new int[2]);
        }
    }

    public LivingEntity getExplosivePlacedBy() {
        return this.exploder == null
                ? null
                : (this.exploder instanceof EntityTNTPrimed
                        ? ((EntityTNTPrimed) this.exploder).getTntPlacedBy()
                        : (this.exploder instanceof LivingEntity
                                ? (LivingEntity) this.exploder
                                : null));
    }

    public Explosion(
            World world, Entity entity, double d0, double d1, double d2, float f, List list) {
        this(world, entity, d0, d1, d2, f, false, true, list);
    }

    static {
        mC = "random.explode";
    }

    // region String decoding support

    // endregion

    public Explosion(
            World world,
            Entity entity,
            double d0,
            double d1,
            double d2,
            float f,
            boolean flag,
            boolean flag1,
            List list) {
        this(world, entity, d0, d1, d2, f, flag, flag1);
        this.affectedBlockPositions.addAll(list);
    }

    public List getAffectedBlockPositions() {
        return this.affectedBlockPositions;
    }
}
