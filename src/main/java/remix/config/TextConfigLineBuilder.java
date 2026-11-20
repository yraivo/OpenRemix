package remix.config;

import net.minecraft.entity.Entity;

public class TextConfigLineBuilder {
    private TextConfigLineList textConfigLineList;
    private static String[] packedTextFragments;
    private StringBuilder stringBuilder;

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.config.TextConfigLineBuilder.packedTextFragments[0] = new String(":");
    }

    // endregion

    public TextConfigLineBuilder(TextConfigLineList textConfigLineListValue) {
        AbstractConfigFile.getLegacyConfigLoadMarker();
        this.textConfigLineList = textConfigLineListValue;
        this.stringBuilder = new StringBuilder();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            AbstractConfigFile.setLegacyConfigLoadMarker(new int[5]);
        }
    }

    public void appendField(String text) {
        this.stringBuilder.append(text);
        this.stringBuilder.append(packedTextFragments[0]);
    }

    @Override
    public String toString() {
        return this.stringBuilder.toString();
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public void commitLine() {
        this.textConfigLineList.add(this);
    }

    public void appendText(String text) {
        this.stringBuilder.append(text);
    }

    public void terminateAndCommitLine() {
        this.stringBuilder.append(AbstractTextConfigFile.LINE_SEPARATOR);
        this.textConfigLineList.add(this);
    }

    // region String decoding support

    // endregion
}
