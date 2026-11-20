package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class ItemBoat extends Item {
    private static String[] packedTextFragments;

    // region String decoding support
    private static void unpackLiteralFragments() {
        net.minecraft.item.ItemBoat.packedTextFragments[0] = new String("OPk68");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public ItemBoat() {
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabTransport);
    }

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        float f = 1.0F;
        float f1 =
                playerEntity.prevRotationPitch
                        + (playerEntity.rotationPitch - playerEntity.prevRotationPitch) * f;
        float f2 =
                playerEntity.prevRotationYaw
                        + (playerEntity.rotationYaw - playerEntity.prevRotationYaw) * f;
        String textValue = text;
        double d0 = playerEntity.prevPosX + (playerEntity.posX - playerEntity.prevPosX) * f;
        double d1 =
                playerEntity.prevPosY
                        + (playerEntity.posY - playerEntity.prevPosY) * f
                        + playerEntity.getEyeHeight();
        double d2 = playerEntity.prevPosZ + (playerEntity.posZ - playerEntity.prevPosZ) * f;
        Vec3 vec3 = new Vec3(d0, d1, d2);
        float f3 = MathHelper.cos(-f2 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f4 = MathHelper.sin(-f2 * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f5 = -MathHelper.cos(-f1 * (float) (Math.PI / 180.0));
        float f6 = MathHelper.sin(-f1 * (float) (Math.PI / 180.0));
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0;
        Vec3 vec3Value = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
        RayTraceResult rayTraceResult = world.rayTraceBlocks(vec3, vec3Value, true);
        if (rayTraceResult == null) {
            return itemStack;
        }

        Vec3 vec3Value2 = playerEntity.getLook(f);
        byte b0 = 0;
        float f9 = 1.0F;
        List entitiesWithinAABBExcludingEntity =
                world.getEntitiesWithinAABBExcludingEntity(
                        playerEntity,
                        playerEntity
                                .getEntityBoundingBox()
                                .addCoord(vec3Value2.x * d3, vec3Value2.y * d3, vec3Value2.z * d3)
                                .expand(f9, f9, f9));
        int i = 0;

        while (i < entitiesWithinAABBExcludingEntity.size()) {
            Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(i);
            if (entity.canBeCollidedWith()) {
                float collisionBorderSize = entity.getCollisionBorderSize();
                BoundingBox boundingBox =
                        entity.getEntityBoundingBox()
                                .expand(
                                        collisionBorderSize,
                                        collisionBorderSize,
                                        collisionBorderSize);
                if (boundingBox.isVecInside(vec3)) {
                    b0 = 1;
                }
            }

            i++;
            if (textValue != null) {
                break;
            }
        }

        if (b0 != 0) {
            return itemStack;
        }

        if (rayTraceResult.typeOfHit == RayHitType.BLOCK) {
            BlockPos blockPos = rayTraceResult.getBlockPos();
            if (world.getBlockState(blockPos).getBlock() == Blocks.snow_layer) {
                blockPos = blockPos.offsetDown();
            }

            EntityBoat entityBoat =
                    new EntityBoat(
                            world,
                            blockPos.getX() + 0.5F,
                            blockPos.getY() + 1.0F,
                            blockPos.getZ() + 0.5F);
            entityBoat.rotationYaw =
                    ((MathHelper.floor(playerEntity.rotationYaw * 4.0F / 360.0F + 0.5) & 3) - 1)
                            * 90;
            if (!world.getCollidingBoundingBoxes(
                            entityBoat, entityBoat.getEntityBoundingBox().expand(-0.1, -0.1, -0.1))
                    .isEmpty()) {
                return itemStack;
            }

            if (!world.isRemote) {
                world.spawnEntityInWorld(entityBoat);
            }

            if (!playerEntity.capabilities.isCreativeMode) {
                itemStack.stackSize--;
            }

            playerEntity.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ItemStack.setLegacyItemStackFlowMarker(packedTextFragments[0]);
        }

        return itemStack;
    }

    // region String decoding support

    // endregion
}
