package remix.input;

import remix.input.FriendMiddleClickKeyAction;
import remix.event.InputType;
import remix.client.notification.NotificationManager;
import remix.client.notification.NotificationPriority;
import remix.event.annotation.Subscribe;
import remix.client.RemixClient;
import remix.event.EventBus;
import remix.module.ModuleBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntSupplier;

public final class KeyBindingDispatcher {

    public static Map<IntSupplier, List<KeyAction>> bindings;
    public static boolean noKeyActionDispatched;

    public void clearBindings() {
        bindings.clear();
    }

    // region Static initialization and string tables
    static {
        bindings = new HashMap<>();
        remix.input.KeyBindingDispatcher.noKeyActionDispatched = true;
    }

    // endregion

    @Subscribe(mc = InputEvent.class)
    public void onInput(InputEvent event) {
        InputType.getLegacyInputTypeMarker();

        int keyCode = event.getKeyCode();
        if (keyCode == 0) {
            return;
        }
        InputType type = event.getType();

        ArrayList arrayList = new ArrayList();

        for (Entry entry : bindings.entrySet()) {
            if (((IntSupplier) entry.getKey()).getAsInt() == keyCode) {
                arrayList.add(entry.getValue());
            }
        }

        int l = 0;
        int i1 = arrayList.size();

        while (l < i1) {
            List list = (List) arrayList.get(l);
            int j = 0;
            int k = list.size();

            while (true) {
                if (j < k) {
                    KeyAction keyAction = (KeyAction) list.get(j);
                    if (RemixClient.INSTANCE.handleKeyAction(
                            keyAction, type, keyCode)) {
                        remix.input.KeyBindingDispatcher.noKeyActionDispatched = false;
                        keyAction.execute();
                        if (keyAction instanceof ModuleToggleKeyAction) {
                            ModuleBase moduleBase = (ModuleBase) keyAction.getBoundKeyTask().getTarget();
                            NotificationManager.post(
                                    new NotificationManager(
                                            new String[] {
                                                "Toggled!",
                                                moduleBase.getName() + " has been toggled!"
                                            },
                                            NotificationPriority.LOW,
                                            4000));
                        }
                    }

                    j++;

                    continue;
                }

                l++;

                break;
            }
        }
    }

    public static KeyBindingDispatcher create(EventBus amg) {
        return new KeyBindingDispatcher(amg);
    }

    private KeyBindingDispatcher(EventBus amg) {
        bindings.put(
                () -> 3,
                Collections.singletonList(new FriendMiddleClickKeyAction(InputType.MIDDLE_CLICK)));
        amg.register(this);
    }
}
