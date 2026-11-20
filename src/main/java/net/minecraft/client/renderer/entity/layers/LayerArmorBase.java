package net.minecraft.client.renderer.entity.layers;

import com.google.common.collect.Maps;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import optifine.CustomItems;
import optifine.OptiFineConfig;
import optifine.Reflector;

import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

import java.util.Map;

public abstract class LayerArmorBase implements LayerRenderer {

    private float colorB;
    private float colorR;
    private boolean field_177193_i;
    protected ModelBase field_177189_c;
    private RendererLivingEntity renderer;
    protected static ResourceLocation ENCHANTED_ITEM_GLINT_RES;
    private float colorG;
    private static Map ARMOR_TEXTURE_RES_MAP;
    private static String[] packedTextFragments;
    protected ModelBase field_177186_d;
    private float alpha = 1.0F;
    private static int[] Cu;

    protected abstract void func_177179_a(ModelBase modelBase, int i);

    public static int[] method_650532ba484532b3efb0() {
        return Cu;
    }

    public ResourceLocation getArmorResource(
            Entity entity, ItemStack itemStack, int i, String text) {
        method_650532ba484532b3efb0();
        ItemArmor item = (ItemArmor) itemStack.getItem();
        String textValue = ((ItemArmor) itemStack.getItem()).getArmorMaterial().getName();
        String textValue2 = "minecraft";
        int j = textValue.indexOf(58);
        if (j != -1) {
            textValue2 = textValue.substring(0, j);
            textValue = textValue.substring(j + 1);
        }

        String textValue3 =
                String.format(
                        "%s:textures/models/armor/%s_layer_%d%s.png",
                        textValue2,
                        textValue,
                        i == 2 ? 2 : 1,
                        text == null ? packedTextFragments[1] : String.format("_%s", text));
        textValue3 =
                Reflector.callString(
                        Reflector.ForgeHooksClient_getArmorTexture,
                        new Object[] {entity, itemStack, textValue3, i, text});
        ResourceLocation resourceLocation =
                (ResourceLocation)
                        net.minecraft.client.renderer.entity.layers.LayerArmorBase
                                .ARMOR_TEXTURE_RES_MAP
                                .get(textValue3);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(textValue3);
            net.minecraft.client.renderer.entity.layers.LayerArmorBase.ARMOR_TEXTURE_RES_MAP.put(
                    textValue3, resourceLocation);
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(new int[1]);
        }

        return resourceLocation;
    }

    // region String decoding support

    // endregion

    public LayerArmorBase(RendererLivingEntity aDOValue) {
        this.colorR = 1.0F;
        this.colorG = 1.0F;
        this.colorB = 1.0F;
        this.renderer = aDOValue;
        this.initArmor();
    }

    public ModelBase func_177175_a(int i) {
        return this.isSlotForLeggings(i) ? this.field_177189_c : this.field_177186_d;
    }

    static {
        net.minecraft.client.renderer.entity.layers.LayerArmorBase.packedTextFragments =
                new String[4];
        net.minecraft.client.renderer.entity.layers.LayerArmorBase.packedTextFragments[0] =
                new String("");
        net.minecraft.client.renderer.entity.layers.LayerArmorBase.packedTextFragments[1] =
                new String("");
        b(null);
        net.minecraft.client.renderer.entity.layers.LayerArmorBase.ENCHANTED_ITEM_GLINT_RES =
                new ResourceLocation("textures/misc/enchanted_item_glint.png");
        net.minecraft.client.renderer.entity.layers.LayerArmorBase.ARMOR_TEXTURE_RES_MAP =
                Maps.newHashMap();
    }

    private void renderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6,
            int i) {
        int[] aint1 = method_650532ba484532b3efb0();
        ItemStack itemStack = this.getCurrentArmor(livingEntity, i);
        int[] aint = aint1;
        if (itemStack != null && itemStack.getItem() instanceof ItemArmor) {
            ItemArmor item = (ItemArmor) itemStack.getItem();
            ModelBase modelBase = this.func_177175_a(i);
            modelBase.setModelAttributes(this.renderer.getMainModel());
            modelBase.setLivingAnimations(livingEntity, f, f1, f2);
            if (Reflector.IA.exists()) {
                modelBase =
                        (ModelBase)
                                Reflector.call(
                                        Reflector.IA,
                                        new Object[] {livingEntity, itemStack, i, modelBase});
            }

            boolean flag;
            this.func_177179_a(modelBase, i);
            flag = this.isSlotForLeggings(i);
            if (!OptiFineConfig.isCustomItems()
                    || !CustomItems.bindCustomArmorTexture(
                            itemStack, flag ? 2 : 1, (String) null)) {
                if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
                    this.renderer.bindTexture(
                            this.getArmorResource(
                                    livingEntity, itemStack, flag ? 2 : 1, (String) null));
                    if (!(aint == null)) {

                        this.renderer.bindTexture(this.getArmorResource(item, flag));
                    }
                } else {

                    this.renderer.bindTexture(this.getArmorResource(item, flag));
                }
            }

            if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
                int k = item.getColor(itemStack);
                if (k != -1) {
                    float f10 = (k >> 16 & 0xFF) / 255.0F;
                    float f11 = (k >> 8 & 0xFF) / 255.0F;
                    float f12 = (k & 0xFF) / 255.0F;
                    GlStateManager.color(
                            this.colorR * f10, this.colorG * f11, this.colorB * f12, this.alpha);
                    modelBase.render(livingEntity, f, f1, f3, f4, f5, f6);
                    if (!OptiFineConfig.isCustomItems()
                            || !CustomItems.bindCustomArmorTexture(
                                    itemStack, flag ? 2 : 1, "overlay")) {
                        this.renderer.bindTexture(
                                this.getArmorResource(
                                        livingEntity, itemStack, flag ? 2 : 1, "overlay"));
                    }
                }

                GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
                modelBase.render(livingEntity, f, f1, f3, f4, f5, f6);
                if (!this.field_177193_i
                        && itemStack.isItemEnchanted()
                        && (!OptiFineConfig.isCustomItems()
                                || !CustomItems.renderCustomArmorEffect(
                                        livingEntity,
                                        itemStack,
                                        modelBase,
                                        f,
                                        f1,
                                        f2,
                                        f3,
                                        f4,
                                        f5,
                                        f6))) {
                    this.method_507007b9321139e438e3(
                            livingEntity, modelBase, f, f1, f2, f3, f4, f5, f6);
                }

                return;
            }

            switch (item.getArmorMaterial()) {
                case LEATHER:
                    int j = item.getColor(itemStack);
                    float f7 = (j >> 16 & 0xFF) / 255.0F;
                    float f8 = (j >> 8 & 0xFF) / 255.0F;
                    float f9 = (j & 0xFF) / 255.0F;
                    GlStateManager.color(
                            this.colorR * f7, this.colorG * f8, this.colorB * f9, this.alpha);
                    modelBase.render(livingEntity, f, f1, f3, f4, f5, f6);
                    if (!OptiFineConfig.isCustomItems()
                            || !CustomItems.bindCustomArmorTexture(
                                    itemStack, flag ? 2 : 1, "overlay")) {
                        this.renderer.bindTexture(this.getArmorResource(item, flag, "overlay"));
                    }
                case CHAIN:
                case IRON:
                case GOLD:
                case DIAMOND:
                    GlStateManager.color(this.colorR, this.colorG, this.colorB, this.alpha);
                    modelBase.render(livingEntity, f, f1, f3, f4, f5, f6);
            }

            if (!this.field_177193_i
                    && itemStack.isItemEnchanted()
                    && (!OptiFineConfig.isCustomItems()
                            || !CustomItems.renderCustomArmorEffect(
                                    livingEntity,
                                    itemStack,
                                    modelBase,
                                    f,
                                    f1,
                                    f2,
                                    f3,
                                    f4,
                                    f5,
                                    f6))) {
                this.method_507007b9321139e438e3(
                        livingEntity, modelBase, f, f1, f2, f3, f4, f5, f6);
            }
        }
    }

    public void doRenderLayer(
            LivingEntity livingEntity,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        this.renderLayer(livingEntity, f, f1, f2, f3, f4, f5, f6, 4);
        this.renderLayer(livingEntity, f, f1, f2, f3, f4, f5, f6, 3);
        this.renderLayer(livingEntity, f, f1, f2, f3, f4, f5, f6, 2);
        this.renderLayer(livingEntity, f, f1, f2, f3, f4, f5, f6, 1);
    }

    // region String decoding support

    // endregion

    private boolean isSlotForLeggings(int i) {
        return i == 2;
    }

    public ItemStack getCurrentArmor(LivingEntity livingEntity, int i) {
        return livingEntity.getCurrentArmor(i - 1);
    }

    // region String decoding support

    // endregion

    protected abstract void initArmor();

    private ResourceLocation getArmorResource(ItemArmor itemArmor, boolean flag, String text) {
        String textValue =
                String.format(
                        "textures/models/armor/%s_layer_%d%s.png",
                        itemArmor.getArmorMaterial().getName(),
                        flag ? 2 : 1,
                        text == null ? packedTextFragments[0] : String.format("_%s", text));
        ResourceLocation resourceLocation =
                (ResourceLocation)
                        net.minecraft.client.renderer.entity.layers.LayerArmorBase
                                .ARMOR_TEXTURE_RES_MAP
                                .get(textValue);
        if (resourceLocation == null) {
            resourceLocation = new ResourceLocation(textValue);
            net.minecraft.client.renderer.entity.layers.LayerArmorBase.ARMOR_TEXTURE_RES_MAP.put(
                    textValue, resourceLocation);
        }

        return resourceLocation;
    }

    private void method_507007b9321139e438e3(
            LivingEntity livingEntity,
            ModelBase modelBase,
            float f,
            float f1,
            float f2,
            float f3,
            float f4,
            float f5,
            float f6) {
        int[] aint = method_650532ba484532b3efb0();
        if (!OptiFineConfig.isCustomItems() || CustomItems.isUseGlint()) {
            if (OptiFineConfig.isShaders()) {
                if (Shaders.isShadowPass) {
                    return;
                }

                ShadersRender.renderEnchantedGlintBegin();
            }

            float f7 = livingEntity.ticksExisted + f2;
            this.renderer.bindTexture(
                    net.minecraft.client.renderer.entity.layers.LayerArmorBase
                            .ENCHANTED_ITEM_GLINT_RES);
            GlStateManager.enableBlend();
            GlStateManager.depthFunc(514);
            GlStateManager.depthMask(false);
            float f8 = 0.5F;
            GlStateManager.color(f8, f8, f8, 1.0F);
            int i = 0;

            while (i < 2) {
                GlStateManager.disableLighting();
                GlStateManager.blendFunc(768, 1);
                float f9 = 0.76F;
                GlStateManager.color(0.5F * f9, 0.25F * f9, 0.8F * f9, 1.0F);
                GlStateManager.matrixMode(5890);
                GlStateManager.loadIdentity();
                float f10 = 0.33333334F;
                GlStateManager.scale(f10, f10, f10);
                GlStateManager.rotate(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translate(0.0F, f7 * (0.001F + i * 0.003F) * 20.0F, 0.0F);
                GlStateManager.matrixMode(5888);
                modelBase.render(livingEntity, f, f1, f3, f4, f5, f6);
                i++;
                if (aint != null) {
                    break;
                }
            }

            GlStateManager.matrixMode(5890);
            GlStateManager.loadIdentity();
            GlStateManager.matrixMode(5888);
            GlStateManager.enableLighting();
            GlStateManager.depthMask(true);
            GlStateManager.depthFunc(515);
            GlStateManager.disableBlend();
            if (OptiFineConfig.isShaders()) {
                ShadersRender.renderEnchantedGlintEnd();
            }
        }
    }

    private ResourceLocation getArmorResource(ItemArmor itemArmor, boolean flag) {
        return this.getArmorResource(itemArmor, flag, (String) null);
    }

    public static void b(int[] aint) {
        Cu = aint;
    }

    // region String decoding support

    // endregion

    public boolean shouldCombineTextures() {
        return false;
    }
}
