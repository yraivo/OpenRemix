package net.minecraft.client.entity;

import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponent;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.client.gui.GuiScreen;

import com.mojang.authlib.GameProfile;

public class EntityOtherPlayerMP extends AbstractClientPlayer {
    private double otherPlayerMPZ;
    private boolean isItemInUse;
    private double otherPlayerMPPitch;
    private double otherPlayerMPX;
    private int otherPlayerMPPosRotationIncrements;
    private double otherPlayerMPYaw;
    private double otherPlayerMPY;

    public void onLivingUpdate() {
        String text = GuiScreen.getLegacyScreenFlowMarker();
        if (this.otherPlayerMPPosRotationIncrements > 0) {
            double d0 = this.posX + (this.otherPlayerMPX - this.posX) / this.otherPlayerMPPosRotationIncrements;
            double d1 = this.posY + (this.otherPlayerMPY - this.posY) / this.otherPlayerMPPosRotationIncrements;
            double d2 = this.posZ + (this.otherPlayerMPZ - this.posZ) / this.otherPlayerMPPosRotationIncrements;
            double d3 = this.otherPlayerMPYaw - this.rotationYaw;

            while (d3 < -180.0) {
                d3 += 360.0;
                if (text != null) {
                    break;
                }
            }

            while (d3 >= 180.0) {
                d3 -= 360.0;
                if (text != null) {
                    break;
                }
            }

            this.rotationYaw = (float) (this.rotationYaw + d3 / this.otherPlayerMPPosRotationIncrements);
            this.rotationPitch =
                    (float) (this.rotationPitch + (this.otherPlayerMPPitch - this.rotationPitch) / this.otherPlayerMPPosRotationIncrements);
            this.otherPlayerMPPosRotationIncrements--;
            this.setPosition(d0, d1, d2);
            this.setRotation(this.rotationYaw, this.rotationPitch);
        }

        this.prevCameraYaw = this.cameraYaw;
        this.nx();
        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float f = (float) Math.atan(-this.motionY * 0.2F) * 15.0F;
        if (f1 > 0.1F) {
            f1 = 0.1F;
        }

        if (!this.onGround || this.getHealth() <= 0.0F) {
            f1 = 0.0F;
        }

        if (this.onGround || this.getHealth() <= 0.0F) {
            f = 0.0F;
        }

        this.cameraYaw = this.cameraYaw + (f1 - this.cameraYaw) * 0.4F;
        this.cameraPitch = this.cameraPitch + (f - this.cameraPitch) * 0.8F;
    }

    public boolean canCommandSenderUseCommand(int i, String text) {
        return false;
    }

    public void addChatMessage(ChatComponent chatComponent) {
        net.minecraft.client.MinecraftClient.getMinecraft().ingameGUI.getChatGUI().printChatMessage(chatComponent);
    }

    public EntityOtherPlayerMP(World worldValue, GameProfile p171_1) {
        super(worldValue, p171_1);
        this.stepHeight = 0.0F;
        this.noClip = true;
        this.sleepRenderOffsetY = 0.25F;
        this.renderDistanceWeight = 10.0;
    }

    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        return true;
    }

    public void setPositionAndRotation2(
            double aLUValue,
            double aLXValue,
            double aLSValue,
            float aLWValue,
            float aLTValue,
            int aLVValue,
            boolean p171_6) {
        this.otherPlayerMPX = aLUValue;
        this.otherPlayerMPY = aLXValue;
        this.otherPlayerMPZ = aLSValue;
        this.otherPlayerMPYaw = aLWValue;
        this.otherPlayerMPPitch = aLTValue;
        this.otherPlayerMPPosRotationIncrements = aLVValue;
    }

    public BlockPos getPosition() {
        return new net.minecraft.util.BlockPos(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5);
    }

    public void onUpdate() {
        String text = GuiScreen.getLegacyScreenFlowMarker();
        this.sleepRenderOffsetY = 0.0F;
        String textValue = text;
        super.onUpdate();
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double d0 = this.posX - this.prevPosX;
        double d1 = this.posZ - this.prevPosZ;
        float f = MathHelper.sqrt(d0 * d0 + d1 * d1) * 4.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        this.limbSwingAmount = this.limbSwingAmount + (f - this.limbSwingAmount) * 0.4F;
        this.limbSwing = this.limbSwing + this.limbSwingAmount;
        if (!this.isItemInUse
                && this.isEating()
                && this.inventory.mainInventory[this.inventory.currentItem] != null) {
            ItemStack itemStack = this.inventory.mainInventory[this.inventory.currentItem];
            this.setItemInUse(
                    this.inventory.mainInventory[this.inventory.currentItem],
                    itemStack.getItem().getMaxItemUseDuration(itemStack));
            this.isItemInUse = true;
            if (textValue == null) {
                return;
            }
        }

        if (this.isItemInUse && !this.isEating()) {
            this.clearItemInUse();
            this.isItemInUse = false;
        }
    }

    public void setCurrentItemOrArmor(int i, ItemStack itemStack) {
        String text = GuiScreen.getLegacyScreenFlowMarker();
        if (i == 0) {
            this.inventory.mainInventory[this.inventory.currentItem] = itemStack;
            if (text == null) {
                return;
            }
        }

        this.inventory.armorInventory[i - 1] = itemStack;
    }
}
