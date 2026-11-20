package remix.config;

import remix.ui.account.AltAccountEntry;
import remix.ui.account.AltAccountType;
import remix.ui.account.AltLoginManager;
import remix.client.ClientServices;
import remix.client.RemixClient;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class AccountsTextConfig extends AbstractTextConfigFile {
    private static int[] legacyAccountConfigMarker;
    private AltLoginManager altLoginManager;
    private static String[] packedTextFragments;

    public void saveTo(java.util.List output) throws IOException {
        getLegacyAccountConfigMarker();
        TextConfigLineList records = (TextConfigLineList) output;
        for (AltAccountEntry account : this.altLoginManager.getAccounts()) {
            TextConfigLineBuilder record = records.newLine();
            if (account.getAltAccountType() == AltAccountType.PREMIUM) {
                record.appendField(account.getEmail());
                if (account.getUsername().isEmpty()) {
                    record.appendText(account.getPassword());
                } else {
                    record.appendField(account.getPassword());
                    record.appendText(account.getUsername());
                }
            } else {
                record.appendText(account.getUsername());
            }
            record.terminateAndCommitLine();
        }
    }

    public static int[] getLegacyAccountConfigMarker() {
        return remix.config.AccountsTextConfig.legacyAccountConfigMarker;
    }

    public static void setLegacyAccountConfigMarker(int[] aint) {
        remix.config.AccountsTextConfig.legacyAccountConfigMarker = aint;
    }

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.config.AccountsTextConfig.packedTextFragments[0] = new String(":");
        remix.config.AccountsTextConfig.packedTextFragments[1] = new String("");
        remix.config.AccountsTextConfig.packedTextFragments[2] = new String("@");
    }

    // endregion

    public AccountsTextConfig(File file1) {
        super(file1, StandardCharsets.UTF_8);
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        this.altLoginManager = ClientServices.altLoginManager;
    }

    public void readLine(TextConfigLineBuilder record) throws IOException {
        getLegacyAccountConfigMarker();
        String[] fields = record.toString().split(packedTextFragments[0]);
        String email = packedTextFragments[1];
        String username = packedTextFragments[1];
        String password = packedTextFragments[1];
        AltAccountType type = AltAccountType.CRACKED;
        switch (fields.length) {
            case 1:
                if (fields[0].contains(packedTextFragments[2])) {
                    return;
                }
                email = username = fields[0];
                break;
            case 2:
                email = fields[0];
                password = fields[1];
                type = AltAccountType.PREMIUM;
                break;
            case 3:
                email = fields[0];
                password = fields[1];
                username = fields[2];
                type = AltAccountType.PREMIUM;
                break;
        }
        this.altLoginManager.addAccount(new AltAccountEntry(email, username, password, type));
    }

    static {
        packedTextFragments = new String[3];
        unpackLiteralFragments();
        if (getLegacyAccountConfigMarker() == null) {
            setLegacyAccountConfigMarker(new int[1]);
        }
    }

    // region String decoding support

    // endregion

    public void loadFrom(Object object) throws IOException {
        this.readLine((TextConfigLineBuilder) object);
    }

}
