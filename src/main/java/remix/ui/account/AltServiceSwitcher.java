package remix.ui.account;

import com.thealtening.api.TheAltening;
import com.thealtening.authlibapi.EnumAltService;
import com.thealtening.authlibapi.TheAlteningAuthLibAPI;

public final class AltServiceSwitcher {
    private String apiToken;

    private int authServiceIndex;
    private static String[] packedTextFragments;
    private TheAltening theAltening;

    private TheAlteningAuthLibAPI theAlteningAuthLibAPI;

    public static AltServiceSwitcher create() {
        return new AltServiceSwitcher();
    }

    private AltServiceSwitcher() {
        this.apiToken = packedTextFragments[0];
        this.theAlteningAuthLibAPI = new TheAlteningAuthLibAPI();
        this.theAlteningAuthLibAPI.method_8d9b536bcdc9991c48fb();
    }

    public String getServiceLabel() {
        return this.theAlteningAuthLibAPI.b() == EnumAltService.THEALTENING ? "TheAltening" : "Mojang";
    }

    static {
        remix.ui.account.AltServiceSwitcher.packedTextFragments = new String[2];
        remix.ui.account.AltServiceSwitcher.packedTextFragments[0] = new String("");
    }

    public void setTheAltening(TheAltening thealtening) {
        this.theAltening = thealtening;
    }

    public void cycleAuthService() {
        this.authServiceIndex++;
        if (this.authServiceIndex >= EnumAltService.values().length) {
            this.authServiceIndex = 0;
        }

        this.theAlteningAuthLibAPI.a(EnumAltService.values()[this.authServiceIndex]);
    }

    public TheAltening getTheAltening() {
        return this.theAltening;
    }

    public void setApiToken(String text) {
        this.apiToken = text;
    }

    public String getApiToken() {
        return this.apiToken;
    }
}
