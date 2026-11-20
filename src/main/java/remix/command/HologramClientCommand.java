package remix.command;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import remix.command.AbstractClientCommand;
import remix.command.CommandAction;

public final class HologramClientCommand extends AbstractClientCommand {
    private static String[] packedTextFragments;

    static {
        remix.command.HologramClientCommand.packedTextFragments = new String[7];
        remix.command.HologramClientCommand.packedTextFragments[0] = new String(" ");
        remix.command.HologramClientCommand.packedTextFragments[1] = new String(" ");
        remix.command.HologramClientCommand.packedTextFragments[2] = new String("");
        remix.command.HologramClientCommand.packedTextFragments[3] = new String("&");
        remix.command.HologramClientCommand.packedTextFragments[4] = new String("\u00a7");
    }

    public String getUsage() {
        return "hologram <text>";
    }

    public HologramClientCommand() {
        super("Hologram", new String[] {"hologram", "hg"});
        this.addAction(
                CommandAction.requiringArguments(
                        2,
                        astring -> {
                            boolean flag = TargetsClientCommand.isStandardTargetFlow();
                            if (!ec.playerController.isInCreativeMode()) {
                                ClientCommandManager.printMessage(
                                        "You must be in creative to execute this command!");
                                return true;
                            }

                            StringBuilder stringbuilder = new StringBuilder();
                            int i = 1;

                            while (i < astring.length) {
                                stringbuilder.append(packedTextFragments[0]).append(astring[i]);
                                i++;
                                if (!flag) {
                                    break;
                                }
                            }

                            String text = stringbuilder.toString();
                            String textValue =
                                    text.replaceFirst(
                                            packedTextFragments[1], packedTextFragments[2]);
                            ItemStack itemStack = new ItemStack(Items.armor_stand);
                            CompoundTag compoundTag = new CompoundTag();
                            NBTTagList nbtTagList = new NBTTagList();
                            nbtTagList.appendTag(new NBTTagDouble(ec.player.posX));
                            nbtTagList.appendTag(new NBTTagDouble(ec.player.posY));
                            nbtTagList.appendTag(new NBTTagDouble(ec.player.posZ));
                            compoundTag.setBoolean("Invisible", true);
                            compoundTag.setBoolean("NoGravity", true);
                            compoundTag.setBoolean("CustomNameVisible", true);
                            compoundTag.setString(
                                    "CustomName",
                                    textValue.replaceAll(
                                            packedTextFragments[3], packedTextFragments[4]));
                            compoundTag.setTag("Pos", nbtTagList);
                            itemStack.setTagInfo("EntityTag", compoundTag);
                            itemStack.setStackDisplayName("Place me to create the hologram!");
                            ec.getNetworkHandler()
                                    .sendPacketNoEvent(
                                            new C10PacketCreativeInventoryAction(36, itemStack));
                            ClientCommandManager.printMessage(
                                    "Successfully given you the hologram!");
                            return true;
                        }));
    }
}
