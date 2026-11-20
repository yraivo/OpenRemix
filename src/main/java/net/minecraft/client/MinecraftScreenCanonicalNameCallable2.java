package net.minecraft.client;

import net.minecraft.client.MinecraftClient;

import java.util.concurrent.Callable;

public class MinecraftScreenCanonicalNameCallable2 implements Callable {
    MinecraftClient minecraftClient;

    public Object cy() {
        return this.method_e0e3092a59fee58fae52();
    }

    public String method_e0e3092a59fee58fae52() {
        return this.minecraftClient.currentScreen.getClass().getCanonicalName();
    }

    @Override
    public Object call() throws Exception {
        return this.method_e0e3092a59fee58fae52();
    }

    public MinecraftScreenCanonicalNameCallable2(MinecraftClient minecraftClient) {
        this.minecraftClient = minecraftClient;
    }
}
