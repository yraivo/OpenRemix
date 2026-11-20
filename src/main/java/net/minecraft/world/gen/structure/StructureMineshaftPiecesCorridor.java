package net.minecraft.world.gen.structure;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class StructureMineshaftPiecesCorridor extends StructureComponent {

    private int sectionCount;
    private boolean spawnerPlaced;

    private boolean hasSpiders;
    private boolean hasRails;


    protected void readStructureFromNBT(CompoundTag compoundTag) {
        this.hasRails = compoundTag.getBoolean("hr");
        this.hasSpiders = compoundTag.getBoolean("sc");
        this.spawnerPlaced = compoundTag.getBoolean("hps");
        this.sectionCount = compoundTag.getInteger("Num");
    }

    protected void writeStructureToNBT(CompoundTag compoundTag) {
        compoundTag.setBoolean("hr", this.hasRails);
        compoundTag.setBoolean("sc", this.hasSpiders);
        compoundTag.setBoolean("hps", this.spawnerPlaced);
        compoundTag.setInteger("Num", this.sectionCount);
    }

    public void buildComponent(StructureComponent structureComponent, List list, Random random) {
        int l1 = StructureComponent.f();
        int j = this.getComponentType();
        int i = l1;
        int k = random.nextInt(4);
        if (this.coordBaseMode != null) {
            switch (this.coordBaseMode) {
                case NORTH:
                    if (k <= 1) {
                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.minX,
                                this.boundingBox.minY - 1 + random.nextInt(3),
                                this.boundingBox.minZ - 1,
                                this.coordBaseMode,
                                j);
                        if (i == 0) {
                            break;
                        }
                    }

                    if (k == 2) {
                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.minX - 1,
                                this.boundingBox.minY - 1 + random.nextInt(3),
                                this.boundingBox.minZ,
                                Facing.WEST,
                                j);
                        if (i == 0) {
                            break;
                        }
                    }

                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY - 1 + random.nextInt(3),
                            this.boundingBox.minZ,
                            Facing.EAST,
                            j);
                    if (i == 0) {
                        break;
                    }
                case SOUTH:
                    if (k <= 1) {
                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.minX,
                                this.boundingBox.minY - 1 + random.nextInt(3),
                                this.boundingBox.maxZ + 1,
                                this.coordBaseMode,
                                j);
                        if (i == 0) {
                            break;
                        }
                    }

                    if (k == 2) {
                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.minX - 1,
                                this.boundingBox.minY - 1 + random.nextInt(3),
                                this.boundingBox.maxZ - 3,
                                Facing.WEST,
                                j);
                        if (i == 0) {
                            break;
                        }
                    }

                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.maxX + 1,
                            this.boundingBox.minY - 1 + random.nextInt(3),
                            this.boundingBox.maxZ - 3,
                            Facing.EAST,
                            j);
                    if (i == 0) {
                        break;
                    }
                case WEST:
                    if (k <= 1) {
                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.minX - 1,
                                this.boundingBox.minY - 1 + random.nextInt(3),
                                this.boundingBox.minZ,
                                this.coordBaseMode,
                                j);
                        if (i == 0) {
                            break;
                        }
                    }

                    if (k == 2) {
                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.minX,
                                this.boundingBox.minY - 1 + random.nextInt(3),
                                this.boundingBox.minZ - 1,
                                Facing.NORTH,
                                j);
                        if (i == 0) {
                            break;
                        }
                    }

                    StructureMineshaftPieces.method_6267762401a35bb3f241(
                            structureComponent,
                            list,
                            random,
                            this.boundingBox.minX,
                            this.boundingBox.minY - 1 + random.nextInt(3),
                            this.boundingBox.maxZ + 1,
                            Facing.SOUTH,
                            j);
                    if (i == 0) {
                        break;
                    }
                case EAST:
                    label111:
                    {
                        if (k <= 1) {
                            StructureMineshaftPieces.method_6267762401a35bb3f241(
                                    structureComponent,
                                    list,
                                    random,
                                    this.boundingBox.maxX + 1,
                                    this.boundingBox.minY - 1 + random.nextInt(3),
                                    this.boundingBox.minZ,
                                    this.coordBaseMode,
                                    j);
                            if (i == 0) {
                                break label111;
                            }
                        }

                        if (k == 2) {
                            StructureMineshaftPieces.method_6267762401a35bb3f241(
                                    structureComponent,
                                    list,
                                    random,
                                    this.boundingBox.maxX - 3,
                                    this.boundingBox.minY - 1 + random.nextInt(3),
                                    this.boundingBox.minZ - 1,
                                    Facing.NORTH,
                                    j);
                            if (i == 0) {
                                break label111;
                            }
                        }

                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.maxX - 3,
                                this.boundingBox.minY - 1 + random.nextInt(3),
                                this.boundingBox.maxZ + 1,
                                Facing.SOUTH,
                                j);
                    }
            }
        }

        if (j < 8) {
            if (this.coordBaseMode != Facing.NORTH && this.coordBaseMode != Facing.SOUTH) {
                int l = this.boundingBox.minX + 3;

                do {
                    if (l + 3 > this.boundingBox.maxX) {
                        return;
                    }

                    label66:
                    {
                        int i1 = random.nextInt(5);
                        if (i1 == 0) {
                            StructureMineshaftPieces.method_6267762401a35bb3f241(
                                    structureComponent,
                                    list,
                                    random,
                                    l,
                                    this.boundingBox.minY,
                                    this.boundingBox.minZ - 1,
                                    Facing.NORTH,
                                    j + 1);
                            if (i == 0) {
                                break label66;
                            }
                        }

                        if (i1 == 1) {
                            StructureMineshaftPieces.method_6267762401a35bb3f241(
                                    structureComponent,
                                    list,
                                    random,
                                    l,
                                    this.boundingBox.minY,
                                    this.boundingBox.maxZ + 1,
                                    Facing.SOUTH,
                                    j + 1);
                        }
                    }

                    l += 5;
                } while (i == 0);
            }

            int j1 = this.boundingBox.minZ + 3;

            while (j1 + 3 <= this.boundingBox.maxZ) {
                label53:
                {
                    int k1 = random.nextInt(5);
                    if (k1 == 0) {
                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.minX - 1,
                                this.boundingBox.minY,
                                j1,
                                Facing.WEST,
                                j + 1);
                        if (i == 0) {
                            break label53;
                        }
                    }

                    if (k1 == 1) {
                        StructureMineshaftPieces.method_6267762401a35bb3f241(
                                structureComponent,
                                list,
                                random,
                                this.boundingBox.maxX + 1,
                                this.boundingBox.minY,
                                j1,
                                Facing.EAST,
                                j + 1);
                    }
                }

                j1 += 5;
                if (i != 0) {
                    break;
                }
            }
        }
    }

    public StructureMineshaftPiecesCorridor(
            int i, Random random, StructureBoundingBox structureBoundingBox, Facing facing) {
        this(i, random, structureBoundingBox, facing, StructureComponent.f());
    }

    private StructureMineshaftPiecesCorridor(
            int i,
            Random random,
            StructureBoundingBox bMdValue,
            Facing aAcValue,
            int initialFlowToken) {
        super(i);

        int k = initialFlowToken;

        this.coordBaseMode = aAcValue;
        int j = k;
        this.boundingBox = bMdValue;
        this.hasRails =
                remix.compat.compiler.JvmBooleanSlots.fromInt((random.nextInt(3) == 0 ? 1 : 0));
        this.hasSpiders =
                remix.compat.compiler.JvmBooleanSlots.fromInt(
                        (!this.hasRails && random.nextInt(23) == 0 ? 1 : 0));
        if (this.coordBaseMode != Facing.NORTH && this.coordBaseMode != Facing.SOUTH) {
            this.sectionCount = bMdValue.getXSize() / 5;
            if (j == 0) {
                return;
            }
        }

        this.sectionCount = bMdValue.getZSize() / 5;
    }

    public boolean addComponentParts(
            World world, Random random, StructureBoundingBox structureBoundingBox) {
        int i = StructureComponent.f();
        if (this.isLiquidInStructureBoundingBox(world, structureBoundingBox)) {
            return false;
        }

        int j = this.sectionCount * 5 - 1;
        this.fillWithBlocks(
                world,
                structureBoundingBox,
                0,
                0,
                0,
                2,
                1,
                j,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        this.method_1b7f0bf882063fb4ee50(
                world,
                structureBoundingBox,
                random,
                0.8F,
                0,
                2,
                0,
                2,
                2,
                j,
                Blocks.air.getDefaultState(),
                Blocks.air.getDefaultState(),
                false);
        if (this.hasSpiders) {
            this.method_1b7f0bf882063fb4ee50(
                    world,
                    structureBoundingBox,
                    random,
                    0.6F,
                    0,
                    0,
                    0,
                    2,
                    1,
                    j,
                    Blocks.web.getDefaultState(),
                    Blocks.air.getDefaultState(),
                    false);
        }

        int k = 0;

        while (k < this.sectionCount) {
            int l;
            label84:
            {
                l = 2 + k * 5;
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        0,
                        0,
                        l,
                        0,
                        1,
                        l,
                        Blocks.oak_fence.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        2,
                        0,
                        l,
                        2,
                        1,
                        l,
                        Blocks.oak_fence.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
                if (random.nextInt(4) == 0) {
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            0,
                            2,
                            l,
                            0,
                            2,
                            l,
                            Blocks.planks.getDefaultState(),
                            Blocks.air.getDefaultState(),
                            false);
                    this.fillWithBlocks(
                            world,
                            structureBoundingBox,
                            2,
                            2,
                            l,
                            2,
                            2,
                            l,
                            Blocks.planks.getDefaultState(),
                            Blocks.air.getDefaultState(),
                            false);
                    if (i == 0) {
                        break label84;
                    }
                }

                this.fillWithBlocks(
                        world,
                        structureBoundingBox,
                        0,
                        2,
                        l,
                        2,
                        2,
                        l,
                        Blocks.planks.getDefaultState(),
                        Blocks.air.getDefaultState(),
                        false);
            }

            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.1F,
                    0,
                    2,
                    l - 1,
                    Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.1F,
                    2,
                    2,
                    l - 1,
                    Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.1F,
                    0,
                    2,
                    l + 1,
                    Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.1F,
                    2,
                    2,
                    l + 1,
                    Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.05F,
                    0,
                    2,
                    l - 2,
                    Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.05F,
                    2,
                    2,
                    l - 2,
                    Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.05F,
                    0,
                    2,
                    l + 2,
                    Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.05F,
                    2,
                    2,
                    l + 2,
                    Blocks.web.getDefaultState());
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.05F,
                    1,
                    2,
                    l - 1,
                    Blocks.torch.getStateFromMeta(Facing.UP.getIndex()));
            this.randomlyPlaceBlock(
                    world,
                    structureBoundingBox,
                    random,
                    0.05F,
                    1,
                    2,
                    l + 1,
                    Blocks.torch.getStateFromMeta(Facing.UP.getIndex()));
            if (random.nextInt(100) == 0) {
                this.generateChestContents(
                        world,
                        structureBoundingBox,
                        random,
                        2,
                        0,
                        l - 1,
                        WeightedRandomChestContent.func_177629_a(
                                StructureMineshaftPieces.fN(),
                                new WeightedRandomChestContent[] {
                                    Items.enchanted_book.getRandomEnchantedBook(random)
                                }),
                        3 + random.nextInt(4));
            }

            if (random.nextInt(100) == 0) {
                this.generateChestContents(
                        world,
                        structureBoundingBox,
                        random,
                        0,
                        0,
                        l + 1,
                        WeightedRandomChestContent.func_177629_a(
                                StructureMineshaftPieces.fN(),
                                new WeightedRandomChestContent[] {
                                    Items.enchanted_book.getRandomEnchantedBook(random)
                                }),
                        3 + random.nextInt(4));
            }

            if (this.hasSpiders && !this.spawnerPlaced) {
                int yWithOffset = this.getYWithOffset(0);
                int j1 = l - 1 + random.nextInt(3);
                int k1 = this.getXWithOffset(1, j1);
                j1 = this.getZWithOffset(1, j1);
                BlockPos blockPos = new BlockPos(k1, yWithOffset, j1);
                if (structureBoundingBox.isVecInside(blockPos)) {
                    this.spawnerPlaced = true;
                    world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), 2);
                    TileEntity tileEntity = world.getTileEntity(blockPos);
                    if (tileEntity instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner) tileEntity)
                                .getSpawnerBaseLogic()
                                .setEntityName("CaveSpider");
                    }
                }
            }

            k++;
            if (i != 0) {
                break;
            }
        }

        k = 0;

        label71:
        while (k <= 2) {
            int l1 = 0;

            while (true) {
                if (l1 <= j) {
                    byte b4 = -1;
                    BlockState blockState =
                            this.getBlockStateFromPos(world, k, b4, l1, structureBoundingBox);
                    if (blockState.getBlock().getMaterial() == Material.AIR) {
                        byte b5 = -1;
                        this.setBlockState(
                                world,
                                Blocks.planks.getDefaultState(),
                                k,
                                b5,
                                l1,
                                structureBoundingBox);
                    }

                    l1++;
                    if (i == 0) {
                        continue;
                    }
                }

                k++;
                if (i != 0) {
                    break label71;
                }
                break;
            }
        }

        if (this.hasRails) {
            k = 0;

            while (k <= j) {
                BlockState blockStateValue =
                        this.getBlockStateFromPos(world, 1, -1, k, structureBoundingBox);
                if (blockStateValue.getBlock().getMaterial() != Material.AIR
                        && blockStateValue.getBlock().isFullBlock()) {
                    this.randomlyPlaceBlock(
                            world,
                            structureBoundingBox,
                            random,
                            0.7F,
                            1,
                            0,
                            k,
                            Blocks.rail.getStateFromMeta(
                                    this.getMetadataWithOffset(Blocks.rail, 0)));
                }

                k++;
                if (i != 0) {
                    break;
                }
            }
        }

        return true;
    }

    public StructureMineshaftPiecesCorridor() {}

    public static StructureBoundingBox findPieceBox(
            List list, Random random, int i, int j, int k, Facing facing) {
        StructureBoundingBox structureBoundingBox = new StructureBoundingBox(i, j, k, i, j + 2, k);
        int k1 = StructureComponent.cR();
        int i1 = random.nextInt(3) + 2;
        int l = k1;

        while (i1 > 0) {
            int j1 = i1 * 5;
            switch (facing) {
                case NORTH:
                    structureBoundingBox.maxX = i + 2;
                    structureBoundingBox.minZ = k - (j1 - 1);
                    if (l != 0) {
                        break;
                    }
                case SOUTH:
                    structureBoundingBox.maxX = i + 2;
                    structureBoundingBox.maxZ = k + (j1 - 1);
                    if (l != 0) {
                        break;
                    }
                case WEST:
                    structureBoundingBox.minX = i - (j1 - 1);
                    structureBoundingBox.maxZ = k + 2;
                    if (l != 0) {
                        break;
                    }
                case EAST:
                    structureBoundingBox.maxX = i + (j1 - 1);
                    structureBoundingBox.maxZ = k + 2;
            }

            if (StructureComponent.findIntersecting(list, structureBoundingBox) == null && l != 0) {
                break;
            }

            i1--;
            if (l == 0) {
                break;
            }
        }

        return i1 > 0 ? structureBoundingBox : null;
    }

    protected boolean generateChestContents(
            World world,
            StructureBoundingBox structureBoundingBox,
            Random random,
            int i,
            int j,
            int k,
            List list,
            int l) {
        BlockPos blockPos =
                new BlockPos(
                        this.getXWithOffset(i, k),
                        this.getYWithOffset(j),
                        this.getZWithOffset(i, k));
        if (structureBoundingBox.isVecInside(blockPos)
                && world.getBlockState(blockPos).getBlock().getMaterial() == Material.AIR) {
            int i1 = random.nextBoolean() ? 1 : 0;
            world.setBlockState(
                    blockPos,
                    Blocks.rail.getStateFromMeta(this.getMetadataWithOffset(Blocks.rail, i1)),
                    2);
            EntityMinecartChest entityMinecartChest =
                    new EntityMinecartChest(
                            world,
                            blockPos.getX() + 0.5F,
                            blockPos.getY() + 0.5F,
                            blockPos.getZ() + 0.5F);
            WeightedRandomChestContent.generateChestContents(random, list, entityMinecartChest, l);
            world.spawnEntityInWorld(entityMinecartChest);
            return true;
        } else {
            return false;
        }
    }
}
