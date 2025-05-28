package ru.mirea.tenyutin.serviceapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final String[] NOTIF_PERMS = {
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.FOREGROUND_SERVICE };

    private final ActivityResultContracts.RequestMultiplePermissions permLauncher =
            new ActivityResultContracts.RequestMultiplePermissions();

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 33 &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.POST_NOTIFICATIONS)
                        != PackageManager.PERMISSION_GRANTED) {
            registerForActivityResult(permLauncher, res -> {
                if (!Boolean.TRUE.equals(res.get(Manifest.permission.POST_NOTIFICATIONS)))
                    Toast.makeText(this, "Нет разрешения на уведомления", Toast.LENGTH_SHORT).show();
            }).launch(NOTIF_PERMS);
        }

        findViewById(R.id.btnPlay).setOnClickListener(v ->
                ContextCompat.startForegroundService(this,
                        new Intent(this, PlayerService.class)));

        findViewById(R.id.btnStop).setOnClickListener(v ->
                stopService(new Intent(this, PlayerService.class)));
    }
}