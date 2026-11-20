package remix.compat.legacy.script;

import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentStyle;
import remix.client.RemixClient;

public final class JsVariableSnippet implements JsCodeSnippet {
    private static String c;
    private String e;
    private String d;
    private static String b;
    private static String a;

    public String method_c60693f457aa70be7160() {
        return this.d;
    }

    static {
        a =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=(:'4:5'1:1'1:3':'1:'2:':':':':1'3:1'2:':1':'3:':':'5:':'4)#[74, 47, 41, 10, 7, 4]('1:'1:'3:2'2:2'2:1':1'1:3'1:3'3:1':1'4:'1:':1'2:2'2:'2:'1:4'1)#[38, -44, -56, -99, -123, -203](:':':'3:1':1'2:1'2:1'1:3':1':'2:'1:'4:'2:'1:'6:':')#[13, -9, -45, -96, -107, -109]('1:3':':1':2':1'2:2'1:2'1:3':'1:')#[36, 14, -4, -18, -34, -46](:3'1:2'3:2'1:':':')#[7, -14, -33, -37, -53, -55](:':':':2':2'1:3'1:'2:'2:':2'2:4':3'3:1':3'2:':':'4:3')#[74, 8, -6, -79, -140, -168](':1':':3'1:':'4:':6)#[17, 15, 2, -12, -16, -36](':'1:4'10:2'3:1'3:1'3:5'2:'1:2);");
        b =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=(:1':1'1:4':':1':'2:1':2':1':2':'1:1'1:'2:':3'1:':1':':2'1:1'1:'1:':)#[21, -13, -86, -107, -115, -166]('6:1':2'3:1':'1:6':':':2'4:'4:5'2)#[30, 9, -43, -63, -114, -164](':'1:'1:1'2:2'2:1'6:1'2:1'2:2':'1:':':')#[25, -18, -30, -39, -63, -69](':1'1:1'4:1'3:'2:'3:'4:'2:'2:2':5'2:1':':2':5':'2:'1:3'1);");
        c =
                (String)
                        RemixClient.encodedStringCodec
                                .getDecoder()
                                .decode(
                                        "=(:'4:'1:'1:2'2:2':3'1:2')#[11, 1, -5, -28, -47, -57](\"2.\".1\".\".3\"1.2\".2\".\".\"4.\"2.4\"1.\"1.\".1\".\".\".\"2.1\"1.\"2.\".1\"1.\".2\"2')#[20, -4, -75, -103, -181, -224](:'2:1':':':':5'2:1'4:':4':1':)#[17, 16, -2, -14, -36, -55](:':':4'1:'1:1'1:2':1':1'1:3':':2':'1:':1);");
    }

    public String method_cee993844cce4b97ed62() {
        return this.e;
    }

    public JsVariableSnippet(String text, String textValue) {
        this.d = text;
        this.e = textValue;
    }

    public String method_e0e3092a59fee58fae52() {
        int i = ChatComponentStyle.method_7eec03da329a017ec143();
        String text =
                new String("var ")
                        + this.method_c60693f457aa70be7160()
                        + new String(" = ")
                        + this.method_cee993844cce4b97ed62()
                        + new String(";");
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ChatComponentStyle.b(++i);
        }

        return text;
    }
}
