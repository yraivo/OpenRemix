package shadersmod.client;

import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage;

public class SVertexFormat {
    public static final VertexFormat defVertexFormatTextured = makeDefVertexFormatTextured();
    public static final int a = 14;
    public static final int c = 10;
    public static final int dD = 12;
    public static final int cz = 8;

    public static VertexFormat makeDefVertexFormatTextured() {
        VertexFormat vertexFormat = new VertexFormat();
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.UBYTE,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0, VertexFormatElementEnumType.FLOAT, VertexFormatElementEnumUseage.UV, 2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.NORMAL,
                        3));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.PADDING,
                        1));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.PADDING,
                        2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
        return vertexFormat;
    }

    public static void setDefBakedFormat(VertexFormat vertexFormat) {
        vertexFormat.clear();
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.UBYTE,
                        VertexFormatElementEnumUseage.COLOR,
                        4));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0, VertexFormatElementEnumType.FLOAT, VertexFormatElementEnumUseage.UV, 2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.NORMAL,
                        3));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.PADDING,
                        1));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.PADDING,
                        2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
    }

    public static VertexFormat makeDefVertexFormatItem() {
        VertexFormat vertexFormat = new VertexFormat();
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.UBYTE,
                        VertexFormatElementEnumUseage.COLOR,
                        4));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0, VertexFormatElementEnumType.FLOAT, VertexFormatElementEnumUseage.UV, 2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.NORMAL,
                        3));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.PADDING,
                        1));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.PADDING,
                        2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
        return vertexFormat;
    }

    public static VertexFormat makeDefVertexFormatBlock() {
        VertexFormat vertexFormat = new VertexFormat();
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.UBYTE,
                        VertexFormatElementEnumUseage.COLOR,
                        4));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0, VertexFormatElementEnumType.FLOAT, VertexFormatElementEnumUseage.UV, 2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        1, VertexFormatElementEnumType.SHORT, VertexFormatElementEnumUseage.UV, 2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.NORMAL,
                        3));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.BYTE,
                        VertexFormatElementEnumUseage.PADDING,
                        1));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.PADDING,
                        2));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
        vertexFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.SHORT,
                        VertexFormatElementEnumUseage.PADDING,
                        4));
        return vertexFormat;
    }
}
