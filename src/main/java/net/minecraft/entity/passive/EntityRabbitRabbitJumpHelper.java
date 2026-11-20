package net.minecraft.entity.passive;

import net.minecraft.entity.ai.EntityJumpHelper;

public class EntityRabbitRabbitJumpHelper extends EntityJumpHelper {
    EntityRabbit field_180069_b;
    private boolean field_180068_d;
    private static String[] packedTextFragments;
    private static String f;
    private EntityRabbit theEntity;

    public static void b(String text) {
        f = text;
    }

    public boolean func_180065_d() {
        return this.field_180068_d;
    }

    public boolean aY() {
        return this.isJumping;
    }

    public void doJump() {
        if (this.isJumping) {
            this.theEntity.doMovementAction(EntityRabbitEnumMoveType.STEP);
            this.isJumping = false;
        }
    }

    public static String method_cee993844cce4b97ed62() {
        return f;
    }

    public void func_180066_a(boolean flag) {
        this.field_180068_d = flag;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.entity.passive.EntityRabbitRabbitJumpHelper.packedTextFragments[0] = new String("XVEzvb");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
        if (method_cee993844cce4b97ed62() != null) {
            b(packedTextFragments[0]);
        }
    }

    // region String decoding support

    // endregion

    public EntityRabbitRabbitJumpHelper(EntityRabbit bjoValue, EntityRabbit bjqValue) {
        super(bjqValue);
        this.field_180069_b = bjoValue;
        this.field_180068_d = false;
        this.theEntity = bjqValue;
    }
}
