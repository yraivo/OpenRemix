package remix.compat.legacy.http;

import remix.compat.legacy.auth.DefaultClientIdentityToken;
import remix.compat.legacy.auth.DefaultLicenseSession;
import remix.compat.legacy.auth.LicenseProfileRecord;
import remix.compat.legacy.auth.RoleTierParser;
import remix.compat.legacy.auth.UserRoleTier;
import remix.compat.legacy.codec.CipherKeySelector;
import remix.compat.legacy.crypto.HashListParser;
import remix.util.collection.FluentCollections;
import remix.util.collection.FluentListWrapper;

import java.util.Collections;
import java.util.List;

public class DefaultPasteResponseParser implements PasteResponseParser {
    private HashListParser hashListParser;
    private static String[] packedTextFragments;
    private static int[] k;

    private RoleTierParser roleTierParser;

    public FluentListWrapper method_1e183c9c1e4cfdb85647(HttpResponseData httpResponseData) {
        method_650532ba484532b3efb0();

        String text = httpResponseData.getBody();

        try {
            if (!text.contains(new String("-"))) {
                return FluentCollections.method_e2cb25ad6dbcdd481b22(Collections.emptyList());
            }
        } catch (Exception exception3) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception3));
        }

        FluentListWrapper fluentListWrapper = FluentCollections.ax();

        try {
            if (text.contains("<html")) {
                return FluentCollections.method_e2cb25ad6dbcdd481b22(Collections.emptyList());
            }
        } catch (Exception exception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception1));
        }

        try {
            if (text.contains("This page is no longer available")) {
                return FluentCollections.method_e2cb25ad6dbcdd481b22(Collections.emptyList());
            }
        } catch (Exception exception2) {
            throw remix.compat.compiler.JvmThrowTypes.flow(a(exception2));
        }

        String[] astring = text.split(packedTextFragments[0]);

        String[] astring1 = astring;
        int length = astring1.length;
        int j = 0;

        while (j < length) {
            String textValue = astring1[j];
            if (textValue.contains(packedTextFragments[1])) {
                String[] astring2 = textValue.split(new String("-"));
                int kx = astring2.length;
                if (kx == 4) {
                    List list = this.hashListParser.method_ded7919cd8af21971636(astring2[--kx]);
                    String textValue2 = astring2[--kx];
                    UserRoleTier userRoleTier =
                            this.roleTierParser.method_96e3e8c70ae398f6f3ff(astring2[--kx]);
                    String textValue3 = astring2[--kx];
                    if (((String) list.get(0)).length() != 1) {
                        try {
                            fluentListWrapper.add(
                                    new LicenseProfileRecord(
                                            new DefaultLicenseSession(
                                                    new DefaultClientIdentityToken(
                                                            new CipherKeySelector(textValue3, true),
                                                            null,
                                                            textValue2),
                                                    userRoleTier),
                                            list));
                        } catch (Exception exception) {
                        }
                    }
                }
            }

            j++;
        }

        return fluentListWrapper;
    }

    public DefaultPasteResponseParser(RoleTierParser bUxValue, HashListParser bUvValue) {
        this.roleTierParser = bUxValue;
        this.hashListParser = bUvValue;
    }

    // region String decoding support

    // endregion

    public static void b(int[] aint) {
        k = aint;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    static {
        remix.compat.legacy.http.DefaultPasteResponseParser.packedTextFragments = new String[4];
        remix.compat.legacy.http.DefaultPasteResponseParser.packedTextFragments[0] =
                new String("\n");
        remix.compat.legacy.http.DefaultPasteResponseParser.packedTextFragments[1] =
                new String("-");
        b(null);
    }

    public RoleTierParser getRoleTierParser() {
        return this.roleTierParser;
    }

    public static int[] method_650532ba484532b3efb0() {
        return k;
    }

    public HashListParser getHashListParser() {
        return this.hashListParser;
    }

    private static Exception a(Exception exception) {
        return exception;
    }
}
