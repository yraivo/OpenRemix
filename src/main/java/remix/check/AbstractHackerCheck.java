package remix.check;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public abstract class AbstractHackerCheck {
    private HackerCheckCategory hackerCheckCategory;
    private static Entity[] dL;
    private String description;
    private String name;
    private String b;

    public abstract boolean checkPlayer(PlayerEntity playerEntity, UUID uuid);

    public HackerCheckCategory getHackerCheckCategory() {
        return this.hackerCheckCategory;
    }

    public static Entity[] p() {
        return dL;
    }

    public static void b(Entity[] entityArray) {
        dL = entityArray;
    }

    public abstract int getMaxViolations();

    public String getDescription() {
        return this.description;
    }

    public String q() {
        return this.b;
    }

    static {
        if (p() == null) {
            b(new Entity[4]);
        }
    }

    public AbstractHackerCheck(
            String text,
            String textValue,
            String textValue2,
            HackerCheckCategory hackerCheckCategoryValue) {
        super();

        this.name = text;
        this.b = textValue;
        this.description = textValue2;
        this.hackerCheckCategory = hackerCheckCategoryValue;
    }

    public abstract int getViolationWeight();

    public String getName() {
        return this.name;
    }
}
