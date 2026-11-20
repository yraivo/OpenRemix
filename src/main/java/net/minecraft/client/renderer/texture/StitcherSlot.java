package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;

import java.util.List;

public class StitcherSlot {
    private List subSlots;

    private int width;
    private int originY;

    private int originX;
    private int height;
    private StitcherHolder holder;

    public void getAllStitchSlots(List list) {
        int i = TextureManager.method_7eec03da329a017ec143();
        if (this.holder != null) {
            list.add(this);
            if (i != 0) {
                return;
            }
        }

        if (this.subSlots != null) {
            for (java.util.Iterator<?> __recoveryIterator171_1665 = (this.subSlots).iterator();
                    (__recoveryIterator171_1665).hasNext(); ) {
                StitcherSlot stitcherSlot = (StitcherSlot) (__recoveryIterator171_1665).next();
                stitcherSlot.getAllStitchSlots(list);
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public StitcherHolder getStitchHolder() {
        return this.holder;
    }

    public int getOriginX() {
        return this.originX;
    }


    public StitcherSlot(int originXValue, int j, int k, int l) {
        this.originX = originXValue;
        this.originY = j;
        this.width = k;
        this.height = l;
    }

    public int getOriginY() {
        return this.originY;
    }

    public boolean addSlot(StitcherHolder aGOValue) {
        int i = TextureManager.method_beea3e16fe3ec0995eb9();
        if (this.holder != null) {
            return false;
        }

        int j = aGOValue.getWidth();
        int k = aGOValue.getHeight();
        if (j > this.width || k > this.height) {
            return false;
        }

        if (j == this.width && k == this.height) {
            this.holder = aGOValue;
            return true;
        }

        label56:
        if (this.subSlots == null) {
            this.subSlots = Lists.newArrayListWithCapacity(1);
            this.subSlots.add(new StitcherSlot(this.originX, this.originY, j, k));
            int l = this.width - j;
            int i1 = this.height - k;
            if (i1 > 0 && l > 0) {
                {
                    int j1 = Math.max(this.height, l);
                    int k1 = Math.max(this.width, i1);
                    if (j1 >= k1) {
                        this.subSlots.add(new StitcherSlot(this.originX, this.originY + k, j, i1));
                        this.subSlots.add(
                                new StitcherSlot(this.originX + j, this.originY, l, this.height));
                        if (!(i == 0)) {

                            this.subSlots.add(new StitcherSlot(this.originX + j, this.originY, l, k));
                            this.subSlots.add(
                                    new StitcherSlot(this.originX, this.originY + k, this.width, i1));
                        }
                    } else {

                        this.subSlots.add(new StitcherSlot(this.originX + j, this.originY, l, k));
                        this.subSlots.add(
                                new StitcherSlot(this.originX, this.originY + k, this.width, i1));
                    }
                }

                if (i == 0) {
                    break label56;
                }
            }

            if (l == 0) {
                this.subSlots.add(new StitcherSlot(this.originX, this.originY + k, j, i1));
                if (i == 0) {
                    break label56;
                }
            }

            if (i1 == 0) {
                this.subSlots.add(new StitcherSlot(this.originX + j, this.originY, l, k));
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_29931 = (this.subSlots).iterator();
                (__recoveryIterator171_29931).hasNext(); ) {
            StitcherSlot stitcherSlot = (StitcherSlot) (__recoveryIterator171_29931).next();
            if (stitcherSlot.addSlot(aGOValue)) {
                return true;
            }

            if (i != 0) {
                break;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return "Slot{originX="
                + this.originX
                + ", originY="
                + this.originY
                + ", width="
                + this.width
                + ", height="
                + this.height
                + ", texture="
                + this.holder
                + ", subSlots="
                + this.subSlots
                + '}';
    }
}
