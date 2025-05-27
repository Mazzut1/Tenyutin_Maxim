package ru.mirea.tenyutin.toastapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private static final String STUDENT_INFO = "СТУДЕНТ № 24 ГРУППА БСБО-09-22";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editText);
    }

    public void onShowToast(View view) {
        String text = editText.getText().toString();
        int length = text.length();
        String message = STUDENT_INFO + " Количество символов - " + length;

        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        toast.show();
    }
}