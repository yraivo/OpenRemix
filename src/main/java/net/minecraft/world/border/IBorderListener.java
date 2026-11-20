package net.minecraft.world.border;

public interface IBorderListener {
    void onCenterChanged(WorldBorder worldBorder, double d0, double d1);

    void onWarningTimeChanged(WorldBorder worldBorder, int i);

    void onDamageBufferChanged(WorldBorder worldBorder, double d0);

    void onSizeChanged(WorldBorder worldBorder, double d0);

    void onWarningDistanceChanged(WorldBorder worldBorder, int i);

    void onDamageAmountChanged(WorldBorder worldBorder, double d0);

    void onTransitionStarted(WorldBorder worldBorder, double d0, double d1, long i);
}
