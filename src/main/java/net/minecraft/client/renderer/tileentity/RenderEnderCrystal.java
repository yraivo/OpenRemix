package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEnderCrystal extends Render {
    private static ResourceLocation enderCrystalTextures;

    private ModelBase modelEnderCrystal = new ModelEnderCrystal(0.0F, true);

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_5de75094e1db0a39177d((EntityEnderCrystal) entity);
    }

    static {
        net.minecraft.client.renderer.tileentity.RenderEnderCrystal.enderCrystalTextures =
                new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
    }

    public void doRender(
            EntityEnderCrystal entityEnderCrystal,
            double d0,
            double d1,
            double d2,
            float f,
            float f1) {
        float f2 = entityEnderCrystal.innerRotation + f1;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float) d0, (float) d1, (float) d2);
        this.bindTexture(net.minecraft.client.renderer.tileentity.RenderEnderCrystal.enderCrystalTextures);
        float f3 = MathHelper.sin(f2 * 0.2F) / 2.0F + 0.5F;
        f3 += f3 * f3;
        this.modelEnderCrystal.render(
                entityEnderCrystal, 0.0F, f2 * 3.0F, f3 * 0.2F, 0.0F, 0.0F, 0.0625F);
        GlStateManager.popMatrix();
        super.doRender(entityEnderCrystal, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    protected ResourceLocation method_5de75094e1db0a39177d(EntityEnderCrystal entityEnderCrystal) {
        return net.minecraft.client.renderer.tileentity.RenderEnderCrystal.enderCrystalTextures;
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityEnderCrystal) entity, d0, d1, d2, f, f1);
    }

    // region String decoding support

    // endregion

    public RenderEnderCrystal(RenderManager renderManagerValue) {
        super(renderManagerValue);
        this.shadowSize = 0.5F;
    }
}
