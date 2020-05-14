package com.example.zidan.learnislam;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zidan.learnislam.api.ApiService;
import com.example.zidan.learnislam.api.ApiUrl;
import com.example.zidan.learnislam.model.Jadwal;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JadwalActivity extends AppCompatActivity {

    private TextView tv_lokasi_value, tv_fajr_value, tv_dhuhr_value,
            tv_asr_value, tv_maghrib_value, tv_isha_value;
    private FloatingActionButton fab_refresh;
    private ProgressDialog progressDialog;
    private String city;
    private Button alarm_subuh_btn, alarm_dhuhur_btn, alarm_ashar_btn, alarm_maghrib_btn, alarm_isya_btn;
    boolean subuh = false, dhuhur = false, ashar = false, maghrib = false, isya = false;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);

//        getSupportActionBar().setTitle("Jadwal Sholat");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_lokasi_value = findViewById(R.id.tv_lokasi_value);
        tv_fajr_value = findViewById(R.id.tv_fajr_value);
        tv_dhuhr_value = findViewById(R.id.tv_dhuhr_value);
        tv_asr_value = findViewById(R.id.tv_asr_value);
        tv_maghrib_value = findViewById(R.id.tv_maghrib_value);
        tv_isha_value = findViewById(R.id.tv_isha_value);
        fab_refresh = findViewById(R.id.fab_refresh);

        FusedLocationProviderClient mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocation.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null){
                    city = currentLocation(location.getLatitude(), location.getLongitude());
                    getJadwal(city);
                }
            }
        });


        fab_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJadwal(city);
            }
        });

        alarm_subuh_btn = findViewById(R.id.alarm_subuh);
        alarm_subuh_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(subuh) {
                    subuh = false;
                    alarm_subuh_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp);
                }
                else {
                    subuh = true;
                    alarm_subuh_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp);
                }
            }
        });
        alarm_dhuhur_btn = findViewById(R.id.alarm_dhuhur);
        alarm_dhuhur_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dhuhur) {
                    dhuhur = false;
                    alarm_dhuhur_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp);
                }
                else {
                    dhuhur = true;
                    alarm_dhuhur_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp);
                }
            }
        });
        alarm_ashar_btn = findViewById(R.id.alarm_ashar);
        alarm_ashar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ashar) {
                    ashar = false;
                    alarm_ashar_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp);
                }
                else {
                    ashar = true;
                    alarm_ashar_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp);
                }
            }
        });
        alarm_maghrib_btn = findViewById(R.id.alarm_maghrib);
        alarm_maghrib_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(maghrib) {
                    maghrib = false;
                    alarm_maghrib_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp);
                }
                else {
                    maghrib = true;
                    alarm_maghrib_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp);
                }
            }
        });
        alarm_isya_btn = findViewById(R.id.alarm_isya);
        alarm_isya_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isya) {
                    isya = false;
                    alarm_isya_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp);
                }
                else {
                    isya = true;
                    alarm_isya_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp);
                }
            }
        });
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public String currentLocation(double lat, double lon){
        String currentCity = "";

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(lat, lon, 10);
            if(addresses.size() > 0){
                currentCity = addresses.get(0).getLocality();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return currentCity;
    }

    private void getJadwal (String city) {

        progressDialog = new ProgressDialog(JadwalActivity.this);
        progressDialog.setMessage("Silahkan tunggu ...");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.URL_ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<Jadwal> call = apiService.getJadwal(city);

        call.enqueue(new Callback<Jadwal>() {
            @Override
            public void onResponse(Call<Jadwal> call, Response<Jadwal> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    tv_lokasi_value.setText(response.body().getQuery()+", "+response.body().getItems().get(0).getDateFor());
                    tv_fajr_value.setText(response.body().getItems().get(0).getFajr());
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
