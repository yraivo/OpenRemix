package net.minecraft.client.renderer.block.model;

public enum ItemCameraTransformsTransformType {
    NONE("NONE", 0),
    THIRD_PERSON("THIRD_PERSON", 1),
    FIRST_PERSON("FIRST_PERSON", 2),
    HEAD("HEAD", 3),
    GUI("GUI", 4);
    private static ItemCameraTransformsTransformType[] aQl;

    // region String decoding support

    // endregion

    ItemCameraTransformsTransformType(String text, int j) {}

    // region String decoding support

    // endregion

    static {
        aQl =
                new ItemCameraTransformsTransformType[] {
                    net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType.NONE,
                    net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType.THIRD_PERSON,
                    net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType.FIRST_PERSON,
                    net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType.HEAD,
                    net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType.GUI
                };
    }

    // region String decoding support

    // endregion

}
