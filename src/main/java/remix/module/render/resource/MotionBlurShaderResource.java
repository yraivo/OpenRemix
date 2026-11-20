package remix.module.render.resource;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.io.IOUtils;

import remix.module.render.CameraModule;

import java.io.InputStream;
import java.util.Locale;

public class MotionBlurShaderResource implements IResource {
    private static String b;

    CameraModule ut;

    public InputStream getInputStream() {
        double d0 = 0.7 + this.ut.blurAmountSetting.asDouble() / 100.0 * 3.0 - 0.01;
        return IOUtils.toInputStream(String.format(Locale.ENGLISH, b, d0, d0, d0));
    }

    // region String decoding support

    // endregion

    public IMetadataSection getMetadata(String text) {
        return null;
    }

    public ResourceLocation getResourceLocation() {
        return new ResourceLocation("motionblur", "motionblur");
    }

    public String getResourcePackName() {
        return "Remix MotionBlur";
    }

    public boolean hasMetadata() {
        return false;
    }

    static {
        b =
                "{\"targets\":[\"swap\",\"previous\"],\"passes\":[{\"name\":\"phosphor\",\"intarget\":\"minecraft:main\",\"outtarget\":\"swap\",\"auxtargets\":[{\"name\":\"PrevSampler\",\"id\":\"previous\"}],\"uniforms\":[{\"name\":\"Phosphor\",\"values\":[%.2f,"
                    + " %.2f,"
                    + " %.2f]}]},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"previous\"},{\"name\":\"blit\",\"intarget\":\"swap\",\"outtarget\":\"minecraft:main\"}]}";
    }

    // region String decoding support

    // endregion

    // Recovered explicit constructors from original mapped instruction streams.
    public MotionBlurShaderResource(CameraModule cameraModule) {
        super();
        this.ut = cameraModule;
    }
}
