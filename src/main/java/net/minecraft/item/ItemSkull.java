package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import java.util.List;
import java.util.UUID;

public class ItemSkull extends Item {
    private static String[] packedTextFragments;

    private static String[] skullTypes;

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        int i = 0;
        String textValue = text;

        while (i < net.minecraft.item.ItemSkull.skullTypes.length) {
            list.add(new ItemStack(item, 1, i));
            i++;
            if (textValue != null) {
                break;
            }
        }
    }

    public String getUnlocalizedName(ItemStack itemStack) {
        int itemDamage = itemStack.getItemDamage();
        if (itemDamage < 0 || itemDamage >= net.minecraft.item.ItemSkull.skullTypes.length) {
            itemDamage = 0;
        }

        return super.getUnlocalizedName() + packedTextFragments[0] + net.minecraft.item.ItemSkull.skullTypes[itemDamage];
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        if (itemStack.getItemDamage() == 3 && itemStack.hasTagCompound()) {
            if (itemStack.getTag().hasKey("SkullOwner", 8)) {
                return StatCollector.translateToLocalFormatted(
                        "item.skull.player.name",
                        new Object[] {itemStack.getTag().getString("SkullOwner")});
            }

            if (itemStack.getTag().hasKey("SkullOwner", 10)) {
                CompoundTag compoundTag = itemStack.getTag().getCompoundTag("SkullOwner");
                if (compoundTag.hasKey("Name", 8)) {
                    return StatCollector.translateToLocalFormatted(
                            "item.skull.player.name", new Object[] {compoundTag.getString("Name")});
                }
            }
        }

        return super.getItemStackDisplayName(itemStack);
    }

    static {
        net.minecraft.item.ItemSkull.packedTextFragments = new String[3];
        net.minecraft.item.ItemSkull.packedTextFragments[0] = new String(".");
        net.minecraft.item.ItemSkull.skullTypes = new String[] {"skeleton", "wither", "zombie", "char", "creeper"};
    }

    public boolean updateItemStackNBT(CompoundTag compoundTag) {
        super.updateItemStackNBT(compoundTag);
        if (compoundTag.hasKey("SkullOwner", 8)
                && compoundTag.getString("SkullOwner").length() > 0) {
            GameProfile gameprofile =
                    new GameProfile((UUID) null, compoundTag.getString("SkullOwner"));
            gameprofile = TileEntitySkull.updateGameprofile(gameprofile);
            compoundTag.setTag(
                    "SkullOwner", NBTUtil.writeGameProfile(new CompoundTag(), gameprofile));
            return true;
        } else {
            return false;
        }
    }

    public int getMetadata(int i) {
        return i;
    }

    public ItemSkull() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
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

        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        boolean replaceable = block.isReplaceable(world, blockPos);
        if (!replaceable) {
            if (!world.getBlockState(blockPos).getBlock().getMaterial().isSolid()) {
                return false;
            }

            blockPos = blockPos.offset(facing);
        }

        if (!playerEntity.canPlayerEdit(blockPos, facing, itemStack)) {
            return false;
        }

        if (!Blocks.skull.canPlaceBlockAt(world, blockPos)) {
            return false;
        }

        if (!world.isRemote) {
            world.setBlockState(
                    blockPos,
                    Blocks.skull.getDefaultState().withProperty(BlockSkull.FACING, facing),
                    3);
            int i = 0;
            if (facing == Facing.UP) {
                i = MathHelper.floor(playerEntity.rotationYaw * 16.0F / 360.0F + 0.5) & 15;
            }

            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntitySkull) {
                TileEntitySkull tileEntitySkull;
                {
                    tileEntitySkull = (TileEntitySkull) tileEntity;
                    if (itemStack.getItemDamage() == 3) {
                        GameProfile gameprofile = null;
                        if (itemStack.hasTagCompound()) {
                            CompoundTag tag = itemStack.getTag();
                            if (tag.hasKey("SkullOwner", 10)) {
                                gameprofile =
                                        NBTUtil.readGameProfileFromNBT(
                                                tag.getCompoundTag("SkullOwner"));
                            } else if (tag.hasKey("SkullOwner", 8)
                                    && tag.getString("SkullOwner").length() > 0) {
                                gameprofile =
                                        new GameProfile((UUID) null, tag.getString("SkullOwner"));
                            }
                        }

                        tileEntitySkull.setPlayerProfile(gameprofile);
                        if (!(text == null)) {

                            tileEntitySkull.setType(itemStack.getItemDamage());
                        }
                    } else {

                        tileEntitySkull.setType(itemStack.getItemDamage());
                    }
                }

                tileEntitySkull.setSkullRotation(i);
                Blocks.skull.checkWitherSpawn(world, blockPos, tileEntitySkull);
            }

            itemStack.stackSize--;
        }

        return true;
    }
}
