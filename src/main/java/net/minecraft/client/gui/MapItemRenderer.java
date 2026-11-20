package net.minecraft.client.gui;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;

import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;

public class MapItemRenderer {

    private final Map loadedMaps = Maps.newHashMap();
    private static ResourceLocation mapIcons;
    private TextureManager textureManager;

    public MapItemRenderer(TextureManager aqPValue) {
        this.textureManager = aqPValue;
    }

    public void renderMap(MapData mapData, boolean flag) {
        MapItemRendererInstance.access$100(this.getMapRendererInstance(mapData), flag);
    }

    static {
        net.minecraft.client.gui.MapItemRenderer.mapIcons = new ResourceLocation("textures/map/map_icons.png");
    }

    private MapItemRendererInstance getMapRendererInstance(MapData mapData) {
        MapItemRendererInstance mapItemRendererInstance =
                (MapItemRendererInstance) this.loadedMaps.get(mapData.mapName);
        if (mapItemRendererInstance == null) {
            mapItemRendererInstance = new MapItemRendererInstance(this, mapData, null);
            this.loadedMaps.put(mapData.mapName, mapItemRendererInstance);
        }

        return mapItemRendererInstance;
    }

    // region String decoding support

    // endregion

    static ResourceLocation access$500() {
        return net.minecraft.client.gui.MapItemRenderer.mapIcons;
    }

    public void clearLoadedMaps() {
        String[] astring1 = Gui.getLegacyGuiRenderMarker();
        Iterator iterator = this.loadedMaps.values().iterator();
        String[] astring = astring1;

        while (iterator.hasNext()) {
            MapItemRendererInstance mapItemRendererInstance =
                    (MapItemRendererInstance) iterator.next();
            this.textureManager.deleteTexture(
                    MapItemRendererInstance.access$300(mapItemRendererInstance));
            if (astring != null) {
                break;
            }
        }

        this.loadedMaps.clear();
    }

    public void updateMapTexture(MapData mapData) {
        MapItemRendererInstance.access$000(this.getMapRendererInstance(mapData));
    }

    static TextureManager access$400(MapItemRenderer mapItemRenderer) {
        return mapItemRenderer.textureManager;
    }

    // region String decoding support

    // endregion
}
