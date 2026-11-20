package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.Random;

public class BlockCommandBlock extends BlockContainer {

    public static PropertyBool TRIGGERED_PROP;

    public void updateTick(World world, BlockPos blockPos, BlockState blockState, Random random) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityCommandBlock) {
            ((TileEntityCommandBlock) tileEntity).getCommandBlockLogic().trigger(world);
            world.updateComparatorOutputLevel(blockPos, this);
        }
    }

    static {
        net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP = PropertyBool.create("triggered");
    }

    public TileEntity createNewTileEntity(World world, int i) {
        return new TileEntityCommandBlock();
    }

    public BlockCommandBlock() {
        super(Material.iron);
        this.setDefaultState(
                this.blockState
                        .getBaseState()
                        .withProperty(net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP, false));
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(
                this, new IProperty[] {net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP});
    }

    public boolean hasComparatorInputOverride() {
        return true;
    }

    public BlockState onBlockPlaced(
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2,
            int i,
            LivingEntity livingEntity) {
        return this.getDefaultState().withProperty(net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP, false);
    }

    public void onNeighborBlockChange(
            World world, BlockPos blockPos, BlockState blockState, Block block) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isRemote) {
            boolean blockPowered = world.isBlockPowered(blockPos);
            boolean value = (Boolean) blockState.getValue(net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP);
            if (blockPowered && !value) {
                world.setBlockState(
                        blockPos,
                        blockState.withProperty(net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP, true),
                        4);
                world.scheduleUpdate(blockPos, this, this.tickRate(world));
                if (i != 0) {
                    return;
                }
            }

            if (!blockPowered && value) {
                world.setBlockState(
                        blockPos,
                        blockState.withProperty(net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP, false),
                        4);
            }
        }
    }

    public int getMetaFromState(BlockState blockState) {
        byte b0 = 0;
        if ((Boolean) blockState.getValue(net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP)) {
            b0 |= 1;
        }

        return b0;
    }

    public void onBlockPlacedBy(
            World world,
            BlockPos blockPos,
            BlockState blockState,
            LivingEntity livingEntity,
            ItemStack itemStack) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        if (tileEntity instanceof TileEntityCommandBlock) {
            CommandBlockLogic commandBlockLogic =
                    ((TileEntityCommandBlock) tileEntity).getCommandBlockLogic();
            if (itemStack.hasDisplayName()) {
                commandBlockLogic.setName(itemStack.getDisplayName());
            }

            if (!world.isRemote) {
                commandBlockLogic.setTrackOutput(
                        world.getGameRules().getGameRuleBooleanValue("sendCommandFeedback"));
            }
        }
    }

    public int getComparatorInputOverride(World world, BlockPos blockPos) {
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntityCommandBlock
                ? ((TileEntityCommandBlock) tileEntity).getCommandBlockLogic().getSuccessCount()
                : 0;
    }

    public int getRenderType() {
        return 3;
    }

    public int tickRate(World world) {
        return 1;
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
        TileEntity tileEntity = world.getTileEntity(blockPos);
        return tileEntity instanceof TileEntityCommandBlock
                ? ((TileEntityCommandBlock) tileEntity)
                        .getCommandBlockLogic()
                        .tryOpenEditCommandBlock(playerEntity)
                : false;
    }

    public BlockState getStateFromMeta(int i) {
        return this.getDefaultState()
                .withProperty(net.minecraft.block.BlockCommandBlock.TRIGGERED_PROP, (i & 1) > 0);
    }
}
