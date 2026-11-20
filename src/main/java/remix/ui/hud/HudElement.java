package remix.ui.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ScaledResolution;

public interface HudElement {
    MinecraftClient ec = MinecraftClient.getMinecraft();

    void render(ScaledResolution scaledResolution);
}
