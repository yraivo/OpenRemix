package net.minecraft.client.resources;

import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.client.resources.SkinManagerSkinAvailableCallback;
import net.minecraft.util.ResourceLocation;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;

import java.awt.image.BufferedImage;

public class SkinManagerLoadSkinIImageBuffer implements IImageBuffer {
    SkinManagerSkinAvailableCallback aKP;
    MinecraftProfileTexture aKQ;
    Type aKR;
    ResourceLocation Fu;
    ImageBufferDownload val$imageBufferDownload;
    SkinManager aKS;

    public SkinManagerLoadSkinIImageBuffer(
            SkinManager aKSValue,
            ImageBufferDownload val$imageBufferDownloadValue,
            SkinManagerSkinAvailableCallback aKPValue,
            Type type,
            ResourceLocation resourceLocation,
            MinecraftProfileTexture minecraftprofiletexture) {
        this.aKS = aKSValue;
        this.val$imageBufferDownload = val$imageBufferDownloadValue;
        this.aKP = aKPValue;
        this.aKR = type;
        this.Fu = resourceLocation;
        this.aKQ = minecraftprofiletexture;
    }

    public BufferedImage parseUserSkin(BufferedImage bufferedimage) {
        if (this.val$imageBufferDownload != null) {
            bufferedimage = this.val$imageBufferDownload.parseUserSkin(bufferedimage);
        }

        return bufferedimage;
    }

    public void skinAvailable() {
        if (this.val$imageBufferDownload != null) {
            this.val$imageBufferDownload.skinAvailable();
        }

        if (this.aKP != null) {
            this.aKP.skinAvailable(this.aKR, this.Fu, this.aKQ);
        }
    }
}
