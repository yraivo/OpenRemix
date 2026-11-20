package net.minecraft.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import remix.compat.vecmath.Matrix4f;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class ShaderUniform {

    private int uniformType;

    private int uniformCount;

    private int uniformLocation;
    private IntBuffer uniformIntBuffer;
    private FloatBuffer uniformFloatBuffer;
    private static Logger logger;
    private ShaderManager shaderManager;
    private String shaderName;
    private boolean dirty;

    private void cQ() {
        Entity[] entityArray = Framebuffer.p();
        switch (this.uniformType) {
            case 4:
                OpenGlHelper.glUniform1(
                        this.uniformLocation, this.uniformFloatBuffer);
                if (entityArray == null) {
                    break;
                }
            case 5:
                OpenGlHelper.glUniform2(
                        this.uniformLocation, this.uniformFloatBuffer);
                if (entityArray == null) {
                    break;
                }
            case 6:
                OpenGlHelper.glUniform3(
                        this.uniformLocation, this.uniformFloatBuffer);
                if (entityArray == null) {
                    break;
                }
            case 7:
                OpenGlHelper.glUniform4(
                        this.uniformLocation, this.uniformFloatBuffer);
                if (entityArray == null) {
                    break;
                }
            default:
                net.minecraft.client.shader.ShaderUniform.logger.warn(
                        "Uniform.upload called, but count value ("
                                + this.uniformCount
                                + ") is not in the range of 1 to 4. Ignoring.");
        }
    }

    public void cj() {
        Entity[] entityArray = Framebuffer.p();
        if (!this.dirty) {}

        this.dirty = false;
        if (this.uniformType <= 3) {
            this.l();
            if (entityArray == null) {
                return;
            }
        }

        if (this.uniformType <= 7) {
            this.cQ();
            if (entityArray == null) {
                return;
            }
        }

        if (this.uniformType > 10) {
            net.minecraft.client.shader.ShaderUniform.logger.warn(
                    "Uniform.upload called, but type value ("
                            + this.uniformType
                            + ") is not a valid type. Ignoring.");
        } else {
            this.cS();
        }
    }

    public ShaderUniform(String text, int uniformTypeValue, int j, ShaderManager aJZValue) {
        this.shaderName = text;
        this.uniformCount = j;
        this.uniformType = uniformTypeValue;
        this.shaderManager = aJZValue;
        if (uniformTypeValue <= 3) {
            this.uniformIntBuffer = BufferUtils.createIntBuffer(j);
            this.uniformFloatBuffer = null;
        } else {
            this.uniformIntBuffer = null;
            this.uniformFloatBuffer = BufferUtils.createFloatBuffer(j);
        }

        this.uniformLocation = -1;
        this.markDirty();
    }

    public void setFloatsForUniformType(
            float p171_0, float p171_1, float p171_2, float p171_3) {
        ((java.nio.Buffer) this.uniformFloatBuffer).position(0);
        if (this.uniformType >= 4) {
            this.uniformFloatBuffer.put(0, p171_0);
        }

        if (this.uniformType >= 5) {
            this.uniformFloatBuffer.put(1, p171_1);
        }

        if (this.uniformType >= 6) {
            this.uniformFloatBuffer.put(2, p171_2);
        }

        if (this.uniformType >= 7) {
            this.uniformFloatBuffer.put(3, p171_3);
        }

        this.markDirty();
    }

    public void setFloat4(
            float p171_0, float p171_1, float p171_2, float p171_3) {
        ((java.nio.Buffer) this.uniformFloatBuffer).position(0);
        this.uniformFloatBuffer.put(p171_0);
        this.uniformFloatBuffer.put(p171_1);
        this.uniformFloatBuffer.put(p171_2);
        this.uniformFloatBuffer.put(p171_3);
        ((java.nio.Buffer) this.uniformFloatBuffer).flip();
        this.markDirty();
    }

    public String getShaderName() {
        return this.shaderName;
    }

    private void l() {
        Entity[] entityArray = Framebuffer.p();
        switch (this.uniformType) {
            case 0:
                OpenGlHelper.glUniform1(
                        this.uniformLocation, this.uniformIntBuffer);
                if (entityArray == null) {
                    break;
                }
            case 1:
                OpenGlHelper.glUniform2(
                        this.uniformLocation, this.uniformIntBuffer);
                if (entityArray == null) {
                    break;
                }
            case 2:
                OpenGlHelper.glUniform3(
                        this.uniformLocation, this.uniformIntBuffer);
                if (entityArray == null) {
                    break;
                }
            case 3:
                OpenGlHelper.glUniform4(
                        this.uniformLocation, this.uniformIntBuffer);
                if (entityArray == null) {
                    break;
                }
            default:
                net.minecraft.client.shader.ShaderUniform.logger.warn(
                        "Uniform.upload called, but count value ("
                                + this.uniformCount
                                + ") is  not in the range of 1 to 4. Ignoring.");
        }
    }

    public void set(int p171_0, int p171_1, int p171_2, int p171_3) {
        ((java.nio.Buffer) this.uniformIntBuffer).position(0);
        if (this.uniformType >= 0) {
            this.uniformIntBuffer.put(0, p171_0);
        }

        if (this.uniformType >= 1) {
            this.uniformIntBuffer.put(1, p171_1);
        }

        if (this.uniformType >= 2) {
            this.uniformIntBuffer.put(2, p171_2);
        }

        if (this.uniformType >= 3) {
            this.uniformIntBuffer.put(3, p171_3);
        }

        this.markDirty();
    }

    public void set(float p171_0, float p171_1) {
        ((java.nio.Buffer) this.uniformFloatBuffer).position(0);
        this.uniformFloatBuffer.put(0, p171_0);
        this.uniformFloatBuffer.put(1, p171_1);
        this.markDirty();
    }

    public static int parseType(String text) {
        Entity[] entityArray = Framebuffer.p();
        byte b0 = -1;
        Entity[] entityArrayValue = entityArray;
        if (text.equals("int")) {
            b0 = 0;
            if (entityArrayValue == null) {
                return b0;
            }
        }

        if (text.equals("float")) {
            b0 = 4;
            if (entityArrayValue == null) {
                return b0;
            }
        }

        if (text.startsWith("matrix")) {
            if (text.endsWith("2x2")) {
                b0 = 8;
                if (entityArrayValue == null) {
                    return b0;
                }
            }

            if (text.endsWith("3x3")) {
                b0 = 9;
                if (entityArrayValue == null) {
                    return b0;
                }
            }

            if (text.endsWith("4x4")) {
                b0 = 10;
            }
        }

        return b0;
    }

    private void markDirty() {
        this.dirty = true;
        if (this.shaderManager != null) {
            this.shaderManager.markDirty();
        }
    }

    private void cS() {
        Entity[] entityArray = Framebuffer.p();
        switch (this.uniformType) {
            case 8:
                OpenGlHelper.glUniformMatrix2(
                        this.uniformLocation, true, this.uniformFloatBuffer);
                if (entityArray == null) {
                    break;
                }
            case 9:
                OpenGlHelper.glUniformMatrix3(
                        this.uniformLocation, true, this.uniformFloatBuffer);
                if (entityArray == null) {
                    break;
                }
            case 10:
                OpenGlHelper.glUniformMatrix4(
                        this.uniformLocation, true, this.uniformFloatBuffer);
        }
    }

    public void set(float p171_0, float p171_1, float p171_2) {
        ((java.nio.Buffer) this.uniformFloatBuffer).position(0);
        this.uniformFloatBuffer.put(0, p171_0);
        this.uniformFloatBuffer.put(1, p171_1);
        this.uniformFloatBuffer.put(2, p171_2);
        this.markDirty();
    }

    public void setUniformLocation(int uniformLocationValue) {
        this.uniformLocation = uniformLocationValue;
    }

    static {
        net.minecraft.client.shader.ShaderUniform.logger = LogManager.getLogger();
    }

    public void set(float[] afloat) {
        Entity[] entityArray = Framebuffer.p();
        if (afloat.length < this.uniformCount) {
            net.minecraft.client.shader.ShaderUniform.logger.warn(
                    "Uniform.set called with a too-small value array (expected "
                            + this.uniformCount
                            + ", got "
                            + afloat.length
                            + "). Ignoring.");
            if (entityArray == null) {
                return;
            }
        }

        ((Buffer) this.uniformFloatBuffer).position(0);
        this.uniformFloatBuffer.put(afloat);
        ((Buffer) this.uniformFloatBuffer).position(0);
        this.markDirty();
    }

    public void set(
            float p171_0,
            float p171_1,
            float p171_2,
            float p171_3,
            float p171_4,
            float p171_5,
            float p171_6,
            float p171_7,
            float p171_8,
            float p171_9,
            float p171_10,
            float p171_11,
            float p171_12,
            float p171_13,
            float p171_14,
            float p171_15) {
        ((java.nio.Buffer) this.uniformFloatBuffer).position(0);
        this.uniformFloatBuffer.put(0, p171_0);
        this.uniformFloatBuffer.put(1, p171_1);
        this.uniformFloatBuffer.put(2, p171_2);
        this.uniformFloatBuffer.put(3, p171_3);
        this.uniformFloatBuffer.put(4, p171_4);
        this.uniformFloatBuffer.put(5, p171_5);
        this.uniformFloatBuffer.put(6, p171_6);
        this.uniformFloatBuffer.put(7, p171_7);
        this.uniformFloatBuffer.put(8, p171_8);
        this.uniformFloatBuffer.put(9, p171_9);
        this.uniformFloatBuffer.put(10, p171_10);
        this.uniformFloatBuffer.put(11, p171_11);
        this.uniformFloatBuffer.put(12, p171_12);
        this.uniformFloatBuffer.put(13, p171_13);
        this.uniformFloatBuffer.put(14, p171_14);
        this.uniformFloatBuffer.put(15, p171_15);
        this.markDirty();
    }

    public void set(float p171_0) {
        ((java.nio.Buffer) this.uniformFloatBuffer).position(0);
        this.uniformFloatBuffer.put(0, p171_0);
        this.markDirty();
    }

    public void set(Matrix4f p171_0) {
        this.set(
                p171_0.m00, p171_0.m01, p171_0.m02, p171_0.m03, p171_0.m10, p171_0.m11, p171_0.m12,
                p171_0.m13, p171_0.m20, p171_0.m21, p171_0.m22, p171_0.m23, p171_0.m30, p171_0.m31,
                p171_0.m32, p171_0.m33);
    }
}
