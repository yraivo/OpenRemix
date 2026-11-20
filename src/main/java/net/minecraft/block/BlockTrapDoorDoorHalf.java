package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockTrapDoorDoorHalf implements StringSerializable {
    TOP("TOP", 0, "top"),
    BOTTOM("BOTTOM", 1, "bottom");
    private static BlockTrapDoorDoorHalf[] aeo;

    private String name;

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        aeo =
                new BlockTrapDoorDoorHalf[] {
                    net.minecraft.block.BlockTrapDoorDoorHalf.TOP, net.minecraft.block.BlockTrapDoorDoorHalf.BOTTOM
                };
    }

    @Override
    public String toString() {
        return this.name;
    }

    BlockTrapDoorDoorHalf(String text, int j, String textValue) {
        this.name = textValue;
    }

    // region String decoding support

    // endregion

}
