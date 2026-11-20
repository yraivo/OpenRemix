package shadersmod.client;

import optifine.StrUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ShaderPackZip implements IShaderPack {
    private static String[] packedTextFragments;
    protected ZipFile packZipFile;
    protected File packFile;

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public boolean hasDirectory(String text) {
        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            try {
                if (this.packZipFile == null) {
                    this.packZipFile = new ZipFile(this.packFile);
                }
            } catch (IOException ioexception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception1));
            }

            String textValue = StrUtils.removePrefix(text, packedTextFragments[1]);
            ZipEntry zipentry = this.packZipFile.getEntry(textValue);

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (zipentry != null) {
                    return true;
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(ioexception));
            }

            return false;
        } catch (IOException ioexception2) {
            return false;
        }
    }

    // region String decoding support

    // endregion

    public InputStream getResourceAsStream(String text) {
        try {
            try {
                if (this.packZipFile == null) {
                    this.packZipFile = new ZipFile(this.packFile);
                }
            } catch (Exception exception1) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
            }

            String textValue = StrUtils.removePrefix(text, packedTextFragments[0]);
            ZipEntry zipentry = this.packZipFile.getEntry(textValue);

            try {
                if (zipentry == null) {
                    return null;
                }
            } catch (Exception exception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
            }

            return this.packZipFile.getInputStream(zipentry);
        } catch (Exception exception2) {
            return null;
        }
    }

    public void close() {
        if (this.packZipFile != null) {
            try {
                this.packZipFile.close();
            } catch (Exception exception) {
            }

            this.packZipFile = null;
        }
    }

    public ShaderPackZip(String text, File file1) {
        this.packFile = file1;
        this.packZipFile = null;
    }

    public String getName() {
        return this.packFile.getName();
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        shadersmod.client.ShaderPackZip.packedTextFragments[0] = new String("/");
        shadersmod.client.ShaderPackZip.packedTextFragments[1] = new String("/");
    }
    // endregion

    // region String decoding support

    // endregion
}
