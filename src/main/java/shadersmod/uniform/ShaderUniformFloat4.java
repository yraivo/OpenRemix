package shadersmod.uniform;

public class ShaderUniformFloat4 extends ShaderUniformBase {
    private float[] values = new float[4];

    public float[] getValues() {
        return this.values;
    }

    public ShaderUniformFloat4(String text) {
        super(text);
    }

    protected void onProgramChanged() {
        this.values[0] = 0.0F;
        this.values[1] = 0.0F;
        this.values[2] = 0.0F;
        this.values[3] = 0.0F;
    }

    public void setValue(float p171_0, float p171_1, float p171_2, float p171_3) {
        if (this.getLocation() >= 0
                && (this.values[0] != p171_0
                        || this.values[1] != p171_1
                        || this.values[2] != p171_2
                        || this.values[3] != p171_3)) {
            org.lwjgl.opengl.ARBShaderObjects.glUniform4fARB(
                    this.getLocation(), p171_0, p171_1, p171_2, p171_3);
            shadersmod.client.Shaders.checkGLError(this.getName());
            this.values[0] = p171_0;
            this.values[1] = p171_1;
            this.values[2] = p171_2;
            this.values[3] = p171_3;
        }
    }
}
