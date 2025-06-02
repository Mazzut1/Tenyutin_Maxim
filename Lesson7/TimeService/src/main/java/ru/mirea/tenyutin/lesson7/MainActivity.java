package ru.mirea.tenyutin.lesson7;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ru.mirea.tenyutin.lesson7.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final String host = "time.nist.gov";
    private final int port = 13;
    private final String TAG = "tagSocket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(v -> {
            GetTimeTask timeTask = new GetTimeTask();
            timeTask.execute();
        });
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            try (Socket socket = new Socket(host, port)) {
                Log.d(TAG, "Соединение установлено с " + host);
                BufferedReader reader = SocketUtils.getReader(socket);

                String firstLine = reader.readLine();
                Log.d(TAG, "Первая строка от сервера: " + firstLine);

                String secondLine = reader.readLine();
                Log.d(TAG, "Сырая строка времени: " + secondLine);

                if (secondLine == null || secondLine.isEmpty()) {
                    return "Ошибка: пустой ответ от сервера";
                }

                return convertToMoscowTime(secondLine);

            } catch (IOException e) {
                Log.e(TAG, "Ошибка соединения: " + e.getMessage(), e);
                return "Ошибка соединения: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            binding.textView.setText(result);
        }

        private String convertToMoscowTime(String timeString) {
            String[] parts = timeString.split(" ");
            if (parts.length < 3) {
                Log.w(TAG, "Некорректный формат строки: " + timeString);
                return "Неверный формат времени";
            }

            String date = parts[1];
            String time = parts[2];
            try {
                SimpleDateFormat sdfUtc = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.US);
                sdfUtc.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date utcDate = sdfUtc.parse(date + " " + time);

                SimpleDateFormat sdfMsk = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault());
                sdfMsk.setTimeZone(TimeZone.getTimeZone("Europe/Moscow"));
                String formatted = sdfMsk.format(utcDate);

                Log.d(TAG, "Московское время: " + formatted);
                return "Московское время:\n" + formatted;

            } catch (ParseException e) {
                Log.e(TAG, "Ошибка преобразования времени", e);
                return "Ошибка преобразования времени";
            }
        }
    }
}
