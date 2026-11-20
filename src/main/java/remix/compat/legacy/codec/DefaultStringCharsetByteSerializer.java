package remix.compat.legacy.codec;

import java.nio.charset.Charset;

final class DefaultStringCharsetByteSerializer implements StringCharsetByteSerializer {
    Charset charset;

    public byte[] bO(String text) {
        return this.method_a8ac4be1d843bf47e3c6(text);
    }

    public byte[] serializeValue(Object object) {
        return this.bO((String) object);
    }

    public Charset getCharset() {
        return this.charset;
    }

    DefaultStringCharsetByteSerializer(Charset charset) {
        this.charset = charset;
    }
}
