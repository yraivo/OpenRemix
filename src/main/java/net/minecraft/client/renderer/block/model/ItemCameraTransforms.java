package net.minecraft.client.renderer.block.model;

public class ItemCameraTransforms {
    public ItemTransformVec3f firstPerson;
    public static final ItemCameraTransforms DEFAULT =
            new ItemCameraTransforms(
                    ItemTransformVec3f.DEFAULT,
                    ItemTransformVec3f.DEFAULT,
                    ItemTransformVec3f.DEFAULT,
                    ItemTransformVec3f.DEFAULT);
    public ItemTransformVec3f gui;
    public ItemTransformVec3f head;
    public ItemTransformVec3f thirdPerson;

    public ItemCameraTransforms(
            ItemTransformVec3f ayXValue,
            ItemTransformVec3f ayTValue,
            ItemTransformVec3f ayWValue,
            ItemTransformVec3f ayVValue) {
        this.thirdPerson = ayXValue;
        this.firstPerson = ayTValue;
        this.head = ayWValue;
        this.gui = ayVValue;
    }
}
