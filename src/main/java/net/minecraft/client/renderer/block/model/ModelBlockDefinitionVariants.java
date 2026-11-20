package net.minecraft.client.renderer.block.model;

import java.util.List;

public class ModelBlockDefinitionVariants {
    private List listVariants;
    private String name;

    public ModelBlockDefinitionVariants(String text, List listVariantsValue) {
        this.name = text;
        this.listVariants = listVariantsValue;
    }

    public List getVariants() {
        return this.listVariants;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ModelBlockDefinitionVariants)) {
            return false;
        }

        ModelBlockDefinitionVariants modelBlockDefinitionVariants =
                (ModelBlockDefinitionVariants) object;
        return !this.name.equals(modelBlockDefinitionVariants.name)
                ? false
                : this.listVariants.equals(modelBlockDefinitionVariants.listVariants);
    }

    static String access$000(ModelBlockDefinitionVariants modelBlockDefinitionVariants) {
        return modelBlockDefinitionVariants.name;
    }

    @Override
    public int hashCode() {
        int i = this.name.hashCode();
        return 31 * i + this.listVariants.hashCode();
    }
}
