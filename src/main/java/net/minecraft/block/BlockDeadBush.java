package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class BlockDeadBush extends BlockBush {
    public boolean isReplaceable(World world, BlockPos blockPos) {
        return true;
    }

    protected BlockDeadBush() {
        super(Material.vine);
        float f = 0.4F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, 0.8F, 0.5F + f);
    }

    public Item getItemDropped(BlockState blockState, Random random, int i) {
        return null;
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.sand
                || block == Blocks.hardened_clay
                || block == Blocks.stained_hardened_clay
                || block == Blocks.dirt;
    }

    public void harvestBlock(
            World world,
            PlayerEntity playerEntity,
            BlockPos blockPos,
            BlockState blockState,
            TileEntity tileEntity) {
        int i = BlockPlanksEnumType.getLegacyBlockFlowToken();
        if (!world.isRemote
                && playerEntity.getCurrentEquippedItem() != null
                && playerEntity.getCurrentEquippedItem().getItem() == Items.shears) {
            playerEntity.triggerAchievement(
                    StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
            spawnAsEntity(world, blockPos, new ItemStack(Blocks.deadbush, 1, 0));
            if (i != 0) {
                return;
            }
        }

        super.harvestBlock(world, playerEntity, blockPos, blockState, tileEntity);
    }
}
