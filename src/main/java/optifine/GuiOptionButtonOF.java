package optifine;

import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.settings.GameSettingsOptions;

public class GuiOptionButtonOF extends GuiOptionButton
        implements IOptionControl {
    private GameSettingsOptions option = null;

    public GameSettingsOptions getOption() {
        return this.option;
    }

    public GuiOptionButtonOF(
            int p171_0,
            int p171_1,
            int p171_2,
            GameSettingsOptions gameSettingsOptions,
            String text) {
        super(p171_0, p171_1, p171_2, gameSettingsOptions, text);
        this.option = gameSettingsOptions;
    }
}
