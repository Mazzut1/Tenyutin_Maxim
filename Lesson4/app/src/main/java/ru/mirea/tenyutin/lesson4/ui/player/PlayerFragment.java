package ru.mirea.tenyutin.lesson4.ui.player;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

import ru.mirea.tenyutin.lesson4.R;
import ru.mirea.tenyutin.lesson4.databinding.FragmentPlayerBinding;

public class PlayerFragment extends Fragment {

    private FragmentPlayerBinding binding;

    private MediaPlayer mediaPlayer;
    private boolean isPrepared   = false;
    private boolean isPlaying    = false;

    private final Handler uiHandler = new Handler(Looper.getMainLooper());
    private final Runnable progressUpdater = new Runnable() {
        @Override public void run() {
            if (mediaPlayer != null && isPrepared) {
                int pos = mediaPlayer.getCurrentPosition();
                binding.seekBar.setProgress(pos);
                binding.textCurrentTime.setText(formatTime(pos));
                uiHandler.postDelayed(this, 500);
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlayerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.imageAlbum.setImageResource(R.drawable.music);
        binding.textTitle.setText("La bohème");

        initMediaPlayer();
        initButtons();
        initSeekBar();
    }

    @Override
    public void onPause() {
        super.onPause();
        pausePlayback();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
        uiHandler.removeCallbacksAndMessages(null);
        binding = null;
    }

    private void initMediaPlayer() {
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.la_boheme);

        if (mediaPlayer != null) {
            isPrepared = true;
            int duration = mediaPlayer.getDuration();

            binding.seekBar.setMax(duration);
            binding.textDuration.setText(formatTime(duration));
            binding.textCurrentTime.setText(formatTime(0));
        } else {
            isPrepared = false;
        }
    }

    private void initButtons() {
        binding.buttonPlayPause.setOnClickListener(v -> togglePlayback());
        binding.buttonPrev.setOnClickListener(v -> seekRelative(-10_000)); // −10 с
        binding.buttonNext.setOnClickListener(v -> seekRelative(+10_000)); // +10 с
    }

    private void initSeekBar() {
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar bar, int progress, boolean fromUser) {
                if (fromUser && isPrepared) mediaPlayer.seekTo(progress);
                if (fromUser) binding.textCurrentTime.setText(formatTime(progress));
            }
            @Override public void onStartTrackingTouch(SeekBar bar)  { }
            @Override public void onStopTrackingTouch(SeekBar bar)   { }
        });
    }

    private void togglePlayback() {
        if (!isPrepared) return;

        if (isPlaying) {
            pausePlayback();
        } else {
            startPlayback();
        }
    }

    private void startPlayback() {
        mediaPlayer.start();
        isPlaying = true;
        binding.buttonPlayPause.setImageResource(R.drawable.ic_pause);

        uiHandler.post(progressUpdater);
    }

    private void pausePlayback() {
        if (!isPlaying) return;
        mediaPlayer.pause();
        isPlaying = false;
        binding.buttonPlayPause.setImageResource(R.drawable.ic_play_arrow_24);
        uiHandler.removeCallbacks(progressUpdater);
    }

    private void seekRelative(int deltaMs) {
        if (!isPrepared) return;
        int newPos = mediaPlayer.getCurrentPosition() + deltaMs;
        newPos = Math.max(0, Math.min(newPos, mediaPlayer.getDuration()));
        mediaPlayer.seekTo(newPos);
        binding.seekBar.setProgress(newPos);
        binding.textCurrentTime.setText(formatTime(newPos));
    }

    private void releasePlayer() {
        if (mediaPlayer != null) {
            if (isPlaying) mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
            isPrepared  = false;
            isPlaying   = false;
        }
    }

    private static String formatTime(int millis) {
        int totalSec = millis / 1000;
        int min      = totalSec / 60;
        int sec      = totalSec % 60;
        return String.format(Locale.getDefault(), "%d:%02d", min, sec);
    }
}
