package remix.util.render;

import net.minecraft.world.WorldProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/** Client-thread brightness ownership shared by FullBright and Xray. */
public final class WorldBrightnessOverride {
    private static final Map<WorldProvider, State> STATES = new IdentityHashMap<>();
    private WorldProvider provider;

    public void apply(WorldProvider nextProvider) {
        if (this.provider != nextProvider) {
            this.release();
            this.provider = nextProvider;
        }
        if (nextProvider == null) {
            return;
        }
        State state = STATES.get(nextProvider);
        if (state == null) {
            state = new State(nextProvider.getLightBrightnessTable().clone());
            STATES.put(nextProvider, state);
        }
        state.owners.add(this);
        Arrays.fill(nextProvider.getLightBrightnessTable(), 10.0F);
    }

    public void release() {
        if (this.provider == null) {
            return;
        }
        State state = STATES.get(this.provider);
        if (state != null && state.owners.remove(this) && state.owners.isEmpty()) {
            float[] table = this.provider.getLightBrightnessTable();
            System.arraycopy(state.original, 0, table, 0, table.length);
            STATES.remove(this.provider);
        }
        this.provider = null;
    }

    private static final class State {
        final float[] original;
        final Set<WorldBrightnessOverride> owners =
                Collections.newSetFromMap(new IdentityHashMap<WorldBrightnessOverride, Boolean>());

        State(float[] original) {
            this.original = original;
        }
    }
}
