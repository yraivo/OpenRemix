package remix.compat.legacy.http;

import remix.compat.legacy.script.NashornScriptService;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentIterableList;
import remix.util.collection.FluentListWrapper;

import remix.client.RemixClient;

import java.util.Arrays;
import java.util.Iterator;

public final class PastebinStringPasteEndpoint extends AbstractStringPasteEndpoint {

    private static String[] packedTextFragments;

    public FluentListWrapper fetchParsedProfiles() throws Exception {
        RemixClient.getLegacyClientStateMarker();

        FluentIterableList fluentIterableList = new FluentIterableList();

        for (java.util.Iterator<?> __recoveryIterator171_930 = (this.getFluentListWrapper().asList()).iterator();
                (__recoveryIterator171_930).hasNext(); ) {
            String text = (String) (__recoveryIterator171_930).next();
            fluentIterableList.a(
                    (HttpEndpointUrl)
                            NashornScriptService.bYK
                                    .afn()
                                    .method_ac2483d759ac3aef8c49(
                                            "tUrl",
                                            new Object[] {
                                                this.getBaseUrl() + text
                                            }));
        }

        int i = -1;
        FluentIterableList fluentIterableListValue = new FluentIterableList();
        Iterator iterator = fluentIterableList.iterator();

        while (iterator.hasNext()) {
            i++;
            HttpResponseData httpResponseData =
                    fetchPasteResponse((HttpEndpointUrl) iterator.next());
            fluentIterableListValue.a(httpResponseData);
            if (httpResponseData.getBody().isEmpty()
                    || httpResponseData
                            .getBody()
                            .contains("This page is no longer available")
                    || httpResponseData.getBody().contains("<html")
                    || !httpResponseData
                            .getBody()
                            .contains(packedTextFragments[0])) {
                return FluentCollections.ax();
            }
        }

        FluentListWrapper fluentListWrapper = FluentCollections.ax();

        for (java.util.Iterator<?> __recoveryIterator171_2471 =
                        (fluentIterableListValue).iterator();
                (__recoveryIterator171_2471).hasNext(); ) {
            HttpResponseData httpResponseDataValue =
                    (HttpResponseData) (__recoveryIterator171_2471).next();
            fluentListWrapper.addAll(
                    Arrays.asList(
                            httpResponseDataValue
                                    .getBody()
                                    .split(packedTextFragments[1])));
        }

        return fluentListWrapper;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public PastebinStringPasteEndpoint(
            PasteProfileFilter pasteProfileFilter, String... astring) {
        super(new String("https://pastebin.com/raw/"), pasteProfileFilter, astring);
    }

    static {
        remix.compat.legacy.http.PastebinStringPasteEndpoint.packedTextFragments = new String[4];
        remix.compat.legacy.http.PastebinStringPasteEndpoint.packedTextFragments[0] = new String("-");
        remix.compat.legacy.http.PastebinStringPasteEndpoint.packedTextFragments[1] = new String("\n");
    }
}
