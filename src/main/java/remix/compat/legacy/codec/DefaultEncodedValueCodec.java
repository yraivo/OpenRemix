package remix.compat.legacy.codec;

public final class DefaultEncodedValueCodec<T>
        implements EncodedValueCodec<DefaultEncodedStageSequence, T> {
    private EncodedValueCodecConfig configuration;
    private EncodedValueDecoder<DefaultEncodedStageSequence, T> decoder;
    private EncodedValueEncoder encoder;

    @Override
    public EncodedValueCodecConfig getConfiguration() {
        return this.configuration;
    }

    public DefaultEncodedValueCodec(EncodedValueCodecConfig bWPValue) {
        this.configuration = bWPValue;
        this.decoder = new DefaultEncodedValueDecoder<>(bWPValue);
        this.encoder = new DefaultEncodedValueEncoder(bWPValue);
    }

    @Override
    public EncodedValueEncoder getEncoder() {
        return this.encoder;
    }

    @Override
    public EncodedValueDecoder<DefaultEncodedStageSequence, T> getDecoder() {
        return this.decoder;
    }
}
