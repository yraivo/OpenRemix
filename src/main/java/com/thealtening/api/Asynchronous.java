package com.thealtening.api;

import com.thealtening.api.data.AccountData;
import com.thealtening.api.data.LicenseData;
import com.thealtening.api.utils.exceptions.TheAlteningException;

import java.util.concurrent.CompletableFuture;

public class Asynchronous {
    private TheAltening a;

    public CompletableFuture<Boolean> b(String text) {
        CompletableFuture completablefuture = new CompletableFuture();

        try {
            completablefuture.complete(this.a.jvm171_b_6af870e5e469af(text));
        } catch (TheAlteningException thealteningexception) {
            completablefuture.completeExceptionally(thealteningexception);
        }

        return completablefuture;
    }

    public CompletableFuture<Boolean> a(String text) {
        CompletableFuture completablefuture = new CompletableFuture();

        try {
            completablefuture.complete(this.a.jvm171_a_12d19207cee439(text));
        } catch (TheAlteningException thealteningexception) {
            completablefuture.completeExceptionally(thealteningexception);
        }

        return completablefuture;
    }

    public CompletableFuture<LicenseData> a() {
        CompletableFuture completablefuture = new CompletableFuture();

        try {
            completablefuture.complete(this.a.fetchLicense());
        } catch (TheAlteningException thealteningexception) {
            completablefuture.completeExceptionally(thealteningexception);
        }

        return completablefuture;
    }

    public CompletableFuture<AccountData> b() {
        CompletableFuture completablefuture = new CompletableFuture();

        try {
            completablefuture.complete(this.a.generateAccount());
        } catch (TheAlteningException thealteningexception) {
            completablefuture.completeExceptionally(thealteningexception);
        }

        return completablefuture;
    }

    // Recovered explicit constructors from original mapped instruction streams.
    public Asynchronous(TheAltening thealtening) {
        super();
        this.a = thealtening;
    }
}
