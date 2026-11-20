package net.minecraft.entity.boss;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.DamageSource;

public class EntityDragonPart extends Entity {
    public IEntityMultiPart entityDragonObj;
    public String partName;

    protected void writeEntityToNBT(CompoundTag compoundTag) {}

    protected void entityInit() {}

    public boolean isEntityEqual(Entity entity) {
        return this == entity || this.entityDragonObj == entity;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public EntityDragonPart(IEntityMultiPart beeValue, String bbqValue, float f, float f1) {
        super(beeValue.getWorld());
        this.setSize(f, f1);
        this.entityDragonObj = beeValue;
        this.partName = bbqValue;
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {}

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return this.isEntityInvulnerable(damageSource)
                ? false
                : this.entityDragonObj.attackEntityFromPart(this, damageSource, f);
    }
}
