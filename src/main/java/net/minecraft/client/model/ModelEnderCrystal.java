package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelEnderCrystal extends ModelBase {
    private ModelRenderer glass = new ModelRenderer(this, "glass");

    private ModelRenderer cube;

    private ModelRenderer base;

    public void render(Entity entity, float f4, float f, float f1, float f5, float f6, float f2) {
        GlStateManager.pushMatrix();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GlStateManager.translate(0.0F, -0.5F, 0.0F);
        if (this.base != null) {
            this.base.render(f2);
        }

        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.8F + f1, 0.0F);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        this.glass.render(f2);
        float f3 = 0.875F;
        GlStateManager.scale(f3, f3, f3);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
        this.glass.render(f2);
        GlStateManager.scale(f3, f3, f3);
        GlStateManager.rotate(60.0F, 0.7071F, 0.0F, 0.7071F);
        GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
        this.cube.render(f2);
        GlStateManager.popMatrix();
    }


    public ModelEnderCrystal(float f, boolean flag) {
        this.glass.setTextureOffset(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
        this.cube = new ModelRenderer(this, "cube");
        this.cube.setTextureOffset(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
        if (flag) {
            this.base = new ModelRenderer(this, "base");
            this.base.setTextureOffset(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12, 4, 12);
        }
    }
}
