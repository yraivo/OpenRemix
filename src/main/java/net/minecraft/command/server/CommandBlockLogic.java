package net.minecraft.command.server;

import net.minecraft.command.CommandResultStats;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.command.ICommandManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponentSerializer;
import net.minecraft.util.ReportedException;
import net.minecraft.world.World;
import net.minecraft.command.server.CommandBlockLogicCommandCallable;
import net.minecraft.command.server.CommandBlockLogicNameCallable;

import io.netty.buffer.ByteBuf;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class CommandBlockLogic implements CommandSender {
    private String customName;

    private ChatComponent lastOutput;
    private CommandResultStats resultStats;
    private static Entity[] PK;
    private static String[] packedTextFragments;
    private boolean trackOutput = true;
    private String commandStored;
    private int successCount;

    private static SimpleDateFormat timestampFormat;

    public String getName() {
        return this.customName;
    }

    static {
        net.minecraft.command.server.CommandBlockLogic.packedTextFragments = new String[5];
        net.minecraft.command.server.CommandBlockLogic.packedTextFragments[0] = new String("");
        net.minecraft.command.server.CommandBlockLogic.packedTextFragments[1] = new String("@");
        net.minecraft.command.server.CommandBlockLogic.packedTextFragments[2] = new String("[");
        b((Entity[]) null);
        net.minecraft.command.server.CommandBlockLogic.timestampFormat = new SimpleDateFormat("HH:mm:ss");
    }

    public CommandBlockLogic() {
        this.lastOutput = null;
        this.commandStored = packedTextFragments[0];
        this.customName = packedTextFragments[1];
        this.resultStats = new CommandResultStats();
    }

    public ChatComponent getDisplayName() {
        return new ChatComponentText(this.getName());
    }

    public abstract void updateCommand();

    // region String decoding support

    // endregion

    public void setTrackOutput(boolean trackOutputValue) {
        this.trackOutput = trackOutputValue;
    }

    public void trigger(World world) {
        Entity[] entityArray = GU();
        if (world.isRemote) {
            this.successCount = 0;
        }

        MinecraftServer server = MinecraftServer.getServer();
        if (server != null && server.isAnvilFileSet() && server.isCommandBlockEnabled()) {
            ICommandManager commandManager = server.getCommandManager();

            try {
                this.lastOutput = null;
                this.successCount = commandManager.executeCommand(this, this.commandStored);
            } catch (Throwable throwable) {
                CrashReport crashReport =
                        CrashReport.makeCrashReport(throwable, "Executing command block");
                CrashReportCategory crashReportCategory =
                        crashReport.makeCategory("Command to be executed");
                crashReportCategory.addCrashSectionCallable(
                        "Command", new CommandBlockLogicCommandCallable(this));
                crashReportCategory.addCrashSectionCallable(
                        "Name", new CommandBlockLogicNameCallable(this));
                throw new ReportedException(crashReport);
            }

            if (entityArray == null) {
                return;
            }
        }

        this.successCount = 0;
    }

    public CommandResultStats getCommandResultStats() {
        return this.resultStats;
    }

    public void writeDataToNBT(CompoundTag compoundTag) {
        compoundTag.setString("Command", this.commandStored);
        compoundTag.setInteger("SuccessCount", this.successCount);
        compoundTag.setString("CustomName", this.customName);
        compoundTag.setBoolean("TrackOutput", this.trackOutput);
        if (this.lastOutput != null && this.trackOutput) {
            compoundTag.setString(
                    "LastOutput", IChatComponentSerializer.componentToJson(this.lastOutput));
        }

        this.resultStats.writeStatsToNBT(compoundTag);
    }

    public void setCommand(String text) {
        this.commandStored = text;
        this.successCount = 0;
    }

    public void setLastOutput(ChatComponent agLValue) {
        this.lastOutput = agLValue;
    }

    // region String decoding support

    // endregion

    public int getSuccessCount() {
        return this.successCount;
    }

    public boolean canCommandSenderUseCommand(int i, String text) {
        return i <= 2;
    }

    public String getCustomName() {
        return this.commandStored;
    }

    public void addChatMessage(ChatComponent chatComponent) {
        if (this.trackOutput && this.getEntityWorld() != null && !this.getEntityWorld().isRemote) {
            this.lastOutput =
                    new ChatComponentText(
                                    packedTextFragments[2]
                                            + net.minecraft.command.server.CommandBlockLogic.timestampFormat.format(
                                                    new Date())
                                            + "] ")
                            .appendSibling(chatComponent);
            this.updateCommand();
        }
    }

    public void setCommandStat(CommandResultStatsType commandResultStatsType, int i) {
        this.resultStats.applyResult(this, commandResultStatsType, i);
    }

    public boolean shouldTrackOutput() {
        return this.trackOutput;
    }

    // region String decoding support

    // endregion

    public ChatComponent getLastOutput() {
        return this.lastOutput;
    }

    public static Entity[] GU() {
        return PK;
    }

    public void readDataFromNBT(CompoundTag compoundTag) {
        this.commandStored = compoundTag.getString("Command");
        this.successCount = compoundTag.getInteger("SuccessCount");
        if (compoundTag.hasKey("CustomName", 8)) {
            this.customName = compoundTag.getString("CustomName");
        }

        if (compoundTag.hasKey("TrackOutput", 1)) {
            this.trackOutput = compoundTag.getBoolean("TrackOutput");
        }

        if (compoundTag.hasKey("LastOutput", 8) && this.trackOutput) {
            this.lastOutput =
                    IChatComponentSerializer.jsonToComponent(compoundTag.getString("LastOutput"));
        }

        this.resultStats.readStatsFromNBT(compoundTag);
    }

    public abstract int func_145751_f();

    public abstract void func_145757_a(ByteBuf bytebuf);

    public boolean tryOpenEditCommandBlock(PlayerEntity playerEntity) {
        if (!playerEntity.capabilities.isCreativeMode) {
            return false;
        }

        if (playerEntity.getEntityWorld().isRemote) {
            playerEntity.openEditCommandBlock(this);
        }

        return true;
    }

    public void setName(String text) {
        this.customName = text;
    }

    // region String decoding support

    // endregion

    public static void b(Entity[] entityArray) {
        PK = entityArray;
    }

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }

    public boolean sendCommandFeedback() {
        MinecraftServer server = MinecraftServer.getServer();
        return server == null
                || !server.isAnvilFileSet()
                || server.worldServers[0]
                        .getGameRules()
                        .getGameRuleBooleanValue("commandBlockOutput");
    }
}
