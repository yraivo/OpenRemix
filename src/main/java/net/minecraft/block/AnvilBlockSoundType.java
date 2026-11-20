package net.minecraft.block;

import net.minecraft.block.BlockSoundType;

public final class AnvilBlockSoundType extends BlockSoundType {

    public String getPlaceSound() {
        return "random.anvil_land";
    }


    public String getBreakSound() {
        return "dig.stone";
    }

    public AnvilBlockSoundType(String text, float f, float f1) {
        super(text, f, f1);
    }
}
