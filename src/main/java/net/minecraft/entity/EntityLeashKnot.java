package net.minecraft.entity;

import net.minecraft.block.BlockFence;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

public class EntityLeashKnot extends EntityHanging {
    public EntityLeashKnot(World worldValue, BlockPos blockPos) {
        super(worldValue, blockPos);
        this.setPosition(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);

        this.setBoundingBox(
                new BoundingBox(
                        this.posX - 0.1875,
                        this.posY - 0.25 + 0.125,
                        this.posZ - 0.1875,
                        this.posX + 0.1875,
                        this.posY + 0.25 + 0.125,
                        this.posZ + 0.1875));
    }

    public float getEyeHeight() {
        return -0.0625F;
    }

    public boolean interactFirst(PlayerEntity playerEntity) {
        String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        ItemStack heldItem = playerEntity.getHeldItem();
        String[] astring = astring1;
        byte b0 = 0;
        if (heldItem != null && heldItem.getItem() == Items.lead && !this.world.isRemote) {
            double d0 = 7.0;

            for (java.util.Iterator<?> __recoveryIterator171_1531 =
                            (this.world.getEntitiesWithinAABB(
                                            EntityLiving.class,
                                            new BoundingBox(
                                                    this.posX - d0,
                                                    this.posY - d0,
                                                    this.posZ - d0,
                                                    this.posX + d0,
                                                    this.posY + d0,
                                                    this.posZ + d0)))
                                    .iterator();
                    (__recoveryIterator171_1531).hasNext(); ) {
                EntityLiving entityLiving = (EntityLiving) (__recoveryIterator171_1531).next();
                if (entityLiving.getLeashed()
                        && entityLiving.getLeashedToEntity() == playerEntity) {
                    entityLiving.setLeashedToEntity(this, true);
                    b0 = 1;
                    if (astring != null) {
                        break;
                    }
                }
            }
        }

        if (!this.world.isRemote && b0 == 0) {
            this.setDead();
            if (playerEntity.capabilities.isCreativeMode) {
                double d1 = 7.0;

                for (java.util.Iterator<?> __recoveryIterator171_2323 =
                                (this.world.getEntitiesWithinAABB(
                                                EntityLiving.class,
                                                new BoundingBox(
                                                        this.posX - d1,
                                                        this.posY - d1,
                                                        this.posZ - d1,
                                                        this.posX + d1,
                                                        this.posY + d1,
                                                        this.posZ + d1)))
                                        .iterator();
                        (__recoveryIterator171_2323).hasNext(); ) {
                    EntityLiving entityLivingValue =
                            (EntityLiving) (__recoveryIterator171_2323).next();
                    if (entityLivingValue.getLeashed()
                            && entityLivingValue.getLeashedToEntity() == this) {
                        entityLivingValue.clearLeashed(true, false);
                        if (astring != null) {
                            break;
                        }
                    }
                }
            }
        }

        return true;
    }

    public void onBroken(Entity entity) {}

    public EntityLeashKnot(World worldValue) {
        super(worldValue);
    }

    public int getWidthPixels() {
        return 9;
    }

    public static EntityLeashKnot createKnot(World worldValue, BlockPos blockPos) {
        net.minecraft.entity.EntityLeashKnot entityLeashKnot = new net.minecraft.entity.EntityLeashKnot(worldValue, blockPos);
        entityLeashKnot.forceSpawn = true;
        worldValue.spawnEntityInWorld(entityLeashKnot);
        return entityLeashKnot;
    }

    public boolean writeToNBTOptional(CompoundTag compoundTag) {
        return false;
    }

    public int getHeightPixels() {
        return 9;
    }

    protected void entityInit() {
        super.entityInit();
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {}

    public boolean onValidSurface() {
        return this.world.getBlockState(this.hangingPosition).getBlock() instanceof BlockFence;
    }

    public boolean isInRangeToRenderDist(double p171_0) {
        return p171_0 < 1024.0;
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {}

    public static EntityLeashKnot getKnotForPosition(World worldValue, BlockPos blockPos) {
        int x = blockPos.getX();
        int j = blockPos.getY();
        int k = blockPos.getZ();

        for (java.util.Iterator<?> __recoveryIterator171_5370 =
                        (worldValue.getEntitiesWithinAABB(
                                        EntityLeashKnot.class,
                                        new BoundingBox(
                                                x - 1.0, j - 1.0, k - 1.0, x + 1.0, j + 1.0,
                                                k + 1.0)))
                                .iterator();
                (__recoveryIterator171_5370).hasNext(); ) {
            EntityLeashKnot entityLeashKnot = (EntityLeashKnot) (__recoveryIterator171_5370).next();
            if (entityLeashKnot.getHangingPosition().equals(blockPos)) {
                return entityLeashKnot;
            }
        }

        return null;
    }

    public void updateFacingWithBoundingBox(Facing facing) {}
}
