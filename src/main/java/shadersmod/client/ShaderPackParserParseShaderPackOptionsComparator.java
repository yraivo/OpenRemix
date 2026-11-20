package shadersmod.client;

import shadersmod.client.ShaderOption;

import java.util.Comparator;

public final class ShaderPackParserParseShaderPackOptionsComparator implements Comparator {
    public int compare(
            ShaderOption shaderOption, ShaderOption shaderOptionValue) {
        return shaderOption.getName().compareToIgnoreCase(shaderOptionValue.getName());
    }

    @Override
    public int compare(Object p171_0, Object p171_1) {
        return this.compare(
                (shadersmod.client.ShaderOption) p171_0, (shadersmod.client.ShaderOption) p171_1);
    }
}
