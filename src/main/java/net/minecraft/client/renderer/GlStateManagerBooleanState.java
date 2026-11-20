package net.minecraft.client.renderer;

import org.lwjgl.opengl.GL11;

class GlStateManagerBooleanState {
    private boolean currentState = false;
    private int capability;

    public void setState(boolean currentStateValue) {
        int i = WorldRenderer.getLegacyRenderFlowMarker();
        if (currentStateValue != this.currentState) {
            this.currentState = currentStateValue;
            if (currentStateValue) {
                GL11.glEnable(this.capability);
                if (i != 0) {
                    return;
                }
            }

            GL11.glDisable(this.capability);
        }
    }

    public GlStateManagerBooleanState(int capabilityValue) {
        this.capability = capabilityValue;
    }

    public void setDisabled() {
        this.setState(false);
    }

    public void setEnabled() {
        this.setState(true);
    }
}
