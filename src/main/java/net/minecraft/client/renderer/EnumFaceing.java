package net.minecraft.client.renderer;

import net.minecraft.util.Facing;

public enum EnumFaceing {
    DOWN(
            "DOWN",
            0,
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null)),
    UP(
            "UP",
            1,
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null)),
    NORTH(
            "NORTH",
            2,
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null)),
    SOUTH(
            "SOUTH",
            3,
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null)),
    WEST(
            "WEST",
            4,
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.WEST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null)),
    EAST(
            "EAST",
            5,
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.SOUTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.DOWN_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null),
            new EnumFaceingVertexInformation(
                    EnumFaceingConstants.EAST_INDEX,
                    EnumFaceingConstants.UP_INDEX,
                    EnumFaceingConstants.NORTH_INDEX,
                    null));

    private static EnumFaceing[] facings;

    private static EnumFaceing[] aPP;

    private EnumFaceingVertexInformation[] vertexInfos;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.EnumFaceing.facings = new EnumFaceing[6];
        aPP = new EnumFaceing[] {net.minecraft.client.renderer.EnumFaceing.DOWN, net.minecraft.client.renderer.EnumFaceing.UP, net.minecraft.client.renderer.EnumFaceing.NORTH, net.minecraft.client.renderer.EnumFaceing.SOUTH, net.minecraft.client.renderer.EnumFaceing.WEST, net.minecraft.client.renderer.EnumFaceing.EAST};
        net.minecraft.client.renderer.EnumFaceing.facings[EnumFaceingConstants.DOWN_INDEX] = net.minecraft.client.renderer.EnumFaceing.DOWN;
        net.minecraft.client.renderer.EnumFaceing.facings[EnumFaceingConstants.UP_INDEX] = net.minecraft.client.renderer.EnumFaceing.UP;
        net.minecraft.client.renderer.EnumFaceing.facings[EnumFaceingConstants.NORTH_INDEX] = net.minecraft.client.renderer.EnumFaceing.NORTH;
        net.minecraft.client.renderer.EnumFaceing.facings[EnumFaceingConstants.SOUTH_INDEX] = net.minecraft.client.renderer.EnumFaceing.SOUTH;
        net.minecraft.client.renderer.EnumFaceing.facings[EnumFaceingConstants.WEST_INDEX] = net.minecraft.client.renderer.EnumFaceing.WEST;
        net.minecraft.client.renderer.EnumFaceing.facings[EnumFaceingConstants.EAST_INDEX] = net.minecraft.client.renderer.EnumFaceing.EAST;
    }

    EnumFaceing(String text, int j, EnumFaceingVertexInformation... aPRValue) {
        this.vertexInfos = aPRValue;
    }

    public EnumFaceingVertexInformation ap(int i) {
        return this.vertexInfos[i];
    }

    public static EnumFaceing getFacing(Facing facing) {
        return net.minecraft.client.renderer.EnumFaceing.facings[facing.getIndex()];
    }

    // region String decoding support

    // endregion

}
