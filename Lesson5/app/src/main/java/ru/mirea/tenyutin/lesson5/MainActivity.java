package ru.mirea.tenyutin.lesson5;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ru.mirea.tenyutin.lesson5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SensorManager sensorManager =
                (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        ListView listView = binding.listView;

        ArrayList<HashMap<String, Object>> data = new ArrayList<>();
        for (Sensor sensor : sensors) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("Name", sensor.getName());
            map.put("Value", sensor.getMaximumRange());
            data.add(map);
        }

        SimpleAdapter adapter = new SimpleAdapter(
                this,
                data,
                android.R.layout.simple_list_item_2,
                new String[] { "Name", "Value" },
                new int[] { android.R.id.text1, android.R.id.text2 }
        );

        listView.setAdapter(adapter);
    }
}