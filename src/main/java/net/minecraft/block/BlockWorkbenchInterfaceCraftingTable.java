package net.minecraft.block;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockWorkbenchInterfaceCraftingTable implements IInteractionObject {

    private BlockPos position;
    private World world;

    public String getName() {
        return null;
    }

    public String getGuiID() {
        return "minecraft:crafting_table";
    }

    public boolean hasCustomName() {
        return false;
    }

    public ChatComponent getDisplayName() {
        return new ChatComponentTranslation(
                Blocks.crafting_table.getUnlocalizedName() + ".name", new Object[0]);
    }

    public BlockWorkbenchInterfaceCraftingTable(World world, BlockPos blockPos) {
        this.world = world;
        this.position = blockPos;
    }


    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerWorkbench(inventoryPlayer, this.world, this.position);
    }
}
