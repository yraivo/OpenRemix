package net.minecraft.util;

import java.util.Random;

public class EnchantmentNameParts {
    private String[] namePartsArray;
    private Random rand = new Random();
    private static EnchantmentNameParts instance;
    private static String[] packedTextFragments;
    private static String d;

    // region String decoding support

    // endregion

    public void reseedRandomGenerator(long i) {
        this.rand.setSeed(i);
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.util.EnchantmentNameParts.packedTextFragments = new String[4];
        net.minecraft.util.EnchantmentNameParts.packedTextFragments[0] = new String(" ");
        net.minecraft.util.EnchantmentNameParts.packedTextFragments[1] = new String("");
        net.minecraft.util.EnchantmentNameParts.packedTextFragments[2] = new String(" ");
        net.minecraft.util.EnchantmentNameParts.packedTextFragments[3] =
                new String(
                        "rnJ\u000bFsXct\u000fX@mSjj\\\u000bHs]grZ\u000bAzNgrN\u000bMvWrs\u000fSZeF\u007f&MGFlO&eZYPz\u001cjoHCW?XgtDEFlO&`FYF?]ot\u000fNBmHn&XJWzN&n@_\u0003{N\u007f&LDO{\u001cqc[\u000bJxRorJ\u000bPqI``\u000fNN}UaaJE\u0003kKou[\u000bPwStrJE\u0003lHtc[HK?ZobKGF?Xcu[YLf\u001cokM^F?[gjYJMvFc&JE@w]hr\u000fMQzY&jFFJkYb&]JMxY&iI\u000bWpKgtKX\u0003vRuoKN\u0003lLnc]N\u0003|Idc\u000fXFsZ&i[CFm\u001cdgCG\u0003rYhrNG\u0003oT\u007fuFHBs\u001cat@\\\u0003lTtoA@\u0003{YkiA\u000bFsYkcA_Bs\u001cuvFYJk\u001cghFFBs\u001cetJJWjNc&MNBlH&nZFBqSob\u000f^M{Ygb\u000fMQzOn&\\_BsY&");
        d =
                "the elder scrolls klaatu berata niktu xyzzy bless curse light darkness fire air earth water hot dry cold wet ignite snuff embiggen twist shorten stretch fiddle destroy imbue galvanize enchant free limited range of towards inside sphere cube self other ball mental physical grow shrink demon elemental spirit animal creature beast humanoid undead fresh stale ";
        net.minecraft.util.EnchantmentNameParts.instance = new EnchantmentNameParts();
    }

    // region String decoding support

    // endregion

    public String generateNewRandomName() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        int i = this.rand.nextInt(2) + 3;
        String textValue = text;
        String textValue2 = packedTextFragments[1];
        int j = 0;

        while (j < i) {
            if (j > 0) {
                textValue2 = textValue2 + packedTextFragments[2];
            }

            textValue2 = textValue2 + this.namePartsArray[this.rand.nextInt(this.namePartsArray.length)];
            j++;
            if (textValue == null) {
                break;
            }
        }

        return textValue2;
    }

    public EnchantmentNameParts() {
        this.namePartsArray = d.split(packedTextFragments[0]);
    }

    // region String decoding support

    // endregion

    public static EnchantmentNameParts getInstance() {
        return net.minecraft.util.EnchantmentNameParts.instance;
    }
}
