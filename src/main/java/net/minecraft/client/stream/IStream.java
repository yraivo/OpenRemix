package net.minecraft.client.stream;

import tv.twitch.ErrorCode;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.chat.ChatUserInfo;

public interface IStream {
    boolean func_152928_D();

    boolean isBroadcasting();

    void nG();

    boolean func_152913_F();

    IngestServer[] func_152925_v();

    void muteMicrophone(boolean flag);

    void func_152909_x();

    void sendMetadataSpan(Metadata metadata, long i, long j);

    void unpause();

    IngestServerTester func_152932_y();

    void ns();

    void pause();

    void submitStreamFrame();

    boolean func_152929_G();

    boolean func_152908_z();

    void func_152917_b(String text);

    boolean isPaused();

    void shutdownStream();

    boolean isReadyToBroadcast();

    void sendMetadataEvent(Metadata metadata, long i);

    void iJ();

    boolean func_152927_B();

    ChatUserInfo func_152926_a(String text);

    ErrorCode func_152912_E();

    boolean func_152936_l();

    IStreamAuthFailureReason func_152918_H();

    void updateStreaming();

    void updateStreamVolume();

    int func_152920_A();

    String func_152921_C();
}
