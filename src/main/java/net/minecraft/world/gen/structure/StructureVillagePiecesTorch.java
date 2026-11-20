package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureVillagePiecesVillage;

import java.util.List;
import java.util.Random;

public class StructureVillagePiecesTorch extends StructureVillagePiecesVillage {
    public static StructureBoundingBox findPieceBox(
            StructureVillagePiecesStart p171_0,
            List list,
            Random random,
            int p171_3,
            int p171_4,
            int p171_5,
            Facing facing) {
        net.minecraft.world.gen.structure.StructureBoundingBox structureBoundingBox =
                net.minecraft.world.gen.structure.StructureBoundingBox.getComponentToAddBoundingBox(
                        p171_3, p171_4, p171_5, 0, 0, 0, 3, 4, 2, facing);
        return net.minecraft.world.gen.structure.StructureComponent.findIntersecting(list, structureBoundingBox) != null
                ? null
                : structureBoundingBox;
    }

    public StructureVillagePiecesTorch() {}

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        if (this.ef < 0) {
            this.ef = this.getAverageGroundLevel(world, structureBoundingBox);
            if (this.ef < 0) {
                return true;
            }

            this.boundingBox.offset(0, this.ef - this.boundingBox.maxY + 4 - 1, 0);
        }

        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                2,
                3,
                1,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 1, 0, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 1, 1, 0, structureBoundingBox);
        this.setBlockState(
                world, Blocks.oak_fence.getDefaultState(), 1, 2, 0, structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.wool.getStateFromMeta(EnumDyeColor.WHITE.getDyeColorDamage()),
                1,
                3,
                0,
                structureBoundingBox);
        int i = this.coordBaseMode != Facing.EAST && this.coordBaseMode != Facing.NORTH ? 0 : 1;
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.rotateY()),
                i != 0 ? 2 : 0,
                3,
                0,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode),
                1,
                3,
                1,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.rotateYCCW()),
                i != 0 ? 0 : 2,
                3,
                0,
                structureBoundingBox);
        this.setBlockState(
                world,
                Blocks.torch
                        .getDefaultState()
                        .withProperty(BlockTorch.FACING_PROP, this.coordBaseMode.getOpposite()),
                1,
                3,
                -1,
                structureBoundingBox);
        return true;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public StructureVillagePiecesTorch(
            StructureVillagePiecesStart structureVillagePiecesStart,
            int i,
            java.util.Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue) {
        super(structureVillagePiecesStart, i);
        this.coordBaseMode = aAcValue;
        this.boundingBox = bMdValue;
    }
}
