package net.minecraft.entity;

import java.util.concurrent.Callable;

class EntityTrackingIntervalCrashDetail implements Callable {

    private static String[] packedTextFragments;
    int field_96570_a;

    EntityTracker field_96569_b;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    public String getCrashDetail() {
        String text = "Once per " + this.field_96570_a + " ticks";
        if (this.field_96570_a == Integer.MAX_VALUE) {
            text = "Maximum (" + text + packedTextFragments[0];
        }

        return text;
    }

    EntityTrackingIntervalCrashDetail(EntityTracker bjhValue, int i) {
        this.field_96569_b = bjhValue;
        this.field_96570_a = i;
    }

    static {
        net.minecraft.entity.EntityTrackingIntervalCrashDetail.packedTextFragments = new String[2];
        net.minecraft.entity.EntityTrackingIntervalCrashDetail.packedTextFragments[0] = new String(")");
    }
}
