package net.minecraft.block;

import net.minecraft.block.BlockSoundType;

public final class LadderBlockSoundType extends BlockSoundType {

    private static String f;

    public LadderBlockSoundType(String text, float f, float f1) {
        super(text, f, f1);
    }

    static {
        f = "dig.wood";
    }

    public String getBreakSound() {
        return f;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion
}
