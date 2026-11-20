package net.minecraft.client.resources.data;

public class FontMetadataSection implements IMetadataSection {
    private float[] charWidths;
    private float[] charSpacings;
    private float[] charLefts;

    public FontMetadataSection(float[] afloat, float[] afloat1, float[] afloat2) {
        this.charWidths = afloat;
        this.charLefts = afloat1;
        this.charSpacings = afloat2;
    }
}
