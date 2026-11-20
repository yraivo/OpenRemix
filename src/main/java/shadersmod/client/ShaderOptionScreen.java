package shadersmod.client;

public class ShaderOptionScreen extends ShaderOption {

    public String getNameText() {
        return Shaders.translate("screen." + this.getName(), this.getName());
    }

    public ShaderOptionScreen(String text) {
        super(
                text,
                (String) null,
                (String) null,
                new String[] {null},
                (String) null,
                (String) null);
    }

    public String getDescriptionText() {
        return Shaders.translate("screen." + this.getName() + ".comment", (String) null);
    }


}
