package net.minecraft.tileentity;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import com.google.common.collect.Lists;

import java.util.List;

public class TileEntityBanner extends TileEntity {

    private String patternResourceLocation;
    private int baseColor;
    private NBTTagList patterns;
    private static String[] packedTextFragments;

    private List colorList;
    private List patternList;
    private boolean field_175119_g;

    public List getPatternList() {
        this.initializeBannerData();
        return this.patternList;
    }

    public static void removeBannerData(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getSubCompound("BlockEntityTag", false);
        if (compoundTag != null && compoundTag.hasKey("Patterns", 9)) {
            NBTTagList nbtTagList = compoundTag.getTagList("Patterns", 10);
            if (nbtTagList.tagCount() > 0) {
                nbtTagList.removeTag(nbtTagList.tagCount() - 1);
                if (nbtTagList.hasNoTags()) {
                    itemStack.getTag().removeTag("BlockEntityTag");
                    if (itemStack.getTag().hasNoTags()) {
                        itemStack.setTag((CompoundTag) null);
                    }
                }
            }
        }
    }

    public Packet getDescriptionPacket() {
        CompoundTag compoundTag = new CompoundTag();
        this.writeToNBT(compoundTag);
        return new S35PacketUpdateTileEntity(this.pos, 6, compoundTag);
    }

    public void writeToNBT(CompoundTag compoundTag) {
        super.writeToNBT(compoundTag);
        compoundTag.setInteger("Base", this.baseColor);
        if (this.patterns != null) {
            compoundTag.setTag("Patterns", this.patterns);
        }
    }

    static {
        net.minecraft.tileentity.TileEntityBanner.packedTextFragments = new String[5];
        net.minecraft.tileentity.TileEntityBanner.packedTextFragments[0] = new String("");
        net.minecraft.tileentity.TileEntityBanner.packedTextFragments[1] = new String("");
        net.minecraft.tileentity.TileEntityBanner.packedTextFragments[2] = new String("b");
    }

    public int getBaseColor() {
        return this.baseColor;
    }

    public void setItemValues(ItemStack itemStack) {
        {
            MobSpawnerBaseLogic.GU();

            this.patterns = null;

            if (itemStack.hasTagCompound() && itemStack.getTag().hasKey("BlockEntityTag", 10)) {
                CompoundTag compoundTag = itemStack.getTag().getCompoundTag("BlockEntityTag");
                if (compoundTag.hasKey("Patterns")) {
                    this.patterns = (NBTTagList) compoundTag.getTagList("Patterns", 10).copy();
                }

                {
                    if (compoundTag.hasKey("Base", 99)) {
                        this.baseColor = compoundTag.getInteger("Base");

                        
                    } else {

                        this.baseColor = itemStack.getItemDamage() & 15;
                    }
                }

                
            } else {

                this.baseColor = itemStack.getItemDamage() & 15;
            }
        }

        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = packedTextFragments[0];
        this.field_175119_g = true;
    }

    private void initializeBannerData() {
        MobSpawnerBaseLogic.GU();

        if (this.patternList == null
                || this.colorList == null
                || this.patternResourceLocation == null) {
            if (!this.field_175119_g) {
                this.patternResourceLocation = packedTextFragments[1];

                return;
            }

            this.patternList = Lists.newArrayList();
            this.colorList = Lists.newArrayList();
            this.patternList.add(BannerPattern.BASE);
            this.colorList.add(EnumDyeColor.byDyeDamage(this.baseColor));
            this.patternResourceLocation = packedTextFragments[2] + this.baseColor;
            if (this.patterns != null) {
                int i = 0;

                while (i < this.patterns.tagCount()) {
                    CompoundTag compoundTag = this.patterns.getCompoundTagAt(i);
                    BannerPattern patternByID =
                            BannerPattern.getPatternByID(compoundTag.getString("Pattern"));
                    if (patternByID != null) {
                        this.patternList.add(patternByID);
                        int j = compoundTag.getInteger("Color");
                        this.colorList.add(EnumDyeColor.byDyeDamage(j));
                        this.patternResourceLocation =
                                this.patternResourceLocation + patternByID.getPatternID() + j;
                    }

                    i++;
                }
            }
        }
    }

    public static int getPatterns(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getSubCompound("BlockEntityTag", false);
        return compoundTag != null && compoundTag.hasKey("Patterns")
                ? compoundTag.getTagList("Patterns", 10).tagCount()
                : 0;
    }

    public static int getBaseColor(ItemStack itemStack) {
        CompoundTag compoundTag = itemStack.getSubCompound("BlockEntityTag", false);
        return compoundTag != null && compoundTag.hasKey("Base")
                ? compoundTag.getInteger("Base")
                : itemStack.getItemDamage();
    }

    public String func_175116_e() {
        this.initializeBannerData();
        return this.patternResourceLocation;
    }

    public void readFromNBT(CompoundTag compoundTag) {
        super.readFromNBT(compoundTag);
        this.baseColor = compoundTag.getInteger("Base");
        this.patterns = compoundTag.getTagList("Patterns", 10);
        this.patternList = null;
        this.colorList = null;
        this.patternResourceLocation = null;
        this.field_175119_g = true;
    }

    public List getColorList() {
        this.initializeBannerData();
        return this.colorList;
    }
}
