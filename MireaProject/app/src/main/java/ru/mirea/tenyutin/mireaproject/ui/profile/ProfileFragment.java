package ru.mirea.tenyutin.mireaproject.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.tenyutin.mireaproject.R;

public class ProfileFragment extends Fragment {
    private EditText etName, etAge;
    private Button btnSave;
    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        etName = view.findViewById(R.id.etName);
        etAge = view.findViewById(R.id.etAge);
        btnSave = view.findViewById(R.id.btnSave);

        sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);

        // Загрузка
        etName.setText(sharedPreferences.getString("name", ""));
        etAge.setText(sharedPreferences.getString("age", ""));

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name", etName.getText().toString());
            editor.putString("age", etAge.getText().toString());
            editor.apply();
            Toast.makeText(getContext(), "Сохранено", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}