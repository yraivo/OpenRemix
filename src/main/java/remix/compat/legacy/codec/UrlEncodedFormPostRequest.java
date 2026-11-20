package remix.compat.legacy.codec;

import net.minecraft.entity.Entity;

import remix.compat.legacy.http.AbstractHttpRequest;
import remix.compat.legacy.http.HttpEndpointUrl;
import remix.compat.legacy.http.HttpGetRequest;
import remix.compat.legacy.http.HttpMethod;
import remix.compat.legacy.http.MediaType;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentMapWrapper;

import java.util.Map;
import java.util.Map.Entry;

public final class UrlEncodedFormPostRequest extends AbstractHttpRequest {

    private static String[] packedTextFragments;
    private static FluentMapWrapper bOD;

    public String method_c60693f457aa70be7160() {
        return HttpMethod.POST.method_e0e3092a59fee58fae52();
    }

    public int aW() {
        try {
            return this.q().length();
        } catch (Exception exception) {
            exception.printStackTrace();
            return -1;
        }
    }

    public MediaType Zu() {
        return MediaType.URL_ENCODED;
    }

    static {
        remix.compat.legacy.codec.UrlEncodedFormPostRequest.packedTextFragments = new String[6];
        remix.compat.legacy.codec.UrlEncodedFormPostRequest.packedTextFragments[0] = new String("");
        remix.compat.legacy.codec.UrlEncodedFormPostRequest.packedTextFragments[1] =
                new String("=");
        remix.compat.legacy.codec.UrlEncodedFormPostRequest.packedTextFragments[2] =
                new String("&");
        remix.compat.legacy.codec.UrlEncodedFormPostRequest.packedTextFragments[3] =
                new String("&");
        bOD = FluentCollections.newMutableMap();
    }

    public FluentMapWrapper Zs() {
        String[] astring1 = HttpGetRequest.y();
        bOD.fZ();
        String[] astring = astring1;
        bOD.putEntry(
                "User-Agent",
                "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko)"
                    + " Chrome/41.0.2228.0 Safari/537.36");
        bOD.putEntry("Content-Type", this.Zu().getMimeType());
        bOD.putEntry("Content-Length", String.valueOf(this.aW()));
        if (astring == null) {
            Entity.setLegacyGlobalFlowMarker(new String[5]);
        }

        return bOD;
    }

    private static Exception method_bbe44e843daabb729dea(Exception exception) {
        return exception;
    }

    public String q() throws Exception {
        String[] astring = HttpGetRequest.y();

        try {
            if (((Map) this.fluentMapWrapper.Qu()).isEmpty()) {
                return packedTextFragments[0];
            }
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(method_bbe44e843daabb729dea(exception));
        }

        StringBuilder stringbuilder = new StringBuilder();

        for (java.util.Iterator<?> __recoveryIterator171_26507 =
                        (((Map) this.fluentMapWrapper.Qu()).entrySet()).iterator();
                (__recoveryIterator171_26507).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_26507).next();
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

    public HttpEndpointUrl method_e4fc96dd4a272a88e831(HttpEndpointUrl httpEndpointUrl)
            throws Exception {
        return httpEndpointUrl;
    }
}
