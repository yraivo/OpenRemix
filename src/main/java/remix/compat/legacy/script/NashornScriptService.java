package remix.compat.legacy.script;

import remix.compat.legacy.http.HttpEndpointUrl;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

import remix.client.RemixClient;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public enum NashornScriptService {
    bYK;

    private final String d = "nashorn";
    private static JsScriptSource bYF;
    private final ScriptEngineManager bYG = null;
    private static FluentMapWrapper bOl;

    private static JsScriptSource bYH;
    private final ScriptEngine bYI = null;
    private ScriptFunctionInvoker bYJ;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        bOl = FluentCollections.newMutableMap();
        bYF =
                new JsScriptBuilder("uis")
                        .Xo(
                                        "mUrl",
                                        "Java.type(\""
                                                + HttpEndpointUrl.class.getName()
                                                + "\")")
                                .method_15396bb5aa0a5b61df10(
                                        "tUrl",
                                        (String)
                                                RemixClient.encodedStringCodec
                                                        .getDecoder()
                                                        .decode(
                                                                "=('7:2'3:'3:':'2:':':1':2'1:2'2:1':':'3:'3:'1:1':':':'4:1':'2:)#[64, 40, -7, -46, -125, -127](:':1'1:':':':1'2:1'2:'1:4'5:4':2':':1'1:':3':2'1:'2:1'3:)#[34, 12, -62, -108, -153, -165](:1'1:':4':':':1':1'1:'2:2'1:1':':':1'1:'3:'1:'1:'1:':3'1:)#[56, 31, 23, -30, -63, -120](\".1\".\"2.1\".2\"2.1\".\".\".1\"3.1\"2.\"2.2\".\"1.1\"2.\".\".1\".\"2.\".7\".5\"2)#[46, 3, -61, -96, -102, -116](':':':'4:2'2:'11:':3':':'1:5':':'1:2':3'1:1':1':1'3:1')#[63, 34, 1, -32, -50, -71]('1:1'2:':':1':':'1:2'2:2'2:'2:1'1:3':3':'2:3'1:':':1'1:2'1:1':':1':1)#[19, 10, 6, -19, -100, -173](:'1:':':':)#[0, -2, -11, -15, -17, -19](\".\"2.\".2\".\"1.1\"2.1\".1\".\"2.\"1.1\".\".\".\"1.1\"1.\".5\"1.\".4\".1\"1.4\".1\".2\".2\".1\"1.)#[75, 18, -67, -129, -202, -264](:'1:'1:'3:'1:2':4'4:2'1:1':':3':':':':1':':2':4':')#[35, -15, -74, -135, -167, -193](\"1.2\"1.3\".\"5.2\"1.3\".1\"1.2\".1\".1\"1.2\".\"1.\".\"1.\"1.1\"5.2\"2.\"1.\"1)#[9, -58, -73, -104, -160, -161](':2':'2:4':'2:':')#[2, -7, -11, -15, -24, -34](:1':2'1:3':':'1:2'1:2':1'1:1'1:3'2:'2:'3:2'4:5':'1:1':':':'1:')#[38, -6, -8, -80, -134, -177](':1':2':'1:1':1'1:':2'2:'1:1'1:1'5:3'2)#[22, -7, -42, -43, -80, -107](.2\"2.2\"3.1\"4.\".\"1.3\"1.\".\".\".\"1.1\".\".\".2\"1.5\".4\".1\".4\".2\".1\".\".9)#[88, 40, -8, -28, -100, -136](:2':1'1:1':1':':3'1:'4:3':2'1:1'1:'1:3':':5'1:5')#[42, 11, -44, -58, -83, -144](:1'1:'4:1'1:':2'1:':1'1:1':':2'2)#[36, 5, -13, -46, -49, -53]('2:3'1:'3:':'2:'3:':4'1:'1:1'1:1':1':1':'4:3':1':2'1)#[53, 46, 45, -19, -46, -106](.1\"2.\".\".3\".1\"8.3\"2.\"1.\"2.\"1.\".3\"2.\".\"1.4\".\"1.\".2\"4.\"2.1\")#[38, -22, -38, -78, -154, -183](':':'3:1':':1':2'3:3'3:':1'1:'1:':'4:1'1:'1:1'1:':'1:2'1:2':2')#[65, 61, 1, -13, -73, -149](':11':':':1':'2:':1'2:1)#[4, -18, -18, -39, -69, -99](:1':'5:2'1:2':'1:':1':':);"),
                                        new String[] {"url"})
                                .acH();
        bYH =
                new JsScriptBuilder("pls")
                        .method_15396bb5aa0a5b61df10(
                                "pLn",
                                (String)
                                        RemixClient.encodedStringCodec
                                                .getDecoder()
                                                .decode(
                                                        "=(':3':'5:'1:'1:':'5:'1:'3:'1:2'4:3':1':'1:2'2:1'2:':5')#[52, 47, 0, -30, -45, -83](\".\".\".1\"2.1\".\"4.1\".1\".1\".\"1.\".\".\".\".1\"1.\".\".2\".2\".\"1.4\".\".\".3\".3\"2.\"1.2\".)#[11, -23, -62, -138, -188, -262]('1:5':':':1':':':6'4:'1:'4:':1':'3:1'3:'2:1':1'2:2)#[66, 28, -19, -50, -89, -143](:2':'1:'1:'1:':2'1:':'1:':':':1'5:'5:':1'2:1'2:3'3:':':3':'4:'1:'1:)#[10, -39, -43, -73, -75, -143](:2':'2:':1'4:2'4:1'1:1':1':1':'1:1':'1:2':'3:':1':':'1:)#[12, -26, -35, -51, -63, -97](:':1'2:1':2':':1':'2:2':':2'1)#[5, -14, -30, -41, -53, -83](':1':'1:'2:':2':'3:2':':'1:2':'1:2':':1':'2:':2':':')#[1, -36, -43, -49, -78, -124](\".\"1.\"5.\".\".2\"1.\".\".1\"1.\".\"3.\"1.1\"1.4\"1.\"1.3\"3.\".3\"2.\"6.)#[20, -46, -69, -135, -139, -160](:3':2'1:1'1:1':1'1:':3':1':1':1':4':':'4:2':1':1':3)#[30, 4, -33, -80, -122, -125](':2'2:'1:':1'1:'1:3':'1:4':2)#[12, 11, -15, -16, -17, -33](:'2:1'3:4':':'1:':'1:1);"),
                                new String[] {"msg"})
                        .acH();
    }

    public ScriptEngineManager afm() {
        return this.bYG;
    }

    public void method_0a556232c975d934e55d(JsScriptSource jsScriptSource)
            throws ScriptException {
        if (jsScriptSource != bYF && jsScriptSource != bYH)
            throw new javax.script.ScriptException(
                    "Custom script execution is disabled in the local build");
        // Both built-in functions are implemented by the typed Java bridge. No JS is evaluated.
    }

    public ScriptFunctionInvoker afn() {
        if (this.bYJ == null) {
            this.bYJ = new ScriptFunctionInvoker((Invocable) this.bYI);
        }

        return this.bYJ;
    }

    public ScriptEngine afo() {
        return this.bYI;
    }

    public void initializeLegacyScriptBridge() throws ScriptException {
        // The two built-in functions are provided directly by the typed Java bridge.
    }

    private NashornScriptService() {}
}
