package optifine;

import net.minecraft.client.gui.GuiOptionSlider;
import net.minecraft.client.settings.GameSettingsOptions;

public class GuiOptionSliderOF extends GuiOptionSlider
        implements IOptionControl {
    private GameSettingsOptions option = null;

    public GameSettingsOptions getOption() {
        return this.option;
    }

    public GuiOptionSliderOF(
            int p171_0, int p171_1, int p171_2, GameSettingsOptions gameSettingsOptions) {
        super(p171_0, p171_1, p171_2, gameSettingsOptions);
        this.option = gameSettingsOptions;
    }
}
