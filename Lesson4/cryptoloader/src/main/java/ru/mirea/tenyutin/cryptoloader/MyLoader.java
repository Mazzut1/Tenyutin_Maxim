package ru.mirea.tenyutin.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {

    public static final String ARG_CIPHER = "cipher_text";
    public static final String ARG_KEY    = "aes_key";

    private final byte[] cipherText;
    private final byte[] keyBytes;

    public MyLoader(@NonNull Context ctx, @Nullable Bundle args) {
        super(ctx);
        cipherText = args != null ? args.getByteArray(ARG_CIPHER) : null;
        keyBytes   = args != null ? args.getByteArray(ARG_KEY)    : null;
    }

    @Override protected void onStartLoading() { forceLoad(); }

    @Nullable @Override
    public String loadInBackground() {
        if (cipherText == null || keyBytes == null) return null;

        SystemClock.sleep(2000);

        SecretKey key = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");
        return CryptoUtils.decryptMsg(cipherText, key);
    }
}