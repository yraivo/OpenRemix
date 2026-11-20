package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Rotations;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class ItemArmorStand extends Item {

    public ItemArmorStand() {
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }


    private void applyRandomRotations(EntityArmorStand entityArmorStand, Random random) {
        Rotations headRotation = entityArmorStand.getHeadRotation();
        float f = random.nextFloat() * 5.0F;
        float f1 = random.nextFloat() * 20.0F - 10.0F;
        Rotations rotations =
                new Rotations(
                        headRotation.getX() + f, headRotation.getY() + f1, headRotation.getZ());
        entityArmorStand.setHeadRotation(rotations);
        headRotation = entityArmorStand.getBodyRotation();
        f = random.nextFloat() * 10.0F - 5.0F;
        rotations =
                new Rotations(headRotation.getX(), headRotation.getY() + f, headRotation.getZ());
        entityArmorStand.setBodyRotation(rotations);
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f1,
            float f2,
            float f3) {
        if (facing == Facing.DOWN) {
            return false;
        }

        boolean replaceable =
                world.getBlockState(blockPos).getBlock().isReplaceable(world, blockPos);
        BlockPos blockPosValue = replaceable ? blockPos : blockPos.offset(facing);
        if (!playerEntity.canPlayerEdit(blockPosValue, facing, itemStack)) {
            return false;
        }

        BlockPos blockPosValue2 = blockPosValue.offsetUp();
        int i =
                !world.isAirBlock(blockPosValue)
                                && !world.getBlockState(blockPosValue)
                                        .getBlock()
                                        .isReplaceable(world, blockPosValue)
                        ? 1
                        : 0;
        i |=
                !world.isAirBlock(blockPosValue2)
                                && !world.getBlockState(blockPosValue2)
                                        .getBlock()
                                        .isReplaceable(world, blockPosValue2)
                        ? 1
                        : 0;
        if (i != 0) {
            return false;
        }

        double x = blockPosValue.getX();
        double y = blockPosValue.getY();
        double z = blockPosValue.getZ();
        List entitiesWithinAABBExcludingEntity =
                world.getEntitiesWithinAABBExcludingEntity(
                        (Entity) null, BoundingBox.fromBounds(x, y, z, x + 1.0, y + 2.0, z + 1.0));
        if (entitiesWithinAABBExcludingEntity.size() > 0) {
            return false;
        }

        if (!world.isRemote) {
            world.setBlockToAir(blockPosValue);
            world.setBlockToAir(blockPosValue2);
            EntityArmorStand entityArmorStand = new EntityArmorStand(world, x + 0.5, y, z + 0.5);
            float f =
                    MathHelper.floor(
                                    (MathHelper.wrapAngleTo180(playerEntity.rotationYaw - 180.0F)
                                                    + 22.5F)
                                            / 45.0F)
                            * 45.0F;
            entityArmorStand.setLocationAndAngles(x + 0.5, y, z + 0.5, f, 0.0F);
            this.applyRandomRotations(entityArmorStand, world.rand);
            CompoundTag tag = itemStack.getTag();
            if (tag != null && tag.hasKey("EntityTag", 10)) {
                CompoundTag compoundTag = new CompoundTag();
                entityArmorStand.writeToNBTOptional(compoundTag);
                compoundTag.merge(tag.getCompoundTag("EntityTag"));
                entityArmorStand.readFromNBT(compoundTag);
            }

            world.spawnEntityInWorld(entityArmorStand);
        }

        itemStack.stackSize--;
        return true;
    }
}
