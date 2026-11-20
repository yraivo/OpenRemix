package remix.module.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Vec3;
import remix.ui.hud.HudColor;
import remix.event.MotionPhase;
import remix.util.render.Render2D3DUtils;
import remix.event.impl.Render3DEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.command.TargetsClientCommand;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.player.AutoHypixelModule;
import remix.module.world.AntiBotModule;
import remix.setting.Setting;

import org.lwjgl.opengl.GL11;

import java.awt.Color;

/** Tracers module: render events and 6 settings. */
public final class TracersModule extends ModuleBase {
    private Setting invisiblesSetting;

    private Setting mobsSetting;
    private final Setting lineWidthSetting = new Setting("Line Width", this, 1.0, 1.0, 3.0, 1.0);
    private Setting animalsSetting;

    private final Setting playersSetting = new Setting("Players", this, true);

    private Setting neutralSetting;

    public boolean shouldRenderEntity(Entity entity) {
        AntiBotModule antiBot = ClientServices.moduleManager.antiBot;
        return entity != minecraft.player
                && (!entity.isInvisible() || this.invisiblesSetting.asBoolean())
                && (entity instanceof PlayerEntity
                                && this.playersSetting.asBoolean()
                                && (!antiBot.isEnabled() || !antiBot.isBot(entity))
                        || entity instanceof AnimalEntity && this.animalsSetting.asBoolean()
                        || (entity instanceof MonsterEntity
                                        || entity instanceof EntityGolem
                                        || entity instanceof SlimeEntity)
                                && this.mobsSetting.asBoolean()
                        || (entity instanceof BatEntity
                                        || entity instanceof SquidEntity
                                        || entity instanceof EntityVillager)
                                && this.neutralSetting.asBoolean());
    }

    public TracersModule() {
        super("Tracers", new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.mobsSetting = new Setting("Mobs", this, false);
        this.animalsSetting = new Setting("Animals", this, false);
        this.neutralSetting = new Setting("Neutral", this, false);
        this.invisiblesSetting = new Setting("Invisibles", this, false);
    }

    @Subscribe(mc = Render3DEvent.class)
    public void onRender3D(Render3DEvent event) {
        if (event.getPhase() == MotionPhase.POST) {
            for (Entity entity : minecraft.world.loadedEntities) {
                if (this.shouldRenderEntity(entity)
                        && entity != minecraft.player
                        && entity.getEntityId() != -420
                        && (this.invisiblesSetting.asBoolean() || !entity.isInvisible())) {

                    Render2D3DUtils.beginUnoccludedWorldOverlay();
                    GL11.glLineWidth((float) this.lineWidthSetting.asDouble());
                    ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                    AutoHypixelModule autoHypixel = ClientServices.moduleManager.autoHypixel;
                    if (autoHypixel.isEnabled()
                            && autoHypixel.bountyHunterSetting.asBoolean()
                            && autoHypixel.hasBounty(entity)) {
                        Color color = new Color(-662245);
                        GlStateManager.color(
                                color.getRed() / 255.0F,
                                color.getGreen() / 255.0F,
                                color.getBlue() / 255.0F,
                                color.getAlpha() / 255.0F);
                    } else if (TargetsClientCommand.isTarget(entity)) {
                        Color color1 = HudColor.getRainbowColor(15000.0, 1);
                        GlStateManager.color(
                                color1.getRed() / 255.0F,
                                color1.getGreen() / 255.0F,
                                color1.getBlue() / 255.0F,
                                color1.getAlpha() / 255.0F);
                    } else if (ClientServices.friendManager.isFriend(entity.getName())) {
                        GlStateManager.color(0.1F, 0.3F, 1.0F, 1.0F);
                    } else {
                        float distNorm =
                                Math.min(
                                        minecraft.player.getDistanceToEntity(entity) / 100.0F,
                                        1.0F);
                        GlStateManager.color(1.0F - distNorm, distNorm, 0.0F, 0.75F);
                    }

                    double d3 =
                            entity.lastTickPosX
                                    + (entity.posX - entity.lastTickPosX)
                                            * minecraft.timer.renderPartialTicks;
                    double d4 =
                            entity.lastTickPosY
                                    + (entity.posY - entity.lastTickPosY)
                                            * minecraft.timer.renderPartialTicks;
                    double d5 =
                            entity.lastTickPosZ
                                    + (entity.posZ - entity.lastTickPosZ)
                                            * minecraft.timer.renderPartialTicks;
                    double d0 = d3 - minecraft.getRenderManager().getRenderPosX();
                    double d1 = d4 - minecraft.getRenderManager().getRenderPosY();
                    double d2 = d5 - minecraft.getRenderManager().getRenderPosZ();
                    Vec3 vec3 =
                            new Vec3(0.0, 0.0, 1.0)
                                    .rotatePitch(
                                            -((float)
                                                    Math.toRadians(minecraft.player.rotationPitch)))
                                    .rotateYaw(
                                            -((float)
                                                    Math.toRadians(minecraft.player.rotationYaw)));
                    GlStateManager.ab(3);
                    GlStateManager.vertex3d(
                            (float) vec3.x,
                            minecraft.player.getEyeHeight() + (float) vec3.y,
                            (float) vec3.z);
                    GlStateManager.vertex3d((float) d0, (float) d1, (float) d2);
                    GlStateManager.mO();
                    Render2D3DUtils.endUnoccludedWorldOverlay();
                }
            }
        }
    }
}
