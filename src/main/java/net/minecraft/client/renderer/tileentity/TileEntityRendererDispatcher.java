package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnchantmentTable;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;

import com.google.common.collect.Maps;

import java.util.Map;

public class TileEntityRendererDispatcher {
    public Entity entity;
    public TextureManager renderEngine;
    public double entityY;
    public static TileEntityRendererDispatcher instance;
    public double entityX;
    public double entityZ;
    public float tE;
    public World worldObj;
    private FontRenderer fontRenderer;
    public static double staticPlayerY;
    public static double staticPlayerX;
    public static double staticPlayerZ;

    private Map mapSpecialRenderers = Maps.newHashMap();
    public float tH;

    public void cacheActiveRenderInfo(
            World world, TextureManager aFyValue, FontRenderer aFBValue, Entity entity, float f) {
        try {
            if (this.worldObj != world) {
                this.setWorld(world);
            }
        } catch (ReportedException reportedException) {
            throw method_7b8b77b0e24b2985ecf5(reportedException);
        }

        this.renderEngine = aFyValue;
        this.entity = entity;
        this.fontRenderer = aFBValue;
        this.tH = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * f;
        this.tE = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * f;
        this.entityX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * f;
        this.entityY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * f;
        this.entityZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * f;
    }

    public TileEntitySpecialRenderer getSpecialRendererByClass(Class oclass) {
        TileEntitySpecialRenderer tileEntitySpecialRenderer =
                (TileEntitySpecialRenderer) this.mapSpecialRenderers.get(oclass);

        try {
            if (tileEntitySpecialRenderer != null || oclass == TileEntity.class) {
                return tileEntitySpecialRenderer;
            }
        } catch (ReportedException reportedException) {
            throw method_7b8b77b0e24b2985ecf5(reportedException);
        }

        tileEntitySpecialRenderer = this.getSpecialRendererByClass(oclass.getSuperclass());
        this.mapSpecialRenderers.put(oclass, tileEntitySpecialRenderer);
        return tileEntitySpecialRenderer;
    }

    public void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f) {
        this.renderTileEntityAt(tileEntity, d0, d1, d2, f, -1);
    }

    public void renderTileEntity(TileEntity tileEntity, float f, int i) {
        if (tileEntity.getDistanceSq(this.entityX, this.entityY, this.entityZ)
                < tileEntity.getMaxRenderDistanceSquared()) {
            int j = this.worldObj.getCombinedLight(tileEntity.getPos(), 0);
            int k = j % 65536;
            int l = j / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, k / 1.0F, l / 1.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            BlockPos blockPos = tileEntity.getPos();
            this.renderTileEntityAt(
                    tileEntity,
                    blockPos.getX() - net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.staticPlayerX,
                    blockPos.getY() - net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.staticPlayerY,
                    blockPos.getZ() - net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.staticPlayerZ,
                    f,
                    i);
        }
    }

    private static ReportedException method_7b8b77b0e24b2985ecf5(
            ReportedException reportedException) {
        return reportedException;
    }

    public boolean hasSpecialRenderer(TileEntity tileEntity) {
        try {
            if (this.getSpecialRenderer(tileEntity) != null) {
                return true;
            }
        } catch (ReportedException reportedException) {
            throw method_7b8b77b0e24b2985ecf5(reportedException);
        }

        return false;
    }

    static {
        net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher.instance = new TileEntityRendererDispatcher();
    }

    public void setWorld(World aFAValue) {
        this.worldObj = aFAValue;
    }

    public void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f, int i) {
        TileEntitySpecialRenderer specialRenderer = this.getSpecialRenderer(tileEntity);
        if (specialRenderer != null) {
            try {
                specialRenderer.renderTileEntityAt(tileEntity, d0, d1, d2, f, i);
            } catch (Throwable throwable) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(throwable, "Rendering Block Entity");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Block Entity Details");
                tileEntity.addInfoToCrashReport(crashReportCategory);
                throw new ReportedException(crashReport);
            }
        }
    }

    private TileEntityRendererDispatcher() {
        this.mapSpecialRenderers.put(TileEntitySign.class, new TileEntitySignRenderer());
        this.mapSpecialRenderers.put(
                TileEntityMobSpawner.class, new TileEntityMobSpawnerRenderer());
        this.mapSpecialRenderers.put(TileEntityPiston.class, new TileEntityPistonRenderer());
        this.mapSpecialRenderers.put(TileEntityChest.class, new TileEntityChestRenderer());
        this.mapSpecialRenderers.put(
                TileEntityEnderChest.class, new TileEntityEnderChestRenderer());
        this.mapSpecialRenderers.put(
                TileEntityEnchantmentTable.class, new TileEntityEnchantmentTableRenderer());
        this.mapSpecialRenderers.put(TileEntityEndPortal.class, new TileEntityEndPortalRenderer());
        this.mapSpecialRenderers.put(TileEntityBeacon.class, new TileEntityBeaconRenderer());
        this.mapSpecialRenderers.put(TileEntitySkull.class, new TileEntitySkullRenderer());
        this.mapSpecialRenderers.put(TileEntityBanner.class, new TileEntityBannerRenderer());

        for (java.util.Iterator<?> __recoveryIterator171_31391 =
                        (this.mapSpecialRenderers.values()).iterator();
                (__recoveryIterator171_31391).hasNext(); ) {
            TileEntitySpecialRenderer tileEntitySpecialRenderer =
                    (TileEntitySpecialRenderer) (__recoveryIterator171_31391).next();
            tileEntitySpecialRenderer.setRendererDispatcher(this);
        }
    }

    public FontRenderer getFontRenderer() {
        return this.fontRenderer;
    }

    public TileEntitySpecialRenderer getSpecialRenderer(TileEntity tileEntity) {
        try {
            if (tileEntity == null) {
                return null;
            }
        } catch (ReportedException reportedException) {
            throw method_7b8b77b0e24b2985ecf5(reportedException);
        }

        return this.getSpecialRendererByClass(tileEntity.getClass());
    }
}
