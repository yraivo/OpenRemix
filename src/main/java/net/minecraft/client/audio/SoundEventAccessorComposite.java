package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SoundEventAccessorComposite implements ISoundEventAccessor {
    private double eventPitch;
    private Random rnd;
    private final List soundPool = Lists.newArrayList();
    private ResourceLocation soundLocation;
    private double eventVolume;
    private SoundCategory category;

    public SoundPoolEntry us() {
        int weight = this.getWeight();
        if (!this.soundPool.isEmpty() && weight != 0) {
            int j = this.rnd.nextInt(weight);

            for (java.util.Iterator<?> __recoveryIterator171_838 = (this.soundPool).iterator();
                    (__recoveryIterator171_838).hasNext(); ) {
                ISoundEventAccessor iSoundEventAccessor =
                        (ISoundEventAccessor) (__recoveryIterator171_838).next();
                j -= iSoundEventAccessor.getWeight();
                if (j < 0) {
                    SoundPoolEntry soundPoolEntry =
                            (SoundPoolEntry) iSoundEventAccessor.cloneEntry();
                    soundPoolEntry.setPitch(soundPoolEntry.getPitch() * this.eventPitch);
                    soundPoolEntry.setVolume(soundPoolEntry.getVolume() * this.eventVolume);
                    return soundPoolEntry;
                }
            }

            return SoundHandler.missing_sound;
        } else {
            return SoundHandler.missing_sound;
        }
    }

    public SoundCategory getSoundCategory() {
        return this.category;
    }

    public void addSoundToEventPool(ISoundEventAccessor iSoundEventAccessor) {
        this.soundPool.add(iSoundEventAccessor);
    }

    public SoundEventAccessorComposite(
            ResourceLocation soundLocationValue,
            double eventPitchValue,
            double eventVolumeValue,
            SoundCategory aqpValue) {
        this.rnd = new Random();
        this.soundLocation = soundLocationValue;
        this.eventVolume = eventVolumeValue;
        this.eventPitch = eventPitchValue;
        this.category = aqpValue;
    }

    public ResourceLocation getSoundEventLocation() {
        return this.soundLocation;
    }

    public int getWeight() {
        int j = 0;
        int k = SoundHandler.getOpaqueControlBranchValue();
        Iterator iterator = this.soundPool.iterator();
        int i = k;

        while (iterator.hasNext()) {
            ISoundEventAccessor iSoundEventAccessor = (ISoundEventAccessor) iterator.next();
            j += iSoundEventAccessor.getWeight();
            if (i == 0) {
                break;
            }
        }

        return j;
    }

    public Object cloneEntry() {
        return this.us();
    }
}
