package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;
import java.util.Random;

public abstract class ModelBase {
    private static String[] legacyModelFlowMarker;
    public boolean isRiding;
    public int textureWidth;
    private Map modelTextureMap;
    public List boxList;
    public int textureHeight;
    public float swingProgress;
    public boolean isChild = true;

    static {
        if (getLegacyModelFlowMarker() != null) {
            setLegacyModelFlowMarker(new String[2]);
        }
    }

    public ModelBase() {
        this.boxList = Lists.newArrayList();
        this.modelTextureMap = Maps.newHashMap();
        this.textureWidth = 64;
        this.textureHeight = 32;
    }

    public TextureOffset getTextureOffset(String text) {
        return (net.minecraft.client.model.TextureOffset) this.modelTextureMap.get(text);
    }

    public ModelRenderer getRandomModelBox(Random random) {
        return (net.minecraft.client.model.ModelRenderer) this.boxList.get(random.nextInt(this.boxList.size()));
    }

    public static String[] getLegacyModelFlowMarker() {
        return net.minecraft.client.model.ModelBase.legacyModelFlowMarker;
    }

    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {}

    public static void setLegacyModelFlowMarker(String[] astring) {
        net.minecraft.client.model.ModelBase.legacyModelFlowMarker = astring;
    }

    public void setRotationAngles(
            float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {}

    protected void setTextureOffset(String text, int p171_1, int p171_2) {
        this.modelTextureMap.put(text, new net.minecraft.client.model.TextureOffset(p171_1, p171_2));
    }

    public static void copyModelAngles(
            ModelRenderer modelRenderer, ModelRenderer modelRendererValue) {
        modelRendererValue.rotateAngleX = modelRenderer.rotateAngleX;
        modelRendererValue.rotateAngleY = modelRenderer.rotateAngleY;
        modelRendererValue.rotateAngleZ = modelRenderer.rotateAngleZ;
        modelRendererValue.rotationPointX = modelRenderer.rotationPointX;
        modelRendererValue.rotationPointY = modelRenderer.rotationPointY;
        modelRendererValue.rotationPointZ = modelRenderer.rotationPointZ;
    }

    public void setLivingAnimations(LivingEntity livingEntity, float f, float f1, float f2) {}

    public void setModelAttributes(ModelBase modelBase) {
        this.swingProgress = modelBase.swingProgress;
        this.isRiding = modelBase.isRiding;
        this.isChild = modelBase.isChild;
    }
}
