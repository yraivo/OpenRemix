package optifine;

public class CacheLocal {
    private int offsetX;
    private int maxX = 18;
    private int offsetY;
    private int[][][] cache;
    private int maxZ;
    private int lastDz;
    private int[] lastZs;
    private int maxY = 128;
    private int offsetZ;

    public int get(int i, int j, int k) {
        try {
            this.lastZs = this.cache[i - this.offsetX][j - this.offsetY];
            this.lastDz = k - this.offsetZ;
            return this.lastZs[this.lastDz];
        } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            arrayindexoutofboundsexception.printStackTrace();
            return -1;
        }
    }

    public void resetCache() {
        int i1 = ReflectorMethod.f();
        int j = 0;
        int i = i1;

        while (j < this.maxX) {
            int[][] aint = this.cache[j];
            int k = 0;

            while (true) {
                if (k < this.maxY) {
                    int[] aint1 = aint[k];
                    int l = 0;

                    while (l < this.maxZ) {
                        aint1[l] = -1;
                        l++;
                        if (i != 0) {
                            break;
                        }
                    }

                    k++;
                    if (i == 0) {
                        continue;
                    }
                }

                j++;
                if (i != 0) {
                    return;
                }
                break;
            }
        }
    }

    public void setOffset(int i, int j, int k) {
        this.offsetX = i;
        this.offsetY = j;
        this.offsetZ = k;
        this.resetCache();
    }

    public void setLast(int i) {
        try {
            this.lastZs[this.lastDz] = i;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public CacheLocal(int i, int j, int k) {
        this.maxZ = 18;
        this.offsetX = 0;
        this.offsetY = 0;
        this.offsetZ = 0;
        this.cache = (int[][][]) null;
        this.lastZs = null;
        this.lastDz = 0;
        this.maxX = i;
        this.maxY = j;
        this.maxZ = k;
        this.cache = new int[i][j][k];
        this.resetCache();
    }
}
