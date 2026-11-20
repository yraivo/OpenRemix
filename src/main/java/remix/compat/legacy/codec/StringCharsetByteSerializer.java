package remix.compat.legacy.codec;

import java.nio.charset.Charset;

public interface StringCharsetByteSerializer extends remix.compat.legacy.codec.ValueByteSerializer {
    static StringCharsetByteSerializer forCharset(Charset charset) {
        return new DefaultStringCharsetByteSerializer(charset);
    }

    Charset getCharset();

    default byte[] method_a8ac4be1d843bf47e3c6(String text) {
        Charset charset = this.getCharset();
        return charset != null ? text.getBytes(charset) : new byte[0];
    }
}
