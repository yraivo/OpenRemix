package net.minecraft.util;

public class LongHashMap {
    private int mask;
    private transient volatile int modCount;
    private transient LongHashMapEntry[] hashArray = new LongHashMapEntry[4096];
    private transient int numHashElements;
    private float percentUseable;
    private int capacity = 3072;

    final LongHashMapEntry removeKey(long i) {
        int j = getHashedKey(i);
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int k = getHashIndex(j, this.mask);
        LongHashMapEntry longHashMapEntry = this.hashArray[k];
        String textValue = text;
        LongHashMapEntry longHashMapEntryValue = longHashMapEntry;

        while (longHashMapEntryValue != null) {
            LongHashMapEntry bEm = longHashMapEntryValue.nextEntry;
            if (longHashMapEntryValue.key == i) {
                this.modCount++;
                this.numHashElements--;
                if (longHashMapEntry == longHashMapEntryValue) {
                    this.hashArray[k] = bEm;
                    if (textValue != null) {
                        return longHashMapEntryValue;
                    }
                }

                longHashMapEntry.nextEntry = bEm;
                return longHashMapEntryValue;
            }

            longHashMapEntry = longHashMapEntryValue;
            longHashMapEntryValue = bEm;
            if (textValue == null) {
                break;
            }
        }

        return longHashMapEntryValue;
    }

    private static int getHashIndex(int i, int j) {
        return i & j;
    }

    private static int getHashedKey(long i) {
        return (int) (i ^ i >>> 27);
    }

    static int access$000(long i) {
        return getHashedKey(i);
    }

    public Object getValueByKey(long i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int j = getHashedKey(i);
        String textValue = text;
        LongHashMapEntry longHashMapEntry = this.hashArray[getHashIndex(j, this.mask)];

        while (longHashMapEntry != null) {
            if (longHashMapEntry.key == i) {
                return longHashMapEntry.value;
            }

            longHashMapEntry = longHashMapEntry.nextEntry;
            if (textValue == null) {
                break;
            }
        }

        return null;
    }

    final LongHashMapEntry getEntry(long i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int j = getHashedKey(i);
        String textValue = text;
        LongHashMapEntry longHashMapEntry = this.hashArray[getHashIndex(j, this.mask)];

        while (longHashMapEntry != null) {
            if (longHashMapEntry.key == i) {
                return longHashMapEntry;
            }

            longHashMapEntry = longHashMapEntry.nextEntry;
            if (textValue == null) {
                break;
            }
        }

        return null;
    }

    public double cl() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int i = 0;
        String textValue = text;
        int j = 0;

        while (j < this.hashArray.length) {
            if (this.hashArray[j] != null) {
                i++;
            }

            j++;
            if (textValue == null) {
                break;
            }
        }

        return 1.0 * i / this.numHashElements;
    }

    public int getNumHashElements() {
        return this.numHashElements;
    }

    private static int hash(int i) {
        i ^= i >>> 20 ^ i >>> 12;
        return i ^ i >>> 7 ^ i >>> 4;
    }

    public LongHashMap() {
        this.percentUseable = 0.75F;
        this.mask = this.hashArray.length - 1;
    }

    public void add(long i, Object value) {
        int j = getHashedKey(i);
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int k = getHashIndex(j, this.mask);
        LongHashMapEntry longHashMapEntry = this.hashArray[k];
        String textValue = text;

        while (longHashMapEntry != null) {
            if (longHashMapEntry.key == i) {
                longHashMapEntry.value = value;
                return;
            }

            longHashMapEntry = longHashMapEntry.nextEntry;
            if (textValue == null) {
                break;
            }
        }

        this.modCount++;
        this.createKey(j, i, value, k);
    }

    public Object remove(long i) {
        LongHashMapEntry longHashMapEntry = this.removeKey(i);
        return longHashMapEntry == null ? null : longHashMapEntry.value;
    }

    private void createKey(int i, long j, Object object, int k) {
        LongHashMapEntry longHashMapEntry = this.hashArray[k];
        this.hashArray[k] = new LongHashMapEntry(i, j, object, longHashMapEntry);
        if (this.numHashElements++ >= this.capacity) {
            this.resizeTable(2 * this.hashArray.length);
        }
    }

    private void resizeTable(int i) {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        LongHashMapEntry[] bElValue = this.hashArray;
        String textValue = text;
        int j = bElValue.length;
        if (j == 1073741824) {
            this.capacity = Integer.MAX_VALUE;
            if (textValue != null) {
                return;
            }
        }

        LongHashMapEntry[] bElValue2 = new LongHashMapEntry[i];
        this.copyHashTableTo(bElValue2);
        this.hashArray = bElValue2;
        this.mask = this.hashArray.length - 1;
        float fx = i;
        this.getClass();
        this.capacity = (int) (fx * 0.75F);
    }

    public boolean containsItem(long i) {
        return this.getEntry(i) != null;
    }

    private void copyHashTableTo(LongHashMapEntry[] longHashMapEntryArray) {
        LongHashMapEntry[] bElValue = this.hashArray;
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int length = longHashMapEntryArray.length;
        String textValue = text;
        int j = 0;

        while (j < bElValue.length) {
            LongHashMapEntry longHashMapEntry = bElValue[j];
            if (longHashMapEntry != null) {
                bElValue[j] = null;

                LongHashMapEntry longHashMapEntryValue;
                do {
                    longHashMapEntryValue = longHashMapEntry.nextEntry;
                    int k = getHashIndex(longHashMapEntry.hash, length - 1);
                    longHashMapEntry.nextEntry = longHashMapEntryArray[k];
                    longHashMapEntryArray[k] = longHashMapEntry;
                    longHashMapEntry = longHashMapEntryValue;
                } while (longHashMapEntryValue != null);
            }

            j++;
            if (textValue == null) {
                break;
            }
        }
    }
}
