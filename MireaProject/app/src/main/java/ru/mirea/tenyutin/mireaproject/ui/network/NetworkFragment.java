package ru.mirea.tenyutin.mireaproject.ui.network;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mirea.tenyutin.mireaproject.R;

public class NetworkFragment extends Fragment {

    private TextView factTxt;

    @Override
    public View onCreateView(@NonNull LayoutInflater inf, ViewGroup cont, Bundle st) {
        View v = inf.inflate(R.layout.fragment_network, cont, false);
        factTxt = v.findViewById(R.id.quoteText);
        loadFact();
        return v;
    }

    private void loadFact() {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://catfact.ninja")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CatApi api = retrofit.create(CatApi.class);
        api.getFact().enqueue(new Callback<CatFact>() {
            @Override public void onResponse(Call<CatFact> call, Response<CatFact> res) {
                if (res.isSuccessful() && res.body() != null) {
                    factTxt.setText(res.body().fact);
                } else {
                    factTxt.setText("Ошибка сервера: " + res.code());
                }
            }

            @Override public void onFailure(Call<CatFact> call, Throwable t) {
                factTxt.setText("Сетевая ошибка: " + t.getMessage());
            }
        });
    }
}
