package shadersmod.uniform;

public class ShaderUniformInt extends ShaderUniformBase {
    private int value = -1;

    protected void onProgramChanged() {
        this.value = -1;
    }

    public int getValue() {
        return this.value;
    }

    public ShaderUniformInt(String text) {
        super(text);
    }

    public void setValue(int valueValue) {
        if (this.getLocation() >= 0 && this.value != valueValue) {
            org.lwjgl.opengl.ARBShaderObjects.glUniform1iARB(this.getLocation(), valueValue);
            shadersmod.client.Shaders.checkGLError(this.getName());
            this.value = valueValue;
        }
    }
}
