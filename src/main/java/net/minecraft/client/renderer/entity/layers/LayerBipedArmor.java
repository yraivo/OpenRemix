package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

public class LayerBipedArmor extends LayerArmorBase {
    protected void func_177194_a(ModelBiped modelBiped) {
        modelBiped.setAllPartsVisible(false);
    }

    protected void initArmor() {
        this.field_177189_c = new net.minecraft.client.model.ModelBiped(0.5F);
        this.field_177186_d = new net.minecraft.client.model.ModelBiped(1.0F);
    }

    public LayerBipedArmor(RendererLivingEntity rendererLivingEntity) {
        super(rendererLivingEntity);
    }

    protected void func_177179_a(ModelBase modelBase, int p171_1) {
        this.func_177195_a((net.minecraft.client.model.ModelBiped) modelBase, p171_1);
    }

    protected void func_177195_a(ModelBiped modelBiped, int i) {
        int[] aint1 = LayerArmorBase.method_650532ba484532b3efb0();
        this.func_177194_a(modelBiped);
        int[] aint = aint1;
        switch (i) {
            case 1:
                modelBiped.bipedRightLeg.showModel = true;
                modelBiped.bipedLeftLeg.showModel = true;
                if (aint == null) {
                    break;
                }
            case 2:
                modelBiped.bipedBody.showModel = true;
                modelBiped.bipedRightLeg.showModel = true;
                modelBiped.bipedLeftLeg.showModel = true;
                if (aint == null) {
                    break;
                }
            case 3:
                modelBiped.bipedBody.showModel = true;
                modelBiped.bipedRightArm.showModel = true;
                modelBiped.bipedLeftArm.showModel = true;
                if (aint == null) {
                    break;
                }
            case 4:
                modelBiped.bipedHead.showModel = true;
                modelBiped.bipedHeadwear.showModel = true;
        }
    }
}
