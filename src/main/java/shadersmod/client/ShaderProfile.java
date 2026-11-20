package shadersmod.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShaderProfile {
    private String name = null;
    private Set<String> disabledPrograms;
    private Map<String, String> mapOptionValues = new HashMap<>();

    public String getName() {
        return this.name;
    }

    public void addDisabledPrograms(Collection<String> textCollection) {
        this.disabledPrograms.addAll(textCollection);
    }

    public void applyOptionValues(ShaderOption[] shaderOptionArray) {
        int[] aint1 = ShaderOption.lz();
        int i = 0;
        int[] aint = aint1;

        while (i < shaderOptionArray.length) {
            ShaderOption shaderOption = shaderOptionArray[i];
            String text = shaderOption.getName();
            String textValue = ((java.lang.String) this.mapOptionValues.get(text));
            if (textValue != null) {
                shaderOption.setValue(textValue);
            }

            i++;
            if (aint != null) {
                break;
            }
        }
    }

    public ShaderProfile(String text) {
        this.disabledPrograms = new HashSet<>();
        this.name = text;
    }

    public void addOptionValues(ShaderProfile shaderProfile) {
        if (shaderProfile != null) {
            this.mapOptionValues.putAll(shaderProfile.mapOptionValues);
        }
    }

    public void addDisabledProgram(String text) {
        this.disabledPrograms.add(text);
    }

    public String[] getOptions() {
        Set set = this.mapOptionValues.keySet();
        return ((java.lang.String[]) set.toArray(new String[set.size()]));
    }

    public void addOptionValue(String text, String textValue) {
        this.mapOptionValues.put(text, textValue);
    }

    public String getValue(String text) {
        return ((java.lang.String) this.mapOptionValues.get(text));
    }

    public Collection<String> getDisabledPrograms() {
        return new HashSet<>(this.disabledPrograms);
    }

    public boolean isProgramDisabled(String text) {
        return this.disabledPrograms.contains(text);
    }
}
