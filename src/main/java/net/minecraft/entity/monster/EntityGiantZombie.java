package net.minecraft.entity.monster;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityGiantZombie extends MonsterEntity {
    public EntityGiantZombie(World worldValue) {
        super(worldValue);
        this.setSize(this.width * 6.0F, this.height * 6.0F);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(50.0);
    }

    public float getBlockPathWeight(BlockPos blockPos) {
        return this.world.getLightBrightness(blockPos) - 0.5F;
    }

    public float getEyeHeight() {
        return 10.440001F;
    }
}
