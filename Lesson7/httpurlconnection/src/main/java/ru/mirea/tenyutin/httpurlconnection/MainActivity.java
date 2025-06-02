package ru.mirea.tenyutin.httpurlconnection;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.mirea.tenyutin.httpurlconnection.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(view -> {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                new DownloadIpInfoTask().execute("https://ipinfo.io/json");
            } else {
                Toast.makeText(this, "Нет интернета", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class DownloadIpInfoTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            try {
                String json = downloadUrl(urls[0]);
                return new JSONObject(json);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (result == null) return;

            try {
                String ip = result.getString("ip");
                String city = result.getString("city");
                String region = result.getString("region");
                String loc = result.getString("loc");

                binding.tvIP.setText("IP: " + ip);
                binding.tvCity.setText("Город: " + city);
                binding.tvRegion.setText("Регион: " + region);

                String[] coords = loc.split(",");
                String lat = coords[0];
                String lon = coords[1];

                new DownloadWeatherTask().execute("https://api.open-meteo.com/v1/forecast?latitude=" +
                        lat + "&longitude=" + lon + "&current_weather=true");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class DownloadWeatherTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            try {
                String json = downloadUrl(urls[0]);
                return new JSONObject(json);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            if (result == null) return;

            try {
                JSONObject current = result.getJSONObject("current_weather");
                String temp = current.getString("temperature");
                String wind = current.getString("windspeed");

                binding.tvWeather.setText("Температура: " + temp + "°C\nСкорость ветра: " + wind + " км/ч");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String downloadUrl(String address) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return conn.getResponseMessage() + " Error code: " + responseCode;
            }

            is = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int read;
            while ((read = is.read()) != -1) {
                out.write(read);
            }
            return out.toString();
        } finally {
            if (is != null) is.close();
        }
    }
}