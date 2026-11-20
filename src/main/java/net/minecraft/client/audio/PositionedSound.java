package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public abstract class PositionedSound implements ISound {
    protected boolean repeat;
    protected ResourceLocation positionedSoundLocation;
    protected float volume = 1.0F;
    protected SoundAttenuationType attenuationType;
    protected float pitch = 1.0F;
    protected float xPosF;
    protected int repeatDelay;
    protected float zPosF;
    protected float yPosF;

    public float getYPosF() {
        return this.yPosF;
    }

    public ResourceLocation getSoundLocation() {
        return this.positionedSoundLocation;
    }

    public float getVolume() {
        return this.volume;
    }

    public float getZPosF() {
        return this.zPosF;
    }

    public float getPitch() {
        return this.pitch;
    }

    public int getRepeatDelay() {
        return this.repeatDelay;
    }

    public boolean canRepeat() {
        return this.repeat;
    }

    public SoundAttenuationType getAttenuationType() {
        return this.attenuationType;
    }

    public float getXPosF() {
        return this.xPosF;
    }

    protected PositionedSound(ResourceLocation positionedSoundLocationValue) {
        this.repeat = false;
        this.repeatDelay = 0;
        this.attenuationType = SoundAttenuationType.LINEAR;
        this.positionedSoundLocation = positionedSoundLocationValue;
    }
}
