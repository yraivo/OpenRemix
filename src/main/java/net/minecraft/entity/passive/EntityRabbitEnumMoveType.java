package net.minecraft.entity.passive;

enum EntityRabbitEnumMoveType {
    NONE("NONE", 0, 0.0F, 0.0F, 30, 1),
    HOP("HOP", 1, 0.8F, 0.2F, 20, 10),
    STEP("STEP", 2, 1.0F, 0.45F, 14, 14),
    SPRINT("SPRINT", 3, 1.75F, 0.4F, 1, 8),
    ATTACK("ATTACK", 4, 2.0F, 0.7F, 7, 8);
    private static EntityRabbitEnumMoveType[] agk;

    private float hX;

    private int field_180085_i;
    private float speed;
    private int duration;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public float getSpeed() {
        return this.speed;
    }

    public int getDuration() {
        return this.duration;
    }

    public float aN() {
        return this.hX;
    }

    public int func_180073_d() {
        return this.field_180085_i;
    }

    static {
        agk =
                new EntityRabbitEnumMoveType[] {
                    net.minecraft.entity.passive.EntityRabbitEnumMoveType.NONE,
                    net.minecraft.entity.passive.EntityRabbitEnumMoveType.HOP,
                    net.minecraft.entity.passive.EntityRabbitEnumMoveType.STEP,
                    net.minecraft.entity.passive.EntityRabbitEnumMoveType.SPRINT,
                    net.minecraft.entity.passive.EntityRabbitEnumMoveType.ATTACK
                };
    }

    EntityRabbitEnumMoveType(String text, int l, float f, float f1, int j, int k) {
        this.speed = f;
        this.hX = f1;
        this.duration = j;
        this.field_180085_i = k;
    }
}
