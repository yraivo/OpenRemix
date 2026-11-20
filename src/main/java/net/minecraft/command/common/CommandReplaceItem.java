package net.minecraft.command.common;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStatsType;
import net.minecraft.command.CommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

public class CommandReplaceItem extends CommandBase {

    private static boolean d;
    private static Map SHORTCUTS;

    public int getRequiredPermissionLevel() {
        return 2;
    }

    protected String[] getUsernames() {
        return MinecraftServer.getServer().getAllUsernames();
    }

    public static void method_a8745b073fa8adfa1857(boolean flag) {
        d = flag;
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return astring.length > 0 && astring[0].equals("entity") && i == 1;
    }

    public String getCommandName() {
        return "replaceitem";
    }

    public static boolean method_b417d7500ec59d69e373() {
        return d;
    }

    public String getCommandUsage(CommandSender commandSender) {
        return "commands.replaceitem.usage";
    }

    // region String decoding support

    // endregion

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        boolean flag = method_d87039b07bd6961907f2();
        List list =
                astring.length == 1
                        ? getListOfStringsMatchingLastWord(astring, new String[] {"entity", "block"})
                        : (astring.length == 2 && astring[0].equals("entity")
                                ? getListOfStringsMatchingLastWord(astring, this.getUsernames())
                                : ((astring.length != 3 || !astring[0].equals("entity"))
                                                && (astring.length != 5
                                                        || !astring[0].equals("block"))
                                        ? (astring.length == 4 && astring[0].equals("entity")
                                                        || astring.length == 6
                                                                && astring[0].equals("block")
                                                ? getListOfStringsMatchingLastWord(
                                                        astring, Item.itemRegistry.getKeys())
                                                : null)
                                        : getListOfStringsMatchingLastWord(astring, net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.keySet())));
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            method_a8745b073fa8adfa1857(!flag);
        }

        return list;
    }

    public static boolean method_d87039b07bd6961907f2() {
        boolean flag = method_b417d7500ec59d69e373();
        return !flag;
    }

    private static CommandException method_94a99a47e223766d1a34(
            CommandException commandException) {
        return commandException;
    }

    static {
        method_a8745b073fa8adfa1857(false);
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS = Maps.newHashMap();
        for (int l = 0; l < 54; l++) {
            net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.container." + l, l);
        }
        for (int j1 = 0; j1 < 9; j1++) {
            net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.hotbar." + j1, j1);
        }
        for (int k1 = 0; k1 < 27; k1++) {
            net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.inventory." + k1, 9 + k1);
        }
        for (int l1 = 0; l1 < 27; l1++) {
            net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.enderchest." + l1, 200 + l1);
        }
        for (int i2 = 0; i2 < 8; i2++) {
            net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.villager." + i2, 300 + i2);
        }
        for (int j2 = 0; j2 < 15; j2++) {
            net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.horse." + j2, 500 + j2);
        }
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.weapon", 99);
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.armor.head", 103);
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.armor.chest", 102);
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.armor.legs", 101);
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.armor.feet", 100);
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.horse.saddle", 400);
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.horse.armor", 401);
        net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.put("slot.horse.chest", 499);
    }

    private int getSlotForShortcut(String text) throws CommandException {
        if (!net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.containsKey(text)) {
            throw new CommandException(
                    "commands.generic.parameter.invalid", new Object[] {text});
        } else {
            return (Integer) net.minecraft.command.common.CommandReplaceItem.SHORTCUTS.get(text);
        }
    }

    // region String decoding support

    // endregion

    public void processCommand(CommandSender commandSender, String[] astring)
            throws CommandException {
        method_d87039b07bd6961907f2();

        if (astring.length < 1) {
            throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
        }

        byte b0;
        {
            if (astring[0].equals("entity")) {
                b0 = 0;

                
            } else {

                if (!astring[0].equals("block")) {
                    throw new WrongUsageException("commands.replaceitem.usage", new Object[0]);
                }

                b0 = 1;
            }
        }

        byte b1;
        {
            if (b0 != 0) {
                if (astring.length < 6) {
                    throw new WrongUsageException(
                            "commands.replaceitem.block.usage", new Object[0]);
                }

                b1 = 4;

                
            } else {

                if (astring.length < 4) {
                    throw new WrongUsageException(
                            "commands.replaceitem.entity.usage", new Object[0]);
                }

                b1 = 2;
            }
        }

        int i = b1 + 1;
        int j = this.getSlotForShortcut(astring[b1]);

        Item item;
        try {
            item = getItemByText(commandSender, astring[i]);
        } catch (NumberInvalidException numberInvalidException) {
            if (Block.getBlockFromName(astring[i]) != Blocks.air) {
                throw numberInvalidException;
            }

            item = null;
        }

        i++;
        int k = astring.length > i ? parseInt(astring[i++], 1, 64) : 1;
        int l = astring.length > i ? parseInt(astring[i++]) : 0;
        ItemStack itemStack = new ItemStack(item, k, l);
        if (astring.length > i) {
            String text =
                    getChatComponentFromNthArg(commandSender, astring, i).getUnformattedText();

            try {
                itemStack.setTag(JsonToNBT.getTagFromJson(text));
            } catch (NBTException nbtException) {
                throw new CommandException(
                        "commands.replaceitem.tagError",
                        new Object[] {nbtException.getMessage()});
            }
        }

        if (itemStack.getItem() == null) {
            itemStack = null;
        }

        {
            if (b0 != 0) {
                commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ITEMS, 0);
                BlockPos blockPos = parseBlockPos(commandSender, astring, 1, false);
                World entityWorld = commandSender.getEntityWorld();
                TileEntity tileEntity = entityWorld.getTileEntity(blockPos);
                if (tileEntity == null || !(tileEntity instanceof IInventory)) {
                    throw new CommandException(
                            "commands.replaceitem.noContainer",
                            new Object[] {blockPos.getX(), blockPos.getY(), blockPos.getZ()});
                }

                IInventory iInventory = (IInventory) tileEntity;
                if (j >= 0 && j < iInventory.getSizeInventory()) {
                    iInventory.setInventorySlotContents(j, itemStack);
                }

                
            } else {

                Entity entity = method_824f938e7a5e1aaa9e04(commandSender, astring[1]);
                commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ITEMS, 0);
                if (entity instanceof PlayerEntity) {
                    ((PlayerEntity) entity).inventoryContainer.detectAndSendChanges();
                }

                if (!entity.replaceItemInInventory(j, itemStack)) {
                    throw new CommandException(
                            "commands.replaceitem.failed",
                            new Object[] {j, k, itemStack == null ? "Air" : itemStack.getChatComponent()});
                }

                if (entity instanceof PlayerEntity) {
                    ((PlayerEntity) entity).inventoryContainer.detectAndSendChanges();
                }
            }
        }

        commandSender.setCommandStat(CommandResultStatsType.AFFECTED_ITEMS, k);
        notifyOperators(
                commandSender,
                this,
                "commands.replaceitem.success",
                new Object[] {j, k, itemStack == null ? "Air" : itemStack.getChatComponent()});
    }
}
