package remix.client.notification;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.vecmath.Vector2f;
import remix.ui.animation.AnimationInterpolator;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.InfoOverlayHudElement;
import remix.util.render.Render2D3DUtils;
import remix.util.time.ElapsedTimer;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class NotificationManager {
    private String[] lines;
    private static String[] packedTextFragments;
    private ElapsedTimer elapsedTimer;
    private AnimationInterpolator animationInterpolator;
    private static String notificationBodyColorPrefix;
    private int durationMillis;
    private int notificationHeight;
    private MinecraftClient minecraftClient;
    private NotificationPriority notificationPriority;
    public static List<NotificationManager> activeNotifications;

    // region String decoding support

    // endregion

    public ElapsedTimer getElapsedTimer() {
        return this.elapsedTimer;
    }

    public int getHeight() {
        return this.notificationHeight;
    }

    public void setAnimationInterpolator(AnimationInterpolator animationInterpolatorValue) {
        this.animationInterpolator = animationInterpolatorValue;
    }

    // region String decoding support

    // endregion

    public String[] getLines() {
        return this.lines;
    }

    public void render(ScaledResolution scaledResolution, float f) {
        boolean flag = NotificationPriority.isLegacyNotificationFlowFlagUnset();
        this.animationInterpolator.setTargetPosition(
                new Vector2f(
                        this.elapsedTimer.hasElapsed(this.durationMillis)
                                ? scaledResolution.getWidth()
                                : scaledResolution.getWidth() - 200,
                        f));
        float f1 = this.animationInterpolator.stepX();
        float f2 = this.animationInterpolator.stepY();
        ClientFontRenderer clientFontRenderer = RemixClient.fonts.roboto32;
        Gui.drawRect(f1, f2 - this.notificationHeight, f1 + 200.0F, f2, 2013265920);
        switch (this.getNotificationPriority()) {
            case HIGH:
                Gui.drawRect(f1, f2 - this.notificationHeight, f1 + 8.0F, f2, -52433);
                Gui.drawRect(f1, f2 - this.notificationHeight, f1 + 6.0F, f2, -3333341);
                Render2D3DUtils.drawHollowRect(f1, f2 - this.notificationHeight, f1 + 200.0F, f2);
                clientFontRenderer.drawStringWithShadow(
                        clientFontRenderer.trimStringToWidth(packedTextFragments[0], 200),
                        f1 + 1.0F,
                        f2 - this.notificationHeight + 2.0F,
                        -1);

                break;

            case NORMAL:
                Gui.drawRect(f1, f2 - this.notificationHeight, f1 + 8.0F, f2, -1442500);
                Gui.drawRect(f1, f2 - this.notificationHeight, f1 + 6.0F, f2, -4206552);
                Render2D3DUtils.drawHollowRect(f1, f2 - this.notificationHeight, f1 + 200.0F, f2);
                clientFontRenderer.drawStringWithShadow(
                        clientFontRenderer.trimStringToWidth(packedTextFragments[1], 200),
                        f1,
                        f2 - this.notificationHeight + 2.0F,
                        -1);

                break;

            case LOW:
                Gui.drawRect(f1, f2 - this.notificationHeight, f1 + 8.0F, f2, -13710223);
                Gui.drawRect(f1, f2 - this.notificationHeight, f1 + 6.0F, f2, -14176672);
                Render2D3DUtils.drawHollowRect(f1, f2 - this.notificationHeight, f1 + 200.0F, f2);
        }

        float f3 = f2;
        ClientFontRenderer clientFontRendererValue = RemixClient.fonts.roboto21;
        if (this.lines.length >= 1) {
            clientFontRendererValue.drawStringWithShadow(
                    clientFontRendererValue.trimStringToWidth(this.lines[0], 200),
                    f1 + 12.0F,
                    f3 - this.notificationHeight + 2.0F,
                    -1);
            f3 += clientFontRendererValue.getUnscaledHeight();
        }

        int i = 1;
        int j = this.lines.length;

        while (i < j) {
            String text = this.lines[i];
            clientFontRendererValue = RemixClient.fonts.roboto19;
            clientFontRendererValue.drawStringWithShadow(
                    remix.client.notification.NotificationManager.notificationBodyColorPrefix
                            + clientFontRendererValue.trimStringToWidth(text, 200),
                    f1 + 12.0F,
                    f3 - this.notificationHeight + 2.0F,
                    -1);
            f3 += clientFontRendererValue.getUnscaledHeight();
            i++;
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            NotificationPriority.setLegacyNotificationFlowFlag(!flag);
        }
    }

    public NotificationManager(String[] lines, NotificationPriority priority, int durationMillis) {
        this.lines = lines;
        NotificationPriority.getLegacyNotificationFlowFlag();

        this.notificationPriority = priority;
        this.durationMillis = durationMillis;
        this.minecraftClient = MinecraftClient.getMinecraft();
        this.elapsedTimer = new ElapsedTimer();
        ScaledResolution resolution =
                new ScaledResolution(
                        this.minecraftClient,
                        this.minecraftClient.displayWidth,
                        this.minecraftClient.displayHeight);

        ClientFontRenderer bodyFont = RemixClient.fonts.roboto19;
        for (int index = 0; index < lines.length; index++) {
            ClientFontRenderer lineFont = index == 0 ? RemixClient.fonts.roboto21 : bodyFont;
            this.notificationHeight =
                    (int) (this.notificationHeight + lineFont.getUnscaledHeight());
        }
        this.notificationHeight += 4;

        float bottom =
                resolution.getHeight()
                        - (this.minecraftClient.currentScreen instanceof GuiChat ? 24.5F : 12.0F);
        InfoOverlayHudElement info = ClientServices.moduleManager.hud.informationRenderer;
        if (info.serverSetting.asBoolean() || info.uidSetting.asBoolean()) {
            bottom -= bodyFont.getUnscaledHeight();
        }

        this.animationInterpolator =
                new AnimationInterpolator(
                        10.0F,
                        new Vector2f(
                                resolution.getWidth(),
                                bottom
                                        - this.notificationHeight
                                                * remix.client.notification.NotificationManager
                                                        .activeNotifications
                                                        .size()),
                        new Vector2f(
                                resolution.getWidth(),
                                bottom
                                        - this.notificationHeight
                                                * remix.client.notification.NotificationManager
                                                        .activeNotifications
                                                        .size()));
    }

    public NotificationPriority getNotificationPriority() {
        return this.notificationPriority;
    }

    static {
        remix.client.notification.NotificationManager.packedTextFragments = new String[3];
        remix.client.notification.NotificationManager.packedTextFragments[0] = new String("!");
        remix.client.notification.NotificationManager.packedTextFragments[1] = new String("?");
        remix.client.notification.NotificationManager.packedTextFragments[2] =
                new String("\u00b3\u0003");
        remix.client.notification.NotificationManager.notificationBodyColorPrefix = "\u00a77";
        remix.client.notification.NotificationManager.activeNotifications =
                new CopyOnWriteArrayList<>();
    }

    public long getDurationMillis() {
        return this.durationMillis;
    }

    public void setHeight(int i) {
        this.notificationHeight = i;
    }

    public AnimationInterpolator getAnimationInterpolator() {
        return this.animationInterpolator;
    }

    public boolean hasExpired(ScaledResolution scaledResolution) {
        return this.elapsedTimer.hasElapsed(this.durationMillis)
                && this.animationInterpolator.isAtX(scaledResolution.getWidth());
    }

    // region String decoding support

    // endregion

    public static void post(NotificationManager notificationManager) {
        remix.client.notification.NotificationManager.activeNotifications.add(notificationManager);
    }
}
