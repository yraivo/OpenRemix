package net.minecraft.entity.passive;

import net.minecraft.block.material.Material;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class SquidEntity extends EntityWaterMob {
    public float squidRotation;
    public float squidYaw;
    private float randomMotionSpeed;
    public float prevSquidYaw;
    public float tentacleAngle;
    private float randomMotionVecX;
    private float randomMotionVecY;
    public float prevSquidRotation;
    public float lastTentacleAngle;
    private float bbL;
    public float squidPitch;
    private float randomMotionVecZ;
    public float prevSquidPitch;
    private float rotationVelocity;

    public boolean getCanSpawnHere() {
        return this.posY > 45.0 && this.posY < 63.0 && super.getCanSpawnHere();
    }

    public SquidEntity(World worldValue) {
        super(worldValue);
        this.setSize(0.95F, 0.95F);
        this.rand.setSeed(1 + this.getEntityId());
        this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
        this.tasks.addTask(0, new EntitySquidAIMoveRandom(this));
    }

    public void handleHealthUpdate(byte b0) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        if (b0 == 19) {
            this.squidRotation = 0.0F;
            if (text == null) {
                return;
            }
        }

        super.handleHealthUpdate(b0);
    }

    protected void dropFewItems(boolean flag, int i) {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int j = this.rand.nextInt(3 + i) + 1;
        String textValue = text;
        int k = 0;

        while (k < j) {
            this.entityDropItem(
                    new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeColorDamage()), 0.0F);
            k++;
            if (textValue != null) {
                break;
            }
        }
    }

    public void func_175568_b(float bbHValue, float bbIValue, float bbGValue) {
        this.randomMotionVecX = bbHValue;
        this.randomMotionVecY = bbIValue;
        this.randomMotionVecZ = bbGValue;
    }

    protected String getDeathSound() {
        return null;
    }

    public void onLivingUpdate() {
        String text;
        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        String textValue = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        this.lastTentacleAngle = this.tentacleAngle;
        text = textValue;
        this.squidRotation = this.squidRotation + this.rotationVelocity;
        if (this.squidRotation > Math.PI * 2) {
            if (this.world.isRemote) {
                this.squidRotation = (float) (Math.PI * 2);
                if (!(text == null)) {

                    this.squidRotation = (float) (this.squidRotation - (Math.PI * 2));
                    if (this.rand.nextInt(10) == 0) {
                        this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
                    }

                    this.world.setEntityState(this, (byte) 19);
                }
            } else {

                this.squidRotation = (float) (this.squidRotation - (Math.PI * 2));
                if (this.rand.nextInt(10) == 0) {
                    this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
                }

                this.world.setEntityState(this, (byte) 19);
            }
        }

        if (this.inWater) {
            label37:
            {
                if (this.squidRotation < (float) Math.PI) {
                    float f = this.squidRotation / (float) Math.PI;
                    this.tentacleAngle =
                            MathHelper.sin(f * f * (float) Math.PI) * (float) Math.PI * 0.25F;
                    if (f > 0.75) {
                        this.randomMotionSpeed = 1.0F;
                        this.bbL = 1.0F;
                        if (text == null) {
                            break label37;
                        }
                    }

                    this.bbL *= 0.8F;
                    if (text == null) {
                        break label37;
                    }
                }

                this.tentacleAngle = 0.0F;
                this.randomMotionSpeed *= 0.9F;
                this.bbL *= 0.99F;
            }

            if (!this.world.isRemote) {
                this.motionX = this.randomMotionVecX * this.randomMotionSpeed;
                this.motionY = this.randomMotionVecY * this.randomMotionSpeed;
                this.motionZ = this.randomMotionVecZ * this.randomMotionSpeed;
            }

            float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset =
                    this.renderYawOffset
                            + (-((float) Math.atan2(this.motionX, this.motionZ))
                                                    * 180.0F
                                                    / (float) Math.PI
                                            - this.renderYawOffset)
                                    * 0.1F;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw = (float) (this.squidYaw + Math.PI * this.bbL * 1.5);
            this.squidPitch =
                    this.squidPitch
                            + (-((float) Math.atan2(f1, this.motionY)) * 180.0F / (float) Math.PI
                                            - this.squidPitch)
                                    * 0.1F;
            if (text == null) {
                return;
            }
        }

        this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float) Math.PI * 0.25F;
        if (!this.world.isRemote) {
            this.motionX = 0.0;
            this.motionY -= 0.08;
            this.motionY *= 0.98F;
            this.motionZ = 0.0;
        }

        this.squidPitch = (float) (this.squidPitch + (-90.0F - this.squidPitch) * 0.02);
    }

    public float getEyeHeight() {
        return this.height * 0.5F;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    protected boolean canTriggerWalking() {
        return false;
    }

    public boolean isInWater() {
        return this.world.handleMaterialAcceleration(
                this.getEntityBoundingBox().expand(0.0, -0.6F, 0.0), Material.water, this);
    }

    protected Item getDropItem() {
        return null;
    }

    protected String getHurtSound() {
        return null;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
    }

    public boolean func_175567_n() {
        return this.randomMotionVecX != 0.0F
                || this.randomMotionVecY != 0.0F
                || this.randomMotionVecZ != 0.0F;
    }

    public void moveEntityWithHeading(float p171_0, float p171_1) {
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
    }

    protected String getLivingSound() {
        return null;
    }

    static boolean method_b80dd75a016382a287ad(SquidEntity squidEntity) {
        return squidEntity.inWater;
    }
}
