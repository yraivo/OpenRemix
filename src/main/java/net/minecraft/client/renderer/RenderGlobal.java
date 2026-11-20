package net.minecraft.client.renderer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonSyntaxException;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
import net.minecraft.client.renderer.chunk.ListChunkFactory;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.chunk.VboChunkFactory;
import net.minecraft.client.renderer.chunk.VisGraph;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.culling.Frustrum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumType;
import net.minecraft.client.renderer.vertex.VertexFormatElementEnumUseage;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPosMutableBlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.IWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;

import optifine.ChunkUtils;
import optifine.CloudRenderer;
import optifine.CustomColors;
import optifine.CustomSky;
import optifine.DynamicLights;
import optifine.Lagometer;
import optifine.OptiFineConfig;
import optifine.Reflector;
import optifine.ShadowChunkEntryCache;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.compat.vecmath.Matrix4f;
import remix.compat.vecmath.Vector3d;
import remix.compat.vecmath.Vector3f;
import remix.compat.vecmath.Vector4f;
import remix.module.render.CameraModule;
import remix.util.render.WorldRenderUtils;

import shadersmod.client.Shaders;
import shadersmod.client.ShadersRender;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class RenderGlobal implements IWorldAccess, IResourceManagerReloadListener {
    private static ResourceLocation locationCloudsPng;
    private List<ChunkVisibilityEntry> visibleTerrainChunks;
    private double lastVisibilityViewYaw;
    private int frustumUpdatePosChunkY;
    private double frustumUpdatePosZ;
    private double lastVisibilityViewY;
    private static ResourceLocation locationMoonPhasesPng;
    private double frustumUpdatePosX;
    private ShaderGroup entityOutlineShader;
    private boolean vboEnabled;
    private Framebuffer entityOutlineFramebuffer;
    private List<ChunkVisibilityEntry> mainTileEntityChunks;
    private ClippingHelper debugFixedClippingHelper;
    public MinecraftClient mc;
    public boolean chunkVisibilityDirty;
    private static ResourceLocation locationForcefieldPng;
    private List<ChunkVisibilityEntry> visibleEntityChunks;
    private int renderedEntityCount;
    private int glSkyList;
    private Map mapSoundPositions;
    private double lastVisibilityViewZ;
    public Map damagedBlocks;
    private int renderDistanceChunks;
    private int renderEntitiesStartupCounter;
    private static String[] packedTextFragments;
    private Set<RenderChunk> chunksToUpdate;
    private int renderedTileEntityCount;
    private int frustumUpdatePosChunkZ;
    private static ResourceLocation locationSunPng;
    private static ResourceLocation locationEndSkyPng;
    private VertexBuffer sky2VBO;
    private double frustumUpdatePosY;
    private int hiddenEntityCount;
    private Vector4f[] debugTerrainMatrix;
    public Set<RenderChunk> pendingNearChunkUpdates;
    private RenderManager renderManager;
    private List<ChunkVisibilityEntry> shadowEntityChunks;
    private static Set<Facing> SET_ALL_FACINGS;
    private int renderDistanceSq;
    private int cloudTickCounter;
    private static Logger logger;
    private Deque<ChunkVisibilityEntry> visibilityDeque;
    private boolean debugFixTerrainFrustum;
    private VertexBuffer starVBO;
    private double lastTranslucentSortY;
    private VertexBuffer skyVBO;
    private List<ChunkVisibilityEntry> shadowTileEntityChunks;
    private List<ChunkVisibilityEntry> shadowTerrainChunks;
    IRenderChunkFactory renderChunkFactory;
    private List<ChunkVisibilityEntry> mainEntityChunks;
    private VertexFormat vertexBufferFormat;
    private double lastTranslucentSortX;
    private TextureManager renderEngine;
    private ClientWorld theWorld;
    private int renderDistance;
    private TextureAtlasSprite[] destroyBlockIcons;
    private int frustumUpdatePosChunkX;
    private ChunkRenderContainer renderContainer;

    public Set<RenderChunk> pendingTransparencyUpdates;
    private Vector3d debugTerrainFrustumPosition;
    private double lastVisibilityViewPitch;
    private CloudRenderer cloudRenderer;
    private List<ChunkVisibilityEntry> visibleTileEntityChunks;
    private int glSkyList2;

    private List<ChunkVisibilityEntry> mainTerrainChunks;
    private int totalEntityCount;
    private double lastTranslucentSortZ;
    private ViewFrustum viewFrustum;
    public Entity renderedEntity;
    private double lastVisibilityViewX;
    private int starGLCallList;
    private ChunkRenderDispatcher renderDispatcher;

    public void playAuxSFX(PlayerEntity playerEntity, int i, BlockPos blockPos, int j) {
        int k2 = WorldRenderer.getLegacyRenderFlowToken();
        Random random = this.theWorld.rand;
        int k = k2;
        switch (i) {
            case 1000:
                this.theWorld.playSoundAtPos(blockPos, "random.click", 1.0F, 1.0F, false);
                if (k == 0) {
                    break;
                }
            case 1001:
                this.theWorld.playSoundAtPos(blockPos, "random.click", 1.0F, 1.2F, false);
                if (k == 0) {
                    break;
                }
            case 1002:
                this.theWorld.playSoundAtPos(blockPos, "random.bow", 1.0F, 1.2F, false);
                if (k == 0) {
                    break;
                }
            case 1003:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "random.door_open",
                        1.0F,
                        this.theWorld.rand.nextFloat() * 0.1F + 0.9F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1004:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "random.fizz",
                        0.5F,
                        2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1005:
                if (Item.getItemById(j) instanceof ItemRecord) {
                    this.theWorld.playRecord(
                            blockPos, "records." + ((ItemRecord) Item.getItemById(j)).recordName);
                    if (k == 0) {
                        break;
                    }
                }

                this.theWorld.playRecord(blockPos, null);
                if (k == 0) {
                    break;
                }
            case 1006:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "random.door_close",
                        1.0F,
                        this.theWorld.rand.nextFloat() * 0.1F + 0.9F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1007:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.ghast.charge",
                        10.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1008:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.ghast.fireball",
                        10.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1009:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.ghast.fireball",
                        2.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1010:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.zombie.wood",
                        2.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1011:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.zombie.metal",
                        2.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1012:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.zombie.woodbreak",
                        2.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1014:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.wither.shoot",
                        2.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1015:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.bat.takeoff",
                        0.05F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1016:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.zombie.infect",
                        2.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1017:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "mob.zombie.unfect",
                        2.0F,
                        (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1020:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "random.anvil_break",
                        1.0F,
                        this.theWorld.rand.nextFloat() * 0.1F + 0.9F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1021:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "random.anvil_use",
                        1.0F,
                        this.theWorld.rand.nextFloat() * 0.1F + 0.9F,
                        false);
                if (k == 0) {
                    break;
                }
            case 1022:
                this.theWorld.playSoundAtPos(
                        blockPos,
                        "random.anvil_land",
                        0.3F,
                        this.theWorld.rand.nextFloat() * 0.1F + 0.9F,
                        false);
                if (k == 0) {
                    break;
                }
            case 2000:
                int j1 = j % 3 - 1;
                int k1 = j / 3 % 3 - 1;
                double d13 = blockPos.getX() + j1 * 0.6 + 0.5;
                double d15 = blockPos.getY() + 0.5;
                double d19 = blockPos.getZ() + k1 * 0.6 + 0.5;
                int j2 = 0;

                while (j2 < 10) {
                    double d22 = random.nextDouble() * 0.2 + 0.01;
                    double d23 = d13 + j1 * 0.01 + (random.nextDouble() - 0.5) * k1 * 0.5;
                    double d20 = d15 + (random.nextDouble() - 0.5) * 0.5;
                    double d21 = d19 + k1 * 0.01 + (random.nextDouble() - 0.5) * j1 * 0.5;
                    double d24 = j1 * d22 + random.nextGaussian() * 0.01;
                    double d9 = -0.03 + random.nextGaussian() * 0.01;
                    double d10 = k1 * d22 + random.nextGaussian() * 0.01;
                    this.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d23, d20, d21, d24, d9, d10);
                    j2++;
                    if (k != 0) {
                        break;
                    }
                }

                return;
            case 2001:
                Block block = Block.getBlockById(j & 4095);
                if (block.getMaterial() != Material.AIR) {
                    this.mc
                            .getSoundHandler()
                            .playSound(
                                    new PositionedSoundRecord(
                                            new ResourceLocation(block.stepSound.getBreakSound()),
                                            (block.stepSound.getVolume() + 1.0F) / 2.0F,
                                            block.stepSound.getFrequency() * 0.8F,
                                            blockPos.getX() + 0.5F,
                                            blockPos.getY() + 0.5F,
                                            blockPos.getZ() + 0.5F));
                }

                this.mc.effectRenderer.addBlockDestroyEffects(
                        blockPos, block.getStateFromMeta(j >> 12 & 0xFF));
                if (k == 0) {
                    break;
                }
            case 2002:
                double x = blockPos.getX();
                double y = blockPos.getY();
                double z = blockPos.getZ();
                int l = 0;

                while (l < 8) {
                    this.spawnParticle(
                            EnumParticleTypes.ITEM_CRACK,
                            x,
                            y,
                            z,
                            random.nextGaussian() * 0.15,
                            random.nextDouble() * 0.2,
                            random.nextGaussian() * 0.15,
                            Item.getIdFromItem(Items.potionitem),
                            j);
                    l++;
                    if (k != 0) {
                        break;
                    }
                }

                l = Items.potionitem.getColorFromDamage(j);
                float f = (l >> 16 & 0xFF) / 255.0F;
                float f1 = (l >> 8 & 0xFF) / 255.0F;
                float f2 = (l >> 0 & 0xFF) / 255.0F;
                EnumParticleTypes enumParticleTypes = EnumParticleTypes.SPELL;
                if (Items.potionitem.isEffectInstant(j)) {
                    enumParticleTypes = EnumParticleTypes.SPELL_INSTANT;
                }

                int i2 = 0;

                while (i2 < 100) {
                    double d16 = random.nextDouble() * 4.0;
                    double d17 = random.nextDouble() * Math.PI * 2.0;
                    double d18 = Math.cos(d17) * d16;
                    double d7 = 0.01 + random.nextDouble() * 0.5;
                    double d8 = Math.sin(d17) * d16;
                    EntityFX entityFX =
                            this.spawnEntityFX(
                                    enumParticleTypes.getParticleID(),
                                    enumParticleTypes.getShouldIgnoreRange(),
                                    x + d18 * 0.1,
                                    y + 0.3,
                                    z + d8 * 0.1,
                                    d18,
                                    d7,
                                    d8);
                    if (entityFX != null) {
                        float f3 = 0.75F + random.nextFloat() * 0.25F;
                        entityFX.setRBGColorF(f * f3, f1 * f3, f2 * f3);
                        entityFX.multiplyVelocity((float) d16);
                    }

                    i2++;
                    if (k != 0) {
                        break;
                    }
                }

                this.theWorld.playSoundAtPos(
                        blockPos,
                        "game.potion.smash",
                        1.0F,
                        this.theWorld.rand.nextFloat() * 0.1F + 0.9F,
                        false);
                if (k == 0) {
                    break;
                }
            case 2003:
                double d11 = blockPos.getX() + 0.5;
                double yValue = blockPos.getY();
                double d14 = blockPos.getZ() + 0.5;
                int l1 = 0;

                while (l1 < 8) {
                    this.spawnParticle(
                            EnumParticleTypes.ITEM_CRACK,
                            d11,
                            yValue,
                            d14,
                            random.nextGaussian() * 0.15,
                            random.nextDouble() * 0.2,
                            random.nextGaussian() * 0.15,
                            Item.getIdFromItem(Items.ender_eye));
                    l1++;
                    if (k != 0) {
                        break;
                    }
                }

                double d6 = 0.0;

                while (d6 < Math.PI * 2) {
                    this.spawnParticle(
                            EnumParticleTypes.PORTAL,
                            d11 + Math.cos(d6) * 5.0,
                            yValue - 0.4,
                            d14 + Math.sin(d6) * 5.0,
                            Math.cos(d6) * -5.0,
                            0.0,
                            Math.sin(d6) * -5.0);
                    this.spawnParticle(
                            EnumParticleTypes.PORTAL,
                            d11 + Math.cos(d6) * 5.0,
                            yValue - 0.4,
                            d14 + Math.sin(d6) * 5.0,
                            Math.cos(d6) * -7.0,
                            0.0,
                            Math.sin(d6) * -7.0);
                    d6 += Math.PI / 20;
                    if (k != 0) {
                        break;
                    }
                }

                return;
            case 2004:
                int i1 = 0;

                while (i1 < 20) {
                    double d3 =
                            blockPos.getX() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    double d4 =
                            blockPos.getY() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    double d5 =
                            blockPos.getZ() + 0.5 + (this.theWorld.rand.nextFloat() - 0.5) * 2.0;
                    this.theWorld.spawnParticle(
                            EnumParticleTypes.SMOKE_NORMAL, d3, d4, d5, 0.0, 0.0, 0.0, new int[0]);
                    this.theWorld.spawnParticle(
                            EnumParticleTypes.FLAME, d3, d4, d5, 0.0, 0.0, 0.0, new int[0]);
                    i1++;
                    if (k != 0) {
                        break;
                    }
                }

                return;
            case 2005:
                ItemDye.spawnBonemealParticles(this.theWorld, blockPos, j);
        }
    }

    public void tickLevelRenderer() {
        if (optifine.OptiFineConfig.isShaders()
                && org.lwjgl.input.Keyboard.isKeyDown(61)
                && org.lwjgl.input.Keyboard.isKeyDown(19)) {
            shadersmod.client.Shaders.uninit();
        }

        this.cloudTickCounter += 1;
        if (this.cloudTickCounter % 20 == 0) {
            this.cleanupDamagedBlocks(
                    ((java.util.Iterator)
                            (((java.util.Collection)
                                            (((java.util.Collection)
                                                    (((java.util.Map) (this.damagedBlocks))
                                                            .values()))))
                                    .iterator())));
        }
    }

    private void renderBlockLayer(EnumWorldBlockLayer enumWorldBlockLayer) {
        int k = WorldRenderer.getLegacyRenderFlowToken();
        this.mc.entityRenderer.enableLightmap();
        int i = k;
        if (OpenGlHelper.useVbo()) {
            GL11.glEnableClientState(32884);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glEnableClientState(32888);
            OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
            GL11.glEnableClientState(32886);
        }

        if (OptiFineConfig.isShaders()) {
            ShadersRender.fZ();
        }

        this.renderContainer.renderChunkLayer(enumWorldBlockLayer);
        if (OptiFineConfig.isShaders()) {
            ShadersRender.ha();
        }

        if (OpenGlHelper.useVbo()) {
            for (java.util.Iterator<?> __recoveryIterator171_42100 =
                            (DefaultVertexFormats.BLOCK.getElements()).iterator();
                    (__recoveryIterator171_42100).hasNext(); ) {
                VertexFormatElement vertexFormatElement =
                        (VertexFormatElement) (__recoveryIterator171_42100).next();
                VertexFormatElementEnumUseage vertexFormatElementEnumUseage =
                        vertexFormatElement.getUsage();
                int j = vertexFormatElement.getIndex();
                switch (vertexFormatElementEnumUseage) {
                    case POSITION:
                        GL11.glDisableClientState(32884);
                        if (i == 0) {
                            break;
                        }
                    case UV:
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + j);
                        GL11.glDisableClientState(32888);
                        OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
                        if (i == 0) {
                            break;
                        }
                    case COLOR:
                        GL11.glDisableClientState(32886);
                        GlStateManager.resetColor();
                }

                if (i != 0) {
                    break;
                }
            }
        }

        this.mc.entityRenderer.disableLightmap();
    }

    private void fixTerrainFrustum(double cameraX, double cameraY, double cameraZ) {
        ClippingHelperImpl clipping = new ClippingHelperImpl();
        this.debugFixedClippingHelper = clipping;
        clipping.init();
        boolean legacyFirstCornerOnly = WorldRenderer.getLegacyRenderFlowToken() != 0;

        Matrix4f modelView = new Matrix4f(clipping.modelviewMatrix);
        modelView.transpose();
        Matrix4f projection = new Matrix4f(clipping.projectionMatrix);
        projection.transpose();
        Matrix4f inverseClip = new Matrix4f();
        inverseClip.multiplyFrom(projection, modelView);
        inverseClip.invertInPlace();

        this.debugTerrainFrustumPosition.x = cameraX;
        this.debugTerrainFrustumPosition.y = cameraY;
        this.debugTerrainFrustumPosition.z = cameraZ;
        this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
        this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
        this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
        this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
        this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
        this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
        this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);
        for (int index = 0; index < 8; index++) {
            Vector4f corner = this.debugTerrainMatrix[index];
            inverseClip.transform(corner);
            corner.x /= corner.w;
            corner.y /= corner.w;
            corner.z /= corner.w;
            corner.w = 1.0F;
            if (legacyFirstCornerOnly) {
                break;
            }
        }
    }

    private void generateSky2() {
        Tessellator instance = Tessellator.getInstance();
        int j = WorldRenderer.getLegacyRenderFlowToken();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        int i = j;
        if (this.sky2VBO != null) {
            this.sky2VBO.deleteGlBuffers();
        }

        if (this.glSkyList2 >= 0) {
            GLAllocation.b(this.glSkyList2);
            this.glSkyList2 = -1;
        }

        if (this.vboEnabled) {
            this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderSky(worldRenderer, -16.0F, true);
            worldRenderer.draw();
            worldRenderer.reset();
            this.sky2VBO.uploadVertexData(
                    worldRenderer.getByteBuffer(), worldRenderer.getDrawByteCount());
            if (i == 0) {
                return;
            }
        }

        this.glSkyList2 = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.glSkyList2, 4864);
        this.renderSky(worldRenderer, -16.0F, true);
        instance.draw();
        GL11.glEndList();
    }

    public void markBlockRangeForRenderUpdate(
            int p171_0, int p171_1, int p171_2, int p171_3, int p171_4, int p171_5) {
        this.markBlocksForUpdate(
                p171_0 - 1, p171_1 - 1, p171_2 - 1, p171_3 + 1, p171_4 + 1, p171_5 + 1);
    }

    public void notifyLightSet(BlockPos blockPos) {
        int x = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        this.markBlocksForUpdate(x - 1, j - 1, k - 1, x + 1, j + 1, k + 1);
    }

    protected boolean isRenderEntityOutlines() {
        return !OptiFineConfig.isFastRender()
                && !OptiFineConfig.isShaders()
                && !OptiFineConfig.isAntialiasing()
                && this.entityOutlineFramebuffer != null
                && this.entityOutlineShader != null
                && this.mc.player != null
                && this.mc.player.isSpectator()
                && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown();
    }

    private void markBlocksForUpdate(
            int p171_0, int p171_1, int p171_2, int p171_3, int p171_4, int p171_5) {
        this.viewFrustum.markBlocksForUpdate(p171_0, p171_1, p171_2, p171_3, p171_4, p171_5);
    }

    public void createBindEntityOutlineFbs(int p171_0, int p171_1) {
        if (net.minecraft.client.renderer.OpenGlHelper.shadersSupported
                && this.entityOutlineShader != null) {
            this.entityOutlineShader.createBindFramebuffers(p171_0, p171_1);
        }
    }

    public void updateChunks(long uploadDeadline) {
        boolean fullUpdatePass = WorldRenderer.getLegacyRenderFlowMarker() != 0;
        this.chunkVisibilityDirty |= this.renderDispatcher.runChunkUploads(uploadDeadline);
        this.submitNearChunkUpdates(fullUpdatePass);
        this.submitNextTransparencyUpdate();
        this.submitChunkRebuilds(fullUpdatePass);
    }

    private void submitNearChunkUpdates(boolean fullUpdatePass) {
        Iterator<RenderChunk> pending = this.pendingNearChunkUpdates.iterator();
        while (pending.hasNext()) {
            RenderChunk chunk = pending.next();
            if (!this.renderDispatcher.updateChunkLater(chunk) && fullUpdatePass) {
                break;
            }
            chunk.setNeedsUpdate(false);
            pending.remove();
            this.chunksToUpdate.remove(chunk);
            this.pendingTransparencyUpdates.remove(chunk);
            if (!fullUpdatePass) {
                break;
            }
        }
    }

    private void submitNextTransparencyUpdate() {
        Iterator<RenderChunk> pending = this.pendingTransparencyUpdates.iterator();
        if (pending.hasNext() && this.renderDispatcher.updateTransparencyLater(pending.next())) {
            pending.remove();
        }
    }

    private void submitChunkRebuilds(boolean fullUpdatePass) {
        int submitted = 0;
        int budget = OptiFineConfig.getUpdatesPerFrame();
        int maximumBudget = budget * 2;
        Iterator<RenderChunk> pending = this.chunksToUpdate.iterator();
        while (pending.hasNext()) {
            RenderChunk chunk = pending.next();
            if (!this.renderDispatcher.updateChunkLater(chunk) && fullUpdatePass) {
                break;
            }
            chunk.setNeedsUpdate(false);
            pending.remove();
            if (chunk.getCompiledChunk().isEmpty() && budget < maximumBudget) {
                budget++;
            }
            submitted++;
            if (!fullUpdatePass || submitted >= budget) {
                break;
            }
        }
    }

    public void setWorldAndLoadRenderers(ClientWorld azNValue) {
        if (this.theWorld != null) {
            this.theWorld.removeWorldAccess(this);
        }

        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.renderManager.setWorld(azNValue);
        this.theWorld = azNValue;
        if (OptiFineConfig.isDynamicLights()) {
            DynamicLights.clear();
        }

        if (azNValue != null) {
            azNValue.addWorldAccess(this);
            this.loadRenderers();
        }
    }

    private EntityFX spawnEntityFX(
            int i,
            boolean flag,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        if (this.mc != null
                && this.mc.getRenderViewEntity() != null
                && this.mc.effectRenderer != null) {
            int j = this.mc.gameSettings.particleSetting;
            if (j == 1 && this.theWorld.rand.nextInt(3) == 0) {
                j = 2;
            }

            double d6 = this.mc.getRenderViewEntity().posX - d0;
            double d7 = this.mc.getRenderViewEntity().posY - d1;
            double d8 = this.mc.getRenderViewEntity().posZ - d2;
            if (i == EnumParticleTypes.EXPLOSION_HUGE.getParticleID()
                    && !OptiFineConfig.isAnimatedExplosion()) {
                return null;
            }

            if (i == EnumParticleTypes.EXPLOSION_LARGE.getParticleID()
                    && !OptiFineConfig.isAnimatedExplosion()) {
                return null;
            }

            if (i == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID()
                    && !OptiFineConfig.isAnimatedExplosion()) {
                return null;
            }

            if (i == EnumParticleTypes.SUSPENDED.getParticleID()
                    && !OptiFineConfig.isWaterParticles()) {
                return null;
            }

            if (i == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID()
                    && !OptiFineConfig.isVoidParticles()) {
                return null;
            }

            if (i == EnumParticleTypes.SMOKE_NORMAL.getParticleID()
                    && !OptiFineConfig.isAnimatedSmoke()) {
                return null;
            }

            if (i == EnumParticleTypes.SMOKE_LARGE.getParticleID()
                    && !OptiFineConfig.isAnimatedSmoke()) {
                return null;
            }

            if (i == EnumParticleTypes.SPELL_MOB.getParticleID()
                    && !OptiFineConfig.isPotionParticles()) {
                return null;
            }

            if (i == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID()
                    && !OptiFineConfig.isPotionParticles()) {
                return null;
            }

            if (i == EnumParticleTypes.SPELL.getParticleID()
                    && !OptiFineConfig.isPotionParticles()) {
                return null;
            }

            if (i == EnumParticleTypes.SPELL_INSTANT.getParticleID()
                    && !OptiFineConfig.isPotionParticles()) {
                return null;
            }

            if (i == EnumParticleTypes.SPELL_WITCH.getParticleID()
                    && !OptiFineConfig.isPotionParticles()) {
                return null;
            }

            if (i == EnumParticleTypes.PORTAL.getParticleID()
                    && !OptiFineConfig.isAnimatedPortal()) {
                return null;
            }

            if (i == EnumParticleTypes.FLAME.getParticleID() && !OptiFineConfig.isAnimatedFlame()) {
                return null;
            }

            if (i == EnumParticleTypes.REDSTONE.getParticleID()
                    && !OptiFineConfig.isAnimatedRedstone()) {
                return null;
            }

            if (i == EnumParticleTypes.DRIP_WATER.getParticleID()
                    && !OptiFineConfig.isDrippingWaterLava()) {
                return null;
            }

            if (i == EnumParticleTypes.DRIP_LAVA.getParticleID()
                    && !OptiFineConfig.isDrippingWaterLava()) {
                return null;
            }

            if (i == EnumParticleTypes.FIREWORKS_SPARK.getParticleID()
                    && !OptiFineConfig.isFireworkParticles()) {
                return null;
            }

            if (flag) {
                return this.mc.effectRenderer.spawnEffectParticle(i, d0, d1, d2, d3, d4, d5, aint);
            }

            double d10 = 256.0;
            if (i == EnumParticleTypes.CRIT.getParticleID()) {
                d10 = 38416.0;
            }

            if (d6 * d6 + d7 * d7 + d8 * d8 > d10) {
                return null;
            }

            if (j > 1) {
                return null;
            }

            EntityFX entityFX =
                    this.mc.effectRenderer.spawnEffectParticle(i, d0, d1, d2, d3, d4, d5, aint);
            if (i == EnumParticleTypes.WATER_BUBBLE.getParticleID()) {
                CustomColors.updateWaterFX(entityFX, this.theWorld, d0, d1, d2);
            }

            if (i == EnumParticleTypes.WATER_SPLASH.getParticleID()) {
                CustomColors.updateWaterFX(entityFX, this.theWorld, d0, d1, d2);
            }

            if (i == EnumParticleTypes.WATER_DROP.getParticleID()) {
                CustomColors.updateWaterFX(entityFX, this.theWorld, d0, d1, d2);
            }

            if (i == EnumParticleTypes.TOWN_AURA.getParticleID()) {
                CustomColors.updateMyceliumFX(entityFX);
            }

            if (i == EnumParticleTypes.PORTAL.getParticleID()) {
                CustomColors.updatePortalFX(entityFX);
            }

            if (i == EnumParticleTypes.REDSTONE.getParticleID()) {
                CustomColors.updateReddustFX(entityFX, this.theWorld, d0, d1, d2);
            }

            return entityFX;
        } else {
            return null;
        }
    }

    private void renderSky(WorldRenderer worldRenderer, float f, boolean flag) {

        worldRenderer.startDrawingQuads();
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        short short1 = -384;
        int i = j;

        while (short1 <= 384) {
            short short2 = -384;

            while (true) {
                if (short2 <= 384) {
                    float f1 = short1;
                    float f2 = short1 + 64;
                    if (flag) {
                        f2 = short1;
                        f1 = short1 + 64;
                    }

                    worldRenderer.addVertex(f1, f, short2);
                    worldRenderer.addVertex(f2, f, short2);
                    worldRenderer.addVertex(f2, f, short2 + 64);
                    worldRenderer.addVertex(f1, f, short2 + 64);
                    short2 += 64;
                    if (i != 0) {
                        continue;
                    }
                }

                short1 += 64;
                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }

    // region Static initialization and string tables
    static {
        net.minecraft.client.renderer.RenderGlobal.packedTextFragments = new String[3];
        net.minecraft.client.renderer.RenderGlobal.packedTextFragments[0] = new String("");
        net.minecraft.client.renderer.RenderGlobal.packedTextFragments[1] = new String("/");
        net.minecraft.client.renderer.RenderGlobal.logger = LogManager.getLogger();
        net.minecraft.client.renderer.RenderGlobal.locationMoonPhasesPng =
                new ResourceLocation("textures/environment/moon_phases.png");
        net.minecraft.client.renderer.RenderGlobal.locationSunPng =
                new ResourceLocation("textures/environment/sun.png");
        net.minecraft.client.renderer.RenderGlobal.locationCloudsPng =
                new ResourceLocation("textures/environment/clouds.png");
        net.minecraft.client.renderer.RenderGlobal.locationEndSkyPng =
                new ResourceLocation("textures/environment/end_sky.png");
        net.minecraft.client.renderer.RenderGlobal.locationForcefieldPng =
                new ResourceLocation("textures/misc/forcefield.png");
        net.minecraft.client.renderer.RenderGlobal.SET_ALL_FACINGS =
                Collections.unmodifiableSet(new HashSet<>(Arrays.asList(Facing.VALUES)));
    }

    // endregion

    public void makeEntityOutlineShader() {
        int i = WorldRenderer.getLegacyRenderFlowToken();
        if (OpenGlHelper.shadersSupported) {
            if (ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
                ShaderLinkHelper.setNewStaticShaderLinkHelper();
            }

            ResourceLocation resourceLocation =
                    new ResourceLocation("shaders/post/entity_outline.json");

            try {
                this.entityOutlineShader =
                        new ShaderGroup(
                                this.mc.getTextureManager(),
                                this.mc.getResourceManager(),
                                this.mc.getFramebuffer(),
                                resourceLocation);
                this.entityOutlineShader.createBindFramebuffers(
                        this.mc.displayWidth, this.mc.displayHeight);
                this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
            } catch (IOException ioexception) {
                net.minecraft.client.renderer.RenderGlobal.logger.warn(
                        "Failed to load shader: " + resourceLocation, ioexception);
                this.entityOutlineShader = null;
                this.entityOutlineFramebuffer = null;
            } catch (JsonSyntaxException jsonsyntaxexception) {
                net.minecraft.client.renderer.RenderGlobal.logger.warn(
                        "Failed to load shader: " + resourceLocation, jsonsyntaxexception);
                this.entityOutlineShader = null;
                this.entityOutlineFramebuffer = null;
            }

            if (i == 0) {
                return;
            }
        }

        this.entityOutlineShader = null;
        this.entityOutlineFramebuffer = null;
    }

    public void setDisplayListEntitiesDirty() {
        this.chunkVisibilityDirty = true;
    }

    public void markBlockForUpdate(BlockPos blockPos) {
        int x = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();
        this.markBlocksForUpdate(x - 1, j - 1, k - 1, x + 1, j + 1, k + 1);
    }

    public void loadRenderers() {
        int i = WorldRenderer.getLegacyRenderFlowToken();
        if (this.theWorld != null) {
            this.chunkVisibilityDirty = true;
            Blocks.leaves.setGraphicsLevel(OptiFineConfig.jI());
            Blocks.leaves2.setGraphicsLevel(OptiFineConfig.jI());
            BlockModelRenderer.updateAoLightValue();
            if (OptiFineConfig.isDynamicLights()) {
                DynamicLights.clear();
            }

            this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
            this.renderDistance = this.renderDistanceChunks * 16;
            this.renderDistanceSq = this.renderDistance * this.renderDistance;
            boolean previousVbo = this.vboEnabled;
            this.vboEnabled = OpenGlHelper.useVbo();
            if (previousVbo && !this.vboEnabled) {
                this.renderContainer = new RenderList();
                this.renderChunkFactory = new ListChunkFactory();
            } else if (!previousVbo && this.vboEnabled) {
                this.renderContainer = new VboRenderList();
                this.renderChunkFactory = new VboChunkFactory();
            }

            if (previousVbo != this.vboEnabled) {
                this.generateStars();
                this.generateSky();
                this.generateSky2();
            }

            if (this.viewFrustum != null) {
                this.viewFrustum.deleteGlResources();
            }

            this.stopChunkUpdates();
            this.viewFrustum =
                    new ViewFrustum(
                            this.theWorld,
                            this.mc.gameSettings.renderDistanceChunks,
                            this,
                            this.renderChunkFactory);
            if (this.theWorld != null) {
                Entity entity = this.mc.getRenderViewEntity();
                if (entity != null) {
                    this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
                }
            }

            this.renderEntitiesStartupCounter = 2;
        }
    }

    private void endBlockDamageRender() {
        try {
            net.minecraft.client.renderer.GlStateManager.disableAlpha();
            net.minecraft.client.renderer.GlStateManager.doPolygonOffset(0.0F, 0.0F);
            net.minecraft.client.renderer.GlStateManager.disablePolygonOffset();
            net.minecraft.client.renderer.GlStateManager.enableAlpha();
            net.minecraft.client.renderer.GlStateManager.depthMask(true);
            net.minecraft.client.renderer.GlStateManager.popMatrix();
            if (optifine.OptiFineConfig.isShaders()) {
                shadersmod.client.ShadersRender.endBlockDamage();
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw (jsonsyntaxexception);
        }
    }

    private void renderEndSky() {
        int i = WorldRenderer.getLegacyRenderFlowToken();
        if (OptiFineConfig.isSkyEnabled()) {
            GlStateManager.disableFog();
            GlStateManager.disableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.depthMask(false);
            this.renderEngine.bindTexture(
                    net.minecraft.client.renderer.RenderGlobal.locationEndSkyPng);
            Tessellator instance = Tessellator.getInstance();
            WorldRenderer worldRenderer = instance.getWorldRenderer();
            int j = 0;

            while (j < 6) {
                GlStateManager.pushMatrix();
                if (j == 1) {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (j == 2) {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (j == 3) {
                    GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
                }

                if (j == 4) {
                    GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
                }

                if (j == 5) {
                    GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
                }

                worldRenderer.startDrawingQuads();
                worldRenderer.setOpaqueVertexColorFromRgb(2631720);
                worldRenderer.addVertexWithUV(-100.0, -100.0, -100.0, 0.0, 0.0);
                worldRenderer.addVertexWithUV(-100.0, -100.0, 100.0, 0.0, 16.0);
                worldRenderer.addVertexWithUV(100.0, -100.0, 100.0, 16.0, 16.0);
                worldRenderer.addVertexWithUV(100.0, -100.0, -100.0, 16.0, 0.0);
                instance.draw();
                GlStateManager.popMatrix();
                j++;
                if (i != 0) {
                    break;
                }
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
        }
    }

    public int getRenderedEntityCount() {
        return this.renderedEntityCount;
    }

    public RenderChunk getRenderChunk(BlockPos blockPos) {
        return this.viewFrustum.getRenderChunk(blockPos);
    }

    // region String decoding support

    // endregion

    public ClientWorld getWorld() {
        return this.theWorld;
    }

    public void playSound(String text, double d0, double d1, double d2, float f, float f1) {}

    public void deleteAllDisplayLists() {}

    public void onResourceManagerReload(IResourceManager iResourceManager) {
        this.updateDestroyBlockIcons();
    }

    public void sendBlockBreakProgress(int i, BlockPos blockPos, int j) {
        int k = WorldRenderer.getLegacyRenderFlowMarker();
        if (j >= 0 && j < 10) {
            DestroyBlockProgress destroyBlockProgress =
                    (DestroyBlockProgress) this.damagedBlocks.get(i);
            if (destroyBlockProgress == null
                    || destroyBlockProgress.getPosition().getX() != blockPos.getX()
                    || destroyBlockProgress.getPosition().getY() != blockPos.getY()
                    || destroyBlockProgress.getPosition().getZ() != blockPos.getZ()) {
                destroyBlockProgress = new DestroyBlockProgress(i, blockPos);
                this.damagedBlocks.put(i, destroyBlockProgress);
            }

            destroyBlockProgress.setPartialBlockDamage(j);
            destroyBlockProgress.setCloudUpdateTick(this.cloudTickCounter);
            if (k != 0) {
                return;
            }
        }

        this.damagedBlocks.remove(i);
    }

    public String getDebugInfoEntities() {
        return "E: "
                + this.renderedEntityCount
                + packedTextFragments[1]
                + this.totalEntityCount
                + ", B: "
                + this.hiddenEntityCount
                + ", I: "
                + (this.totalEntityCount - this.hiddenEntityCount - this.renderedEntityCount)
                + ", "
                + OptiFineConfig.getVersionDebug();
    }

    private void generateStars() {
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        int i = j;
        if (this.starVBO != null) {
            this.starVBO.deleteGlBuffers();
        }

        if (this.starGLCallList >= 0) {
            GLAllocation.b(this.starGLCallList);
            this.starGLCallList = -1;
        }

        if (this.vboEnabled) {
            this.starVBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderStars(worldRenderer);
            worldRenderer.draw();
            worldRenderer.reset();
            this.starVBO.uploadVertexData(
                    worldRenderer.getByteBuffer(), worldRenderer.getDrawByteCount());
            if (i != 0) {
                return;
            }
        }

        this.starGLCallList = GLAllocation.generateDisplayLists(1);
        GlStateManager.pushMatrix();
        GL11.glNewList(this.starGLCallList, 4864);
        this.renderStars(worldRenderer);
        instance.draw();
        GL11.glEndList();
        GlStateManager.popMatrix();
    }

    public void resetClouds() {
        this.cloudRenderer.reset();
    }

    public void setupTerrain(
            Entity viewEntity,
            double partialTicks,
            ICamera camera,
            int frameIndex,
            boolean spectator) {
        boolean legacyFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks) {
            this.loadRenderers();
        }

        this.theWorld.theProfiler.startSection("camera");
        this.updateChunkGridPosition(viewEntity);
        if (OptiFineConfig.isDynamicLights()) {
            DynamicLights.update(this);
        }

        this.theWorld.theProfiler.endStartSection("renderlistcamera");
        double viewX =
                viewEntity.lastTickPosX
                        + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
        double viewY =
                viewEntity.lastTickPosY
                        + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
        double viewZ =
                viewEntity.lastTickPosZ
                        + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
        this.renderContainer.initialize(viewX, viewY, viewZ);
        this.theWorld.theProfiler.endStartSection("cull");
        if (this.debugFixedClippingHelper != null) {
            Frustrum fixedCamera = new Frustrum(this.debugFixedClippingHelper);
            fixedCamera.setPosition(
                    this.debugTerrainFrustumPosition.x,
                    this.debugTerrainFrustumPosition.y,
                    this.debugTerrainFrustumPosition.z);
            camera = fixedCamera;
        }

        this.mc.mcProfiler.endStartSection("culling");
        BlockPos eyePosition = new BlockPos(viewX, viewY + viewEntity.getEyeHeight(), viewZ);
        RenderChunk eyeChunk = this.viewFrustum.getRenderChunk(eyePosition);
        BlockPos cameraChunkOrigin =
                new BlockPos(
                        MathHelper.floor(viewX) / 16 * 16,
                        MathHelper.floor(viewY) / 16 * 16,
                        MathHelper.floor(viewZ) / 16 * 16);
        this.updateChunkVisibilityDirtyFlag(viewEntity);
        boolean fixedFrustum = this.debugFixedClippingHelper != null;
        Lagometer.timerVisibility.start();
        this.selectChunkVisibilityLists(fixedFrustum, legacyFlow);
        if (!fixedFrustum && this.chunkVisibilityDirty && !Shaders.isShadowPass) {
            this.collectVisibleChunks(
                    viewEntity,
                    partialTicks,
                    camera,
                    frameIndex,
                    spectator,
                    eyePosition,
                    eyeChunk,
                    legacyFlow);
        }

        if (this.debugFixTerrainFrustum) {
            this.fixTerrainFrustum(viewX, viewY, viewZ);
            this.debugFixTerrainFrustum = false;
        }

        Lagometer.timerVisibility.end();
        if (Shaders.isShadowPass) {
            Shaders.mcProfilerEndSection();
            if (!legacyFlow) {
                return;
            }
        }

        this.scheduleVisibleChunkUpdates(cameraChunkOrigin, legacyFlow);
        this.mc.mcProfiler.endSection();
    }

    private void updateChunkGridPosition(Entity viewEntity) {
        double dx = viewEntity.posX - this.frustumUpdatePosX;
        double dy = viewEntity.posY - this.frustumUpdatePosY;
        double dz = viewEntity.posZ - this.frustumUpdatePosZ;
        if (this.frustumUpdatePosChunkX != viewEntity.chunkCoordX
                || this.frustumUpdatePosChunkY != viewEntity.chunkCoordY
                || this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ
                || dx * dx + dy * dy + dz * dz > 16.0) {
            this.frustumUpdatePosX = viewEntity.posX;
            this.frustumUpdatePosY = viewEntity.posY;
            this.frustumUpdatePosZ = viewEntity.posZ;
            this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
            this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
            this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
            this.viewFrustum.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
        }
    }

    private void updateChunkVisibilityDirtyFlag(Entity viewEntity) {
        this.chunkVisibilityDirty =
                this.chunkVisibilityDirty
                        || !this.chunksToUpdate.isEmpty()
                        || viewEntity.posX != this.lastVisibilityViewX
                        || viewEntity.posY != this.lastVisibilityViewY
                        || viewEntity.posZ != this.lastVisibilityViewZ
                        || viewEntity.rotationPitch != this.lastVisibilityViewPitch
                        || viewEntity.rotationYaw != this.lastVisibilityViewYaw;
        this.lastVisibilityViewX = viewEntity.posX;
        this.lastVisibilityViewY = viewEntity.posY;
        this.lastVisibilityViewZ = viewEntity.posZ;
        this.lastVisibilityViewPitch = viewEntity.rotationPitch;
        this.lastVisibilityViewYaw = viewEntity.rotationYaw;
    }

    private void selectChunkVisibilityLists(boolean fixedFrustum, boolean legacyFlow) {
        if (Shaders.isShadowPass) {
            this.visibleTerrainChunks = this.shadowTerrainChunks;
            this.visibleEntityChunks = this.shadowEntityChunks;
            this.visibleTileEntityChunks = this.shadowTileEntityChunks;
            if (fixedFrustum || !this.chunkVisibilityDirty) {
                return;
            }

            this.collectShadowChunks(legacyFlow);
            if (!legacyFlow) {
                return;
            }
        }

        this.visibleTerrainChunks = this.mainTerrainChunks;
        this.visibleEntityChunks = this.mainEntityChunks;
        this.visibleTileEntityChunks = this.mainTileEntityChunks;
    }

    private void clearVisibleChunkLists() {
        this.visibleTerrainChunks.clear();
        this.visibleEntityChunks.clear();
        this.visibleTileEntityChunks.clear();
    }

    private void collectShadowChunks(boolean firstChunkOnly) {
        this.clearVisibleChunkLists();
        ShadowChunkEntryCache entry = new ShadowChunkEntryCache();
        for (RenderChunk chunk : this.viewFrustum.renderChunks) {
            entry.resetForChunk(chunk);
            if (!chunk.compiledChunk.isEmpty() || chunk.isNeedsUpdate()) {
                this.visibleTerrainChunks.add(entry.getRenderInfo());
            }
            if (ChunkUtils.hasEntities(
                    this.theWorld.getChunkFromBlockCoords(chunk.getPosition()))) {
                this.visibleEntityChunks.add(entry.getRenderInfo());
            }
            if (!chunk.getCompiledChunk().getTileEntities().isEmpty()) {
                this.visibleTileEntityChunks.add(entry.getRenderInfo());
            }
            if (firstChunkOnly) {
                break;
            }
        }
    }

    private void collectVisibleChunks(
            Entity viewEntity,
            double partialTicks,
            ICamera camera,
            int frameIndex,
            boolean spectator,
            BlockPos eyePosition,
            RenderChunk eyeChunk,
            boolean legacyFlow) {
        this.chunkVisibilityDirty = false;
        this.clearVisibleChunkLists();
        this.visibilityDeque.clear();
        boolean useOcclusion = this.mc.renderChunksMany;
        if (eyeChunk == null) {
            this.seedBoundaryChunks(eyePosition, camera, frameIndex, legacyFlow);
        }
        if (eyeChunk != null || legacyFlow) {
            ChunkVisibilityEntry entry = new ChunkVisibilityEntry(eyeChunk, null, 0, null);
            Set<Facing> visibleFaces = net.minecraft.client.renderer.RenderGlobal.SET_ALL_FACINGS;
            if (visibleFaces.size() == 1) {
                Vector3f look = this.getViewVector(viewEntity, partialTicks);
                visibleFaces.remove(
                        Facing.getFacingFromVector(look.x, look.y, look.z).getOpposite());
            }
            boolean drawEyeChunkOnly = visibleFaces.isEmpty() && !spectator;
            if (drawEyeChunkOnly) {
                this.visibleTerrainChunks.add(entry);
            }
            if (!drawEyeChunkOnly || legacyFlow) {
                useOcclusion =
                        this.enqueueEyeChunk(
                                eyePosition, eyeChunk, entry, frameIndex, spectator, useOcclusion);
            }
        }

        while (!this.visibilityDeque.isEmpty()) {
            ChunkVisibilityEntry entry = this.visibilityDeque.poll();
            RenderChunk chunk = entry.renderChunk;
            BlockPos chunkPosition = chunk.getPosition();
            if (!chunk.compiledChunk.isEmpty() || chunk.isNeedsUpdate()) {
                this.visibleTerrainChunks.add(entry);
            }
            if (ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(chunkPosition))) {
                this.visibleEntityChunks.add(entry);
            }
            if (!chunk.getCompiledChunk().getTileEntities().isEmpty()) {
                this.visibleTileEntityChunks.add(entry);
            }

            for (Facing exitFace : Facing.VALUES) {
                this.enqueueVisibleNeighbor(
                        entry, exitFace, eyePosition, camera, frameIndex, useOcclusion);
                if (legacyFlow) {
                    break;
                }
            }
            if (legacyFlow) {
                break;
            }
        }
    }

    private boolean enqueueEyeChunk(
            BlockPos eyePosition,
            RenderChunk eyeChunk,
            ChunkVisibilityEntry entry,
            int frameIndex,
            boolean spectator,
            boolean useOcclusion) {
        if (spectator && this.theWorld.getBlockState(eyePosition).getBlock().isOpaqueCube()) {
            useOcclusion = false;
        }
        eyeChunk.setFrameIndex(frameIndex);
        this.visibilityDeque.add(entry);
        return useOcclusion;
    }

    private void seedBoundaryChunks(
            BlockPos eyePosition, ICamera camera, int frameIndex, boolean firstChunkOnly) {
        int boundaryY = eyePosition.getY() > 0 ? 248 : 8;
        for (int x = -this.renderDistanceChunks; x <= this.renderDistanceChunks; x++) {
            for (int z = -this.renderDistanceChunks; z <= this.renderDistanceChunks; z++) {
                RenderChunk chunk =
                        this.viewFrustum.getRenderChunk(
                                new BlockPos((x << 4) + 8, boundaryY, (z << 4) + 8));
                if (chunk != null && camera.isBoundingBoxInFrustum(chunk.boundingBox)) {
                    chunk.setFrameIndex(frameIndex);
                    this.visibilityDeque.add(new ChunkVisibilityEntry(chunk, null, 0, null));
                }
                if (firstChunkOnly) {
                    break;
                }
            }
            if (firstChunkOnly) {
                break;
            }
        }
    }

    private void enqueueVisibleNeighbor(
            ChunkVisibilityEntry entry,
            Facing exitFace,
            BlockPos eyePosition,
            ICamera camera,
            int frameIndex,
            boolean useOcclusion) {
        Facing entryFace = entry.entryDirection;
        RenderChunk chunk = entry.renderChunk;
        if (useOcclusion
                && (entry.pathDirections.contains(exitFace.getOpposite())
                        || (entryFace != null
                                && !chunk.getCompiledChunk()
                                        .isVisible(entryFace.getOpposite(), exitFace)))) {
            return;
        }
        RenderChunk neighbor = this.getRenderChunkOffset(eyePosition, chunk, exitFace);
        if (neighbor != null
                && neighbor.setFrameIndex(frameIndex)
                && camera.isBoundingBoxInFrustum(neighbor.boundingBox)) {
            ChunkVisibilityEntry next =
                    new ChunkVisibilityEntry(neighbor, exitFace, entry.traversalDepth + 1, null);
            next.pathDirections.addAll(entry.pathDirections);
            next.pathDirections.add(exitFace);
            this.visibilityDeque.add(next);
        }
    }

    private void scheduleVisibleChunkUpdates(BlockPos cameraChunkOrigin, boolean legacyFlow) {
        this.renderDispatcher.clearPendingChunkUpdates();
        Set<RenderChunk> previousUpdates = this.chunksToUpdate;
        this.chunksToUpdate = Sets.newLinkedHashSet();
        Lagometer.timerChunkUpdate.start();
        for (ChunkVisibilityEntry entry : this.visibleTerrainChunks) {
            RenderChunk chunk = entry.renderChunk;
            if (chunk.isNeedsUpdate() || previousUpdates.contains(chunk)) {
                this.chunkVisibilityDirty = true;
                if (this.isPositionInRenderChunk(cameraChunkOrigin, chunk)) {
                    if (!chunk.hasPendingPlayerUpdate()) {
                        this.pendingNearChunkUpdates.add(chunk);
                        if (!legacyFlow) {
                            continue;
                        }
                    }

                    this.mc.mcProfiler.startSection("build near");
                    this.renderDispatcher.updateChunkNow(chunk);
                    chunk.setNeedsUpdate(false);
                    this.mc.mcProfiler.endSection();
                    if (!legacyFlow) {
                        continue;
                    }
                }

                this.chunksToUpdate.add(chunk);
                if (legacyFlow) {
                    break;
                }
            }
        }
        Lagometer.timerChunkUpdate.end();
        this.chunksToUpdate.addAll(previousUpdates);
    }

    public void broadcastSound(int i, BlockPos blockPos, int k) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        switch (i) {
            case 1013:
            case 1018:
                if (this.mc.getRenderViewEntity() != null) {
                    double d0 = blockPos.getX() - this.mc.getRenderViewEntity().posX;
                    double d1 = blockPos.getY() - this.mc.getRenderViewEntity().posY;
                    double d2 = blockPos.getZ() - this.mc.getRenderViewEntity().posZ;
                    double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                    double posX = this.mc.getRenderViewEntity().posX;
                    double posY = this.mc.getRenderViewEntity().posY;
                    double posZ = this.mc.getRenderViewEntity().posZ;
                    if (d3 > 0.0) {
                        posX += d0 / d3 * 2.0;
                        posY += d1 / d3 * 2.0;
                        posZ += d2 / d3 * 2.0;
                    }

                    if (i == 1013) {
                        this.theWorld.playSound(
                                posX, posY, posZ, "mob.wither.spawn", 1.0F, 1.0F, false);
                        if (j == 0) {
                            return;
                        }
                    }

                    this.theWorld.playSound(
                            posX, posY, posZ, "mob.enderdragon.end", 5.0F, 1.0F, false);
                }
        }
    }

    private Set getVisibleFacings(BlockPos blockPos) {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        VisGraph visGraph = new VisGraph();
        int i = j;
        BlockPos blockPosValue =
                new BlockPos(
                        blockPos.getX() >> 4 << 4,
                        blockPos.getY() >> 4 << 4,
                        blockPos.getZ() >> 4 << 4);
        Chunk chunkFromBlockCoords = this.theWorld.getChunkFromBlockCoords(blockPosValue);

        for (java.util.Iterator<?> __recoveryIterator171_94836 =
                        (BlockPos.getAllInBoxMutable(blockPosValue, blockPosValue.add(15, 15, 15)))
                                .iterator();
                (__recoveryIterator171_94836).hasNext(); ) {
            BlockPosMutableBlockPos blockPosMutableBlockPos =
                    (BlockPosMutableBlockPos) (__recoveryIterator171_94836).next();
            if (chunkFromBlockCoords.getBlock(blockPosMutableBlockPos).isOpaqueCube()) {
                visGraph.markOpaque(blockPosMutableBlockPos);
            }

            if (i != 0) {
                break;
            }
        }

        return visGraph.getVisibleFacingsFrom(blockPos);
    }

    // region String decoding support

    // endregion

    public void renderSky(float partialTicks, int renderPass) {
        boolean legacyFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        WorldProvider provider = this.mc.world.provider;
        if (Reflector.forgeGetSkyRenderer.exists()) {
            Object skyRenderer =
                    Reflector.call(provider, Reflector.forgeGetSkyRenderer, new Object[0]);
            if (skyRenderer != null) {
                Reflector.callVoid(
                        skyRenderer,
                        Reflector.forgeRenderHandlerRender,
                        new Object[] {partialTicks, this.theWorld, this.mc});
                return;
            }
        }

        if (provider.getDimensionId() == 1) {
            this.renderEndSky();
            if (!legacyFlow) {
                return;
            }
        }
        if (provider.isSurfaceWorld()) {
            this.renderSurfaceSky(partialTicks, renderPass, legacyFlow);
        }
    }

    private void renderSurfaceSky(float partialTicks, int renderPass, boolean legacyFlow) {
        GlStateManager.disableTexture2D();
        boolean shaders = OptiFineConfig.isShaders();
        if (shaders) {
            Shaders.onTexture2DDisabled();
        }

        Entity view = this.mc.getRenderViewEntity();
        Vec3 sky = this.theWorld.getSkyColor(view, partialTicks);
        sky = CustomColors.getSkyColor(sky, this.mc.world, view.posX, view.posY + 1.0, view.posZ);
        if (shaders) {
            Shaders.setSkyColor(sky);
        }

        float red = (float) sky.x;
        float green = (float) sky.y;
        float blue = (float) sky.z;
        if (renderPass != 2) {
            float stereoRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
            float stereoGreen = (red * 30.0F + green * 70.0F) / 100.0F;
            float stereoBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
            red = stereoRed;
            green = stereoGreen;
            blue = stereoBlue;
        }

        GlStateManager.color(red, green, blue);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertices = tessellator.getWorldRenderer();
        GlStateManager.depthMask(false);
        GlStateManager.enableFog();
        if (shaders) {
            Shaders.enableFog();
        }
        GlStateManager.color(red, green, blue);
        if (shaders) {
            Shaders.renderSkyFog();
        }
        if (OptiFineConfig.isSkyEnabled()) {
            this.drawSkyGeometry(this.skyVBO, this.glSkyList, legacyFlow);
        }

        GlStateManager.disableFog();
        if (shaders) {
            Shaders.disableFog();
        }
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        this.drawSunriseGradient(
                partialTicks, renderPass, shaders, legacyFlow, tessellator, vertices);
        this.drawCelestialBodies(partialTicks, shaders, legacyFlow, tessellator, vertices);
        this.drawSkyHorizon(
                partialTicks, shaders, legacyFlow, red, green, blue, tessellator, vertices);
    }

    private void drawSkyGeometry(VertexBuffer buffer, int displayList, boolean legacyFlow) {
        if (this.vboEnabled) {
            buffer.bindBuffer();
            GL11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
            GL11.glVertexPointer(3, GL11.GL_FLOAT, 12, 0L);
            buffer.drawArrays(GL11.GL_QUADS);
            buffer.unbindBuffer();
            GL11.glDisableClientState(GL11.GL_VERTEX_ARRAY);
        }
        if (!this.vboEnabled || legacyFlow) {
            GlStateManager.callList(displayList);
        }
    }

    private void drawSunriseGradient(
            float partialTicks,
            int renderPass,
            boolean shaders,
            boolean legacyFlow,
            Tessellator tessellator,
            WorldRenderer vertices) {
        float[] gradient =
                this.theWorld.provider.calcSunriseSunsetColors(
                        this.theWorld.getCelestialAngle(partialTicks), partialTicks);
        if (gradient != null && OptiFineConfig.isSunMoonEnabled()) {
            GlStateManager.disableTexture2D();
            if (shaders) {
                Shaders.onTexture2DDisabled();
            }

            GlStateManager.shadeModel(GL11.GL_SMOOTH);
            GlStateManager.pushMatrix();
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(
                    MathHelper.sin(this.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F
                            ? 180.0F
                            : 0.0F,
                    0.0F,
                    0.0F,
                    1.0F);
            GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
            float red = gradient[0];
            float green = gradient[1];
            float blue = gradient[2];
            if (renderPass != 2) {
                float stereoRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
                float stereoGreen = (red * 30.0F + green * 70.0F) / 100.0F;
                float stereoBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
                red = stereoRed;
                green = stereoGreen;
                blue = stereoBlue;
            }

            vertices.startDrawing(GL11.GL_TRIANGLE_FAN);
            vertices.setVertexColorFromFloats(red, green, blue, gradient[3]);
            vertices.addVertex(0.0, 100.0, 0.0);

            vertices.setVertexColorFromFloats(gradient[0], gradient[1], gradient[2], 0.0F);
            int segment = 0;

            while (segment <= 16) {
                float angle = segment * (float) Math.PI * 2.0F / 16.0F;
                float sin = MathHelper.sin(angle);
                float cos = MathHelper.cos(angle);
                vertices.addVertex(sin * 120.0F, cos * 120.0F, -cos * 40.0F * gradient[3]);
                segment++;
                if (legacyFlow) {
                    break;
                }
            }

            tessellator.draw();
            GlStateManager.popMatrix();
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }
    }

    private void drawCelestialBodies(
            float partialTicks,
            boolean shaders,
            boolean legacyFlow,
            Tessellator tessellator,
            WorldRenderer vertices) {
        GlStateManager.enableTexture2D();
        if (shaders) {
            Shaders.onTexture2DEnabled();
        }

        GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
        GlStateManager.pushMatrix();
        float weatherOpacity = 1.0F - this.theWorld.getRainStrength(partialTicks);

        GlStateManager.color(1.0F, 1.0F, 1.0F, weatherOpacity);
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
        CustomSky.renderSky(
                this.theWorld,
                this.renderEngine,
                this.theWorld.getCelestialAngle(partialTicks),
                weatherOpacity);
        if (shaders) {
            Shaders.preCelestialRotate();
        }

        GlStateManager.rotate(
                this.theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
        if (shaders) {
            Shaders.postCelestialRotate();
        }

        if (OptiFineConfig.isSunMoonEnabled()) {
            float radius = 30.0F;
            this.renderEngine.bindTexture(
                    net.minecraft.client.renderer.RenderGlobal.locationSunPng);
            vertices.startDrawingQuads();
            vertices.addVertexWithUV(-radius, 100.0, -radius, 0.0, 0.0);
            vertices.addVertexWithUV(radius, 100.0, -radius, 1.0, 0.0);
            vertices.addVertexWithUV(radius, 100.0, radius, 1.0, 1.0);
            vertices.addVertexWithUV(-radius, 100.0, radius, 0.0, 1.0);
            tessellator.draw();
            radius = 20.0F;
            this.renderEngine.bindTexture(
                    net.minecraft.client.renderer.RenderGlobal.locationMoonPhasesPng);
            int moonPhase = this.theWorld.getMoonPhase();
            int phaseColumn = moonPhase % 4;
            int phaseRow = moonPhase / 4 % 2;
            float minU = (phaseColumn + 0) / 4.0F;
            float minV = (phaseRow + 0) / 2.0F;
            float maxU = (phaseColumn + 1) / 4.0F;
            float maxV = (phaseRow + 1) / 2.0F;
            vertices.startDrawingQuads();
            vertices.addVertexWithUV(-radius, -100.0, radius, maxU, maxV);
            vertices.addVertexWithUV(radius, -100.0, radius, minU, maxV);
            vertices.addVertexWithUV(radius, -100.0, -radius, minU, minV);
            vertices.addVertexWithUV(-radius, -100.0, -radius, maxU, minV);
            tessellator.draw();
        }

        GlStateManager.disableTexture2D();
        if (shaders) {
            Shaders.onTexture2DDisabled();
        }

        float starBrightness = this.theWorld.getStarBrightness(partialTicks) * weatherOpacity;
        if (starBrightness > 0.0F
                && OptiFineConfig.isStarsEnabled()
                && !CustomSky.hasSkyLayers(this.theWorld)) {
            GlStateManager.color(starBrightness, starBrightness, starBrightness, starBrightness);
            this.drawSkyGeometry(this.starVBO, this.starGLCallList, legacyFlow);
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableFog();
        if (shaders) {
            Shaders.enableFog();
        }

        GlStateManager.popMatrix();
    }

    private void drawSkyHorizon(
            float partialTicks,
            boolean shaders,
            boolean legacyFlow,
            float red,
            float green,
            float blue,
            Tessellator tessellator,
            WorldRenderer vertices) {
        GlStateManager.disableTexture2D();
        if (shaders) {
            Shaders.onTexture2DDisabled();
        }

        GlStateManager.color(0.0F, 0.0F, 0.0F);
        double eyeAboveHorizon =
                this.mc.player.getPositionEyes(partialTicks).y - this.theWorld.getHorizonHeight();
        if (eyeAboveHorizon < 0.0) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0F, 12.0F, 0.0F);
            this.drawSkyGeometry(this.sky2VBO, this.glSkyList2, legacyFlow);
            GlStateManager.popMatrix();

            float voidHeight = -((float) (eyeAboveHorizon + 65.0));

            vertices.startDrawingQuads();
            vertices.setVertexColorFromRgbAndAlpha(0, 255);
            vertices.addVertex(-1.0, voidHeight, 1.0);
            vertices.addVertex(1.0, voidHeight, 1.0);
            vertices.addVertex(1.0, -1.0, 1.0);
            vertices.addVertex(-1.0, -1.0, 1.0);
            vertices.addVertex(-1.0, -1.0, -1.0);
            vertices.addVertex(1.0, -1.0, -1.0);
            vertices.addVertex(1.0, voidHeight, -1.0);
            vertices.addVertex(-1.0, voidHeight, -1.0);
            vertices.addVertex(1.0, -1.0, -1.0);
            vertices.addVertex(1.0, -1.0, 1.0);
            vertices.addVertex(1.0, voidHeight, 1.0);
            vertices.addVertex(1.0, voidHeight, -1.0);
            vertices.addVertex(-1.0, voidHeight, -1.0);
            vertices.addVertex(-1.0, voidHeight, 1.0);
            vertices.addVertex(-1.0, -1.0, 1.0);
            vertices.addVertex(-1.0, -1.0, -1.0);
            vertices.addVertex(-1.0, -1.0, -1.0);
            vertices.addVertex(-1.0, -1.0, 1.0);
            vertices.addVertex(1.0, -1.0, 1.0);
            vertices.addVertex(1.0, -1.0, -1.0);
            tessellator.draw();
        }

        boolean coloredHorizon = this.theWorld.provider.isSkyColored();
        if (coloredHorizon) {
            GlStateManager.color(red * 0.2F + 0.04F, green * 0.2F + 0.04F, blue * 0.6F + 0.1F);
        }
        if (!coloredHorizon || legacyFlow) {
            GlStateManager.color(red, green, blue);
        }

        if (this.mc.gameSettings.renderDistanceChunks <= 4) {
            GlStateManager.color(
                    this.mc.entityRenderer.fogColorRed,
                    this.mc.entityRenderer.fogColorGreen,
                    this.mc.entityRenderer.fogColorBlue);
        }

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -((float) (eyeAboveHorizon - 16.0)), 0.0F);
        if (OptiFineConfig.isSkyEnabled()) {
            GlStateManager.callList(this.glSkyList2);
        }

        GlStateManager.popMatrix();
        GlStateManager.enableTexture2D();
        if (shaders) {
            Shaders.onTexture2DEnabled();
        }

        GlStateManager.depthMask(true);
    }

    public static void drawOutlinedBoundingBox(BoundingBox boundingBox, int p171_1) {
        net.minecraft.client.renderer.Tessellator instance =
                net.minecraft.client.renderer.Tessellator.getInstance();
        net.minecraft.client.renderer.WorldRenderer worldRenderer = instance.getWorldRenderer();

        try {
            worldRenderer.startDrawing(3);
            if (p171_1 != -1) {
                worldRenderer.setOpaqueVertexColorFromRgb(p171_1);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception2) {
            throw (jsonsyntaxexception2);
        }

        try {
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
            instance.draw();
            worldRenderer.startDrawing(3);
            if (p171_1 != -1) {
                worldRenderer.setOpaqueVertexColorFromRgb(p171_1);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw (jsonsyntaxexception);
        }

        try {
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
            worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
            worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
            instance.draw();
            worldRenderer.startDrawing(1);
            if (p171_1 != -1) {
                worldRenderer.setOpaqueVertexColorFromRgb(p171_1);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception1) {
            throw (jsonsyntaxexception1);
        }

        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
        worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
        instance.draw();
    }

    private void spawnParticle(
            EnumParticleTypes enumParticleTypes,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        this.spawnParticle(
                enumParticleTypes.getParticleID(),
                enumParticleTypes.getShouldIgnoreRange(),
                d0,
                d1,
                d2,
                d3,
                d4,
                d5,
                aint);
    }

    public void playRecord(String text, BlockPos blockPos) {
        ISound iSound = (ISound) this.mapSoundPositions.get(blockPos);
        if (iSound != null) {
            this.mc.getSoundHandler().stopSound(iSound);
            this.mapSoundPositions.remove(blockPos);
        }

        if (text != null) {
            ItemRecord record = ItemRecord.getRecord(text);
            if (record != null) {
                this.mc.ingameGUI.setRecordPlayingMessage(record.getRecordNameLocal());
            }

            ResourceLocation resourceLocation = null;
            if (Reflector.Gg.exists() && record != null) {
                resourceLocation =
                        (ResourceLocation)
                                Reflector.call(record, Reflector.Gg, new Object[] {text});
            }

            if (resourceLocation == null) {
                resourceLocation = new ResourceLocation(text);
            }

            PositionedSoundRecord recordSoundAtPosition =
                    PositionedSoundRecord.createRecordSoundAtPosition(
                            resourceLocation, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            this.mapSoundPositions.put(blockPos, recordSoundAtPosition);
            this.mc.getSoundHandler().playSound(recordSoundAtPosition);
        }
    }

    public void onEntityRemoved(Entity entity) {
        try {
            if (optifine.OptiFineConfig.isDynamicLights()) {
                optifine.DynamicLights.entityRemoved(entity, this);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw (jsonsyntaxexception);
        }
    }

    public int renderBlockLayer(
            EnumWorldBlockLayer enumWorldBlockLayer, double d3, int j2, Entity entity) {
        int i2 = WorldRenderer.getLegacyRenderFlowMarker();
        RenderHelper.disableStandardItemLighting();
        int i = i2;
        if (enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
            this.mc.mcProfiler.startSection("translucent_sort");
            double d0 = entity.posX - this.lastTranslucentSortX;
            double d1 = entity.posY - this.lastTranslucentSortY;
            double d2 = entity.posZ - this.lastTranslucentSortZ;
            if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0) {
                this.lastTranslucentSortX = entity.posX;
                this.lastTranslucentSortY = entity.posY;
                this.lastTranslucentSortZ = entity.posZ;
                int i1 = 0;
                Iterator iterator = this.visibleTerrainChunks.iterator();
                this.pendingTransparencyUpdates.clear();

                while (iterator.hasNext()) {
                    ChunkVisibilityEntry chunkVisibilityEntry =
                            (ChunkVisibilityEntry) iterator.next();
                    if (chunkVisibilityEntry.renderChunk.compiledChunk.isLayerStarted(
                                    enumWorldBlockLayer)
                            && i1++ < 15) {
                        this.pendingTransparencyUpdates.add(chunkVisibilityEntry.renderChunk);
                    }

                    if (i == 0) {
                        break;
                    }
                }
            }

            this.mc.mcProfiler.endSection();
        }

        this.mc.mcProfiler.startSection("filterempty");
        int j1 = 0;
        int j = enumWorldBlockLayer == EnumWorldBlockLayer.TRANSLUCENT ? 1 : 0;
        int k1 = j != 0 ? this.visibleTerrainChunks.size() - 1 : 0;
        int k = j != 0 ? -1 : this.visibleTerrainChunks.size();
        int l1 = j != 0 ? -1 : 1;
        int l = k1;

        while (l != k) {
            RenderChunk renderChunk =
                    ((ChunkVisibilityEntry) this.visibleTerrainChunks.get(l)).renderChunk;
            if (!renderChunk.getCompiledChunk().isLayerEmpty(enumWorldBlockLayer)) {
                j1++;
                this.renderContainer.addRenderChunk(renderChunk, enumWorldBlockLayer);
            }

            l += l1;
            if (i == 0) {
                break;
            }
        }

        if (j1 == 0) {
            this.mc.mcProfiler.endSection();
            return j1;
        }

        if (OptiFineConfig.isFogOff() && this.mc.entityRenderer.terrainFogActive) {
            GlStateManager.disableFog();
        }

        this.mc.mcProfiler.endStartSection("render_" + enumWorldBlockLayer);
        this.renderBlockLayer(enumWorldBlockLayer);
        this.mc.mcProfiler.endSection();
        return j1;
    }

    public void spawnParticle(
            int i,
            boolean flag,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... aint) {
        try {
            this.spawnEntityFX(i, flag, d0, d1, d2, d3, d4, d5, aint);
        } catch (Throwable throwable) {
            CrashReport crashReport =
                    CrashReport.makeCrashReport(throwable, "Exception while adding particle");
            CrashReportCategory crashReportCategory =
                    crashReport.makeCategory("Particle being added");
            crashReportCategory.addCrashSection("ID", i);
            if (aint != null) {
                crashReportCategory.addCrashSection("Parameters", aint);
            }

            crashReportCategory.addCrashSectionCallable(
                    "Position", new RenderParticlePositionCrashDetail(this, d0, d1, d2));
            throw new ReportedException(crashReport);
        }
    }

    public RenderGlobal(MinecraftClient client) {
        this.damagedBlocks = Maps.newHashMap();
        this.mapSoundPositions = Maps.newHashMap();
        this.destroyBlockIcons = new TextureAtlasSprite[10];
        this.renderDispatcher = new ChunkRenderDispatcher();
        this.debugTerrainMatrix = new Vector4f[8];
        this.debugTerrainFrustumPosition = new Vector3d();
        this.chunkVisibilityDirty = true;
        this.pendingTransparencyUpdates = new LinkedHashSet();
        this.pendingNearChunkUpdates = new LinkedHashSet();
        this.chunksToUpdate = Sets.newLinkedHashSet();
        this.visibleTerrainChunks = Lists.newArrayListWithCapacity(69696);
        this.starGLCallList = -1;
        int renderFlowMarker = WorldRenderer.getLegacyRenderFlowMarker();
        this.glSkyList = -1;
        this.glSkyList2 = -1;
        this.frustumUpdatePosX = Double.MIN_VALUE;
        this.frustumUpdatePosY = Double.MIN_VALUE;
        this.frustumUpdatePosZ = Double.MIN_VALUE;
        this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
        this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
        this.lastVisibilityViewX = Double.MIN_VALUE;
        this.lastVisibilityViewY = Double.MIN_VALUE;
        this.lastVisibilityViewZ = Double.MIN_VALUE;
        this.lastVisibilityViewPitch = Double.MIN_VALUE;
        this.lastVisibilityViewYaw = Double.MIN_VALUE;
        this.renderDistanceChunks = -1;
        this.renderEntitiesStartupCounter = 2;
        this.debugFixTerrainFrustum = false;
        this.vboEnabled = false;
        this.visibilityDeque = new ArrayDeque();
        this.visibleEntityChunks = new ArrayList(1024);
        this.visibleTileEntityChunks = new ArrayList(1024);
        this.mainTerrainChunks = new ArrayList(1024);
        this.mainEntityChunks = new ArrayList(1024);
        this.mainTileEntityChunks = new ArrayList(1024);
        this.shadowTerrainChunks = new ArrayList(1024);
        this.shadowEntityChunks = new ArrayList(1024);
        this.shadowTileEntityChunks = new ArrayList(1024);
        this.renderDistance = 0;
        this.renderDistanceSq = 0;
        this.cloudRenderer = new CloudRenderer(client);
        this.mc = client;
        this.renderManager = client.getRenderManager();
        this.renderEngine = client.getTextureManager();
        this.renderEngine.bindTexture(
                net.minecraft.client.renderer.RenderGlobal.locationForcefieldPng);
        GL11.glTexParameteri(3553, 10242, 10497);
        GL11.glTexParameteri(3553, 10243, 10497);
        GlStateManager.bindTexture(0);
        this.updateDestroyBlockIcons();
        this.vboEnabled = OpenGlHelper.useVbo();
        if (this.vboEnabled) {
            this.renderContainer = new VboRenderList();
            this.renderChunkFactory = new VboChunkFactory();
        }
        if (!this.vboEnabled || renderFlowMarker == 0) {
            this.renderContainer = new RenderList();
            this.renderChunkFactory = new ListChunkFactory();
        }

        this.vertexBufferFormat = new VertexFormat();
        this.vertexBufferFormat.addElement(
                new VertexFormatElement(
                        0,
                        VertexFormatElementEnumType.FLOAT,
                        VertexFormatElementEnumUseage.POSITION,
                        3));
        this.generateStars();
        this.generateSky();
        this.generateSky2();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            WorldRenderer.setLegacyRenderFlowMarker(renderFlowMarker + 1);
        }
    }

    public void onEntityAdded(Entity entity) {
        try {
            optifine.RandomMobs.entityLoaded(entity, this.theWorld);
            if (optifine.OptiFineConfig.isDynamicLights()) {
                optifine.DynamicLights.entityAdded(entity, this);
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw (jsonsyntaxexception);
        }
    }

    private void renderCloudsFancy(float partialTicks, int renderPass) {
        this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, partialTicks);
        float geometryPartialTicks = 0.0F;
        GlStateManager.disableCull();
        boolean fullCloudPasses = WorldRenderer.getLegacyRenderFlowMarker() != 0;
        float cameraY =
                (float)
                        (this.mc.getRenderViewEntity().lastTickPosY
                                + (this.mc.getRenderViewEntity().posY
                                                - this.mc.getRenderViewEntity().lastTickPosY)
                                        * geometryPartialTicks);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertices = tessellator.getWorldRenderer();

        double animationTicks = this.cloudTickCounter + geometryPartialTicks;
        double cloudX =
                (this.mc.getRenderViewEntity().prevPosX
                                + (this.mc.getRenderViewEntity().posX
                                                - this.mc.getRenderViewEntity().prevPosX)
                                        * geometryPartialTicks
                                + animationTicks * 0.03F)
                        / 12.0;
        double cloudZ =
                (this.mc.getRenderViewEntity().prevPosZ
                                        + (this.mc.getRenderViewEntity().posZ
                                                        - this.mc.getRenderViewEntity().prevPosZ)
                                                * geometryPartialTicks)
                                / 12.0
                        + 0.33F;
        float cloudHeight = this.theWorld.provider.getCloudHeight() - cameraY + 0.33F;
        cloudHeight += this.mc.gameSettings.ofCloudsHeight * 128.0F;
        int wrapX = MathHelper.floor(cloudX / 2048.0);
        int wrapZ = MathHelper.floor(cloudZ / 2048.0);
        cloudX -= wrapX * 2048;
        cloudZ -= wrapZ * 2048;
        this.renderEngine.bindTexture(net.minecraft.client.renderer.RenderGlobal.locationCloudsPng);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        Vec3 cloudColor = this.theWorld.getCloudColour(geometryPartialTicks);
        float red = (float) cloudColor.x;
        float green = (float) cloudColor.y;
        float blue = (float) cloudColor.z;
        if (renderPass != 2) {
            float stereoRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
            float stereoGreen = (red * 30.0F + green * 70.0F) / 100.0F;
            float stereoBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
            red = stereoRed;
            green = stereoGreen;
            blue = stereoBlue;
        }

        float baseU = MathHelper.floor(cloudX) * 0.00390625F;
        float baseV = MathHelper.floor(cloudZ) * 0.00390625F;
        float fractionalX = (float) (cloudX - MathHelper.floor(cloudX));
        float fractionalZ = (float) (cloudZ - MathHelper.floor(cloudZ));

        GlStateManager.scale(12.0F, 1.0F, 12.0F);
        int passes = fullCloudPasses ? 2 : 1;
        for (int cloudPass = 0; cloudPass < passes; cloudPass++) {
            if (cloudPass == 0) {
                GlStateManager.colorMask(false, false, false, false);
            }
            if (cloudPass != 0 || !fullCloudPasses) {
                if (renderPass == 0) {
                    GlStateManager.colorMask(false, true, true, true);
                }
                if (renderPass == 1 || (renderPass == 0 && !fullCloudPasses)) {
                    GlStateManager.colorMask(true, false, false, true);
                }
                if (renderPass == 2 || ((renderPass == 0 || renderPass == 1) && !fullCloudPasses)) {
                    GlStateManager.colorMask(true, true, true, true);
                }
            }
            this.cloudRenderer.renderDisplayList();
        }

        if (this.cloudRenderer.shouldRebuildDisplayList()) {
            this.cloudRenderer.startUpdateGlList();
            int tileX = -3;

            while (tileX <= 4) {
                int tileZ = -3;

                while (tileZ <= 4) {
                    vertices.startDrawingQuads();
                    float tileU = tileX * 8;
                    float tileV = tileZ * 8;
                    float vertexX = tileU - fractionalX;
                    float vertexZ = tileV - fractionalZ;
                    if (cloudHeight > -5.0F) {
                        vertices.setVertexColorFromFloats(
                                red * 0.7F, green * 0.7F, blue * 0.7F, 0.8F);
                        vertices.setVertexNormal(0.0F, -1.0F, 0.0F);
                        vertices.addVertexWithUV(
                                vertexX + 0.0F,
                                cloudHeight + 0.0F,
                                vertexZ + 8.0F,
                                (tileU + 0.0F) * 0.00390625F + baseU,
                                (tileV + 8.0F) * 0.00390625F + baseV);
                        vertices.addVertexWithUV(
                                vertexX + 8.0F,
                                cloudHeight + 0.0F,
                                vertexZ + 8.0F,
                                (tileU + 8.0F) * 0.00390625F + baseU,
                                (tileV + 8.0F) * 0.00390625F + baseV);
                        vertices.addVertexWithUV(
                                vertexX + 8.0F,
                                cloudHeight + 0.0F,
                                vertexZ + 0.0F,
                                (tileU + 8.0F) * 0.00390625F + baseU,
                                (tileV + 0.0F) * 0.00390625F + baseV);
                        vertices.addVertexWithUV(
                                vertexX + 0.0F,
                                cloudHeight + 0.0F,
                                vertexZ + 0.0F,
                                (tileU + 0.0F) * 0.00390625F + baseU,
                                (tileV + 0.0F) * 0.00390625F + baseV);
                    }

                    if (cloudHeight <= 5.0F) {
                        vertices.setVertexColorFromFloats(red, green, blue, 0.8F);
                        vertices.setVertexNormal(0.0F, 1.0F, 0.0F);
                        vertices.addVertexWithUV(
                                vertexX + 0.0F,
                                cloudHeight + 4.0F - 9.765625E-4F,
                                vertexZ + 8.0F,
                                (tileU + 0.0F) * 0.00390625F + baseU,
                                (tileV + 8.0F) * 0.00390625F + baseV);
                        vertices.addVertexWithUV(
                                vertexX + 8.0F,
                                cloudHeight + 4.0F - 9.765625E-4F,
                                vertexZ + 8.0F,
                                (tileU + 8.0F) * 0.00390625F + baseU,
                                (tileV + 8.0F) * 0.00390625F + baseV);
                        vertices.addVertexWithUV(
                                vertexX + 8.0F,
                                cloudHeight + 4.0F - 9.765625E-4F,
                                vertexZ + 0.0F,
                                (tileU + 8.0F) * 0.00390625F + baseU,
                                (tileV + 0.0F) * 0.00390625F + baseV);
                        vertices.addVertexWithUV(
                                vertexX + 0.0F,
                                cloudHeight + 4.0F - 9.765625E-4F,
                                vertexZ + 0.0F,
                                (tileU + 0.0F) * 0.00390625F + baseU,
                                (tileV + 0.0F) * 0.00390625F + baseV);
                    }

                    vertices.setVertexColorFromFloats(red * 0.9F, green * 0.9F, blue * 0.9F, 0.8F);
                    if (tileX > -1) {
                        vertices.setVertexNormal(-1.0F, 0.0F, 0.0F);
                        int westStrip = 0;

                        while (westStrip < 8) {
                            vertices.addVertexWithUV(
                                    vertexX + westStrip + 0.0F,
                                    cloudHeight + 0.0F,
                                    vertexZ + 8.0F,
                                    (tileU + westStrip + 0.5F) * 0.00390625F + baseU,
                                    (tileV + 8.0F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + westStrip + 0.0F,
                                    cloudHeight + 4.0F,
                                    vertexZ + 8.0F,
                                    (tileU + westStrip + 0.5F) * 0.00390625F + baseU,
                                    (tileV + 8.0F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + westStrip + 0.0F,
                                    cloudHeight + 4.0F,
                                    vertexZ + 0.0F,
                                    (tileU + westStrip + 0.5F) * 0.00390625F + baseU,
                                    (tileV + 0.0F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + westStrip + 0.0F,
                                    cloudHeight + 0.0F,
                                    vertexZ + 0.0F,
                                    (tileU + westStrip + 0.5F) * 0.00390625F + baseU,
                                    (tileV + 0.0F) * 0.00390625F + baseV);
                            westStrip++;
                            if (!fullCloudPasses) {
                                break;
                            }
                        }
                    }

                    if (tileX <= 1) {
                        vertices.setVertexNormal(1.0F, 0.0F, 0.0F);
                        int eastStrip = 0;

                        while (eastStrip < 8) {
                            vertices.addVertexWithUV(
                                    vertexX + eastStrip + 1.0F - 9.765625E-4F,
                                    cloudHeight + 0.0F,
                                    vertexZ + 8.0F,
                                    (tileU + eastStrip + 0.5F) * 0.00390625F + baseU,
                                    (tileV + 8.0F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + eastStrip + 1.0F - 9.765625E-4F,
                                    cloudHeight + 4.0F,
                                    vertexZ + 8.0F,
                                    (tileU + eastStrip + 0.5F) * 0.00390625F + baseU,
                                    (tileV + 8.0F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + eastStrip + 1.0F - 9.765625E-4F,
                                    cloudHeight + 4.0F,
                                    vertexZ + 0.0F,
                                    (tileU + eastStrip + 0.5F) * 0.00390625F + baseU,
                                    (tileV + 0.0F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + eastStrip + 1.0F - 9.765625E-4F,
                                    cloudHeight + 0.0F,
                                    vertexZ + 0.0F,
                                    (tileU + eastStrip + 0.5F) * 0.00390625F + baseU,
                                    (tileV + 0.0F) * 0.00390625F + baseV);
                            eastStrip++;
                            if (!fullCloudPasses) {
                                break;
                            }
                        }
                    }

                    vertices.setVertexColorFromFloats(red * 0.8F, green * 0.8F, blue * 0.8F, 0.8F);
                    if (tileZ > -1) {
                        vertices.setVertexNormal(0.0F, 0.0F, -1.0F);
                        int northStrip = 0;

                        while (northStrip < 8) {
                            vertices.addVertexWithUV(
                                    vertexX + 0.0F,
                                    cloudHeight + 4.0F,
                                    vertexZ + northStrip + 0.0F,
                                    (tileU + 0.0F) * 0.00390625F + baseU,
                                    (tileV + northStrip + 0.5F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + 8.0F,
                                    cloudHeight + 4.0F,
                                    vertexZ + northStrip + 0.0F,
                                    (tileU + 8.0F) * 0.00390625F + baseU,
                                    (tileV + northStrip + 0.5F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + 8.0F,
                                    cloudHeight + 0.0F,
                                    vertexZ + northStrip + 0.0F,
                                    (tileU + 8.0F) * 0.00390625F + baseU,
                                    (tileV + northStrip + 0.5F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + 0.0F,
                                    cloudHeight + 0.0F,
                                    vertexZ + northStrip + 0.0F,
                                    (tileU + 0.0F) * 0.00390625F + baseU,
                                    (tileV + northStrip + 0.5F) * 0.00390625F + baseV);
                            northStrip++;
                            if (!fullCloudPasses) {
                                break;
                            }
                        }
                    }

                    if (tileZ <= 1) {
                        vertices.setVertexNormal(0.0F, 0.0F, 1.0F);
                        int southStrip = 0;

                        while (southStrip < 8) {
                            vertices.addVertexWithUV(
                                    vertexX + 0.0F,
                                    cloudHeight + 4.0F,
                                    vertexZ + southStrip + 1.0F - 9.765625E-4F,
                                    (tileU + 0.0F) * 0.00390625F + baseU,
                                    (tileV + southStrip + 0.5F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + 8.0F,
                                    cloudHeight + 4.0F,
                                    vertexZ + southStrip + 1.0F - 9.765625E-4F,
                                    (tileU + 8.0F) * 0.00390625F + baseU,
                                    (tileV + southStrip + 0.5F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + 8.0F,
                                    cloudHeight + 0.0F,
                                    vertexZ + southStrip + 1.0F - 9.765625E-4F,
                                    (tileU + 8.0F) * 0.00390625F + baseU,
                                    (tileV + southStrip + 0.5F) * 0.00390625F + baseV);
                            vertices.addVertexWithUV(
                                    vertexX + 0.0F,
                                    cloudHeight + 0.0F,
                                    vertexZ + southStrip + 1.0F - 9.765625E-4F,
                                    (tileU + 0.0F) * 0.00390625F + baseU,
                                    (tileV + southStrip + 0.5F) * 0.00390625F + baseV);
                            southStrip++;
                            if (!fullCloudPasses) {
                                break;
                            }
                        }
                    }

                    tessellator.draw();
                    tileZ++;
                    if (!fullCloudPasses) {
                        break;
                    }
                }

                tileX++;
                if (!fullCloudPasses) {
                    break;
                }
            }

            this.cloudRenderer.endUpdateGlList();
        }

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
    }

    public RenderChunk getRenderChunk(RenderChunk renderChunk, Facing facing) {
        try {
            if (renderChunk == null) {
                return null;
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw (jsonsyntaxexception);
        }

        net.minecraft.util.BlockPos blockPos = renderChunk.getPositionOffset16(facing);
        return this.viewFrustum.getRenderChunk(blockPos);
    }

    private RenderChunk getRenderChunkOffset(
            BlockPos blockPos, RenderChunk renderChunk, Facing facing) {
        BlockPos blockPosValue = renderChunk.getPositionOffset16(facing);
        if (blockPosValue.getY() >= 0 && blockPosValue.getY() < 256) {
            int i = MathHelper.abs(blockPos.getX() - blockPosValue.getX());
            int j = MathHelper.abs(blockPos.getZ() - blockPosValue.getZ());
            if (OptiFineConfig.isFogOff()) {
                if (i > this.renderDistance || j > this.renderDistance) {
                    return null;
                }
            } else {
                int k = i * i + j * j;
                if (k > this.renderDistanceSq) {
                    return null;
                }
            }

            return this.viewFrustum.getRenderChunk(blockPosValue);
        } else {
            return null;
        }
    }

    private boolean isPositionInRenderChunk(BlockPos blockPos, RenderChunk renderChunk) {
        BlockPos blockPosValue = renderChunk.getPosition();
        return MathHelper.abs(blockPos.getX() - blockPosValue.getX()) <= 16
                && MathHelper.abs(blockPos.getY() - blockPosValue.getY()) <= 16
                && MathHelper.abs(blockPos.getZ() - blockPosValue.getZ()) <= 16;
    }

    private void updateDestroyBlockIcons() {
        TextureMap textureMapBlocks = this.mc.getTextureMapBlocks();
        int k = WorldRenderer.getLegacyRenderFlowToken();
        int j = 0;
        int i = k;

        while (j < this.destroyBlockIcons.length) {
            this.destroyBlockIcons[j] =
                    textureMapBlocks.getAtlasSprite("minecraft:blocks/destroy_stage_" + j);
            j++;
            if (i != 0) {
                break;
            }
        }
    }

    private void beginBlockDamageRender() {
        try {
            net.minecraft.client.renderer.GlStateManager.tryBlendFuncSeparate(774, 768, 1, 0);
            net.minecraft.client.renderer.GlStateManager.enableBlend();
            net.minecraft.client.renderer.GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
            net.minecraft.client.renderer.GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            net.minecraft.client.renderer.GlStateManager.enablePolygonOffset();
            net.minecraft.client.renderer.GlStateManager.alphaFunc(516, 0.1F);
            net.minecraft.client.renderer.GlStateManager.enableAlpha();
            net.minecraft.client.renderer.GlStateManager.pushMatrix();
            if (optifine.OptiFineConfig.isShaders()) {
                shadersmod.client.ShadersRender.beginBlockDamage();
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw (jsonsyntaxexception);
        }
    }

    public String getDebugInfoChunks() {
        boolean legacyFirstChunkOnly = WorldRenderer.getLegacyRenderFlowToken() != 0;
        int totalChunks = this.viewFrustum.renderChunks.length;
        int visibleChunks = 0;
        for (Object entry : this.visibleTerrainChunks) {
            ChunkVisibilityEntry info = (ChunkVisibilityEntry) entry;
            CompiledChunk chunk = info.renderChunk.compiledChunk;
            if (chunk != CompiledChunk.DUMMY && !chunk.isEmpty()) {
                visibleChunks++;
            }
            if (legacyFirstChunkOnly) {
                break;
            }
        }

        return String.format(
                "C: %d/%d %sD: %d, %s",
                visibleChunks,
                totalChunks,
                this.mc.renderChunksMany ? "(s) " : packedTextFragments[0],
                this.renderDistanceChunks,
                this.renderDispatcher.getDebugInfo());
    }

    public boolean hasCloudFog(double d0, double d1, double d2, float f) {
        return false;
    }

    public void drawSelectionBox(
            PlayerEntity player, RayTraceResult hit, int pass, float partialTicks) {
        boolean replaceVanillaOutline = WorldRenderer.getLegacyRenderFlowMarker() != 0;
        if (pass != 0 || hit.typeOfHit != RayHitType.BLOCK) {
            return;
        }

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
        GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
        GL11.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        if (OptiFineConfig.isShaders()) {
            Shaders.onTexture2DDisabled();
        }
        GlStateManager.depthMask(false);

        BlockPos position = hit.getBlockPos();
        Block block = this.theWorld.getBlockState(position).getBlock();
        if (block.getMaterial() != Material.AIR
                && this.theWorld.getWorldBorder().contains(position)) {
            block.setBlockBoundsBasedOnState(this.theWorld, position);
            double cameraX =
                    player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
            double cameraY =
                    player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
            double cameraZ =
                    player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
            RemixClient.INSTANCE.getClientServices();
            CameraModule camera = ClientServices.moduleManager.camera;
            boolean customOutline = camera.isEnabled() && camera.selectionBoxSetting.asBoolean();
            if (customOutline) {
                this.drawRemixSelectionBox(block, position, cameraX, cameraY, cameraZ);
            }
            if (!customOutline || !replaceVanillaOutline) {
                drawOutlinedBoundingBox(
                        selectionBounds(this.theWorld, block, position, cameraX, cameraY, cameraZ),
                        -1);
            }
        }

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        if (OptiFineConfig.isShaders()) {
            Shaders.onTexture2DEnabled();
        }
        GlStateManager.disableBlend();
    }

    private void drawRemixSelectionBox(
            Block block, BlockPos position, double cameraX, double cameraY, double cameraZ) {
        ClientServices services = (ClientServices) RemixClient.INSTANCE.getClientServices();
        Color color = new Color(services.getThemeColor()).brighter();
        WorldRenderUtils.drawESPBoundingBox(
                selectionBounds(this.mc.world, block, position, cameraX, cameraY, cameraZ),
                color.getRGB(),
                color.darker().getRGB());
        WorldRenderUtils.drawOutlinedBox(
                selectionBounds(this.mc.world, block, position, cameraX, cameraY, cameraZ));
    }

    private static BoundingBox selectionBounds(
            World world,
            Block block,
            BlockPos position,
            double cameraX,
            double cameraY,
            double cameraZ) {
        return block.getSelectedBoundingBox(world, position)
                .expand(0.002F, 0.002F, 0.002F)
                .offset(-cameraX, -cameraY, -cameraZ);
    }

    private void generateSky() {
        Tessellator instance = Tessellator.getInstance();
        int j = WorldRenderer.getLegacyRenderFlowMarker();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        int i = j;
        if (this.skyVBO != null) {
            this.skyVBO.deleteGlBuffers();
        }

        if (this.glSkyList >= 0) {
            GLAllocation.b(this.glSkyList);
            this.glSkyList = -1;
        }

        if (this.vboEnabled) {
            this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
            this.renderSky(worldRenderer, 16.0F, false);
            worldRenderer.draw();
            worldRenderer.reset();
            this.skyVBO.uploadVertexData(
                    worldRenderer.getByteBuffer(), worldRenderer.getDrawByteCount());
            if (i != 0) {
                return;
            }
        }

        this.glSkyList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.glSkyList, 4864);
        this.renderSky(worldRenderer, 16.0F, false);
        instance.draw();
        GL11.glEndList();
    }

    public void playSoundToNearExcept(
            PlayerEntity playerEntity,
            String text,
            double d0,
            double d1,
            double d2,
            float f,
            float f1) {}

    protected Vector3f getViewVector(Entity entity, double d0) {
        float f =
                (float)
                        (entity.prevRotationPitch
                                + (entity.rotationPitch - entity.prevRotationPitch) * d0);
        float f1 =
                (float)
                        (entity.prevRotationYaw
                                + (entity.rotationYaw - entity.prevRotationYaw) * d0);
        if (MinecraftClient.getMinecraft().gameSettings.thirdPersonView == 2) {
            f += 180.0F;
        }

        float f2 = MathHelper.cos(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f3 = MathHelper.sin(-f1 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f4 = -MathHelper.cos(-f * (float) (Math.PI / 180.0));
        float f5 = MathHelper.sin(-f * (float) (Math.PI / 180.0));
        return new Vector3f(f3 * f4, f5, f2 * f4);
    }

    public int getCountRenderers() {
        return this.viewFrustum.renderChunks.length;
    }

    public void renderEntities(Entity viewEntity, ICamera camera, float partialTicks) {
        boolean legacyFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        int renderPass =
                Reflector.MinecraftForgeClient_getRenderPass.exists()
                        ? Reflector.callInt(
                                Reflector.MinecraftForgeClient_getRenderPass, new Object[0])
                        : 0;
        if (this.renderEntitiesStartupCounter > 0) {
            if (renderPass > 0) {
                return;
            }
            this.renderEntitiesStartupCounter--;
            if (!legacyFlow) {
                return;
            }
        }

        double viewX = viewEntity.prevPosX + (viewEntity.posX - viewEntity.prevPosX) * partialTicks;
        double viewY = viewEntity.prevPosY + (viewEntity.posY - viewEntity.prevPosY) * partialTicks;
        double viewZ = viewEntity.prevPosZ + (viewEntity.posZ - viewEntity.prevPosZ) * partialTicks;
        this.theWorld.theProfiler.startSection("prepare");
        TileEntityRendererDispatcher.instance.cacheActiveRenderInfo(
                this.theWorld,
                this.mc.getTextureManager(),
                this.mc.fontRendererObj,
                this.mc.getRenderViewEntity(),
                partialTicks);
        this.renderManager.cacheActiveRenderInfo(
                this.theWorld,
                this.mc.fontRendererObj,
                this.mc.getRenderViewEntity(),
                this.mc.pointedEntity,
                this.mc.gameSettings,
                partialTicks);
        if (renderPass == 0) {
            this.totalEntityCount = 0;
            this.renderedEntityCount = 0;
            this.hiddenEntityCount = 0;
            this.renderedTileEntityCount = 0;
        }

        Entity cameraEntity = this.mc.getRenderViewEntity();
        double staticPlayerX =
                cameraEntity.lastTickPosX
                        + (cameraEntity.posX - cameraEntity.lastTickPosX) * partialTicks;
        double staticPlayerY =
                cameraEntity.lastTickPosY
                        + (cameraEntity.posY - cameraEntity.lastTickPosY) * partialTicks;
        double staticPlayerZ =
                cameraEntity.lastTickPosZ
                        + (cameraEntity.posZ - cameraEntity.lastTickPosZ) * partialTicks;
        TileEntityRendererDispatcher.staticPlayerX = staticPlayerX;
        TileEntityRendererDispatcher.staticPlayerY = staticPlayerY;
        TileEntityRendererDispatcher.staticPlayerZ = staticPlayerZ;
        this.renderManager.setRenderPosition(staticPlayerX, staticPlayerY, staticPlayerZ);
        this.mc.entityRenderer.enableLightmap();
        this.theWorld.theProfiler.endStartSection("global");
        List<Entity> loadedEntities = this.theWorld.getLoadedEntities();
        if (renderPass == 0) {
            this.totalEntityCount = loadedEntities.size();
        }

        if (OptiFineConfig.isFogOff() && this.mc.entityRenderer.terrainFogActive) {
            GlStateManager.disableFog();
        }

        boolean forgeEntityPass = Reflector.forgeEntityShouldRenderInPass.exists();
        boolean forgeTilePass = Reflector.forgeTileShouldRenderInPass.exists();
        this.renderWeatherEntities(
                partialTicks, renderPass, viewX, viewY, viewZ, forgeEntityPass, legacyFlow);
        if (this.isRenderEntityOutlines()) {
            this.renderSpectatorOutlines(
                    loadedEntities,
                    camera,
                    partialTicks,
                    renderPass,
                    viewX,
                    viewY,
                    viewZ,
                    forgeEntityPass,
                    legacyFlow);
        }

        this.theWorld.theProfiler.endStartSection("entities");
        boolean shaders = OptiFineConfig.isShaders();
        if (shaders) {
            Shaders.beginEntities();
        }
        boolean fancyGraphics = this.mc.gameSettings.fancyGraphics;
        this.mc.gameSettings.fancyGraphics = OptiFineConfig.isDroppedItemsFancy();
        this.renderChunkEntities(
                camera,
                partialTicks,
                renderPass,
                viewX,
                viewY,
                viewZ,
                forgeEntityPass,
                shaders,
                legacyFlow);
        this.mc.gameSettings.fancyGraphics = fancyGraphics;

        FontRenderer fontRenderer = TileEntityRendererDispatcher.instance.getFontRenderer();
        if (shaders) {
            Shaders.endEntities();
            Shaders.beginBlockEntities();
        }
        this.theWorld.theProfiler.endStartSection("blockentities");
        RenderHelper.enableStandardItemLighting();
        if (Reflector.forgeTileDispatcherPreDrawBatch.exists()) {
            Reflector.call(
                    TileEntityRendererDispatcher.instance,
                    Reflector.forgeTileDispatcherPreDrawBatch,
                    new Object[0]);
        }
        this.renderChunkTileEntities(
                camera, partialTicks, renderPass, fontRenderer, forgeTilePass, shaders, legacyFlow);
        if (Reflector.forgeTileDispatcherDrawBatch.exists()) {
            Reflector.call(
                    TileEntityRendererDispatcher.instance,
                    Reflector.forgeTileDispatcherDrawBatch,
                    new Object[] {renderPass});
        }

        this.beginBlockDamageRender();
        this.renderTileEntityDamage(
                camera, partialTicks, renderPass, forgeTilePass, shaders, legacyFlow);
        this.endBlockDamageRender();
        this.mc.entityRenderer.disableLightmap();
        this.mc.mcProfiler.endSection();
    }

    private void renderWeatherEntities(
            float partialTicks,
            int renderPass,
            double viewX,
            double viewY,
            double viewZ,
            boolean forgeEntityPass,
            boolean firstEntityOnly) {
        for (int index = 0; index < this.theWorld.weatherEffects.size(); index++) {
            Entity weather = this.theWorld.weatherEffects.get(index);
            if (this.shouldRenderEntityInPass(weather, renderPass, forgeEntityPass)) {
                this.renderedEntityCount++;
                if (weather.isInRangeToRender3d(viewX, viewY, viewZ)) {
                    this.renderManager.renderEntitySimple(weather, partialTicks);
                }
            }
            if (firstEntityOnly) {
                break;
            }
        }
    }

    private void renderSpectatorOutlines(
            List<Entity> loadedEntities,
            ICamera camera,
            float partialTicks,
            int renderPass,
            double viewX,
            double viewY,
            double viewZ,
            boolean forgeEntityPass,
            boolean firstEntityOnly) {
        GlStateManager.depthFunc(519);
        GlStateManager.disableFog();
        this.entityOutlineFramebuffer.framebufferClear();
        this.entityOutlineFramebuffer.bindFramebuffer(false);
        this.theWorld.theProfiler.endStartSection("entityOutlines");
        RenderHelper.disableStandardItemLighting();
        this.renderManager.setRenderOutlines(true);

        for (int index = 0; index < loadedEntities.size(); index++) {
            Entity candidate = loadedEntities.get(index);
            if (this.shouldRenderEntityInPass(candidate, renderPass, forgeEntityPass)) {
                boolean viewEntitySleeping = this.isViewEntitySleeping();
                boolean visiblePlayer =
                        candidate.isInRangeToRender3d(viewX, viewY, viewZ)
                                && (candidate.ignoreFrustumCheck
                                        || camera.isBoundingBoxInFrustum(
                                                candidate.getEntityBoundingBox())
                                        || candidate.riddenByEntity == this.mc.player)
                                && candidate instanceof PlayerEntity;
                if ((candidate != this.mc.getRenderViewEntity()
                                || this.mc.gameSettings.thirdPersonView != 0
                                || viewEntitySleeping)
                        && visiblePlayer) {
                    this.renderManager.renderEntitySimple(candidate, partialTicks);
                }
            }
            if (firstEntityOnly) {
                break;
            }
        }

        this.renderManager.setRenderOutlines(false);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.depthMask(false);
        this.entityOutlineShader.renderShaderGroup(partialTicks);
        GlStateManager.depthMask(true);
        this.mc.getFramebuffer().bindFramebuffer(false);
        GlStateManager.enableFog();
        GlStateManager.depthFunc(515);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
    }

    private boolean shouldRenderEntityInPass(
            Entity entity, int renderPass, boolean forgeEntityPass) {
        return !forgeEntityPass
                || Reflector.callBoolean(
                        entity, Reflector.forgeEntityShouldRenderInPass, new Object[] {renderPass});
    }

    private boolean isViewEntitySleeping() {
        return this.mc.getRenderViewEntity() instanceof LivingEntity
                && ((LivingEntity) this.mc.getRenderViewEntity()).isPlayerSleeping();
    }

    private void renderChunkEntities(
            ICamera camera,
            float partialTicks,
            int renderPass,
            double viewX,
            double viewY,
            double viewZ,
            boolean forgeEntityPass,
            boolean shaders,
            boolean legacyFlow) {
        for (ChunkVisibilityEntry entry : this.visibleEntityChunks) {
            BlockPos chunkPosition = entry.renderChunk.getPosition();
            Chunk chunk = this.theWorld.getChunkFromBlockCoords(chunkPosition);
            for (Object element :
                    chunk.getEntityLists()[entry.renderChunk.getPosition().getY() / 16]) {
                Entity entity = (Entity) element;
                if (this.shouldRenderEntityInPass(entity, renderPass, forgeEntityPass)) {
                    boolean visible =
                            this.renderManager.shouldRender(entity, camera, viewX, viewY, viewZ)
                                    || entity.riddenByEntity == this.mc.player;
                    if (visible) {
                        boolean viewEntitySleeping = this.isViewEntitySleeping();
                        if ((entity == this.mc.getRenderViewEntity()
                                        && this.mc.gameSettings.thirdPersonView == 0
                                        && !viewEntitySleeping)
                                || (entity.posY >= 0.0
                                        && entity.posY < 256.0
                                        && !this.theWorld.isBlockLoaded(new BlockPos(entity))
                                        && !legacyFlow)) {
                            continue;
                        }

                        this.renderedEntityCount++;
                        if (entity.getClass() == EntityItemFrame.class) {
                            entity.renderDistanceWeight = 0.06;
                        }
                        this.renderedEntity = entity;
                        if (shaders) {
                            Shaders.nextEntity(entity);
                        }
                        this.renderManager.renderEntitySimple(entity, partialTicks);
                        this.renderedEntity = null;
                    } else if (entity instanceof EntityWitherSkull) {
                        if (shaders) {
                            Shaders.nextEntity(entity);
                        }
                        this.mc.getRenderManager().renderWitherSkull(entity, partialTicks);
                    }
                }
                if (legacyFlow) {
                    break;
                }
            }
            if (legacyFlow) {
                break;
            }
        }
    }

    private void renderChunkTileEntities(
            ICamera camera,
            float partialTicks,
            int renderPass,
            FontRenderer fontRenderer,
            boolean forgeTilePass,
            boolean shaders,
            boolean legacyFlow) {
        for (ChunkVisibilityEntry entry : this.visibleTileEntityChunks) {
            for (Object element : entry.renderChunk.getCompiledChunk().getTileEntities()) {
                TileEntity tile = (TileEntity) element;
                if (forgeTilePass) {
                    if (!Reflector.callBoolean(
                                    tile,
                                    Reflector.forgeTileShouldRenderInPass,
                                    new Object[] {renderPass})
                            && !legacyFlow) {
                        continue;
                    }
                    BoundingBox bounds =
                            (BoundingBox)
                                    Reflector.call(
                                            tile,
                                            Reflector.forgeTileGetRenderBoundingBox,
                                            new Object[0]);
                    if (bounds != null && !camera.isBoundingBoxInFrustum(bounds) && !legacyFlow) {
                        continue;
                    }
                }

                if (tile.getClass() == TileEntitySign.class && !OptiFineConfig.zoomMode) {
                    LocalPlayer player = this.mc.player;
                    if (tile.getDistanceSq(player.posX, player.posY, player.posZ) > 256.0) {
                        fontRenderer.enabled = false;
                    }
                }
                if (shaders) {
                    Shaders.nextBlockEntity(tile);
                }
                TileEntityRendererDispatcher.instance.renderTileEntity(tile, partialTicks, -1);
                this.renderedTileEntityCount++;
                fontRenderer.enabled = true;
                if (legacyFlow) {
                    break;
                }
            }
            if (legacyFlow) {
                break;
            }
        }
    }

    private void renderTileEntityDamage(
            ICamera camera,
            float partialTicks,
            int renderPass,
            boolean forgeTilePass,
            boolean shaders,
            boolean legacyFlow) {
        for (Object element : this.damagedBlocks.values()) {
            DestroyBlockProgress damage = (DestroyBlockProgress) element;
            BlockPos position = damage.getPosition();
            TileEntity tile = this.theWorld.getTileEntity(position);
            if (tile instanceof TileEntityChest) {
                TileEntityChest chest = (TileEntityChest) tile;
                boolean useWestChest = chest.adjacentChestXNeg != null;
                if (useWestChest) {
                    position = position.offset(Facing.WEST);
                    tile = this.theWorld.getTileEntity(position);
                }
                if ((!useWestChest || legacyFlow) && chest.adjacentChestZNeg != null) {
                    position = position.offset(Facing.NORTH);
                    tile = this.theWorld.getTileEntity(position);
                }
            }
            Block block = this.theWorld.getBlockState(position).getBlock();
            if (this.shouldRenderTileDamage(
                    tile, block, camera, renderPass, forgeTilePass, legacyFlow)) {
                if (shaders) {
                    Shaders.nextBlockEntity(tile);
                }
                TileEntityRendererDispatcher.instance.renderTileEntity(
                        tile, partialTicks, damage.getPartialBlockDamage());
            }
            if (legacyFlow) {
                break;
            }
        }
    }

    private boolean shouldRenderTileDamage(
            TileEntity tile,
            Block block,
            ICamera camera,
            int renderPass,
            boolean forgeTilePass,
            boolean legacyFlow) {
        if (forgeTilePass) {
            if (tile == null
                    || !Reflector.callBoolean(
                            tile, Reflector.forgeTileShouldRenderInPass, new Object[] {renderPass})
                    || !Reflector.callBoolean(
                            tile, Reflector.forgeTileCanRenderBreaking, new Object[0])) {
                return false;
            }
            BoundingBox bounds =
                    (BoundingBox)
                            Reflector.call(
                                    tile, Reflector.forgeTileGetRenderBoundingBox, new Object[0]);
            boolean insideFrustum = bounds != null && camera.isBoundingBoxInFrustum(bounds);
            if (!legacyFlow) {
                return insideFrustum;
            }
        }
        return tile != null
                && (block instanceof BlockChest
                        || block instanceof BlockEnderChest
                        || block instanceof BlockSign
                        || block instanceof BlockSkull);
    }

    private void cleanupDamagedBlocks(Iterator iterator) {
        int i = WorldRenderer.getLegacyRenderFlowToken();

        while (iterator.hasNext()) {
            DestroyBlockProgress destroyBlockProgress = (DestroyBlockProgress) iterator.next();
            int j = destroyBlockProgress.getCreationCloudUpdateTick();
            if (this.cloudTickCounter - j > 400) {
                iterator.remove();
            }

            if (i != 0) {
                break;
            }
        }
    }

    public void renderWorldBorder(Entity entity, float f) {
        int l = WorldRenderer.getLegacyRenderFlowToken();
        Tessellator instance = Tessellator.getInstance();
        WorldRenderer worldRenderer = instance.getWorldRenderer();
        WorldBorder worldBorder = this.theWorld.getWorldBorder();
        double d0 = this.mc.gameSettings.renderDistanceChunks * 16;
        int i = l;
        if (entity.posX >= worldBorder.maxX() - d0
                || entity.posX <= worldBorder.minX() + d0
                || entity.posZ >= worldBorder.maxZ() - d0
                || entity.posZ <= worldBorder.minZ() + d0) {
            double d1 = 1.0 - worldBorder.getClosestDistance(entity) / d0;
            d1 = Math.pow(d1, 4.0);
            double d2 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
            double d3 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
            double d4 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 1, 1, 0);
            this.renderEngine.bindTexture(
                    net.minecraft.client.renderer.RenderGlobal.locationForcefieldPng);
            GlStateManager.depthMask(false);
            GlStateManager.pushMatrix();
            int j = worldBorder.getStatus().getID();
            float f1 = (j >> 16 & 0xFF) / 255.0F;
            float f2 = (j >> 8 & 0xFF) / 255.0F;
            float f3 = (j & 0xFF) / 255.0F;
            GlStateManager.color(f1, f2, f3, (float) d1);
            GlStateManager.doPolygonOffset(-3.0F, -3.0F);
            GlStateManager.enablePolygonOffset();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.enableAlpha();
            GlStateManager.disableCull();
            long k = MinecraftClient.getSystemTime();

            float f4 = (float) (k % 3000L) / 3000.0F;

            worldRenderer.startDrawingQuads();
            worldRenderer.setTranslation(-d2, -d3, -d4);
            worldRenderer.disableColor();
            double d5 = Math.max(MathHelper.floor(d4 - d0), worldBorder.minZ());
            double d6 = Math.min(MathHelper.ceil(d4 + d0), worldBorder.maxZ());
            if (d2 > worldBorder.maxX() - d0) {
                float f8 = 0.0F;
                double d7 = d5;

                while (d7 < d6) {
                    double d8 = Math.min(1.0, d6 - d7);
                    float f9 = (float) d8 * 0.5F;
                    worldRenderer.addVertexWithUV(
                            worldBorder.maxX(), 256.0, d7, f4 + f8, f4 + 0.0F);
                    worldRenderer.addVertexWithUV(
                            worldBorder.maxX(), 256.0, d7 + d8, f4 + f9 + f8, f4 + 0.0F);
                    worldRenderer.addVertexWithUV(
                            worldBorder.maxX(), 0.0, d7 + d8, f4 + f9 + f8, f4 + 128.0F);
                    worldRenderer.addVertexWithUV(
                            worldBorder.maxX(), 0.0, d7, f4 + f8, f4 + 128.0F);
                    d7++;
                    f8 += 0.5F;
                    if (i != 0) {
                        break;
                    }
                }
            }

            if (d2 < worldBorder.minX() + d0) {
                float f10 = 0.0F;
                double d9 = d5;

                while (d9 < d6) {
                    double d12 = Math.min(1.0, d6 - d9);
                    float f13 = (float) d12 * 0.5F;
                    worldRenderer.addVertexWithUV(
                            worldBorder.minX(), 256.0, d9, f4 + f10, f4 + 0.0F);
                    worldRenderer.addVertexWithUV(
                            worldBorder.minX(), 256.0, d9 + d12, f4 + f13 + f10, f4 + 0.0F);
                    worldRenderer.addVertexWithUV(
                            worldBorder.minX(), 0.0, d9 + d12, f4 + f13 + f10, f4 + 128.0F);
                    worldRenderer.addVertexWithUV(
                            worldBorder.minX(), 0.0, d9, f4 + f10, f4 + 128.0F);
                    d9++;
                    f10 += 0.5F;
                    if (i != 0) {
                        break;
                    }
                }
            }

            d5 = Math.max(MathHelper.floor(d2 - d0), worldBorder.minX());
            d6 = Math.min(MathHelper.ceil(d2 + d0), worldBorder.maxX());
            if (d4 > worldBorder.maxZ() - d0) {
                float f11 = 0.0F;
                double d10 = d5;

                while (d10 < d6) {
                    double d13 = Math.min(1.0, d6 - d10);
                    float f14 = (float) d13 * 0.5F;
                    worldRenderer.addVertexWithUV(
                            d10, 256.0, worldBorder.maxZ(), f4 + f11, f4 + 0.0F);
                    worldRenderer.addVertexWithUV(
                            d10 + d13, 256.0, worldBorder.maxZ(), f4 + f14 + f11, f4 + 0.0F);
                    worldRenderer.addVertexWithUV(
                            d10 + d13, 0.0, worldBorder.maxZ(), f4 + f14 + f11, f4 + 128.0F);
                    worldRenderer.addVertexWithUV(
                            d10, 0.0, worldBorder.maxZ(), f4 + f11, f4 + 128.0F);
                    d10++;
                    f11 += 0.5F;
                    if (i != 0) {
                        break;
                    }
                }
            }

            if (d4 < worldBorder.minZ() + d0) {
                float f12 = 0.0F;
                double d11 = d5;

                while (d11 < d6) {
                    double d14 = Math.min(1.0, d6 - d11);
                    float f15 = (float) d14 * 0.5F;
                    worldRenderer.addVertexWithUV(
                            d11, 256.0, worldBorder.minZ(), f4 + f12, f4 + 0.0F);
                    worldRenderer.addVertexWithUV(
                            d11 + d14, 256.0, worldBorder.minZ(), f4 + f15 + f12, f4 + 0.0F);
                    worldRenderer.addVertexWithUV(
                            d11 + d14, 0.0, worldBorder.minZ(), f4 + f15 + f12, f4 + 128.0F);
                    worldRenderer.addVertexWithUV(
                            d11, 0.0, worldBorder.minZ(), f4 + f12, f4 + 128.0F);
                    d11++;
                    f12 += 0.5F;
                    if (i != 0) {
                        break;
                    }
                }
            }

            instance.draw();
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            GlStateManager.enableCull();
            GlStateManager.disableAlpha();
            GlStateManager.doPolygonOffset(0.0F, 0.0F);
            GlStateManager.disablePolygonOffset();
            GlStateManager.enableAlpha();
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
        }
    }

    public void compositeEntityOutlines() {
        try {
            if (this.isRenderEntityOutlines()) {
                net.minecraft.client.renderer.GlStateManager.enableBlend();
                net.minecraft.client.renderer.GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
                this.entityOutlineFramebuffer.framebufferRenderExt(
                        this.mc.displayWidth, this.mc.displayHeight, false);
                net.minecraft.client.renderer.GlStateManager.disableBlend();
            }
        } catch (com.google.gson.JsonSyntaxException jsonsyntaxexception) {
            throw (jsonsyntaxexception);
        }
    }

    public int getCountActiveRenderers() {
        return this.visibleTerrainChunks.size();
    }

    public void renderClouds(float partialTicks, int renderPass) {
        boolean legacyFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        if (OptiFineConfig.isCloudsOff()) {
            return;
        }
        if (Reflector.forgeGetCloudRenderer.exists()) {
            Object cloudRenderer =
                    Reflector.call(
                            this.mc.world.provider, Reflector.forgeGetCloudRenderer, new Object[0]);
            if (cloudRenderer != null) {
                Reflector.callVoid(
                        cloudRenderer,
                        Reflector.forgeRenderHandlerRender,
                        new Object[] {partialTicks, this.theWorld, this.mc});
                return;
            }
        }
        if (!this.mc.world.provider.isSurfaceWorld()) {
            return;
        }
        if (OptiFineConfig.isShaders()) {
            Shaders.beginClouds();
        }

        boolean fancy = OptiFineConfig.isCloudsFancy();
        if (fancy) {
            this.renderCloudsFancy(partialTicks, renderPass);
        }
        if (!fancy || legacyFlow) {
            this.renderCloudsFlat(partialTicks, renderPass, legacyFlow);
        }

        if (OptiFineConfig.isShaders()) {
            Shaders.endClouds();
        }
    }

    private void renderCloudsFlat(float partialTicks, int renderPass, boolean firstTileOnly) {
        this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, partialTicks);
        // Display list geometry uses tick positions; playback interpolates the offset.
        float geometryPartialTicks = 0.0F;
        GlStateManager.disableCull();
        float cameraY =
                (float)
                        (this.mc.getRenderViewEntity().lastTickPosY
                                + (this.mc.getRenderViewEntity().posY
                                                - this.mc.getRenderViewEntity().lastTickPosY)
                                        * geometryPartialTicks);

        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer vertices = tessellator.getWorldRenderer();
        this.renderEngine.bindTexture(net.minecraft.client.renderer.RenderGlobal.locationCloudsPng);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        if (this.cloudRenderer.shouldRebuildDisplayList()) {
            this.cloudRenderer.startUpdateGlList();
            Vec3 cloudColor = this.theWorld.getCloudColour(geometryPartialTicks);
            float red = (float) cloudColor.x;
            float green = (float) cloudColor.y;
            float blue = (float) cloudColor.z;
            if (renderPass != 2) {
                float stereoRed = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
                float stereoGreen = (red * 30.0F + green * 70.0F) / 100.0F;
                float stereoBlue = (red * 30.0F + blue * 70.0F) / 100.0F;
                red = stereoRed;
                green = stereoGreen;
                blue = stereoBlue;
            }

            double animationTicks = this.cloudTickCounter + geometryPartialTicks;
            double cloudX =
                    this.mc.getRenderViewEntity().prevPosX
                            + (this.mc.getRenderViewEntity().posX
                                            - this.mc.getRenderViewEntity().prevPosX)
                                    * geometryPartialTicks
                            + animationTicks * 0.03F;
            double cloudZ =
                    this.mc.getRenderViewEntity().prevPosZ
                            + (this.mc.getRenderViewEntity().posZ
                                            - this.mc.getRenderViewEntity().prevPosZ)
                                    * geometryPartialTicks;
            int wrapX = MathHelper.floor(cloudX / 2048.0);
            int wrapZ = MathHelper.floor(cloudZ / 2048.0);
            cloudX -= wrapX * 2048;
            cloudZ -= wrapZ * 2048;
            float cloudHeight = this.theWorld.provider.getCloudHeight() - cameraY + 0.33F;
            cloudHeight += this.mc.gameSettings.ofCloudsHeight * 128.0F;
            float textureOffsetX = (float) (cloudX * 4.8828125E-4);
            float textureOffsetZ = (float) (cloudZ * 4.8828125E-4);
            vertices.startDrawingQuads();
            vertices.setVertexColorFromFloats(red, green, blue, 0.8F);
            short tileX = -256;

            while (tileX < 256) {
                short tileZ = -256;

                while (tileZ < 256) {
                    vertices.addVertexWithUV(
                            tileX + 0,
                            cloudHeight,
                            tileZ + 32,
                            (tileX + 0) * 4.8828125E-4F + textureOffsetX,
                            (tileZ + 32) * 4.8828125E-4F + textureOffsetZ);
                    vertices.addVertexWithUV(
                            tileX + 32,
                            cloudHeight,
                            tileZ + 32,
                            (tileX + 32) * 4.8828125E-4F + textureOffsetX,
                            (tileZ + 32) * 4.8828125E-4F + textureOffsetZ);
                    vertices.addVertexWithUV(
                            tileX + 32,
                            cloudHeight,
                            tileZ + 0,
                            (tileX + 32) * 4.8828125E-4F + textureOffsetX,
                            (tileZ + 0) * 4.8828125E-4F + textureOffsetZ);
                    vertices.addVertexWithUV(
                            tileX + 0,
                            cloudHeight,
                            tileZ + 0,
                            (tileX + 0) * 4.8828125E-4F + textureOffsetX,
                            (tileZ + 0) * 4.8828125E-4F + textureOffsetZ);
                    tileZ += 32;
                    if (firstTileOnly) {
                        break;
                    }
                }

                tileX += 32;
                if (firstTileOnly) {
                    break;
                }
            }

            tessellator.draw();
            this.cloudRenderer.endUpdateGlList();
        }

        this.cloudRenderer.renderDisplayList();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableCull();
    }

    protected void stopChunkUpdates() {
        this.chunksToUpdate.clear();
        this.renderDispatcher.stopChunkUpdates();
    }

    private void renderStars(WorldRenderer worldRenderer) {
        int l = WorldRenderer.getLegacyRenderFlowToken();

        Random random = new Random(10842L);
        int i = l;
        worldRenderer.startDrawingQuads();
        int j = 0;

        while (j < 1500) {
            double d0 = random.nextFloat() * 2.0F - 1.0F;
            double d1 = random.nextFloat() * 2.0F - 1.0F;
            double d2 = random.nextFloat() * 2.0F - 1.0F;
            double d3 = 0.15F + random.nextFloat() * 0.1F;
            double d4 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d4 < 1.0 && d4 > 0.01) {
                d4 = 1.0 / Math.sqrt(d4);
                d0 *= d4;
                d1 *= d4;
                d2 *= d4;
                double d5 = d0 * 100.0;
                double d6 = d1 * 100.0;
                double d7 = d2 * 100.0;
                double d8 = Math.atan2(d0, d2);
                double d9 = Math.sin(d8);
                double d10 = Math.cos(d8);
                double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
                double d12 = Math.sin(d11);
                double d13 = Math.cos(d11);
                double d14 = random.nextDouble() * Math.PI * 2.0;
                double d15 = Math.sin(d14);
                double d16 = Math.cos(d14);
                int k = 0;

                while (k < 4) {

                    double d18 = ((k & 2) - 1) * d3;
                    double d19 = ((k + 1 & 2) - 1) * d3;

                    double d21 = d18 * d16 - d19 * d15;
                    double d22 = d19 * d16 + d18 * d15;
                    double d23 = d21 * d12 + 0.0 * d13;
                    double d24 = 0.0 * d12 - d21 * d13;
                    double d25 = d24 * d9 - d22 * d10;
                    double d26 = d22 * d9 + d24 * d10;
                    worldRenderer.addVertex(d5 + d25, d6 + d23, d7 + d26);
                    k++;
                    if (i != 0) {
                        break;
                    }
                }
            }

            j++;
            if (i != 0) {
                break;
            }
        }
    }

    public int getRenderedTileEntityCount() {
        return this.renderedTileEntityCount;
    }

    public void drawBlockDamageTexture(
            Tessellator tessellator, WorldRenderer vertices, Entity view, float partialTicks) {
        double cameraX = view.lastTickPosX + (view.posX - view.lastTickPosX) * partialTicks;
        boolean legacyDamageFlow = WorldRenderer.getLegacyRenderFlowToken() != 0;
        double cameraY = view.lastTickPosY + (view.posY - view.lastTickPosY) * partialTicks;
        double cameraZ = view.lastTickPosZ + (view.posZ - view.lastTickPosZ) * partialTicks;
        if (this.damagedBlocks.isEmpty()) {
            return;
        }

        this.renderEngine.bindTexture(TextureMap.locationBlocksTexture);
        this.beginBlockDamageRender();
        vertices.startDrawingQuads();
        vertices.setVertexFormat(DefaultVertexFormats.BLOCK);
        vertices.setTranslation(-cameraX, -cameraY, -cameraZ);
        vertices.disableColor();
        Iterator<?> damageEntries = this.damagedBlocks.values().iterator();
        while (damageEntries.hasNext()) {
            DestroyBlockProgress damage = (DestroyBlockProgress) damageEntries.next();
            BlockPos position = damage.getPosition();
            if (this.useChunkDamageOverlay(position, legacyDamageFlow)) {
                double dx = position.getX() - cameraX;
                double dy = position.getY() - cameraY;
                double dz = position.getZ() - cameraZ;
                boolean tooFar = dx * dx + dy * dy + dz * dz > 1024.0;
                if (tooFar) {
                    damageEntries.remove();
                }
                if (!tooFar || legacyDamageFlow) {
                    BlockState state = this.theWorld.getBlockState(position);
                    if (state.getBlock().getMaterial() != Material.AIR) {
                        TextureAtlasSprite cracks =
                                this.destroyBlockIcons[damage.getPartialBlockDamage()];
                        this.mc
                                .getBlockRendererDispatcher()
                                .renderBlockDamage(state, position, cracks, this.theWorld);
                    }
                }
            }
            if (legacyDamageFlow) {
                break;
            }
        }

        tessellator.draw();
        vertices.setTranslation(0.0, 0.0, 0.0);
        this.endBlockDamageRender();
    }

    private boolean useChunkDamageOverlay(BlockPos position, boolean legacyDamageFlow) {
        Block block = this.theWorld.getBlockState(position).getBlock();
        boolean tileRendersCracks =
                block instanceof BlockChest
                        || block instanceof BlockEnderChest
                        || block instanceof BlockSign
                        || block instanceof BlockSkull;
        if (Reflector.forgeTileCanRenderBreaking.exists()) {
            boolean forgeRendersCracks = tileRendersCracks;
            if (!forgeRendersCracks) {
                TileEntity tile = this.theWorld.getTileEntity(position);
                forgeRendersCracks =
                        tile != null
                                && Reflector.callBoolean(
                                        tile, Reflector.forgeTileCanRenderBreaking, new Object[0]);
            }
            if (!legacyDamageFlow) {
                return !forgeRendersCracks;
            }
        }
        return !tileRendersCracks;
    }
}
