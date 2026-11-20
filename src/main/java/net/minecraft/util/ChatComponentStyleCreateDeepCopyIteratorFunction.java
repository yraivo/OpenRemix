package net.minecraft.util;

import net.minecraft.util.ChatComponent;

import com.google.common.base.Function;

import java.util.Iterator;

public final class ChatComponentStyleCreateDeepCopyIteratorFunction implements Function {
    public Iterator apply(ChatComponent chatComponent) {
        return chatComponent.iterator();
    }

    public Object apply(Object p171_0) {
        return this.apply((net.minecraft.util.ChatComponent) p171_0);
    }
}
