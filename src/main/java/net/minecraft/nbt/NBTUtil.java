package net.minecraft.nbt;

import net.minecraft.util.StringUtils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.util.UUID;

public final class NBTUtil {

    private static Throwable method_b7e52da193241b30710d(Throwable throwable) {
        return throwable;
    }


    public static CompoundTag writeGameProfile(CompoundTag compoundTag, GameProfile gameprofile) {
        String[] astring = NBTBase.xf();
        if (!StringUtils.isNullOrEmpty(gameprofile.getName())) {
            compoundTag.setString("Name", gameprofile.getName());
        }

        if (gameprofile.getId() != null) {
            compoundTag.setString("Id", gameprofile.getId().toString());
        }

        if (!gameprofile.getProperties().isEmpty()) {
            CompoundTag compoundTagValue = new CompoundTag();

            for (java.util.Iterator<?> __recoveryIterator171_25937 =
                            (gameprofile.getProperties().keySet()).iterator();
                    (__recoveryIterator171_25937).hasNext(); ) {
                String text = (String) (__recoveryIterator171_25937).next();
                NBTTagList nbtTagList = new NBTTagList();

                for (java.util.Iterator<?> __recoveryIterator171_26111 =
                                (gameprofile.getProperties().get(text)).iterator();
                        (__recoveryIterator171_26111).hasNext(); ) {
                    Property property = (Property) (__recoveryIterator171_26111).next();
                    CompoundTag compoundTagValue2 = new CompoundTag();
                    compoundTagValue2.setString("Value", property.getValue());
                    if (property.hasSignature()) {
                        compoundTagValue2.setString("Signature", property.getSignature());
                    }

                    nbtTagList.appendTag(compoundTagValue2);
                    if (astring != null) {
                        break;
                    }
                }

                compoundTagValue.setTag(text, nbtTagList);
                if (astring != null) {
                    break;
                }
            }

            compoundTag.setTag("Properties", compoundTagValue);
        }

        return compoundTag;
    }

    public static GameProfile readGameProfileFromNBT(CompoundTag compoundTag) {
        String[] astring1 = NBTBase.xf();
        String text = null;
        String textValue = null;
        String[] astring = astring1;
        if (compoundTag.hasKey("Name", 8)) {
            text = compoundTag.getString("Name");
        }

        if (compoundTag.hasKey("Id", 8)) {
            textValue = compoundTag.getString("Id");
        }

        if (StringUtils.isNullOrEmpty(text) && StringUtils.isNullOrEmpty(textValue)) {
            return null;
        }

        UUID uuid;
        try {
            uuid = UUID.fromString(textValue);
        } catch (Throwable throwable) {
            uuid = null;
        }

        GameProfile gameprofile = new GameProfile(uuid, text);
        if (compoundTag.hasKey("Properties", 10)) {
            CompoundTag compoundTagValue = compoundTag.getCompoundTag("Properties");

            for (java.util.Iterator<?> __recoveryIterator171_28239 =
                            (compoundTagValue.getKeySet()).iterator();
                    (__recoveryIterator171_28239).hasNext(); ) {
                String textValue2 = (String) (__recoveryIterator171_28239).next();
                NBTTagList nbtTagList = compoundTagValue.getTagList(textValue2, 10);
                int i = 0;

                while (i < nbtTagList.tagCount()) {
                    {
                        CompoundTag compoundTagValue2 = nbtTagList.getCompoundTagAt(i);
                        String textValue3 = compoundTagValue2.getString("Value");
                        if (compoundTagValue2.hasKey("Signature", 8)) {
                            gameprofile
                                    .getProperties()
                                    .put(
                                            textValue2,
                                            new Property(
                                                    textValue2,
                                                    textValue3,
                                                    compoundTagValue2.getString("Signature")));
                            if (!(astring == null)) {

                                gameprofile
                                        .getProperties()
                                        .put(textValue2, new Property(textValue2, textValue3));
                            }
                        } else {

                            gameprofile
                                    .getProperties()
                                    .put(textValue2, new Property(textValue2, textValue3));
                        }
                    }

                    i++;
                    if (astring != null) {
                        break;
                    }
                }

                if (astring != null) {
                    break;
                }
            }
        }

        return gameprofile;
    }
}
