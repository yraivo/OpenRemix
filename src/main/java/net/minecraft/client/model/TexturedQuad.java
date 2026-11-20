package net.minecraft.client.model;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.Vec3;
import optifine.OptiFineConfig;
import shadersmod.client.SVertexBuilder;

public class TexturedQuad {
    public int nVertices;
    public PositionTextureVertex[] vertexPositions;
    private boolean invertNormal;

    public TexturedQuad(PositionTextureVertex[] awnValue) {
        this.vertexPositions = awnValue;
        this.nVertices = awnValue.length;
    }

    public TexturedQuad(
            PositionTextureVertex[] positionTextureVertexArray,
            int p171_1,
            int p171_2,
            int p171_3,
            int p171_4,
            float p171_5,
            float p171_6) {
        this(positionTextureVertexArray);
        float f2 = 0.0F / p171_5;
        float f3 = 0.0F / p171_6;
        positionTextureVertexArray[0] =
                positionTextureVertexArray[0].setTexturePosition(
                        p171_3 / p171_5 - f2, p171_2 / p171_6 + f3);
        positionTextureVertexArray[1] =
                positionTextureVertexArray[1].setTexturePosition(
                        p171_1 / p171_5 + f2, p171_2 / p171_6 + f3);
        positionTextureVertexArray[2] =
                positionTextureVertexArray[2].setTexturePosition(
                        p171_1 / p171_5 + f2, p171_4 / p171_6 - f3);
        positionTextureVertexArray[3] =
                positionTextureVertexArray[3].setTexturePosition(
                        p171_3 / p171_5 - f2, p171_4 / p171_6 - f3);
    }

    public void draw(WorldRenderer worldRenderer, float f) {
        String[] astring1 = ModelBase.getLegacyModelFlowMarker();
        Vec3 vec3 =
                this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[0].vector3D);
        Vec3 vec3Value =
                this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[2].vector3D);
        String[] astring = astring1;
        Vec3 vec3Value2 = vec3Value.crossProduct(vec3).normalize();
        worldRenderer.startDrawingQuads();
        if (OptiFineConfig.isShaders()) {
            SVertexBuilder.method_52776da4508bcea23b22(worldRenderer);
        }

        {
            if (this.invertNormal) {
                worldRenderer.setVertexNormal(
                        -((float) vec3Value2.x), -((float) vec3Value2.y), -((float) vec3Value2.z));
                if (!(astring == null)) {

                    worldRenderer.setVertexNormal(
                            (float) vec3Value2.x, (float) vec3Value2.y, (float) vec3Value2.z);
                }
            } else {

                worldRenderer.setVertexNormal(
                        (float) vec3Value2.x, (float) vec3Value2.y, (float) vec3Value2.z);
            }
        }

        int i = 0;

        while (i < 4) {
            PositionTextureVertex positionTextureVertex = this.vertexPositions[i];
            worldRenderer.addVertexWithUV(
                    positionTextureVertex.vector3D.x * f,
                    positionTextureVertex.vector3D.y * f,
                    positionTextureVertex.vector3D.z * f,
                    positionTextureVertex.texturePositionX,
                    positionTextureVertex.texturePositionY);
            i++;
            if (astring != null) {
                break;
            }
        }

        Tessellator.getInstance().draw();
    }

    public void flipFace() {
        net.minecraft.client.model.PositionTextureVertex[] awnValue =
                new net.minecraft.client.model.PositionTextureVertex[this.vertexPositions.length];

        for (int i = 0; i < this.vertexPositions.length; i++) {
            awnValue[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
        }

        this.vertexPositions = awnValue;
    }
}
