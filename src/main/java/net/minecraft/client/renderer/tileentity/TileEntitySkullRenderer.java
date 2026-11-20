package net.minecraft.client.renderer.tileentity;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelHumanoidHead;
import net.minecraft.client.model.ModelSkeletonHead;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.Facing;
import net.minecraft.util.ResourceLocation;

import java.util.Map;
import java.util.UUID;

public class TileEntitySkullRenderer extends TileEntitySpecialRenderer {
    private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
    private static ResourceLocation ZOMBIE_TEXTURES;
    private static ResourceLocation CREEPER_TEXTURES;
    private static ResourceLocation SKELETON_TEXTURES;

    public static TileEntitySkullRenderer instance;
    private static ResourceLocation WITHER_SKELETON_TEXTURES;
    private final ModelSkeletonHead humanoidHead = new ModelHumanoidHead();

    // region String decoding support

    // endregion

    static {
        net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer.SKELETON_TEXTURES =
                new ResourceLocation("textures/entity/skeleton/skeleton.png");
        net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer.WITHER_SKELETON_TEXTURES =
                new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
        net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer.ZOMBIE_TEXTURES =
                new ResourceLocation("textures/entity/zombie/zombie.png");
        net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer.CREEPER_TEXTURES =
                new ResourceLocation("textures/entity/creeper/creeper.png");
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    // region String decoding support

    // endregion

    public void method_1440633709c6d1fdb647(
            TileEntitySkull tileEntitySkull, double d0, double d1, double d2, float f, int i) {
        Facing facing = Facing.getFront(tileEntitySkull.getBlockMetadata() & 7);
        this.renderSkull(
                (float) d0,
                (float) d1,
                (float) d2,
                facing,
                tileEntitySkull.getSkullRotation() * 360 / 16.0F,
                tileEntitySkull.getSkullType(),
                tileEntitySkull.getPlayerProfile(),
                i);
    }

    // region String decoding support

    // endregion

    public void renderSkull(
            float f,
            float f1,
            float f2,
            Facing facing,
            float f3,
            int i,
            GameProfile gameprofile,
            int j) {
        int[] aint;
        ModelSkeletonHead modelSkeletonHead;
        {
            int[] aint1 = TileEntitySpecialRenderer.getLegacyTileRendererState();
            modelSkeletonHead = this.skeletonHead;
            aint = aint1;
            if (j >= 0) {
                this.bindTexture(
                        net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer
                                .DESTROY_STAGES[j]);
                GlStateManager.matrixMode(5890);
                GlStateManager.pushMatrix();
                GlStateManager.scale(4.0F, 2.0F, 1.0F);
                GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
                GlStateManager.matrixMode(5888);

            } else {

                switch (i) {
                    case 0:
                    default:
                        this.bindTexture(
                                net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer
                                        .SKELETON_TEXTURES);

                        break;

                    case 1:
                        this.bindTexture(
                                net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer
                                        .WITHER_SKELETON_TEXTURES);

                        break;

                    case 2:
                        this.bindTexture(
                                net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer
                                        .ZOMBIE_TEXTURES);
                        modelSkeletonHead = this.humanoidHead;

                        break;

                    case 3:
                        modelSkeletonHead = this.humanoidHead;
                        ResourceLocation resourceLocation =
                                DefaultPlayerSkin.getDefaultSkinLegacy();
                        if (gameprofile != null) {
                            MinecraftClient minecraftClient = MinecraftClient.getMinecraft();

                            try {
                                {
                                    Map map =
                                            minecraftClient
                                                    .getSkinManager()
                                                    .loadSkinFromCache(gameprofile);
                                    if (map != null && map.containsKey(Type.SKIN)) {
                                        resourceLocation =
                                                minecraftClient
                                                        .getSkinManager()
                                                        .loadSkin(
                                                                (MinecraftProfileTexture)
                                                                        map.get(Type.SKIN),
                                                                Type.SKIN);

                                    } else {

                                        UUID uuid = PlayerEntity.getUUID(gameprofile);
                                        resourceLocation = DefaultPlayerSkin.getDefaultSkin(uuid);
                                    }
                                }
                            } catch (Exception exception) {
                            }
                        }

                        this.bindTexture(resourceLocation);

                        break;

                    case 4:
                        this.bindTexture(
                                net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer
                                        .CREEPER_TEXTURES);
                }
            }
        }

        label46:
        {
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            if (facing != Facing.UP) {
                switch (facing) {
                    case NORTH:
                        GlStateManager.translate(f + 0.5F, f1 + 0.25F, f2 + 0.74F);

                        break label46;

                    case SOUTH:
                        GlStateManager.translate(f + 0.5F, f1 + 0.25F, f2 + 0.26F);
                        f3 = 180.0F;

                        break label46;

                    case WEST:
                        GlStateManager.translate(f + 0.74F, f1 + 0.25F, f2 + 0.5F);
                        f3 = 270.0F;

                        break label46;

                    case EAST:
                    default:
                        GlStateManager.translate(f + 0.26F, f1 + 0.25F, f2 + 0.5F);
                        f3 = 90.0F;

                        break label46;
                }
            }

            GlStateManager.translate(f + 0.5F, f1, f2 + 0.5F);
        }

        float f4 = 0.0625F;
        GlStateManager.enableRescaleNormal();
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.enableAlpha();
        modelSkeletonHead.render((Entity) null, 0.0F, 0.0F, 0.0F, f3, 0.0F, f4);
        GlStateManager.popMatrix();
        if (j >= 0) {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    public void renderTileEntityAt(
            TileEntity tileEntity, double d0, double d1, double d2, float f, int i) {
        this.method_1440633709c6d1fdb647((TileEntitySkull) tileEntity, d0, d1, d2, f, i);
    }

    public void setRendererDispatcher(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super.setRendererDispatcher(tileEntityRendererDispatcher);
        net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer.instance = this;
    }
}
