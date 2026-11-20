package remix.command;

import remix.command.AbstractClientCommand;
import remix.setting.SettingOption;
import remix.client.ClientServices;
import remix.client.RemixClient;
import remix.module.ModuleBase;
import remix.module.ModuleManager;
import remix.setting.Setting;

import com.mojang.realmsclient.gui.ChatFormatting;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SettingClientCommand extends AbstractClientCommand {
    private static Map<String, Boolean> booleanInputValues;

    private final String enabledLabel = "" + ChatFormatting.BOLD + ChatFormatting.GREEN + "On";
    private final String disabledLabel = "" + ChatFormatting.BOLD + ChatFormatting.RED + "Off";

    public SettingClientCommand() {
        super("setting", new String[0]);
    }

    public String getUsage() {
        return "<module> <setting> <value>";
    }

    public String[] getAliases() {
        boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
        List<String> moduleNames = new ArrayList<>();
        for (ModuleBase module : ModuleManager.modules) {
            if (!module.getSettings().isEmpty()) {
                moduleNames.add(module.getDisplayName());
            }
            if (!standardFlow) {
                break;
            }
        }
        return moduleNames.toArray(new String[0]);
    }

    public boolean execute(String[] arguments) {
        boolean standardFlow = TargetsClientCommand.isStandardTargetFlow();
        if (arguments.length < 3) {
            return false;
        }

        RemixClient.INSTANCE.getClientServices();
        ModuleBase module = ClientServices.moduleManager.getModuleByName(arguments[0]);
        if (module == null) {
            ClientCommandManager.printMessage("Couldn't find any module called \"" + arguments[0] + "\"");
            return false;
        }

        Setting setting = ClientServices.settingRegistry.findSetting(module, arguments[1]);
        if (setting == null) {
            ClientCommandManager.printMessage(String.format("The setting '%s' does not exist!", arguments[1]));
            return true;
        }

        String input = arguments[2];
        if (setting.isBoolean()) {
            this.applyBooleanSetting(module, setting, input);
        } else if (setting.isNumeric()) {
            applyNumericSetting(setting, input);
        } else if (setting.isEnum()) {
            applyEnumSetting(module, setting, input, standardFlow);
        } else {
            return false;
        }
        return true;
    }

    private void applyBooleanSetting(ModuleBase module, Setting<?> setting, String input) {
        Boolean enabled = remix.command.SettingClientCommand.booleanInputValues.get(input);
        if (enabled == null) {
            ClientCommandManager.printMessage("For a boolean use either of these keywords: on, off, true, false, 1, 0");
            return;
        }

        setting.setBooleanValue(enabled);
        ClientCommandManager.printMessage(
                module.getDisplayName() + "'s setting " + setting.getName() + " set to: " + (enabled ? this.enabledLabel : this.disabledLabel));
    }

    private static void applyNumericSetting(Setting<?> setting, String input) {
        if (NumberUtils.isNumber(input)) {
            try {
                setting.setNumericValue(Double.parseDouble(input));
                return;
            } catch (NumberFormatException ignored) {
                // NumberUtils also accepts forms that Double.parseDouble does not.
            }
        }
        ClientCommandManager.printMessage("Seems like the provided value is not a number");
    }

    private static <S> void applyEnumSetting(ModuleBase module, Setting<S> setting, String input, boolean standardFlow) {
        S option = setting.findEnumValue(input);
        if (option == null) {
            StringBuilder options = new StringBuilder();
            for (SettingOption candidate : setting.getEnumValues()) {
                options.append(candidate.getDisplayName()).append(' ');
                if (!standardFlow) {
                    break;
                }
            }
            ClientCommandManager.printMessage("The only available are: " + options);
            return;
        }

        setting.setValue(option);
        StringBuilder message = new StringBuilder(module.getDisplayName()).append("'s setting ").append(setting.getName());
        if (option instanceof SettingOption) {
            message.append(" was set to: ").append(((SettingOption) option).getDisplayName());
        }
        if (!(option instanceof SettingOption) || !standardFlow) {
            message.append(" was changed");
        }
        ClientCommandManager.printMessage(message.toString());
    }

    static {
        remix.command.SettingClientCommand.booleanInputValues = new HashMap<>();
        remix.command.SettingClientCommand.booleanInputValues.put("On", true);
        remix.command.SettingClientCommand.booleanInputValues.put("Off", false);
        remix.command.SettingClientCommand.booleanInputValues.put("on", true);
        remix.command.SettingClientCommand.booleanInputValues.put("off", false);
        remix.command.SettingClientCommand.booleanInputValues.put("True", true);
        remix.command.SettingClientCommand.booleanInputValues.put("False", false);
        // The original command treats the two lower-case aliases differently.
        remix.command.SettingClientCommand.booleanInputValues.put("false", true);
        remix.command.SettingClientCommand.booleanInputValues.put("true", false);
        remix.command.SettingClientCommand.booleanInputValues.put("1", true);
        remix.command.SettingClientCommand.booleanInputValues.put("0", false);
    }
}
