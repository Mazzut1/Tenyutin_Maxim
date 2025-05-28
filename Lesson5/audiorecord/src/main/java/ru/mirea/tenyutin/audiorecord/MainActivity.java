package ru.mirea.tenyutin.audiorecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import ru.mirea.tenyutin.audiorecord.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private static final int REQUEST_CODE_RECORD = 200;
    private static final String PERM_RECORD = Manifest.permission.RECORD_AUDIO;

    private MediaRecorder recorder;
    private MediaPlayer   player;
    private String        recordFilePath;

    private boolean isRecording = false;
    private boolean isPlaying   = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Сохраняем в приватную Music-папку приложения
        File outDir = getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        recordFilePath = new File(outDir, "audiorecord.m4a").getAbsolutePath();

        // Play недоступен до первой записи
        binding.btnPlay.setEnabled(false);

        // Запросим RECORD_AUDIO, если надо
        if (ContextCompat.checkSelfPermission(this, PERM_RECORD)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{PERM_RECORD},
                    REQUEST_CODE_RECORD
            );
        }

        // Обработчики кнопок
        binding.btnRecord.setOnClickListener(v -> {
            if (!isRecording) startRecording();
            else              stopRecording();
        });

        binding.btnPlay.setOnClickListener(v -> {
            if (!isPlaying) startPlaying();
            else            stopPlaying();
        });
    }

    // ——— Запись: MPEG_4 + AAC ———
    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;
            binding.btnRecord.setText("Stop recording");
            binding.btnPlay  .setEnabled(false);
            Toast.makeText(this, "Recording started", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Record failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        isRecording = false;
        binding.btnRecord.setText("Start recording");
        binding.btnPlay  .setEnabled(true);
        Toast.makeText(this, "Saved: " + recordFilePath, Toast.LENGTH_SHORT).show();
    }

    // ——— Воспроизведение ———
    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
            isPlaying = true;
            binding.btnPlay  .setText("Stop playing");
            binding.btnRecord.setEnabled(false);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Play failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlaying() {
        player.release();
        player = null;
        isPlaying = false;
        binding.btnPlay  .setText("Start playing");
        binding.btnRecord.setEnabled(true);
    }

    // ——— Обработка результата запроса RECORD_AUDIO ———
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[]    grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_RECORD) {
            if (grantResults.length == 0 ||
                    grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,
                        "RECORD_AUDIO permission is required",
                        Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
