package net.minecraft.client.renderer;

import net.minecraft.client.renderer.EntityRenderer;

import org.lwjgl.input.Mouse;

import java.util.concurrent.Callable;

public class EntityRendererMouseLocationCallable implements Callable {
    EntityRenderer aya;
    int cz;
    int a;

    private static String d;

    // region String decoding support

    // endregion

    static {
        d = "Scaled: (%d, %d). Absolute: (%d, %d)";
    }

    public EntityRendererMouseLocationCallable(EntityRenderer ayaValue, int i, int j) {
        this.aya = ayaValue;
        this.a = i;
        this.cz = j;
    }

    public String getCrashDetail() {
        return String.format(d, this.a, this.cz, Mouse.getX(), Mouse.getY());
    }

    // region String decoding support

    // endregion

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
