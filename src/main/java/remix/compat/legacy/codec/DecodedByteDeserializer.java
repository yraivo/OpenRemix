package remix.compat.legacy.codec;

public interface DecodedByteDeserializer<T> {
    T deserializeBytes(byte[] abyte, ValueByteSerializer valueByteSerializer);
}
