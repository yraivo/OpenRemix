package net.minecraft.entity.monster;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

class EntitySpiderAISpiderTarget extends EntityAINearestAttackableTarget {
    EntitySpider bhW;

    public boolean shouldExecute() {
        float brightness = this.taskOwner.getBrightness(1.0F);
        return brightness >= 0.5F ? false : super.shouldExecute();
    }

    public EntitySpiderAISpiderTarget(EntitySpider bhWValue, Class oclass) {
        super(bhWValue, oclass, true);
        this.bhW = bhWValue;
    }
}
