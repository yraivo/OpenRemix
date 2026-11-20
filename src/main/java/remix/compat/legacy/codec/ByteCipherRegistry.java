package remix.compat.legacy.codec;

public interface ByteCipherRegistry<T> {
    ByteArrayCipher selectCipher(
            byte[] abyte, CipherIndexSelector cipherIndexSelector);

    DecodedByteDeserializer<T> getDeserializerForCipher(
            ByteArrayCipher byteArrayCipher);

    DecodedByteDeserializer<T> getDecodedByteDeserializer();

    DecodedByteDeserializer<T> selectDeserializer(
            byte[] abyte, CipherIndexSelector cipherIndexSelector);

    ByteArrayCipher getCipherAt(int i);

    DecodedByteDeserializer<T> getDeserializerAt(int i);

    ByteArrayCipher getByteArrayCipher();

    DecodedByteDeserializer<T> registerCipher(
            ByteArrayCipher byteArrayCipher,
            DecodedByteDeserializer<T> decodedByteDeserializer);
}
