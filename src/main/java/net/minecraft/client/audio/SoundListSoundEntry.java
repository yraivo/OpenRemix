package net.minecraft.client.audio;

import net.minecraft.client.audio.SoundListSoundEntryType;

public class SoundListSoundEntry {
    private String name;
    private float pitch;
    private int weight;
    private boolean streaming;
    private float volume = 1.0F;
    private SoundListSoundEntryType type;

    public SoundListSoundEntry() {
        this.pitch = 1.0F;
        this.weight = 1;
        this.type = SoundListSoundEntryType.FILE;
        this.streaming = false;
    }

    public String getSoundEntryName() {
        return this.name;
    }

    public float getSoundEntryPitch() {
        return this.pitch;
    }

    public int getSoundEntryWeight() {
        return this.weight;
    }

    public void setStreaming(boolean flag) {
        this.streaming = flag;
    }

    public void setSoundEntryType(SoundListSoundEntryType aqAValue) {
        this.type = aqAValue;
    }

    public float getSoundEntryVolume() {
        return this.volume;
    }

    public boolean isStreaming() {
        return this.streaming;
    }

    public void setSoundEntryWeight(int i) {
        this.weight = i;
    }

    public SoundListSoundEntryType getSoundEntryType() {
        return this.type;
    }

    public void setSoundEntryPitch(float f) {
        this.pitch = f;
    }

    public void setSoundEntryName(String text) {
        this.name = text;
    }

    public void setSoundEntryVolume(float f) {
        this.volume = f;
    }
}
