package net.minecraft.client.stream;

import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.StreamInfo;

public interface BroadcastControllerBroadcastListener {
    void method_05b43377ca0aa222e61a(ErrorCode errorcode);

    void func_152894_a(StreamInfo streaminfo);

    void func_152896_a(IngestList ingestlist);

    void func_152900_a(ErrorCode errorcode, AuthToken authtoken);

    void func_152898_a(ErrorCode errorcode, GameInfo[] agameinfo);

    void func_152899_b();

    void method_17259210e80146ea0cdb(ErrorCode errorcode);

    void func_152901_c();

    void method_b3805c57e9d8e69c1958(ErrorCode errorcode);

    void func_152895_a();

    void func_152891_a(BroadcastControllerBroadcastState broadcastControllerBroadcastState);
}
