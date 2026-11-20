package net.minecraft.client.gui.stream;

import com.google.common.collect.Lists;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.stream.IStream;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import tv.twitch.chat.ChatUserInfo;
import tv.twitch.chat.ChatUserMode;
import tv.twitch.chat.ChatUserSubscription;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GuiTwitchUserMode extends GuiScreen {

    private IStream stream;
    private ChatUserInfo arU;
    private final List arQ = Lists.newArrayList();
    private static EnumChatFormatting arV;
    private static EnumChatFormatting arW;
    private ChatComponent arX;
    private static EnumChatFormatting arY;

    private int D;

    public void drawScreen(int i, int j, float f) {
        int i1 = MathHelper.method_7eec03da329a017ec143();
        this.drawDefaultBackground();
        int k = i1;
        this.drawCenteredString(
                this.fontRendererObj, this.arX.getUnformattedText(), this.width / 2, 70, 16777215);
        int l = 80;

        for (java.util.Iterator<?> __recoveryIterator171_1692 = (this.arQ).iterator();
                (__recoveryIterator171_1692).hasNext(); ) {
            ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_1692).next();
            this.drawString(
                    this.fontRendererObj, chatComponent.getFormattedText(), this.D, l, 16777215);
            l += this.fontRendererObj.FONT_HEIGHT;
            if (k != 0) {
                break;
            }
        }

        super.drawScreen(i, j, f);
    }

    public GuiTwitchUserMode(IStream arTValue, ChatUserInfo chatuserinfo) {
        this.stream = arTValue;
        this.arU = chatuserinfo;
        this.arX = new ChatComponentText(chatuserinfo.displayName);
        this.arQ.addAll(
                method_cd08be8d4500ca3eb3bc(
                        chatuserinfo.modes, chatuserinfo.subscriptions, arTValue));
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        int i = MathHelper.method_beea3e16fe3ec0995eb9();
        if (guiButton.enabled) {
            label43:
            {
                if (guiButton.id == 0) {
                    this.stream.func_152917_b("/ban " + this.arU.displayName);
                    if (i != 0) {
                        break label43;
                    }
                }

                if (guiButton.id == 3) {
                    this.stream.func_152917_b("/unban " + this.arU.displayName);
                    if (i != 0) {
                        break label43;
                    }
                }

                if (guiButton.id == 2) {
                    this.stream.func_152917_b("/mod " + this.arU.displayName);
                    if (i != 0) {
                        break label43;
                    }
                }

                if (guiButton.id == 4) {
                    this.stream.func_152917_b("/unmod " + this.arU.displayName);
                    if (i != 0) {
                        break label43;
                    }
                }

                if (guiButton.id == 1) {
                    this.stream.func_152917_b("/timeout " + this.arU.displayName);
                }
            }

            this.mc.displayGuiScreen((GuiScreen) null);
        }
    }

    public static ChatComponent method_438c311d4dd13bd76cac(
            ChatUserSubscription chatusersubscription, String text, boolean flag) {
        ChatComponentTranslation chatComponentTranslation = null;
        if (chatusersubscription == ChatUserSubscription.TTV_CHAT_USERSUB_SUBSCRIBER) {
            if (text == null) {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "stream.user.subscription.subscriber", new Object[0]);
            } else if (flag) {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "stream.user.subscription.subscriber.self", new Object[0]);
            } else {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "stream.user.subscription.subscriber.other", new Object[] {text});
            }

            chatComponentTranslation.getChatStyle().setColor(arV);
        } else if (chatusersubscription == ChatUserSubscription.TTV_CHAT_USERSUB_TURBO) {
            chatComponentTranslation =
                    new ChatComponentTranslation("stream.user.subscription.turbo", new Object[0]);
            chatComponentTranslation.getChatStyle().setColor(arW);
        }

        return chatComponentTranslation;
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public static ChatComponent method_aa9cd9ec12534da77494(
            ChatUserMode chatusermode, String text, boolean flag) {
        ChatComponentTranslation chatComponentTranslation = null;
        if (chatusermode == ChatUserMode.TTV_CHAT_USERMODE_ADMINSTRATOR) {
            chatComponentTranslation =
                    new ChatComponentTranslation("stream.user.mode.administrator", new Object[0]);
            chatComponentTranslation.getChatStyle().setColor(arW);
        } else if (chatusermode == ChatUserMode.TTV_CHAT_USERMODE_BANNED) {
            if (text == null) {
                chatComponentTranslation =
                        new ChatComponentTranslation("stream.user.mode.banned", new Object[0]);
            } else if (flag) {
                chatComponentTranslation =
                        new ChatComponentTranslation("stream.user.mode.banned.self", new Object[0]);
            } else {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "stream.user.mode.banned.other", new Object[] {text});
            }

            chatComponentTranslation.getChatStyle().setColor(arY);
        } else if (chatusermode == ChatUserMode.TTV_CHAT_USERMODE_BROADCASTER) {
            if (text == null) {
                chatComponentTranslation =
                        new ChatComponentTranslation("stream.user.mode.broadcaster", new Object[0]);
            } else if (flag) {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "stream.user.mode.broadcaster.self", new Object[0]);
            } else {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "stream.user.mode.broadcaster.other", new Object[0]);
            }

            chatComponentTranslation.getChatStyle().setColor(arV);
        } else if (chatusermode == ChatUserMode.TTV_CHAT_USERMODE_MODERATOR) {
            if (text == null) {
                chatComponentTranslation =
                        new ChatComponentTranslation("stream.user.mode.moderator", new Object[0]);
            } else if (flag) {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "stream.user.mode.moderator.self", new Object[0]);
            } else {
                chatComponentTranslation =
                        new ChatComponentTranslation(
                                "stream.user.mode.moderator.other", new Object[] {text});
            }

            chatComponentTranslation.getChatStyle().setColor(arV);
        } else if (chatusermode == ChatUserMode.TTV_CHAT_USERMODE_STAFF) {
            chatComponentTranslation =
                    new ChatComponentTranslation("stream.user.mode.staff", new Object[0]);
            chatComponentTranslation.getChatStyle().setColor(arW);
        }

        return chatComponentTranslation;
    }

    public static List method_cd08be8d4500ca3eb3bc(Set set, Set set1, IStream iStream) {
        int i = MathHelper.method_beea3e16fe3ec0995eb9();
        String text = iStream == null ? null : iStream.func_152921_C();
        int j = iStream != null && iStream.func_152927_B() ? 1 : 0;
        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_9139 = (set).iterator();
                (__recoveryIterator171_9139).hasNext(); ) {
            ChatUserMode chatusermode = (ChatUserMode) (__recoveryIterator171_9139).next();
            ChatComponent chatComponent =
                    method_aa9cd9ec12534da77494(
                            chatusermode, text, remix.compat.compiler.JvmBooleanSlots.fromInt(j));
            if (chatComponent != null) {
                ChatComponentText chatComponentText = new ChatComponentText("- ");
                chatComponentText.appendSibling(chatComponent);
                arrayList.add(chatComponentText);
            }

            if (i == 0) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_9741 = (set1).iterator();
                (__recoveryIterator171_9741).hasNext(); ) {
            ChatUserSubscription chatusersubscription =
                    (ChatUserSubscription) (__recoveryIterator171_9741).next();
            ChatComponent chatComponentValue =
                    method_438c311d4dd13bd76cac(
                            chatusersubscription,
                            text,
                            remix.compat.compiler.JvmBooleanSlots.fromInt(j));
            if (chatComponentValue != null) {
                ChatComponentText chatComponentTextValue = new ChatComponentText("- ");
                chatComponentTextValue.appendSibling(chatComponentValue);
                arrayList.add(chatComponentTextValue);
            }

            if (i == 0) {
                break;
            }
        }

        return arrayList;
    }

    public void initGui() {
        int j = this.width / 3;
        int k = j - 130;
        this.buttonList.add(
                new GuiButton(
                        1,
                        j * 0 + k / 2,
                        this.height - 70,
                        130,
                        20,
                        I18n.format("stream.userinfo.timeout", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        0,
                        j * 1 + k / 2,
                        this.height - 70,
                        130,
                        20,
                        I18n.format("stream.userinfo.ban", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        2,
                        j * 2 + k / 2,
                        this.height - 70,
                        130,
                        20,
                        I18n.format("stream.userinfo.mod", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        5,
                        j * 0 + k / 2,
                        this.height - 45,
                        130,
                        20,
                        I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(
                new GuiButton(
                        3,
                        j * 1 + k / 2,
                        this.height - 45,
                        130,
                        20,
                        I18n.format("stream.userinfo.unban", new Object[0])));
        int i1 = MathHelper.method_beea3e16fe3ec0995eb9();
        this.buttonList.add(
                new GuiButton(
                        4,
                        j * 2 + k / 2,
                        this.height - 45,
                        130,
                        20,
                        I18n.format("stream.userinfo.unmod", new Object[0])));
        int l = 0;
        int i = i1;

        for (java.util.Iterator<?> __recoveryIterator171_32697 = (this.arQ).iterator();
                (__recoveryIterator171_32697).hasNext(); ) {
            ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_32697).next();
            l = Math.max(l, this.fontRendererObj.getStringWidth(chatComponent.getFormattedText()));
            if (i == 0) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
                break;
            }
        }

        this.D = this.width / 2 - l / 2;
    }

    static {
        arV = EnumChatFormatting.DARK_GREEN;
        arY = EnumChatFormatting.RED;
        arW = EnumChatFormatting.DARK_PURPLE;
    }
}
