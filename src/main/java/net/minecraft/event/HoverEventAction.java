package net.minecraft.event;

import com.google.common.collect.Maps;

import java.util.Map;

public enum HoverEventAction {
    SHOW_TEXT("SHOW_TEXT", 0, "show_text", true),
    SHOW_ACHIEVEMENT("SHOW_ACHIEVEMENT", 1, "show_achievement", true),
    SHOW_ITEM("SHOW_ITEM", 2, "show_item", true),
    SHOW_ENTITY("SHOW_ENTITY", 3, "show_entity", true);

    private static HoverEventAction[] ZB;

    private boolean allowedInChat;
    private static Map nameMapping;
    private String canonicalName;

    public static HoverEventAction getValueByCanonicalName(String text) {
        return (HoverEventAction) net.minecraft.event.HoverEventAction.nameMapping.get(text);
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    // region String decoding support

    // endregion

    HoverEventAction(String text, int j, String textValue, boolean allowedInChatValue) {
        this.canonicalName = textValue;
        this.allowedInChat = allowedInChatValue;
    }

    public boolean shouldAllowInChat() {
        return this.allowedInChat;
    }

    // region String decoding support

    // endregion

    static {
        ZB =
                new HoverEventAction[] {
                    net.minecraft.event.HoverEventAction.SHOW_TEXT,
                    net.minecraft.event.HoverEventAction.SHOW_ACHIEVEMENT,
                    net.minecraft.event.HoverEventAction.SHOW_ITEM,
                    net.minecraft.event.HoverEventAction.SHOW_ENTITY
                };
        net.minecraft.event.HoverEventAction.nameMapping = Maps.newHashMap();
        for (HoverEventAction class_cf3ac68f5894a728b055 : values()) {
            net.minecraft.event.HoverEventAction.nameMapping.put(
                    class_cf3ac68f5894a728b055.getCanonicalName(), class_cf3ac68f5894a728b055);
        }
    }

    // region String decoding support

    // endregion

}
