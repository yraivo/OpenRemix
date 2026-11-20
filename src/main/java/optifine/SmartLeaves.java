package optifine;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.Facing;

import java.util.ArrayList;
import java.util.List;

public class SmartLeaves {
    private static IBakedModel modelLeavesCullJungle;

    private static List generalQuadsCullAcacia;
    private static IBakedModel modelLeavesCullSpruce;
    private static IBakedModel modelLeavesDoubleSpruce;
    private static List generalQuadsCullBirch;
    private static IBakedModel modelLeavesDoubleJungle;
    private static List generalQuadsCullDarkOak;
    private static List generalQuadsCullJungle;
    private static List generalQuadsCullSpruce;

    private static IBakedModel modelLeavesCullBirch;
    private static IBakedModel modelLeavesCullDarkOak;
    private static IBakedModel modelLeavesCullOak;
    private static IBakedModel modelLeavesDoubleOak;
    private static IBakedModel modelLeavesDoubleDarkOak;
    private static List generalQuadsCullOak;
    private static IBakedModel modelLeavesCullAcacia;
    private static IBakedModel modelLeavesDoubleAcacia;
    private static IBakedModel modelLeavesDoubleBirch;

    static {
        optifine.SmartLeaves.modelLeavesCullAcacia = null;
        optifine.SmartLeaves.modelLeavesCullBirch = null;
        optifine.SmartLeaves.modelLeavesCullDarkOak = null;
        optifine.SmartLeaves.modelLeavesCullJungle = null;
        optifine.SmartLeaves.modelLeavesCullOak = null;
        optifine.SmartLeaves.modelLeavesCullSpruce = null;
        optifine.SmartLeaves.generalQuadsCullAcacia = null;
        optifine.SmartLeaves.generalQuadsCullBirch = null;
        optifine.SmartLeaves.generalQuadsCullDarkOak = null;
        optifine.SmartLeaves.generalQuadsCullJungle = null;
        optifine.SmartLeaves.generalQuadsCullOak = null;
        optifine.SmartLeaves.generalQuadsCullSpruce = null;
        optifine.SmartLeaves.modelLeavesDoubleAcacia = null;
        optifine.SmartLeaves.modelLeavesDoubleBirch = null;
        optifine.SmartLeaves.modelLeavesDoubleDarkOak = null;
        optifine.SmartLeaves.modelLeavesDoubleJungle = null;
        optifine.SmartLeaves.modelLeavesDoubleOak = null;
        optifine.SmartLeaves.modelLeavesDoubleSpruce = null;
    }

    private static IBakedModel getModelDoubleFace(IBakedModel iBakedModel) {
        int i = ReflectorMethod.cR();
        if (iBakedModel == null) {
            return null;
        }

        if (iBakedModel.getGeneralQuads().size() > 0) {
            OptiFineConfig.warn(
                    "SmartLeaves: Model is not cube, general quads: "
                            + iBakedModel.getGeneralQuads().size()
                            + ", model: "
                            + iBakedModel);
            return iBakedModel;
        }

        Facing[] facingArray = Facing.VALUES;
        int j = 0;

        while (j < facingArray.length) {
            Facing facing = facingArray[j];
            List faceQuads = iBakedModel.getFaceQuads(facing);
            if (faceQuads.size() != 1) {
                OptiFineConfig.warn(
                        "SmartLeaves: Model is not cube, side: "
                                + facing
                                + ", quads: "
                                + faceQuads.size()
                                + ", model: "
                                + iBakedModel);
                return iBakedModel;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        IBakedModel iBakedModelValue = ModelUtils.duplicateModel(iBakedModel);
        List[] alist = new List[facingArray.length];
        int l = 0;

        while (l < facingArray.length) {
            Facing facingValue = facingArray[l];
            List listValue = iBakedModelValue.getFaceQuads(facingValue);
            BakedQuad bakedQuad = (BakedQuad) listValue.get(0);
            BakedQuad bakedQuadValue =
                    new BakedQuad(
                            (int[]) bakedQuad.getVertexData().clone(),
                            bakedQuad.getTintIndex(),
                            bakedQuad.getFace(),
                            bakedQuad.getSprite());
            int[] aint = bakedQuadValue.getVertexData();
            int[] aint1 = (int[]) aint.clone();
            int k = aint.length / 4;
            System.arraycopy(aint, 0 * k, aint1, 3 * k, k);
            System.arraycopy(aint, 1 * k, aint1, 2 * k, k);
            System.arraycopy(aint, 2 * k, aint1, 1 * k, k);
            System.arraycopy(aint, 3 * k, aint1, 0 * k, k);
            System.arraycopy(aint1, 0, aint, 0, aint1.length);
            listValue.add(bakedQuadValue);
            l++;
            if (i == 0) {
                break;
            }
        }

        return iBakedModelValue;
    }

    private static List getGeneralQuadsSafe(IBakedModel iBakedModel) {
        return iBakedModel == null ? null : iBakedModel.getGeneralQuads();
    }

    static IBakedModel getModelCull(String text, List list) {
        int j = ReflectorMethod.cR();
        OptiFineConfig.getModelManager();

        int i = j;

        return null;
    }

    public static IBakedModel getLeavesModel(IBakedModel iBakedModel) {
        if (!OptiFineConfig.isTreesSmart()) {
            return iBakedModel;
        }

        List generalQuads = iBakedModel.getGeneralQuads();
        return generalQuads == optifine.SmartLeaves.generalQuadsCullAcacia
                ? optifine.SmartLeaves.modelLeavesDoubleAcacia
                : (generalQuads == optifine.SmartLeaves.generalQuadsCullBirch
                        ? optifine.SmartLeaves.modelLeavesDoubleBirch
                        : (generalQuads == optifine.SmartLeaves.generalQuadsCullDarkOak
                                ? optifine.SmartLeaves.modelLeavesDoubleDarkOak
                                : (generalQuads == optifine.SmartLeaves.generalQuadsCullJungle
                                        ? optifine.SmartLeaves.modelLeavesDoubleJungle
                                        : (generalQuads == optifine.SmartLeaves.generalQuadsCullOak
                                                ? optifine.SmartLeaves.modelLeavesDoubleOak
                                                : (generalQuads
                                                                == optifine.SmartLeaves
                                                                        .generalQuadsCullSpruce
                                                        ? optifine.SmartLeaves.modelLeavesDoubleSpruce
                                                        : iBakedModel)))));
    }

    public static void updateLeavesModels() {
        ArrayList arrayList = new ArrayList();
        optifine.SmartLeaves.modelLeavesCullAcacia = getModelCull("acacia", arrayList);
        optifine.SmartLeaves.modelLeavesCullBirch = getModelCull("birch", arrayList);
        optifine.SmartLeaves.modelLeavesCullDarkOak = getModelCull("dark_oak", arrayList);
        optifine.SmartLeaves.modelLeavesCullJungle = getModelCull("jungle", arrayList);
        optifine.SmartLeaves.modelLeavesCullOak = getModelCull("oak", arrayList);
        optifine.SmartLeaves.modelLeavesCullSpruce = getModelCull("spruce", arrayList);
        optifine.SmartLeaves.generalQuadsCullAcacia =
                getGeneralQuadsSafe(optifine.SmartLeaves.modelLeavesCullAcacia);
        optifine.SmartLeaves.generalQuadsCullBirch =
                getGeneralQuadsSafe(optifine.SmartLeaves.modelLeavesCullBirch);
        optifine.SmartLeaves.generalQuadsCullDarkOak =
                getGeneralQuadsSafe(optifine.SmartLeaves.modelLeavesCullDarkOak);
        optifine.SmartLeaves.generalQuadsCullJungle =
                getGeneralQuadsSafe(optifine.SmartLeaves.modelLeavesCullJungle);
        optifine.SmartLeaves.generalQuadsCullOak =
                getGeneralQuadsSafe(optifine.SmartLeaves.modelLeavesCullOak);
        optifine.SmartLeaves.generalQuadsCullSpruce =
                getGeneralQuadsSafe(optifine.SmartLeaves.modelLeavesCullSpruce);
        optifine.SmartLeaves.modelLeavesDoubleAcacia =
                getModelDoubleFace(optifine.SmartLeaves.modelLeavesCullAcacia);
        optifine.SmartLeaves.modelLeavesDoubleBirch =
                getModelDoubleFace(optifine.SmartLeaves.modelLeavesCullBirch);
        optifine.SmartLeaves.modelLeavesDoubleDarkOak =
                getModelDoubleFace(optifine.SmartLeaves.modelLeavesCullDarkOak);
        optifine.SmartLeaves.modelLeavesDoubleJungle =
                getModelDoubleFace(optifine.SmartLeaves.modelLeavesCullJungle);
        optifine.SmartLeaves.modelLeavesDoubleOak =
                getModelDoubleFace(optifine.SmartLeaves.modelLeavesCullOak);
        optifine.SmartLeaves.modelLeavesDoubleSpruce =
                getModelDoubleFace(optifine.SmartLeaves.modelLeavesCullSpruce);
        if (arrayList.size() > 0) {
            OptiFineConfig.dbg(
                    "Enable face culling: " + OptiFineConfig.arrayToString(arrayList.toArray()));
        }
    }
}
