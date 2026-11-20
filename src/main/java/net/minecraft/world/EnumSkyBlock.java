package net.minecraft.world;

public enum EnumSkyBlock {
    SKY("SKY", 0, 15),
    BLOCK("BLOCK", 1, 0);

    private static EnumSkyBlock[] ahP;

    public int defaultLightValue;

    // region String decoding support

    // endregion

    static {
        ahP = new EnumSkyBlock[] {net.minecraft.world.EnumSkyBlock.SKY, net.minecraft.world.EnumSkyBlock.BLOCK};
    }

    // region String decoding support

    // endregion

    EnumSkyBlock(String text, int k, int j) {
        this.defaultLightValue = j;
    }

    // region String decoding support

    // endregion

}
