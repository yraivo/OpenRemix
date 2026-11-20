package remix.module.hidden;

import net.minecraft.entity.DataWatcherWatchableObject;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.event.PacketDirection;
import remix.event.annotation.Subscribe;
import remix.event.impl.PacketEvent;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.movement.BlinkModule;
import remix.module.movement.BlinkOnDamageMode;
import remix.module.movement.FlightBlinkMode;
import remix.module.movement.FlightModule;
import remix.module.render.HudModule;

import java.util.List;

public final class HealthDeathListenerModule extends ModuleBase {


    public HealthDeathListenerModule() {

        super(
                "HealthListener",
                new ModuleDefaults(ModuleCategory.HIDDEN).visible(false).keyBind(0));

        RemixClient.eventBus.register(this);
    }





    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {

        if (event.getDirection() == PacketDirection.RECEIVED) {

            Packet packet = event.getPacket();

            if (packet instanceof S1CPacketEntityMetadata) {

                S1CPacketEntityMetadata packetValue = (S1CPacketEntityMetadata) event.getPacket();

                int entityId = packetValue.getEntityId();

                List list = packetValue.getWatchableObjects();

                if (list != null && !list.isEmpty()) {

                    for (Object item : list) {

                        DataWatcherWatchableObject dataWatcherWatchableObject =
                                (DataWatcherWatchableObject) item;

                        if (dataWatcherWatchableObject != null
                                && dataWatcherWatchableObject.getDataValueId() == 6) {

                            Object object = dataWatcherWatchableObject.getObject();

                            if (object instanceof Float) {

                                float f = (Float) object;

                                HudModule hud = ClientServices.moduleManager.hud;

                                boolean showDamage =
                                        hud.isEnabled()
                                                && ClientServices.moduleManager.hud
                                                        .damageIndicatorsSetting.asBoolean();

                                if (f <= 0.0F) {

                                    if (showDamage && minecraft.world != null) {

                                        Entity entity = minecraft.world.getEntityByID(entityId);

                                        if (entity != null) {

                                            ClientServices.moduleManager.hud.spawnFloatingText(
                                                    entity, "Death!", -43691);
                                        }
                                    }

                                } else if (minecraft.world != null) {

                                    Entity entityValue = minecraft.world.getEntityByID(entityId);

                                    if (entityValue instanceof LivingEntity) {

                                        LivingEntity livingEntity = (LivingEntity) entityValue;

                                        float f1 = f - livingEntity.getHealth();

                                        if (f1 != 0.0F) {

                                            if (showDamage) {

                                                hud.showHealthChange(entityValue, f1);
                                            }

                                            if (f1 < 0.0F
                                                    && minecraft.player.getEntityId() == entityId) {

                                                FlightModule flight =
                                                        ClientServices.moduleManager.flight;

                                                if (flight.isEnabled()
                                                        && flight.blinkModeSetting.getValue()
                                                                == FlightBlinkMode.DAMAGE) {

                                                    flight.flushBufferedPackets();
                                                }

                                                BlinkModule blink =
                                                        ClientServices.moduleManager.blink;

                                                if (blink.isEnabled()) {

                                                    switch (((BlinkOnDamageMode)
                                                            blink.onDamageSetting.getValue())) {
                                                        case DISABLE:
                                                            blink.restartOrDisable(false, true);

                                                            break;

                                                        case UPDATE:
                                                            blink.restartOrDisable(true, false);

                                                            break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
