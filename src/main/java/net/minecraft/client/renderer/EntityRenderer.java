package net.minecraft.client.renderer;

import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.MapItemRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MouseFilter;
import net.minecraft.util.MouseHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldSettingsGameType;
import net.minecraft.world.biome.BiomeGenBase;

import optifine.CustomColors;
import optifine.Lagometer;
import optifine.OptiFineConfig;
import optifine.RandomMobs;
import optifine.Reflector;
import optifine.ReflectorForge;
import optifine.TextureUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Project;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.event.EventBus;
import remix.event.MotionPhase;
import remix.event.impl.CameraRenderEvent;
import remix.event.impl.CameraRotationEvent;
import remix.event.impl.CameraTransformEvent;
import remix.event.impl.Render3DEvent;
import remix.module.ModuleManager;
import remix.module.combat.ExtendedReachModule;
import remix.module.render.CameraModule;
import remix.module.render.CaveFinderModule;
import remix.module.render.ESPMode;
import remix.module.render.ESPModule;
import remix.module.render.NameTagsModule;
import remix.module.render.OnScreenRadarModule;
import remix.module.render.WireFrameModule;
import remix.ui.screen.RemixMainMenuScreen;

import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public final class EntityRenderer implements IResourceManagerReloadListener {
    private float farPlaneDistance;
    public static int anaglyphPassIndex;
    private float torchFlicker;
    private IResourceManager resourceManager;
    private int debugViewDirection;
    private float lastMousePartialTicks;
    private MapItemRenderer theMapItemRenderer;
    private float fogRenderDistance;
    private int serverPacingWaitMillis;
    private float thirdPersonDistance;
    private MouseFilter mouseFilterXAxis;
    private float bossColorModifier;
    private boolean useShader;
    private ShaderGroup[] fxaaShaders;
    private Entity pointedEntity;
    private int shaderIndex;
    private long lastFrameRenderNanos;
    private float fovModifier;
    private float previousBossColorModifier;
    private boolean renderHand;
    private long lastServerPacingSampleMillis;
    private MinecraftClient mc;
    private long lastErrorCheckTimeMs;
    private DynamicTexture lightmapTexture;
    public float fogColorRed;
    public static int shaderCount;
    private int rainSoundDelayCounter;
    public boolean lightmapNeedsUpdate;
    private float previousFogBrightness;
    public ShaderGroup theShaderGroup;
    private float fogBrightness;
    private float[] rainYCoords;
    public float fogColorGreen;
    public boolean terrainFogActive = false;
    private static Logger logger;
    private int lastServerPacingTick;
    private MouseFilter mouseFilterYAxis;
    private double cameraZoomOffsetY;
    private long prevFrameTime;
    private float smoothedMouseYaw;
    private boolean debugView;
    private FloatBuffer fogColorBuffer;
    private World updatedWorld;
    private double cameraZoom;
    private boolean cloudFog;
    private float smoothedMousePitch;
    private double cameraZoomOffsetX;
    private boolean amn;
    private float previousFovModifier;
    private int[] lightmapColors;
    private static ResourceLocation[] shaderResourceLocations;
    private ResourceLocation locationLightMap;
    private static ResourceLocation locationRainPng;
    private float serverPacingSampleTicks;
    private float pendingMouseYaw;

    private static String[] packedTextFragments;
    private float serverPacingSampleIntervalMillis;
    private static ResourceLocation locationSnowPng;
    private Random random = new Random();
    private float[] rainXCoords;
    private int rendererUpdateCount;
    private int cameraLightmapHueStep;
    public int frameCount;
    public ItemRenderer itemRenderer;
    private float torchFlickerVelocity;
    private int lastServerPacingWaitMillis;
    public static boolean anaglyphEnable;
    private float thirdPersonDistanceTemp;
    public float fogColorBlue;

    private boolean drawBlockOutline;
    private boolean optifineListenerRegistered;
    private float pendingMousePitch;

    private void addRainParticles() {
        boolean firstSampleOnly = WorldRenderer.getLegacyRenderFlowToken() != 0;
        float rainStrength = this.mc.world.getRainStrength(1.0F);
        if (!OptiFineConfig.isRainFancy()) {
            rainStrength /= 2.0F;
        }
        if (rainStrength == 0.0F || !OptiFineConfig.isRainSplash()) {
            return;
        }

        this.random.setSeed(this.rendererUpdateCount * 312987231L);
        Entity viewEntity = this.mc.getRenderViewEntity();
        ClientWorld world = this.mc.world;
        BlockPos viewPosition = new BlockPos(viewEntity);
        int radius = 10;
        int attempts = (int) (100.0F * rainStrength * rainStrength);
        if (this.mc.gameSettings.particleSetting == 1) {
            attempts >>= 1;
        } else if (this.mc.gameSettings.particleSetting == 2) {
            attempts = 0;
        }

        double soundX = 0.0;
        double soundY = 0.0;
        double soundZ = 0.0;
        int splashCount = 0;
        for (int sample = 0; sample < attempts; sample++) {
            BlockPos surface =
                    world.getPrecipitationHeight(
                            viewPosition.add(
                                    this.random.nextInt(radius) - this.random.nextInt(radius),
                                    0,
                                    this.random.nextInt(radius) - this.random.nextInt(radius)));
            BiomeGenBase biome = world.getBiomeGenForCoords(surface);
            BlockPos blockPosition = surface.offsetDown();
            Block block = world.getBlockState(blockPosition).getBlock();
            if (surface.getY() <= viewPosition.getY() + radius
                    && surface.getY() >= viewPosition.getY() - radius
                    && biome.canRain()
                    && biome.getFloatTemperature(surface) >= 0.15F) {
                float offsetX = this.random.nextFloat();
                float offsetZ = this.random.nextFloat();
                boolean lava = block.getMaterial() == Material.lava;
                if (lava) {
                    this.mc.world.spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL,
                            surface.getX() + offsetX,
                            surface.getY() + 0.1F - block.getBlockBoundsMinY(),
                            surface.getZ() + offsetZ,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                }
                if ((!lava || firstSampleOnly) && block.getMaterial() != Material.AIR) {
                    block.setBlockBoundsBasedOnState(world, blockPosition);
                    if (this.random.nextInt(++splashCount) == 0) {
                        soundX = blockPosition.getX() + offsetX;
                        soundY = blockPosition.getY() + 0.1F + block.getBlockBoundsMaxY() - 1.0;
                        soundZ = blockPosition.getZ() + offsetZ;
                    }
                    this.mc.world.spawnParticle(
                            EnumParticleTypes.WATER_DROP,
                            blockPosition.getX() + offsetX,
                            blockPosition.getY() + 0.1F + block.getBlockBoundsMaxY(),
                            blockPosition.getZ() + offsetZ,
                            0.0,
                            0.0,
                            0.0,
                            new int[0]);
                }
            }
            if (firstSampleOnly) {
                break;
            }
        }

        if (splashCount == 0 || this.random.nextInt(3) >= this.rainSoundDelayCounter++) {
            return;
        }
        this.rainSoundDelayCounter = 0;
        boolean rainAboveView =
                soundY > viewPosition.getY() + 1
                        && world.getPrecipitationHeight(viewPosition).getY()
                                > MathHelper.floor(viewPosition.getY());
        if (rainAboveView) {
            this.mc.world.playSound(
                    soundX, soundY, soundZ, "ambient.weather.rain", 0.1F, 0.5F, false);
        }
        if (!rainAboveView || firstSampleOnly) {
            this.mc.world.playSound(
                    soundX, soundY, soundZ, "ambient.weather.rain", 0.2F, 1.0F, false);
        }
    }

    private void updateTorchFlicker() {
        this.torchFlickerVelocity =
                (float)
                        (this.torchFlickerVelocity
                                + (Math.random() - Math.random()) * Math.random() * Math.random());
        this.torchFlickerVelocity = (float) (this.torchFlickerVelocity * 0.9);
        this.torchFlicker =
                this.torchFlicker + (this.torchFlickerVelocity - this.torchFlicker) * 1.0F;
        this.lightmapNeedsUpdate = true;
    }

    public void renderHand(float partialTicks, int renderPass) {
        boolean legacyHandFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        if (this.debugView) {
            return;
        }

        GameSettings settings = this.mc.gameSettings;
        this.setupHandProjection(partialTicks, renderPass, settings);
        boolean sleeping = false;
        if (!OptiFineConfig.isShaders() || !Shaders.isHandRenderedMain) {
            GlStateManager.pushMatrix();
            this.hurtCameraEffect(partialTicks);
            if (settings.viewBobbing) {
                this.setupViewBobbing(partialTicks);
            }

            Entity viewEntity = this.mc.getRenderViewEntity();
            sleeping =
                    viewEntity instanceof LivingEntity
                            && ((LivingEntity) viewEntity).isPlayerSleeping();
            if (this.shouldRenderHeldItem(settings, sleeping)) {
                this.renderFirstPersonHand(partialTicks, legacyHandFlow);
            }
            GlStateManager.popMatrix();
        }

        if (OptiFineConfig.isShaders() && !Shaders.isCompositeRendered) {
            return;
        }
        this.disableLightmap();
        if (settings.thirdPersonView == 0 && !sleeping) {
            this.itemRenderer.renderOverlays(partialTicks);
            this.hurtCameraEffect(partialTicks);
        }
        if (settings.viewBobbing) {
            this.setupViewBobbing(partialTicks);
        }
    }

    private void setupHandProjection(float partialTicks, int renderPass, GameSettings settings) {
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        if (settings.anaglyph) {
            GlStateManager.translate(-(renderPass * 2 - 1) * 0.07F, 0.0F, 0.0F);
        }
        if (OptiFineConfig.isShaders()) {
            Shaders.applyHandDepthScale();
        }
        Project.gluPerspective(
                this.getFOVModifier(partialTicks, false),
                (float) this.mc.displayWidth / this.mc.displayHeight,
                0.05F,
                this.fogRenderDistance * 2.0F);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        if (settings.anaglyph) {
            GlStateManager.translate((renderPass * 2 - 1) * 0.1F, 0.0F, 0.0F);
        }
    }

    private boolean shouldRenderHeldItem(GameSettings settings, boolean sleeping) {
        RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        return (!camera.isEnabled() || !camera.noHandSetting.asBoolean())
                && settings.thirdPersonView == 0
                && !sleeping
                && !settings.hideGUI
                && !this.mc.playerController.usesSpectatorHud();
    }

    private void renderFirstPersonHand(float partialTicks, boolean legacyHandFlow) {
        this.enableLightmap();
        boolean shaders = OptiFineConfig.isShaders();
        if (shaders) {
            ShadersRender.renderHandWithShaders(this.itemRenderer, partialTicks);
        }
        if (!shaders || legacyHandFlow) {
            this.itemRenderer.renderItemInFirstPerson(partialTicks);
        }
        this.disableLightmap();
    }

    public void getMouseOver(float partialTicks) {
        boolean legacyPickingFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        Entity camera = this.mc.getRenderViewEntity();
        if (camera == null || this.mc.world == null) {
            return;
        }

        this.mc.mcProfiler.startSection("pick");
        this.mc.pointedEntity = null;
        double blockReach = this.mc.playerController.getBlockReachDistance();
        this.mc.objectMouseOver = camera.rayTrace(blockReach, partialTicks);
        Vec3 eyes = camera.getPositionEyes(partialTicks);
        RemixClient.INSTANCE.getClientServices();
        ExtendedReachModule extendedReach = ClientServices.moduleManager.extendedReach;
        boolean extendedControllerReach = this.mc.playerController.extendedReach();
        double entityReach =
                extendedControllerReach
                        ? (legacyPickingFlow ? 3.0 : 6.0)
                        : (blockReach > 3.0 ? 3.0 : blockReach);
        double blockHitDistance =
                this.mc.objectMouseOver == null
                        ? entityReach
                        : this.mc.objectMouseOver.hitVec.distanceTo(eyes);
        if (extendedReach.isEnabled()) {
            // Remix applies its configured distance to both the ray and the selection threshold.
            entityReach = blockHitDistance = extendedReach.reachSetting.asDouble();
        }

        Vec3 look = camera.getLook(partialTicks);
        double rayX = look.x * entityReach;
        double rayY = look.y * entityReach;
        double rayZ = look.z * entityReach;
        Vec3 rayEnd = eyes.addVector(rayX, rayY, rayZ);
        BoundingBox searchBox =
                camera.getEntityBoundingBox().addCoord(rayX, rayY, rayZ).expand(1.0, 1.0, 1.0);
        this.pointedEntity = null;
        Vec3 closestHit = null;
        double closestDistance = blockHitDistance;

        for (Object entry : this.mc.world.getEntitiesWithinAABBExcludingEntity(camera, searchBox)) {
            Entity candidate = (Entity) entry;
            if (candidate.canBeCollidedWith()) {
                float border = candidate.getCollisionBorderSize();
                BoundingBox box = candidate.getEntityBoundingBox().expand(border, border, border);
                RayTraceResult intersection = box.calculateIntercept(eyes, rayEnd);
                boolean checkIntersection = true;
                if (box.isVecInside(eyes)) {
                    // The accepted build retains this state read, not execution of a security
                    // check.
                    boolean insidePickEnabled =
                            RemixClient.INSTANCE
                                            .getSecurityManagerRegistry()
                                            .getPrimaryCheckRunner()
                                            .getPreviousCheckBatch()
                                    != null;
                    if (insidePickEnabled && closestDistance >= 0.0) {
                        this.pointedEntity = candidate;
                        closestHit = intersection == null ? eyes : intersection.hitVec;
                        closestDistance = 0.0;
                        checkIntersection = legacyPickingFlow;
                    } else {
                        checkIntersection = false;
                    }
                }

                if (checkIntersection && intersection != null) {
                    double distance = eyes.distanceTo(intersection.hitVec);
                    if (distance < closestDistance || closestDistance == 0.0) {
                        boolean riderCanInteract =
                                Reflector.forgeCanRiderInteract.exists()
                                        && Reflector.callBoolean(
                                                candidate,
                                                Reflector.forgeCanRiderInteract,
                                                new Object[0]);
                        boolean isRestrictedMount =
                                candidate == camera.ridingEntity && !riderCanInteract;
                        if (!isRestrictedMount || closestDistance == 0.0) {
                            this.pointedEntity = candidate;
                            closestHit = intersection.hitVec;
                            if (!isRestrictedMount || legacyPickingFlow) {
                                closestDistance = distance;
                            }
                        }
                    }
                }
            }
            if (legacyPickingFlow) {
                break;
            }
        }

        if (this.pointedEntity != null
                && (closestDistance < blockHitDistance || this.mc.objectMouseOver == null)) {
            this.mc.objectMouseOver = new RayTraceResult(this.pointedEntity, closestHit);
            if (this.pointedEntity instanceof LivingEntity
                    || this.pointedEntity instanceof EntityItemFrame) {
                this.mc.pointedEntity = this.pointedEntity;
            }
        }
        this.mc.mcProfiler.endSection();
    }

    public void stopUseShader() {
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }

        this.theShaderGroup = null;
        this.shaderIndex = net.minecraft.client.renderer.EntityRenderer.shaderCount;
    }

    private void renderWorldPass(int renderPass, float partialTicks, long chunkUploadDeadline) {
        boolean exclusiveRenderStages = WorldRenderer.getLegacyRenderFlowMarker() != 0;
        boolean shaders = OptiFineConfig.isShaders();
        if (shaders) {
            Shaders.beginRenderPass(renderPass, partialTicks, chunkUploadDeadline);
        }

        RenderGlobal renderGlobal = this.mc.renderGlobal;
        EffectRenderer effectRenderer = this.mc.effectRenderer;
        boolean drawSelectionOutline = this.shouldDrawBlockOutline();
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("clear");
        if (shaders) {
            Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        }
        if (!shaders || !exclusiveRenderStages) {
            GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        }

        this.updateFogColor(partialTicks);
        GlStateManager.clear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        if (shaders) {
            Shaders.clearRenderBuffers();
        }

        this.mc.mcProfiler.endStartSection("camera");
        this.setupCameraTransform(partialTicks, renderPass);
        if (shaders) {
            Shaders.setCamera(partialTicks);
        }

        ActiveRenderInfo.updateRenderInfo(
                this.mc.player, this.mc.gameSettings.thirdPersonView == 2);
        this.updateWorldOverlayProjections();

        this.mc.mcProfiler.endStartSection("frustum");
        ClippingHelperImpl.getInstance();
        this.mc.mcProfiler.endStartSection("culling");
        Frustrum frustum = new Frustrum();
        Entity viewEntity = this.mc.getRenderViewEntity();
        positionWorldFrustum(frustum, viewEntity, partialTicks, shaders, exclusiveRenderStages);

        boolean renderSky =
                (OptiFineConfig.isSkyEnabled()
                                || OptiFineConfig.isSunMoonEnabled()
                                || OptiFineConfig.isStarsEnabled())
                        && !Shaders.isShadowPass;
        if (renderSky) {
            this.setupFog(-1, partialTicks);
            this.mc.mcProfiler.endStartSection("sky");
            this.resetSkyProjection(partialTicks);
            if (shaders) {
                Shaders.beginSky();
            }
            renderGlobal.renderSky(partialTicks, renderPass);
            if (shaders) {
                Shaders.endSky();
            }
            this.resetSkyProjection(partialTicks);
        }
        if (!renderSky || !exclusiveRenderStages) {
            GlStateManager.disableBlend();
        }

        this.setupFog(0, partialTicks);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        if (viewEntity.posY + viewEntity.getEyeHeight()
                < 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0F) {
            this.renderCloudsCheck(renderGlobal, partialTicks, renderPass);
        }

        this.mc.mcProfiler.endStartSection("prepareterrain");
        this.setupFog(0, partialTicks);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        RenderHelper.disableStandardItemLighting();
        this.mc.mcProfiler.endStartSection("terrain_setup");
        if (shaders) {
            ShadersRender.setupTerrain(
                    renderGlobal,
                    viewEntity,
                    partialTicks,
                    frustum,
                    this.frameCount++,
                    this.mc.player.isSpectator());
        }
        if (!shaders || !exclusiveRenderStages) {
            renderGlobal.setupTerrain(
                    viewEntity,
                    partialTicks,
                    frustum,
                    this.frameCount++,
                    this.mc.player.isSpectator());
        }

        if (renderPass == 0 || renderPass == 2) {
            this.mc.mcProfiler.endStartSection("updatechunks");
            Lagometer.timerChunkUpload.start();
            if (shaders) {
                ShadersRender.updateShaderChunks(renderGlobal, chunkUploadDeadline);
            }
            if (!shaders || !exclusiveRenderStages) {
                this.mc.renderGlobal.updateChunks(chunkUploadDeadline);
            }
            Lagometer.timerChunkUpload.end();
        }

        this.renderOpaqueTerrainLayers(renderGlobal, viewEntity, partialTicks, renderPass, shaders);

        if (!this.debugView) {
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {0});
            }

            renderGlobal.renderEntities(viewEntity, frustum, partialTicks);
            if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
                Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {-1});
            }

            RenderHelper.disableStandardItemLighting();
            this.disableLightmap();
            GlStateManager.matrixMode(GL11.GL_MODELVIEW);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            if (this.mc.objectMouseOver != null
                    && viewEntity.isInsideOfMaterial(Material.water)
                    && drawSelectionOutline) {
                this.renderSelectionOutline(renderGlobal, (PlayerEntity) viewEntity, partialTicks);
            }
        }

        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();
        if (drawSelectionOutline
                && this.mc.objectMouseOver != null
                && !viewEntity.isInsideOfMaterial(Material.water)) {
            this.renderSelectionOutline(renderGlobal, (PlayerEntity) viewEntity, partialTicks);
        }

        if (!renderGlobal.damagedBlocks.isEmpty()) {
            this.mc.mcProfiler.endStartSection("destroyProgress");
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            renderGlobal.drawBlockDamageTexture(
                    Tessellator.getInstance(),
                    Tessellator.getInstance().getWorldRenderer(),
                    viewEntity,
                    partialTicks);
            GlStateManager.disableBlend();
        }

        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableBlend();
        if (!this.debugView) {
            this.enableLightmap();
            this.mc.mcProfiler.endStartSection("litParticles");
            if (shaders) {
                Shaders.nR();
            }

            effectRenderer.renderLitParticles(viewEntity, partialTicks);
            RenderHelper.disableStandardItemLighting();
            this.setupFog(0, partialTicks);
            this.mc.mcProfiler.endStartSection("particles");
            if (shaders) {
                Shaders.beginParticles();
            }

            effectRenderer.renderParticles(viewEntity, partialTicks);
            if (shaders) {
                Shaders.nv();
            }

            this.disableLightmap();
        }

        GlStateManager.depthMask(false);
        GlStateManager.enableCull();
        this.mc.mcProfiler.endStartSection("weather");
        if (shaders) {
            Shaders.beginWeather();
        }

        this.renderRainSnow(partialTicks);
        if (shaders) {
            Shaders.endWeather();
        }

        GlStateManager.depthMask(true);
        renderGlobal.renderWorldBorder(viewEntity, partialTicks);
        if (shaders) {
            ShadersRender.renderOpaqueHand(this, partialTicks, renderPass);
            Shaders.preWater();
        }

        this.renderTranslucentTerrain(
                renderGlobal, viewEntity, partialTicks, renderPass, shaders, exclusiveRenderStages);

        if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
            RenderHelper.enableStandardItemLighting();
            this.mc.mcProfiler.endStartSection("entities");
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {1});
            this.mc.renderGlobal.renderEntities(viewEntity, frustum, partialTicks);
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] {-1});
            RenderHelper.disableStandardItemLighting();
        }

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.disableFog();
        if (viewEntity.posY + viewEntity.getEyeHeight()
                >= 128.0 + this.mc.gameSettings.ofCloudsHeight * 128.0F) {
            this.mc.mcProfiler.endStartSection("aboveClouds");
            this.renderCloudsCheck(renderGlobal, partialTicks, renderPass);
        }

        if (Reflector.forgeDispatchRenderLast.exists()) {
            this.mc.mcProfiler.endStartSection("forge_render_last");
            Reflector.callVoid(
                    Reflector.forgeDispatchRenderLast, new Object[] {renderGlobal, partialTicks});
        }

        this.mc.mcProfiler.endStartSection("hand");
        boolean forgeHandledHand =
                Reflector.callBoolean(
                        Reflector.forgeRenderFirstPersonHand,
                        new Object[] {this.mc.renderGlobal, partialTicks, renderPass});
        if (!forgeHandledHand && this.renderHand && !Shaders.isShadowPass) {
            if (shaders) {
                ShadersRender.renderHand1(this, partialTicks, renderPass);
                Shaders.mS();
            }

            this.renderWorldOverlayAndHand(
                    partialTicks, renderPass, shaders, exclusiveRenderStages);

            this.renderWorldDirections(partialTicks);
        }

        if (shaders) {
            Shaders.ny();
        }
    }

    private void updateWorldOverlayProjections() {
        RemixClient.INSTANCE.getClientServices();
        ModuleManager moduleManager = ClientServices.moduleManager;
        ESPModule esp = moduleManager.esp;
        OnScreenRadarModule onScreenRadar = moduleManager.onScreenRadar;
        NameTagsModule nameTags = moduleManager.nameTags;
        if ((esp.isEnabled() && esp.modeSetting.getValue() != ESPMode.CYLINDER)
                || onScreenRadar.isEnabled()
                || nameTags.isEnabled()) {
            esp.updateProjectedBoxes();
        }
    }

    private static void positionWorldFrustum(
            Frustrum frustum,
            Entity viewEntity,
            float partialTicks,
            boolean shaders,
            boolean exclusiveRenderStages) {
        double viewX =
                viewEntity.lastTickPosX
                        + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
        double viewY =
                viewEntity.lastTickPosY
                        + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
        double viewZ =
                viewEntity.lastTickPosZ
                        + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
        if (shaders) {
            ShadersRender.setShaderFrustumPosition(frustum, viewX, viewY, viewZ);
        }
        if (!shaders || !exclusiveRenderStages) {
            frustum.setPosition(viewX, viewY, viewZ);
        }
    }

    // The modelview matrix pushed here also covers the following entity pass.
    private void renderOpaqueTerrainLayers(
            RenderGlobal renderGlobal,
            Entity viewEntity,
            float partialTicks,
            int renderPass,
            boolean shaders) {
        this.mc.mcProfiler.endStartSection("terrain");
        RemixClient.INSTANCE.getClientServices();
        WireFrameModule wireFrame = ClientServices.moduleManager.wireFrame;
        if (wireFrame.isEnabled()) {
            GL11.glLineWidth(2.0F);
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }

        Lagometer.timerTerrain.start();
        if (this.mc.gameSettings.ofSmoothFps && renderPass > 0) {
            this.mc.mcProfiler.endStartSection("finish");
            GL11.glFinish();
            this.mc.mcProfiler.endStartSection("terrain");
        }

        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.pushMatrix();
        GlStateManager.disableAlpha();
        if (shaders) {
            ShadersRender.beginTerrainSolid();
        }

        RemixClient.INSTANCE.getClientServices();
        CaveFinderModule caveFinder = ClientServices.moduleManager.caveFinder;
        if (caveFinder.isEnabled()) {
            caveFinder.cullFrontFaces();
        }

        renderGlobal.renderBlockLayer(
                EnumWorldBlockLayer.SOLID, partialTicks, renderPass, viewEntity);
        GlStateManager.enableAlpha();
        if (shaders) {
            ShadersRender.beginCutoutMippedTerrain();
        }

        if (!caveFinder.isEnabled()) {
            renderGlobal.renderBlockLayer(
                    EnumWorldBlockLayer.CUTOUT_MIPPED, partialTicks, renderPass, viewEntity);
        }

        this.mc
                .getTextureManager()
                .getTexture(TextureMap.locationBlocksTexture)
                .setBlurMipmap(false, false);
        if (shaders) {
            ShadersRender.beginCutoutTerrain();
        }

        renderGlobal.renderBlockLayer(
                EnumWorldBlockLayer.CUTOUT, partialTicks, renderPass, viewEntity);
        this.mc
                .getTextureManager()
                .getTexture(TextureMap.locationBlocksTexture)
                .restoreLastBlurMipmap();
        if (shaders) {
            ShadersRender.endTerrain();
        }

        if (caveFinder.isEnabled()) {
            caveFinder.cullBackFaces();
        }

        Lagometer.timerTerrain.end();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        if (wireFrame.isEnabled()) {
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_FILL);
        }
    }

    private void renderSelectionOutline(
            RenderGlobal renderGlobal, PlayerEntity player, float partialTicks) {
        GlStateManager.disableAlpha();
        this.mc.mcProfiler.endStartSection("outline");
        boolean forgeHandledOutline =
                Reflector.forgeDrawBlockHighlight.exists()
                        && Reflector.callBoolean(
                                Reflector.forgeDrawBlockHighlight,
                                new Object[] {
                                    renderGlobal,
                                    player,
                                    this.mc.objectMouseOver,
                                    0,
                                    player.getHeldItem(),
                                    partialTicks
                                });
        if (!forgeHandledOutline && !this.mc.gameSettings.hideGUI) {
            renderGlobal.drawSelectionBox(player, this.mc.objectMouseOver, 0, partialTicks);
        }
        GlStateManager.enableAlpha();
    }

    private void resetSkyProjection(float partialTicks) {
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        Project.gluPerspective(
                this.getFOVModifier(partialTicks, true),
                (float) this.mc.displayWidth / this.mc.displayHeight,
                0.05F,
                this.farPlaneDistance);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
    }

    private void renderTranslucentTerrain(
            RenderGlobal renderGlobal,
            Entity viewEntity,
            float partialTicks,
            int renderPass,
            boolean shaders,
            boolean exclusiveRenderStages) {
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.1F);
        this.setupFog(0, partialTicks);
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        if (OptiFineConfig.isTranslucentBlocksFancy()) {
            this.mc.mcProfiler.endStartSection("translucent");
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            this.drawTranslucentLayer(renderGlobal, viewEntity, partialTicks, renderPass, shaders);
            GlStateManager.disableBlend();
            if (exclusiveRenderStages) {
                return;
            }
        }

        this.mc.mcProfiler.endStartSection("translucent");
        this.drawTranslucentLayer(renderGlobal, viewEntity, partialTicks, renderPass, shaders);
    }

    private void drawTranslucentLayer(
            RenderGlobal renderGlobal,
            Entity viewEntity,
            float partialTicks,
            int renderPass,
            boolean shaders) {
        if (shaders) {
            Shaders.nE();
        }
        renderGlobal.renderBlockLayer(
                EnumWorldBlockLayer.TRANSLUCENT, partialTicks, renderPass, viewEntity);
        if (shaders) {
            Shaders.nK();
        }
    }

    private void renderWorldOverlayAndHand(
            float partialTicks, int renderPass, boolean shaders, boolean exclusiveRenderStages) {
        boolean restoreViewBobbing = this.mc.gameSettings.viewBobbing;
        this.mc.gameSettings.viewBobbing = false;
        try {
            EventBus eventBus = RemixClient.eventBus;
            eventBus.post(new Render3DEvent(MotionPhase.PRE, partialTicks));
            this.setupCameraTransform(partialTicks, renderPass);
            eventBus.post(new Render3DEvent(MotionPhase.POST, partialTicks));
        } finally {
            this.mc.gameSettings.viewBobbing = restoreViewBobbing;
        }

        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        if (shaders) {
            ShadersRender.renderFPOverlay(this, partialTicks, renderPass);
        }
        if (!shaders || !exclusiveRenderStages) {
            this.renderHand(partialTicks, renderPass);
        }
    }

    public boolean isShaderActive() {
        return OpenGlHelper.shadersSupported && this.theShaderGroup != null;
    }

    protected void renderRainSnow(float partialTicks) {
        boolean firstColumnOnly = WorldRenderer.getLegacyRenderFlowMarker() == 0;
        if (Reflector.forgeGetWeatherRenderer.exists()) {
            WorldProvider provider = this.mc.world.provider;
            Object weatherRenderer =
                    Reflector.call(provider, Reflector.forgeGetWeatherRenderer, new Object[0]);
            if (weatherRenderer != null) {
                Reflector.callVoid(
                        weatherRenderer,
                        Reflector.forgeRenderHandlerRender,
                        new Object[] {partialTicks, this.mc.world, this.mc});
                return;
            }
        }

        float rainStrength = this.mc.world.getRainStrength(partialTicks);
        if (rainStrength > 0.0F) {
            if (OptiFineConfig.isRainOff()) {
                return;
            }

            this.enableLightmap();
            Entity viewEntity = this.mc.getRenderViewEntity();
            ClientWorld world = this.mc.world;
            int viewBlockX = MathHelper.floor(viewEntity.posX);
            int viewBlockY = MathHelper.floor(viewEntity.posY);
            int viewBlockZ = MathHelper.floor(viewEntity.posZ);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer vertices = tessellator.getWorldRenderer();
            GlStateManager.disableCull();
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.alphaFunc(516, 0.1F);
            double viewX =
                    viewEntity.lastTickPosX
                            + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
            double viewY =
                    viewEntity.lastTickPosY
                            + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
            double viewZ =
                    viewEntity.lastTickPosZ
                            + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
            int interpolatedBlockY = MathHelper.floor(viewY);
            byte radius = 5;
            if (OptiFineConfig.isRainFancy()) {
                radius = 10;
            }

            byte activeTextureBatch = -1;
            float animationTicks = this.rendererUpdateCount + partialTicks;
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            int columnZ = viewBlockZ - radius;

            while (columnZ <= viewBlockZ + radius) {
                int columnX = viewBlockX - radius;

                while (columnX <= viewBlockX + radius) {
                    int offsetIndex = (columnZ - viewBlockZ + 16) * 32 + columnX - viewBlockX + 16;
                    float offsetX = this.rainXCoords[offsetIndex] * 0.5F;
                    float offsetZ = this.rainYCoords[offsetIndex] * 0.5F;
                    BlockPos blockPos = new BlockPos(columnX, 0, columnZ);
                    BiomeGenBase biomeGenForCoords = world.getBiomeGenForCoords(blockPos);
                    if (biomeGenForCoords.canRain() || biomeGenForCoords.getEnableSnow()) {
                        int surfaceY = world.getPrecipitationHeight(blockPos).getY();
                        int bottomY = viewBlockY - radius;
                        int topY = viewBlockY + radius;
                        if (bottomY < surfaceY) {
                            bottomY = surfaceY;
                        }

                        if (topY < surfaceY) {
                            topY = surfaceY;
                        }

                        float textureScale = 1.0F;
                        int lightY = surfaceY;
                        if (surfaceY < interpolatedBlockY) {
                            lightY = interpolatedBlockY;
                        }

                        if (bottomY != topY) {
                            this.random.setSeed(
                                    columnX * columnX * 3121 + columnX * 45238971
                                            ^ columnZ * columnZ * 418711 + columnZ * 13761);
                            float biomeTemperature =
                                    biomeGenForCoords.getFloatTemperature(
                                            new BlockPos(columnX, bottomY, columnZ));
                            boolean rainColumn =
                                    world.getWorldChunkManager()
                                                    .getTemperatureAtHeight(
                                                            biomeTemperature, surfaceY)
                                            >= 0.15F;
                            if (rainColumn) {
                                if (activeTextureBatch != 0) {
                                    if (activeTextureBatch >= 0) {
                                        tessellator.draw();
                                    }

                                    activeTextureBatch = 0;
                                    this.mc
                                            .getTextureManager()
                                            .bindTexture(
                                                    net.minecraft.client.renderer.EntityRenderer
                                                            .locationRainPng);
                                    vertices.startDrawingQuads();
                                }

                                float rainScroll =
                                        ((this.rendererUpdateCount
                                                                        + columnX * columnX * 3121
                                                                        + columnX * 45238971
                                                                        + columnZ * columnZ * 418711
                                                                        + columnZ * 13761
                                                                & 31)
                                                        + partialTicks)
                                                / 32.0F
                                                * (3.0F + this.random.nextFloat());
                                double rainDistanceX = columnX + 0.5F - viewEntity.posX;
                                double rainDistanceZ = columnZ + 0.5F - viewEntity.posZ;
                                float rainDistance =
                                        MathHelper.sqrt(
                                                        rainDistanceX * rainDistanceX
                                                                + rainDistanceZ * rainDistanceZ)
                                                / radius;
                                float rainBrightness = 1.0F;
                                vertices.setPackedLightmap(
                                        world.getCombinedLight(
                                                new BlockPos(columnX, lightY, columnZ), 0));
                                vertices.setVertexColorFromFloats(
                                        rainBrightness,
                                        rainBrightness,
                                        rainBrightness,
                                        ((1.0F - rainDistance * rainDistance) * 0.5F + 0.5F)
                                                * rainStrength);
                                vertices.setTranslation(-viewX * 1.0, -viewY * 1.0, -viewZ * 1.0);
                                vertices.addVertexWithUV(
                                        columnX - offsetX + 0.5,
                                        bottomY,
                                        columnZ - offsetZ + 0.5,
                                        0.0F * textureScale,
                                        bottomY * textureScale / 4.0F + rainScroll * textureScale);
                                vertices.addVertexWithUV(
                                        columnX + offsetX + 0.5,
                                        bottomY,
                                        columnZ + offsetZ + 0.5,
                                        1.0F * textureScale,
                                        bottomY * textureScale / 4.0F + rainScroll * textureScale);
                                vertices.addVertexWithUV(
                                        columnX + offsetX + 0.5,
                                        topY,
                                        columnZ + offsetZ + 0.5,
                                        1.0F * textureScale,
                                        topY * textureScale / 4.0F + rainScroll * textureScale);
                                vertices.addVertexWithUV(
                                        columnX - offsetX + 0.5,
                                        topY,
                                        columnZ - offsetZ + 0.5,
                                        0.0F * textureScale,
                                        topY * textureScale / 4.0F + rainScroll * textureScale);
                                vertices.setTranslation(0.0, 0.0, 0.0);
                            }

                            if (!rainColumn || firstColumnOnly) {
                                if (activeTextureBatch != 1) {
                                    if (activeTextureBatch >= 0) {
                                        tessellator.draw();
                                    }

                                    activeTextureBatch = 1;
                                    this.mc
                                            .getTextureManager()
                                            .bindTexture(
                                                    net.minecraft.client.renderer.EntityRenderer
                                                            .locationSnowPng);
                                    vertices.startDrawingQuads();
                                }

                                float snowScroll =
                                        ((this.rendererUpdateCount & 511) + partialTicks) / 512.0F;
                                float snowDriftU =
                                        this.random.nextFloat()
                                                + animationTicks
                                                        * 0.01F
                                                        * (float) this.random.nextGaussian();
                                float snowDriftV =
                                        this.random.nextFloat()
                                                + animationTicks
                                                        * (float) this.random.nextGaussian()
                                                        * 0.001F;
                                double snowDistanceX = columnX + 0.5F - viewEntity.posX;
                                double snowDistanceZ = columnZ + 0.5F - viewEntity.posZ;
                                float snowDistance =
                                        MathHelper.sqrt(
                                                        snowDistanceX * snowDistanceX
                                                                + snowDistanceZ * snowDistanceZ)
                                                / radius;
                                float snowBrightness = 1.0F;
                                vertices.setPackedLightmap(
                                        (world.getCombinedLight(
                                                                        new BlockPos(
                                                                                columnX, lightY,
                                                                                columnZ),
                                                                        0)
                                                                * 3
                                                        + 15728880)
                                                / 4);
                                vertices.setVertexColorFromFloats(
                                        snowBrightness,
                                        snowBrightness,
                                        snowBrightness,
                                        ((1.0F - snowDistance * snowDistance) * 0.3F + 0.5F)
                                                * rainStrength);
                                vertices.setTranslation(-viewX * 1.0, -viewY * 1.0, -viewZ * 1.0);
                                vertices.addVertexWithUV(
                                        columnX - offsetX + 0.5,
                                        bottomY,
                                        columnZ - offsetZ + 0.5,
                                        0.0F * textureScale + snowDriftU,
                                        bottomY * textureScale / 4.0F
                                                + snowScroll * textureScale
                                                + snowDriftV);
                                vertices.addVertexWithUV(
                                        columnX + offsetX + 0.5,
                                        bottomY,
                                        columnZ + offsetZ + 0.5,
                                        1.0F * textureScale + snowDriftU,
                                        bottomY * textureScale / 4.0F
                                                + snowScroll * textureScale
                                                + snowDriftV);
                                vertices.addVertexWithUV(
                                        columnX + offsetX + 0.5,
                                        topY,
                                        columnZ + offsetZ + 0.5,
                                        1.0F * textureScale + snowDriftU,
                                        topY * textureScale / 4.0F
                                                + snowScroll * textureScale
                                                + snowDriftV);
                                vertices.addVertexWithUV(
                                        columnX - offsetX + 0.5,
                                        topY,
                                        columnZ - offsetZ + 0.5,
                                        0.0F * textureScale + snowDriftU,
                                        topY * textureScale / 4.0F
                                                + snowScroll * textureScale
                                                + snowDriftV);
                                vertices.setTranslation(0.0, 0.0, 0.0);
                            }
                        }
                    }

                    columnX++;
                    if (firstColumnOnly) {
                        break;
                    }
                }

                columnZ++;
                if (firstColumnOnly) {
                    break;
                }
            }

            if (activeTextureBatch >= 0) {
                tessellator.draw();
            }

            GlStateManager.enableCull();
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            this.disableLightmap();
        }
    }

    private void updateOptiFineAndMainMenu() {
        if (!this.optifineListenerRegistered) {
            TextureUtils.registerResourceListener();
            if (OptiFineConfig.getBitsOs() == 64 && OptiFineConfig.jJ() == 32) {
                OptiFineConfig.setNotify64BitJava(true);
            }

            this.optifineListenerRegistered = true;
        }

        OptiFineConfig.checkDisplayMode();
        ClientWorld axNValue = this.mc.world;
        if (axNValue != null) {

            OptiFineConfig.getNewRelease();

            if (OptiFineConfig.isNotify64BitJava()) {
                OptiFineConfig.setNotify64BitJava(false);
                ChatComponentText chatComponentText =
                        new ChatComponentText(I18n.format("of.message.java64Bit", new Object[0]));
                this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentText);
            }
        }

        if (this.mc.currentScreen instanceof RemixMainMenuScreen) {
            this.updateMainMenu((RemixMainMenuScreen) this.mc.currentScreen);
        }

        if (this.updatedWorld != axNValue) {
            RandomMobs.worldChanged(this.updatedWorld, axNValue);
            OptiFineConfig.updateThreadPriorities();

            this.lastServerPacingSampleMillis = 0L;
            this.lastServerPacingTick = 0;
            this.updatedWorld = axNValue;
        }

        if (!this.setFxaaShader(Shaders.configAntialiasingLevel)) {
            Shaders.configAntialiasingLevel = 0;
        }
    }

    public MapItemRenderer getMapItemRenderer() {
        return this.theMapItemRenderer;
    }

    public void enableLightmap() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float fx = 0.00390625F;
        GlStateManager.scale(fx, fx, fx);
        GlStateManager.translate(8.0F, 8.0F, 8.0F);
        GlStateManager.matrixMode(5888);
        this.mc.getTextureManager().bindTexture(this.locationLightMap);
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        GL11.glTexParameteri(3553, 10242, 10496);
        GL11.glTexParameteri(3553, 10243, 10496);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (OptiFineConfig.isShaders()) {
            Shaders.enableLightmap();
        }
    }

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        int i = WorldRenderer.getLegacyRenderFlowMarker();
        if (this.theShaderGroup != null) {
            this.theShaderGroup.deleteShaderGroup();
        }

        this.theShaderGroup = null;
        if (this.shaderIndex != net.minecraft.client.renderer.EntityRenderer.shaderCount) {
            this.loadShader(
                    net.minecraft.client.renderer.EntityRenderer.shaderResourceLocations[
                            this.shaderIndex]);
            if (i != 0) {
                return;
            }
        }

        this.loadEntityShader(this.mc.getRenderViewEntity());
    }

    private void renderWorldDirections(float f) {
        if (this.mc.gameSettings.showDebugInfo
                && !this.mc.gameSettings.hideGUI
                && !this.mc.player.hasReducedDebug()
                && !this.mc.gameSettings.reducedDebugInfo) {
            Entity entity = this.mc.getRenderViewEntity();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GL11.glLineWidth(1.0F);
            GlStateManager.disableTexture2D();
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            GlStateManager.matrixMode(5888);
            GlStateManager.loadIdentity();
            this.orientCamera(f);
            GlStateManager.translate(0.0F, entity.getEyeHeight(), 0.0F);
            RenderGlobal.drawOutlinedBoundingBox(
                    new BoundingBox(0.0, 0.0, 0.0, 0.005, 1.0E-4, 1.0E-4), -65536);
            RenderGlobal.drawOutlinedBoundingBox(
                    new BoundingBox(0.0, 0.0, 0.0, 1.0E-4, 1.0E-4, 0.005), -16776961);
            RenderGlobal.drawOutlinedBoundingBox(
                    new BoundingBox(0.0, 0.0, 0.0, 1.0E-4, 0.0033, 1.0E-4), -16711936);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
        }
    }

    public boolean setFxaaShader(int i) {
        if (!OpenGlHelper.isFramebufferEnabled()) {
            return false;
        }

        if (this.theShaderGroup != null
                && this.theShaderGroup != this.fxaaShaders[2]
                && this.theShaderGroup != this.fxaaShaders[4]) {
            return true;
        }

        if (i != 2 && i != 4) {
            if (this.theShaderGroup == null) {
                return true;
            }

            this.theShaderGroup.deleteShaderGroup();
            this.theShaderGroup = null;
            return true;
        } else {
            if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[i]) {
                return true;
            }

            if (this.mc.world == null) {
                return true;
            }

            this.loadShader(new ResourceLocation("shaders/post/fxaa_of_" + i + "x.json"));
            this.fxaaShaders[i] = this.theShaderGroup;
            return this.useShader;
        }
    }

    private boolean isFullBrightNightVisionActive() {
        return ClientServices.moduleManager != null
                && ClientServices.moduleManager.fullBright.isNightVisionActive();
    }

    private boolean hasNightVision(LivingEntity entity) {
        return this.isFullBrightNightVisionActive() || entity.isPotionActive(Potion.NIGHT_VISION);
    }

    private float getNightVisionBrightness(LivingEntity livingEntity, float f) {
        if (this.isFullBrightNightVisionActive()) {
            return 1.0F;
        }
        int duration = livingEntity.getActivePotionEffect(Potion.NIGHT_VISION).getDuration();
        return duration > 200
                ? 1.0F
                : 0.7F + MathHelper.sin((duration - f) * (float) Math.PI * 0.2F) * 0.3F;
    }

    private void updateMainMenu(RemixMainMenuScreen remixMainMenuScreen) {
        int i = WorldRenderer.getLegacyRenderFlowToken();

        try {
            String text = null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            int j = calendar.get(5);
            int k = calendar.get(2) + 1;
            if (j == 8 && k == 4) {
                text = "Happy birthday, OptiFine!";
            }

            if (j == 14 && k == 8) {
                text = "Happy birthday, sp614x!";
            }

            if (text == null) {
                return;
            }

            Field[] afield = RemixMainMenuScreen.class.getDeclaredFields();
            int l = 0;
            int length = afield.length;

            while (l < length) {
                Field field = afield[l];
                if (field.getType() == String.class) {
                    field.setAccessible(true);
                    field.set(remixMainMenuScreen, text);
                    if (i == 0) {
                        break;
                    }
                }

                l++;
                if (i != 0) {
                    break;
                }
            }
        } catch (Throwable throwable) {
        }
    }

    public void setupViewBobbing(float partialTicks) {
        boolean legacyBobbingFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        Entity viewEntity = this.mc.getRenderViewEntity();
        if (!(viewEntity instanceof PlayerEntity)) {
            return;
        }

        PlayerEntity player = (PlayerEntity) viewEntity;
        float walkDelta = player.distanceWalkedModified - player.prevDistanceWalkedModified;
        float walkPhase =
                -(player.distanceWalkedModified + walkDelta * partialTicks) * (float) Math.PI;
        float bobAmount =
                player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * partialTicks;
        float cameraPitch =
                player.prevCameraPitch
                        + (player.cameraPitch - player.prevCameraPitch) * partialTicks;
        float horizontalBob = MathHelper.sin(walkPhase) * bobAmount;
        GlStateManager.translate(
                horizontalBob * 0.5F, -Math.abs(MathHelper.cos(walkPhase) * bobAmount), 0.0F);

        RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        boolean customBobbing = camera.isEnabled() && camera.bobbingSetting.asBoolean();
        if (customBobbing) {
            this.applyBobbingRotation(
                    walkPhase,
                    horizontalBob,
                    bobAmount,
                    (float) camera.bobbingXSetting.asDouble(),
                    (float) camera.bobbingYSetting.asDouble() + 0.2F);
        }
        if (!customBobbing || legacyBobbingFlow) {
            this.applyBobbingRotation(walkPhase, horizontalBob, bobAmount, 3.0F, 0.2F);
        }
        GlStateManager.rotate(cameraPitch, 1.0F, 0.0F, 0.0F);
    }

    private void applyBobbingRotation(
            float phase, float horizontalBob, float amount, float rollScale, float pitchPhase) {
        GlStateManager.rotate(horizontalBob * rollScale, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(
                Math.abs(MathHelper.cos(phase - pitchPhase) * amount) * 5.0F, 1.0F, 0.0F, 0.0F);
    }

    private boolean shouldDrawBlockOutline() {
        int i = WorldRenderer.getLegacyRenderFlowMarker();
        if (!this.drawBlockOutline) {
            return false;
        }

        Entity entity = this.mc.getRenderViewEntity();
        int j = entity instanceof PlayerEntity && !this.mc.gameSettings.hideGUI ? 1 : 0;
        if (j != 0 && !((PlayerEntity) entity).capabilities.allowEdit) {
            ItemStack currentEquippedItem = ((PlayerEntity) entity).getCurrentEquippedItem();
            if (this.mc.objectMouseOver != null
                    && this.mc.objectMouseOver.typeOfHit == RayHitType.BLOCK) {
                BlockPos blockPos = this.mc.objectMouseOver.getBlockPos();
                BlockState blockState = this.mc.world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (this.mc.playerController.getCurrentGameType()
                        == WorldSettingsGameType.SPECTATOR) {
                    j =
                            ReflectorForge.blockHasTileEntity(blockState)
                                            && this.mc.world.getTileEntity(blockPos)
                                                    instanceof IInventory
                                    ? 1
                                    : 0;
                    if (i != 0) {
                        return remix.compat.compiler.JvmBooleanSlots.fromInt(j);
                    }
                }

                j =
                        currentEquippedItem == null
                                        || !currentEquippedItem.canDestroy(block)
                                                && !currentEquippedItem.canPlaceOn(block)
                                ? 0
                                : 1;
            }
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(j);
    }

    public void updateCameraAndRender(float partialTicks) {
        boolean legacyFrameFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        this.updateOptiFineAndMainMenu();
        boolean displayActive = Display.isActive();
        GameSettings settings = this.mc.gameSettings;
        this.updateFocusPause(settings, displayActive, legacyFrameFlow);

        this.mc.mcProfiler.startSection("mouse");
        this.updateFrameMouseLook(partialTicks, settings, displayActive, legacyFrameFlow);
        this.mc.mcProfiler.endSection();

        if (!this.mc.skipWorldRender) {
            anaglyphEnable = settings.anaglyph;
            ScaledResolution resolution =
                    new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
            int mouseX = Mouse.getX() * resolution.getWidth() / this.mc.displayWidth;
            int mouseY =
                    resolution.getHeight()
                            - Mouse.getY() * resolution.getHeight() / this.mc.displayHeight
                            - 1;
            boolean hasWorld = this.mc.world != null;
            if (hasWorld) {
                this.renderClientWorldAndHud(partialTicks, settings, resolution);
            }
            if (!hasWorld || legacyFrameFlow) {
                this.prepareScreenOnlyFrame();
            }
            if (this.mc.currentScreen != null) {
                this.drawCurrentScreen(partialTicks, mouseX, mouseY, resolution, legacyFrameFlow);
            }
        }

        this.frameFinish();
        this.updateIntegratedServerPacing();
        Lagometer.updateLagometer();
        if (settings.ofProfiler) {
            settings.showDebugProfilerChart = true;
        }
    }

    private void updateFocusPause(
            GameSettings settings, boolean displayActive, boolean legacyFrameFlow) {
        if (!displayActive
                && settings.pauseOnLostFocus
                && (!settings.touchscreen || !Mouse.isButtonDown(1))) {
            if (MinecraftClient.getSystemTime() - this.prevFrameTime <= 500L) {
                return;
            }
            this.mc.openIngameMenu();
            if (!legacyFrameFlow) {
                return;
            }
        }
        this.prevFrameTime = MinecraftClient.getSystemTime();
    }

    private void updateFrameMouseLook(
            float partialTicks,
            GameSettings settings,
            boolean displayActive,
            boolean legacyFrameFlow) {
        if (displayActive
                && MinecraftClient.isRunningOnMac
                && this.mc.inGameHasFocus
                && !Mouse.isInsideWindow()) {
            Mouse.setGrabbed(false);
            Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
            Mouse.setGrabbed(true);
        }
        if (!this.mc.inGameHasFocus || !displayActive) {
            return;
        }

        MouseHelper mouse = this.mc.mouseHelper;
        mouse.mouseXYChange();
        float sensitivity = settings.mouseSensitivity * 0.6F + 0.2F;
        float multiplier = sensitivity * sensitivity * sensitivity * 8.0F;
        float yaw = mouse.deltaX * multiplier;
        float pitch = mouse.deltaY * multiplier;
        int invert = settings.invertMouse ? -1 : 1;
        boolean smoothCamera = settings.smoothCamera;
        if (smoothCamera) {
            this.pendingMouseYaw += yaw;
            this.pendingMousePitch += pitch;
            float frameFraction = partialTicks - this.lastMousePartialTicks;
            this.lastMousePartialTicks = partialTicks;
            yaw = this.smoothedMouseYaw * frameFraction;
            pitch = this.smoothedMousePitch * frameFraction;
        }
        if (!smoothCamera || legacyFrameFlow) {
            this.pendingMouseYaw = 0.0F;
            this.pendingMousePitch = 0.0F;
        }
        pitch *= invert;
        RemixClient.eventBus.post(new CameraRenderEvent(yaw, pitch));
        this.mc.player.turn(yaw, pitch);
        RemixClient.eventBus.post(new CameraRenderEvent());
    }

    private void renderClientWorldAndHud(
            float partialTicks, GameSettings settings, ScaledResolution resolution) {
        this.mc.mcProfiler.startSection("level");
        int minimumFps = Math.max(MinecraftClient.getDebugFPS(), 30);
        this.renderWorld(partialTicks, this.lastFrameRenderNanos + 1_000_000_000 / minimumFps);
        if (OpenGlHelper.shadersSupported) {
            this.mc.renderGlobal.compositeEntityOutlines();
            if (this.theShaderGroup != null && this.useShader) {
                GlStateManager.matrixMode(GL11.GL_TEXTURE);
                GlStateManager.pushMatrix();
                GlStateManager.loadIdentity();
                if (ClientServices.moduleManager != null) {
                    ClientServices.moduleManager.camera.prepareMotionBlurFrame(this.theShaderGroup, System.nanoTime());
                }
                this.theShaderGroup.renderShaderGroup(partialTicks);
                GlStateManager.popMatrix();
            }
            this.mc.getFramebuffer().bindFramebuffer(true);
        }

        this.lastFrameRenderNanos = System.nanoTime();
        this.mc.mcProfiler.endStartSection("gui");
        if (!settings.hideGUI || this.mc.currentScreen != null) {
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
            this.mc.ingameGUI.renderGameOverlay(partialTicks);
            if (settings.ofShowFps && !settings.showDebugInfo) {
                OptiFineConfig.iG();
            }
            if (settings.showDebugInfo) {
                Lagometer.showLagometer(resolution);
            }
        }
        this.mc.mcProfiler.endSection();
    }

    private void prepareScreenOnlyFrame() {
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        this.setupOverlayRendering();
        this.lastFrameRenderNanos = System.nanoTime();
    }

    private void drawCurrentScreen(
            float partialTicks,
            int mouseX,
            int mouseY,
            ScaledResolution resolution,
            boolean legacyFrameFlow) {
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        try {
            if (Reflector.forgeDrawScreen.exists()) {
                Reflector.callVoid(
                        Reflector.forgeDrawScreen,
                        new Object[] {this.mc.currentScreen, mouseX, mouseY, partialTicks});
                if (!legacyFrameFlow) {
                    return;
                }
            }
            this.mc.currentScreen.drawScreen(mouseX, mouseY, partialTicks);
        } catch (Throwable failure) {
            CrashReport crash = CrashReport.makeCrashReport(failure, "Rendering screen");
            CrashReportCategory details = crash.makeCategory("Screen render details");
            details.addCrashSectionCallable(
                    "Screen name", new EntityRendererScreenNameCallable(this));
            details.addCrashSectionCallable(
                    "Mouse location",
                    new EntityRendererMouseLocationCallable(this, mouseX, mouseY));
            details.addCrashSectionCallable(
                    "Screen size", new EntityRendererScreenSizeCallable(this, resolution));
            throw new ReportedException(crash);
        }
    }

    private float getFOVModifier(float partialTicks, boolean useSettingsFov) {
        boolean legacyZoomFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        if ((camera.isEnabled() && camera.noFovSetting.asBoolean()) || this.debugView) {
            return 90.0F;
        }

        Entity viewEntity = this.mc.getRenderViewEntity();
        float fov = 70.0F;
        if (useSettingsFov) {
            fov = this.mc.gameSettings.fovSetting;
            if (OptiFineConfig.isDynamicFov()) {
                fov *=
                        this.previousFovModifier
                                + (this.fovModifier - this.previousFovModifier) * partialTicks;
            }
        }

        fov = this.applyOptiFineZoom(fov, legacyZoomFlow);
        if (viewEntity instanceof LivingEntity && ((LivingEntity) viewEntity).getHealth() <= 0.0F) {
            float deathTime = ((LivingEntity) viewEntity).deathTime + partialTicks;
            fov /= (1.0F - 500.0F / (deathTime + 500.0F)) * 2.0F + 1.0F;
        }

        Block viewBlock =
                ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.world, viewEntity, partialTicks);
        if (viewBlock.getMaterial() == Material.water) {
            fov = fov * 60.0F / 70.0F;
        }
        return fov;
    }

    private float applyOptiFineZoom(float fov, boolean legacyZoomFlow) {
        boolean zoomKeyDown =
                this.mc.currentScreen == null
                        && GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
        if (zoomKeyDown) {
            if (!OptiFineConfig.zoomMode) {
                OptiFineConfig.zoomMode = true;
                this.mc.gameSettings.smoothCamera = true;
            }
            if (!OptiFineConfig.zoomMode) {
                return fov;
            }
            fov /= 4.0F;
            if (!legacyZoomFlow) {
                return fov;
            }
        }

        if (OptiFineConfig.zoomMode) {
            OptiFineConfig.zoomMode = false;
            this.mc.gameSettings.smoothCamera = false;
            this.mouseFilterXAxis = new MouseFilter();
            this.mouseFilterYAxis = new MouseFilter();
            this.mc.renderGlobal.chunkVisibilityDirty = true;
        }
        return fov;
    }

    public void renderStreamIndicator(float f) {
        this.setupOverlayRendering();
        this.mc.ingameGUI.renderStreamIndicator(
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight));
    }

    public void updateRenderer() {
        boolean legacyTickFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
            ShaderLinkHelper.setNewStaticShaderLinkHelper();
        }

        this.updateFovModifier();
        this.updateTorchFlicker();
        this.previousFogBrightness = this.fogBrightness;
        this.thirdPersonDistanceTemp = this.thirdPersonDistance;
        this.updateSmoothCamera(legacyTickFlow);

        if (this.mc.getRenderViewEntity() == null) {
            this.mc.setRenderViewEntity(this.mc.player);
        }
        Entity view = this.mc.getRenderViewEntity();
        float light =
                this.mc.world.getLightBrightness(
                        new BlockPos(view.posX, view.posY + view.getEyeHeight(), view.posZ));
        float distanceFactor =
                MathHelper.clamp(this.mc.gameSettings.renderDistanceChunks / 16.0F, 0.0F, 1.0F);
        float targetFogBrightness = light * (1.0F - distanceFactor) + distanceFactor;
        this.fogBrightness += (targetFogBrightness - this.fogBrightness) * 0.1F;
        this.rendererUpdateCount++;
        this.itemRenderer.updateEquippedItem();
        this.addRainParticles();

        this.previousBossColorModifier = this.bossColorModifier;
        if (BossStatus.hasColorModifier) {
            this.bossColorModifier = Math.min(this.bossColorModifier + 0.05F, 1.0F);
            BossStatus.hasColorModifier = false;
            if (!legacyTickFlow) {
                return;
            }
        }
        if (this.bossColorModifier > 0.0F) {
            this.bossColorModifier -= 0.0125F;
        }
    }

    private void updateSmoothCamera(boolean legacyTickFlow) {
        if (this.mc.gameSettings.smoothCamera) {
            float sensitivity = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float gain = sensitivity * sensitivity * sensitivity * 8.0F;
            this.smoothedMouseYaw =
                    this.mouseFilterXAxis.smooth(this.pendingMouseYaw, 0.05F * gain);
            this.smoothedMousePitch =
                    this.mouseFilterYAxis.smooth(this.pendingMousePitch, 0.05F * gain);
            this.lastMousePartialTicks = 0.0F;
            this.pendingMouseYaw = 0.0F;
            this.pendingMousePitch = 0.0F;
            if (!legacyTickFlow) {
                return;
            }
        }
        this.smoothedMouseYaw = 0.0F;
        this.smoothedMousePitch = 0.0F;
        this.mouseFilterXAxis.reset();
        this.mouseFilterYAxis.reset();
    }

    private static Throwable passThroughThrowable(Throwable throwable) {
        return throwable;
    }

    public ShaderGroup getShaderGroup() {
        return this.theShaderGroup;
    }

    public void updateShaderGroupSize(int i, int j) {
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.createBindFramebuffers(i, j);
            }

            this.mc.renderGlobal.createBindEntityOutlineFbs(i, j);
        }
    }

    public void renderWorld(float partialTicks, long chunkUploadDeadline) {
        boolean legacyPassFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        this.updateLightmap(partialTicks);
        if (this.mc.getRenderViewEntity() == null) {
            this.mc.setRenderViewEntity(this.mc.player);
        }
        this.getMouseOver(partialTicks);
        if (OptiFineConfig.isShaders()) {
            Shaders.beginRender(this.mc, partialTicks, chunkUploadDeadline);
        }

        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
        this.mc.mcProfiler.startSection("center");
        boolean anaglyph = this.mc.gameSettings.anaglyph;
        if (anaglyph) {
            net.minecraft.client.renderer.EntityRenderer.anaglyphPassIndex = 0;
            GlStateManager.colorMask(false, true, true, false);
            this.renderWorldPass(0, partialTicks, chunkUploadDeadline);
            net.minecraft.client.renderer.EntityRenderer.anaglyphPassIndex = 1;
            GlStateManager.colorMask(true, false, false, false);
            this.renderWorldPass(1, partialTicks, chunkUploadDeadline);
            GlStateManager.colorMask(true, true, true, false);
        }
        if (!anaglyph || legacyPassFlow) {
            this.renderWorldPass(2, partialTicks, chunkUploadDeadline);
        }
        this.mc.mcProfiler.endSection();
    }

    public static MinecraftClient access$000(EntityRenderer entityRenderer) {
        return entityRenderer.mc;
    }

    public void loadEntityShader(Entity entity) {
        int i = WorldRenderer.getLegacyRenderFlowMarker();
        if (OpenGlHelper.shadersSupported) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }

            this.theShaderGroup = null;
            if (entity instanceof EntityCreeper) {
                this.loadShader(new ResourceLocation("shaders/post/creeper.json"));
                if (i != 0) {
                    return;
                }
            }

            if (entity instanceof EntitySpider) {
                this.loadShader(new ResourceLocation("shaders/post/spider.json"));
                if (i != 0) {
                    return;
                }
            }

            if (entity instanceof EntityEnderman) {
                this.loadShader(new ResourceLocation("shaders/post/invert.json"));
                if (i != 0) {
                    return;
                }
            }

            if (Reflector.Gd.exists()) {
                Reflector.call(Reflector.Gd, new Object[] {entity, this});
            }
        }
    }

    private void hurtCameraEffect(float f) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        if (!camera.isEnabled() || !camera.noHurtCamSetting.asBoolean()) {
            Entity entity = this.mc.getRenderViewEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                float f1 = livingEntity.hurtTime - f;
                if (livingEntity.getHealth() <= 0.0F) {
                    float f2 = livingEntity.deathTime + f;
                    GlStateManager.rotate(40.0F - 8000.0F / (f2 + 200.0F), 0.0F, 0.0F, 1.0F);
                }

                if (f1 < 0.0F) {
                    return;
                }

                f1 /= livingEntity.maxHurtTime;
                f1 = MathHelper.sin(f1 * f1 * f1 * f1 * (float) Math.PI);
                float aLo = livingEntity.attackedAtYaw;
                GlStateManager.rotate(-aLo, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-f1 * 14.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(aLo, 0.0F, 1.0F, 0.0F);
            }
        }
    }

    public void activateNextShader() {
        int i = WorldRenderer.getLegacyRenderFlowToken();
        if (OpenGlHelper.shadersSupported
                && this.mc.getRenderViewEntity() instanceof PlayerEntity) {
            if (this.theShaderGroup != null) {
                this.theShaderGroup.deleteShaderGroup();
            }

            this.shaderIndex =
                    (this.shaderIndex + 1)
                            % (net.minecraft.client.renderer.EntityRenderer.shaderResourceLocations
                                            .length
                                    + 1);
            if (this.shaderIndex != net.minecraft.client.renderer.EntityRenderer.shaderCount) {
                this.loadShader(
                        net.minecraft.client.renderer.EntityRenderer.shaderResourceLocations[
                                this.shaderIndex]);
                if (i == 0) {
                    return;
                }
            }

            this.theShaderGroup = null;
        }
    }

    private void updateLightmap(float partialTicks) {
        boolean updateAllColors = WorldRenderer.getLegacyRenderFlowMarker() != 0;
        ClientWorld world = this.mc.world;
        if (!this.lightmapNeedsUpdate || world == null) {
            return;
        }

        this.mc.mcProfiler.startSection("lightTex");
        LocalPlayer player = this.mc.player;
        if (!OptiFineConfig.isCustomColors()
                || !CustomColors.updateLightmap(
                        world,
                        this.torchFlicker,
                        this.lightmapColors,
                        this.hasNightVision(player))) {
            float[] brightnessTable = world.provider.getLightBrightnessTable();
            for (int index = 0; index < 256; index++) {
                int ordinaryColor =
                        this.calculateLightmapColor(
                                world, player, brightnessTable, index, partialTicks);
                this.writeLightmapColor(index, ordinaryColor, updateAllColors);
                if (!updateAllColors) {
                    break;
                }
            }
        }
        this.lightmapTexture.updateDynamicTexture();
        this.lightmapNeedsUpdate = false;
        this.mc.mcProfiler.endSection();
    }

    private int calculateLightmapColor(
            ClientWorld world,
            LocalPlayer player,
            float[] brightnessTable,
            int index,
            float partialTicks) {
        float skyScale = world.getSunBrightness(1.0F) * 0.95F + 0.05F;
        float skyLight = brightnessTable[index / 16] * skyScale;
        float blockLight = brightnessTable[index % 16] * (this.torchFlicker * 0.1F + 1.5F);
        if (world.getLastLightningBolt() > 0) {
            skyLight = brightnessTable[index / 16];
        }

        float skyRed = skyLight * (world.getSunBrightness(1.0F) * 0.65F + 0.35F);
        float skyGreen = skyLight * (world.getSunBrightness(1.0F) * 0.65F + 0.35F);
        float blockGreen = blockLight * ((blockLight * 0.6F + 0.4F) * 0.6F + 0.4F);
        float blockBlue = blockLight * (blockLight * blockLight * 0.6F + 0.4F);
        float red = skyRed + blockLight;
        float green = skyGreen + blockGreen;
        float blue = skyLight + blockBlue;
        red = red * 0.96F + 0.03F;
        green = green * 0.96F + 0.03F;
        blue = blue * 0.96F + 0.03F;
        if (this.bossColorModifier > 0.0F) {
            float bossTint =
                    this.previousBossColorModifier
                            + (this.bossColorModifier - this.previousBossColorModifier)
                                    * partialTicks;
            red = red * (1.0F - bossTint) + red * 0.7F * bossTint;
            green = green * (1.0F - bossTint) + green * 0.6F * bossTint;
            blue = blue * (1.0F - bossTint) + blue * 0.6F * bossTint;
        }

        if (world.provider.getDimensionId() == 1) {
            red = 0.22F + blockLight * 0.75F;
            green = 0.28F + blockGreen * 0.75F;
            blue = 0.25F + blockBlue * 0.75F;
        }

        if (this.hasNightVision(player)) {
            float nightVision = this.getNightVisionBrightness(player, partialTicks);
            float brightestChannelScale = 1.0F / red;
            if (brightestChannelScale > 1.0F / green) {
                brightestChannelScale = 1.0F / green;
            }

            if (brightestChannelScale > 1.0F / blue) {
                brightestChannelScale = 1.0F / blue;
            }

            red = red * (1.0F - nightVision) + red * brightestChannelScale * nightVision;
            green = green * (1.0F - nightVision) + green * brightestChannelScale * nightVision;
            blue = blue * (1.0F - nightVision) + blue * brightestChannelScale * nightVision;
        }

        if (red > 1.0F) {
            red = 1.0F;
        }

        if (green > 1.0F) {
            green = 1.0F;
        }

        if (blue > 1.0F) {
            blue = 1.0F;
        }

        float gamma = this.mc.gameSettings.gammaSetting;
        float gammaRed = 1.0F - red;
        float gammaGreen = 1.0F - green;
        float gammaBlue = 1.0F - blue;
        gammaRed = 1.0F - gammaRed * gammaRed * gammaRed * gammaRed;
        gammaGreen = 1.0F - gammaGreen * gammaGreen * gammaGreen * gammaGreen;
        gammaBlue = 1.0F - gammaBlue * gammaBlue * gammaBlue * gammaBlue;
        red = red * (1.0F - gamma) + gammaRed * gamma;
        green = green * (1.0F - gamma) + gammaGreen * gamma;
        blue = blue * (1.0F - gamma) + gammaBlue * gamma;
        red = red * 0.96F + 0.03F;
        green = green * 0.96F + 0.03F;
        blue = blue * 0.96F + 0.03F;
        if (red > 1.0F) {
            red = 1.0F;
        }

        if (green > 1.0F) {
            green = 1.0F;
        }

        if (blue > 1.0F) {
            blue = 1.0F;
        }

        if (red < 0.0F) {
            red = 0.0F;
        }

        if (green < 0.0F) {
            green = 0.0F;
        }

        if (blue < 0.0F) {
            blue = 0.0F;
        }

        return 0xFF000000
                | (int) (red * 255.0F) << 16
                | (int) (green * 255.0F) << 8
                | (int) (blue * 255.0F);
    }

    private void writeLightmapColor(int index, int ordinaryColor, boolean keepCameraColor) {
        RemixClient.INSTANCE.getClientServices();
        CameraModule camera = ClientServices.moduleManager.camera;
        if (camera.isEnabled() && camera.drugsSetting.asBoolean()) {
            int color = Color.HSBtoRGB(this.cameraLightmapHueStep / 50.0F, 0.7F, 0.6F) & 0xFFFFFF;
            if (this.cameraLightmapHueStep < 255) {
                this.cameraLightmapHueStep++;
            }
            if (this.cameraLightmapHueStep >= 255) {
                this.cameraLightmapHueStep = 0;
            }
            this.lightmapColors[index] = color | 0xFF000000;
            if (keepCameraColor) {
                return;
            }
        }
        this.lightmapColors[index] = ordinaryColor;
    }

    private void updateFovModifier() {
        float fx = 1.0F;
        if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
            AbstractClientPlayer renderViewEntity =
                    (AbstractClientPlayer) this.mc.getRenderViewEntity();
            fx = renderViewEntity.getFovModifier();
        }

        this.previousFovModifier = this.fovModifier;
        this.fovModifier = this.fovModifier + (fx - this.fovModifier) * 0.5F;
        if (this.fovModifier > 1.5F) {
            this.fovModifier = 1.5F;
        }

        if (this.fovModifier < 0.1F) {
            this.fovModifier = 0.1F;
        }
    }

    private FloatBuffer setFogColorBuffer(float f, float f1, float f2, float f3) {
        if (OptiFineConfig.isShaders()) {
            Shaders.setFogColor(f, f1, f2);
        }

        ((Buffer) this.fogColorBuffer).clear();
        this.fogColorBuffer.put(f).put(f1).put(f2).put(f3);
        ((Buffer) this.fogColorBuffer).flip();
        return this.fogColorBuffer;
    }

    public void setupOverlayRendering() {
        ScaledResolution scaledResolution =
                new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GlStateManager.clear(256);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(
                0.0,
                scaledResolution.getWidthDouble(),
                scaledResolution.getHeightDouble(),
                0.0,
                1000.0,
                3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0F, 0.0F, -2000.0F);
    }

    private void frameFinish() {
        if (this.mc.world != null) {
            long axEValue = System.currentTimeMillis();

            if (axEValue > this.lastErrorCheckTimeMs + 10000L) {
                this.lastErrorCheckTimeMs = axEValue;
                int j = GL11.glGetError();
                if (j != 0) {
                    String text = GLU.gluErrorString(j);
                    ChatComponentText chatComponentText =
                            new ChatComponentText(
                                    I18n.format("of.message.openglError", new Object[] {j, text}));
                    this.mc.ingameGUI.getChatGUI().printChatMessage(chatComponentText);
                }
            }
        }
    }

    public void loadShader(ResourceLocation resourceLocation) {
        if (OpenGlHelper.isFramebufferEnabled()) {
            try {
                this.theShaderGroup =
                        new ShaderGroup(
                                this.mc.getTextureManager(),
                                this.resourceManager,
                                this.mc.getFramebuffer(),
                                resourceLocation);
                this.theShaderGroup.createBindFramebuffers(
                        this.mc.displayWidth, this.mc.displayHeight);
                this.useShader = true;
            } catch (IOException | JsonSyntaxException ioexception) {
                net.minecraft.client.renderer.EntityRenderer.logger.warn(
                        "Failed to load shader: " + resourceLocation, ioexception);
                this.shaderIndex = net.minecraft.client.renderer.EntityRenderer.shaderCount;
                this.useShader = false;
            }
        }
    }

    private void renderCloudsCheck(RenderGlobal renderGlobal, float f, int i) {
        if (this.mc.gameSettings.renderDistanceChunks >= 4
                && !OptiFineConfig.isCloudsOff()
                && Shaders.shouldRenderClouds(this.mc.gameSettings)) {
            this.mc.mcProfiler.endStartSection("clouds");
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective(
                    this.getFOVModifier(f, true),
                    (float) this.mc.displayWidth / this.mc.displayHeight,
                    0.05F,
                    this.farPlaneDistance * 4.0F);
            GlStateManager.matrixMode(5888);
            GlStateManager.pushMatrix();
            this.setupFog(0, f);
            renderGlobal.renderClouds(f, i);
            GlStateManager.disableFog();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5889);
            GlStateManager.loadIdentity();
            Project.gluPerspective(
                    this.getFOVModifier(f, true),
                    (float) this.mc.displayWidth / this.mc.displayHeight,
                    0.05F,
                    this.farPlaneDistance);
            GlStateManager.matrixMode(5888);
        }
    }

    private void updateIntegratedServerPacing() {
        boolean legacyFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        this.lastServerPacingWaitMillis = 0;
        if (!OptiFineConfig.isSmoothWorld() || !OptiFineConfig.isSingleProcessor()) {
            this.resetServerPacingSample();
            return;
        }
        if (!this.mc.isIntegratedServerRunning()) {
            return;
        }

        IntegratedServer server = this.mc.getIntegratedServer();
        if (server == null) {
            if (legacyFlow) {
                this.resetServerPacingSample();
            }
            return;
        }

        if (!this.mc.isGamePaused() && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
            if (this.serverPacingWaitMillis > 0) {
                Lagometer.timerServer.start();
                OptiFineConfig.sleep(this.serverPacingWaitMillis);
                Lagometer.timerServer.end();
                this.lastServerPacingWaitMillis = this.serverPacingWaitMillis;
            }
            this.sampleIntegratedServerTicks(server, legacyFlow);
            if (!legacyFlow) {
                return;
            }
        }

        if (this.mc.currentScreen instanceof GuiDownloadTerrain) {
            OptiFineConfig.sleep(20L);
        }
        this.resetServerPacingSample();
    }

    private void resetServerPacingSample() {
        this.lastServerPacingSampleMillis = 0L;
        this.lastServerPacingTick = 0;
    }

    private void sampleIntegratedServerTicks(IntegratedServer server, boolean legacyFlow) {
        long nowMillis = System.nanoTime() / 1000000L;
        if (this.lastServerPacingSampleMillis != 0L && this.lastServerPacingTick != 0) {
            long elapsedMillis = nowMillis - this.lastServerPacingSampleMillis;
            if (elapsedMillis < 0L) {
                this.lastServerPacingSampleMillis = nowMillis;
                elapsedMillis = 0L;
            }
            if (elapsedMillis >= 50L) {
                this.lastServerPacingSampleMillis = nowMillis;
                int serverTick = server.getTickCounter();
                int elapsedTicks = serverTick - this.lastServerPacingTick;
                if (elapsedTicks < 0) {
                    this.lastServerPacingTick = serverTick;
                    elapsedTicks = 0;
                }
                if (elapsedTicks < 1 && this.serverPacingWaitMillis < 100) {
                    this.serverPacingWaitMillis += 2;
                }
                if (elapsedTicks > 1 && this.serverPacingWaitMillis > 0) {
                    this.serverPacingWaitMillis--;
                }
                this.lastServerPacingTick = serverTick;
            }
            if (!legacyFlow) {
                return;
            }
        }

        this.lastServerPacingSampleMillis = nowMillis;
        this.lastServerPacingTick = server.getTickCounter();
        this.serverPacingSampleTicks = 1.0F;
        this.serverPacingSampleIntervalMillis = 50.0F;
    }

    // region Static initialization and string tables
    static {
        net.minecraft.client.renderer.EntityRenderer.packedTextFragments = new String[3];
        net.minecraft.client.renderer.EntityRenderer.packedTextFragments[0] = new String("L");
        net.minecraft.client.renderer.EntityRenderer.packedTextFragments[1] = new String(" ");
        net.minecraft.client.renderer.EntityRenderer.logger = LogManager.getLogger();
        net.minecraft.client.renderer.EntityRenderer.locationRainPng =
                new ResourceLocation("textures/environment/rain.png");
        net.minecraft.client.renderer.EntityRenderer.locationSnowPng =
                new ResourceLocation("textures/environment/snow.png");
        net.minecraft.client.renderer.EntityRenderer.shaderResourceLocations =
                new ResourceLocation[] {
                    new ResourceLocation("shaders/post/notch.json"),
                    new ResourceLocation("shaders/post/fxaa.json"),
                    new ResourceLocation("shaders/post/art.json"),
                    new ResourceLocation("shaders/post/bumpy.json"),
                    new ResourceLocation("shaders/post/blobs2.json"),
                    new ResourceLocation("shaders/post/pencil.json"),
                    new ResourceLocation("shaders/post/color_convolve.json"),
                    new ResourceLocation("shaders/post/deconverge.json"),
                    new ResourceLocation("shaders/post/flip.json"),
                    new ResourceLocation("shaders/post/invert.json"),
                    new ResourceLocation("shaders/post/ntsc.json"),
                    new ResourceLocation("shaders/post/outline.json"),
                    new ResourceLocation("shaders/post/phosphor.json"),
                    new ResourceLocation("shaders/post/scan_pincushion.json"),
                    new ResourceLocation("shaders/post/sobel.json"),
                    new ResourceLocation("shaders/post/bits.json"),
                    new ResourceLocation("shaders/post/desaturate.json"),
                    new ResourceLocation("shaders/post/green.json"),
                    new ResourceLocation("shaders/post/blur.json"),
                    new ResourceLocation("shaders/post/wobble.json"),
                    new ResourceLocation("shaders/post/blobs.json"),
                    new ResourceLocation("shaders/post/antialias.json"),
                    new ResourceLocation("shaders/post/creeper.json"),
                    new ResourceLocation("shaders/post/spider.json")
                };
        net.minecraft.client.renderer.EntityRenderer.shaderCount =
                net.minecraft.client.renderer.EntityRenderer.shaderResourceLocations.length;
    }

    // endregion

    public void switchUseShader() {
        this.useShader = remix.compat.compiler.JvmBooleanSlots.fromInt((!this.useShader ? 1 : 0));
    }

    private void setupFog(int renderPass, float partialTicks) {
        boolean legacyFogFallthrough = WorldRenderer.getLegacyRenderFlowToken() != 0;
        Entity viewEntity = this.mc.getRenderViewEntity();
        this.terrainFogActive = false;

        GL11.glFog(
                GL11.GL_FOG_COLOR,
                this.setFogColorBuffer(
                        this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
        GL11.glNormal3f(0.0F, -1.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        Block viewBlock =
                ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.world, viewEntity, partialTicks);
        this.applyFogAtViewpoint(
                viewEntity, viewBlock, renderPass, partialTicks, legacyFogFallthrough);
        GlStateManager.enableColorMaterial();
        GlStateManager.enableFog();
        GlStateManager.colorMaterial(GL11.GL_FRONT, GL11.GL_AMBIENT);
    }

    private void applyFogAtViewpoint(
            Entity viewEntity,
            Block viewBlock,
            int renderPass,
            float partialTicks,
            boolean legacyFogFallthrough) {
        if (Reflector.forgeGetFogDensity.exists()) {
            float customDensity =
                    Reflector.callFloat(
                            Reflector.forgeGetFogDensity,
                            new Object[] {this, viewEntity, viewBlock, partialTicks, 0.1F});
            if (customDensity >= 0.0F) {
                GlStateManager.setFogDensity(customDensity);
                if (!legacyFogFallthrough) {
                    return;
                }
            }
        }

        LivingEntity livingView =
                viewEntity instanceof LivingEntity ? (LivingEntity) viewEntity : null;
        if (livingView != null && livingView.isPotionActive(Potion.BLINDNESS)) {
            this.applyBlindnessFog(livingView, renderPass, legacyFogFallthrough);
            if (!legacyFogFallthrough) {
                return;
            }
        }

        if (this.cloudFog) {
            this.setViewFogMode(GL11.GL_EXP, legacyFogFallthrough);
            GlStateManager.setFogDensity(0.1F);
            if (!legacyFogFallthrough) {
                return;
            }
        }

        if (viewBlock.getMaterial() == Material.water) {
            this.setViewFogMode(GL11.GL_EXP, legacyFogFallthrough);
            boolean waterBreathing =
                    livingView != null && livingView.isPotionActive(Potion.WATER_BREATHING);
            if (waterBreathing) {
                GlStateManager.setFogDensity(0.01F);
            }
            if (!waterBreathing || legacyFogFallthrough) {
                GlStateManager.setFogDensity(
                        0.1F - EnchantmentHelper.getRespiration(viewEntity) * 0.03F);
            }
            if (!OptiFineConfig.isClearWater()) {
                return;
            }
            GlStateManager.setFogDensity(0.02F);
            if (!legacyFogFallthrough) {
                return;
            }
        }

        if (viewBlock.getMaterial() == Material.lava) {
            this.setViewFogMode(GL11.GL_EXP, legacyFogFallthrough);
            GlStateManager.setFogDensity(2.0F);
            if (!legacyFogFallthrough) {
                return;
            }
        }

        this.applyTerrainFog(viewEntity, viewBlock, renderPass, partialTicks, legacyFogFallthrough);
    }

    private void setViewFogMode(int mode, boolean legacyFogFallthrough) {
        boolean shadersEnabled = OptiFineConfig.isShaders();
        if (shadersEnabled) {
            Shaders.setFog(mode);
        }
        if (!shadersEnabled || legacyFogFallthrough) {
            GlStateManager.setFog(mode);
        }
    }

    private void applyBlindnessFog(
            LivingEntity viewEntity, int renderPass, boolean legacyFogFallthrough) {
        float distance = 5.0F;
        int effectTicks = viewEntity.getActivePotionEffect(Potion.BLINDNESS).getDuration();
        if (effectTicks < 20) {
            distance += (this.fogRenderDistance - 5.0F) * (1.0F - effectTicks / 20.0F);
        }

        this.setViewFogMode(GL11.GL_LINEAR, legacyFogFallthrough);
        if (renderPass == -1) {
            GlStateManager.setFogStart(0.0F);
            GlStateManager.setFogEnd(distance * 0.8F);
        }
        if (renderPass != -1 || legacyFogFallthrough) {
            GlStateManager.setFogStart(distance * 0.25F);
            GlStateManager.setFogEnd(distance);
        }
        if (GLContext.getCapabilities().GL_NV_fog_distance && OptiFineConfig.isFancyFogEnabled()) {
            GL11.glFogi(34138, 34139);
        }
    }

    private void applyTerrainFog(
            Entity viewEntity,
            Block viewBlock,
            int renderPass,
            float partialTicks,
            boolean legacyFogFallthrough) {
        float distance = this.fogRenderDistance;
        this.terrainFogActive = true;
        this.setViewFogMode(GL11.GL_LINEAR, legacyFogFallthrough);

        if (renderPass == -1) {
            GlStateManager.setFogStart(0.0F);
            GlStateManager.setFogEnd(distance);
        }
        if (renderPass != -1 || legacyFogFallthrough) {
            GlStateManager.setFogStart(distance * OptiFineConfig.getFogStart());
            GlStateManager.setFogEnd(distance);
        }

        if (GLContext.getCapabilities().GL_NV_fog_distance) {
            if (OptiFineConfig.isFancyFogEnabled()) {
                GL11.glFogi(34138, 34139);
            }
            if (OptiFineConfig.isFastFogEnabled()) {
                GL11.glFogi(34138, 34140);
            }
        }
        if (this.mc.world.provider.doesXZShowFog((int) viewEntity.posX, (int) viewEntity.posZ)) {
            GlStateManager.setFogStart(distance * 0.05F);
            GlStateManager.setFogEnd(distance);
        }
        if (Reflector.forgeOnFogRender.exists()) {
            Reflector.callVoid(
                    Reflector.forgeOnFogRender,
                    new Object[] {this, viewEntity, viewBlock, partialTicks, renderPass, distance});
        }
    }

    public void setupCameraTransform(float f, int i) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        this.fogRenderDistance = this.mc.gameSettings.renderDistanceChunks * 16;
        int j = k;
        if (OptiFineConfig.isFancyFogEnabled()) {
            this.fogRenderDistance *= 0.95F;
        }

        if (OptiFineConfig.isFastFogEnabled()) {
            this.fogRenderDistance *= 0.83F;
        }

        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        float f1 = 0.07F;
        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate(-(i * 2 - 1) * f1, 0.0F, 0.0F);
        }

        this.farPlaneDistance = this.fogRenderDistance * 2.0F;
        if (this.farPlaneDistance < 173.0F) {
            this.farPlaneDistance = 173.0F;
        }

        if (this.mc.world.provider.getDimensionId() == 1) {
            this.farPlaneDistance = 256.0F;
        }

        if (this.cameraZoom != 1.0) {
            GlStateManager.translate(
                    (float) this.cameraZoomOffsetX, (float) (-this.cameraZoomOffsetY), 0.0F);
            GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0);
        }

        Project.gluPerspective(
                this.getFOVModifier(f, true),
                (float) this.mc.displayWidth / this.mc.displayHeight,
                0.05F,
                this.farPlaneDistance);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();

        if (this.mc.gameSettings.anaglyph) {
            GlStateManager.translate((i * 2 - 1) * 0.1F, 0.0F, 0.0F);
        }

        this.hurtCameraEffect(f);
        if (this.mc.gameSettings.viewBobbing) {
            this.setupViewBobbing(f);
        }

        RemixClient.eventBus.post(new CameraTransformEvent());
        LocalPlayer player = this.mc.player;
        float f2 = player.prevTimeInPortal + (player.timeInPortal - player.prevTimeInPortal) * f;
        if (f2 > 0.0F) {
            byte b0 = 20;
            if (player.isPotionActive(Potion.CONFUSION)) {
                b0 = 7;
            }

            float f3 = 5.0F / (f2 * f2 + 5.0F) - f2 * 0.04F;
            f3 *= f3;
            GlStateManager.rotate((this.rendererUpdateCount + f) * b0, 0.0F, 1.0F, 1.0F);
            GlStateManager.scale(1.0F / f3, 1.0F, 1.0F);
            GlStateManager.rotate(-(this.rendererUpdateCount + f) * b0, 0.0F, 1.0F, 1.0F);
        }

        this.orientCamera(f);
        if (this.debugView) {
            switch (this.debugViewDirection) {
                case 0:
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                    if (j == 0) {
                        break;
                    }
                case 1:
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    if (j == 0) {
                        break;
                    }
                case 2:
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                    if (j == 0) {
                        break;
                    }
                case 3:
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                    if (j == 0) {
                        break;
                    }
                case 4:
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
            }
        }
    }

    public void disableLightmap() {
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        if (OptiFineConfig.isShaders()) {
            Shaders.nY();
        }
    }

    private void orientCamera(float partialTicks) {
        Entity viewEntity = this.mc.getRenderViewEntity();
        float eyeHeight = viewEntity.getEyeHeight();
        double eyeX = viewEntity.prevPosX + (viewEntity.posX - viewEntity.prevPosX) * partialTicks;
        double eyeY =
                viewEntity.prevPosY
                        + (viewEntity.posY - viewEntity.prevPosY) * partialTicks
                        + eyeHeight;
        double eyeZ = viewEntity.prevPosZ + (viewEntity.posZ - viewEntity.prevPosZ) * partialTicks;
        ClientWorld world = this.mc.world;
        boolean exclusiveCameraStages = WorldRenderer.getLegacyRenderFlowMarker() != 0;
        GameSettings settings = this.mc.gameSettings;
        boolean sleeping =
                viewEntity instanceof LivingEntity
                        && ((LivingEntity) viewEntity).isPlayerSleeping();
        boolean sleepHandledCamera = false;
        if (sleeping) {
            eyeHeight = (float) (eyeHeight + 1.0);
            GlStateManager.translate(0.0F, 0.3F, 0.0F);
            boolean debugCamera = settings.debugCameraEnabled;
            if (!debugCamera) {
                this.orientSleepingView(viewEntity, world, partialTicks, exclusiveCameraStages);
            }
            sleepHandledCamera = debugCamera || exclusiveCameraStages;
        }

        if (!sleepHandledCamera) {
            boolean thirdPerson = settings.thirdPersonView > 0;
            if (thirdPerson) {
                this.orientThirdPersonView(
                        viewEntity,
                        world,
                        settings,
                        eyeX,
                        eyeY,
                        eyeZ,
                        partialTicks,
                        exclusiveCameraStages);
            }
            if (!thirdPerson || !exclusiveCameraStages) {
                GlStateManager.translate(0.0F, 0.0F, -0.1F);
            }
        }

        this.applyViewRotation(viewEntity, world, settings, partialTicks, exclusiveCameraStages);
        GlStateManager.translate(0.0F, -eyeHeight, 0.0F);
        double cameraX =
                viewEntity.prevPosX + (viewEntity.posX - viewEntity.prevPosX) * partialTicks;
        double cameraY =
                viewEntity.prevPosY
                        + (viewEntity.posY - viewEntity.prevPosY) * partialTicks
                        + eyeHeight;
        double cameraZ =
                viewEntity.prevPosZ + (viewEntity.posZ - viewEntity.prevPosZ) * partialTicks;
        this.cloudFog = this.mc.renderGlobal.hasCloudFog(cameraX, cameraY, cameraZ, partialTicks);
    }

    private void orientSleepingView(
            Entity viewEntity,
            ClientWorld world,
            float partialTicks,
            boolean exclusiveCameraStages) {
        BlockPos bedPosition = new BlockPos(viewEntity);
        BlockState bedState = world.getBlockState(bedPosition);
        Block block = bedState.getBlock();
        boolean forgeBedOrientation = Reflector.forgeOrientBedCamera.exists();
        if (forgeBedOrientation) {
            Reflector.callVoid(
                    Reflector.forgeOrientBedCamera,
                    new Object[] {world, bedPosition, bedState, viewEntity});
        }
        if ((!forgeBedOrientation || !exclusiveCameraStages) && block == Blocks.bed) {
            int direction = ((Facing) bedState.getValue(BlockBed.AGE)).getHorizontalIndex();
            GlStateManager.rotate(direction * 90, 0.0F, 1.0F, 0.0F);
        }

        GlStateManager.rotate(
                viewEntity.prevRotationYaw
                        + (viewEntity.rotationYaw - viewEntity.prevRotationYaw) * partialTicks
                        + 180.0F,
                0.0F,
                -1.0F,
                0.0F);
        GlStateManager.rotate(
                viewEntity.prevRotationPitch
                        + (viewEntity.rotationPitch - viewEntity.prevRotationPitch) * partialTicks,
                -1.0F,
                0.0F,
                0.0F);
    }

    private void orientThirdPersonView(
            Entity viewEntity,
            ClientWorld world,
            GameSettings settings,
            double eyeX,
            double eyeY,
            double eyeZ,
            float partialTicks,
            boolean exclusiveCameraStages) {
        double distance =
                this.thirdPersonDistanceTemp
                        + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * partialTicks;
        if (settings.debugCameraEnabled) {
            GlStateManager.translate(0.0F, 0.0F, (float) -distance);
            if (exclusiveCameraStages) {
                return;
            }
        }

        float yaw = viewEntity.rotationYaw;
        float pitch = viewEntity.rotationPitch;
        if (settings.thirdPersonView == 2) {
            pitch += 180.0F;
        }

        double horizontalX =
                -MathHelper.sin(yaw / 180.0F * (float) Math.PI)
                        * MathHelper.cos(pitch / 180.0F * (float) Math.PI)
                        * distance;
        double horizontalZ =
                MathHelper.cos(yaw / 180.0F * (float) Math.PI)
                        * MathHelper.cos(pitch / 180.0F * (float) Math.PI)
                        * distance;
        double vertical = -MathHelper.sin(pitch / 180.0F * (float) Math.PI) * distance;
        for (int corner = 0; corner < 8; corner++) {
            float offsetX = ((corner & 1) * 2 - 1) * 0.1F;
            float offsetY = ((corner >> 1 & 1) * 2 - 1) * 0.1F;
            float offsetZ = ((corner >> 2 & 1) * 2 - 1) * 0.1F;
            RayTraceResult hit =
                    world.rayTraceBlocks(
                            new Vec3(eyeX + offsetX, eyeY + offsetY, eyeZ + offsetZ),
                            new Vec3(
                                    eyeX - horizontalX + offsetX + offsetZ,
                                    eyeY - vertical + offsetY,
                                    eyeZ - horizontalZ + offsetZ));
            if (hit != null) {
                double hitDistance = hit.hitVec.distanceTo(new Vec3(eyeX, eyeY, eyeZ));
                if (hitDistance < distance) {
                    ClientServices services =
                            (ClientServices) RemixClient.INSTANCE.getClientServices();
                    CameraModule camera = ClientServices.moduleManager.camera;
                    if (!camera.isEnabled() || !camera.viewClipSetting.asBoolean()) {
                        distance = hitDistance;
                    }
                }
            }

            if (!exclusiveCameraStages) {
                break;
            }
        }

        if (settings.thirdPersonView == 2) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        }
        GlStateManager.rotate(viewEntity.rotationPitch - pitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(viewEntity.rotationYaw - yaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.0F, (float) -distance);
        GlStateManager.rotate(yaw - viewEntity.rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(pitch - viewEntity.rotationPitch, 1.0F, 0.0F, 0.0F);
    }

    private void applyViewRotation(
            Entity viewEntity,
            ClientWorld world,
            GameSettings settings,
            float partialTicks,
            boolean exclusiveCameraStages) {
        if (Reflector.forgeCameraSetupConstructor.exists()) {
            if (settings.debugCameraEnabled) {
                return;
            }

            float yaw =
                    viewEntity.prevRotationYaw
                            + (viewEntity.rotationYaw - viewEntity.prevRotationYaw) * partialTicks
                            + 180.0F;
            float pitch =
                    viewEntity.prevRotationPitch
                            + (viewEntity.rotationPitch - viewEntity.prevRotationPitch)
                                    * partialTicks;
            if (viewEntity instanceof AnimalEntity) {
                yaw = getAnimalViewYaw((AnimalEntity) viewEntity, partialTicks);
            }
            Block viewBlock =
                    ActiveRenderInfo.getBlockAtEntityViewpoint(world, viewEntity, partialTicks);
            Object forgeEvent =
                    Reflector.newInstance(
                            Reflector.forgeCameraSetupConstructor,
                            new Object[] {
                                this, viewEntity, viewBlock, partialTicks, yaw, pitch, 0.0F
                            });
            Reflector.postForgeBusEvent(forgeEvent);
            float roll =
                    Reflector.getFieldValueFloat(forgeEvent, Reflector.forgeCameraSetupRoll, 0.0F);
            pitch =
                    Reflector.getFieldValueFloat(
                            forgeEvent, Reflector.forgeCameraSetupPitch, pitch);
            yaw = Reflector.getFieldValueFloat(forgeEvent, Reflector.forgeCameraSetupYaw, yaw);
            GlStateManager.rotate(roll, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
            if (exclusiveCameraStages) {
                return;
            }
        }

        if (!settings.debugCameraEnabled) {
            CameraRotationEvent event =
                    new CameraRotationEvent(
                            viewEntity.prevRotationYaw
                                    + (viewEntity.rotationYaw - viewEntity.prevRotationYaw)
                                            * partialTicks
                                    + 180.0F,
                            viewEntity.prevRotationPitch
                                    + (viewEntity.rotationPitch - viewEntity.prevRotationPitch)
                                            * partialTicks);
            RemixClient.eventBus.post(event);
            if (!event.isCancelled()) {
                GlStateManager.rotate(event.pitch, 1.0F, 0.0F, 0.0F);
                if (viewEntity instanceof AnimalEntity) {
                    GlStateManager.rotate(
                            getAnimalViewYaw((AnimalEntity) viewEntity, partialTicks),
                            0.0F,
                            1.0F,
                            0.0F);
                    if (exclusiveCameraStages) {
                        return;
                    }
                }
                GlStateManager.rotate(event.yaw, 0.0F, 1.0F, 0.0F);
            }
        }
    }

    private static float getAnimalViewYaw(AnimalEntity entity, float partialTicks) {
        return entity.prevRotationYawHead
                + (entity.rotationYawHead - entity.prevRotationYawHead) * partialTicks
                + 180.0F;
    }

    public EntityRenderer(MinecraftClient axDValue, IResourceManager axxValue) {
        this.mouseFilterXAxis = new MouseFilter();
        this.mouseFilterYAxis = new MouseFilter();
        this.thirdPersonDistance = 4.0F;
        this.thirdPersonDistanceTemp = 4.0F;
        this.renderHand = true;
        this.drawBlockOutline = true;
        this.prevFrameTime = MinecraftClient.getSystemTime();
        this.rainXCoords = new float[1024];
        this.rainYCoords = new float[1024];
        int l = WorldRenderer.getLegacyRenderFlowMarker();
        this.fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
        this.debugViewDirection = 0;
        this.debugView = false;
        this.cameraZoom = 1.0;
        this.optifineListenerRegistered = false;
        this.updatedWorld = null;
        this.amn = false;
        this.farPlaneDistance = 128.0F;

        this.lastServerPacingSampleMillis = 0L;
        this.lastServerPacingTick = 0;
        int i = l;
        this.serverPacingWaitMillis = 0;
        this.lastServerPacingWaitMillis = 0;
        this.serverPacingSampleIntervalMillis = 0.0F;
        this.serverPacingSampleTicks = 0.0F;

        this.lastErrorCheckTimeMs = 0L;
        this.fxaaShaders = new ShaderGroup[10];
        this.shaderIndex = net.minecraft.client.renderer.EntityRenderer.shaderCount;
        this.useShader = false;
        this.frameCount = 0;
        this.mc = axDValue;
        this.resourceManager = axxValue;
        this.itemRenderer = axDValue.getItemRenderer();
        this.theMapItemRenderer = new MapItemRenderer(axDValue.getTextureManager());
        this.lightmapTexture = new DynamicTexture(16, 16);
        this.locationLightMap =
                axDValue.getTextureManager()
                        .getDynamicTextureLocation("lightMap", this.lightmapTexture);
        this.lightmapColors = this.lightmapTexture.getTextureData();
        this.theShaderGroup = null;
        int j = 0;

        while (j < 32) {
            int k = 0;

            while (true) {
                if (k < 32) {
                    float fx = k - 16;
                    float f1 = j - 16;
                    float f2 = MathHelper.sqrt(fx * fx + f1 * f1);
                    this.rainXCoords[j << 5 | k] = -f1 / f2;
                    this.rainYCoords[j << 5 | k] = fx / f2;
                    k++;
                    if (i != 0) {
                        continue;
                    }
                }

                j++;
                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }

    private void updateFogColor(float partialTicks) {
        ClientWorld world = this.mc.world;
        Entity viewEntity = this.mc.getRenderViewEntity();
        float skyBlend = 0.25F + 0.75F * this.mc.gameSettings.renderDistanceChunks / 32.0F;
        skyBlend = 1.0F - (float) Math.pow(skyBlend, 0.25);
        Vec3 skyColor = world.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
        skyColor =
                CustomColors.getWorldFogColor(
                        skyColor, world, this.mc.getRenderViewEntity(), partialTicks);
        boolean legacyFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        float skyRed = (float) skyColor.x;
        float skyGreen = (float) skyColor.y;
        float skyBlue = (float) skyColor.z;
        Vec3 dimensionFog = world.getFogColor(partialTicks);
        dimensionFog =
                CustomColors.resolveDimensionFogColor(
                        dimensionFog, world, this.mc.getRenderViewEntity(), partialTicks);
        this.fogColorRed = (float) dimensionFog.x;
        this.fogColorGreen = (float) dimensionFog.y;
        this.fogColorBlue = (float) dimensionFog.z;
        if (this.mc.gameSettings.renderDistanceChunks >= 4) {
            double west = -1.0;
            Vec3 sunriseDirection =
                    MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) > 0.0F
                            ? new Vec3(west, 0.0, 0.0)
                            : new Vec3(1.0, 0.0, 0.0);
            float sunriseBlend =
                    (float) viewEntity.getLook(partialTicks).dotProduct(sunriseDirection);
            if (sunriseBlend < 0.0F) {
                sunriseBlend = 0.0F;
            }

            if (sunriseBlend > 0.0F) {
                float[] sunrise =
                        world.provider.calcSunriseSunsetColors(
                                world.getCelestialAngle(partialTicks), partialTicks);
                if (sunrise != null) {
                    sunriseBlend *= sunrise[3];
                    this.fogColorRed =
                            this.fogColorRed * (1.0F - sunriseBlend) + sunrise[0] * sunriseBlend;
                    this.fogColorGreen =
                            this.fogColorGreen * (1.0F - sunriseBlend) + sunrise[1] * sunriseBlend;
                    this.fogColorBlue =
                            this.fogColorBlue * (1.0F - sunriseBlend) + sunrise[2] * sunriseBlend;
                }
            }
        }

        this.fogColorRed = this.fogColorRed + (skyRed - this.fogColorRed) * skyBlend;
        this.fogColorGreen = this.fogColorGreen + (skyGreen - this.fogColorGreen) * skyBlend;
        this.fogColorBlue = this.fogColorBlue + (skyBlue - this.fogColorBlue) * skyBlend;
        float rainStrength = world.getRainStrength(partialTicks);
        if (rainStrength > 0.0F) {
            float rainRedGreenScale = 1.0F - rainStrength * 0.5F;
            float rainBlueScale = 1.0F - rainStrength * 0.4F;
            this.fogColorRed *= rainRedGreenScale;
            this.fogColorGreen *= rainRedGreenScale;
            this.fogColorBlue *= rainBlueScale;
        }

        float weightedThunderStrength = world.getWeightedThunderStrength(partialTicks);
        if (weightedThunderStrength > 0.0F) {
            float thunderScale = 1.0F - weightedThunderStrength * 0.5F;
            this.fogColorRed *= thunderScale;
            this.fogColorGreen *= thunderScale;
            this.fogColorBlue *= thunderScale;
        }

        Block eyeBlock =
                ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.world, viewEntity, partialTicks);
        this.applyViewpointFog(world, viewEntity, eyeBlock, partialTicks, legacyFlow);

        float brightness =
                this.previousFogBrightness
                        + (this.fogBrightness - this.previousFogBrightness) * partialTicks;
        this.fogColorRed *= brightness;
        this.fogColorGreen *= brightness;
        this.fogColorBlue *= brightness;
        double voidFogYFactor = world.provider.getVoidFogYFactor();
        double voidFade =
                (viewEntity.lastTickPosY
                                + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks)
                        * voidFogYFactor;
        if (viewEntity instanceof LivingEntity
                && ((LivingEntity) viewEntity).isPotionActive(Potion.BLINDNESS)) {
            int blindnessTicks =
                    ((LivingEntity) viewEntity)
                            .getActivePotionEffect(Potion.BLINDNESS)
                            .getDuration();
            voidFade =
                    blindnessTicks < 20 && !legacyFlow
                            ? voidFade * (1.0F - blindnessTicks / 20.0F)
                            : 0.0;
        }

        if (voidFade < 1.0) {
            if (voidFade < 0.0) {
                voidFade = 0.0;
            }

            voidFade *= voidFade;
            this.fogColorRed = (float) (this.fogColorRed * voidFade);
            this.fogColorGreen = (float) (this.fogColorGreen * voidFade);
            this.fogColorBlue = (float) (this.fogColorBlue * voidFade);
        }

        if (this.bossColorModifier > 0.0F) {
            float bossTint =
                    this.previousBossColorModifier
                            + (this.bossColorModifier - this.previousBossColorModifier)
                                    * partialTicks;
            this.fogColorRed =
                    this.fogColorRed * (1.0F - bossTint) + this.fogColorRed * 0.7F * bossTint;
            this.fogColorGreen =
                    this.fogColorGreen * (1.0F - bossTint) + this.fogColorGreen * 0.6F * bossTint;
            this.fogColorBlue =
                    this.fogColorBlue * (1.0F - bossTint) + this.fogColorBlue * 0.6F * bossTint;
        }

        if (viewEntity instanceof LivingEntity && this.hasNightVision((LivingEntity) viewEntity)) {
            float nightVision =
                    this.getNightVisionBrightness((LivingEntity) viewEntity, partialTicks);
            float maxColorScale = 1.0F / this.fogColorRed;
            if (maxColorScale > 1.0F / this.fogColorGreen) {
                maxColorScale = 1.0F / this.fogColorGreen;
            }

            if (maxColorScale > 1.0F / this.fogColorBlue) {
                maxColorScale = 1.0F / this.fogColorBlue;
            }

            this.fogColorRed =
                    this.fogColorRed * (1.0F - nightVision)
                            + this.fogColorRed * maxColorScale * nightVision;
            this.fogColorGreen =
                    this.fogColorGreen * (1.0F - nightVision)
                            + this.fogColorGreen * maxColorScale * nightVision;
            this.fogColorBlue =
                    this.fogColorBlue * (1.0F - nightVision)
                            + this.fogColorBlue * maxColorScale * nightVision;
        }

        if (this.mc.gameSettings.anaglyph) {
            float stereoRed =
                    (this.fogColorRed * 30.0F
                                    + this.fogColorGreen * 59.0F
                                    + this.fogColorBlue * 11.0F)
                            / 100.0F;
            float stereoGreen = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
            float stereoBlue = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
            this.fogColorRed = stereoRed;
            this.fogColorGreen = stereoGreen;
            this.fogColorBlue = stereoBlue;
        }

        if (Reflector.forgeFogColorsConstructor.exists()) {
            Object object =
                    Reflector.newInstance(
                            Reflector.forgeFogColorsConstructor,
                            new Object[] {
                                this,
                                viewEntity,
                                eyeBlock,
                                partialTicks,
                                this.fogColorRed,
                                this.fogColorGreen,
                                this.fogColorBlue
                            });
            Reflector.postForgeBusEvent(object);
            this.fogColorRed =
                    Reflector.getFieldValueFloat(
                            object, Reflector.forgeFogColorRed, this.fogColorRed);
            this.fogColorGreen =
                    Reflector.getFieldValueFloat(
                            object, Reflector.forgeFogColorGreen, this.fogColorGreen);
            this.fogColorBlue =
                    Reflector.getFieldValueFloat(
                            object, Reflector.forgeFogColorBlue, this.fogColorBlue);
        }

        Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
    }

    private void applyViewpointFog(
            ClientWorld world,
            Entity viewEntity,
            Block eyeBlock,
            float partialTicks,
            boolean legacyFlow) {
        if (this.cloudFog) {
            Vec3 cloudColor = world.getCloudColour(partialTicks);
            this.fogColorRed = (float) cloudColor.x;
            this.fogColorGreen = (float) cloudColor.y;
            this.fogColorBlue = (float) cloudColor.z;
            if (!legacyFlow) {
                return;
            }
        }

        if (eyeBlock.getMaterial() == Material.water) {
            float breathingTint = EnchantmentHelper.getRespiration(viewEntity) * 0.2F;
            if (viewEntity instanceof LivingEntity
                    && ((LivingEntity) viewEntity).isPotionActive(Potion.WATER_BREATHING)) {
                breathingTint = breathingTint * 0.3F + 0.6F;
            }

            this.fogColorRed = 0.02F + breathingTint;
            this.fogColorGreen = 0.02F + breathingTint;
            this.fogColorBlue = 0.2F + breathingTint;
            Vec3 waterColor =
                    CustomColors.getUnderwaterColor(
                            this.mc.world,
                            this.mc.getRenderViewEntity().posX,
                            this.mc.getRenderViewEntity().posY + 1.0,
                            this.mc.getRenderViewEntity().posZ);
            if (waterColor == null) {
                return;
            }

            this.fogColorRed = (float) waterColor.x;
            this.fogColorGreen = (float) waterColor.y;
            this.fogColorBlue = (float) waterColor.z;
            if (!legacyFlow) {
                return;
            }
        }

        if (eyeBlock.getMaterial() == Material.lava) {
            this.fogColorRed = 0.6F;
            this.fogColorGreen = 0.1F;
            this.fogColorBlue = 0.0F;
        }
    }
}
