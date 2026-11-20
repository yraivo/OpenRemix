package net.minecraft.client.stream;

import net.minecraft.client.stream.ChatController;
import net.minecraft.client.stream.ChatControllerChatState;
import net.minecraft.client.stream.IngestServerTester;

import tv.twitch.ErrorCode;
import tv.twitch.chat.IChatAPIListener;

public class TwitchChatApiListener implements IChatAPIListener {

    ChatController aMF;

    public void chatShutdownCallback(ErrorCode errorcode) {
        {
            String text = IngestServerTester.sT();
            if (ErrorCode.succeeded(errorcode)) {
                ErrorCode errorcode1 = this.aMF.field_175992_e.shutdown();
                if (ErrorCode.failed(errorcode1)) {
                    String string = ErrorCode.getString(errorcode1);
                    this.aMF.func_152995_h(
                            String.format("Error shutting down the Twitch sdk: %s", string));
                }

                this.aMF.func_175985_a(ChatControllerChatState.Uninitialized);
                if (!(text == null)) {

                    this.aMF.func_175985_a(ChatControllerChatState.Initialized);
                    this.aMF.func_152995_h(String.format("Error shutting down Twith chat: %s", errorcode));
                }
            } else {

                this.aMF.func_175985_a(ChatControllerChatState.Initialized);
                this.aMF.func_152995_h(String.format("Error shutting down Twith chat: %s", errorcode));
            }
        }

        try {
            if (this.aMF.field_153003_a != null) {
                this.aMF.field_153003_a.func_176022_e(errorcode);
            }
        } catch (Exception exception) {
            this.aMF.func_152995_h(exception.toString());
        }
    }

    public void chatInitializationCallback(ErrorCode errorcode) {
        {
            String text = IngestServerTester.sT();
            if (ErrorCode.succeeded(errorcode)) {
                this.aMF.field_153008_f.setMessageFlushInterval(this.aMF.jM);
                this.aMF.field_153008_f.setUserChangeEventInterval(this.aMF.rz);
                this.aMF.cS();
                this.aMF.func_175985_a(ChatControllerChatState.Initialized);
                if (!(text == null)) {

                    this.aMF.func_175985_a(ChatControllerChatState.Uninitialized);
                }
            } else {

                this.aMF.func_175985_a(ChatControllerChatState.Uninitialized);
            }
        }

        try {
            if (this.aMF.field_153003_a != null) {
                this.aMF.field_153003_a.func_176023_d(errorcode);
            }
        } catch (Exception exception) {
            this.aMF.func_152995_h(exception.toString());
        }
    }

    public TwitchChatApiListener(ChatController aMFValue) {
        this.aMF = aMFValue;
    }

    public void chatEmoticonDataDownloadCallback(ErrorCode errorcode) {
        if (ErrorCode.succeeded(errorcode)) {
            this.aMF.dn();
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }


}
