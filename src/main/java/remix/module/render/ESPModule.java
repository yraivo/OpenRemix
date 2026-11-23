package remix.module.render;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.renderer.GlStateManager;
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
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.vecmath.Point2d;
import remix.event.MotionPhase;
import remix.event.annotation.Subscribe;
import remix.event.impl.Render2DEvent;
import remix.event.impl.Render3DEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.player.AutoHypixelModule;
import remix.setting.Setting;
import remix.util.entity.RotationHelper;
import remix.util.math.ConvexHull2DHelper;
import remix.util.render.Render2D3DUtils;
import remix.util.render.ScreenProjection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.IdentityHashMap;
import java.util.Collections;

/** ESP module: render events and 13 settings. */
public final class ESPModule extends ModuleBase {

    private Map<PlayerEntity, float[][]> skeletonRotations;

    private Setting animalsSetting;

    private Setting invisiblesSetting;

    private static String[] packedTextFragments;

    private Setting filledSetting;

    private static int unusedBlueColor;

    private Setting armorBarSetting;

    private Setting playersSetting;

    private Setting borderSetting;

    private static int fillColor;

    private Setting neutralSetting;

    public final Setting<ESPMode> modeSetting = new Setting("Mode", this, ESPMode.TWO_D);

    public final Setting labelsSetting = new Setting("Labels", this, false);

    final Map<LivingEntity, double[][]> projectedBoxVertices = new IdentityHashMap<>();
    private final Set<LivingEntity> projectedThisFrame =
            Collections.newSetFromMap(new IdentityHashMap<LivingEntity, Boolean>());
    private final ScreenProjection projection = new ScreenProjection();

    private static int borderColor;

    private static int armorBarColor;

    private Setting skeletalSetting;

    private Setting healthBarsSetting;

    private Setting mobsSetting;

    private Setting healthSetting;

    private ConvexHull2DHelper convexHullBuilder;
    private final List<LivingEntity> drawEntities = new ArrayList<>();
    private final Cylinder cylinder = new Cylinder();

    private static int unattackableEntityColor;





    @Subscribe(mc = Render2DEvent.class)
    public void onRender2D(Render2DEvent event) {
        ESPMode mode = this.modeSetting.getValue();
        if (mode != ESPMode.TWO_D && mode != ESPMode.POLYGON) {
            return;
        }

        double inverseScale = 1.0 / event.getResolution().getScaleFactor();
        GlStateManager.pushMatrix();
        try {
            GlStateManager.scale(inverseScale, inverseScale, inverseScale);
            LocalPlayer player = minecraft.player;
            boolean drawBorder = this.borderSetting.asBoolean();
            boolean drawFill = this.filledSetting.asBoolean();
            boolean drawHealth = this.healthSetting.asBoolean();
            boolean drawArmor = this.armorBarSetting.asBoolean();
            boolean drawHealthSegments = this.healthBarsSetting.asBoolean();
            boolean drawLabels =
                    this.labelsSetting.asBoolean()
                            && !ClientServices.moduleManager.nameTags.isEnabled();
            Map<LivingEntity, double[][]> projected = this.projectedBoxVertices;
            List<LivingEntity> entities = this.drawEntities;
            entities.clear();
            for (LivingEntity entity : projected.keySet()) {
                if (this.shouldRenderEntity(entity)) entities.add(entity);
            }
            entities.sort((first, second) ->
                    -Double.compare(
                            player.getDistanceToEntity(first), player.getDistanceToEntity(second)));

            for (LivingEntity entity : entities) {
                this.drawProjectedEntity(
                        mode,
                        entity,
                        projected.get(entity),
                        drawBorder,
                        drawFill,
                        drawHealth,
                        drawHealthSegments,
                        drawArmor,
                        drawLabels);
            }
        } finally {
            this.drawEntities.clear();
            GlStateManager.popMatrix();
        }
    }

    private void drawProjectedEntity(
            ESPMode mode,
            LivingEntity entity,
            double[][] vertices,
            boolean drawBorder,
            boolean drawFill,
            boolean drawHealth,
            boolean drawHealthSegments,
            boolean drawArmor,
            boolean drawLabels) {
        if (vertices == null || vertices.length == 0) {
            return;
        }
        double left = Double.NaN;
        double top = Double.NaN;
        double right = Double.NaN;
        double bottom = Double.NaN;
        double nearDepth = Double.NaN;
        double farDepth = Double.NaN;
        for (double[] vertex : vertices) {
            left = Math.min(Double.isNaN(left) ? vertex[0] : left, vertex[0]);
            top = Math.min(Double.isNaN(top) ? vertex[1] : top, vertex[1]);
            right = Math.max(Double.isNaN(right) ? vertex[0] : right, vertex[0]);
            bottom = Math.max(Double.isNaN(bottom) ? vertex[1] : bottom, vertex[1]);
            nearDepth = Math.min(Double.isNaN(nearDepth) ? vertex[2] : nearDepth, vertex[2]);
            farDepth = Math.max(Double.isNaN(farDepth) ? vertex[2] : farDepth, vertex[2]);
        }
        if (nearDepth < 0.0 || nearDepth >= 1.0 || farDepth < 0.0 || farDepth >= 1.0) {
            return;
        }

        RemixClient.INSTANCE.getClientServices();
        AutoHypixelModule autoHypixel = ClientServices.moduleManager.autoHypixel;
        int bounty =
                autoHypixel.isEnabled() && autoHypixel.bountyHunterSetting.asBoolean()
                        ? autoHypixel.getBounty(entity)
                        : -1;
        if (drawBorder || drawFill) {
            int bountyColor = WorldRenderer.withAlpha(-662245, 0.75F);
            int outline =
                    bounty != -1 && !drawFill
                            ? bountyColor
                            : ClientServices.moduleManager.aura.isValidTarget(
                                            entity, 1000.0, 180.0, false)
                                    ? -1618884
                                    : unattackableEntityColor;
            int fill = drawFill ? (bounty != -1 && drawBorder ? bountyColor : fillColor) : 0;
            this.drawProjectedShape(mode, vertices, left, top, right, bottom, fill, outline);
        }

        float absorption = entity.getAbsorptionAmount();
        if (drawHealth) {
            drawProjectedHealth(entity, left, top, bottom, absorption, drawHealthSegments);
        }
        if (drawArmor) {
            drawProjectedArmor(entity, right, top, bottom);
        }
        if (drawLabels) {
            drawProjectedLabel(entity, (left + right) / 2.0, top, absorption, bounty);
        }
    }

    private void drawProjectedShape(
            ESPMode mode,
            double[][] vertices,
            double left,
            double top,
            double right,
            double bottom,
            int fill,
            int outline) {
        switch (mode) {
            case TWO_D:
                Render2D3DUtils.drawBorderedRect(
                        left - 1.0, top, right + 1.0, bottom, 3.0, fill, borderColor);
                Render2D3DUtils.drawBorder(left, top + 1.0, right, bottom - 1.0, 1.0, outline);
                break;
            case POLYGON:
                List<Point2d> corners = new ArrayList<>(vertices.length);
                for (double[] vertex : vertices) {
                    corners.add(new Point2d(vertex[0], vertex[1]));
                }
                List<Point2d> hull = this.convexHullBuilder.buildConvexHull(corners);
                double[] x = new double[hull.size()];
                double[] y = new double[hull.size()];
                for (int index = 0; index < hull.size(); index++) {
                    Point2d point = hull.get(index);
                    x[index] = point.getX();
                    y[index] = point.getY();
                }
                Render2D3DUtils.drawFilledPolygon(x, y, fill, outline, 2.0F);
                break;
            default:
                break;
        }
    }

    private static void drawProjectedHealth(
            LivingEntity entity,
            double left,
            double top,
            double bottom,
            float absorption,
            boolean drawSegments) {
        double capacity = entity.getMaxHealth() + absorption;
        double current = Math.min(entity.getHealth() + absorption, capacity);
        double fillHeight = (bottom - top - 2.0) / capacity * current;
        int green = (int) Math.min(255.0 / capacity * current, 255.0);
        int color = new Color(255 - green, green, 0).getRGB();
        Gui.drawRect(left - 6.0, top, left - 3.0, bottom, 2097152000);
        Gui.drawRect(left - 4.0, bottom - fillHeight - 1.0, left - 5.0, bottom - 1.0, color);
        if (drawSegments) {
            for (int segment = 1; segment < 9; segment++) {
                double y = top + segment * ((bottom - top) / 9.0);
                Gui.drawRect(left - 6.0, y - 0.5, left - 3.0, y + 0.5, borderColor);
            }
        }
    }

    private static void drawProjectedArmor(
            LivingEntity entity, double right, double top, double bottom) {
        double armor = entity.getTotalArmorValue();
        if (armor != 0.0) {
            double fillHeight = (bottom - top - 2.0) / 20.0 * armor;
            Gui.drawRect(right + 4.0, top, right + 7.0, bottom, 2097152000);
            Gui.drawRect(right + 5.0, bottom - fillHeight, right + 6.0, bottom, armorBarColor);
        }
    }

    private static void drawProjectedLabel(
            LivingEntity entity, double center, double top, float absorption, int bounty) {
        FontRenderer font = minecraft.fontRendererObj;
        String label =
                String.format(
                        "%s \u00a77[\u00a7f%s \u00a7c\u2764\u00a77]",
                        entity.getName(), Math.round(entity.getHealth() + absorption));
        font.drawString(
                label,
                (int) center - font.getStringWidth(label) / 2,
                (int) top - 13,
                Render2D3DUtils.getTeamColor(entity),
                true);
        if (bounty != -1) {
            String bountyLabel = bounty + packedTextFragments[0];
            font.drawString(
                    bountyLabel,
                    (int) center - font.getStringWidth(bountyLabel) / 2,
                    (int) top - 26,
                    -662245,
                    true);
        }
    }

    public void updateProjectedBoxes() {
        if (minecraft.world == null || minecraft.player == null) {
            this.projectedBoxVertices.clear();
            this.projectedThisFrame.clear();
            return;
        }
        boolean boxes = this.isEnabled() && this.modeSetting.getValue() != ESPMode.CYLINDER;
        NameTagsModule names = ClientServices.moduleManager.nameTags;
        OnScreenRadarModule radar = ClientServices.moduleManager.onScreenRadar;
        this.projectedThisFrame.clear();
        this.projection.capture();
        for (Object object : minecraft.world.loadedEntities) {
            if (!(object instanceof LivingEntity)) continue;
            LivingEntity entity = (LivingEntity) object;
            if (!(boxes && this.shouldRenderEntity(entity))
                    && !(names.isEnabled() && names.shouldRenderEntity(entity))
                    && !(radar.isEnabled() && radar.shouldRenderEntity(entity))) continue;
            Vec3 position = Render2D3DUtils.getInterpolatedPos(entity);
            double minX = position.x - entity.width + 0.15;
            double minY = position.y;
            double minZ = position.z - entity.width + 0.15;
            double maxX = position.x + entity.width - 0.15;
            double maxY = position.y + entity.height + (entity.isSneaking() ? 0.02 : 0.2);
            double maxZ = position.z + entity.width - 0.15;
            double[][] points = this.projectedBoxVertices.get(entity);
            if (points == null) points = new double[8][3];
            boolean projected =
                    this.projection.project(minX, minY, minZ, points[0])
                    && this.projection.project(minX, maxY, minZ, points[1])
                    && this.projection.project(maxX, maxY, minZ, points[2])
                    && this.projection.project(maxX, minY, minZ, points[3])
                    && this.projection.project(maxX, maxY, maxZ, points[4])
                    && this.projection.project(minX, maxY, maxZ, points[5])
                    && this.projection.project(minX, minY, maxZ, points[6])
                    && this.projection.project(maxX, minY, maxZ, points[7]);
            if (projected) {
                this.projectedBoxVertices.put(entity, points);
                this.projectedThisFrame.add(entity);
            }
        }
        this.projectedBoxVertices.keySet().retainAll(this.projectedThisFrame);
    }

    @Subscribe(mc = Render3DEvent.class)
    public void onRender3D(Render3DEvent event) {

        if (event.getPhase() == MotionPhase.PRE) {

            ClientWorld world = minecraft.world;

            if (this.skeletalSetting.asBoolean()) {

                GlStateManager.pushMatrix();

                GlStateManager.enableBlend();

                GL11.glEnable(2848);

                GlStateManager.disableDepth();

                GlStateManager.disableTexture2D();

                GlStateManager.blendFunc(770, 771);

                GL11.glHint(3154, 4354);

                GL11.glEnable(2903);

                GL11.glEnable(2848);

                GL11.glHint(3154, 4354);

                GlStateManager.depthMask(false);

                boolean invisibles = this.invisiblesSetting.asBoolean();

                List playerEntities = world.playerEntities;

                Map map = this.skeletonRotations;

                Set set = map.keySet();

                set.removeIf(
                        ((java.util.function.Predicate<net.minecraft.entity.player.PlayerEntity>)
                                (playerEntity -> !playerEntities.contains(playerEntity))));

                for (java.util.Iterator<?> __recoveryIterator171_15996 = (set).iterator();
                        (__recoveryIterator171_15996).hasNext(); ) {

                    PlayerEntity playerEntityValue =
                            (PlayerEntity) (__recoveryIterator171_15996).next();

                    if (!playerEntityValue.isDead && playerEntityValue != minecraft.player) {

                        float[][] afloat = (float[][]) map.get(playerEntityValue);

                        this.drawSkeleton(playerEntityValue, invisibles, afloat);
                    }
                }

                GlStateManager.disableBlend();

                GlStateManager.enableTexture2D();

                GL11.glDisable(2848);

                GlStateManager.enableDepth();

                GlStateManager.popMatrix();

                GlStateManager.depthMask(true);
            }

            if (this.modeSetting.getValue() == ESPMode.CYLINDER) {

                Render2D3DUtils.beginUnoccludedWorldOverlay();

                Render2D3DUtils.enablePrimitiveSmoothing();

                LocalPlayer player = minecraft.player;

                boolean enabled = ClientServices.moduleManager.aura.isEnabled();

                List loadedEntities = world.loadedEntities;

                int i = 0;

                int j = loadedEntities.size();

                while (i < j) {

                    Entity entity = (Entity) loadedEntities.get(i);

                    if (this.shouldRenderEntity(entity)) {

                        LivingEntity livingEntity = (LivingEntity) entity;

                        Vec3 vec3 = Render2D3DUtils.getInterpolatedPos(entity);

                        if (ClientServices.friendManager.isFriend(entity.getName())) {

                            GlStateManager.color(0.36078432F, 0.7490196F, 1.0F, 1.0F);

                        } else {

                            float distanceToEntity = player.getDistanceToEntity(entity);

                            if (distanceToEntity >= 42.0F || enabled) {

                                GlStateManager.color(0.0F, 1.0F, 0.0F);

                            } else {

                                GlStateManager.color(1.0F, distanceToEntity / 42.0F, 0.0F, 1.0F);
                            }
                        }

                        if (livingEntity.hurtTime > 0) {

                            GlStateManager.color(1.0F, 0.33333334F, 0.0F, 0.4F);
                        }

                        GlStateManager.pushMatrix();

                        GlStateManager.translate(vec3.x, vec3.y, vec3.z);

                        Cylinder cylinder = this.cylinder;

                        GlStateManager.glLineWidth(1.0F);

                        GL11.glRotatef(90.0F, -90.0F, 0.0F, 1.0F);

                        float width = livingEntity.width;

                        cylinder.draw(width, width, entity.height + 0.2F, 20, 0);

                        GlStateManager.popMatrix();
                    }

                    i++;
                }

                Render2D3DUtils.disablePrimitiveSmoothing();

                Render2D3DUtils.endUnoccludedWorldOverlay();
            }
        }
    }

    public ESPModule() {

        super("ESP", new ModuleDefaults(ModuleCategory.RENDER).visible(false));

        this.cylinder.setDrawStyle(100013);
        this.borderSetting = new Setting("Border", this, true);

        this.healthSetting = new Setting("Health", this, true);

        this.healthBarsSetting = new Setting("HealthBars", this, false);

        this.armorBarSetting = new Setting("ArmorBar", this, false);

        this.skeletalSetting = new Setting("Skeletal", this, true);

        this.filledSetting = new Setting("Filled", this, false);

        this.playersSetting = new Setting("Players", this, true);

        this.mobsSetting = new Setting("Mobs", this, false);

        this.animalsSetting = new Setting("Animals", this, false);

        this.neutralSetting = new Setting("Neutral", this, false);

        this.invisiblesSetting = new Setting("Invisibles", this, false);

        this.skeletonRotations = new HashMap<>();

        this.convexHullBuilder = new ConvexHull2DHelper();
    }

    public boolean shouldRenderEntity(Entity entity) {

        return entity != minecraft.player
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

    private void drawSkeleton(PlayerEntity player, boolean renderInvisible, float[][] rotations) {

        LocalPlayer playerValue = minecraft.player;

        if (rotations != null
                && !player.isPlayerSleeping()
                && (renderInvisible || !playerValue.isInvisible())) {

            Vec3 vec3 = Render2D3DUtils.getInterpolatedPos(player);

            double d0 = vec3.x;

            double d1 = vec3.y;

            double d2 = vec3.z;

            float f1 =
                    player.prevRenderYawOffset
                            + (player.renderYawOffset - player.prevRenderYawOffset)
                                    * minecraft.timer.renderPartialTicks;

            float f2 = player.isSneaking() ? 0.5F : 0.75F;

            GlStateManager.pushMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            GL11.glLineWidth(1.0F);

            GlStateManager.translate(d0, d1, d2);

            GL11.glRotatef(-f1, 0.0F, 1.0F, 0.0F);

            GlStateManager.translate(0.0, 0.0, player.isSneaking() ? -0.235 : 0.0);

            GlStateManager.pushMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.translate(-0.125, f2, 0.0);

            if (rotations[3][0] != 0.0F) {

                GL11.glRotatef(rotations[3][0] * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            }

            if (rotations[3][1] != 0.0F) {

                GL11.glRotatef(rotations[3][1] * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (rotations[3][2] != 0.0F) {

                GL11.glRotatef(rotations[3][2] * (180.0F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.ab(3);

            GL11.glVertex3d(0.0, 0.0, 0.0);

            GL11.glVertex3d(0.0, player.isSneaking() ? -0.4 : -0.6, 0.0);

            GlStateManager.mO();

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.translate(0.125, f2, 0.0);

            if (rotations[4][0] != 0.0F) {

                GL11.glRotatef(rotations[4][0] * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            }

            if (rotations[4][1] != 0.0F) {

                GL11.glRotatef(rotations[4][1] * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (rotations[4][2] != 0.0F) {

                GL11.glRotatef(rotations[4][2] * (180.0F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.ab(3);

            GL11.glVertex3d(0.0, 0.0, 0.0);

            GL11.glVertex3d(0.0, player.isSneaking() ? -0.45 : -0.65, 0.0);

            GlStateManager.mO();

            GlStateManager.popMatrix();

            GlStateManager.translate(0.0, 0.0, player.isSneaking() ? 0.25 : 0.0);

            GlStateManager.pushMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.translate(
                    0.0, player.isSneaking() ? -0.042 : 0.0, player.isSneaking() ? -0.06 : 0.0);

            GlStateManager.pushMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.translate(-0.375, f2 + 0.55, 0.0);

            if (rotations[1][0] != 0.0F) {

                GL11.glRotatef(rotations[1][0] * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            }

            if (rotations[1][1] != 0.0F) {

                GL11.glRotatef(rotations[1][1] * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (rotations[1][2] != 0.0F) {

                GL11.glRotatef(-rotations[1][2] * (180.0F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.ab(3);

            GL11.glVertex3d(0.0, 0.0, 0.0);

            GL11.glVertex3d(0.0, -0.5, 0.0);

            GlStateManager.mO();

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();

            GlStateManager.translate(0.375, f2 + 0.55, 0.0);

            if (rotations[2][0] != 0.0F) {

                GL11.glRotatef(rotations[2][0] * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            }

            if (rotations[2][1] != 0.0F) {

                GL11.glRotatef(rotations[2][1] * (180.0F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
            }

            if (rotations[2][2] != 0.0F) {

                GL11.glRotatef(-rotations[2][2] * (180.0F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
            }

            GlStateManager.ab(3);

            GL11.glVertex3d(0.0, 0.0, 0.0);

            GL11.glVertex3d(0.0, -0.5, 0.0);

            GlStateManager.mO();

            GlStateManager.popMatrix();

            GL11.glRotatef(f1 - player.rotationYawHead, 0.0F, 1.0F, 0.0F);

            GlStateManager.pushMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.translate(0.0, f2 + 0.55, 0.0);

            if (rotations[0][0] != 0.0F) {

                GL11.glRotatef(rotations[0][0] * (180.0F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
            }

            GlStateManager.ab(3);

            GL11.glVertex3d(0.0, 0.0, 0.0);

            GL11.glVertex3d(0.0, 0.3, 0.0);

            GlStateManager.mO();

            GlStateManager.popMatrix();

            GlStateManager.popMatrix();

            GL11.glRotatef(player.isSneaking() ? 25.0F : 0.0F, 1.0F, 0.0F, 0.0F);

            GlStateManager.translate(
                    0.0,
                    player.isSneaking() ? -0.16175 : 0.0,
                    player.isSneaking() ? -0.48025 : 0.0);

            GlStateManager.pushMatrix();

            GlStateManager.translate(0.0, f2, 0.0);

            GlStateManager.ab(3);

            GL11.glVertex3d(-0.125, 0.0, 0.0);

            GL11.glVertex3d(0.125, 0.0, 0.0);

            GlStateManager.mO();

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

            GlStateManager.translate(0.0, f2, 0.0);

            GlStateManager.ab(3);

            GL11.glVertex3d(0.0, 0.0, 0.0);

            GL11.glVertex3d(0.0, 0.55, 0.0);

            GlStateManager.mO();

            GlStateManager.popMatrix();

            GlStateManager.pushMatrix();

            GlStateManager.translate(0.0, f2 + 0.55, 0.0);

            GlStateManager.ab(3);

            GL11.glVertex3d(-0.375, 0.0, 0.0);

            GL11.glVertex3d(0.375, 0.0, 0.0);

            GlStateManager.mO();

            GlStateManager.popMatrix();

            GlStateManager.popMatrix();

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }


    // region Static initialization and string tables

    static {
        remix.module.render.ESPModule.packedTextFragments = new String[3];

        remix.module.render.ESPModule.packedTextFragments[0] = new String("g");

        fillColor = new Color(0, 0, 0, 125).getRGB();

        unattackableEntityColor = new Color(31232).getRGB();

        armorBarColor = new Color(33, 150, 200).getRGB();

        unusedBlueColor = Color.BLUE.brighter().getRGB();

        borderColor = Color.BLACK.getRGB();
    }

    // endregion

    public void captureModelRotations(PlayerEntity player, ModelBiped model) {

        ModelRenderer aut = model.bipedHead;

        ModelRenderer aup = model.bipedRightArm;

        ModelRenderer auo = model.bipedLeftArm;

        ModelRenderer auq = model.bipedRightLeg;

        ModelRenderer aus = model.bipedLeftLeg;

        float[][] rotations = this.skeletonRotations.get(player);
        if (rotations == null) {
            rotations = new float[5][3];
            this.skeletonRotations.put(player, rotations);
        }
        copyRotation(rotations[0], aut);
        copyRotation(rotations[1], aup);
        copyRotation(rotations[2], auo);
        copyRotation(rotations[3], auq);
        copyRotation(rotations[4], aus);
    }
    private static void copyRotation(float[] rotation, ModelRenderer model) {
        rotation[0] = model.rotateAngleX;
        rotation[1] = model.rotateAngleY;
        rotation[2] = model.rotateAngleZ;
    }

    @Override
    public void onDisable() {
        this.drawEntities.clear();
        this.skeletonRotations.clear();
        super.onDisable();
    }
}
