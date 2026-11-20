package net.minecraft.client.renderer.vertex;

import optifine.OptiFineConfig;
import optifine.Reflector;
import shadersmod.client.SVertexFormat;

public class DefaultVertexFormats {
    private static final VertexFormat ITEM_VANILLA;
    public static VertexFormat BLOCK;
    private static final VertexFormat BLOCK_VANILLA;
    public static VertexFormat ITEM;

    static {
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK = new VertexFormat();
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM = new VertexFormat();
        BLOCK_VANILLA = net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK;
        ITEM_VANILLA = DefaultVertexFormats.ITEM;
    }

    public static void updateVertexFormats() {
        String[] astring;
        {
            astring = VertexFormatElement.lD();
            if (OptiFineConfig.isShaders()) {
                net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK = SVertexFormat.makeDefVertexFormatBlock();
                net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM = SVertexFormat.makeDefVertexFormatItem();
                if (!(astring == null)) {

                    net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK = net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK_VANILLA;
                    net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM = net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM_VANILLA;
                }
            } else {

                net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK = net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK_VANILLA;
                net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM = net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM_VANILLA;
            }
        }

        if (Reflector.Attributes_DEFAULT_BAKED_FORMAT.exists()) {
            VertexFormat aFYValue = net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM;
            VertexFormat vertexFormat = (VertexFormat) Reflector.getFieldValue(Reflector.Attributes_DEFAULT_BAKED_FORMAT);
            vertexFormat.clear();
            int i = 0;

            while (i < aFYValue.getElementCount()) {
                vertexFormat.addElement(aFYValue.getElement(i));
                i++;
                if (astring != null) {
                    break;
                }
            }
        }
    }

    static {
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.UBYTE,
                        VertexFormatElementEnumUseage.COLOR,
                        4));
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK.addElement(
                new VertexFormatElement(
                        0, VertexFormatElementEnumType.FLOAT, VertexFormatElementEnumUseage.UV, 2));
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.BLOCK.addElement(
                new VertexFormatElement(
                        1, VertexFormatElementEnumType.SHORT, VertexFormatElementEnumUseage.UV, 2));
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.UBYTE,
                        VertexFormatElementEnumUseage.COLOR,
                        4));
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM.addElement(
                new VertexFormatElement(
                        0, VertexFormatElementEnumType.FLOAT, VertexFormatElementEnumUseage.UV, 2));
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.NORMAL,
                        3));
        net.minecraft.client.renderer.vertex.DefaultVertexFormats.ITEM.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.PADDING,
                        1));
    }
}
