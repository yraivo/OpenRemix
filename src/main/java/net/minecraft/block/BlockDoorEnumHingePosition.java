package net.minecraft.block;

import net.minecraft.util.StringSerializable;

public enum BlockDoorEnumHingePosition implements StringSerializable {
    LEFT("LEFT", 0),
    RIGHT("RIGHT", 1);
    private static BlockDoorEnumHingePosition[] SD;

    // region String decoding support

    // endregion

    BlockDoorEnumHingePosition(String text, int j) {}

    public String getName() {
        return this == net.minecraft.block.BlockDoorEnumHingePosition.LEFT ? "left" : "right";
    }

    // region String decoding support

    // endregion

    static {
        SD =
                new BlockDoorEnumHingePosition[] {
                    net.minecraft.block.BlockDoorEnumHingePosition.LEFT, net.minecraft.block.BlockDoorEnumHingePosition.RIGHT
                };
    }

    @Override
    public String toString() {
        return this.getName();
    }

    // region String decoding support

    // endregion

}
