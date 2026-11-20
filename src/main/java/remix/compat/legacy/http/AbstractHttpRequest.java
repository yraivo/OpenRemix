package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

import java.net.URLEncoder;

public abstract class AbstractHttpRequest implements HttpRequestSpec {
    private static String[] packedTextFragments;
    protected final FluentMapWrapper fluentMapWrapper = FluentCollections.newMutableMap();
    private static String f;

    private static Exception a(Exception exception) {
        return exception;
    }

    public FluentMapWrapper getFluentMapWrapper() {
        return this.fluentMapWrapper;
    }

    public static String method_cee993844cce4b97ed62() {
        return f;
    }

    static {
        packedTextFragments = new String[2];
        unpackLiteralFragments();
        if (method_cee993844cce4b97ed62() != null) {
            b(packedTextFragments[1]);
        }
    }

    public abstract FluentMapWrapper Zs();

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.compat.legacy.http.AbstractHttpRequest.packedTextFragments[0] = new String("sFufUc");
        remix.compat.legacy.http.AbstractHttpRequest.packedTextFragments[1] = new String("t2K83b");
    }

    // endregion

    public abstract HttpEndpointUrl method_e4fc96dd4a272a88e831(HttpEndpointUrl httpEndpointUrl)
            throws Exception;

    // region String decoding support

    // endregion

    public HttpRequestSpec bD(String text) throws Exception {
        method_cee993844cce4b97ed62();
        String textValue = URLEncoder.encode(text, a).trim();
        this.fluentMapWrapper.method_f0b8fe7eb8ebef089956(textValue);

        try {
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                b(packedTextFragments[0]);
            }

            return this;
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
        }
    }

    public static void b(String text) {
        f = text;
    }

    // region String decoding support

    // endregion

    public HttpRequestSpec UL(String text, String textValue) throws Exception {
        String textValue2 = method_cee993844cce4b97ed62();
        String textValue3 = URLEncoder.encode(text, a).trim();
        String textValue4 = textValue2;
        String textValue5 = URLEncoder.encode(textValue, a).trim();

        try {
            this.fluentMapWrapper.putEntry(textValue3, textValue5);
            if (textValue4 != null) {
                Entity.setLegacyGlobalFlowMarker(new String[4]);
            }

            return this;
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
        }
    }
}
