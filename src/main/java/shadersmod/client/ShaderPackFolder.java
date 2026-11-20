package shadersmod.client;

import optifine.StrUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ShaderPackFolder implements IShaderPack {
    private static String[] packedTextFragments;
    protected File packFile;

    private static Exception a(Exception exception) {
        return exception;
    }

    public void close() {}

    public InputStream getResourceAsStream(String text) {
        try {
            String textValue =
                    StrUtils.removePrefixSuffix(
                            text, packedTextFragments[0], packedTextFragments[1]);
            File file1 = remix.security.SafeResourcePaths.inside(this.packFile, textValue);

            try {
                if (!file1.exists()) {
                    return null;
                }
            } catch (Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
            }

            return new BufferedInputStream(new FileInputStream(file1));
        } catch (Exception exception1) {
            return null;
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        shadersmod.client.ShaderPackFolder.packedTextFragments[0] = new String("/");
        shadersmod.client.ShaderPackFolder.packedTextFragments[1] = new String("/");
    }

    // endregion

    // region String decoding support

    // endregion

    public ShaderPackFolder(String text, File file1) {
        this.packFile = file1;
    }

    public String getName() {
        return this.packFile.getName();
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    public boolean hasDirectory(String text) {
        File candidate =
                remix.security.SafeResourcePaths.insideOrNull(
                        this.packFile,
                        text == null || text.length() == 0 ? null : text.substring(1));
        return candidate != null && candidate.isDirectory();
    }

    // region String decoding support

    // endregion
}
