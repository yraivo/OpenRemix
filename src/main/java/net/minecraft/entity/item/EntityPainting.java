package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class EntityPainting extends EntityHanging {

    public EntityPaintingEnumArt art;

    public int getWidthPixels() {
        return this.art.sizeX;
    }

    public EntityPainting(World worldValue, BlockPos blockPos, Facing facing, String text) {
        this(worldValue, blockPos, facing);
        EntityPaintingEnumArt[] entityPaintingEnumArtArray = EntityPaintingEnumArt.values();
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        int length = entityPaintingEnumArtArray.length;
        boolean flag = flag1;
        int j = 0;

        while (j < length) {
            EntityPaintingEnumArt baIValue = entityPaintingEnumArtArray[j];
            if (baIValue.title.equals(text)) {
                this.art = baIValue;
                if (!flag) {
                    break;
                }
            }

            j++;
            if (flag) {
                break;
            }
        }

        this.updateFacingWithBoundingBox(facing);
    }

    public void setLocationAndAngles(double d0, double d1, double d2, float f, float f1) {
        BlockPos blockPos = new BlockPos(d0 - this.posX, d1 - this.posY, d2 - this.posZ);
        BlockPos blockPosValue = this.hangingPosition.add(blockPos);
        this.setPosition(blockPosValue.getX(), blockPosValue.getY(), blockPosValue.getZ());
    }


    public EntityPainting(World worldValue, BlockPos blockPos, Facing facing) {
        super(worldValue, blockPos);
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        ArrayList arrayList = Lists.newArrayList();
        EntityPaintingEnumArt[] entityPaintingEnumArtArray = EntityPaintingEnumArt.values();
        boolean flag = flag1;

        for (EntityPaintingEnumArt baIValue : entityPaintingEnumArtArray) {
            this.art = baIValue;
            this.updateFacingWithBoundingBox(facing);
            if (this.onValidSurface()) {
                arrayList.add(baIValue);
            }

            if (flag) {
                break;
            }
        }

        if (!arrayList.isEmpty()) {
            this.art = (EntityPaintingEnumArt) arrayList.get(this.rand.nextInt(arrayList.size()));
        }

        this.updateFacingWithBoundingBox(facing);
    }

    public void onBroken(Entity entity) {
        if (this.world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            if (entity instanceof PlayerEntity) {
                PlayerEntity playerEntity = (PlayerEntity) entity;
                if (playerEntity.capabilities.isCreativeMode) {
                    return;
                }
            }

            this.entityDropItem(new ItemStack(Items.painting), 0.0F);
        }
    }

    public void setPositionAndRotation2(
            double d0, double d1, double d2, float f, float f1, int i, boolean flag) {
        BlockPos blockPos = new BlockPos(d0 - this.posX, d1 - this.posY, d2 - this.posZ);
        BlockPos blockPosValue = this.hangingPosition.add(blockPos);
        this.setPosition(blockPosValue.getX(), blockPosValue.getY(), blockPosValue.getZ());
    }

    public void writeEntityToNBT(CompoundTag compoundTag) {
        compoundTag.setString("Motive", this.art.title);
        super.writeEntityToNBT(compoundTag);
    }

    public EntityPainting(World worldValue) {
        super(worldValue);
    }

    public int getHeightPixels() {
        return this.art.sizeY;
    }

    public void readEntityFromNBT(CompoundTag compoundTag) {
        String text = compoundTag.getString("Motive");
        boolean flag1 = EntityMinecartEnumMinecartType.d();
        EntityPaintingEnumArt[] entityPaintingEnumArtArray = EntityPaintingEnumArt.values();
        int length = entityPaintingEnumArtArray.length;
        int j = 0;
        boolean flag = flag1;

        while (j < length) {
            EntityPaintingEnumArt baIValue = entityPaintingEnumArtArray[j];
            if (baIValue.title.equals(text)) {
                this.art = baIValue;
            }

            j++;
            if (flag) {
                break;
            }
        }

        if (this.art == null) {
            this.art = EntityPaintingEnumArt.KEBAB;
        }

        super.readEntityFromNBT(compoundTag);
    }
}
