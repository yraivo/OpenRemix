package remix.module;

public final class ModuleDefaults {
    private ModuleCategory category;
    private int keyBind = 0;
    private boolean visible = true;

    public ModuleDefaults visible(boolean flag) {
        this.visible = flag;
        return this;
    }

    public ModuleDefaults keyBind(int i) {
        this.keyBind = i;
        return this;
    }

    public int getKeyBind() {
        return this.keyBind;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public ModuleDefaults(ModuleCategory moduleCategory) {
        this.category = moduleCategory;
    }

    public static final ModuleDefaults ofCategory(ModuleCategory moduleCategory) {
        return new ModuleDefaults(moduleCategory);
    }

    public ModuleCategory getCategory() {
        return this.category;
    }
}
