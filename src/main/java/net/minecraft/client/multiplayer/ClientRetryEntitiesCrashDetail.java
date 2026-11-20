package net.minecraft.client.multiplayer;

import java.util.concurrent.Callable;

class ClientRetryEntitiesCrashDetail implements Callable {
    ClientWorld field_78835_a;
    private static String b;

    public String getCrashDetail() {
        return ClientWorld.access$100(this.field_78835_a).size()
                + b
                + ClientWorld.access$100(this.field_78835_a).toString();
    }

    // region String decoding support

    // endregion

    static {
        b = " total; ";
    }

    @Override
    public Object call() throws Exception {
        return this.getCrashDetail();
    }

    // region String decoding support

    // endregion

    ClientRetryEntitiesCrashDetail(ClientWorld awDValue) {
        this.field_78835_a = awDValue;
    }
}
