package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import remix.client.ClientServices;
import remix.client.RemixClient;

import org.lwjgl.opengl.GL11;

import java.util.Calendar;

public class TileEntityChestRenderer extends TileEntitySpecialRenderer {
    private static ResourceLocation textureTrapped;
    private ModelChest simpleChest = new ModelChest();
    private static ResourceLocation textureNormalDouble;
    private static ResourceLocation textureTrappedDouble;
    private static ResourceLocation textureChristmas;
    private static ResourceLocation textureChristmasDouble;
    private boolean useChristmasTextures;
    private ModelChest largeChest = new ModelLargeChest();
    private static ResourceLocation textureNormal;

    public TileEntityChestRenderer() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        this.useChristmasTextures = calendar.get(Calendar.MONTH) == Calendar.DECEMBER && day >= 24 && day <= 26;
    }

    static {
        textureTrappedDouble = new ResourceLocation("textures/entity/chest/trapped_double.png");
        textureChristmasDouble = new ResourceLocation("textures/entity/chest/christmas_double.png");
        textureNormalDouble = new ResourceLocation("textures/entity/chest/normal_double.png");
        textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
        textureChristmas = new ResourceLocation("textures/entity/chest/christmas.png");
        textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
    }

    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z,
                                   float partialTicks, int damageStage) {
        RemixClient.INSTANCE.getClientServices();
        boolean depthOffset = ClientServices.moduleManager.chams.usesStorageDepthOffset();
        if (depthOffset) {
            GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
            GL11.glPolygonOffset(1.0F, -2000000.0F);
        }
        try {
            this.renderChestModel((TileEntityChest) tileEntity, x, y, z, partialTicks, damageStage);
        } finally {
            if (depthOffset) {
                GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
                GL11.glPolygonOffset(1.0F, 2000000.0F);
            }
        }
    }

    public void renderChestModel(TileEntityChest chest, double x, double y, double z,
                                           float partialTicks, int damageStage) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();
        int metadata = refreshChestMetadata(chest);
        // Only one half draws a double chest.
        if (chest.adjacentChestZNeg != null || chest.adjacentChestXNeg != null) {
            return;
        }

        boolean doubleChest = chest.adjacentChestXPos != null || chest.adjacentChestZPos != null;
        ModelChest model = doubleChest ? this.largeChest : this.simpleChest;
        if (damageStage >= 0) {
            this.beginDestroyStageTexture(damageStage, doubleChest ? 8.0F : 4.0F, 4.0F);
        } else {
            this.bindTexture(this.chestTexture(chest, doubleChest));
        }

        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();
        try {
            if (damageStage < 0) {
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }
            translateChestToPivot(x, y, z);
            if (metadata == 2 && chest.adjacentChestXPos != null) {
                GlStateManager.translate(1.0F, 0.0F, 0.0F);
            }
            if (metadata == 5 && chest.adjacentChestZPos != null) {
                GlStateManager.translate(0.0F, 0.0F, -1.0F);
            }
            rotateChestAtPivot(metadata);

            float opening = interpolateLid(chest, partialTicks);
            if (chest.adjacentChestZNeg != null) {
                float otherOpening = interpolateLid(chest.adjacentChestZNeg, partialTicks);
                if (otherOpening > opening) {
                    opening = otherOpening;
                }
            }
            if (chest.adjacentChestXNeg != null) {
                float otherOpening = interpolateLid(chest.adjacentChestXNeg, partialTicks);
                if (otherOpening > opening) {
                    opening = otherOpening;
                }
            }
            model.chestLid.rotateAngleX = chestLidRotation(opening);
            model.renderAll();
        } finally {
            GlStateManager.disableRescaleNormal();
            GlStateManager.popMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            if (damageStage >= 0) {
                endDestroyStageTexture();
            }
        }
    }

    private static int refreshChestMetadata(TileEntityChest chest) {
        if (!chest.hasWorldObj()) {
            return 0;
        }
        Block block = chest.getBlockType();
        int metadata = chest.getBlockMetadata();
        if (block instanceof BlockChest && metadata == 0) {
            World world = chest.getWorld();
            BlockPos position = chest.getPos();
            ((BlockChest) block).checkForSurroundingChests(world, position, world.getBlockState(position));
            metadata = chest.getBlockMetadata();
        }
        chest.checkForAdjacentChests();
        return metadata;
    }

    private ResourceLocation chestTexture(TileEntityChest chest, boolean doubleChest) {
        if (chest.getChestType() == 1) {
            return doubleChest ? textureTrappedDouble : textureTrapped;
        }
        if (this.useChristmasTextures) {
            return doubleChest ? textureChristmasDouble : textureChristmas;
        }
        return doubleChest ? textureNormalDouble : textureNormal;
    }

    private static float interpolateLid(TileEntityChest chest, float partialTicks) {
        return chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * partialTicks;
    }
}
