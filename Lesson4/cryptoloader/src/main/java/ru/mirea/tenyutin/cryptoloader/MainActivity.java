package ru.mirea.tenyutin.cryptoloader;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import javax.crypto.SecretKey;

import ru.mirea.tenyutin.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private static final int LOADER_ID = 2025;
    private ActivityMainBinding binding;

    private SecretKey lastKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnEncrypt.setOnClickListener(v -> {
            String phrase = binding.etPhrase.getText().toString().trim();
            if (phrase.isEmpty()) {
                Toast.makeText(this, "Введите текст!", Toast.LENGTH_SHORT).show();
                return;
            }

            lastKey = CryptoUtils.generateKey();
            byte[] cipher = CryptoUtils.encryptMsg(phrase, lastKey);

            Bundle args = new Bundle();
            args.putByteArray(MyLoader.ARG_CIPHER, cipher);
            args.putByteArray(MyLoader.ARG_KEY,   lastKey.getEncoded());

            LoaderManager.getInstance(this)
                    .restartLoader(LOADER_ID, args, this);
            Toast.makeText(this, "Старт дешифровки (Loader)…", Toast.LENGTH_SHORT).show();
        });
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyLoader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        if (data != null)
            Toast.makeText(this,
                    "Результат дешифровки: " + data,
                    Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) { }
}