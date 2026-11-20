package net.minecraft.client.renderer.block.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.Facing;
import net.minecraft.util.ResourceLocation;

import remix.compat.vecmath.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ItemModelGenerator {

    public static List LAYERS;

    private void func_178396_a(
            ItemModelGeneratorSpanFacing itemModelGeneratorSpanFacing,
            List list,
            int[] aint,
            int i,
            int j,
            int k,
            int l,
            boolean flag) {
        int i1 =
                this.func_178391_a(
                                        aint,
                                        i + itemModelGeneratorSpanFacing.func_178372_b(),
                                        j + itemModelGeneratorSpanFacing.func_178371_c(),
                                        k,
                                        l)
                                && flag
                        ? 1
                        : 0;
        if (i1 != 0) {
            this.func_178395_a(list, itemModelGeneratorSpanFacing, i, j);
        }
    }

    public ModelBlock makeItemModel(TextureMap textureMap, ModelBlock modelBlock) {
        int k = BakedQuad.eR();
        HashMap hashmap = Maps.newHashMap();
        int i = k;
        ArrayList arrayList = Lists.newArrayList();
        int j = 0;

        while (j < net.minecraft.client.renderer.block.model.ItemModelGenerator.LAYERS.size()) {
            String text =
                    (String)
                            net.minecraft.client.renderer.block.model.ItemModelGenerator.LAYERS.get(
                                    j);
            if (!modelBlock.isTexturePresent(text) && i == 0) {
                break;
            }

            String textValue = modelBlock.resolveTextureName(text);
            hashmap.put(text, textValue);
            TextureAtlasSprite atlasSprite =
                    textureMap.getAtlasSprite(new ResourceLocation(textValue).toString());
            arrayList.addAll(this.method_e71b3d54e95f9734ec88(j, text, atlasSprite));
            j++;
            if (i != 0) {
                break;
            }
        }

        if (arrayList.isEmpty()) {
            return null;
        }

        hashmap.put(
                "particle",
                modelBlock.isTexturePresent("particle")
                        ? modelBlock.resolveTextureName("particle")
                        : (String) hashmap.get("layer0"));
        return new ModelBlock(
                arrayList,
                hashmap,
                false,
                false,
                new ItemCameraTransforms(
                        modelBlock.getThirdPersonTransform(),
                        modelBlock.getFirstPersonTransform(),
                        modelBlock.getHeadTransform(),
                        modelBlock.getInGuiTransform()));
    }

    static {
        net.minecraft.client.renderer.block.model.ItemModelGenerator.LAYERS =
                Lists.newArrayList(new String[] {"layer0", "layer1", "layer2", "layer3", "layer4"});
    }

    private List method_078457c165a604a4f922(
            TextureAtlasSprite textureAtlasSprite, String text, int i) {
        float iconWidth = textureAtlasSprite.getIconWidth();
        int k = BakedQuad.cM();
        float iconHeight = textureAtlasSprite.getIconHeight();
        int j = k;
        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_7781 =
                        (this.method_b4186309defd05bdd3ab(textureAtlasSprite)).iterator();
                (__recoveryIterator171_7781).hasNext(); ) {
            ItemModelGeneratorSpan itemModelGeneratorSpan =
                    (ItemModelGeneratorSpan) (__recoveryIterator171_7781).next();
            float f2 = 0.0F;
            float f3 = 0.0F;
            float f4 = 0.0F;
            float f5 = 0.0F;
            float f6 = 0.0F;
            float f7 = 0.0F;
            float f8 = 0.0F;
            float f9 = 0.0F;
            float f10 = 0.0F;
            float f11 = 0.0F;
            float f12 = itemModelGeneratorSpan.method_7eec03da329a017ec143();
            float f13 = itemModelGeneratorSpan.method_beea3e16fe3ec0995eb9();
            float f14 = itemModelGeneratorSpan.aW();
            ItemModelGeneratorSpanFacing itemModelGeneratorSpanFacing =
                    itemModelGeneratorSpan.getSpanFacing();
            switch (itemModelGeneratorSpanFacing) {
                case UP:
                    f6 = f12;
                    f2 = f12;
                    f4 = f7 = f13 + 1.0F;
                    f8 = f14;
                    f3 = f14;
                    f9 = f14;
                    f5 = f14;
                    f10 = 16.0F / iconWidth;
                    f11 = 16.0F / (iconHeight - 1.0F);
                    if (j != 0) {
                        break;
                    }
                case DOWN:
                    f9 = f14;
                    f8 = f14;
                    f6 = f12;
                    f2 = f12;
                    f4 = f7 = f13 + 1.0F;
                    f3 = f14 + 1.0F;
                    f5 = f14 + 1.0F;
                    f10 = 16.0F / iconWidth;
                    f11 = 16.0F / (iconHeight - 1.0F);
                    if (j != 0) {
                        break;
                    }
                case LEFT:
                    f6 = f14;
                    f2 = f14;
                    f7 = f14;
                    f4 = f14;
                    f9 = f12;
                    f3 = f12;
                    f5 = f8 = f13 + 1.0F;
                    f10 = 16.0F / (iconWidth - 1.0F);
                    f11 = 16.0F / iconHeight;
                    if (j != 0) {
                        break;
                    }
                case RIGHT:
                    f7 = f14;
                    f6 = f14;
                    f2 = f14 + 1.0F;
                    f4 = f14 + 1.0F;
                    f9 = f12;
                    f3 = f12;
                    f5 = f8 = f13 + 1.0F;
                    f10 = 16.0F / (iconWidth - 1.0F);
                    f11 = 16.0F / iconHeight;
            }

            float f15 = 16.0F / iconWidth;
            float f16 = 16.0F / iconHeight;
            f2 *= f15;
            f4 *= f15;
            f3 *= f16;
            f5 *= f16;
            f3 = 16.0F - f3;
            f5 = 16.0F - f5;
            f6 *= f10;
            f7 *= f10;
            f8 *= f11;
            f9 *= f11;
            HashMap hashmap = Maps.newHashMap();
            hashmap.put(
                    itemModelGeneratorSpanFacing.getFacing(),
                    new BlockPartFace(
                            (Facing) null,
                            i,
                            text,
                            new BlockFaceUV(new float[] {f6, f8, f7, f9}, 0)));
            switch (itemModelGeneratorSpanFacing) {
                case UP:
                    arrayList.add(
                            new BlockPart(
                                    new Vector3f(f2, f3, 7.5F),
                                    new Vector3f(f4, f3, 8.5F),
                                    hashmap,
                                    (BlockPartRotation) null,
                                    true));
                    if (j != 0) {
                        break;
                    }
                case DOWN:
                    arrayList.add(
                            new BlockPart(
                                    new Vector3f(f2, f5, 7.5F),
                                    new Vector3f(f4, f5, 8.5F),
                                    hashmap,
                                    (BlockPartRotation) null,
                                    true));
                    if (j != 0) {
                        break;
                    }
                case LEFT:
                    arrayList.add(
                            new BlockPart(
                                    new Vector3f(f2, f3, 7.5F),
                                    new Vector3f(f2, f5, 8.5F),
                                    hashmap,
                                    (BlockPartRotation) null,
                                    true));
                    if (j != 0) {
                        break;
                    }
                case RIGHT:
                    arrayList.add(
                            new BlockPart(
                                    new Vector3f(f4, f3, 7.5F),
                                    new Vector3f(f4, f5, 8.5F),
                                    hashmap,
                                    (BlockPartRotation) null,
                                    true));
            }

            if (j == 0) {
                break;
            }
        }

        return arrayList;
    }

    private List method_e71b3d54e95f9734ec88(
            int i, String text, TextureAtlasSprite textureAtlasSprite) {
        HashMap hashmap = Maps.newHashMap();
        hashmap.put(
                Facing.SOUTH,
                new BlockPartFace(
                        (Facing) null,
                        i,
                        text,
                        new BlockFaceUV(new float[] {0.0F, 0.0F, 16.0F, 16.0F}, 0)));
        hashmap.put(
                Facing.NORTH,
                new BlockPartFace(
                        (Facing) null,
                        i,
                        text,
                        new BlockFaceUV(new float[] {16.0F, 0.0F, 0.0F, 16.0F}, 0)));
        ArrayList arrayList = Lists.newArrayList();
        arrayList.add(
                new BlockPart(
                        new Vector3f(0.0F, 0.0F, 7.5F),
                        new Vector3f(16.0F, 16.0F, 8.5F),
                        hashmap,
                        (BlockPartRotation) null,
                        true));
        arrayList.addAll(this.method_078457c165a604a4f922(textureAtlasSprite, text, i));
        return arrayList;
    }

    private boolean func_178391_a(int[] aint, int i, int j, int k, int l) {
        return i >= 0 && j >= 0 && i < k && j < l ? (aint[j * k + i] >> 24 & 0xFF) == 0 : true;
    }

    private void func_178395_a(
            List list, ItemModelGeneratorSpanFacing itemModelGeneratorSpanFacing, int i, int j) {
        ItemModelGeneratorSpan itemModelGeneratorSpan = null;
        int k1 = BakedQuad.cM();
        Iterator iterator = list.iterator();
        int k = k1;

        while (iterator.hasNext()) {
            ItemModelGeneratorSpan itemModelGeneratorSpanValue =
                    (ItemModelGeneratorSpan) iterator.next();
            if (itemModelGeneratorSpanValue.getSpanFacing() == itemModelGeneratorSpanFacing) {
                int l =
                        ItemModelGeneratorSpanFacing.access$000(itemModelGeneratorSpanFacing)
                                ? j
                                : i;
                if (itemModelGeneratorSpanValue.aW() == l) {
                    itemModelGeneratorSpan = itemModelGeneratorSpanValue;
                    break;
                }
            }
        }

        int i1 = ItemModelGeneratorSpanFacing.access$000(itemModelGeneratorSpanFacing) ? j : i;
        int j1 = ItemModelGeneratorSpanFacing.access$000(itemModelGeneratorSpanFacing) ? i : j;
        if (itemModelGeneratorSpan == null) {
            list.add(new ItemModelGeneratorSpan(itemModelGeneratorSpanFacing, j1, i1));
            if (k != 0) {
                return;
            }
        }

        itemModelGeneratorSpan.a(j1);
    }

    private List method_b4186309defd05bdd3ab(TextureAtlasSprite textureAtlasSprite) {
        int l1 = BakedQuad.cM();
        int j = textureAtlasSprite.getIconWidth();
        int k = textureAtlasSprite.getIconHeight();
        ArrayList arrayList = Lists.newArrayList();
        int l = 0;
        int i = l1;

        while (l < textureAtlasSprite.getFrameCount()) {
            int[] aint = textureAtlasSprite.getFrameTextureData(l)[0];
            int i1 = 0;

            while (true) {
                if (i1 < k) {
                    int j1 = 0;

                    while (j1 < j) {
                        int k1 = !this.func_178391_a(aint, j1, i1, j, k) ? 1 : 0;
                        this.func_178396_a(
                                ItemModelGeneratorSpanFacing.UP,
                                arrayList,
                                aint,
                                j1,
                                i1,
                                j,
                                k,
                                remix.compat.compiler.JvmBooleanSlots.fromInt(k1));
                        this.func_178396_a(
                                ItemModelGeneratorSpanFacing.DOWN,
                                arrayList,
                                aint,
                                j1,
                                i1,
                                j,
                                k,
                                remix.compat.compiler.JvmBooleanSlots.fromInt(k1));
                        this.func_178396_a(
                                ItemModelGeneratorSpanFacing.LEFT,
                                arrayList,
                                aint,
                                j1,
                                i1,
                                j,
                                k,
                                remix.compat.compiler.JvmBooleanSlots.fromInt(k1));
                        this.func_178396_a(
                                ItemModelGeneratorSpanFacing.RIGHT,
                                arrayList,
                                aint,
                                j1,
                                i1,
                                j,
                                k,
                                remix.compat.compiler.JvmBooleanSlots.fromInt(k1));
                        j1++;
                        if (i == 0) {
                            break;
                        }
                    }

                    i1++;
                    if (i != 0) {
                        continue;
                    }
                }

                l++;
                if (i == 0) {
                    return arrayList;
                }
                break;
            }
        }

        return arrayList;
    }
}
