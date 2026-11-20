package net.minecraft.profiler;

public interface IPlayerUsage {
    void addServerTypeToSnooper(PlayerUsageSnooper playerUsageSnooper);

    void addServerStatsToSnooper(PlayerUsageSnooper playerUsageSnooper);

    boolean isSnooperEnabled();
}
