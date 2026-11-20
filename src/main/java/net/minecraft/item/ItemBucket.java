package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class ItemBucket extends Item {
    private Block isFull;

    private static String ks;

    static {
        ks = "random.fizz";
    }

    public ItemBucket(Block blWValue) {
        this.maxStackSize = 1;
        this.isFull = blWValue;
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    // region String decoding support

    // endregion

    private ItemStack fillBucket(ItemStack itemStack, PlayerEntity playerEntity, Item item) {
        if (playerEntity.capabilities.isCreativeMode) {
            return itemStack;
        }

        if (--itemStack.stackSize <= 0) {
            return new ItemStack(item);
        }

        if (!playerEntity.inventory.addItemStackToInventory(new ItemStack(item))) {
            playerEntity.dropPlayerItemWithRandomChoice(new ItemStack(item, 1, 0), false);
        }

        return itemStack;
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        int i = this.isFull == Blocks.air ? 1 : 0;
        RayTraceResult rayTraceResult =
                this.getMovingObjectPositionFromPlayer(
                        world, playerEntity, remix.compat.compiler.JvmBooleanSlots.fromInt(i));
        if (rayTraceResult == null) {
            return itemStack;
        }

        if (rayTraceResult.typeOfHit == RayHitType.BLOCK) {
            BlockPos blockPos = rayTraceResult.getBlockPos();
            if (!world.isBlockModifiable(playerEntity, blockPos)) {
                return itemStack;
            }

            if (i != 0) {
                if (!playerEntity.canPlayerEdit(
                        blockPos.offset(rayTraceResult.sideHit),
                        rayTraceResult.sideHit,
                        itemStack)) {
                    return itemStack;
                }

                BlockState blockState = world.getBlockState(blockPos);
                Material material = blockState.getBlock().getMaterial();
                if (material == Material.water
                        && (Integer) blockState.getValue(BlockLiquid.LEVEL) == 0) {
                    world.setBlockToAir(blockPos);
                    playerEntity.triggerAchievement(
                            StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return this.fillBucket(itemStack, playerEntity, Items.water_bucket);
                }

                if (material == Material.lava
                        && (Integer) blockState.getValue(BlockLiquid.LEVEL) == 0) {
                    world.setBlockToAir(blockPos);
                    playerEntity.triggerAchievement(
                            StatList.objectUseStats[Item.getIdFromItem(this)]);
                    return this.fillBucket(itemStack, playerEntity, Items.lava_bucket);
                }

                if (text == null) {
                    return itemStack;
                }
            }

            if (this.isFull == Blocks.air) {
                return new ItemStack(Items.bucket);
            }

            BlockPos blockPosValue = blockPos.offset(rayTraceResult.sideHit);
            if (!playerEntity.canPlayerEdit(blockPosValue, rayTraceResult.sideHit, itemStack)) {
                return itemStack;
            }

            if (this.tryPlaceContainedLiquid(world, blockPosValue)
                    && !playerEntity.capabilities.isCreativeMode) {
                playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
                return new ItemStack(Items.bucket);
            }
        }

        return itemStack;
    }

    public Block getContainedBlock() {
        return this.isFull;
    }

    public boolean tryPlaceContainedLiquid(World world, BlockPos blockPos) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (this.isFull == Blocks.air) {
            return false;
        }

        Material material = world.getBlockState(blockPos).getBlock().getMaterial();
        int i = !material.isSolid() ? 1 : 0;
        if (!world.isAirBlock(blockPos) && i == 0) {
            return false;
        }

        if (world.provider.doesWaterVaporize() && this.isFull == Blocks.flowing_water) {
            int j = blockPos.getX();
            int k = blockPos.getY();
            int l = blockPos.getZ();
            world.playSoundEffect(
                    j + 0.5F,
                    k + 0.5F,
                    l + 0.5F,
                    ks,
                    0.5F,
                    2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
            int i1 = 0;

            while (i1 < 8) {
                world.spawnParticle(
                        EnumParticleTypes.SMOKE_LARGE,
                        j + Math.random(),
                        k + Math.random(),
                        l + Math.random(),
                        0.0,
                        0.0,
                        0.0,
                        new int[0]);
                i1++;
                if (text != null) {
                    break;
                }
            }

            if (text == null) {
                return true;
            }
        }

        if (!world.isRemote && i != 0 && !material.isLiquid()) {
            world.destroyBlock(blockPos, true);
        }

        world.setBlockState(blockPos, this.isFull.getDefaultState(), 3);
        return true;
    }

    // region String decoding support

    // endregion
}
