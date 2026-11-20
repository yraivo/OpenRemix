package net.minecraft.client.renderer;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;

import java.util.concurrent.Callable;

public class EntityRendererScreenSizeCallable implements Callable {
    EntityRenderer KP;
    private static String c;

    ScaledResolution ayd;

    public String getCrashDetail() {
        return String.format(
                c,
                this.ayd.getWidth(),
                this.ayd.getHeight(),
                EntityRenderer.access$000(this.KP).displayWidth,
                EntityRenderer.access$000(this.KP).displayHeight,
                this.ayd.getScaleFactor());
    }

    static {
        c = "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d";
    }

    public EntityRendererScreenSizeCallable(EntityRenderer entityRenderer, ScaledResolution aydValue) {
        this.KP = entityRenderer;
        this.ayd = aydValue;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }
}
