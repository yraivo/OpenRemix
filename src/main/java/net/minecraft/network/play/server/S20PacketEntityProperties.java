package net.minecraft.network.play.server;

import com.google.common.collect.Lists;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketHandler;
import net.minecraft.network.play.INetHandlerPlayClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class S20PacketEntityProperties implements Packet {

    private final List field_149444_b = Lists.newArrayList();
    private int entityId;
    private static String c;

    public void processPacket(PacketHandler packetHandler) {
        this.func_180754_a((INetHandlerPlayClient) packetHandler);
    }

    public void func_180754_a(INetHandlerPlayClient iNetHandlerPlayClient) {
        iNetHandlerPlayClient.handleEntityProperties(this);
    }

    static {
        c = "Unknown synced attribute modifier";
    }

    // region String decoding support

    // endregion

    public int getEntityId() {
        return this.entityId;
    }

    public List getSnapshots() {
        return this.field_149444_b;
    }

    public void writePacketData(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeVarIntToBuffer(this.entityId);
        packetBuffer.writeInt(this.field_149444_b.size());
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        Iterator iterator = this.field_149444_b.iterator();
        int[] aint = aint1;

        while (iterator.hasNext()) {
            S20PacketEntityPropertiesSnapshot s20PacketEntityPropertiesSnapshot =
                    (S20PacketEntityPropertiesSnapshot) iterator.next();
            packetBuffer.writeString(s20PacketEntityPropertiesSnapshot.getAttributeName());
            packetBuffer.writeDouble(s20PacketEntityPropertiesSnapshot.getBaseValue());
            packetBuffer.writeVarIntToBuffer(
                    s20PacketEntityPropertiesSnapshot.getModifiers().size());
            Iterator iteratorValue = s20PacketEntityPropertiesSnapshot.getModifiers().iterator();

            while (true) {
                if (iteratorValue.hasNext()) {
                    AttributeModifier attributeModifier = (AttributeModifier) iteratorValue.next();
                    packetBuffer.writeUuid(attributeModifier.getID());
                    packetBuffer.writeDouble(attributeModifier.getAmount());
                    packetBuffer.writeByte(attributeModifier.getOperation());
                    if (aint == null) {
                        continue;
                    }
                }

                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }

    public S20PacketEntityProperties(int i, Collection collection) {
        this(i, collection, S14PacketEntity.getLegacyPacketFlowMarker());
    }

    private S20PacketEntityProperties(
            int entityIdValue, Collection collection, int[] initialFlowMarker) {
        super();

        int[] aint1 = initialFlowMarker;

        int[] aint = aint1;
        this.entityId = entityIdValue;

        for (java.util.Iterator<?> __recoveryIterator171_5832 = (collection).iterator();
                (__recoveryIterator171_5832).hasNext(); ) {
            IAttributeInstance iAttributeInstance =
                    (IAttributeInstance) (__recoveryIterator171_5832).next();
            this.field_149444_b.add(
                    new S20PacketEntityPropertiesSnapshot(
                            this,
                            iAttributeInstance.getAttribute().getAttributeUnlocalizedName(),
                            iAttributeInstance.getBaseValue(),
                            iAttributeInstance.func_111122_c()));
            if (aint != null) {
                break;
            }
        }
    }

    public S20PacketEntityProperties() {}

    // region String decoding support

    // endregion

    public void readPacketData(PacketBuffer packetBuffer) throws IOException {
        int[] aint1 = S14PacketEntity.getLegacyPacketFlowMarker();
        this.entityId = packetBuffer.readVarIntFromBuffer();
        int i = packetBuffer.readInt();
        int j = 0;
        int[] aint = aint1;

        while (j < i) {
            String text = packetBuffer.readStringFromBuffer(64);
            double d0 = packetBuffer.readDouble();
            ArrayList arrayList = Lists.newArrayList();
            int k = packetBuffer.readVarIntFromBuffer();
            int l = 0;

            while (true) {
                if (l < k) {
                    UUID uuid = packetBuffer.readUuid();
                    arrayList.add(
                            new AttributeModifier(
                                    uuid, c, packetBuffer.readDouble(), packetBuffer.readByte()));
                    l++;
                    if (aint == null) {
                        continue;
                    }
                }

                this.field_149444_b.add(
                        new S20PacketEntityPropertiesSnapshot(this, text, d0, arrayList));
                j++;
                if (aint != null) {
                    return;
                }
                break;
            }
        }
    }
}
