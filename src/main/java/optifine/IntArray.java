package optifine;

public class IntArray {
    private int limit;
    private int[] array = null;
    private int position = 0;

    public void putAt(int i, int j) {
        this.array[i] = j;
        if (this.limit < i) {
            this.limit = i;
        }
    }

    public int get(int i) {
        return this.array[i];
    }

    public void clear() {
        this.position = 0;
        this.limit = 0;
    }

    public int getLimit() {
        return this.limit;
    }

    public int[] getArray() {
        return this.array;
    }

    public void put(int i) {
        this.array[this.position] = i;
        this.position++;
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }

    public void setPosition(int i) {
        this.position = i;
    }

    public void put(int[] aint) {
        int l = ReflectorMethod.cR();
        int j = aint.length;
        int i = l;
        int k = 0;

        while (k < j) {
            this.array[this.position] = aint[k];
            this.position++;
            k++;
            if (i == 0) {
                break;
            }
        }

        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }

    public int getPosition() {
        return this.position;
    }

    public IntArray(int i) {
        this.limit = 0;
        this.array = new int[i];
    }
}
