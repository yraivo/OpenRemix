package remix.input;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

import remix.event.InputType;

public class SayingKeyAction extends KeyAction<String> {

    private static String f;

    static {
        f = "say_task";
    }

    // region String decoding support

    // endregion

    // region String decoding support

    // endregion

    public SayingKeyAction(String text, InputType inputType) {
        this(text, inputType, ModuleToggleKeyAction.getLegacyModuleToggleFlowState());
    }

    private SayingKeyAction(String text, InputType inputType, int[] initialFlowMarker) {
        super(
                new BoundKeyTask(
                        f,
                        text,
                        ((java.util.function.BiPredicate<java.lang.String, java.lang.Integer>)
                                ((sx, integer) ->
                                        sx != null
                                                && !sx.isEmpty()
                                                && MinecraftClient.getMinecraft().player != null)),
                        ((java.util.function.Consumer<java.lang.String>)
                                (sx -> MinecraftClient.getMinecraft().player.sendChatMessage(sx)))),
                inputType);

        if (Entity.getLegacyGlobalFlowMarker() != null) {
            ModuleToggleKeyAction.setLegacyModuleToggleFlowState(new int[2]);
        }
    }
}
