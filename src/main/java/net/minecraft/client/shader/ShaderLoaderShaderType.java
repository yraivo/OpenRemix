package net.minecraft.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;

import com.google.common.collect.Maps;

import java.util.Map;

public enum ShaderLoaderShaderType {
    VERTEX("VERTEX", 0, "vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER),
    FRAGMENT("FRAGMENT", 1, "fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);
    private static ShaderLoaderShaderType[] akO;

    private String shaderExtension;

    private final Map loadedShaders = Maps.newHashMap();
    private String shaderName;
    private int shaderMode;

    ShaderLoaderShaderType(String text, int k, String textValue, String textValue2, int j) {
        this.shaderName = textValue;
        this.shaderExtension = textValue2;
        this.shaderMode = j;
    }

    protected Map getLoadedShaders() {
        return this.loadedShaders;
    }

    protected String getShaderExtension() {
        return this.shaderExtension;
    }

    protected int getShaderMode() {
        return this.shaderMode;
    }

    static {
        akO =
                new ShaderLoaderShaderType[] {
                    net.minecraft.client.shader.ShaderLoaderShaderType.VERTEX, net.minecraft.client.shader.ShaderLoaderShaderType.FRAGMENT
                };
    }

    // region String decoding support

    // endregion

    public String getShaderName() {
        return this.shaderName;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

}
