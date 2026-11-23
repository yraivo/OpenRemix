package remix.module.render;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import remix.util.render.Render2D3DUtils;
import remix.event.impl.Render2DEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.vecmath.Vector2f;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.player.AutoHypixelModule;
import remix.setting.Setting;

import org.lwjgl.opengl.GL11;

import java.util.Map.Entry;

/** OnScreenRadar module: render events and 10 settings. */
public final class OnScreenRadarModule extends ModuleBase {
    private final Vector2f direction = new Vector2f();
    private final Vector2f up = new Vector2f(0.0F, 1.0F);
    private Setting mobsSetting;
    private Setting throughWallsSetting;
    private Setting outOfViewSetting;

    private Setting playersSetting;
    private Setting invisiblesSetting;
    private Setting animalsSetting;
    private Setting distanceSetting;
    private final Setting radiusSetting = new Setting("Radius", this, 30.0, 10.0, 100.0, 1.0);

    private final Setting sizeSetting = new Setting("Size", this, 5.0, 5.0, 20.0, 1.0);
    private Setting neutralSetting;

    public OnScreenRadarModule() {
        super("OnScreenRadar", new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.distanceSetting = new Setting("Distance", this, 150.0, 10.0, 300.0, 1.0);
        this.throughWallsSetting = new Setting("ThroughWalls", this, true);
        this.outOfViewSetting = new Setting("OutOfView", this, true);
        this.playersSetting = new Setting("Players", this, true);
        this.mobsSetting = new Setting("Mobs", this, false);
        this.animalsSetting = new Setting("Animals", this, false);
        this.neutralSetting = new Setting("Neutral", this, false);
        this.invisiblesSetting = new Setting("Invisibles", this, false);
        this.setName("On Screen Radar");
    }

    public boolean shouldRenderEntity(Entity entity) {
        return entity != minecraft.player
                && minecraft.player.getDistanceToEntity(entity) <= this.distanceSetting.asDouble()
                && (this.throughWallsSetting.asBoolean()
                        || minecraft.player.canEntityBeSeen(entity))
                && (!entity.isInvisible() || this.invisiblesSetting.asBoolean())
                && (entity instanceof PlayerEntity && this.playersSetting.asBoolean()
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

    @Subscribe(mc = Render2DEvent.class)
    public void onRender2D(Render2DEvent event) {
        int width = event.getWidth();
        int height = event.getHeight();
        GL11.glEnable(3042);
        GL11.glLineWidth(1.0F);
        GL11.glEnable(2848);
        GL11.glTranslatef(width / 2.0F, height / 2.0F, 0.0F);

        for (Entry<LivingEntity, double[][]> entry : ClientServices.moduleManager.esp.projectedBoxVertices.entrySet()) {
            LivingEntity key = entry.getKey();
            if (this.shouldRenderEntity(key)) {
                double[][] vertices = entry.getValue();
                double minX = -1.0;
                double minY = -1.0;
                double minZ = -1.0;
                double maxX = -1.0;
                double maxY = -1.0;
                double maxZ = -1.0;

                for (double[] pt : vertices) {
                    minX = pt[0];
                    minY = pt[1];
                    minZ = pt[2];
                    maxX = pt[0];
                    maxY = pt[1];
                    maxZ = pt[2];
                }

                double midX = (minX + maxX) / 2.0;
                double midY = (minY + maxY) / 2.0;
                boolean outZ = (minZ <= -10.0 || minZ >= 1.0 || maxZ <= -10.0 || maxZ >= 1.0);
                boolean outXY = (minX < 0.0 || minY < 0.0 || minX > width * 2 || minY > height * 2);
                if (!this.outOfViewSetting.asBoolean() || outZ || outXY) {
                    Vector2f v2 = this.direction;
                    v2.x = (int) midX - width / 2;
                    v2.y = (int) midY - height / 2;
                    v2.x -= width / 2.0F;
                    v2.y -= height / 2.0F;
                    float angle = v2.angle(this.up);
                    float f;
                    if (v2.x < 0.0F) {
                        f = (float) Math.toDegrees(angle) - 180.0F;
                    } else {
                        f = (float) (-Math.toDegrees(angle)) + 180.0F;
                    }

                    if (outZ) {
                        f -= 180.0F;
                    }

                    GL11.glRotated(f, 0.0, 0.0, 1.0);
                    ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
                    AutoHypixelModule autoHypixel = ClientServices.moduleManager.autoHypixel;
                    int bountyColor = WorldRenderer.withAlpha(-662245, 0.75F);
                    boolean hasBounty =
                            autoHypixel.isEnabled()
                                    && autoHypixel.bountyHunterSetting.asBoolean()
                                    && autoHypixel.hasBounty(key);
                    int distPercent =
                            (int)
                                    (Math.min(minecraft.player.getDistanceToEntity(key), 50.0F)
                                            / 50.0F
                                            * 255.0F);
                    int color = hasBounty ? bountyColor : (0xff000000 | (255 - distPercent) << 16 | distPercent << 8);
                    double size = this.sizeSetting.asDouble();
                    Render2D3DUtils.drawArrowColored(
                            -1.5, -this.radiusSetting.asDouble() - 1.5, size, true, color);
                    GL11.glRotated(-f, 0.0, 0.0, 1.0);
                }
            }
        }

        GL11.glTranslatef(-(width / 2.0F), -(height / 2.0F), 0.0F);
    }
}
