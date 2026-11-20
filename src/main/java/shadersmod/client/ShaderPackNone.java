package shadersmod.client;

import java.io.InputStream;

public class ShaderPackNone implements IShaderPack {
    public boolean hasDirectory(String text) {
        return false;
    }

    public InputStream getResourceAsStream(String text) {
        return null;
    }

    public void close() {}

    public String getName() {
        return Shaders.packNameNone;
    }
}
