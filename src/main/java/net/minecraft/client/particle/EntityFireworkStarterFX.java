package net.minecraft.client.particle;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkStarterFX extends EntityFX {

    private EffectRenderer theEffectRenderer;

    private int fireworkAge;
    private static String[] packedTextFragments;
    boolean twinkle;
    private NBTTagList fireworkExplosions;

    public void onUpdate() {
        String text = EffectRenderer.getParticleFlowMarker();
        if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
            boolean flag;
            byte b0;
            label91:
            {
                flag = this.aY();
                b0 = 0;
                if (this.fireworkExplosions.tagCount() >= 3) {
                    b0 = 1;
                    if (text != null) {
                        break label91;
                    }
                }

                int i = 0;

                while (i < this.fireworkExplosions.tagCount()) {
                    CompoundTag compoundTag = this.fireworkExplosions.getCompoundTagAt(i);
                    if (compoundTag.getByte("Type") == 1) {
                        b0 = 1;
                        if (text != null) {
                            break;
                        }
                    }

                    i++;
                    if (text == null) {
                        break;
                    }
                }
            }

            String textValue =
                    "fireworks."
                            + (b0 != 0 ? "largeBlast" : "blast")
                            + (flag ? "_far" : packedTextFragments[0]);
            this.world.playSound(
                    this.posX,
                    this.posY,
                    this.posZ,
                    textValue,
                    20.0F,
                    0.95F + this.rand.nextFloat() * 0.1F,
                    true);
        }

        if (this.fireworkAge % 2 == 0
                && this.fireworkExplosions != null
                && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
            int k = this.fireworkAge / 2;
            CompoundTag compoundTagValue = this.fireworkExplosions.getCompoundTagAt(k);
            byte b1 = compoundTagValue.getByte("Type");
            boolean booleanValue = compoundTagValue.getBoolean("Trail");
            boolean booleanValueValue = compoundTagValue.getBoolean("Flicker");
            int[] aint = compoundTagValue.getIntArray("Colors");
            int[] aint1 = compoundTagValue.getIntArray("FadeColors");
            if (aint.length == 0) {
                aint = new int[] {ItemDye.dyeColors[0]};
            }

            label94:
            {
                if (b1 == 1) {
                    this.createBall(
                            0.5, 4, aint, aint1, booleanValue, booleanValueValue);
                    if (text != null) {
                        break label94;
                    }
                }

                if (b1 == 2) {
                    this.createShaped(
                            0.5,
                            new double[][] {
                                {0.0, 1.0},
                                {0.3455, 0.309},
                                {0.9511, 0.309},
                                {0.3795918367346939, -0.12653061224489795},
                                {0.6122448979591837, -0.8040816326530612},
                                {0.0, -0.35918367346938773}
                            },
                            aint,
                            aint1,
                            booleanValue,
                            booleanValueValue,
                            false);
                    if (text != null) {
                        break label94;
                    }
                }

                if (b1 == 3) {
                    this.createShaped(
                            0.5,
                            new double[][] {
                                {0.0, 0.2},
                                {0.2, 0.2},
                                {0.2, 0.6},
                                {0.6, 0.6},
                                {0.6, 0.2},
                                {0.2, 0.2},
                                {0.2, 0.0},
                                {0.4, 0.0},
                                {0.4, -0.6},
                                {0.2, -0.6},
                                {0.2, -0.4},
                                {0.0, -0.4}
                            },
                            aint,
                            aint1,
                            booleanValue,
                            booleanValueValue,
                            true);
                    if (text != null) {
                        break label94;
                    }
                }

                if (b1 == 4) {
                    this.createBurst(aint, aint1, booleanValue, booleanValueValue);
                    if (text != null) {
                        break label94;
                    }
                }

                this.createBall(
                        0.25, 2, aint, aint1, booleanValue, booleanValueValue);
            }

            int j = aint[0];
            float f = ((j & 0xFF0000) >> 16) / 255.0F;
            float f1 = ((j & 0xFF00) >> 8) / 255.0F;
            float f2 = ((j & 0xFF) >> 0) / 255.0F;
            EntityFireworkOverlayFX entityFireworkOverlayFX =
                    new EntityFireworkOverlayFX(this.world, this.posX, this.posY, this.posZ);
            entityFireworkOverlayFX.setRBGColorF(f, f1, f2);
            this.theEffectRenderer.addEffect(entityFireworkOverlayFX);
        }

        this.fireworkAge++;
        if (this.fireworkAge > this.particleMaxAge) {
            if (this.twinkle) {
                boolean flag3 = this.aY();
                String textValue2 = "fireworks." + (flag3 ? "twinkle_far" : "twinkle");
                this.world.playSound(
                        this.posX,
                        this.posY,
                        this.posZ,
                        textValue2,
                        20.0F,
                        0.9F + this.rand.nextFloat() * 0.15F,
                        true);
            }

            this.setDead();
        }
    }

    private boolean aY() {
        MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
        return minecraftClient == null
                || minecraftClient.getRenderViewEntity() == null
                || minecraftClient
                                .getRenderViewEntity()
                                .getDistanceSq(this.posX, this.posY, this.posZ)
                        >= 256.0;
    }

    private void createParticle(
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int[] aint,
            int[] aint1,
            boolean flag,
            boolean flag1) {
        EntityFireworkSparkFX entityFireworkSparkFX =
                new EntityFireworkSparkFX(
                        this.world, d0, d1, d2, d3, d4, d5, this.theEffectRenderer);
        entityFireworkSparkFX.setAlphaF(0.99F);
        entityFireworkSparkFX.setTrail(flag);
        entityFireworkSparkFX.setTwinkle(flag1);
        int i = this.rand.nextInt(aint.length);
        entityFireworkSparkFX.setColour(aint[i]);
        if (aint1 != null && aint1.length > 0) {
            entityFireworkSparkFX.setFadeColour(aint1[this.rand.nextInt(aint1.length)]);
        }

        this.theEffectRenderer.addEffect(entityFireworkSparkFX);
    }

    private void createBurst(int[] aint, int[] aint1, boolean flag, boolean flag1) {
        String text = EffectRenderer.getParticleFlowMarker();
        double d0 = this.rand.nextGaussian() * 0.05;
        double d1 = this.rand.nextGaussian() * 0.05;
        String textValue = text;
        int i = 0;

        while (i < 70) {
            double d2 = this.motionX * 0.5 + this.rand.nextGaussian() * 0.15 + d0;
            double d3 = this.motionZ * 0.5 + this.rand.nextGaussian() * 0.15 + d1;
            double d4 = this.motionY * 0.5 + this.rand.nextDouble() * 0.5;
            this.createParticle(
                    this.posX, this.posY, this.posZ, d2, d4, d3, aint, aint1, flag, flag1);
            i++;
            if (textValue == null) {
                break;
            }
        }
    }

    static {
        net.minecraft.client.particle.EntityFireworkStarterFX.packedTextFragments = new String[3];
        net.minecraft.client.particle.EntityFireworkStarterFX.packedTextFragments[0] = new String("");
    }

    private void createBall(
            double d0, int i, int[] aint, int[] aint1, boolean flag, boolean flag1) {
        double posXValue = this.posX;
        double posYValue = this.posY;
        String text = EffectRenderer.getParticleFlowMarker();
        double posZValue = this.posZ;
        String textValue = text;
        int j = -i;

        while (j <= i) {
            int k = -i;

            while (true) {
                if (k <= i) {
                    int l = -i;

                    while (l <= i) {
                        double d4 = k + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        double d5 = j + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        double d6 = l + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5;
                        double d7 =
                                MathHelper.sqrt(d4 * d4 + d5 * d5 + d6 * d6) / d0
                                        + this.rand.nextGaussian() * 0.05;
                        this.createParticle(
                                posXValue, posYValue, posZValue, d4 / d7, d5 / d7, d6 / d7, aint,
                                aint1, flag, flag1);
                        if (j != -i && j != i && k != -i && k != i) {
                            l += i * 2 - 1;
                        }

                        l++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    k++;
                    if (textValue != null) {
                        continue;
                    }
                }

                j++;
                if (textValue == null) {
                    return;
                }
                break;
            }
        }
    }

    public void renderParticle(
            WorldRenderer worldRenderer,
            Entity entity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5) {}

    private void createShaped(
            double d0,
            double[][] adouble,
            int[] aint,
            int[] aint1,
            boolean flag,
            boolean flag1,
            boolean flag2) {
        String text = EffectRenderer.getParticleFlowMarker();
        double d1 = adouble[0][0];
        double d2 = adouble[0][1];
        this.createParticle(
                this.posX, this.posY, this.posZ, d1 * d0, d2 * d0, 0.0, aint, aint1, flag, flag1);
        String textValue = text;
        float f = this.rand.nextFloat() * (float) Math.PI;
        double d3 = flag2 ? 0.034 : 0.34;
        int i = 0;

        while (i < 3) {
            double d4 = f + i * (float) Math.PI * d3;
            double d5 = d1;
            double d6 = d2;
            int j = 1;

            while (true) {
                if (j < adouble.length) {
                    double d7 = adouble[j][0];
                    double d8 = adouble[j][1];
                    double d9 = 0.25;

                    while (d9 <= 1.0) {
                        double d10 = (d5 + (d7 - d5) * d9) * d0;
                        double d11 = (d6 + (d8 - d6) * d9) * d0;
                        double d12 = d10 * Math.sin(d4);
                        d10 *= Math.cos(d4);
                        double d13 = -1.0;

                        while (d13 <= 1.0) {
                            this.createParticle(
                                    this.posX, this.posY, this.posZ, d10 * d13, d11, d12 * d13,
                                    aint, aint1, flag, flag1);
                            d13 += 2.0;
                            if (textValue == null) {
                                break;
                            }
                        }

                        d9 += 0.25;
                        if (textValue == null) {
                            break;
                        }
                    }

                    d5 = d7;
                    d6 = d8;
                    j++;
                    if (textValue != null) {
                        continue;
                    }
                }

                i++;
                if (textValue == null) {
                    return;
                }
                break;
            }
        }
    }

    public int getFXLayer() {
        return 0;
    }

    public EntityFireworkStarterFX(
            World worldValue,
            double d0,
            double d1,
            double d2,
            double motionXValue,
            double motionYValue,
            double motionZValue,
            EffectRenderer aLHValue,
            CompoundTag compoundTag) {
        super(worldValue, d0, d1, d2, 0.0, 0.0, 0.0);
        String text = EffectRenderer.getParticleFlowMarker();
        this.motionX = motionXValue;
        this.motionY = motionYValue;
        this.motionZ = motionZValue;
        this.theEffectRenderer = aLHValue;
        String textValue = text;
        this.particleMaxAge = 8;
        if (compoundTag != null) {
            this.fireworkExplosions = compoundTag.getTagList("Explosions", 10);
            if (this.fireworkExplosions.tagCount() == 0) {
                this.fireworkExplosions = null;
                if (textValue != null) {
                    return;
                }
            }

            this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;
            int i = 0;

            while (i < this.fireworkExplosions.tagCount()) {
                CompoundTag compoundTagValue = this.fireworkExplosions.getCompoundTagAt(i);
                if (compoundTagValue.getBoolean("Flicker")) {
                    this.twinkle = true;
                    this.particleMaxAge += 15;
                    if (textValue != null) {
                        break;
                    }
                }

                i++;
                if (textValue == null) {
                    break;
                }
            }
        }
    }
}
