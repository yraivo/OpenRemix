package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBiped extends RenderLiving {
    protected float od;
    private static ResourceLocation DEFAULT_RES_LOC;

    protected ModelBiped modelBipedMain;

    protected ResourceLocation getEntityTexture(EntityLiving entityLiving) {
        return net.minecraft.client.renderer.entity.RenderBiped.DEFAULT_RES_LOC;
    }

    static {
        net.minecraft.client.renderer.entity.RenderBiped.DEFAULT_RES_LOC = new ResourceLocation("textures/entity/steve.png");
    }

    // region String decoding support

    // endregion

    public RenderBiped(RenderManager renderManagerValue, ModelBiped aBqValue, float f, float f1) {
        super(renderManagerValue, aBqValue, f);
        this.modelBipedMain = aBqValue;
        this.od = f1;
        this.addLayer(new LayerCustomHead(aBqValue.bipedHead));
    }

    public RenderBiped(RenderManager renderManagerValue, ModelBiped modelBiped, float f) {
        this(renderManagerValue, modelBiped, f, 1.0F);
        this.addLayer(new LayerHeldItem(this));
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityLiving) entity);
    }

    public void transformHeldFull3DItemLayer() {
        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
    }
}
