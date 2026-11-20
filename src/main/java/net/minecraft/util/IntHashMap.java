package net.minecraft.util;

public class IntHashMap {
    private float growFactor;
    private transient int count;
    private int threshold;
    private transient IntHashMapEntry[] slots = new IntHashMapEntry[16];

    final IntHashMapEntry removeEntry(int i) {
        int j = computeHash(i);
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int k = getSlotIndex(j, this.slots.length);
        IntHashMapEntry intHashMapEntry = this.slots[k];
        String textValue = text;
        IntHashMapEntry intHashMapEntryValue = intHashMapEntry;

        while (intHashMapEntryValue != null) {
            IntHashMapEntry bEd = intHashMapEntryValue.nextEntry;
            if (intHashMapEntryValue.hashEntry == i) {
                this.count--;
                if (intHashMapEntry == intHashMapEntryValue) {
                    this.slots[k] = bEd;
                    if (textValue != null) {
                        return intHashMapEntryValue;
                    }
                }

                intHashMapEntry.nextEntry = bEd;
                return intHashMapEntryValue;
            }

            intHashMapEntry = intHashMapEntryValue;
            intHashMapEntryValue = bEd;
            if (textValue == null) {
                break;
            }
        }

        return intHashMapEntryValue;
    }

    static int access$000(int i) {
        return computeHash(i);
    }

    public void addKey(int i, Object valueEntry) {
        int j = computeHash(i);
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int k = getSlotIndex(j, this.slots.length);
        String textValue = text;
        IntHashMapEntry intHashMapEntry = this.slots[k];

        while (intHashMapEntry != null) {
            if (intHashMapEntry.hashEntry == i) {
                intHashMapEntry.valueEntry = valueEntry;
                return;
            }

            intHashMapEntry = intHashMapEntry.nextEntry;
            if (textValue == null) {
                break;
            }
        }

        this.insert(j, i, valueEntry, k);
    }

    private static int computeHash(int i) {
        i ^= i >>> 20 ^ i >>> 12;
        return i ^ i >>> 7 ^ i >>> 4;
    }

    public Object lookup(int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int j = computeHash(i);
        IntHashMapEntry intHashMapEntry =
                this.slots[getSlotIndex(j, this.slots.length)];
        String textValue = text;

        while (intHashMapEntry != null) {
            if (intHashMapEntry.hashEntry == i) {
                return intHashMapEntry.valueEntry;
            }

            intHashMapEntry = intHashMapEntry.nextEntry;
            if (textValue == null) {
                break;
            }
        }

        return null;
    }

    public IntHashMap() {
        this.threshold = 12;
        this.growFactor = 0.75F;
    }

    private static int getSlotIndex(int i, int j) {
        return i & j - 1;
    }

    public Object removeObject(int i) {
        IntHashMapEntry intHashMapEntry = this.removeEntry(i);
        return intHashMapEntry == null ? null : intHashMapEntry.valueEntry;
    }

    private void insert(int i, int j, Object object, int k) {
        IntHashMapEntry intHashMapEntry = this.slots[k];
        this.slots[k] = new IntHashMapEntry(i, j, object, intHashMapEntry);
        if (this.count++ >= this.threshold) {
            this.grow(2 * this.slots.length);
        }
    }

    private void grow(int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        IntHashMapEntry[] bEcValue = this.slots;
        String textValue = text;
        int j = bEcValue.length;
        if (j == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            if (textValue != null) {
                return;
            }
        }

        IntHashMapEntry[] bEcValue2 = new IntHashMapEntry[i];
        this.copyTo(bEcValue2);
        this.slots = bEcValue2;
        this.threshold = (int) (i * 0.75F);
    }

    public void clearMap() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        IntHashMapEntry[] bEcValue = this.slots;
        int i = 0;
        String textValue = text;

        while (i < bEcValue.length) {
            bEcValue[i] = null;
            i++;
            if (textValue == null) {
                break;
            }
        }

        this.count = 0;
    }

    public boolean containsItem(int i) {
        return this.lookupEntry(i) != null;
    }

    final IntHashMapEntry lookupEntry(int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int j = computeHash(i);
        IntHashMapEntry intHashMapEntry =
                this.slots[getSlotIndex(j, this.slots.length)];
        String textValue = text;

        while (intHashMapEntry != null) {
            if (intHashMapEntry.hashEntry == i) {
                return intHashMapEntry;
            }

            intHashMapEntry = intHashMapEntry.nextEntry;
            if (textValue == null) {
                break;
            }
        }

        return null;
    }

    private void copyTo(IntHashMapEntry[] intHashMapEntryArray) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        IntHashMapEntry[] bEcValue = this.slots;
        String textValue = text;
        int length = intHashMapEntryArray.length;
        int j = 0;

        while (j < bEcValue.length) {
            IntHashMapEntry intHashMapEntry = bEcValue[j];
            if (intHashMapEntry != null) {
                bEcValue[j] = null;

                IntHashMapEntry intHashMapEntryValue;
                do {
                    intHashMapEntryValue = intHashMapEntry.nextEntry;
                    int k = getSlotIndex(intHashMapEntry.slotHash, length);
                    intHashMapEntry.nextEntry = intHashMapEntryArray[k];
                    intHashMapEntryArray[k] = intHashMapEntry;
                    intHashMapEntry = intHashMapEntryValue;
                } while (intHashMapEntryValue != null);
            }

            j++;
            if (textValue == null) {
                break;
            }
        }
    }
}
