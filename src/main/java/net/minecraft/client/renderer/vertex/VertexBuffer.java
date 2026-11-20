package net.minecraft.client.renderer.vertex;

import net.minecraft.client.renderer.OpenGlHelper;

import org.lwjgl.opengl.GL11;

import java.nio.ByteBuffer;

public class VertexBuffer {
    private int glBufferId;
    private VertexFormat vertexFormat;
    private int count;

    public void unbindBuffer() {
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, 0);
    }

    public void drawArrays(int i) {
        GL11.glDrawArrays(i, 0, this.count);
    }

    public void uploadVertexData(ByteBuffer bytebuffer, int i) {
        this.bindBuffer();
        OpenGlHelper.glBufferData(OpenGlHelper.GL_ARRAY_BUFFER, bytebuffer, 35044);
        this.unbindBuffer();
        this.count = i / this.vertexFormat.getNextOffset();
    }

    public void deleteGlBuffers() {
        if (this.glBufferId >= 0) {
            OpenGlHelper.glDeleteBuffers(this.glBufferId);
            this.glBufferId = -1;
        }
    }

    public VertexBuffer(VertexFormat aFYValue) {
        this.vertexFormat = aFYValue;
        this.glBufferId = OpenGlHelper.glGenBuffers();
    }

    public void bindBuffer() {
        OpenGlHelper.glBindBuffer(OpenGlHelper.GL_ARRAY_BUFFER, this.glBufferId);
    }
}
