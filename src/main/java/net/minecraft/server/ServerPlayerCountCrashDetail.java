package net.minecraft.server;

import java.util.concurrent.Callable;

class ServerPlayerCountCrashDetail implements Callable {

    MinecraftServer field_74274_a;

    public String getCrashDetail() {
        return MinecraftServer.access$100(this.field_74274_a).getCurrentPlayerCount()
                + " / "
                + MinecraftServer.access$100(this.field_74274_a).getMaxPlayers()
                + "; "
                + MinecraftServer.access$100(this.field_74274_a).playerEntityList;
    }

    ServerPlayerCountCrashDetail(MinecraftServer awJValue) {
        this.field_74274_a = awJValue;
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }


}
