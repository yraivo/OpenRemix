package net.minecraft.client;

class ResourceReloadTask implements Runnable {
    MinecraftClient field_152389_a;

    @Override
    public void run() {
        this.field_152389_a.refreshResources();
    }

    ResourceReloadTask(MinecraftClient minecraftClient) {
        this.field_152389_a = minecraftClient;
    }
}
