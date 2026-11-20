package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransformsTransformType;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.Facing;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

public class LayerCustomHead implements LayerRenderer {
    private ModelRenderer field_177209_a;

    public boolean shouldCombineTextures() {
        return true;
    }

    public LayerCustomHead(ModelRenderer modelRenderer) {
        this.field_177209_a = modelRenderer;
    }


    public void doRenderLayer(
            LivingEntity livingEntity,
            float f5,
            float f6,
            float f7,
            float f8,
            float f9,
            float f10,
            float f) {
        int[] aint1 = LayerArmorBase.method_650532ba484532b3efb0();
        ItemStack itemStack = livingEntity.getCurrentArmor(3);
        int[] aint = aint1;
        if (itemStack != null && itemStack.getItem() != null) {
            Item item = itemStack.getItem();
            MinecraftClient minecraftClient = MinecraftClient.getMinecraft();
            GlStateManager.pushMatrix();
            if (livingEntity.isSneaking()) {
                GlStateManager.translate(0.0F, 0.2F, 0.0F);
            }

            int i =
                    !(livingEntity instanceof EntityVillager)
                                    && (!(livingEntity instanceof EntityZombie)
                                            || !((EntityZombie) livingEntity).isVillager())
                            ? 0
                            : 1;
            if (i == 0 && livingEntity.isChild()) {
                float f1 = 2.0F;
                float f2 = 1.4F;
                GlStateManager.scale(f2 / f1, f2 / f1, f2 / f1);
                GlStateManager.translate(0.0F, 16.0F * f, 0.0F);
            }

            label46:
            {
                this.field_177209_a.postRender(0.0625F);
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                if (item instanceof ItemBlock) {
                    float f3 = 0.625F;
                    GlStateManager.translate(0.0F, -0.25F, 0.0F);
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                    GlStateManager.scale(f3, -f3, -f3);
                    if (i != 0) {
                        GlStateManager.translate(0.0F, 0.1875F, 0.0F);
                    }

                    minecraftClient
                            .getItemRenderer()
                            .renderItem(
                                    livingEntity,
                                    itemStack,
                                    ItemCameraTransformsTransformType.HEAD);
                    if (aint == null) {
                        break label46;
                    }
                }

                if (item == Items.skull) {
                    float f4 = 1.1875F;
                    GlStateManager.scale(f4, -f4, -f4);
                    if (i != 0) {
                        GlStateManager.translate(0.0F, 0.0625F, 0.0F);
                    }

                    GameProfile gameprofile = null;
                    if (itemStack.hasTagCompound()) {
                        CompoundTag tag = itemStack.getTag();
                        if (tag.hasKey("SkullOwner", 10)) {
                            gameprofile =
                                    NBTUtil.readGameProfileFromNBT(
                                            tag.getCompoundTag("SkullOwner"));
                        } else if (tag.hasKey("SkullOwner", 8)) {
                            gameprofile =
                                    TileEntitySkull.updateGameprofile(
                                            new GameProfile(
                                                    (UUID) null, tag.getString("SkullOwner")));
                            tag.setTag(
                                    "SkullOwner",
                                    NBTUtil.writeGameProfile(new CompoundTag(), gameprofile));
                        }
                    }

                    TileEntitySkullRenderer.instance.renderSkull(
                            -0.5F,
                            0.0F,
                            -0.5F,
                            Facing.UP,
                            180.0F,
                            itemStack.getItemDamage(),
                            gameprofile,
                            -1);
                }
            }

            GlStateManager.popMatrix();
        }
    }
}
