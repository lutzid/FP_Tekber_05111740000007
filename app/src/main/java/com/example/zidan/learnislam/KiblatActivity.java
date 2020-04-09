package com.example.zidan.learnislam;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

public class KiblatActivity extends AppCompatActivity implements SensorEventListener {

    ImageView kiblat_kompas;
    private static SensorManager sensorManager;
    private static Sensor sensor;
    private float currentDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiblat);

//        getSupportActionBar().setTitle("Arah Kiblat");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        kiblat_kompas = findViewById(R.id.kiblat_kompas);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        if(sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
        } else {
            Toast.makeText(getApplicationContext(), "Your Smartphone Is No Support", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onSensorChanged(SensorEvent event){
        int degree = Math.round(event.values[0]);
        RotateAnimation animation = new RotateAnimation(currentDegree, - degree, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        kiblat_kompas.setAnimation(animation);
        currentDegree = - degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }
}
