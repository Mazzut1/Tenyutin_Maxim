package ru.mirea.tenyutin.multiactivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate()");
        editText = findViewById(R.id.editText);
    }

    public void onClickNewActivity(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("key", editText.getText().toString());
        startActivity(intent);
    }

    @Override protected void onStart() { super.onStart(); Log.i(TAG, "onStart()"); }
    @Override protected void onResume() { super.onResume(); Log.i(TAG, "onResume()"); }
    @Override protected void onPause() { super.onPause(); Log.i(TAG, "onPause()"); }
    @Override protected void onStop() { super.onStop(); Log.i(TAG, "onStop()"); }
    @Override protected void onRestart() { super.onRestart(); Log.i(TAG, "onRestart()"); }
    @Override protected void onDestroy() { super.onDestroy(); Log.i(TAG, "onDestroy()"); }
}