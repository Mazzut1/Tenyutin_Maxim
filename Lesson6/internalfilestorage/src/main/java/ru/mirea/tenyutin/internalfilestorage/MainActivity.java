package ru.mirea.tenyutin.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String FILENAME = "history.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText etHistory = findViewById(R.id.etHistory);
        Button btnSave    = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> {
            String text = etHistory.getText().toString().trim();
            if (text.isEmpty()) {
                Toast.makeText(this, "Введите дату и описание", Toast.LENGTH_SHORT).show();
                return;
            }

            try (FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE)) {
                fos.write(text.getBytes());
                Toast.makeText(this, "Сохранено в " + getFilesDir() + "/" + FILENAME, Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Ошибка сохранения: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}