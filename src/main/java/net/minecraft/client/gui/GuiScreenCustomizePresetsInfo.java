package net.minecraft.client.gui;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.ChunkProviderSettingsFactory;

class GuiScreenCustomizePresetsInfo {
    public ResourceLocation amq;
    public String a;
    public ChunkProviderSettingsFactory arn;

    public GuiScreenCustomizePresetsInfo(
            String text, ResourceLocation amqValue, ChunkProviderSettingsFactory arnValue) {
        this.a = text;
        this.amq = amqValue;
        this.arn = arnValue;
    }
}
