package remix.server.hypixel;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.ChatComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MegaWallsScoreTracker {
    private String[] jt;
    private static final int mu = 4;
    private static String b;
    private ArrayList<String> arrayList;
    private static int jE;
    private static String[] jr;
    private static String e;
    private static String[] h;
    private static String f;

    private static String a;
    public static final int rW = 3;
    private static String[] kw;
    private static String c;
    private HashMap<String, Integer>[] vd;
    private static String g;
    private static String d;
    public static final int tB = 0;
    public static final int mE = 2;
    private boolean[] ve;
    public static final int mF = 1;
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    private static List<String> fN() {
        int j = method_beea3e16fe3ec0995eb9();
        ArrayList arrayList = new ArrayList();
        int i = j;

        try {
            Scoreboard scoreboard = MinecraftClient.getMinecraft().world.getScoreboard();
            ScoreObjective objectiveInDisplaySlot = scoreboard.getObjectiveInDisplaySlot(1);

            for (java.util.Iterator<?> __recoveryIterator171_3206 =
                            (scoreboard.getSortedScores(objectiveInDisplaySlot))
                                    .iterator();
                    (__recoveryIterator171_3206).hasNext(); ) {
                Score score = (Score) (__recoveryIterator171_3206).next();
                String text = score.getPlayerName();
                ScorePlayerTeam playersTeam = scoreboard.getPlayersTeam(text);
                arrayList.add(ScorePlayerTeam.formatPlayerName(playersTeam, text));
                if (i == 0) {
                    break;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return arrayList;
    }

    private boolean b(String text) {
        int i = this.getTeamIndex(text);
        return this.isValidTeamIndex(i) && this.ve[i];
    }

    private int getTeamIndex(String text) {
        int k = method_beea3e16fe3ec0995eb9();
        int j = 0;
        int i = k;

        while (j < 4) {
            if (this.jt[j].equalsIgnoreCase(text)) {
                return j;
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        return -1;
    }

    public static int aW() {
        int i = method_beea3e16fe3ec0995eb9();
        return i == 0 ? 95 : 0;
    }

    public MegaWallsScoreTracker() {
        int k = method_beea3e16fe3ec0995eb9();
        this.ve = new boolean[4];
        int i = k;
        this.jt = new String[4];
        this.vd = new HashMap[4];
        this.arrayList = new ArrayList<>();
        int j = 0;

        while (j < 4) {
            this.jt[j] = jr[j];
            this.vd[j] = new HashMap<>();
            j++;
            if (i == 0) {
                break;
            }
        }

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            method_facbdb7a0205fcce736a(++i);
        }
    }

    // region String decoding support

    // endregion

    static {
        remix.server.hypixel.MegaWallsScoreTracker.packedTextFragments = new String[8];
        remix.server.hypixel.MegaWallsScoreTracker.packedTextFragments[0] = new String("\u00a7");
        remix.server.hypixel.MegaWallsScoreTracker.packedTextFragments[1] = new String("\u00a7");
        remix.server.hypixel.MegaWallsScoreTracker.packedTextFragments[2] = new String("\u00a7");
        remix.server.hypixel.MegaWallsScoreTracker.packedTextFragments[4] = new String("c");
        remix.server.hypixel.MegaWallsScoreTracker.packedTextFragments[5] = new String("a");
        remix.server.hypixel.MegaWallsScoreTracker.packedTextFragments[6] = new String("e");
        remix.server.hypixel.MegaWallsScoreTracker.packedTextFragments[7] = new String("9");
        method_facbdb7a0205fcce736a(78);
        a = "The Red Wither has died!";
        f = "All withers are dead! 10 seconds till deathmatch!";
        c = "The Yellow Wither has died!";
        e = "Your wither has died. You can no longer respawn!";
        b = "The Green Wither has died!";
        d = "The Blue Wither has died!";
        g = "Prepare your defenses!";
        h =
                new String[] {
                    "(\\w+) was shot and killed by (\\w+).*",
                    "(\\w+) was snowballed to death by (\\w+).*",
                    "(\\w+) was killed by (\\w+).*",
                    "(\\w+) was killed with a potion by (\\w+).*",
                    "(\\w+) was killed with an explosion by (\\w+).*",
                    "(\\w+) was killed with magic by (\\w+).*",
                    "(\\w+) was filled full of lead by (\\w+).*",
                    "(\\w+) was iced by (\\w+).*",
                    "(\\w+) met their end by (\\w+).*",
                    "(\\w+) lost a drinking contest with (\\w+).*",
                    "(\\w+) was killed with dynamite by (\\w+).*",
                    "(\\w+) lost the draw to (\\w+).*",
                    "(\\w+) was struck down by (\\w+).*",
                    "(\\w+) was turned to dust by (\\w+).*",
                    "(\\w+) was turned to ash by (\\w+).*",
                    "(\\w+) was melted by (\\w+).*",
                    "(\\w+) was incinerated by (\\w+).*",
                    "(\\w+) was vaporized by (\\w+).*",
                    "(\\w+) was struck with Cupid's arrow by (\\w+).*",
                    "(\\w+) was given the cold shoulder by (\\w+).*",
                    "(\\w+) was hugged too hard by (\\w+).*",
                    "(\\w+) drank a love potion from (\\w+).*",
                    "(\\w+) was hit by a love bomb from (\\w+).*",
                    "(\\w+) was no match for (\\w+).*",
                    "(\\w+) was smote from afar by (\\w+).*",
                    "(\\w+) was justly ended by (\\w+).*",
                    "(\\w+) was purified by (\\w+).*",
                    "(\\w+) was killed with holy water by (\\w+).*",
                    "(\\w+) was dealt vengeful justice by (\\w+).*",
                    "(\\w+) was returned to dust by (\\w+).*",
                    "(\\w+) be shot and killed by (\\w+).*",
                    "(\\w+) be snowballed to death by (\\w+).*",
                    "(\\w+) be sent to Davy Jones' locker by (\\w+).*",
                    "(\\w+) be killed with rum by (\\w+).*",
                    "(\\w+) be shot with cannon by (\\w+).*",
                    "(\\w+) be killed with magic by (\\w+).*",
                    "(\\w+) was glazed in BBQ sauce by (\\w+).*",
                    "(\\w+) was sprinked in chilli poweder by (\\w+).*",
                    "(\\w+) was sliced up by (\\w+).*",
                    "(\\w+) was overcooked by (\\w+).*",
                    "(\\w+) was deep fried by (\\w+).*",
                    "(\\w+) was boiled by (\\w+).*",
                    "(\\w+) was injected with malware by (\\w+).*",
                    "(\\w+) was DDoS'd by (\\w+).*",
                    "(\\w+) was deleted by (\\w+).*",
                    "(\\w+) was purged by an antivirus owned by (\\w+).*",
                    "(\\w+) was fragmented by (\\w+).*",
                    "(\\w+) was squeaked from a distance by (\\w+).*",
                    "(\\w+) was hit by frozen cheese from (\\w+).*",
                    "(\\w+) was chewed up by (\\w+).*",
                    "(\\w+) was chemically cheesed by (\\w+).*",
                    "(\\w+) was turned into cheese wiz by (\\w+).*",
                    "(\\w+) was magically squeaked by (\\w+).*",
                    "(\\w+) was corrupted by (\\w+).*"
                };
        kw = new String[] {"[R]", "[G]", "[Y]", "[B]"};
        jr =
                new String[] {
                    packedTextFragments[4],
                    packedTextFragments[5],
                    packedTextFragments[6],
                    packedTextFragments[7]
                };
    }

    public void onChatPacket(S02PacketChat s02PacketChat) {
        int k = aW();
        ChatComponent chatComponent = s02PacketChat.getChatComponent();
        String text = chatComponent.getUnformattedTextForChat();
        String textValue = chatComponent.getFormattedText();
        int i = k;
        if (text.equals("Prepare your defenses!")) {
            this.updateTeamColorCodes();
        }

        String textValue2 = text;
        String textValue3 = textValue2;
        byte b0 = -1;
        switch (textValue3.hashCode()) {
            case -401475544:
                if (!textValue3.equals("The Red Wither has died!")) {
                    break;
                }

                b0 = 0;
                if (i == 0) {
                    break;
                }

                Entity.setLegacyGlobalFlowMarker(new String[5]);
            case 959474742:
                if (!textValue3.equals("The Green Wither has died!")) {
                    break;
                }

                b0 = 1;
                if (i == 0) {
                    break;
                }
            case -132194521:
                if (!textValue3.equals("The Yellow Wither has died!")) {
                    break;
                }

                b0 = 2;
                if (i == 0) {
                    break;
                }
            case -1453151167:
                if (!textValue3.equals("The Blue Wither has died!")) {
                    break;
                }

                b0 = 3;
                if (i == 0) {
                    break;
                }
            case 1318254302:
                if (!textValue3.equals("Your wither has died. You can no longer respawn!")) {
                    break;
                }

                b0 = 4;
                if (i == 0) {
                    break;
                }
            case 1765345817:
                if (textValue3.equals("All withers are dead! 10 seconds till deathmatch!")) {
                    b0 = 5;
                }
        }

        switch (b0) {
            case 0:
                this.ve[0] = true;
            case 1:
                this.ve[1] = true;
            case 2:
                this.ve[2] = true;
            case 3:
                this.ve[3] = true;
            case 4:
                String textValue4 =
                        MinecraftClient.getMinecraft()
                                .world
                                .getScoreboard()
                                .getObjectiveInDisplaySlot(1)
                                .getDisplayName()
                                .split(packedTextFragments[0])[1]
                                .substring(0, 1);
                this.setWitherDead(textValue4);
            case 5:
                int j = 0;

                while (j < 4) {
                    this.ve[j] = true;
                    j++;
                    if (i != 0) {
                        break;
                    }
                }
            default:
                for (String textValue5 : h) {
                    Matcher matcher = Pattern.compile(textValue5).matcher(text);
                    if (matcher.matches()) {
                        String textValue6 = matcher.group(1);
                        String textValue7 = matcher.group(2);
                        String[] astring = textValue.split(packedTextFragments[1]);
                        String textValue8 = astring[2].substring(0, 1);
                        String textValue9 = astring[8].substring(0, 1);
                        this.O(textValue6, textValue8);
                        if (!this.b(textValue8)) {
                            break;
                        }

                        this.P(textValue7, textValue9);
                        if (i == 0) {
                            break;
                        }
                    }

                    if (i != 0) {
                        break;
                    }
                }
        }
    }

    private void O(String text, String textValue) {
        int i = this.getTeamIndex(textValue);
        if (this.isValidTeamIndex(i)) {
            if (this.ve[i]) {
                this.vd[i].remove(text);
                this.arrayList.add(text);
            }
        }
    }

    private boolean isValidTeamIndex(int i) {
        return i >= 0 && i < 4;
    }

    private void setWitherDead(String text) {
        int i = this.getTeamIndex(text);
        if (this.isValidTeamIndex(i)) {
            this.ve[i] = true;
        }
    }

    private void P(String text, String textValue) {
        int k = method_beea3e16fe3ec0995eb9();
        int j = this.getTeamIndex(textValue);
        int i = k;
        if (this.isValidTeamIndex(j)) {
            if (!this.arrayList.contains(text)) {
                {
                    if (this.vd[j].containsKey(text)) {
                        this.vd[j].put(text, this.vd[j].get(text) + 1);
                        if (!(i != 0)) {

                            this.vd[j].put(text, 1);
                        }
                    } else {

                        this.vd[j].put(text, 1);
                    }
                }

                this.sortTeamKills(j);
            }
        }
    }

    public static int method_beea3e16fe3ec0995eb9() {
        return jE;
    }

    public HashMap<String, Integer> getTeamKillsMap(int i) {
        return !this.isValidTeamIndex(i) ? new HashMap<>() : this.vd[i];
    }

    public static void method_facbdb7a0205fcce736a(int i) {
        jE = i;
    }

    public int getTeamTotalKills(int i) {
        int j = method_beea3e16fe3ec0995eb9();
        if (!this.isValidTeamIndex(i)) {
            return 0;
        }

        int k = 0;

        for (int l : this.vd[i].values()) {
            k += l;
            if (j == 0) {
                break;
            }
        }

        return k;
    }

    private void sortTeamKills(int i) {
        int j = method_beea3e16fe3ec0995eb9();
        if (this.isValidTeamIndex(i)) {
            ArrayList arrayList = new ArrayList();

            for (java.util.Iterator<?> __recoveryIterator171_20495 =
                            (this.vd[i].entrySet()).iterator();
                    (__recoveryIterator171_20495).hasNext(); ) {
                Entry entry = (Entry) (__recoveryIterator171_20495).next();
                arrayList.add(entry);
                if (j == 0) {
                    break;
                }
            }

            arrayList.sort(Entry.comparingByValue().reversed());
            LinkedHashMap linkedhashmap = new LinkedHashMap();

            for (java.util.Iterator<?> __recoveryIterator171_20789 = (arrayList).iterator();
                    (__recoveryIterator171_20789).hasNext(); ) {
                Entry entry1 = (Entry) (__recoveryIterator171_20789).next();
                linkedhashmap.putIfAbsent(entry1.getKey(), entry1.getValue());
                if (j == 0) {
                    break;
                }
            }

            this.vd[i] = linkedhashmap;
        }
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    private void updateTeamColorCodes() {
        int k = method_beea3e16fe3ec0995eb9();
        Iterator iterator = fN().iterator();
        int i = k;

        while (iterator.hasNext()) {
            String text = (String) iterator.next();
            String[] astring = text.split(packedTextFragments[2]);
            int j = 0;

            while (true) {
                if (j < 4) {
                    if (text.contains(kw[j])) {
                        this.jt[j] = astring[1].substring(0, 1);
                    }

                    j++;
                    if (i != 0) {
                        continue;
                    }
                }

                if (i == 0) {
                    return;
                }
                break;
            }
        }
    }
}
