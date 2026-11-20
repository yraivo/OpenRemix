package net.minecraft.client.renderer.vertex;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class VertexFormat {
    private int colorElementOffset;
    private List offsets;
    private int normalElementOffset;
    private static String[] packedTextFragments;

    private static Logger LOGGER;

    private int nextOffset;
    private List uvOffsetsById;
    private final List elements = Lists.newArrayList();

    public boolean hasColor() {
        return this.colorElementOffset >= 0;
    }

    public int getNormalOffset() {
        return this.normalElementOffset;
    }

    public int getColorOffset() {
        return this.colorElementOffset;
    }

    public boolean hasUvOffset(int i) {
        return this.uvOffsetsById.size() - 1 >= i;
    }

    public VertexFormatElement getElement(int i) {
        return (VertexFormatElement) this.elements.get(i);
    }

    public int getNextOffset() {
        return this.nextOffset;
    }

    public void clear() {
        this.elements.clear();
        this.offsets.clear();
        this.colorElementOffset = -1;
        this.uvOffsetsById.clear();
        this.normalElementOffset = -1;
        this.nextOffset = 0;
    }

    public VertexFormat() {
        this.offsets = Lists.newArrayList();
        this.nextOffset = 0;
        this.colorElementOffset = -1;
        this.uvOffsetsById = Lists.newArrayList();
        this.normalElementOffset = -1;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object != null && this.getClass() == object.getClass()) {
            VertexFormat vertexFormat = (VertexFormat) object;
            return this.nextOffset != vertexFormat.nextOffset
                    ? false
                    : (!this.elements.equals(vertexFormat.elements)
                            ? false
                            : this.offsets.equals(vertexFormat.offsets));
        } else {
            return false;
        }
    }

    public int getElementCount() {
        return this.elements.size();
    }

    public int getUvOffsetById(int i) {
        return (Integer) this.uvOffsetsById.get(i);
    }

    public void addElement(VertexFormatElement vertexFormatElement) {
        if (vertexFormatElement.isPositionElement() && this.hasPosition()) {
            net.minecraft.client.renderer.vertex.VertexFormat.LOGGER.warn(
                    "VertexFormat error: Trying to add a position VertexFormatElement when one"
                            + " already exists, ignoring.");
        } else {
            this.elements.add(vertexFormatElement);
            this.offsets.add(this.nextOffset);
            vertexFormatElement.setByteOffset(this.nextOffset);
            this.nextOffset = this.nextOffset + vertexFormatElement.getSize();
            switch (vertexFormatElement.getUsage()) {
                case NORMAL:
                    this.normalElementOffset = vertexFormatElement.getByteOffset();
                    break;
                case COLOR:
                    this.colorElementOffset = vertexFormatElement.getByteOffset();
                    break;
                case UV:
                    this.uvOffsetsById.add(
                            vertexFormatElement.getIndex(), vertexFormatElement.getByteOffset());
            }
        }
    }

    private boolean hasPosition() {
        for (java.util.Iterator<?> __recoveryIterator171_26069 = (this.elements).iterator();
                (__recoveryIterator171_26069).hasNext(); ) {
            VertexFormatElement vertexFormatElement =
                    (VertexFormatElement) (__recoveryIterator171_26069).next();
            if (vertexFormatElement.isPositionElement()) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String[] astring1 = VertexFormatElement.lD();
        String text = "format: " + this.elements.size() + " elements: ";
        int i = 0;
        String[] astring = astring1;

        while (i < this.elements.size()) {
            text = text + ((VertexFormatElement) this.elements.get(i)).toString();
            if (i != this.elements.size() - 1) {
                text = text + packedTextFragments[0];
            }

            i++;
            if (astring != null) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            VertexFormatElement.b(new String[4]);
        }

        return text;
    }

    public List getElements() {
        return this.elements;
    }

    @Override
    public int hashCode() {
        int i = this.elements.hashCode();
        i = 31 * i + this.offsets.hashCode();
        return 31 * i + this.nextOffset;
    }

    public boolean hasNormal() {
        return this.normalElementOffset >= 0;
    }

    static {
        net.minecraft.client.renderer.vertex.VertexFormat.packedTextFragments = new String[2];
        net.minecraft.client.renderer.vertex.VertexFormat.packedTextFragments[0] = new String(" ");
        net.minecraft.client.renderer.vertex.VertexFormat.LOGGER = LogManager.getLogger();
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public VertexFormat(VertexFormat vertexFormat) {
        this(vertexFormat, VertexFormatElement.lD());
    }

    private VertexFormat(VertexFormat vertexFormat, String[] initialFlowMarker) {
        this();
        String[] astring1 = initialFlowMarker;
        String[] astring = astring1;
        int i = 0;

        while (i < vertexFormat.getElementCount()) {
            this.addElement(vertexFormat.getElement(i));
            i++;
            if (astring != null) {
                break;
            }
        }

        this.nextOffset = vertexFormat.getNextOffset();
    }
}
