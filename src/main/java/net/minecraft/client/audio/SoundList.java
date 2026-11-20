package net.minecraft.client.audio;

import com.google.common.collect.Lists;

import java.util.List;

public class SoundList {
    private SoundCategory category;
    private boolean replaceExisting;
    private final List soundList = Lists.newArrayList();

    public SoundCategory getSoundCategory() {
        return this.category;
    }

    public void setReplaceExisting(boolean flag) {
        this.replaceExisting = flag;
    }

    public boolean canReplaceExisting() {
        return this.replaceExisting;
    }

    public void setSoundCategory(SoundCategory aqzValue) {
        this.category = aqzValue;
    }

    public List getSoundList() {
        return this.soundList;
    }
}
