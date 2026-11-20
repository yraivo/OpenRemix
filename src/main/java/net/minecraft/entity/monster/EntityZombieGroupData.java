package net.minecraft.entity.monster;

import net.minecraft.entity.IEntityLivingData;

class EntityZombieGroupData implements IEntityLivingData {
    public boolean il;
    EntityZombie bsY;
    public boolean b;

    private EntityZombieGroupData(EntityZombie bsYValue, boolean flag, boolean flag1) {
        this.bsY = bsYValue;
        this.il = false;
        this.b = false;
        this.il = flag;
        this.b = flag1;
    }

    EntityZombieGroupData(
            EntityZombie entityZombie, boolean flag, boolean flag1, Object object) {
        this(entityZombie, flag, flag1);
    }
}
