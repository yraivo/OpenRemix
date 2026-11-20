package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaCube extends SlimeEntity {

    protected boolean makesSoundOnLand() {
        return true;
    }

    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.FLAME;
    }

    protected String getJumpSound() {
        return this.getSlimeSize() > 1 ? "mob.magmacube.big" : "mob.magmacube.small";
    }

    protected void handleJumpLava() {
        this.motionY = 0.22F + this.getSlimeSize() * 0.05F;
        this.isAirBorne = true;
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2F);
    }

    public boolean isBurning() {
        return false;
    }

    public int getBrightnessForRender(float f) {
        return 15728880;
    }

    public EntityMagmaCube(World worldValue) {
        super(worldValue);
        this.isImmuneToFire = true;
    }

    public int getTotalArmorValue() {
        return this.getSlimeSize() * 3;
    }

    protected Item getDropItem() {
        return Items.magma_cream;
    }

    public boolean handleLavaMovement() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this)
                && this.world.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()
                && !this.world.isAnyLiquid(this.getEntityBoundingBox());
    }

    public void fall(float f, float f1) {}

    protected SlimeEntity createInstance() {
        return new EntityMagmaCube(this.world);
    }


    protected void alterSquishAmount() {
        this.squishAmount *= 0.9F;
    }

    public float getBrightness(float f) {
        return 1.0F;
    }

    public boolean getCanSpawnHere() {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
    }

    protected int getJumpDelay() {
        return super.getJumpDelay() * 4;
    }

    protected int getAttackStrength() {
        return super.getAttackStrength() + 2;
    }

    protected boolean canDamagePlayer() {
        return true;
    }

    protected void jump() {
        this.motionY = 0.42F + this.getSlimeSize() * 0.1F;
        this.isAirBorne = true;
    }

    protected void dropFewItems(boolean flag, int i) {
        int i1 = EntitySpiderGroupData.method_beea3e16fe3ec0995eb9();
        Item item = this.getDropItem();
        int j = i1;
        if (item != null && this.getSlimeSize() > 1) {
            int k = this.rand.nextInt(4) - 2;
            if (i > 0) {
                k += this.rand.nextInt(i + 1);
            }

            int l = 0;

            while (l < k) {
                this.dropItem(item, 1);
                l++;
                if (j != 0) {
                    break;
                }
            }
        }
    }
}
