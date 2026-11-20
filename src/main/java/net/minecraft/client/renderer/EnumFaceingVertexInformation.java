package net.minecraft.client.renderer;

public class EnumFaceingVertexInformation {
    public int cz;
    public int a;
    public int c;

    EnumFaceingVertexInformation(int i, int j, int k, Object object) {
        this(i, j, k);
    }

    private EnumFaceingVertexInformation(int i, int j, int k) {
        this.a = i;
        this.cz = j;
        this.c = k;
    }
}
