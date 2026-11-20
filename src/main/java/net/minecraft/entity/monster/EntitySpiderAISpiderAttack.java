package net.minecraft.entity.monster;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;

class EntitySpiderAISpiderAttack extends EntityAIAttackOnCollide {
    EntitySpider bhW;

    public EntitySpiderAISpiderAttack(EntitySpider bhWValue, Class oclass) {
        super(bhWValue, oclass, 1.0, true);
        this.bhW = bhWValue;
    }

    public boolean continueExecuting() {
        float brightness = this.attacker.getBrightness(1.0F);
        if (brightness >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
            this.attacker.setAttackTarget((LivingEntity) null);
            return false;
        } else {
            return super.continueExecuting();
        }
    }

    protected double method_d2bb536412a0d5078fca(LivingEntity livingEntity) {
        return 4.0F + livingEntity.width;
    }
}
