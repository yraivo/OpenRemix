package net.minecraft.network;

class ServerDisconnectCheckTask implements Runnable {
    NetHandlerPlayServer field_151286_b;

    @Override
    public void run() {
        this.field_151286_b.netManager.checkDisconnected();
    }

    ServerDisconnectCheckTask(NetHandlerPlayServer bvLValue) {
        this.field_151286_b = bvLValue;
    }
}
