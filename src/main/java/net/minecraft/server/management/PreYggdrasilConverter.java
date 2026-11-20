package net.minecraft.server.management;

import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.ProfileLookupCallback;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StringUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class PreYggdrasilConverter {
    public static File OLD_OPS_FILE;
    private static Logger LOGGER;
    public static File OLD_WHITELIST_FILE;
    public static File OLD_IPBAN_FILE;
    public static File OLD_PLAYERBAN_FILE;
    private static String[] packedTextFragments;

    public static Logger vb() {
        return net.minecraft.server.management.PreYggdrasilConverter.LOGGER;
    }

    // region String decoding support

    // endregion

    static {
        net.minecraft.server.management.PreYggdrasilConverter.packedTextFragments = new String[3];
        net.minecraft.server.management.PreYggdrasilConverter.packedTextFragments[0] =
                new String("");
        net.minecraft.server.management.PreYggdrasilConverter.packedTextFragments[1] =
                new String("L\u0013dH[*f\u000eA\u0002y\bJ6?J\u0013dH[*f");
        net.minecraft.server.management.PreYggdrasilConverter.packedTextFragments[2] =
                new String("~!T8`UT`:Ibq\u0000L\u0012k(S\"`\u001c\u0015y%\\5`\nK'=E8");
        net.minecraft.server.management.PreYggdrasilConverter.LOGGER = LogManager.getLogger();
        net.minecraft.server.management.PreYggdrasilConverter.OLD_IPBAN_FILE =
                new File("banned-ips.txt");
        net.minecraft.server.management.PreYggdrasilConverter.OLD_PLAYERBAN_FILE =
                new File("banned-players.txt");
        net.minecraft.server.management.PreYggdrasilConverter.OLD_OPS_FILE = new File("ops.txt");
        net.minecraft.server.management.PreYggdrasilConverter.OLD_WHITELIST_FILE =
                new File("white-list.txt");
    }

    // region String decoding support

    // endregion

    public static String getStringUUIDFromName(String text) {
        if (!StringUtils.isNullOrEmpty(text) && text.length() <= 16) {
            MinecraftServer server = MinecraftServer.getServer();
            GameProfile gameprofile =
                    server.getPlayerProfileCache().getGameProfileForUsername(text);
            if (gameprofile != null && gameprofile.getId() != null) {
                return gameprofile.getId().toString();
            } else if (!server.isSinglePlayer() && server.isServerInOnlineMode()) {
                ArrayList arrayList = Lists.newArrayList();
                PreYggdrasilConverterGetStringUUIDFromNameProfileLookupCallback
                        preYggdrasilConverterGetStringUUIDFromNameProfileLookupCallback =
                                new PreYggdrasilConverterGetStringUUIDFromNameProfileLookupCallback(
                                        server, arrayList);
                lookupNames(
                        server,
                        Lists.newArrayList(new String[] {text}),
                        preYggdrasilConverterGetStringUUIDFromNameProfileLookupCallback);
                return arrayList.size() > 0 && ((GameProfile) arrayList.get(0)).getId() != null
                        ? ((GameProfile) arrayList.get(0)).getId().toString()
                        : packedTextFragments[0];
            } else {
                return PlayerEntity.getUUID(new GameProfile((UUID) null, text)).toString();
            }
        } else {
            return text;
        }
    }

    // region String decoding support

    // endregion

    private static void lookupNames(
            MinecraftServer minecraftServer,
            Collection collection,
            ProfileLookupCallback profilelookupcallback) {
        ServerConfigurationManager.kr();

        String[] astring =
                (String[])
                        Iterators.toArray(
                                Iterators.filter(collection.iterator(), new LookupNamesPredicate()),
                                String.class);

        if (minecraftServer.isServerInOnlineMode()) {
            minecraftServer
                    .getGameProfileRepository()
                    .findProfilesByNames(astring, Agent.MINECRAFT, profilelookupcallback);

            return;
        }

        String[] astring1 = astring;
        int length = astring.length;
        int j = 0;

        while (j < length) {
            String text = astring1[j];
            UUID uuid = PlayerEntity.getUUID(new GameProfile((UUID) null, text));
            GameProfile gameprofile = new GameProfile(uuid, text);
            profilelookupcallback.onProfileLookupSucceeded(gameprofile);
            j++;
        }
    }

    // Helper used only by this class.
    public static final class LookupNamesPredicate implements com.google.common.base.Predicate {
        public boolean a(java.lang.String text) {
            return !net.minecraft.util.StringUtils.isNullOrEmpty(text);
        }

        public boolean apply(java.lang.Object p171_0) {
            return this.a((java.lang.String) p171_0);
        }
    }
}
