package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class PlayerItemModel {
    public static final int tB = 2;
    private ResourceLocation textureLocation;
    public static final int rW = 5;
    private Dimension textureSize = null;
    private static String ks;
    public static final int mu = 1;
    private boolean usePlayerTexture = false;
    private BufferedImage textureImage;
    public static final int sR = 0;

    public static final int rC = 6;
    public static final int mE = 4;
    private DynamicTexture texture;
    private ResourceLocation locationMissing;
    public static final int mF = 3;
    private PlayerItemRenderer[] modelRenderers = new PlayerItemRenderer[0];

    public void render(
            ModelBiped modelBiped,
            AbstractClientPlayer abstractClientPlayer,
            float f,
            float f1) {
        int i;
        label40:
        {
            int k = ReflectorMethod.f();
            TextureManager textureManager = OptiFineConfig.getTextureManager();
            i = k;
            if (this.usePlayerTexture) {
                textureManager.bindTexture(abstractClientPlayer.getLocationSkin());
                if (i == 0) {
                    break label40;
                }
            }

            if (this.textureLocation != null) {
                if (this.texture == null && this.textureImage != null) {
                    this.texture = new DynamicTexture(this.textureImage);
                    MinecraftClient.getMinecraft()
                            .getTextureManager()
                            .loadTexture(this.textureLocation, this.texture);
                }

                textureManager.bindTexture(this.textureLocation);
                if (i == 0) {
                    break label40;
                }
            }

            textureManager.bindTexture(this.locationMissing);
        }

        int j = 0;

        while (j < this.modelRenderers.length) {
            PlayerItemRenderer playerItemRenderer = this.modelRenderers[j];
            GlStateManager.pushMatrix();
            if (abstractClientPlayer.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            playerItemRenderer.render(modelBiped, f);
            GlStateManager.popMatrix();
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public DynamicTexture getTexture() {
        return this.texture;
    }

    public static ModelRenderer getAttachModel(ModelBiped modelBiped, int i) {
        switch (i) {
            case 0:
                return modelBiped.bipedBody;
            case 1:
                return modelBiped.bipedHead;
            case 2:
                return modelBiped.bipedLeftArm;
            case 3:
                return modelBiped.bipedRightArm;
            case 4:
                return modelBiped.bipedLeftLeg;
            case 5:
                return modelBiped.bipedRightLeg;
            default:
                return null;
        }
    }

    public BufferedImage getTextureImage() {
        return this.textureImage;
    }

    // region String decoding support

    // endregion

    public boolean isUsePlayerTexture() {
        return this.usePlayerTexture;
    }

    public void setTextureLocation(ResourceLocation resourceLocation) {
        this.textureLocation = resourceLocation;
    }

    // region String decoding support

    // endregion

    public void setTextureImage(BufferedImage bufferedimage) {
        this.textureImage = bufferedimage;
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    static {
        ks = "textures/blocks/wool_colored_red.png";
    }

    public PlayerItemModel(
            Dimension dimension,
            boolean usePlayerTextureValue,
            PlayerItemRenderer[] modelRenderersValue) {
        this.textureLocation = null;
        this.textureImage = null;
        this.texture = null;
        this.locationMissing = new ResourceLocation(ks);
        this.textureSize = dimension;
        this.usePlayerTexture = usePlayerTextureValue;
        this.modelRenderers = modelRenderersValue;
    }
}
