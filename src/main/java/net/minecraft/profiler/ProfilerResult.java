package net.minecraft.profiler;

public final class ProfilerResult implements Comparable {
    public double parentPercent;
    public double totalPercent;
    public String sectionName;

    @Override
    public int compareTo(Object other) {
        return this.compareTo((net.minecraft.profiler.ProfilerResult) other);
    }

    public int compareTo(ProfilerResult other) {
        return other.parentPercent < this.parentPercent ? -1 : (other.parentPercent > this.parentPercent ? 1 : other.sectionName.compareTo(this.sectionName));
    }

    public ProfilerResult(String sectionName, double parentPercent, double totalPercent) {
        this.sectionName = sectionName;
        this.parentPercent = parentPercent;
        this.totalPercent = totalPercent;
    }

    public int getColor() {
        return (this.sectionName.hashCode() & 11184810) + 4473924;
    }
}
