package net.minecraft.client.stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tv.twitch.AuthToken;
import tv.twitch.Core;
import tv.twitch.ErrorCode;
import tv.twitch.StandardCoreAPI;
import tv.twitch.chat.Chat;
import tv.twitch.chat.ChatEmoticonData;
import tv.twitch.chat.ChatTokenizationOption;
import tv.twitch.chat.IChatAPIListener;
import tv.twitch.chat.StandardChatAPI;

import java.util.HashMap;
import java.util.HashSet;

public class ChatController {
    public Chat field_153008_f;
    public ChatControllerChatListener field_153003_a = null;
    protected ChatEmoticonData field_175996_m;
    private static String[] packedTextFragments;
    protected AuthToken field_153012_j;
    protected String d;
    protected String c;
    protected ChatControllerEnumEmoticonMode aMz;
    protected ChatControllerEnumEmoticonMode aMA;
    public int rz;
    public Core field_175992_e;
    protected IChatAPIListener field_175999_p;
    protected HashMap field_175998_i;
    protected String e;
    protected int mF;

    private static Logger LOGGER;
    public int jM;
    protected ChatControllerChatState field_153011_i;

    public void dn() {
        String text = IngestServerTester.sT();
        if (this.field_175996_m == null) {
            this.field_175996_m = new ChatEmoticonData();
            ErrorCode errorcode = this.field_153008_f.getEmoticonData(this.field_175996_m);
            if (ErrorCode.succeeded(errorcode)) {
                try {
                    if (this.field_153003_a != null) {
                        this.field_153003_a.l();
                    }

                    return;
                } catch (Exception exception) {
                    this.func_152995_h(exception.toString());
                    if (text == null) {
                        return;
                    }
                }
            }

            this.func_152995_h("Error preparing emoticon data: " + ErrorCode.getString(errorcode));
        }
    }

    public ChatControllerEnumChannelState func_175989_e(String text) {
        if (!this.field_175998_i.containsKey(text)) {
            return ChatControllerEnumChannelState.Disconnected;
        }

        ChatControllerChatChannelListener chatControllerChatChannelListener =
                (ChatControllerChatChannelListener) this.field_175998_i.get(text);
        return chatControllerChatChannelListener.func_176040_a();
    }

    protected void func_152996_t() {
        String text = IngestServerTester.sT();
        if (this.field_175996_m != null) {
            ErrorCode errorcode = this.field_153008_f.clearEmoticonData();
            if (ErrorCode.succeeded(errorcode)) {
                this.field_175996_m = null;

                try {
                    if (this.field_153003_a != null) {
                        this.field_153003_a.cQ();
                    }

                    return;
                } catch (Exception exception) {
                    this.func_152995_h(exception.toString());
                    if (text == null) {
                        return;
                    }
                }
            }

            this.func_152995_h("Error clearing emoticon data: " + ErrorCode.getString(errorcode));
        }
    }

    public void func_175985_a(ChatControllerChatState aMEValue) {
        if (aMEValue != this.field_153011_i) {
            this.field_153011_i = aMEValue;

            try {
                if (this.field_153003_a != null) {
                    this.field_153003_a.func_176017_a(aMEValue);
                }
            } catch (Exception exception) {
                this.func_152995_h(exception.toString());
            }
        }
    }

    public void cS() {
        if (this.aMz != ChatControllerEnumEmoticonMode.None && this.field_175996_m == null) {
            ErrorCode errorcode = this.field_153008_f.downloadEmoticonData();
            if (ErrorCode.failed(errorcode)) {
                String string = ErrorCode.getString(errorcode);
                this.func_152995_h(
                        String.format("Error trying to download emoticon data: %s", string));
            }
        }
    }

    public boolean method_b417d7500ec59d69e373() {
        String text = IngestServerTester.sT();
        if (this.field_153011_i != ChatControllerChatState.Uninitialized) {
            return false;
        }

        this.func_175985_a(ChatControllerChatState.Initializing);
        ErrorCode errorcode = this.field_175992_e.initialize(this.d, (String) null);
        if (ErrorCode.failed(errorcode)) {
            this.func_175985_a(ChatControllerChatState.Uninitialized);
            String string = ErrorCode.getString(errorcode);
            this.func_152995_h(String.format("Error initializing Twitch sdk: %s", string));
            return false;
        }

        this.aMz = this.aMA;
        HashSet hashset = new HashSet();
        switch (this.aMA) {
            case None:
                hashset.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_NONE);
                if (text == null) {
                    break;
                }
            case Url:
                hashset.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_URLS);
                if (text == null) {
                    break;
                }
            case TextureAtlas:
                hashset.add(ChatTokenizationOption.TTV_CHAT_TOKENIZATION_OPTION_EMOTICON_TEXTURES);
        }

        errorcode = this.field_153008_f.initialize(hashset, this.field_175999_p);
        if (ErrorCode.failed(errorcode)) {
            this.field_175992_e.shutdown();
            this.func_175985_a(ChatControllerChatState.Uninitialized);
            String stringValue = ErrorCode.getString(errorcode);
            this.func_152995_h(String.format("Error initializing Twitch chat: %s", stringValue));
            return false;
        } else {
            this.func_175985_a(ChatControllerChatState.Initialized);
            return true;
        }
    }

    public void func_152990_a(ChatControllerChatListener aMvValue) {
        this.field_153003_a = aMvValue;
    }

    static {
        net.minecraft.client.stream.ChatController.packedTextFragments = new String[6];
        net.minecraft.client.stream.ChatController.packedTextFragments[0] = new String("");
        net.minecraft.client.stream.ChatController.packedTextFragments[1] = new String("");
        net.minecraft.client.stream.ChatController.packedTextFragments[2] = new String("");
        net.minecraft.client.stream.ChatController.packedTextFragments[3] = new String("");
        net.minecraft.client.stream.ChatController.LOGGER = LogManager.getLogger();
    }

    public void method_be01d1eb5e587c583ed6(String text) {
        this.d = text;
    }

    public ChatController() {
        this.c = packedTextFragments[0];
        this.d = packedTextFragments[1];
        this.e = packedTextFragments[2];
        this.field_175992_e = null;
        this.field_153008_f = null;
        this.field_153011_i = ChatControllerChatState.Uninitialized;
        this.field_153012_j = new AuthToken();
        this.field_175998_i = new HashMap();
        this.mF = 128;
        this.aMA = ChatControllerEnumEmoticonMode.None;
        this.aMz = ChatControllerEnumEmoticonMode.None;
        this.field_175996_m = null;
        this.jM = 500;
        this.rz = 2000;
        this.field_175999_p = new TwitchChatApiListener(this);
        this.field_175992_e = Core.getInstance();
        if (this.field_175992_e == null) {
            this.field_175992_e = new Core(new StandardCoreAPI());
        }

        this.field_153008_f = new Chat(new StandardChatAPI());
    }

    public boolean func_175990_d(String text) {
        if (!this.field_175998_i.containsKey(text)) {
            return false;
        }

        ChatControllerChatChannelListener chatControllerChatChannelListener =
                (ChatControllerChatChannelListener) this.field_175998_i.get(text);
        return chatControllerChatChannelListener.func_176040_a()
                == ChatControllerEnumChannelState.Connected;
    }

    public void cQ() {
        if (this.field_153011_i != ChatControllerChatState.Uninitialized) {
            ErrorCode errorcode = this.field_153008_f.flushEvents();
            if (ErrorCode.failed(errorcode)) {
                String string = ErrorCode.getString(errorcode);
                this.func_152995_h(String.format("Error flushing chat events: %s", string));
            }
        }
    }

    public void func_152995_h(String text) {
        net.minecraft.client.stream.ChatController.LOGGER.error(
                TwitchStream.STREAM_MARKER, "[Chat controller] {}", new Object[] {text});
    }

    public void func_175988_p() {
        String text = IngestServerTester.sT();
        if (this.func_153000_j() != ChatControllerChatState.Uninitialized) {
            this.func_152993_m();
            if (this.func_153000_j() == ChatControllerChatState.ShuttingDown) {
                while (this.func_153000_j() != ChatControllerChatState.Uninitialized) {
                    try {

                        Thread.sleep(200L);
                        this.cQ();
                    } catch (InterruptedException interruptedexception) {
                        if (text != null) {
                            break;
                        }
                    }
                }
            }
        }
    }

    public void func_152994_a(AuthToken authtoken) {
        this.field_153012_j = authtoken;
    }

    public ChatControllerChatState func_153000_j() {
        return this.field_153011_i;
    }

    public boolean func_152993_m() {
        if (this.field_153011_i != ChatControllerChatState.Initialized) {
            return false;
        } else {
            ErrorCode errorcode = this.field_153008_f.shutdown();
            if (ErrorCode.failed(errorcode)) {
                String string = ErrorCode.getString(errorcode);
                this.func_152995_h(String.format("Error shutting down chat: %s", string));
                return false;
            } else {
                this.func_152996_t();
                this.func_175985_a(ChatControllerChatState.ShuttingDown);
                return true;
            }
        }
    }

    protected boolean method_9edacf2d2f94213c26f8(String text, boolean flag) {
        if (this.field_153011_i != ChatControllerChatState.Initialized) {
            return false;
        }

        if (this.field_175998_i.containsKey(text)) {
            this.func_152995_h("Already in channel: " + text);
            return false;
        }

        if (text != null && !text.equals(packedTextFragments[3])) {
            ChatControllerChatChannelListener chatControllerChatChannelListener =
                    new ChatControllerChatChannelListener(this, text);
            this.field_175998_i.put(text, chatControllerChatChannelListener);
            boolean flag1 = chatControllerChatChannelListener.method_fca30d7091edc9ba6442(flag);
            if (!flag1) {
                this.field_175998_i.remove(text);
            }

            return flag1;
        } else {
            return false;
        }
    }

    public boolean method_792a04a06ccab826b74c(String text) {
        return this.method_9edacf2d2f94213c26f8(text, false);
    }

    public boolean func_175986_a(String text, String textValue) {
        if (this.field_153011_i != ChatControllerChatState.Initialized) {
            return false;
        } else if (!this.field_175998_i.containsKey(text)) {
            this.func_152995_h("Not in channel: " + text);
            return false;
        } else {
            ChatControllerChatChannelListener chatControllerChatChannelListener =
                    (ChatControllerChatChannelListener) this.field_175998_i.get(text);
            return chatControllerChatChannelListener.func_176037_b(textValue);
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public boolean func_175991_l(String text) {
        if (this.field_153011_i != ChatControllerChatState.Initialized) {
            return false;
        } else if (!this.field_175998_i.containsKey(text)) {
            this.func_152995_h("Not in channel: " + text);
            return false;
        } else {
            ChatControllerChatChannelListener chatControllerChatChannelListener =
                    (ChatControllerChatChannelListener) this.field_175998_i.get(text);
            return chatControllerChatChannelListener.func_176034_g();
        }
    }

    public void b(String text) {
        this.c = text;
    }
}
