package net.minecraft.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.JsonException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper {
    private static ShaderLinkHelper staticShaderLinkHelper;
    private static String[] packedTextFragments;

    private static Logger logger;

    public void linkProgram(ShaderManager shaderManager) {
        shaderManager.getFragmentShaderLoader().attachShader(shaderManager);
        shaderManager.getVertexShaderLoader().attachShader(shaderManager);
        OpenGlHelper.glLinkProgram(shaderManager.getProgram());
        int i = OpenGlHelper.glGetProgrami(shaderManager.getProgram(), OpenGlHelper.GL_LINK_STATUS);
        if (i == 0) {
            net.minecraft.client.shader.ShaderLinkHelper.logger.warn(
                    "Error encountered when linking program containing VS "
                            + shaderManager.getVertexShaderLoader().getShaderFilename()
                            + " and FS "
                            + shaderManager.getFragmentShaderLoader().getShaderFilename()
                            + ". Log output:");
            net.minecraft.client.shader.ShaderLinkHelper.logger.warn(
                    OpenGlHelper.glGetProgramInfoLog(shaderManager.getProgram(), 32768));
        }
    }

    public static ShaderLinkHelper getStaticShaderLinkHelper() {
        return net.minecraft.client.shader.ShaderLinkHelper.staticShaderLinkHelper;
    }

    public void deleteShader(ShaderManager shaderManager) {
        shaderManager.getFragmentShaderLoader().deleteShader(shaderManager);
        shaderManager.getVertexShaderLoader().deleteShader(shaderManager);
        OpenGlHelper.glDeleteProgram(shaderManager.getProgram());
    }

    public int createProgram() throws JsonException {
        int i = OpenGlHelper.glCreateProgram();

        try {
            if (i <= 0) {
                throw new JsonException(
                        "Could not create shader program (returned program ID "
                                + i
                                + packedTextFragments[0]);
            } else {
                return i;
            }
        } catch (JsonException jsonException) {
            throw remix.compat.compiler.JvmThrowTypes.flow(
                    method_b0c5492756dcc30d645d(jsonException));
        }
    }

    static {
        net.minecraft.client.shader.ShaderLinkHelper.packedTextFragments = new String[3];
        net.minecraft.client.shader.ShaderLinkHelper.packedTextFragments[0] = new String(")");
        net.minecraft.client.shader.ShaderLinkHelper.logger = LogManager.getLogger();
    }

    public static void setNewStaticShaderLinkHelper() {
        net.minecraft.client.shader.ShaderLinkHelper.staticShaderLinkHelper =
                new ShaderLinkHelper();
    }

    private static JsonException method_b0c5492756dcc30d645d(JsonException jsonException) {
        return jsonException;
    }
}
