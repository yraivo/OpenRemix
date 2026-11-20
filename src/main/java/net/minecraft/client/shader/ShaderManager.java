package net.minecraft.client.shader;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.client.util.JsonException;
import net.minecraft.entity.Entity;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class ShaderManager {
    private boolean isDirty;
    private ShaderLoader vertexShaderLoader;
    private static int currentProgram;
    private final Map shaderSamplers = Maps.newHashMap();
    private static boolean field_148000_e;
    private static ShaderDefault defaultShaderUniform;
    private List attribLocations;
    private List attributes;
    private JsonBlendingMode field_148016_p;

    private ShaderLoader fragmentShaderLoader;
    private List shaderSamplerLocations;
    private Map mappedShaderUniforms;
    private static String[] packedTextFragments;
    private List shaderUniformLocations;
    private static Logger logger;
    private String programFilename;

    private int program;
    private static ShaderManager staticShaderManager;
    private final List samplerNames = Lists.newArrayList();
    private List shaderUniforms;
    private boolean useFaceCulling;

    public ShaderLoader getVertexShaderLoader() {
        return this.vertexShaderLoader;
    }

    public void deleteShader() {
        ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
    }

    public void addSamplerTexture(String text, Object object) {
        if (this.shaderSamplers.containsKey(text)) {
            this.shaderSamplers.remove(text);
        }

        this.shaderSamplers.put(text, object);
        this.markDirty();
    }

    public ShaderLoader getFragmentShaderLoader() {
        return this.fragmentShaderLoader;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private void setupUniforms() {
        int i = 0;
        Entity[] entityArray = Framebuffer.p();
        int k = 0;
        Entity[] entityArrayValue = entityArray;

        while (i < this.samplerNames.size()) {
            {
                String text = (String) this.samplerNames.get(i);
                int j = OpenGlHelper.glGetUniformLocation(this.program, text);
                if (j == -1) {
                    net.minecraft.client.shader.ShaderManager.logger.warn(
                            "Shader "
                                    + this.programFilename
                                    + "could not find sampler named "
                                    + text
                                    + " in the specified shader program.");
                    this.shaderSamplers.remove(text);
                    this.samplerNames.remove(k);
                    k--;
                    if (!(entityArrayValue == null)) {

                        this.shaderSamplerLocations.add(j);
                    }
                } else {

                    this.shaderSamplerLocations.add(j);
                }
            }

            i++;
            k++;
            if (entityArrayValue != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_24635 = (this.shaderUniforms).iterator();
                (__recoveryIterator171_24635).hasNext(); ) {
            ShaderUniform shaderUniform = (ShaderUniform) (__recoveryIterator171_24635).next();
            {
                String textValue = shaderUniform.getShaderName();
                int l = OpenGlHelper.glGetUniformLocation(this.program, textValue);
                if (l == -1) {
                    net.minecraft.client.shader.ShaderManager.logger.warn(
                            "Could not find uniform named "
                                    + textValue
                                    + " in the specified shader program.");
                    if (!(entityArrayValue == null)) {

                        this.shaderUniformLocations.add(l);
                        shaderUniform.setUniformLocation(l);
                        this.mappedShaderUniforms.put(textValue, shaderUniform);
                    }
                } else {

                    this.shaderUniformLocations.add(l);
                    shaderUniform.setUniformLocation(l);
                    this.mappedShaderUniforms.put(textValue, shaderUniform);
                }
            }

            if (entityArrayValue != null) {
                break;
            }
        }
    }

    public ShaderUniform aq(String text) {
        return (ShaderUniform)
                (this.mappedShaderUniforms.containsKey(text)
                        ? (ShaderUniform) this.mappedShaderUniforms.get(text)
                        : net.minecraft.client.shader.ShaderManager.defaultShaderUniform);
    }

    private void parseSampler(JsonElement jsonelement) {
        Entity[] entityArray = Framebuffer.p();
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "sampler");
        String text = JsonUtils.getString(jsonobject, "name");
        Entity[] entityArrayValue = entityArray;
        if (!JsonUtils.method_7cc6238b82e560d487c5(jsonobject, "file")) {
            this.shaderSamplers.put(text, null);
            this.samplerNames.add(text);
            if (entityArrayValue == null) {
                return;
            }
        }

        this.samplerNames.add(text);
    }

    public void cj() {
        Entity[] entityArray = Framebuffer.p();
        this.isDirty = false;
        net.minecraft.client.shader.ShaderManager.staticShaderManager = this;
        Entity[] entityArrayValue = entityArray;
        this.field_148016_p.method_8d9b536bcdc9991c48fb();
        if (this.program != net.minecraft.client.shader.ShaderManager.currentProgram) {
            OpenGlHelper.glUseProgram(this.program);
            net.minecraft.client.shader.ShaderManager.currentProgram = this.program;
        }

        {
            if (this.useFaceCulling) {
                GlStateManager.enableCull();
                if (!(entityArrayValue == null)) {

                    GlStateManager.disableCull();
                }
            } else {

                GlStateManager.disableCull();
            }
        }

        int i = 0;

        while (i < this.shaderSamplerLocations.size()) {
            if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
                int j;
                label60:
                {
                    GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
                    GlStateManager.enableTexture2D();
                    Object object = this.shaderSamplers.get(this.samplerNames.get(i));
                    j = -1;
                    if (object instanceof Framebuffer) {
                        j = ((Framebuffer) object).framebufferTexture;
                        if (entityArrayValue == null) {
                            break label60;
                        }
                    }

                    if (object instanceof ITextureObject) {
                        j = ((ITextureObject) object).getGlTextureId();
                        if (entityArrayValue == null) {
                            break label60;
                        }
                    }

                    if (object instanceof Integer) {
                        j = (Integer) object;
                    }
                }

                if (j != -1) {
                    GlStateManager.bindTexture(j);
                    OpenGlHelper.glUniform1i(
                            OpenGlHelper.glGetUniformLocation(
                                    this.program, (CharSequence) this.samplerNames.get(i)),
                            i);
                }
            }

            i++;
            if (entityArrayValue != null) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_30960 = (this.shaderUniforms).iterator();
                (__recoveryIterator171_30960).hasNext(); ) {
            ShaderUniform shaderUniform = (ShaderUniform) (__recoveryIterator171_30960).next();
            shaderUniform.cj();
            if (entityArrayValue != null) {
                break;
            }
        }
    }

    public int getProgram() {
        return this.program;
    }

    public void useShader() {
        Entity[] entityArray = Framebuffer.p();
        OpenGlHelper.glUseProgram(0);
        net.minecraft.client.shader.ShaderManager.currentProgram = -1;
        net.minecraft.client.shader.ShaderManager.staticShaderManager = null;
        Entity[] entityArrayValue = entityArray;
        net.minecraft.client.shader.ShaderManager.field_148000_e = true;
        int i = 0;

        while (i < this.shaderSamplerLocations.size()) {
            if (this.shaderSamplers.get(this.samplerNames.get(i)) != null) {
                GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + i);
                GlStateManager.bindTexture(0);
            }

            i++;
            if (entityArrayValue != null) {
                break;
            }
        }
    }

    static {
        net.minecraft.client.shader.ShaderManager.packedTextFragments = new String[7];
        net.minecraft.client.shader.ShaderManager.packedTextFragments[0] = new String("]");
        net.minecraft.client.shader.ShaderManager.packedTextFragments[1] = new String("]");
        net.minecraft.client.shader.ShaderManager.packedTextFragments[2] = new String("]");
        net.minecraft.client.shader.ShaderManager.packedTextFragments[3] = new String(")");
        net.minecraft.client.shader.ShaderManager.packedTextFragments[4] = new String("]");
        net.minecraft.client.shader.ShaderManager.logger = LogManager.getLogger();
        net.minecraft.client.shader.ShaderManager.defaultShaderUniform = new ShaderDefault();
        net.minecraft.client.shader.ShaderManager.staticShaderManager = null;
        net.minecraft.client.shader.ShaderManager.currentProgram = -1;
        net.minecraft.client.shader.ShaderManager.field_148000_e = true;
    }

    private void parseUniform(JsonElement jsonelement) throws JsonException {
        Entity[] entityArray = Framebuffer.p();
        JsonObject jsonobject = JsonUtils.getJsonObject(jsonelement, "uniform");
        Entity[] entityArrayValue = entityArray;
        String text = JsonUtils.getString(jsonobject, "name");
        int i = ShaderUniform.parseType(JsonUtils.getString(jsonobject, "type"));
        int j = JsonUtils.getInt(jsonobject, "count");
        float[] afloat = new float[Math.max(j, 16)];
        JsonArray jsonarray = JsonUtils.getJsonArray(jsonobject, "values");
        if (jsonarray.size() != j && jsonarray.size() > 1) {
            throw new JsonException(
                    "Invalid amount of values specified (expected "
                            + j
                            + ", found "
                            + jsonarray.size()
                            + packedTextFragments[3]);
        }

        int k = 0;

        for (java.util.Iterator<?> __recoveryIterator171_38673 = (jsonarray).iterator();
                (__recoveryIterator171_38673).hasNext(); ) {
            JsonElement jsonelement1 = (JsonElement) (__recoveryIterator171_38673).next();
            try {
                afloat[k] = JsonUtils.getFloat(jsonelement1, "value");
            } catch (Exception exception) {
                JsonException jsonException = JsonException.method_4b749345a5b256762054(exception);
                jsonException.method_be01d1eb5e587c583ed6("values[" + k + packedTextFragments[4]);
                throw jsonException;
            }

            k++;
            if (entityArrayValue != null) {
                break;
            }
        }

        if (j > 1 && jsonarray.size() == 1) {
            while (k < j) {
                afloat[k] = afloat[0];
                k++;
                if (entityArrayValue != null) {
                    break;
                }
            }
        }

        ShaderUniform shaderUniform;
        label78:
        {
            int l = j > 1 && j <= 4 && i < 8 ? j - 1 : 0;
            shaderUniform = new ShaderUniform(text, i + l, j, this);
            if (i <= 3) {
                shaderUniform.set(
                        (int) afloat[0], (int) afloat[1], (int) afloat[2], (int) afloat[3]);
                if (entityArrayValue == null) {
                    break label78;
                }
            }

            if (i <= 7) {
                shaderUniform.setFloatsForUniformType(
                        afloat[0], afloat[1], afloat[2], afloat[3]);
                if (entityArrayValue == null) {
                    break label78;
                }
            }

            shaderUniform.set(afloat);
        }

        this.shaderUniforms.add(shaderUniform);
    }

    public void markDirty() {
        this.isDirty = true;
    }

    public ShaderUniform ar(String text) {
        return this.mappedShaderUniforms.containsKey(text)
                ? (ShaderUniform) this.mappedShaderUniforms.get(text)
                : null;
    }

    public ShaderManager(IResourceManager iResourceManager, String text) throws JsonException {
        this.shaderSamplerLocations = Lists.newArrayList();
        this.shaderUniforms = Lists.newArrayList();
        Entity[] entityArray = Framebuffer.p();
        this.shaderUniformLocations = Lists.newArrayList();
        this.mappedShaderUniforms = Maps.newHashMap();
        JsonParser jsonparser = new JsonParser();
        Entity[] entityArrayValue = entityArray;
        ResourceLocation resourceLocation =
                new ResourceLocation("shaders/program/" + text + ".json");
        this.programFilename = text;
        InputStream inputstream = null;

        try {
            inputstream = iResourceManager.getResource(resourceLocation).getInputStream();
            JsonObject jsonobject =
                    jsonparser
                            .parse(IOUtils.toString(inputstream, Charsets.UTF_8))
                            .getAsJsonObject();
            String textValue = JsonUtils.getString(jsonobject, "vertex");
            String textValue2 = JsonUtils.getString(jsonobject, "fragment");
            JsonArray jsonarray =
                    JsonUtils.getJsonArray(jsonobject, "samplers", (JsonArray) null);
            if (jsonarray != null) {
                int i = 0;

                for (java.util.Iterator<?> __recoveryIterator171_42154 = (jsonarray).iterator();
                        (__recoveryIterator171_42154).hasNext(); ) {
                    JsonElement jsonelement = (JsonElement) (__recoveryIterator171_42154).next();
                    try {
                        this.parseSampler(jsonelement);
                    } catch (Exception exception2) {
                        JsonException jsonException =
                                JsonException.method_4b749345a5b256762054(exception2);
                        jsonException.method_be01d1eb5e587c583ed6(
                                "samplers[" + i + packedTextFragments[0]);
                        throw jsonException;
                    }

                    i++;
                    if (entityArrayValue != null) {
                        break;
                    }
                }
            }

            {
                JsonArray jsonarray1 =
                        JsonUtils.getJsonArray(
                                jsonobject, "attributes", (JsonArray) null);
                if (jsonarray1 != null) {
                    int j = 0;
                    this.attribLocations = Lists.newArrayListWithCapacity(jsonarray1.size());
                    this.attributes = Lists.newArrayListWithCapacity(jsonarray1.size());

                    for (java.util.Iterator<?> __recoveryIterator171_43213 =
                                    (jsonarray1).iterator();
                            (__recoveryIterator171_43213).hasNext(); ) {
                        JsonElement jsonelement1 =
                                (JsonElement) (__recoveryIterator171_43213).next();
                        try {
                            this.attributes.add(JsonUtils.getString(jsonelement1, "attribute"));
                        } catch (Exception exception1) {
                            JsonException jsonExceptionValue =
                                    JsonException.method_4b749345a5b256762054(exception1);
                            jsonExceptionValue.method_be01d1eb5e587c583ed6(
                                    "attributes[" + j + packedTextFragments[1]);
                            throw jsonExceptionValue;
                        }

                        j++;
                        if (entityArrayValue != null) {
                            break;
                        }
                    }

                    if (!(entityArrayValue == null)) {

                        this.attribLocations = null;
                        this.attributes = null;
                    }
                } else {

                    this.attribLocations = null;
                    this.attributes = null;
                }
            }

            JsonArray jsonarray2 =
                    JsonUtils.getJsonArray(jsonobject, "uniforms", (JsonArray) null);
            if (jsonarray2 != null) {
                int k = 0;

                for (java.util.Iterator<?> __recoveryIterator171_44366 = (jsonarray2).iterator();
                        (__recoveryIterator171_44366).hasNext(); ) {
                    JsonElement jsonelement2 = (JsonElement) (__recoveryIterator171_44366).next();
                    try {
                        this.parseUniform(jsonelement2);
                    } catch (Exception exception) {
                        JsonException jsonExceptionValue2 =
                                JsonException.method_4b749345a5b256762054(exception);
                        jsonExceptionValue2.method_be01d1eb5e587c583ed6(
                                "uniforms[" + k + packedTextFragments[2]);
                        throw jsonExceptionValue2;
                    }

                    k++;
                    if (entityArrayValue != null) {
                        break;
                    }
                }
            }

            this.field_148016_p =
                    JsonBlendingMode.method_630ec23117d00eb89950(
                            JsonUtils.getJsonObject(
                                    jsonobject, "blend", (JsonObject) null));
            this.useFaceCulling = JsonUtils.getBoolean(jsonobject, "cull", true);
            this.vertexShaderLoader =
                    ShaderLoader.loadShader(
                            iResourceManager, ShaderLoaderShaderType.VERTEX, textValue);
            this.fragmentShaderLoader =
                    ShaderLoader.loadShader(
                            iResourceManager, ShaderLoaderShaderType.FRAGMENT, textValue2);
            this.program =
                    ShaderLinkHelper.getStaticShaderLinkHelper().createProgram();
            ShaderLinkHelper.getStaticShaderLinkHelper().linkProgram(this);
            this.setupUniforms();
            if (this.attributes != null) {
                for (java.util.Iterator<?> __recoveryIterator171_45852 = (this.attributes).iterator();
                        (__recoveryIterator171_45852).hasNext(); ) {
                    String textValue3 = (String) (__recoveryIterator171_45852).next();
                    int l = OpenGlHelper.glGetAttribLocation(this.program, textValue3);
                    this.attribLocations.add(l);
                    if (entityArrayValue != null) {
                        break;
                    }
                }
            }
        } catch (Exception exception3) {
            JsonException jsonExceptionValue3 =
                    JsonException.method_4b749345a5b256762054(exception3);
            jsonExceptionValue3.b(resourceLocation.getResourcePath());
            throw jsonExceptionValue3;
        } finally {
            IOUtils.closeQuietly(inputstream);
        }

        this.markDirty();
    }
}
