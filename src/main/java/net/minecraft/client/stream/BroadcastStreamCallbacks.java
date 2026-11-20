package net.minecraft.client.stream;

import net.minecraft.client.stream.BroadcastController;
import net.minecraft.client.stream.BroadcastControllerBroadcastState;
import net.minecraft.client.stream.IngestServerTester;

import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.GameInfoList;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.UserInfo;

public class BroadcastStreamCallbacks implements IStreamCallbacks {

    BroadcastController aMs;

    private static String[] packedTextFragments;

    public void runCommercialCallback(ErrorCode errorcode) {
        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152832_e(String.format("RunCommercialCallback got failure: %s", string));
        }
    }

    public void sendStartSpanMetaDataCallback(ErrorCode errorcode) {
        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152820_d(
                    String.format("sendStartSpanMetaDataCallback got failure: %s", string));
        }
    }

    public void bufferUnlockCallback(long i) {
        FrameBuffer framebuffer = FrameBuffer.lookupBuffer(i);
        this.aMs.field_152875_k.add(framebuffer);
    }

    public void loginCallback(ErrorCode errorcode, ChannelInfo channelinfo) {
        label21:
        {
            String text = IngestServerTester.sT();
            if (ErrorCode.succeeded(errorcode)) {
                this.aMs.channelInfo = channelinfo;
                this.aMs.func_152827_a(BroadcastControllerBroadcastState.LoggedIn);
                this.aMs.field_152877_m = true;
                if (text == null) {
                    break label21;
                }
            }

            this.aMs.func_152827_a(BroadcastControllerBroadcastState.Initialized);
            this.aMs.field_152877_m = false;
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152820_d(String.format("LoginCallback got failure: %s", string));
        }

        try {
            if (this.aMs.broadcastListener != null) {
                this.aMs.broadcastListener.method_05b43377ca0aa222e61a(errorcode);
            }
        } catch (Exception exception) {
            this.aMs.func_152820_d(exception.toString());
        }
    }

    public void setStreamInfoCallback(ErrorCode errorcode) {
        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152832_e(String.format("SetStreamInfoCallback got failure: %s", string));
        }
    }

    public void requestAuthTokenCallback(ErrorCode errorcode, AuthToken authtoken) {
        label21:
        {
            String text = IngestServerTester.sT();
            if (ErrorCode.succeeded(errorcode)) {
                this.aMs.authenticationToken = authtoken;
                this.aMs.func_152827_a(BroadcastControllerBroadcastState.Authenticated);
                if (text == null) {
                    break label21;
                }
            }

            this.aMs.authenticationToken.data = packedTextFragments[0];
            this.aMs.func_152827_a(BroadcastControllerBroadcastState.Initialized);
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152820_d(
                    String.format("RequestAuthTokenDoneCallback got failure: %s", string));
        }

        try {
            if (this.aMs.broadcastListener != null) {
                this.aMs.broadcastListener.func_152900_a(errorcode, authtoken);
            }
        } catch (Exception exception) {
            this.aMs.func_152820_d(exception.toString());
        }
    }

    public BroadcastStreamCallbacks(BroadcastController aMsValue) {
        this.aMs = aMsValue;
    }

    public void getUserInfoCallback(ErrorCode errorcode, UserInfo userinfo) {
        this.aMs.userInfo = userinfo;
        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152820_d(String.format("UserInfoDoneCallback got failure: %s", string));
        }
    }

    static {
        net.minecraft.client.stream.BroadcastStreamCallbacks.packedTextFragments = new String[3];
        net.minecraft.client.stream.BroadcastStreamCallbacks.packedTextFragments[0] = new String("");
    }

    public void getStreamInfoCallback(ErrorCode errorcode, StreamInfo streaminfo) {
        String text = IngestServerTester.sT();
        if (ErrorCode.succeeded(errorcode)) {
            this.aMs.streamInfo = streaminfo;

            try {
                if (this.aMs.broadcastListener != null) {
                    this.aMs.broadcastListener.func_152894_a(streaminfo);
                }

                return;
            } catch (Exception exception) {
                this.aMs.func_152820_d(exception.toString());
                if (text == null) {
                    return;
                }
            }
        }

        String string = ErrorCode.getString(errorcode);
        this.aMs.func_152832_e(String.format("StreamInfoDoneCallback got failure: %s", string));
    }

    public void sendActionMetaDataCallback(ErrorCode errorcode) {
        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152820_d(
                    String.format("sendActionMetaDataCallback got failure: %s", string));
        }
    }

    public void stopCallback(ErrorCode errorcode) {
        String text = IngestServerTester.sT();
        if (ErrorCode.succeeded(errorcode)) {
            this.aMs.videoParamaters = null;
            this.aMs.audioParamaters = null;
            this.aMs.nW();

            try {
                if (this.aMs.broadcastListener != null) {
                    this.aMs.broadcastListener.func_152901_c();
                }
            } catch (Exception exception) {
                this.aMs.func_152820_d(exception.toString());
            }

            if (this.aMs.field_152877_m) {
                this.aMs.func_152827_a(BroadcastControllerBroadcastState.ReadyToBroadcast);
                if (text == null) {
                    return;
                }
            }

            this.aMs.func_152827_a(BroadcastControllerBroadcastState.Initialized);
            if (text == null) {
                return;
            }
        }

        this.aMs.func_152827_a(BroadcastControllerBroadcastState.ReadyToBroadcast);
        String string = ErrorCode.getString(errorcode);
        this.aMs.func_152820_d(String.format("stopCallback got failure: %s", string));
    }

    public void getGameNameListCallback(ErrorCode errorcode, GameInfoList gameinfolist) {
        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152820_d(String.format("GameNameListCallback got failure: %s", string));
        }

        try {
            if (this.aMs.broadcastListener != null) {
                this.aMs.broadcastListener.func_152898_a(
                        errorcode, gameinfolist == null ? new GameInfo[0] : gameinfolist.list);
            }
        } catch (Exception exception) {
            this.aMs.func_152820_d(exception.toString());
        }
    }

    public void sendEndSpanMetaDataCallback(ErrorCode errorcode) {
        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.aMs.func_152820_d(
                    String.format("sendEndSpanMetaDataCallback got failure: %s", string));
        }
    }

    public void startCallback(ErrorCode errorcode) {
        String text = IngestServerTester.sT();
        if (ErrorCode.succeeded(errorcode)) {
            try {
                if (this.aMs.broadcastListener != null) {
                    this.aMs.broadcastListener.func_152899_b();
                }
            } catch (Exception exception1) {
                this.aMs.func_152820_d(exception1.toString());
            }

            this.aMs.func_152827_a(BroadcastControllerBroadcastState.Broadcasting);
            if (text == null) {
                return;
            }
        }

        this.aMs.videoParamaters = null;
        this.aMs.audioParamaters = null;
        this.aMs.func_152827_a(BroadcastControllerBroadcastState.ReadyToBroadcast);

        try {
            if (this.aMs.broadcastListener != null) {
                this.aMs.broadcastListener.method_17259210e80146ea0cdb(errorcode);
            }
        } catch (Exception exception) {
            this.aMs.func_152820_d(exception.toString());
        }

        String string = ErrorCode.getString(errorcode);
        this.aMs.func_152820_d(String.format("startCallback got failure: %s", string));
    }

    public void getIngestServersCallback(ErrorCode errorcode, IngestList ingestlist) {
        String text = IngestServerTester.sT();
        if (ErrorCode.succeeded(errorcode)) {
            this.aMs.ingestList = ingestlist;
            this.aMs.field_152884_t = this.aMs.ingestList.getDefaultServer();
            this.aMs.func_152827_a(BroadcastControllerBroadcastState.ReceivedIngestServers);

            try {
                if (this.aMs.broadcastListener != null) {
                    this.aMs.broadcastListener.func_152896_a(ingestlist);
                }

                return;
            } catch (Exception exception) {
                this.aMs.func_152820_d(exception.toString());
                if (text == null) {
                    return;
                }
            }
        }

        String string = ErrorCode.getString(errorcode);
        this.aMs.func_152820_d(String.format("IngestListCallback got failure: %s", string));
        this.aMs.func_152827_a(BroadcastControllerBroadcastState.LoggingIn);
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public void getArchivingStateCallback(ErrorCode errorcode, ArchivingState archivingstate) {
        this.aMs.field_152889_y = archivingstate;
        if (ErrorCode.failed(errorcode)) {}
    }
}
