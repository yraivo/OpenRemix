package net.minecraft.client.resources.data;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class AnimationMetadataSection implements IMetadataSection {
    private boolean interpolate;
    private int frameTime;
    private int frameHeight;
    private int frameWidth;
    private List animationFrames;

    public int getFrameHeight() {
        return this.frameHeight;
    }

    public int getFrameTime() {
        return this.frameTime;
    }

    public int getFrameCount() {
        return this.animationFrames.size();
    }

    public int method_46b7d87f71f96e2a0dba(int i) {
        AnimationFrame animationFrame = this.getAnimationFrame(i);
        return animationFrame.hasNoTime() ? this.frameTime : animationFrame.getFrameTime();
    }

    public boolean isInterpolate() {
        return this.interpolate;
    }

    public AnimationMetadataSection(
            List animationFramesValue,
            int frameWidthValue,
            int j,
            int k,
            boolean interpolateValue) {
        this.animationFrames = animationFramesValue;
        this.frameWidth = frameWidthValue;
        this.frameHeight = j;
        this.frameTime = k;
        this.interpolate = interpolateValue;
    }

    public int getFrameWidth() {
        return this.frameWidth;
    }

    public boolean frameHasTime(int i) {
        return !((AnimationFrame) this.animationFrames.get(i)).hasNoTime();
    }

    public int getFrameIndex(int i) {
        return ((AnimationFrame) this.animationFrames.get(i)).getFrameIndex();
    }

    private AnimationFrame getAnimationFrame(int i) {
        return (AnimationFrame) this.animationFrames.get(i);
    }

    public Set getFrameIndexSet() {
        HashSet hashset = Sets.newHashSet();
        boolean flag1 = BaseMetadataSectionSerializer.method_d87039b07bd6961907f2();
        Iterator iterator = this.animationFrames.iterator();
        boolean flag = flag1;

        while (iterator.hasNext()) {
            AnimationFrame animationFrame = (AnimationFrame) iterator.next();
            hashset.add(animationFrame.getFrameIndex());
            if (!flag) {
                break;
            }
        }

        return hashset;
    }
}
