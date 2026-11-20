package shadersmod.client;

import optifine.OptiFineConfig;

public class ShaderUtils {
    public static boolean matchProfile(
            ShaderProfile shaderProfile, ShaderOption[] shaderOptionArray, boolean flag) {
        int[] aint = ShaderOption.lz();
        if (shaderProfile == null) {
            return false;
        }

        if (shaderOptionArray == null) {
            return false;
        }

        String[] astring = shaderProfile.getOptions();
        int i = 0;

        while (i < astring.length) {
            String text = astring[i];
            ShaderOption shaderOption = getShaderOption(text, shaderOptionArray);
            if (shaderOption != null) {
                String textValue = flag ? shaderOption.getValueDefault() : shaderOption.getValue();
                String textValue2 = shaderProfile.getValue(text);
                if (!OptiFineConfig.equals(textValue, textValue2)) {
                    return false;
                }
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return true;
    }

    public static ShaderOption getShaderOption(String text, ShaderOption[] shaderOptionArray) {
        int[] aint = ShaderOption.lz();
        if (shaderOptionArray == null) {
            return null;
        }

        int i = 0;

        while (i < shaderOptionArray.length) {
            ShaderOption shaderOption = shaderOptionArray[i];
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

    public static ShaderProfile detectProfile(
            ShaderProfile[] shaderProfileArray, ShaderOption[] shaderOptionArray, boolean flag) {
        int[] aint = ShaderOption.lz();
        if (shaderProfileArray == null) {
            return null;
        }

        int i = 0;

        while (i < shaderProfileArray.length) {
            ShaderProfile shaderProfile = shaderProfileArray[i];
            if (matchProfile(shaderProfile, shaderOptionArray, flag)) {
                return shaderProfile;
            }

            i++;
            if (aint != null) {
                break;
            }
        }

        return null;
    }
}
