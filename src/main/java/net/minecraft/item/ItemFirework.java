package net.minecraft.item;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class ItemFirework extends Item {

    private static String[] packedTextFragments;

    static {
        net.minecraft.item.ItemFirework.packedTextFragments = new String[3];
        net.minecraft.item.ItemFirework.packedTextFragments[0] = new String(" ");
    }

    public void addInformation(
            ItemStack itemStack, PlayerEntity playerEntity, List list, boolean flag) {
        String text = ItemStack.getLegacyItemStackFlowMarker();
        if (itemStack.hasTagCompound()) {
            CompoundTag compoundTag = itemStack.getTag().getCompoundTag("Fireworks");
            if (compoundTag != null) {
                if (compoundTag.hasKey("Flight", 99)) {
                    list.add(
                            StatCollector.translateToLocal("item.fireworks.flight")
                                    + packedTextFragments[0]
                                    + compoundTag.getByte("Flight"));
                }

                NBTTagList nbtTagList = compoundTag.getTagList("Explosions", 10);
                if (nbtTagList != null && nbtTagList.tagCount() > 0) {
                    int i = 0;

                    while (i < nbtTagList.tagCount()) {
                        CompoundTag compoundTagValue = nbtTagList.getCompoundTagAt(i);
                        ArrayList arrayList = Lists.newArrayList();
                        ItemFireworkCharge.addExplosionInfo(compoundTagValue, arrayList);
                        if (arrayList.size() > 0) {
                            int j = 1;

                            while (j < arrayList.size()) {
                                arrayList.set(j, "  " + (String) arrayList.get(j));
                                j++;
                                if (text != null) {
                                    break;
                                }
                            }

                            list.addAll(arrayList);
                        }

                        i++;
                        if (text != null) {
                            break;
                        }
                    }
                }
            }
        }
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
        if (!world.isRemote) {
            EntityFireworkRocket entityFireworkRocket =
                    new EntityFireworkRocket(
                            world,
                            blockPos.getX() + f,
                            blockPos.getY() + f1,
                            blockPos.getZ() + f2,
                            itemStack);
            world.spawnEntityInWorld(entityFireworkRocket);
            if (!playerEntity.capabilities.isCreativeMode) {
                itemStack.stackSize--;
            }

            return true;
        } else {
            return false;
        }
    }
}
