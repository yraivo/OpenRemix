package net.minecraft.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class ShaderLoader {

    private int shader;
    private String shaderFilename;
    private ShaderLoaderShaderType shaderType;

    private int shaderAttachCount = 0;


    public void deleteShader(ShaderManager shaderManager) {
        this.shaderAttachCount--;
        if (this.shaderAttachCount <= 0) {
            OpenGlHelper.glDeleteShader(this.shader);
            this.shaderType.getLoadedShaders().remove(this.shaderFilename);
        }
    }

    public void attachShader(ShaderManager shaderManager) {
        this.shaderAttachCount++;
        OpenGlHelper.glAttachShader(shaderManager.getProgram(), this.shader);
    }

    private ShaderLoader(ShaderLoaderShaderType aHkValue, int shaderValue, String text) {
        this.shaderType = aHkValue;
        this.shader = shaderValue;
        this.shaderFilename = text;
    }

    protected static byte[] toByteArray(BufferedInputStream bufferedinputstream)
            throws IOException {
        byte[] abyte;
        try {
            abyte = IOUtils.toByteArray(bufferedinputstream);
        } finally {
            bufferedinputstream.close();
        }

        return abyte;
    }

    public String getShaderFilename() {
        return this.shaderFilename;
    }

    public static ShaderLoader loadShader(
            IResourceManager iResourceManager,
            ShaderLoaderShaderType shaderLoaderShaderType,
            String text)
            throws IOException {
        ShaderLoader shaderLoader =
                (ShaderLoader) shaderLoaderShaderType.getLoadedShaders().get(text);
        if (shaderLoader == null) {
            ResourceLocation resourceLocation =
                    new ResourceLocation(
                            "shaders/program/"
                                    + text
                                    + shaderLoaderShaderType.getShaderExtension());
            BufferedInputStream bufferedinputstream =
                    new BufferedInputStream(
                            iResourceManager.getResource(resourceLocation).getInputStream());
            byte[] abyte = toByteArray(bufferedinputstream);
            ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
            bytebuffer.put(abyte);
            ((Buffer) bytebuffer).position(0);
            int i =
                    OpenGlHelper.glCreateShader(
                            shaderLoaderShaderType.getShaderMode());
            OpenGlHelper.glShaderSource(i, bytebuffer);
            OpenGlHelper.glCompileShader(i);
            if (OpenGlHelper.glGetShaderi(i, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
                String textValue =
                        StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(i, 32768));
                JsonException jsonException =
                        new JsonException(
                                "Couldn't compile "
                                        + shaderLoaderShaderType.getShaderName()
                                        + " program: "
                                        + textValue);
                jsonException.b(resourceLocation.getResourcePath());
                throw jsonException;
            }

            shaderLoader = new ShaderLoader(shaderLoaderShaderType, i, text);
            shaderLoaderShaderType.getLoadedShaders().put(text, shaderLoader);
        }

        return shaderLoader;
    }
}
