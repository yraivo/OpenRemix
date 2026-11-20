package net.minecraft.client.renderer.block.model;

import net.minecraft.util.ResourceLocation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Reader;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ModelBlock {
    private boolean ambientOcclusion;
    protected ModelBlock parent;
    private static String[] packedTextFragments;
    protected Map textures;
    private List elements;
    protected ResourceLocation parentLocation;
    static Gson SERIALIZER;
    public String name;

    private boolean gui3d;
    private ItemCameraTransforms itemCameraTransforms;
    private static Logger LOGGER;

    protected ModelBlock(
            ResourceLocation resourceLocation,
            Map map,
            boolean flag,
            boolean flag1,
            ItemCameraTransforms itemCameraTransformsValue) {
        this(
                resourceLocation,
                Collections.emptyList(),
                map,
                flag,
                flag1,
                itemCameraTransformsValue);
    }

    private boolean hasParent() {
        try {
            if (this.parent != null) {
                return true;
            }
        } catch (net.minecraft.client.renderer.block.model.ModelBlockLoopException modelBlockLoopException) {
            throw net.minecraft.client.renderer.block.model.ModelBlock.method_fe62021786becf8e80db(modelBlockLoopException);
        }

        return false;
    }

    public ItemTransformVec3f getInGuiTransform() {
        return this.parent != null
                        && this.itemCameraTransforms.gui == net.minecraft.client.renderer.block.model.ItemTransformVec3f.DEFAULT
                ? this.parent.getInGuiTransform()
                : this.itemCameraTransforms.gui;
    }

    private static ModelBlockLoopException method_fe62021786becf8e80db(
            ModelBlockLoopException modelBlockLoopException) {
        return modelBlockLoopException;
    }

    public ItemTransformVec3f getHeadTransform() {
        return this.parent != null
                        && this.itemCameraTransforms.head == net.minecraft.client.renderer.block.model.ItemTransformVec3f.DEFAULT
                ? this.parent.getHeadTransform()
                : this.itemCameraTransforms.head;
    }

    public static ModelBlock deserialize(String text) {
        return net.minecraft.client.renderer.block.model.ModelBlock.deserialize(new java.io.StringReader(text));
    }

    public List getElements() {
        try {
            if (this.hasParent()) {
                return this.parent.getElements();
            }
        } catch (net.minecraft.client.renderer.block.model.ModelBlockLoopException modelBlockLoopException) {
            throw net.minecraft.client.renderer.block.model.ModelBlock.method_fe62021786becf8e80db(modelBlockLoopException);
        }

        return this.elements;
    }

    private boolean startsWithHash(String text) {
        try {
            if (text.charAt(0) == '#') {
                return true;
            }
        } catch (net.minecraft.client.renderer.block.model.ModelBlockLoopException modelBlockLoopException) {
            throw net.minecraft.client.renderer.block.model.ModelBlock.method_fe62021786becf8e80db(modelBlockLoopException);
        }

        return false;
    }

    public void getParentFromMap(Map map) {
        if (this.parentLocation != null) {
            this.parent = (ModelBlock) map.get(this.parentLocation);
        }
    }

    public static ModelBlock deserialize(Reader reader) {
        return (ModelBlock) net.minecraft.client.renderer.block.model.ModelBlock.SERIALIZER.fromJson(reader, ModelBlock.class);
    }

    public static void checkModelHierarchy(Map map) {
        int j = BakedQuad.cM();
        Iterator iterator = map.values().iterator();
        int i = j;

        while (iterator.hasNext()) {
            ModelBlock modelBlock = (ModelBlock) iterator.next();

            try {
                ModelBlock azhValue = modelBlock.parent;
                ModelBlock azhValue2 = azhValue.parent;

                while (azhValue != azhValue2) {
                    azhValue = azhValue.parent;
                    azhValue2 = azhValue2.parent.parent;
                    if (i == 0) {
                        break;
                    }
                }

                throw new ModelBlockLoopException();
            } catch (NullPointerException nullpointerexception) {
                if (i != 0) {
                    continue;
                }
                break;
            }
        }
    }

    public ItemTransformVec3f getFirstPersonTransform() {
        return this.parent != null
                        && this.itemCameraTransforms.firstPerson == net.minecraft.client.renderer.block.model.ItemTransformVec3f.DEFAULT
                ? this.parent.getFirstPersonTransform()
                : this.itemCameraTransforms.firstPerson;
    }

    public String resolveTextureName(String text) {
        if (!this.startsWithHash(text)) {
            text = '#' + text;
        }

        return this.resolveTextureName(
                text, new net.minecraft.client.renderer.block.model.ModelBlockBookkeep(this, null));
    }

    public boolean isGui3d() {
        return this.gui3d;
    }

    protected ModelBlock(
            List list,
            Map map,
            boolean flag,
            boolean flag1,
            ItemCameraTransforms itemCameraTransformsValue) {
        this((ResourceLocation) null, list, map, flag, flag1, itemCameraTransformsValue);
    }

    public ModelBlock getRootModel() {
        try {
            if (this.hasParent()) {
                return this.parent.getRootModel();
            }
        } catch (net.minecraft.client.renderer.block.model.ModelBlockLoopException modelBlockLoopException) {
            throw net.minecraft.client.renderer.block.model.ModelBlock.method_fe62021786becf8e80db(modelBlockLoopException);
        }

        return this;
    }

    private ModelBlock(
            ResourceLocation parentLocationValue,
            List elementsValue,
            Map map,
            boolean flag,
            boolean flag1,
            ItemCameraTransforms azjValue) {
        this.name = packedTextFragments[0];
        this.elements = elementsValue;
        this.ambientOcclusion = flag;
        this.gui3d = flag1;
        this.textures = map;
        this.parentLocation = parentLocationValue;
        this.itemCameraTransforms = azjValue;
    }

    private String resolveTextureName(
            String text, ModelBlockBookkeep modelBlockBookkeep) {
        if (this.startsWithHash(text)) {
            if (this == modelBlockBookkeep.modelExt) {
                net.minecraft.client.renderer.block.model.ModelBlock.LOGGER.warn(
                        "Unable to resolve texture due to upward reference: "
                                + text
                                + " in "
                                + this.name);
                return "missingno";
            }

            String textValue = (String) this.textures.get(text.substring(1));
            if (textValue == null && this.hasParent()) {
                textValue =
                        this.parent.resolveTextureName(text, modelBlockBookkeep);
            }

            modelBlockBookkeep.modelExt = this;
            if (textValue != null && this.startsWithHash(textValue)) {
                textValue =
                        modelBlockBookkeep.model.resolveTextureName(
                                textValue, modelBlockBookkeep);
            }

            return textValue != null && !this.startsWithHash(textValue) ? textValue : "missingno";
        } else {
            return text;
        }
    }

    // region String decoding support

    // endregion

    public ResourceLocation getParentLocation() {
        return this.parentLocation;
    }

    public ItemTransformVec3f getThirdPersonTransform() {
        return this.parent != null
                        && this.itemCameraTransforms.thirdPerson == net.minecraft.client.renderer.block.model.ItemTransformVec3f.DEFAULT
                ? this.parent.getThirdPersonTransform()
                : this.itemCameraTransforms.thirdPerson;
    }

    public boolean isTexturePresent(String text) {
        return !"missingno".equals(this.resolveTextureName(text));
    }

    public boolean isAmbientOcclusion() {
        try {
            if (this.hasParent()) {
                return this.parent.isAmbientOcclusion();
            }
        } catch (net.minecraft.client.renderer.block.model.ModelBlockLoopException modelBlockLoopException) {
            throw net.minecraft.client.renderer.block.model.ModelBlock.method_fe62021786becf8e80db(modelBlockLoopException);
        }

        return this.ambientOcclusion;
    }

    public boolean isResolved() {
        return this.parentLocation == null || this.parent != null && this.parent.isResolved();
    }

    static {
        net.minecraft.client.renderer.block.model.ModelBlock.packedTextFragments = new String[3];
        net.minecraft.client.renderer.block.model.ModelBlock.packedTextFragments[0] = new String("");
        net.minecraft.client.renderer.block.model.ModelBlock.LOGGER = LogManager.getLogger();
        net.minecraft.client.renderer.block.model.ModelBlock.SERIALIZER =
                new GsonBuilder()
                        .registerTypeAdapter(ModelBlock.class, new ModelBlockDeserializer())
                        .registerTypeAdapter(
                                BlockPart.class, new BlockPartDeserializer())
                        .registerTypeAdapter(
                                BlockPartFace.class, new BlockPartFaceDeserializer())
                        .registerTypeAdapter(
                                BlockFaceUV.class, new BlockFaceUVDeserializer())
                        .registerTypeAdapter(
                                ItemTransformVec3f.class, new ItemTransformVec3fDeserializer())
                        .registerTypeAdapter(
                                ItemCameraTransforms.class, new ItemCameraTransformsDeserializer())
                        .create();
    }
}
