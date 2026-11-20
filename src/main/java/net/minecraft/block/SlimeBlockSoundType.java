package net.minecraft.block;

import net.minecraft.block.BlockSoundType;

public final class SlimeBlockSoundType extends BlockSoundType {

    public String getPlaceSound() {
        return "mob.slime.big";
    }

    public String getBreakSound() {
        return "mob.slime.big";
    }

    public String getStepSound() {
        return "mob.slime.small";
    }


    public SlimeBlockSoundType(String text, float f, float f1) {
        super(text, f, f1);
    }
}
