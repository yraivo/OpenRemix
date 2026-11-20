package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BreakingFour;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.util.Facing;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

public class SimpleBakedModelBuilder {

    private final List builderGeneralQuads = Lists.newArrayList();
    private boolean builderGui3d;
    private List builderFaceQuads;
    private boolean builderAmbientOcclusion;
    private TextureAtlasSprite builderTexture;
    private ItemCameraTransforms builderCameraTransforms;
    private static String g;

    public IBakedModel makeBakedModel() {
        try {
            if (this.builderTexture == null) {
                throw new RuntimeException(g);
            }
        } catch (RuntimeException runtimeexception) {
            throw method_1a1f08aee5fe72868e61(runtimeexception);
        }

        return new SimpleBakedModel(
                this.builderGeneralQuads, this.builderFaceQuads, this.builderAmbientOcclusion, this.builderGui3d, this.builderTexture, this.builderCameraTransforms);
    }

    // region String decoding support

    // endregion

    public SimpleBakedModelBuilder(
            IBakedModel iBakedModel, TextureAtlasSprite textureAtlasSprite) {
        this(
                iBakedModel.isAmbientOcclusion(),
                iBakedModel.isGui3d(),
                iBakedModel.getItemCameraTransforms());
        this.builderTexture = iBakedModel.getTexture();
        int j = ModelResourceLocation.aW();
        Facing[] facingArray = Facing.values();
        int i = j;

        for (Facing facing : facingArray) {
            this.addFaceBreakingFours(iBakedModel, textureAtlasSprite, facing);
            if (i != 0) {
                break;
            }
        }

        try {
            this.addGeneralBreakingFours(iBakedModel, textureAtlasSprite);
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                ModelResourceLocation.b(++i);
            }
        } catch (RuntimeException runtimeexception) {
            throw method_1a1f08aee5fe72868e61(runtimeexception);
        }
    }

    private void addGeneralBreakingFours(
            IBakedModel iBakedModel, TextureAtlasSprite textureAtlasSprite) {
        int j = ModelResourceLocation.cR();
        Iterator iterator = iBakedModel.getGeneralQuads().iterator();
        int i = j;

        while (iterator.hasNext()) {
            BakedQuad bakedQuad = (BakedQuad) iterator.next();
            this.addGeneralQuad(
                    new BreakingFour(bakedQuad, textureAtlasSprite));
            if (i == 0) {
                break;
            }
        }
    }

    public SimpleBakedModelBuilder addGeneralQuad(BakedQuad bakedQuad) {
        this.builderGeneralQuads.add(bakedQuad);
        return this;
    }

    // region String decoding support

    // endregion

    public SimpleBakedModelBuilder setTexture(TextureAtlasSprite aAyValue) {
        this.builderTexture = aAyValue;
        return this;
    }

    static {
        g = "Missing particle!";
    }

    public SimpleBakedModelBuilder(ModelBlock modelBlock) {
        this(
                modelBlock.isAmbientOcclusion(),
                modelBlock.isGui3d(),
                new ItemCameraTransforms(
                        modelBlock.getThirdPersonTransform(), modelBlock.getFirstPersonTransform(), modelBlock.getHeadTransform(), modelBlock.getInGuiTransform()));
    }

    private void addFaceBreakingFours(
            IBakedModel iBakedModel, TextureAtlasSprite textureAtlasSprite, Facing facing) {
        int j = ModelResourceLocation.aW();
        Iterator iterator = iBakedModel.getFaceQuads(facing).iterator();
        int i = j;

        while (iterator.hasNext()) {
            BakedQuad bakedQuad = (BakedQuad) iterator.next();
            this.addFaceQuad(
                    facing, new BreakingFour(bakedQuad, textureAtlasSprite));
            if (i != 0) {
                break;
            }
        }
    }

    private SimpleBakedModelBuilder(boolean flag, boolean flag1, ItemCameraTransforms azjValue) {
        int j = ModelResourceLocation.aW();
        this.builderFaceQuads = Lists.newArrayListWithCapacity(6);
        int i = j;

        for (Facing facing : Facing.values()) {
            this.builderFaceQuads.add(Lists.newArrayList());
            if (i != 0) {
                break;
            }
        }

        this.builderAmbientOcclusion = flag;
        this.builderGui3d = flag1;
        this.builderCameraTransforms = azjValue;
    }

    public SimpleBakedModelBuilder addFaceQuad(
            Facing facing, BakedQuad bakedQuad) {
        ((List) this.builderFaceQuads.get(facing.ordinal())).add(bakedQuad);
        return this;
    }

    private static RuntimeException method_1a1f08aee5fe72868e61(RuntimeException runtimeexception) {
        return runtimeexception;
    }
}
