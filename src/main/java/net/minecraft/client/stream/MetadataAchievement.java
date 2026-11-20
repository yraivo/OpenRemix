package net.minecraft.client.stream;

import net.minecraft.stats.Achievement;

public class MetadataAchievement extends Metadata {


    public MetadataAchievement(Achievement achievement) {
        super("achievement");
        this.O("achievement_id", achievement.statId);
        this.O("achievement_name", achievement.getStatName().getUnformattedText());
        this.O("achievement_description", achievement.getDescription());
        this.setDescription(
                "Achievement '" + achievement.getStatName().getUnformattedText() + "' obtained!");
    }
}
