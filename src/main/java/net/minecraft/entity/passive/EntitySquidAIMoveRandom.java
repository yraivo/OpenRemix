package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.MathHelper;

class EntitySquidAIMoveRandom extends EntityAIBase {
    private SquidEntity squid;
    SquidEntity bkb;

    EntitySquidAIMoveRandom(SquidEntity bkbValue) {
        this.bkb = bkbValue;
        this.squid = this.bkb;
    }

    public boolean shouldExecute() {
        return true;
    }

    public void updateTask() {
        String text = EntityRabbitRabbitJumpHelper.method_cee993844cce4b97ed62();
        int i = this.squid.IJ();
        String textValue = text;
        if (i > 100) {
            this.squid.func_175568_b(0.0F, 0.0F, 0.0F);
            if (textValue == null) {
                return;
            }
        }

        if (this.squid.getRNG().nextInt(50) == 0
                || !SquidEntity.method_b80dd75a016382a287ad(this.squid)
                || !this.squid.func_175567_n()) {
            float f = this.squid.getRNG().nextFloat() * (float) Math.PI * 2.0F;
            float f1 = MathHelper.cos(f) * 0.2F;
            float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
            float f3 = MathHelper.sin(f) * 0.2F;
            this.squid.func_175568_b(f1, f2, f3);
        }
    }
}
