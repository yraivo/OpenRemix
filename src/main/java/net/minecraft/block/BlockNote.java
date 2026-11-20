package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityNote;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.List;

public class BlockNote extends BlockContainer {

    private static List INSTRUMENTS;

    public boolean onBlockEventReceived(
            World world, BlockPos blockPos, BlockState blockState, int i, int j) {
        float f = (float) Math.pow(2.0, (j - 12) / 12.0);
        world.playSoundEffect(
                blockPos.getX() + 0.5,
                blockPos.getY() + 0.5,
                blockPos.getZ() + 0.5,
                "note." + this.getInstrument(i),
                3.0F,
                f);
        world.spawnParticle(
                EnumParticleTypes.NOTE,
                blockPos.getX() + 0.5,
                blockPos.getY() + 1.2,
                blockPos.getZ() + 0.5,
                j / 24.0,
                0.0,
                0.0,
                new int[0]);
        return true;
    }

    public int getRenderType() {
        return 3;
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityNote();
    }

    public BlockNote() {
        super(Material.wood);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    private String getInstrument(int i) {
        if (i < 0 || i >= net.minecraft.block.BlockNote.INSTRUMENTS.size()) {
            i = 0;
        }

        return (String) net.minecraft.block.BlockNote.INSTRUMENTS.get(i);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        boolean previousRedstoneState = world.isBlockPowered(blockPos);
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityNote) {
            TileEntityNote tileEntityNote = (TileEntityNote) tileEntity;
            if (tileEntityNote.previousRedstoneState != previousRedstoneState) {
                if (previousRedstoneState) {
                    tileEntityNote.triggerNote(world, blockPos);
                }

                tileEntityNote.previousRedstoneState = previousRedstoneState;
            }
        }
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
        if (world.isRemote) {
            return true;
        }

        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityNote) {
            TileEntityNote tileEntityNote = (TileEntityNote) tileEntity;
            tileEntityNote.changePitch();
            tileEntityNote.triggerNote(world, blockPos);
        }

        return true;
    }

    static {
        net.minecraft.block.BlockNote.INSTRUMENTS =
                Lists.newArrayList(new String[] {"harp", "bd", "snare", "hat", "bassattack"});
    }

    public void onBlockClicked(World world, BlockPos blockPos, PlayerEntity playerEntity) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityNote) {
                ((TileEntityNote) tileEntity).triggerNote(world, blockPos);
            }
        }
    }
}
