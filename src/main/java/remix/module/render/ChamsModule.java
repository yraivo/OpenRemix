package remix.module.render;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import remix.ui.hud.ChamsColor;
import remix.module.ModuleBase;
import remix.module.ModuleCategory;
import remix.module.ModuleDefaults;
import remix.setting.Setting;

/** Chams module: render events and 10 settings. */
public final class ChamsModule extends ModuleBase {
    public Setting localPlayerSetting;
    private Setting mobsSetting;

    private Setting animalsSetting;

    private Setting neutralSetting;
    public Setting storageSetting;

    public boolean usesStorageDepthOffset() {
        return this.isEnabled() && this.storageSetting.asBoolean();
    }
    public Setting alphaSetting;
    public final Setting<ChamsColor> colorSetting = new Setting<>("Color", this, ChamsColor.CLIENT);
    public Setting playersSetting;
    public Setting coloredSetting;
    public Setting textureSetting;


    public boolean shouldRenderEntity(LivingEntity entity) {
        byte b0 = 1;
        boolean localPlayer = this.localPlayerSetting.asBoolean();
        int i = entity == minecraft.player ? 1 : 0;
        if (!localPlayer && i != 0) {
            b0 = 0;
        }

        return entity instanceof PlayerEntity && b0 != 0 && this.playersSetting.asBoolean()
                || entity instanceof AnimalEntity && this.animalsSetting.asBoolean()
                || (entity instanceof MonsterEntity
                                || entity instanceof EntityGolem
                                || entity instanceof SlimeEntity)
                        && this.mobsSetting.asBoolean()
                || (entity instanceof BatEntity
                                || entity instanceof SquidEntity
                                || entity instanceof EntityVillager)
                        && this.neutralSetting.asBoolean();
    }

    public ChamsModule() {
        super("Chams", ModuleDefaults.ofCategory(ModuleCategory.RENDER));
        this.alphaSetting = new Setting("Alpha", this, 0.3, 0.1, 1.0, 0.1);
        this.localPlayerSetting = new Setting("LocalPlayer", this, false);
        this.playersSetting = new Setting("Players", this, true);
        this.mobsSetting = new Setting("Mobs", this, false);
        this.animalsSetting = new Setting("Animals", this, false);
        this.storageSetting = new Setting("Storage", this, false);
        this.neutralSetting = new Setting("Neutral", this, false);
        this.coloredSetting = new Setting("Colored", this, true);
        this.textureSetting = new Setting("Texture", this, false);
        this.setVisible(false);
    }
}
