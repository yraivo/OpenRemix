package optifine;

public class CacheLocalByte {
    private int offsetY;
    private int maxX = 18;
    private int maxZ;
    private byte[][][] cache;
    private byte[] lastZs;
    private int lastDz;
    private int offsetX;
    private int offsetZ;
    private int maxY = 128;

    public void resetCache() {
        int i1 = ReflectorMethod.cR();
        int j = 0;
        int i = i1;

        while (j < this.maxX) {
            byte[][] abyte = this.cache[j];
            int k = 0;

            while (true) {
                if (k < this.maxY) {
                    byte[] abyte1 = abyte[k];
                    int l = 0;

                    while (l < this.maxZ) {
                        abyte1[l] = -1;
                        l++;
                        if (i == 0) {
                            break;
                        }
                    }

                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }

    public byte get(int i, int j, int k) {
        try {
            this.lastZs = this.cache[i - this.offsetX][j - this.offsetY];
            this.lastDz = k - this.offsetZ;
            return this.lastZs[this.lastDz];
        } catch (ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) {
            arrayindexoutofboundsexception.printStackTrace();
            return -1;
        }
    }

    public CacheLocalByte(int i, int j, int k) {
        this.maxZ = 18;
        this.offsetX = 0;
        this.offsetY = 0;
        this.offsetZ = 0;
        this.cache = (byte[][][]) null;
        this.lastZs = null;
        this.lastDz = 0;
        this.maxX = i;
        this.maxY = j;
        this.maxZ = k;
        this.cache = new byte[i][j][k];
        this.resetCache();
    }

    public void setOffset(int i, int j, int k) {
        this.offsetX = i;
        this.offsetY = j;
        this.offsetZ = k;
        this.resetCache();
    }

    public void setLast(byte b0) {
        try {
            this.lastZs[this.lastDz] = b0;
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
