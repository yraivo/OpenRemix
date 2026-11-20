package remix.compat.legacy.script;

import net.minecraft.entity.Entity;

import remix.compat.legacy.http.HttpEndpointUrl;

import javax.script.Invocable;

public class ScriptFunctionInvoker {
    private Invocable invocable;
    private static int cz;

    public Object method_ac2483d759ac3aef8c49(String text, Object... aobject) {
        if (aobject == null || aobject.length != 1 || !(aobject[0] instanceof String))
            throw new IllegalArgumentException(
                    "Built-in functions require exactly one String argument");
        if ("tUrl".equals(text)) return new HttpEndpointUrl((String) aobject[0]);
        if ("pLn".equals(text)) {
            System.out.println((String) aobject[0]);
            return null;
        }
        throw new SecurityException("Custom script function is disabled: " + text);
    }

    public static int method_7eec03da329a017ec143() {
        return cz;
    }

    static {
        if (method_beea3e16fe3ec0995eb9() != 0) {
            b(29);
        }
    }

    public ScriptFunctionInvoker(Invocable invocable) {
        super();
        int initialFlowToken = method_beea3e16fe3ec0995eb9();

        int j = initialFlowToken;

        this.invocable = invocable;
        int i = j;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            b(++i);
        }
    }

    public static int method_beea3e16fe3ec0995eb9() {
        int i = method_7eec03da329a017ec143();
        return i == 0 ? 29 : 0;
    }

    public static void b(int i) {
        cz = i;
    }

    public Invocable acI() {
        throw new UnsupportedOperationException("Raw script engine access is disabled");
    }
}
