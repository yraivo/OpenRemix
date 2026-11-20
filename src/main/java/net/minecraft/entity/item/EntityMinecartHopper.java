package net.minecraft.entity.item;

import net.minecraft.block.state.BlockState;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerHopper;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tileentity.IHopper;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.List;

public class EntityMinecartHopper extends EntityMinecartContainer implements IHopper {
    private int transferTicker;

    private boolean isBlocked = true;
    private BlockPos field_174900_c;

    public double getYPos() {
        return this.posY;
    }

    public boolean interactFirst(PlayerEntity playerEntity) {
        if (!this.world.isRemote) {
            playerEntity.displayGUIChest(this);
        }

        return true;
    }

    public EntityMinecartHopper(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
        this.transferTicker = -1;
        this.field_174900_c = BlockPos.ORIGIN;
    }

    protected void writeEntityToNBT(CompoundTag compoundTag) {
        super.writeEntityToNBT(compoundTag);
        compoundTag.setInteger("TransferCooldown", this.transferTicker);
    }

    public boolean canTransfer() {
        return this.transferTicker > 0;
    }

    public EntityMinecartHopper(World worldValue) {
        super(worldValue);
        this.transferTicker = -1;
        this.field_174900_c = BlockPos.ORIGIN;
    }

    protected void readEntityFromNBT(CompoundTag compoundTag) {
        super.readEntityFromNBT(compoundTag);
        this.transferTicker = compoundTag.getInteger("TransferCooldown");
    }

    public void setBlocked(boolean bbCValue) {
        this.isBlocked = bbCValue;
    }

    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        this.dropItemWithOffset(Item.getItemFromBlock(Blocks.hopper), 1, 0.0F);
    }

    public void setTransferTicker(int amNValue) {
        this.transferTicker = amNValue;
    }

    public double getZPos() {
        return this.posZ;
    }

    public void onActivatorRailPass(int j, int k, int l, boolean flag) {
        int i = !flag ? 1 : 0;
        if (i != remix.compat.compiler.JvmBooleanSlots.toInt(this.getBlocked())) {
            this.setBlocked(remix.compat.compiler.JvmBooleanSlots.fromInt(i));
        }
    }

    public String getGuiID() {
        return "minecraft:hopper";
    }

    public World getWorld() {
        return this.world;
    }

    public boolean func_96112_aD() {
        if (TileEntityHopper.captureDroppedItems(this)) {
            return true;
        }

        List entitiesWithinAABB =
                this.world.getEntitiesWithinAABB(
                        EntityItem.class,
                        this.getEntityBoundingBox().expand(0.25, 0.0, 0.25),
                        IEntitySelector.selectAnything);
        if (entitiesWithinAABB.size() > 0) {
            TileEntityHopper.putDropInInventoryAllSlots(
                    this, (EntityItem) entitiesWithinAABB.get(0));
        }

        return false;
    }

    public int getSizeInventory() {
        return 5;
    }

    public double getXPos() {
        return this.posX;
    }

    public boolean getBlocked() {
        return this.isBlocked;
    }

    public int getDefaultDisplayTileOffset() {
        return 1;
    }

    public void onUpdate() {
        boolean flag1 = EntityMinecartEnumMinecartType.method_d87039b07bd6961907f2();
        super.onUpdate();
        boolean flag = flag1;
        if (!this.world.isRemote && this.isEntityAlive() && this.getBlocked()) {
            {
                BlockPos blockPos = new BlockPos(this);
                if (blockPos.equals(this.field_174900_c)) {
                    this.transferTicker--;
                    if (!(flag)) {

                        this.setTransferTicker(0);
                    }
                } else {

                    this.setTransferTicker(0);
                }
            }

            if (!this.canTransfer()) {
                this.setTransferTicker(0);
                if (this.func_96112_aD()) {
                    this.setTransferTicker(4);
                    this.markDirty();
                }
            }
        }
    }

    public BlockState getDefaultDisplayTile() {
        return Blocks.hopper.getDefaultState();
    }


    public EntityMinecartEnumMinecartType getMinecartType() {
        return EntityMinecartEnumMinecartType.HOPPER;
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerHopper(inventoryPlayer, this, playerEntity);
    }
}
