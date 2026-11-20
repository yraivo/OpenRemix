package net.minecraft.world.gen.structure;

import net.minecraft.block.BlockSandStoneEnumType;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public abstract class StructureVillagePiecesVillage extends StructureComponent {
    private boolean isDesertVillage;

    protected int ef = -1;
    private int villagersSpawned;

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        compoundTag.setInteger("HPos", this.ef);
        compoundTag.setInteger("VCount", this.villagersSpawned);
        compoundTag.setBoolean("Desert", this.isDesertVillage);
    }

    protected void spawnVillagers(
            World world, StructureBoundingBox structureBoundingBox, int i, int j, int k, int l) {
        int i1 = StructureComponent.f();
        if (this.villagersSpawned < l) {
            int j1 = this.villagersSpawned;

            while (j1 < l) {
                int k1 = this.getXWithOffset(i + j1, k);
                int l1 = this.getYWithOffset(j);
                int zWithOffset = this.getZWithOffset(i + j1, k);
                if (!structureBoundingBox.isVecInside(new BlockPos(k1, l1, zWithOffset))
                        && i1 == 0) {
                    break;
                }

                this.villagersSpawned++;
                EntityVillager entityVillager = new EntityVillager(world);
                entityVillager.setLocationAndAngles(k1 + 0.5, l1, zWithOffset + 0.5, 0.0F, 0.0F);
                entityVillager.onInitialSpawn(
                        world.getDifficultyForLocation(new BlockPos(entityVillager)),
                        (IEntityLivingData) null);
                entityVillager.setProfession(
                        this.method_eea4c703766e71e0df14(
                                j1, entityVillager.jvm171_f_bd9fdf88541948()));
                world.spawnEntityInWorld(entityVillager);
                j1++;
                if (i1 != 0) {
                    break;
                }
            }
        }
    }

    protected StructureComponent getNextComponentPP(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j) {
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    return StructureVillagePieces.method_ed85da7976afa3b7220a(
                            structureVillagePiecesStart,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.EAST,
                            this.getComponentType());
                case SOUTH:
                    return StructureVillagePieces.method_ed85da7976afa3b7220a(
                            structureVillagePiecesStart,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.EAST,
                            this.getComponentType());
                case WEST:
                    return StructureVillagePieces.method_ed85da7976afa3b7220a(
                            structureVillagePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            this.getComponentType());
                case EAST:
                    return StructureVillagePieces.method_ed85da7976afa3b7220a(
                            structureVillagePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            this.getComponentType());
            }
        }

        return null;
    }

    public StructureVillagePiecesVillage(
            StructureVillagePiecesStart structureVillagePiecesStart, int i) {
        super(i);
        if (structureVillagePiecesStart != null) {
            this.isDesertVillage = structureVillagePiecesStart.inDesert;
        }
    }

    protected void setBlockState(
            World world,
            BlockState blockState,
            int i,
            int j,
            int k,
            StructureBoundingBox structureBoundingBox) {
        BlockState blockStateValue = this.method_bfd0bbcb6bd28da56f06(blockState);
        super.setBlockState(world, blockStateValue, i, j, k, structureBoundingBox);
    }

    protected void replaceAirAndLiquidDownwards(
            World world,
            BlockState blockState,
            int i,
            int j,
            int k,
            StructureBoundingBox structureBoundingBox) {
        BlockState blockStateValue = this.method_bfd0bbcb6bd28da56f06(blockState);
        super.replaceAirAndLiquidDownwards(world, blockStateValue, i, j, k, structureBoundingBox);
    }

    public StructureVillagePiecesVillage() {}

    public static boolean canVillageGoDeeper(StructureBoundingBox structureBoundingBox) {
        return structureBoundingBox != null && structureBoundingBox.minY > 10;
    }


    protected int method_eea4c703766e71e0df14(int j, int i) {
        return i;
    }

    protected StructureComponent getNextComponentNN(
            StructureVillagePiecesStart structureVillagePiecesStart,
            List list,
            Random random,
            int i,
            int j) {
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    return StructureVillagePieces.method_ed85da7976afa3b7220a(
                            structureVillagePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.WEST,
                            this.getComponentType());
                case SOUTH:
                    return StructureVillagePieces.method_ed85da7976afa3b7220a(
                            structureVillagePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX - 1,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ + j,
                            Facing.WEST,
                            this.getComponentType());
                case WEST:
                    return StructureVillagePieces.method_ed85da7976afa3b7220a(
                            structureVillagePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            this.getComponentType());
                case EAST:
                    return StructureVillagePieces.method_ed85da7976afa3b7220a(
                            structureVillagePiecesStart,
                            list,
                            random,
                            this.boundingBox.minX + j,
                            this.boundingBox.minY + i,
                            this.boundingBox.minZ - 1,
                            Facing.NORTH,
                            this.getComponentType());
            }
        }

        return null;
    }

    protected void setDesertVillage(boolean flag) {
        this.isDesertVillage = flag;
    }

    protected void fillWithBlocks(
            World world,
            StructureBoundingBox structureBoundingBox,
            int i,
            int j,
            int k,
            int l,
            int i1,
            int j1,
            BlockState blockState,
            BlockState blockStateValue,
            boolean flag) {
        BlockState blockStateValue2 = this.method_bfd0bbcb6bd28da56f06(blockState);
        BlockState blockStateValue3 = this.method_bfd0bbcb6bd28da56f06(blockStateValue);
        super.fillWithBlocks(
                world,
                structureBoundingBox,
                i,
                j,
                k,
                l,
                i1,
                j1,
                blockStateValue2,
                blockStateValue3,
                flag);
    }

    protected void readStructureFromNBT(CompoundTag compoundTag) {
        this.ef = compoundTag.getInteger("HPos");
        this.villagersSpawned = compoundTag.getInteger("VCount");
        this.isDesertVillage = compoundTag.getBoolean("Desert");
    }

    protected int getAverageGroundLevel(World world, StructureBoundingBox structureBoundingBox) {
        int j = 0;
        int j1 = StructureComponent.f();
        int k = 0;
        int i = j1;
        int l = this.boundingBox.minZ;

        while (l <= this.boundingBox.maxZ) {
            int minX = this.boundingBox.minX;

            while (true) {
                if (minX <= this.boundingBox.maxX) {
                    BlockPos blockPos = new BlockPos(minX, 64, l);
                    if (structureBoundingBox.isVecInside(blockPos)) {
                        j +=
                                Math.max(
                                        world.getTopSolidOrLiquidBlock(blockPos).getY(),
                                        world.provider.getAverageGroundLevel());
                        k++;
                    }

                    minX++;
                    if (i == 0) {
                        continue;
                    }
                }

                l++;
                if (i != 0) {
                    return k == 0 ? -1 : j / k;
                }
                break;
            }
        }

        return k == 0 ? -1 : j / k;
    }

    protected BlockState method_bfd0bbcb6bd28da56f06(BlockState blockState) {
        if (this.isDesertVillage) {
            if (blockState.getBlock() == Blocks.log || blockState.getBlock() == Blocks.log2) {
                return Blocks.sandstone.getDefaultState();
            }

            if (blockState.getBlock() == Blocks.cobblestone) {
                return Blocks.sandstone.getStateFromMeta(
                        BlockSandStoneEnumType.DEFAULT.getMetadata());
            }

            if (blockState.getBlock() == Blocks.planks) {
                return Blocks.sandstone.getStateFromMeta(
                        BlockSandStoneEnumType.SMOOTH.getMetadata());
            }

            if (blockState.getBlock() == Blocks.oak_stairs) {
                return Blocks.sandstone_stairs
                        .getDefaultState()
                        .withProperty(BlockStairs.FACING, blockState.getValue(BlockStairs.FACING));
            }

            if (blockState.getBlock() == Blocks.stone_stairs) {
                return Blocks.sandstone_stairs
                        .getDefaultState()
                        .withProperty(BlockStairs.FACING, blockState.getValue(BlockStairs.FACING));
            }

            if (blockState.getBlock() == Blocks.gravel) {
                return Blocks.sandstone.getDefaultState();
            }
        }

        return blockState;
    }
}
