package net.minecraft.client.resources.model;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Queues;
import com.google.common.collect.Sets;

import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockPart;
import net.minecraft.client.renderer.block.model.BlockPartFace;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemModelGenerator;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.block.model.ModelBlockDefinitionVariant;
import net.minecraft.client.renderer.block.model.ModelBlockDefinitionVariants;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.Facing;
import net.minecraft.util.IRegistry;
import net.minecraft.util.RegistrySimple;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ModelBakery {
    private Map variants;
    protected static ModelResourceLocation MODEL_MISSING;
    private FaceBakery faceBakery;
    private BlockModelShapes blockModelShapes;
    private Map sprites;
    private static ModelBlock MODEL_COMPASS;
    private Map blockDefinitions;

    private TextureMap textureMap;
    private Map variantNames;

    private static Logger LOGGER;
    private static ModelBlock MODEL_CLOCK;
    private Map itemLocations;
    private static String[] packedTextFragments;
    private static ModelBlock MODEL_ENTITY;
    private ItemModelGenerator itemModelGenerator;
    private static Set LOCATIONS_BUILTIN_TEXTURES;
    private IResourceManager resourceManager;
    private RegistrySimple bakedRegistry;
    private static Joiner JOINER;
    private static ModelBlock MODEL_GENERATED;
    private static Map BUILT_IN_MODELS;
    private Map models;

    private ResourceLocation getParentLocation(ResourceLocation resourceLocation) {
        for (java.util.Iterator<?> __recoveryIterator171_2805 = (this.models.entrySet()).iterator();
                (__recoveryIterator171_2805).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_2805).next();
            ModelBlock value = (ModelBlock) entry.getValue();
            if (value != null && resourceLocation.equals(value.getParentLocation())) {
                return (ResourceLocation) entry.getKey();
            }
        }

        return null;
    }

    private void registerVariantNames() {
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.stone),
                Lists.newArrayList(
                        new String[] {
                            "stone",
                            "granite",
                            "granite_smooth",
                            "diorite",
                            "diorite_smooth",
                            "andesite",
                            "andesite_smooth"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.dirt),
                Lists.newArrayList(new String[] {"dirt", "coarse_dirt", "podzol"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.planks),
                Lists.newArrayList(
                        new String[] {
                            "oak_planks",
                            "spruce_planks",
                            "birch_planks",
                            "jungle_planks",
                            "acacia_planks",
                            "dark_oak_planks"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.sapling),
                Lists.newArrayList(
                        new String[] {
                            "oak_sapling",
                            "spruce_sapling",
                            "birch_sapling",
                            "jungle_sapling",
                            "acacia_sapling",
                            "dark_oak_sapling"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.sand),
                Lists.newArrayList(new String[] {"sand", "red_sand"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.log),
                Lists.newArrayList(
                        new String[] {"oak_log", "spruce_log", "birch_log", "jungle_log"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.leaves),
                Lists.newArrayList(
                        new String[] {
                            "oak_leaves", "spruce_leaves", "birch_leaves", "jungle_leaves"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.sponge),
                Lists.newArrayList(new String[] {"sponge", "sponge_wet"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.sandstone),
                Lists.newArrayList(
                        new String[] {"sandstone", "chiseled_sandstone", "smooth_sandstone"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.red_sandstone),
                Lists.newArrayList(
                        new String[] {
                            "red_sandstone", "chiseled_red_sandstone", "smooth_red_sandstone"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.tallgrass),
                Lists.newArrayList(new String[] {"dead_bush", "tall_grass", "fern"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.deadbush),
                Lists.newArrayList(new String[] {"dead_bush"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.wool),
                Lists.newArrayList(
                        new String[] {
                            "black_wool",
                            "red_wool",
                            "green_wool",
                            "brown_wool",
                            "blue_wool",
                            "purple_wool",
                            "cyan_wool",
                            "silver_wool",
                            "gray_wool",
                            "pink_wool",
                            "lime_wool",
                            "yellow_wool",
                            "light_blue_wool",
                            "magenta_wool",
                            "orange_wool",
                            "white_wool"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.yellow_flower),
                Lists.newArrayList(new String[] {"dandelion"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.red_flower),
                Lists.newArrayList(
                        new String[] {
                            "poppy",
                            "blue_orchid",
                            "allium",
                            "houstonia",
                            "red_tulip",
                            "orange_tulip",
                            "white_tulip",
                            "pink_tulip",
                            "oxeye_daisy"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.stone_slab),
                Lists.newArrayList(
                        new String[] {
                            "stone_slab",
                            "sandstone_slab",
                            "cobblestone_slab",
                            "brick_slab",
                            "stone_brick_slab",
                            "nether_brick_slab",
                            "quartz_slab"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.stone_slab2),
                Lists.newArrayList(new String[] {"red_sandstone_slab"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.stained_glass),
                Lists.newArrayList(
                        new String[] {
                            "black_stained_glass",
                            "red_stained_glass",
                            "green_stained_glass",
                            "brown_stained_glass",
                            "blue_stained_glass",
                            "purple_stained_glass",
                            "cyan_stained_glass",
                            "silver_stained_glass",
                            "gray_stained_glass",
                            "pink_stained_glass",
                            "lime_stained_glass",
                            "yellow_stained_glass",
                            "light_blue_stained_glass",
                            "magenta_stained_glass",
                            "orange_stained_glass",
                            "white_stained_glass"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.monster_egg),
                Lists.newArrayList(
                        new String[] {
                            "stone_monster_egg",
                            "cobblestone_monster_egg",
                            "stone_brick_monster_egg",
                            "mossy_brick_monster_egg",
                            "cracked_brick_monster_egg",
                            "chiseled_brick_monster_egg"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.stonebrick),
                Lists.newArrayList(
                        new String[] {
                            "stonebrick",
                            "mossy_stonebrick",
                            "cracked_stonebrick",
                            "chiseled_stonebrick"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.wooden_slab),
                Lists.newArrayList(
                        new String[] {
                            "oak_slab",
                            "spruce_slab",
                            "birch_slab",
                            "jungle_slab",
                            "acacia_slab",
                            "dark_oak_slab"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.cobblestone_wall),
                Lists.newArrayList(new String[] {"cobblestone_wall", "mossy_cobblestone_wall"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.anvil),
                Lists.newArrayList(
                        new String[] {
                            "anvil_intact", "anvil_slightly_damaged", "anvil_very_damaged"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.quartz_block),
                Lists.newArrayList(
                        new String[] {"quartz_block", "chiseled_quartz_block", "quartz_column"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.stained_hardened_clay),
                Lists.newArrayList(
                        new String[] {
                            "black_stained_hardened_clay",
                            "red_stained_hardened_clay",
                            "green_stained_hardened_clay",
                            "brown_stained_hardened_clay",
                            "blue_stained_hardened_clay",
                            "purple_stained_hardened_clay",
                            "cyan_stained_hardened_clay",
                            "silver_stained_hardened_clay",
                            "gray_stained_hardened_clay",
                            "pink_stained_hardened_clay",
                            "lime_stained_hardened_clay",
                            "yellow_stained_hardened_clay",
                            "light_blue_stained_hardened_clay",
                            "magenta_stained_hardened_clay",
                            "orange_stained_hardened_clay",
                            "white_stained_hardened_clay"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.stained_glass_pane),
                Lists.newArrayList(
                        new String[] {
                            "black_stained_glass_pane",
                            "red_stained_glass_pane",
                            "green_stained_glass_pane",
                            "brown_stained_glass_pane",
                            "blue_stained_glass_pane",
                            "purple_stained_glass_pane",
                            "cyan_stained_glass_pane",
                            "silver_stained_glass_pane",
                            "gray_stained_glass_pane",
                            "pink_stained_glass_pane",
                            "lime_stained_glass_pane",
                            "yellow_stained_glass_pane",
                            "light_blue_stained_glass_pane",
                            "magenta_stained_glass_pane",
                            "orange_stained_glass_pane",
                            "white_stained_glass_pane"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.leaves2),
                Lists.newArrayList(new String[] {"acacia_leaves", "dark_oak_leaves"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.log2),
                Lists.newArrayList(new String[] {"acacia_log", "dark_oak_log"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.prismarine),
                Lists.newArrayList(
                        new String[] {"prismarine", "prismarine_bricks", "dark_prismarine"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.carpet),
                Lists.newArrayList(
                        new String[] {
                            "black_carpet",
                            "red_carpet",
                            "green_carpet",
                            "brown_carpet",
                            "blue_carpet",
                            "purple_carpet",
                            "cyan_carpet",
                            "silver_carpet",
                            "gray_carpet",
                            "pink_carpet",
                            "lime_carpet",
                            "yellow_carpet",
                            "light_blue_carpet",
                            "magenta_carpet",
                            "orange_carpet",
                            "white_carpet"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.double_plant),
                Lists.newArrayList(
                        new String[] {
                            "sunflower",
                            "syringa",
                            "double_grass",
                            "double_fern",
                            "double_rose",
                            "paeonia"
                        }));
        this.variantNames.put(
                Items.bow,
                Lists.newArrayList(
                        new String[] {"bow", "bow_pulling_0", "bow_pulling_1", "bow_pulling_2"}));
        this.variantNames.put(Items.coal, Lists.newArrayList(new String[] {"coal", "charcoal"}));
        this.variantNames.put(
                Items.fishing_rod,
                Lists.newArrayList(new String[] {"fishing_rod", "fishing_rod_cast"}));
        this.variantNames.put(
                Items.fish,
                Lists.newArrayList(new String[] {"cod", "salmon", "clownfish", "pufferfish"}));
        this.variantNames.put(
                Items.cooked_fish,
                Lists.newArrayList(new String[] {"cooked_cod", "cooked_salmon"}));
        this.variantNames.put(
                Items.dye,
                Lists.newArrayList(
                        new String[] {
                            "dye_black",
                            "dye_red",
                            "dye_green",
                            "dye_brown",
                            "dye_blue",
                            "dye_purple",
                            "dye_cyan",
                            "dye_silver",
                            "dye_gray",
                            "dye_pink",
                            "dye_lime",
                            "dye_yellow",
                            "dye_light_blue",
                            "dye_magenta",
                            "dye_orange",
                            "dye_white"
                        }));
        this.variantNames.put(
                Items.potionitem,
                Lists.newArrayList(new String[] {"bottle_drinkable", "bottle_splash"}));
        this.variantNames.put(
                Items.skull,
                Lists.newArrayList(
                        new String[] {
                            "skull_skeleton",
                            "skull_wither",
                            "skull_zombie",
                            "skull_char",
                            "skull_creeper"
                        }));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.oak_fence_gate),
                Lists.newArrayList(new String[] {"oak_fence_gate"}));
        this.variantNames.put(
                Item.getItemFromBlock(Blocks.oak_fence),
                Lists.newArrayList(new String[] {"oak_fence"}));
        this.variantNames.put(Items.oak_door, Lists.newArrayList(new String[] {"oak_door"}));
    }

    static Map access$000(ModelBakery modelBakery) {
        return modelBakery.sprites;
    }

    private void loadVariantItemModels() {
        this.loadVariants(
                this.blockModelShapes.getBlockStateMapper().putAllStateModelLocations().values());
        this.variants.put(
                net.minecraft.client.resources.model.ModelBakery.MODEL_MISSING,
                new ModelBlockDefinitionVariants(
                        net.minecraft.client.resources.model.ModelBakery.MODEL_MISSING.getVariant(),
                        Lists.newArrayList(
                                new ModelBlockDefinitionVariant[] {
                                    new ModelBlockDefinitionVariant(
                                            new ResourceLocation(
                                                    net.minecraft.client.resources.model.ModelBakery
                                                            .MODEL_MISSING
                                                            .getResourcePath()),
                                            ModelRotation.X0_Y0,
                                            false,
                                            1)
                                })));
        ResourceLocation resourceLocation = new ResourceLocation("item_frame");
        ModelBlockDefinition modelBlockDefinition = this.getModelBlockDefinition(resourceLocation);
        this.registerVariant(
                modelBlockDefinition, new ModelResourceLocation(resourceLocation, "normal"));
        this.registerVariant(
                modelBlockDefinition, new ModelResourceLocation(resourceLocation, "map"));
        this.loadVariantModels();
        this.loadItemModels();
    }

    private void fZ() {
        int j = ModelResourceLocation.cR();
        ArrayDeque arraydeque = Queues.newArrayDeque();
        int i = j;
        HashSet hashset = Sets.newHashSet();

        for (java.util.Iterator<?> __recoveryIterator171_18245 = (this.models.keySet()).iterator();
                (__recoveryIterator171_18245).hasNext(); ) {
            ResourceLocation resourceLocation =
                    (ResourceLocation) (__recoveryIterator171_18245).next();
            hashset.add(resourceLocation);
            ResourceLocation parentLocation =
                    ((ModelBlock) this.models.get(resourceLocation)).getParentLocation();
            if (parentLocation != null) {
                arraydeque.add(parentLocation);
            }

            if (i == 0) {
                break;
            }
        }

        while (!arraydeque.isEmpty()) {
            ResourceLocation resourceLocationValue = (ResourceLocation) arraydeque.pop();

            label39:
            {
                {
                    boolean runGuardedCode;
                    try {
                        runGuardedCode = !(this.models.get(resourceLocationValue) == null);
                    } catch (Exception exception1) {
                        net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                                "In parent chain: "
                                        + net.minecraft.client.resources.model.ModelBakery.JOINER
                                                .join(this.getParentPath(resourceLocationValue))
                                        + "; unable to load model: '"
                                        + resourceLocationValue
                                        + packedTextFragments[3],
                                exception1);
                        break label39;
                    }
                    if (runGuardedCode) {

                        if (i != 0) {
                            continue;
                        }
                    }
                }

                try {
                    ModelBlock modelBlock = this.method_bd943e6565788e685ef1(resourceLocationValue);
                    this.models.put(resourceLocationValue, modelBlock);
                    ResourceLocation parentLocationValue = modelBlock.getParentLocation();
                    if (parentLocationValue != null && !hashset.contains(parentLocationValue)) {
                        arraydeque.add(parentLocationValue);
                    }
                } catch (Exception exception) {
                    net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                            "In parent chain: "
                                    + net.minecraft.client.resources.model.ModelBakery.JOINER.join(
                                            this.getParentPath(resourceLocationValue))
                                    + "; unable to load model: '"
                                    + resourceLocationValue
                                    + packedTextFragments[3],
                            exception);
                }
            }

            hashset.add(resourceLocationValue);
            if (i == 0) {
                break;
            }
        }
    }

    private boolean isCustomRenderer(ModelBlock modelBlock) {
        if (modelBlock == null) {
            return false;
        }

        ModelBlock rootModel = modelBlock.getRootModel();
        return rootModel == net.minecraft.client.resources.model.ModelBakery.MODEL_ENTITY;
    }

    private ModelBlock method_bd943e6565788e685ef1(ResourceLocation resourceLocation)
            throws IOException {
        int j = ModelResourceLocation.cR();
        String text = resourceLocation.getResourcePath();
        int i = j;
        if ("builtin/generated".equals(text)) {
            return net.minecraft.client.resources.model.ModelBakery.MODEL_GENERATED;
        }

        if ("builtin/compass".equals(text)) {
            return net.minecraft.client.resources.model.ModelBakery.MODEL_COMPASS;
        }

        if ("builtin/clock".equals(text)) {
            return net.minecraft.client.resources.model.ModelBakery.MODEL_CLOCK;
        }

        if ("builtin/entity".equals(text)) {
            return net.minecraft.client.resources.model.ModelBakery.MODEL_ENTITY;
        }

        Reader reader;
        {
            if (text.startsWith("builtin/")) {
                String textValue = text.substring("builtin/".length());
                String textValue2 =
                        (String)
                                net.minecraft.client.resources.model.ModelBakery.BUILT_IN_MODELS
                                        .get(textValue);
                if (textValue2 == null) {
                    throw new FileNotFoundException(resourceLocation.toString());
                }

                reader = new StringReader(textValue2);
                if (!(i != 0)) {

                    IResource resource =
                            this.resourceManager.getResource(
                                    this.getModelLocation(resourceLocation));
                    reader = new InputStreamReader(resource.getInputStream(), Charsets.UTF_8);
                }
            } else {

                IResource resource =
                        this.resourceManager.getResource(this.getModelLocation(resourceLocation));
                reader = new InputStreamReader(resource.getInputStream(), Charsets.UTF_8);
            }
        }

        ModelBlock modelBlock;
        try {
            ModelBlock modelBlockValue = ModelBlock.deserialize(reader);
            modelBlockValue.name = resourceLocation.toString();
            modelBlock = modelBlockValue;
        } finally {
            reader.close();
        }

        return modelBlock;
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    private Set getVariantsTextureLocations() {
        int j = ModelResourceLocation.aW();
        HashSet hashset = Sets.newHashSet();
        ArrayList arrayList = Lists.newArrayList(this.variants.keySet());
        Collections.sort(arrayList, new GetVariantsTextureLocationsComparator(this));
        Iterator iterator = arrayList.iterator();
        int i = j;

        label30:
        while (iterator.hasNext()) {
            ModelResourceLocation modelResourceLocation = (ModelResourceLocation) iterator.next();
            ModelBlockDefinitionVariants modelBlockDefinitionVariants =
                    (ModelBlockDefinitionVariants) this.variants.get(modelResourceLocation);
            Iterator iteratorValue = modelBlockDefinitionVariants.getVariants().iterator();

            while (true) {
                if (iteratorValue.hasNext()) {
                    {
                        ModelBlockDefinitionVariant modelBlockDefinitionVariant =
                                (ModelBlockDefinitionVariant) iteratorValue.next();
                        ModelBlock modelBlock =
                                (ModelBlock)
                                        this.models.get(
                                                modelBlockDefinitionVariant.getModelLocation());
                        if (modelBlock == null) {
                            net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                                    "Missing model for: " + modelResourceLocation);
                            if (!(i == 0)) {

                                hashset.addAll(this.getTextureLocations(modelBlock));
                            }
                        } else {

                            hashset.addAll(this.getTextureLocations(modelBlock));
                        }
                    }

                    if (i == 0) {
                        continue;
                    }
                }

                if (i != 0) {
                    break label30;
                }
                break;
            }
        }

        hashset.addAll(net.minecraft.client.resources.model.ModelBakery.LOCATIONS_BUILTIN_TEXTURES);
        return hashset;
    }

    public IRegistry setupModelRegistry() {
        this.loadVariantItemModels();
        this.loadModelsCheck();
        this.loadSprites();
        this.bakeItemModels();
        this.bakeBlockModels();
        return this.bakedRegistry;
    }

    private void registerVariant(
            ModelBlockDefinition modelBlockDefinition,
            ModelResourceLocation modelResourceLocation) {
        this.variants.put(
                modelResourceLocation,
                modelBlockDefinition.getVariants(modelResourceLocation.getVariant()));
    }

    private IBakedModel bakeModel(
            ModelBlock modelBlock, ModelRotation modelRotation, boolean flag) {
        int j = ModelResourceLocation.cR();
        TextureAtlasSprite textureAtlasSprite =
                (TextureAtlasSprite)
                        this.sprites.get(
                                new ResourceLocation(modelBlock.resolveTextureName("particle")));
        SimpleBakedModelBuilder simpleBakedModelBuilder =
                new SimpleBakedModelBuilder(modelBlock).setTexture(textureAtlasSprite);
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_26030 =
                        (modelBlock.getElements()).iterator();
                (__recoveryIterator171_26030).hasNext(); ) {
            BlockPart blockPart = (BlockPart) (__recoveryIterator171_26030).next();
            Iterator iterator = blockPart.mapFaces.keySet().iterator();

            while (true) {
                if (iterator.hasNext()) {
                    label24:
                    {
                        Facing facing = (Facing) iterator.next();
                        BlockPartFace blockPartFace =
                                (BlockPartFace) blockPart.mapFaces.get(facing);
                        TextureAtlasSprite textureAtlasSpriteValue =
                                (TextureAtlasSprite)
                                        this.sprites.get(
                                                new ResourceLocation(
                                                        modelBlock.resolveTextureName(
                                                                blockPartFace.texture)));
                        if (blockPartFace.cullFace == null) {
                            simpleBakedModelBuilder.addGeneralQuad(
                                    this.makeBakedQuad(
                                            blockPart,
                                            blockPartFace,
                                            textureAtlasSpriteValue,
                                            facing,
                                            modelRotation,
                                            flag));
                            if (i != 0) {
                                break label24;
                            }
                        }

                        simpleBakedModelBuilder.addFaceQuad(
                                modelRotation.rotateFace(blockPartFace.cullFace),
                                this.makeBakedQuad(
                                        blockPart,
                                        blockPartFace,
                                        textureAtlasSpriteValue,
                                        facing,
                                        modelRotation,
                                        flag));
                    }

                    if (i != 0) {
                        continue;
                    }
                }

                if (i == 0) {
                    return simpleBakedModelBuilder.makeBakedModel();
                }
                break;
            }
        }

        return simpleBakedModelBuilder.makeBakedModel();
    }

    private void loadModelsCheck() {
        int j = ModelResourceLocation.aW();
        this.fZ();
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_28596 = (this.models.values()).iterator();
                (__recoveryIterator171_28596).hasNext(); ) {
            ModelBlock modelBlock = (ModelBlock) (__recoveryIterator171_28596).next();
            modelBlock.getParentFromMap(this.models);
            if (i != 0) {
                break;
            }
        }

        ModelBlock.checkModelHierarchy(this.models);
    }

    private void loadVariants(Collection collection) {
        int j = ModelResourceLocation.cR();
        Iterator iterator = collection.iterator();
        int i = j;

        while (iterator.hasNext()) {
            ModelResourceLocation modelResourceLocation = (ModelResourceLocation) iterator.next();

            try {
                ModelBlockDefinition modelBlockDefinition =
                        this.getModelBlockDefinition(modelResourceLocation);

                try {
                    this.registerVariant(modelBlockDefinition, modelResourceLocation);
                } catch (Exception exception) {
                    net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                            "Unable to load variant: "
                                    + modelResourceLocation.getVariant()
                                    + " from "
                                    + modelResourceLocation);
                }
            } catch (Exception exception1) {
                net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                        "Unable to load definition " + modelResourceLocation, exception1);
            }

            if (i == 0) {
                break;
            }
        }
    }

    private BakedQuad makeBakedQuad(
            BlockPart blockPart,
            BlockPartFace blockPartFace,
            TextureAtlasSprite textureAtlasSprite,
            Facing facing,
            ModelRotation modelRotation,
            boolean flag) {
        return this.faceBakery.makeBakedQuad(
                blockPart.positionFrom,
                blockPart.positionTo,
                blockPartFace,
                textureAtlasSprite,
                facing,
                modelRotation,
                blockPart.partRotation,
                flag,
                blockPart.shade);
    }

    static {
        net.minecraft.client.resources.model.ModelBakery.packedTextFragments = new String[5];
        net.minecraft.client.resources.model.ModelBakery.packedTextFragments[0] = new String("'");
        net.minecraft.client.resources.model.ModelBakery.packedTextFragments[1] = new String("'");
        net.minecraft.client.resources.model.ModelBakery.packedTextFragments[2] = new String("'");
        net.minecraft.client.resources.model.ModelBakery.packedTextFragments[3] = new String("'");
        net.minecraft.client.resources.model.ModelBakery.LOCATIONS_BUILTIN_TEXTURES =
                Sets.newHashSet(
                        new ResourceLocation[] {
                            new ResourceLocation("blocks/water_flow"),
                            new ResourceLocation("blocks/water_still"),
                            new ResourceLocation("blocks/lava_flow"),
                            new ResourceLocation("blocks/lava_still"),
                            new ResourceLocation("blocks/destroy_stage_0"),
                            new ResourceLocation("blocks/destroy_stage_1"),
                            new ResourceLocation("blocks/destroy_stage_2"),
                            new ResourceLocation("blocks/destroy_stage_3"),
                            new ResourceLocation("blocks/destroy_stage_4"),
                            new ResourceLocation("blocks/destroy_stage_5"),
                            new ResourceLocation("blocks/destroy_stage_6"),
                            new ResourceLocation("blocks/destroy_stage_7"),
                            new ResourceLocation("blocks/destroy_stage_8"),
                            new ResourceLocation("blocks/destroy_stage_9"),
                            new ResourceLocation("items/empty_armor_slot_helmet"),
                            new ResourceLocation("items/empty_armor_slot_chestplate"),
                            new ResourceLocation("items/empty_armor_slot_leggings"),
                            new ResourceLocation("items/empty_armor_slot_boots")
                        });
        net.minecraft.client.resources.model.ModelBakery.LOGGER = LogManager.getLogger();
        net.minecraft.client.resources.model.ModelBakery.MODEL_MISSING =
                new ModelResourceLocation("builtin/missing", "missing");
        net.minecraft.client.resources.model.ModelBakery.BUILT_IN_MODELS = Maps.newHashMap();
        net.minecraft.client.resources.model.ModelBakery.BUILT_IN_MODELS.put(
                "missing",
                "{ \"textures\": {   \"particle\": \"missingno\",   \"missingno\": \"missingno\"},"
                    + " \"elements\": [ {     \"from\": [ 0, 0, 0 ],     \"to\": [ 16, 16, 16 ],   "
                    + "  \"faces\": {         \"down\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\":"
                    + " \"down\", \"texture\": \"#missingno\" },         \"up\":    { \"uv\": [ 0,"
                    + " 0, 16, 16 ], \"cullface\": \"up\", \"texture\": \"#missingno\" },        "
                    + " \"north\": { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"north\","
                    + " \"texture\": \"#missingno\" },         \"south\": { \"uv\": [ 0, 0, 16, 16"
                    + " ], \"cullface\": \"south\", \"texture\": \"#missingno\" },        "
                    + " \"west\":  { \"uv\": [ 0, 0, 16, 16 ], \"cullface\": \"west\", \"texture\":"
                    + " \"#missingno\" },         \"east\":  { \"uv\": [ 0, 0, 16, 16 ],"
                    + " \"cullface\": \"east\", \"texture\": \"#missingno\" }    }}]}");
        net.minecraft.client.resources.model.ModelBakery.JOINER = Joiner.on(" -> ");
        net.minecraft.client.resources.model.ModelBakery.MODEL_GENERATED =
                ModelBlock.deserialize(
                        "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],  "
                                + " \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16],"
                                + " \"texture\":\"\"}   }}]}");
        net.minecraft.client.resources.model.ModelBakery.MODEL_COMPASS =
                ModelBlock.deserialize(
                        "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],  "
                                + " \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16],"
                                + " \"texture\":\"\"}   }}]}");
        net.minecraft.client.resources.model.ModelBakery.MODEL_CLOCK =
                ModelBlock.deserialize(
                        "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],  "
                                + " \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16],"
                                + " \"texture\":\"\"}   }}]}");
        net.minecraft.client.resources.model.ModelBakery.MODEL_ENTITY =
                ModelBlock.deserialize(
                        "{\"elements\":[{  \"from\": [0, 0, 0],   \"to\": [16, 16, 16],  "
                                + " \"faces\": {       \"down\": {\"uv\": [0, 0, 16, 16],"
                                + " \"texture\":\"\"}   }}]}");
        net.minecraft.client.resources.model.ModelBakery.MODEL_GENERATED.name = "generation marker";
        net.minecraft.client.resources.model.ModelBakery.MODEL_COMPASS.name =
                "compass generation marker";
        net.minecraft.client.resources.model.ModelBakery.MODEL_CLOCK.name =
                "class generation marker";
        net.minecraft.client.resources.model.ModelBakery.MODEL_ENTITY.name = "block entity marker";
    }

    private Set getTextureLocations(ModelBlock modelBlock) {
        HashSet hashset = Sets.newHashSet();
        int j = ModelResourceLocation.aW();
        Iterator iterator = modelBlock.getElements().iterator();
        int i = j;

        label21:
        while (iterator.hasNext()) {
            BlockPart blockPart = (BlockPart) iterator.next();
            Iterator iteratorValue = blockPart.mapFaces.values().iterator();

            while (true) {
                if (iteratorValue.hasNext()) {
                    BlockPartFace blockPartFace = (BlockPartFace) iteratorValue.next();
                    ResourceLocation resourceLocation =
                            new ResourceLocation(
                                    modelBlock.resolveTextureName(blockPartFace.texture));
                    hashset.add(resourceLocation);
                    if (i == 0) {
                        continue;
                    }
                }

                if (i != 0) {
                    break label21;
                }
                break;
            }
        }

        hashset.add(new ResourceLocation(modelBlock.resolveTextureName("particle")));
        return hashset;
    }

    private Set getItemsTextureLocations() {
        int j = ModelResourceLocation.cR();
        HashSet hashset = Sets.newHashSet();
        Iterator iterator = this.itemLocations.values().iterator();
        int i = j;

        while (iterator.hasNext()) {
            ResourceLocation resourceLocation = (ResourceLocation) iterator.next();
            ModelBlock modelBlock = (ModelBlock) this.models.get(resourceLocation);
            label66:
            if (modelBlock != null) {
                hashset.add(new ResourceLocation(modelBlock.resolveTextureName("particle")));
                if (this.hasItemModel(modelBlock)) {
                    Iterator iteratorValue = ItemModelGenerator.LAYERS.iterator();

                    do {
                        if (!iteratorValue.hasNext()) {
                            break label66;
                        }

                        ResourceLocation resourceLocationValue;
                        label59:
                        {
                            String text = (String) iteratorValue.next();
                            resourceLocationValue =
                                    new ResourceLocation(modelBlock.resolveTextureName(text));
                            if (modelBlock.getRootModel()
                                            == net.minecraft.client.resources.model.ModelBakery
                                                    .MODEL_COMPASS
                                    && !TextureMap.LOCATION_MISSING_TEXTURE.equals(
                                            resourceLocationValue)) {
                                TextureAtlasSprite.setLocationNameCompass(
                                        resourceLocationValue.toString());
                                if (i != 0) {
                                    break label59;
                                }
                            }

                            if (modelBlock.getRootModel()
                                            == net.minecraft.client.resources.model.ModelBakery
                                                    .MODEL_CLOCK
                                    && !TextureMap.LOCATION_MISSING_TEXTURE.equals(
                                            resourceLocationValue)) {
                                TextureAtlasSprite.setLocationNameClock(
                                        resourceLocationValue.toString());
                            }
                        }

                        hashset.add(resourceLocationValue);
                    } while (i != 0);
                }

                if (!this.isCustomRenderer(modelBlock)) {
                    for (java.util.Iterator<?> __recoveryIterator171_46732 =
                                    (modelBlock.getElements()).iterator();
                            (__recoveryIterator171_46732).hasNext(); ) {
                        BlockPart blockPart = (BlockPart) (__recoveryIterator171_46732).next();
                        for (java.util.Iterator<?> __recoveryIterator171_46846 =
                                        (blockPart.mapFaces.values()).iterator();
                                (__recoveryIterator171_46846).hasNext(); ) {
                            BlockPartFace blockPartFace =
                                    (BlockPartFace) (__recoveryIterator171_46846).next();
                            ResourceLocation resourceLocationValue2 =
                                    new ResourceLocation(
                                            modelBlock.resolveTextureName(blockPartFace.texture));
                            hashset.add(resourceLocationValue2);
                            if (i == 0) {
                                break;
                            }
                        }

                        if (i == 0) {
                            break;
                        }
                    }
                }
            }

            if (i == 0) {
                break;
            }
        }

        return hashset;
    }

    private void loadSprites() {
        Set set = this.getVariantsTextureLocations();
        set.addAll(this.getItemsTextureLocations());
        set.remove(TextureMap.LOCATION_MISSING_TEXTURE);
        ModelTextureAtlasIconCreator modelTextureAtlasIconCreator =
                new ModelTextureAtlasIconCreator(this, set);
        this.textureMap.loadSprites(this.resourceManager, modelTextureAtlasIconCreator);
        this.sprites.put(new ResourceLocation("missingno"), this.textureMap.getMissingSprite());
    }

    private ResourceLocation getBlockStateLocation(ResourceLocation resourceLocation) {
        return new ResourceLocation(
                resourceLocation.getResourceDomain(),
                "blockstates/" + resourceLocation.getResourcePath() + ".json");
    }

    private List getVariantNames(Item item) {
        List list = (List) this.variantNames.get(item);
        if (list == null) {
            list =
                    Collections.singletonList(
                            ((ResourceLocation) Item.itemRegistry.getNameForObject(item))
                                    .toString());
        }

        return list;
    }

    private ModelBlockDefinition getModelBlockDefinition(ResourceLocation resourceLocation) {
        ResourceLocation resourceLocationValue = this.getBlockStateLocation(resourceLocation);
        int j = ModelResourceLocation.cR();
        ModelBlockDefinition modelBlockDefinition =
                (ModelBlockDefinition) this.blockDefinitions.get(resourceLocationValue);
        int i = j;
        if (modelBlockDefinition == null) {
            ArrayList arrayList = Lists.newArrayList();

            try {
                for (java.util.Iterator<?> __recoveryIterator171_50740 =
                                (this.resourceManager.getAllResources(resourceLocationValue))
                                        .iterator();
                        (__recoveryIterator171_50740).hasNext(); ) {
                    IResource iResource = (IResource) (__recoveryIterator171_50740).next();
                    InputStream inputstream = null;

                    try {
                        inputstream = iResource.getInputStream();
                        ModelBlockDefinition modelBlockDefinitionValue =
                                ModelBlockDefinition.parseFromReader(
                                        new InputStreamReader(inputstream, Charsets.UTF_8));
                        arrayList.add(modelBlockDefinitionValue);
                    } catch (Exception exception) {
                        throw new RuntimeException(
                                "Encountered an exception when loading model definition of '"
                                        + resourceLocation
                                        + "' from: '"
                                        + iResource.getResourceLocation()
                                        + "' in resourcepack: '"
                                        + iResource.getResourcePackName()
                                        + packedTextFragments[0],
                                exception);
                    } finally {
                        IOUtils.closeQuietly(inputstream);
                    }

                    if (i == 0) {
                        break;
                    }
                }
            } catch (IOException ioexception) {
                throw new RuntimeException(
                        "Encountered an exception when loading model definition of model "
                                + resourceLocationValue.toString(),
                        ioexception);
            }

            modelBlockDefinition = new ModelBlockDefinition(arrayList);
            this.blockDefinitions.put(resourceLocationValue, modelBlockDefinition);
        }

        return modelBlockDefinition;
    }

    public ModelBakery(
            IResourceManager iResourceManager,
            TextureMap textureMapValue,
            BlockModelShapes blockModelShapesValue) {
        super();
        int initialFlowToken = ModelResourceLocation.cR();

        int j = initialFlowToken;

        this.sprites = Maps.newHashMap();
        this.models = Maps.newLinkedHashMap();
        this.variants = Maps.newLinkedHashMap();
        this.faceBakery = new FaceBakery();
        this.itemModelGenerator = new ItemModelGenerator();
        this.bakedRegistry = new RegistrySimple();
        this.itemLocations = Maps.newLinkedHashMap();
        int i = j;
        this.blockDefinitions = Maps.newHashMap();
        this.variantNames = Maps.newIdentityHashMap();
        this.resourceManager = iResourceManager;
        this.textureMap = textureMapValue;
        this.blockModelShapes = blockModelShapesValue;
        if (i == 0) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    private void loadItemModels() {
        int j = ModelResourceLocation.cR();
        this.registerVariantNames();
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_53968 = (Item.itemRegistry).iterator();
                (__recoveryIterator171_53968).hasNext(); ) {
            Item item = (Item) (__recoveryIterator171_53968).next();
            List variantNamesValue = this.getVariantNames(item);
            Iterator iterator = variantNamesValue.iterator();

            while (true) {
                if (iterator.hasNext()) {
                    String text = (String) iterator.next();
                    ResourceLocation resourceLocation = this.getItemLocation(text);
                    this.itemLocations.put(text, resourceLocation);
                    if (this.models.get(resourceLocation) == null) {
                        try {
                            ModelBlock modelBlock =
                                    this.method_bd943e6565788e685ef1(resourceLocation);
                            this.models.put(resourceLocation, modelBlock);
                        } catch (Exception exception) {
                            net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                                    "Unable to load item model: '"
                                            + resourceLocation
                                            + "' for item: '"
                                            + Item.itemRegistry.getNameForObject(item)
                                            + packedTextFragments[2],
                                    exception);
                        }
                    }

                    if (i != 0) {
                        continue;
                    }
                }

                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }

    private boolean hasItemModel(ModelBlock modelBlock) {
        if (modelBlock == null) {
            return false;
        }

        ModelBlock rootModel = modelBlock.getRootModel();
        return rootModel == net.minecraft.client.resources.model.ModelBakery.MODEL_GENERATED
                || rootModel == net.minecraft.client.resources.model.ModelBakery.MODEL_COMPASS
                || rootModel == net.minecraft.client.resources.model.ModelBakery.MODEL_CLOCK;
    }

    private void bakeItemModels() {
        int j = ModelResourceLocation.cR();
        Iterator iterator = this.itemLocations.values().iterator();
        int i = j;

        while (iterator.hasNext()) {
            {
                ResourceLocation resourceLocation = (ResourceLocation) iterator.next();
                ModelBlock modelBlock = (ModelBlock) this.models.get(resourceLocation);
                if (this.hasItemModel(modelBlock)) {
                    ModelBlock modelBlockValue = this.makeItemModel(modelBlock);
                    if (modelBlockValue != null) {
                        modelBlockValue.name = resourceLocation.toString();
                    }

                    this.models.put(resourceLocation, modelBlockValue);
                    if (!(i != 0)) {

                        if (this.isCustomRenderer(modelBlock)) {
                            this.models.put(resourceLocation, modelBlock);
                        }
                    }
                } else {

                    if (this.isCustomRenderer(modelBlock)) {
                        this.models.put(resourceLocation, modelBlock);
                    }
                }
            }

            if (i == 0) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_58582 = (this.sprites.values()).iterator();
                (__recoveryIterator171_58582).hasNext(); ) {
            TextureAtlasSprite textureAtlasSprite =
                    (TextureAtlasSprite) (__recoveryIterator171_58582).next();
            if (!textureAtlasSprite.dL()) {
                textureAtlasSprite.iJ();
            }

            if (i == 0) {
                break;
            }
        }
    }

    private ModelBlock makeItemModel(ModelBlock modelBlock) {
        return this.itemModelGenerator.makeItemModel(this.textureMap, modelBlock);
    }

    private ResourceLocation getItemLocation(String text) {
        ResourceLocation resourceLocation = new ResourceLocation(text);
        return new ResourceLocation(
                resourceLocation.getResourceDomain(), "item/" + resourceLocation.getResourcePath());
    }

    private List getParentPath(ResourceLocation resourceLocation) {
        int j = ModelResourceLocation.cR();
        ArrayList arrayList = Lists.newArrayList(new ResourceLocation[] {resourceLocation});
        ResourceLocation resourceLocationValue = resourceLocation;
        int i = j;

        while ((resourceLocationValue = this.getParentLocation(resourceLocationValue)) != null) {
            arrayList.add(0, resourceLocationValue);
            if (i == 0) {
                break;
            }
        }

        return arrayList;
    }

    private void bakeBlockModels() {
        int k = ModelResourceLocation.aW();
        Iterator iterator = this.variants.keySet().iterator();
        int i = k;

        while (iterator.hasNext()) {
            ModelResourceLocation modelResourceLocation = (ModelResourceLocation) iterator.next();
            WeightedBakedModelBuilder weightedBakedModelBuilder = new WeightedBakedModelBuilder();
            int j = 0;

            for (java.util.Iterator<?> __recoveryIterator171_60620 =
                            (((ModelBlockDefinitionVariants)
                                                    this.variants.get(modelResourceLocation))
                                            .getVariants())
                                    .iterator();
                    (__recoveryIterator171_60620).hasNext(); ) {
                ModelBlockDefinitionVariant modelBlockDefinitionVariant =
                        (ModelBlockDefinitionVariant) (__recoveryIterator171_60620).next();
                {
                    ModelBlock modelBlock =
                            (ModelBlock)
                                    this.models.get(modelBlockDefinitionVariant.getModelLocation());
                    if (modelBlock != null && modelBlock.isResolved()) {
                        j++;
                        weightedBakedModelBuilder.add(
                                this.bakeModel(
                                        modelBlock,
                                        modelBlockDefinitionVariant.getRotation(),
                                        modelBlockDefinitionVariant.isUvLocked()),
                                modelBlockDefinitionVariant.getWeight());
                        if (!(i == 0)) {

                            net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                                    "Missing model for: " + modelResourceLocation);
                        }
                    } else {

                        net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                                "Missing model for: " + modelResourceLocation);
                    }
                }

                if (i != 0) {
                    break;
                }
            }

            label86:
            {
                if (j == 0) {
                    net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                            "No weighted models for: " + modelResourceLocation);
                    if (i == 0) {
                        break label86;
                    }
                }

                if (j == 1) {
                    this.bakedRegistry.putObject(
                            modelResourceLocation, weightedBakedModelBuilder.first());
                    if (i == 0) {
                        break label86;
                    }
                }

                this.bakedRegistry.putObject(
                        modelResourceLocation, weightedBakedModelBuilder.build());
            }

            if (i != 0) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_62361 =
                        (this.itemLocations.entrySet()).iterator();
                (__recoveryIterator171_62361).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_62361).next();
            label48:
            {
                ResourceLocation value = (ResourceLocation) entry.getValue();
                ModelResourceLocation modelResourceLocationValue =
                        new ModelResourceLocation((String) entry.getKey(), "inventory");
                ModelBlock modelBlockValue = (ModelBlock) this.models.get(value);
                if (modelBlockValue != null && modelBlockValue.isResolved()) {
                    if (this.isCustomRenderer(modelBlockValue)) {
                        this.bakedRegistry.putObject(
                                modelResourceLocationValue,
                                new BuiltInModel(
                                        new ItemCameraTransforms(
                                                modelBlockValue.getThirdPersonTransform(),
                                                modelBlockValue.getFirstPersonTransform(),
                                                modelBlockValue.getHeadTransform(),
                                                modelBlockValue.getInGuiTransform())));
                        if (i == 0) {
                            break label48;
                        }
                    }

                    this.bakedRegistry.putObject(
                            modelResourceLocationValue,
                            this.bakeModel(modelBlockValue, ModelRotation.X0_Y0, false));
                    if (i == 0) {
                        break label48;
                    }
                }

                net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                        "Missing model for: " + value);
            }

            if (i != 0) {
                break;
            }
        }
    }

    private ResourceLocation getModelLocation(ResourceLocation resourceLocation) {
        return new ResourceLocation(
                resourceLocation.getResourceDomain(),
                "models/" + resourceLocation.getResourcePath() + ".json");
    }

    private void loadVariantModels() {
        int j = ModelResourceLocation.cR();
        Iterator iterator = this.variants.keySet().iterator();
        int i = j;

        while (iterator.hasNext()) {
            ModelResourceLocation modelResourceLocation = (ModelResourceLocation) iterator.next();
            Iterator iteratorValue =
                    ((ModelBlockDefinitionVariants) this.variants.get(modelResourceLocation))
                            .getVariants()
                            .iterator();

            while (true) {
                if (iteratorValue.hasNext()) {
                    ModelBlockDefinitionVariant modelBlockDefinitionVariant =
                            (ModelBlockDefinitionVariant) iteratorValue.next();
                    ResourceLocation resourceLocation =
                            modelBlockDefinitionVariant.getModelLocation();
                    if (this.models.get(resourceLocation) == null) {
                        try {
                            ModelBlock modelBlock =
                                    this.method_bd943e6565788e685ef1(resourceLocation);
                            this.models.put(resourceLocation, modelBlock);
                        } catch (Exception exception) {
                            net.minecraft.client.resources.model.ModelBakery.LOGGER.warn(
                                    "Unable to load block model: '"
                                            + resourceLocation
                                            + "' for variant: '"
                                            + modelResourceLocation
                                            + packedTextFragments[1],
                                    exception);
                        }
                    }

                    if (i != 0) {
                        continue;
                    }
                }

                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }

    // Helper used only by this class.
    public static class GetVariantsTextureLocationsComparator implements java.util.Comparator {
        net.minecraft.client.resources.model.ModelBakery aHT;

        public GetVariantsTextureLocationsComparator(
                net.minecraft.client.resources.model.ModelBakery aHTValue) {
            this.aHT = aHTValue;
        }

        @java.lang.Override
        public int compare(java.lang.Object object, java.lang.Object object1) {
            return this.method_4f75ca2b3d01b6410ee5(
                    (net.minecraft.client.resources.model.ModelResourceLocation) object,
                    (net.minecraft.client.resources.model.ModelResourceLocation) object1);
        }

        public int method_4f75ca2b3d01b6410ee5(
                net.minecraft.client.resources.model.ModelResourceLocation modelResourceLocation,
                net.minecraft.client.resources.model.ModelResourceLocation
                        modelResourceLocationValue) {
            return modelResourceLocation
                    .toString()
                    .compareTo(modelResourceLocationValue.toString());
        }
    }
}
