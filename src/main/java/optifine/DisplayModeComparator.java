package optifine;

import java.util.Comparator;

final class DisplayModeComparator implements Comparator {
    @Override
    public int compare(Object p171_0, Object p171_1) {
        org.lwjgl.opengl.DisplayMode displaymode = (org.lwjgl.opengl.DisplayMode) p171_0;
        org.lwjgl.opengl.DisplayMode displaymode1 = (org.lwjgl.opengl.DisplayMode) p171_1;
        return displaymode.getWidth() != displaymode1.getWidth()
                ? displaymode1.getWidth() - displaymode.getWidth()
                : (displaymode.getHeight() != displaymode1.getHeight()
                        ? displaymode1.getHeight() - displaymode.getHeight()
                        : 0);
    }
}
