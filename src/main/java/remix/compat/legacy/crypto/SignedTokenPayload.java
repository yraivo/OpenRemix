package remix.compat.legacy.crypto;

import net.minecraft.entity.Entity;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public final class SignedTokenPayload {
    public static Encoder bTz;
    public static Decoder bTA;
    private static String g;
    private String e;
    private String d;

    private String c;
    private String f;

    public SignedTokenPayload(String text, String textValue, String textValue2) {
        super();
        String[] initialFlowMarker = SignedTokenVerifier.B();

        String[] astring1 = initialFlowMarker;

        this.c = text;
        this.d = textValue;
        this.e =
                String.format(
                        new String("%s.%s"),
                        bTz.encodeToString(text.getBytes()),
                        bTz.encodeToString(textValue.getBytes()));
        String[] astring = astring1;
        this.f = textValue2;
        if (astring != null) {
            Entity.setLegacyGlobalFlowMarker(new String[1]);
        }
    }

    // region String decoding support

    // endregion

    public String method_cee993844cce4b97ed62() {
        return this.f;
    }

    public String d() {
        return this.e;
    }

    public String method_e0e3092a59fee58fae52() {
        return this.c;
    }

    public void method_be01d1eb5e587c583ed6(String text) {
        this.f = text;
    }

    // region String decoding support

    // endregion

    public String method_c60693f457aa70be7160() {
        return this.d;
    }

    static {
        g = "4YzpYzY=";
        bTA = Base64.getMimeDecoder();
        bTz = Base64.getEncoder();
    }
}
