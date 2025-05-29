package ru.mirea.tenyutin.mireaproject.ui.file;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.io.File;
import java.io.IOException;
import ru.mirea.tenyutin.mireaproject.R;

public class FileFragment extends Fragment {
    private FloatingActionButton fab;
    private TextView tvFiles;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        fab = view.findViewById(R.id.fab);
        tvFiles = view.findViewById(R.id.tvFiles);

        File[] files = requireContext().getFilesDir().listFiles();
        StringBuilder sb = new StringBuilder();
        for (File file : files) {
            sb.append(file.getName()).append("\n");
        }
        tvFiles.setText(sb.toString());

        fab.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            EditText input = new EditText(getContext());
            input.setHint("Имя файла");

            builder.setView(input)
                    .setTitle("Создание файла")
                    .setPositiveButton("Создать", (dialog, which) -> {
                        String fileName = input.getText().toString();
                        try {
                            File file = new File(requireContext().getFilesDir(), fileName + ".txt");
                            if (file.createNewFile()) {
                                tvFiles.append(file.getName() + "\n");
                                Toast.makeText(getContext(), "Файл создан", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Файл уже существует", Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton("Отмена", null)
                    .show();
        });

        return view;
    }
}