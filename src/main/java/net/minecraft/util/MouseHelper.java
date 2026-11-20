package net.minecraft.util;

public class MouseHelper {
    public int deltaX;
    public int deltaY;

    public void ungrabMouseCursor() {
        org.lwjgl.input.Mouse.setCursorPosition(
                org.lwjgl.opengl.Display.getWidth() / 2, org.lwjgl.opengl.Display.getHeight() / 2);
        org.lwjgl.input.Mouse.setGrabbed(false);
    }

    public void grabMouseCursor() {
        org.lwjgl.input.Mouse.setGrabbed(true);
        this.deltaX = 0;
        this.deltaY = 0;
    }

    public void mouseXYChange() {
        this.deltaX = org.lwjgl.input.Mouse.getDX();
        this.deltaY = org.lwjgl.input.Mouse.getDY();
    }
}
