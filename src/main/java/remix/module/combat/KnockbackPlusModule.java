package remix.module.combat;

import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityActionAction;
import net.minecraft.network.play.client.PlayerMovementPacket;
import net.minecraft.network.play.client.PlayerPositionPacket;
import net.minecraft.util.RayTraceResult;

import remix.client.ClientServices;
import remix.event.PacketDirection;
import remix.event.annotation.Subscribe;
import remix.event.impl.PacketEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/**
 * Runs the selected knockback sequence when an outgoing attack packet has a target. W-tap/sprint
 * and extra movement-packet branches retain original order and limits.
 */
public final class KnockbackPlusModule extends ModuleBase {

    private final Setting<KnockbackPlusMode> modeSetting =
            new Setting("Mode", this, KnockbackPlusMode.W_TAP);

    private final Setting packetsSetting = new Setting("Packets", this, 200.0, 50.0, 1000.0, 50.0);

    public KnockbackPlusModule() {

        super("KnockbackPlus", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));

        this.setName("Knockback+");
    }






    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {

        if (event.getDirection() == PacketDirection.SEND
                && event.getPacket() instanceof C0APacketAnimation) {

            Entity targetEntity = null;

            AuraModule aura = ClientServices.moduleManager.aura;

            if (aura.currentTarget != null) {

                targetEntity = aura.currentTarget;

            } else {

                RayTraceResult objectMouseOver = minecraft.objectMouseOver;

                if (objectMouseOver != null && objectMouseOver.entityHit instanceof LivingEntity) {

                    targetEntity = objectMouseOver.entityHit;
                }
            }

            if (targetEntity != null) {

                LocalPlayer player = minecraft.player;

                double packets = this.packetsSetting.asDouble();

                KnockbackPlusMode mode = (KnockbackPlusMode) this.modeSetting.getValue();

                boolean closeEnough =
                        player.getDistanceToEntity(targetEntity) <= targetEntity.width / 2.0F
                                || player.getEntityBoundingBox()
                                        .intersectsWith(targetEntity.getEntityBoundingBox());

                switch (mode) {
                    case GENERIC:
                        if (!closeEnough) {

                            return;
                        }

                        for (int j = 0; j < packets; j++) {

                            this.sendPacketWithoutEvent(new PlayerMovementPacket(true));
                        }

                        return;

                    case W_TAP:
                        this.sendPacketWithoutEvent(
                                new C0BPacketEntityAction(
                                        player, C0BPacketEntityActionAction.START_SPRINTING));

                        return;

                    case FAITHFUL:
                        break;

                    default:
                        return;
                }

                if (closeEnough) {

                    for (int k = 0; k < packets / 2.0; k++) {

                        this.sendPacketWithoutEvent(
                                new PlayerPositionPacket(
                                        player.posX, player.posY - 999.0, player.posZ, true));

                        this.sendPacketWithoutEvent(
                                new PlayerPositionPacket(
                                        player.posX, player.posY, player.posZ, true));
                    }
                }
            }
        }
    }
}
