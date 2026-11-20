package optifine;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.util.Facing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModelUtils {

    private static String[] packedTextFragments;

    public static List duplicateQuadList(List list) {
        ArrayList arrayList = new ArrayList();
        int j = ReflectorMethod.f();
        Iterator iterator = list.iterator();
        int i = j;

        while (iterator.hasNext()) {
            BakedQuad bakedQuad = (BakedQuad) iterator.next();
            BakedQuad bakedQuadValue = duplicateQuad(bakedQuad);
            arrayList.add(bakedQuadValue);
            if (i != 0) {
                break;
            }
        }

        return arrayList;
    }

    public static void dbgQuad(String text, BakedQuad bakedQuad, String textValue) {
        OptiFineConfig.dbg(
                textValue
                        + "Quad: "
                        + bakedQuad.getClass().getName()
                        + ", type: "
                        + text
                        + ", face: "
                        + bakedQuad.getFace()
                        + ", tint: "
                        + bakedQuad.getTintIndex()
                        + ", sprite: "
                        + bakedQuad.getSprite());
        dbgVertexData(bakedQuad.getVertexData(), "  " + textValue);
    }

    public static void dbgModel(IBakedModel iBakedModel) {
        int i = ReflectorMethod.f();
        if (iBakedModel != null) {
            OptiFineConfig.dbg(
                    "Model: "
                            + iBakedModel
                            + ", ao: "
                            + iBakedModel.isAmbientOcclusion()
                            + ", gui3d: "
                            + iBakedModel.isGui3d()
                            + ", builtIn: "
                            + iBakedModel.isBuiltInRenderer()
                            + ", particle: "
                            + iBakedModel.getTexture());
            Facing[] facingArray = Facing.VALUES;
            int j = 0;

            while (j < facingArray.length) {
                Facing facing = facingArray[j];
                List faceQuads = iBakedModel.getFaceQuads(facing);
                dbgQuads(facing.getName(), faceQuads, "  ");
                j++;
                if (i != 0) {
                    break;
                }
            }

            List listValue = iBakedModel.getGeneralQuads();
            dbgQuads("General", listValue, "  ");
        }
    }

    private static void dbgQuads(String text, List list, String textValue) {
        int j = ReflectorMethod.f();
        Iterator iterator = list.iterator();
        int i = j;

        while (iterator.hasNext()) {
            BakedQuad bakedQuad = (BakedQuad) iterator.next();
            dbgQuad(text, bakedQuad, textValue);
            if (i != 0) {
                break;
            }
        }
    }

    public static void dbgVertexData(int[] aint, String text) {
        int j = aint.length / 4;
        OptiFineConfig.dbg(text + "Length: " + aint.length + ", step: " + j);
        int j1 = ReflectorMethod.cR();
        int k = 0;
        int i = j1;

        while (k < 4) {
            int l = k * j;
            float f = Float.intBitsToFloat(aint[l + 0]);
            float f1 = Float.intBitsToFloat(aint[l + 1]);
            float f2 = Float.intBitsToFloat(aint[l + 2]);
            int i1 = aint[l + 3];
            float f3 = Float.intBitsToFloat(aint[l + 4]);
            float f4 = Float.intBitsToFloat(aint[l + 5]);
            OptiFineConfig.dbg(
                    text
                            + k
                            + " xyz: "
                            + f
                            + packedTextFragments[0]
                            + f1
                            + packedTextFragments[1]
                            + f2
                            + " col: "
                            + i1
                            + " u,v: "
                            + f3
                            + packedTextFragments[2]
                            + f4);
            k++;
            if (i == 0) {
                break;
            }
        }
    }

    public static IBakedModel duplicateModel(IBakedModel iBakedModel) {
        int k = ReflectorMethod.cR();
        List list = duplicateQuadList(iBakedModel.getGeneralQuads());
        Facing[] facingArray = Facing.VALUES;
        ArrayList arrayList = new ArrayList();
        int j = 0;
        int i = k;

        while (j < facingArray.length) {
            Facing facing = facingArray[j];
            List listValue = iBakedModel.getFaceQuads(facing);
            List listValue2 = duplicateQuadList(listValue);
            arrayList.add(listValue2);
            j++;
            if (i == 0) {
                break;
            }
        }

        return new SimpleBakedModel(
                list,
                arrayList,
                iBakedModel.isAmbientOcclusion(),
                iBakedModel.isGui3d(),
                iBakedModel.getTexture(),
                iBakedModel.getItemCameraTransforms());
    }

    public static BakedQuad duplicateQuad(BakedQuad bakedQuad) {
        return new BakedQuad(
                (int[]) bakedQuad.getVertexData().clone(),
                bakedQuad.getTintIndex(),
                bakedQuad.getFace(),
                bakedQuad.getSprite());
    }

    static {
        optifine.ModelUtils.packedTextFragments = new String[5];
        optifine.ModelUtils.packedTextFragments[0] = new String(",");
        optifine.ModelUtils.packedTextFragments[1] = new String(",");
        optifine.ModelUtils.packedTextFragments[2] = new String(",");
    }
}
