package remix.compat.legacy.http;

import java.util.Arrays;

public abstract class AbstractStringPasteEndpoint extends AbstractRemotePasteEndpoint<String> {
    public AbstractStringPasteEndpoint(
            String text, PasteProfileFilter pasteProfileFilter, String... astring) {
        super(text, Arrays.asList(astring), pasteProfileFilter);
    }
}
