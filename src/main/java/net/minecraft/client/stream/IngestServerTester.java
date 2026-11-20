package net.minecraft.client.stream;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;

import tv.twitch.ErrorCode;
import tv.twitch.broadcast.AudioParams;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.IStatCallbacks;
import tv.twitch.broadcast.IStreamCallbacks;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.PixelFormat;
import tv.twitch.broadcast.RTMPState;
import tv.twitch.broadcast.StartFlags;
import tv.twitch.broadcast.Stream;
import tv.twitch.broadcast.VideoParams;

import java.util.List;

public class IngestServerTester {
    public long field_153050_h;
    protected long awy;
    protected IngestServerTesterIngestTestListener field_153044_b = null;
    protected int field_153063_u;
    protected int field_153062_t;
    public boolean oK;
    public boolean field_176008_y;
    private static String yz;
    public boolean field_153056_n;
    protected IStatCallbacks aMP;
    protected long field_153064_v;
    public RTMPState field_153051_i;
    protected long aMR;
    protected IngestServerTesterIngestTestState field_153047_e;
    protected float awp;
    public boolean lL;
    public IngestServer field_153059_q;
    protected VideoParams field_153052_j;
    public long wc;
    protected AudioParams audioParameters;
    private static String[] packedTextFragments;
    private static String zc;
    protected IStatCallbacks aMX;
    protected boolean lc;
    protected IngestList field_153046_d;
    public boolean nC;
    protected float w;
    protected IStreamCallbacks aMZ;
    protected Stream field_153045_c;
    protected IStreamCallbacks aNb;
    protected List field_153055_m;

    public void func_153042_a(IngestServerTesterIngestTestListener aMOValue) {
        this.field_153044_b = aMOValue;
    }

    public static void b(String text) {
        yz = text;
    }

    // region String decoding support

    // endregion

    protected void fZ() {
        String text = sT();
        float f = (float) this.CU();
        String textValue = text;
        switch (this.field_153047_e) {
            case Starting:
            case ConnectingToServer:
            case Uninitalized:
            case Finished:
            case Cancelled:
            case Failed:
                this.w = 0.0F;
                if (textValue == null) {
                    break;
                }
            case DoneTestingServer:
                this.w = 1.0F;
                if (textValue == null) {
                    break;
                }
            case TestingServer:
            case Cancelling:
            default:
                this.w = f / (float) this.awy;
        }

        switch (this.field_153047_e) {
            case Finished:
            case Cancelled:
            case Failed:
                this.awp = 1.0F;
                if (textValue == null) {
                    break;
                }
            default:
                this.awp = (float) this.field_153062_t / this.field_153046_d.getServers().length;
                this.awp = this.awp + this.w / this.field_153046_d.getServers().length;
        }
    }

    public int func_153028_p() {
        return this.field_153062_t;
    }

    public static String sT() {
        return yz;
    }

    protected void method_e5daaeb468e1a294e962(IngestServer ingestserver) {
        String text = sT();
        if (this.field_176008_y) {
            this.lc = true;
            if (text == null) {
                return;
            }
        }

        if (this.nC) {
            this.oK = true;
            ErrorCode errorcode = this.field_153045_c.stop(true);
            if (ErrorCode.failed(errorcode)) {
                this.aNb.stopCallback(ErrorCode.TTV_EC_SUCCESS);
                System.out.println(zc + errorcode.toString());
            }

            this.field_153045_c.pollStats();
            if (text == null) {
                return;
            }
        }

        this.aNb.stopCallback(ErrorCode.TTV_EC_SUCCESS);
    }

    public void func_153034_a(IngestServerTesterIngestTestState aMSValue) {
        if (aMSValue != this.field_153047_e) {
            this.field_153047_e = aMSValue;
            if (this.field_153044_b != null) {
                this.field_153044_b.func_152907_a(this, aMSValue);
            }
        }
    }

    protected long CU() {
        return System.currentTimeMillis() - this.wc;
    }

    public void func_176004_j() {
        String text = sT();
        if (this.field_153047_e == IngestServerTesterIngestTestState.Uninitalized) {
            this.field_153062_t = 0;
            this.lL = false;
            this.lc = false;
            this.nC = false;
            this.field_176008_y = false;
            this.oK = false;
            this.aMX = this.field_153045_c.getStatCallbacks();
            this.field_153045_c.setStatCallbacks(this.aMP);
            this.aMZ = this.field_153045_c.getStreamCallbacks();
            this.field_153045_c.setStreamCallbacks(this.aNb);
            this.field_153052_j = new VideoParams();
            this.field_153052_j.targetFps = 60;
            this.field_153052_j.maxKbps = 3500;
            this.field_153052_j.outputWidth = 1280;
            this.field_153052_j.outputHeight = 720;
            this.field_153052_j.pixelFormat = PixelFormat.TTV_PF_BGRA;
            this.field_153052_j.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
            this.field_153052_j.disableAdaptiveBitrate = true;
            this.field_153052_j.verticalFlip = false;
            this.field_153045_c.getDefaultParams(this.field_153052_j);
            this.audioParameters = new AudioParams();
            this.audioParameters.audioEnabled = false;
            this.audioParameters.enableMicCapture = false;
            this.audioParameters.enablePlaybackCapture = false;
            this.audioParameters.enablePassthroughAudio = false;
            this.field_153055_m = Lists.newArrayList();
            byte b0 = 3;
            int i = 0;

            while (i < b0) {
                FrameBuffer framebuffer =
                        this.field_153045_c.allocateFrameBuffer(
                                this.field_153052_j.outputWidth
                                        * this.field_153052_j.outputHeight
                                        * 4);
                if (!framebuffer.getIsValid()) {
                    this.ha();
                    this.func_153034_a(IngestServerTesterIngestTestState.Failed);
                    return;
                }

                this.field_153055_m.add(framebuffer);
                this.field_153045_c.randomizeFrameBuffer(framebuffer);
                i++;
                if (text != null) {
                    break;
                }
            }

            this.func_153034_a(IngestServerTesterIngestTestState.Starting);
            this.wc = System.currentTimeMillis();
        }
    }

    public float aJ() {
        return this.w;
    }

    protected boolean method_a4a2cf176465d25bf56a(IngestServer ingestserver) {
        if (this.lc || this.lL || this.CU() >= this.awy) {
            this.func_153034_a(IngestServerTesterIngestTestState.DoneTestingServer);
            return true;
        }

        if (!this.field_176008_y && !this.oK) {
            ErrorCode errorcode =
                    this.field_153045_c.submitVideoFrame(
                            (FrameBuffer) this.field_153055_m.get(this.field_153063_u));
            if (ErrorCode.failed(errorcode)) {
                this.field_153056_n = false;
                this.func_153034_a(IngestServerTesterIngestTestState.DoneTestingServer);
                return false;
            }

            this.field_153063_u = (this.field_153063_u + 1) % this.field_153055_m.size();
            this.field_153045_c.pollStats();
            if (this.field_153051_i == RTMPState.SendVideo) {
                this.func_153034_a(IngestServerTesterIngestTestState.TestingServer);
                long i = this.CU();

                if (i > 0L && this.field_153050_h > this.field_153064_v) {

                    ingestserver.bitrateKbps =
                            (float) (this.field_153050_h * 8L) / (float) this.CU();
                    this.field_153064_v = this.field_153050_h;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public boolean func_153032_e() {
        return this.field_153047_e == IngestServerTesterIngestTestState.Finished
                || this.field_153047_e == IngestServerTesterIngestTestState.Cancelled
                || this.field_153047_e == IngestServerTesterIngestTestState.Failed;
    }

    protected boolean func_153036_a(IngestServer ingestserver) {
        this.field_153056_n = true;

        this.field_153050_h = 0L;
        this.field_153051_i = RTMPState.Idle;
        this.field_153059_q = ingestserver;
        this.field_176008_y = true;
        this.func_153034_a(IngestServerTesterIngestTestState.ConnectingToServer);
        ErrorCode errorcode =
                this.field_153045_c.start(
                        this.field_153052_j,
                        this.audioParameters,
                        ingestserver,
                        StartFlags.TTV_Start_BandwidthTest,
                        true);
        if (ErrorCode.failed(errorcode)) {
            this.field_176008_y = false;
            this.field_153056_n = false;
            this.func_153034_a(IngestServerTesterIngestTestState.DoneTestingServer);
            return false;
        } else {
            this.field_153064_v = this.field_153050_h;
            ingestserver.bitrateKbps = 0.0F;
            this.field_153063_u = 0;
            return true;
        }
    }

    public IngestServer func_153040_c() {
        return this.field_153059_q;
    }

    protected void ha() {
        String text = sT();
        this.field_153059_q = null;
        String textValue = text;
        if (this.field_153055_m != null) {
            int i = 0;

            while (i < this.field_153055_m.size()) {
                ((FrameBuffer) this.field_153055_m.get(i)).free();
                i++;
                if (textValue != null) {
                    break;
                }
            }

            this.field_153055_m = null;
        }

        if (this.field_153045_c.getStatCallbacks() == this.aMP) {
            this.field_153045_c.setStatCallbacks(this.aMX);
            this.aMX = null;
        }

        if (this.field_153045_c.getStreamCallbacks() == this.aNb) {
            this.field_153045_c.setStreamCallbacks(this.aMZ);
            this.aMZ = null;
        }
    }

    static {
        net.minecraft.client.stream.IngestServerTester.packedTextFragments = new String[3];
        net.minecraft.client.stream.IngestServerTester.packedTextFragments[0] = new String("NdSXP");
        net.minecraft.client.stream.IngestServerTester.packedTextFragments[1] =
                new String("weE1Jc");
        net.minecraft.client.stream.IngestServerTester.packedTextFragments[2] =
                new String("\u0002o\u007f\u0012tDI8wu\u0006n\u0002");
        if (sT() != null) {
            b(packedTextFragments[1]);
        }
        zc = "Stop failed: ";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public void dn() {
        if (!this.func_153032_e() && !this.lL) {
            this.lL = true;
            if (this.field_153059_q != null) {
                this.field_153059_q.bitrateKbps = 0.0F;
            }
        }
    }

    public IngestServerTester(Stream stream, IngestList ingestlist) {
        sT();
        this.field_153045_c = null;
        this.field_153046_d = null;
        this.field_153047_e = IngestServerTesterIngestTestState.Uninitalized;

        this.awy = 8000L;

        this.aMR = 2000L;

        this.field_153050_h = 0L;
        this.field_153051_i = RTMPState.Invalid;
        this.field_153052_j = null;
        this.audioParameters = null;

        this.wc = 0L;
        this.field_153055_m = null;
        this.field_153056_n = false;
        this.aMZ = null;
        this.aMX = null;
        this.field_153059_q = null;
        this.lL = false;
        this.lc = false;
        this.field_153062_t = -1;
        this.field_153063_u = 0;

        this.field_153064_v = 0L;
        this.awp = 0.0F;
        this.w = 0.0F;
        this.nC = false;
        this.field_176008_y = false;
        this.oK = false;
        this.aNb = new IStreamCallbacks1(this);
        this.aMP = new IStatCallbacks2(this);
        this.field_153045_c = stream;
        this.field_153046_d = ingestlist;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(packedTextFragments[0]);
        }
    }

    public void cS() {
        String text = sT();
        if (!this.func_153032_e()
                && this.field_153047_e != IngestServerTesterIngestTestState.Uninitalized
                && !this.field_176008_y
                && !this.oK) {
            label51:
            {
                switch (this.field_153047_e) {
                    case Starting:
                    case DoneTestingServer:
                        if (this.field_153059_q != null) {
                            if (this.lc || !this.field_153056_n) {
                                this.field_153059_q.bitrateKbps = 0.0F;
                            }

                            this.method_e5daaeb468e1a294e962(this.field_153059_q);
                            if (text == null) {
                                break label51;
                            }
                        }

                        long i = 2869876086641835757L;
                        this.wc = 0L;
                        this.lc = false;
                        this.field_153056_n = true;
                        if (this.field_153047_e != IngestServerTesterIngestTestState.Starting) {
                            this.field_153062_t++;
                        }

                        if (this.field_153062_t < this.field_153046_d.getServers().length) {
                            this.field_153059_q =
                                    this.field_153046_d.getServers()[this.field_153062_t];
                            this.func_153036_a(this.field_153059_q);
                            if (text == null) {
                                break label51;
                            }
                        }

                        this.func_153034_a(IngestServerTesterIngestTestState.Finished);
                        if (text == null) {
                            break label51;
                        }
                    case ConnectingToServer:
                    case TestingServer:
                        this.method_a4a2cf176465d25bf56a(this.field_153059_q);
                        if (text == null) {
                            break label51;
                        }
                    case Cancelling:
                        break;
                    default:
                        break label51;
                }

                this.func_153034_a(IngestServerTesterIngestTestState.Cancelled);
            }

            this.fZ();
            if (this.field_153047_e == IngestServerTesterIngestTestState.Cancelled
                    || this.field_153047_e == IngestServerTesterIngestTestState.Finished) {
                this.ha();
            }
        }
    }

    // Helper used only by this class.
    public static class IStatCallbacks2 implements tv.twitch.broadcast.IStatCallbacks {
        net.minecraft.client.stream.IngestServerTester aNc;

        public void statCallback(tv.twitch.broadcast.StatType stattype, long aMN) {
            java.lang.String text = net.minecraft.client.stream.IngestServerTester.sT();
            switch (stattype) {
                case TTV_ST_RTMPSTATE:
                    this.aNc.field_153051_i = tv.twitch.broadcast.RTMPState.lookupValue((int) aMN);
                    if (text == null) {
                        break;
                    }
                case TTV_ST_RTMPDATASENT:
                    this.aNc.field_153050_h = aMN;
            }
        }

        public IStatCallbacks2(net.minecraft.client.stream.IngestServerTester aNcValue) {
            this.aNc = aNcValue;
        }
    }

    // Helper used only by this class.
    public static class IStreamCallbacks1 implements tv.twitch.broadcast.IStreamCallbacks {
        net.minecraft.client.stream.IngestServerTester aNc;

        public void startCallback(tv.twitch.ErrorCode errorcode) {
            java.lang.String text = net.minecraft.client.stream.IngestServerTester.sT();
            this.aNc.field_176008_y = false;
            java.lang.String textValue = text;
            if (tv.twitch.ErrorCode.succeeded(errorcode)) {
                this.aNc.nC = true;
                this.aNc.wc = java.lang.System.currentTimeMillis();
                this.aNc.func_153034_a(
                        net.minecraft.client.stream.IngestServerTesterIngestTestState
                                .ConnectingToServer);
                if (textValue == null) {
                    return;
                }
            }

            this.aNc.field_153056_n = false;
            this.aNc.func_153034_a(
                    net.minecraft.client.stream.IngestServerTesterIngestTestState
                            .DoneTestingServer);
        }

        public void getUserInfoCallback(
                tv.twitch.ErrorCode errorcode, tv.twitch.broadcast.UserInfo userinfo) {}

        public IStreamCallbacks1(net.minecraft.client.stream.IngestServerTester aNcValue) {
            this.aNc = aNcValue;
        }

        public void sendActionMetaDataCallback(tv.twitch.ErrorCode errorcode) {}


        public void sendEndSpanMetaDataCallback(tv.twitch.ErrorCode errorcode) {}

        public void requestAuthTokenCallback(
                tv.twitch.ErrorCode errorcode, tv.twitch.AuthToken authtoken) {}

        public void loginCallback(
                tv.twitch.ErrorCode errorcode, tv.twitch.broadcast.ChannelInfo channelinfo) {}

        public void getIngestServersCallback(
                tv.twitch.ErrorCode errorcode, tv.twitch.broadcast.IngestList ingestlist) {}

        public void getStreamInfoCallback(
                tv.twitch.ErrorCode errorcode, tv.twitch.broadcast.StreamInfo streaminfo) {}

        public void bufferUnlockCallback(long i) {}

        public void stopCallback(tv.twitch.ErrorCode errorcode) {
            if (tv.twitch.ErrorCode.failed(errorcode)) {
                java.lang.System.out.println(
                        "IngestTester.stopCallback failed to stop - "
                                + this.aNc.field_153059_q.serverName
                                + ": "
                                + errorcode.toString());
            }

            this.aNc.oK = false;
            this.aNc.nC = false;
            this.aNc.func_153034_a(
                    net.minecraft.client.stream.IngestServerTesterIngestTestState
                            .DoneTestingServer);
            this.aNc.field_153059_q = null;
            if (this.aNc.lL) {
                this.aNc.func_153034_a(
                        net.minecraft.client.stream.IngestServerTesterIngestTestState.Cancelling);
            }
        }

        public void runCommercialCallback(tv.twitch.ErrorCode errorcode) {}

        public void setStreamInfoCallback(tv.twitch.ErrorCode errorcode) {}

        public void getArchivingStateCallback(
                tv.twitch.ErrorCode errorcode, tv.twitch.broadcast.ArchivingState archivingstate) {}

        public void sendStartSpanMetaDataCallback(tv.twitch.ErrorCode errorcode) {}

        public void getGameNameListCallback(
                tv.twitch.ErrorCode errorcode, tv.twitch.broadcast.GameInfoList gameinfolist) {}
    }
}
