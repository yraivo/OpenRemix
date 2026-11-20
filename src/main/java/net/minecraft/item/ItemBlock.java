package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;

public class ItemBlock extends Item {

    private static String[] packedTextFragments;
    protected Block block;

    public ItemBlock(Block blWValue) {
        this.block = blWValue;
    }

    public String getUnlocalizedName() {
        return this.block.getUnlocalizedName();
    }

    public CreativeTabs getCreativeTab() {
        return this.block.getCreativeTabToDisplayOn();
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        this.block.getSubBlocks(item, creativeTabs, list);
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
        {
            String text = ItemStack.getLegacyItemStackFlowMarker();
            BlockState blockState = world.getBlockState(blockPos);
            String textValue = text;
            Block block = blockState.getBlock();
            if (block == Blocks.snow_layer
                    && (Integer) blockState.getValue(BlockSnow.LAYERS_PROP) < 1) {
                facing = Facing.UP;
                if (!(textValue == null)) {

                    if (!block.isReplaceable(world, blockPos)) {
                        blockPos = blockPos.offset(facing);
                    }
                }
            } else {

                if (!block.isReplaceable(world, blockPos)) {
                    blockPos = blockPos.offset(facing);
                }
            }
        }

        if (itemStack.stackSize == 0) {
            return false;
        }

        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)) {
            return false;
        }

        if (blockPos.getY() == 255 && this.block.getMaterial().isSolid()) {
            return false;
        }

        if (world.canBlockBePlaced(
                this.block, blockPos, false, facing, (Entity) null, itemStack)) {
            int i = this.getMetadata(itemStack.getItemDamage());
            BlockState blockStateValue =
                    this.block.onBlockPlaced(world, blockPos, facing, f, f1, f2, i, playerEntity);
            if (world.setBlockState(blockPos, blockStateValue, 3)) {
                blockStateValue = world.getBlockState(blockPos);
                if (blockStateValue.getBlock() == this.block) {
                    setTileEntityNBT(world, blockPos, itemStack);
                    this.block.onBlockPlacedBy(
                            world, blockPos, blockStateValue, playerEntity, itemStack);
                }

                world.playSoundEffect(
                        blockPos.getX() + 0.5F,
                        blockPos.getY() + 0.5F,
                        blockPos.getZ() + 0.5F,
                        this.block.stepSound.getPlaceSound(),
                        (this.block.stepSound.getVolume() + 1.0F) / 2.0F,
                        this.block.stepSound.getFrequency() * 0.8F);
                itemStack.stackSize--;
            }

            return true;
        } else {
            return false;
        }
    }

    static {
        net.minecraft.item.ItemBlock.packedTextFragments = new String[4];
        net.minecraft.item.ItemBlock.packedTextFragments[0] = new String("x");
        net.minecraft.item.ItemBlock.packedTextFragments[1] = new String("y");
        net.minecraft.item.ItemBlock.packedTextFragments[2] = new String("z");
    }

    public ItemBlock aO(String text) {
        super.setUnlocalizedName(text);
        return this;
    }

    public Block getBlock() {
        return this.block;
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        return this.block.getUnlocalizedName();
    }

    public boolean canPlaceBlockOnSide(
            World world,
            BlockPos blockPos,
            Facing facing,
            PlayerEntity playerEntity,
            ItemStack itemStack) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        Block block = world.getBlockState(blockPos).getBlock();
        String textValue = text;
        if (block == Blocks.snow_layer) {
            facing = Facing.UP;
            if (textValue == null) {
                return world.canBlockBePlaced(
                        this.block, blockPos, false, facing, (Entity) null, itemStack);
            }
        }

        if (!block.isReplaceable(world, blockPos)) {
            blockPos = blockPos.offset(facing);
        }

        return world.canBlockBePlaced(
                this.block, blockPos, false, facing, (Entity) null, itemStack);
    }

    public static boolean setTileEntityNBT(
            World world, BlockPos blockPos, ItemStack itemStack) {
        if (itemStack.hasTagCompound() && itemStack.getTag().hasKey("BlockEntityTag", 10)) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity != null) {
                CompoundTag compoundTag = new CompoundTag();
                CompoundTag compoundTagValue = (CompoundTag) compoundTag.copy();
                tileEntity.writeToNBT(compoundTag);
                CompoundTag compoundTagValue2 =
                        (CompoundTag) itemStack.getTag().getTag("BlockEntityTag");
                compoundTag.merge(compoundTagValue2);
                compoundTag.setInteger(packedTextFragments[0], blockPos.getX());
                compoundTag.setInteger(packedTextFragments[1], blockPos.getY());
                compoundTag.setInteger(packedTextFragments[2], blockPos.getZ());
                if (!compoundTag.equals(compoundTagValue)) {
                    tileEntity.readFromNBT(compoundTag);
                    tileEntity.markDirty();
                    return true;
                }
            }
        }

        return false;
    }

    public Item setUnlocalizedName(String text) {
        return this.aO(text);
    }
}
