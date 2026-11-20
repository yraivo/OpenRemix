package net.minecraft.util;

class IntHashMapEntry {
    IntHashMapEntry nextEntry;
    private static String[] packedTextFragments;
    Object valueEntry;
    int slotHash;
    int hashEntry;

    public final int getHash() {
        return this.hashEntry;
    }

    @Override
    public final boolean equals(Object object) {
        if (!(object instanceof IntHashMapEntry)) {
            return false;
        }

        IntHashMapEntry intHashMapEntry = (IntHashMapEntry) object;
        Integer integer = this.getHash();
        Integer integer1 = intHashMapEntry.getHash();
        if (integer == integer1 || integer != null && integer.equals(integer1)) {
            Object thisValue = this.getValue();
            Object value = intHashMapEntry.getValue();
            if (thisValue == value || thisValue != null && thisValue.equals(value)) {
                return true;
            }
        }

        return false;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    @Override
    public final int hashCode() {
        return IntHashMap.access$000(this.hashEntry);
    }

    @Override
    public final String toString() {
        return this.getHash() + packedTextFragments[0] + this.getValue();
    }

    public final Object getValue() {
        return this.valueEntry;
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.util.IntHashMapEntry.packedTextFragments[0] = new String("=");
    }

    // endregion

    // region String decoding support

    // endregion

    // Recovered explicit constructors from original mapped instruction streams.
    IntHashMapEntry(int slotHashValue, int j, Object valueEntryValue, IntHashMapEntry bEdValue) {
        super();
        this.valueEntry = valueEntryValue;
        this.nextEntry = bEdValue;
        this.hashEntry = j;
        this.slotHash = slotHashValue;
    }
}
