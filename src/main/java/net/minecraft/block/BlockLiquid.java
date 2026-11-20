package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;
import remix.client.ClientServices;
import remix.client.RemixClient;

import java.util.Random;

public abstract class BlockLiquid extends Block {

    public static PropertyInteger LEVEL;

    public int colorMultiplier(IBlockAccess iBlockAccess, BlockPos blockPos, int i) {
        return this.blockMaterial == Material.water
                ? BiomeColorHelper.getWaterColorAtPos(iBlockAccess, blockPos)
                : 16777215;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockLiquid.LEVEL});
    }

    public static float getLiquidHeightPercent(int i) {
        if (i >= 8) {
            i = 0;
        }

        return (i + 1) / 9.0F;
    }

    protected int getEffectiveFlowDecay(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int level = this.getLevel(iBlockAccess, blockPos);
        return level >= 8 ? 0 : level;
    }

    protected void triggerMixEffects(World world, BlockPos blockPos) {
        double x = blockPos.getX();
        int k = BlockPlanksEnumType.getLegacyBlockFlowToken();
        double y = blockPos.getY();
        double z = blockPos.getZ();
        int i = k;
        world.playSoundEffect(
                x + 0.5,
                y + 0.5,
                z + 0.5,
                "random.fizz",
                0.5F,
                2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
        int j = 0;

        while (j < 8) {
            world.spawnParticle(
                    EnumParticleTypes.SMOKE_LARGE,
                    x + Math.random(),
                    y + 1.2,
                    z + Math.random(),
                    0.0,
                    0.0,
                    0.0,
                    new int[0]);
            j++;
            if (i == 0) {
                break;
            }
        }
    }

    public boolean shouldSideBeRendered(
            IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        if (ClientServices.moduleManager.xray.isEnabled()) {
            return iBlockAccess.getBlockState(blockPos).getBlock().getMaterial()
                    != this.blockMaterial;
        } else {
            return iBlockAccess.getBlockState(blockPos).getBlock().getMaterial()
                            == this.blockMaterial
                    ? false
                    : (facing == Facing.UP
                            ? true
                            : super.shouldSideBeRendered(iBlockAccess, blockPos, facing));
        }
    }

    public int tickRate(World world) {
        return this.blockMaterial == Material.water
                ? 5
                : (this.blockMaterial == Material.lava
                        ? (world.provider.getHasNoSky() ? 10 : 30)
                        : 0);
    }

    static {
        net.minecraft.block.BlockLiquid.LEVEL = PropertyInteger.create("level", 0, 15);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public static double getFlowDirection(
            IBlockAccess iBlockAccess, BlockPos blockPos, Material material) {
        Vec3 vec3 = getDynamicLiquidForMaterial(material).getFlowVector(iBlockAccess, blockPos);
        return vec3.x == 0.0 && vec3.z == 0.0
                ? -1000.0
                : Math.atan2(vec3.z, vec3.x) - (Math.PI / 2);
    }

    public boolean isFullCube() {
        return false;
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    public static BlockStaticLiquid getStaticLiquidForMaterial(Material material) {
        if (material == Material.water) {
            return Blocks.water;
        } else if (material == Material.lava) {
            return Blocks.lava;
        } else {
            throw new IllegalArgumentException("Invalid material");
        }
    }

    protected BlockLiquid(Material material) {
        super(material);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockLiquid.LEVEL, 0));
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.setTickRandomly(true);
    }

    public static BlockDynamicLiquid getDynamicLiquidForMaterial(Material material) {
        if (material == Material.water) {
            return Blocks.flowing_water;
        } else if (material == Material.lava) {
            return Blocks.flowing_lava;
        } else {
            throw new IllegalArgumentException("Invalid material");
        }
    }

    protected Vec3 getFlowVector(IBlockAccess world, BlockPos position) {
        boolean firstNeighborOnly = BlockPlanksEnumType.getLegacyBlockFlowToken() == 0;
        Vec3 flow = new Vec3(0.0, 0.0, 0.0);
        int decay = this.getEffectiveFlowDecay(world, position);

        for (Object direction : FacingPlane.HORIZONTAL) {
            Facing facing = (Facing) direction;
            BlockPos neighbor = position.offset(facing);
            int neighborDecay = this.getEffectiveFlowDecay(world, neighbor);
            if (neighborDecay >= 0) {
                flow = addFlowGradient(flow, position, neighbor, neighborDecay - decay);
            } else if (!world.getBlockState(neighbor).getBlock().getMaterial().blocksMovement()) {
                int lowerDecay = this.getEffectiveFlowDecay(world, neighbor.offsetDown());
                if (lowerDecay >= 0) {
                    flow = addFlowGradient(flow, position, neighbor, lowerDecay - (decay - 8));
                    if (firstNeighborOnly) {
                        flow = addFlowGradient(flow, position, neighbor, lowerDecay - decay);
                    }
                }
            }
            if (firstNeighborOnly) {
                break;
            }
        }

        if ((Integer) world.getBlockState(position).getValue(LEVEL) >= 8) {
            for (Object direction : FacingPlane.HORIZONTAL) {
                Facing facing = (Facing) direction;
                BlockPos neighbor = position.offset(facing);
                if (this.isBlockSolid(world, neighbor, facing) || this.isBlockSolid(world, neighbor.offsetUp(), facing)) {
                    flow = flow.normalize().addVector(0.0, -6.0, 0.0);
                    break;
                }
            }
        }
        return flow.normalize();
    }

    private static Vec3 addFlowGradient(Vec3 flow, BlockPos position, BlockPos neighbor, int difference) {
        return flow.addVector((neighbor.getX() - position.getX()) * difference,
                (neighbor.getY() - position.getY()) * difference,
                (neighbor.getZ() - position.getZ()) * difference);
    }

    public boolean shouldRenderSides(IBlockAccess world, BlockPos position) {
        boolean firstNeighborOnly = BlockPlanksEnumType.getLegacyBlockFlowToken() == 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                Block neighbor = world.getBlockState(position.add(dx, 0, dz)).getBlock();
                if (neighbor.getMaterial() != this.blockMaterial && !neighbor.isFullBlock()) {
                    return true;
                }
                if (firstNeighborOnly) {
                    return false;
                }
            }
        }
        return false;
    }

    public Vec3 modifyAcceleration(World world, BlockPos blockPos, Entity entity, Vec3 vec3) {
        return vec3.add(this.getFlowVector(world, blockPos));
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        this.checkForMixing(world, blockPos, blockState);
    }

    protected int getLevel(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return iBlockAccess.getBlockState(blockPos).getBlock().getMaterial() == this.blockMaterial
                ? (Integer) iBlockAccess.getBlockState(blockPos).getValue(net.minecraft.block.BlockLiquid.LEVEL)
                : -1;
    }

    public EnumWorldBlockLayer getBlockLayer() {
        return this.blockMaterial == Material.water
                ? EnumWorldBlockLayer.TRANSLUCENT
                : EnumWorldBlockLayer.SOLID;
    }

    public boolean checkForMixing(World world, BlockPos blockPos, BlockState blockState) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (this.blockMaterial == Material.lava) {
            byte b0 = 0;

            for (Facing facing : Facing.values()) {
                if (facing != Facing.DOWN
                        && world.getBlockState(blockPos.offset(facing)).getBlock().getMaterial()
                                == Material.water) {
                    b0 = 1;
                    if (i == 0) {
                        break;
                    }
                }

                if (i != 0) {
                    break;
                }
            }

            if (b0 != 0) {
                Integer integer = (Integer) blockState.getValue(net.minecraft.block.BlockLiquid.LEVEL);
                if (integer == 0) {
                    world.setBlockState(blockPos, Blocks.obsidian.getDefaultState());
                    this.triggerMixEffects(world, blockPos);
                    return true;
                }

                if (integer <= 4) {
                    world.setBlockState(blockPos, Blocks.cobblestone.getDefaultState());
                    this.triggerMixEffects(world, blockPos);
                    return true;
                }
            }
        }

        return false;
    }

    public void randomDisplayTick(World world, BlockPos position, BlockState state, Random random) {
        boolean extraWaterEffects = BlockPlanksEnumType.getInvertedBlockFlowToken() != 0;
        double x = position.getX();
        double y = position.getY();
        double z = position.getZ();
        if (this.blockMaterial == Material.water) {
            this.renderWaterEffects(world, state, random, x, y, z, extraWaterEffects);
        }
        if (this.blockMaterial == Material.lava
                && world.getBlockState(position.offsetUp()).getBlock().getMaterial() == Material.AIR
                && !world.getBlockState(position.offsetUp()).getBlock().isOpaqueCube()) {
            this.renderLavaEffects(world, random, x, y, z);
        }
        this.renderLiquidDrips(world, position, random, x, y, z, extraWaterEffects);
    }

    private void renderWaterEffects(World world, BlockState state, Random random,
            double x, double y, double z, boolean extraWaterEffects) {
        int level = (Integer) state.getValue(LEVEL);
        if (level > 0 && level < 8) {
            if (random.nextInt(64) != 0) {
                return;
            }
            world.playSound(x + 0.5, y + 0.5, z + 0.5, "liquid.water",
                    random.nextFloat() * 0.25F + 0.75F, random.nextFloat() * 1.0F + 0.5F, false);
            if (!extraWaterEffects) {
                return;
            }
        }
        if (random.nextInt(10) == 0) {
            world.spawnParticle(EnumParticleTypes.SUSPENDED, x + random.nextFloat(), y + random.nextFloat(),
                    z + random.nextFloat(), 0.0, 0.0, 0.0, new int[0]);
        }
    }

    private void renderLavaEffects(World world, Random random, double x, double y, double z) {
        if (random.nextInt(100) == 0) {
            double particleX = x + random.nextFloat();
            double particleY = y + this.maxY;
            double particleZ = z + random.nextFloat();
            world.spawnParticle(EnumParticleTypes.LAVA, particleX, particleY, particleZ, 0.0, 0.0, 0.0, new int[0]);
            world.playSound(particleX, particleY, particleZ, "liquid.lavapop",
                    0.2F + random.nextFloat() * 0.2F, 0.9F + random.nextFloat() * 0.15F, false);
        }
        if (random.nextInt(200) == 0) {
            world.playSound(x, y, z, "liquid.lava", 0.2F + random.nextFloat() * 0.2F,
                    0.9F + random.nextFloat() * 0.15F, false);
        }
    }

    private void renderLiquidDrips(World world, BlockPos position, Random random,
            double x, double y, double z, boolean extraWaterEffects) {
        if (random.nextInt(10) != 0 || !World.doesBlockHaveSolidTopSurface(world, position.offsetDown())) {
            return;
        }
        Material material = world.getBlockState(position.offsetDown(2)).getBlock().getMaterial();
        if (material.blocksMovement() || material.isLiquid()) {
            return;
        }

        double dripX = x + random.nextFloat();
        double dripY = y - 1.05;
        double dripZ = z + random.nextFloat();
        if (this.blockMaterial == Material.water) {
            world.spawnParticle(EnumParticleTypes.DRIP_WATER, dripX, dripY, dripZ, 0.0, 0.0, 0.0, new int[0]);
            if (!extraWaterEffects) {
                return;
            }
        }
        world.spawnParticle(EnumParticleTypes.DRIP_LAVA, dripX, dripY, dripZ, 0.0, 0.0, 0.0, new int[0]);
    }

    public int getRenderType() {
        return 1;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockLiquid.LEVEL, i);
    }

    public boolean canCollideCheck(BlockState blockState, boolean flag) {
        return flag && (Integer) blockState.getValue(net.minecraft.block.BlockLiquid.LEVEL) == 0;
    }

    public int getMixedBrightnessForBlock(IBlockAccess iBlockAccess, BlockPos blockPos) {
        int combinedLight = iBlockAccess.getCombinedLight(blockPos, 0);
        int j = iBlockAccess.getCombinedLight(blockPos.offsetUp(), 0);
        int k = combinedLight & 0xFF;
        int l = j & 0xFF;
        int i1 = combinedLight >> 16 & 0xFF;
        int j1 = j >> 16 & 0xFF;
        return (k > l ? k : l) | (i1 > j1 ? i1 : j1) << 16;
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockLiquid.LEVEL);
    }

    public boolean isBlockSolid(IBlockAccess iBlockAccess, BlockPos blockPos, Facing facing) {
        Material material = iBlockAccess.getBlockState(blockPos).getBlock().getMaterial();
        return material == this.blockMaterial
                ? false
                : (facing == Facing.UP
                        ? true
                        : (material == Material.ice
                                ? false
                                : super.isBlockSolid(iBlockAccess, blockPos, facing)));
    }

    public boolean isPassable(IBlockAccess iBlockAccess, BlockPos blockPos) {
        return this.blockMaterial != Material.lava;
    }

    public BoundingBox getCollisionBoundingBox(
            World world, BlockPos blockPos, BlockState blockState) {
        return null;
    }

    public void onBlockAdded(World world, BlockPos blockPos, BlockState blockState) {
        this.checkForMixing(world, blockPos, blockState);
    }
}
