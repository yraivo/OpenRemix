package remix.module.render;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.UseEntityAction;
import net.minecraft.network.play.client.UseEntityPacket;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import optifine.MathUtils;
import remix.ui.account.AltServiceSwitcher;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.util.reflection.ReflectiveFieldAccessor;
import remix.event.annotation.Subscribe;
import remix.util.reflection.ZeroArgReflectiveInstantiator;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** Crack module: render events and 5 settings. */
public final class CrackModule extends ModuleBase {
    private Setting criticalSetting;
    private Setting bloodSetting;
    private Setting enchantmentSetting;
    private final Setting amountSetting = new Setting("Amount", this, 5.0, 1.0, 10.0, 1.0);
    private Setting hitSoundSetting;

    public CrackModule() {
        super(
                "Crack",
                new ModuleDefaults(ModuleCategory.RENDER).visible(false));
        this.enchantmentSetting = new Setting("Enchantment", this, true);
        this.criticalSetting = new Setting("Critical", this, false);
        this.bloodSetting = new Setting("Blood", this, true);
        this.hitSoundSetting = new Setting("HitSound", this, true);
        if (!(ReflectiveFieldAccessor.b instanceof AltServiceSwitcher)) {
            ZeroArgReflectiveInstantiator.cz = 200;
        }
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        // Receive events run on Netty; particles and the sound table belong to the client thread.
        if (!minecraft.isCallingFromMinecraftThread()) {
            PacketDirection direction = event.getDirection();
            Packet packet = event.getPacket();
            if (direction == PacketDirection.RECEIVED && packet instanceof S29PacketSoundEffect
                    || direction == PacketDirection.SEND && packet instanceof UseEntityPacket) {
                World eventWorld = minecraft.world;
                PacketEvent renderEvent = new PacketEvent(direction, packet);
                minecraft.addScheduledTask(() -> {
                    if (eventWorld != null && minecraft.world == eventWorld) {
                        this.onPacket(renderEvent);
                    }
                });
            }
            return;
        }

        if (event.getDirection() == PacketDirection.SEND) {
            if (!(event.getPacket() instanceof UseEntityPacket)) {
                return;
            }

            boolean enchantment = this.enchantmentSetting.asBoolean();
            boolean critical = this.criticalSetting.asBoolean();
            if (enchantment || critical) {
                UseEntityPacket packet = (UseEntityPacket) event.getPacket();
                if (packet.getAction() == UseEntityAction.ATTACK) {
                    Entity entity = packet.getEntityFromWorld(minecraft.world);
                    if (entity != null) {
                        int count = (int) this.amountSetting.asDouble();
                        for (int i = 0; i < count; i++) {
                            if (enchantment) {
                                minecraft.player.onEnchantmentCritical(entity);
                            }
                            if (critical) {
                                minecraft.player.onCriticalHit(entity);
                            }
                        }
                    }
                }
            }
            return;
        }

        if (event.getDirection() == PacketDirection.RECEIVED
                && event.getPacket() instanceof S29PacketSoundEffect) {
            S29PacketSoundEffect packetValue = (S29PacketSoundEffect) event.getPacket();
            if (this.bloodSetting.asBoolean()
                    && packetValue.getSoundName().equals("game.player.hurt")) {
                if (packetValue.getVolume() != 0.0F) {
                    Vec3 vec3 =
                            new Vec3(packetValue.getX(), packetValue.getY(), packetValue.getZ());
                    int count = (int) this.amountSetting.asDouble();
                    for (int j = 0; j < count; j++) {
                        for (double d0 = 0.0; d0 < 2.0; d0 += 0.1) {
                            minecraft.effectRenderer.spawnEffectParticle(
                                    37,
                                    vec3.x,
                                    vec3.y + d0,
                                    vec3.z,
                                    MathUtils.aN(),
                                    1.0,
                                    MathUtils.aN(),
                                    new int[] {Block.getIdFromBlock(Blocks.redstone_block)});
                        }
                    }
                }

                if (this.hitSoundSetting.asBoolean()) {
                    minecraft.world.playSound(
                            packetValue.getX(),
                            packetValue.getY(),
                            packetValue.getZ(),
                            "dig.stone",
                            1.0F,
                            0.8F,
                            false);
                }
            }
        }
    }
}
