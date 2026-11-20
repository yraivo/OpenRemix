package remix.module.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BoundingBox;
import remix.event.MotionPhase;
import remix.event.impl.Render3DEvent;
import remix.module.render.StorageESPMode;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

import java.awt.Color;

public class StorageESPModule extends ModuleBase {

    private Color enderChestColor;
    public Setting enderChestSetting;
    private Color chestColor;
    public Setting furnaceSetting;
    private Color dispenserColor;
    private Color trappedChestColor;
    public Setting chestSetting;
    private Color dropperColor;
    public Setting trapChestSetting;
    public Setting dispenserSetting;
    public final Setting<StorageESPMode> modeSetting =
            new Setting("Mode", this, StorageESPMode.LINES);

    private Color furnaceColor;
    public Setting dropperSetting;

    public static void drawBoxForMode(BoundingBox bounds, int color, StorageESPMode mode) {
        GlStateManager.glLineWidth(2.0F);
        int j = mode == StorageESPMode.LINES ? 3 : 7;
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        GlStateManager.color(f, f1, f2, f3);
        GlStateManager.ab(3);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.mO();
        GlStateManager.ab(3);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.mO();
        f3 -= 0.6F;
        GlStateManager.color(f, f1, f2, f3);
        GlStateManager.ab(j);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.mO();
        GlStateManager.ab(j);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.mO();
        GlStateManager.ab(j);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.mO();
        GlStateManager.ab(j);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.mO();
        GlStateManager.ab(j);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.mO();
        GlStateManager.ab(j);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.mO();
        GlStateManager.ab(j);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
        GlStateManager.vertex3f(
                (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
        GlStateManager.mO();
    }

    public static void attachDepthStencilBuffer(Framebuffer framebuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(framebuffer.depthBuffer);
        int i = EXTFramebufferObject.glGenRenderbuffersEXT();
        EXTFramebufferObject.glBindRenderbufferEXT(36161, i);
        EXTFramebufferObject.glRenderbufferStorageEXT(
                36161,
                34041,
                MinecraftClient.getMinecraft().displayWidth,
                MinecraftClient.getMinecraft().displayHeight);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, i);
        EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, i);
    }

    public static void ensureStencilBuffer() {
        Framebuffer framebuffer = MinecraftClient.getMinecraft().getFramebuffer();
        if (framebuffer != null && framebuffer.depthBuffer > -1) {
            attachDepthStencilBuffer(framebuffer);
            framebuffer.depthBuffer = -1;
        }
    }

    public static void restoreOverlayState() {
        GlStateManager.enableDepth();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GL11.glDisable(2848);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public static void setStorageColor(int color, TileEntity tileEntity) {
        float f = (color >> 16 & 0xFF) / 255.0F;
        float f1 = (color >> 8 & 0xFF) / 255.0F;
        float f2 = (color & 0xFF) / 255.0F;
        float f3 = (color >> 24 & 0xFF) / 255.0F;
        GlStateManager.color(f, f1, f2, f3);
    }

    private boolean shouldRenderStorage(TileEntity tileEntity) {
        return tileEntity instanceof TileEntityChest
                        && (this.chestSetting.asBoolean()
                                        && ((TileEntityChest) tileEntity).getChestType() != 1
                                || ((TileEntityChest) tileEntity).getChestType() == 1
                                        && this.trapChestSetting.asBoolean())
                || tileEntity instanceof TileEntityEnderChest && this.enderChestSetting.asBoolean()
                || tileEntity instanceof TileEntityFurnace && this.furnaceSetting.asBoolean()
                || tileEntity instanceof TileEntityDispenser && this.dispenserSetting.asBoolean()
                || tileEntity instanceof TileEntityDropper && this.dropperSetting.asBoolean();
    }

    public void renderStorageBounds(TileEntity tileEntity) {
        double d0 = tileEntity.getPos().getX() - minecraft.getRenderManager().viewerPosX;
        double d1 = tileEntity.getPos().getY() - minecraft.getRenderManager().viewerPosY;
        double d2 = tileEntity.getPos().getZ() - minecraft.getRenderManager().viewerPosZ;
        StorageESPMode mode = (StorageESPMode) this.modeSetting.getValue();
        boolean solid = mode == StorageESPMode.SOLID;

        GlStateManager.pushMatrix();
        GlStateManager.translate(d0, d1, d2);

        if (tileEntity instanceof TileEntityChest) {
            TileEntityChest tileEntityChest = (TileEntityChest) tileEntity;
            int color = tileEntityChest.getChestType() == 1
                    ? this.trappedChestColor.getRGB()
                    : this.chestColor.getRGB();
            setStorageColor(color, tileEntity);

            BoundingBox boundingBox = null;
            if (tileEntityChest.adjacentChestXPos != null) {
                boundingBox = new BoundingBox(0.0625, 0.0, 0.0625, 1.9375, 0.875, 0.9375);
            } else if (tileEntityChest.adjacentChestZPos != null) {
                boundingBox = new BoundingBox(0.0625, 0.0, 0.0625, 0.9375, 0.875, 1.9375);
            } else if (tileEntityChest.adjacentChestXNeg == null && tileEntityChest.adjacentChestZNeg == null) {
                boundingBox = new BoundingBox(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
            }

            if (boundingBox != null) {
                if (solid) {
                    drawFilledBox(boundingBox);
                }
                drawBoxForMode(boundingBox, color, mode);
            }
        } else if (tileEntity instanceof TileEntityEnderChest) {
            BoundingBox boundingBox = new BoundingBox(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
            int color = this.enderChestColor.getRGB();
            setStorageColor(color, tileEntity);
            if (solid) {
                drawFilledBox(boundingBox);
            }
            drawBoxForMode(boundingBox, color, mode);
        } else if (tileEntity instanceof TileEntityFurnace
                || tileEntity instanceof TileEntityDropper
                || tileEntity instanceof TileEntityDispenser) {
            BoundingBox boundingBox = new BoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
            int color = this.dispenserColor.getRGB();
            if (tileEntity instanceof TileEntityFurnace) {
                color = this.furnaceColor.getRGB();
            } else if (tileEntity instanceof TileEntityDropper) {
                color = this.dropperColor.getRGB();
            }

            setStorageColor(color, tileEntity);
            if (solid) {
                drawFilledBox(boundingBox);
            }
            drawBoxForMode(boundingBox, color, mode);
        }

        GlStateManager.popMatrix();
    }

    public static void beginOverlayState() {
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GL11.glEnable(2848);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        RenderHelper.disableStandardItemLighting();
    }

    @Subscribe(mc = Render3DEvent.class)
    private void onRender3D(Render3DEvent event) {
        if (event.getPhase() == MotionPhase.PRE) {
            StorageESPMode mode = (StorageESPMode) this.modeSetting.getValue();
            if (mode == StorageESPMode.SOLID) {
                ensureStencilBuffer();
                beginOverlayState();
                GL11.glPushAttrib(1048575);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.glLineWidth(1.5F);
                GL11.glHint(3154, 4354);
                GL11.glEnable(2960);
                GL11.glClear(1024);
                GL11.glClearStencil(15);
                GL11.glStencilFunc(512, 1, 15);
                GL11.glStencilOp(7681, 7681, 7681);
                GL11.glPolygonMode(1032, 6913);

                for (TileEntity tileEntity : minecraft.world.loadedTileEntityList) {
                    if (this.shouldRenderStorage(tileEntity)) {
                        this.renderStorageBounds(tileEntity);
                    }
                }

                GL11.glStencilFunc(512, 0, 15);
                GL11.glStencilOp(7681, 7681, 7681);
                GL11.glPolygonMode(1032, 6914);

                for (TileEntity tileEntityValue : minecraft.world.loadedTileEntityList) {
                    if (this.shouldRenderStorage(tileEntityValue)) {
                        this.renderStorageBounds(tileEntityValue);
                    }
                }

                GL11.glStencilFunc(514, 1, 15);
                GL11.glStencilOp(7680, 7680, 7680);
                GL11.glPolygonMode(1032, 6913);
                GL11.glEnable(10754);
                GlStateManager.doPolygonOffset(1.0F, -1000000.0F);

                for (TileEntity tileEntityValue2 : minecraft.world.loadedTileEntityList) {
                    if (this.shouldRenderStorage(tileEntityValue2)) {
                        this.renderStorageBounds(tileEntityValue2);
                    }
                }

                GlStateManager.doPolygonOffset(1.0F, 1000000.0F);
                GL11.glDisable(10754);
                GL11.glDisable(2960);
                GL11.glDisable(2848);
                GL11.glHint(3154, 4352);
                GL11.glPopAttrib();
                restoreOverlayState();
                minecraft.getFramebuffer().bindFramebuffer(true);
                minecraft.getFramebuffer().bindFramebufferTexture();
                return;
            }

            beginOverlayState();
            for (TileEntity tileEntityValue3 : minecraft.world.loadedTileEntityList) {
                if (this.shouldRenderStorage(tileEntityValue3)) {
                    this.renderStorageBounds(tileEntityValue3);
                }
            }
            restoreOverlayState();
        }
    }

    public StorageESPModule() {
        super("StorageESP", ModuleDefaults.ofCategory(ModuleCategory.RENDER));
        this.chestSetting = new Setting("Chest", this, true);
        this.trapChestSetting = new Setting("TrapChest", this, false);
        this.enderChestSetting = new Setting("EnderChest", this, true);
        this.dispenserSetting = new Setting("Dispenser", this, false);
        this.dropperSetting = new Setting("Dropper", this, false);
        this.furnaceSetting = new Setting("Furnace", this, false);
        this.setVisible(false);
        this.chestColor = new Color(16163608);
        this.enderChestColor = new Color(11409656);
        this.trappedChestColor = new Color(16716306);
        this.dispenserColor = new Color(16739103);
        this.dropperColor = new Color(2779898);
        this.furnaceColor = new Color(3487547);
    }

    public static void drawFilledBox(BoundingBox bounds) {
        if (bounds != null) {
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.maxY, (float) bounds.maxZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.mO();
            GlStateManager.ab(7);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.minZ);
            GlStateManager.vertex3f(
                    (float) bounds.minX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.vertex3f(
                    (float) bounds.maxX, (float) bounds.minY, (float) bounds.maxZ);
            GlStateManager.mO();
        }
    }
}
