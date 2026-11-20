package net.minecraft.util;

import net.minecraft.util.ChatComponent;

import com.google.common.base.Function;

public final class ChatComponentStyleCreateDeepCopyIteratorFunction2 implements Function {
    public ChatComponent apply(ChatComponent chatComponent) {
        net.minecraft.util.ChatComponent chatComponentValue = chatComponent.createCopy();
        chatComponentValue.setChatStyle(chatComponentValue.getChatStyle().createDeepCopy());
        return chatComponentValue;
    }

    public Object apply(Object p171_0) {
        return this.apply((net.minecraft.util.ChatComponent) p171_0);
    }
}
