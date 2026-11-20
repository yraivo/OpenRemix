package remix.config;

import org.lwjgl.input.Keyboard;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.command.ClientCommandManager;
import remix.event.InputType;
import remix.input.KeyAction;
import remix.input.KeyBindingDispatcher;
import remix.input.SayingKeyAction;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.IntSupplier;

public final class BindsTextConfig extends AbstractTextConfigFile {
    private ClientCommandManager clientCommandManager;
    private static String[] packedTextFragments;

    public BindsTextConfig(File file1) {
        super(file1, StandardCharsets.UTF_8);
        ClientServices clientServices = (ClientServices) RemixClient.INSTANCE.getClientServices();
        this.clientCommandManager = ClientServices.commands;
    }

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    // region String decoding support

    // endregion

    public void saveTo(java.util.List list) throws IOException {
        AccountsTextConfig.getLegacyAccountConfigMarker();

        TextConfigLineList textConfigLineList = (TextConfigLineList) list;

        for (java.util.Iterator<?> __recoveryIterator171_1703 =
                        (KeyBindingDispatcher.bindings.entrySet()).iterator();
                (__recoveryIterator171_1703).hasNext(); ) {
            Entry entry = (Entry) (__recoveryIterator171_1703).next();
            int asInt = ((IntSupplier) entry.getKey()).getAsInt();
            if (asInt != 0) {
                List value = (List) entry.getValue();
                if (!value.isEmpty()) {
                    for (java.util.Iterator<?> __recoveryIterator171_1953 = (value).iterator();
                            (__recoveryIterator171_1953).hasNext(); ) {
                        KeyAction keyAction = (KeyAction) (__recoveryIterator171_1953).next();
                        if (keyAction instanceof SayingKeyAction) {
                            String text = (String) keyAction.getBoundKeyTask().getTarget();
                            TextConfigLineBuilder textConfigLineBuilder =
                                    textConfigLineList.newLine();
                            textConfigLineBuilder.appendField(Keyboard.getKeyName(asInt));
                            textConfigLineBuilder.appendText(text);
                            textConfigLineBuilder.terminateAndCommitLine();
                        }
                    }
                }
            }
        }
    }

    public void readLine(TextConfigLineBuilder textConfigLineBuilder) throws IOException {
        String text = textConfigLineBuilder.toString();

        try {
            if (false)
                throw (java.io.IOException)
                        null; // Original JVM checked handler; no emitted instructions.
            if (text.isEmpty() || !text.contains(packedTextFragments[0])) {
                return;
            }
        } catch (IOException ioexception1) {
            throw remix.compat.compiler.JvmThrowTypes.flow(ioexception1);
        }

        int i = text.indexOf(58);
        if (i != -1) {
            String textValue = text.substring(i + 1);
            String textValue2 = text.substring(0, i);
            int j = Keyboard.getKeyIndex(textValue2);

            try {
                if (false)
                    throw (java.io.IOException)
                            null; // Original JVM checked handler; no emitted instructions.
                if (j != 0) {
                    KeyBindingDispatcher.bindings.put(
                            () -> j,
                            Collections.singletonList(
                                    new SayingKeyAction(textValue, InputType.KEY_PRESS)));
                }
            } catch (IOException ioexception) {
                throw remix.compat.compiler.JvmThrowTypes.flow(ioexception);
            }
        }
    }

    // region String decoding support
    private static void unpackLiteralFragments() {
        remix.config.BindsTextConfig.packedTextFragments[0] = new String(":");
    }

    // endregion

    public void loadFrom(Object object) throws IOException {
        this.readLine((TextConfigLineBuilder) object);
    }
}
