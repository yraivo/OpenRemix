package remix.ui.font;

public class GlyphMetrics {
    public int width;
    GlyphAtlasFont font;
    public int height;
    public int atlasY;
    public int atlasX;

    protected GlyphMetrics(GlyphAtlasFont fontValue) {
        this.font = fontValue;
    }
}
