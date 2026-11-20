package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCreeperCharge;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderCreeper extends RenderLiving {

    private static ResourceLocation creeperTextures;

    protected void method_0fc8193b74d19b47d87a(EntityCreeper entityCreeper, float f) {
        float creeperFlashIntensity = entityCreeper.getCreeperFlashIntensity(f);
        float f2 =
                1.0F
                        + MathHelper.sin(creeperFlashIntensity * 100.0F)
                                * creeperFlashIntensity
                                * 0.01F;
        creeperFlashIntensity = MathHelper.clamp(creeperFlashIntensity, 0.0F, 1.0F);
        creeperFlashIntensity *= creeperFlashIntensity;
        creeperFlashIntensity *= creeperFlashIntensity;
        float f3 = (1.0F + creeperFlashIntensity * 0.4F) * f2;
        float f4 = (1.0F + creeperFlashIntensity * 0.1F) / f2;
        GlStateManager.scale(f3, f4, f3);
    }

    public RenderCreeper(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelCreeper(), 0.5F);
        this.addLayer(new LayerCreeperCharge(this));
    }

    protected int getColorMultiplier(LivingEntity livingEntity, float f, float f1) {
        return this.method_1454630832dfef9bfd98((EntityCreeper) livingEntity, f, f1);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityCreeper) entity);
    }

    // region String decoding support

    // endregion

    protected int method_1454630832dfef9bfd98(EntityCreeper entityCreeper, float f2, float f) {
        float creeperFlashIntensity = entityCreeper.getCreeperFlashIntensity(f);
        if ((int) (creeperFlashIntensity * 10.0F) % 2 == 0) {
            return 0;
        }

        int i = (int) (creeperFlashIntensity * 0.2F * 255.0F);
        i = MathHelper.clamp(i, 0, 255);
        return i << 24 | 16777215;
    }

    static {
        net.minecraft.client.renderer.entity.RenderCreeper.creeperTextures =
                new ResourceLocation("textures/entity/creeper/creeper.png");
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.method_0fc8193b74d19b47d87a((EntityCreeper) livingEntity, f);
    }

    protected ResourceLocation getEntityTexture(EntityCreeper entityCreeper) {
        return net.minecraft.client.renderer.entity.RenderCreeper.creeperTextures;
    }
}
