package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityTNTPrimed extends Entity {

    public int fuse;

    private LivingEntity tntPlacedBy;

    public EntityTNTPrimed(World worldValue) {
        super(worldValue);
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
    }

    protected void entityInit() {}

    protected void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setByte("Fuse", (byte) this.fuse);
    }


    public EntityTNTPrimed(
            World worldValue,
            double prevPosXValue,
            double prevPosYValue,
            double prevPosZValue,
            LivingEntity biuValue) {
        this(worldValue);
        this.setPosition(prevPosXValue, prevPosYValue, prevPosZValue);
        float f = (float) (Math.random() * Math.PI * 2.0);
        this.motionX = -((float) Math.sin(f)) * 0.02F;
        this.motionY = 0.2F;
        this.motionZ = -((float) Math.cos(f)) * 0.02F;
        this.fuse = 80;
        this.prevPosX = prevPosXValue;
        this.prevPosY = prevPosYValue;
        this.prevPosZ = prevPosZValue;
        this.tntPlacedBy = biuValue;
    }

    public LivingEntity getTntPlacedBy() {
        return this.tntPlacedBy;
    }

    private void explode() {
        float f = 4.0F;
        this.world.createExplosion(
                this, this.posX, this.posY + this.height / 2.0F, this.posZ, f, true);
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        this.fuse = compoundTag.getByte("Fuse");
    }

    public float getEyeHeight() {
        return 0.0F;
    }

    public void onUpdate() {
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.motionY -= 0.04F;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        boolean flag = flag1;
        this.motionX *= 0.98F;
        this.motionY *= 0.98F;
        this.motionZ *= 0.98F;
        if (this.onGround) {
            this.motionX *= 0.7F;
            this.motionZ *= 0.7F;
            this.motionY *= -0.5;
        }

        if (this.fuse-- <= 0) {
            this.setDead();
            if (this.world.isRemote) {
                return;
            }

            this.explode();
            if (!flag) {
                return;
            }
        }

        this.handleWaterMovement();
        this.world.spawnParticle(
                EnumParticleTypes.SMOKE_NORMAL,
                this.posX,
                this.posY + 0.5,
                this.posZ,
                0.0,
                0.0,
                0.0,
                new int[0]);
    }
}
