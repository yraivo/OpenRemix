package remix.compat.legacy.http;

import remix.compat.legacy.auth.DefaultLicenseProfileEvaluator;
import remix.compat.legacy.auth.LicenseProfileEvaluator;
import remix.compat.legacy.auth.LicenseProfileRecord;

import java.util.List;

public abstract class AbstractProfilePasteEndpoint
        extends AbstractRemotePasteEndpoint<LicenseProfileRecord> {
    private PasteResponseParser pasteResponseParser;
    private LicenseProfileEvaluator licenseProfileEvaluator;
    private PasteProfileFetcher pasteProfileFetcher;

    public AbstractProfilePasteEndpoint(
            String text,
            List<String> textList,
            PasteResponseParser bPeValue,
            PasteProfileFilter pasteProfileFilter) {
        super(text, textList, pasteProfileFilter);
        this.pasteResponseParser = bPeValue;
        this.pasteProfileFetcher = new DefaultPasteProfileFetcher(this);
        this.licenseProfileEvaluator = new DefaultLicenseProfileEvaluator();
    }

    public LicenseProfileEvaluator getLicenseProfileEvaluator() {
        return this.licenseProfileEvaluator;
    }

    public PasteProfileFetcher getPasteProfileFetcher() {
        return this.pasteProfileFetcher;
    }

    public PasteResponseParser getPasteResponseParser() {
        return this.pasteResponseParser;
    }
}
