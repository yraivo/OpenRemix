package remix.check;

import net.minecraft.entity.player.PlayerEntity;
import remix.command.ClientCommandManager;
import remix.setting.Setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class HackerCheckManager {

    private static String violationMessageFormat;
    private Map<UUID, List<PlayerViolationRecord>> violationsByPlayerId;
    private Map<String, AbstractHackerCheck> checksByName;
    private static int legacyCheckFlowState;

    private Optional<List<PlayerViolationRecord>> findPlayerViolations(UUID uuid) {
        return Optional.ofNullable(this.violationsByPlayerId.get(uuid));
    }

    public static int getLegacyCheckFlowState() {
        return remix.check.HackerCheckManager.legacyCheckFlowState;
    }

    // region String decoding support

    // endregion

    public static HackerCheckManager create() {
        return new HackerCheckManager();
    }

    private HackerCheckManager() {
        getLegacyCheckFlowToken();

        this.violationsByPlayerId = new HashMap<>();
        this.checksByName = new HashMap<>();
        AbstractHackerCheck[] abstractHackerCheckArray =
                new AbstractHackerCheck[] {
                    new FlightHoverCheck(),
                    new MovementPredictionCheck(),
                    new ImpossiblePitchCheck(),
                    new OmniSprintCheck(),
                    new GroundSpoofCheck(),
                    new NoSlowdownCheck(),
                    new AutoArmorCheck()
                };
        AbstractHackerCheck[] abstractHackerCheckArrayValue = abstractHackerCheckArray;
        int j = abstractHackerCheckArrayValue.length;

        int k = 0;

        while (k < j) {
            AbstractHackerCheck abstractHackerCheck = abstractHackerCheckArrayValue[k];
            this.checksByName.put(abstractHackerCheck.getName(), abstractHackerCheck);
            k++;
        }
    }

    // region String decoding support

    // endregion

    public static int getLegacyCheckFlowToken() {
        int i = getLegacyCheckFlowState();
        return i == 0 ? 81 : 0;
    }

    public static void setLegacyCheckFlowState(int i) {
        remix.check.HackerCheckManager.legacyCheckFlowState = i;
    }

    static {
        if (getLegacyCheckFlowToken() != 0) {
            setLegacyCheckFlowState(65);
        }
        remix.check.HackerCheckManager.violationMessageFormat = "%s flagged %s (vl %s)";
    }

    public void runEnabledChecks(PlayerEntity player, Setting... checkSettings) {
        getLegacyCheckFlowState();

        List<AbstractHackerCheck> enabledChecks = new ArrayList<>();
        for (Setting setting : checkSettings) {
            if (setting.asBoolean()) {
                AbstractHackerCheck check = this.checksByName.get(setting.getName());
                if (check != null) {
                    enabledChecks.add(check);
                }
            }
        }

        for (AbstractHackerCheck check : enabledChecks) {
            UUID playerId = player.getUniqueId();
            if (!check.checkPlayer(player, playerId)) {
                continue;
            }

            int weight = check.getViolationWeight();
            List<PlayerViolationRecord> records =
                    this.findPlayerViolations(playerId).orElse(new ArrayList<>());
            PlayerViolationRecord violation = null;
            for (PlayerViolationRecord candidate : records) {
                if (candidate.abstractHackerCheck.equals(check)) {
                    violation = candidate;
                    break;
                }
            }

            if (violation == null) {
                violation = new PlayerViolationRecord(check, weight);
            } else {
                violation.violationLevel += weight;
            }
            // The original also appends the existing record after increasing its weight.
            records.add(violation);
            this.violationsByPlayerId.put(playerId, records);
            ClientCommandManager.printMessage(
                    String.format(remix.check.HackerCheckManager.violationMessageFormat, player.getName(), check.getDescription(), violation.violationLevel));
        }
    }
}
