package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockDropper;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.BlockReed;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockWall;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMapBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Map.Entry;

public class BlockModelShapes {

    private final Map bakedModelStore = Maps.newIdentityHashMap();

    private ModelManager modelManager;
    private final BlockStateMapper blockStateMapper = new BlockStateMapper();

    private void registerAllBlocks() {
        this.registerBuiltInBlocks(
                Blocks.air,
                Blocks.flowing_water,
                Blocks.water,
                Blocks.flowing_lava,
                Blocks.lava,
                Blocks.piston_extension,
                Blocks.chest,
                Blocks.ender_chest,
                Blocks.trapped_chest,
                Blocks.standing_sign,
                Blocks.skull,
                Blocks.end_portal,
                Blocks.barrier,
                Blocks.wall_sign,
                Blocks.wall_banner,
                Blocks.standing_banner);
        this.registerBlockWithStateMapper(
                Blocks.stone, new StateMapBuilder().withName(BlockStone.VARIANT_PROP).build());
        this.registerBlockWithStateMapper(
                Blocks.prismarine,
                new StateMapBuilder().withName(BlockPrismarine.VARIANTS).build());
        this.registerBlockWithStateMapper(
                Blocks.leaves,
                new StateMapBuilder()
                        .withName(BlockOldLeaf.VARIANT_PROP)
                        .withSuffix("_leaves")
                        .ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE})
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.leaves2,
                new StateMapBuilder()
                        .withName(BlockNewLeaf.VARIANT)
                        .withSuffix("_leaves")
                        .ignore(new IProperty[] {BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE})
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.cactus,
                new StateMapBuilder().ignore(new IProperty[] {BlockCactus.AGE_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.reeds,
                new StateMapBuilder().ignore(new IProperty[] {BlockReed.AGE}).build());
        this.registerBlockWithStateMapper(
                Blocks.jukebox,
                new StateMapBuilder().ignore(new IProperty[] {BlockJukebox.HAS_RECORD}).build());
        this.registerBlockWithStateMapper(
                Blocks.command_block,
                new StateMapBuilder()
                        .ignore(new IProperty[] {BlockCommandBlock.TRIGGERED_PROP})
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.cobblestone_wall,
                new StateMapBuilder().withName(BlockWall.VARIANT).withSuffix("_wall").build());
        this.registerBlockWithStateMapper(
                Blocks.double_plant,
                new StateMapBuilder().withName(BlockDoublePlant.VARIANT_PROP).build());
        this.registerBlockWithStateMapper(
                Blocks.oak_fence_gate,
                new StateMapBuilder().ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(
                Blocks.spruce_fence_gate,
                new StateMapBuilder().ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(
                Blocks.birch_fence_gate,
                new StateMapBuilder().ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(
                Blocks.jungle_fence_gate,
                new StateMapBuilder().ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(
                Blocks.dark_oak_fence_gate,
                new StateMapBuilder().ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(
                Blocks.acacia_fence_gate,
                new StateMapBuilder().ignore(new IProperty[] {BlockFenceGate.POWERED}).build());
        this.registerBlockWithStateMapper(
                Blocks.tripwire,
                new StateMapBuilder()
                        .ignore(new IProperty[] {BlockTripWire.DISARMED, BlockTripWire.POWERED})
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.double_wooden_slab,
                new StateMapBuilder()
                        .withName(BlockPlanks.VARIANT_PROP)
                        .withSuffix("_double_slab")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.wooden_slab,
                new StateMapBuilder()
                        .withName(BlockPlanks.VARIANT_PROP)
                        .withSuffix("_slab")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.tnt,
                new StateMapBuilder().ignore(new IProperty[] {BlockTNT.EXPLODE}).build());
        this.registerBlockWithStateMapper(
                Blocks.fire, new StateMapBuilder().ignore(new IProperty[] {BlockFire.AGE}).build());
        this.registerBlockWithStateMapper(
                Blocks.redstone_wire,
                new StateMapBuilder().ignore(new IProperty[] {BlockRedstoneWire.POWER}).build());
        this.registerBlockWithStateMapper(
                Blocks.oak_door,
                new StateMapBuilder().ignore(new IProperty[] {BlockDoor.POWERED_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.spruce_door,
                new StateMapBuilder().ignore(new IProperty[] {BlockDoor.POWERED_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.birch_door,
                new StateMapBuilder().ignore(new IProperty[] {BlockDoor.POWERED_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.jungle_door,
                new StateMapBuilder().ignore(new IProperty[] {BlockDoor.POWERED_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.acacia_door,
                new StateMapBuilder().ignore(new IProperty[] {BlockDoor.POWERED_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.dark_oak_door,
                new StateMapBuilder().ignore(new IProperty[] {BlockDoor.POWERED_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.iron_door,
                new StateMapBuilder().ignore(new IProperty[] {BlockDoor.POWERED_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.wool,
                new StateMapBuilder().withName(BlockColored.COLOR).withSuffix("_wool").build());
        this.registerBlockWithStateMapper(
                Blocks.carpet,
                new StateMapBuilder().withName(BlockColored.COLOR).withSuffix("_carpet").build());
        this.registerBlockWithStateMapper(
                Blocks.stained_hardened_clay,
                new StateMapBuilder()
                        .withName(BlockColored.COLOR)
                        .withSuffix("_stained_hardened_clay")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.stained_glass_pane,
                new StateMapBuilder()
                        .withName(BlockColored.COLOR)
                        .withSuffix("_stained_glass_pane")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.stained_glass,
                new StateMapBuilder()
                        .withName(BlockColored.COLOR)
                        .withSuffix("_stained_glass")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.sandstone, new StateMapBuilder().withName(BlockSandStone.TYPE).build());
        this.registerBlockWithStateMapper(
                Blocks.red_sandstone,
                new StateMapBuilder().withName(BlockRedSandstone.TYPE).build());
        this.registerBlockWithStateMapper(
                Blocks.tallgrass, new StateMapBuilder().withName(BlockTallGrass.TYPE).build());
        this.registerBlockWithStateMapper(
                Blocks.bed,
                new StateMapBuilder().ignore(new IProperty[] {BlockBed.OCCUPIED_PROP}).build());
        this.registerBlockWithStateMapper(
                Blocks.yellow_flower,
                new StateMapBuilder().withName(Blocks.yellow_flower.getTypeProperty()).build());
        this.registerBlockWithStateMapper(
                Blocks.red_flower,
                new StateMapBuilder().withName(Blocks.red_flower.getTypeProperty()).build());
        this.registerBlockWithStateMapper(
                Blocks.stone_slab,
                new StateMapBuilder().withName(BlockStoneSlab.VARIANT).withSuffix("_slab").build());
        this.registerBlockWithStateMapper(
                Blocks.stone_slab2,
                new StateMapBuilder()
                        .withName(BlockStoneSlabNew.VARIANT)
                        .withSuffix("_slab")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.monster_egg,
                new StateMapBuilder()
                        .withName(BlockSilverfish.VARIANT_PROP)
                        .withSuffix("_monster_egg")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.stonebrick,
                new StateMapBuilder().withName(BlockStoneBrick.VARIANT_PROP).build());
        this.registerBlockWithStateMapper(
                Blocks.dispenser,
                new StateMapBuilder().ignore(new IProperty[] {BlockDispenser.TRIGGERED}).build());
        this.registerBlockWithStateMapper(
                Blocks.dropper,
                new StateMapBuilder().ignore(new IProperty[] {BlockDropper.TRIGGERED}).build());
        this.registerBlockWithStateMapper(
                Blocks.log,
                new StateMapBuilder()
                        .withName(BlockOldLog.VARIANT_PROP)
                        .withSuffix("_log")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.log2,
                new StateMapBuilder().withName(BlockNewLog.VARIANT).withSuffix("_log").build());
        this.registerBlockWithStateMapper(
                Blocks.planks,
                new StateMapBuilder()
                        .withName(BlockPlanks.VARIANT_PROP)
                        .withSuffix("_planks")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.sapling,
                new StateMapBuilder()
                        .withName(BlockSapling.TYPE_PROP)
                        .withSuffix("_sapling")
                        .build());
        this.registerBlockWithStateMapper(
                Blocks.sand, new StateMapBuilder().withName(BlockSand.VARIANT_PROP).build());
        this.registerBlockWithStateMapper(
                Blocks.hopper,
                new StateMapBuilder().ignore(new IProperty[] {BlockHopper.ENABLED}).build());
        this.registerBlockWithStateMapper(
                Blocks.flower_pot,
                new StateMapBuilder().ignore(new IProperty[] {BlockFlowerPot.LEGACY_DATA}).build());
        this.registerBlockWithStateMapper(Blocks.quartz_block, new QuartzBlockStateMapper(this));
        this.registerBlockWithStateMapper(Blocks.deadbush, new DeadBushStateMapper(this));
        this.registerBlockWithStateMapper(Blocks.pumpkin_stem, new PumpkinStemStateMapper(this));
        this.registerBlockWithStateMapper(Blocks.melon_stem, new MelonStemStateMapper(this));
        this.registerBlockWithStateMapper(Blocks.dirt, new DirtBlockStateMapper(this));
        this.registerBlockWithStateMapper(
                Blocks.double_stone_slab, new StoneDoubleSlabStateMapper(this));
        this.registerBlockWithStateMapper(
                Blocks.double_stone_slab2, new RedSandstoneDoubleSlabStateMapper(this));
    }

    public ModelManager getModelManager() {
        return this.modelManager;
    }

    public void reloadModels() {
        int j = WorldRenderer.getLegacyRenderFlowToken();
        this.bakedModelStore.clear();
        int i = j;

        for (java.util.Iterator<?> __recoveryIterator171_15641 =
                        (this.blockStateMapper.putAllStateModelLocations().entrySet()).iterator();
                (__recoveryIterator171_15641).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_15641).next();
            this.bakedModelStore.put(
                    entry.getKey(),
                    this.modelManager.getModel((ModelResourceLocation) entry.getValue()));
            if (i != 0) {
                break;
            }
        }
    }

    public void registerBlockWithStateMapper(Block block, IStateMapper iStateMapper) {
        this.blockStateMapper.registerBlockStateMapper(block, iStateMapper);
    }

    public BlockModelShapes(ModelManager ayKValue) {
        this.modelManager = ayKValue;
        this.registerAllBlocks();
    }

    public IBakedModel getModelForState(BlockState blockState) {
        IBakedModel iBakedModel = (IBakedModel) this.bakedModelStore.get(blockState);
        if (iBakedModel == null) {
            iBakedModel = this.modelManager.getMissingModel();
        }

        return iBakedModel;
    }

    public void registerBuiltInBlocks(Block... blockArray) {
        this.blockStateMapper.registerBuiltInBlocks(blockArray);
    }

    public BlockStateMapper getBlockStateMapper() {
        return this.blockStateMapper;
    }

    public TextureAtlasSprite getTexture(BlockState blockState) {
        Block block = blockState.getBlock();
        IBakedModel iBakedModel = this.getModelForState(blockState);
        if (iBakedModel == null || iBakedModel == this.modelManager.getMissingModel()) {
            if (block == Blocks.wall_sign
                    || block == Blocks.standing_sign
                    || block == Blocks.chest
                    || block == Blocks.trapped_chest
                    || block == Blocks.standing_banner
                    || block == Blocks.wall_banner) {
                return this.modelManager
                        .getTextureMap()
                        .getAtlasSprite("minecraft:blocks/planks_oak");
            }

            if (block == Blocks.ender_chest) {
                return this.modelManager
                        .getTextureMap()
                        .getAtlasSprite("minecraft:blocks/obsidian");
            }

            if (block == Blocks.flowing_lava || block == Blocks.lava) {
                return this.modelManager
                        .getTextureMap()
                        .getAtlasSprite("minecraft:blocks/lava_still");
            }

            if (block == Blocks.flowing_water || block == Blocks.water) {
                return this.modelManager
                        .getTextureMap()
                        .getAtlasSprite("minecraft:blocks/water_still");
            }

            if (block == Blocks.skull) {
                return this.modelManager
                        .getTextureMap()
                        .getAtlasSprite("minecraft:blocks/soul_sand");
            }

            if (block == Blocks.barrier) {
                return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/barrier");
            }
        }

        if (iBakedModel == null) {
            iBakedModel = this.modelManager.getMissingModel();
        }

        return iBakedModel.getTexture();
    }


}
