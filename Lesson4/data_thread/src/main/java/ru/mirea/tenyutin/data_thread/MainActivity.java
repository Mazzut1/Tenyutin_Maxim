package ru.mirea.tenyutin.data_thread;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import ru.mirea.tenyutin.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final SimpleDateFormat fmt =
            new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Runnable runn1 = () ->
                append("runn1 (runOnUiThread)");

        final Runnable runn2 = () ->
                append("runn2 (View.post)");

        final Runnable runn3 = () ->
                append("runn3 (postDelayed 2 сек.)");

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                runOnUiThread(runn1);

                TimeUnit.SECONDS.sleep(1);
                binding.tvInfo.postDelayed(runn3,
                        2_000);
                binding.tvInfo.post(runn2);
            } catch (InterruptedException ignored) {}
        }).start();
    }

    private void append(String text) {
        String line = fmt.format(System.currentTimeMillis()) +
                " — " + text + "\n";
        binding.tvInfo.append(line);
    }
}