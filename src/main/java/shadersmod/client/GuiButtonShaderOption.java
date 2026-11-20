package shadersmod.client;

import net.minecraft.client.gui.GuiButton;

public class GuiButtonShaderOption extends GuiButton {
    private ShaderOption shaderOption = null;

    public GuiButtonShaderOption(
            int i, int j, int k, int l, int i1, ShaderOption shaderOption, String text) {
        super(i, j, k, l, i1, text);
        this.shaderOption = shaderOption;
    }

    public ShaderOption getShaderOption() {
        return this.shaderOption;
    }
}
