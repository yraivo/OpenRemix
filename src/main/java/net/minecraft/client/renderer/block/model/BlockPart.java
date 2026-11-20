package net.minecraft.client.renderer.block.model;

import net.minecraft.util.Facing;

import remix.compat.vecmath.Vector3f;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class BlockPart {
    public Vector3f positionFrom;
    public Map mapFaces;
    public boolean shade;
    public Vector3f positionTo;
    public BlockPartRotation partRotation;

    private void setDefaultUvs() {
        int j = BakedQuad.eR();
        Iterator iterator = this.mapFaces.entrySet().iterator();
        int i = j;

        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            float[] afloat = this.getFaceUvs((Facing) entry.getKey());
            ((BlockPartFace) entry.getValue()).blockFaceUV.setUvs(afloat);
            if (i != 0) {
                break;
            }
        }
    }

    private float[] getFaceUvs(Facing facing) {
        int i = BakedQuad.cM();
        float[] afloat;
        switch (facing) {
            case DOWN:
            case UP:
                afloat =
                        new float[] {
                            this.positionFrom.x,
                            this.positionFrom.z,
                            this.positionTo.x,
                            this.positionTo.z
                        };
                if (i != 0) {
                    break;
                }
            case NORTH:
            case SOUTH:
                afloat =
                        new float[] {
                            this.positionFrom.x,
                            16.0F - this.positionTo.y,
                            this.positionTo.x,
                            16.0F - this.positionFrom.y
                        };
                if (i != 0) {
                    break;
                }
            case WEST:
            case EAST:
                afloat =
                        new float[] {
                            this.positionFrom.z,
                            16.0F - this.positionTo.y,
                            this.positionTo.z,
                            16.0F - this.positionFrom.y
                        };

                try {
                    if (i != 0) {
                        break;
                    }
                } catch (NullPointerException nullpointerexception1) {
                    throw method_339a46a0855ea6bd2c63(nullpointerexception1);
                }
            default:
                try {
                    throw new NullPointerException();
                } catch (NullPointerException nullpointerexception) {
                    throw method_339a46a0855ea6bd2c63(nullpointerexception);
                }
        }

        return afloat;
    }

    public BlockPart(
            Vector3f azgValue,
            Vector3f azdValue,
            Map map,
            BlockPartRotation aAFValue,
            boolean flag) {
        this.positionFrom = azgValue;
        this.positionTo = azdValue;
        this.mapFaces = map;
        this.partRotation = aAFValue;
        this.shade = flag;
        this.setDefaultUvs();
    }

    private static NullPointerException method_339a46a0855ea6bd2c63(
            NullPointerException nullpointerexception) {
        return nullpointerexception;
    }
}
