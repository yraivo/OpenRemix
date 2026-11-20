package net.minecraft.world;

import net.minecraft.profiler.Profiler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.storage.DerivedWorldInfo;
import net.minecraft.world.storage.ISaveHandler;

public class WorldServerMulti extends WorldServer {
    private WorldServer delegate;

    protected void saveLevel() throws MinecraftException {}

    public World init() {
        World.getLegacyWorldFlowMarker();

        this.mapStorage = this.delegate.getMapStorage();

        this.worldScoreboard = this.delegate.getScoreboard();
        String text = VillageCollection.fileNameForProvider(this.provider);
        VillageCollection bGbValue =
                (VillageCollection) this.mapStorage.loadData(VillageCollection.class, text);
        if (bGbValue == null) {
            this.villageCollectionObj = new VillageCollection(this);
            this.mapStorage.setData(text, this.villageCollectionObj);

            return this;
        }

        this.villageCollectionObj = bGbValue;
        this.villageCollectionObj.setWorldsForAll(this);
        return this;
    }

    public WorldServerMulti(
            MinecraftServer minecraftServer,
            ISaveHandler iSaveHandler,
            int i,
            WorldServer bGsValue,
            Profiler profiler) {
        super(
                minecraftServer,
                iSaveHandler,
                new DerivedWorldInfo(bGsValue.getWorldInfo()),
                i,
                profiler);
        this.delegate = bGsValue;
        bGsValue.getWorldBorder().addListener(new IBorderListener1(this));
    }

    // Helper used only by this class.
    static class IBorderListener1 implements net.minecraft.world.border.IBorderListener {
        net.minecraft.world.WorldServerMulti bFF;

        public void onWarningDistanceChanged(
                net.minecraft.world.border.WorldBorder worldBorder, int i) {
            this.bFF.getWorldBorder().setWarningDistance(i);
        }

        public void onCenterChanged(
                net.minecraft.world.border.WorldBorder worldBorder, double d0, double d1) {
            this.bFF.getWorldBorder().setCenter(d0, d1);
        }

        public void onSizeChanged(net.minecraft.world.border.WorldBorder worldBorder, double d0) {
            this.bFF.getWorldBorder().setTransition(d0);
        }

        public void onWarningTimeChanged(
                net.minecraft.world.border.WorldBorder worldBorder, int i) {
            this.bFF.getWorldBorder().setWarningTime(i);
        }

        IBorderListener1(net.minecraft.world.WorldServerMulti bFFValue) {
            this.bFF = bFFValue;
        }

        public void onTransitionStarted(
                net.minecraft.world.border.WorldBorder worldBorder, double d0, double d1, long i) {
            this.bFF.getWorldBorder().setTransition(d0, d1, i);
        }

        public void onDamageBufferChanged(
                net.minecraft.world.border.WorldBorder worldBorder, double d0) {
            this.bFF.getWorldBorder().setDamageBuffer(d0);
        }

        public void onDamageAmountChanged(
                net.minecraft.world.border.WorldBorder worldBorder, double d0) {
            this.bFF.getWorldBorder().setDamageAmount(d0);
        }
    }
}
