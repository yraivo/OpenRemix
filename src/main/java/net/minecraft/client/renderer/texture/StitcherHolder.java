package net.minecraft.client.renderer.texture;

public class StitcherHolder implements Comparable {
    private float scaleFactor = 1.0F;
    private TextureAtlasSprite theTexture;
    private int mipmapLevelHolder;

    private int height;

    private int width;
    private boolean rotated;


    @Override
    public String toString() {
        return "Holder{width="
                + this.width
                + ", height="
                + this.height
                + ", name="
                + this.theTexture.getIconName()
                + '}';
    }

    public TextureAtlasSprite getAtlasSprite() {
        return this.theTexture;
    }

    public int compareTo(StitcherHolder stitcherHolder) {
        int i = TextureManager.method_7eec03da329a017ec143();
        if (this.getHeight() == stitcherHolder.getHeight()) {
            if (this.getWidth() == stitcherHolder.getWidth()) {
                if (this.theTexture.getIconName() == null) {
                    return stitcherHolder.theTexture.getIconName() == null ? 0 : -1;
                }

                return this.theTexture
                        .getIconName()
                        .compareTo(stitcherHolder.theTexture.getIconName());
            }

            int j = this.getWidth() < stitcherHolder.getWidth() ? 1 : -1;
            if (i != 0) {
                return j;
            }
        }

        return this.getHeight() < stitcherHolder.getHeight() ? 1 : -1;
    }

    public void setNewDimension(int i) {
        if (this.width > i && this.height > i) {
            this.scaleFactor = (float) i / Math.min(this.width, this.height);
        }
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((StitcherHolder) object);
    }

    public void rotate() {
        this.rotated = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.rotated ? 1 : 0));
    }

    public int getHeight() {
        return this.rotated
                ? Stitcher.access$000((int) (this.width * this.scaleFactor), this.mipmapLevelHolder)
                : Stitcher.access$000(
                        (int) (this.height * this.scaleFactor), this.mipmapLevelHolder);
    }

    public int getWidth() {
        return this.rotated
                ? Stitcher.access$000(
                        (int) (this.height * this.scaleFactor), this.mipmapLevelHolder)
                : Stitcher.access$000(
                        (int) (this.width * this.scaleFactor), this.mipmapLevelHolder);
    }

    public StitcherHolder(TextureAtlasSprite aFlValue, int mipmapLevelHolderValue) {
        this.theTexture = aFlValue;
        this.width = aFlValue.getIconWidth();
        this.height = aFlValue.getIconHeight();
        this.mipmapLevelHolder = mipmapLevelHolderValue;
        this.rotated =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (Stitcher.access$000(this.height, mipmapLevelHolderValue)
                                        > Stitcher.access$000(this.width, mipmapLevelHolderValue)
                                ? 1
                                : 0));
    }

    public boolean isRotated() {
        return this.rotated;
    }
}
