package net.minecraft.util;

public enum EnumWorldBlockLayer {
    SOLID("SOLID", 0, "Solid"),
    CUTOUT_MIPPED("CUTOUT_MIPPED", 1, "Mipped Cutout"),
    CUTOUT("CUTOUT", 2, "Cutout"),
    TRANSLUCENT("TRANSLUCENT", 3, "Translucent");
    private static EnumWorldBlockLayer[] Zq;

    private String layerName;

    @Override
    public String toString() {
        return this.layerName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    EnumWorldBlockLayer(String text, int j, String textValue) {
        this.layerName = textValue;
    }

    static {
        Zq =
                new EnumWorldBlockLayer[] {
                    net.minecraft.util.EnumWorldBlockLayer.SOLID,
                    net.minecraft.util.EnumWorldBlockLayer.CUTOUT_MIPPED,
                    net.minecraft.util.EnumWorldBlockLayer.CUTOUT,
                    net.minecraft.util.EnumWorldBlockLayer.TRANSLUCENT
                };
    }
}
