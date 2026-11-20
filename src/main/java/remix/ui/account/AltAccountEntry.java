package remix.ui.account;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public final class AltAccountEntry {
    private static String[] packedTextFragments;
    private String email;

    private AltAccountType altAccountType;
    public static ResourceLocation defaultAvatarTexture;
    public ResourceLocation avatarTexture;
    public boolean loginDisabled;

    private String username;
    private String password;

    public String getPassword() {
        return this.password;
    }

    public AltAccountType getAltAccountType() {
        return this.altAccountType;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUsername(String text) {
        this.username = text;
        if (this.avatarTexture == null) {
            this.avatarTexture = new ResourceLocation("heads/" + text);
            ThreadDownloadImageData threadDownloadImageData =
                    new ThreadDownloadImageData(
                            null,
                            String.format("https://minotar.net/armor/bust/%s/64.png", text),
                            null,
                            null);
            MinecraftClient.getMinecraft()
                    .getTextureManager()
                    .loadTexture(this.avatarTexture, threadDownloadImageData);
        }
    }

    public void setPassword(String text) {
        this.password = text;
    }

    public static AltAccountEntry fromCredentials(String text, String textValue) {
        return textValue != null && !textValue.isEmpty()
                ? new AltAccountEntry(text, text, textValue, AltAccountType.PREMIUM)
                : new AltAccountEntry(text, text, textValue, AltAccountType.CRACKED);
    }

    public void setEmail(String text) {
        this.email = text;
    }

    public void updateUsername(String text) {
        if (!text.isEmpty() && this.avatarTexture == null) {
            this.setUsername(text);
        }

        this.username = text;
    }

    public void setAltAccountType(AltAccountType altAccountTypeValue) {
        this.altAccountType = altAccountTypeValue;
    }

    static {
        remix.ui.account.AltAccountEntry.packedTextFragments = new String[5];
        remix.ui.account.AltAccountEntry.packedTextFragments[0] = new String("@");
        remix.ui.account.AltAccountEntry.packedTextFragments[1] = new String("@");
        remix.ui.account.AltAccountEntry.packedTextFragments[4] = new String("a");
        remix.ui.account.AltAccountEntry.defaultAvatarTexture = new ResourceLocation("heads/a");
        ThreadDownloadImageData class_beff6a5833a563a7398d =
                new ThreadDownloadImageData(
                        null,
                        String.format(
                                "https://minotar.net/armor/bust/%s/64.png", packedTextFragments[4]),
                        null,
                        null);
        MinecraftClient.getMinecraft()
                .getTextureManager()
                .loadTexture(
                        remix.ui.account.AltAccountEntry.defaultAvatarTexture,
                        class_beff6a5833a563a7398d);
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public AltAccountEntry(
            String text, String textValue, String textValue2, AltAccountType altAccountTypeValue) {
        super();
        int[] initialFlowMarker = AltLoginManager.getLegacyLoginFlowMarker();

        this.email = text;
        boolean emailIsUsername = !text.isEmpty() && !text.contains(packedTextFragments[0]);
        if (emailIsUsername) {
            this.setUsername(text);
        }
        if ((!emailIsUsername || initialFlowMarker != null)
                && !textValue.isEmpty()
                && !textValue.contains(packedTextFragments[1])) {
            this.setUsername(textValue);
        }

        this.username = textValue;
        this.password = textValue2;
        this.altAccountType = altAccountTypeValue;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AltLoginManager.setLegacyLoginFlowMarker(new int[2]);
        }
    }
}
