package net.minecraft.client.renderer;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;

public class ImageBufferDownload implements IImageBuffer {
    private int[] imageData;
    private int c;
    private int cz;

    public void skinAvailable() {}

    private void method_f418b0f77f1897435d21(int i, int j, int k, int l) {
        int i1 = WorldRenderer.getLegacyRenderFlowToken();
        if (!this.hasTransparency(i, j, k, l)) {
            int j1 = i;

            while (j1 < k) {
                int k1 = j;

                while (k1 < l) {
                    this.imageData[j1 + k1 * this.cz] = this.imageData[j1 + k1 * this.cz] & 16777215;
                    k1++;
                    if (i1 != 0) {
                        break;
                    }
                }

                j1++;
                if (i1 != 0) {
                    break;
                }
            }
        }
    }

    private boolean hasTransparency(int i, int j, int k, int l) {
        int i2 = WorldRenderer.getLegacyRenderFlowMarker();
        int j1 = i;
        int i1 = i2;

        while (j1 < k) {
            int k1 = j;

            while (true) {
                if (k1 < l) {
                    int l1 = this.imageData[j1 + k1 * this.cz];
                    if ((l1 >> 24 & 0xFF) < 128) {
                        return true;
                    }

                    k1++;
                    if (i1 != 0) {
                        continue;
                    }
                }

                j1++;
                if (i1 == 0) {
                    return false;
                }
                break;
            }
        }

        return false;
    }

    public BufferedImage parseUserSkin(BufferedImage bufferedimage) {
        int i = WorldRenderer.getLegacyRenderFlowToken();
        if (bufferedimage == null) {
            return null;
        }

        this.cz = 64;
        this.c = 64;
        int j = bufferedimage.getWidth();
        int k = bufferedimage.getHeight();
        byte b0 = 1;

        while (this.cz < j || this.c < k) {
            this.cz *= 2;
            this.c *= 2;
            b0 *= 2;
            if (i != 0) {
                break;
            }
        }

        BufferedImage bufferedimage1 = new BufferedImage(this.cz, this.c, 2);
        Graphics graphics = bufferedimage1.getGraphics();
        graphics.drawImage(bufferedimage, 0, 0, (ImageObserver) null);
        if (bufferedimage.getHeight() == 32 * b0) {
            graphics.drawImage(
                    bufferedimage1,
                    24 * b0,
                    48 * b0,
                    20 * b0,
                    52 * b0,
                    4 * b0,
                    16 * b0,
                    8 * b0,
                    20 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    28 * b0,
                    48 * b0,
                    24 * b0,
                    52 * b0,
                    8 * b0,
                    16 * b0,
                    12 * b0,
                    20 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    20 * b0,
                    52 * b0,
                    16 * b0,
                    64 * b0,
                    8 * b0,
                    20 * b0,
                    12 * b0,
                    32 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    24 * b0,
                    52 * b0,
                    20 * b0,
                    64 * b0,
                    4 * b0,
                    20 * b0,
                    8 * b0,
                    32 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    28 * b0,
                    52 * b0,
                    24 * b0,
                    64 * b0,
                    0 * b0,
                    20 * b0,
                    4 * b0,
                    32 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    32 * b0,
                    52 * b0,
                    28 * b0,
                    64 * b0,
                    12 * b0,
                    20 * b0,
                    16 * b0,
                    32 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    40 * b0,
                    48 * b0,
                    36 * b0,
                    52 * b0,
                    44 * b0,
                    16 * b0,
                    48 * b0,
                    20 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    44 * b0,
                    48 * b0,
                    40 * b0,
                    52 * b0,
                    48 * b0,
                    16 * b0,
                    52 * b0,
                    20 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    36 * b0,
                    52 * b0,
                    32 * b0,
                    64 * b0,
                    48 * b0,
                    20 * b0,
                    52 * b0,
                    32 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    40 * b0,
                    52 * b0,
                    36 * b0,
                    64 * b0,
                    44 * b0,
                    20 * b0,
                    48 * b0,
                    32 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    44 * b0,
                    52 * b0,
                    40 * b0,
                    64 * b0,
                    40 * b0,
                    20 * b0,
                    44 * b0,
                    32 * b0,
                    (ImageObserver) null);
            graphics.drawImage(
                    bufferedimage1,
                    48 * b0,
                    52 * b0,
                    44 * b0,
                    64 * b0,
                    52 * b0,
                    20 * b0,
                    56 * b0,
                    32 * b0,
                    (ImageObserver) null);
        }

        graphics.dispose();
        this.imageData = ((DataBufferInt) bufferedimage1.getRaster().getDataBuffer()).getData();
        this.method_366505046587ede28c94(0, 0, 32 * b0, 16 * b0);
        this.method_f418b0f77f1897435d21(32 * b0, 0, 64 * b0, 32 * b0);
        this.method_366505046587ede28c94(0, 16 * b0, 64 * b0, 32 * b0);
        this.method_f418b0f77f1897435d21(0, 32 * b0, 16 * b0, 48 * b0);
        this.method_f418b0f77f1897435d21(16 * b0, 32 * b0, 40 * b0, 48 * b0);
        this.method_f418b0f77f1897435d21(40 * b0, 32 * b0, 56 * b0, 48 * b0);
        this.method_f418b0f77f1897435d21(0, 48 * b0, 16 * b0, 64 * b0);
        this.method_366505046587ede28c94(16 * b0, 48 * b0, 48 * b0, 64 * b0);
        this.method_f418b0f77f1897435d21(48 * b0, 48 * b0, 64 * b0, 64 * b0);
        return bufferedimage1;
    }

    private void method_366505046587ede28c94(int i, int j, int k, int l) {
        int l1 = WorldRenderer.getLegacyRenderFlowMarker();
        int j1 = i;
        int i1 = l1;

        while (j1 < k) {
            int k1 = j;

            while (true) {
                if (k1 < l) {
                    this.imageData[j1 + k1 * this.cz] = this.imageData[j1 + k1 * this.cz] | 0xFF000000;
                    k1++;
                    if (i1 != 0) {
                        continue;
                    }
                }

                j1++;
                if (i1 == 0) {
                    return;
                }
                break;
            }
        }
    }
}
