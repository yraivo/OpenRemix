package remix.module.world;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S14PacketEntityS15PacketEntityRelMove;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import remix.module.world.AntiBotMode;
import remix.event.PacketDirection;
import remix.event.impl.PacketEvent;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * AntiBot module: detects and filters fake player entities and watchdog/NPC bots.
 * Modes: UUID, DISTANCE, HYPIXEL, HYPIXELCN, TABLIST, MINEPLEX, ADVANCED.
 */
public final class AntiBotModule extends ModuleBase {

    private static String unusedBotText;
    private Setting rangeSetting;
    private Map<UUID, LivingEntity> firstSeenPlayers;
    private List<LivingEntity> verifiedPlayers;
    private static String[] packedTextFragments;
    private final Setting<AntiBotMode> modeSetting = new Setting("Mode", this, AntiBotMode.HYPIXEL);

    static {
        packedTextFragments = new String[4];
        packedTextFragments[0] = "\u00a7";
        packedTextFragments[1] = "";
        unusedBotText =
                "minecraft:skins/b2f3fc4516774596bfe5c2e16f661106f115ebf651e1287a7cca43399e8c194";
    }

    public AntiBotModule() {
        super("AntiBot", ModuleDefaults.ofCategory(ModuleCategory.WORLD));
        this.rangeSetting = new Setting("Range", this, 7.0, 3.0, 20.0, 0.1);
        this.setName("Anti Bot");
        this.verifiedPlayers = new ArrayList<>();
        this.firstSeenPlayers = new HashMap<>();
    }

    @Override
    public void onEnable() {
        this.verifiedPlayers.clear();
        super.onEnable();
    }

    @Override
    public String getSuffix() {
        return ((AntiBotMode) this.modeSetting.getValue()).getDisplayName();
    }

    public boolean isBot(Entity entity) {
        if (!(entity instanceof PlayerEntity)) {
            return false;
        }

        PlayerEntity playerEntity = (PlayerEntity) entity;
        PlayerInfo playerInfo =
                minecraft.getNetworkHandler().getPlayerInfo(playerEntity.getUniqueId());

        AntiBotMode mode = (AntiBotMode) this.modeSetting.getValue();
        switch (mode) {
            case UUID:
                return this.firstSeenPlayers.getOrDefault(playerEntity.getUniqueId(), playerEntity)
                        != playerEntity;

            case DISTANCE:
            case ADVANCED:
                return !this.verifiedPlayers.contains(playerEntity);

            case HYPIXEL:
                if (playerInfo != null && playerInfo.getResponseTime() != 1) {
                    return true;
                }
                // Fallthrough to HYPIXELCN check:

            case HYPIXELCN: {
                AbstractClientPlayer clientPlayer = (AbstractClientPlayer) playerEntity;
                String selfName = minecraft.player.getDisplayName().getFormattedText();
                String targetName = playerEntity.getDisplayName().getFormattedText();

                boolean isLegit =
                        !clientPlayer
                                        .getLocationSkin()
                                        .toString()
                                        .equalsIgnoreCase(unusedBotText)
                                && (!selfName.startsWith("\u00a78[NPC]")
                                        || targetName.startsWith("\u00a78[NPC]"))
                                && !playerEntity
                                        .getUniqueId()
                                        .toString()
                                        .startsWith(entity.getCommandSenderEntity().getName())
                                && targetName.startsWith(packedTextFragments[0])
                                && clientPlayer.getCustomNameTag().equalsIgnoreCase(packedTextFragments[1])
                                && (playerInfo == null || playerInfo.getResponseTime() == 1)
                                && playerEntity.getGameProfile().getId() == entity.getUniqueId();

                return !isLegit;
            }

            case TABLIST:
                return !this.isPlayerInTabList(playerEntity);

            case MINEPLEX:
                return !Float.isNaN(playerEntity.getHealth());

            default:
                return false;
        }
    }

    @Subscribe(mc = PacketEvent.class)
    public void onPacket(PacketEvent event) {
        Packet packet = event.getPacket();
        if (event.getDirection() != PacketDirection.RECEIVED) {
            return;
        }

        if (packet instanceof S01PacketJoinGame) {
            this.verifiedPlayers.clear();
            this.firstSeenPlayers.clear();
        }

        if (this.modeSetting.getValue() == AntiBotMode.ADVANCED) {
            ClientWorld world = minecraft.world;
            if (packet instanceof S14PacketEntityS15PacketEntityRelMove) {
                S14PacketEntity s14PacketEntity = (S14PacketEntity) packet;
                for (PlayerEntity playerEntity : world.playerEntities) {
                    if (playerEntity == s14PacketEntity.getEntity(world)) {
                        this.verifiedPlayers.add(playerEntity);
                    }
                }
                return;
            }

            if (packet instanceof S18PacketEntityTeleport) {
                S18PacketEntityTeleport s18PacketEntityTeleport =
                        (S18PacketEntityTeleport) packet;
                for (PlayerEntity playerEntityValue : world.playerEntities) {
                    if (playerEntityValue.getEntityId()
                            == s18PacketEntityTeleport.getEntityId()) {
                        this.verifiedPlayers.add(playerEntityValue);
                    }
                }
            }
        }
    }

    @Subscribe(mc = PlayerUpdateEvent.class)
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        LocalPlayer player = minecraft.player;

        for (Entity entity : minecraft.world.getLoadedEntities()) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                if (playerEntity != player) {
                    AntiBotMode mode = (AntiBotMode) this.modeSetting.getValue();
                    switch (mode) {
                        case UUID:
                            if (!this.firstSeenPlayers.containsKey(playerEntity.getUniqueId())) {
                                this.firstSeenPlayers.put(playerEntity.getUniqueId(), playerEntity);
                            }
                            break;

                        case DISTANCE:
                            if (playerEntity.getDistanceSqToEntity(player)
                                    >= Math.pow(this.rangeSetting.asDouble(), 2.0)) {
                                this.verifiedPlayers.add(playerEntity);
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        }
    }

    private boolean isPlayerInTabList(PlayerEntity player) {
        if (minecraft.isSingleplayer()) {
            return false;
        }

        for (PlayerInfo playerInfo : minecraft.getNetworkHandler().getPlayerInfoMap()) {
            if (playerInfo.getGameProfile().getName().equalsIgnoreCase(player.getName())) {
                return true;
            }
        }

        return false;
    }
}
