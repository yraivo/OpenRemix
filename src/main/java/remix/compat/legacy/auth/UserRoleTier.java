package remix.compat.legacy.auth;

import remix.compat.legacy.script.EmbeddedScript1;
import remix.compat.legacy.script.EmbeddedScript2;
import remix.compat.legacy.script.EmbeddedScript3;
import remix.compat.legacy.script.EmbeddedScript4;
import remix.compat.legacy.script.EmbeddedScriptProvider;
import remix.compat.legacy.security.ResourcePermission;

import java.io.Serializable;

public enum UserRoleTier implements Serializable {
    DEVELOPER(new EmbeddedScript4()),
    BETA_TESTER(new EmbeddedScript1()),
    USER(new EmbeddedScript2()),
    DEVELOPER_EXTRA(new EmbeddedScript4()),
    BETA_TESTER_EXTRA(new EmbeddedScript1()),
    USER_EXTRA(new EmbeddedScript2()),
    BANNED_EXTRA(new EmbeddedScript4()),
    DEVELOPER_LOL(new EmbeddedScript1()),
    BETA_TESTER_LOL(new EmbeddedScript3()),
    USER_LOL(new EmbeddedScript2()),
    BANNED_LOL(new EmbeddedScript4()),
    BANNED(new EmbeddedScript3());

    private String a;

    UserRoleTier(EmbeddedScriptProvider embeddedScriptProvider) {
        this.a = embeddedScriptProvider.method_e0e3092a59fee58fae52();
    }

    public boolean a(String text) {
        return text.matches(new String("^[a-fA-F0-9]{32}$"));
    }

    public String method_e0e3092a59fee58fae52() {
        return this.a;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion


    public ResourcePermission[] afv() {
        return new ResourcePermission[0];
    }
}
