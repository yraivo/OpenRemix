package remix.compat.legacy.codec;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultByteCipherRegistry<T> implements ByteCipherRegistry<T> {
    private final Map<Class<? extends ByteArrayCipher>, DecodedByteDeserializer<T>>
            deserializersByCipherType = new HashMap<>();
    private final List<ByteArrayCipher> registeredCiphers = new ArrayList<>();
    private static Entity[] legacyCipherRegistryMarker;
    private DecodedByteDeserializer<T> decodedByteDeserializer;
    private ByteArrayCipher byteArrayCipher;

    public static void setLegacyCipherRegistryMarker(Entity[] entityArray) {
        remix.compat.legacy.codec.DefaultByteCipherRegistry.legacyCipherRegistryMarker = entityArray;
    }

    public DecodedByteDeserializer<T> getDeserializerForCipher(
            ByteArrayCipher byteArrayCipherValue) {
        return this.deserializersByCipherType.get(byteArrayCipherValue.getClass());
    }

    public DecodedByteDeserializer<T> getDecodedByteDeserializer() {
        return this.decodedByteDeserializer;
    }

    public ByteArrayCipher selectCipher(
            byte[] abyte, CipherIndexSelector cipherIndexSelector) {
        return cipherIndexSelector == null
                ? this.byteArrayCipher
                : this.getCipherAt(cipherIndexSelector.selectCipherIndex(abyte));
    }

    public ByteArrayCipher getByteArrayCipher() {
        return this.byteArrayCipher;
    }

    public DecodedByteDeserializer<T> selectDeserializer(
            byte[] abyte, CipherIndexSelector cipherIndexSelector) {
        return cipherIndexSelector == null
                ? this.decodedByteDeserializer
                : this.getDeserializerAt(cipherIndexSelector.selectCipherIndex(abyte));
    }

    static {
        getLegacyCipherRegistryMarker();
    }

    public DecodedByteDeserializer<T> getDeserializerAt(int i) {
        getLegacyCipherRegistryMarker();

        DecodedByteDeserializer decodedByteDeserializerValue =
                this.deserializersByCipherType.get(this.registeredCiphers.get(i).getClass());

        return decodedByteDeserializerValue;
    }

    public static Entity[] getLegacyCipherRegistryMarker() {
        return remix.compat.legacy.codec.DefaultByteCipherRegistry.legacyCipherRegistryMarker;
    }

    public ByteArrayCipher getCipherAt(int i) {
        return this.registeredCiphers.get(i);
    }

    public DefaultByteCipherRegistry(
            ByteArrayCipher bWfValue, DecodedByteDeserializer<T> bWeValue) {
        this.byteArrayCipher = bWfValue;
        this.decodedByteDeserializer = bWeValue;
        this.registerCipher(bWfValue, bWeValue);
    }

    public DefaultByteCipherRegistry(DecodedByteDeserializer<T> decodedByteDeserializerValue) {
        this(ByteArrayCipher.IDENTITY, decodedByteDeserializerValue);
    }

    public DecodedByteDeserializer<T> registerCipher(
            ByteArrayCipher byteArrayCipherValue,
            DecodedByteDeserializer<T> decodedByteDeserializerValue) {
        if (byteArrayCipherValue != null && decodedByteDeserializerValue != null) {
            this.registeredCiphers.add(byteArrayCipherValue);
            return this.deserializersByCipherType.put(
                    (Class<? extends ByteArrayCipher>)
                            byteArrayCipherValue.getClass(),
                    decodedByteDeserializerValue);
        } else {
            return this.decodedByteDeserializer;
        }
    }
}
