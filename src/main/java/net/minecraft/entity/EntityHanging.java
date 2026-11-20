package net.minecraft.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Facing;
import net.minecraft.util.FacingAxis;
import net.minecraft.world.World;

import org.apache.commons.lang3.Validate;

public abstract class EntityHanging extends Entity {
    protected BlockPos hangingPosition;

    public Facing facingDirection;
    private int tickCounter1;

    public Facing getHorizontalFacing() {
        return this.facingDirection;
    }

    private void updateBoundingBox() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (this.facingDirection != null) {
            double d6;
            double d7;
            double d8;
            double posXValue;
            double posYValue;
            double posZValue;
            {
                double d0 = this.hangingPosition.getX() + 0.5;
                double d1 = this.hangingPosition.getY() + 0.5;
                double d2 = this.hangingPosition.getZ() + 0.5;

                double d4 = this.az(this.getWidthPixels());
                double d5 = this.az(this.getHeightPixels());
                double d9 = d0 - this.facingDirection.getFrontOffsetX() * 0.46875;
                double d12 = d2 - this.facingDirection.getFrontOffsetZ() * 0.46875;
                posYValue = d1 + d5;
                Facing facing = this.facingDirection.rotateYCCW();
                posXValue = d9 + d4 * facing.getFrontOffsetX();
                posZValue = d12 + d4 * facing.getFrontOffsetZ();
                this.posX = posXValue;
                this.posY = posYValue;
                this.posZ = posZValue;
                d6 = this.getWidthPixels();
                d7 = this.getHeightPixels();
                d8 = this.getWidthPixels();
                if (this.facingDirection.getAxis() == FacingAxis.Z) {
                    d8 = 1.0;
                    if (!(astring == null)) {

                        d6 = 1.0;
                    }
                } else {

                    d6 = 1.0;
                }
            }

            d6 /= 32.0;
            d7 /= 32.0;
            d8 /= 32.0;
            this.setBoundingBox(
                    new BoundingBox(
                            posXValue - d6,
                            posYValue - d7,
                            posZValue - d8,
                            posXValue + d6,
                            posYValue + d7,
                            posZValue + d8));
        }
    }

    protected boolean shouldSetPositionAfterLoading() {
        return false;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        Facing facing;
        label19:
        {
            String[] astring1 = SharedMonsterAttributes.getLegacyEntityFlowMarker();
            this.hangingPosition =
                    new BlockPos(
                            compoundTag.getInteger("TileX"),
                            compoundTag.getInteger("TileY"),
                            compoundTag.getInteger("TileZ"));
            String[] astring = astring1;
            if (compoundTag.hasKey("Direction", 99)) {
                facing = Facing.getHorizontal(compoundTag.getByte("Direction"));
                this.hangingPosition = this.hangingPosition.offset(facing);
                if (astring == null) {
                    break label19;
                }
            }

            if (compoundTag.hasKey("Facing", 99)) {
                facing = Facing.getHorizontal(compoundTag.getByte("Facing"));
                if (astring == null) {
                    break label19;
                }
            }

            facing = Facing.getHorizontal(compoundTag.getByte("Dir"));
        }

        this.updateFacingWithBoundingBox(facing);
    }

    public abstract int getHeightPixels();

    public void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setByte("Facing", (byte) this.facingDirection.getHorizontalIndex());
        compoundTag.setInteger("TileX", this.getHangingPosition().getX());
        compoundTag.setInteger("TileY", this.getHangingPosition().getY());
        compoundTag.setInteger("TileZ", this.getHangingPosition().getZ());
    }

    public boolean hitByEntity(Entity entity) {
        return entity instanceof PlayerEntity
                ? this.attackEntityFrom(DamageSource.causePlayerDamage((PlayerEntity) entity), 0.0F)
                : false;
    }

    public BlockPos getHangingPosition() {
        return this.hangingPosition;
    }

    public boolean canBeCollidedWith() {
        return true;
    }

    public boolean onValidSurface() {
        String[] astring = SharedMonsterAttributes.getLegacyEntityFlowMarker();
        if (!this.world.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
            return false;
        }

        int i = Math.max(1, this.getWidthPixels() / 16);
        int j = Math.max(1, this.getHeightPixels() / 16);
        BlockPos blockPos = this.hangingPosition.offset(this.facingDirection.getOpposite());
        Facing facing = this.facingDirection.rotateYCCW();
        int k = 0;

        label42:
        while (k < i) {
            int l = 0;

            while (true) {
                if (l < j) {
                    BlockPos blockPosValue = blockPos.offset(facing, k).offsetUp(l);
                    Block block = this.world.getBlockState(blockPosValue).getBlock();
                    if (!block.getMaterial().isSolid()
                            && !BlockRedstoneDiode.isRedstoneRepeaterBlockID(block)) {
                        return false;
                    }

                    l++;
                    if (astring == null) {
                        continue;
                    }
                }

                k++;
                if (astring != null) {
                    break label42;
                }
                break;
            }
        }

        for (java.util.Iterator<?> __recoveryIterator171_7816 =
                        (this.world.getEntitiesWithinAABBExcludingEntity(
                                        this, this.getEntityBoundingBox()))
                                .iterator();
                (__recoveryIterator171_7816).hasNext(); ) {
            Entity entity = (Entity) (__recoveryIterator171_7816).next();
            if (entity instanceof EntityHanging) {
                return false;
            }
        }

        return true;
    }

    public void moveEntity(double d0, double d1, double d2) {
        if (!this.world.isRemote && !this.isDead && d0 * d0 + d1 * d1 + d2 * d2 > 0.0) {
            this.setDead();
            this.onBroken((Entity) null);
        }
    }

    public abstract int getWidthPixels();

    private double az(int i) {
        return i % 32 == 0 ? 0.5 : 0.0;
    }

    public void setPosition(double posXValue, double posYValue, double posZValue) {
        this.posX = posXValue;
        this.posY = posYValue;
        this.posZ = posZValue;
        BlockPos aZEValue = this.hangingPosition;
        this.hangingPosition = new BlockPos(posXValue, posYValue, posZValue);
        if (!this.hangingPosition.equals(aZEValue)) {
            this.updateBoundingBox();
            this.isAirBorne = true;
        }
    }


    protected void entityInit() {}

    public EntityHanging(World worldValue, BlockPos aZEValue) {
        this(worldValue);
        this.hangingPosition = aZEValue;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (this.isEntityInvulnerable(damageSource)) {
            return false;
        }

        if (!this.isDead && !this.world.isRemote) {
            this.setDead();
            this.setBeenAttacked();
            this.onBroken(damageSource.getEntity());
        }

        return true;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.tickCounter1++ == 100 && !this.world.isRemote) {
            this.tickCounter1 = 0;
            if (!this.isDead && !this.onValidSurface()) {
                this.setDead();
                this.onBroken((Entity) null);
            }
        }
    }

    public EntityHanging(World worldValue) {
        super(worldValue);
        this.setSize(0.5F, 0.5F);
    }

    protected void updateFacingWithBoundingBox(Facing aZGValue) {
        Validate.notNull(aZGValue);
        Validate.isTrue(aZGValue.getAxis().isHorizontal());
        this.facingDirection = aZGValue;
        this.prevRotationYaw = this.rotationYaw = this.facingDirection.getHorizontalIndex() * 90;
        this.updateBoundingBox();
    }

    public void addVelocity(double d0, double d1, double d2) {
        if (!this.world.isRemote && !this.isDead && d0 * d0 + d1 * d1 + d2 * d2 > 0.0) {
            this.setDead();
            this.onBroken((Entity) null);
        }
    }

    public abstract void onBroken(Entity entity);
}
