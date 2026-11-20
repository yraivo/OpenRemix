package net.minecraft.init;

import net.minecraft.block.BlockDispenser;
import net.minecraft.block.BlockSkull;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.init.Bootstrap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.mojang.authlib.GameProfile;

import java.util.UUID;

public final class BootstrapBlockDispenserDispenserBehavior5 extends BehaviorDefaultDispenseItem {

    private boolean succeeded = true;


    protected void playDispenseSound(IBlockSource iBlockSource) {
        int i = Bootstrap.f();
        if (this.succeeded) {
            iBlockSource.getWorld().playAuxSFX(1000, iBlockSource.getBlockPos(), 0);
            if (i == 0) {
                return;
            }
        }

        iBlockSource.getWorld().playAuxSFX(1001, iBlockSource.getBlockPos(), 0);
    }

    protected ItemStack dispenseStack(IBlockSource iBlockSource, ItemStack itemStack) {
        World world = iBlockSource.getWorld();
        int j = Bootstrap.cR();
        Facing facing = BlockDispenser.getFacing(iBlockSource.getBlockMetadata());
        BlockPos blockPos = iBlockSource.getBlockPos().offset(facing);
        int i = j;
        BlockSkull boo = Blocks.skull;
        if (world.isAirBlock(blockPos)
                && boo.canDispenserPlace(world, blockPos, itemStack)) {
            if (world.isRemote) {
                return itemStack;
            }

            world.setBlockState(
                    blockPos, boo.getDefaultState().withProperty(BlockSkull.FACING, Facing.UP), 3);
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntitySkull) {
                {
                    if (itemStack.getItemDamage() == 3) {
                        GameProfile gameprofile = null;
                        if (itemStack.hasTagCompound()) {
                            CompoundTag tag = itemStack.getTag();
                            if (tag.hasKey("SkullOwner", 10)) {
                                gameprofile =
                                        NBTUtil.readGameProfileFromNBT(
                                                tag.getCompoundTag("SkullOwner"));
                            } else if (tag.hasKey("SkullOwner", 8)) {
                                gameprofile =
                                        new GameProfile((UUID) null, tag.getString("SkullOwner"));
                            }
                        }

                        ((TileEntitySkull) tileEntity).setPlayerProfile(gameprofile);
                        if (!(i != 0)) {

                            ((TileEntitySkull) tileEntity).setType(itemStack.getItemDamage());
                        }
                    } else {

                        ((TileEntitySkull) tileEntity).setType(itemStack.getItemDamage());
                    }
                }

                ((TileEntitySkull) tileEntity)
                        .setSkullRotation(facing.getOpposite().getHorizontalIndex() * 4);
                Blocks.skull.checkWitherSpawn(
                        world, blockPos, (TileEntitySkull) tileEntity);
            }

            itemStack.stackSize--;
            if (i != 0) {
                return itemStack;
            }
        }

        this.succeeded = false;
        return itemStack;
    }
}
