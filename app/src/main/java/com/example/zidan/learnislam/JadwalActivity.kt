package com.example.zidan.learnislam;

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.*
import com.example.zidan.learnislam.model.ClientAsyncTask
import com.example.zidan.learnislam.model.DaftarKota
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_jadwal.*
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class JadwalActivity : AppCompatActivity() {

    private var listDaftarKota: MutableList<DaftarKota>? = null
    private var mDaftarKotaAdapter: ArrayAdapter<DaftarKota>? = null
    private var subuh = false
    private var dhuhur = false
    private var ashar = false
    private var maghrib = false
    private var isya = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)

        listDaftarKota = ArrayList()
        mDaftarKotaAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listDaftarKota)
        mDaftarKotaAdapter!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kota.adapter = mDaftarKotaAdapter
        kota.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {

                val kota = mDaftarKotaAdapter!!.getItem(position)
                loadJadwal(kota.id)
            }

        }
        val refresh_btn = findViewById(R.id.fab_refresh) as FloatingActionButton
        refresh_btn.setOnClickListener {
            loadKota()
        }
        val alarm_subuh_btn = findViewById(R.id.alarm_subuh) as Button
        alarm_subuh_btn.setOnClickListener {
            if(subuh){
                subuh = false;
                alarm_subuh_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp)
            }
            else{
                subuh = true;
                alarm_subuh_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp)
            }
        }
        val alarm_dhuhur_btn = findViewById(R.id.alarm_dhuhur) as Button
        alarm_dhuhur_btn.setOnClickListener {
            if(dhuhur){
                dhuhur = false;
                alarm_dhuhur_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp)
            }
            else{
                dhuhur = true;
                alarm_dhuhur_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp)
            }
        }
        val alarm_ashar_btn = findViewById(R.id.alarm_ashar) as Button
        alarm_ashar_btn.setOnClickListener {
            if(ashar){
                ashar = false;
                alarm_ashar_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp)
            }
            else{
                ashar = true;
                alarm_ashar_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp)
            }
        }
        val alarm_maghrib_btn = findViewById(R.id.alarm_maghrib) as Button
        alarm_maghrib_btn.setOnClickListener {
            if(maghrib){
                maghrib = false;
                alarm_maghrib_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp)
            }
            else{
                maghrib = true;
                alarm_maghrib_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp)
            }
        }
        val alarm_isya_btn = findViewById(R.id.alarm_isya) as Button
        alarm_isya_btn.setOnClickListener {
            if(isya){
                isya = false;
                alarm_isya_btn.setBackgroundResource(R.drawable.baseline_alarm_off_black_18dp)
            }
            else{
                isya = true;
                alarm_isya_btn.setBackgroundResource(R.drawable.baseline_alarm_on_black_18dp)
            }
        }
        loadKota()

    }

    @SuppressLint("SimpleDateFormat")
    private fun loadJadwal(id: Int?) {
        try {
            val idKota = id.toString()

            val current = SimpleDateFormat("yyyy-MM-dd")
            val tanggal = current.format(Date())

            val url = "https://api.banghasan.com/sholat/format/json/jadwal/kota/$idKota/tanggal/$tanggal"
            val task = ClientAsyncTask(this, object : ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {

                    Log.d("JadwalData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val objJadwal = jsonObj.getJSONObject("jadwal")
                        val obData = objJadwal.getJSONObject("data")

                        tv_lokasi_value.text = obData.getString("tanggal")
                        tv_subuh_value.text = obData.getString("subuh")
                        tv_dhuhur_value.text = obData.getString("dzuhur")
                        tv_ashar_value.text = obData.getString("ashar")
                        tv_maghrib_value.text = obData.getString("maghrib")
                        tv_isya_value.text = obData.getString("isya")

                        Log.d("dataJadwal", obData.toString())

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
            task.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun loadKota() {
        try {
            val url = "https://api.banghasan.com/sholat/format/json/kota"
            val task = ClientAsyncTask(this, object : ClientAsyncTask.OnPostExecuteListener {
                override fun onPostExecute(result: String) {

                    Log.d("KotaData", result)
                    try {
                        val jsonObj = JSONObject(result)
                        val jsonArray = jsonObj.getJSONArray("kota")
                        var daftarKota: DaftarKota?
                        for (i in 0 until jsonArray.length()) {
                            val obj = jsonArray.getJSONObject(i)
                            daftarKota = DaftarKota()
                            daftarKota.id = obj.getInt("id")
                            daftarKota.nama = obj.getString("nama")
                            listDaftarKota!!.add(daftarKota)
                        }
                        mDaftarKotaAdapter!!.notifyDataSetChanged()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            })
            task.execute(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
