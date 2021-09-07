package com.example.Biodata;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pelatihangatauapa.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private DBPengguna dbPengguna;
    private Pengguna pengguna;
    private RequestQueue queue;
    private EditText etNim;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etNim = findViewById(R.id.et_nim);
        etPassword = findViewById(R.id.et_password);
    }

    public void registrasi(View view) {
        //Intent i = new Intent(MainActivity.this, Register.class);
        //startActivity(i);
    }


    public void login(View view) {

        queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://www.priludestudio.com/apps/pelatihan/Mahasiswa/login";

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, url,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject result = jsonObject.getJSONObject("prilude");
                    String status = result.getString("status");
                    String message = result.getString("message");

                    if (status.equalsIgnoreCase("success")) {
                        Intent intent=new Intent(MainActivity.this,Biodata.class);
                        startActivity(intent);
                        Log.d("test",message);
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
                params.put("nim", etNim.getText().toString());
                params.put("password", etPassword.getText().toString());
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