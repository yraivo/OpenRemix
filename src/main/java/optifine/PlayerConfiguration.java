package optifine;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class PlayerConfiguration {
    private PlayerItemModel[] playerItemModels = new PlayerItemModel[0];
    private boolean initialized = false;

    public void renderPlayerItems(
            ModelBiped modelBiped,
            AbstractClientPlayer abstractClientPlayer,
            float f,
            float f1) {
        int i = ReflectorMethod.cR();
        if (this.initialized) {
            int j = 0;

            while (j < this.playerItemModels.length) {
                PlayerItemModel playerItemModel = this.playerItemModels[j];
                playerItemModel.render(
                        modelBiped, abstractClientPlayer, f, f1);
                j++;
                if (i == 0) {
                    break;
                }
            }
        }
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public void setInitialized(boolean flag) {
        this.initialized = flag;
    }

    public void addPlayerItemModel(PlayerItemModel playerItemModel) {
        this.playerItemModels =
                (optifine.PlayerItemModel[])
                        optifine.OptiFineConfig.addObjectToArray(this.playerItemModels, playerItemModel);
    }

    public PlayerItemModel[] getPlayerItemModels() {
        return this.playerItemModels;
    }
}
