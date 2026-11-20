package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

import java.util.Map;
import java.util.Map.Entry;

public final class HttpGetRequest extends AbstractHttpRequest {
    private static String[] h;
    private static FluentMapWrapper bOD;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String q() throws Exception {
        String[] astring = y();

        try {
            if (((Map) this.fluentMapWrapper.Qu()).isEmpty()) {
                return packedTextFragments[0];
            }
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_bbe44e843daabb729dea(exception));
        }

        StringBuilder stringbuilder = new StringBuilder();

        for (java.util.Iterator<?> __recoveryIterator171_4105 =
                        (((Map) this.fluentMapWrapper.Qu()).entrySet()).iterator();
                (__recoveryIterator171_4105).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_4105).next();
            stringbuilder
                    .append((String) entry.getKey())
                    .append(packedTextFragments[1])
                    .append((String) entry.getValue())
                    .append(packedTextFragments[2]);
            if (astring == null) {
                break;
            }
        }

        stringbuilder.substring(0, stringbuilder.lastIndexOf(packedTextFragments[3]));
        return stringbuilder.toString();
    }

    public int aW() {
        try {
            return this.q().length();
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public HttpEndpointUrl method_e4fc96dd4a272a88e831(HttpEndpointUrl httpEndpointUrl)
            throws Exception {
        y();
        String text = httpEndpointUrl.toString();
        if (!text.endsWith(packedTextFragments[4])) {
            text = text.concat(packedTextFragments[5]);
        }

        text = text.concat(this.q());

        try {
            HttpEndpointUrl httpEndpointUrlValue = new HttpEndpointUrl(text);
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                b(new String[4]);
            }

            return httpEndpointUrlValue;
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_bbe44e843daabb729dea(exception));
        }
    }

    private static Exception method_bbe44e843daabb729dea(Exception exception) {
        return exception;
    }

    // region String decoding support

    // endregion

    public FluentMapWrapper Zs() {
        return bOD;
    }

    static {
        remix.compat.legacy.http.HttpGetRequest.packedTextFragments = new String[7];
        remix.compat.legacy.http.HttpGetRequest.packedTextFragments[0] = new String("");
        remix.compat.legacy.http.HttpGetRequest.packedTextFragments[1] = new String("=");
        remix.compat.legacy.http.HttpGetRequest.packedTextFragments[2] = new String("&");
        remix.compat.legacy.http.HttpGetRequest.packedTextFragments[3] = new String("&");
        remix.compat.legacy.http.HttpGetRequest.packedTextFragments[4] = new String("?");
        remix.compat.legacy.http.HttpGetRequest.packedTextFragments[5] = new String("?");
        remix.compat.legacy.http.HttpGetRequest.packedTextFragments[6] =
                new String(
                        "\u0012\u0011\t?T\u001e\u0019\"\f\u0018d\n\r"
                            + "\u0016$\u00153\u001fhWB}Yw).\f\b\"\u000e,^"
                            + "\t6L{WnWg#\u001c=\u0015:)\"\u0000'$\r"
                            + "pKtUB~O\u007fV\f*8\u00005s^+\u000b\u0007(Y\u0018\u001b$"
                            + "\t\u0003dY\u001c\u00165\r"
                            + "\u0001(VkOiRB\u007fKmFiRL\u001e\u00189\u001f5\u000bCxJhPtT");
        String[] astring2 = new String[1];
        b(astring2);
        bOD = FluentCollections.newMutableMap();
        bOD.putEntry(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko)"
                    + " Chrome/41.0.2228.0 Safari/537.36");
    }

    public String method_c60693f457aa70be7160() {
        return HttpMethod.GET.method_e0e3092a59fee58fae52();
    }

    public static String[] y() {
        return h;
    }

    public static void b(String[] astring) {
        h = astring;
    }

    public MediaType Zu() {
        return MediaType.URL_ENCODED;
    }
}
