package net.minecraft.entity.effect;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import java.util.List;

public class EntityLightningBolt extends EntityWeatherEffect {
    private int lightningState;
    private int boltLivingTime;

    public long boltVertex;

    protected void entityInit() {}

    protected void readEntityFromNBT(CompoundTag compoundTag) {}

    public void onUpdate() {
        int k = CompoundTag.method_beea3e16fe3ec0995eb9();
        super.onUpdate();
        int i = k;
        if (this.lightningState == 2) {
            this.world.playSoundEffect(
                    this.posX,
                    this.posY,
                    this.posZ,
                    "ambient.weather.thunder",
                    10000.0F,
                    0.8F + this.rand.nextFloat() * 0.2F);
            this.world.playSoundEffect(
                    this.posX,
                    this.posY,
                    this.posZ,
                    "random.explode",
                    2.0F,
                    0.5F + this.rand.nextFloat() * 0.2F);
        }

        this.lightningState--;
        label50:
        if (this.lightningState < 0) {
            if (this.boltLivingTime == 0) {
                this.setDead();
                if (i == 0) {
                    break label50;
                }
            }

            if (this.lightningState < -this.rand.nextInt(10)) {
                this.boltLivingTime--;
                this.lightningState = 1;
                this.boltVertex = this.rand.nextLong();
                BlockPos blockPos = new BlockPos(this);
                if (!this.world.isRemote
                        && this.world.getGameRules().getGameRuleBooleanValue("doFireTick")
                        && this.world.isAreaLoaded(blockPos, 10)
                        && this.world.getBlockState(blockPos).getBlock().getMaterial()
                                == Material.AIR
                        && Blocks.fire.canPlaceBlockAt(this.world, blockPos)) {
                    this.world.setBlockState(blockPos, Blocks.fire.getDefaultState());
                }
            }
        }

        label44:
        if (this.lightningState >= 0) {
            if (this.world.isRemote) {
                this.world.setLastLightningBolt(2);
                if (i == 0) {
                    break label44;
                }
            }

            double d0 = 3.0;
            List entitiesWithinAABBExcludingEntity =
                    this.world.getEntitiesWithinAABBExcludingEntity(
                            this,
                            new BoundingBox(
                                    this.posX - d0,
                                    this.posY - d0,
                                    this.posZ - d0,
                                    this.posX + d0,
                                    this.posY + 6.0 + d0,
                                    this.posZ + d0));
            int j = 0;

            while (j < entitiesWithinAABBExcludingEntity.size()) {
                Entity entity = (Entity) entitiesWithinAABBExcludingEntity.get(j);
                entity.onStruckByLightning(this);
                j++;
                if (i != 0) {
                    break;
                }
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            CompoundTag.b(++i);
        }
    }

    public EntityLightningBolt(World worldValue, double d0, double d1, double d2) {
        this(worldValue, d0, d1, d2, CompoundTag.aW());
    }

    private EntityLightningBolt(
            World worldValue, double d0, double d1, double d2, int initialFlowToken) {
        super(worldValue);

        int k = initialFlowToken;

        this.setLocationAndAngles(d0, d1, d2, 0.0F, 0.0F);
        this.lightningState = 2;
        this.boltVertex = this.rand.nextLong();
        int i = k;
        this.boltLivingTime = this.rand.nextInt(3) + 1;
        if (!worldValue.isRemote
                && worldValue.getGameRules().getGameRuleBooleanValue("doFireTick")
                && (worldValue.getDifficulty() == EnumDifficulty.NORMAL
                        || worldValue.getDifficulty() == EnumDifficulty.HARD)
                && worldValue.isAreaLoaded(new BlockPos(this), 10)) {
            BlockPos blockPos = new BlockPos(this);
            if (worldValue.getBlockState(blockPos).getBlock().getMaterial() == Material.AIR
                    && Blocks.fire.canPlaceBlockAt(worldValue, blockPos)) {
                worldValue.setBlockState(blockPos, Blocks.fire.getDefaultState());
            }

            int j = 0;

            while (j < 4) {
                BlockPos blockPosValue =
                        blockPos.add(
                                this.rand.nextInt(3) - 1,
                                this.rand.nextInt(3) - 1,
                                this.rand.nextInt(3) - 1);
                if (worldValue.getBlockState(blockPosValue).getBlock().getMaterial() == Material.AIR
                        && Blocks.fire.canPlaceBlockAt(worldValue, blockPosValue)) {
                    worldValue.setBlockState(blockPosValue, Blocks.fire.getDefaultState());
                }

                j++;
                if (i == 0) {
                    Entity.setLegacyGlobalFlowMarker(new String[5]);
                    break;
                }
            }
        }
    }

    protected void writeEntityToNBT(CompoundTag compoundTag) {}


}
