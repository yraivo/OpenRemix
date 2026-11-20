package net.minecraft.event;

import com.google.common.collect.Maps;

import java.util.Map;

public enum ClickEventAction {
    OPEN_URL("OPEN_URL", 0, "open_url", true),
    OPEN_FILE("OPEN_FILE", 1, "open_file", false),
    RUN_COMMAND("RUN_COMMAND", 2, "run_command", true),
    TWITCH_USER_INFO("TWITCH_USER_INFO", 3, "twitch_user_info", false),
    SUGGEST_COMMAND("SUGGEST_COMMAND", 4, "suggest_command", true),
    CHANGE_PAGE("CHANGE_PAGE", 5, "change_page", true);
    private static ClickEventAction[] agS;

    private boolean allowedInChat;
    private String canonicalName;
    private static Map nameMapping;

    // region String decoding support

    // endregion

    ClickEventAction(String text, int j, String textValue, boolean allowedInChatValue) {
        this.canonicalName = textValue;
        this.allowedInChat = allowedInChatValue;
    }

    static {
        agS =
                new ClickEventAction[] {
                    net.minecraft.event.ClickEventAction.OPEN_URL,
                    net.minecraft.event.ClickEventAction.OPEN_FILE,
                    net.minecraft.event.ClickEventAction.RUN_COMMAND,
                    net.minecraft.event.ClickEventAction.TWITCH_USER_INFO,
                    net.minecraft.event.ClickEventAction.SUGGEST_COMMAND,
                    net.minecraft.event.ClickEventAction.CHANGE_PAGE
                };
        net.minecraft.event.ClickEventAction.nameMapping = Maps.newHashMap();
        for (ClickEventAction class_aec9cc4899e8cc98cd5f : values()) {
            net.minecraft.event.ClickEventAction.nameMapping.put(
                    class_aec9cc4899e8cc98cd5f.getCanonicalName(), class_aec9cc4899e8cc98cd5f);
        }
    }

    public String getCanonicalName() {
        return this.canonicalName;
    }

    // region String decoding support

    // endregion

    public static ClickEventAction getValueByCanonicalName(String text) {
        return (ClickEventAction) net.minecraft.event.ClickEventAction.nameMapping.get(text);
    }

    public boolean shouldAllowInChat() {
        return this.allowedInChat;
    }

    // region String decoding support

    // endregion

}
