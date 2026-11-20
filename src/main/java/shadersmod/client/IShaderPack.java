package shadersmod.client;

import java.io.InputStream;

public interface IShaderPack {
    String getName();

    void close();

    boolean hasDirectory(String text);

    InputStream getResourceAsStream(String text);
}
