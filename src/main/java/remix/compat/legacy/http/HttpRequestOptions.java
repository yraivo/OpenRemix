package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

import remix.util.collection.FluentMapWrapper;

public final class HttpRequestOptions {
    private int c;
    private AbstractHttpRequest abstractHttpRequest;
    private FluentMapWrapper fluentMapWrapper;
    private static Entity[] dL;
    private int dD;

    HttpRequestOptions(
            AbstractHttpRequest abstractHttpRequestValue,
            FluentMapWrapper fluentMapWrapperValue,
            int i,
            int j,
            HttpOptionsAccessMarker accessMarker2) {
        this(abstractHttpRequestValue, fluentMapWrapperValue, i, j);
    }

    static {
        if (p() == null) {
            b(new Entity[4]);
        }
    }

    public int method_beea3e16fe3ec0995eb9() {
        return this.c;
    }

    public static final HttpRequestOptionsBuilder Zm() {
        return new HttpRequestOptionsBuilder(null);
    }

    public FluentMapWrapper getFluentMapWrapper() {
        return this.fluentMapWrapper;
    }

    public static Entity[] p() {
        return dL;
    }

    private HttpRequestOptions(
            AbstractHttpRequest bOvValue, FluentMapWrapper bOwValue, int i, int j) {
        this.abstractHttpRequest = bOvValue;
        this.fluentMapWrapper = bOwValue;
        this.c = i;
        this.dD = j;
    }

    public int method_9b417d27949cba33a0d0() {
        return this.dD;
    }

    public String method_c60693f457aa70be7160() {
        return this.abstractHttpRequest.method_c60693f457aa70be7160();
    }

    public AbstractHttpRequest getAbstractHttpRequest() {
        return this.abstractHttpRequest;
    }

    public static void b(Entity[] entityArray) {
        dL = entityArray;
    }
}
