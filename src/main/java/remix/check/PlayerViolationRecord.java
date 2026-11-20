package remix.check;

import net.minecraft.entity.Entity;

public final class PlayerViolationRecord {
    public int violationLevel;
    public AbstractHackerCheck abstractHackerCheck;

    public PlayerViolationRecord(AbstractHackerCheck abstractHackerCheckValue, int i) {
        super();
        AbstractHackerCheck.p();

        this.abstractHackerCheck = abstractHackerCheckValue;
        this.violationLevel = i;
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AbstractHackerCheck.b(new Entity[2]);
        }
    }
}
