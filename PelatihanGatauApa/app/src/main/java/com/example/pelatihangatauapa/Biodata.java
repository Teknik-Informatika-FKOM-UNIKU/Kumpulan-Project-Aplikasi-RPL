package com.example.pelatihangatauapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private EditText editNim;
    private EditText editNama;
    private EditText editProdi;
    private EditText editTahun;
    private EditText editStatus;
    private RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);

        editNim = findViewById(R.id.et_nim);
        editNama = findViewById(R.id.et_nama);
        editProdi = findViewById(R.id.et_prodi);
        editTahun = findViewById(R.id.et_tahun);
        editStatus = findViewById(R.id.et_status);
        getData();
    }

    public void getData() {

        queue = Volley.newRequestQueue(Biodata.this);
        String url = "http://www.priludestudio.com/apps/pelatihan/Mahasiswa/data";
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("prilude");
                    JSONObject result2 = result.getJSONObject("data");
                    String status = result.getString("status");
                    String message = result.getString("message");
                    Log.d("percobaan", result2.getString("nim"));

                    if (status.equalsIgnoreCase("success")) {
                        editNim.setText(result2.getString("nim"));
                        editNama.setText(result2.getString("nama_mahasiswa"));
                        editProdi.setText(result2.getString("program_studi"));
                        editTahun.setText(result2.getString("tahun_masuk"));
                        editStatus.setText(result2.getString("status"));
                    } else {
                        Context context = getApplicationContext();
                        int duration = Toast.LENGTH_LONG;
                        Toast.makeText(context, message, duration).show();
                        Log.e("test",message);
                    }
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
