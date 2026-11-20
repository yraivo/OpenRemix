package net.minecraft.world;

import net.minecraft.block.BlockPortal;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.util.LongHashMap;
import net.minecraft.util.MathHelper;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Teleporter {
    private final LongHashMap destinationCoordinateCache = new LongHashMap();
    private Random random;
    private WorldServer worldServerInstance;
    private final List destinationCoordinateKeys = Lists.newArrayList();

    public void removeStalePortalLocations(long i) {
        World.getLegacyWorldFlowMarker();

        long k = i % 100L;

        if (k == 0L) {
            Iterator iterator = this.destinationCoordinateKeys.iterator();

            long j = i - 600L;

            while (iterator.hasNext()) {
                Long olong = (Long) iterator.next();
                TeleporterPortalPosition valueByKey =
                        (TeleporterPortalPosition) this.destinationCoordinateCache.getValueByKey(olong);
                if (valueByKey == null || valueByKey.lastUpdateTime < j) {
                    iterator.remove();
                    this.destinationCoordinateCache.remove(olong);
                }
            }
        }
    }

    public boolean placeInExistingPortal(Entity entity, float f) {
        int[] aint;
        double d0;
        byte b1;
        Object object;
        long k;
        {
            int[] aint1 = World.getLegacyWorldFlowMarker();
            d0 = -1.0;
            aint = aint1;
            int i = MathHelper.floor(entity.posX);
            int j = MathHelper.floor(entity.posZ);
            b1 = 1;
            object = BlockPos.ORIGIN;
            k = ChunkCoordIntPair.chunkXZ2Int(i, j);
            if (this.destinationCoordinateCache.containsItem(k)) {
                TeleporterPortalPosition valueByKey =
                        (TeleporterPortalPosition) this.destinationCoordinateCache.getValueByKey(k);
                d0 = 0.0;
                object = valueByKey;
                valueByKey.lastUpdateTime = this.worldServerInstance.getTotalWorldTime();
                b1 = 0;

                
            } else {

                BlockPos blockPos = new BlockPos(entity);
                int l = -128;

                while (l <= 128) {
                    int i1 = -128;

                    while (i1 <= 128) {
                        BlockPos blockPosValue =
                                blockPos.add(l, this.worldServerInstance.getActualHeight() - 1 - blockPos.getY(), i1);

                        while (blockPosValue.getY() >= 0) {
                            BlockPos blockPosValue2 = blockPosValue.offsetDown();
                            if (this.worldServerInstance.getBlockState(blockPosValue).getBlock() == Blocks.portal) {
                                while (this.worldServerInstance
                                                .getBlockState(
                                                        blockPosValue2 = blockPosValue.offsetDown())
                                                .getBlock()
                                        == Blocks.portal) {
                                    blockPosValue = blockPosValue2;
                                }

                                double d1 = blockPosValue.distanceSq(blockPos);
                                if (d0 < 0.0 || d1 < d0) {
                                    d0 = d1;
                                    object = blockPosValue;
                                }
                            }

                            blockPosValue = blockPosValue2;
                        }

                        i1++;
                    }

                    l++;
                }
            }
        }

        if (d0 >= 0.0) {
            if (b1 != 0) {
                this.destinationCoordinateCache.add(
                        k,
                        new TeleporterPortalPosition(
                                this, (BlockPos) object, this.worldServerInstance.getTotalWorldTime()));
                this.destinationCoordinateKeys.add(k);
            }

            double d4 = ((BlockPos) object).getX() + 0.5;
            double d5 = ((BlockPos) object).getY() + 0.5;
            double d6 = ((BlockPos) object).getZ() + 0.5;
            Facing facing = null;
            if (this.worldServerInstance.getBlockState(((BlockPos) object).offsetWest()).getBlock()
                    == Blocks.portal) {
                facing = Facing.NORTH;
            }

            if (this.worldServerInstance.getBlockState(((BlockPos) object).offsetEast()).getBlock()
                    == Blocks.portal) {
                facing = Facing.SOUTH;
            }

            if (this.worldServerInstance.getBlockState(((BlockPos) object).offsetNorth()).getBlock()
                    == Blocks.portal) {
                facing = Facing.EAST;
            }

            if (this.worldServerInstance.getBlockState(((BlockPos) object).offsetSouth()).getBlock()
                    == Blocks.portal) {
                facing = Facing.WEST;
            }

            {
                Facing facingValue = Facing.getHorizontal(entity.getTeleportDirection());
                if (facing != null) {
                    Facing facingValue2 = facing.rotateYCCW();
                    BlockPos blockPosValue3 = ((BlockPos) object).offset(facing);
                    boolean flag = this.method_51a343cb461860600ef7(blockPosValue3);
                    boolean flag1 =
                            this.method_51a343cb461860600ef7(blockPosValue3.offset(facingValue2));
                    if (flag1 && flag) {
                        object = ((BlockPos) object).offset(facingValue2);
                        facing = facing.getOpposite();
                        facingValue2 = facingValue2.getOpposite();
                        BlockPos blockPosValue4 = ((BlockPos) object).offset(facing);
                        flag = this.method_51a343cb461860600ef7(blockPosValue4);
                        flag1 =
                                this.method_51a343cb461860600ef7(
                                        blockPosValue4.offset(facingValue2));
                    }

                    float f1;
                    float f6;
                    {
                        f6 = 0.5F;
                        f1 = 0.5F;
                        if (!flag1 && flag) {
                            f6 = 1.0F;

                            
                        } else {

                            if (flag1 && !flag) {
                                f6 = 0.0F;


                            } else {

                                if (flag1) {
                                    f1 = 0.0F;
                                }
                            }
                        }
                    }

                    float f2;
                    float f3;
                    float f4;
                    float f5;
                    {
                        d4 = ((BlockPos) object).getX() + 0.5;
                        d5 = ((BlockPos) object).getY() + 0.5;
                        d6 = ((BlockPos) object).getZ() + 0.5;
                        d4 += facingValue2.getFrontOffsetX() * f6 + facing.getFrontOffsetX() * f1;
                        d6 += facingValue2.getFrontOffsetZ() * f6 + facing.getFrontOffsetZ() * f1;
                        f2 = 0.0F;
                        f3 = 0.0F;
                        f4 = 0.0F;
                        f5 = 0.0F;
                        if (facing == facingValue) {
                            f2 = 1.0F;
                            f3 = 1.0F;

                            
                        } else {

                            if (facing == facingValue.getOpposite()) {
                                f2 = -1.0F;
                                f3 = -1.0F;


                            } else {

                                if (facing == facingValue.rotateY()) {
                                    f4 = 1.0F;
                                    f5 = -1.0F;


                                } else {

                                    f4 = -1.0F;
                                    f5 = 1.0F;
                                }
                            }
                        }
                    }

                    double motionX = entity.motionX;
                    double motionZ = entity.motionZ;
                    entity.motionX = motionX * f2 + motionZ * f5;
                    entity.motionZ = motionX * f4 + motionZ * f3;
                    entity.rotationYaw =
                            f
                                    - facingValue.getHorizontalIndex() * 90
                                    + facing.getHorizontalIndex() * 90;

                    
                } else {

                    entity.motionX = entity.motionY = entity.motionZ = 0.0;
                }
            }

            entity.setLocationAndAngles(d4, d5, d6, entity.rotationYaw, entity.rotationPitch);
            return true;
        } else {
            return false;
        }
    }

    public Teleporter(WorldServer bFEValue) {
        this.worldServerInstance = bFEValue;
        this.random = new Random(bFEValue.getSeed());
    }

    public boolean makePortal(Entity entity) {
        byte b0 = 16;
        double d0 = -1.0;
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY);
        int k = MathHelper.floor(entity.posZ);
        int l = i;
        int[] aint1 = World.getLegacyWorldFlowMarker();
        int i1 = j;
        int j1 = k;
        int[] aint = aint1;
        int k1 = 0;
        int l1 = this.random.nextInt(4);
        int i2 = i - b0;

        while (i2 <= i + b0) {
            double d1 = i2 + 0.5 - entity.posX;
            int j2 = k - b0;

            while (true) {
                if (j2 <= k + b0) {
                    double d2 = j2 + 0.5 - entity.posZ;
                    int k2 = this.worldServerInstance.getActualHeight() - 1;

                    while (k2 >= 0) {
                        if (this.worldServerInstance.isAirBlock(new BlockPos(i2, k2, j2))) {
                            while (k2 > 0 && this.worldServerInstance.isAirBlock(new BlockPos(i2, k2 - 1, j2))) {
                                k2--;
                            }

                            int l2 = l1;

                            label305:
                            while (l2 < l1 + 4) {
                                int i3 = l2 % 2;
                                int j3 = 1 - i3;
                                if (l2 % 4 >= 2) {
                                    i3 = -i3;
                                    j3 = -j3;
                                }

                                int k3 = 0;

                                while (k3 < 3) {
                                    int l3 = 0;

                                    while (l3 < 4) {
                                        int i4 = -1;

                                        while (i4 < 4) {
                                            int j4 = i2 + (l3 - 1) * i3 + k3 * j3;
                                            int k4 = k2 + i4;
                                            int l4 = j2 + (l3 - 1) * j3 - k3 * i3;
                                            if (i4 < 0
                                                            && !this.worldServerInstance
                                                                    .getBlockState(
                                                                            new BlockPos(
                                                                                    j4, k4, l4))
                                                                    .getBlock()
                                                                    .getMaterial()
                                                                    .isSolid()
                                                    || i4 >= 0
                                                            && !this.worldServerInstance.isAirBlock(
                                                                    new BlockPos(j4, k4, l4))
                                                            && aint != null) {
                                                break label305;
                                            }

                                            i4++;
                                        }

                                        l3++;
                                    }

                                    k3++;
                                }

                                double d3 = k2 + 0.5 - entity.posY;
                                double d4 = d1 * d1 + d3 * d3 + d2 * d2;
                                if (d0 < 0.0 || d4 < d0) {
                                    d0 = d4;
                                    l = i2;
                                    i1 = k2;
                                    j1 = j2;
                                    k1 = l2 % 4;
                                }

                                l2++;
                            }
                        }

                        k2--;
                    }

                    j2++;

                    continue;
                }

                i2++;

                break;
            }
        }

        if (d0 < 0.0) {
            i2 = i - b0;

            while (i2 <= i + b0) {
                double d5 = i2 + 0.5 - entity.posX;
                int j6 = k - b0;

                while (j6 <= k + b0) {
                    double d6 = j6 + 0.5 - entity.posZ;
                    int l6 = this.worldServerInstance.getActualHeight() - 1;

                    while (l6 >= 0) {
                        if (this.worldServerInstance.isAirBlock(new BlockPos(i2, l6, j6))) {
                            while (l6 > 0 && this.worldServerInstance.isAirBlock(new BlockPos(i2, l6 - 1, j6))) {
                                l6--;
                            }

                            int j7 = l1;

                            label234:
                            while (j7 < l1 + 2) {
                                int i8 = j7 % 2;
                                int l8 = 1 - i8;
                                int l9 = 0;

                                while (l9 < 4) {
                                    int l10 = -1;

                                    while (l10 < 4) {
                                        int l11 = i2 + (l9 - 1) * i8;
                                        int k12 = l6 + l10;
                                        int l12 = j6 + (l9 - 1) * l8;
                                        if (l10 < 0
                                                        && !this.worldServerInstance
                                                                .getBlockState(
                                                                        new BlockPos(l11, k12, l12))
                                                                .getBlock()
                                                                .getMaterial()
                                                                .isSolid()
                                                || l10 >= 0
                                                        && !this.worldServerInstance.isAirBlock(
                                                                new BlockPos(l11, k12, l12))
                                                        && aint != null) {
                                            break label234;
                                        }

                                        l10++;
                                    }

                                    l9++;
                                }

                                double d7 = l6 + 0.5 - entity.posY;
                                double d8 = d5 * d5 + d7 * d7 + d6 * d6;
                                if (d0 < 0.0 || d8 < d0) {
                                    d0 = d8;
                                    l = i2;
                                    i1 = l6;
                                    j1 = j6;
                                    k1 = j7 % 2;
                                }

                                j7++;
                            }
                        }

                        l6--;
                    }

                    j6++;
                }

                i2++;
            }
        }

        int i13 = l;
        int i5 = i1;
        int k6 = j1;
        int j5 = k1 % 2;
        int k5 = 1 - j5;
        if (k1 % 4 >= 2) {
            j5 = -j5;
            k5 = -k5;
        }

        if (d0 < 0.0) {
            i1 = MathHelper.clamp(i1, 70, this.worldServerInstance.getActualHeight() - 10);
            i5 = i1;
            int i7 = -1;

            while (i7 <= 1) {
                int k7 = 1;

                while (k7 < 3) {
                    int j8 = -1;

                    while (j8 < 3) {
                        int i9 = i13 + (k7 - 1) * j5 + i7 * k5;
                        int i10 = i5 + j8;
                        int i11 = k6 + (k7 - 1) * k5 - i7 * j5;
                        int l5 = j8 < 0 ? 1 : 0;
                        this.worldServerInstance.setBlockState(
                                new BlockPos(i9, i10, i11),
                                l5 != 0
                                        ? Blocks.obsidian.getDefaultState()
                                        : Blocks.air.getDefaultState());
                        j8++;
                    }

                    k7++;
                }

                i7++;
            }
        }

        BlockState blockState =
                Blocks.portal
                        .getDefaultState()
                        .withProperty(BlockPortal.AXIS, j5 != 0 ? FacingAxis.X : FacingAxis.Z);
        int l7 = 0;

        while (l7 < 4) {
            int k8 = 0;

            while (k8 < 4) {
                int j9 = -1;

                while (true) {
                    if (j9 < 4) {
                        int j10 = i13 + (k8 - 1) * j5;
                        int j11 = i5 + j9;
                        int i12 = k6 + (k8 - 1) * k5;
                        int i6 = k8 != 0 && k8 != 3 && j9 != -1 && j9 != 3 ? 0 : 1;
                        this.worldServerInstance.setBlockState(
                                new BlockPos(j10, j11, i12),
                                i6 != 0 ? Blocks.obsidian.getDefaultState() : blockState,
                                2);
                        j9++;

                        continue;
                    }

                    k8++;

                    break;
                }
            }

            k8 = 0;

            while (true) {
                if (k8 < 4) {
                    int k9 = -1;

                    while (k9 < 4) {
                        int k10 = i13 + (k8 - 1) * j5;
                        int k11 = i5 + k9;
                        int j12 = k6 + (k8 - 1) * k5;
                        this.worldServerInstance.notifyNeighborsOfStateChange(
                                new BlockPos(k10, k11, j12),
                                this.worldServerInstance.getBlockState(new BlockPos(k10, k11, j12)).getBlock());
                        k9++;
                    }

                    k8++;

                    continue;
                }

                l7++;

                break;
            }
        }

        return true;
    }

    public void placeInPortal(Entity entity, float f) {
        World.getLegacyWorldFlowMarker();

        if (this.worldServerInstance.provider.getDimensionId() != 1) {
            if (this.placeInExistingPortal(entity, f)) {
                return;
            }

            this.makePortal(entity);
            this.placeInExistingPortal(entity, f);

            return;
        }

        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY) - 1;
        int k = MathHelper.floor(entity.posZ);
        byte b0 = 1;
        byte b1 = 0;
        int l = -2;

        while (l <= 2) {
            int i1 = -2;

            while (i1 <= 2) {
                int j1 = -1;

                while (j1 < 3) {
                    int k1 = i + i1 * b0 + l * b1;
                    int l1 = j + j1;
                    int i2 = k + i1 * b1 - l * b0;
                    int j2 = j1 < 0 ? 1 : 0;
                    this.worldServerInstance.setBlockState(
                            new BlockPos(k1, l1, i2),
                            j2 != 0
                                    ? Blocks.obsidian.getDefaultState()
                                    : Blocks.air.getDefaultState());
                    j1++;
                }

                i1++;
            }

            l++;
        }

        entity.setLocationAndAngles(i, j, k, entity.rotationYaw, 0.0F);
        entity.motionX = entity.motionY = entity.motionZ = 0.0;
    }

    private boolean method_51a343cb461860600ef7(BlockPos blockPos) {
        return !this.worldServerInstance.isAirBlock(blockPos) || !this.worldServerInstance.isAirBlock(blockPos.offsetUp());
    }
}
