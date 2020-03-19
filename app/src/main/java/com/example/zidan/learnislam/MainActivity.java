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
        jadwalintent = (Button)findViewById(R.id.jadwal);
        jadwalintent.setOnClickListener(this);
        kiblatintent = (Button)findViewById(R.id.kiblat);
        kiblatintent.setOnClickListener(this);
        sholatintent = (Button)findViewById(R.id.sholat);
        sholatintent.setOnClickListener(this);
        wudhuintent = (Button)findViewById(R.id.wudhu);
        wudhuintent.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.jadwal:
                Intent jadwal = new Intent(this, JadwalActivity.class);
                startActivity(jadwal);
                break;
            case R.id.kiblat:
                Intent kiblat = new Intent(this, KiblatActivity.class);
                startActivity(kiblat);
                break;
            case R.id.sholat:
                Intent sholat = new Intent(this, SholatActivity.class);
                startActivity(sholat);
                break;
            case R.id.wudhu:
                Intent wudhu = new Intent(this, WudhuActivity.class);
                startActivity(wudhu);
                break;
            default:
                break;
        }
    }
}
