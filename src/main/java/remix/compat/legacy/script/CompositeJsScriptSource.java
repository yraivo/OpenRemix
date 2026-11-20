package remix.compat.legacy.script;

import remix.util.collection.FluentListWrapper;

import net.minecraft.entity.Entity;

import java.util.Iterator;

public final class CompositeJsScriptSource implements JsScriptSource {
    private FluentListWrapper fluentListWrapper;
    private String a;

    public CompositeJsScriptSource(
            String text, FluentListWrapper fluentListWrapperValue) {
        this.a = text;
        this.fluentListWrapper = fluentListWrapperValue;
    }

    public String method_c60693f457aa70be7160() {
        StringBuilder stringbuilder = new StringBuilder();
        int j = ScriptFunctionInvoker.method_beea3e16fe3ec0995eb9();
        Iterator iterator = this.fluentListWrapper.asList().iterator();
        int i = j;

        while (iterator.hasNext()) {
            JsCodeSnippet jsCodeSnippet =
                    (JsCodeSnippet) iterator.next();
            stringbuilder.append(jsCodeSnippet.method_e0e3092a59fee58fae52());
            if (i != 0) {
                Entity.setLegacyGlobalFlowMarker(new String[1]);
                break;
            }
        }

        return stringbuilder.toString();
    }

    public String method_e0e3092a59fee58fae52() {
        return this.a;
    }
}
