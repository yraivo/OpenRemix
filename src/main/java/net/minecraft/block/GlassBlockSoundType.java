package net.minecraft.block;

import net.minecraft.block.BlockSoundType;

public final class GlassBlockSoundType extends BlockSoundType {

    public String getBreakSound() {
        return "dig.glass";
    }

    public String getPlaceSound() {
        return "step.stone";
    }


    public GlassBlockSoundType(String text, float f, float f1) {
        super(text, f, f1);
    }
}
