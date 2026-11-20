package optifine;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

public class ReflectorForge {
    private static String[] packedTextFragments;

    // region String decoding support

    // endregion

    // region String decoding support
    private static void unpackLiteralFragments() {
        optifine.ReflectorForge.packedTextFragments[0] = new String("/");
    }

    // endregion

    static {
        packedTextFragments = new String[1];
        unpackLiteralFragments();
    }

    public static boolean isItemDamaged(ItemStack itemStack) {
        return !Reflector.ForgeItem_showDurabilityBar.exists()
                ? itemStack.isItemDamaged()
                : Reflector.callBoolean(
                        itemStack.getItem(), Reflector.ForgeItem_showDurabilityBar, new Object[] {itemStack});
    }

    public static void FMLClientHandler_trackBrokenTexture(ResourceLocation resourceLocation, String text) {
        if (!Reflector.FMLClientHandler_trackBrokenTexture.exists()) {
            Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
            Reflector.call(object, Reflector.FMLClientHandler_trackBrokenTexture, new Object[] {resourceLocation, text});
        }
    }

    public static void putLaunchBlackboard(String text, Object object) {
        Map map = (Map) Reflector.getFieldValue(Reflector.Launch_blackboard);
        if (map != null) {
            map.put(text, object);
        }
    }

    public static void FMLClientHandler_trackMissingTexture(ResourceLocation resourceLocation) {
        if (!Reflector.FMLClientHandler_trackMissingTexture.exists()) {
            Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
            Reflector.call(object, Reflector.FMLClientHandler_trackMissingTexture, new Object[] {resourceLocation});
        }
    }

    public static boolean blockHasTileEntity(BlockState blockState) {
        Block block = blockState.getBlock();
        return !Reflector.ForgeBlock_hasTileEntity.exists()
                ? block.hasTileEntity()
                : Reflector.callBoolean(block, Reflector.ForgeBlock_hasTileEntity, new Object[] {blockState});
    }

    public static InputStream getOptiFineResourceStream(String text) {
        if (!Reflector.OptiFineClassTransformer_instance.exists()) {
            return null;
        }

        Object fieldValue = Reflector.getFieldValue(Reflector.OptiFineClassTransformer_instance);
        if (fieldValue == null) {
            return null;
        }

        if (text.startsWith(packedTextFragments[0])) {
            text = text.substring(1);
        }

        byte[] abyte = (byte[]) Reflector.call(fieldValue, Reflector.OptiFineClassTransformer_getOptiFineResource, new Object[] {text});
        return abyte == null ? null : new ByteArrayInputStream(abyte);
    }
}
