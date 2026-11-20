package net.minecraft.block.state;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockPistonStructureHelper {
    private BlockPos pistonPos;
    private List toMove;
    private BlockPos blockToMove;
    private World world;
    private List toDestroy;
    private Facing moveDirection;

    public List getBlocksToMove() {
        return this.toMove;
    }

    private void method_80c3ce64938e63de8c07(int i, int j) {
        ArrayList arrayList = Lists.newArrayList();
        ArrayList arrayListValue = Lists.newArrayList();
        ArrayList arrayListValue2 = Lists.newArrayList();
        arrayList.addAll(this.toMove.subList(0, j));
        arrayListValue.addAll(this.toMove.subList(this.toMove.size() - i, this.toMove.size()));
        arrayListValue2.addAll(this.toMove.subList(j, this.toMove.size() - i));
        this.toMove.clear();
        this.toMove.addAll(arrayList);
        this.toMove.addAll(arrayListValue);
        this.toMove.addAll(arrayListValue2);
    }

    public List getBlocksToDestroy() {
        return this.toDestroy;
    }

    public boolean aY() {
        int[] aint1 = BlockStateContainer.xZ();
        this.toMove.clear();
        int[] aint = aint1;
        this.toDestroy.clear();
        Block block = this.world.getBlockState(this.blockToMove).getBlock();
        if (!BlockPistonBase.canPush(
                block, this.world, this.blockToMove, this.moveDirection, false)) {
            if (block.getMobilityFlag() != 1) {
                return false;
            }

            this.toDestroy.add(this.blockToMove);
            return true;
        } else {
            if (!this.method_51a343cb461860600ef7(this.blockToMove)) {
                return false;
            }

            int i = 0;

            while (i < this.toMove.size()) {
                BlockPos blockPos = (BlockPos) this.toMove.get(i);
                if (this.world.getBlockState(blockPos).getBlock() == Blocks.slime_block
                        && !this.method_08c26e1ff2b44fcfa823(blockPos)) {
                    return false;
                }

                i++;
                if (aint != null) {
                    break;
                }
            }

            if (Entity.getLegacyGlobalFlowMarker() != null) {
                BlockStateContainer.b(new int[5]);
            }

            return true;
        }
    }

    private boolean method_51a343cb461860600ef7(BlockPos blockPos) {
        int[] aint1 = BlockStateContainer.xZ();
        Block block = this.world.getBlockState(blockPos).getBlock();
        int[] aint = aint1;
        if (block.getMaterial() == Material.AIR) {
            return true;
        }

        if (!BlockPistonBase.canPush(block, this.world, blockPos, this.moveDirection, false)) {
            return true;
        }

        if (blockPos.equals(this.pistonPos)) {
            return true;
        }

        if (this.toMove.contains(blockPos)) {
            return true;
        }

        int i = 1;
        if (i + this.toMove.size() > 12) {
            return false;
        }

        while (block == Blocks.slime_block) {
            BlockPos blockPosValue = blockPos.offset(this.moveDirection.getOpposite(), i);
            block = this.world.getBlockState(blockPosValue).getBlock();
            if (block.getMaterial() == Material.AIR
                    || !BlockPistonBase.canPush(
                            block, this.world, blockPosValue, this.moveDirection, false)
                    || blockPosValue.equals(this.pistonPos) && aint == null) {
                break;
            }

            if (++i + this.toMove.size() > 12) {
                return false;
            }

            if (aint != null) {
                break;
            }
        }

        int i1 = 0;
        int j = i - 1;

        while (j >= 0) {
            this.toMove.add(blockPos.offset(this.moveDirection.getOpposite(), j));
            i1++;
            j--;
            if (aint != null) {
                break;
            }
        }

        j = 1;

        while (true) {
            BlockPos blockPosValue2 = blockPos.offset(this.moveDirection, j);
            int k = this.toMove.indexOf(blockPosValue2);
            if (k > -1) {
                this.method_80c3ce64938e63de8c07(i1, k);
                int l = 0;

                while (l <= k + i1) {
                    BlockPos blockPosValue3 = (BlockPos) this.toMove.get(l);
                    if (this.world.getBlockState(blockPosValue3).getBlock() == Blocks.slime_block
                            && !this.method_08c26e1ff2b44fcfa823(blockPosValue3)) {
                        return false;
                    }

                    l++;
                    if (aint != null) {
                        break;
                    }
                }

                return true;
            }

            block = this.world.getBlockState(blockPosValue2).getBlock();
            if (block.getMaterial() == Material.AIR) {
                return true;
            }

            if (!BlockPistonBase.canPush(
                            block, this.world, blockPosValue2, this.moveDirection, true)
                    || blockPosValue2.equals(this.pistonPos)) {
                return false;
            }

            if (block.getMobilityFlag() == 1) {
                this.toDestroy.add(blockPosValue2);
                return true;
            }

            if (this.toMove.size() >= 12) {
                return false;
            }

            this.toMove.add(blockPosValue2);
            i1++;
            j++;
        }
    }

    public BlockPistonStructureHelper(World world, BlockPos blockPos, Facing facing, boolean flag) {
        super();
        int[] initialFlowMarker = BlockStateContainer.xZ();

        int[] aint1 = initialFlowMarker;

        int[] aint = aint1;
        this.toMove = Lists.newArrayList();
        this.toDestroy = Lists.newArrayList();
        this.world = world;
        this.pistonPos = blockPos;
        if (flag) {
            this.moveDirection = facing;
            this.blockToMove = blockPos.offset(facing);
            if (aint == null) {
                return;
            }
        }

        this.moveDirection = facing.getOpposite();
        this.blockToMove = blockPos.offset(facing, 2);
    }

    private boolean method_08c26e1ff2b44fcfa823(BlockPos blockPos) {
        int[] aint1 = BlockStateContainer.xZ();
        Facing[] facingArray = Facing.values();
        int[] aint = aint1;

        for (Facing facing : facingArray) {
            if (facing.getAxis() != this.moveDirection.getAxis()
                    && !this.method_51a343cb461860600ef7(blockPos.offset(facing))) {
                return false;
            }

            if (aint != null) {
                break;
            }
        }

        return true;
    }
}
