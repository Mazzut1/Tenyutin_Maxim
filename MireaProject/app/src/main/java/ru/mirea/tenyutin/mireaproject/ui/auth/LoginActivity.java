package ru.mirea.tenyutin.mireaproject.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.tenyutin.mireaproject.MainActivity;
import ru.mirea.tenyutin.mireaproject.R;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailInput, passInput;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput  = findViewById(R.id.emailInput);
        passInput   = findViewById(R.id.passInput);
        Button btn  = findViewById(R.id.btnLogin);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.GONE);

        addContentView(progressBar,
                new android.widget.FrameLayout.LayoutParams(
                        android.widget.FrameLayout.LayoutParams.WRAP_CONTENT,
                        android.widget.FrameLayout.LayoutParams.WRAP_CONTENT));

        mAuth = FirebaseAuth.getInstance();

        FirebaseUser current = mAuth.getCurrentUser();
        if (current != null) {
            openMain();
            return;
        }

        btn.setOnClickListener(v -> doAuth());
    }

    private void doAuth() {
        String email = emailInput.getText().toString().trim();
        String pass  = passInput.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Введите e-mail и пароль", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(r -> openMain())
                .addOnFailureListener(e ->
                        mAuth.createUserWithEmailAndPassword(email, pass)
                                .addOnSuccessListener(r -> openMain())
                                .addOnFailureListener(err -> {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(this, "Ошибка: "+err.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                }));
    }

    private void openMain() {
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}