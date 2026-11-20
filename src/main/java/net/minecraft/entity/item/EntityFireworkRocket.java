package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkRocket extends Entity {

    private int fireworkAge;

    private int lifetime;

    public void setVelocity(double motionXValue, double motionYValue, double motionZValue) {
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt(motionXValue * motionXValue + motionZValue * motionZValue);
            this.prevRotationYaw =
                    this.rotationYaw =
                            (float) (Math.atan2(motionXValue, motionZValue) * 180.0 / Math.PI);
            this.prevRotationPitch =
                    this.rotationPitch = (float) (Math.atan2(motionYValue, f) * 180.0 / Math.PI);
        }
    }

    public boolean isInRangeToRenderDist(double d0) {
        return d0 < 4096.0;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setInteger("Life", this.fireworkAge);
        compoundTag.setInteger("LifeTime", this.lifetime);
        ItemStack itemStack = this.dataWatcher.getWatchableObjectItemStack(8);
        if (itemStack != null) {
            CompoundTag compoundTagValue = new CompoundTag();
            itemStack.writeToNBT(compoundTagValue);
            compoundTag.setTag("FireworksItem", compoundTagValue);
        }
    }

    public float getBrightness(float f) {
        return super.getBrightness(f);
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        this.fireworkAge = compoundTag.getInteger("Life");
        this.lifetime = compoundTag.getInteger("LifeTime");
        CompoundTag compoundTagValue = compoundTag.getCompoundTag("FireworksItem");
        if (compoundTagValue != null) {
            ItemStack itemStack = ItemStack.loadItemStackFromNBT(compoundTagValue);
            if (itemStack != null) {
                this.dataWatcher.updateObject(8, itemStack);
            }
        }
    }

    public EntityFireworkRocket(World worldValue) {
        super(worldValue);
        this.setSize(0.25F, 0.25F);
    }

    public EntityFireworkRocket(
            World worldValue, double d0, double d1, double d2, ItemStack itemStack) {
        super(worldValue);
        this.fireworkAge = 0;
        this.setSize(0.25F, 0.25F);
        this.setPosition(d0, d1, d2);
        byte b0 = 1;
        if (itemStack != null && itemStack.hasTagCompound()) {
            this.dataWatcher.updateObject(8, itemStack);
            CompoundTag tag = itemStack.getTag();
            CompoundTag compoundTag = tag.getCompoundTag("Fireworks");
            if (compoundTag != null) {
                b0 += compoundTag.getByte("Flight");
            }
        }

        this.motionX = this.rand.nextGaussian() * 0.001;
        this.motionZ = this.rand.nextGaussian() * 0.001;
        this.motionY = 0.05;
        this.lifetime = 10 * b0 + this.rand.nextInt(6) + this.rand.nextInt(7);
    }


    public int getBrightnessForRender(float f) {
        return super.getBrightnessForRender(f);
    }

    public void onUpdate() {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();
        this.motionX *= 1.15;
        this.motionZ *= 1.15;
        this.motionY += 0.04;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0 / Math.PI);
        boolean flag = flag1;
        this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0 / Math.PI);

        while (this.rotationPitch - this.prevRotationPitch < -180.0F) {
            this.prevRotationPitch -= 360.0F;
            if (!flag) {
                break;
            }
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
            if (!flag) {
                break;
            }
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
            if (!flag) {
                break;
            }
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
            if (!flag) {
                break;
            }
        }

        this.rotationPitch =
                this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        if (this.fireworkAge == 0 && !this.isSlient()) {
            this.world.playSoundAtEntity(this, "fireworks.launch", 3.0F, 1.0F);
        }

        this.fireworkAge++;
        if (this.world.isRemote && this.fireworkAge % 2 < 2) {
            this.world.spawnParticle(
                    EnumParticleTypes.FIREWORKS_SPARK,
                    this.posX,
                    this.posY - 0.3,
                    this.posZ,
                    this.rand.nextGaussian() * 0.05,
                    -this.motionY * 0.5,
                    this.rand.nextGaussian() * 0.05,
                    new int[0]);
        }

        if (!this.world.isRemote && this.fireworkAge > this.lifetime) {
            this.world.setEntityState(this, (byte) 17);
            this.setDead();
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            EntityMinecartEnumMinecartType.method_a8745b073fa8adfa1857(!flag);
        }
    }

    protected void entityInit() {
        this.dataWatcher.addObjectByDataType(8, 5);
    }

    public boolean canAttackWithItem() {
        return false;
    }

    public void handleHealthUpdate(byte b0) {
        if (b0 == 17 && this.world.isRemote) {
            ItemStack itemStack = this.dataWatcher.getWatchableObjectItemStack(8);
            CompoundTag compoundTag = null;
            if (itemStack != null && itemStack.hasTagCompound()) {
                compoundTag = itemStack.getTag().getCompoundTag("Fireworks");
            }

            this.world.makeFireworks(
                    this.posX,
                    this.posY,
                    this.posZ,
                    this.motionX,
                    this.motionY,
                    this.motionZ,
                    compoundTag);
        }

        super.handleHealthUpdate(b0);
    }
}
