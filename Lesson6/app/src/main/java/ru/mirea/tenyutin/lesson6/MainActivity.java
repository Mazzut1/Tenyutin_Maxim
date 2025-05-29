package ru.mirea.tenyutin.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "mirea_settings";
    private EditText etGroup;
    private EditText etNumber;
    private EditText etFavorite;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etGroup    = findViewById(R.id.etGroup);
        etNumber   = findViewById(R.id.etNumber);
        etFavorite = findViewById(R.id.etFavorite);
        btnSave    = findViewById(R.id.btnSave);

        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        String savedGroup    = prefs.getString("GROUP", "");
        int    savedNumber   = prefs.getInt("NUMBER", 0);
        String savedFavorite = prefs.getString("FAVORITE", "");

        etGroup.setText(savedGroup);
        if (savedNumber != 0) {
            etNumber.setText(String.valueOf(savedNumber));
        }
        etFavorite.setText(savedFavorite);

        btnSave.setOnClickListener(view -> {
            String groupText    = etGroup.getText().toString();
            String numberText   = etNumber.getText().toString();
            String favoriteText = etFavorite.getText().toString();

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("GROUP", groupText);
            if (!TextUtils.isEmpty(numberText)) {
                try {
                    int num = Integer.parseInt(numberText);
                    editor.putInt("NUMBER", num);
                } catch (NumberFormatException e) {
                    editor.putInt("NUMBER", 0);
                }
            } else {
                editor.putInt("NUMBER", 0);
            }
            editor.putString("FAVORITE", favoriteText);
            editor.apply();
        });
    }
}