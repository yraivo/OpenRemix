package net.minecraft.client.renderer.block.model;

public class BlockFaceUV {

    public float[] uvs;
    public int rotation;

    public void setUvs(float[] afloat) {
        if (this.uvs == null) {
            this.uvs = afloat;
        }
    }

    private static NullPointerException method_339a46a0855ea6bd2c63(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }

    public int getReverseRotatedVertexIndex(int i) {
        return (i + (4 - this.rotation / 90)) % 4;
    }

    public float getVertexV(int i) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }

        int j = this.getRotatedVertexIndex(i);
        return j != 0 && j != 3 ? this.uvs[3] : this.uvs[1];
    }

    private int getRotatedVertexIndex(int i) {
        return (i + this.rotation / 90) % 4;
    }

    public BlockFaceUV(float[] afloat, int i) {
        this.uvs = afloat;
        this.rotation = i;
    }

    public float getVertexU(int i) {
        if (this.uvs == null) {
            throw new NullPointerException("uvs");
        }

        int j = this.getRotatedVertexIndex(i);
        return j != 0 && j != 1 ? this.uvs[2] : this.uvs[0];
    }


}
