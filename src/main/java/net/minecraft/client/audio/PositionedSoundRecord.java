package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public class PositionedSoundRecord extends PositionedSound {
    private PositionedSoundRecord(
            ResourceLocation resourceLocation,
            float volumeValue,
            float pitchValue,
            boolean repeatValue,
            int repeatDelayValue,
            SoundAttenuationType ansValue,
            float xPosFValue,
            float yPosFValue,
            float zPosFValue) {
        super(resourceLocation);
        this.volume = volumeValue;
        this.pitch = pitchValue;
        this.xPosF = xPosFValue;
        this.yPosF = yPosFValue;
        this.zPosF = zPosFValue;
        this.repeat = repeatValue;
        this.repeatDelay = repeatDelayValue;
        this.attenuationType = ansValue;
    }

    public static PositionedSoundRecord createPositionedSoundRecord(
            ResourceLocation resourceLocation, float f) {
        return new PositionedSoundRecord(
                resourceLocation, 0.25F, f, false, 0, SoundAttenuationType.NONE, 0.0F, 0.0F, 0.0F);
    }

    public static PositionedSoundRecord createRecordSoundAtPosition(
            ResourceLocation resourceLocation, float f, float f1, float f2) {
        return new PositionedSoundRecord(
                resourceLocation, 4.0F, 1.0F, false, 0, SoundAttenuationType.LINEAR, f, f1, f2);
    }

    public static PositionedSoundRecord createPositionedSoundRecord(
            ResourceLocation resourceLocation) {
        return new PositionedSoundRecord(
                resourceLocation,
                1.0F,
                1.0F,
                false,
                0,
                SoundAttenuationType.NONE,
                0.0F,
                0.0F,
                0.0F);
    }

    public PositionedSoundRecord(
            ResourceLocation resourceLocation, float f, float f1, float f2, float f3, float f4) {
        this(resourceLocation, f, f1, false, 0, SoundAttenuationType.LINEAR, f2, f3, f4);
    }
}
