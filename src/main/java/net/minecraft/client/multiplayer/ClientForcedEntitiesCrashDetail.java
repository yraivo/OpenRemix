package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;

class ClientForcedEntitiesCrashDetail implements Callable {

    private static String b;
    ClientWorld field_78833_a;

    // region String decoding support

    // endregion

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    static {
        b = " total; ";
    }

    // region String decoding support

    // endregion

    ClientForcedEntitiesCrashDetail(ClientWorld awDValue) {
        this.field_78833_a = awDValue;
    }

    public String getCrashDetail() {
        return ClientWorld.access$000(this.field_78833_a).size()
                + b
                + ClientWorld.access$000(this.field_78833_a).toString();
    }
}
