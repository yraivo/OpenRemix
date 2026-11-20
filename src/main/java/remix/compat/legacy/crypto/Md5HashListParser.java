package remix.compat.legacy.crypto;

import remix.compat.legacy.codec.BracedColonFormatPredicate;
import remix.compat.legacy.codec.BracedColonTokenSplitter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class Md5HashListParser extends remix.compat.legacy.crypto.AbstractHashListParser {
    private static String d;

    // region String decoding support

    // endregion

    public Md5HashListParser() {
        super(new BracedColonFormatPredicate(), new BracedColonTokenSplitter());
    }

    public List method_ded7919cd8af21971636(Object object) {
        return this.bN((String) object);
    }

    // region String decoding support

    // endregion

    public java.util.List bN(String text) {
        AbstractHashListParser.method_7eec03da329a017ec143();

        if (this.getFormatPredicate().method_c7b139ccf3ff22603b21(text)) {
            return Collections.emptyList();
        }

        String[] astring = (String[]) this.getTokenArraySplitter().method_443057c58a5a709862b7(text);
        int j = 0;
        int k = astring.length;

        while (j < k) {
            String textValue = astring[j];
            if (!this.getFormatPredicate().method_c7b139ccf3ff22603b21(textValue)
                    && !textValue.matches(new String("^[a-fA-F0-9]{32}$"))) {
                return Collections.emptyList();
            }

            j++;
        }

        return Arrays.asList(astring);
    }

    static {
        d = "SDyuK8l2Cl8YSi3W0i5Wo5w=";
    }
}
