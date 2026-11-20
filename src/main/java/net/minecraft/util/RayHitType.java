package net.minecraft.util;

public enum RayHitType {
    MISS("MISS", 0),
    BLOCK("BLOCK", 1),
    ENTITY("ENTITY", 2);
    private static RayHitType[] Zw;

    // region String decoding support

    // endregion

    RayHitType(String text, int j) {}

    // region Static initialization and string tables
    static {
        Zw = new RayHitType[] {MISS, BLOCK, ENTITY};
    }
    // endregion

    // region String decoding support

    // endregion

}
