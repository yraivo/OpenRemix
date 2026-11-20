package net.minecraft.util;

public interface IProgressUpdate {
    void displayLoadingString(String text);

    void setLoadingProgress(int i);

    void resetProgressAndMessage(String text);

    void setDoneWorking();

    void displaySavingString(String text);
}
