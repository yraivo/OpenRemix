package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.NoRenderModule;

public class ArmorStandRenderer extends RendererLivingEntity {

    public static ResourceLocation TEXTURE_ARMOR_STAND;

    protected void rotateCorpse(LivingEntity livingEntity, float f, float f1, float f2) {
        this.rotateArmorStand((EntityArmorStand) livingEntity, f, f1, f2);
    }

    static {
        net.minecraft.client.renderer.entity.ArmorStandRenderer.TEXTURE_ARMOR_STAND =
                new ResourceLocation("textures/entity/armorstand/wood.png");
    }

    protected boolean canRenderName(LivingEntity livingEntity) {
        return this.canRenderArmorStandName((EntityArmorStand) livingEntity);
    }

    public ModelBase getMainModel() {
        return this.getArmorStandModel();
    }

    public ModelArmorStand getArmorStandModel() {
        return (ModelArmorStand) super.getMainModel();
    }

    protected boolean canRenderName(Entity entity) {
        return this.canRenderArmorStandName((EntityArmorStand) entity);
    }

    protected void rotateArmorStand(
            EntityArmorStand entityArmorStand, float f1, float f, float f2) {
        GlStateManager.rotate(180.0F - f, 0.0F, 1.0F, 0.0F);
    }

    public ArmorStandRenderer(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelArmorStand(), 0.0F);
        ArmorStandArmorLayer armorStandArmorLayer = new ArmorStandArmorLayer(this, this);
        this.addLayer(armorStandArmorLayer);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerCustomHead(this.getArmorStandModel().bipedHead));
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getArmorStandTexture((EntityArmorStand) entity);
    }

    protected ResourceLocation getArmorStandTexture(EntityArmorStand entityArmorStand) {
        return net.minecraft.client.renderer.entity.ArmorStandRenderer.TEXTURE_ARMOR_STAND;
    }

    protected boolean canRenderArmorStandName(EntityArmorStand entityArmorStand) {
        RemixClient.INSTANCE.getClientServices();
        NoRenderModule noRender = ClientServices.moduleManager.noRender;
        return entityArmorStand.getAlwaysRenderNameTag() && noRender.allowsArmorStandNameTags();
    }
}
