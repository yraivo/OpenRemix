package remix.compat.legacy.script;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

import java.util.LinkedList;

public final class JsScriptBuilder {
    private String a;
    private FluentListWrapper fluentListWrapper;

    public JsScriptBuilder(String text) {
        this.a = text;
        this.fluentListWrapper = FluentCollections.method_e2cb25ad6dbcdd481b22(new LinkedList());
    }

    public JsScriptBuilder Xo(String text, String textValue) {
        this.fluentListWrapper.add(new JsVariableSnippet(text, textValue));
        return this;
    }

    public JsScriptBuilder method_15396bb5aa0a5b61df10(
            String text, String textValue, String... astring) {
        this.fluentListWrapper.add(
                new JsFunctionSnippet(text, textValue, astring));
        return this;
    }

    public CompositeJsScriptSource acH() {
        return new CompositeJsScriptSource(this.a, this.fluentListWrapper);
    }
}
