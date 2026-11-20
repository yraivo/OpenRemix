package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;

public class RenderVillager extends RenderLiving {
    private static ResourceLocation librarianVillagerTextures;
    private static ResourceLocation smithVillagerTextures;
    private static ResourceLocation priestVillagerTextures;

    private static ResourceLocation butcherVillagerTextures;
    private static ResourceLocation farmerVillagerTextures;
    private static ResourceLocation villagerTextures;

    public ModelBase getMainModel() {
        return this.zk();
    }

    protected void preRenderCallback(LivingEntity livingEntity, float f) {
        this.preRenderCallback((EntityVillager) livingEntity, f);
    }

    public RenderVillager(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelVillager(0.0F), 0.5F);
        this.addLayer(new LayerCustomHead(this.zk().villagerHead));
    }

    protected void preRenderCallback(EntityVillager entityVillager, float f1) {
        float f;
        {
            Render.getLegacyEntityRenderState();

            f = 0.9375F;

            if (entityVillager.getGrowingAge() < 0) {
                f = (float) (f * 0.5);
                this.shadowSize = 0.25F;

                
            } else {

                this.shadowSize = 0.5F;
            }
        }

        GlStateManager.scale(f, f, f);
    }

    public ModelVillager zk() {
        return (ModelVillager) super.getMainModel();
    }

    // region String decoding support

    // endregion

    protected ResourceLocation getEntityTexture(EntityVillager entityVillager) {
        switch (entityVillager.jvm171_f_bd9fdf88541948()) {
            case 0:
                return net.minecraft.client.renderer.entity.RenderVillager.farmerVillagerTextures;
            case 1:
                return net.minecraft.client.renderer.entity.RenderVillager.librarianVillagerTextures;
            case 2:
                return net.minecraft.client.renderer.entity.RenderVillager.priestVillagerTextures;
            case 3:
                return net.minecraft.client.renderer.entity.RenderVillager.smithVillagerTextures;
            case 4:
                return net.minecraft.client.renderer.entity.RenderVillager.butcherVillagerTextures;
            default:
                return net.minecraft.client.renderer.entity.RenderVillager.villagerTextures;
        }
    }

    static {
        net.minecraft.client.renderer.entity.RenderVillager.villagerTextures =
                new ResourceLocation("textures/entity/villager/villager.png");
        net.minecraft.client.renderer.entity.RenderVillager.farmerVillagerTextures =
                new ResourceLocation("textures/entity/villager/farmer.png");
        net.minecraft.client.renderer.entity.RenderVillager.librarianVillagerTextures =
                new ResourceLocation("textures/entity/villager/librarian.png");
        net.minecraft.client.renderer.entity.RenderVillager.priestVillagerTextures =
                new ResourceLocation("textures/entity/villager/priest.png");
        net.minecraft.client.renderer.entity.RenderVillager.smithVillagerTextures =
                new ResourceLocation("textures/entity/villager/smith.png");
        net.minecraft.client.renderer.entity.RenderVillager.butcherVillagerTextures =
                new ResourceLocation("textures/entity/villager/butcher.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityVillager) entity);
    }

    // region String decoding support

    // endregion
}
