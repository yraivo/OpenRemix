package net.minecraft.client.main;

import net.minecraft.client.MinecraftClient;

final class ClientShutdownHookThread extends Thread {
    ClientShutdownHookThread(String text) {
        super(text);
    }

    @Override
    public void run() {
        MinecraftClient.stopIntegratedServer();
    }
}
