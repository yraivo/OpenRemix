package remix.module.player;

import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.client.network.PlayerInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.WorldSettingsGameType;
import remix.event.impl.PlayerUpdateEvent;
import remix.event.annotation.Subscribe;
import remix.client.ClientServices;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.module.world.AntiBotModule;
import remix.setting.Setting;



import java.util.List;



/** HackerDetector module: player events and 7 settings. */

public final class HackerDetectorModule extends ModuleBase {

    private Setting noSlowdownSetting;

    private Setting autoArmorSetting;

    private final Setting flightHoverSetting = new Setting("FlightHover", this, true);



    private final Setting sprintDirectionSetting = new Setting("SprintDirection", this, false);

    private Setting impossiblePitchSetting;

    private Setting groundSpoofSetting;



    private Setting predictionSetting;



    public HackerDetectorModule() {

        super("HackerDetector", ModuleDefaults.ofCategory(ModuleCategory.PLAYER));

        this.noSlowdownSetting = new Setting("NoSlowdown", this, false);

        this.predictionSetting = new Setting("Prediction", this, false);

        this.groundSpoofSetting = new Setting("GroundSpoof", this, false);

        this.impossiblePitchSetting = new Setting("ImpossiblePitch", this, false);

        this.autoArmorSetting = new Setting("AutoArmor", this, false);

        this.setName("Hacker Detector");

    }






    @Subscribe(mc = PlayerUpdateEvent.class)

    public void onPlayerUpdate(PlayerUpdateEvent event) {

        AntiBotModule antiBot = ClientServices.moduleManager.antiBot;

        ClientPlayHandler networkHandler = minecraft.getNetworkHandler();

        List playerEntities = minecraft.world.playerEntities;

        int i = 0;



        int j = playerEntities.size();



        while (i < j) {

            PlayerEntity playerEntity = (PlayerEntity) playerEntities.get(i);

            PlayerInfo playerInfo = networkHandler.getPlayerInfo(playerEntity.getUniqueId());

            WorldSettingsGameType worldSettingsGameType =

                    playerInfo != null ? playerInfo.getGameType() : WorldSettingsGameType.NOT_SET;

            if (!playerEntity.isInvisible()

                    && worldSettingsGameType.isSurvivalOrAdventure()

                    && playerEntity.isEntityAlive()

                    && playerEntity != minecraft.player

                    && (!antiBot.isEnabled() || !antiBot.isBot(playerEntity))) {

                ClientServices.hackerCheckManager.runEnabledChecks(

                        playerEntity,

                        new Setting[] {

                            this.flightHoverSetting,

                            this.predictionSetting,

                            this.impossiblePitchSetting,

                            this.sprintDirectionSetting,

                            this.groundSpoofSetting,

                            this.noSlowdownSetting,

                            this.autoArmorSetting

                        });

            }



            i++;

        }

    }

}

