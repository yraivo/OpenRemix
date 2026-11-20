package remix.compat.legacy.http;

import net.minecraft.entity.Entity;
import net.minecraft.util.BoundingBox;

import remix.compat.legacy.auth.DefaultRoleTierParser;
import remix.compat.legacy.crypto.Md5HashListParser;
import remix.compat.legacy.script.NashornScriptService;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentIterableList;
import remix.util.collection.FluentListWrapper;

import java.util.Arrays;
import java.util.Iterator;

public final class PastebinProfilePasteEndpoint extends AbstractProfilePasteEndpoint {

    public FluentListWrapper fluentListWrapper;


    public PastebinProfilePasteEndpoint(PasteProfileFilter pasteProfileFilter, String... astring) {
        super(
                new String("https://pastebin.com/raw/"),
                Arrays.asList(astring),
                new DefaultPasteResponseParser(
                        new DefaultRoleTierParser(), new Md5HashListParser()),
                pasteProfileFilter);
    }

    public FluentListWrapper fetchParsedProfiles() throws Exception {
        FluentIterableList fluentIterableList = new FluentIterableList();
        BoundingBox.b();

        Iterator iterator = this.getFluentListWrapper().asList().iterator();

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            fluentIterableList.a(
                    (HttpEndpointUrl)
                            NashornScriptService.bYK
                                    .afn()
                                    .method_ac2483d759ac3aef8c49(
                                            "tUrl", new Object[] {this.getBaseUrl() + text}));
        }

        int i = -1;
        FluentIterableList fluentIterableListValue = new FluentIterableList();
        Iterator iteratorValue = fluentIterableList.iterator();

        while (iteratorValue.hasNext()) {
            i++;
            fluentIterableListValue.a(fetchPasteResponse((HttpEndpointUrl) iteratorValue.next()));
        }

        FluentListWrapper bPhValue = FluentCollections.ax();

        for (java.util.Iterator<?> __recoveryIterator171_26449 =
                        (fluentIterableListValue).iterator();
                (__recoveryIterator171_26449).hasNext(); ) {
            HttpResponseData httpResponseData =
                    (HttpResponseData) (__recoveryIterator171_26449).next();
            bPhValue.addAll(
                    this.getPasteResponseParser()
                            .method_1e183c9c1e4cfdb85647(httpResponseData)
                            .asList());
        }

        try {
            this.fluentListWrapper = bPhValue;
            if (Entity.getLegacyGlobalFlowMarker() != null) {
                BoundingBox.b(new String[5]);
            }

            return bPhValue;
        } catch (Exception exception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(exception);
        }
    }
}
