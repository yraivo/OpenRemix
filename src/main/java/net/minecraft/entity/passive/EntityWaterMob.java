package net.minecraft.entity.passive;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public abstract class EntityWaterMob extends EntityLiving implements IAnimals {
    protected int getExperiencePoints(PlayerEntity playerEntity) {
        return 1 + this.world.rand.nextInt(3);
    }

    public boolean getCanSpawnHere() {
        return true;
    }

    public boolean method_b417d7500ec59d69e373() {
        return true;
    }

    public EntityWaterMob(World worldValue) {
        super(worldValue);
    }

    public boolean isPushedByWater() {
        return false;
    }

    protected boolean canDespawn() {
        return true;
    }

    public boolean handleLavaMovement() {
        return this.world.checkNoEntityCollision(this.getEntityBoundingBox(), this);
    }

    public int getTalkInterval() {
        return 120;
    }

    public void onEntityUpdate() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int air = this.getAir();
        String textValue = text;
        super.onEntityUpdate();
        if (this.isEntityAlive() && !this.isInWater()) {
            this.setAir(--air);
            if (this.getAir() != -20) {
                return;
            }

            this.setAir(0);
            this.attackEntityFrom(DamageSource.drown, 2.0F);
            if (textValue == null) {
                return;
            }
        }

        this.setAir(300);
    }
}
