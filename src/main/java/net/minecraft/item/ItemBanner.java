package net.minecraft.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemBanner extends ItemBlock {

    private static String[] packedTextFragments;

    private EnumDyeColor getBaseColor(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getSubCompound("BlockEntityTag", false);
        EnumDyeColor enumDyeColor = null;
        if (compoundTag != null && compoundTag.hasKey("Base")) {
            enumDyeColor = EnumDyeColor.byDyeDamage(compoundTag.getInteger("Base"));
        } else {
            enumDyeColor = EnumDyeColor.byDyeDamage(itemStack.getItemDamage());
        }

        return enumDyeColor;
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
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (facing == Facing.DOWN) {
            return false;
        }

        if (!world.getBlockState(blockPos).getBlock().getMaterial().isSolid()) {
            return false;
        }

        blockPos = blockPos.offset(facing);
        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)) {
            return false;
        }

        if (!Blocks.standing_banner.canPlaceBlockAt(world, blockPos)) {
            return false;
        }

        if (world.isRemote) {
            return true;
        }

        {
            if (facing == Facing.UP) {
                int i =
                        MathHelper.floor((playerEntity.rotationYaw + 180.0F) * 16.0F / 360.0F + 0.5)
                                & 15;
                world.setBlockState(
                        blockPos,
                        Blocks.standing_banner
                                .getDefaultState()
                                .withProperty(BlockStandingSign.ROTATION_PROP, i),
                        3);
                if (!(text == null)) {

                    world.setBlockState(
                            blockPos,
                            Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.FACING, facing),
                            3);
                }
            } else {

                world.setBlockState(
                        blockPos,
                        Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.FACING, facing),
                        3);
            }
        }

        itemStack.stackSize--;
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityBanner) {
            ((TileEntityBanner) tileEntity).setItemValues(itemStack);
        }

        return true;
    }

    public CreativeTabs getCreativeTab() {
        return CreativeTabs.tabDecorations;
    }

    static {
        net.minecraft.item.ItemBanner.packedTextFragments = new String[3];
        net.minecraft.item.ItemBanner.packedTextFragments[0] = new String(".");
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        EnumDyeColor[] enumDyeColorArray = EnumDyeColor.values();
        String textValue = text;

        for (EnumDyeColor enumDyeColor : enumDyeColorArray) {
            list.add(new ItemStack(item, 1, enumDyeColor.getDyeColorDamage()));
            if (textValue != null) {
                break;
            }
        }
    }

    public ItemBanner() {
        super(Blocks.standing_banner);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    public void addInformation(
            ItemStack itemStack, PlayerEntity playerEntity, List list, boolean flag) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        CompoundTag compoundTag = itemStack.getSubCompound("BlockEntityTag", false);
        String textValue = text;
        if (compoundTag != null && compoundTag.hasKey("Patterns")) {
            NBTTagList nbtTagList = compoundTag.getTagList("Patterns", 10);
            int i = 0;

            while (i < nbtTagList.tagCount() && i < 6) {
                CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
                EnumDyeColor enumDyeColor =
                        EnumDyeColor.byDyeDamage(compoundTagValue.getInteger("Color"));
                BannerPattern patternByID =
                        BannerPattern.getPatternByID(compoundTagValue.getString("Pattern"));
                if (patternByID != null) {
                    list.add(
                            StatCollector.translateToLocal(
                                    "item.banner."
                                            + patternByID.getPatternName()
                                            + packedTextFragments[0]
                                            + enumDyeColor.getUnlocalizedName()));
                }

                i++;
                if (textValue != null) {
                    break;
                }
            }
        }
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        String text = "item.banner.";
        EnumDyeColor enumDyeColor = this.getBaseColor(itemStack);
        text = text + enumDyeColor.getUnlocalizedName() + ".name";
        return StatCollector.translateToLocal(text);
    }

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        if (i == 0) {
            return 16777215;
        }

        EnumDyeColor enumDyeColor = this.getBaseColor(itemStack);
        return enumDyeColor.getMapColor().colorValue;
    }
}
