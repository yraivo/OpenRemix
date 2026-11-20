package net.minecraft.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntityEnderChest extends TileEntity implements IUpdatePlayerListBox {
    public int numPlayersUsing;

    public float prevLidAngle;
    private int ticksSinceSync;

    public float lidAngle;


    public boolean canBeUsed(PlayerEntity playerEntity) {
        return this.worldObj.getTileEntity(this.pos) != this
                ? false
                : playerEntity.getDistanceSq(
                                this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5)
                        <= 64.0;
    }

    public void openChest() {
        this.numPlayersUsing++;
        this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
    }

    public boolean receiveClientEvent(int i, int j) {
        if (i == 1) {
            this.numPlayersUsing = j;
            return true;
        } else {
            return super.receiveClientEvent(i, j);
        }
    }

    public void invalidate() {
        this.updateContainingBlockInfo();
        super.invalidate();
    }

    public void closeChest() {
        this.numPlayersUsing--;
        this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
    }

    public void update() {
        MobSpawnerBaseLogic.GU();

        if (++this.ticksSinceSync % 20 * 4 == 0) {
            this.worldObj.addBlockEvent(this.pos, Blocks.ender_chest, 1, this.numPlayersUsing);
        }

        this.prevLidAngle = this.lidAngle;
        int x = this.pos.getX();
        int j = this.pos.getY();
        int k = this.pos.getZ();
        float f = 0.1F;
        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F) {
            double d1 = x + 0.5;
            double d0 = k + 0.5;
            this.worldObj.playSoundEffect(
                    d1,
                    j + 0.5,
                    d0,
                    "random.chestopen",
                    0.5F,
                    this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F
                || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f2;
            {
                f2 = this.lidAngle;
                if (this.numPlayersUsing > 0) {
                    this.lidAngle += f;

                    
                } else {

                    this.lidAngle -= f;
                }
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            float f1 = 0.5F;
            if (this.lidAngle < f1 && f2 >= f1) {
                double d3 = x + 0.5;
                double d2 = k + 0.5;
                this.worldObj.playSoundEffect(
                        d3,
                        j + 0.5,
                        d2,
                        "random.chestclosed",
                        0.5F,
                        this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }
}
