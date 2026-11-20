package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;

class ClientBrandCrashDetail implements Callable {
    ClientWorld field_142027_a;

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    ClientBrandCrashDetail(ClientWorld awDValue) {
        this.field_142027_a = awDValue;
    }

    public String getCrashDetail() {
        return ClientWorld.access$200(this.field_142027_a).player.getClientBrand();
    }
}
