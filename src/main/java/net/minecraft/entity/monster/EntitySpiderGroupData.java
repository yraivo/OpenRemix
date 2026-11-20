package net.minecraft.entity.monster;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.potion.Potion;

import java.util.Random;

public class EntitySpiderGroupData implements IEntityLivingData {
    public int potionEffectId;
    private static int cz;

    public static void b(int i) {
        cz = i;
    }

    static {
        if (method_7eec03da329a017ec143() == 0) {
            b(63);
        }
    }

    public static int method_7eec03da329a017ec143() {
        return cz;
    }

    public void method_5cca81c7e4d1b0803e89(Random random) {
        int k = method_7eec03da329a017ec143();
        int j = random.nextInt(5);
        int i = k;
        if (j <= 1) {
            this.potionEffectId = Potion.MOVE_SPEED.id;
            if (i != 0) {
                return;
            }
        }

        if (j <= 2) {
            this.potionEffectId = Potion.DAMAGE_BOOST.id;
            if (i != 0) {
                return;
            }
        }

        if (j <= 3) {
            this.potionEffectId = Potion.REGENERATION.id;
            if (i != 0) {
                return;
            }
        }

        if (j <= 4) {
            this.potionEffectId = Potion.INVISIBILITY.id;
        }
    }

    public static int method_beea3e16fe3ec0995eb9() {
        int i = method_7eec03da329a017ec143();
        return i == 0 ? 60 : 0;
    }
}
