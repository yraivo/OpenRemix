package net.minecraft.client.renderer.vertex;

import net.minecraft.entity.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement {
    private int index;
    private int elementCount;
    private VertexFormatElementEnumType type;
    private static String te;
    private static Logger LOGGER;
    private VertexFormatElementEnumUseage usage;
    private static String[] packedTextFragments;
    private int byteOffset;
    private static String[] g;

    @Override
    public String toString() {
        return this.elementCount
                + packedTextFragments[0]
                + this.usage.getDisplayName()
                + packedTextFragments[1]
                + this.type.getDisplayName();
    }

    // region String decoding support

    // endregion

    public static void b(String[] astring) {
        g = astring;
    }

    public final VertexFormatElementEnumType getType() {
        return this.type;
    }

    private final boolean func_177372_a(
            int i, VertexFormatElementEnumUseage vertexFormatElementEnumUseage) {
        return i == 0 || vertexFormatElementEnumUseage == VertexFormatElementEnumUseage.UV;
    }

    @Override
    public int hashCode() {
        String[] astring1 = lD();
        int i = this.type.hashCode();
        String[] astring = astring1;
        i = 31 * i + this.usage.hashCode();
        i = 31 * i + this.index;
        i = 31 * i + this.elementCount;
        i = 31 * i + this.byteOffset;
        if (astring != null) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        return i;
    }

    public void setByteOffset(int i) {
        this.byteOffset = i;
    }

    public final VertexFormatElementEnumUseage getUsage() {
        return this.usage;
    }

    public int getByteOffset() {
        return this.byteOffset;
    }

    public final int getElementCount() {
        return this.elementCount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            VertexFormatElement vertexFormatElement = (VertexFormatElement) object;
            return this.elementCount != vertexFormatElement.elementCount
                    ? false
                    : (this.index != vertexFormatElement.index
                            ? false
                            : (this.byteOffset != vertexFormatElement.byteOffset
                                    ? false
                                    : (this.type != vertexFormatElement.type
                                            ? false
                                            : this.usage == vertexFormatElement.usage)));
        } else {
            return false;
        }
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.vertex.VertexFormatElement.packedTextFragments = new String[3];
        net.minecraft.client.renderer.vertex.VertexFormatElement.packedTextFragments[0] = new String(",");
        net.minecraft.client.renderer.vertex.VertexFormatElement.packedTextFragments[1] = new String(",");
        net.minecraft.client.renderer.vertex.VertexFormatElement.packedTextFragments[2] =
                new String(
                        "q5\u0019`0Q\u001cY`\u0003q+U\u0015D`\u0010x<L\u0015R4\u000646GPH(\u00104*@\u001dY`\u0001m)DPS4\u001dq+\u0001\u0004T!\u001b4\fw\u0003\u001c!\u0007qyO\u001fH`\u0006a)Q\u001fN4\u0010pw\u00016S2\u0016}7FPH9\u0005qyU\u001f\u001c\u0015#:");
        if (lD() != null) {
            b(new String[5]);
        }
        te =
                "Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.";
        net.minecraft.client.renderer.vertex.VertexFormatElement.LOGGER = LogManager.getLogger();
    }

    public final int getSize() {
        return this.type.getSize() * this.elementCount;
    }

    public final int getIndex() {
        return this.index;
    }

    public static String[] lD() {
        return g;
    }

    public final boolean isPositionElement() {
        return this.usage == VertexFormatElementEnumUseage.POSITION;
    }

    public VertexFormatElement(
            int i,
            VertexFormatElementEnumType aERValue,
            VertexFormatElementEnumUseage aESValue,
            int j) {
        if (!this.func_177372_a(i, aESValue)) {
            net.minecraft.client.renderer.vertex.VertexFormatElement.LOGGER.warn(te);
            this.usage = VertexFormatElementEnumUseage.UV;
        } else {
            this.usage = aESValue;
        }

        this.type = aERValue;
        this.index = i;
        this.elementCount = j;
        this.byteOffset = 0;
    }

    // region String decoding support

    // endregion
}
