package net.minecraft.util;

import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEventAction;
import net.minecraft.util.ChatStyleRootStyleChatStyle;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.CameraModule;

public class ChatStyle {
    private String insertion;
    private Boolean obfuscated;
    private Boolean bold;
    private Boolean italic;

    private static ChatStyle rootStyle;
    private Boolean underlined;
    private HoverEvent chatHoverEvent;

    private Boolean strikethrough;
    private EnumChatFormatting color;
    private ChatStyle parentStyle;
    private ClickEvent chatClickEvent;
    private static String[] packedTextFragments;

    @Override
    public boolean equals(Object object) {
        boolean strictInteractions = Vec3i.getLegacyCoordinateFlowMarker() != null;
        if (this == object) {
            return true;
        }
        if (!(object instanceof ChatStyle)) {
            return false;
        }
        ChatStyle other = (ChatStyle) object;
        return this.getBold() == other.getBold()
                && this.getColor() == other.getColor()
                && this.getItalic() == other.getItalic()
                && this.getObfuscated() == other.getObfuscated()
                && this.getStrikethrough() == other.getStrikethrough()
                && this.getUnderlined() == other.getUnderlined()
                && this.matchesClickEvent(other, strictInteractions)
                && this.matchesHoverEvent(other, strictInteractions)
                && this.matchesInsertion(other, strictInteractions);
    }

    private boolean matchesClickEvent(ChatStyle other, boolean strict) {
        if (this.getChatClickEvent() != null) {
            if (this.getChatClickEvent().equals(other.getChatClickEvent())) {
                return true;
            }
            if (strict) {
                return false;
            }
        }
        return other.getChatClickEvent() == null || !strict;
    }

    private boolean matchesHoverEvent(ChatStyle other, boolean strict) {
        if (this.getChatHoverEvent() != null) {
            if (this.getChatHoverEvent().equals(other.getChatHoverEvent())) {
                return true;
            }
            if (strict) {
                return false;
            }
        }
        return other.getChatHoverEvent() == null || !strict;
    }

    private boolean matchesInsertion(ChatStyle other, boolean strict) {
        if (this.getInsertion() != null) {
            if (this.getInsertion().equals(other.getInsertion())) {
                return true;
            }
            if (strict) {
                return false;
            }
        }
        return other.getInsertion() == null || !strict;
    }

    public boolean getStrikethrough() {
        return this.strikethrough == null
                ? this.getParent().getStrikethrough()
                : this.strikethrough;
    }

    @Override
    public int hashCode() {
        int i = this.color.hashCode();
        i = 31 * i + this.bold.hashCode();
        i = 31 * i + this.italic.hashCode();
        i = 31 * i + this.underlined.hashCode();
        i = 31 * i + this.strikethrough.hashCode();
        i = 31 * i + this.obfuscated.hashCode();
        i = 31 * i + this.chatClickEvent.hashCode();
        i = 31 * i + this.chatHoverEvent.hashCode();
        return 31 * i + this.insertion.hashCode();
    }

    static Boolean access$402(ChatStyle chatStyle, Boolean obool) {
        return chatStyle.obfuscated = obool;
    }

    public ChatStyle setParentStyle(ChatStyle bEMValue) {
        this.parentStyle = bEMValue;
        return this;
    }

    public ChatStyle createDeepCopy() {
        ChatStyle chatStyle = new ChatStyle();
        chatStyle.setBold(this.getBold());
        chatStyle.setItalic(this.getItalic());
        chatStyle.setStrikethrough(this.getStrikethrough());
        chatStyle.setUnderlined(this.getUnderlined());
        chatStyle.setObfuscated(this.getObfuscated());
        chatStyle.setColor(this.getColor());
        chatStyle.setChatClickEvent(this.getChatClickEvent());
        chatStyle.setChatHoverEvent(this.getChatHoverEvent());
        chatStyle.setInsertion(this.getInsertion());
        return chatStyle;
    }

    static {
        net.minecraft.util.ChatStyle.packedTextFragments = new String[3];
        net.minecraft.util.ChatStyle.packedTextFragments[0] = new String("");
        net.minecraft.util.ChatStyle.rootStyle = new ChatStyleRootStyleChatStyle();
    }

    public String getInsertion() {
        return this.insertion == null ? this.getParent().getInsertion() : this.insertion;
    }

    public boolean getObfuscated() {
        return this.obfuscated == null ? this.getParent().getObfuscated() : this.obfuscated;
    }

    public ChatStyle setChatHoverEvent(HoverEvent bEJValue) {
        this.chatHoverEvent = bEJValue;
        return this;
    }

    public ChatStyle setChatClickEvent(ClickEvent bENValue) {
        this.chatClickEvent = bENValue;
        return this;
    }

    public boolean getBold() {
        return this.bold == null ? this.getParent().getBold() : this.bold;
    }

    public boolean getUnderlined() {
        return this.underlined == null ? this.getParent().getUnderlined() : this.underlined;
    }

    static Boolean access$300(ChatStyle chatStyle) {
        return chatStyle.strikethrough;
    }

    static EnumChatFormatting access$500(ChatStyle chatStyle) {
        return chatStyle.color;
    }

    static Boolean access$200(ChatStyle chatStyle) {
        return chatStyle.underlined;
    }

    public String getFormattingCode() {
        if (this.isEmpty()) {
            return this.parentStyle != null ? this.parentStyle.getFormattingCode() : packedTextFragments[0];
        }

        StringBuilder stringbuilder = new StringBuilder();
        if (this.getColor() != null) {
            stringbuilder.append(this.getColor());
        }

        if (this.getBold()) {
            stringbuilder.append(EnumChatFormatting.BOLD);
        }

        if (this.getItalic()) {
            stringbuilder.append(EnumChatFormatting.ITALIC);
        }

        if (this.getUnderlined()) {
            stringbuilder.append(EnumChatFormatting.UNDERLINE);
        }

        if (this.getObfuscated()) {
            stringbuilder.append(EnumChatFormatting.OBFUSCATED);
        }

        if (this.getStrikethrough()) {
            stringbuilder.append(EnumChatFormatting.STRIKETHROUGH);
        }

        return stringbuilder.toString();
    }

    public boolean getItalic() {
        return this.italic == null ? this.getParent().getItalic() : this.italic;
    }

    public ChatStyle setBold(Boolean obool) {
        this.bold = obool;
        return this;
    }

    public ChatStyle setStrikethrough(Boolean obool) {
        this.strikethrough = obool;
        return this;
    }

    static String access$602(ChatStyle chatStyle, String text) {
        return chatStyle.insertion = text;
    }

    public ChatStyle setInsertion(String text) {
        this.insertion = text;
        return this;
    }

    static Boolean access$202(ChatStyle chatStyle, Boolean obool) {
        return chatStyle.underlined = obool;
    }

    @Override
    public String toString() {
        return "Style{hasParent="
                + (this.parentStyle != null)
                + ", color="
                + this.color
                + ", bold="
                + this.bold
                + ", italic="
                + this.italic
                + ", underlined="
                + this.underlined
                + ", obfuscated="
                + this.obfuscated
                + ", clickEvent="
                + this.getChatClickEvent()
                + ", hoverEvent="
                + this.getChatHoverEvent()
                + ", insertion="
                + this.getInsertion()
                + '}';
    }

    static Boolean access$000(ChatStyle chatStyle) {
        return chatStyle.bold;
    }

    static Boolean access$302(ChatStyle chatStyle, Boolean obool) {
        return chatStyle.strikethrough = obool;
    }

    public ChatStyle setObfuscated(Boolean obool) {
        this.obfuscated = obool;
        return this;
    }

    private ChatStyle getParent() {
        return this.parentStyle == null ? net.minecraft.util.ChatStyle.rootStyle : this.parentStyle;
    }

    static EnumChatFormatting access$502(ChatStyle chatStyle, EnumChatFormatting bELValue) {
        return chatStyle.color = bELValue;
    }

    static Boolean access$100(ChatStyle chatStyle) {
        return chatStyle.italic;
    }

    public ChatStyle createShallowCopy() {
        ChatStyle chatStyle = new ChatStyle();
        chatStyle.bold = this.bold;
        chatStyle.italic = this.italic;
        chatStyle.strikethrough = this.strikethrough;
        chatStyle.underlined = this.underlined;
        chatStyle.obfuscated = this.obfuscated;
        chatStyle.color = this.color;
        chatStyle.chatClickEvent = this.chatClickEvent;
        chatStyle.chatHoverEvent = this.chatHoverEvent;
        chatStyle.parentStyle = this.parentStyle;
        chatStyle.insertion = this.insertion;
        return chatStyle;
    }

    static HoverEvent access$800(ChatStyle chatStyle) {
        return chatStyle.chatHoverEvent;
    }

    static ClickEvent access$700(ChatStyle chatStyle) {
        return chatStyle.chatClickEvent;
    }

    public ChatStyle setColor(EnumChatFormatting bELValue) {
        this.color = bELValue;
        return this;
    }

    static Boolean access$400(ChatStyle chatStyle) {
        return chatStyle.obfuscated;
    }

    public HoverEvent getChatHoverEvent() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        if (camera.isEnabled()
                && camera.nbtViewerSetting.asBoolean()
                && this.chatClickEvent != null) {
            return new HoverEvent(
                    HoverEventAction.SHOW_TEXT,
                    new ChatComponentText(
                            this.chatClickEvent.getAction().toString().toLowerCase()
                                    + ": "
                                    + this.chatClickEvent.getValue()));
        } else {
            return this.chatHoverEvent == null
                    ? this.getParent().getChatHoverEvent()
                    : this.chatHoverEvent;
        }
    }

    public ChatStyle setUnderlined(Boolean obool) {
        this.underlined = obool;
        return this;
    }

    static ClickEvent access$702(ChatStyle chatStyle, ClickEvent bENValue) {
        return chatStyle.chatClickEvent = bENValue;
    }

    static Boolean access$102(ChatStyle chatStyle, Boolean obool) {
        return chatStyle.italic = obool;
    }

    static Boolean access$002(ChatStyle chatStyle, Boolean obool) {
        return chatStyle.bold = obool;
    }

    public ChatStyle setItalic(Boolean obool) {
        this.italic = obool;
        return this;
    }

    public EnumChatFormatting getColor() {
        return this.color == null ? this.getParent().getColor() : this.color;
    }

    static HoverEvent access$802(ChatStyle chatStyle, HoverEvent bEJValue) {
        return chatStyle.chatHoverEvent = bEJValue;
    }

    public boolean isEmpty() {
        return this.bold == null
                && this.italic == null
                && this.strikethrough == null
                && this.underlined == null
                && this.obfuscated == null
                && this.color == null
                && this.chatClickEvent == null
                && this.chatHoverEvent == null;
    }

    static String access$600(ChatStyle chatStyle) {
        return chatStyle.insertion;
    }

    public ClickEvent getChatClickEvent() {
        return this.chatClickEvent == null
                ? this.getParent().getChatClickEvent()
                : this.chatClickEvent;
    }
}
