package net.minecraft.client.renderer.entity;

import net.minecraft.block.material.Material;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.layers.LayerEndermanEyes;
import net.minecraft.client.renderer.entity.layers.LayerHeldBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class RenderEnderman extends RenderLiving {
    private Random rnd = new Random();
    private static ResourceLocation endermanTextures;

    private ModelEnderman endermanModel = (ModelEnderman) this.mainModel;

    public void doRender(
            LivingEntity livingEntity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityEnderman) livingEntity, d0, d1, d2, f, f1);
    }

    public RenderEnderman(RenderManager renderManagerValue) {
        super(renderManagerValue, new ModelEnderman(0.0F), 0.5F);
        this.addLayer(new LayerEndermanEyes(this));
        this.addLayer(new LayerHeldBlock(this));
    }

    public void doRender(
            EntityLiving entityLiving, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityEnderman) entityLiving, d0, d1, d2, f, f1);
    }

    protected ResourceLocation method_948a5b96ba4b1b8bddb4(EntityEnderman entityEnderman) {
        return net.minecraft.client.renderer.entity.RenderEnderman.endermanTextures;
    }

    protected ResourceLocation getEntityTexture(Entity entity) {
        return this.method_948a5b96ba4b1b8bddb4((EntityEnderman) entity);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void doRender(Entity entity, double d0, double d1, double d2, float f, float f1) {
        this.doRender((EntityEnderman) entity, d0, d1, d2, f, f1);
    }

    static {
        net.minecraft.client.renderer.entity.RenderEnderman.endermanTextures =
                new ResourceLocation("textures/entity/enderman/enderman.png");
    }

    public void doRender(
            EntityEnderman entityEnderman, double d0, double d1, double d2, float f, float f1) {
        this.endermanModel.isCarrying =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (entityEnderman.getHeldBlockState().getBlock().getMaterial() != Material.AIR
                                ? 1
                                : 0));
        this.endermanModel.isAttacking = entityEnderman.isScreaming();
        if (entityEnderman.isScreaming()) {
            double d3 = 0.02;
            d0 += this.rnd.nextGaussian() * d3;
            d2 += this.rnd.nextGaussian() * d3;
        }

        super.doRender(entityEnderman, d0, d1, d2, f, f1);
    }
}
