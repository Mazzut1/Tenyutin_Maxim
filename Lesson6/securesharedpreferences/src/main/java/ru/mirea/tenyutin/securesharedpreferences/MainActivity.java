package ru.mirea.tenyutin.securesharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_FILE = "secret_shared_prefs";
    private static final String KEY_POET = "favorite_poet";
    private static final String KEY_IMAGE = "poet_image";

    private TextView tvPoetName;
    private ImageView ivPoet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvPoetName = findViewById(R.id.tvPoetName);
        ivPoet     = findViewById(R.id.ivPoet);

        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            SharedPreferences securePrefs = EncryptedSharedPreferences.create(
                    PREFS_FILE,
                    masterKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

            SharedPreferences.Editor editor = securePrefs.edit();
            editor.putString(KEY_POET,  "Сергей Есенин");
            editor.putString(KEY_IMAGE, "poet");
            editor.apply();

            String poetName = securePrefs.getString(KEY_POET, "Неизвестный поэт");
            String imageResName = securePrefs.getString(KEY_IMAGE, "");

            tvPoetName.setText(poetName);
            if (!imageResName.isEmpty()) {
                int resId = getResources().getIdentifier(imageResName, "drawable", getPackageName());
                if (resId != 0) {
                    ivPoet.setImageResource(resId);
                }
            }

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Ошибка работы с EncryptedSharedPreferences", e);
        }
    }
}