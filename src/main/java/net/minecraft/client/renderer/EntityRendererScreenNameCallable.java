package net.minecraft.client.renderer;

import net.minecraft.client.renderer.EntityRenderer;

import java.util.concurrent.Callable;

public class EntityRendererScreenNameCallable implements Callable {
    EntityRenderer entityRenderer;

    public EntityRendererScreenNameCallable(EntityRenderer axZValue) {
        this.entityRenderer = axZValue;
    }

    public String method_e0e3092a59fee58fae52() {
        return EntityRenderer.access$000(this.entityRenderer).currentScreen.getClass().getCanonicalName();
    }

    @Override
    public Object call() throws Exception {
        return this.method_e0e3092a59fee58fae52();
    }
}
