package remix.compat.legacy.codec;

public interface ByteArrayCipher {
    ByteArrayCipher IDENTITY = new IdentityByteArrayCipher();

    byte[] method_e2e2729eb56538051fd7(byte[] abyte, byte[] abyte1);

    byte[] decodeBytes(byte[] abyte);
}
