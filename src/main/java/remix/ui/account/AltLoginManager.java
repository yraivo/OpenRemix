package remix.ui.account;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.util.Session;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.config.AccountsTextConfig;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public final class AltLoginManager {
    private static int[] legacyLoginFlowMarker;

    private List<AltAccountEntry> accounts;
    private String statusMessage = "\u00a7eWaiting...";
    private static String[] packedTextFragments;

    public static void setLegacyLoginFlowMarker(int[] aint) {
        remix.ui.account.AltLoginManager.legacyLoginFlowMarker = aint;
    }

    public void setAccounts(List<AltAccountEntry> list) {
        this.accounts = list;
    }

    public void addAccountAndSave(String text, String textValue) {
        this.accounts.add(AltAccountEntry.fromCredentials(text, textValue));
        this.saveAccounts();
    }

    public void login(AltAccountEntry account) {
        MinecraftClient client = MinecraftClient.getMinecraft();
        boolean legacyLoginFlow = getLegacyLoginFlowMarker() != null;
        RemixClient.INSTANCE.getClientServices();
        AltLoginManager status = ClientServices.altLoginManager;
        status.setStatusMessage("Logging in...");
        if (account.getPassword().isEmpty()) {
            this.loginOffline(client, status, account);
            return;
        }
        this.loginWithService(client, status, account, legacyLoginFlow);
    }

    private void loginOffline(MinecraftClient client, AltLoginManager status, AltAccountEntry account) {
        String username = account.getUsername();
        client.session = new Session(username, "", "", "legacy");
        this.saveAccounts();
        status.setStatusMessage(String.format("Successfully\u00a7f set username to \"%s\"!", username));
        account.setEmail(username);
    }

    private void loginWithService(MinecraftClient client, AltLoginManager status, AltAccountEntry account,
                                  boolean legacyLoginFlow) {
        AltServiceSwitcher service = ClientServices.altServiceSwitcher;
        boolean switched = account.getEmail().contains("@alt.com") && !service.getServiceLabel().equals("TheAltening");
        if (switched) {
            service.cycleAuthService();
        }
        Session authenticated;
        try {
            authenticated = this.authenticateWithYggdrasil(account.getEmail(), account.getPassword());
            if (authenticated == null) {
                status.setStatusMessage(String.format("\u00a74Failed\u00a7f logging in to \"%s\"", account.getEmail()));
                if (legacyLoginFlow) {
                    Entity.setLegacyGlobalFlowMarker(new String[2]);
                }
                return;
            }
            client.session = authenticated;
            status.setStatusMessage(String.format("Successfully\u00a7f logged in to \"%s\"!", authenticated.getUsername()));
            account.setUsername(authenticated.getUsername());
            this.saveAccounts();
        } finally {
            if (switched) {
                service.cycleAuthService();
            }
        }
        account.setUsername(authenticated.getUsername());
    }

    private void saveAccounts() {
        RemixClient.INSTANCE.getClientServices();
        ClientServices.configFileManager.saveConfig(AccountsTextConfig.class);
    }

    public void removeAccount(AltAccountEntry altAccountEntry) {
        this.accounts.remove(altAccountEntry);
    }

    public List<AltAccountEntry> getAccounts() {
        return this.accounts;
    }

    public static AltLoginManager create() {
        return new AltLoginManager();
    }

    private AltLoginManager() {
        this.accounts = new ArrayList<>();
    }

    public void setStatusMessage(String text) {
        this.statusMessage = text;
    }

    // region String decoding support

    // endregion

    public void loginWithCredentials(String text, String textValue) {
        this.login(AltAccountEntry.fromCredentials(text, textValue));
    }

    public void addAccount(AltAccountEntry altAccountEntry) {
        this.accounts.add(altAccountEntry);
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public String getStatusMessage() {
        return this.statusMessage;
    }

    private Session authenticateWithYggdrasil(String identifier, String password) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication authentication =
                (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
        authentication.setUsername(identifier);
        authentication.setPassword(password);

        try {
            authentication.logIn();
            return new Session(authentication.getSelectedProfile().getName(),
                    authentication.getSelectedProfile().getId().toString(),
                    authentication.getAuthenticatedToken(), "legacy");
        } catch (AuthenticationException failure) {
            return null;
        }
    }

    static {
        remix.ui.account.AltLoginManager.packedTextFragments = new String[5];
        remix.ui.account.AltLoginManager.packedTextFragments[0] = new String("");
        remix.ui.account.AltLoginManager.packedTextFragments[1] = new String("");
        remix.ui.account.AltLoginManager.packedTextFragments[2] = new String("");
        setLegacyLoginFlowMarker(null);
    }

    public static int[] getLegacyLoginFlowMarker() {
        return remix.ui.account.AltLoginManager.legacyLoginFlowMarker;
    }
}
