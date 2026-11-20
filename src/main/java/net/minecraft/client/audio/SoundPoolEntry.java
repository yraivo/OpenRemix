package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class SoundPoolEntry {
    private boolean streamingSound;
    private ResourceLocation location;
    private double volume;
    private double pitch;

    public void setVolume(double volumeValue) {
        this.volume = volumeValue;
    }

    public SoundPoolEntry(
            ResourceLocation resourceLocation,
            double pitchValue,
            double volumeValue,
            boolean streamingSoundValue) {
        this.location = resourceLocation;
        this.pitch = pitchValue;
        this.volume = volumeValue;
        this.streamingSound = streamingSoundValue;
    }

    public double getVolume() {
        return this.volume;
    }

    public boolean isStreamingSound() {
        return this.streamingSound;
    }

    public void setPitch(double pitchValue) {
        this.pitch = pitchValue;
    }

    public ResourceLocation getSoundPoolEntryLocation() {
        return this.location;
    }

    public double getPitch() {
        return this.pitch;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public SoundPoolEntry(SoundPoolEntry soundPoolEntry) {
        super();
        this.location = soundPoolEntry.location;
        this.pitch = soundPoolEntry.pitch;
        this.volume = soundPoolEntry.volume;
        this.streamingSound = soundPoolEntry.streamingSound;
    }
}
