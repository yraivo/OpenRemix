package net.minecraft.scoreboard;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.MathHelper;

import java.util.List;

public class ScoreHealthCriteria extends ScoreDummyCriteria {
    public int setScore(List list) {
        String text = Team.cI();
        float f = 0.0F;
        String textValue = text;

        for (java.util.Iterator<?> __recoveryIterator171_325 = (list).iterator();
                (__recoveryIterator171_325).hasNext(); ) {
            PlayerEntity playerEntity = (PlayerEntity) (__recoveryIterator171_325).next();
            f += playerEntity.getHealth() + playerEntity.getAbsorptionAmount();
            if (textValue == null) {
                break;
            }
        }

        if (list.size() > 0) {
            f /= list.size();
        }

        return MathHelper.ceil(f);
    }

    public ScoreHealthCriteria(String text) {
        super(text);
    }

    public boolean isReadOnly() {
        return true;
    }

    public IScoreObjectiveCriteriaEnumRenderType getRenderType() {
        return IScoreObjectiveCriteriaEnumRenderType.HEARTS;
    }
}
