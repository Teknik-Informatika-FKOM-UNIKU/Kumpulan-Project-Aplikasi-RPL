package ac.id.uniku.trainingapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import ac.id.uniku.trainingapp.database.DBUser;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etPassword;
    private EditText etUsername;
    private RelativeLayout loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBUser dbUser = new DBUser(this);
        if (dbUser.isNull()){
            Log.d("test", "DB is Empty!");
        } else {
//            Users user = dbUser.findUser();
//            int idUser = user.getUserId();
//            String username = user.getUsername();
//            String password = user.getPassword();
            Log.d("test", "DB is Already!");
        }

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v -> {
            if(v.getId() == R.id.btn_login){
                login();
            }
        });

        loading = findViewById(R.id.progressBar);

        etUsername = findViewById(R.id.ed_username);
//        etUsername.setText(username);
        etPassword = findViewById(R.id.ed_password);
//        etPassword.setText(password);
    }

    public void login(){
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://www.priludestudio.com/apps/pelatihan/Mahasiswa/login";
        Log.d("test","Login");
        loading.setVisibility(View.VISIBLE);

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url, response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject result = jsonObject.getJSONObject("prilude");
                        String status = result.getString("status");
                        String message = result.getString("message");

                        if (status.equalsIgnoreCase("success")) {
                            loading.setVisibility(View.GONE);
                            Intent intent=new Intent(MainActivity.this,Biodata.class);
                            startActivity(intent);

                            Toast.makeText(getApplicationContext(), "Login Success!", Toast.LENGTH_SHORT).show();
                            Log.d("test", message);
                        } else {
                            loading.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_SHORT).show();
                            Log.e("test", message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("nim", etUsername.getText().toString());
                params.put("password", etPassword.getText().toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
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