package net.minecraft.item;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.world.World;

public class ItemEnderEye extends Item {

    public ItemEnderEye() {
        this.setCreativeTab(CreativeTabs.tabMisc);
    }


    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        RayTraceResult rayTraceResult =
                this.getMovingObjectPositionFromPlayer(world, playerEntity, false);
        if (rayTraceResult != null
                && rayTraceResult.typeOfHit == RayHitType.BLOCK
                && world.getBlockState(rayTraceResult.getBlockPos()).getBlock()
                        == Blocks.end_portal_frame) {
            return itemStack;
        }

        if (!world.isRemote) {
            BlockPos blockPos = world.getStrongholdPos("Stronghold", new BlockPos(playerEntity));
            if (blockPos != null) {
                EntityEnderEye entityEnderEye =
                        new EntityEnderEye(
                                world, playerEntity.posX, playerEntity.posY, playerEntity.posZ);
                entityEnderEye.moveTowards(blockPos);
                world.spawnEntityInWorld(entityEnderEye);
                world.playSoundAtEntity(
                        playerEntity,
                        "random.bow",
                        0.5F,
                        0.4F / (net.minecraft.item.Item.itemRand.nextFloat() * 0.4F + 0.8F));
                world.playAuxSFXAtEntity((PlayerEntity) null, 1002, new BlockPos(playerEntity), 0);
                if (!playerEntity.capabilities.isCreativeMode) {
                    itemStack.stackSize--;
                }

                playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }

        return itemStack;
    }

    @Override
    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        BlockState blockState = world.getBlockState(blockPos);
        String textValue = text;
        if (!playerEntity.canPlayerEdit(blockPos.offset(facing), facing, itemStack)
                || blockState.getBlock() != Blocks.end_portal_frame
                || (Boolean) blockState.getValue(BlockEndPortalFrame.EYE)) {
            return false;
        }

        if (world.isRemote) {
            return true;
        }

        world.setBlockState(blockPos, blockState.withProperty(BlockEndPortalFrame.EYE, true), 2);
        world.updateComparatorOutputLevel(blockPos, Blocks.end_portal_frame);
        itemStack.stackSize--;
        int i = 0;

        while (i < 16) {
            double d0 = blockPos.getX() + (5.0F + net.minecraft.item.Item.itemRand.nextFloat() * 6.0F) / 16.0F;
            double d1 = blockPos.getY() + 0.8125F;
            double d2 = blockPos.getZ() + (5.0F + net.minecraft.item.Item.itemRand.nextFloat() * 6.0F) / 16.0F;
            double d3 = 0.0;
            double d4 = 0.0;
            double d5 = 0.0;
            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5, new int[0]);
            i++;
            if (textValue != null) {
                break;
            }
        }

        Facing facingValue = (Facing) blockState.getValue(BlockEndPortalFrame.FACING);
        int l = 0;
        int j = 0;
        byte b1 = 0;
        byte b0 = 1;
        Facing facingValue2 = facingValue.rotateY();
        int k = -2;

        while (k <= 2) {
            BlockPos blockPosValue = blockPos.offset(facingValue2, k);
            BlockState blockStateValue = world.getBlockState(blockPosValue);
            if (blockStateValue.getBlock() == Blocks.end_portal_frame) {
                if (!(Boolean) blockStateValue.getValue(BlockEndPortalFrame.EYE)) {
                    b0 = 0;
                    if (textValue == null) {
                        break;
                    }
                }

                j = k;
                if (b1 == 0) {
                    l = k;
                    b1 = 1;
                }
            }

            k++;
            if (textValue != null) {
                break;
            }
        }

        if (b0 != 0 && j == l + 2) {
            BlockPos blockPosValue2 = blockPos.offset(facingValue, 4);
            int i1 = l;

            while (i1 <= j) {
                BlockPos blockPosValue3 = blockPosValue2.offset(facingValue2, i1);
                BlockState blockStateValue2 = world.getBlockState(blockPosValue3);
                if (blockStateValue2.getBlock() != Blocks.end_portal_frame
                        || !(Boolean) blockStateValue2.getValue(BlockEndPortalFrame.EYE)) {
                    b0 = 0;
                    if (textValue == null) {
                        break;
                    }
                }

                i1++;
                if (textValue != null) {
                    break;
                }
            }

            i1 = l - 1;

            while (i1 <= j + 1) {
                BlockPos blockPosValue4 = blockPos.offset(facingValue2, i1);
                int j1 = 1;

                while (j1 <= 3) {
                    BlockPos blockPosValue5 = blockPosValue4.offset(facingValue, j1);
                    BlockState blockStateValue3 = world.getBlockState(blockPosValue5);
                    if (blockStateValue3.getBlock() != Blocks.end_portal_frame
                            || !(Boolean) blockStateValue3.getValue(BlockEndPortalFrame.EYE)) {
                        b0 = 0;
                        if (textValue == null) {
                            break;
                        }
                    }

                    j1++;
                    if (textValue != null) {
                        break;
                    }
                }

                i1 += 4;
                if (textValue != null) {
                    break;
                }
            }

            if (b0 != 0) {
                i1 = l;

                while (i1 <= j) {
                    BlockPos blockPosValue6 = blockPos.offset(facingValue2, i1);
                    int k1 = 1;

                    while (k1 <= 3) {
                        BlockPos blockPosValue7 = blockPosValue6.offset(facingValue, k1);
                        world.setBlockState(blockPosValue7, Blocks.end_portal.getDefaultState(), 2);
                        k1++;
                        if (textValue != null) {
                            break;
                        }
                    }

                    i1++;
                    if (textValue != null) {
                        break;
                    }
                }
            }
        }

        return true;
    }
}
