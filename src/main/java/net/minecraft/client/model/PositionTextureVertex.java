package net.minecraft.client.model;

import net.minecraft.util.Vec3;

public class PositionTextureVertex {
    public Vec3 vector3D;
    public float texturePositionY;
    public float texturePositionX;

    public PositionTextureVertex(
            Vec3 vec3, float texturePositionXValue, float texturePositionYValue) {
        this.vector3D = vec3;
        this.texturePositionX = texturePositionXValue;
        this.texturePositionY = texturePositionYValue;
    }

    public PositionTextureVertex(
            PositionTextureVertex positionTextureVertex,
            float texturePositionXValue,
            float texturePositionYValue) {
        this.vector3D = positionTextureVertex.vector3D;
        this.texturePositionX = texturePositionXValue;
        this.texturePositionY = texturePositionYValue;
    }

    public PositionTextureVertex setTexturePosition(float p171_0, float p171_1) {
        return new net.minecraft.client.model.PositionTextureVertex(this, p171_0, p171_1);
    }

    public PositionTextureVertex(
            float p171_0, float p171_1, float p171_2, float p171_3, float p171_4) {
        this(new net.minecraft.util.Vec3(p171_0, p171_1, p171_2), p171_3, p171_4);
    }
}
