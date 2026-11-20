package net.minecraft.client;

import net.minecraft.client.MinecraftClient;

public class MinecraftTimerHackThread extends Thread {
    MinecraftClient minecraftClient;

    public MinecraftTimerHackThread(MinecraftClient minecraftClient, String text) {
        super(text);
        this.minecraftClient = minecraftClient;
    }

    @Override
    public void run() {
        String text = MinecraftClient.getLegacyClientFlowMarker();

        while (this.minecraftClient.running) {
            try {

                Thread.sleep(2147483647L);
            } catch (InterruptedException interruptedexception) {
                if (text != null) {
                    break;
                }
            }
        }
    }
}
