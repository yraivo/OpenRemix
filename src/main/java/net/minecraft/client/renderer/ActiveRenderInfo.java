package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ActiveRenderInfo {
    private static float rotationXY;
    private static final FloatBuffer OBJECTCOORDS = GLAllocation.createDirectFloatBuffer(3);
    private static final FloatBuffer MODELVIEW = GLAllocation.createDirectFloatBuffer(16);
    private static Vec3 position = new Vec3(0.0, 0.0, 0.0);
    private static float rotationXZ;
    private static float rotationZ;
    private static final FloatBuffer PROJECTION = GLAllocation.createDirectFloatBuffer(16);
    private static float rotationYZ;
    private static final IntBuffer VIEWPORT = GLAllocation.createDirectIntBuffer(16);
    private static float rotationX;

    public static float getRotationX() {
        return net.minecraft.client.renderer.ActiveRenderInfo.rotationX;
    }

    public static float getRotationZ() {
        return net.minecraft.client.renderer.ActiveRenderInfo.rotationZ;
    }

    public static Vec3 projectViewFromEntity(Entity entity, double p171_1) {
        double d1 = entity.prevPosX + (entity.posX - entity.prevPosX) * p171_1;
        double d2 = entity.prevPosY + (entity.posY - entity.prevPosY) * p171_1;
        double d3 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * p171_1;
        double d4 = d1 + net.minecraft.client.renderer.ActiveRenderInfo.position.x;
        double d5 = d2 + net.minecraft.client.renderer.ActiveRenderInfo.position.y;
        double d6 = d3 + net.minecraft.client.renderer.ActiveRenderInfo.position.z;
        return new net.minecraft.util.Vec3(d4, d5, d6);
    }

    public static Vec3 getPosition() {
        return net.minecraft.client.renderer.ActiveRenderInfo.position;
    }

    public static float getRotationXZ() {
        return net.minecraft.client.renderer.ActiveRenderInfo.rotationXZ;
    }

    public static void updateRenderInfo(PlayerEntity playerEntity, boolean flag) {
        GlStateManager.getFloat(2982, net.minecraft.client.renderer.ActiveRenderInfo.MODELVIEW);
        GlStateManager.getFloat(2983, net.minecraft.client.renderer.ActiveRenderInfo.PROJECTION);
        GL11.glGetInteger(2978, net.minecraft.client.renderer.ActiveRenderInfo.VIEWPORT);
        float f = (net.minecraft.client.renderer.ActiveRenderInfo.VIEWPORT.get(0) + net.minecraft.client.renderer.ActiveRenderInfo.VIEWPORT.get(2)) / 2;
        float f1 = (net.minecraft.client.renderer.ActiveRenderInfo.VIEWPORT.get(1) + net.minecraft.client.renderer.ActiveRenderInfo.VIEWPORT.get(3)) / 2;
        GLU.gluUnProject(f, f1, 0.0F, net.minecraft.client.renderer.ActiveRenderInfo.MODELVIEW, net.minecraft.client.renderer.ActiveRenderInfo.PROJECTION, net.minecraft.client.renderer.ActiveRenderInfo.VIEWPORT, net.minecraft.client.renderer.ActiveRenderInfo.OBJECTCOORDS);
        net.minecraft.client.renderer.ActiveRenderInfo.position = new Vec3(net.minecraft.client.renderer.ActiveRenderInfo.OBJECTCOORDS.get(0), net.minecraft.client.renderer.ActiveRenderInfo.OBJECTCOORDS.get(1), net.minecraft.client.renderer.ActiveRenderInfo.OBJECTCOORDS.get(2));
        int i = flag ? 1 : 0;
        float rotationPitch = playerEntity.rotationPitch;
        float rotationYaw = playerEntity.rotationYaw;
        net.minecraft.client.renderer.ActiveRenderInfo.rotationX = MathHelper.cos(rotationYaw * (float) Math.PI / 180.0F) * (1 - i * 2);
        net.minecraft.client.renderer.ActiveRenderInfo.rotationZ = MathHelper.sin(rotationYaw * (float) Math.PI / 180.0F) * (1 - i * 2);
        net.minecraft.client.renderer.ActiveRenderInfo.rotationYZ = -net.minecraft.client.renderer.ActiveRenderInfo.rotationZ * MathHelper.sin(rotationPitch * (float) Math.PI / 180.0F) * (1 - i * 2);
        net.minecraft.client.renderer.ActiveRenderInfo.rotationXY = net.minecraft.client.renderer.ActiveRenderInfo.rotationX * MathHelper.sin(rotationPitch * (float) Math.PI / 180.0F) * (1 - i * 2);
        net.minecraft.client.renderer.ActiveRenderInfo.rotationXZ = MathHelper.cos(rotationPitch * (float) Math.PI / 180.0F);
    }

    public static float getRotationYZ() {
        return net.minecraft.client.renderer.ActiveRenderInfo.rotationYZ;
    }

    public static Block getBlockAtEntityViewpoint(World world, Entity entity, float f) {
        Vec3 vec3 = projectViewFromEntity(entity, f);
        BlockPos blockPos = new BlockPos(vec3);
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        if (block.getMaterial().isLiquid()) {
            float f1 = 0.0F;
            if (blockState.getBlock() instanceof BlockLiquid) {
                f1 = BlockLiquid.getLiquidHeightPercent((Integer) blockState.getValue(BlockLiquid.LEVEL)) - 0.11111111F;
            }

            float f2 = blockPos.getY() + 1 - f1;
            if (vec3.y >= f2) {
                block = world.getBlockState(blockPos.offsetUp()).getBlock();
            }
        }

        return block;
    }

    public static float getRotationXY() {
        return net.minecraft.client.renderer.ActiveRenderInfo.rotationXY;
    }
}
