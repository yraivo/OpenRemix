package net.minecraft.client.renderer.texture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.MathHelper;
import optifine.OptiFineConfig;
import shadersmod.client.ShadersTex;

public class TextureClock extends TextureAtlasSprite {
    private double yL;
    private double aFf;

    public void updateAnimation() {
        int i = TextureManager.method_7eec03da329a017ec143();
        if (!this.framesTextureData.isEmpty()) {
            MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
            double d0 = 0.0;
            if (minecraftClient.world != null && minecraftClient.player != null) {
                float celestialAngle = minecraftClient.world.getCelestialAngle(1.0F);
                d0 = celestialAngle;
                if (!minecraftClient.world.provider.isSurfaceWorld()) {
                    d0 = Math.random();
                }
            }

            double d1 = d0 - this.aFf;

            while (d1 < -0.5) {
                d1++;
                if (i == 0) {
                    break;
                }
            }

            while (d1 >= 0.5) {
                d1--;
                if (i == 0) {
                    break;
                }
            }

            d1 = MathHelper.clamp(d1, -1.0, 1.0);
            this.yL += d1 * 0.1;
            this.yL *= 0.8;
            this.aFf = this.aFf + this.yL;
            int j =
                    (int) ((this.aFf + 1.0) * this.framesTextureData.size())
                            % this.framesTextureData.size();

            while (j < 0) {
                j = (j + this.framesTextureData.size()) % this.framesTextureData.size();
                if (i == 0) {
                    break;
                }
            }

            if (j != this.rC) {
                this.rC = j;
                if (OptiFineConfig.isShaders()) {
                    ShadersTex.uploadTexSub(
                            (int[][]) this.framesTextureData.get(this.rC),
                            this.width,
                            this.height,
                            this.originX,
                            this.originY,
                            false,
                            false);
                    if (i != 0) {
                        return;
                    }
                }

                TextureUtil.uploadTextureMipmap(
                        (int[][]) this.framesTextureData.get(this.rC),
                        this.width,
                        this.height,
                        this.originX,
                        this.originY,
                        false,
                        false);
            }
        }
    }

    public TextureClock(String text) {
        super(text);
    }
}
