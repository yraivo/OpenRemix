package net.minecraft.client.audio;

public class SoundEventAccessor implements ISoundEventAccessor {
    private SoundPoolEntry entry;
    private int weight;

    SoundEventAccessor(SoundPoolEntry aqnValue, int i) {
        this.entry = aqnValue;
        this.weight = i;
    }

    public SoundPoolEntry us() {
        return new SoundPoolEntry(this.entry);
    }

    @Override
    public int getWeight() {
        return this.weight;
    }

    public Object ut() {
        return this.us();
    }

    @Override
    public Object cloneEntry() {
        return this.us();
    }
}
