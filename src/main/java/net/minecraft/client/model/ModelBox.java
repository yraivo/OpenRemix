package net.minecraft.client.model;

import net.minecraft.client.renderer.WorldRenderer;

public class ModelBox {
    public String boxName;
    public float posX1;
    public float posY2;
    public float posZ2;
    private TexturedQuad[] quadList;
    public float posZ1;
    private PositionTextureVertex[] vertexPositions;
    public float posY1;
    public float posX2;

    public ModelBox setBoxName(String text) {
        this.boxName = text;
        return this;
    }

    public ModelBox(
            ModelRenderer modelRenderer,
            int p171_1,
            int p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            int p171_6,
            int p171_7,
            int p171_8,
            float p171_9) {
        this(
                modelRenderer,
                p171_1,
                p171_2,
                p171_3,
                p171_4,
                p171_5,
                p171_6,
                p171_7,
                p171_8,
                p171_9,
                modelRenderer.mirror);
    }

    public void render(WorldRenderer worldRenderer, float f) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        int i = 0;
        String[] astring = astring1;

        while (i < this.quadList.length) {
            this.quadList[i].draw(worldRenderer, f);
            i++;
            if (astring != null) {
                break;
            }
        }
    }

    public ModelBox(
            ModelRenderer modelRenderer,
            int p171_1,
            int p171_2,
            float posX1Value,
            float posY1Value,
            float posZ1Value,
            int p171_6,
            int p171_7,
            int p171_8,
            float p171_9,
            boolean p171_10) {
        this.posX1 = posX1Value;
        this.posY1 = posY1Value;
        this.posZ1 = posZ1Value;
        this.posX2 = posX1Value + p171_6;
        this.posY2 = posY1Value + p171_7;
        this.posZ2 = posZ1Value + p171_8;
        this.vertexPositions = new net.minecraft.client.model.PositionTextureVertex[8];
        this.quadList = new net.minecraft.client.model.TexturedQuad[6];
        float f4 = posX1Value + p171_6;
        float f5 = posY1Value + p171_7;
        float f6 = posZ1Value + p171_8;
        posX1Value -= p171_9;
        posY1Value -= p171_9;
        posZ1Value -= p171_9;
        f4 += p171_9;
        f5 += p171_9;
        f6 += p171_9;
        if (p171_10) {
            float f7 = f4;
            f4 = posX1Value;
            posX1Value = f7;
        }

        net.minecraft.client.model.PositionTextureVertex positionTextureVertex =
                new net.minecraft.client.model.PositionTextureVertex(posX1Value, posY1Value, posZ1Value, 0.0F, 0.0F);
        net.minecraft.client.model.PositionTextureVertex positionTextureVertexValue =
                new net.minecraft.client.model.PositionTextureVertex(f4, posY1Value, posZ1Value, 0.0F, 8.0F);
        net.minecraft.client.model.PositionTextureVertex positionTextureVertexValue2 =
                new net.minecraft.client.model.PositionTextureVertex(f4, f5, posZ1Value, 8.0F, 8.0F);
        net.minecraft.client.model.PositionTextureVertex positionTextureVertexValue3 =
                new net.minecraft.client.model.PositionTextureVertex(posX1Value, f5, posZ1Value, 8.0F, 0.0F);
        net.minecraft.client.model.PositionTextureVertex positionTextureVertexValue4 =
                new net.minecraft.client.model.PositionTextureVertex(posX1Value, posY1Value, f6, 0.0F, 0.0F);
        net.minecraft.client.model.PositionTextureVertex positionTextureVertexValue5 =
                new net.minecraft.client.model.PositionTextureVertex(f4, posY1Value, f6, 0.0F, 8.0F);
        net.minecraft.client.model.PositionTextureVertex positionTextureVertexValue6 =
                new net.minecraft.client.model.PositionTextureVertex(f4, f5, f6, 8.0F, 8.0F);
        net.minecraft.client.model.PositionTextureVertex positionTextureVertexValue7 =
                new net.minecraft.client.model.PositionTextureVertex(posX1Value, f5, f6, 8.0F, 0.0F);
        this.vertexPositions[0] = positionTextureVertex;
        this.vertexPositions[1] = positionTextureVertexValue;
        this.vertexPositions[2] = positionTextureVertexValue2;
        this.vertexPositions[3] = positionTextureVertexValue3;
        this.vertexPositions[4] = positionTextureVertexValue4;
        this.vertexPositions[5] = positionTextureVertexValue5;
        this.vertexPositions[6] = positionTextureVertexValue6;
        this.vertexPositions[7] = positionTextureVertexValue7;
        this.quadList[0] =
                new net.minecraft.client.model.TexturedQuad(
                        new net.minecraft.client.model.PositionTextureVertex[] {
                            positionTextureVertexValue5,
                            positionTextureVertexValue,
                            positionTextureVertexValue2,
                            positionTextureVertexValue6
                        },
                        p171_1 + p171_8 + p171_6,
                        p171_2 + p171_8,
                        p171_1 + p171_8 + p171_6 + p171_8,
                        p171_2 + p171_8 + p171_7,
                        modelRenderer.textureWidth,
                        modelRenderer.textureHeight);
        this.quadList[1] =
                new net.minecraft.client.model.TexturedQuad(
                        new net.minecraft.client.model.PositionTextureVertex[] {
                            positionTextureVertex,
                            positionTextureVertexValue4,
                            positionTextureVertexValue7,
                            positionTextureVertexValue3
                        },
                        p171_1,
                        p171_2 + p171_8,
                        p171_1 + p171_8,
                        p171_2 + p171_8 + p171_7,
                        modelRenderer.textureWidth,
                        modelRenderer.textureHeight);
        this.quadList[2] =
                new net.minecraft.client.model.TexturedQuad(
                        new net.minecraft.client.model.PositionTextureVertex[] {
                            positionTextureVertexValue5,
                            positionTextureVertexValue4,
                            positionTextureVertex,
                            positionTextureVertexValue
                        },
                        p171_1 + p171_8,
                        p171_2,
                        p171_1 + p171_8 + p171_6,
                        p171_2 + p171_8,
                        modelRenderer.textureWidth,
                        modelRenderer.textureHeight);
        this.quadList[3] =
                new net.minecraft.client.model.TexturedQuad(
                        new net.minecraft.client.model.PositionTextureVertex[] {
                            positionTextureVertexValue2,
                            positionTextureVertexValue3,
                            positionTextureVertexValue7,
                            positionTextureVertexValue6
                        },
                        p171_1 + p171_8 + p171_6,
                        p171_2 + p171_8,
                        p171_1 + p171_8 + p171_6 + p171_6,
                        p171_2,
                        modelRenderer.textureWidth,
                        modelRenderer.textureHeight);
        this.quadList[4] =
                new net.minecraft.client.model.TexturedQuad(
                        new net.minecraft.client.model.PositionTextureVertex[] {
                            positionTextureVertexValue,
                            positionTextureVertex,
                            positionTextureVertexValue3,
                            positionTextureVertexValue2
                        },
                        p171_1 + p171_8,
                        p171_2 + p171_8,
                        p171_1 + p171_8 + p171_6,
                        p171_2 + p171_8 + p171_7,
                        modelRenderer.textureWidth,
                        modelRenderer.textureHeight);
        this.quadList[5] =
                new net.minecraft.client.model.TexturedQuad(
                        new net.minecraft.client.model.PositionTextureVertex[] {
                            positionTextureVertexValue4,
                            positionTextureVertexValue5,
                            positionTextureVertexValue6,
                            positionTextureVertexValue7
                        },
                        p171_1 + p171_8 + p171_6 + p171_8,
                        p171_2 + p171_8,
                        p171_1 + p171_8 + p171_6 + p171_8 + p171_6,
                        p171_2 + p171_8 + p171_7,
                        modelRenderer.textureWidth,
                        modelRenderer.textureHeight);
        if (p171_10) {
            for (int j1 = 0; j1 < this.quadList.length; j1++) {
                this.quadList[j1].flipFace();
            }
        }
    }
}
