package net.minecraft.world.gen.feature;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingPlane;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;

public class WorldGenDungeons extends WorldGenerator {
    private static List CHESTCONTENT;
    private static String[] SPAWNERTYPES;
    private static Logger bhd;

    private static String[] packedTextFragments;

    private String pickMobSpawner(Random random) {
        return net.minecraft.world.gen.feature.WorldGenDungeons.SPAWNERTYPES[random.nextInt(net.minecraft.world.gen.feature.WorldGenDungeons.SPAWNERTYPES.length)];
    }

    static {
        net.minecraft.world.gen.feature.WorldGenDungeons.packedTextFragments = new String[3];
        net.minecraft.world.gen.feature.WorldGenDungeons.packedTextFragments[0] = new String(")");
        bhd = LogManager.getLogger();
        net.minecraft.world.gen.feature.WorldGenDungeons.SPAWNERTYPES = new String[] {"Skeleton", "Zombie", "Zombie", "Spider"};
        net.minecraft.world.gen.feature.WorldGenDungeons.CHESTCONTENT =
                Lists.newArrayList(
                        new WeightedRandomChestContent[] {
                            new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 10),
                            new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 4, 10),
                            new WeightedRandomChestContent(Items.bread, 0, 1, 1, 10),
                            new WeightedRandomChestContent(Items.wheat, 0, 1, 4, 10),
                            new WeightedRandomChestContent(Items.gunpowder, 0, 1, 4, 10),
                            new WeightedRandomChestContent(Items.string, 0, 1, 4, 10),
                            new WeightedRandomChestContent(Items.bucket, 0, 1, 1, 10),
                            new WeightedRandomChestContent(Items.golden_apple, 0, 1, 1, 1),
                            new WeightedRandomChestContent(Items.redstone, 0, 1, 4, 10),
                            new WeightedRandomChestContent(Items.record_13, 0, 1, 1, 4),
                            new WeightedRandomChestContent(Items.record_cat, 0, 1, 1, 4),
                            new WeightedRandomChestContent(Items.name_tag, 0, 1, 1, 10),
                            new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 2),
                            new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 5),
                            new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)
                        });
    }

    public boolean generate(World world, Random random, BlockPos blockPos) {

        int i = random.nextInt(2) + 2;
        int j = -i - 1;
        int k = i + 1;
        String text = WorldGenerator.method_c60693f457aa70be7160();

        String textValue = text;

        int l = random.nextInt(2) + 2;
        int i1 = -l - 1;
        int j1 = l + 1;
        int k1 = 0;
        int l1 = j;

        label191:
        while (l1 <= k) {
            int i2 = -1;

            while (true) {
                if (i2 <= 4) {
                    int j2 = i1;

                    while (j2 <= j1) {
                        BlockPos blockPosValue = blockPos.add(l1, i2, j2);
                        Material material =
                                world.getBlockState(blockPosValue).getBlock().getMaterial();
                        boolean solid = material.isSolid();
                        if (i2 == -1 && !solid) {
                            return false;
                        }

                        if (i2 == 4 && !solid) {
                            return false;
                        }

                        if ((l1 == j || l1 == k || j2 == i1 || j2 == j1)
                                && i2 == 0
                                && world.isAirBlock(blockPosValue)
                                && world.isAirBlock(blockPosValue.offsetUp())) {
                            k1++;
                        }

                        j2++;
                        if (textValue == null) {
                            break;
                        }
                    }

                    i2++;
                    if (textValue != null) {
                        continue;
                    }
                }

                l1++;
                if (textValue == null) {
                    break label191;
                }
                break;
            }
        }

        if (k1 >= 1 && k1 <= 5) {
            l1 = j;

            label157:
            while (l1 <= k) {
                int l2 = 3;

                while (true) {
                    if (l2 >= -1) {
                        int j3 = i1;

                        while (j3 <= j1) {
                            label202:
                            {
                                BlockPos blockPosValue2 = blockPos.add(l1, l2, j3);
                                if (l1 != j && l2 != -1 && j3 != i1 && l1 != k && l2 != 4
                                        && j3 != j1) {
                                    if (world.getBlockState(blockPosValue2).getBlock()
                                            == Blocks.chest) {
                                        break label202;
                                    }

                                    world.setBlockToAir(blockPosValue2);
                                    if (textValue != null) {
                                        break label202;
                                    }
                                }

                                if (blockPosValue2.getY() >= 0
                                        && !world.getBlockState(blockPosValue2.offsetDown())
                                                .getBlock()
                                                .getMaterial()
                                                .isSolid()) {
                                    world.setBlockToAir(blockPosValue2);
                                    if (textValue != null) {
                                        break label202;
                                    }
                                }

                                if (world.getBlockState(blockPosValue2)
                                                .getBlock()
                                                .getMaterial()
                                                .isSolid()
                                        && world.getBlockState(blockPosValue2).getBlock()
                                                != Blocks.chest) {
                                    if (l2 == -1 && random.nextInt(4) != 0) {
                                        world.setBlockState(
                                                blockPosValue2,
                                                Blocks.mossy_cobblestone.getDefaultState(),
                                                2);
                                        if (!(textValue != null)) {

                                            world.setBlockState(
                                                    blockPosValue2,
                                                    Blocks.cobblestone.getDefaultState(),
                                                    2);
                                        }
                                    } else {

                                        world.setBlockState(
                                                blockPosValue2,
                                                Blocks.cobblestone.getDefaultState(),
                                                2);
                                    }
                                }
                            }

                            j3++;
                            if (textValue == null) {
                                break;
                            }
                        }

                        l2--;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    l1++;
                    if (textValue == null) {
                        break label157;
                    }
                    break;
                }
            }

            l1 = 0;

            label112:
            while (l1 < 2) {
                int i3 = 0;

                while (true) {
                    label108:
                    if (i3 < 3) {
                        int k3 = blockPos.getX() + random.nextInt(i * 2 + 1) - i;
                        int l3 = blockPos.getY();
                        int i4 = blockPos.getZ() + random.nextInt(l * 2 + 1) - l;
                        BlockPos blockPosValue3 = new BlockPos(k3, l3, i4);
                        if (world.isAirBlock(blockPosValue3)) {
                            int k2 = 0;

                            for (java.util.Iterator<?> __recoveryIterator171_15702 =
                                            (FacingPlane.HORIZONTAL).iterator();
                                    (__recoveryIterator171_15702).hasNext(); ) {
                                Facing facing = (Facing) (__recoveryIterator171_15702).next();
                                if (world.getBlockState(blockPosValue3.offset(facing))
                                        .getBlock()
                                        .getMaterial()
                                        .isSolid()) {
                                    k2++;
                                }

                                if (textValue == null) {
                                    break;
                                }
                            }

                            if (k2 == 1) {
                                world.setBlockState(
                                        blockPosValue3,
                                        Blocks.chest.correctFacing(
                                                world,
                                                blockPosValue3,
                                                Blocks.chest.getDefaultState()),
                                        2);
                                List list =
                                        WeightedRandomChestContent.func_177629_a(
                                                net.minecraft.world.gen.feature.WorldGenDungeons.CHESTCONTENT,
                                                new WeightedRandomChestContent[] {
                                                    Items.enchanted_book.getRandomEnchantedBook(
                                                            random)
                                                });
                                TileEntity tileEntity = world.getTileEntity(blockPosValue3);
                                if (!(tileEntity instanceof TileEntityChest)) {
                                    break label108;
                                }

                                WeightedRandomChestContent.generateChestContents(
                                        random, list, (TileEntityChest) tileEntity, 8);
                                if (textValue != null) {
                                    break label108;
                                }
                            }
                        }

                        i3++;
                        if (textValue != null) {
                            continue;
                        }
                    }

                    l1++;
                    if (textValue == null) {
                        break label112;
                    }
                    break;
                }
            }

            world.setBlockState(blockPos, Blocks.mob_spawner.getDefaultState(), 2);
            TileEntity tileEntityValue = world.getTileEntity(blockPos);
            if (tileEntityValue instanceof TileEntityMobSpawner) {
                ((TileEntityMobSpawner) tileEntityValue)
                        .getSpawnerBaseLogic()
                        .setEntityName(this.pickMobSpawner(random));
                if (textValue != null) {
                    return true;
                }
            }

            bhd.error(
                    "Failed to fetch mob spawner entity at ("
                            + blockPos.getX()
                            + ", "
                            + blockPos.getY()
                            + ", "
                            + blockPos.getZ()
                            + packedTextFragments[0]);
            return true;
        } else {
            return false;
        }
    }
}
