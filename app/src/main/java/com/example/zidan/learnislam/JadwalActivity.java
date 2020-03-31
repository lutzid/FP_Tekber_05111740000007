package com.example.zidan.learnislam;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zidan.learnislam.api.ApiService;
import com.example.zidan.learnislam.api.ApiUrl;
import com.example.zidan.learnislam.model.Jadwal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JadwalActivity extends AppCompatActivity {

    private TextView tv_lokasi_value, tv_fajr_value, tv_shurooq_value,
            tv_dhuhr_value, tv_asr_value, tv_maghrib_value, tv_isha_value;
    private FloatingActionButton fab_refresh;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

        getSupportActionBar().setTitle("Jadwal Sholat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_lokasi_value = findViewById(R.id.tv_lokasi_value);
        tv_fajr_value = findViewById(R.id.tv_fajr_value);
        tv_shurooq_value = findViewById(R.id.tv_shurooq_value);
        tv_dhuhr_value = findViewById(R.id.tv_dhuhr_value);
        tv_asr_value = findViewById(R.id.tv_asr_value);
        tv_maghrib_value = findViewById(R.id.tv_maghrib_value);
        tv_isha_value = findViewById(R.id.tv_isha_value);
        fab_refresh = findViewById(R.id.fab_refresh);

        getJadwal();

        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJadwal();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void getJadwal () {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Jadwal> call = apiService.getJadwal("Solo");

        call.enqueue(new Callback<Jadwal>() {
            @Override
            public void onResponse(Call<Jadwal> call, Response<Jadwal> response) {
                if (response.isSuccessful()) {
                    tv_lokasi_value.setText(response.body().getCity()+", "+response.body().getItems().get(0).getDateFor());
                    tv_fajr_value.setText(response.body().getItems().get(0).getFajr());
                    tv_shurooq_value.setText(response.body().getItems().get(0).getShurooq());
                    tv_dhuhr_value.setText(response.body().getItems().get(0).getDhuhr());
                    tv_asr_value.setText(response.body().getItems().get(0).getAsr());
                    tv_maghrib_value.setText(response.body().getItems().get(0).getMaghrib());
                    tv_isha_value.setText(response.body().getItems().get(0).getIsha());
                } else {
                    Toast.makeText(JadwalActivity.this, "Sorry, please try again...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Jadwal> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(JadwalActivity.this, "Sorry, please try again... server Down..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
