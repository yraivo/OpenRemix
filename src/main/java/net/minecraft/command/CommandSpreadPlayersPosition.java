package net.minecraft.command;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

class CommandSpreadPlayersPosition {
    double uj;
    double um;

    public int method_1912249ddb3c94e7de19(World world) {
        BlockPos blockPos = new BlockPos(this.uj, 256.0, this.um);

        while (blockPos.getY() > 0) {
            blockPos = blockPos.offsetDown();
            if (world.getBlockState(blockPos).getBlock().getMaterial() != Material.AIR) {
                return blockPos.getY() + 1;
            }
        }

        return 257;
    }

    CommandSpreadPlayersPosition(double p171_0, double p171_1) {
        this.uj = p171_0;
        this.um = p171_1;
    }

    public void method_874329c84755abd55bd7(
            Random random, double d0, double d1, double d2, double d3) {
        this.uj = MathHelper.nextDouble(random, d0, d2);
        this.um = MathHelper.nextDouble(random, d1, d3);
    }

    CommandSpreadPlayersPosition() {}

    public void method_49177e68d1cf2f23bbc7(CommandSpreadPlayersPosition p171_0) {
        this.uj = this.uj - p171_0.uj;
        this.um = this.um - p171_0.um;
    }

    double method_5c985ae91d9d2ff2ff2e(CommandSpreadPlayersPosition p171_0) {
        double d0 = this.uj - p171_0.uj;
        double d1 = this.um - p171_0.um;
        return java.lang.Math.sqrt(d0 * d0 + d1 * d1);
    }

    float aN() {
        return MathHelper.sqrt(this.uj * this.uj + this.um * this.um);
    }

    void method_8d9b536bcdc9991c48fb() {
        double d0 = this.aN();
        this.uj /= d0;
        this.um /= d0;
    }

    public boolean method_4e028d13c660a558a4cd(World world) {
        BlockPos blockPos = new BlockPos(this.uj, 256.0, this.um);

        while (blockPos.getY() > 0) {
            blockPos = blockPos.offsetDown();
            Material material = world.getBlockState(blockPos).getBlock().getMaterial();
            if (material != Material.AIR) {
                return !material.isLiquid() && material != Material.fire;
            }
        }

        return false;
    }

    public boolean method_33897f45f2222b224424(double d0, double d1, double d2, double d3) {
        boolean flag;
        byte b0;
        {
            boolean flag1 = CommandException.method_d87039b07bd6961907f2();
            b0 = 0;
            flag = flag1;
            if (this.uj < d0) {
                this.uj = d0;
                b0 = 1;
                if (!(!flag)) {

                    if (this.uj > d2) {
                        this.uj = d2;
                        b0 = 1;
                    }
                }
            } else {

                if (this.uj > d2) {
                    this.uj = d2;
                    b0 = 1;
                }
            }
        }

        if (this.um < d1) {
            this.um = d1;
            b0 = 1;
            if (!flag) {
                return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
            }
        }

        if (this.um > d3) {
            this.um = d3;
            b0 = 1;
        }

        return remix.compat.compiler.JvmBooleanSlots.fromInt(b0);
    }
}
