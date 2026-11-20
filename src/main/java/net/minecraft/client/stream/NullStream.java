package net.minecraft.client.stream;

import tv.twitch.ErrorCode;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.chat.ChatUserInfo;

public class NullStream implements IStream {
    private Throwable field_152938_a;

    public IngestServerTester func_152932_y() {
        return null;
    }

    public void iJ() {}

    public String func_152921_C() {
        return null;
    }

    public void unpause() {}

    public ChatUserInfo func_152926_a(String text) {
        return null;
    }

    public void func_152917_b(String text) {}

    public void sendMetadataSpan(Metadata metadata, long i, long j) {}

    public boolean isReadyToBroadcast() {
        return false;
    }

    public boolean func_152929_G() {
        return false;
    }

    public boolean func_152936_l() {
        return false;
    }

    public boolean isBroadcasting() {
        return false;
    }

    public void shutdownStream() {}

    public boolean func_152928_D() {
        return false;
    }

    public void func_152909_x() {}

    public Throwable func_152937_a() {
        return this.field_152938_a;
    }

    public ErrorCode func_152912_E() {
        return null;
    }

    public void muteMicrophone(boolean flag) {}

    public boolean func_152908_z() {
        return false;
    }

    public int func_152920_A() {
        return 0;
    }

    public IStreamAuthFailureReason func_152918_H() {
        return IStreamAuthFailureReason.ERROR;
    }

    public void updateStreaming() {}

    public void submitStreamFrame() {}

    public void nG() {}

    public void sendMetadataEvent(Metadata metadata, long i) {}

    public boolean func_152913_F() {
        return false;
    }

    public void ns() {}

    public IngestServer[] func_152925_v() {
        return new IngestServer[0];
    }

    public void updateStreamVolume() {}

    public NullStream(Throwable throwable) {
        this.field_152938_a = throwable;
    }

    public void pause() {}

    public boolean func_152927_B() {
        return false;
    }

    public boolean isPaused() {
        return false;
    }
}
