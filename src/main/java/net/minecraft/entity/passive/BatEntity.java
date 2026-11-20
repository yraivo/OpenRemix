package net.minecraft.entity.passive;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Calendar;

public class BatEntity extends EntityAmbientCreature {

    private BlockPos spawnPosition;

    protected float Jr() {
        return super.Jr() * 0.95F;
    }

    public boolean getCanSpawnHere() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        BlockPos blockPos = new BlockPos(this.posX, this.getEntityBoundingBox().minY, this.posZ);
        String textValue = text;
        if (blockPos.getY() >= 63) {
            return false;
        }

        int lightFromNeighbors = this.world.getLightFromNeighbors(blockPos);
        byte b0 = 4;
        if (this.isDateAroundHalloween(this.world.getCurrentDate())) {
            b0 = 7;
            if (textValue == null) {
                return lightFromNeighbors > this.rand.nextInt(b0) ? false : super.getCanSpawnHere();
            }
        }

        if (this.rand.nextBoolean()) {
            return false;
        } else {
            return lightFromNeighbors > this.rand.nextInt(b0) ? false : super.getCanSpawnHere();
        }
    }

    protected String getLivingSound() {
        return this.getIsBatHanging() && this.rand.nextInt(4) != 0 ? null : "mob.bat.idle";
    }

    public boolean getIsBatHanging() {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1) != 0;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setByte("BatFlags", this.dataWatcher.getWatchableObjectByte(16));
    }

    protected void updateAITasks() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        super.updateAITasks();
        BlockPos blockPos = new BlockPos(this);
        String textValue = text;
        BlockPos blockPosValue = blockPos.offsetUp();
        if (this.getIsBatHanging()) {
            if (!this.world.getBlockState(blockPosValue).getBlock().isNormalCube()) {
                this.setIsBatHanging(false);
                this.world.playAuxSFXAtEntity((PlayerEntity) null, 1015, blockPos, 0);
                if (textValue == null) {
                    return;
                }
            }

            if (this.rand.nextInt(200) == 0) {
                this.rotationYawHead = this.rand.nextInt(360);
            }

            if (this.world.getClosestPlayerToEntity(this, 4.0) == null) {
                return;
            }

            this.setIsBatHanging(false);
            this.world.playAuxSFXAtEntity((PlayerEntity) null, 1015, blockPos, 0);
            if (textValue == null) {
                return;
            }
        }

        if (this.spawnPosition != null
                && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1)) {
            this.spawnPosition = null;
        }

        if (this.spawnPosition == null
                || this.rand.nextInt(30) == 0
                || this.spawnPosition.distanceSq((int) this.posX, (int) this.posY, (int) this.posZ)
                        < 4.0) {
            this.spawnPosition =
                    new BlockPos(
                            (int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7),
                            (int) this.posY + this.rand.nextInt(6) - 2,
                            (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
        }

        double d0 = this.spawnPosition.getX() + 0.5 - this.posX;
        double d1 = this.spawnPosition.getY() + 0.1 - this.posY;
        double d2 = this.spawnPosition.getZ() + 0.5 - this.posZ;
        this.motionX = this.motionX + (Math.signum(d0) * 0.5 - this.motionX) * 0.1F;
        this.motionY = this.motionY + (Math.signum(d1) * 0.7F - this.motionY) * 0.1F;
        this.motionZ = this.motionZ + (Math.signum(d2) * 0.5 - this.motionZ) * 0.1F;
        float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0 / Math.PI) - 90.0F;
        float f1 = MathHelper.wrapAngleTo180(f - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += f1;
        if (this.rand.nextInt(100) == 0
                && this.world.getBlockState(blockPosValue).getBlock().isNormalCube()) {
            this.setIsBatHanging(true);
        }
    }

    protected String getDeathSound() {
        return "mob.bat.death";
    }

    protected void collideWithEntity(Entity entity) {}


    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    protected void updateFallState(double d0, boolean flag, Block block, BlockPos blockPos) {}

    public void setIsBatHanging(boolean flag) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        byte b0 = this.dataWatcher.getWatchableObjectByte(16);
        String textValue = text;
        if (flag) {
            this.dataWatcher.updateObject(16, (byte) (b0 | 1));
            if (textValue == null) {
                return;
            }
        }

        this.dataWatcher.updateObject(16, (byte) (b0 & -2));
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0);
    }

    public BatEntity(World worldValue) {
        super(worldValue);
        this.setSize(0.5F, 0.9F);
        this.setIsBatHanging(true);
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (!this.world.isRemote && this.getIsBatHanging()) {
            this.setIsBatHanging(false);
        }

        return super.attackEntityFrom(damageSource, f);
    }

    protected void nr() {}

    public float getEyeHeight() {
        return this.height / 2.0F;
    }

    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, new Byte((byte) 0));
    }

    protected float getSoundVolume() {
        return 0.1F;
    }

    protected String getHurtSound() {
        return "mob.bat.hurt";
    }

    public void fall(float f, float f1) {}

    public void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.dataWatcher.updateObject(16, compoundTag.getByte("BatFlags"));
    }

    public boolean canBePushed() {
        return false;
    }

    private boolean isDateAroundHalloween(Calendar calendar) {
        return calendar.get(2) + 1 == 10 && calendar.get(5) >= 20
                || calendar.get(2) + 1 == 11 && calendar.get(5) <= 3;
    }

    public void onUpdate() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        super.onUpdate();
        String textValue = text;
        if (this.getIsBatHanging()) {
            this.motionX = this.motionY = this.motionZ = 0.0;
            this.posY = MathHelper.floor(this.posY) + 1.0 - this.height;
            if (textValue == null) {
                return;
            }
        }

        this.motionY *= 0.6F;
    }

    protected boolean canTriggerWalking() {
        return false;
    }
}
