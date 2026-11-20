package net.minecraft.network.play.server;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.util.ChatComponent;
import net.minecraft.world.WorldSettingsGameType;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class S38PacketPlayerListItem implements Packet {
    private S38PacketPlayerListItemAction action;
    private final List players = Lists.newArrayList();

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        this.action =
                (S38PacketPlayerListItemAction)
                        packetBuffer.readEnumValue(S38PacketPlayerListItemAction.class);
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        int i = packetBuffer.readVarIntFromBuffer();
        int j = 0;
        int[] aint = aint1;

        while (j < i) {
            GameProfile gameprofile = null;
            int k = 0;
            WorldSettingsGameType worldSettingsGameType = null;
            ChatComponent chatComponent = null;
            switch (this.action) {
                case ADD_PLAYER:
                    gameprofile =
                            new GameProfile(
                                    packetBuffer.readUuid(), packetBuffer.readStringFromBuffer(16));
                    int l = packetBuffer.readVarIntFromBuffer();
                    int i1 = 0;

                    while (i1 < l) {
                        {
                            String text = packetBuffer.readStringFromBuffer(32767);
                            String textValue = packetBuffer.readStringFromBuffer(32767);
                            if (packetBuffer.readBoolean()) {
                                gameprofile
                                        .getProperties()
                                        .put(
                                                text,
                                                new Property(
                                                        text,
                                                        textValue,
                                                        packetBuffer.readStringFromBuffer(32767)));
                                if (!(aint == null)) {

                                    gameprofile
                                            .getProperties()
                                            .put(text, new Property(text, textValue));
                                }
                            } else {

                                gameprofile
                                        .getProperties()
                                        .put(text, new Property(text, textValue));
                            }
                        }

                        i1++;
                        if (aint != null) {
                            break;
                        }
                    }

                    worldSettingsGameType =
                            WorldSettingsGameType.getByID(packetBuffer.readVarIntFromBuffer());
                    k = packetBuffer.readVarIntFromBuffer();
                    if (packetBuffer.readBoolean()) {
                        chatComponent = packetBuffer.readChatComponent();
                    }
                    break;
                case UPDATE_GAME_MODE:
                    gameprofile = new GameProfile(packetBuffer.readUuid(), (String) null);
                    worldSettingsGameType =
                            WorldSettingsGameType.getByID(packetBuffer.readVarIntFromBuffer());
                    break;
                case UPDATE_LATENCY:
                    gameprofile = new GameProfile(packetBuffer.readUuid(), (String) null);
                    k = packetBuffer.readVarIntFromBuffer();
                    break;
                case UPDATE_DISPLAY_NAME:
                    gameprofile = new GameProfile(packetBuffer.readUuid(), (String) null);
                    if (packetBuffer.readBoolean()) {
                        chatComponent = packetBuffer.readChatComponent();
                    }
                    break;
                case REMOVE_PLAYER:
                    gameprofile = new GameProfile(packetBuffer.readUuid(), (String) null);
            }

            this.players.add(
                    new S38PacketPlayerListItemAddPlayerData(
                            this, gameprofile, k, worldSettingsGameType, chatComponent));
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    public void func_180743_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handlePlayerListItem(this);
    }

    private static IOException a(IOException ioexception) {
        return ioexception;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        packetBuffer.writeEnumValue(this.action);
        packetBuffer.writeVarIntToBuffer(this.players.size());
        int[] aint = aint1;

        for (java.util.Iterator<?> __recoveryIterator171_4754 = (this.players).iterator();
                (__recoveryIterator171_4754).hasNext(); ) {
            S38PacketPlayerListItemAddPlayerData s38PacketPlayerListItemAddPlayerData =
                    (S38PacketPlayerListItemAddPlayerData) (__recoveryIterator171_4754).next();
            label69:
            {
                label77:
                {
                    label67:
                    {
                        switch (this.action) {
                            case ADD_PLAYER:
                                packetBuffer.writeUuid(
                                        s38PacketPlayerListItemAddPlayerData.getProfile().getId());
                                packetBuffer.writeString(
                                        s38PacketPlayerListItemAddPlayerData
                                                .getProfile()
                                                .getName());
                                packetBuffer.writeVarIntToBuffer(
                                        s38PacketPlayerListItemAddPlayerData
                                                .getProfile()
                                                .getProperties()
                                                .size());

                                for (java.util.Iterator<?> __recoveryIterator171_5356 =
                                                (s38PacketPlayerListItemAddPlayerData
                                                                .getProfile()
                                                                .getProperties()
                                                                .values())
                                                        .iterator();
                                        (__recoveryIterator171_5356).hasNext(); ) {
                                    Property property =
                                            (Property) (__recoveryIterator171_5356).next();
                                    {
                                        packetBuffer.writeString(property.getName());
                                        packetBuffer.writeString(property.getValue());
                                        if (property.hasSignature()) {
                                            packetBuffer.writeBoolean(true);
                                            packetBuffer.writeString(property.getSignature());
                                            if (!(aint == null)) {

                                                packetBuffer.writeBoolean(false);
                                            }
                                        } else {

                                            packetBuffer.writeBoolean(false);
                                        }
                                    }

                                    if (aint != null) {
                                        break;
                                    }
                                }

                                packetBuffer.writeVarIntToBuffer(
                                        s38PacketPlayerListItemAddPlayerData.getGameMode().getID());
                                packetBuffer.writeVarIntToBuffer(
                                        s38PacketPlayerListItemAddPlayerData.getPing());
                                if (s38PacketPlayerListItemAddPlayerData.getDisplayName() == null) {
                                    packetBuffer.writeBoolean(false);
                                    if (aint == null) {
                                        break label69;
                                    }
                                }

                                packetBuffer.writeBoolean(true);
                                packetBuffer.writeChatComponent(
                                        s38PacketPlayerListItemAddPlayerData.getDisplayName());
                                if (aint == null) {
                                    break label69;
                                }
                            case UPDATE_GAME_MODE:
                                packetBuffer.writeUuid(
                                        s38PacketPlayerListItemAddPlayerData.getProfile().getId());
                                packetBuffer.writeVarIntToBuffer(
                                        s38PacketPlayerListItemAddPlayerData.getGameMode().getID());
                                if (aint == null) {
                                    break label69;
                                }
                            case UPDATE_LATENCY:
                                break;
                            case UPDATE_DISPLAY_NAME:
                                break label67;
                            case REMOVE_PLAYER:
                                break label77;
                            default:
                                break label69;
                        }

                        packetBuffer.writeUuid(
                                s38PacketPlayerListItemAddPlayerData.getProfile().getId());
                        packetBuffer.writeVarIntToBuffer(
                                s38PacketPlayerListItemAddPlayerData.getPing());
                        if (aint == null) {
                            break label69;
                        }
                    }

                    packetBuffer.writeUuid(
                            s38PacketPlayerListItemAddPlayerData.getProfile().getId());
                    if (s38PacketPlayerListItemAddPlayerData.getDisplayName() == null) {
                        packetBuffer.writeBoolean(false);
                        if (aint == null) {
                            break label69;
                        }
                    }

                    packetBuffer.writeBoolean(true);
                    packetBuffer.writeChatComponent(
                            s38PacketPlayerListItemAddPlayerData.getDisplayName());
                    if (aint == null) {
                        break label69;
                    }
                }

                packetBuffer.writeUuid(s38PacketPlayerListItemAddPlayerData.getProfile().getId());
            }

            if (aint != null) {
                break;
            }
        }
    }

    public S38PacketPlayerListItem(
            S38PacketPlayerListItemAction bxrValue, EntityPlayerMP... entityPlayerMPArray) {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.action = bxrValue;
        EntityPlayerMP[] entityPlayerMPArrayValue = entityPlayerMPArray;
        int length = entityPlayerMPArray.length;
        int j = 0;
        int[] aint = aint1;

        while (j < length) {
            EntityPlayerMP entityPlayerMP = entityPlayerMPArrayValue[j];
            this.players.add(
                    new S38PacketPlayerListItemAddPlayerData(
                            this,
                            entityPlayerMP.getGameProfile(),
                            entityPlayerMP.ping,
                            entityPlayerMP.theItemInWorldManager.getGameType(),
                            entityPlayerMP.getTabListDisplayName()));
            j++;
            if (aint != null) {
                break;
            }
        }
    }

    public S38PacketPlayerListItem(
            S38PacketPlayerListItemAction s38PacketPlayerListItemAction, Iterable iterable) {
        this(s38PacketPlayerListItemAction, iterable, S14PacketEntity.getLegacyPacketFlowMarker());
    }

    private S38PacketPlayerListItem(
            S38PacketPlayerListItemAction bxrValue, Iterable iterable, int[] initialFlowMarker) {
        super();

        int[] aint1 = initialFlowMarker;

        this.action = bxrValue;
        Iterator iterator = iterable.iterator();
        int[] aint = aint1;

        while (iterator.hasNext()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iterator.next();
            this.players.add(
                    new S38PacketPlayerListItemAddPlayerData(
                            this,
                            entityPlayerMP.getGameProfile(),
                            entityPlayerMP.ping,
                            entityPlayerMP.theItemInWorldManager.getGameType(),
                            entityPlayerMP.getTabListDisplayName()));
            if (aint != null) {
                break;
            }
        }
    }

    public S38PacketPlayerListItem() {}

    public S38PacketPlayerListItemAction getAction() {
        return this.action;
    }

    public void processPacket(PacketHandler packetHandler) {
        this.func_180743_a((INetHandlerPlayClient) packetHandler);
    }

    public List getEntries() {
        return this.players;
    }
}
