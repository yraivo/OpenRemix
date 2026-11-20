package optifine;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagDouble;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.nbt.NBTTagShort;
import net.minecraft.nbt.NBTTagString;

import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Arrays;

public class NbtTagValue {

    private static final int f = 1;
    private static String mC;
    private static String vI;
    private static String sT;
    private static final int sR = 3;
    private String d;
    private static final int Ao = 2;
    private static final int ef = 0;
    private String[] parents;
    private static final int mu = 4;
    private String b;
    private int type;
    private static String kG;

    private static String[] packedTextFragments;

    static {
        optifine.NbtTagValue.packedTextFragments = new String[6];
        optifine.NbtTagValue.packedTextFragments[0] = new String(".");
        optifine.NbtTagValue.packedTextFragments[1] = new String("*");
        optifine.NbtTagValue.packedTextFragments[2] = new String(".");
        optifine.NbtTagValue.packedTextFragments[3] = new String(".");
        kG = "iregex:";
        vI = "ipattern:";
        mC = "regex:";
        sT = "pattern:";
    }

    private boolean matchesRegex(String text, String textValue) {
        return text.matches(textValue);
    }

    private static String getValue(NBTBase nbtBase) {
        try {
            if (nbtBase == null) {
                return null;
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        if (nbtBase instanceof NBTTagString) {
            NBTTagString nbtTagString = (NBTTagString) nbtBase;
            return nbtTagString.getString();
        } else if (nbtBase instanceof NBTTagInt) {
            NBTTagInt nbtTagInt = (NBTTagInt) nbtBase;
            return Integer.toString(nbtTagInt.getInt());
        } else if (nbtBase instanceof NBTTagByte) {
            NBTTagByte nbtTagByte = (NBTTagByte) nbtBase;
            return Byte.toString(nbtTagByte.getByte());
        } else if (nbtBase instanceof NBTTagShort) {
            NBTTagShort nbtTagShort = (NBTTagShort) nbtBase;
            return Short.toString(nbtTagShort.getShort());
        } else if (nbtBase instanceof NBTTagLong) {
            NBTTagLong nbtTagLong = (NBTTagLong) nbtBase;
            return Long.toString(nbtTagLong.getLong());
        } else if (nbtBase instanceof NBTTagFloat) {
            NBTTagFloat nbtTagFloat = (NBTTagFloat) nbtBase;
            return Float.toString(nbtTagFloat.getFloat());
        } else if (nbtBase instanceof NBTTagDouble) {
            NBTTagDouble nbtTagDouble = (NBTTagDouble) nbtBase;
            return Double.toString(nbtTagDouble.getDouble());
        } else {
            return nbtBase.toString();
        }
    }

    private static NBTBase getChildTag(NBTBase nbtBase, String text) {
        if (nbtBase instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag) nbtBase;
            return compoundTag.getTag(text);
        }

        if (nbtBase instanceof NBTTagList) {
            NBTTagList nbtTagList = (NBTTagList) nbtBase;
            int i = OptiFineConfig.parseInt(text, -1);

            try {
                if (i < 0) {
                    return null;
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }

            return nbtTagList.get(i);
        } else {
            return null;
        }
    }

    private boolean matchesAnyChild(NBTBase nbtBase) {
        int i = ReflectorMethod.cR();
        if (nbtBase instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag) nbtBase;

            for (java.util.Iterator<?> __recoveryIterator171_11142 =
                            (compoundTag.getKeySet()).iterator();
                    (__recoveryIterator171_11142).hasNext(); ) {
                String text = (String) (__recoveryIterator171_11142).next();
                NBTBase tag = compoundTag.getTag(text);

                try {
                    if (this.matchesBase(tag)) {
                        return true;
                    }
                } catch (IllegalArgumentException illegalargumentexception1) {
                    throw method_9a70918140b0a7642dba(illegalargumentexception1);
                }

                if (i == 0) {
                    break;
                }
            }
        }

        if (nbtBase instanceof NBTTagList) {
            NBTTagList nbtTagList = (NBTTagList) nbtBase;
            int j = nbtTagList.tagCount();
            int k = 0;

            while (k < j) {
                NBTBase nbtBaseValue = nbtTagList.get(k);

                try {
                    if (this.matchesBase(nbtBaseValue)) {
                        return true;
                    }
                } catch (IllegalArgumentException illegalargumentexception) {
                    throw method_9a70918140b0a7642dba(illegalargumentexception);
                }

                k++;
                if (i == 0) {
                    break;
                }
            }
        }

        return false;
    }

    private boolean matchesPattern(String text, String textValue) {
        return StrUtils.equalsMask(text, textValue, '*', '?');
    }

    public boolean matches(CompoundTag compoundTag) {
        int i = ReflectorMethod.cR();

        try {
            if (compoundTag == null) {
                return false;
            }
        } catch (IllegalArgumentException illegalargumentexception4) {
            throw method_9a70918140b0a7642dba(illegalargumentexception4);
        }

        Object object = compoundTag;
        int j = 0;

        while (j < this.parents.length) {
            String text = this.parents[j];
            object = getChildTag((NBTBase) object, text);

            try {
                if (object == null) {
                    return false;
                }
            } catch (IllegalArgumentException illegalargumentexception1) {
                throw method_9a70918140b0a7642dba(illegalargumentexception1);
            }

            j++;
            if (i == 0) {
                break;
            }
        }

        try {
            if (this.b.equals(packedTextFragments[1])) {
                return this.matchesAnyChild((NBTBase) object);
            }
        } catch (IllegalArgumentException illegalargumentexception3) {
            throw method_9a70918140b0a7642dba(illegalargumentexception3);
        }

        NBTBase nbtBase = getChildTag((NBTBase) object, this.b);

        try {
            if (nbtBase == null) {
                return false;
            }
        } catch (IllegalArgumentException illegalargumentexception2) {
            throw method_9a70918140b0a7642dba(illegalargumentexception2);
        }

        try {
            return this.matchesBase(nbtBase);
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }
    }

    public NbtTagValue(String text, String textValue) {
        label70:
        {
            this.parents = null;
            int j = ReflectorMethod.f();
            this.b = null;
            this.type = 0;
            int i = j;
            this.d = null;
            String[] astring = OptiFineConfig.tokenize(text, packedTextFragments[0]);
            this.parents = Arrays.copyOfRange(astring, 0, astring.length - 1);
            this.b = astring[astring.length - 1];
            if (textValue.startsWith("pattern:")) {
                this.type = 1;
                textValue = textValue.substring("pattern:".length());

                try {
                    if (i == 0) {
                        break label70;
                    }
                } catch (IllegalArgumentException illegalargumentexception7) {
                    throw method_9a70918140b0a7642dba(illegalargumentexception7);
                }
            }

            {
                boolean runGuardedCode;
                try {
                    runGuardedCode = !(!textValue.startsWith("ipattern:"));
                } catch (IllegalArgumentException illegalargumentexception6) {
                    throw method_9a70918140b0a7642dba(illegalargumentexception6);
                }
                if (runGuardedCode) {

                    this.type = 2;
                    textValue = textValue.substring("ipattern:".length()).toLowerCase();

                    try {
                        if (i == 0) {
                            break label70;
                        }
                    } catch (IllegalArgumentException illegalargumentexception5) {
                        throw method_9a70918140b0a7642dba(illegalargumentexception5);
                    }
                }
            }

            {
                boolean runGuardedCode;
                try {
                    runGuardedCode = !(!textValue.startsWith("regex:"));
                } catch (IllegalArgumentException illegalargumentexception4) {
                    throw method_9a70918140b0a7642dba(illegalargumentexception4);
                }
                if (runGuardedCode) {

                    this.type = 3;
                    textValue = textValue.substring("regex:".length());

                    try {
                        if (i == 0) {
                            break label70;
                        }
                    } catch (IllegalArgumentException illegalargumentexception3) {
                        throw method_9a70918140b0a7642dba(illegalargumentexception3);
                    }
                }
            }

            {
                boolean runGuardedCode;
                try {
                    runGuardedCode = !(!textValue.startsWith("iregex:"));
                } catch (IllegalArgumentException illegalargumentexception2) {
                    throw method_9a70918140b0a7642dba(illegalargumentexception2);
                }
                if (runGuardedCode) {

                    this.type = 4;
                    textValue = textValue.substring("iregex:".length()).toLowerCase();

                    try {
                        if (i == 0) {
                            break label70;
                        }
                    } catch (IllegalArgumentException illegalargumentexception1) {
                        throw method_9a70918140b0a7642dba(illegalargumentexception1);
                    }
                }
            }

            try {
                this.type = 0;
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }
        }

        textValue = StringEscapeUtils.unescapeJava(textValue);
        this.d = textValue;
    }

    private boolean matchesBase(NBTBase nbtBase) {
        try {
            if (nbtBase == null) {
                return false;
            }
        } catch (IllegalArgumentException illegalargumentexception2) {
            throw method_9a70918140b0a7642dba(illegalargumentexception2);
        }

        String text = getValue(nbtBase);

        try {
            if (text == null) {
                return false;
            }
        } catch (IllegalArgumentException illegalargumentexception) {
            throw method_9a70918140b0a7642dba(illegalargumentexception);
        }

        try {
            switch (this.type) {
                case 0:
                    return text.equals(this.d);
                case 1:
                    return this.matchesPattern(text, this.d);
                case 2:
                    return this.matchesPattern(text.toLowerCase(), this.d);
                case 3:
                    return this.matchesRegex(text, this.d);
                case 4:
                    return this.matchesRegex(text.toLowerCase(), this.d);
            }
        } catch (IllegalArgumentException illegalargumentexception1) {
            throw method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        throw new IllegalArgumentException("Unknown NbtTagValue type: " + this.type);
    }

    @Override
    public String toString() {
        int k = ReflectorMethod.f();
        StringBuffer stringbuffer = new StringBuffer();
        int i = k;
        int j = 0;

        while (j < this.parents.length) {
            String text = this.parents[j];

            try {
                if (j > 0) {
                    stringbuffer.append(packedTextFragments[2]);
                }
            } catch (IllegalArgumentException illegalargumentexception) {
                throw method_9a70918140b0a7642dba(illegalargumentexception);
            }

            stringbuffer.append(text);
            j++;
            if (i != 0) {
                break;
            }
        }

        try {
            if (stringbuffer.length() > 0) {
                stringbuffer.append(packedTextFragments[3]);
            }
        } catch (IllegalArgumentException illegalargumentexception1) {
            throw method_9a70918140b0a7642dba(illegalargumentexception1);
        }

        stringbuffer.append(this.b);
        stringbuffer.append(" = ");
        stringbuffer.append(this.d);
        return stringbuffer.toString();
    }

    private static IllegalArgumentException method_9a70918140b0a7642dba(
            IllegalArgumentException illegalargumentexception) {
        return illegalargumentexception;
    }
}
