package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.model.ModelSign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ResourceLocation;
import optifine.CustomColors;
import optifine.OptiFineConfig;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.render.NoRenderModule;

import org.lwjgl.opengl.GL11;

import java.util.List;

public class TileEntitySignRenderer extends TileEntitySpecialRenderer {
    private final ModelSign model = new ModelSign();

    private static String[] packedTextFragments;
    private static ResourceLocation SIGN_TEXTURE;

    public void renderSignAt(TileEntitySign sign, double x, double y, double z,
                                              float partialTicks, int damageStage) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();
        boolean standing = sign.getBlockType() == Blocks.standing_sign;
        GlStateManager.pushMatrix();
        float modelScale = 0.6666667F;
        int wallDirection = standing ? 0 : sign.getBlockMetadata();
        GlStateManager.translate((float) x + 0.5F, (float) y + 0.75F * modelScale, (float) z + 0.5F);
        if (standing) {
            float yaw = sign.getBlockMetadata() * 360 / 16.0F;
            GlStateManager.rotate(-yaw, 0.0F, 1.0F, 0.0F);
        } else {
            GlStateManager.rotate(-wallSignYaw(wallDirection), 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(0.0F, -0.3125F, -0.4375F);
        }
        this.model.signStick.showModel = standing;

        if (damageStage >= 0) {
            this.beginDestroyStageTexture(damageStage, 4.0F, 2.0F);
        } else {
            this.bindTexture(SIGN_TEXTURE);
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.scale(modelScale, -modelScale, -modelScale);
        this.model.renderSign();
        GlStateManager.popMatrix();

        FontRenderer font = this.getFontRenderer();
        float textScale = 0.015625F * modelScale;
        GlStateManager.translate(0.0F, 0.5F * modelScale, 0.07F * modelScale);
        GlStateManager.scale(textScale, -textScale, textScale);
        GL11.glNormal3f(0.0F, 0.0F, -1.0F * textScale);
        GlStateManager.depthMask(false);
        int textColor = OptiFineConfig.isCustomColors() ? CustomColors.getSignTextColor(0) : 0;

        RemixClient.INSTANCE.getClientServices();
        NoRenderModule noRender = ClientServices.moduleManager.noRender;
        if (!noRender.hidesSignText() && damageStage < 0) {
            this.drawSignText(sign, font, textColor);
        }

        GlStateManager.depthMask(true);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.popMatrix();
        if (damageStage >= 0) {
            endDestroyStageTexture();
        }
        // The original tile renderers share this legacy render-state marker.
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            TileEntitySpecialRenderer.setLegacyTileRendererState(new int[1]);
        }
    }

    private static float wallSignYaw(int metadata) {
        switch (metadata) {
            case 2: return 180.0F;
            case 4: return 90.0F;
            case 5: return -90.0F;
            default: return 0.0F;
        }
    }

    private void drawSignText(TileEntitySign sign, FontRenderer font, int color) {
        for (int index = 0; index < sign.signText.length; index++) {
            ChatComponent message = sign.signText[index];
            if (message == null) {
                continue;
            }
            List<ChatComponent> lines = GuiUtilRenderComponents.splitText(message, 90, font, false, true);
            String line = lines != null && !lines.isEmpty() ? lines.get(0).getFormattedText() : "";
            if (index == sign.lineBeingEdited) {
                line = "> " + line + " <";
            }
            font.drawString(line, -font.getStringWidth(line) / 2, index * 10 - sign.signText.length * 5, color);
        }
    }

    static {
        net.minecraft.client.renderer.tileentity.TileEntitySignRenderer.packedTextFragments = new String[2];
        net.minecraft.client.renderer.tileentity.TileEntitySignRenderer.packedTextFragments[0] = new String("");
        net.minecraft.client.renderer.tileentity.TileEntitySignRenderer.SIGN_TEXTURE =
                new ResourceLocation("textures/entity/sign.png");
    }

    public void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f, int i) {
        this.renderSignAt((TileEntitySign) tileEntity, d0, d1, d2, f, i);
    }
}
