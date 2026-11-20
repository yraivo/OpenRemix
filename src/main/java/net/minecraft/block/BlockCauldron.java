package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmorArmorMaterial;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockCauldron extends Block {

    public static PropertyInteger LEVEL;

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {net.minecraft.block.BlockCauldron.LEVEL});
    }

    // region String decoding support

    // endregion

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockCauldron.LEVEL, i);
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        return (Integer) world.getBlockState(blockPos).getValue(net.minecraft.block.BlockCauldron.LEVEL);
    }

    public void fillWithRain(World world, BlockPos blockPos) {
        if (world.rand.nextInt(20) == 1) {
            BlockState blockState = world.getBlockState(blockPos);
            if ((Integer) blockState.getValue(net.minecraft.block.BlockCauldron.LEVEL) < 3) {
                world.setBlockState(
                        blockPos, blockState.cycleProperty(net.minecraft.block.BlockCauldron.LEVEL), 2);
            }
        }
    }

    public void addCollisionBoxesToList(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            BoundingBox boundingBox,
            List list,
            Entity entity) {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.3125F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        float f = 0.125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, blockPos, blockState, boundingBox, list, entity);
        this.setBlockBoundsForItemRender();
    }

    public Item getItem(World world, BlockPos blockPos) {
        return Items.cauldron;
    }

    // region String decoding support

    // endregion

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return Items.cauldron;
    }

    public BlockCauldron() {
        super(Material.iron);
        this.setDefaultState(
                this.blockState.getBaseState().withProperty(net.minecraft.block.BlockCauldron.LEVEL, 0));
    }

    public void setWaterLevel(World world, BlockPos blockPos, BlockState blockState, int i) {
        world.setBlockState(
                blockPos,
                blockState.withProperty(net.minecraft.block.BlockCauldron.LEVEL, MathHelper.clamp(i, 0, 3)),
                2);
        world.updateComparatorOutputLevel(blockPos, this);
    }

    public void setBlockBoundsForItemRender() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean onBlockActivated(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            PlayerEntity playerEntity,
            Facing facing,
            float f,
            float f1,
            float f2) {
        int i = BlockPlanksEnumType.getInvertedBlockFlowToken();
        if (world.isRemote) {
            return true;
        }

        ItemStack itemStack = playerEntity.inventory.getCurrentItem();
        if (itemStack == null) {
            return true;
        }

        int j = (Integer) blockState.getValue(net.minecraft.block.BlockCauldron.LEVEL);
        Item item = itemStack.getItem();
        if (item == Items.water_bucket) {
            if (j < 3) {
                if (!playerEntity.capabilities.isCreativeMode) {
                    playerEntity.inventory.setInventorySlotContents(
                            playerEntity.inventory.currentItem, new ItemStack(Items.bucket));
                }

                this.setWaterLevel(world, blockPos, blockState, 3);
            }

            return true;
        } else if (item == Items.glass_bottle) {
            if (j > 0) {
                if (!playerEntity.capabilities.isCreativeMode) {
                    {
                        ItemStack itemStackValue = new ItemStack(Items.potionitem, 1, 0);
                        if (!playerEntity.inventory.addItemStackToInventory(itemStackValue)) {
                            world.spawnEntityInWorld(
                                    new EntityItem(
                                            world,
                                            blockPos.getX() + 0.5,
                                            blockPos.getY() + 1.5,
                                            blockPos.getZ() + 0.5,
                                            itemStackValue));
                            if (!(i == 0)) {

                                if (playerEntity instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerEntity)
                                            .sendContainerToPlayer(playerEntity.inventoryContainer);
                                }
                            }
                        } else {

                            if (playerEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) playerEntity)
                                        .sendContainerToPlayer(playerEntity.inventoryContainer);
                            }
                        }
                    }

                    itemStack.stackSize--;
                    if (itemStack.stackSize <= 0) {
                        playerEntity.inventory.setInventorySlotContents(
                                playerEntity.inventory.currentItem, (ItemStack) null);
                    }
                }

                this.setWaterLevel(world, blockPos, blockState, j - 1);
            }

            return true;
        } else {
            if (j > 0 && item instanceof ItemArmor) {
                ItemArmor itemArmor = (ItemArmor) item;
                if (itemArmor.getArmorMaterial() == ItemArmorArmorMaterial.LEATHER
                        && itemArmor.hasColor(itemStack)) {
                    itemArmor.removeColor(itemStack);
                    this.setWaterLevel(world, blockPos, blockState, j - 1);
                    return true;
                }
            }

            if (j > 0
                    && item instanceof ItemBanner
                    && TileEntityBanner.getPatterns(itemStack) > 0) {
                label98:
                {
                    ItemStack itemStackValue2 = itemStack.copy();
                    itemStackValue2.stackSize = 1;
                    TileEntityBanner.removeBannerData(itemStackValue2);
                    if (itemStack.stackSize <= 1 && !playerEntity.capabilities.isCreativeMode) {
                        playerEntity.inventory.setInventorySlotContents(
                                playerEntity.inventory.currentItem, itemStackValue2);
                        if (i == 0) {
                            break label98;
                        }
                    }

                    {
                        if (!playerEntity.inventory.addItemStackToInventory(itemStackValue2)) {
                            world.spawnEntityInWorld(
                                    new EntityItem(
                                            world,
                                            blockPos.getX() + 0.5,
                                            blockPos.getY() + 1.5,
                                            blockPos.getZ() + 0.5,
                                            itemStackValue2));
                            if (!(i == 0)) {

                                if (playerEntity instanceof EntityPlayerMP) {
                                    ((EntityPlayerMP) playerEntity)
                                            .sendContainerToPlayer(playerEntity.inventoryContainer);
                                }
                            }
                        } else {

                            if (playerEntity instanceof EntityPlayerMP) {
                                ((EntityPlayerMP) playerEntity)
                                        .sendContainerToPlayer(playerEntity.inventoryContainer);
                            }
                        }
                    }

                    if (!playerEntity.capabilities.isCreativeMode) {
                        itemStack.stackSize--;
                    }
                }

                if (!playerEntity.capabilities.isCreativeMode) {
                    this.setWaterLevel(world, blockPos, blockState, j - 1);
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public boolean isFullCube() {
        return false;
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public void onEntityCollidedWithBlock(
            World world, BlockPos blockPos, BlockState blockState, Entity entity) {
        int value = (Integer) blockState.getValue(net.minecraft.block.BlockCauldron.LEVEL);
        float f = blockPos.getY() + (6.0F + 3 * value) / 16.0F;
        if (!world.isRemote
                && entity.isBurning()
                && value > 0
                && entity.getEntityBoundingBox().minY <= f) {
            entity.extinguish();
            this.setWaterLevel(world, blockPos, blockState, value - 1);
        }
    }

    public int getMetaFromState(BlockState blockState) {
        return (Integer) blockState.getValue(net.minecraft.block.BlockCauldron.LEVEL);
    }

    static {
        net.minecraft.block.BlockCauldron.LEVEL = PropertyInteger.create("level", 0, 3);
    }
}
