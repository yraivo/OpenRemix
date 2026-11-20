package remix.compat.legacy.auth;

import net.minecraft.client.MinecraftClient;
import remix.client.ClientServices;
import remix.client.RemixClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public final class UidFileStore {

    private static File legacyConfigDirectory;
    private static File uidFile;

    public void writeLicenseUidIfMissing() {
        ClientServices.getLegacyServiceMarker();
        if (remix.compat.legacy.auth.UidFileStore.uidFile == null || remix.compat.legacy.auth.UidFileStore.uidFile.exists()) {
            return;
        }
        ClientServices services = (ClientServices) RemixClient.INSTANCE.getClientServices();
        LicenseProfileRecord profile = services.getVerifiedLicenseProfile();
        if (profile == null) {
            return;
        }
        ClientIdentityToken identity = profile.getLicenseSession().getClientIdentityToken();
        if (identity == null) {
            return;
        }
        String uid = identity.getCipherKeySelector().getIdentityText();
        if (uid.isEmpty()) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(remix.compat.legacy.auth.UidFileStore.uidFile), StandardCharsets.UTF_8))) {
            writer.write(uid);
        } catch (IOException ignored) {
            // This optional compatibility file must not interrupt shutdown.
        }
    }

    // region String decoding support

    // endregion

    public String readSavedUid() {
        boolean retryCloseOnReadFailure = ClientServices.getLegacyServiceMarker() != null;
        if (uidFile == null || !uidFile.exists()) {
            return null;
        }

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(uidFile), StandardCharsets.UTF_8));
            if (retryCloseOnReadFailure) {
                return readUidWithLegacyClose(reader);
            }
            try (BufferedReader closeable = reader) {
                return closeable.readLine();
            }
        } catch (IOException ignored) {
            return null;
        }
    }

    private static String readUidWithLegacyClose(BufferedReader reader) throws IOException {
        String uid;
        try {
            uid = reader.readLine();
        } catch (Throwable readFailure) {
            try {
                reader.close();
            } catch (Throwable closeFailure) {
                readFailure.addSuppressed(closeFailure);
                reader.close();
            }
            throw readFailure;
        }
        reader.close();
        return uid;
    }

    // region String decoding support

    // endregion

    static {
        remix.compat.legacy.auth.UidFileStore.legacyConfigDirectory = new File(MinecraftClient.getMinecraft().gameDirectory, new String("Remix"));
        remix.compat.legacy.auth.UidFileStore.uidFile = new File(remix.compat.legacy.auth.UidFileStore.legacyConfigDirectory, new String("UID.txt"));
    }

}
