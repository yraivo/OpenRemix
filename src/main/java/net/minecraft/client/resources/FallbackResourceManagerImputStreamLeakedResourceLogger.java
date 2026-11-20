package net.minecraft.client.resources;

import net.minecraft.util.ResourceLocation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

class FallbackResourceManagerImputStreamLeakedResourceLogger extends InputStream {
    private boolean closed = false;
    private InputStream axv;

    private String b;


    @Override
    public int read() throws IOException {
        return this.axv.read();
    }

    @Override
    public void close() throws IOException {
        this.axv.close();
        this.closed = true;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public FallbackResourceManagerImputStreamLeakedResourceLogger(
            InputStream inputstream, ResourceLocation resourceLocation, String text) {
        this.axv = inputstream;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        new Exception().printStackTrace(new PrintStream(bytearrayoutputstream));
        this.b =
                "Leaked resource: '"
                        + resourceLocation
                        + "' loaded from pack: '"
                        + text
                        + "'\n"
                        + bytearrayoutputstream.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (!this.closed) {
                FallbackResourceManager.getLogger().warn(this.b);
            }
        } catch (Throwable throwable) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_b7e52da193241b30710d(throwable));
        }

        super.finalize();
    }
}
