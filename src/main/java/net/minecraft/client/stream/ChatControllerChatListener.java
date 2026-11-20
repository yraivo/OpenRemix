package net.minecraft.client.stream;

import tv.twitch.ErrorCode;
import tv.twitch.chat.ChatRawMessage;
import tv.twitch.chat.ChatTokenizedMessage;
import tv.twitch.chat.ChatUserInfo;

public interface ChatControllerChatListener {
    void method_30d7470cbf2bd0219255(String text);

    void func_176017_a(ChatControllerChatState chatControllerChatState);

    void func_180606_a(String text);

    void l();

    void func_176022_e(ErrorCode errorcode);

    void func_180605_a(String text, ChatRawMessage[] achatrawmessage);

    void func_176018_a(
            String text,
            ChatUserInfo[] achatuserinfo,
            ChatUserInfo[] achatuserinfo1,
            ChatUserInfo[] achatuserinfo2);

    void func_180607_b(String text);

    void func_176025_a(String text, ChatTokenizedMessage[] achattokenizedmessage);

    void func_176019_a(String text, String textValue);

    void func_176023_d(ErrorCode errorcode);

    void cQ();

    void method_75df6c73644e20229bce(String text);
}
