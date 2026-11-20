package remix.module.render.resource;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import remix.module.render.CameraModule;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MotionBlurResourceManager implements IResourceManager {
    CameraModule ut;

    public MotionBlurResourceManager(CameraModule cameraModule) {
        this.ut = cameraModule;
    }

    public List<IResource> getAllResources(ResourceLocation resourceLocation) throws IOException {
        return Collections.singletonList(this.getResource(resourceLocation));
    }

    public IResource getResource(ResourceLocation resourceLocation) throws IOException {
        if (!"motionblur".equals(resourceLocation.getResourceDomain())
                || !"motionblur".equals(resourceLocation.getResourcePath())) {
            throw new FileNotFoundException(resourceLocation.toString());
        }
        return new MotionBlurShaderResource(this.ut);
    }

    public Set<String> getResourceDomains() {
        return Collections.singleton("motionblur");
    }
}
