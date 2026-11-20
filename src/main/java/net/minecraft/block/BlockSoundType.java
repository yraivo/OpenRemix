package net.minecraft.block;

public class BlockSoundType {
    public float frequency;

    public String soundName;
    public float volume;

    public float getVolume() {
        return this.volume;
    }

    public String getStepSound() {
        return "step." + this.soundName;
    }

    public float getFrequency() {
        return this.frequency;
    }

    public BlockSoundType(String text, float volumeValue, float frequencyValue) {
        this.soundName = text;
        this.volume = volumeValue;
        this.frequency = frequencyValue;
    }


    public String getPlaceSound() {
        return this.getBreakSound();
    }

    public String getBreakSound() {
        return "dig." + this.soundName;
    }
}
