package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockPlanksEnumType;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;

public class ItemDye extends Item {
    private static String[] packedTextFragments;
    public static final int[] dyeColors =
            new int[] {
                1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131,
                14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320
            };

    public static boolean applyBonemeal(ItemStack itemStack, World world, BlockPos blockPos) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() instanceof IGrowable) {
            IGrowable block = (IGrowable) blockState.getBlock();
            if (block.isStillGrowing(world, blockPos, blockState, world.isRemote)) {
                if (!world.isRemote) {
                    if (block.canUseBonemeal(world, world.rand, blockPos, blockState)) {
                        block.grow(world, world.rand, blockPos, blockState);
                    }

                    itemStack.stackSize--;
                }

                return true;
            }
        }

        return false;
    }

    public ItemDye() {
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }

    public static void spawnBonemealParticles(World world, BlockPos blockPos, int i) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (i == 0) {
            i = 15;
        }

        Block block = world.getBlockState(blockPos).getBlock();
        if (block.getMaterial() != Material.AIR) {
            block.setBlockBoundsBasedOnState(world, blockPos);
            int j = 0;

            while (j < i) {
                double d0 = net.minecraft.item.Item.itemRand.nextGaussian() * 0.02;
                double d1 = net.minecraft.item.Item.itemRand.nextGaussian() * 0.02;
                double d2 = net.minecraft.item.Item.itemRand.nextGaussian() * 0.02;
                world.spawnParticle(
                        EnumParticleTypes.VILLAGER_HAPPY,
                        blockPos.getX() + net.minecraft.item.Item.itemRand.nextFloat(),
                        blockPos.getY()
                                + net.minecraft.item.Item.itemRand.nextFloat() * block.getBlockBoundsMaxY(),
                        blockPos.getZ() + net.minecraft.item.Item.itemRand.nextFloat(),
                        d0,
                        d1,
                        d2,
                        new int[0]);
                j++;
                if (text != null) {
                    break;
                }
            }
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.item.ItemDye.packedTextFragments[0] = new String(".");
    }

    // endregion

    public String getUnlocalizedName(ItemStack itemStack) {
        int itemDamage = itemStack.getItemDamage();
        return super.getUnlocalizedName()
                + packedTextFragments[0]
                + EnumDyeColor.byDyeDamage(itemDamage).getUnlocalizedName();
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        int i = 0;
        String textValue = text;

        while (i < 16) {
            list.add(new ItemStack(item, 1, i));
            i++;
            if (textValue != null) {
                break;
            }
        }
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (!playerEntity.canPlayerEdit(blockPos.offset(facing), facing, itemStack)) {
            return false;
        }

        EnumDyeColor enumDyeColor = EnumDyeColor.byDyeDamage(itemStack.getItemDamage());
        if (enumDyeColor == EnumDyeColor.WHITE) {
            if (applyBonemeal(itemStack, world, blockPos)) {
                if (!world.isRemote) {
                    world.playAuxSFX(2005, blockPos, 0);
                }

                return true;
            }
        } else if (enumDyeColor == EnumDyeColor.BROWN) {
            BlockState blockState = world.getBlockState(blockPos);
            Block block = blockState.getBlock();
            if (block == Blocks.log
                    && blockState.getValue(BlockPlanks.VARIANT_PROP)
                            == BlockPlanksEnumType.JUNGLE) {
                if (facing == Facing.DOWN) {
                    return false;
                }

                if (facing == Facing.UP) {
                    return false;
                }

                blockPos = blockPos.offset(facing);
                if (world.isAirBlock(blockPos)) {
                    BlockState blockStateValue =
                            Blocks.cocoa.onBlockPlaced(
                                    world, blockPos, facing, f, f1, f2, 0, playerEntity);
                    world.setBlockState(blockPos, blockStateValue, 2);
                    if (!playerEntity.capabilities.isCreativeMode) {
                        itemStack.stackSize--;
                    }
                }

                return true;
            }
        }

        return false;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public boolean itemInteractionForEntity(
            ItemStack itemStack, PlayerEntity playerEntity, LivingEntity livingEntity) {
        if (livingEntity instanceof EntitySheep) {
            EntitySheep entitySheep = (EntitySheep) livingEntity;
            EnumDyeColor enumDyeColor = EnumDyeColor.byDyeDamage(itemStack.getItemDamage());
            if (!entitySheep.getSheared() && entitySheep.getFleeceColor() != enumDyeColor) {
                entitySheep.setFleeceColor(enumDyeColor);
                itemStack.stackSize--;
            }

            return true;
        } else {
            return false;
        }
    }
}
