package shadersmod.client;

import optifine.Lang;

import java.util.ArrayList;

public class ShaderOptionProfile extends ShaderOption {

    private static String[] packedTextFragments;
    private static String kK;
    private ShaderProfile[] profiles = null;
    private ShaderOption[] options = null;

    private static String ks;

    public String getValueColor(String text) {
        return text.equals("<custom>") ? "\u00a7c" : "\u00a7a";
    }

    private static String detectProfileName(
            ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray, boolean flag) {
        ShaderProfile shaderProfile =
                ShaderUtils.detectProfile(shaderProfileArray, shaderOptionArray, flag);
        return shaderProfile == null ? "<custom>" : shaderProfile.getName();
    }

    private static String[] getProfileNames(ShaderProfile[] shaderProfileArray) {
        int[] aint1 = ShaderOption.lz();
        ArrayList arrayList = new ArrayList();
        int[] aint = aint1;
        int i = 0;

        while (i < shaderProfileArray.length) {
            ShaderProfile shaderProfile = shaderProfileArray[i];
            arrayList.add(shaderProfile.getName());
            i++;
            if (aint != null) {
                break;
            }
        }

        arrayList.add("<custom>");
        return ((java.lang.String[]) arrayList.toArray(new String[arrayList.size()]));
    }

    public String getNameText() {
        return Lang.get("of.shaders.profile");
    }

    private ShaderProfile getProfile(String text) {
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < this.profiles.length) {
            ShaderProfile shaderProfile = this.profiles[i];
            if (shaderProfile.getName().equals(text)) {
                return shaderProfile;
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return null;
    }

    public void updateProfile() {
        ShaderProfile profile = this.getProfile(this.getValue());
        if (profile == null || !ShaderUtils.matchProfile(profile, this.options, false)) {
            String text = detectProfileName(this.profiles, this.options);
            this.setValue(text);
        }
    }

    public void nextValue() {
        super.nextValue();
        if (this.getValue().equals("<custom>")) {
            super.nextValue();
        }

        this.applyProfileOptions();
    }

    public ShaderOptionProfile(ShaderProfile[] profilesValue, ShaderOption[] optionsValue) {
        super(
                "<profile>",
                packedTextFragments[0],
                detectProfileName(profilesValue, optionsValue),
                getProfileNames(profilesValue),
                detectProfileName(profilesValue, optionsValue, true),
                (String) null);
        this.profiles = profilesValue;
        this.options = optionsValue;
    }

    static {
        shadersmod.client.ShaderOptionProfile.packedTextFragments = new String[3];
        shadersmod.client.ShaderOptionProfile.packedTextFragments[0] = new String("");
        ks = "<profile>";
        kK = "<custom>";
    }

    private static String detectProfileName(
            ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray) {
        return detectProfileName(shaderProfileArray, shaderOptionArray, false);
    }

    private ShaderOption getOption(String text) {
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < this.options.length) {
            ShaderOption shaderOption = this.options[i];
            if (shaderOption.getName().equals(text)) {
                return shaderOption;
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return null;
    }

    public String getValueText(String text) {
        return text.equals("<custom>")
                ? Lang.get("of.general.custom", "<custom>")
                : Shaders.translate("profile." + text, text);
    }

    private void applyProfileOptions() {
        int[] aint1 = ShaderOption.lz();
        ShaderProfile profile = this.getProfile(this.getValue());
        int[] aint = aint1;
        if (profile != null) {
            String[] astring = profile.getOptions();
            int i = 0;

            while (i < astring.length) {
                String text = astring[i];
                ShaderOption shaderOption = this.getOption(text);
                if (shaderOption != null) {
                    String textValue = profile.getValue(text);
                    shaderOption.setValue(textValue);
                }

                i++;
                if (aint != null) {
                    break;
                }
            }
        }
    }
}
