package net.minecraft.tileentity;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEventAction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.IChatComponentSerializer;
import net.minecraft.tileentity.TileEntitySignExecuteCommandICommandSender;
import net.minecraft.tileentity.TileEntitySignReadFromNBTICommandSender;

import com.google.gson.JsonParseException;

public class TileEntitySign extends TileEntity {
    private CommandResultStats stats;

    public ChatComponent[] signText;
    private boolean isEditable;
    private PlayerEntity player;
    private static String[] packedTextFragments;

    public int lineBeingEdited;

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        MobSpawnerBaseLogic.GU();

        int i = 0;

        while (i < 4) {
            String text = IChatComponentSerializer.componentToJson(this.signText[i]);
            compoundTag.setString("Text" + (i + 1), text);
            i++;
        }

        this.stats.writeStatsToNBT(compoundTag);
    }

    public boolean getIsEditable() {
        return this.isEditable;
    }

    public CommandResultStats getStats() {
        return this.stats;
    }

    public void setPlayer(PlayerEntity playerEntity) {
        this.player = playerEntity;
    }

    public TileEntitySign() {
        this.signText =
                new ChatComponent[] {
                    new ChatComponentText(packedTextFragments[0]),
                    new ChatComponentText(packedTextFragments[1]),
                    new ChatComponentText(packedTextFragments[2]),
                    new ChatComponentText(packedTextFragments[3])
                };
        this.lineBeingEdited = -1;
        this.isEditable = true;
        this.stats = new CommandResultStats();
    }

    static {
        net.minecraft.tileentity.TileEntitySign.packedTextFragments = new String[5];
        net.minecraft.tileentity.TileEntitySign.packedTextFragments[0] = new String("");
        net.minecraft.tileentity.TileEntitySign.packedTextFragments[1] = new String("");
        net.minecraft.tileentity.TileEntitySign.packedTextFragments[2] = new String("");
        net.minecraft.tileentity.TileEntitySign.packedTextFragments[3] = new String("");
    }

    public void setEditable(boolean isEditableValue) {
        try {
            this.isEditable = isEditableValue;
            if (!isEditableValue) {
                this.player = null;
            }
        } catch (JsonParseException jsonparseexception) {
            throw method_a7757c6047e9e5ecea97(jsonparseexception);
        }
    }

    public PlayerEntity getPlayer() {
        return this.player;
    }

    public boolean executeCommand(PlayerEntity playerEntity) {
        MobSpawnerBaseLogic.GU();

        TileEntitySignExecuteCommandICommandSender tileEntitySignExecuteCommandICommandSender =
                new TileEntitySignExecuteCommandICommandSender(this, playerEntity);

        int i = 0;

        while (i < this.signText.length) {
            ChatStyle chatStyle;
            label45:
            {
                try {
                    if (this.signText[i] == null) {
                        chatStyle = null;
                        break label45;
                    }
                } catch (JsonParseException jsonparseexception2) {
                    throw method_a7757c6047e9e5ecea97(jsonparseexception2);
                }

                chatStyle = this.signText[i].getChatStyle();
            }

            ChatStyle chatStyleValue = chatStyle;

            {
                boolean runGuardedCode;
                try {
                    runGuardedCode = !(chatStyleValue == null || chatStyleValue.getChatClickEvent() == null);
                } catch (JsonParseException jsonparseexception1) {
                    throw method_a7757c6047e9e5ecea97(jsonparseexception1);
                }
                if (runGuardedCode) {

                    ClickEvent chatClickEvent = chatStyleValue.getChatClickEvent();

                    try {
                        if (chatClickEvent.getAction() == ClickEventAction.RUN_COMMAND) {
                            MinecraftServer.getServer()
                                    .getCommandManager()
                                    .executeCommand(
                                            tileEntitySignExecuteCommandICommandSender, chatClickEvent.getValue());
                        }
                    } catch (JsonParseException jsonparseexception) {
                        throw method_a7757c6047e9e5ecea97(jsonparseexception);
                    }
                }
            }

            i++;
        }

        return true;
    }

    public Packet getDescriptionPacket() {
        ChatComponent[] chatComponentArray = new ChatComponent[4];
        System.arraycopy(this.signText, 0, chatComponentArray, 0, 4);
        return new S33PacketUpdateSign(this.worldObj, this.pos, chatComponentArray);
    }

    private static JsonParseException method_a7757c6047e9e5ecea97(
            JsonParseException jsonparseexception) {
        return jsonparseexception;
    }

    public void readFromNBT(CompoundTag compoundTag) {
        this.isEditable = false;
        MobSpawnerBaseLogic.GU();

        super.readFromNBT(compoundTag);
        TileEntitySignReadFromNBTICommandSender tileEntitySignReadFromNBTICommandSender =
                new TileEntitySignReadFromNBTICommandSender(this);

        int i = 0;

        while (i < 4) {
            String text = compoundTag.getString("Text" + (i + 1));

            try {
                ChatComponent chatComponent = IChatComponentSerializer.jsonToComponent(text);

                try {
                    this.signText[i] =
                            ChatComponentProcessor.processComponent(
                                    tileEntitySignReadFromNBTICommandSender, chatComponent, (Entity) null);
                } catch (CommandException commandException) {
                    this.signText[i] = chatComponent;
                }
            } catch (JsonParseException jsonparseexception) {
                this.signText[i] = new ChatComponentText(text);
            }

            i++;
        }

        this.stats.readStatsFromNBT(compoundTag);
    }

    public static CommandResultStats access$000(TileEntitySign tileEntitySign) {
        return tileEntitySign.stats;
    }
}
