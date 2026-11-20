package optifine;

public class RangeListInt {
    private RangeInt[] ranges = new RangeInt[0];
    private static String[] packedTextFragments;
    private static String b;

    public int getCountRanges() {
        return this.ranges.length;
    }

    static {
        optifine.RangeListInt.packedTextFragments = new String[3];
        optifine.RangeListInt.packedTextFragments[0] = new String("[");
        optifine.RangeListInt.packedTextFragments[1] = new String("]");
        optifine.RangeListInt.packedTextFragments[2] = new String("$h");
        b = ", ";
    }

    // region String decoding support

    // endregion

    public boolean isInRange(int i) {
        int l = ReflectorMethod.f();
        int k = 0;
        int j = l;

        while (k < this.ranges.length) {
            RangeInt rangeInt = this.ranges[k];
            if (rangeInt.isInRange(i)) {
                return true;
            }

            k++;
            if (j != 0) {
                break;
            }
        }

        return false;
    }

    public RangeInt getRange(int i) {
        return this.ranges[i];
    }

    @Override
    public String toString() {
        StringBuffer stringbuffer = new StringBuffer();
        int k = ReflectorMethod.cR();
        stringbuffer.append(packedTextFragments[0]);
        int i = k;
        int j = 0;

        while (j < this.ranges.length) {
            RangeInt rangeInt = this.ranges[j];
            if (j > 0) {
                stringbuffer.append(b);
            }

            stringbuffer.append(rangeInt.toString());
            j++;
            if (i == 0) {
                break;
            }
        }

        stringbuffer.append(packedTextFragments[1]);
        return stringbuffer.toString();
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void addRange(RangeInt rangeInt) {
        this.ranges = (RangeInt[]) OptiFineConfig.addObjectToArray(this.ranges, rangeInt);
    }
}
