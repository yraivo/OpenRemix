package remix.compat.legacy.http;

import net.minecraft.entity.Entity;

import remix.client.RemixClient;
import remix.compat.legacy.script.NashornScriptService;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentIterableList;
import remix.util.collection.FluentListWrapper;

import java.util.Arrays;
import java.util.Iterator;

public final class TeknikStringPasteEndpoint extends AbstractStringPasteEndpoint {

    private static String[] packedTextFragments;

    public FluentListWrapper fetchParsedProfiles() throws Exception {
        RemixClient.getLegacyClientStateMarker();

        FluentIterableList fluentIterableList = new FluentIterableList();

        for (java.util.Iterator<?> __recoveryIterator171_1518 =
                        (this.getFluentListWrapper().asList()).iterator();
                (__recoveryIterator171_1518).hasNext(); ) {
            String text = (String) (__recoveryIterator171_1518).next();
            fluentIterableList.a(
                    (HttpEndpointUrl)
                            NashornScriptService.bYK
                                    .afn()
                                    .method_ac2483d759ac3aef8c49(
                                            "tUrl", new Object[] {this.getBaseUrl() + text}));
        }

        int i = -1;
        FluentIterableList fluentIterableListValue = new FluentIterableList();
        Iterator iterator = fluentIterableList.iterator();

        while (iterator.hasNext()) {
            i++;
            HttpResponseData httpResponseData =
                    fetchPasteResponse((HttpEndpointUrl) iterator.next());
            fluentIterableListValue.a(httpResponseData);
        }

        FluentListWrapper fluentListWrapper = FluentCollections.ax();

        for (java.util.Iterator<?> __recoveryIterator171_2558 =
                        (fluentIterableListValue).iterator();
                (__recoveryIterator171_2558).hasNext(); ) {
            HttpResponseData httpResponseDataValue =
                    (HttpResponseData) (__recoveryIterator171_2558).next();
            fluentListWrapper.addAll(
                    Arrays.asList(httpResponseDataValue.getBody().split(packedTextFragments[0])));
        }

        try {
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                RemixClient.setLegacyClientStateMarker(new Entity[3]);
            }

            return fluentListWrapper;
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception));
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    static {
        remix.compat.legacy.http.TeknikStringPasteEndpoint.packedTextFragments = new String[2];
        remix.compat.legacy.http.TeknikStringPasteEndpoint.packedTextFragments[0] =
                new String("\n");
    }

    public TeknikStringPasteEndpoint(PasteProfileFilter pasteProfileFilter, String... astring) {
        super(new String("https://p.teknik.io/Raw/"), pasteProfileFilter, astring);
    }
}
