package net.minecraft.entity.monster;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnowman extends EntityGolem implements IRangedAttackMob {
    private static String bgJ;

    public EntitySnowman(World worldValue) {
        super(worldValue);
        this.setSize(0.7F, 1.9F);
        ((PathNavigateGround) this.getNavigator()).setAvoidsWater(true);
        this.tasks.addTask(1, new EntityAIArrowAttack(this, 1.25, 20, 10.0F));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(
                1,
                new EntityAINearestAttackableTarget(
                        this, EntityLiving.class, 10, true, false, IMob.mobSelector));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F);
    }

    // region String decoding support

    // endregion

    public void attackEntityWithRangedAttack(LivingEntity livingEntity, float f1) {
        EntitySnowball entitySnowball = new EntitySnowball(this.world, this);
        double d0 = livingEntity.posY + livingEntity.getEyeHeight() - 1.1F;
        double deltaPosX = livingEntity.posX - this.posX;
        double d2 = d0 - entitySnowball.posY;
        double deltaPosZ = livingEntity.posZ - this.posZ;
        float f = MathHelper.sqrt(deltaPosX * deltaPosX + deltaPosZ * deltaPosZ) * 0.2F;
        entitySnowball.setThrowableHeading(deltaPosX, d2 + f, deltaPosZ, 1.6F, 12.0F);
        this.playSound(bgJ, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntityInWorld(entitySnowball);
    }

    public float getEyeHeight() {
        return 1.7F;
    }

    protected void dropFewItems(boolean flag, int i1) {
        int j = this.rand.nextInt(16);
        int l = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        int k = 0;
        int i = l;

        while (k < j) {
            this.dropItem(Items.snowball, 1);
            k++;
            if (i != 0) {
                break;
            }
        }
    }

    public void onLivingUpdate() {
        int j1 = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        super.onLivingUpdate();
        int i = j1;
        if (!this.world.isRemote) {
            int j = MathHelper.floor(this.posX);
            int k = MathHelper.floor(this.posY);
            int l = MathHelper.floor(this.posZ);
            if (this.isWet()) {
                this.attackEntityFrom(DamageSource.drown, 1.0F);
            }

            if (this.world
                            .getBiomeGenForCoords(new BlockPos(j, 0, l))
                            .getFloatTemperature(new BlockPos(j, k, l))
                    > 1.0F) {
                this.attackEntityFrom(DamageSource.onFire, 1.0F);
            }

            int i1 = 0;

            while (i1 < 4) {
                j = MathHelper.floor(this.posX + (i1 % 2 * 2 - 1) * 0.25F);
                k = MathHelper.floor(this.posY);
                l = MathHelper.floor(this.posZ + (i1 / 2 % 2 * 2 - 1) * 0.25F);
                if (this.world.getBlockState(new BlockPos(j, k, l)).getBlock().getMaterial()
                                == Material.AIR
                        && this.world
                                        .getBiomeGenForCoords(new BlockPos(j, 0, l))
                                        .getFloatTemperature(new BlockPos(j, k, l))
                                < 0.8F
                        && Blocks.snow_layer.canPlaceBlockAt(this.world, new BlockPos(j, k, l))) {
                    this.world.setBlockState(
                            new BlockPos(j, k, l), Blocks.snow_layer.getDefaultState());
                }

                i1++;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    protected Item getDropItem() {
        return Items.snowball;
    }

    static {
        bgJ = "random.bow";
    }
}
