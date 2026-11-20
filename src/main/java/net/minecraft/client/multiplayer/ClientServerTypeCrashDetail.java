package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;

class ClientServerTypeCrashDetail implements Callable {

    ClientWorld field_142029_a;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    ClientServerTypeCrashDetail(ClientWorld awDValue) {
        this.field_142029_a = awDValue;
    }

    public String getCrashDetail() {
        return ClientWorld.access$200(this.field_142029_a).getIntegratedServer() == null
                ? "Non-integrated multiplayer server"
                : "Integrated singleplayer server";
    }


}
