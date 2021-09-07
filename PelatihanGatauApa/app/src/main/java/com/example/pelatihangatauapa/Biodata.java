package com.example.pelatihangatauapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Biodata extends AppCompatActivity {

    private DBPengguna dbPengguna;
    private Pengguna pengguna;
    private TextView tv_nim;
    private TextView tv_nama;
    private TextView tv_prodi;
    private TextView tv_tahun;
    private TextView tv_staus;
    private TextView tv_gelombang;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);

        tv_nim = findViewById(R.id.textView);
        tv_nama = findViewById(R.id.textView2);
        tv_prodi = findViewById(R.id.textView3);
        tv_tahun = findViewById(R.id.textView4);
        tv_staus = findViewById(R.id.textView5);
        tv_gelombang = findViewById(R.id.textView6);

        //dbPengguna = new DBPengguna(this);
//        if (dbPengguna.isNull()) {
//            Log.d("Test", "DB Kosong");
//        }
//        else {
//            pengguna = dbPengguna.findUser();
//            Log.d("Test", pengguna.getNim());
//            Log.d("Test", pengguna.getNama_nama());
//            Log.d("Test", pengguna.getProgramstudi());
//            Log.d("Test", pengguna.getTahunmasuk());
//            Log.d("Test", pengguna.getStatus());
//            Log.d("Test", pengguna.getSemester());
//        }
//
//        pengguna = dbPengguna.findUser();
//        tv_nim.setText(pengguna.getNim());
//        tv_nama.setText(pengguna.getNama_nama());
//        tv_prodi.setText(pengguna.getProgramstudi());
//        tv_tahun.setText(pengguna.getTahunmasuk());
//        tv_staus.setText(pengguna.getStatus());
//        tv_semester.setText(pengguna.getSemester());
//    }

        queue = Volley.newRequestQueue(Biodata.this);
        String url = "https://www.priludestudio.com/apps/pelatihan/Mahasiswa/data";
        Log.d("test", "haaa");

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("prilude");
                    JSONObject result2 = result.getJSONObject("data");
                    String status = result.getString("status");
                    String message = result.getString("message");


                    tv_nim.setText(result2.getString("nim"));
                    tv_nama.setText(result2.getString("nama_mahasiswa"));
                    tv_prodi.setText(result2.getString("program_studi"));
                    tv_tahun.setText(result2.getString("tahun_masuk"));
                    tv_staus.setText(result2.getString("status"));
                    tv_gelombang.setText(result2.getString("gelombang"));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("nim", "02183207001");

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        final int DEFAULT_MAX_RETRIES = 1;
        final float DEFAULT_BACKOFF_MULT = 1f;
        jsonObjReq.setRetryPolicy(
                new DefaultRetryPolicy(
                        (int) TimeUnit.SECONDS.toMillis(20),
                        DEFAULT_MAX_RETRIES,
                        DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);
    }
}
