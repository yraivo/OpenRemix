package net.minecraft.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

import java.util.Iterator;

public class WorldManager implements IWorldAccess {
    private WorldServer theWorldServer;
    private MinecraftServer mcServer;

    public void markBlockRangeForRenderUpdate(int i, int j, int k, int l, int i1, int j1) {}

    public void playAuxSFX(PlayerEntity playerEntity, int i, BlockPos blockPos, int j) {
        this.mcServer
                .getConfigurationManager()
                .sendToAllNearExcept(
                        playerEntity,
                        blockPos.getX(),
                        blockPos.getY(),
                        blockPos.getZ(),
                        64.0,
                        this.theWorldServer.provider.getDimensionId(),
                        new S28PacketEffect(i, blockPos, j, false));
    }

    public void playRecord(String text, BlockPos blockPos) {}

    public void sendBlockBreakProgress(int i, BlockPos blockPos, int j) {
        World.getLegacyWorldFlowMarker();

        Iterator iterator = this.mcServer.getConfigurationManager().playerEntityList.iterator();

        while (iterator.hasNext()) {
            EntityPlayerMP entityPlayerMP = (EntityPlayerMP) iterator.next();
            if (entityPlayerMP != null
                    && entityPlayerMP.world == this.theWorldServer
                    && entityPlayerMP.getEntityId() != i) {
                double d0 = blockPos.getX() - entityPlayerMP.posX;
                double d1 = blockPos.getY() - entityPlayerMP.posY;
                double d2 = blockPos.getZ() - entityPlayerMP.posZ;
                if (d0 * d0 + d1 * d1 + d2 * d2 < 1024.0) {
                    entityPlayerMP.playerNetServerHandler.sendPacket(
                            new S25PacketBlockBreakAnim(i, blockPos, j));
                }
            }
        }
    }

    public void playSound(String text, double d0, double d1, double d2, float f, float f1) {
        this.mcServer
                .getConfigurationManager()
                .sendToAllNear(
                        d0,
                        d1,
                        d2,
                        f > 1.0F ? 16.0F * f : 16.0,
                        this.theWorldServer.provider.getDimensionId(),
                        new S29PacketSoundEffect(text, d0, d1, d2, f, f1));
    }

    public void playSoundToNearExcept(
            PlayerEntity playerEntity,
            String text,
            double d0,
            double d1,
            double d2,
            float f,
            float f1) {
        this.mcServer
                .getConfigurationManager()
                .sendToAllNearExcept(
                        playerEntity,
                        d0,
                        d1,
                        d2,
                        f > 1.0F ? 16.0F * f : 16.0,
                        this.theWorldServer.provider.getDimensionId(),
                        new S29PacketSoundEffect(text, d0, d1, d2, f, f1));
    }

    public void notifyLightSet(BlockPos blockPos) {}

    public void markBlockForUpdate(BlockPos blockPos) {
        this.theWorldServer.getPlayerManager().markBlockForUpdate(blockPos);
    }

    public void spawnParticle(
            int i,
            boolean flag,
            double d0,
            double d1,
            double d2,
            double d3,
            double d4,
            double d5,
            int... j) {}

    public void broadcastSound(int i, BlockPos blockPos, int j) {
        this.mcServer
                .getConfigurationManager()
                .sendPacketToAllPlayers(new S28PacketEffect(i, blockPos, j, true));
    }

    public WorldManager(MinecraftServer awJValue, WorldServer bjdValue) {
        this.mcServer = awJValue;
        this.theWorldServer = bjdValue;
    }

    public void onEntityAdded(Entity entity) {
        this.theWorldServer.getEntityTracker().trackEntity(entity);
    }

    public void onEntityRemoved(Entity entity) {
        this.theWorldServer.getEntityTracker().untrackEntity(entity);
    }
}
