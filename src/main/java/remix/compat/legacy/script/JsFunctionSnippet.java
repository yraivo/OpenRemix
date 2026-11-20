package remix.compat.legacy.script;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentStyle;
import remix.client.RemixClient;

public final class JsFunctionSnippet implements JsCodeSnippet {

    private String[] e;
    private String d;
    private static String b;

    private String c;
    private static String a;
    private static String[] packedTextFragments;

    public JsFunctionSnippet(String text, String textValue, String[] astring) {
        this.c = text;
        this.d = textValue;
        this.e = astring;
    }

    public String d() {
        return this.c;
    }

    public String[] B() {
        return this.e;
    }

    static {
        remix.compat.legacy.script.JsFunctionSnippet.packedTextFragments = new String[1];
        remix.compat.legacy.script.JsFunctionSnippet.packedTextFragments[0] = new String(",");
        a =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=(':':'1:6'3:1':6':1'1:1'4:2':':'3:3':':':2'1)#[0, -39, -74, -136, -141, -202](':1':':':2':1':1':3'1:':3'1:1':':2'4:'2:':':3)#[52, 31, -10, -27, -81, -118](:':4'6:1':4'2:2'1:1'2:1')#[7, 4, -23, -45, -49, -60]('6:':':'1:3':':':5':5')#[22, -11, -21, -42, -58, -91](:'1:1':1'1:1':1':':2'7:':'1:1':2')#[26, 25, 3, -19, -36, -58](':'2:1'1:1'1:2':':2'1:3':3':4'2:':'1:3':7'1:1'1)#[0, -38, -60, -74, -109, -117](:2':1':'1:':'1:1':'1:':1'4:'1:'2:'4:':':'1:'1)#[4, -11, -14, -55, -100, -100](':5':1'1:':2'4:'1:1'5:'2:'2:3'1:':1'1:1':'2:1':':'1:'2:1':'2:'2:1')#[44, -33, -63, -117, -138, -141](:1':1':'2:2':':'1:'4:1'1:4':4'3:':':'2:':':'1:':'1:':1)#[51, -6, -30, -82, -134, -176](:':':'1:'1:':':1':':1':1':2'2:'1:1'2:4':1':'2:4':1'4:':'1:1':'4:'5)#[74, 38, -5, -27, -109, -138](:3'2:':2'3:1':2'1:1':':'1:2'1:3':2'2)#[33, -6, -6, -53, -89, -101](:1'3:3':2'1:':1'2:5'3:'1:1'2:')#[22, 21, -2, -8, -30, -40](:':':'1:'2:1':'4:':3'2:':1'4:'1:'1:1')#[34, 14, -23, -69, -91, -119](:':':'3:1':':2':':':'2:':1':2'3:'1:':2'2:':2':3':':'1:':1'2:':':':1':')#[27, 24, 21, -52, -117, -121](:1'3:1'4:'1:':':2'1:1'1:2':'2:'1:':':'1:')#[44, 15, -21, -55, -65, -95](':'1:':3':':5'2:':':4'1:':1');");
        b =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=(:':2':2'2:'1:1'1:2':':1'1:':'1:1'2:'1:4)#[11, 9, -15, -52, -64, -94](.\"1.5\".1\"2.1\"1.\"2.1\"1.\"1.1\"1.2\".2\"1.2\".6\".2\"4.\".\".1\".\".1\".\".\"1.\"2)#[33, -21, -32, -45, -93, -148](:2'1:1':':3'1:':'2:1':':3':1')#[21, 20, -1, 26, 14, 1](\"4.1\"2.\".2\"1.\"1.\"3.\"1.5\"1.\".\"1.\"3.\".1\".\"2.1\".\"1.\"1.\"2.\".\"1.\".\".)#[68, 48, 31, 21, -35, -72](:3'1:':2'3:'1:'3:1':':2':':':':1'2:2':2':1'2:':2':2)#[9, -41, -43, -64, -85, -97](.1\".\"2.\".\".1\".\"3.\"3.1\".2\"2.1\"2.\"3.1\"1.\"2.1\".\".\".\".\"7.1\".\"1)#[24, 8, -62, -76, -148, -148](':':2'2:':1':':'1:':':1':':':3':'1:'1)#[11, 8, -8, -42, -76, -107](':2'6:1'2:1':2'2:':':'1:':4':3':2');");
    }

    public String method_c60693f457aa70be7160() {
        return this.d;
    }

    public String method_e0e3092a59fee58fae52() {
        StringBuilder stringbuilder = new StringBuilder();
        int k = ChatComponentStyle.method_beea3e16fe3ec0995eb9();
        int j = 0;
        int i = k;

        while (j < this.e.length) {
            String text = this.e[j];
            stringbuilder.append(text);
            if (j != this.e.length - 1) {
                stringbuilder.append(packedTextFragments[0]);
            }

            j++;
            if (i != 0) {
                Entity.setLegacyGlobalFlowMarker(new String[3]);
                break;
            }
        }

        return new String("var ")
                + this.d()
                + new String(" = function(")
                + stringbuilder.toString()
                + new String(") {")
                + this.method_c60693f457aa70be7160()
                + new String("};");
    }
}
