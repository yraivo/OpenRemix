package net.minecraft.client.renderer.texture;

import net.minecraft.client.renderer.StitcherException;
import net.minecraft.util.MathHelper;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Stitcher {
    private int maxWidth;
    private final Set setStitchHolders = Sets.newHashSetWithExpectedSize(256);
    private int maxTileDimension;
    private int currentWidth;
    private int maxHeight;
    private static String sT;

    private int currentHeight;
    private boolean forcePowerOf2;
    private int mipmapLevelStitcher;
    private final List stitchSlots = Lists.newArrayListWithCapacity(256);

    private boolean method_03e79b6afcd84821966d(StitcherHolder stitcherHolder) {
        int l;
        label158:
        {
            int l3 = TextureManager.method_7eec03da329a017ec143();
            int j = Math.min(stitcherHolder.getWidth(), stitcherHolder.getHeight());
            int i = l3;
            int k = this.currentWidth == 0 && this.currentHeight == 0 ? 1 : 0;
            if (this.forcePowerOf2) {
                int i1 = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
                int j1 = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
                int k1 = MathHelper.roundUpToPowerOfTwo(this.currentWidth + j);
                int l1 = MathHelper.roundUpToPowerOfTwo(this.currentHeight + j);
                int i2 = k1 <= this.maxWidth ? 1 : 0;
                int j2 = l1 <= this.maxHeight ? 1 : 0;
                if (i2 == 0 && j2 == 0) {
                    return false;
                }

                {
                    int k2 = i1 != k1 ? 1 : 0;
                    int l2 = j1 != l1 ? 1 : 0;
                    if ((k2 ^ l2) != 0) {
                        l = k2 == 0 ? 1 : 0;
                        if (!(i != 0)) {

                            l = i2 != 0 && i1 <= j1 ? 1 : 0;
                        }
                    } else {

                        l = i2 != 0 && i1 <= j1 ? 1 : 0;
                    }
                }

                if (i != 0) {
                    break label158;
                }
            }

            int j3 = this.currentWidth + j <= this.maxWidth ? 1 : 0;
            int k3 = this.currentHeight + j <= this.maxHeight ? 1 : 0;
            if (j3 == 0 && k3 == 0) {
                return false;
            }

            l = j3 == 0 || k == 0 && this.currentWidth > this.currentHeight ? 0 : 1;
        }

        int i3 = Math.max(stitcherHolder.getWidth(), stitcherHolder.getHeight());
        if (MathHelper.roundUpToPowerOfTwo((l == 0 ? this.currentHeight : this.currentWidth) + i3)
                > (l == 0 ? this.maxHeight : this.maxWidth)) {
            return false;
        }

        StitcherSlot stitcherSlot;
        if (l != 0) {
            if (stitcherHolder.getWidth() > stitcherHolder.getHeight()) {
                stitcherHolder.rotate();
            }

            if (this.currentHeight == 0) {
                this.currentHeight = stitcherHolder.getHeight();
            }

            stitcherSlot = new StitcherSlot(this.currentWidth, 0, stitcherHolder.getWidth(), this.currentHeight);
            this.currentWidth = this.currentWidth + stitcherHolder.getWidth();
        } else {
            stitcherSlot = new StitcherSlot(0, this.currentHeight, this.currentWidth, stitcherHolder.getHeight());
            this.currentHeight = this.currentHeight + stitcherHolder.getHeight();
        }

        stitcherSlot.addSlot(stitcherHolder);
        this.stitchSlots.add(stitcherSlot);
        return true;
    }

    public void addSprite(TextureAtlasSprite textureAtlasSprite) {
        StitcherHolder stitcherHolder =
                new StitcherHolder(textureAtlasSprite, this.mipmapLevelStitcher);
        if (this.maxTileDimension > 0) {
            stitcherHolder.setNewDimension(this.maxTileDimension);
        }

        this.setStitchHolders.add(stitcherHolder);
    }

    static int access$000(int i, int j) {
        return getMipmapDimension(i, j);
    }

    private static int getMipmapDimension(int i, int j) {
        return (i >> j) + ((i & (1 << j) - 1) == 0 ? 0 : 1) << j;
    }

    public List getStichSlots() {
        ArrayList arrayList = Lists.newArrayList();
        int j = TextureManager.method_beea3e16fe3ec0995eb9();
        Iterator iterator = this.stitchSlots.iterator();
        int i = j;

        while (iterator.hasNext()) {
            StitcherSlot stitcherSlot = (StitcherSlot) iterator.next();
            stitcherSlot.getAllStitchSlots(arrayList);
            if (i != 0) {
                break;
            }
        }

        ArrayList arrayListValue = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_5540 = (arrayList).iterator();
                (__recoveryIterator171_5540).hasNext(); ) {
            StitcherSlot stitcherSlotValue = (StitcherSlot) (__recoveryIterator171_5540).next();
            StitcherHolder stitchHolder = stitcherSlotValue.getStitchHolder();
            TextureAtlasSprite textureAtlasSprite = stitchHolder.getAtlasSprite();
            textureAtlasSprite.initSprite(
                    this.currentWidth,
                    this.currentHeight,
                    stitcherSlotValue.getOriginX(),
                    stitcherSlotValue.getOriginY(),
                    stitchHolder.isRotated());
            arrayListValue.add(textureAtlasSprite);
            if (i != 0) {
                break;
            }
        }

        return arrayListValue;
    }

    static {
        sT =
                "Unable to fit: %s, size: %dx%d, atlas: %dx%d, atlasMax: %dx%d - Maybe try a lower resolution resourcepack?";
    }

    // region String decoding support

    // endregion

    public int getCurrentWidth() {
        return this.currentWidth;
    }

    // region String decoding support

    // endregion

    private static StitcherException method_54aa3550ca98fe1eeefa(
            StitcherException stitcherException) {
        return stitcherException;
    }

    public Stitcher(int maxWidthValue, int j, boolean forcePowerOf2Value, int k, int l) {
        this.mipmapLevelStitcher = l;
        this.maxWidth = maxWidthValue;
        this.maxHeight = j;
        this.forcePowerOf2 = forcePowerOf2Value;
        this.maxTileDimension = k;
    }

    public void cj() {
        int l = TextureManager.method_beea3e16fe3ec0995eb9();
        StitcherHolder[] stitcherHolderArray =
                ((net.minecraft.client.renderer.texture.StitcherHolder[])
                        this.setStitchHolders.toArray(
                                new StitcherHolder[this.setStitchHolders.size()]));
        int i = l;
        Arrays.sort(stitcherHolderArray);
        StitcherHolder[] stitcherHolderArrayValue = stitcherHolderArray;
        int j = stitcherHolderArray.length;
        int k = 0;

        while (k < j) {
            StitcherHolder stitcherHolder = stitcherHolderArrayValue[k];
            if (!this.method_c609b89363fd61b85b69(stitcherHolder)) {
                String text =
                        String.format(
                                sT,
                                stitcherHolder.getAtlasSprite().getIconName(),
                                stitcherHolder.getAtlasSprite().getIconWidth(),
                                stitcherHolder.getAtlasSprite().getIconHeight(),
                                this.currentWidth,
                                this.currentHeight,
                                this.maxWidth,
                                this.maxHeight);
                throw new StitcherException(stitcherHolder, text);
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        if (this.forcePowerOf2) {
            this.currentWidth = MathHelper.roundUpToPowerOfTwo(this.currentWidth);
            this.currentHeight = MathHelper.roundUpToPowerOfTwo(this.currentHeight);
        }
    }

    private boolean method_c609b89363fd61b85b69(StitcherHolder stitcherHolder) {
        int k = TextureManager.method_7eec03da329a017ec143();
        int j = 0;
        int i = k;

        while (j < this.stitchSlots.size()) {
            if (((StitcherSlot) this.stitchSlots.get(j))
                    .addSlot(stitcherHolder)) {
                return true;
            }

            stitcherHolder.rotate();
            if (((StitcherSlot) this.stitchSlots.get(j))
                    .addSlot(stitcherHolder)) {
                return true;
            }

            stitcherHolder.rotate();
            j++;
            if (i == 0) {
                break;
            }
        }

        return this.method_03e79b6afcd84821966d(stitcherHolder);
    }

    public int getCurrentHeight() {
        return this.currentHeight;
    }
}
