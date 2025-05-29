package ru.mirea.tenyutin.notebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMS = 100;
    private EditText etFileName, etQuote;
    private Button btnSave, btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFileName = findViewById(R.id.etFileName);
        etQuote    = findViewById(R.id.etQuote);
        btnSave    = findViewById(R.id.btnSave);
        btnLoad    = findViewById(R.id.btnLoad);

        // Check and request permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!hasStoragePermissions()) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_PERMS);
            }
        }

        btnSave.setOnClickListener(v -> saveToFile());
        btnLoad.setOnClickListener(v -> loadFromFile());
    }

    private boolean hasStoragePermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isExternalWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void saveToFile() {
        if (!isExternalWritable()) {
            Toast.makeText(this, "Внешнее хранилище недоступно для записи", Toast.LENGTH_SHORT).show();
            return;
        }
        String name  = etFileName.getText().toString().trim();
        String quote = etQuote.getText().toString().trim();

        if (name.isEmpty() || quote.isEmpty()) {
            Toast.makeText(this, "Введите имя файла и цитату", Toast.LENGTH_SHORT).show();
            return;
        }

        File docsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        if (!docsDir.exists()) docsDir.mkdirs();

        File outFile = new File(docsDir, name + ".txt");
        try (FileOutputStream fos = new FileOutputStream(outFile, false);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            osw.write(quote);
            Toast.makeText(this,
                    "Сохранено: " + outFile.getAbsolutePath(),
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "Ошибка записи: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        String name = etFileName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Введите имя файла", Toast.LENGTH_SHORT).show();
            return;
        }

        File docsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File inFile  = new File(docsDir, name + ".txt");
        if (!inFile.exists()) {
            Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
            return;
        }

        try (FileInputStream fis = new FileInputStream(inFile);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(isr)) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
            etQuote.setText(sb.toString().trim());
            Toast.makeText(this,
                    "Загрузка успешна",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,
                    "Ошибка чтения: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] perms, int[] grantResults) {
        if (requestCode == REQUEST_PERMS) {
            if (!hasStoragePermissions()) {
                Toast.makeText(this, "Без разрешений работать нельзя", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, perms, grantResults);
    }
}