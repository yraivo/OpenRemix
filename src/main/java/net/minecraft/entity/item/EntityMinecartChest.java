package net.minecraft.entity.item;

import net.minecraft.block.BlockChest;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class EntityMinecartChest extends EntityMinecartContainer {
    private static String bbx;

    // region String decoding support

    // endregion

    public void killMinecart(DamageSource damageSource) {
        super.killMinecart(damageSource);
        this.dropItemWithOffset(Item.getItemFromBlock(Blocks.chest), 1, 0.0F);
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerChest(inventoryPlayer, this, playerEntity);
    }

    public int getSizeInventory() {
        return 27;
    }

    public EntityMinecartEnumMinecartType getMinecartType() {
        return EntityMinecartEnumMinecartType.CHEST;
    }

    static {
        bbx = "minecraft:chest";
    }

    public String getGuiID() {
        return bbx;
    }

    public BlockState getDefaultDisplayTile() {
        return Blocks.chest.getDefaultState().withProperty(BlockChest.FACING_PROP, Facing.NORTH);
    }

    public int getDefaultDisplayTileOffset() {
        return 8;
    }

    public EntityMinecartChest(World worldValue, double d0, double d1, double d2) {
        super(worldValue, d0, d1, d2);
    }

    public EntityMinecartChest(World worldValue) {
        super(worldValue);
    }

    // region String decoding support

    // endregion
}
