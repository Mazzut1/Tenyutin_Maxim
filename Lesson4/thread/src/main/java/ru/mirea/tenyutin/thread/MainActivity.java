package ru.mirea.tenyutin.thread;

import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import ru.mirea.tenyutin.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG        = "ThreadModule";
    private static final String GROUP      = "БСБО-09-22";
    private static final int    LIST_INDEX = 24;

    private ActivityMainBinding binding;

    private final AtomicInteger threadCounter = new AtomicInteger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        showMainThreadInfo();
        binding.btnCalculate.setOnClickListener(v ->
                startBackgroundCalculation());
    }

    private void showMainThreadInfo() {
        Thread mainThread = Thread.currentThread();

        binding.tvResult.setText(String.format(
                Locale.getDefault(),
                "Имя текущего потока: %s", mainThread.getName()));

        mainThread.setName(String.format(
                Locale.getDefault(),
                "%s, №-по-списку: %d, фильм: Меч короля Артура",
                GROUP, LIST_INDEX));

        binding.tvResult.append(
                "\nНовое имя потока: " + mainThread.getName());
    }

    private void startBackgroundCalculation() {

        int totalLessons, studyDays;
        try {
            totalLessons = Integer.parseInt(
                    binding.etLessons.getText().toString());
            studyDays = Integer.parseInt(
                    binding.etDays.getText().toString());
            if (studyDays == 0) throw new IllegalArgumentException();
        } catch (Exception e) {
            binding.tvResult.setText("Введите корректные числа!");
            return;
        }

        new Thread(() -> {

            int num = threadCounter.incrementAndGet();
            Log.d(TAG, String.format(Locale.getDefault(),
                    "Запущен поток #%d студентом группы %s номер по списку %d",
                    num, GROUP, LIST_INDEX));

            Process.setThreadPriority(
                    Process.THREAD_PRIORITY_BACKGROUND);

            long end = System.currentTimeMillis() + 2_000;
            while (System.currentTimeMillis() < end) {
                synchronized (MainActivity.this) {
                    try {
                        MainActivity.this.wait(
                                end - System.currentTimeMillis());
                    } catch (InterruptedException ignored) { }
                }
            }

            double average = (double) totalLessons / studyDays;

            runOnUiThread(() -> binding.tvResult.setText(
                    String.format(Locale.getDefault(),
                            "Среднее число пар в день: %.2f",
                            average)));

            Log.d(TAG, String.format(Locale.getDefault(),
                    "Выполнен поток #%d", num));

        }).start();
    }
}
