package net.minecraft.item;

import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.BlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Facing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class ItemRecord extends Item {
    public String recordName;
    private static Map RECORDS;

    public boolean onItemUse(
            ItemStack itemStack,
            PlayerEntity playerEntity,
            World world,
            BlockPos blockPos,
            Facing facing,
            float f,
            float f1,
            float f2) {
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() != Blocks.jukebox
                || (Boolean) blockState.getValue(BlockJukebox.HAS_RECORD)) {
            return false;
        }

        if (world.isRemote) {
            return true;
        }

        ((BlockJukebox) Blocks.jukebox)
                .insertRecord(world, blockPos, blockState, itemStack);
        world.playAuxSFXAtEntity((PlayerEntity) null, 1005, blockPos, Item.getIdFromItem(this));
        itemStack.stackSize--;
        return true;
    }

    public EnumRarity getRarity(ItemStack itemStack) {
        return EnumRarity.RARE;
    }

    static {
        net.minecraft.item.ItemRecord.RECORDS = Maps.newHashMap();
    }

    protected ItemRecord(String text) {
        this.recordName = text;
        this.maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabMisc);
        net.minecraft.item.ItemRecord.RECORDS.put("records." + text, this);
    }

    public void addInformation(
            ItemStack itemStack, PlayerEntity playerEntity, List list, boolean flag) {
        list.add(this.getRecordNameLocal());
    }

    public static ItemRecord getRecord(String text) {
        return (ItemRecord) net.minecraft.item.ItemRecord.RECORDS.get(text);
    }

    public String getRecordNameLocal() {
        return StatCollector.translateToLocal("item.record." + this.recordName + ".desc");
    }
}
