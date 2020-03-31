package com.example.zidan.learnislam;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button jadwalintent;
    Button kiblatintent;
    Button sholatintent;
    Button wudhuintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jadwalintent = findViewById(R.id.jadwal);
        jadwalintent.setOnClickListener(this);
        kiblatintent = findViewById(R.id.kiblat);
        kiblatintent.setOnClickListener(this);
        sholatintent = findViewById(R.id.sholat);
        sholatintent.setOnClickListener(this);
        wudhuintent = findViewById(R.id.wudhu);
        wudhuintent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jadwal:
                startActivity(new Intent(this, JadwalActivity.class));
                break;
            case R.id.kiblat:
                startActivity(new Intent(this, KiblatActivity.class));
                break;
            case R.id.sholat:
                startActivity(new Intent(this, SholatActivity.class));
                break;
            case R.id.wudhu:
                startActivity(new Intent(this, WudhuActivity.class));
                break;
            default:
                break;
        }
    }
}
