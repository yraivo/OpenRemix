package remix.config;

import remix.module.player.KillSultsModule;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class KillsultsTextConfig extends AbstractTextConfigFile {
    public KillsultsTextConfig(File file1) {
        super(file1, StandardCharsets.UTF_8);
    }

    public void readLine(TextConfigLineBuilder textConfigLineBuilder) throws IOException {
        String text = textConfigLineBuilder.toString();

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (!KillSultsModule.remainingMessages.contains(text)) {
                KillSultsModule.remainingMessages.add(text);
            }
        } catch (IOException ioexception) {
            throw remix.compat.compiler.JvmThrowTypes.flow(ioexception);
        }
    }

    public void loadFrom(Object object) throws IOException {
        this.readLine((TextConfigLineBuilder) object);
    }

    public void saveTo(java.util.List list) throws IOException {
        AccountsTextConfig.getLegacyAccountConfigMarker();

        TextConfigLineList textConfigLineList = (TextConfigLineList) list;

        for (java.util.Iterator<?> __recoveryIterator171_1391 =
                        (KillSultsModule.messageTemplates).iterator();
                (__recoveryIterator171_1391).hasNext(); ) {
            String text = (String) (__recoveryIterator171_1391).next();
            TextConfigLineBuilder textConfigLineBuilder = textConfigLineList.newLine();
            textConfigLineBuilder.appendText(text);
            textConfigLineBuilder.terminateAndCommitLine();
        }
    }
}
