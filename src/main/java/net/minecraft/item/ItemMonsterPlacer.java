package net.minecraft.item;

import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityListEntityEggInfo;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.RayHitType;
import net.minecraft.util.RayTraceResult;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class ItemMonsterPlacer extends Item {

    private static String[] packedTextFragments;

    public ItemStack onItemRightClick(ItemStack itemStack, World world, PlayerEntity playerEntity) {
        if (world.isRemote) {
            return itemStack;
        }

        RayTraceResult rayTraceResult = this.getMovingObjectPositionFromPlayer(world, playerEntity, true);
        if (rayTraceResult == null) {
            return itemStack;
        }

        if (rayTraceResult.typeOfHit == RayHitType.BLOCK) {
            BlockPos blockPos = rayTraceResult.getBlockPos();
            if (!world.isBlockModifiable(playerEntity, blockPos)) {
                return itemStack;
            }

            if (!playerEntity.canPlayerEdit(blockPos, rayTraceResult.sideHit, itemStack)) {
                return itemStack;
            }

            if (world.getBlockState(blockPos).getBlock() instanceof BlockLiquid) {
                Entity entity =
                        spawnCreature(
                                world,
                                itemStack.getItemDamage(),
                                blockPos.getX() + 0.5,
                                blockPos.getY() + 0.5,
                                blockPos.getZ() + 0.5);
                if (entity != null) {
                    if (entity instanceof LivingEntity && itemStack.hasDisplayName()) {
                        ((EntityLiving) entity).setCustomNameTag(itemStack.getDisplayName());
                    }

                    if (!playerEntity.capabilities.isCreativeMode) {
                        itemStack.stackSize--;
                    }

                    playerEntity.triggerAchievement(
                            StatList.objectUseStats[Item.getIdFromItem(this)]);
                }
            }
        }

        return itemStack;
    }

    public ItemMonsterPlacer() {
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.tabMisc);
    }

    public static Entity spawnCreature(
            World world, int i, double d0, double d1, double d2) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (!EntityList.entityEggs.containsKey(i)) {
            return null;
        }

        Entity entity = null;
        int j = 0;

        while (j < 1) {
            entity = EntityList.createEntityByID(i, world);
            if (entity instanceof LivingEntity) {
                EntityLiving entityLiving = (EntityLiving) entity;
                entity.setLocationAndAngles(
                        d0,
                        d1,
                        d2,
                        MathHelper.wrapAngleTo180(world.rand.nextFloat() * 360.0F),
                        0.0F);
                entityLiving.rotationYawHead = entityLiving.rotationYaw;
                entityLiving.renderYawOffset = entityLiving.rotationYaw;
                entityLiving.onInitialSpawn(
                        world.getDifficultyForLocation(new BlockPos(entityLiving)),
                        (IEntityLivingData) null);
                world.spawnEntityInWorld(entity);
                entityLiving.playLivingSound();
            }

            j++;
            if (text != null) {
                break;
            }
        }

        return entity;
    }

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        if (world.isRemote) {
            return true;
        }

        if (!playerEntity.canPlayerEdit(blockPos.offset(facing), facing, itemStack)) {
            return false;
        }

        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == Blocks.mob_spawner) {
            TileEntity tileEntity = world.getTileEntity(blockPos);
            if (tileEntity instanceof TileEntityMobSpawner) {
                MobSpawnerBaseLogic spawnerBaseLogic =
                        ((TileEntityMobSpawner) tileEntity).getSpawnerBaseLogic();
                spawnerBaseLogic.setEntityName(
                        EntityList.getStringFromID(itemStack.getItemDamage()));
                tileEntity.markDirty();
                world.markBlockForUpdate(blockPos);
                if (!playerEntity.capabilities.isCreativeMode) {
                    itemStack.stackSize--;
                }

                return true;
            }
        }

        blockPos = blockPos.offset(facing);
        double d0 = 0.0;
        if (facing == Facing.UP && blockState instanceof BlockFence) {
            d0 = 0.5;
        }

        Entity entity =
                spawnCreature(
                        world,
                        itemStack.getItemDamage(),
                        blockPos.getX() + 0.5,
                        blockPos.getY() + d0,
                        blockPos.getZ() + 0.5);
        if (entity != null) {
            if (entity instanceof LivingEntity && itemStack.hasDisplayName()) {
                entity.setCustomNameTag(itemStack.getDisplayName());
            }

            if (!playerEntity.capabilities.isCreativeMode) {
                itemStack.stackSize--;
            }
        }

        return true;
    }

    public String getItemStackDisplayName(ItemStack itemStack) {
        String text =
                (packedTextFragments[0]
                                + StatCollector.translateToLocal(
                                        this.getUnlocalizedName() + ".name"))
                        .trim();
        String textValue = EntityList.getStringFromID(itemStack.getItemDamage());
        if (textValue != null) {
            text =
                    text
                            + packedTextFragments[1]
                            + StatCollector.translateToLocal("entity." + textValue + ".name");
        }

        return text;
    }

    static {
        net.minecraft.item.ItemMonsterPlacer.packedTextFragments = new String[3];
        net.minecraft.item.ItemMonsterPlacer.packedTextFragments[0] = new String("");
        net.minecraft.item.ItemMonsterPlacer.packedTextFragments[1] = new String(" ");
    }

    public void getSubItems(Item item, CreativeTabs creativeTabs, List list) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        Iterator iterator = EntityList.entityEggs.values().iterator();
        String textValue = text;

        while (iterator.hasNext()) {
            EntityListEntityEggInfo entityListEntityEggInfo =
                    (EntityListEntityEggInfo) iterator.next();
            list.add(new ItemStack(item, 1, entityListEntityEggInfo.spawnedID));
            if (textValue != null) {
                break;
            }
        }
    }

    public int getColorFromItemStack(ItemStack itemStack, int i) {
        EntityListEntityEggInfo entityListEntityEggInfo =
                (EntityListEntityEggInfo) EntityList.entityEggs.get(itemStack.getItemDamage());
        return entityListEntityEggInfo != null
                ? (i == 0
                        ? entityListEntityEggInfo.primaryColor
                        : entityListEntityEggInfo.secondaryColor)
                : 16777215;
    }
}
