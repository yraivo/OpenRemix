package net.minecraft.client.audio;

import net.minecraft.util.RegistrySimple;

import java.util.Map;

public class SoundRegistry extends RegistrySimple {
    private Map soundRegistry;

    public void clearMap() {
        this.soundRegistry.clear();
    }

    public void registerSound(SoundEventAccessorComposite soundEventAccessorComposite) {
        this.putObject(
                soundEventAccessorComposite.getSoundEventLocation(), soundEventAccessorComposite);
    }

    protected Map createUnderlyingMap() {
        this.soundRegistry = com.google.common.collect.Maps.newHashMap();
        return this.soundRegistry;
    }
}
