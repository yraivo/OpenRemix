package net.minecraft.client.renderer.block.model;

import remix.compat.vecmath.Vector3f;

public class ItemTransformVec3f {
    public static final ItemTransformVec3f DEFAULT =
            new ItemTransformVec3f(
                    new Vector3f(),
                    new Vector3f(),
                    new Vector3f(1.0F, 1.0F, 1.0F));
    public Vector3f scale;
    public Vector3f rotation;
    public Vector3f translation;

    public ItemTransformVec3f(
            Vector3f vector3f,
            Vector3f vector3fValue,
            Vector3f vector3fValue2) {
        this.rotation = new Vector3f(vector3f);
        this.translation = new Vector3f(vector3fValue);
        this.scale = new Vector3f(vector3fValue2);
    }
}
