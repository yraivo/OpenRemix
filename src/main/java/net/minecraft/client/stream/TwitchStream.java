package net.minecraft.client.stream;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.mojang.authlib.properties.Property;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.stream.GuiTwitchUserMode;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEventAction;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Util;
import net.minecraft.util.UtilEnumOS;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.lwjgl.opengl.GL11;

import tv.twitch.AuthToken;
import tv.twitch.ErrorCode;
import tv.twitch.broadcast.EncodingCpuUsage;
import tv.twitch.broadcast.FrameBuffer;
import tv.twitch.broadcast.GameInfo;
import tv.twitch.broadcast.IngestList;
import tv.twitch.broadcast.IngestServer;
import tv.twitch.broadcast.StreamInfo;
import tv.twitch.broadcast.VideoParams;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatUserMode;
import tv.twitch.chat.ChatUserSubscription;

import java.util.Map;
import java.util.Set;

public class TwitchStream
        implements BroadcastControllerBroadcastListener,
                ChatControllerChatListener,
                IStream,
                IngestServerTesterIngestTestListener {
    public static Marker STREAM_MARKER;
    private Map field_152955_g;
    private boolean loggedIn;
    private long field_152959_k;
    private String field_176029_e;
    private Framebuffer framebuffer;
    private boolean mx;
    private static String[] packedTextFragments;
    private MinecraftClient mc;

    private IStreamAuthFailureReason authFailureReason;
    private ChatController chatController;

    private boolean field_152960_l;
    private int targetFPS;
    private static boolean field_152965_q;
    private BroadcastController broadcastController;
    private boolean field_152962_n;
    private final ChatComponent twitchComponent = new ChatComponentText("Twitch");
    private static Logger LOGGER;
    private boolean field_152963_o;

    public void func_152895_a() {
        net.minecraft.client.stream.TwitchStream.LOGGER.info(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER, "Logged out of twitch");
    }

    public boolean func_152908_z() {
        return this.broadcastController.isIngestTesting();
    }

    public boolean isPaused() {
        return this.broadcastController.isBroadcastPaused();
    }

    public void method_17259210e80146ea0cdb(ErrorCode errorcode) {
        String text = IngestServerTester.sT();
        if (errorcode == ErrorCode.TTV_EC_SOUNDFLOWER_NOT_INSTALLED) {
            ChatComponentTranslation chatComponentTranslation =
                    new ChatComponentTranslation(
                            "stream.unavailable.soundflower.chat.link", new Object[0]);
            chatComponentTranslation
                    .getChatStyle()
                    .setChatClickEvent(
                            new ClickEvent(
                                    ClickEventAction.OPEN_URL,
                                    "https://help.mojang.com/customer/portal/articles/1374877-configuring-soundflower-for-streaming-on-apple-computers"));
            chatComponentTranslation.getChatStyle().setUnderlined(true);
            ChatComponentTranslation chatComponentTranslationValue =
                    new ChatComponentTranslation(
                            "stream.unavailable.soundflower.chat",
                            new Object[] {chatComponentTranslation});
            chatComponentTranslationValue.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
            this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslationValue);
            if (text == null) {
                return;
            }
        }

        ChatComponentTranslation chatComponentTranslationValue2 =
                new ChatComponentTranslation(
                        "stream.unavailable.unknown.chat",
                        new Object[] {ErrorCode.getString(errorcode)});
        chatComponentTranslationValue2.getChatStyle().setColor(EnumChatFormatting.DARK_RED);
        this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslationValue2);
    }

    public void sendMetadataEvent(Metadata metadata, long i) {
        String text = IngestServerTester.sT();
        if (this.isBroadcasting() && this.mx) {
            long j = this.broadcastController.func_152844_x();
            if (!this.broadcastController.func_152840_a(
                    metadata.getName(),
                    j + i,
                    metadata.method_e0e3092a59fee58fae52(),
                    metadata.method_c60693f457aa70be7160())) {
                net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                        net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                        "Couldn't send stream metadata action at {}: {}",
                        new Object[] {j + i, metadata});
                if (text == null) {
                    return;
                }
            }

            net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                    net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                    "Sent stream metadata action at {}: {}",
                    new Object[] {j + i, metadata});
        }
    }

    public void method_05b43377ca0aa222e61a(ErrorCode errorcode) {
        String text = IngestServerTester.sT();
        if (ErrorCode.succeeded(errorcode)) {
            net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                    net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                    "Login attempt successful");
            this.loggedIn = true;
            if (text == null) {
                return;
            }
        }

        net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Login attempt unsuccessful: {} (error code {})",
                new Object[] {ErrorCode.getString(errorcode), errorcode.getValue()});
        this.loggedIn = false;
    }

    public boolean func_152936_l() {
        return this.broadcastController.func_152849_q();
    }

    public static int formatStreamFps(float f) {
        return MathHelper.floor(10.0F + f * 50.0F);
    }

    public static IStreamAuthFailureReason access$302(
            TwitchStream twitchStream, IStreamAuthFailureReason aNmValue) {
        return twitchStream.authFailureReason = aNmValue;
    }

    public boolean func_152927_B() {
        return this.field_176029_e != null
                && this.field_176029_e.equals(this.broadcastController.getChannelInfo().name);
    }

    public boolean func_152913_F() {
        return this.loggedIn;
    }

    public void muteMicrophone(boolean flag) {
        this.field_152963_o = flag;
        this.updateStreamVolume();
    }

    public void func_152907_a(
            IngestServerTester ingestServerTester,
            IngestServerTesterIngestTestState ingestServerTesterIngestTestState) {
        net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Ingest test state changed to {}",
                new Object[] {ingestServerTesterIngestTestState});
        if (ingestServerTesterIngestTestState == IngestServerTesterIngestTestState.Finished) {
            this.field_152960_l = true;
        }
    }

    static {
        net.minecraft.client.stream.TwitchStream.packedTextFragments = new String[4];
        net.minecraft.client.stream.TwitchStream.packedTextFragments[0] = new String(")");
        net.minecraft.client.stream.TwitchStream.packedTextFragments[1] = new String("");
        net.minecraft.client.stream.TwitchStream.packedTextFragments[2] = new String("\n");
        net.minecraft.client.stream.TwitchStream.LOGGER = LogManager.getLogger();
        net.minecraft.client.stream.TwitchStream.STREAM_MARKER = MarkerManager.getMarker("STREAM");
        // The obsolete Twitch native integration remains disabled in this source build.
        net.minecraft.client.stream.TwitchStream.field_152965_q = false;
    }

    public void func_152898_a(ErrorCode errorcode, GameInfo[] agameinfo) {}

    static BroadcastController access$100(TwitchStream twitchStream) {
        return twitchStream.broadcastController;
    }

    public void submitStreamFrame() {
        if (this.broadcastController.isBroadcasting()
                && !this.broadcastController.isBroadcastPaused()) {
            long avUValue = System.nanoTime();
            long j = 1000000000 / this.targetFPS;
            long k = avUValue - this.field_152959_k;
            int l = k >= j ? 1 : 0;
            if (l != 0) {
                FrameBuffer framebuffer = this.broadcastController.func_152822_N();
                Framebuffer framebufferValue = this.mc.getFramebuffer();
                this.framebuffer.bindFramebuffer(true);
                GlStateManager.matrixMode(5889);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                GlStateManager.ortho(
                        0.0,
                        this.framebuffer.framebufferWidth,
                        this.framebuffer.framebufferHeight,
                        0.0,
                        1000.0,
                        3000.0);
                GlStateManager.matrixMode(5888);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                GlStateManager.translate(0.0F, 0.0F, -2000.0F);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.viewport(
                        0,
                        0,
                        this.framebuffer.framebufferWidth,
                        this.framebuffer.framebufferHeight);
                GlStateManager.enableTexture2D();
                GlStateManager.disableAlpha();
                GlStateManager.disableBlend();
                float framebufferWidth = this.framebuffer.framebufferWidth;
                float framebufferHeight = this.framebuffer.framebufferHeight;
                float f2 =
                        (float) framebufferValue.framebufferWidth
                                / framebufferValue.framebufferTextureWidth;
                float f3 =
                        (float) framebufferValue.framebufferHeight
                                / framebufferValue.framebufferTextureHeight;
                framebufferValue.bindFramebufferTexture();
                GL11.glTexParameterf(3553, 10241, 9729.0F);
                GL11.glTexParameterf(3553, 10240, 9729.0F);
                Tessellator instance = Tessellator.getInstance();
                WorldRenderer worldRenderer = instance.getWorldRenderer();
                worldRenderer.startDrawingQuads();
                worldRenderer.addVertexWithUV(0.0, framebufferHeight, 0.0, 0.0, f3);
                worldRenderer.addVertexWithUV(framebufferWidth, framebufferHeight, 0.0, f2, f3);
                worldRenderer.addVertexWithUV(framebufferWidth, 0.0, 0.0, f2, 0.0);
                worldRenderer.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
                instance.draw();
                framebufferValue.unbindFramebufferTexture();
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5889);
                GlStateManager.popMatrix();
                GlStateManager.matrixMode(5888);
                this.broadcastController.captureFramebuffer(framebuffer);
                this.framebuffer.unbindFramebuffer();
                this.broadcastController.submitStreamFrame(framebuffer);
                this.field_152959_k = avUValue;
            }
        }
    }

    public void func_152909_x() {
        IngestServerTester ingestServerTester = this.broadcastController.func_152838_J();
        if (ingestServerTester != null) {
            ingestServerTester.func_153042_a(this);
        }
    }

    public int func_152920_A() {
        return this.isBroadcasting() ? this.broadcastController.getStreamInfo().viewers : 0;
    }

    private boolean method_38a0e776855e6c4e7d97(Set set, Set set1, int i) {
        return set.contains(ChatUserMode.TTV_CHAT_USERMODE_BANNED)
                ? false
                : (set.contains(ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR)
                        ? true
                        : (set.contains(ChatUserMode.TTV_CHAT_USERMODE_MODERATOR)
                                ? true
                                : (set.contains(ChatUserMode.TTV_CHAT_USERMODE_STAFF)
                                        ? true
                                        : (i == 0
                                                ? true
                                                : (i == 1
                                                        ? set1.contains(
                                                                ChatUserSubscription
                                                                        .TTV_CHAT_USERSUB_SUBSCRIBER)
                                                        : false)))));
    }

    public void method_75df6c73644e20229bce(String text) {}

    public void func_152896_a(IngestList ingestlist) {}

    public void func_152900_a(ErrorCode errorcode, AuthToken authtoken) {}

    public void updateStreaming() {
        label66:
        {
            String text = IngestServerTester.sT();
            int i = this.mc.gameSettings.streamChatEnabled;
            String textValue = text;
            int j =
                    this.field_176029_e != null
                                    && this.chatController.func_175990_d(this.field_176029_e)
                            ? 1
                            : 0;
            int k =
                    this.chatController.func_153000_j() != ChatControllerChatState.Initialized
                                    || this.field_176029_e != null
                                            && this.chatController.func_175989_e(
                                                            this.field_176029_e)
                                                    != ChatControllerEnumChannelState.Disconnected
                            ? 0
                            : 1;
            if (i == 2) {
                if (j == 0) {
                    break label66;
                }

                net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                        net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                        "Disconnecting from twitch chat per user options");
                this.chatController.func_175991_l(this.field_176029_e);
                if (textValue == null) {
                    break label66;
                }
            }

            if (i == 1) {
                if (k == 0 || !this.broadcastController.func_152849_q()) {
                    break label66;
                }

                net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                        net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                        "Connecting to twitch chat per user options");
                this.fZ();
                if (textValue == null) {
                    break label66;
                }
            }

            if (i == 0) {
                if (j != 0 && !this.isBroadcasting()) {
                    net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                            net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                            "Disconnecting from twitch chat as user is no longer streaming");
                    this.chatController.func_175991_l(this.field_176029_e);
                    if (!(textValue == null)) {

                        if (k != 0 && this.isBroadcasting()) {
                            net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                                    net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                                    "Connecting to twitch chat as user is streaming");
                            this.fZ();
                        }
                    }
                } else {

                    if (k != 0 && this.isBroadcasting()) {
                        net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                                "Connecting to twitch chat as user is streaming");
                        this.fZ();
                    }
                }
            }
        }

        this.broadcastController.nq();
        this.chatController.cQ();
    }

    public boolean isBroadcasting() {
        return this.broadcastController.isBroadcasting();
    }

    static ChatController access$200(TwitchStream twitchStream) {
        return twitchStream.chatController;
    }

    public void method_b3805c57e9d8e69c1958(ErrorCode errorcode) {
        net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Issue submitting frame: {} (Error code {})",
                new Object[] {ErrorCode.getString(errorcode), errorcode.getValue()});
        this.mc
                .ingameGUI
                .getChatGUI()
                .printChatMessageWithOptionalDeletion(
                        new ChatComponentText(
                                "Issue streaming frame: "
                                        + errorcode
                                        + " ("
                                        + ErrorCode.getString(errorcode)
                                        + packedTextFragments[0]),
                        2);
    }

    public void updateStreamVolume() {
        if (this.isBroadcasting()) {
            float axF = this.mc.gameSettings.streamGameVolume;
            int i = !this.field_152962_n && !(axF <= 0.0F) ? 0 : 1;
            this.broadcastController.setPlaybackDeviceVolume(i != 0 ? 0.0F : axF);
            this.broadcastController.setRecordingDeviceVolume(
                    this.func_152929_G() ? 0.0F : this.mc.gameSettings.streamMicVolume);
        }
    }

    public ChatUserInfo func_152926_a(String text) {
        return (ChatUserInfo) this.field_152955_g.get(text);
    }

    public void func_176019_a(String text, String textValue) {}

    public void func_152891_a(BroadcastControllerBroadcastState broadcastControllerBroadcastState) {
        net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Broadcast state changed to {}",
                new Object[] {broadcastControllerBroadcastState});
        if (broadcastControllerBroadcastState == BroadcastControllerBroadcastState.Initialized) {
            this.broadcastController.func_152827_a(BroadcastControllerBroadcastState.Authenticated);
        }
    }

    protected void fZ() {
        String text = IngestServerTester.sT();
        ChatControllerChatState chatControllerChatState = this.chatController.func_153000_j();
        String textValue = text;
        String name = this.broadcastController.getChannelInfo().name;
        this.field_176029_e = name;
        if (chatControllerChatState != ChatControllerChatState.Initialized) {
            net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                    "Invalid twitch chat state {}", new Object[] {chatControllerChatState});
            if (textValue == null) {
                return;
            }
        }

        if (this.chatController.func_175989_e(this.field_176029_e)
                == ChatControllerEnumChannelState.Disconnected) {
            this.chatController.method_792a04a06ccab826b74c(name);
            if (textValue == null) {
                return;
            }
        }

        net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                "Invalid twitch chat state {}", new Object[] {chatControllerChatState});
    }

    public String func_152921_C() {
        return this.field_176029_e;
    }

    public void func_176018_a(
            String text,
            ChatUserInfo[] achatuserinfo,
            ChatUserInfo[] achatuserinfo1,
            ChatUserInfo[] achatuserinfo2) {
        String textValue = IngestServerTester.sT();
        ChatUserInfo[] achatuserinfo3 = achatuserinfo1;
        String textValue2 = textValue;
        int length = achatuserinfo1.length;
        int j = 0;

        while (j < length) {
            ChatUserInfo chatuserinfo = achatuserinfo3[j];
            this.field_152955_g.remove(chatuserinfo.displayName);
            j++;
            if (textValue2 != null) {
                break;
            }
        }

        achatuserinfo3 = achatuserinfo2;
        length = achatuserinfo2.length;
        j = 0;

        while (j < length) {
            ChatUserInfo chatuserinfo1 = achatuserinfo3[j];
            this.field_152955_g.put(chatuserinfo1.displayName, chatuserinfo1);
            j++;
            if (textValue2 != null) {
                break;
            }
        }

        achatuserinfo3 = achatuserinfo;
        length = achatuserinfo.length;
        j = 0;

        while (j < length) {
            ChatUserInfo chatuserinfo2 = achatuserinfo3[j];
            this.field_152955_g.put(chatuserinfo2.displayName, chatuserinfo2);
            j++;
            if (textValue2 != null) {
                break;
            }
        }
    }

    public void cQ() {}

    public void shutdownStream() {
        net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER, "Shutdown streaming");
        this.broadcastController.statCallback();
        this.chatController.func_175988_p();
    }

    public void func_152894_a(StreamInfo streaminfo) {
        net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Stream info updated; {} viewers on stream ID {}",
                new Object[] {streaminfo.viewers, streaminfo.streamId});
    }

    public void func_152901_c() {
        net.minecraft.client.stream.TwitchStream.LOGGER.info(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Broadcast to Twitch has stopped");
    }

    public void func_180607_b(String text) {
        net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER, "Chat disconnected");
        this.field_152955_g.clear();
    }

    public TwitchStream(MinecraftClient apcValue, Property property) {
        this.field_152955_g = Maps.newHashMap();
        this.targetFPS = 30;

        this.field_152959_k = 0L;
        this.field_152960_l = false;
        this.authFailureReason = IStreamAuthFailureReason.ERROR;
        this.mc = apcValue;
        this.broadcastController = new BroadcastController();
        this.chatController = new ChatController();
        this.broadcastController.setBroadcastListener(this);
        this.chatController.func_152990_a(this);
        this.broadcastController.method_be01d1eb5e587c583ed6("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.chatController.method_be01d1eb5e587c583ed6("nmt37qblda36pvonovdkbopzfzw3wlq");
        this.twitchComponent.getChatStyle().setColor(EnumChatFormatting.DARK_PURPLE);
        if (property != null
                && !Strings.isNullOrEmpty(property.getValue())
                && OpenGlHelper.framebufferSupported) {
            TwitchStreamThread1 twitchStreamThread1 =
                    new TwitchStreamThread1(this, "Twitch authenticator", property);
            twitchStreamThread1.setDaemon(true);
            twitchStreamThread1.start();
        }
    }

    public void method_30d7470cbf2bd0219255(String text) {}

    static Logger access$000() {
        return net.minecraft.client.stream.TwitchStream.LOGGER;
    }

    public void ns() {
        String text = IngestServerTester.sT();
        if (this.broadcastController.stopBroadcasting()) {
            net.minecraft.client.stream.TwitchStream.LOGGER.info(
                    net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                    "Stopped streaming to Twitch");
            if (text == null) {
                return;
            }
        }

        net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Could not stop streaming to Twitch");
    }

    private void func_176027_a(String displayName, ChatRawMessage chatrawmessage) {
        ChatUserInfo chatuserinfo = (ChatUserInfo) this.field_152955_g.get(displayName);
        if (chatuserinfo == null) {
            chatuserinfo = new ChatUserInfo();
            chatuserinfo.displayName = displayName;
            this.field_152955_g.put(displayName, chatuserinfo);
        }

        chatuserinfo.subscriptions = chatrawmessage.subscriptions;
        chatuserinfo.modes = chatrawmessage.modes;
        chatuserinfo.nameColorARGB = chatrawmessage.nameColorARGB;
    }

    public void iJ() {
        String text = IngestServerTester.sT();
        if (this.broadcastController.kb()) {
            net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                    net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                    "Requested commercial from Twitch");
            if (text == null) {
                return;
            }
        }

        net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Could not request commercial from Twitch");
    }

    public IStreamAuthFailureReason func_152918_H() {
        return this.authFailureReason;
    }

    public boolean isReadyToBroadcast() {
        return this.broadcastController.isReadyToBroadcast();
    }

    public void func_152899_b() {
        this.updateStreamVolume();
        net.minecraft.client.stream.TwitchStream.LOGGER.info(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Broadcast to Twitch has started");
    }

    public IngestServerTester func_152932_y() {
        return this.broadcastController.isReady();
    }

    public ErrorCode func_152912_E() {
        return !net.minecraft.client.stream.TwitchStream.field_152965_q
                ? ErrorCode.TTV_EC_OS_TOO_OLD
                : this.broadcastController.getErrorCode();
    }

    public void l() {}

    public void func_176023_d(ErrorCode errorcode) {
        if (ErrorCode.failed(errorcode)) {
            net.minecraft.client.stream.TwitchStream.LOGGER.error(
                    net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                    "Chat failed to initialize");
        }
    }

    public void func_176022_e(ErrorCode errorcode) {
        if (ErrorCode.failed(errorcode)) {
            net.minecraft.client.stream.TwitchStream.LOGGER.error(
                    net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                    "Chat failed to shutdown");
        }
    }

    public boolean func_152928_D() {
        return net.minecraft.client.stream.TwitchStream.field_152965_q
                && this.broadcastController.func_152858_b();
    }

    public boolean func_152929_G() {
        int i = this.mc.gameSettings.streamMicToggleBehavior == 1 ? 1 : 0;
        return this.field_152962_n
                || this.mc.gameSettings.streamMicVolume <= 0.0F
                || i != remix.compat.compiler.JvmBooleanSlots.toInt(this.field_152963_o);
    }

    public static float formatStreamBps(float f) {
        return 0.1F + f * 0.1F;
    }

    public IngestServer[] func_152925_v() {
        return this.broadcastController.getIngestList().getServers();
    }

    public void func_152917_b(String text) {
        this.chatController.func_175986_a(this.field_176029_e, text);
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public void sendMetadataSpan(Metadata metadata, long i, long j) {
        String text = IngestServerTester.sT();
        if (this.isBroadcasting() && this.mx) {
            long k = this.broadcastController.func_152844_x();
            String textValue = metadata.method_e0e3092a59fee58fae52();
            String textValue2 = metadata.method_c60693f457aa70be7160();
            long l =
                    this.broadcastController.func_177946_b(
                            metadata.getName(), k + i, textValue, textValue2);

            if (l < 0L) {
                net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                        net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                        "Could not send stream metadata sequence from {} to {}: {}",
                        new Object[] {k + i, k + j, metadata});
                if (text == null) {
                    return;
                }
            }

            if (this.broadcastController.func_177947_a(
                    metadata.getName(), k + j, l, textValue, textValue2)) {
                net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                        net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                        "Sent stream metadata sequence from {} to {}: {}",
                        new Object[] {k + i, k + j, metadata});
                if (text == null) {
                    return;
                }
            }

            net.minecraft.client.stream.TwitchStream.LOGGER.warn(
                    net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                    "Half-sent stream metadata sequence from {} to {}: {}",
                    new Object[] {k + i, k + j, metadata});
        }
    }

    public void func_180606_a(String text) {
        net.minecraft.client.stream.TwitchStream.LOGGER.debug(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER, "Chat connected");
    }

    public void nG() {
        String text = IngestServerTester.sT();
        GameSettings gameSettings = this.mc.gameSettings;
        String textValue = text;
        VideoParams videoparams =
                this.broadcastController.func_152834_a(
                        formatStreamKbps(gameSettings.streamKbps),
                        formatStreamFps(gameSettings.streamFps),
                        formatStreamBps(gameSettings.streamBytesPerPixel),
                        (float) this.mc.displayWidth / this.mc.displayHeight);
        switch (gameSettings.streamCompression) {
            case 0:
                videoparams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_LOW;
                if (textValue == null) {
                    break;
                }
            case 1:
                videoparams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_MEDIUM;
                if (textValue == null) {
                    break;
                }
            case 2:
                videoparams.encodingCpuUsage = EncodingCpuUsage.TTV_ECU_HIGH;
        }

        {
            if (this.framebuffer == null) {
                this.framebuffer =
                        new Framebuffer(videoparams.outputWidth, videoparams.outputHeight, false);
                if (!(textValue == null)) {

                    this.framebuffer.createBindFramebuffer(
                            videoparams.outputWidth, videoparams.outputHeight);
                }
            } else {

                this.framebuffer.createBindFramebuffer(
                        videoparams.outputWidth, videoparams.outputHeight);
            }
        }

        if (gameSettings.streamPreferredServer != null
                && gameSettings.streamPreferredServer.length() > 0) {
            for (IngestServer ingestserver : this.func_152925_v()) {
                if (ingestserver.serverUrl.equals(gameSettings.streamPreferredServer)) {
                    this.broadcastController.func_152824_a(ingestserver);
                    if (textValue == null) {
                        break;
                    }
                }

                if (textValue != null) {
                    break;
                }
            }
        }

        this.targetFPS = videoparams.targetFps;
        this.mx = gameSettings.streamSendMetadata;
        this.broadcastController.method_fc7bda2aa8546e15d1ac(videoparams);
        net.minecraft.client.stream.TwitchStream.LOGGER.info(
                net.minecraft.client.stream.TwitchStream.STREAM_MARKER,
                "Streaming at {}/{} at {} kbps to {}",
                new Object[] {
                    videoparams.outputWidth,
                    videoparams.outputHeight,
                    videoparams.maxKbps,
                    this.broadcastController.func_152833_s().serverUrl
                });
        this.broadcastController.method_cccb8bbcc1bebe0a2111(
                (String) null, "Minecraft", (String) null);
    }

    public void func_180605_a(String text, ChatRawMessage[] achatrawmessage) {
        String textValue = IngestServerTester.sT();
        ChatRawMessage[] achatrawmessage1 = achatrawmessage;
        String textValue2 = textValue;
        int length = achatrawmessage.length;
        int j = 0;

        while (j < length) {
            ChatRawMessage chatrawmessage = achatrawmessage1[j];
            this.func_176027_a(chatrawmessage.userName, chatrawmessage);
            if (this.method_38a0e776855e6c4e7d97(
                    chatrawmessage.modes,
                    chatrawmessage.subscriptions,
                    this.mc.gameSettings.streamChatUserFilter)) {
                ChatComponentText chatComponentText =
                        new ChatComponentText(chatrawmessage.userName);
                ChatComponentTranslation chatComponentTranslation =
                        new ChatComponentTranslation(
                                "chat.stream." + (chatrawmessage.action ? "emote" : "text"),
                                new Object[] {
                                    this.twitchComponent,
                                    chatComponentText,
                                    EnumChatFormatting.getTextWithoutFormattingCodes(
                                            chatrawmessage.message)
                                });
                if (chatrawmessage.action) {
                    chatComponentTranslation.getChatStyle().setItalic(true);
                }

                ChatComponentText chatComponentTextValue =
                        new ChatComponentText(packedTextFragments[1]);
                chatComponentTextValue.appendSibling(
                        new ChatComponentTranslation("stream.userinfo.chatTooltip", new Object[0]));

                for (java.util.Iterator<?> __recoveryIterator171_63292 =
                                (GuiTwitchUserMode.method_cd08be8d4500ca3eb3bc(
                                                chatrawmessage.modes,
                                                chatrawmessage.subscriptions,
                                                (IStream) null))
                                        .iterator();
                        (__recoveryIterator171_63292).hasNext(); ) {
                    ChatComponent chatComponent =
                            (ChatComponent) (__recoveryIterator171_63292).next();
                    chatComponentTextValue.appendText(packedTextFragments[2]);
                    chatComponentTextValue.appendSibling(chatComponent);
                    if (textValue2 != null) {
                        break;
                    }
                }

                chatComponentText
                        .getChatStyle()
                        .setChatHoverEvent(
                                new HoverEvent(HoverEventAction.SHOW_TEXT, chatComponentTextValue));
                chatComponentText
                        .getChatStyle()
                        .setChatClickEvent(
                                new ClickEvent(
                                        ClickEventAction.TWITCH_USER_INFO,
                                        chatrawmessage.userName));
                this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentTranslation);
            }

            j++;
            if (textValue2 != null) {
                break;
            }
        }
    }

    public void func_176017_a(ChatControllerChatState chatControllerChatState) {}

    public void pause() {
        this.broadcastController.func_152847_F();
        this.field_152962_n = true;
        this.updateStreamVolume();
    }

    public void unpause() {
        this.broadcastController.func_152854_G();
        this.field_152962_n = false;
        this.updateStreamVolume();
    }

    public static int formatStreamKbps(float f) {
        return MathHelper.floor(230.0F + f * 3270.0F);
    }

    public void func_176025_a(String text, ChatTokenizedMessage[] achattokenizedmessage) {}
}
