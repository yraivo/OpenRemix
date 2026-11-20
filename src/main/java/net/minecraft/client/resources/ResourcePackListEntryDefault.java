package net.minecraft.client.resources;

import net.minecraft.client.gui.GuiScreenResourcePacks;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import com.google.gson.JsonParseException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ResourcePackListEntryDefault extends ResourcePackListEntry {

    private final IResourcePack aKj = this.mc.getResourcePackRepository().rprDefaultResourcePack;
    private ResourceLocation resourcePackIcon;
    private static Logger logger;

    protected boolean f() {
        return false;
    }

    protected String method_c60693f457aa70be7160() {
        return "Default";
    }

    public ResourcePackListEntryDefault(GuiScreenResourcePacks guiScreenResourcePacks) {
        super(guiScreenResourcePacks);

        DynamicTexture dynamicTexture;
        try {
            dynamicTexture = new DynamicTexture(this.aKj.getPackImage());
        } catch (IOException ioexception) {
            dynamicTexture = TextureUtil.missingTexture;
        }

        this.resourcePackIcon =
                this.mc
                        .getTextureManager()
                        .getDynamicTextureLocation("texturepackicon", dynamicTexture);
    }

    protected boolean e() {
        return false;
    }

    protected String method_e0e3092a59fee58fae52() {
        try {
            PackMetadataSection packMetadata =
                    (PackMetadataSection)
                            this.aKj.getPackMetadata(
                                    this.mc.getResourcePackRepository().rprMetadataSerializer,
                                    "pack");
            if (packMetadata != null) {
                return packMetadata.getPackDescription().getFormattedText();
            }
        } catch (JsonParseException jsonparseexception) {
            net.minecraft.client.resources.ResourcePackListEntryDefault.logger.error("Couldn't load metadata info", jsonparseexception);
        } catch (IOException ioexception) {
            net.minecraft.client.resources.ResourcePackListEntryDefault.logger.error("Couldn't load metadata info", ioexception);
        }

        return EnumChatFormatting.RED + "Missing pack.mcmeta :(";
    }

    protected boolean fo() {
        return false;
    }

    static {
        net.minecraft.client.resources.ResourcePackListEntryDefault.logger = LogManager.getLogger();
    }

    protected boolean aL() {
        return false;
    }

    protected void cj() {
        this.mc.getTextureManager().bindTexture(this.resourcePackIcon);
    }

    protected boolean d() {
        return false;
    }
}
