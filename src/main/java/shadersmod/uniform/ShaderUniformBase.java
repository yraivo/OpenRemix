package shadersmod.uniform;

public abstract class ShaderUniformBase {
    private String name;
    private int location;
    private int program = -1;

    public int getLocation() {
        return this.location;
    }

    public ShaderUniformBase(String text) {
        this.location = -1;
        this.name = text;
    }

    public boolean isDefined() {
        return this.location >= 0;
    }

    public String getName() {
        return this.name;
    }

    protected abstract void onProgramChanged();

    public void setProgram(int programValue) {
        if (this.program != programValue) {
            this.program = programValue;
            this.location =
                    org.lwjgl.opengl.ARBShaderObjects.glGetUniformLocationARB(
                            programValue, this.name);
            this.onProgramChanged();
        }
    }

    public int getProgram() {
        return this.program;
    }
}
