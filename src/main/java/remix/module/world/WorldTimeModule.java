package remix.module.world;

import net.minecraft.network.play.server.S03PacketTimeUpdate;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.world.WorldTimeMode;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import java.time.LocalDateTime;

/** WorldTime module: world events and 3 settings. */
public final class WorldTimeModule extends ModuleBase {
    private Setting worldTimeSetting;
    private final Setting<WorldTimeMode> modeSetting =
            new Setting("Mode", this, WorldTimeMode.CUSTOM);

    private Setting<Boolean> disableWeatherSetting;

    public WorldTimeModule() {
        super(
                "WorldTime",
                new ModuleDefaults(ModuleCategory.WORLD).visible(false));
        this.worldTimeSetting = new Setting("WorldTime", this, 6.0, 0.0, 23.0, 1.0);
        this.disableWeatherSetting = new Setting("DisableWeather", this, true);
        this.setName("Day Time");
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        if (minecraft.world != null) {
            if (this.disableWeatherSetting.asBoolean()) {
                minecraft.world.weatherEffects.clear();
                minecraft.world.setRainStrength(0.0F);
            }

            long time;
            switch ((WorldTimeMode) this.modeSetting.getValue()) {
                case CUSTOM:
                default:
                    time = (long) (this.worldTimeSetting.asDouble() * 1000.0);
                    break;
                case REAL:
                    LocalDateTime localdatetime = LocalDateTime.now();
                    long j = localdatetime.getHour() * 1000;
                    long k = (long) (localdatetime.getMinute() / 60.0 * 1000.0);
                    long l = (long) (localdatetime.getSecond() / 60.0 * 100.0);
                    time = j + k + l;
            }

            minecraft.world.setWorldTime(time);
        }
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        if (minecraft.world != null) {
            if (event.getDirection() == PacketDirection.RECEIVED && event.getPacket() instanceof S03PacketTimeUpdate) {
                event.cancel();
            }
        }
    }
}
