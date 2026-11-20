package net.minecraft.entity.item;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityXPOrb extends Entity {
    private int xpOrbHealth = 5;
    public int xpColor;

    public int delayBeforeCanPickup;

    public int xpOrbAge;
    private PlayerEntity closestPlayer;
    private int xpTargetColor;
    private int xpValue;


    public void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setShort("Health", (byte) this.xpOrbHealth);
        compoundTag.setShort("Age", (short) this.xpOrbAge);
        compoundTag.setShort("Value", (short) this.xpValue);
    }

    public int getTextureByXP() {
        return this.xpValue >= 2477
                ? 10
                : (this.xpValue >= 1237
                        ? 9
                        : (this.xpValue >= 617
                                ? 8
                                : (this.xpValue >= 307
                                        ? 7
                                        : (this.xpValue >= 149
                                                ? 6
                                                : (this.xpValue >= 73
                                                        ? 5
                                                        : (this.xpValue >= 37
                                                                ? 4
                                                                : (this.xpValue >= 17
                                                                        ? 3
                                                                        : (this.xpValue >= 7
                                                                                ? 2
                                                                                : (this.xpValue >= 3
                                                                                        ? 1
                                                                                        : 0)))))))));
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.delayBeforeCanPickup > 0) {
            this.delayBeforeCanPickup--;
        }

        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.03F;
        if (this.world.getBlockState(new BlockPos(this)).getBlock().getMaterial()
                == Material.lava) {
            this.motionY = 0.2F;
            this.motionX = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.motionZ = (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F;
            this.playSound("random.fizz", 0.4F, 2.0F + this.rand.nextFloat() * 0.4F);
        }

        this.pushOutOfBlocks(
                this.posX,
                (this.getEntityBoundingBox().minY + this.getEntityBoundingBox().maxY) / 2.0,
                this.posZ);
        double d0 = 8.0;
        if (this.xpTargetColor < this.xpColor - 20 + this.getEntityId() % 100) {
            if (this.closestPlayer == null
                    || this.closestPlayer.getDistanceSqToEntity(this) > d0 * d0) {
                this.closestPlayer = this.world.getClosestPlayerToEntity(this, d0);
            }

            this.xpTargetColor = this.xpColor;
        }

        if (this.closestPlayer != null && this.closestPlayer.isSpectator()) {
            this.closestPlayer = null;
        }

        if (this.closestPlayer != null) {
            double d1 = (this.closestPlayer.posX - this.posX) / d0;
            double d2 =
                    (this.closestPlayer.posY + this.closestPlayer.getEyeHeight() - this.posY) / d0;
            double d3 = (this.closestPlayer.posZ - this.posZ) / d0;
            double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
            double d5 = 1.0 - d4;
            if (d5 > 0.0) {
                d5 *= d5;
                this.motionX += d1 / d4 * d5 * 0.1;
                this.motionY += d2 / d4 * d5 * 0.1;
                this.motionZ += d3 / d4 * d5 * 0.1;
            }
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = 0.98F;
        if (this.onGround) {
            f =
                    this.world
                                    .getBlockState(
                                            new BlockPos(
                                                    MathHelper.floor(this.posX),
                                                    MathHelper.floor(
                                                                    this.getEntityBoundingBox()
                                                                            .minY)
                                                            - 1,
                                                    MathHelper.floor(this.posZ)))
                                    .getBlock()
                                    .slipperiness
                            * 0.98F;
        }

        this.motionX *= f;
        this.motionY *= 0.98F;
        this.motionZ *= f;
        if (this.onGround) {
            this.motionY *= -0.9F;
        }

        this.xpColor++;
        this.xpOrbAge++;
        if (this.xpOrbAge >= 6000) {
            this.setDead();
        }
    }

    public boolean attackEntityFrom(DamageSource damageSource, float p171_1) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        this.setBeenAttacked();
        this.xpOrbHealth = (int) (this.xpOrbHealth - p171_1);
        if (this.xpOrbHealth <= 0) {
            this.setDead();
        }

        return false;
    }

    public static int method_c640c0ab3979b883b99e(int p171_0) {
        return p171_0 >= 2477
                ? 2477
                : (p171_0 >= 1237
                        ? 1237
                        : (p171_0 >= 617
                                ? 617
                                : (p171_0 >= 307
                                        ? 307
                                        : (p171_0 >= 149
                                                ? 149
                                                : (p171_0 >= 73
                                                        ? 73
                                                        : (p171_0 >= 37
                                                                ? 37
                                                                : (p171_0 >= 17
                                                                        ? 17
                                                                        : (p171_0 >= 7
                                                                                ? 7
                                                                                : (p171_0 >= 3
                                                                                        ? 3
                                                                                        : 1)))))))));
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        this.xpOrbHealth = compoundTag.getShort("Health") & 255;
        this.xpOrbAge = compoundTag.getShort("Age");
        this.xpValue = compoundTag.getShort("Value");
    }

    public void onCollideWithPlayer(PlayerEntity playerEntity) {
        if (!this.world.isRemote && this.delayBeforeCanPickup == 0 && playerEntity.xpCooldown == 0) {
            playerEntity.xpCooldown = 2;
            this.world.playSoundAtEntity(
                    playerEntity,
                    "random.orb",
                    0.1F,
                    0.5F * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.8F));
            playerEntity.onItemPickup(this, 1);
            playerEntity.addExperience(this.xpValue);
            this.setDead();
        }
    }

    public int getXpValue() {
        return this.xpValue;
    }

    public EntityXPOrb(World worldValue) {
        super(worldValue);
        this.setSize(0.25F, 0.25F);
    }

    public boolean canAttackWithItem() {
        return false;
    }

    protected void entityInit() {}

    public int getBrightnessForRender(float f) {
        float f1 = 0.5F;
        f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
        int brightnessForRender = super.getBrightnessForRender(f);
        int j = brightnessForRender & 0xFF;
        int k = brightnessForRender >> 16 & 0xFF;
        j += (int) (f1 * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    public EntityXPOrb(
            World worldValue, double p171_1, double p171_2, double p171_3, int aLpValue) {
        super(worldValue);
        this.setSize(0.5F, 0.5F);
        this.setPosition(p171_1, p171_2, p171_3);
        this.rotationYaw = (float) (java.lang.Math.random() * 360.0);
        this.motionX = (float) (java.lang.Math.random() * 0.2F - 0.1F) * 2.0F;
        this.motionY = (float) (java.lang.Math.random() * 0.2) * 2.0F;
        this.motionZ = (float) (java.lang.Math.random() * 0.2F - 0.1F) * 2.0F;
        this.xpValue = aLpValue;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean handleWaterMovement() {
        return this.world.handleMaterialAcceleration(
                this.getEntityBoundingBox(), Material.water, this);
    }

    protected void dealFireDamage(int p171_0) {
        this.attackEntityFrom(net.minecraft.util.DamageSource.inFire, p171_0);
    }
}
