package ru.mirea.tenyutin.serviceapp;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PlayerService extends Service {

    public static final String CHANNEL_ID = "MusicChannel42";
    private static final int   NOTIFY_ID  = 1;

    private MediaPlayer player;

    @Nullable @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override public void onCreate() {
        super.onCreate();

        NotificationChannel ch = new NotificationChannel(
                CHANNEL_ID,
                "Music player channel",
                NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManagerCompat.from(this).createNotificationChannel(ch);

        NotificationCompat.Builder nb =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Music player")
                        .setContentText("Воспроизведение: La bohème.mp3")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);

        startForeground(NOTIFY_ID, nb.build());

        player = MediaPlayer.create(this, R.raw.la_boheme);
        player.setLooping(false);
        player.setOnCompletionListener(mp -> stopSelf());
    }

    @Override public int onStartCommand(Intent intent, int flags, int startId) {
        if (player != null && !player.isPlaying()) player.start();
        return START_NOT_STICKY;
    }

    @Override public void onDestroy() {
        if (player != null) {
            player.stop();
            player.release();
        }
        stopForeground(true);
        super.onDestroy();
    }
}