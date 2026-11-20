package net.minecraft.world.demo;

import net.minecraft.entity.Entity;
import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.WorldType;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.WorldInfo;

public class DemoWorldServer extends WorldServer {
    private static long demoWorldSeed;
    private static String[] packedTextFragments;
    public static WorldSettings demoWorldSettings;

    public DemoWorldServer(
            MinecraftServer minecraftServer,
            ISaveHandler iSaveHandler,
            WorldInfo worldInfoValue,
            int i,
            Profiler profiler) {
        this(
                minecraftServer,
                iSaveHandler,
                worldInfoValue,
                i,
                profiler,
                DemoWorldManager.method_c60693f457aa70be7160());
    }

    private DemoWorldServer(
            MinecraftServer minecraftServer,
            ISaveHandler iSaveHandler,
            WorldInfo worldInfoValue,
            int i,
            Profiler profiler,
            java.lang.String initialFlowMarker) {
        super(minecraftServer, iSaveHandler, worldInfoValue, i, profiler);

        this.worldInfo.populateFromWorldSettings(
                net.minecraft.world.demo.DemoWorldServer.demoWorldSettings);
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            DemoWorldManager.b(packedTextFragments[0]);
        }
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.world.demo.DemoWorldServer.packedTextFragments = new String[2];
        net.minecraft.world.demo.DemoWorldServer.packedTextFragments[0] = new String("QJ3vob");
        net.minecraft.world.demo.DemoWorldServer.packedTextFragments[1] =
                new String("M=t%Q/:b i=Pa\u0018");
        net.minecraft.world.demo.DemoWorldServer.demoWorldSeed = "North Carolina".hashCode();
        net.minecraft.world.demo.DemoWorldServer.demoWorldSettings =
                new WorldSettings(
                                net.minecraft.world.demo.DemoWorldServer.demoWorldSeed,
                                WorldSettingsGameType.SURVIVAL,
                                true,
                                false,
                                WorldType.DEFAULT)
                        .enableBonusChest();
    }

    // region String decoding support

    // endregion
}
