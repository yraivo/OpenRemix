package net.minecraft.client.audio;

import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.SoundEventAccessorComposite;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundListSoundEntry;
import net.minecraft.client.audio.SoundPoolEntry;
import net.minecraft.util.ResourceLocation;

public class SoundEventReferenceAccessor implements ISoundEventAccessor {
    SoundHandler aqx;
    ResourceLocation dy;
    SoundListSoundEntry aqy;
    String b;

    public SoundEventReferenceAccessor(
            SoundHandler aqxValue, String text, SoundListSoundEntry aqyValue) {
        this.aqx = aqxValue;
        this.b = text;
        this.aqy = aqyValue;
        this.dy = new ResourceLocation(this.b, this.aqy.getSoundEntryName());
    }

    public Object cloneEntry() {
        return this.getEntry();
    }

    public int getWeight() {
        SoundEventAccessorComposite object =
                (SoundEventAccessorComposite) SoundHandler.access$000(this.aqx).getObject(this.dy);
        return object == null ? 0 : object.getWeight();
    }

    public SoundPoolEntry getEntry() {
        SoundEventAccessorComposite object =
                (SoundEventAccessorComposite) SoundHandler.access$000(this.aqx).getObject(this.dy);
        return (SoundPoolEntry) (object == null ? SoundHandler.missing_sound : object.cloneEntry());
    }
}
