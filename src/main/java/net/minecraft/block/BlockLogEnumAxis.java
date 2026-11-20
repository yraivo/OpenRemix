package net.minecraft.block;

import net.minecraft.util.FacingAxis;
import net.minecraft.util.StringSerializable;

public enum BlockLogEnumAxis implements StringSerializable {
    X(initializeConstantState(), 0, constantTextFragment(3)),
    Y(constantTextFragment(5), 1, constantTextFragment(6)),
    Z(constantTextFragment(8), 2, constantTextFragment(9)),
    NONE("NONE", 3, "none");
    private static BlockLogEnumAxis[] Ux;

    private static BlockLogEnumAxis[] Uu;

    private String name;
    private static String[] packedTextFragments;

    public String getName() {
        return this.name;
    }

    static {
        Uu =
                new BlockLogEnumAxis[] {
                    net.minecraft.block.BlockLogEnumAxis.X,
                    net.minecraft.block.BlockLogEnumAxis.Y,
                    net.minecraft.block.BlockLogEnumAxis.Z,
                    net.minecraft.block.BlockLogEnumAxis.NONE
                };
        Ux =
                new BlockLogEnumAxis[] {
                    net.minecraft.block.BlockLogEnumAxis.X,
                    net.minecraft.block.BlockLogEnumAxis.Y,
                    net.minecraft.block.BlockLogEnumAxis.Z,
                    net.minecraft.block.BlockLogEnumAxis.NONE
                };
    }

    // region String decoding support

    // endregion

    BlockLogEnumAxis(String text, int j, String textValue) {
        this.name = textValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    @Override
    public String toString() {
        return this.name;
    }

    public static BlockLogEnumAxis fromFacingAxis(FacingAxis facingAxis) {
        switch (facingAxis) {
            case X:
                return net.minecraft.block.BlockLogEnumAxis.X;
            case Y:
                return net.minecraft.block.BlockLogEnumAxis.Y;
            case Z:
                return net.minecraft.block.BlockLogEnumAxis.Z;
            default:
                return net.minecraft.block.BlockLogEnumAxis.NONE;
        }
    }

    private static BlockLogEnumAxis[] __recovery_values171() {
        return Uu.clone();
    }

    // Initialize shared state before the first constant is published.
    private static String initializeConstantState() {
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments = new String[10];
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[1] = new String("X");
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[2] = new String("X");
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[3] = new String("x");
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[4] = new String("Y");
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[5] = new String("Y");
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[6] = new String("y");
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[7] = new String("Z");
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[8] = new String("Z");
        net.minecraft.block.BlockLogEnumAxis.packedTextFragments[9] = new String("z");
        return net.minecraft.block.BlockLogEnumAxis.packedTextFragments[2];
    }

    private static String constantTextFragment(int index) {
        return packedTextFragments[index];
    }
}
