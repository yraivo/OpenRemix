package net.minecraft.network.play;

import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.DiggingPacket;
import net.minecraft.network.play.client.HeldItemChangePacket;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.client.UseItemPacket;

public interface ServerPlayPacketHandler extends PacketHandler {
    void processVanilla250Packet(C17PacketCustomPayload c17PacketCustomPayload);

    void handleResourcePackStatus(C19PacketResourcePackStatus c19PacketResourcePackStatus);

    void processClientStatus(C16PacketClientStatus c16PacketClientStatus);

    void processPlayer(PlayerMovementPacket playerMovementPacket);

    void processTabComplete(C14PacketTabComplete c14PacketTabComplete);

    void processHeldItemChange(HeldItemChangePacket heldItemChangePacket);

    void handleSpectate(C18PacketSpectate c18PacketSpectate);

    void processPlayerBlockPlacement(UseItemPacket useItemPacket);

    void processPlayerDigging(DiggingPacket diggingPacket);

    void processUseEntity(UseEntityPacket useEntityPacket);

    void processKeepAlive(C00PacketKeepAlive c00PacketKeepAlive);

    void processConfirmTransaction(C0FPacketConfirmTransaction c0FPacketConfirmTransaction);

    void handleAnimation(C0APacketAnimation c0APacketAnimation);

    void processCreativeInventoryAction(
            C10PacketCreativeInventoryAction c10PacketCreativeInventoryAction);

    void processPlayerAbilities(C13PacketPlayerAbilities c13PacketPlayerAbilities);

    void processEntityAction(C0BPacketEntityAction c0BPacketEntityAction);

    void processUpdateSign(C12PacketUpdateSign c12PacketUpdateSign);

    void processInput(C0CPacketInput c0CPacketInput);

    void processClientSettings(C15PacketClientSettings c15PacketClientSettings);

    void processClickWindow(C0EPacketClickWindow c0EPacketClickWindow);

    void processCloseWindow(C0DPacketCloseWindow c0DPacketCloseWindow);

    void processChatMessage(C01PacketChatMessage c01PacketChatMessage);

    void processEnchantItem(C11PacketEnchantItem c11PacketEnchantItem);
}
