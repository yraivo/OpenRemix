package net.minecraft.block;

import com.google.common.collect.UnmodifiableIterator;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.ObjectIntIdentityMap;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.RegistryNamespacedDefaultedByKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.event.impl.BlockCollisionEvent;
import remix.module.exploit.GhostHandModule;
import remix.module.player.ChestAuraModule;

import java.util.List;
import java.util.Random;

public class Block {
    private static String opaqueControlState;
    private static ResourceLocation AIR_ID;
    protected int lightOpacity;
    public static BlockSoundType soundTypeMetal;
    public static BlockSoundType SLIME_SOUND;
    protected boolean needsRandomTick;
    public static BlockSoundType soundTypePiston;
    public static BlockSoundType soundTypeStone;
    private String unlocalizedName;
    protected double minZ;
    public static BlockSoundType soundTypeSnow;
    public static RegistryNamespacedDefaultedByKey blockRegistry;
    protected double maxZ;
    public float blockParticleGravity;
    public static BlockSoundType soundTypeGlass;
    protected boolean fullBlock;
    protected double maxY;
    protected double minX;
    private BlockState defaultBlockState;
    public BlockSoundType stepSound;

    public static BlockSoundType soundTypeSand;
    protected boolean useNeighborBrightness;
    public static BlockSoundType soundTypeGrass;
    protected double maxX;
    public static ObjectIntIdentityMap BLOCK_STATE_IDS;
    public static BlockSoundType soundTypeCloth;

    protected float blockResistance;
    public float slipperiness;
    public static BlockSoundType soundTypeWood;
    protected boolean translucent;
    private CreativeTabs displayOnCreativeTab;
    protected float blockHardness;
    protected boolean isBlockContainer;
    protected double minY;
    public static BlockSoundType soundTypeLadder;
    protected int lightValue;
    protected boolean enableStats = true;
    protected BlockStateContainer blockState;
    public static BlockSoundType soundTypeGravel;

    public static BlockSoundType soundTypeAnvil;
    protected Material blockMaterial;

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {}

    public boolean isFullBlock() {
        return this.fullBlock;
    }

    public boolean isTranslucent() {
        return this.translucent;
    }

    public boolean canProvidePower() {
        return false;
    }

    public static Block getBlockById(int p171_0) {
        return (net.minecraft.block.Block)
                net.minecraft.block.Block.blockRegistry.getObjectById(p171_0);
    }

    public BlockStateContainer getBlockState() {
        return this.blockState;
    }

    public static Block getBlockFromItem(Item item) {
        return item instanceof ItemBlock ? ((ItemBlock) item).getBlock() : null;
    }

    public float getAmbientOcclusionLightValue() {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        if (ClientServices.moduleManager.xray.isEnabled()) {
            return 1.0F;
        } else {
            return this.isSolidFullCube() ? 0.2F : 1.0F;
        }
    }

    public void fillWithRain(World world, BlockPos blockPos) {}

    public int getDamageValue(World world, BlockPos blockPos) {
        return this.damageDropped(world.getBlockState(blockPos));
    }

    public boolean isCollidable() {
        return true;
    }

    public boolean hasComparatorInputOverride() {
        return false;
    }

    protected Block setResistance(float p171_0) {
        this.blockResistance = p171_0 * 3.0F;
        return this;
    }

    public final void dropBlockAsItem(
            World world, BlockPos blockPos, BlockState blockState, int p171_3) {
        this.dropBlockAsItemWithChance(world, blockPos, blockState, 1.0F, p171_3);
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {}

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return 0;
    }

    public boolean isSolidFullCube() {
        return this.blockMaterial.blocksMovement() && this.isFullCube();
    }

    public int getMetaFromState(BlockState blockState) {
        if (blockState != null && !blockState.getPropertyNames().isEmpty()) {
            throw new IllegalArgumentException(
                    "Don't know how to convert " + blockState + " back into data...");
        } else {
            return 0;
        }
    }

    public void setBlockBoundsBasedOnState(IBlockAccess iBlockAccess, BlockPos blockPos) {}

    // region String decoding support

    // endregion

    public boolean hasTileEntity() {
        return this.isBlockContainer;
    }

    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {}

    public int getMixedBrightnessForBlock(IBlockAccess iBlockAccess, BlockPos blockPos) {
        net.minecraft.block.Block block = iBlockAccess.getBlockState(blockPos).getBlock();
        int combinedLight = iBlockAccess.getCombinedLight(blockPos, block.getLightValue());

        try {
            if (combinedLight != 0 || !(block instanceof net.minecraft.block.BlockSlab)) {
                return combinedLight;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw numberformatexception;
        }

        blockPos = blockPos.offsetDown();
        block = iBlockAccess.getBlockState(blockPos).getBlock();
        return iBlockAccess.getCombinedLight(blockPos, block.getLightValue());
    }

    public BlockState getActualState(
            BlockState blockState, IBlockAccess iBlockAccess, BlockPos blockPos) {
        return blockState;
    }

    protected Block setTickRandomly(boolean needsRandomTickValue) {
        this.needsRandomTick = needsRandomTickValue;
        return this;
    }

    public boolean canPlaceBlockOnSide(World world, BlockPos blockPos, Facing facing) {
        return this.canPlaceBlockAt(world, blockPos);
    }

    protected ItemStack createStackedBlock(BlockState blockState) {
        int i = 0;
        Item item = Item.getItemFromBlock(this);
        if (item != null && item.getHasSubtypes()) {
            i = this.getMetaFromState(blockState);
        }

        return new ItemStack(item, 1, i);
    }

    public static Block getBlockFromName(String text) {
        ResourceLocation resourceLocation = new ResourceLocation(text);
        if (net.minecraft.block.Block.blockRegistry.containsKey(resourceLocation)) {
            return (Block) net.minecraft.block.Block.blockRegistry.getObject(resourceLocation);
        }

        try {
            return (Block)
                    net.minecraft.block.Block.blockRegistry.getObjectById(Integer.parseInt(text));
        } catch (NumberFormatException numberformatexception) {
            return null;
        }
    }

    public static int getIdFromBlock(Block block) {
        return net.minecraft.block.Block.blockRegistry.getIDForObject(block);
    }

    public int getLightValue() {
        return this.lightValue;
    }

    private static void registerBlock(int i, String text, Block block) {
        registerBlock(i, new ResourceLocation(text), block);
    }

    public Block setCreativeTab(CreativeTabs ajpValue) {
        this.displayOnCreativeTab = ajpValue;
        return this;
    }

    public int tickRate(World world) {
        return 10;
    }

    public Block setUnlocalizedName(String text) {
        this.unlocalizedName = text;
        return this;
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        BoundingBox boundingBoxValue = this.getCollisionBoundingBox(world, blockPos, blockState);
        if (MinecraftClient.getMinecraft().player != null
                && entity == MinecraftClient.getMinecraft().player) {
            BlockCollisionEvent blockCollisionEvent =
                    new BlockCollisionEvent(blockState.getBlock(), blockPos, boundingBoxValue);
            RemixClient.eventBus.post(blockCollisionEvent);
            boundingBoxValue = blockCollisionEvent.getBounds();
        }

        if (boundingBoxValue != null && boundingBox.intersectsWith(boundingBoxValue)) {
            list.add(boundingBoxValue);
        }
    }

    public void onLanded(World world, Entity entity) {
        entity.motionY = 0.0;
    }

    public BoundingBox getSelectedBoundingBox(World world, BlockPos blockPos) {
        return new BoundingBox(
                blockPos.getX() + this.minX,
                blockPos.getY() + this.minY,
                blockPos.getZ() + this.minZ,
                blockPos.getX() + this.maxX,
                blockPos.getY() + this.maxY,
                blockPos.getZ() + this.maxZ);
    }

    public boolean isBlockSolid(IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return iBlockAccess.getBlockState(blockPos).getBlock().getMaterial().isSolid();
    }

    protected final void setBlockBounds(
            float ajeValue,
            float minYValue,
            float minZValue,
            float ajiValue,
            float maxYValue,
            float ajcValue) {
        this.minX = ajeValue;
        this.minY = minYValue;
        this.minZ = minZValue;
        this.maxX = ajiValue;
        this.maxY = maxYValue;
        this.maxZ = ajcValue;
    }

    public boolean canPlaceBlockAt(World world, BlockPos blockPos) {
        return world.getBlockState(blockPos).getBlock().blockMaterial.isReplaceable();
    }

    public final double getBlockBoundsMinY() {
        return this.minY;
    }

    public boolean isOpaqueCube() {
        return true;
    }

    public void randomDisplayTick(
            World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public static void setOpaqueControlState(String text) {
        net.minecraft.block.Block.opaqueControlState = text;
    }

    public final int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return this.colorMultiplier(iBlockAccess, blockPos, 0);
    }

    public void onEntityCollidedWithBlock(World world, BlockPos blockPos, Entity entity) {}

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        return facing == Facing.DOWN && this.minY > 0.0
                || facing == Facing.UP && this.maxY < 1.0
                || facing == Facing.NORTH && this.minZ > 0.0
                || facing == Facing.SOUTH && this.maxZ < 1.0
                || facing == Facing.WEST && this.minX > 0.0
                || facing == Facing.EAST && this.maxX < 1.0
                || !iBlockAccess.getBlockState(blockPos).getBlock().isOpaqueCube();
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return EnumWorldBlockLayer.SOLID;
    }

    public Material getMaterial() {
        return this.blockMaterial;
    }

    public void onBlockDestroyedByExplosion(World world, BlockPos blockPos, Explosion explosion) {}

    public MapColor getMapColor(BlockState blockState) {
        return this.getMaterial().getMaterialMapColor();
    }

    public int isProvidingStrongPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return 0;
    }

    public static boolean isEqualTo(Block block, Block blockValue) {
        return block != null
                && blockValue != null
                && (block == blockValue || block.isAssociatedBlock(blockValue));
    }

    public float getBlockHardness(World world, BlockPos blockPos) {
        return this.blockHardness;
    }

    public void setBlockBoundsForItemRender() {}

    protected Block setLightOpacity(int lightOpacityValue) {
        this.lightOpacity = lightOpacityValue;
        return this;
    }

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {}

    public int quantityDroppedWithBonus(int i, Random random) {
        return this.quantityDropped(random);
    }

    public void harvestBlock(
            World world,
            PlayerEntity playerEntity,
            BlockPos blockPos,
            BlockState blockState,
            TileEntity tileEntity) {
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        playerEntity.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
        int i = k;
        playerEntity.addExhaustion(0.025F);
        if (this.canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier(playerEntity)) {
            ItemStack itemStack = this.createStackedBlock(blockState);
            if (itemStack != null) {
                spawnAsEntity(world, blockPos, itemStack);
            }

            if (i != 0) {
                return;
            }
        }

        int j = EnchantmentHelper.getFortuneModifier(playerEntity);
        this.dropBlockAsItem(world, blockPos, blockState, j);
    }

    public float getExplosionResistance(Entity entity) {
        return this.blockResistance / 5.0F;
    }

    public boolean getTickRandomly() {
        return this.needsRandomTick;
    }

    public String getLocalizedName() {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    public boolean canReplace(World world, BlockPos blockPos, Facing facing, ItemStack itemStack) {
        return this.canPlaceBlockOnSide(world, blockPos, facing);
    }

    public void breakBlock(World world, BlockPos blockPos, BlockState blockState) {}

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return new BoundingBox(
                blockPos.getX() + this.minX,
                blockPos.getY() + this.minY,
                blockPos.getZ() + this.minZ,
                blockPos.getX() + this.maxX,
                blockPos.getY() + this.maxY,
                blockPos.getZ() + this.maxZ);
    }

    public boolean requiresUpdates() {
        return true;
    }

    public final double getBlockBoundsMinZ() {
        return this.minZ;
    }

    public static int getStateId(BlockState blockState) {
        return net.minecraft.block.Block.getIdFromBlock(blockState.getBlock())
                + (blockState.getBlock().getMetaFromState(blockState) << 12);
    }

    public static String getOpaqueControlState() {
        return net.minecraft.block.Block.opaqueControlState;
    }

    public final double getBlockBoundsMinX() {
        return this.minX;
    }

    protected Block disableStats() {
        this.enableStats = false;
        return this;
    }

    public boolean onBlockEventReceived(
            World world, BlockPos blockPos, BlockState blockState, int i, int j) {
        return false;
    }

    public boolean isAssociatedBlock(Block block) {
        try {
            if (this == block) {
                return true;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw numberformatexception;
        }

        return false;
    }

    public BlockEnumOffsetType getOffsetType() {
        return BlockEnumOffsetType.NONE;
    }

    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(item, 1, 0));
    }

    protected void dropXpOnBlockBreak(World world, BlockPos blockPos, int i) {
        int j = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isRemote) {
            while (i > 0) {
                int k = EntityXPOrb.method_c640c0ab3979b883b99e(i);
                i -= k;
                world.spawnEntityInWorld(
                        new EntityXPOrb(
                                world,
                                blockPos.getX() + 0.5,
                                blockPos.getY() + 0.5,
                                blockPos.getZ() + 0.5,
                                k));
                if (j == 0) {
                    break;
                }
            }
        }
    }

    public float getPlayerRelativeBlockHardness(
            PlayerEntity playerEntity, World world, BlockPos blockPos) {
        float blockHardnessValue = this.getBlockHardness(world, blockPos);

        try {
            if (blockHardnessValue < 0.0F) {
                return 0.0F;
            }
        } catch (java.lang.NumberFormatException numberformatexception1) {
            throw numberformatexception1;
        }

        try {
            if (!playerEntity.canHarvestBlock(this)) {
                return playerEntity.getToolDigEfficiency(this) / blockHardnessValue / 100.0F;
            }
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw numberformatexception;
        }

        return playerEntity.getToolDigEfficiency(this) / blockHardnessValue / 30.0F;
    }

    public int getMobilityFlag() {
        return this.blockMaterial.getMaterialMobility();
    }

    public final double getBlockBoundsMaxY() {
        return this.maxY;
    }

    protected Block setBlockUnbreakable() {
        this.setHardness(-1.0F);
        return this;
    }

    protected Block setStepSound(BlockSoundType ajfValue) {
        this.stepSound = ajfValue;
        return this;
    }

    public void onBlockClicked(World world, BlockPos blockPos, PlayerEntity playerEntity) {}

    public boolean isFullCube() {
        return true;
    }

    public void onFallenUpon(World world, BlockPos blockPos, Entity entity, float p171_3) {
        entity.fall(p171_3, 1.0F);
    }

    public void onBlockHarvested(
            World world, BlockPos blockPos, BlockState blockState, PlayerEntity playerEntity) {}

    private boolean isVecInsideXZBounds(Vec3 vec3) {
        return vec3 != null
                && vec3.x >= this.minX
                && vec3.x <= this.maxX
                && vec3.z >= this.minZ
                && vec3.z <= this.maxZ;
    }

    private boolean isVecInsideXYBounds(Vec3 vec3) {
        return vec3 != null
                && vec3.x >= this.minX
                && vec3.x <= this.maxX
                && vec3.y >= this.minY
                && vec3.y <= this.maxY;
    }

    public boolean getEnableStats() {
        return this.enableStats;
    }

    public static void spawnAsEntity(World world, BlockPos blockPos, ItemStack itemStack) {
        if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            float f = 0.5F;
            double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5;
            double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5;
            double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5;
            EntityItem entityItem =
                    new EntityItem(
                            world,
                            blockPos.getX() + d0,
                            blockPos.getY() + d1,
                            blockPos.getZ() + d2,
                            itemStack);
            entityItem.setDefaultPickupDelay();
            world.spawnEntityInWorld(entityItem);
        }
    }

    public boolean isNormalCube() {
        return this.blockMaterial.isOpaque() && this.isFullCube() && !this.canProvidePower();
    }

    public RayTraceResult collisionRayTrace(
            World world, BlockPos blockPos, Vec3 vec3, Vec3 vec3Value) {
        this.setBlockBoundsBasedOnState(world, blockPos);
        vec3 = vec3.addVector(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        vec3Value = vec3Value.addVector(-blockPos.getX(), -blockPos.getY(), -blockPos.getZ());
        Vec3 intermediateWithXValue = vec3.getIntermediateWithXValue(vec3Value, this.minX);
        Vec3 intermediateWithXValueValue = vec3.getIntermediateWithXValue(vec3Value, this.maxX);
        Vec3 intermediateWithYValue = vec3.getIntermediateWithYValue(vec3Value, this.minY);
        Vec3 intermediateWithYValueValue = vec3.getIntermediateWithYValue(vec3Value, this.maxY);
        Vec3 intermediateWithZValue = vec3.getIntermediateWithZValue(vec3Value, this.minZ);
        Vec3 intermediateWithZValueValue = vec3.getIntermediateWithZValue(vec3Value, this.maxZ);
        if (!this.isVecInsideYZBounds(intermediateWithXValue)) {
            intermediateWithXValue = null;
        }

        if (!this.isVecInsideYZBounds(intermediateWithXValueValue)) {
            intermediateWithXValueValue = null;
        }

        if (!this.isVecInsideXZBounds(intermediateWithYValue)) {
            intermediateWithYValue = null;
        }

        if (!this.isVecInsideXZBounds(intermediateWithYValueValue)) {
            intermediateWithYValueValue = null;
        }

        if (!this.isVecInsideXYBounds(intermediateWithZValue)) {
            intermediateWithZValue = null;
        }

        if (!this.isVecInsideXYBounds(intermediateWithZValueValue)) {
            intermediateWithZValueValue = null;
        }

        Vec3 vec3Value2 = null;
        if (intermediateWithXValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithXValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithXValue;
        }

        if (intermediateWithXValueValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithXValueValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithXValueValue;
        }

        if (intermediateWithYValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithYValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithYValue;
        }

        if (intermediateWithYValueValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithYValueValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithYValueValue;
        }

        if (intermediateWithZValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithZValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithZValue;
        }

        if (intermediateWithZValueValue != null
                && (vec3Value2 == null
                        || vec3.squareDistanceTo(intermediateWithZValueValue)
                                < vec3.squareDistanceTo(vec3Value2))) {
            vec3Value2 = intermediateWithZValueValue;
        }

        if (vec3Value2 == null) {
            return null;
        }

        Facing facing = null;
        if (vec3Value2 == intermediateWithXValue) {
            facing = Facing.WEST;
        }

        if (vec3Value2 == intermediateWithXValueValue) {
            facing = Facing.EAST;
        }

        if (vec3Value2 == intermediateWithYValue) {
            facing = Facing.DOWN;
        }

        if (vec3Value2 == intermediateWithYValueValue) {
            facing = Facing.UP;
        }

        if (vec3Value2 == intermediateWithZValue) {
            facing = Facing.NORTH;
        }

        if (vec3Value2 == intermediateWithZValueValue) {
            facing = Facing.SOUTH;
        }

        return new RayTraceResult(
                vec3Value2.addVector(blockPos.getX(), blockPos.getY(), blockPos.getZ()),
                facing,
                blockPos);
    }

    public boolean isReplaceable(World world, BlockPos blockPos) {
        return false;
    }

    public int getRenderColor(BlockState blockState) {
        return 16777215;
    }

    protected Block(Material ajwValue) {
        this.stepSound = net.minecraft.block.Block.soundTypeStone;
        this.blockParticleGravity = 1.0F;
        this.slipperiness = 0.6F;
        this.blockMaterial = ajwValue;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.fullBlock = this.isOpaqueCube();
        this.lightOpacity = this.isOpaqueCube() ? 255 : 0;
        this.translucent =
                remix.compat.compiler.JvmBooleanSlots.fromInt((!ajwValue.blocksLight() ? 1 : 0));
        this.blockState = this.createBlockState();
        this.setDefaultState(this.blockState.getBaseState());
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return !this.blockMaterial.blocksMovement();
    }

    public boolean getUseNeighborBrightness() {
        return this.useNeighborBrightness;
    }

    public int getBlockColor() {
        return 16777215;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState();
    }

    public BlockState getStateForEntityRender(BlockState blockState) {
        return blockState;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Item.getItemFromBlock(this);
    }

    private boolean isVecInsideYZBounds(Vec3 vec3) {
        return vec3 != null
                && vec3.y >= this.minY
                && vec3.y <= this.maxY
                && vec3.z >= this.minZ
                && vec3.z <= this.maxZ;
    }

    public boolean canCollideCheck(BlockState blockState, boolean flag) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        GhostHandModule ghostHand = ClientServices.moduleManager.ghostHand;
        clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        ChestAuraModule chestAura = ClientServices.moduleManager.chestAura;
        if (ghostHand.isEnabled()) {
            return ghostHand.isGhostHandTarget(blockState.getBlock());
        } else {
            return chestAura.isEnabled()
                            && chestAura.targetChest != null
                            && chestAura.throughWallsSetting.asBoolean()
                    ? blockState.getBlock() instanceof BlockChest
                    : this.isCollidable();
        }
    }

    public int isProvidingWeakPower(
            IBlockAccess iBlockAccess, BlockPos blockPos, BlockState blockState, Facing facing) {
        return 0;
    }

    public int damageDropped(BlockState blockState) {
        return 0;
    }

    protected BlockStateContainer createBlockState() {
        return new net.minecraft.block.state.BlockStateContainer(
                this, new net.minecraft.block.properties.IProperty[0]);
    }

    // region Static initialization and string tables
    static {
        setOpaqueControlState(null);
        net.minecraft.block.Block.BLOCK_STATE_IDS = new ObjectIntIdentityMap();
        net.minecraft.block.Block.soundTypeStone = new BlockSoundType("stone", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeWood = new BlockSoundType("wood", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeGravel = new BlockSoundType("gravel", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeGrass = new BlockSoundType("grass", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypePiston = new BlockSoundType("stone", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeMetal = new BlockSoundType("stone", 1.0F, 1.5F);
        net.minecraft.block.Block.soundTypeGlass = new GlassBlockSoundType("stone", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeCloth = new BlockSoundType("cloth", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeSand = new BlockSoundType("sand", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeSnow = new BlockSoundType("snow", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeLadder = new LadderBlockSoundType("ladder", 1.0F, 1.0F);
        net.minecraft.block.Block.soundTypeAnvil = new AnvilBlockSoundType("anvil", 0.3F, 1.0F);
        net.minecraft.block.Block.SLIME_SOUND = new SlimeBlockSoundType("slime", 1.0F, 1.0F);
        net.minecraft.block.Block.AIR_ID = new ResourceLocation("air");
        net.minecraft.block.Block.blockRegistry =
                new RegistryNamespacedDefaultedByKey(net.minecraft.block.Block.AIR_ID);
    }

    // endregion

    public String getUnlocalizedName() {
        return "tile." + this.unlocalizedName;
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        return this.getStateFromMeta(i);
    }

    public void onBlockDestroyedByPlayer(World world, BlockPos blockPos, BlockState blockState) {}

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        return 16777215;
    }

    public final double getBlockBoundsMaxX() {
        return this.maxX;
    }

    protected Block setLightLevel(float p171_0) {
        this.lightValue = (int) (15.0F * p171_0);
        return this;
    }

    protected final void setDefaultState(BlockState blockState) {
        this.defaultBlockState = blockState;
    }

    public CreativeTabs getCreativeTabToDisplayOn() {
        return this.displayOnCreativeTab;
    }

    public final BlockState getDefaultState() {
        return this.defaultBlockState;
    }

    public int getLightOpacity() {
        return this.lightOpacity;
    }

    public static void registerBlocks() {
        registerBlock(
                0, net.minecraft.block.Block.AIR_ID, new BlockAir().setUnlocalizedName("air"));
        registerBlock(
                1,
                "stone",
                new BlockStone()
                        .setHardness(1.5F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("stone"));
        registerBlock(
                2,
                "grass",
                new BlockGrass()
                        .setHardness(0.6F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("grass"));
        registerBlock(
                3,
                "dirt",
                new BlockDirt()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGravel)
                        .setUnlocalizedName("dirt"));
        Block block =
                new Block(Material.rock)
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("stonebrick")
                        .setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(4, "cobblestone", block);
        Block blockValue =
                new BlockPlanks()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("wood");
        registerBlock(5, "planks", blockValue);
        registerBlock(
                6,
                "sapling",
                new BlockSapling()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("sapling"));
        registerBlock(
                7,
                "bedrock",
                new Block(Material.rock)
                        .setBlockUnbreakable()
                        .setResistance(6000000.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("bedrock")
                        .disableStats()
                        .setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(
                8,
                "flowing_water",
                new BlockDynamicLiquid(Material.water)
                        .setHardness(100.0F)
                        .setLightOpacity(3)
                        .setUnlocalizedName("water")
                        .disableStats());
        registerBlock(
                9,
                "water",
                new BlockStaticLiquid(Material.water)
                        .setHardness(100.0F)
                        .setLightOpacity(3)
                        .setUnlocalizedName("water")
                        .disableStats());
        registerBlock(
                10,
                "flowing_lava",
                new BlockDynamicLiquid(Material.lava)
                        .setHardness(100.0F)
                        .setLightLevel(1.0F)
                        .setUnlocalizedName("lava")
                        .disableStats());
        registerBlock(
                11,
                "lava",
                new BlockStaticLiquid(Material.lava)
                        .setHardness(100.0F)
                        .setLightLevel(1.0F)
                        .setUnlocalizedName("lava")
                        .disableStats());
        registerBlock(
                12,
                "sand",
                new BlockSand()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeSand)
                        .setUnlocalizedName("sand"));
        registerBlock(
                13,
                "gravel",
                new BlockGravel()
                        .setHardness(0.6F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGravel)
                        .setUnlocalizedName("gravel"));
        registerBlock(
                14,
                "gold_ore",
                new BlockOre()
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("oreGold"));
        registerBlock(
                15,
                "iron_ore",
                new BlockOre()
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("oreIron"));
        registerBlock(
                16,
                "coal_ore",
                new BlockOre()
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("oreCoal"));
        registerBlock(17, "log", new BlockOldLog().setUnlocalizedName("log"));
        registerBlock(18, "leaves", new BlockOldLeaf().setUnlocalizedName("leaves"));
        registerBlock(
                19,
                "sponge",
                new BlockSponge()
                        .setHardness(0.6F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("sponge"));
        registerBlock(
                20,
                "glass",
                new BlockGlass(Material.glass, false)
                        .setHardness(0.3F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setUnlocalizedName("glass"));
        registerBlock(
                21,
                "lapis_ore",
                new BlockOre()
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("oreLapis"));
        registerBlock(
                22,
                "lapis_block",
                new BlockCompressed(MapColor.lapisColor)
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("blockLapis")
                        .setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(
                23,
                "dispenser",
                new BlockDispenser()
                        .setHardness(3.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("dispenser"));
        Block blockValue2 =
                new BlockSandStone()
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setHardness(0.8F)
                        .setUnlocalizedName("sandStone");
        registerBlock(24, "sandstone", blockValue2);
        registerBlock(
                25,
                "noteblock",
                new BlockNote().setHardness(0.8F).setUnlocalizedName("musicBlock"));
        registerBlock(
                26,
                "bed",
                new BlockBed()
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setHardness(0.2F)
                        .setUnlocalizedName("bed")
                        .disableStats());
        registerBlock(
                27,
                "golden_rail",
                new BlockRailPowered()
                        .setHardness(0.7F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("goldenRail"));
        registerBlock(
                28,
                "detector_rail",
                new BlockRailDetector()
                        .setHardness(0.7F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("detectorRail"));
        registerBlock(
                29,
                "sticky_piston",
                new BlockPistonBase(true).setUnlocalizedName("pistonStickyBase"));
        registerBlock(
                30,
                "web",
                new BlockWeb().setLightOpacity(1).setHardness(4.0F).setUnlocalizedName("web"));
        registerBlock(
                31,
                "tallgrass",
                new BlockTallGrass()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("tallgrass"));
        registerBlock(
                32,
                "deadbush",
                new BlockDeadBush()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("deadbush"));
        registerBlock(33, "piston", new BlockPistonBase(false).setUnlocalizedName("pistonBase"));
        registerBlock(34, "piston_head", new BlockPistonExtension());
        registerBlock(
                35,
                "wool",
                new BlockColored(Material.cloth)
                        .setHardness(0.8F)
                        .setStepSound(net.minecraft.block.Block.soundTypeCloth)
                        .setUnlocalizedName("cloth"));
        registerBlock(36, "piston_extension", new BlockPistonMoving());
        registerBlock(
                37,
                "yellow_flower",
                new BlockYellowFlower()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("flower1"));
        registerBlock(
                38,
                "red_flower",
                new BlockRedFlower()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("flower2"));
        Block blockValue3 =
                new BlockMushroom()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setLightLevel(0.125F)
                        .setUnlocalizedName("mushroom");
        registerBlock(39, "brown_mushroom", blockValue3);
        Block blockValue4 =
                new BlockMushroom()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("mushroom");
        registerBlock(40, "red_mushroom", blockValue4);
        registerBlock(
                41,
                "gold_block",
                new BlockCompressed(MapColor.goldColor)
                        .setHardness(3.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("blockGold"));
        registerBlock(
                42,
                "iron_block",
                new BlockCompressed(MapColor.ironColor)
                        .setHardness(5.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("blockIron"));
        registerBlock(
                43,
                "double_stone_slab",
                new BlockDoubleStoneSlab()
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("stoneSlab"));
        registerBlock(
                44,
                "stone_slab",
                new BlockHalfStoneSlab()
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("stoneSlab"));
        Block blockValue5 =
                new Block(Material.rock)
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("brick")
                        .setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(45, "brick_block", blockValue5);
        registerBlock(
                46,
                "tnt",
                new BlockTNT()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("tnt"));
        registerBlock(
                47,
                "bookshelf",
                new BlockBookshelf()
                        .setHardness(1.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("bookshelf"));
        registerBlock(
                48,
                "mossy_cobblestone",
                new Block(Material.rock)
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("stoneMoss")
                        .setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(
                49,
                "obsidian",
                new BlockObsidian()
                        .setHardness(50.0F)
                        .setResistance(2000.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("obsidian"));
        registerBlock(
                50,
                "torch",
                new BlockTorch()
                        .setHardness(0.0F)
                        .setLightLevel(0.9375F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("torch"));
        registerBlock(
                51,
                "fire",
                new BlockFire()
                        .setHardness(0.0F)
                        .setLightLevel(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeCloth)
                        .setUnlocalizedName("fire")
                        .disableStats());
        registerBlock(
                52,
                "mob_spawner",
                new BlockMobSpawner()
                        .setHardness(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("mobSpawner")
                        .disableStats());
        registerBlock(
                53,
                "oak_stairs",
                new BlockStairs(
                                blockValue
                                        .getDefaultState()
                                        .withProperty(
                                                BlockPlanks.VARIANT_PROP, BlockPlanksEnumType.OAK))
                        .setUnlocalizedName("stairsWood"));
        registerBlock(
                54,
                "chest",
                new BlockChest(0)
                        .setHardness(2.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("chest"));
        registerBlock(
                55,
                "redstone_wire",
                new BlockRedstoneWire()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeStone)
                        .setUnlocalizedName("redstoneDust")
                        .disableStats());
        registerBlock(
                56,
                "diamond_ore",
                new BlockOre()
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("oreDiamond"));
        registerBlock(
                57,
                "diamond_block",
                new BlockCompressed(MapColor.diamondColor)
                        .setHardness(5.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("blockDiamond"));
        registerBlock(
                58,
                "crafting_table",
                new BlockWorkbench()
                        .setHardness(2.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("workbench"));
        registerBlock(59, "wheat", new BlockCrops().setUnlocalizedName("crops"));
        Block blockValue6 =
                new BlockFarmland()
                        .setHardness(0.6F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGravel)
                        .setUnlocalizedName("farmland");
        registerBlock(60, "farmland", blockValue6);
        registerBlock(
                61,
                "furnace",
                new BlockFurnace(false)
                        .setHardness(3.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("furnace")
                        .setCreativeTab(CreativeTabs.tabDecorations));
        registerBlock(
                62,
                "lit_furnace",
                new BlockFurnace(true)
                        .setHardness(3.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setLightLevel(0.875F)
                        .setUnlocalizedName("furnace"));
        registerBlock(
                63,
                "standing_sign",
                new BlockStandingSign()
                        .setHardness(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("sign")
                        .disableStats());
        registerBlock(
                64,
                "wooden_door",
                new BlockDoor(Material.wood)
                        .setHardness(3.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("doorOak")
                        .disableStats());
        registerBlock(
                65,
                "ladder",
                new BlockLadder()
                        .setHardness(0.4F)
                        .setStepSound(net.minecraft.block.Block.soundTypeLadder)
                        .setUnlocalizedName("ladder"));
        registerBlock(
                66,
                "rail",
                new BlockRail()
                        .setHardness(0.7F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("rail"));
        registerBlock(
                67,
                "stone_stairs",
                new BlockStairs(block.getDefaultState()).setUnlocalizedName("stairsStone"));
        registerBlock(
                68,
                "wall_sign",
                new BlockWallSign()
                        .setHardness(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("sign")
                        .disableStats());
        registerBlock(
                69,
                "lever",
                new BlockLever()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("lever"));
        registerBlock(
                70,
                "stone_pressure_plate",
                new BlockPressurePlate(Material.rock, BlockPressurePlateSensitivity.MOBS)
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("pressurePlateStone"));
        registerBlock(
                71,
                "iron_door",
                new BlockDoor(Material.iron)
                        .setHardness(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("doorIron")
                        .disableStats());
        registerBlock(
                72,
                "wooden_pressure_plate",
                new BlockPressurePlate(Material.wood, BlockPressurePlateSensitivity.EVERYTHING)
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("pressurePlateWood"));
        registerBlock(
                73,
                "redstone_ore",
                new BlockRedstoneOre(false)
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("oreRedstone")
                        .setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(
                74,
                "lit_redstone_ore",
                new BlockRedstoneOre(true)
                        .setLightLevel(0.625F)
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("oreRedstone"));
        registerBlock(
                75,
                "unlit_redstone_torch",
                new BlockRedstoneTorch(false)
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("notGate"));
        registerBlock(
                76,
                "redstone_torch",
                new BlockRedstoneTorch(true)
                        .setHardness(0.0F)
                        .setLightLevel(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("notGate")
                        .setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(
                77,
                "stone_button",
                new BlockButtonStone()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("button"));
        registerBlock(
                78,
                "snow_layer",
                new BlockSnow()
                        .setHardness(0.1F)
                        .setStepSound(net.minecraft.block.Block.soundTypeSnow)
                        .setUnlocalizedName("snow")
                        .setLightOpacity(0));
        registerBlock(
                79,
                "ice",
                new BlockIce()
                        .setHardness(0.5F)
                        .setLightOpacity(3)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setUnlocalizedName("ice"));
        registerBlock(
                80,
                "snow",
                new BlockSnowBlock()
                        .setHardness(0.2F)
                        .setStepSound(net.minecraft.block.Block.soundTypeSnow)
                        .setUnlocalizedName("snow"));
        registerBlock(
                81,
                "cactus",
                new BlockCactus()
                        .setHardness(0.4F)
                        .setStepSound(net.minecraft.block.Block.soundTypeCloth)
                        .setUnlocalizedName("cactus"));
        registerBlock(
                82,
                "clay",
                new BlockClay()
                        .setHardness(0.6F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGravel)
                        .setUnlocalizedName("clay"));
        registerBlock(
                83,
                "reeds",
                new BlockReed()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("reeds")
                        .disableStats());
        registerBlock(
                84,
                "jukebox",
                new BlockJukebox()
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("jukebox"));
        registerBlock(
                85,
                "fence",
                new BlockFence(Material.wood)
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("fence"));
        Block blockValue7 =
                new BlockPumpkin()
                        .setHardness(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("pumpkin");
        registerBlock(86, "pumpkin", blockValue7);
        registerBlock(
                87,
                "netherrack",
                new BlockNetherrack()
                        .setHardness(0.4F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("hellrock"));
        registerBlock(
                88,
                "soul_sand",
                new BlockSoulSand()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeSand)
                        .setUnlocalizedName("hellsand"));
        registerBlock(
                89,
                "glowstone",
                new BlockGlowstone(Material.glass)
                        .setHardness(0.3F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setLightLevel(1.0F)
                        .setUnlocalizedName("lightgem"));
        registerBlock(
                90,
                "portal",
                new BlockPortal()
                        .setHardness(-1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setLightLevel(0.75F)
                        .setUnlocalizedName("portal"));
        registerBlock(
                91,
                "lit_pumpkin",
                new BlockPumpkin()
                        .setHardness(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setLightLevel(1.0F)
                        .setUnlocalizedName("litpumpkin"));
        registerBlock(
                92,
                "cake",
                new BlockCake()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeCloth)
                        .setUnlocalizedName("cake")
                        .disableStats());
        registerBlock(
                93,
                "unpowered_repeater",
                new BlockRedstoneRepeater(false)
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("diode")
                        .disableStats());
        registerBlock(
                94,
                "powered_repeater",
                new BlockRedstoneRepeater(true)
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("diode")
                        .disableStats());
        registerBlock(
                95,
                "stained_glass",
                new BlockStainedGlass(Material.glass)
                        .setHardness(0.3F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setUnlocalizedName("stainedGlass"));
        registerBlock(
                96,
                "trapdoor",
                new BlockTrapDoor(Material.wood)
                        .setHardness(3.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("trapdoor")
                        .disableStats());
        registerBlock(
                97,
                "monster_egg",
                new BlockSilverfish().setHardness(0.75F).setUnlocalizedName("monsterStoneEgg"));
        Block blockValue8 =
                new BlockStoneBrick()
                        .setHardness(1.5F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("stonebricksmooth");
        registerBlock(98, "stonebrick", blockValue8);
        registerBlock(
                99,
                "brown_mushroom_block",
                new BlockHugeMushroom(Material.wood, blockValue3)
                        .setHardness(0.2F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("mushroom"));
        registerBlock(
                100,
                "red_mushroom_block",
                new BlockHugeMushroom(Material.wood, blockValue4)
                        .setHardness(0.2F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("mushroom"));
        registerBlock(
                101,
                "iron_bars",
                new BlockPane(Material.iron, true)
                        .setHardness(5.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("fenceIron"));
        registerBlock(
                102,
                "glass_pane",
                new BlockPane(Material.glass, false)
                        .setHardness(0.3F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setUnlocalizedName("thinGlass"));
        Block blockValue9 =
                new BlockMelon()
                        .setHardness(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("melon");
        registerBlock(103, "melon_block", blockValue9);
        registerBlock(
                104,
                "pumpkin_stem",
                new BlockStem(blockValue7)
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("pumpkinStem"));
        registerBlock(
                105,
                "melon_stem",
                new BlockStem(blockValue9)
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("pumpkinStem"));
        registerBlock(
                106,
                "vine",
                new BlockVine()
                        .setHardness(0.2F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("vine"));
        registerBlock(
                107,
                "fence_gate",
                new BlockFenceGate()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("fenceGate"));
        registerBlock(
                108,
                "brick_stairs",
                new BlockStairs(blockValue5.getDefaultState()).setUnlocalizedName("stairsBrick"));
        registerBlock(
                109,
                "stone_brick_stairs",
                new BlockStairs(
                                blockValue8
                                        .getDefaultState()
                                        .withProperty(
                                                BlockStoneBrick.VARIANT_PROP,
                                                BlockStoneBrickEnumType.DEFAULT))
                        .setUnlocalizedName("stairsStoneBrickSmooth"));
        registerBlock(
                110,
                "mycelium",
                new BlockMycelium()
                        .setHardness(0.6F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("mycel"));
        registerBlock(
                111,
                "waterlily",
                new BlockLilyPad()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("waterlily"));
        Block blockValue10 =
                new BlockNetherBrick()
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("netherBrick")
                        .setCreativeTab(CreativeTabs.tabBlock);
        registerBlock(112, "nether_brick", blockValue10);
        registerBlock(
                113,
                "nether_brick_fence",
                new BlockFence(Material.rock)
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("netherFence"));
        registerBlock(
                114,
                "nether_brick_stairs",
                new BlockStairs(blockValue10.getDefaultState())
                        .setUnlocalizedName("stairsNetherBrick"));
        registerBlock(115, "nether_wart", new BlockNetherWart().setUnlocalizedName("netherStalk"));
        registerBlock(
                116,
                "enchanting_table",
                new BlockEnchantmentTable()
                        .setHardness(5.0F)
                        .setResistance(2000.0F)
                        .setUnlocalizedName("enchantmentTable"));
        registerBlock(
                117,
                "brewing_stand",
                new BlockBrewingStand()
                        .setHardness(0.5F)
                        .setLightLevel(0.125F)
                        .setUnlocalizedName("brewingStand"));
        registerBlock(
                118,
                "cauldron",
                new BlockCauldron().setHardness(2.0F).setUnlocalizedName("cauldron"));
        registerBlock(
                119,
                "end_portal",
                new BlockEndPortal(Material.portal).setHardness(-1.0F).setResistance(6000000.0F));
        registerBlock(
                120,
                "end_portal_frame",
                new BlockEndPortalFrame()
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setLightLevel(0.125F)
                        .setHardness(-1.0F)
                        .setUnlocalizedName("endPortalFrame")
                        .setResistance(6000000.0F)
                        .setCreativeTab(CreativeTabs.tabDecorations));
        registerBlock(
                121,
                "end_stone",
                new Block(Material.rock)
                        .setHardness(3.0F)
                        .setResistance(15.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("whiteStone")
                        .setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(
                122,
                "dragon_egg",
                new BlockDragonEgg()
                        .setHardness(3.0F)
                        .setResistance(15.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setLightLevel(0.125F)
                        .setUnlocalizedName("dragonEgg"));
        registerBlock(
                123,
                "redstone_lamp",
                new BlockRedstoneLight(false)
                        .setHardness(0.3F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setUnlocalizedName("redstoneLight")
                        .setCreativeTab(CreativeTabs.tabRedstone));
        registerBlock(
                124,
                "lit_redstone_lamp",
                new BlockRedstoneLight(true)
                        .setHardness(0.3F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setUnlocalizedName("redstoneLight"));
        registerBlock(
                125,
                "double_wooden_slab",
                new BlockDoubleWoodSlab()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("woodSlab"));
        registerBlock(
                126,
                "wooden_slab",
                new BlockHalfWoodSlab()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("woodSlab"));
        registerBlock(
                127,
                "cocoa",
                new BlockCocoa()
                        .setHardness(0.2F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("cocoa"));
        registerBlock(
                128,
                "sandstone_stairs",
                new BlockStairs(
                                blockValue2
                                        .getDefaultState()
                                        .withProperty(
                                                BlockSandStone.TYPE, BlockSandStoneEnumType.SMOOTH))
                        .setUnlocalizedName("stairsSandStone"));
        registerBlock(
                129,
                "emerald_ore",
                new BlockOre()
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("oreEmerald"));
        registerBlock(
                130,
                "ender_chest",
                new BlockEnderChest()
                        .setHardness(22.5F)
                        .setResistance(1000.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("enderChest")
                        .setLightLevel(0.5F));
        registerBlock(
                131, "tripwire_hook", new BlockTripWireHook().setUnlocalizedName("tripWireSource"));
        registerBlock(132, "tripwire", new BlockTripWire().setUnlocalizedName("tripWire"));
        registerBlock(
                133,
                "emerald_block",
                new BlockCompressed(MapColor.emeraldColor)
                        .setHardness(5.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("blockEmerald"));
        registerBlock(
                134,
                "spruce_stairs",
                new BlockStairs(
                                blockValue
                                        .getDefaultState()
                                        .withProperty(
                                                BlockPlanks.VARIANT_PROP,
                                                BlockPlanksEnumType.SPRUCE))
                        .setUnlocalizedName("stairsWoodSpruce"));
        registerBlock(
                135,
                "birch_stairs",
                new BlockStairs(
                                blockValue
                                        .getDefaultState()
                                        .withProperty(
                                                BlockPlanks.VARIANT_PROP,
                                                BlockPlanksEnumType.BIRCH))
                        .setUnlocalizedName("stairsWoodBirch"));
        registerBlock(
                136,
                "jungle_stairs",
                new BlockStairs(
                                blockValue
                                        .getDefaultState()
                                        .withProperty(
                                                BlockPlanks.VARIANT_PROP,
                                                BlockPlanksEnumType.JUNGLE))
                        .setUnlocalizedName("stairsWoodJungle"));
        registerBlock(
                137,
                "command_block",
                new BlockCommandBlock()
                        .setBlockUnbreakable()
                        .setResistance(6000000.0F)
                        .setUnlocalizedName("commandBlock"));
        registerBlock(
                138, "beacon", new BlockBeacon().setUnlocalizedName("beacon").setLightLevel(1.0F));
        registerBlock(
                139, "cobblestone_wall", new BlockWall(block).setUnlocalizedName("cobbleWall"));
        registerBlock(
                140,
                "flower_pot",
                new BlockFlowerPot()
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeStone)
                        .setUnlocalizedName("flowerPot"));
        registerBlock(141, "carrots", new BlockCarrot().setUnlocalizedName("carrots"));
        int i1 = BlockPlanksEnumType.getInvertedBlockFlowToken();
        registerBlock(142, "potatoes", new BlockPotato().setUnlocalizedName("potatoes"));
        registerBlock(
                143,
                "wooden_button",
                new BlockButtonWood()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("button"));
        registerBlock(
                144,
                "skull",
                new BlockSkull()
                        .setHardness(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("skull"));
        registerBlock(
                145,
                "anvil",
                new BlockAnvil()
                        .setHardness(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeAnvil)
                        .setResistance(2000.0F)
                        .setUnlocalizedName("anvil"));
        int i = i1;
        registerBlock(
                146,
                "trapped_chest",
                new BlockChest(1)
                        .setHardness(2.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("chestTrap"));
        registerBlock(
                147,
                "light_weighted_pressure_plate",
                new BlockPressurePlateWeighted("gold_block", Material.iron, 15)
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("weightedPlate_light"));
        registerBlock(
                148,
                "heavy_weighted_pressure_plate",
                new BlockPressurePlateWeighted("iron_block", Material.iron, 150)
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("weightedPlate_heavy"));
        registerBlock(
                149,
                "unpowered_comparator",
                new BlockRedstoneComparator(false)
                        .setHardness(0.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("comparator")
                        .disableStats());
        registerBlock(
                150,
                "powered_comparator",
                new BlockRedstoneComparator(true)
                        .setHardness(0.0F)
                        .setLightLevel(0.625F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("comparator")
                        .disableStats());
        registerBlock(151, "daylight_detector", new BlockDaylightDetector(false));
        registerBlock(
                152,
                "redstone_block",
                new BlockCompressedPowered(MapColor.tntColor)
                        .setHardness(5.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("blockRedstone"));
        registerBlock(
                153,
                "quartz_ore",
                new BlockOre()
                        .setHardness(3.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("netherquartz"));
        registerBlock(
                154,
                "hopper",
                new BlockHopper()
                        .setHardness(3.0F)
                        .setResistance(8.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("hopper"));
        Block blockValue11 =
                new BlockQuartz()
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setHardness(0.8F)
                        .setUnlocalizedName("quartzBlock");
        registerBlock(155, "quartz_block", blockValue11);
        registerBlock(
                156,
                "quartz_stairs",
                new BlockStairs(
                                blockValue11
                                        .getDefaultState()
                                        .withProperty(
                                                BlockQuartz.VARIANT_PROP,
                                                BlockQuartzEnumType.DEFAULT))
                        .setUnlocalizedName("stairsQuartz"));
        registerBlock(
                157,
                "activator_rail",
                new BlockRailPowered()
                        .setHardness(0.7F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("activatorRail"));
        registerBlock(
                158,
                "dropper",
                new BlockDropper()
                        .setHardness(3.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("dropper"));
        registerBlock(
                159,
                "stained_hardened_clay",
                new BlockColored(Material.rock)
                        .setHardness(1.25F)
                        .setResistance(7.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("clayHardenedStained"));
        registerBlock(
                160,
                "stained_glass_pane",
                new BlockStainedGlassPane()
                        .setHardness(0.3F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setUnlocalizedName("thinStainedGlass"));
        registerBlock(161, "leaves2", new BlockNewLeaf().setUnlocalizedName("leaves"));
        registerBlock(162, "log2", new BlockNewLog().setUnlocalizedName("log"));
        registerBlock(
                163,
                "acacia_stairs",
                new BlockStairs(
                                blockValue
                                        .getDefaultState()
                                        .withProperty(
                                                BlockPlanks.VARIANT_PROP,
                                                BlockPlanksEnumType.ACACIA))
                        .setUnlocalizedName("stairsWoodAcacia"));
        registerBlock(
                164,
                "dark_oak_stairs",
                new BlockStairs(
                                blockValue
                                        .getDefaultState()
                                        .withProperty(
                                                BlockPlanks.VARIANT_PROP,
                                                BlockPlanksEnumType.DARK_OAK))
                        .setUnlocalizedName("stairsWoodDarkOak"));
        registerBlock(
                165,
                "slime",
                new BlockSlime()
                        .setUnlocalizedName("slime")
                        .setStepSound(net.minecraft.block.Block.SLIME_SOUND));
        registerBlock(166, "barrier", new BlockBarrier().setUnlocalizedName("barrier"));
        registerBlock(
                167,
                "iron_trapdoor",
                new BlockTrapDoor(Material.iron)
                        .setHardness(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeMetal)
                        .setUnlocalizedName("ironTrapdoor")
                        .disableStats());
        registerBlock(
                168,
                "prismarine",
                new BlockPrismarine()
                        .setHardness(1.5F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("prismarine"));
        registerBlock(
                169,
                "sea_lantern",
                new BlockSeaLantern(Material.glass)
                        .setHardness(0.3F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setLightLevel(1.0F)
                        .setUnlocalizedName("seaLantern"));
        registerBlock(
                170,
                "hay_block",
                new BlockHay()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGrass)
                        .setUnlocalizedName("hayBlock")
                        .setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(
                171,
                "carpet",
                new BlockCarpet()
                        .setHardness(0.1F)
                        .setStepSound(net.minecraft.block.Block.soundTypeCloth)
                        .setUnlocalizedName("woolCarpet")
                        .setLightOpacity(0));
        registerBlock(
                172,
                "hardened_clay",
                new BlockHardenedClay()
                        .setHardness(1.25F)
                        .setResistance(7.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("clayHardened"));
        registerBlock(
                173,
                "coal_block",
                new Block(Material.rock)
                        .setHardness(5.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("blockCoal")
                        .setCreativeTab(CreativeTabs.tabBlock));
        registerBlock(
                174,
                "packed_ice",
                new BlockPackedIce()
                        .setHardness(0.5F)
                        .setStepSound(net.minecraft.block.Block.soundTypeGlass)
                        .setUnlocalizedName("icePacked"));
        registerBlock(175, "double_plant", new BlockDoublePlant());
        registerBlock(
                176,
                "standing_banner",
                new BlockBannerBlockBannerStanding()
                        .setHardness(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("banner")
                        .disableStats());
        registerBlock(
                177,
                "wall_banner",
                new BlockBannerBlockBannerHanging()
                        .setHardness(1.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("banner")
                        .disableStats());
        registerBlock(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
        Block blockValue12 =
                new BlockRedSandstone()
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setHardness(0.8F)
                        .setUnlocalizedName("redSandStone");
        registerBlock(179, "red_sandstone", blockValue12);
        registerBlock(
                180,
                "red_sandstone_stairs",
                new BlockStairs(
                                blockValue12
                                        .getDefaultState()
                                        .withProperty(
                                                BlockRedSandstone.TYPE,
                                                BlockRedSandstoneEnumType.SMOOTH))
                        .setUnlocalizedName("stairsRedSandStone"));
        registerBlock(
                181,
                "double_stone_slab2",
                new BlockDoubleStoneSlabNew()
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("stoneSlab2"));
        registerBlock(
                182,
                "stone_slab2",
                new BlockHalfStoneSlabNew()
                        .setHardness(2.0F)
                        .setResistance(10.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypePiston)
                        .setUnlocalizedName("stoneSlab2"));
        registerBlock(
                183,
                "spruce_fence_gate",
                new BlockFenceGate()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("spruceFenceGate"));
        registerBlock(
                184,
                "birch_fence_gate",
                new BlockFenceGate()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("birchFenceGate"));
        registerBlock(
                185,
                "jungle_fence_gate",
                new BlockFenceGate()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("jungleFenceGate"));
        registerBlock(
                186,
                "dark_oak_fence_gate",
                new BlockFenceGate()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("darkOakFenceGate"));
        registerBlock(
                187,
                "acacia_fence_gate",
                new BlockFenceGate()
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("acaciaFenceGate"));
        registerBlock(
                188,
                "spruce_fence",
                new BlockFence(Material.wood)
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("spruceFence"));
        registerBlock(
                189,
                "birch_fence",
                new BlockFence(Material.wood)
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("birchFence"));
        registerBlock(
                190,
                "jungle_fence",
                new BlockFence(Material.wood)
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("jungleFence"));
        registerBlock(
                191,
                "dark_oak_fence",
                new BlockFence(Material.wood)
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("darkOakFence"));
        registerBlock(
                192,
                "acacia_fence",
                new BlockFence(Material.wood)
                        .setHardness(2.0F)
                        .setResistance(5.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("acaciaFence"));
        registerBlock(
                193,
                "spruce_door",
                new BlockDoor(Material.wood)
                        .setHardness(3.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("doorSpruce")
                        .disableStats());
        registerBlock(
                194,
                "birch_door",
                new BlockDoor(Material.wood)
                        .setHardness(3.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("doorBirch")
                        .disableStats());
        registerBlock(
                195,
                "jungle_door",
                new BlockDoor(Material.wood)
                        .setHardness(3.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("doorJungle")
                        .disableStats());
        registerBlock(
                196,
                "acacia_door",
                new BlockDoor(Material.wood)
                        .setHardness(3.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("doorAcacia")
                        .disableStats());
        registerBlock(
                197,
                "dark_oak_door",
                new BlockDoor(Material.wood)
                        .setHardness(3.0F)
                        .setStepSound(net.minecraft.block.Block.soundTypeWood)
                        .setUnlocalizedName("doorDarkOak")
                        .disableStats());
        net.minecraft.block.Block.blockRegistry.validateKey();

        for (java.util.Iterator<?> __recoveryIterator171_125921 =
                        (net.minecraft.block.Block.blockRegistry).iterator();
                (__recoveryIterator171_125921).hasNext(); ) {
            Block blockValue13 = (Block) (__recoveryIterator171_125921).next();
            if (blockValue13.blockMaterial == Material.AIR) {
                blockValue13.useNeighborBrightness = false;
                if (i == 0) {
                    continue;
                }

                Entity.setLegacyGlobalFlowMarker(new String[1]);
            }

            byte b0 = 0;
            boolean flag = blockValue13 instanceof BlockStairs;
            boolean flag1 = blockValue13 instanceof BlockSlab;
            int j = blockValue13 == blockValue6 ? 1 : 0;
            boolean translucentValue = blockValue13.translucent;
            int k = blockValue13.lightOpacity == 0 ? 1 : 0;
            if (flag || flag1 || j != 0 || translucentValue || k != 0) {
                b0 = 1;
            }

            blockValue13.useNeighborBrightness = remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
            if (i != 0) {
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_126944 =
                        (net.minecraft.block.Block.blockRegistry).iterator();
                (__recoveryIterator171_126944).hasNext(); ) {
            Block blockValue14 = (Block) (__recoveryIterator171_126944).next();
            UnmodifiableIterator unmodifiableiterator =
                    blockValue14.getBlockState().getValidStates().iterator();

            while (true) {
                if (unmodifiableiterator.hasNext()) {
                    BlockState blockState = (BlockState) unmodifiableiterator.next();
                    int l =
                            net.minecraft.block.Block.blockRegistry.getIDForObject(blockValue14)
                                            << 4
                                    | blockValue14.getMetaFromState(blockState);
                    net.minecraft.block.Block.BLOCK_STATE_IDS.put(blockState, l);
                    if (i == 0) {
                        continue;
                    }
                }

                if (i != 0) {
                    return;
                }
                break;
            }
        }
    }

    public Vec3 modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3 vec3) {
        return vec3;
    }

    public int quantityDropped(Random random) {
        return 1;
    }

    // region String decoding support

    // endregion

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        return false;
    }

    public boolean canDropFromExplosion(Explosion explosion) {
        return true;
    }

    protected boolean canSilkHarvest() {
        return (this.isFullCube() && !this.isBlockContainer);
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {}

    private static void registerBlock(int i, ResourceLocation resourceLocation, Block block) {
        net.minecraft.block.Block.blockRegistry.register(i, resourceLocation, block);
    }

    public final double getBlockBoundsMaxZ() {
        return this.maxZ;
    }

    public void randomTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        this.updateTick(world, blockPos, blockState, random);
    }

    public static BlockState getStateById(int p171_0) {
        int j = p171_0 & 4095;
        int k = p171_0 >> 12 & 15;
        return net.minecraft.block.Block.getBlockById(j).getStateFromMeta(k);
    }

    protected Block setHardness(float ajqValue) {
        try {
            this.blockHardness = ajqValue;
            if (this.blockResistance < ajqValue * 5.0F) {
                this.blockResistance = ajqValue * 5.0F;
            }

            return this;
        } catch (java.lang.NumberFormatException numberformatexception) {
            throw numberformatexception;
        }
    }

    public boolean isFlowerPot() {
        return false;
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Item.getItemFromBlock(this);
    }

    public void dropBlockAsItemWithChance(
            World world, BlockPos blockPos, BlockState blockState, float f, int i) {
        int j = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (!world.isRemote) {
            int k = this.quantityDroppedWithBonus(i, world.rand);
            int l = 0;

            while (l < k) {
                if (world.rand.nextFloat() <= f) {
                    Item item = this.getItemDropped(blockState, world.rand, i);
                    if (item != null) {
                        spawnAsEntity(
                                world,
                                blockPos,
                                new ItemStack(item, 1, this.damageDropped(blockState)));
                    }
                }

                l++;
                if (j != 0) {
                    break;
                }
            }
        }
    }

    public int getRenderType() {
        return 3;
    }

    public boolean isVisuallyOpaque() {
        return this.blockMaterial.blocksMovement() && this.isFullCube();
    }
}
