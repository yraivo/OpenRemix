package net.minecraft.client.stream;

import com.google.common.collect.Lists;

import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ThreadSafeBoundList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tv.twitch.AuthToken;
import tv.twitch.Core;
import tv.twitch.ErrorCode;
import tv.twitch.MessageLevel;
import tv.twitch.StandardCoreAPI;
import tv.twitch.broadcast.ArchivingState;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.ChannelInfo;
import tv.twitch.broadcast.DesktopStreamAPI;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.Stream;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.UserInfo;
import tv.twitch.broadcast.VideoParams;

import java.util.Arrays;
import java.util.List;

public class BroadcastController {
    protected int c;
    protected String h;
    protected long field_152890_z;

    public List field_152875_k;
    public StreamInfo streamInfo;
    protected final int cz = 30;
    protected boolean field_152876_l;
    protected IngestServerTester field_152860_A;
    public IngestList ingestList;
    protected String g;
    protected Core field_152872_h;
    public ArchivingState field_152889_y;
    private ErrorCode errorCode;
    private String field_152863_D;
    public AudioParams audioParamaters;
    public ChannelInfo channelInfo;
    protected List field_152874_j;
    private static ThreadSafeBoundList field_152862_C;
    private static Logger logger;
    private static String[] packedTextFragments;
    public UserInfo userInfo;
    protected BroadcastControllerBroadcastState broadcastState;
    protected String s;
    protected boolean mx;
    public BroadcastControllerBroadcastListener broadcastListener;

    public AuthToken authenticationToken;
    protected String te;
    protected Stream field_152873_i;
    protected IStatCallbacks field_177949_C;
    protected IStreamCallbacks field_177948_B;
    public boolean field_152877_m;
    protected boolean lL;
    public VideoParams videoParamaters;
    public IngestServer field_152884_t;

    public boolean func_152849_q() {
        return this.field_152877_m;
    }

    protected void nH() {
        long i = System.nanoTime();
        long k = i - this.field_152890_z;

        long j = k / 1000000000L;

        if (j >= 30L) {
            this.field_152890_z = i;
            ErrorCode errorcode =
                    this.field_152873_i.getStreamInfo(this.authenticationToken, this.s);
            if (ErrorCode.failed(errorcode)) {
                String sx = ErrorCode.getString(errorcode);
                this.func_152820_d(String.format("Error in TTV_GetStreamInfo: %s", sx));
            }
        }
    }

    public void setPlaybackDeviceVolume(float p171_0) {
        this.field_152873_i.setVolume(
                tv.twitch.broadcast.AudioDeviceType.TTV_PLAYBACK_DEVICE, p171_0);
    }

    public void statCallback() {
        String sx = IngestServerTester.sT();
        if (this.broadcastState != BroadcastControllerBroadcastState.Uninitialized) {
            if (this.field_152860_A != null) {
                this.field_152860_A.dn();
            }

            while (this.field_152860_A != null) {
                try {

                    Thread.sleep(200L);
                } catch (Exception exception) {
                    this.func_152820_d(exception.toString());
                }

                this.nq();
                if (sx != null) {
                    break;
                }
            }

            this.kA();
        }
    }

    public IngestList getIngestList() {
        return this.ingestList;
    }

    protected boolean method_3d420e2dafed708bd0cc(ErrorCode p171_0) {
        try {
            if (tv.twitch.ErrorCode.failed(p171_0)) {
                this.func_152820_d(tv.twitch.ErrorCode.getString(p171_0));
                return false;
            } else {
                return true;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.unchecked(
                    net.minecraft.client.stream.BroadcastController.a(reportedException));
        }
    }

    public boolean func_152847_F() {
        String sx = IngestServerTester.sT();
        if (!this.isBroadcasting()) {
            return false;
        }

        ErrorCode errorcode = this.field_152873_i.pauseVideo();
        if (ErrorCode.failed(errorcode)) {
            this.stopBroadcasting();
            String string = ErrorCode.getString(errorcode);
            this.func_152820_d(String.format("Error pausing stream: %s\n", string));
            if (sx == null) {
                return ErrorCode.succeeded(errorcode);
            }
        }

        this.func_152827_a(BroadcastControllerBroadcastState.Paused);
        return ErrorCode.succeeded(errorcode);
    }

    public IngestServerTester func_152838_J() {
        if (!this.isReadyToBroadcast() || this.ingestList == null) {
            return null;
        }

        if (this.isIngestTesting()) {
            return null;
        }

        this.field_152860_A = new IngestServerTester(this.field_152873_i, this.ingestList);
        this.field_152860_A.func_176004_j();
        this.func_152827_a(BroadcastControllerBroadcastState.IngestTesting);
        return this.field_152860_A;
    }

    public boolean isIngestTesting() {
        return this.broadcastState == BroadcastControllerBroadcastState.IngestTesting;
    }

    public void func_152824_a(IngestServer ingestserver) {
        this.field_152884_t = ingestserver;
    }

    public boolean stopBroadcasting() {
        if (!this.isBroadcasting()) {
            return false;
        } else {
            ErrorCode errorcode = this.field_152873_i.stop(true);
            if (ErrorCode.failed(errorcode)) {
                String sx = ErrorCode.getString(errorcode);
                this.func_152820_d(String.format("Error while stopping the broadcast: %s", sx));
                return false;
            } else {
                this.func_152827_a(BroadcastControllerBroadcastState.Stopping);
                return ErrorCode.succeeded(errorcode);
            }
        }
    }

    public boolean method_6c4cb404b8d548363d69(String text, AuthToken authtoken) {
        if (this.isIngestTesting()) {
            return false;
        }

        this.je();
        if (text == null || text.isEmpty()) {
            this.func_152820_d("Username must be valid");
            return false;
        }

        if (authtoken != null && authtoken.data != null && !authtoken.data.isEmpty()) {
            this.s = text;
            this.authenticationToken = authtoken;
            if (this.func_152858_b()) {
                this.func_152827_a(BroadcastControllerBroadcastState.Authenticated);
            }

            return true;
        } else {
            this.func_152820_d("Auth token must be valid");
            return false;
        }
    }

    public boolean func_152858_b() {
        return this.field_152876_l;
    }

    public boolean func_152854_G() {
        if (!this.isBroadcastPaused()) {
            return false;
        }

        this.func_152827_a(BroadcastControllerBroadcastState.Broadcasting);
        return true;
    }

    public boolean dI() {
        if (this.field_152876_l) {
            return false;
        } else {
            this.field_152873_i.setStreamCallbacks(this.field_177948_B);
            ErrorCode errorcode =
                    this.field_152872_h.initialize(this.g, System.getProperty("java.library.path"));
            if (!this.method_3d420e2dafed708bd0cc(errorcode)) {
                this.field_152873_i.setStreamCallbacks((IStreamCallbacks) null);
                this.errorCode = errorcode;
                return false;
            } else {
                errorcode = this.field_152872_h.setTraceLevel(MessageLevel.TTV_ML_ERROR);
                if (!this.method_3d420e2dafed708bd0cc(errorcode)) {
                    this.field_152873_i.setStreamCallbacks((IStreamCallbacks) null);
                    this.field_152872_h.shutdown();
                    this.errorCode = errorcode;
                    return false;
                } else if (ErrorCode.succeeded(errorcode)) {
                    this.field_152876_l = true;
                    this.func_152827_a(BroadcastControllerBroadcastState.Initialized);
                    return true;
                } else {
                    this.errorCode = errorcode;
                    this.field_152872_h.shutdown();
                    return false;
                }
            }
        }
    }

    public boolean isBroadcastPaused() {
        return this.broadcastState == BroadcastControllerBroadcastState.Paused;
    }

    public void method_be01d1eb5e587c583ed6(String text) {
        this.g = text;
    }

    public boolean method_fc7bda2aa8546e15d1ac(VideoParams videoparams) {
        if (videoparams != null && this.isReadyToBroadcast()) {
            this.videoParamaters = videoparams.clone();
            this.audioParamaters = new AudioParams();
            this.audioParamaters.audioEnabled =
                    remix.compat.compiler.JvmBooleanSlots.fromInt((this.mx && this.dJ() ? 1 : 0));
            this.audioParamaters.enableMicCapture = this.audioParamaters.audioEnabled;
            this.audioParamaters.enablePlaybackCapture = this.audioParamaters.audioEnabled;
            this.audioParamaters.enablePassthroughAudio = false;
            if (!this.jR()) {
                this.videoParamaters = null;
                this.audioParamaters = null;
                return false;
            } else {
                ErrorCode errorcode =
                        this.field_152873_i.start(
                                videoparams,
                                this.audioParamaters,
                                this.field_152884_t,
                                StartFlags.None,
                                true);
                if (ErrorCode.failed(errorcode)) {
                    this.nW();
                    String sx = ErrorCode.getString(errorcode);
                    this.func_152820_d(String.format("Error while starting to broadcast: %s", sx));
                    this.videoParamaters = null;
                    this.audioParamaters = null;
                    return false;
                } else {
                    this.func_152827_a(BroadcastControllerBroadcastState.Starting);
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    protected boolean dJ() {
        return true;
    }

    public boolean method_cccb8bbcc1bebe0a2111(String text, String gameName, String streamTitle) {
        try {
            if (!this.field_152877_m) {
                return false;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.unchecked(
                    net.minecraft.client.stream.BroadcastController.a(reportedException));
        }

        {
            boolean runGuardedCode;
            try {
                runGuardedCode =
                        !(text != null
                                && !text.equals(
                                        net.minecraft.client.stream.BroadcastController
                                                .packedTextFragments[4]));
            } catch (net.minecraft.util.ReportedException reportedExceptionValue) {
                throw remix.compat.compiler.JvmThrowTypes.unchecked(
                        net.minecraft.client.stream.BroadcastController.a(reportedExceptionValue));
            }
            if (runGuardedCode) {

                text = this.s;
            }
        }

        if (gameName == null) {
            gameName = net.minecraft.client.stream.BroadcastController.packedTextFragments[5];
        }

        if (streamTitle == null) {
            streamTitle = net.minecraft.client.stream.BroadcastController.packedTextFragments[6];
        }

        tv.twitch.broadcast.StreamInfoForSetting streaminfoforsetting =
                new tv.twitch.broadcast.StreamInfoForSetting();
        streaminfoforsetting.streamTitle = streamTitle;
        streaminfoforsetting.gameName = gameName;
        tv.twitch.ErrorCode errorcode =
                this.field_152873_i.setStreamInfo(
                        this.authenticationToken, text, streaminfoforsetting);
        this.method_3d420e2dafed708bd0cc(errorcode);
        return tv.twitch.ErrorCode.succeeded(errorcode);
    }

    public boolean isBroadcasting() {
        return this.broadcastState == BroadcastControllerBroadcastState.Broadcasting
                || this.broadcastState == BroadcastControllerBroadcastState.Paused;
    }

    public void func_152820_d(String text) {
        this.field_152863_D = text;
        net.minecraft.client.stream.BroadcastController.field_152862_C.func_152757_a(
                "<Error> " + text);
        net.minecraft.client.stream.BroadcastController.logger.error(
                TwitchStream.STREAM_MARKER, "[Broadcast controller] {}", new Object[] {text});
    }

    public void setBroadcastListener(BroadcastControllerBroadcastListener aMlValue) {
        this.broadcastListener = aMlValue;
    }

    public void func_152832_e(String text) {
        net.minecraft.client.stream.BroadcastController.field_152862_C.func_152757_a(
                "<Warning> " + text);
        net.minecraft.client.stream.BroadcastController.logger.warn(
                TwitchStream.STREAM_MARKER, "[Broadcast controller] {}", new Object[] {text});
    }

    public boolean func_152840_a(String text, long i, String textValue, String textValue2) {
        ErrorCode errorcode =
                this.field_152873_i.sendActionMetaData(
                        this.authenticationToken, text, i, textValue, textValue2);
        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.func_152820_d(String.format("Error while sending meta data: %s\n", string));
            return false;
        } else {
            return true;
        }
    }

    public IngestServer func_152833_s() {
        return this.field_152884_t;
    }

    public IngestServerTester isReady() {
        return this.field_152860_A;
    }

    public boolean func_177947_a(String text, long i, long j, String textValue, String textValue2) {

        if (j == -1L) {
            this.func_152820_d(String.format("Invalid sequence id: %d\n", j));
            return false;
        } else {
            ErrorCode errorcode =
                    this.field_152873_i.sendEndSpanMetaData(
                            this.authenticationToken, text, i, j, textValue, textValue2);
            if (ErrorCode.failed(errorcode)) {
                String string = ErrorCode.getString(errorcode);
                this.func_152820_d(String.format("Error in SendStopSpanMetaData: %s\n", string));
                return false;
            } else {
                return true;
            }
        }
    }

    public void func_152827_a(BroadcastControllerBroadcastState aMkValue) {
        if (aMkValue != this.broadcastState) {
            this.broadcastState = aMkValue;

            try {
                if (this.broadcastListener != null) {
                    this.broadcastListener.func_152891_a(aMkValue);
                }
            } catch (Exception exception) {
                this.func_152820_d(exception.toString());
            }
        }
    }

    public boolean isReadyToBroadcast() {
        return this.broadcastState == BroadcastControllerBroadcastState.ReadyToBroadcast;
    }

    public long func_152844_x() {
        return this.field_152873_i.getStreamTime();
    }

    public boolean kA() {
        if (!this.field_152876_l) {
            return true;
        }

        if (this.isIngestTesting()) {
            return false;
        }

        this.lL = true;
        this.je();
        this.field_152873_i.setStreamCallbacks((IStreamCallbacks) null);
        this.field_152873_i.setStatCallbacks((IStatCallbacks) null);
        ErrorCode errorcode = this.field_152872_h.shutdown();
        this.method_3d420e2dafed708bd0cc(errorcode);
        this.field_152876_l = false;
        this.lL = false;
        this.func_152827_a(BroadcastControllerBroadcastState.Uninitialized);
        return true;
    }

    public ErrorCode submitStreamFrame(FrameBuffer framebuffer) {
        String sx;
        {
            sx = IngestServerTester.sT();
            if (this.isBroadcastPaused()) {
                this.func_152854_G();
                if (!(sx == null)) {

                    if (!this.isBroadcasting()) {
                        return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
                    }
                }
            } else {

                if (!this.isBroadcasting()) {
                    return ErrorCode.TTV_EC_STREAM_NOT_STARTED;
                }
            }
        }

        ErrorCode errorcode = this.field_152873_i.submitVideoFrame(framebuffer);
        if (errorcode != ErrorCode.TTV_EC_SUCCESS) {
            {
                String string = ErrorCode.getString(errorcode);
                if (ErrorCode.succeeded(errorcode)) {
                    this.func_152832_e(
                            String.format("Warning in SubmitTexturePointer: %s\n", string));
                    if (!(sx == null)) {

                        this.func_152820_d(
                                String.format("Error in SubmitTexturePointer: %s\n", string));
                        this.stopBroadcasting();
                    }
                } else {

                    this.func_152820_d(
                            String.format("Error in SubmitTexturePointer: %s\n", string));
                    this.stopBroadcasting();
                }
            }

            if (this.broadcastListener != null) {
                this.broadcastListener.method_b3805c57e9d8e69c1958(errorcode);
            }
        }

        return errorcode;
    }

    public void setRecordingDeviceVolume(float p171_0) {
        this.field_152873_i.setVolume(
                tv.twitch.broadcast.AudioDeviceType.TTV_RECORDER_DEVICE, p171_0);
    }

    public boolean kb() {
        try {
            if (!this.isBroadcasting()) {
                return false;
            }
        } catch (net.minecraft.util.ReportedException reportedException) {
            throw remix.compat.compiler.JvmThrowTypes.unchecked(
                    net.minecraft.client.stream.BroadcastController.a(reportedException));
        }

        tv.twitch.ErrorCode errorcode = this.field_152873_i.runCommercial(this.authenticationToken);
        this.method_3d420e2dafed708bd0cc(errorcode);
        return tv.twitch.ErrorCode.succeeded(errorcode);
    }

    static {
        net.minecraft.client.stream.BroadcastController.packedTextFragments = new String[8];
        net.minecraft.client.stream.BroadcastController.packedTextFragments[0] = new String("");
        net.minecraft.client.stream.BroadcastController.packedTextFragments[1] = new String("");
        net.minecraft.client.stream.BroadcastController.packedTextFragments[2] = new String("");
        net.minecraft.client.stream.BroadcastController.packedTextFragments[3] = new String("");
        net.minecraft.client.stream.BroadcastController.packedTextFragments[4] = new String("");
        net.minecraft.client.stream.BroadcastController.packedTextFragments[5] = new String("");
        net.minecraft.client.stream.BroadcastController.packedTextFragments[6] = new String("");
        net.minecraft.client.stream.BroadcastController.logger = LogManager.getLogger();
        net.minecraft.client.stream.BroadcastController.field_152862_C =
                new ThreadSafeBoundList(String.class, 50);
    }

    protected boolean jR() {
        String text = IngestServerTester.sT();
        int i = 0;
        String sx = text;

        while (i < 3) {
            FrameBuffer framebuffer =
                    this.field_152873_i.allocateFrameBuffer(
                            this.videoParamaters.outputWidth
                                    * this.videoParamaters.outputHeight
                                    * 4);
            if (!framebuffer.getIsValid()) {
                this.func_152820_d(String.format("Error while allocating frame buffer"));
                return false;
            }

            this.field_152874_j.add(framebuffer);
            this.field_152875_k.add(framebuffer);
            i++;
            if (sx != null) {
                break;
            }
        }

        return true;
    }

    public VideoParams func_152834_a(int maxKbps, int targetFps, float p171_2, float p171_3) {
        int[] aint = this.field_152873_i.getMaxResolution(maxKbps, targetFps, p171_2, p171_3);
        tv.twitch.broadcast.VideoParams videoparams = new tv.twitch.broadcast.VideoParams();
        videoparams.maxKbps = maxKbps;
        videoparams.encodingCpuUsage = tv.twitch.broadcast.EncodingCpuUsage.TTV_ECU_HIGH;
        videoparams.pixelFormat = this.func_152826_z();
        videoparams.targetFps = targetFps;
        videoparams.outputWidth = aint[0];
        videoparams.outputHeight = aint[1];
        videoparams.disableAdaptiveBitrate = false;
        videoparams.verticalFlip = false;
        return videoparams;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

    public void nq() {
        String sx = IngestServerTester.sT();
        if (this.field_152873_i != null && this.field_152876_l) {
            ErrorCode errorcode = this.field_152873_i.pollTasks();
            this.method_3d420e2dafed708bd0cc(errorcode);
            if (this.isIngestTesting()) {
                this.field_152860_A.cS();
                if (this.field_152860_A.func_153032_e()) {
                    this.field_152860_A = null;
                    this.func_152827_a(BroadcastControllerBroadcastState.ReadyToBroadcast);
                }
            }

            switch (this.broadcastState) {
                case Authenticated:
                    this.func_152827_a(BroadcastControllerBroadcastState.LoggingIn);
                    errorcode = this.field_152873_i.login(this.authenticationToken);
                    if (!ErrorCode.failed(errorcode)) {
                        break;
                    }

                    String string = ErrorCode.getString(errorcode);
                    this.func_152820_d(String.format("Error in TTV_Login: %s\n", string));
                    if (sx == null) {
                        break;
                    }
                case LoggedIn:
                    this.func_152827_a(BroadcastControllerBroadcastState.FindingIngestServer);
                    errorcode = this.field_152873_i.getIngestServers(this.authenticationToken);
                    if (!ErrorCode.failed(errorcode)) {
                        break;
                    }

                    this.func_152827_a(BroadcastControllerBroadcastState.LoggedIn);
                    String stringValue = ErrorCode.getString(errorcode);
                    this.func_152820_d(
                            String.format("Error in TTV_GetIngestServers: %s\n", stringValue));
                    if (sx == null) {
                        break;
                    }
                case ReceivedIngestServers:
                    this.func_152827_a(BroadcastControllerBroadcastState.ReadyToBroadcast);
                    errorcode = this.field_152873_i.getUserInfo(this.authenticationToken);
                    if (ErrorCode.failed(errorcode)) {
                        String stringValue2 = ErrorCode.getString(errorcode);
                        this.func_152820_d(
                                String.format("Error in TTV_GetUserInfo: %s\n", stringValue2));
                    }

                    this.nH();
                    errorcode = this.field_152873_i.getArchivingState(this.authenticationToken);
                    if (ErrorCode.failed(errorcode)) {
                        String stringValue3 = ErrorCode.getString(errorcode);
                        this.func_152820_d(
                                String.format(
                                        "Error in TTV_GetArchivingState: %s\n", stringValue3));
                    }
                case Starting:
                case Stopping:
                case FindingIngestServer:
                case Authenticating:
                case Initialized:
                case Uninitialized:
                case IngestTesting:
                default:
                    if (sx == null) {
                        break;
                    }
                case Paused:
                case Broadcasting:
                    this.nH();
            }
        }
    }

    public boolean je() {
        if (this.isIngestTesting()) {
            return false;
        }

        if (this.isBroadcasting()) {
            this.field_152873_i.stop(false);
        }

        this.s = packedTextFragments[3];
        this.authenticationToken = new AuthToken();
        if (!this.field_152877_m) {
            return false;
        }

        this.field_152877_m = false;
        if (!this.lL) {
            try {
                if (this.broadcastListener != null) {
                    this.broadcastListener.func_152895_a();
                }
            } catch (Exception exception) {
                this.func_152820_d(exception.toString());
            }
        }

        this.func_152827_a(BroadcastControllerBroadcastState.Initialized);
        return true;
    }

    protected PixelFormat func_152826_z() {
        return PixelFormat.TTV_PF_RGBA;
    }

    public void nW() {
        String text = IngestServerTester.sT();
        int i = 0;
        String sx = text;

        while (i < this.field_152874_j.size()) {
            FrameBuffer framebuffer = (FrameBuffer) this.field_152874_j.get(i);
            framebuffer.free();
            i++;
            if (sx != null) {
                break;
            }
        }

        this.field_152875_k.clear();
        this.field_152874_j.clear();
    }

    // region String decoding support

    // endregion

    private static Exception a(Exception exception) {
        return exception;
    }

    public FrameBuffer func_152822_N() {
        if (this.field_152875_k.size() == 0) {
            this.func_152820_d(String.format("Out of free buffers, this should never happen"));
            return null;
        } else {
            FrameBuffer framebuffer =
                    (FrameBuffer) this.field_152875_k.get(this.field_152875_k.size() - 1);
            this.field_152875_k.remove(this.field_152875_k.size() - 1);
            return framebuffer;
        }
    }

    public long func_177946_b(String text, long i, String textValue, String textValue2) {
        long j =
                this.field_152873_i.sendStartSpanMetaData(
                        this.authenticationToken, text, i, textValue, textValue2);

        if (j == -1L) {
            this.func_152820_d(String.format("Error in SendStartSpanMetaData\n"));
        }

        return j;
    }

    // region String decoding support

    // endregion

    public void captureFramebuffer(FrameBuffer framebuffer) {
        try {
            this.field_152873_i.captureFrameBuffer_ReadPixels(framebuffer);
        } catch (Throwable throwable) {
            CrashReport crashReport =
                    CrashReport.makeCrashReport(throwable, "Trying to submit a frame to Twitch");
            CrashReportCategory crashReportCategory = crashReport.makeCategory("Broadcast State");
            crashReportCategory.addCrashSection(
                    "Last reported errors",
                    Arrays.toString(
                            net.minecraft.client.stream.BroadcastController.field_152862_C
                                    .func_152756_c()));
            crashReportCategory.addCrashSection("Buffer", framebuffer);
            crashReportCategory.addCrashSection("Free buffer count", this.field_152875_k.size());
            crashReportCategory.addCrashSection("Capture buffer count", this.field_152874_j.size());
            throw new ReportedException(crashReport);
        }
    }

    public StreamInfo getStreamInfo() {
        return this.streamInfo;
    }

    public ChannelInfo getChannelInfo() {
        return this.channelInfo;
    }

    public BroadcastController() {
        this.c = 3;
        this.field_152863_D = null;
        this.broadcastListener = null;
        this.g = packedTextFragments[0];
        this.h = packedTextFragments[1];
        this.te = packedTextFragments[2];
        this.mx = true;
        this.field_152872_h = null;
        this.field_152873_i = null;
        String text = IngestServerTester.sT();
        this.field_152874_j = Lists.newArrayList();
        this.field_152875_k = Lists.newArrayList();
        this.field_152876_l = false;
        this.field_152877_m = false;
        this.lL = false;
        this.broadcastState = BroadcastControllerBroadcastState.Uninitialized;
        this.s = null;
        this.videoParamaters = null;
        this.audioParamaters = null;
        this.ingestList = new IngestList(new IngestServer[0]);
        this.field_152884_t = null;
        this.authenticationToken = new AuthToken();
        this.channelInfo = new ChannelInfo();
        this.userInfo = new UserInfo();
        String sx = text;
        this.streamInfo = new StreamInfo();
        this.field_152889_y = new ArchivingState();

        this.field_152890_z = 0L;
        this.field_152860_A = null;
        this.field_177948_B = new BroadcastStreamCallbacks(this);
        this.field_177949_C = new IStatCallbacks2(this);
        this.field_152872_h = Core.getInstance();
        if (Core.getInstance() == null) {
            this.field_152872_h = new Core(new StandardCoreAPI());
        }

        this.field_152873_i = new Stream(new DesktopStreamAPI());
        if (sx != null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    // Helper used only by this class.
    public static class IStatCallbacks2 implements tv.twitch.broadcast.IStatCallbacks {
        net.minecraft.client.stream.BroadcastController aMs;

        public void statCallback(tv.twitch.broadcast.StatType stattype, long i) {}

        public IStatCallbacks2(net.minecraft.client.stream.BroadcastController aMsValue) {
            this.aMs = aMsValue;
        }
    }
}
