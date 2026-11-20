package net.minecraft.command;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

import com.google.common.base.Functions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public abstract class CommandBase implements ICommand {

    private static String[] packedTextFragments;
    private static Entity[] aXG;

    private static IAdminCommand theAdmin;

    public static List getListOfStringsMatchingLastWord(String[] astring, String... astring1) {
        return getListOfStringsMatchingLastWord(astring, Arrays.asList(astring1));
    }

    public static int parseInt(String text) throws NumberInvalidException {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException numberformatexception) {
            throw new NumberInvalidException(
                    "commands.generic.num.invalid", new Object[] {text});
        }
    }

    public static CommandBaseCoordinateArg parseCoordinate(
            double d0, String text, boolean flag) throws NumberInvalidException {
        return parseCoordinate(d0, text, -30000000, 30000000, flag);
    }

    public int getRequiredPermissionLevel() {
        return 4;
    }

    public static EntityPlayerMP getCommandSenderAsPlayer(CommandSender commandSender)
            throws PlayerNotFoundException {
        if (commandSender instanceof EntityPlayerMP) {
            return (EntityPlayerMP) commandSender;
        } else {
            throw new PlayerNotFoundException(
                    "You must specify which player you wish to perform this action on.",
                    new Object[0]);
        }
    }

    private static Exception a(Exception exception) {
        return exception;
    }

    public static Item getItemByText(CommandSender commandSender, String text)
            throws NumberInvalidException {
        ResourceLocation resourceLocation = new ResourceLocation(text);
        Item item = (Item) Item.itemRegistry.getObject(resourceLocation);
        if (item == null) {
            throw new NumberInvalidException(
                    "commands.give.notFound", new Object[] {resourceLocation});
        } else {
            return item;
        }
    }

    public static double parseDouble(String text) throws NumberInvalidException {
        try {
            double d0 = Double.parseDouble(text);
            if (!Doubles.isFinite(d0)) {
                throw new NumberInvalidException(
                        "commands.generic.num.invalid", new Object[] {text});
            } else {
                return d0;
            }
        } catch (NumberFormatException numberformatexception) {
            throw new NumberInvalidException(
                    "commands.generic.num.invalid", new Object[] {text});
        }
    }

    public static Entity getEntity(
            CommandSender commandSender, String text, Class oclass)
            throws EntityNotFoundException {
        Object object =
                PlayerSelector.matchOneEntity(commandSender, text, oclass);
        MinecraftServer server = MinecraftServer.getServer();
        if (object == null) {
            object = server.getConfigurationManager().getPlayerByUsername(text);
        }

        if (object == null) {
            try {
                UUID uuid = UUID.fromString(text);
                object = server.getEntityFromUuid(uuid);
                if (object == null) {
                    object = server.getConfigurationManager().getPlayerByUUID(uuid);
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw new EntityNotFoundException(
                        "commands.generic.entity.invalidUuid", new Object[0]);
            }
        }

        if (object != null && oclass.isAssignableFrom(object.getClass())) {
            return (Entity) object;
        } else {
            throw new EntityNotFoundException();
        }
    }

    public static List method_cb1cf55a8a3937d053ae(String[] astring, int i, BlockPos blockPos) {
        boolean flag = CommandException.method_b417d7500ec59d69e373();
        if (blockPos == null) {
            return null;
        }

        if (astring.length - 1 == i) {
            String text = Integer.toString(blockPos.getX());
            if (flag) {
                return Lists.newArrayList(new String[] {text});
            }
        }

        if (astring.length - 1 == i + 1) {
            String textValue = Integer.toString(blockPos.getY());
            if (flag) {
                return Lists.newArrayList(new String[] {textValue});
            }
        }

        if (astring.length - 1 != i + 2) {
            return null;
        }

        String textValue2 = Integer.toString(blockPos.getZ());
        return Lists.newArrayList(new String[] {textValue2});
    }

    static {
        net.minecraft.command.CommandBase.packedTextFragments = new String[12];
        net.minecraft.command.CommandBase.packedTextFragments[0] = new String("1");
        net.minecraft.command.CommandBase.packedTextFragments[1] = new String("0");
        net.minecraft.command.CommandBase.packedTextFragments[2] = new String("");
        net.minecraft.command.CommandBase.packedTextFragments[3] = new String(" ");
        net.minecraft.command.CommandBase.packedTextFragments[4] = new String(" ");
        net.minecraft.command.CommandBase.packedTextFragments[5] = new String("~");
        net.minecraft.command.CommandBase.packedTextFragments[6] = new String(".");
        net.minecraft.command.CommandBase.packedTextFragments[7] = new String("~");
        net.minecraft.command.CommandBase.packedTextFragments[8] = new String(".");
        net.minecraft.command.CommandBase.packedTextFragments[9] = new String("");
        b(null);
    }

    public static BlockPos parseBlockPos(
            CommandSender commandSender, String[] astring, int i, boolean flag)
            throws NumberInvalidException {
        BlockPos position = commandSender.getPosition();
        return new BlockPos(
                parseDouble(position.getX(), astring[i], -30000000, 30000000, flag),
                parseDouble(position.getY(), astring[i + 1], 0, 256, false),
                parseDouble(
                        position.getZ(), astring[i + 2], -30000000, 30000000, flag));
    }

    public static ChatComponent join(List list) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        ChatComponentText chatComponentText = new ChatComponentText(packedTextFragments[9]);
        boolean flag = flag1;
        int i = 0;

        while (i < list.size()) {
            if (i > 0) {
                if (i == list.size() - 1) {
                    chatComponentText.appendText(" and ");
                    if (!(!flag)) {

                        if (i > 0) {
                            chatComponentText.appendText(", ");
                        }
                    }
                } else {

                    if (i > 0) {
                        chatComponentText.appendText(", ");
                    }
                }
            }

            chatComponentText.appendSibling((ChatComponent) list.get(i));
            i++;
            if (flag) {
                break;
            }
        }

        return chatComponentText;
    }

    public List addTabCompletionOptions(
            CommandSender commandSender, String[] astring, BlockPos blockPos) {
        return null;
    }

    public boolean canCommandSenderUseCommand(CommandSender commandSender) {
        return commandSender.canCommandSenderUseCommand(
                this.getRequiredPermissionLevel(), this.getCommandName());
    }

    public static ChatComponent getChatComponentFromNthArg(
            CommandSender commandSender, String[] astring, int i, boolean flag)
            throws PlayerNotFoundException {
        boolean flag2 = CommandException.method_b417d7500ec59d69e373();
        ChatComponentText chatComponentText = new ChatComponentText(packedTextFragments[2]);
        boolean flag1 = flag2;
        int j = i;

        while (j < astring.length) {
            if (j > i) {
                chatComponentText.appendText(packedTextFragments[3]);
            }

            Object object = new ChatComponentText(astring[j]);
            if (flag) {
                ChatComponent chatComponent =
                        PlayerSelector.matchEntitiesToChatComponent(
                                commandSender, astring[j]);
                if (chatComponent == null) {
                    if (PlayerSelector.hasArguments(astring[j])) {
                        throw new PlayerNotFoundException();
                    }
                } else {
                    object = chatComponent;
                }
            }

            chatComponentText.appendSibling((ChatComponent) object);
            j++;
            if (!flag1) {
                break;
            }
        }

        return chatComponentText;
    }

    public static long parseLong(String text, long i, long j)
            throws NumberInvalidException {
        long k = parseLong(text);
        if (k < i) {
            throw new NumberInvalidException(
                    "commands.generic.num.tooSmall", new Object[] {k, i});
        } else if (k > j) {
            throw new NumberInvalidException(
                    "commands.generic.num.tooBig", new Object[] {k, j});
        } else {
            return k;
        }
    }

    public static void setAdminCommander(IAdminCommand aXHValue) {
        net.minecraft.command.CommandBase.theAdmin = aXHValue;
    }

    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }

    public static ChatComponent getChatComponentFromNthArg(
            CommandSender commandSender, String[] astring, int i)
            throws CommandException {
        return getChatComponentFromNthArg(commandSender, astring, i, false);
    }

    public List getCommandAliases() {
        return Collections.emptyList();
    }

    public static void notifyOperators(
            CommandSender commandSender,
            ICommand iCommand,
            String text,
            Object... aobject) {
        notifyOperators(commandSender, iCommand, 0, text, aobject);
    }

    public static double parseDouble(String text, double d0)
            throws NumberInvalidException {
        return parseDouble(text, d0, Double.MAX_VALUE);
    }

    public static Entity[] method_da2ac5c94686ee11e9e6() {
        return aXG;
    }

    public static double parseDouble(String text, double d0, double d1)
            throws NumberInvalidException {
        double d2 = parseDouble(text);
        if (d2 < d0) {
            throw new NumberInvalidException(
                    "commands.generic.double.tooSmall", new Object[] {d2, d0});
        } else if (d2 > d1) {
            throw new NumberInvalidException(
                    "commands.generic.double.tooBig", new Object[] {d2, d1});
        } else {
            return d2;
        }
    }

    public static EntityPlayerMP getPlayer(
            CommandSender commandSender, String text) throws PlayerNotFoundException {
        EntityPlayerMP entityPlayerMP =
                PlayerSelector.matchOnePlayer(commandSender, text);
        if (entityPlayerMP == null) {
            try {
                entityPlayerMP =
                        MinecraftServer.getServer()
                                .getConfigurationManager()
                                .getPlayerByUUID(UUID.fromString(text));
            } catch (IllegalArgumentException illegalargumentexception) {
            }
        }

        if (entityPlayerMP == null) {
            entityPlayerMP =
                    MinecraftServer.getServer().getConfigurationManager().getPlayerByUsername(text);
        }

        if (entityPlayerMP == null) {
            throw new PlayerNotFoundException();
        } else {
            return entityPlayerMP;
        }
    }

    public static String joinNiceString(Object[] aobject) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        StringBuilder stringbuilder = new StringBuilder();
        boolean flag = flag1;
        int i = 0;

        while (i < aobject.length) {
            String text;
            text = aobject[i].toString();
            if (i > 0) {
                if (i == aobject.length - 1) {
                    stringbuilder.append(" and ");
                    if (!(!flag)) {

                        stringbuilder.append(", ");
                    }
                } else {

                    stringbuilder.append(", ");
                }
            }

            stringbuilder.append(text);
            i++;
            if (flag) {
                break;
            }
        }

        return stringbuilder.toString();
    }

    public int compareTo(Object object) {
        return this.compareTo((ICommand) object);
    }

    public static long parseLong(String text) throws NumberInvalidException {
        try {
            return Long.parseLong(text);
        } catch (NumberFormatException numberformatexception) {
            throw new NumberInvalidException(
                    "commands.generic.num.invalid", new Object[] {text});
        }
    }

    public static int parseInt(String text, int i)
            throws NumberInvalidException {
        return parseInt(text, i, Integer.MAX_VALUE);
    }

    public static double parseDouble(double d0, String text, boolean flag)
            throws NumberInvalidException {
        return parseDouble(d0, text, -30000000, 30000000, flag);
    }

    public static String joinNiceStringFromCollection(Collection collection) {
        return joinNiceString(collection.toArray(new String[collection.size()]));
    }

    public static CommandBaseCoordinateArg parseCoordinate(
            double d0, String text, int i, int j, boolean flag) throws NumberInvalidException {
        boolean flag1 = text.startsWith(packedTextFragments[5]);
        if (flag1 && Double.isNaN(d0)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {d0});
        }

        double d1 = 0.0;
        if (!flag1 || text.length() > 1) {
            boolean flag2 = text.contains(packedTextFragments[6]);
            if (flag1) {
                text = text.substring(1);
            }

            d1 += parseDouble(text);
            if (!flag2 && !flag1 && flag) {
                d1 += 0.5;
            }
        }

        if (i != 0 || j != 0) {
            if (d1 < i) {
                throw new NumberInvalidException(
                        "commands.generic.double.tooSmall", new Object[] {d1, i});
            }

            if (d1 > j) {
                throw new NumberInvalidException(
                        "commands.generic.double.tooBig", new Object[] {d1, j});
            }
        }

        return new CommandBaseCoordinateArg(d1 + (flag1 ? d0 : 0.0), d1, flag1);
    }

    public static boolean doesStringStartWith(String text, String textValue) {
        return textValue.regionMatches(true, 0, text, 0, text.length());
    }

    public int compareTo(ICommand iCommand) {
        return this.getCommandName()
                .compareTo(iCommand.getCommandName());
    }

    public static void b(Entity[] aXGValue) {
        aXG = aXGValue;
    }

    public static List getListOfStringsMatchingLastWord(String[] astring, Collection collection) {
        String text = astring[astring.length - 1];
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        ArrayList arrayList = Lists.newArrayList();
        boolean flag = flag1;
        if (!collection.isEmpty()) {
            for (java.util.Iterator<?> __recoveryIterator171_40894 =
                            (Iterables.transform(collection, Functions.toStringFunction()))
                                    .iterator();
                    (__recoveryIterator171_40894).hasNext(); ) {
                String textValue = (String) (__recoveryIterator171_40894).next();
                if (doesStringStartWith(text, textValue)) {
                    arrayList.add(textValue);
                }

                if (!flag) {
                    break;
                }
            }

            if (arrayList.isEmpty()) {
                for (Object object : collection) {
                    if (object instanceof ResourceLocation
                            && doesStringStartWith(text, ((ResourceLocation) object).getResourcePath())) {
                        arrayList.add(String.valueOf(object));
                    }

                    if (!flag) {
                        break;
                    }
                }
            }
        }

        return arrayList;
    }

    public static int parseInt(String text, int i, int j)
            throws NumberInvalidException {
        int k = parseInt(text);
        if (k < i) {
            throw new NumberInvalidException(
                    "commands.generic.num.tooSmall", new Object[] {k, i});
        } else if (k > j) {
            throw new NumberInvalidException(
                    "commands.generic.num.tooBig", new Object[] {k, j});
        } else {
            return k;
        }
    }

    public static void notifyOperators(
            CommandSender commandSender,
            ICommand iCommand,
            int i,
            String text,
            Object... aobject) {
        if (net.minecraft.command.CommandBase.theAdmin != null) {
            net.minecraft.command.CommandBase.theAdmin.notifyOperators(
                    commandSender, iCommand, i, text, aobject);
        }
    }

    public static Block getBlockByText(CommandSender commandSender, String text)
            throws NumberInvalidException {
        ResourceLocation resourceLocation = new ResourceLocation(text);
        if (!Block.blockRegistry.containsKey(resourceLocation)) {
            throw new NumberInvalidException(
                    "commands.give.notFound", new Object[] {resourceLocation});
        } else {
            Block block = (Block) Block.blockRegistry.getObject(resourceLocation);
            if (block == null) {
                throw new NumberInvalidException(
                        "commands.give.notFound", new Object[] {resourceLocation});
            } else {
                return block;
            }
        }
    }

    public static String getPlayerName(CommandSender commandSender, String text)
            throws PlayerNotFoundException {
        try {
            return getPlayer(commandSender, text).getName();
        } catch (PlayerNotFoundException playerNotFoundException) {
            if (PlayerSelector.hasArguments(text)) {
                throw playerNotFoundException;
            } else {
                return text;
            }
        }
    }

    public static boolean parseBoolean(String text) throws CommandException {
        if (text.equals("true") || text.equals(packedTextFragments[0])) {
            return true;
        } else if (!text.equals("false") && !text.equals(packedTextFragments[1])) {
            throw new CommandException(
                    "commands.generic.boolean.invalid", new Object[] {text});
        } else {
            return false;
        }
    }

    // region String decoding support

    // endregion

    public static String getEntityName(CommandSender commandSender, String text)
            throws EntityNotFoundException {
        try {
            return getPlayer(commandSender, text).getName();
        } catch (PlayerNotFoundException playerNotFoundException) {
            try {
                return method_824f938e7a5e1aaa9e04(commandSender, text).getUniqueId().toString();
            } catch (EntityNotFoundException entityNotFoundException) {
                if (PlayerSelector.hasArguments(text)) {
                    throw entityNotFoundException;
                } else {
                    return text;
                }
            }
        }
    }

    public static Entity method_824f938e7a5e1aaa9e04(CommandSender commandSender, String text)
            throws EntityNotFoundException {
        return getEntity(commandSender, text, Entity.class);
    }

    public static List method_6c2108a61cf6b9b04dde(CommandSender commandSender, String text)
            throws EntityNotFoundException {
        return PlayerSelector.hasArguments(text)
                ? PlayerSelector.matchEntities(
                        commandSender, text, Entity.class)
                : Lists.newArrayList(
                        new Entity[] {method_824f938e7a5e1aaa9e04(commandSender, text)});
    }

    public static double parseDouble(
            double d0, String text, int i, int j, boolean flag) throws NumberInvalidException {
        boolean flag1 = text.startsWith(packedTextFragments[7]);
        if (flag1 && Double.isNaN(d0)) {
            throw new NumberInvalidException("commands.generic.num.invalid", new Object[] {d0});
        }

        double d1 = flag1 ? d0 : 0.0;
        if (!flag1 || text.length() > 1) {
            boolean flag2 = text.contains(packedTextFragments[8]);
            if (flag1) {
                text = text.substring(1);
            }

            d1 += parseDouble(text);
            if (!flag2 && !flag1 && flag) {
                d1 += 0.5;
            }
        }

        if (i != 0 || j != 0) {
            if (d1 < i) {
                throw new NumberInvalidException(
                        "commands.generic.double.tooSmall", new Object[] {d1, i});
            }

            if (d1 > j) {
                throw new NumberInvalidException(
                        "commands.generic.double.tooBig", new Object[] {d1, j});
            }
        }

        return d1;
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public static String buildString(String[] astring, int i) {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        StringBuilder stringbuilder = new StringBuilder();
        boolean flag = flag1;
        int j = i;

        while (j < astring.length) {
            if (j > i) {
                stringbuilder.append(packedTextFragments[4]);
            }

            String text = astring[j];
            stringbuilder.append(text);
            j++;
            if (!flag) {
                break;
            }
        }

        return stringbuilder.toString();
    }
}
