package ru.mirea.tenyutin.lesson4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.tenyutin.lesson4.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}