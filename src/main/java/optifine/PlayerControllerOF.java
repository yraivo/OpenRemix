package optifine;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.entity.LocalPlayer;
import net.minecraft.client.multiplayer.ClientWorld;
import net.minecraft.client.multiplayer.PlayerController;
import net.minecraft.client.network.ClientPlayHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class PlayerControllerOF extends PlayerController {
    private boolean acting = false;

    public PlayerControllerOF(
            MinecraftClient minecraftClient, ClientPlayHandler clientPlayHandler) {
        super(minecraftClient, clientPlayHandler);
    }

    public boolean onPlayerRightClick(
            LocalPlayer localPlayer,
            ClientWorld clientWorld,
            ItemStack itemStack,
            BlockPos blockPos,
            Facing facing,
            Vec3 vec3) {
        this.acting = true;
        boolean flag =
                super.onPlayerRightClick(
                        localPlayer, clientWorld, itemStack, blockPos, facing, vec3);
        this.acting = false;
        return flag;
    }

    public boolean isActing() {
        return this.acting;
    }

    public boolean sendUseItem(PlayerEntity playerEntity, World world, ItemStack itemStack) {
        this.acting = true;
        boolean flag = super.sendUseItem(playerEntity, world, itemStack);
        this.acting = false;
        return flag;
    }

    public boolean clickBlock(BlockPos blockPos, Facing facing) {
        this.acting = true;
        boolean flag = super.clickBlock(blockPos, facing);
        this.acting = false;
        return flag;
    }

    public boolean onPlayerDamageBlock(BlockPos blockPos, Facing facing) {
        this.acting = true;
        boolean flag = super.onPlayerDamageBlock(blockPos, facing);
        this.acting = false;
        return flag;
    }
}
