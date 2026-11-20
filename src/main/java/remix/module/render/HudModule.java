package remix.module.render;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import remix.ui.particle.DamageIndicatorParticle;
import remix.ui.hud.HudArrayMode;
import remix.ui.hud.HudColor;
import remix.ui.hud.HudMode;
import remix.ui.hud.HudPosition;
import remix.ui.hud.HudSorting;
import remix.event.impl.JumpEvent;
import remix.client.notification.NotificationManager;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.impl.Render2DEvent;
import remix.event.annotation.Subscribe;
import remix.event.annotation.SubscriberPriority;
import remix.client.RemixClient;
import remix.input.InputEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;
import remix.ui.font.ClientFontRenderer;
import remix.ui.hud.ArrayListHudElement;
import remix.ui.hud.CompassHudElement;
import remix.ui.hud.InfoOverlayHudElement;
import remix.ui.hud.PotionEffectsHudElement;
import remix.ui.hud.SpeedometerHudElement;
import remix.ui.hud.TabGuiHudElement;
import remix.ui.hud.TargetHudElement;
import remix.ui.hud.WatermarkHudElement;

/** Hud module: render events and 21 settings. */
public final class HudModule extends ModuleBase {
    private static String[] packedTextFragments;
    public ArrayListHudElement moduleListRenderer;
    public Setting potionEffectsSetting;
    public Setting tpsSetting;
    public WatermarkHudElement watermarkRenderer;
    private CompassHudElement compassRenderer;
    public Setting alphaSetting;

    public Setting targetHUDSetting;
    public Setting serverSetting;
    public InfoOverlayHudElement informationRenderer;
    public Setting<HudArrayMode> arraySetting;
    private SpeedometerHudElement speedRenderer;
    public Setting uidSetting;
    public Setting speedSetting;
    private PotionEffectsHudElement potionRenderer;
    public final Setting<HudMode> hudSetting = new Setting("HUD", this, HudMode.REMIX);

    public Setting modlistSetting;
    public Setting armorSetting;
    public Setting fpsSetting;
    public Setting damageIndicatorsSetting;
    public Setting tabGuiSetting;
    public TabGuiHudElement tabGuiRenderer;
    private ClientFontRenderer mainFont;
    private TargetHudElement targetHudRenderer;
    private ClientFontRenderer tabGuiFont;
    public Setting compassSetting;
    public Setting<HudColor> colorSetting;
    public Setting coordinatesSetting;
    public Setting<HudSorting> sortingSetting;
    public Setting notificationsSetting;
    public Setting<HudPosition> positionSetting;
    public Setting compassModeSetting;

    static {
        packedTextFragments = new String[3];
        packedTextFragments[0] = "NESH3b";
    }

    @Subscribe(mc = JumpEvent.class, priority = SubscriberPriority.HIGHEST)
    public void onJump(JumpEvent event) {
        SpeedometerHudElement.jumpSampleRequested = true;
    }

    @Subscribe(mc = PlayerUpdateEvent.class, priority = SubscriberPriority.HIGHEST)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        this.speedRenderer.updateSpeedSample();
    }

    public HudModule() {
        super("Hud", new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.arraySetting = new Setting("Array", this, HudArrayMode.WHITE);
        this.positionSetting = new Setting("Position", this, HudPosition.TOP_RIGHT);
        this.colorSetting = new Setting("Color", this, HudColor.CLIENT);
        this.sortingSetting = new Setting("Sorting", this, HudSorting.LENGTH);
        this.compassModeSetting = new Setting("CompassMode", this, 2.0, 0.0, 3.0, 1.0);
        this.tabGuiSetting = new Setting("TabGui", this, true);
        this.modlistSetting = new Setting("Modlist", this, true);
        this.potionEffectsSetting = new Setting("PotionEffects", this, true);
        this.damageIndicatorsSetting = new Setting("DamageIndicators", this, true);
        this.compassSetting = new Setting("Compass", this, false);
        this.targetHUDSetting = new Setting("TargetHUD", this, true);
        this.coordinatesSetting = new Setting("Coordinates", this, true);
        this.serverSetting = new Setting("Server", this, true);
        this.fpsSetting = new Setting("Fps", this, true);
        this.tpsSetting = new Setting("Tps", this, true);
        this.armorSetting = new Setting("Armor", this, true);
        this.uidSetting = new Setting("UID", this, true);
        this.alphaSetting = new Setting("Alpha", this, false);
        this.speedSetting = new Setting("Speed", this, true);
        this.notificationsSetting = new Setting("Notifications", this, true);
        this.compassRenderer = new CompassHudElement(this.compassModeSetting);
        this.targetHudRenderer = new TargetHudElement();
        this.mainFont = RemixClient.fonts.roboto19;
        this.watermarkRenderer = new WatermarkHudElement(this.colorSetting, this.mainFont);
        this.informationRenderer =
                new InfoOverlayHudElement(
                        this.coordinatesSetting,
                        this.fpsSetting,
                        this.tpsSetting,
                        this.armorSetting,
                        this.serverSetting,
                        this.uidSetting,
                        this.mainFont);
        this.moduleListRenderer =
                new ArrayListHudElement(
                        this.arraySetting,
                        this.positionSetting,
                        this.tabGuiSetting,
                        this.speedSetting,
                        this.serverSetting,
                        this.uidSetting,
                        this.coordinatesSetting,
                        this.fpsSetting,
                        this.tpsSetting,
                        this.colorSetting,
                        this.sortingSetting,
                        this.alphaSetting,
                        this.mainFont);
        this.potionRenderer =
                new PotionEffectsHudElement(this.mainFont, this.serverSetting, this.uidSetting);
        this.speedRenderer =
                new SpeedometerHudElement(this.mainFont, this.serverSetting, this.uidSetting);
        this.tabGuiFont = RemixClient.fonts.menuRoboto16;
        this.tabGuiRenderer = new TabGuiHudElement(this.hudSetting, this.tabGuiFont);
    }

    public String getSuffix() {
        return ((HudMode) this.hudSetting.getValue()).getDisplayName();
    }

    public void showHealthChange(Entity entity, float healthDelta) {
        if (healthDelta != 0.0F) {
            healthDelta = Math.round(healthDelta);
            if (healthDelta != 0.0F) {
                this.spawnFloatingText(
                        entity,
                        Float.toString(Math.abs(healthDelta)),
                        healthDelta > 0.0F ? -11141291 : -43691);
            }
        }
    }

    @Subscribe(mc = InputEvent.class)
    public void onInput(InputEvent event) {
        if (this.tabGuiSetting.asBoolean()) {
            this.tabGuiRenderer.handleKey(event.getKeyCode());
        }
    }

    public void spawnFloatingText(Entity entity, String text, int color) {
        World world = entity.world;
        double d0 = world.rand.nextGaussian() * 0.02;
        double d2 = world.rand.nextGaussian() * 0.02;
        DamageIndicatorParticle damageIndicatorParticle =
                new DamageIndicatorParticle(
                        text,
                        color,
                        world,
                        entity.posX,
                        entity.posY + entity.height,
                        entity.posZ,
                        d0,
                        0.5,
                        d2);
        minecraft.effectRenderer.addEffect(damageIndicatorParticle);
    }

    @Subscribe(mc = Render2DEvent.class, priority = SubscriberPriority.HIGHEST)
    public void onRender2D(Render2DEvent event) {
        if (!minecraft.gameSettings.showDebugInfo) {
            ScaledResolution resolution = event.getResolution();
            if (this.coordinatesSetting.asBoolean()
                    || this.fpsSetting.asBoolean()
                    || this.tpsSetting.asBoolean()
                    || this.serverSetting.asBoolean()
                    || this.uidSetting.asBoolean()
                    || this.armorSetting.asBoolean()) {
                this.informationRenderer.render(resolution);
            }

            if (this.targetHUDSetting.asBoolean()) {
                this.targetHudRenderer.render(resolution);
            }

            if (this.compassSetting.asBoolean()) {
                this.compassRenderer.render(resolution);
            }

            if (this.modlistSetting.asBoolean()) {
                this.moduleListRenderer.render(resolution);
            }

            if (this.potionEffectsSetting.asBoolean()) {
                this.potionRenderer.render(resolution);
            }

            if (this.tabGuiSetting.asBoolean()) {
                this.tabGuiRenderer.render(resolution);
            }

            if (this.speedSetting.asBoolean()) {
                this.speedRenderer.render(resolution);
            }

            if (this.notificationsSetting.asBoolean()) {
                float height = resolution.getHeight();
                for (NotificationManager notification : NotificationManager.activeNotifications) {
                    if (!notification.hasExpired(resolution)) {
                        notification.render(resolution, height);
                    } else {
                        NotificationManager.activeNotifications.remove(notification);
                    }
                    height -= notification.getHeight() + 2;
                }
            } else {
                NotificationManager.activeNotifications.clear();
            }

            this.watermarkRenderer.render(resolution);
        }

        remix.module.ModuleMetadata.clientServices.setThemeColor(
                ((HudColor) this.colorSetting.getValue()).getColorForModule(this));
    }
}
