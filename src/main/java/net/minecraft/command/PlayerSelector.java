package net.minecraft.command;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BoundingBox;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettingsGameType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayerSelector {
    private static Pattern tokenPattern;
    private static Pattern keyValueListPattern;

    private static String[] packedTextFragments;
    private static Pattern intListPattern;

    private static Set WORLD_BINDING_ARGS;

    private static List getRotationsPredicates(Map map) {
        ArrayList arrayList = Lists.newArrayList();
        if (map.containsKey("rym") || map.containsKey("ry")) {
            int i = getGamemode(parseIntWithDefault(map, "rym", 0));
            int j = getGamemode(parseIntWithDefault(map, "ry", 359));
            arrayList.add(new GetRotationsPredicates(i, j));
        }

        if (map.containsKey("rxm") || map.containsKey("rx")) {
            int k = getGamemode(parseIntWithDefault(map, "rxm", 0));
            int l = getGamemode(parseIntWithDefault(map, "rx", 359));
            arrayList.add(new GetRotationsPredicates2(k, l));
        }

        return arrayList;
    }

    private static BoundingBox method_e82a0116e4afa2f551d4(BlockPos blockPos, int i, int j, int k) {
        int l = i < 0 ? 1 : 0;
        int i1 = j < 0 ? 1 : 0;
        int j1 = k < 0 ? 1 : 0;
        int k1 = blockPos.getX() + (l != 0 ? i : 0);
        int l1 = blockPos.getY() + (i1 != 0 ? j : 0);
        int i2 = blockPos.getZ() + (j1 != 0 ? k : 0);
        int j2 = blockPos.getX() + (l != 0 ? 0 : i) + 1;
        int k2 = blockPos.getY() + (i1 != 0 ? 0 : j) + 1;
        int l2 = blockPos.getZ() + (j1 != 0 ? 0 : k) + 1;
        return new BoundingBox(k1, l1, i2, j2, k2, l2);
    }

    private static Map getArgumentMap(String text) {
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        HashMap hashmap = Maps.newHashMap();
        boolean flag = flag1;
        if (text == null) {
            return hashmap;
        }

        int i = 0;
        int j = -1;
        Matcher matcher = net.minecraft.command.PlayerSelector.intListPattern.matcher(text);

        while (matcher.find()) {
            String textValue = null;
            switch (i++) {
                case 0:
                    textValue = packedTextFragments[35];
                    break;
                case 1:
                    textValue = packedTextFragments[36];
                    break;
                case 2:
                    textValue = packedTextFragments[37];
                    break;
                case 3:
                    textValue = packedTextFragments[38];
            }

            if (textValue != null && matcher.group(1).length() > 0) {
                hashmap.put(textValue, matcher.group(1));
            }

            j = matcher.end();
            if (!flag) {
                break;
            }
        }

        if (j < text.length()) {
            matcher =
                    net.minecraft.command.PlayerSelector.keyValueListPattern.matcher(
                            j == -1 ? text : text.substring(j));

            while (matcher.find()) {
                hashmap.put(matcher.group(1), matcher.group(2));
                if (!flag) {
                    break;
                }
            }
        }

        return hashmap;
    }

    private static int parseIntWithDefault(Map map, String text, int i) {
        return map.containsKey(text)
                ? MathHelper.parseIntWithDefault((String) map.get(text), i)
                : i;
    }

    private static List filterResults(
            Map map, Class oclass, List list, String text, World world, BlockPos blockPos) {
        ArrayList arrayList = Lists.newArrayList();
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        String textValue = parseStringWithDefault(map, "type");
        boolean flag = flag1;
        textValue =
                textValue != null && textValue.startsWith(packedTextFragments[11])
                        ? textValue.substring(1)
                        : textValue;
        int i = !text.equals(packedTextFragments[12]) ? 1 : 0;
        int j = text.equals(packedTextFragments[13]) && textValue != null ? 1 : 0;
        int k = parseIntWithDefault(map, "dx", 0);
        int l = parseIntWithDefault(map, "dy", 0);
        int i1 = parseIntWithDefault(map, "dz", 0);
        int j1 = parseIntWithDefault(map, packedTextFragments[14], -1);
        Predicate predicate = Predicates.and(list);
        Predicate predicate1 = Predicates.and(IEntitySelector.selectAnything, predicate);
        if (blockPos != null) {
            label114:
            {
                int k1 = world.playerEntities.size();
                int l1 = world.loadedEntities.size();
                int i2 = k1 < l1 / 16 ? 1 : 0;
                if (!map.containsKey("dx") && !map.containsKey("dy") && !map.containsKey("dz")) {
                    if (j1 >= 0) {
                        BoundingBox boundingBox =
                                new BoundingBox(
                                        blockPos.getX() - j1,
                                        blockPos.getY() - j1,
                                        blockPos.getZ() - j1,
                                        blockPos.getX() + j1 + 1,
                                        blockPos.getY() + j1 + 1,
                                        blockPos.getZ() + j1 + 1);
                        if (i != 0 && i2 != 0 && j == 0) {
                            arrayList.addAll(world.getPlayers(oclass, predicate1));
                            if (!flag) {
                                break label114;
                            }
                        }

                        arrayList.addAll(
                                world.getEntitiesWithinAABB(oclass, boundingBox, predicate1));
                        if (!flag) {
                            break label114;
                        }
                    }

                    if (text.equals(packedTextFragments[15])) {
                        arrayList.addAll(world.getPlayers(oclass, predicate));
                        if (!flag) {
                            break label114;
                        }
                    }

                    if (!text.equals(packedTextFragments[16])
                            && (!text.equals(packedTextFragments[17]) || j != 0)) {
                        arrayList.addAll(world.getEntities(oclass, predicate1));
                        if (!flag) {
                            break label114;
                        }
                    }

                    arrayList.addAll(world.getPlayers(oclass, predicate1));
                    if (!flag) {
                        break label114;
                    }
                }

                BoundingBox boundingBoxValue = method_e82a0116e4afa2f551d4(blockPos, k, l, i1);
                if (i != 0 && i2 != 0 && j == 0) {
                    PlayerSelectorFilterResultsPredicate playerSelectorFilterResultsPredicate =
                            new PlayerSelectorFilterResultsPredicate(boundingBoxValue);
                    arrayList.addAll(
                            world.getPlayers(
                                    oclass,
                                    Predicates.and(
                                            predicate1, playerSelectorFilterResultsPredicate)));
                    if (!flag) {
                        break label114;
                    }
                }

                arrayList.addAll(world.getEntitiesWithinAABB(oclass, boundingBoxValue, predicate1));
            }

            if (!flag) {
                return arrayList;
            }
        }

        if (text.equals(packedTextFragments[18])) {
            arrayList.addAll(world.getPlayers(oclass, predicate));
            if (!flag) {
                return arrayList;
            }
        }

        if (!text.equals(packedTextFragments[19])
                && (!text.equals(packedTextFragments[20]) || j != 0)) {
            arrayList.addAll(world.getEntities(oclass, predicate1));
            if (!flag) {
                return arrayList;
            }
        }

        arrayList.addAll(world.getPlayers(oclass, predicate1));
        return arrayList;
    }

    public static Entity matchOneEntity(CommandSender commandSender, String text, Class oclass) {
        List list = matchEntities(commandSender, text, oclass);
        return list.size() == 1 ? (Entity) list.get(0) : null;
    }

    private static boolean isEntityTypeValid(CommandSender commandSender, Map map) {
        String text = parseStringWithDefault(map, "type");
        text = text != null && text.startsWith(packedTextFragments[1]) ? text.substring(1) : text;
        if (text != null && !EntityList.isStringValidEntityName(text)) {
            ChatComponentTranslation chatComponentTranslation =
                    new ChatComponentTranslation(
                            "commands.generic.entity.invalidType", new Object[] {text});
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            commandSender.addChatMessage(chatComponentTranslation);
            return false;
        } else {
            return true;
        }
    }

    public static EntityPlayerMP matchOnePlayer(CommandSender commandSender, String text) {
        return (EntityPlayerMP) matchOneEntity(commandSender, text, EntityPlayerMP.class);
    }

    private static BlockPos method_895992fdd44bf4097a3b(Map map, BlockPos blockPos) {
        return new BlockPos(
                parseIntWithDefault(map, packedTextFragments[29], blockPos.getX()),
                parseIntWithDefault(map, packedTextFragments[30], blockPos.getY()),
                parseIntWithDefault(map, packedTextFragments[31], blockPos.getZ()));
    }

    private static List method_10380feb194451935a69(Map map, String text) {
        ArrayList arrayList = Lists.newArrayList();
        boolean flag1 = CommandException.method_b417d7500ec59d69e373();
        String textValue = parseStringWithDefault(map, "type");
        boolean flag = flag1;
        int i = textValue != null && textValue.startsWith(packedTextFragments[2]) ? 1 : 0;
        if (i != 0) {
            textValue = textValue.substring(1);
        }

        int j = !text.equals(packedTextFragments[3]) ? 1 : 0;
        int k = text.equals(packedTextFragments[4]) && textValue != null ? 1 : 0;
        if ((textValue == null || !text.equals(packedTextFragments[5])) && k == 0) {
            if (j == 0) {
                return arrayList;
            }

            arrayList.add(new EntityPredicate());
            if (flag) {
                return arrayList;
            }
        }

        String textValue2 = textValue;
        arrayList.add(
                new PlayerSelectorPredicate2(
                        textValue2, remix.compat.compiler.JvmBooleanSlots.fromInt(i)));
        return arrayList;
    }

    private static List method_7dccf371ed3b6d86490b(Map map) {
        ArrayList arrayList = Lists.newArrayList();
        int i = parseIntWithDefault(map, "lm", -1);
        int j = parseIntWithDefault(map, packedTextFragments[6], -1);
        if (i > -1 || j > -1) {
            arrayList.add(new ScorePredicate(i, j));
        }

        return arrayList;
    }

    public static ChatComponent matchEntitiesToChatComponent(
            CommandSender commandSender, String text) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        List list = matchEntities(commandSender, text, Entity.class);
        boolean flag = flag1;
        if (list.isEmpty()) {
            return null;
        }

        ArrayList arrayList = Lists.newArrayList();

        for (java.util.Iterator<?> __recoveryIterator171_34247 = (list).iterator();
                (__recoveryIterator171_34247).hasNext(); ) {
            Entity entity = (Entity) (__recoveryIterator171_34247).next();
            arrayList.add(entity.getDisplayName());
            if (flag) {
                break;
            }
        }

        return CommandBase.join(arrayList);
    }

    public static boolean matchesMultiplePlayers(String text) {
        Matcher matcher = net.minecraft.command.PlayerSelector.tokenPattern.matcher(text);
        if (!matcher.matches()) {
            return false;
        }

        Map map = getArgumentMap(matcher.group(2));
        String textValue = matcher.group(1);
        int i =
                !packedTextFragments[32].equals(textValue)
                                && !packedTextFragments[33].equals(textValue)
                        ? 1
                        : 0;
        return parseIntWithDefault(map, packedTextFragments[34], i) != 1;
    }

    private static List getScorePredicates(Map map) {
        ArrayList arrayList = Lists.newArrayList();
        Map map1 = method_452b0f2420ca2960db32(map);
        if (map1 != null && map1.size() > 0) {
            arrayList.add(new GetScorePredicates(map1));
        }

        return arrayList;
    }

    public static boolean hasArguments(String text) {
        return net.minecraft.command.PlayerSelector.tokenPattern.matcher(text).matches();
    }

    private static List method_b1319c82471fbe92486c(Map map) {
        ArrayList arrayList = Lists.newArrayList();
        int i =
                parseIntWithDefault(
                        map, packedTextFragments[7], WorldSettingsGameType.NOT_SET.getID());
        if (i != WorldSettingsGameType.NOT_SET.getID()) {
            arrayList.add(new GameModePredicate(i));
        }

        return arrayList;
    }

    private static List method_594922ce6e12dee9e7f3(Map map, BlockPos blockPos) {
        ArrayList arrayList = Lists.newArrayList();
        int i = parseIntWithDefault(map, "rm", -1);
        int j = parseIntWithDefault(map, packedTextFragments[10], -1);
        if (blockPos != null && (i >= 0 || j >= 0)) {
            int k = i * i;
            int l = j * j;
            arrayList.add(new PlayerSelectorPredicate8(blockPos, i, k, j, l));
        }

        return arrayList;
    }

    private static List method_b20a36e716dc22dbea12(
            List list,
            Map map,
            CommandSender commandSender,
            Class oclass,
            String text,
            BlockPos blockPos) {
        int i;
        label44:
        {
            boolean flag = CommandException.method_d87039b07bd6961907f2();
            i =
                    parseIntWithDefault(
                            map,
                            packedTextFragments[21],
                            !text.equals(packedTextFragments[22])
                                            && !text.equals(packedTextFragments[23])
                                    ? 1
                                    : 0);
            if (!text.equals(packedTextFragments[24])
                    && !text.equals(packedTextFragments[25])
                    && !text.equals(packedTextFragments[26])) {
                if (!text.equals(packedTextFragments[27])) {
                    break label44;
                }

                Collections.shuffle(list);
                if (!flag) {
                    break label44;
                }
            }

            if (blockPos != null) {
                Collections.sort(list, new EntityComparator(blockPos));
            }
        }

        Entity commandSenderEntity = commandSender.getCommandSenderEntity();
        if (commandSenderEntity != null
                && oclass.isAssignableFrom(commandSenderEntity.getClass())
                && i == 1
                && list.contains(commandSenderEntity)
                && !packedTextFragments[28].equals(text)) {
            list = Lists.newArrayList(new Entity[] {commandSenderEntity});
        }

        if (i != 0) {
            if (i < 0) {
                Collections.reverse(list);
            }

            list = list.subList(0, Math.min(Math.abs(i), list.size()));
        }

        return list;
    }

    private static List getWorlds(CommandSender commandSender, Map map) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        ArrayList arrayList = Lists.newArrayList();
        boolean flag = flag1;
        if (hasScore(map)) {
            arrayList.add(commandSender.getEntityWorld());
            if (!flag) {
                return arrayList;
            }
        }

        Collections.addAll(arrayList, MinecraftServer.getServer().worldServers);
        return arrayList;
    }

    public static int getGamemode(int i) {
        i %= 360;
        if (i >= 160) {
            i -= 360;
        }

        if (i < 0) {
            i += 360;
        }

        return i;
    }

    private static boolean hasScore(Map map) {
        for (java.util.Iterator<?> __recoveryIterator171_49752 =
                        (net.minecraft.command.PlayerSelector.WORLD_BINDING_ARGS).iterator();
                (__recoveryIterator171_49752).hasNext(); ) {
            String text = (String) (__recoveryIterator171_49752).next();
            if (map.containsKey(text)) {
                return true;
            }
        }

        return false;
    }

    private static String parseStringWithDefault(Map map, String text) {
        return (String) map.get(text);
    }

    private static List method_94fd7883b20466f9a12b(Map map) {
        ArrayList arrayList = Lists.newArrayList();
        String text = parseStringWithDefault(map, "name");
        int i = text != null && text.startsWith(packedTextFragments[9]) ? 1 : 0;
        if (i != 0) {
            text = text.substring(1);
        }

        if (text != null) {
            String textValue = text;
            arrayList.add(
                    new NamePredicate(textValue, remix.compat.compiler.JvmBooleanSlots.fromInt(i)));
        }

        return arrayList;
    }

    public static List matchEntities(CommandSender commandSender, String text, Class oclass) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        Matcher matcher = net.minecraft.command.PlayerSelector.tokenPattern.matcher(text);
        boolean flag = flag1;
        if (matcher.matches()
                && commandSender.canCommandSenderUseCommand(1, packedTextFragments[0])) {
            Map map = getArgumentMap(matcher.group(2));
            if (!isEntityTypeValid(commandSender, map)) {
                return Collections.emptyList();
            }

            String textValue = matcher.group(1);
            BlockPos blockPos = method_895992fdd44bf4097a3b(map, commandSender.getPosition());
            List list = getWorlds(commandSender, map);
            ArrayList arrayList = Lists.newArrayList();

            for (java.util.Iterator<?> __recoveryIterator171_51970 = (list).iterator();
                    (__recoveryIterator171_51970).hasNext(); ) {
                World world = (World) (__recoveryIterator171_51970).next();
                if (world != null) {
                    ArrayList arrayListValue = Lists.newArrayList();
                    arrayListValue.addAll(method_10380feb194451935a69(map, textValue));
                    arrayListValue.addAll(method_7dccf371ed3b6d86490b(map));
                    arrayListValue.addAll(method_b1319c82471fbe92486c(map));
                    arrayListValue.addAll(method_ab5d414f043184d66eb9(map));
                    arrayListValue.addAll(getScorePredicates(map));
                    arrayListValue.addAll(method_94fd7883b20466f9a12b(map));
                    arrayListValue.addAll(method_594922ce6e12dee9e7f3(map, blockPos));
                    arrayListValue.addAll(getRotationsPredicates(map));
                    arrayList.addAll(
                            filterResults(map, oclass, arrayListValue, textValue, world, blockPos));
                }

                if (flag) {
                    break;
                }
            }

            return method_b20a36e716dc22dbea12(
                    arrayList, map, commandSender, oclass, textValue, blockPos);
        } else {
            return Collections.emptyList();
        }
    }

    private static List method_ab5d414f043184d66eb9(Map map) {
        ArrayList arrayList = Lists.newArrayList();
        String text = parseStringWithDefault(map, "team");
        int i = text != null && text.startsWith(packedTextFragments[8]) ? 1 : 0;
        if (i != 0) {
            text = text.substring(1);
        }

        if (text != null) {
            String textValue = text;
            arrayList.add(
                    new TeamPredicate(textValue, remix.compat.compiler.JvmBooleanSlots.fromInt(i)));
        }

        return arrayList;
    }

    static {
        net.minecraft.command.PlayerSelector.packedTextFragments = new String[45];
        net.minecraft.command.PlayerSelector.packedTextFragments[0] = new String("@");
        net.minecraft.command.PlayerSelector.packedTextFragments[1] = new String("!");
        net.minecraft.command.PlayerSelector.packedTextFragments[2] = new String("!");
        net.minecraft.command.PlayerSelector.packedTextFragments[3] = new String("e");
        net.minecraft.command.PlayerSelector.packedTextFragments[4] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[5] = new String("e");
        net.minecraft.command.PlayerSelector.packedTextFragments[6] = new String("l");
        net.minecraft.command.PlayerSelector.packedTextFragments[7] = new String("m");
        net.minecraft.command.PlayerSelector.packedTextFragments[8] = new String("!");
        net.minecraft.command.PlayerSelector.packedTextFragments[9] = new String("!");
        net.minecraft.command.PlayerSelector.packedTextFragments[10] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[11] = new String("!");
        net.minecraft.command.PlayerSelector.packedTextFragments[12] = new String("e");
        net.minecraft.command.PlayerSelector.packedTextFragments[13] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[14] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[15] = new String("a");
        net.minecraft.command.PlayerSelector.packedTextFragments[16] = new String("p");
        net.minecraft.command.PlayerSelector.packedTextFragments[17] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[18] = new String("a");
        net.minecraft.command.PlayerSelector.packedTextFragments[19] = new String("p");
        net.minecraft.command.PlayerSelector.packedTextFragments[20] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[21] = new String("c");
        net.minecraft.command.PlayerSelector.packedTextFragments[22] = new String("a");
        net.minecraft.command.PlayerSelector.packedTextFragments[23] = new String("e");
        net.minecraft.command.PlayerSelector.packedTextFragments[24] = new String("p");
        net.minecraft.command.PlayerSelector.packedTextFragments[25] = new String("a");
        net.minecraft.command.PlayerSelector.packedTextFragments[26] = new String("e");
        net.minecraft.command.PlayerSelector.packedTextFragments[27] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[28] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[29] = new String("x");
        net.minecraft.command.PlayerSelector.packedTextFragments[30] = new String("y");
        net.minecraft.command.PlayerSelector.packedTextFragments[31] = new String("z");
        net.minecraft.command.PlayerSelector.packedTextFragments[32] = new String("a");
        net.minecraft.command.PlayerSelector.packedTextFragments[33] = new String("e");
        net.minecraft.command.PlayerSelector.packedTextFragments[34] = new String("c");
        net.minecraft.command.PlayerSelector.packedTextFragments[35] = new String("x");
        net.minecraft.command.PlayerSelector.packedTextFragments[36] = new String("y");
        net.minecraft.command.PlayerSelector.packedTextFragments[37] = new String("z");
        net.minecraft.command.PlayerSelector.packedTextFragments[38] = new String("r");
        net.minecraft.command.PlayerSelector.packedTextFragments[41] = new String("x");
        net.minecraft.command.PlayerSelector.packedTextFragments[42] = new String("y");
        net.minecraft.command.PlayerSelector.packedTextFragments[43] = new String("z");
        net.minecraft.command.PlayerSelector.packedTextFragments[44] = new String("r");
        net.minecraft.command.PlayerSelector.tokenPattern =
                Pattern.compile("^@([pare])(?:\\[([\\w=,!-]*)\\])?$");
        net.minecraft.command.PlayerSelector.intListPattern =
                Pattern.compile("\\G([-!]?[\\w-]*)(?:$|,)");
        net.minecraft.command.PlayerSelector.keyValueListPattern =
                Pattern.compile("\\G(\\w+)=([-!]?[\\w-]*)(?:$|,)");
        net.minecraft.command.PlayerSelector.WORLD_BINDING_ARGS =
                Sets.newHashSet(
                        new String[] {
                            packedTextFragments[41],
                            packedTextFragments[42],
                            packedTextFragments[43],
                            "dx",
                            "dy",
                            "dz",
                            "rm",
                            packedTextFragments[44]
                        });
    }

    public static Map method_452b0f2420ca2960db32(Map map) {
        boolean flag1 = CommandException.method_d87039b07bd6961907f2();
        HashMap hashmap = Maps.newHashMap();
        boolean flag = flag1;

        for (java.util.Iterator<?> __recoveryIterator171_57819 = (map.keySet()).iterator();
                (__recoveryIterator171_57819).hasNext(); ) {
            String text = (String) (__recoveryIterator171_57819).next();
            if (text.startsWith("score_") && text.length() > "score_".length()) {
                hashmap.put(
                        text.substring("score_".length()),
                        MathHelper.parseIntWithDefault((String) map.get(text), 1));
            }

            if (flag) {
                break;
            }
        }

        return hashmap;
    }

    // Helper used only by this class.
    public static final class EntityComparator implements java.util.Comparator {
        net.minecraft.util.BlockPos vh;

        public int method_e431402c0b0c952a49db(
                net.minecraft.entity.Entity entity, net.minecraft.entity.Entity entityValue) {
            return com.google.common.collect.ComparisonChain.start()
                    .compare(entity.getDistanceSq(this.vh), entityValue.getDistanceSq(this.vh))
                    .result();
        }

        public EntityComparator(net.minecraft.util.BlockPos blockPos) {
            this.vh = blockPos;
        }

        @java.lang.Override
        public int compare(java.lang.Object object, java.lang.Object object1) {
            return this.method_e431402c0b0c952a49db(
                    (net.minecraft.entity.Entity) object, (net.minecraft.entity.Entity) object1);
        }
    }

    // Helper used only by this class.
    public static final class GameModePredicate implements com.google.common.base.Predicate {
        int a;

        public GameModePredicate(int i) {
            this.a = i;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            if (!(entity instanceof net.minecraft.entity.player.EntityPlayerMP)) {
                return false;
            }

            net.minecraft.entity.player.EntityPlayerMP entityPlayerMP =
                    (net.minecraft.entity.player.EntityPlayerMP) entity;
            return entityPlayerMP.theItemInWorldManager.getGameType().getID() == this.a;
        }
    }

    // Helper used only by this class.
    static final class GetRotationsPredicates implements com.google.common.base.Predicate {
        int val$i;
        int val$j;

        GetRotationsPredicates(int i, int j) {
            this.val$i = i;
            this.val$j = j;
        }

        public boolean apply(net.minecraft.entity.Entity entity) {
            int i =
                    net.minecraft.command.PlayerSelector.getGamemode(
                            (int) java.lang.Math.floor(entity.rotationYaw));
            return this.val$i > this.val$j
                    ? i >= this.val$i || i <= this.val$j
                    : i >= this.val$i && i <= this.val$j;
        }

        public boolean apply(java.lang.Object object) {
            return this.apply((net.minecraft.entity.Entity) object);
        }
    }

    // Helper used only by this class.
    static final class GetRotationsPredicates2 implements com.google.common.base.Predicate {
        int val$l;
        int val$m;

        public boolean apply(java.lang.Object object) {
            return this.apply((net.minecraft.entity.Entity) object);
        }

        GetRotationsPredicates2(int i, int j) {
            this.val$l = i;
            this.val$m = j;
        }

        public boolean apply(net.minecraft.entity.Entity entity) {
            int i =
                    net.minecraft.command.PlayerSelector.getGamemode(
                            (int) java.lang.Math.floor(entity.rotationPitch));
            return this.val$l > this.val$m
                    ? i >= this.val$l || i <= this.val$m
                    : i >= this.val$l && i <= this.val$m;
        }
    }

    // Helper used only by this class.
    static final class GetScorePredicates implements com.google.common.base.Predicate {
        private static java.lang.String b;
        java.util.Map dI;

        // region String decoding support

        // endregion

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        // region String decoding support

        // endregion

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            net.minecraft.scoreboard.Scoreboard scoreboard =
                    net.minecraft.server.MinecraftServer.getServer()
                            .worldServerForDimension(0)
                            .getScoreboard();

            for (java.util.Iterator<?> __recoveryIterator171_2202 = (this.dI.entrySet()).iterator();
                    (__recoveryIterator171_2202).hasNext(); ) {
                java.util.Map.Entry entry =
                        (java.util.Map.Entry) (__recoveryIterator171_2202).next();
                java.lang.String key = (java.lang.String) entry.getKey();
                byte b0 = 0;
                if (key.endsWith(b) && key.length() > 4) {
                    b0 = 1;
                    key = key.substring(0, key.length() - 4);
                }

                net.minecraft.scoreboard.ScoreObjective objective = scoreboard.getObjective(key);
                if (objective == null) {
                    return false;
                }

                java.lang.String text =
                        entity instanceof net.minecraft.entity.player.EntityPlayerMP
                                ? entity.getName()
                                : entity.getUniqueId().toString();
                if (!scoreboard.entityHasObjective(text, objective)) {
                    return false;
                }

                net.minecraft.scoreboard.Score valueFromObjective =
                        scoreboard.getValueFromObjective(text, objective);
                int scorePoints = valueFromObjective.getScorePoints();
                if (scorePoints < (java.lang.Integer) entry.getValue() && b0 != 0) {
                    return false;
                }

                if (scorePoints > (java.lang.Integer) entry.getValue() && b0 == 0) {
                    return false;
                }
            }

            return true;
        }

        GetScorePredicates(java.util.Map map) {
            this.dI = map;
        }

        static {
            b = "_min";
        }
    }

    // Helper used only by this class.
    public static final class NamePredicate implements com.google.common.base.Predicate {
        java.lang.String a;
        boolean b;

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public NamePredicate(java.lang.String text, boolean flag) {
            this.a = text;
            this.b = flag;
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity.getName().equals(this.a) != this.b;
        }
    }

    // Helper used only by this class.
    public static final class EntityPredicate implements com.google.common.base.Predicate {
        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            return entity instanceof net.minecraft.entity.player.PlayerEntity;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }
    }

    // Helper used only by this class.
    public static final class PlayerSelectorPredicate2 implements com.google.common.base.Predicate {
        java.lang.String val$string3;
        boolean val$bl;

        public boolean apply(java.lang.Object object) {
            return this.apply((net.minecraft.entity.Entity) object);
        }

        public PlayerSelectorPredicate2(java.lang.String text, boolean flag) {
            this.val$string3 = text;
            this.val$bl = flag;
        }

        public boolean apply(net.minecraft.entity.Entity entity) {
            return net.minecraft.entity.EntityList.isStringEntityName(entity, this.val$string3)
                    != this.val$bl;
        }
    }

    // Helper used only by this class.
    public static final class PlayerSelectorPredicate8 implements com.google.common.base.Predicate {
        int dD;
        int cz;
        int ef;
        net.minecraft.util.BlockPos vh;
        int c;

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            int i = (int) entity.getDistanceSqToCenter(this.vh);
            return (this.cz < 0 || i >= this.c) && (this.dD < 0 || i <= this.ef);
        }

        public PlayerSelectorPredicate8(
                net.minecraft.util.BlockPos blockPos, int i, int j, int k, int l) {
            this.vh = blockPos;
            this.cz = i;
            this.c = j;
            this.dD = k;
            this.ef = l;
        }

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }
    }

    // Helper used only by this class.
    public static final class ScorePredicate implements com.google.common.base.Predicate {
        int a;
        int cz;

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            if (!(entity instanceof net.minecraft.entity.player.EntityPlayerMP)) {
                return false;
            }

            net.minecraft.entity.player.EntityPlayerMP entityPlayerMP =
                    (net.minecraft.entity.player.EntityPlayerMP) entity;
            return (this.a <= -1 || entityPlayerMP.experienceLevel >= this.a)
                    && (this.cz <= -1 || entityPlayerMP.experienceLevel <= this.cz);
        }

        public ScorePredicate(int i, int j) {
            this.a = i;
            this.cz = j;
        }
    }

    // Helper used only by this class.
    public static final class TeamPredicate implements com.google.common.base.Predicate {
        java.lang.String a;
        boolean b;
        private static java.lang.String[] packedTextFragments;

        public TeamPredicate(java.lang.String text, boolean flag) {
            this.a = text;
            this.b = flag;
        }

        public boolean method_aae6a50880e58b2e8640(net.minecraft.entity.Entity entity) {
            if (!(entity instanceof net.minecraft.entity.LivingEntity)) {
                return false;
            }

            net.minecraft.entity.LivingEntity livingEntity =
                    (net.minecraft.entity.LivingEntity) entity;
            net.minecraft.scoreboard.Team team = livingEntity.getTeam();
            java.lang.String text =
                    team == null ? packedTextFragments[0] : team.getRegisteredName();
            return text.equals(this.a) != this.b;
        }

        // region String decoding support
        private static void unpackLiteralFragments() {
            net.minecraft.command.PlayerSelector.TeamPredicate.packedTextFragments[0] =
                    new java.lang.String("");
        }

        // endregion

        static {
            packedTextFragments = new java.lang.String[1];
            unpackLiteralFragments();
        }

        // region String decoding support

        // endregion

        public boolean apply(java.lang.Object object) {
            return this.method_aae6a50880e58b2e8640((net.minecraft.entity.Entity) object);
        }
    }
}
