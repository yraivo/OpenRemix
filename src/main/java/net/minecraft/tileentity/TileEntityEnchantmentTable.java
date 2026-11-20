package net.minecraft.tileentity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IInteractionObject;

import java.util.Random;

public class TileEntityEnchantmentTable extends TileEntity
        implements IUpdatePlayerListBox, IInteractionObject {
    public float tD;
    public float bookSpreadPrev;
    public float bookRotation;
    public float rj;
    public int tickCount;
    private static Random rand;
    public float rG;
    private String customName;

    public float pageFlip;

    public float bookSpread;
    public float pageFlipPrev;
    public float bookRotationPrev;

    public String getName() {
        return this.hasCustomName() ? this.customName : "container.enchant";
    }

    public ChatComponent getDisplayName() {
        return (ChatComponent)
                (this.hasCustomName()
                        ? new ChatComponentText(this.getName())
                        : new ChatComponentTranslation(this.getName(), new Object[0]));
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        if (this.hasCustomName()) {
            compoundTag.setString("CustomName", this.customName);
        }
    }

    public String getGuiID() {
        return "minecraft:enchanting_table";
    }

    public void update() {
        Entity[] entityArray;
        {
            Entity[] entityArrayValue = MobSpawnerBaseLogic.GU();
            this.bookSpreadPrev = this.bookSpread;
            entityArray = entityArrayValue;
            this.bookRotationPrev = this.bookRotation;
            PlayerEntity playerEntity =
                    this.worldObj.getClosestPlayer(
                            this.pos.getX() + 0.5F,
                            this.pos.getY() + 0.5F,
                            this.pos.getZ() + 0.5F,
                            3.0);
            if (playerEntity != null) {
                double d0 = playerEntity.posX - (this.pos.getX() + 0.5F);
                double d1 = playerEntity.posZ - (this.pos.getZ() + 0.5F);
                this.rj = (float) Math.atan2(d1, d0);
                this.bookSpread += 0.1F;
                if (this.bookSpread < 0.5F || net.minecraft.tileentity.TileEntityEnchantmentTable.rand.nextInt(40) == 0) {
                    float f1 = this.tD;

                    do {
                        this.tD =
                                this.tD
                                        + (net.minecraft.tileentity.TileEntityEnchantmentTable.rand.nextInt(4)
                                                - net.minecraft.tileentity.TileEntityEnchantmentTable.rand.nextInt(4));
                    } while (f1 == this.tD);
                }

                
            } else {

                this.rj += 0.02F;
                this.bookSpread -= 0.1F;
            }
        }

        while (this.bookRotation >= (float) Math.PI) {
            this.bookRotation -= (float) (Math.PI * 2);
        }

        while (this.bookRotation < (float) -Math.PI) {
            this.bookRotation += (float) (Math.PI * 2);
        }

        while (this.rj >= (float) Math.PI) {
            this.rj -= (float) (Math.PI * 2);
        }

        while (this.rj < (float) -Math.PI) {
            this.rj += (float) (Math.PI * 2);
        }

        float f2 = this.rj - this.bookRotation;

        while (f2 >= (float) Math.PI) {
            f2 -= (float) (Math.PI * 2);
        }

        while (f2 < (float) -Math.PI) {
            f2 += (float) (Math.PI * 2);
        }

        this.bookRotation += f2 * 0.4F;
        this.bookSpread = MathHelper.clamp(this.bookSpread, 0.0F, 1.0F);
        this.tickCount++;
        this.pageFlipPrev = this.pageFlip;
        float f = (this.tD - this.pageFlip) * 0.4F;
        float f3 = 0.2F;
        f = MathHelper.clamp(f, -f3, f3);
        this.rG = this.rG + (f - this.rG) * 0.9F;
        this.pageFlip = this.pageFlip + this.rG;
    }

    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerEnchantment(inventoryPlayer, this.worldObj, this.pos);
    }

    static {
        net.minecraft.tileentity.TileEntityEnchantmentTable.rand = new Random();
    }

    public void setCustomName(String text) {
        this.customName = text;
    }

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        if (compoundTag.hasKey("CustomName", 8)) {
            this.customName = compoundTag.getString("CustomName");
        }
    }
}
