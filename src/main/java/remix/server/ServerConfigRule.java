package remix.server;

public class ServerConfigRule {
    private int serverPort;
    public String serverName;
    public boolean loadInLobby;
    public String serverAddress;
    private static boolean legacyRuleFlowFlag;
    public String hypixelGameType;

    public static boolean isLegacyRuleFlowFlagUnset() {
        return !getLegacyRuleFlowFlag();
    }

    public String getName() {
        return this.serverName;
    }

    static {
        if (isLegacyRuleFlowFlagUnset()) {
            setLegacyRuleFlowFlag(true);
        }
    }

    public String getIp() {
        return this.serverAddress;
    }

    public ServerConfigRule(String serverName, String serverAddress) {
        this(serverName, serverAddress, true, "_", 25565);
    }

    public boolean isLoadInLobby() {
        return this.loadInLobby;
    }

    public static boolean getLegacyRuleFlowFlag() {
        return remix.server.ServerConfigRule.legacyRuleFlowFlag;
    }

    public int getPort() {
        return this.serverPort;
    }

    public ServerConfigRule(String serverName, String serverAddress, boolean loadInLobby, String gameType, int port) {
        this.serverName = serverName;
        this.serverAddress = serverAddress;
        this.loadInLobby = loadInLobby;
        this.hypixelGameType = gameType;
        this.serverPort = port;
    }

    public static void setLegacyRuleFlowFlag(boolean flag) {
        remix.server.ServerConfigRule.legacyRuleFlowFlag = flag;
    }

    public String getHypixelGameType() {
        return this.hypixelGameType;
    }
}
