package net.minecraft.world.demo;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class DemoWorldManager extends ItemInWorldManager {

    private boolean Bb;
    private int jM;
    private int rC;
    private static String kK;
    private boolean demoTimeExpired;

    public boolean tryUseItem(PlayerEntity playerEntity, World world, ItemStack itemStack) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        } else {
            return super.tryUseItem(playerEntity, world, itemStack);
        }
    }

    public boolean activateBlockOrUseItem(
            PlayerEntity playerEntity,
            World world,
            ItemStack itemStack,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            return false;
        } else {
            return super.activateBlockOrUseItem(
                    playerEntity, world, itemStack, blockPos, facing, f, f1, f2);
        }
    }

    public void onBlockClicked(BlockPos blockPos, Facing facing) {
        String text = method_c60693f457aa70be7160();
        if (this.demoTimeExpired) {
            this.sendDemoReminder();
            if (text == null) {
                return;
            }
        }

        super.onBlockClicked(blockPos, facing);
    }

    // region String decoding support

    // endregion

    public DemoWorldManager(World world) {
        super(world);
    }

    public static void b(String text) {
        kK = text;
    }

    static {
        b(null);
    }

    public static String method_c60693f457aa70be7160() {
        return kK;
    }

    public void updateBlockRemoving() {
        String text = method_c60693f457aa70be7160();
        super.updateBlockRemoving();
        String textValue = text;
        this.jM++;
        long totalWorldTime = this.theWorld.getTotalWorldTime();

        long k = totalWorldTime / 24000L;

        long j = k + 1L;
        if (!this.Bb && this.jM > 20) {
            this.Bb = true;
            this.thisPlayerMP.playerNetServerHandler.sendPacket(
                    new S2BPacketChangeGameState(5, 0.0F));
        }

        this.demoTimeExpired =
                remix.compat.compiler.JvmBooleanSlots.fromInt((totalWorldTime > 120500L ? 1 : 0));
        if (this.demoTimeExpired) {
            this.rC++;
        }

        long l = totalWorldTime % 24000L;

        if (l == 500L) {

            if (j > 6L) {
                return;
            }

            this.thisPlayerMP.addChatMessage(
                    new ChatComponentTranslation("demo.day." + j, new Object[0]));
            if (textValue == null) {
                return;
            }

            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }

        if (j == 1L) {

            if (totalWorldTime == 100L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(
                        new S2BPacketChangeGameState(5, 101.0F));
                if (textValue == null) {
                    return;
                }
            }

            if (totalWorldTime == 175L) {
                this.thisPlayerMP.playerNetServerHandler.sendPacket(
                        new S2BPacketChangeGameState(5, 102.0F));
                if (textValue == null) {
                    return;
                }
            }

            if (totalWorldTime != 250L) {
                return;
            }

            this.thisPlayerMP.playerNetServerHandler.sendPacket(
                    new S2BPacketChangeGameState(5, 103.0F));
            if (textValue == null) {
                return;
            }
        }

        if (j == 5L) {

            long i1 = totalWorldTime % 24000L;

            if (i1 == 22000L) {
                this.thisPlayerMP.addChatMessage(
                        new ChatComponentTranslation("demo.day.warning", new Object[0]));
            }
        }
    }

    private void sendDemoReminder() {
        if (this.rC > 100) {
            this.thisPlayerMP.addChatMessage(
                    new ChatComponentTranslation("demo.reminder", new Object[0]));
            this.rC = 0;
        }
    }

    public void blockRemoving(BlockPos blockPos) {
        if (!this.demoTimeExpired) {
            super.blockRemoving(blockPos);
        }
    }

    // region String decoding support

    // endregion

    public boolean tryHarvestBlock(BlockPos blockPos) {
        return this.demoTimeExpired ? false : super.tryHarvestBlock(blockPos);
    }
}
