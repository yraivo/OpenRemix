package remix.module.combat;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.PlayerPositionPacket;
import remix.util.movement.MovementUtils;
import remix.event.impl.PlayerUpdateEvent;
import remix.module.combat.RegenMode;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.movement.FlightModule;
import remix.module.movement.SpeedModule;
import remix.setting.Setting;



import java.util.ArrayList;

import java.util.List;

import java.util.concurrent.ThreadLocalRandom;



/** Sends movement packets when the player is grounded, below the health threshold and has sufficient food. unusedPacketBuffer is initialized but never consumed by this class. */

public final class RegenModule extends ModuleBase {

    private List<Packet> unusedPacketBuffer;

    private Setting healthSetting;



    private final Setting<RegenMode> modeSetting = new Setting("Mode", this, RegenMode.VANILLA);

    private Setting packetsSetting;



    private boolean canRegenerate() {

        return minecraft.player.onGround

                && minecraft.player.getHealth() < this.healthSetting.asDouble()

                && minecraft.player.getFoodStats().getFoodLevel() > 17

                && minecraft.player.collidedVertically;

    }



    public RegenModule() {

        super("Regen", ModuleDefaults.ofCategory(ModuleCategory.COMBAT));

        this.healthSetting = new Setting("Health", this, 16.0, 1.0, 20.0, 1.0);

        this.packetsSetting = new Setting("Packets", this, 60.0, 1.0, 500.0, 1.0);

        this.unusedPacketBuffer = new ArrayList<>();

    }



    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        if (this.canRegenerate()) {

            SpeedModule speed = ClientServices.moduleManager.speed;

            FlightModule flight = ClientServices.moduleManager.flight;

            int packets = (int) this.packetsSetting.asDouble();

            if (!speed.isEnabled() && !flight.isEnabled() && !MovementUtils.isMoving()) {

                for (int j = 0; j < packets; j++) {

                    this.sendPacket(

                            new PlayerPositionPacket(

                                    minecraft.player.posX,

                                    minecraft.player.posY,

                                    minecraft.player.posZ,

                                    minecraft.player.onGround));

                    if (this.modeSetting.getValue() == RegenMode.FLAG) {

                        double d0 =

                                minecraft.player.motionY >= 0.0

                                        ? 10.0F + ThreadLocalRandom.current().nextFloat()

                                        : -10.0F - ThreadLocalRandom.current().nextFloat();

                        this.sendPacket(

                                new PlayerPositionPacket(

                                        minecraft.player.posX,

                                        minecraft.player.posY + d0,

                                        minecraft.player.posZ,

                                        minecraft.player.onGround));

                    }

                }

            }

        }

    }




}

