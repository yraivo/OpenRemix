package net.minecraft.client.shader;

import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import remix.compat.vecmath.Matrix4f;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL11;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ShaderGroup {
    public Framebuffer mainFramebuffer;
    private final List listShaders = Lists.newArrayList();
    private Matrix4f projectionMatrix;
    private int mainFramebufferHeight;
    private static String[] packedTextFragments;

    private float field_148037_k;

    private IResourceManager resourceManager;
    private int mainFramebufferWidth;
    private List listFramebuffers;
    private float field_148036_j;
    private final Map mapFramebuffers = Maps.newHashMap();
    private String shaderGroupName;

    public void parseGroup(TextureManager textureManager, ResourceLocation resourceLocation)
            throws JsonException {
        Entity[] entityArray = Framebuffer.p();
        JsonParser jsonparser = new JsonParser();
        Entity[] entityArrayValue = entityArray;
        InputStream inputstream = null;

        try {
            IResource resource = this.resourceManager.getResource(resourceLocation);
            inputstream = resource.getInputStream();
            JsonObject jsonobject =
                    jsonparser
                            .parse(IOUtils.toString(inputstream, Charsets.UTF_8))
                            .getAsJsonObject();
            if (JsonUtils.method_6f269b44202e870a5c79(jsonobject, "targets")) {
                JsonArray jsonarray = jsonobject.getAsJsonArray("targets");
                int i = 0;

                for (java.util.Iterator<?> __recoveryIterator171_2705 = (jsonarray).iterator();
                        (__recoveryIterator171_2705).hasNext(); ) {
                    JsonElement jsonelement = (JsonElement) (__recoveryIterator171_2705).next();
                    try {
                        this.initTarget(jsonelement);
                    } catch (Exception exception1) {
                        JsonException jsonException =
                                JsonException.method_4b749345a5b256762054(exception1);
                        jsonException.method_be01d1eb5e587c583ed6(
                                "targets[" + i + packedTextFragments[0]);
                        throw jsonException;
                    }

                    i++;
                    if (entityArrayValue != null) {
                        break;
                    }
                }
            }

            if (JsonUtils.method_6f269b44202e870a5c79(jsonobject, "passes")) {
                JsonArray jsonarray1 = jsonobject.getAsJsonArray("passes");
                int j = 0;

                for (java.util.Iterator<?> __recoveryIterator171_3658 = (jsonarray1).iterator();
                        (__recoveryIterator171_3658).hasNext(); ) {
                    JsonElement jsonelement1 = (JsonElement) (__recoveryIterator171_3658).next();
                    try {
                        this.parsePass(textureManager, jsonelement1);
                    } catch (Exception exception) {
                        JsonException jsonExceptionValue =
                                JsonException.method_4b749345a5b256762054(exception);
                        jsonExceptionValue.method_be01d1eb5e587c583ed6(
                                "passes[" + j + packedTextFragments[1]);
                        throw jsonExceptionValue;
                    }

                    j++;
                    if (entityArrayValue != null) {
                        break;
                    }
                }
            }
        } catch (Exception exception2) {
            JsonException jsonExceptionValue2 =
                    JsonException.method_4b749345a5b256762054(exception2);
            jsonExceptionValue2.b(resourceLocation.getResourcePath());
            throw jsonExceptionValue2;
        } finally {
            IOUtils.closeQuietly(inputstream);
        }
    }

    private void initTarget(JsonElement jsonelement) throws JsonException {
        Entity[] entityArray = Framebuffer.p();
        if (JsonUtils.isString(jsonelement)) {
            this.addFramebuffer(
                    jsonelement.getAsString(),
                    this.mainFramebufferWidth,
                    this.mainFramebufferHeight);
            if (entityArray == null) {
                return;
            }
        }

        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "target");
        String text = JsonUtils.getString(jsonobject, "name");
        int i =
                JsonUtils.getInt(
                        jsonobject, "width", this.mainFramebufferWidth);
        int j =
                JsonUtils.getInt(
                        jsonobject, "height", this.mainFramebufferHeight);
        if (this.mapFramebuffers.containsKey(text)) {
            throw new JsonException(text + " is already defined");
        }

        this.addFramebuffer(text, i, j);
    }

    private void parsePass(TextureManager textureManager, JsonElement jsonelement)
            throws JsonException {
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "pass");
        String text = JsonUtils.getString(jsonobject, "name");
        String textValue = JsonUtils.getString(jsonobject, "intarget");
        String textValue2 = JsonUtils.getString(jsonobject, "outtarget");
        Entity[] entityArray = Framebuffer.p();
        Framebuffer framebuffer = this.getFramebuffer(textValue);
        Entity[] entityArrayValue = entityArray;
        Framebuffer framebufferValue = this.getFramebuffer(textValue2);
        if (framebuffer == null) {
            throw new JsonException("Input target '" + textValue + "' does not exist");
        }

        if (framebufferValue == null) {
            throw new JsonException("Output target '" + textValue2 + "' does not exist");
        }

        Shader shader = this.addShader(text, framebuffer, framebufferValue);
        JsonArray jsonarray =
                JsonUtils.getJsonArray(jsonobject, "auxtargets", (JsonArray) null);
        if (jsonarray != null) {
            int i = 0;

            for (java.util.Iterator<?> __recoveryIterator171_7849 = (jsonarray).iterator();
                    (__recoveryIterator171_7849).hasNext(); ) {
                JsonElement jsonelement1 = (JsonElement) (__recoveryIterator171_7849).next();
                try {
                    label67:
                    {
                        JsonObject jsonobject1 = JsonUtils.getJsonObject(jsonelement1, "auxtarget");
                        String textValue3 = JsonUtils.getString(jsonobject1, "name");
                        String textValue4 = JsonUtils.getString(jsonobject1, "id");
                        Framebuffer framebufferValue2 = this.getFramebuffer(textValue4);
                        if (framebufferValue2 == null) {
                            ResourceLocation resourceLocation =
                                    new ResourceLocation("textures/effect/" + textValue4 + ".png");

                            try {
                                this.resourceManager.getResource(resourceLocation);
                            } catch (FileNotFoundException filenotfoundexception) {
                                throw new JsonException(
                                        "Render target or texture '"
                                                + textValue4
                                                + "' does not exist");
                            }

                            ITextureObject iTextureObject;
                            int j;
                            int k;
                            {
                                textureManager.bindTexture(resourceLocation);
                                iTextureObject = textureManager.getTexture(resourceLocation);
                                j = JsonUtils.getInt(jsonobject1, "width");
                                k = JsonUtils.getInt(jsonobject1, "height");
                                boolean booleanValue =
                                        JsonUtils.getBoolean(jsonobject1, "bilinear");
                                if (booleanValue) {
                                    GL11.glTexParameteri(3553, 10241, 9729);
                                    GL11.glTexParameteri(3553, 10240, 9729);
                                    if (!(entityArrayValue == null)) {

                                        GL11.glTexParameteri(3553, 10241, 9728);
                                        GL11.glTexParameteri(3553, 10240, 9728);
                                    }
                                } else {

                                    GL11.glTexParameteri(3553, 10241, 9728);
                                    GL11.glTexParameteri(3553, 10240, 9728);
                                }
                            }

                            shader.addAuxFramebuffer(
                                    textValue3, iTextureObject.getGlTextureId(), j, k);
                            if (entityArrayValue == null) {
                                break label67;
                            }
                        }

                        shader.addAuxFramebuffer(
                                textValue3,
                                framebufferValue2,
                                framebufferValue2.framebufferTextureWidth,
                                framebufferValue2.framebufferTextureHeight);
                    }
                } catch (Exception exception1) {
                    JsonException jsonException =
                            JsonException.method_4b749345a5b256762054(exception1);
                    jsonException.method_be01d1eb5e587c583ed6(
                            "auxtargets[" + i + packedTextFragments[2]);
                    throw jsonException;
                }

                i++;
                if (entityArrayValue != null) {
                    break;
                }
            }
        }

        JsonArray jsonarray1 =
                JsonUtils.getJsonArray(jsonobject, "uniforms", (JsonArray) null);
        if (jsonarray1 != null) {
            int l = 0;

            for (java.util.Iterator<?> __recoveryIterator171_11780 = (jsonarray1).iterator();
                    (__recoveryIterator171_11780).hasNext(); ) {
                JsonElement jsonelement2 = (JsonElement) (__recoveryIterator171_11780).next();
                try {
                    this.initUniform(jsonelement2);
                } catch (Exception exception) {
                    JsonException jsonExceptionValue =
                            JsonException.method_4b749345a5b256762054(exception);
                    jsonExceptionValue.method_be01d1eb5e587c583ed6(
                            "uniforms[" + l + packedTextFragments[3]);
                    throw jsonExceptionValue;
                }

                l++;
                if (entityArrayValue != null) {
                    break;
                }
            }
        }
    }

    public void method_d40fd202c9f68ff12b2a(float f) {
        Entity[] entityArray;
        {
            entityArray = Framebuffer.p();
            if (f < this.field_148037_k) {
                this.field_148036_j = this.field_148036_j + (1.0F - this.field_148037_k);
                this.field_148036_j += f;
                if (!(entityArray == null)) {

                    this.field_148036_j = this.field_148036_j + (f - this.field_148037_k);
                }
            } else {

                this.field_148036_j = this.field_148036_j + (f - this.field_148037_k);
            }
        }

        this.field_148037_k = f;

        while (this.field_148036_j > 20.0F) {
            this.field_148036_j -= 20.0F;
            if (entityArray != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_13135 = (this.listShaders).iterator();
                (__recoveryIterator171_13135).hasNext(); ) {
            Shader shader = (Shader) (__recoveryIterator171_13135).next();
            shader.renderBlended(this.field_148036_j / 20.0F);
            if (entityArray != null) {
                break;
            }
        }
    }

    static {
        net.minecraft.client.shader.ShaderGroup.packedTextFragments = new String[7];
        net.minecraft.client.shader.ShaderGroup.packedTextFragments[0] = new String("]");
        net.minecraft.client.shader.ShaderGroup.packedTextFragments[1] = new String("]");
        net.minecraft.client.shader.ShaderGroup.packedTextFragments[2] = new String("]");
        net.minecraft.client.shader.ShaderGroup.packedTextFragments[3] = new String("]");
        net.minecraft.client.shader.ShaderGroup.packedTextFragments[4] = new String("]");
    }

    public void deleteShaderGroup() {
        Entity[] entityArray = Framebuffer.p();
        Iterator iterator = this.mapFramebuffers.values().iterator();
        Entity[] entityArrayValue = entityArray;

        while (iterator.hasNext()) {
            Framebuffer framebuffer = (Framebuffer) iterator.next();
            framebuffer.deleteFramebuffer();
            if (entityArrayValue != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_17966 = (this.listShaders).iterator();
                (__recoveryIterator171_17966).hasNext(); ) {
            Shader shader = (Shader) (__recoveryIterator171_17966).next();
            shader.deleteShader();
            if (entityArrayValue != null) {
                break;
            }
        }

        this.listShaders.clear();
    }

    private Framebuffer getFramebuffer(String text) {
        return text == null
                ? null
                : (text.equals("minecraft:main")
                        ? this.mainFramebuffer
                        : (Framebuffer) this.mapFramebuffers.get(text));
    }

    public List<Shader> getShaders() {
        return this.listShaders;
    }

    public void addFramebuffer(String text, int i, int j) {
        Framebuffer framebuffer = new Framebuffer(i, j, true);
        framebuffer.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
        this.mapFramebuffers.put(text, framebuffer);
        if (i == this.mainFramebufferWidth && j == this.mainFramebufferHeight) {
            this.listFramebuffers.add(framebuffer);
        }
    }

    private void resetProjectionMatrix() {
        this.projectionMatrix = new Matrix4f();
        this.projectionMatrix.setIdentity();
        this.projectionMatrix.m00 = 2.0F / this.mainFramebuffer.framebufferTextureWidth;
        this.projectionMatrix.m11 = 2.0F / -this.mainFramebuffer.framebufferTextureHeight;
        this.projectionMatrix.m22 = -0.0020001999F;
        this.projectionMatrix.m33 = 1.0F;
        this.projectionMatrix.m03 = -1.0F;
        this.projectionMatrix.m13 = 1.0F;
        this.projectionMatrix.m23 = -1.0001999F;
    }

    private void initUniform(JsonElement jsonelement) throws JsonException {
        Entity[] entityArray = Framebuffer.p();
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "uniform");
        Entity[] entityArrayValue = entityArray;
        String text = JsonUtils.getString(jsonobject, "name");
        ShaderUniform shaderUniform =
                ((Shader) this.listShaders.get(this.listShaders.size() - 1))
                        .getShaderManager()
                        .ar(text);
        if (shaderUniform == null) {
            throw new JsonException("Uniform '" + text + "' does not exist");
        }

        float[] afloat = new float[4];
        int i = 0;

        for (java.util.Iterator<?> __recoveryIterator171_40687 =
                        (JsonUtils.getJsonArray(jsonobject, "values")).iterator();
                (__recoveryIterator171_40687).hasNext(); ) {
            JsonElement jsonelement1 = (JsonElement) (__recoveryIterator171_40687).next();
            try {
                afloat[i] = JsonUtils.getFloat(jsonelement1, "value");
            } catch (Exception exception) {
                JsonException jsonException = JsonException.method_4b749345a5b256762054(exception);
                jsonException.method_be01d1eb5e587c583ed6("values[" + i + packedTextFragments[4]);
                throw jsonException;
            }

            i++;
            if (entityArrayValue != null) {
                break;
            }
        }

        switch (i) {
            case 0:
            default:
                if (entityArrayValue == null) {
                    break;
                }
            case 1:
                shaderUniform.set(afloat[0]);
                if (entityArrayValue == null) {
                    break;
                }
            case 2:
                shaderUniform.set(afloat[0], afloat[1]);
                if (entityArrayValue == null) {
                    break;
                }
            case 3:
                shaderUniform.set(afloat[0], afloat[1], afloat[2]);
                if (entityArrayValue == null) {
                    break;
                }
            case 4:
                shaderUniform.setFloat4(
                        afloat[0], afloat[1], afloat[2], afloat[3]);
        }
    }

    public ShaderGroup(
            TextureManager textureManager,
            IResourceManager aHhValue,
            Framebuffer aHeValue,
            ResourceLocation resourceLocation)
            throws JsonException {
        this.listFramebuffers = Lists.newArrayList();
        this.resourceManager = aHhValue;
        this.mainFramebuffer = aHeValue;
        this.field_148036_j = 0.0F;
        this.field_148037_k = 0.0F;
        this.mainFramebufferWidth = aHeValue.framebufferWidth;
        this.mainFramebufferHeight = aHeValue.framebufferHeight;
        this.shaderGroupName = resourceLocation.toString();
        this.resetProjectionMatrix();
        this.parseGroup(textureManager, resourceLocation);
    }

    public Framebuffer getFramebufferRaw(String text) {
        return (Framebuffer) this.mapFramebuffers.get(text);
    }

    public Shader addShader(String text, Framebuffer framebuffer, Framebuffer framebufferValue)
            throws JsonException {
        Shader shader = new Shader(this.resourceManager, text, framebuffer, framebufferValue);
        this.listShaders.add(this.listShaders.size(), shader);
        return shader;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public List<Framebuffer> getListFramebuffers() {
        return this.listFramebuffers;
    }

    public final String getShaderGroupName() {
        return this.shaderGroupName;
    }

    public void createBindFramebuffers(int i, int j) {
        Entity[] entityArray = Framebuffer.p();
        this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
        Entity[] entityArrayValue = entityArray;
        this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
        this.resetProjectionMatrix();

        for (java.util.Iterator<?> __recoveryIterator171_46955 = (this.listShaders).iterator();
                (__recoveryIterator171_46955).hasNext(); ) {
            Shader shader = (Shader) (__recoveryIterator171_46955).next();
            shader.setProjectionMatrix(this.projectionMatrix);
            if (entityArrayValue != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_47206 = (this.listFramebuffers).iterator();
                (__recoveryIterator171_47206).hasNext(); ) {
            Framebuffer framebuffer = (Framebuffer) (__recoveryIterator171_47206).next();
            framebuffer.createBindFramebuffer(i, j);
            if (entityArrayValue != null) {
                break;
            }
        }
    }

    public void renderShaderGroup(float f) {
        Entity[] entityArray;
        {
            entityArray = Framebuffer.p();
            if (f < this.field_148037_k) {
                this.field_148036_j = this.field_148036_j + (1.0F - this.field_148037_k);
                this.field_148036_j += f;
                if (!(entityArray == null)) {

                    this.field_148036_j = this.field_148036_j + (f - this.field_148037_k);
                }
            } else {

                this.field_148036_j = this.field_148036_j + (f - this.field_148037_k);
            }
        }

        this.field_148037_k = f;

        while (this.field_148036_j > 20.0F) {
            this.field_148036_j -= 20.0F;
            if (entityArray != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_48156 = (this.listShaders).iterator();
                (__recoveryIterator171_48156).hasNext(); ) {
            Shader shader = (Shader) (__recoveryIterator171_48156).next();
            shader.loadShader(this.field_148036_j / 20.0F);
            if (entityArray != null) {
                break;
            }
        }
    }
}
