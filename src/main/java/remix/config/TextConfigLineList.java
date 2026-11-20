package remix.config;

import net.minecraft.entity.Entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextConfigLineList extends ArrayList<TextConfigLineBuilder> {
    public TextConfigLineList() {}

    public TextConfigLineBuilder newLine() {
        return new TextConfigLineBuilder(this);
    }

    public TextConfigLineList(List<String> lines) {
        boolean loadOnlyFirstRecord = AbstractConfigFile.getLegacyConfigLoadMarker() != null;
        TextConfigLineBuilder record = this.newLine();
        Iterator<String> remaining = lines.iterator();
        while (remaining.hasNext()) {
            record.appendText(remaining.next());
            record.commitLine();
            if (remaining.hasNext()) {
                record = this.newLine();
                if (loadOnlyFirstRecord) {
                    Entity.setLegacyGlobalFlowMarker(new String[5]);
                    break;
                }
            }
        }
    }
}
