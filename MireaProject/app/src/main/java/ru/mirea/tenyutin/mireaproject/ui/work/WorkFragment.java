package ru.mirea.tenyutin.mireaproject.ui.work;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import ru.mirea.tenyutin.mireaproject.R;
import ru.mirea.tenyutin.mireaproject.work.MyWorker;

public class WorkFragment extends Fragment {

    private TextView statusTextView;
    private Button btnStart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_work, container, false);

        statusTextView = view.findViewById(R.id.tvStatus);
        btnStart = view.findViewById(R.id.btnStartWork);

        btnStart.setOnClickListener(v -> {
            OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(MyWorker.class).build();
            WorkManager.getInstance(requireContext()).enqueue(request);

            WorkManager.getInstance(requireContext())
                    .getWorkInfoByIdLiveData(request.getId())
                    .observe(getViewLifecycleOwner(), workInfo -> {
                        if (workInfo != null) {
                            statusTextView.setText("Статус: " + workInfo.getState().name());
                        }
                    });
        });

        return view;
    }
}