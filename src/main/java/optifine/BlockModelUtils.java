package optifine;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.BlockPartRotation;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.Facing;

import remix.compat.vecmath.Vector3f;

import java.util.ArrayList;

public class BlockModelUtils {
    private static String[] packedTextFragments;

    public static IBakedModel makeModelCube(String text, int i) {
        TextureAtlasSprite atlasSprite =
                OptiFineConfig.getMinecraft().getTextureMapBlocks().getAtlasSprite(text);
        return makeModelCubeFromSprite(atlasSprite, i);
    }

    // region String decoding support

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public static IBakedModel makeModelCubeFromSprite(
            TextureAtlasSprite textureAtlasSprite, int i) {
        ArrayList arrayList = new ArrayList();
        int l = ReflectorMethod.f();
        Facing[] facingArray = Facing.VALUES;
        ArrayList arrayListValue = new ArrayList(facingArray.length);
        int j = l;
        int k = 0;

        while (k < facingArray.length) {
            Facing facing = facingArray[k];
            ArrayList arrayListValue2 = new ArrayList();
            arrayListValue2.add(makeBakedQuad(facing, textureAtlasSprite, i));
            arrayListValue.add(arrayListValue2);
            k++;
            if (j != 0) {
                break;
            }
        }

        return new SimpleBakedModel(
                arrayList,
                arrayListValue,
                true,
                true,
                textureAtlasSprite,
                ItemCameraTransforms.DEFAULT);
    }

    private static BakedQuad makeBakedQuad(
            Facing facing, TextureAtlasSprite textureAtlasSprite, int i) {
        Vector3f vector3f = new Vector3f(0.0F, 0.0F, 0.0F);
        Vector3f vector3fValue = new Vector3f(16.0F, 16.0F, 16.0F);
        BlockFaceUV blockFaceUV = new BlockFaceUV(new float[] {0.0F, 0.0F, 16.0F, 16.0F}, 0);
        BlockPartFace blockPartFace =
                new BlockPartFace(
                        facing, i, packedTextFragments[0] + facing.getName(), blockFaceUV);
        ModelRotation modelRotation = ModelRotation.X0_Y0;
        Object object = null;
        byte b0 = 0;
        byte b1 = 1;
        FaceBakery faceBakery = new FaceBakery();
        return faceBakery.makeBakedQuad(
                vector3f,
                vector3fValue,
                blockPartFace,
                textureAtlasSprite,
                facing,
                modelRotation,
                (BlockPartRotation) object,
                remix.compat.compiler.JvmBooleanSlots.fromInt(b0),
                remix.compat.compiler.JvmBooleanSlots.fromInt(b1));
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        optifine.BlockModelUtils.packedTextFragments[0] = new String("#");
    }
    // endregion
}
