package remix.compat.legacy.codec;

import java.util.Random;

public abstract class AbstractEncodedValueEncoder implements remix.compat.legacy.codec.EncodedValueEncoder {
    private static String[] legacyEncoderMarker;
    private boolean alternateBlockOrder;
    private int differenceIndexLimit;
    private ValueByteSerializer valueByteSerializer;
    private java.util.List transformTemplates;
    private Random random;

    public int getDifferenceIndexLimit() {
        return this.differenceIndexLimit;
    }

    static {
        if (getLegacyEncoderMarker() == null) {
            setLegacyEncoderMarker(new String[2]);
        }
    }

    public java.util.List getList() {
        return this.transformTemplates;
    }

    public static String[] getLegacyEncoderMarker() {
        return remix.compat.legacy.codec.AbstractEncodedValueEncoder.legacyEncoderMarker;
    }

    public static void setLegacyEncoderMarker(String[] astring) {
        remix.compat.legacy.codec.AbstractEncodedValueEncoder.legacyEncoderMarker = astring;
    }

    public ValueByteSerializer getValueByteSerializer() {
        return this.valueByteSerializer;
    }

    public boolean usesAlternateBlockOrder() {
        return this.alternateBlockOrder;
    }

    protected AbstractEncodedValueEncoder(EncodedValueCodecConfig encodedValueCodecConfig) {
        this.random = encodedValueCodecConfig.getRandom();
        this.valueByteSerializer = encodedValueCodecConfig.getValueByteSerializer();
        this.transformTemplates = encodedValueCodecConfig.getList();
        this.alternateBlockOrder = encodedValueCodecConfig.usesAlternateBlockOrder();
        this.differenceIndexLimit = encodedValueCodecConfig.getDifferenceIndexLimit();
    }

    public Random getRandom() {
        return this.random;
    }
}
