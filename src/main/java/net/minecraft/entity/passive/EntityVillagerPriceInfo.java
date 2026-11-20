package net.minecraft.entity.passive;

import net.minecraft.util.Tuple;

import java.util.Random;

class EntityVillagerPriceInfo extends Tuple {
    public EntityVillagerPriceInfo(int p171_0, int p171_1) {
        super(p171_0, p171_1);
    }

    public int getPrice(Random random) {
        return ((int) (((java.lang.Integer) ((java.lang.Integer) this.getFirst())).intValue()))
                        >= ((int)
                                (((java.lang.Integer) ((java.lang.Integer) this.getSecond())).intValue()))
                ? (java.lang.Integer) this.getFirst()
                : (java.lang.Integer) this.getFirst()
                        + random.nextInt(
                                (java.lang.Integer) this.getSecond() - (java.lang.Integer) this.getFirst() + 1);
    }
}
