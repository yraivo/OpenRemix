package net.minecraft.util;

class LongHashMapEntry {
    LongHashMapEntry nextEntry;
    long key;
    private static String[] packedTextFragments;
    Object value;
    int hash;

    // region String decoding support

    // endregion

    @Override
    public final int hashCode() {
        return LongHashMap.access$000(this.key);
    }

    public final Object getValue() {
        return this.value;
    }

    public final long getKey() {
        return this.key;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.util.LongHashMapEntry.packedTextFragments[0] = new String("=");
    }

    // endregion

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof LongHashMapEntry)) {
            return false;
        }

        LongHashMapEntry longHashMapEntry = (LongHashMapEntry) object;
        Long olong = this.getKey();
        Long olong1 = longHashMapEntry.getKey();
        if (olong == olong1 || olong != null && olong.equals(olong1)) {
            Object thisValue = this.getValue();
            Object valueValue = longHashMapEntry.getValue();
            if (thisValue == valueValue || thisValue != null && thisValue.equals(valueValue)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public final String toString() {
        return this.getKey() + packedTextFragments[0] + this.getValue();
    }

    // Recovered explicit constructors from original mapped instruction streams.
    LongHashMapEntry(int hashValue, long j, Object valueValue, LongHashMapEntry bEmValue) {
        super();
        this.value = valueValue;
        this.nextEntry = bEmValue;
        this.key = j;
        this.hash = hashValue;
    }
}
