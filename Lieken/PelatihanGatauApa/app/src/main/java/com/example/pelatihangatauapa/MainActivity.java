package com.example.pelatihangatauapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private DBPengguna dbPengguna;
    private Pengguna pengguna;
    private EditText etUsername;
    private String username="";
    private EditText etNim;
    private EditText etPassword;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbPengguna = new DBPengguna(this);
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

//        btnLogin = findViewById(R.id.btn_login);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, Biodata.class);
//                startActivity(i);
//            }
//        });

        setLayout();
    }
        public void setLayout()
        { etNim =(EditText)findViewById(R.id.etNIM);
            etPassword=(EditText)findViewById(R.id.etPassword);

            btnLogin = (Button)findViewById(R.id.btn_login);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    login();
                }
            });

        }
    public void login()
    {
        queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://www.priludestudio.com/apps/pelatihan/Mahasiswa/login";
        Log.d("test","haaa");

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url,new Response.Listener<String>() {
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
                        Log.d("test","berhasil");
                        Context context = getApplicationContext();
                        CharSequence text = "Succesfully Login";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    } else {
                        Log.e("test","error");
                        Context context = getApplicationContext();
                        CharSequence text = "Failed Login";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
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

