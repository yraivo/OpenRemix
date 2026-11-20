package net.minecraft.client.model;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import optifine.ModelSprite;

import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ModelRenderer {
    public float rotationPointX;
    public String boxName;
    public List spriteList = new ArrayList();
    private static String[] packedTextFragments;
    private boolean compiled;
    public boolean isHidden;
    public float textureWidth;
    public float rotateAngleY;
    public boolean mirrorV = false;
    public float rotationPointZ;
    public float textureHeight;
    public List cubeList;
    float savedScale;
    public float offsetY;
    public float rotateAngleZ;
    public float offsetX;
    private int textureOffsetY;
    public float offsetZ;
    public List childModels;
    private int textureOffsetX;
    public boolean mirror;
    public boolean showModel;
    public float rotateAngleX;
    private ModelBase baseModel;
    public float rotationPointY;
    private int displayList;

    public ModelRenderer(ModelBase awqValue, String text) {
        this.textureWidth = 64.0F;
        this.textureHeight = 32.0F;
        this.showModel = true;
        this.cubeList = com.google.common.collect.Lists.newArrayList();
        this.baseModel = awqValue;
        awqValue.boxList.add(this);
        this.boxName = text;
        this.setTextureSize(awqValue.textureWidth, awqValue.textureHeight);
    }

    public void addBox(
            float p171_0,
            float p171_1,
            float p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            float p171_6) {
        this.cubeList.add(
                new net.minecraft.client.model.ModelBox(
                        this,
                        this.textureOffsetX,
                        this.textureOffsetY,
                        p171_0,
                        p171_1,
                        p171_2,
                        p171_3,
                        p171_4,
                        p171_5,
                        p171_6));
    }

    public void postRender(float f) {
        String[] astring = ModelBase.getLegacyModelFlowMarker();
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(f);
            }

            if (this.rotateAngleX == 0.0F
                    && this.rotateAngleY == 0.0F
                    && this.rotateAngleZ == 0.0F) {
                if (this.rotationPointX == 0.0F
                        && this.rotationPointY == 0.0F
                        && this.rotationPointZ == 0.0F) {
                    return;
                }

                GlStateManager.translate(
                        this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
                if (astring == null) {
                    return;
                }
            }

            GlStateManager.translate(
                    this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
            if (this.rotateAngleZ != 0.0F) {
                GlStateManager.rotate(
                        this.rotateAngleZ * (180.0F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
            }

            if (this.rotateAngleY != 0.0F) {
                GlStateManager.rotate(
                        this.rotateAngleY * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (this.rotateAngleX != 0.0F) {
                GlStateManager.rotate(
                        this.rotateAngleX * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            }
        }
    }

    public void addChild(ModelRenderer modelRenderer) {
        if (this.childModels == null) {
            this.childModels = com.google.common.collect.Lists.newArrayList();
        }

        this.childModels.add(modelRenderer);
    }

    public void render(float f) {
        String[] astring = ModelBase.getLegacyModelFlowMarker();
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(f);
            }

            label87:
            {
                GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
                if (this.rotateAngleX == 0.0F
                        && this.rotateAngleY == 0.0F
                        && this.rotateAngleZ == 0.0F) {
                    if (this.rotationPointX == 0.0F
                            && this.rotationPointY == 0.0F
                            && this.rotationPointZ == 0.0F) {
                        GlStateManager.callList(this.displayList);
                        if (this.childModels == null) {
                            break label87;
                        }

                        int i = 0;

                        do {
                            if (i >= this.childModels.size()) {
                                break label87;
                            }

                            ((ModelRenderer) this.childModels.get(i)).render(f);
                            i++;
                        } while (astring == null);
                    }

                    GlStateManager.translate(
                            this.rotationPointX * f,
                            this.rotationPointY * f,
                            this.rotationPointZ * f);
                    GlStateManager.callList(this.displayList);
                    if (this.childModels != null) {
                        int j = 0;

                        while (j < this.childModels.size()) {
                            ((ModelRenderer) this.childModels.get(j)).render(f);
                            j++;
                            if (astring != null) {
                                break;
                            }
                        }
                    }

                    GlStateManager.translate(
                            -this.rotationPointX * f,
                            -this.rotationPointY * f,
                            -this.rotationPointZ * f);
                    if (astring == null) {
                        break label87;
                    }
                }

                GlStateManager.pushMatrix();
                GlStateManager.translate(
                        this.rotationPointX * f, this.rotationPointY * f, this.rotationPointZ * f);
                if (this.rotateAngleZ != 0.0F) {
                    GlStateManager.rotate(
                            this.rotateAngleZ * (180.0F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                }

                if (this.rotateAngleY != 0.0F) {
                    GlStateManager.rotate(
                            this.rotateAngleY * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                }

                if (this.rotateAngleX != 0.0F) {
                    GlStateManager.rotate(
                            this.rotateAngleX * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
                }

                GlStateManager.callList(this.displayList);
                if (this.childModels != null) {
                    int k = 0;

                    while (k < this.childModels.size()) {
                        ((ModelRenderer) this.childModels.get(k)).render(f);
                        k++;
                        if (astring != null) {
                            break;
                        }
                    }
                }

                GlStateManager.popMatrix();
            }

            GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
        }
    }

    // region String decoding support

    // endregion

    public ModelRenderer setTextureOffset(int textureOffsetXValue, int textureOffsetYValue) {
        this.textureOffsetX = textureOffsetXValue;
        this.textureOffsetY = textureOffsetYValue;
        return this;
    }

    public ModelRenderer setTextureSize(int textureWidthValue, int textureHeightValue) {
        this.textureWidth = textureWidthValue;
        this.textureHeight = textureHeightValue;
        return this;
    }

    public ModelRenderer(ModelBase modelBase) {
        this(modelBase, (java.lang.String) null);
    }

    public ModelRenderer(ModelBase modelBase, int p171_1, int p171_2) {
        this(modelBase);
        this.setTextureOffset(p171_1, p171_2);
    }

    public void setRotationPoint(
            float rotationPointXValue, float rotationPointYValue, float rotationPointZValue) {
        this.rotationPointX = rotationPointXValue;
        this.rotationPointY = rotationPointYValue;
        this.rotationPointZ = rotationPointZValue;
    }

    public ModelRenderer addBox(
            float p171_0, float p171_1, float p171_2, int p171_3, int p171_4, int p171_5) {
        this.cubeList.add(
                new net.minecraft.client.model.ModelBox(
                        this,
                        this.textureOffsetX,
                        this.textureOffsetY,
                        p171_0,
                        p171_1,
                        p171_2,
                        p171_3,
                        p171_4,
                        p171_5,
                        0.0F));
        return this;
    }

    public ModelRenderer addBox(
            String text,
            float p171_1,
            float p171_2,
            float p171_3,
            int p171_4,
            int p171_5,
            int p171_6) {
        text = this.boxName + net.minecraft.client.model.ModelRenderer.packedTextFragments[0] + text;
        net.minecraft.client.model.TextureOffset textureOffset = this.baseModel.getTextureOffset(text);
        this.setTextureOffset(textureOffset.textureOffsetX, textureOffset.textureOffsetY);
        this.cubeList.add(
                new net.minecraft.client.model.ModelBox(
                                this,
                                this.textureOffsetX,
                                this.textureOffsetY,
                                p171_1,
                                p171_2,
                                p171_3,
                                p171_4,
                                p171_5,
                                p171_6,
                                0.0F)
                        .setBoxName(text));
        return this;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public void renderWithRotation(float p171_0) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(p171_0);
            }

            net.minecraft.client.renderer.GlStateManager.pushMatrix();
            net.minecraft.client.renderer.GlStateManager.translate(
                    this.rotationPointX * p171_0,
                    this.rotationPointY * p171_0,
                    this.rotationPointZ * p171_0);
            if (this.rotateAngleY != 0.0F) {
                net.minecraft.client.renderer.GlStateManager.rotate(
                        this.rotateAngleY * (180.0F / (float) java.lang.Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (this.rotateAngleX != 0.0F) {
                net.minecraft.client.renderer.GlStateManager.rotate(
                        this.rotateAngleX * (180.0F / (float) java.lang.Math.PI), 1.0F, 0.0F, 0.0F);
            }

            if (this.rotateAngleZ != 0.0F) {
                net.minecraft.client.renderer.GlStateManager.rotate(
                        this.rotateAngleZ * (180.0F / (float) java.lang.Math.PI), 0.0F, 0.0F, 1.0F);
            }

            net.minecraft.client.renderer.GlStateManager.callList(this.displayList);
            net.minecraft.client.renderer.GlStateManager.popMatrix();
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.client.model.ModelRenderer.packedTextFragments[0] = new String(".");
    }

    // endregion

    public int getDisplayList() {
        return this.displayList;
    }

    public ModelRenderer addBox(
            float p171_0,
            float p171_1,
            float p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            boolean p171_6) {
        this.cubeList.add(
                new net.minecraft.client.model.ModelBox(
                        this,
                        this.textureOffsetX,
                        this.textureOffsetY,
                        p171_0,
                        p171_1,
                        p171_2,
                        p171_3,
                        p171_4,
                        p171_5,
                        0.0F,
                        p171_6));
        return this;
    }

    public void resetDisplayList() {
        if (this.compiled) {
            this.compiled = false;
            this.compileDisplayList(this.savedScale);
        }
    }

    public boolean getCompiled() {
        return this.compiled;
    }

    private void compileDisplayList(float savedScaleValue) {
        String[] astring = ModelBase.getLegacyModelFlowMarker();
        if (this.displayList == 0) {
            this.savedScale = savedScaleValue;
            this.displayList = GLAllocation.generateDisplayLists(1);
        }

        GL11.glNewList(this.displayList, 4864);
        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        int i = 0;

        while (i < this.cubeList.size()) {
            ((ModelBox) this.cubeList.get(i))
                    .render(worldRenderer, savedScaleValue);
            i++;
            if (astring != null) {
                break;
            }
        }

        i = 0;

        while (i < this.spriteList.size()) {
            ModelSprite modelSprite = (ModelSprite) this.spriteList.get(i);
            modelSprite.render(Tessellator.getInstance(), savedScaleValue);
            i++;
            if (astring != null) {
                break;
            }
        }

        GL11.glEndList();
        this.compiled = true;
    }

    public void addSprite(
            float p171_0,
            float p171_1,
            float p171_2,
            int p171_3,
            int p171_4,
            int p171_5,
            float p171_6) {
        this.spriteList.add(
                new optifine.ModelSprite(
                        this,
                        this.textureOffsetX,
                        this.textureOffsetY,
                        p171_0,
                        p171_1,
                        p171_2,
                        p171_3,
                        p171_4,
                        p171_5,
                        p171_6));
    }
}
