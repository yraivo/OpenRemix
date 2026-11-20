package net.minecraft.util;

import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

public class ChatComponentScore extends ChatComponentStyle {
    private String name;

    private String objective;
    private String value;
    private static String[] packedTextFragments;

    public String getUnformattedTextForChat() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        MinecraftServer server = MinecraftServer.getServer();
        String textValue = text;
        if (server != null && server.isAnvilFileSet() && StringUtils.isNullOrEmpty(this.value)) {
            Scoreboard scoreboard = server.worldServerForDimension(0).getScoreboard();
            ScoreObjective objective = scoreboard.getObjective(this.objective);
            if (scoreboard.entityHasObjective(this.name, objective)) {
                Score valueFromObjective = scoreboard.getValueFromObjective(this.name, objective);
                this.setValue(String.format("%d", valueFromObjective.getScorePoints()));
                if (textValue != null) {
                    return this.value;
                }
            }

            this.value = packedTextFragments[1];
        }

        return this.value;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ChatComponentScore)) {
            return false;
        }

        ChatComponentScore chatComponentScore = (ChatComponentScore) object;
        return this.name.equals(chatComponentScore.name)
                && this.objective.equals(chatComponentScore.objective)
                && super.equals(object);
    }

    public String getObjective() {
        return this.objective;
    }

    static {
        net.minecraft.util.ChatComponentScore.packedTextFragments = new String[4];
        net.minecraft.util.ChatComponentScore.packedTextFragments[0] = new String("");
        net.minecraft.util.ChatComponentScore.packedTextFragments[1] = new String("");
    }

    public String getName() {
        return this.name;
    }

    public ChatComponentScore(String text, String textValue) {
        this.value = packedTextFragments[0];
        this.name = text;
        this.objective = textValue;
    }

    public ChatComponent createCopy() {
        return this.TG();
    }

    public void setValue(String text) {
        this.value = text;
    }

    public String toString() {
        return "ScoreComponent{name='"
                + this.name
                + '\''
                + "objective='"
                + this.objective
                + '\''
                + ", siblings="
                + this.siblings
                + ", style="
                + this.getChatStyle()
                + '}';
    }

    public ChatComponentScore TG() {
        String text = Vec3i.getLegacyCoordinateFlowMarker();
        ChatComponentScore chatComponentScore = new ChatComponentScore(this.name, this.objective);
        chatComponentScore.setValue(this.value);
        String textValue = text;
        chatComponentScore.setChatStyle(this.getChatStyle().createShallowCopy());

        for (java.util.Iterator<?> __recoveryIterator171_29847 = (this.getSiblings()).iterator();
                (__recoveryIterator171_29847).hasNext(); ) {
            ChatComponent chatComponent = (ChatComponent) (__recoveryIterator171_29847).next();
            chatComponentScore.appendSibling(chatComponent.createCopy());
            if (textValue == null) {
                break;
            }
        }

        return chatComponentScore;
    }
}
