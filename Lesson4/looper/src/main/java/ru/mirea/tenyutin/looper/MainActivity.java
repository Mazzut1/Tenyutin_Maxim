package ru.mirea.tenyutin.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.tenyutin.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Handler mainHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String res = msg.getData().getString("RESULT");
                Log.d("MainActivity", "Получен ответ: " + res);
                Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
            }
        };

        myLooper = new MyLooper(mainHandler);
        myLooper.start();

        binding.btnSend.setOnClickListener(v -> {
            String ageStr = binding.etAge.getText().toString().trim();
            String jobStr = binding.etJob.getText().toString().trim();

            if (ageStr.isEmpty() || jobStr.isEmpty()) {
                Toast.makeText(this, "Заполните оба поля", Toast.LENGTH_SHORT).show();
                return;
            }

            int age;
            try {
                age = Integer.parseInt(ageStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Некорректный возраст", Toast.LENGTH_SHORT).show();
                return;
            }

            Message msg = Message.obtain();
            Bundle  b   = new Bundle();
            b.putInt("AGE", age);
            b.putString("JOB", jobStr);
            msg.setData(b);

            if (myLooper.mHandler != null) {
                myLooper.mHandler.sendMessage(msg);
            } else {
                Toast.makeText(this, "Looper ещё не готов", Toast.LENGTH_SHORT).show();
            }
        });
    }
}