package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityJumpHelper {
    private EntityLiving entity;
    protected boolean isJumping;

    public void setJumping() {
        this.isJumping = true;
    }

    public void doJump() {
        this.entity.setJumping(this.isJumping);
        this.isJumping = false;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public EntityJumpHelper(EntityLiving bjnValue) {
        super();
        this.entity = bjnValue;
    }
}
