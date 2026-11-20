package optifine;

public class RangeInt {

    private int min;

    private int max;

    public int getMax() {
        return this.max;
    }

    public RangeInt(int i, int j) {
        this.min = Math.min(i, j);
        this.max = Math.max(i, j);
    }

    public boolean isInRange(int i) {
        return i < this.min ? false : i <= this.max;
    }

    @Override
    public String toString() {
        return "min: " + this.min + ", max: " + this.max;
    }


    public int getMin() {
        return this.min;
    }
}
