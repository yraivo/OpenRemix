package net.minecraft.world.border;

public enum EnumBorderStatus {
    GROWING("GROWING", 0, 4259712),
    SHRINKING("SHRINKING", 1, 16724016),
    STATIONARY("STATIONARY", 2, 2138367);
    private static EnumBorderStatus[] ais;

    private int id;

    public int getID() {
        return this.id;
    }

    // region String decoding support

    // endregion

    static {
        ais =
                new EnumBorderStatus[] {
                    net.minecraft.world.border.EnumBorderStatus.GROWING,
                    net.minecraft.world.border.EnumBorderStatus.SHRINKING,
                    net.minecraft.world.border.EnumBorderStatus.STATIONARY
                };
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    EnumBorderStatus(String text, int k, int j) {
        this.id = j;
    }
}
