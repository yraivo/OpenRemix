package net.minecraft.client.gui.stream;

import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;

public enum GuiStreamUnavailableReason {
    NO_FBO("NO_FBO", 0, new ChatComponentTranslation("stream.unavailable.no_fbo", new Object[0])),
    LIBRARY_ARCH_MISMATCH(
            "LIBRARY_ARCH_MISMATCH",
            1,
            new ChatComponentTranslation(
                    "stream.unavailable.library_arch_mismatch", new Object[0])),
    LIBRARY_FAILURE(
            "LIBRARY_FAILURE",
            2,
            new ChatComponentTranslation("stream.unavailable.library_failure", new Object[0]),
            new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])),
    UNSUPPORTED_OS_WINDOWS(
            "UNSUPPORTED_OS_WINDOWS",
            3,
            new ChatComponentTranslation(
                    "stream.unavailable.not_supported.windows", new Object[0])),
    UNSUPPORTED_OS_MAC(
            "UNSUPPORTED_OS_MAC",
            4,
            new ChatComponentTranslation("stream.unavailable.not_supported.mac", new Object[0]),
            new ChatComponentTranslation(
                    "stream.unavailable.not_supported.mac.okay", new Object[0])),
    UNSUPPORTED_OS_OTHER(
            "UNSUPPORTED_OS_OTHER",
            5,
            new ChatComponentTranslation("stream.unavailable.not_supported.other", new Object[0])),
    ACCOUNT_NOT_MIGRATED(
            "ACCOUNT_NOT_MIGRATED",
            6,
            new ChatComponentTranslation("stream.unavailable.account_not_migrated", new Object[0]),
            new ChatComponentTranslation(
                    "stream.unavailable.account_not_migrated.okay", new Object[0])),
    ACCOUNT_NOT_BOUND(
            "ACCOUNT_NOT_BOUND",
            7,
            new ChatComponentTranslation("stream.unavailable.account_not_bound", new Object[0]),
            new ChatComponentTranslation(
                    "stream.unavailable.account_not_bound.okay", new Object[0])),
    FAILED_TWITCH_AUTH(
            "FAILED_TWITCH_AUTH",
            8,
            new ChatComponentTranslation("stream.unavailable.failed_auth", new Object[0]),
            new ChatComponentTranslation("stream.unavailable.failed_auth.okay", new Object[0])),
    FAILED_TWITCH_AUTH_ERROR(
            "FAILED_TWITCH_AUTH_ERROR",
            9,
            new ChatComponentTranslation("stream.unavailable.failed_auth_error", new Object[0])),
    INITIALIZATION_FAILURE(
            "INITIALIZATION_FAILURE",
            10,
            new ChatComponentTranslation(
                    "stream.unavailable.initialization_failure", new Object[0]),
            new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0])),
    UNKNOWN(
            "UNKNOWN",
            11,
            new ChatComponentTranslation("stream.unavailable.unknown", new Object[0]),
            new ChatComponentTranslation("stream.unavailable.report_to_mojang", new Object[0]));

    private static GuiStreamUnavailableReason[] aNy;

    private ChatComponent aqM;
    private ChatComponent aHE;

    GuiStreamUnavailableReason(String text, int j, ChatComponent aHEValue, ChatComponent aqMValue) {
        this.aHE = aHEValue;
        this.aqM = aqMValue;
    }

    // region String decoding support

    // endregion

    public ChatComponent vT() {
        return this.aqM;
    }

    public ChatComponent uD() {
        return this.aHE;
    }

    static {
        aNy =
                new GuiStreamUnavailableReason[] {
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.NO_FBO,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.LIBRARY_ARCH_MISMATCH,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.LIBRARY_FAILURE,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.UNSUPPORTED_OS_WINDOWS,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.UNSUPPORTED_OS_MAC,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.UNSUPPORTED_OS_OTHER,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.ACCOUNT_NOT_MIGRATED,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.ACCOUNT_NOT_BOUND,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.FAILED_TWITCH_AUTH,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.FAILED_TWITCH_AUTH_ERROR,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.INITIALIZATION_FAILURE,
                    net.minecraft.client.gui.stream.GuiStreamUnavailableReason.UNKNOWN
                };
    }

    // region String decoding support

    // endregion

    GuiStreamUnavailableReason(String text, int j, ChatComponent chatComponent) {
        this(text, j, chatComponent, (ChatComponent) null);
    }
}
