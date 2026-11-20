package net.minecraft.block;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockAnvilAnvil implements IInteractionObject {

    private World world;

    private BlockPos position;

    public String getName() {
        return "anvil";
    }


    public BlockAnvilAnvil(World world, BlockPos blockPos) {
        this.world = world;
        this.position = blockPos;
    }

    public String getGuiID() {
        return "minecraft:anvil";
    }

    public ChatComponent getDisplayName() {
        return new ChatComponentTranslation(
                Blocks.anvil.getUnlocalizedName() + ".name", new Object[0]);
    }

    public Container createContainer(InventoryPlayer inventoryPlayer, PlayerEntity playerEntity) {
        return new ContainerRepair(inventoryPlayer, this.world, this.position, playerEntity);
    }

    public boolean hasCustomName() {
        return false;
    }
}
