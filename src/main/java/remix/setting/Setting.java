package remix.setting;

import net.minecraft.entity.Entity;

import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleMetadata;

public final class Setting<S> {
    private double numericValue;
    private double increment;
    private double maximum;
    private double minimum;
    private S value;
    private String name;
    private SettingType type;
    private ModuleMetadata owner;
    private boolean booleanValue;
    private static boolean standardSettingFlow;

    public void setOwner(ModuleMetadata ownerValue) {
        this.owner = ownerValue;
    }

    public static boolean usesLegacySettingFlow() {
        return !isStandardSettingFlow();
    }

    public boolean isNumeric() {
        return this.type.equals(SettingType.NUMERIC);
    }

    public Setting(
            String name,
            ModuleMetadata owner,
            double initialValue,
            double minimum,
            double maximum,
            double increment) {
        this.name = name;
        this.owner = owner;
        this.numericValue = initialValue;
        this.minimum = minimum;
        boolean legacyFlow = usesLegacySettingFlow();
        this.maximum = maximum;
        this.increment = increment;
        this.type = SettingType.NUMERIC;
        this.register();
        if (legacyFlow) {
            Entity.setLegacyGlobalFlowMarker(new String[2]);
        }
    }

    public boolean isBoolean() {
        return this.type.equals(SettingType.BOOLEAN);
    }

    // region Static initialization and string tables
    static {
        if (!isStandardSettingFlow()) {
            setStandardSettingFlow(true);
        }
    }

    // endregion

    public boolean asBoolean() {
        return this.booleanValue;
    }

    public boolean isEnum() {
        return this.type.equals(SettingType.ENUM);
    }

    public void setValue(S object) {
        this.value = (S) object;
    }

    public S getValue() {
        return this.value;
    }

    public Setting(String name, ModuleMetadata owner, boolean initialValue) {
        boolean standardFlow = isStandardSettingFlow();
        this.name = name;
        this.owner = owner;
        this.booleanValue = initialValue;
        this.type = SettingType.BOOLEAN;
        this.register();
        if (Entity.getLegacyGlobalFlowMarker() != null) {
            setStandardSettingFlow(!standardFlow);
        }
    }

    public double getIncrement() {
        return this.increment;
    }

    public int getOptionIndex() {
        boolean legacyFlow = usesLegacySettingFlow();
        SettingOption[] options = this.getEnumValues();
        for (int index = 0; index < options.length; index++) {
            if (options[index] == this.getValue()) {
                return index;
            }
            if (legacyFlow) {
                break;
            }
        }

        return 0;
    }

    public void setNumericValue(double requestedValue) {
        if (Double.isNaN(requestedValue) || Double.isInfinite(requestedValue)) {
            return;
        }
        boolean legacyFlow = usesLegacySettingFlow();
        if (requestedValue > this.maximum && !legacyFlow) {
            this.numericValue = this.maximum;
            return;
        }
        this.numericValue =
                requestedValue < this.minimum
                        ? this.minimum
                        : requestedValue > this.maximum ? this.maximum : requestedValue;
    }

    public double getMaximum() {
        return this.maximum;
    }

    public Setting(String name, ModuleMetadata owner, S initialValue) {
        this.name = name;
        this.owner = owner;
        this.value = initialValue;
        this.type = SettingType.ENUM;
        this.register();
    }

    private void register() {
        RemixClient.INSTANCE.getClientServices();
        ClientServices.settingRegistry.registerSetting(this);
    }

    public String getName() {
        return this.name;
    }

    public void setBooleanValue(boolean booleanValueValue) {
        this.booleanValue = booleanValueValue;
    }

    public SettingOption[] getEnumValues() {
        return (SettingOption[]) ((Enum) this.value).getDeclaringClass().getEnumConstants();
    }

    public double asDouble() {
        return this.numericValue;
    }

    public S findEnumValue(String displayName) {
        SettingOption[] options = this.getEnumValues();
        boolean standardFlow = isStandardSettingFlow();
        for (SettingOption option : options) {
            if (option.getDisplayName().equalsIgnoreCase(displayName)) {
                return (S) option;
            }
            if (!standardFlow) {
                break;
            }
        }

        return null;
    }

    public static void setStandardSettingFlow(boolean flag) {
        remix.setting.Setting.standardSettingFlow = flag;
    }

    public SettingType getType() {
        return this.type;
    }

    public double getMinimum() {
        return this.minimum;
    }

    public static boolean isStandardSettingFlow() {
        return remix.setting.Setting.standardSettingFlow;
    }

    public void setEnumValueByName(String displayName) {
        S option = this.findEnumValue(displayName);
        if (option != null) {
            this.value = option;
        }
    }

    public ModuleMetadata getOwner() {
        return this.owner;
    }
}
