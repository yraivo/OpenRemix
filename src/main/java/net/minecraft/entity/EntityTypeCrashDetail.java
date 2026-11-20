package net.minecraft.entity;

import java.util.concurrent.Callable;

class EntityTypeCrashDetail implements Callable {
    private static String b;
    private static String[] packedTextFragments;
    Entity field_85155_a;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getCrashDetail() {
        return EntityList.getEntityString(this.field_85155_a)
                + b
                + this.field_85155_a.getClass().getCanonicalName()
                + packedTextFragments[0];
    }

    EntityTypeCrashDetail(Entity entity) {
        this.field_85155_a = entity;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    static {
        net.minecraft.entity.EntityTypeCrashDetail.packedTextFragments = new String[2];
        net.minecraft.entity.EntityTypeCrashDetail.packedTextFragments[0] = new String(")");
        net.minecraft.entity.EntityTypeCrashDetail.packedTextFragments[1] = new String("rY");
        b = " (";
    }
}
