package shadersmod.client;

import java.io.InputStream;

public class ShaderPackDefault implements IShaderPack {
    public boolean hasDirectory(String text) {
        return false;
    }

    public void close() {}

    public InputStream getResourceAsStream(String text) {
        return ShaderPackDefault.class.getResourceAsStream(text);
    }

    public String getName() {
        return Shaders.packNameDefault;
    }
}
