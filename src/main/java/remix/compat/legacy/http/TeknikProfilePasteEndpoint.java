package remix.compat.legacy.http;

import remix.compat.legacy.auth.DefaultRoleTierParser;
import remix.compat.legacy.crypto.Md5HashListParser;
import remix.compat.legacy.script.NashornScriptService;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentIterableList;
import remix.util.collection.FluentListWrapper;

import net.minecraft.util.BoundingBox;

import java.util.Arrays;
import java.util.Iterator;

public final class TeknikProfilePasteEndpoint extends AbstractProfilePasteEndpoint {

    public FluentListWrapper fluentListWrapper;

    public FluentListWrapper fetchParsedProfiles() throws Exception {
        BoundingBox.b();

        FluentIterableList fluentIterableList = new FluentIterableList();
        Iterator iterator = this.getFluentListWrapper().asList().iterator();

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
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
        Iterator iteratorValue = fluentIterableList.iterator();

        while (iteratorValue.hasNext()) {
            i++;
            fluentIterableListValue.a(
                    fetchPasteResponse((HttpEndpointUrl) iteratorValue.next()));
        }

        FluentListWrapper bPhValue = FluentCollections.ax();

        for (java.util.Iterator<?> __recoveryIterator171_2798 =
                        (fluentIterableListValue).iterator();
                (__recoveryIterator171_2798).hasNext(); ) {
            HttpResponseData httpResponseData =
                    (HttpResponseData) (__recoveryIterator171_2798).next();
            bPhValue.addAll(
                    this.getPasteResponseParser().method_1e183c9c1e4cfdb85647(httpResponseData).asList());
        }

        this.fluentListWrapper = bPhValue;
        return bPhValue;
    }


    public TeknikProfilePasteEndpoint(
            PasteProfileFilter pasteProfileFilter, String... astring) {
        super(
                new String("https://p.teknik.io/Raw/"),
                Arrays.asList(astring),
                new DefaultPasteResponseParser(
                        new DefaultRoleTierParser(), new Md5HashListParser()),
                pasteProfileFilter);
    }
}
