package ru.mirea.tenyutin.employeedb;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SuperheroDao dao;
    private EditText etName, etPower;
    private TextView tvList;
    private Button btnAdd, btnLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao     = App.getInstance().getDatabase().superheroDao();
        etName  = findViewById(R.id.etName);
        etPower = findViewById(R.id.etPower);
        tvList  = findViewById(R.id.tvList);
        btnAdd  = findViewById(R.id.btnAdd);
        btnLoad = findViewById(R.id.btnLoad);

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String power = etPower.getText().toString().trim();
            if (name.isEmpty() || power.isEmpty()) {
                Toast.makeText(this, "Введите имя и способность", Toast.LENGTH_SHORT).show();
                return;
            }
            dao.insert(new Superhero(name, power));
            Toast.makeText(this, "Герой добавлен", Toast.LENGTH_SHORT).show();
            etName.setText("");
            etPower.setText("");
        });

        btnLoad.setOnClickListener(v -> {
            List<Superhero> list = dao.getAll();
            if (list.isEmpty()) {
                tvList.setText("База пуста");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Superhero h : list) {
                    sb.append(h.id)
                            .append(". ")
                            .append(h.name)
                            .append(" — ")
                            .append(h.superpower)
                            .append("\n");
                }
                tvList.setText(sb.toString());
            }
        });
    }
}