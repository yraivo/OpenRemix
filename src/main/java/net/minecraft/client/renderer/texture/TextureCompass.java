package net.minecraft.client.renderer.texture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import optifine.OptiFineConfig;
import shadersmod.client.ShadersTex;

public class TextureCompass extends TextureAtlasSprite {
    public double currentAngle;
    public static String yz;
    public double angleDelta;

    public TextureCompass(String text) {
        super(text);
        net.minecraft.client.renderer.texture.TextureCompass.yz = text;
    }

    public void updateCompass(
            World world, double d0, double d1, double d2, boolean flag, boolean flag1) {
        int i = TextureManager.method_7eec03da329a017ec143();
        if (!this.framesTextureData.isEmpty()) {
            double aFfValue = 0.0;
            if (world != null && !flag) {
                BlockPos blockPos = world.getSpawnPoint();
                double d4 = blockPos.getX() - d0;
                double d5 = blockPos.getZ() - d1;
                d2 %= 360.0;
                aFfValue = -((d2 - 90.0) * Math.PI / 180.0 - Math.atan2(d5, d4));
                if (!world.provider.isSurfaceWorld()) {
                    aFfValue = Math.random() * Math.PI * 2.0;
                }
            }

            label62:
            {
                if (flag1) {
                    this.currentAngle = aFfValue;
                    if (i != 0) {
                        break label62;
                    }
                }

                double d6 = aFfValue - this.currentAngle;

                while (d6 < -Math.PI) {
                    d6 += Math.PI * 2;
                    if (i == 0) {
                        break;
                    }
                }

                while (d6 >= Math.PI) {
                    d6 -= Math.PI * 2;
                    if (i == 0) {
                        break;
                    }
                }

                d6 = MathHelper.clamp(d6, -1.0, 1.0);
                this.angleDelta += d6 * 0.1;
                this.angleDelta *= 0.8;
                this.currentAngle = this.currentAngle + this.angleDelta;
            }

            int j =
                    (int) ((this.currentAngle / (Math.PI * 2) + 1.0) * this.framesTextureData.size())
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

    public void updateAnimation() {
        int j = TextureManager.method_beea3e16fe3ec0995eb9();
        MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
        int i = j;
        if (minecraftClient.world != null && minecraftClient.player != null) {
            this.updateCompass(
                    minecraftClient.world,
                    minecraftClient.player.posX,
                    minecraftClient.player.posZ,
                    minecraftClient.player.rotationYaw,
                    false,
                    false);
            if (i == 0) {
                return;
            }
        }

        this.updateCompass((World) null, 0.0, 0.0, 0.0, true, false);
    }
}
