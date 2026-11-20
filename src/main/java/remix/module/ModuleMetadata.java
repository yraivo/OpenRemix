package remix.module;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.Packet;
import remix.util.time.ElapsedTimer;
import remix.client.ClientServices;
import remix.client.RemixClient;

public class ModuleMetadata {
    protected static MinecraftClient minecraft;
    private static String suffixSeparator;
    private String name;
    private ModuleCategory category;
    protected ElapsedTimer timer = new ElapsedTimer();
    private static String[] packedTextFragments;
    private boolean visible;
    protected int displayColor;
    public static ClientServices clientServices;
    private String displayName;
    private static int legacyModuleMetadataFlowState;

    public String getSuffix() {
        return packedTextFragments[0];
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public boolean isVisible() {
        return this.visible;
    }

    public ModuleCategory getCategory() {
        return this.category;
    }

    public int getDisplayColor() {
        return this.displayColor;
    }

    public void sendPacketWithoutEvent(Packet packet) {
        minecraft.player.connection.sendPacketNoEvent(packet);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getFormattedName() {
        return this.name
                + (!this.getSuffix().isEmpty() ? remix.module.ModuleMetadata.suffixSeparator + this.getSuffix() : packedTextFragments[1]);
    }

    public ModuleMetadata(String nameValue, ModuleDefaults moduleDefaults) {
        this.displayName = this.name = nameValue;
        this.category = moduleDefaults.getCategory();
        this.visible = moduleDefaults.isVisible();
    }

    public int setDisplayColor(int displayColorValue) {
        return this.displayColor = displayColorValue;
    }

    public String getName() {
        return this.name;
    }

    // region String decoding support

    // endregion

    public void setName(String nameValue) {
        this.name = nameValue;
    }

    public void setVisible(boolean flag) {
        this.visible = flag;
    }

    public static int getLegacyModuleMetadataFlowToken() {
        int i = getLegacyModuleMetadataFlowState();
        return i == 0 ? 22 : 0;
    }

    // region String decoding support

    // endregion

    public java.util.List getSettings() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        return ClientServices.settingRegistry.getSettingsByModule(this);
    }

    // region Static initialization and string tables
    static {
        remix.module.ModuleMetadata.packedTextFragments = new String[3];
        remix.module.ModuleMetadata.packedTextFragments[0] = new String("");
        remix.module.ModuleMetadata.packedTextFragments[1] = new String("");
        remix.module.ModuleMetadata.packedTextFragments[2] = new String("\u00e2l\u0018");
        if (getLegacyModuleMetadataFlowToken() == 0) {
            setLegacyModuleMetadataFlowState(13);
        }
        remix.module.ModuleMetadata.suffixSeparator = "\u00a77 ";
        minecraft = MinecraftClient.getMinecraft();
        remix.module.ModuleMetadata.clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
    }

    // endregion

    public void sendPacket(Packet packet) {
        minecraft.player.connection.addToSendQueue(packet);
    }

    public static int getLegacyModuleMetadataFlowState() {
        return remix.module.ModuleMetadata.legacyModuleMetadataFlowState;
    }

    public static void setLegacyModuleMetadataFlowState(int i) {
        remix.module.ModuleMetadata.legacyModuleMetadataFlowState = i;
    }
}
