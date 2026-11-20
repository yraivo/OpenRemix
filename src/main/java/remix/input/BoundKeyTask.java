package remix.input;

import remix.event.CancellableNamedTask;
import remix.event.InputType;

import net.minecraft.entity.Entity;
import remix.client.ClientServices;
import remix.input.KeyAction;

import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BoundKeyTask<E> extends CancellableNamedTask {
    private BiPredicate<E, Integer> biPredicate;
    private Consumer<E> consumer;
    private E target;

    public E getTarget() {
        return this.target;
    }

    public boolean canExecute(ClientServices clientServices, int i) {
        return !this.method_fb2409edd951f8e30da1(clientServices) && this.biPredicate.test(this.getTarget(), i);
    }

    private <A extends E> void a(A object) {
        this.target = (E) object;
    }

    public KeyActionOutcome execute() {
        if (!this.isCancelled()) {
            try {
                this.consumer.accept(this.getTarget());
                return KeyActionOutcome.SUCCEEDED;
            } catch (Exception exception) {
                return KeyActionOutcome.FAILED;
            }
        } else {
            return KeyActionOutcome.FAILED;
        }
    }

    public BoundKeyTask(
            String text, BiPredicate<E, Integer> bipredicate, Consumer<E> consumer) {
        this(text, null, bipredicate, consumer);
    }

    public BoundKeyTask(
            String text, E object, BiPredicate<E, Integer> bipredicate, Consumer<E> consumer) {
        super(text);
        Entity[] entityArray = KeyAction.getLegacyKeyActionMarker();
        this.target = (E) object;
        this.biPredicate = bipredicate;
        Entity[] entityArrayValue = entityArray;
        this.consumer = consumer;
        if (entityArrayValue != null) {
            Entity.setLegacyGlobalFlowMarker(new String[4]);
        }
    }

    public KeyAction<E> toKeyAction(InputType inputType) {
        return new KeyAction(this, inputType);
    }
}
