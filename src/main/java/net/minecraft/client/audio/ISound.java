package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public interface ISound {
    float getZPosF();

    float getYPosF();

    ResourceLocation getSoundLocation();

    float getXPosF();

    float getPitch();

    SoundAttenuationType getAttenuationType();

    int getRepeatDelay();

    boolean canRepeat();

    float getVolume();
}
