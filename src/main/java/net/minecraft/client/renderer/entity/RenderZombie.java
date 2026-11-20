package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.layers.LayerVillagerArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Lists;

import java.util.List;

public class RenderZombie extends RenderBiped {
    private static ResourceLocation zombieTextures;

    private List aDZ;
    private List aEa;
    private ModelZombieVillager zombieVillagerModel;
    private ModelBiped aEc;
    private static ResourceLocation zombieVillagerTextures;

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        this.method_e79c278f3660a138faf2((EntityZombie) entityLiving, d0, d1, d2, f, f1);
    }

    protected ResourceLocation getEntityTexture(EntityLiving entityLiving) {
        return this.getEntityTexture((EntityZombie) entityLiving);
    }

    protected ResourceLocation getEntityTexture(EntityZombie entityZombie) {
        return entityZombie.isVillager()
                ? net.minecraft.client.renderer.entity.RenderZombie.zombieVillagerTextures
                : net.minecraft.client.renderer.entity.RenderZombie.zombieTextures;
    }

    // region String decoding support

    // endregion

    public RenderZombie(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelZombie(), 0.5F, 1.0F);
        LayerRenderer layerRenderer = (LayerRenderer) this.layerRenderers.get(0);
        this.aEc = this.modelBipedMain;
        this.zombieVillagerModel = new ModelZombieVillager();
        this.addLayer(new LayerHeldItem(this));
        ZombieArmorLayer zombieArmorLayer = new ZombieArmorLayer(this, this);
        this.addLayer(zombieArmorLayer);
        this.aEa = Lists.newArrayList(this.layerRenderers);
        if (layerRenderer instanceof LayerCustomHead) {
            this.removeLayer(layerRenderer);
            this.addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
        }

        this.removeLayer(zombieArmorLayer);
        this.addLayer(new LayerVillagerArmor(this));
        this.aDZ = Lists.newArrayList(this.layerRenderers);
    }

    protected void rotateCorpse(EntityZombie entityZombie, float f, float f1, float f2) {
        if (entityZombie.isConverting()) {
            f1 += (float) (Math.cos(entityZombie.ticksExisted * 3.25) * Math.PI * 0.25);
        }

        super.rotateCorpse(entityZombie, f, f1, f2);
    }

    private void method_3392a67c38227c6f0a47(EntityZombie entityZombie) {
        {
            Render.getLegacyEntityRenderState();

            if (entityZombie.isVillager()) {
                this.mainModel = this.zombieVillagerModel;
                this.layerRenderers = this.aDZ;

                
            } else {

                this.mainModel = this.aEc;
                this.layerRenderers = this.aEa;
            }
        }

        this.modelBipedMain = (ModelBiped) this.mainModel;
    }

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.method_e79c278f3660a138faf2((EntityZombie) entity, d0, d1, d2, f, f1);
    }

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.method_e79c278f3660a138faf2((EntityZombie) livingEntity, d0, d1, d2, f, f1);
    }

    static {
        net.minecraft.client.renderer.entity.RenderZombie.zombieTextures =
                new ResourceLocation("textures/entity/zombie/zombie.png");
        net.minecraft.client.renderer.entity.RenderZombie.zombieVillagerTextures =
                new ResourceLocation("textures/entity/zombie/zombie_villager.png");
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.getEntityTexture((EntityZombie) entity);
    }

    // region String decoding support

    // endregion

    public void method_e79c278f3660a138faf2(
            EntityZombie entityZombie, double d0, double d1, double d2, float f, float f1) {
        this.method_3392a67c38227c6f0a47(entityZombie);
        super.doRender(entityZombie, d0, d1, d2, f, f1);
    }

    protected void rotateCorpse(LivingEntity livingEntity, float f, float f1, float f2) {
        this.rotateCorpse((EntityZombie) livingEntity, f, f1, f2);
    }
}
