package net.minecraft.client.main;

public class GameConfiguration {
    public GameConfigurationUserInformation userInfo;
    public GameConfigurationGameInformation gameInfo;
    public GameConfigurationDisplayInformation displayInfo;
    public GameConfigurationFolderInformation folderInfo;
    public GameConfigurationServerInformation serverInfo;
    private static String[] f;

    static {
        if (b() != null) {
            b(new String[2]);
        }
    }

    public static void b(String[] astring) {
        f = astring;
    }

    public static String[] b() {
        return f;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public GameConfiguration(
            GameConfigurationUserInformation aOUValue,
            GameConfigurationDisplayInformation aOWValue,
            GameConfigurationFolderInformation aOXValue,
            GameConfigurationGameInformation aOVValue,
            GameConfigurationServerInformation aOYValue) {
        super();
        this.userInfo = aOUValue;
        b();
        this.displayInfo = aOWValue;
        this.folderInfo = aOXValue;
        this.gameInfo = aOVValue;
        this.serverInfo = aOYValue;
        if (net.minecraft.entity.Entity.getLegacyGlobalFlowMarker() != null) {

            b(new String[3]);
        }
    }
}
