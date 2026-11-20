package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.Facing;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

public class TileEntityRendererChestHelper {
    public static TileEntityRendererChestHelper instance;

    private TileEntityChest aFs;
    private TileEntityBanner banner;

    private TileEntityChest aFv = new TileEntityChest(0);
    private TileEntitySkull skull;
    private TileEntityEnderChest enderChest;

    static {
        net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper.instance = new TileEntityRendererChestHelper();
    }

    public TileEntityRendererChestHelper() {
        this.aFs = new TileEntityChest(1);
        this.enderChest = new TileEntityEnderChest();
        this.banner = new TileEntityBanner();
        this.skull = new TileEntitySkull();
    }

    public void renderByItem(ItemStack itemStack) {
        TileEntitySpecialRenderer.getLegacyTileRendererState();

        if (itemStack.getItem() == Items.banner) {
            this.banner.setItemValues(itemStack);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.banner, 0.0, 0.0, 0.0, 0.0F);

            return;
        }

        if (itemStack.getItem() == Items.skull) {
            GameProfile gameprofile = null;
            if (itemStack.hasTagCompound()) {
                CompoundTag tag = itemStack.getTag();
                if (tag.hasKey("SkullOwner", 10)) {
                    gameprofile =
                            NBTUtil.readGameProfileFromNBT(tag.getCompoundTag("SkullOwner"));
                } else if (tag.hasKey("SkullOwner", 8)
                        && tag.getString("SkullOwner").length() > 0) {
                    GameProfile gameprofile1 =
                            new GameProfile((UUID) null, tag.getString("SkullOwner"));
                    gameprofile = TileEntitySkull.updateGameprofile(gameprofile1);
                    tag.removeTag("SkullOwner");
                    tag.setTag(
                            "SkullOwner", NBTUtil.writeGameProfile(new CompoundTag(), gameprofile));
                }
            }

            if (TileEntitySkullRenderer.instance != null) {
                GlStateManager.pushMatrix();
                GlStateManager.translate(-0.5F, 0.0F, -0.5F);
                GlStateManager.scale(2.0F, 2.0F, 2.0F);
                GlStateManager.disableCull();
                TileEntitySkullRenderer.instance.renderSkull(
                        0.0F,
                        0.0F,
                        0.0F,
                        Facing.UP,
                        0.0F,
                        itemStack.getItemDamage(),
                        gameprofile,
                        -1);
                GlStateManager.enableCull();
                GlStateManager.popMatrix();
            }

            return;
        }

        Block block = Block.getBlockFromItem(itemStack.getItem());
        if (block == Blocks.ender_chest) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.enderChest, 0.0, 0.0, 0.0, 0.0F);

            return;
        }

        if (block == Blocks.trapped_chest) {
            TileEntityRendererDispatcher.instance.renderTileEntityAt(this.aFs, 0.0, 0.0, 0.0, 0.0F);

            return;
        }

        TileEntityRendererDispatcher.instance.renderTileEntityAt(this.aFv, 0.0, 0.0, 0.0, 0.0F);
    }
}
