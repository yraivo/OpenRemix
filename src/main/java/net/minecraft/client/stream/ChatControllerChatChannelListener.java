package net.minecraft.client.stream;

import com.google.common.collect.Lists;

import tv.twitch.ErrorCode;
import tv.twitch.chat.ChatBadgeData;
import tv.twitch.chat.ChatChannelInfo;
import tv.twitch.chat.ChatEvent;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.IChatChannelListener;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class ChatControllerChatChannelListener implements IChatChannelListener {

    protected List field_176044_d;
    protected ChatControllerEnumChannelState field_176047_c;
    protected boolean field_176046_b;
    protected LinkedList aMJ;
    protected String field_176048_a;
    protected LinkedList aMK;

    ChatController field_176049_h;
    protected ChatBadgeData field_176043_g;

    public ChatControllerEnumChannelState func_176040_a() {
        return this.field_176047_c;
    }

    protected void func_176031_c(String text) {
        try {
            if (this.field_176049_h.field_153003_a != null) {
                this.field_176049_h.field_153003_a.func_180606_a(text);
            }
        } catch (Exception exception) {
            this.field_176049_h.func_152995_h(exception.toString());
        }
    }

    public void chatStatusCallback(String text, ErrorCode errorcode) {
        if (!ErrorCode.succeeded(errorcode)) {
            this.field_176049_h.field_175998_i.remove(text);
            this.func_176030_k();
        }
    }

    private void func_176030_k() {
        if (this.field_176047_c != ChatControllerEnumChannelState.Disconnected) {
            this.func_176035_a(ChatControllerEnumChannelState.Disconnected);
            this.func_176036_d(this.field_176048_a);
            this.func_176033_j();
        }
    }

    public boolean func_176037_b(String text) {
        if (this.field_176047_c != ChatControllerEnumChannelState.Connected) {
            return false;
        } else {
            ErrorCode errorcode =
                    this.field_176049_h.field_153008_f.sendMessage(this.field_176048_a, text);
            if (ErrorCode.failed(errorcode)) {
                String string = ErrorCode.getString(errorcode);
                this.field_176049_h.func_152995_h(
                        String.format("Error sending chat message: %s", string));
                return false;
            } else {
                return true;
            }
        }
    }

    protected void func_176035_a(ChatControllerEnumChannelState aMIValue) {
        if (aMIValue != this.field_176047_c) {
            this.field_176047_c = aMIValue;
        }
    }

    public void chatChannelRawMessageCallback(String text, ChatRawMessage[] achatrawmessage) {
        String textValue = IngestServerTester.sT();
        int i = 0;
        String textValue2 = textValue;

        while (i < achatrawmessage.length) {
            this.aMK.addLast(achatrawmessage[i]);
            i++;
            if (textValue2 != null) {
                break;
            }
        }

        try {
            if (this.field_176049_h.field_153003_a != null) {
                this.field_176049_h.field_153003_a.func_180605_a(
                        this.field_176048_a, achatrawmessage);
            }
        } catch (Exception exception) {
            this.field_176049_h.func_152995_h(exception.toString());
        }

        while (this.aMK.size() > this.field_176049_h.mF) {
            this.aMK.removeFirst();
            if (textValue2 != null) {
                break;
            }
        }
    }

    protected void func_176036_d(String text) {
        try {
            if (this.field_176049_h.field_153003_a != null) {
                this.field_176049_h.field_153003_a.func_180607_b(text);
            }
        } catch (Exception exception) {
            this.field_176049_h.func_152995_h(exception.toString());
        }
    }


    protected void l() {
        String text = IngestServerTester.sT();
        if (this.field_176043_g == null) {
            this.field_176043_g = new ChatBadgeData();
            ErrorCode errorcode =
                    this.field_176049_h.field_153008_f.getBadgeData(
                            this.field_176048_a, this.field_176043_g);
            if (ErrorCode.succeeded(errorcode)) {
                try {
                    if (this.field_176049_h.field_153003_a != null) {
                        this.field_176049_h.field_153003_a.method_30d7470cbf2bd0219255(
                                this.field_176048_a);
                    }

                    return;
                } catch (Exception exception) {
                    this.field_176049_h.func_152995_h(exception.toString());
                    if (text == null) {
                        return;
                    }
                }
            }

            this.field_176049_h.func_152995_h(
                    "Error preparing badge data: " + ErrorCode.getString(errorcode));
        }
    }

    protected void func_176033_j() {
        String text = IngestServerTester.sT();
        if (this.field_176043_g != null) {
            ErrorCode errorcode =
                    this.field_176049_h.field_153008_f.clearBadgeData(this.field_176048_a);
            if (ErrorCode.succeeded(errorcode)) {
                this.field_176043_g = null;

                try {
                    if (this.field_176049_h.field_153003_a != null) {
                        this.field_176049_h.field_153003_a.method_75df6c73644e20229bce(
                                this.field_176048_a);
                    }

                    return;
                } catch (Exception exception) {
                    this.field_176049_h.func_152995_h(exception.toString());
                    if (text == null) {
                        return;
                    }
                }
            }

            this.field_176049_h.func_152995_h(
                    "Error releasing badge data: " + ErrorCode.getString(errorcode));
        }
    }

    public boolean method_fca30d7091edc9ba6442(boolean flag) {
        ErrorCode errorcode;
        {
            String text = IngestServerTester.sT();
            this.field_176046_b = flag;
            String textValue = text;
            errorcode = ErrorCode.TTV_EC_SUCCESS;
            if (flag) {
                errorcode =
                        this.field_176049_h.field_153008_f.connectAnonymous(
                                this.field_176048_a, this);
                if (!(textValue == null)) {

                    errorcode =
                            this.field_176049_h.field_153008_f.connect(
                                    this.field_176048_a,
                                    this.field_176049_h.c,
                                    this.field_176049_h.field_153012_j.data,
                                    this);
                }
            } else {

                errorcode =
                        this.field_176049_h.field_153008_f.connect(
                                this.field_176048_a,
                                this.field_176049_h.c,
                                this.field_176049_h.field_153012_j.data,
                                this);
            }
        }

        if (ErrorCode.failed(errorcode)) {
            String string = ErrorCode.getString(errorcode);
            this.field_176049_h.func_152995_h(String.format("Error connecting: %s", string));
            this.func_176036_d(this.field_176048_a);
            return false;
        } else {
            this.func_176035_a(ChatControllerEnumChannelState.Connecting);
            this.cj();
            return true;
        }
    }

    public boolean func_176034_g() {
        switch (this.field_176047_c) {
            case Connected:
            case Connecting:
                ErrorCode errorcode =
                        this.field_176049_h.field_153008_f.disconnect(this.field_176048_a);
                if (ErrorCode.failed(errorcode)) {
                    String string = ErrorCode.getString(errorcode);
                    this.field_176049_h.func_152995_h(
                            String.format("Error disconnecting: %s", string));
                    return false;
                }

                this.func_176035_a(ChatControllerEnumChannelState.Disconnecting);
                return true;
            case Created:
            case Disconnected:
            case Disconnecting:
            default:
                return false;
        }
    }

    public void chatChannelUserChangeCallback(
            String text,
            ChatUserInfo[] achatuserinfo,
            ChatUserInfo[] achatuserinfo1,
            ChatUserInfo[] achatuserinfo2) {
        String textValue = IngestServerTester.sT();
        int i = 0;
        String textValue2 = textValue;

        while (i < achatuserinfo1.length) {
            int j = this.field_176044_d.indexOf(achatuserinfo1[i]);
            if (j >= 0) {
                this.field_176044_d.remove(j);
            }

            i++;
            if (textValue2 != null) {
                break;
            }
        }

        i = 0;

        while (i < achatuserinfo2.length) {
            int k = this.field_176044_d.indexOf(achatuserinfo2[i]);
            if (k >= 0) {
                this.field_176044_d.remove(k);
            }

            this.field_176044_d.add(achatuserinfo2[i]);
            i++;
            if (textValue2 != null) {
                break;
            }
        }

        i = 0;

        while (i < achatuserinfo.length) {
            this.field_176044_d.add(achatuserinfo[i]);
            i++;
            if (textValue2 != null) {
                break;
            }
        }

        try {
            if (this.field_176049_h.field_153003_a != null) {
                this.field_176049_h.field_153003_a.func_176018_a(
                        this.field_176048_a, achatuserinfo, achatuserinfo1, achatuserinfo2);
            }
        } catch (Exception exception) {
            this.field_176049_h.func_152995_h(exception.toString());
        }
    }

    public void chatChannelMembershipCallback(
            String text, ChatEvent chatevent, ChatChannelInfo chatchannelinfo) {
        String textValue = IngestServerTester.sT();
        switch (chatevent) {
            case TTV_CHAT_JOINED_CHANNEL:
                this.func_176035_a(ChatControllerEnumChannelState.Connected);
                this.func_176031_c(text);
                if (textValue == null) {
                    break;
                }
            case TTV_CHAT_LEFT_CHANNEL:
                this.func_176030_k();
        }
    }

    public void chatBadgeDataDownloadCallback(String text, ErrorCode errorcode) {
        if (ErrorCode.succeeded(errorcode)) {
            this.l();
        }
    }

    protected void cj() {
        if (this.field_176049_h.aMz != ChatControllerEnumEmoticonMode.None
                && this.field_176043_g == null) {
            ErrorCode errorcode =
                    this.field_176049_h.field_153008_f.downloadBadgeData(this.field_176048_a);
            if (ErrorCode.failed(errorcode)) {
                String string = ErrorCode.getString(errorcode);
                this.field_176049_h.func_152995_h(
                        String.format("Error trying to download badge data: %s", string));
            }
        }
    }

    public void method_be01d1eb5e587c583ed6(String text) {
        label61:
        {
            String textValue = IngestServerTester.sT();
            if (this.field_176049_h.aMz == ChatControllerEnumEmoticonMode.None) {
                this.aMK.clear();
                this.aMJ.clear();
                if (textValue == null) {
                    break label61;
                }
            }

            if (this.aMK.size() > 0) {
                ListIterator listiterator = this.aMK.listIterator();

                while (listiterator.hasNext()) {
                    ChatRawMessage chatrawmessage = (ChatRawMessage) listiterator.next();
                    if (chatrawmessage.userName.equals(text)) {
                        listiterator.remove();
                    }

                    if (textValue != null) {
                        break;
                    }
                }
            }

            if (this.aMJ.size() > 0) {
                ListIterator listiterator1 = this.aMJ.listIterator();

                while (listiterator1.hasNext()) {
                    ChatTokenizedMessage chattokenizedmessage =
                            (ChatTokenizedMessage) listiterator1.next();
                    if (chattokenizedmessage.displayName.equals(text)) {
                        listiterator1.remove();
                    }

                    if (textValue != null) {
                        break;
                    }
                }
            }
        }

        try {
            if (this.field_176049_h.field_153003_a != null) {
                this.field_176049_h.field_153003_a.func_176019_a(this.field_176048_a, text);
            }
        } catch (Exception exception) {
            this.field_176049_h.func_152995_h(exception.toString());
        }
    }

    public ChatControllerChatChannelListener(ChatController aMLValue, String text) {
        this.field_176049_h = aMLValue;
        this.field_176048_a = null;
        this.field_176046_b = false;
        this.field_176047_c = ChatControllerEnumChannelState.Created;
        this.field_176044_d = Lists.newArrayList();
        this.aMK = new LinkedList();
        this.aMJ = new LinkedList();
        this.field_176043_g = null;
        this.field_176048_a = text;
    }

    public void chatChannelTokenizedMessageCallback(
            String text, ChatTokenizedMessage[] achattokenizedmessage) {
        String textValue = IngestServerTester.sT();
        int i = 0;
        String textValue2 = textValue;

        while (i < achattokenizedmessage.length) {
            this.aMJ.addLast(achattokenizedmessage[i]);
            i++;
            if (textValue2 != null) {
                break;
            }
        }

        try {
            if (this.field_176049_h.field_153003_a != null) {
                this.field_176049_h.field_153003_a.func_176025_a(
                        this.field_176048_a, achattokenizedmessage);
            }
        } catch (Exception exception) {
            this.field_176049_h.func_152995_h(exception.toString());
        }

        while (this.aMJ.size() > this.field_176049_h.mF) {
            this.aMJ.removeFirst();
            if (textValue2 != null) {
                break;
            }
        }
    }

    public void chatClearCallback(String text, String textValue) {
        this.method_be01d1eb5e587c583ed6(textValue);
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}
