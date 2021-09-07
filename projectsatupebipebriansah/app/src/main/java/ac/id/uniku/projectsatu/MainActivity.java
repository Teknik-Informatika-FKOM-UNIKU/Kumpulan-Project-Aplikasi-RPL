package ac.id.uniku.projectsatu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
public class MainActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText etPassword;
    private EditText etUsername;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayout();
    }

    public void setLayout()
    {
        etUsername = findViewById(R.id.et_username);
        etPassword= findViewById(R.id.et_password);


        btnLogin = findViewById(R.id.btn_Login);

        btnLogin.setOnClickListener(v -> login());
    }
    public void login(){
        queue = Volley.newRequestQueue(MainActivity.this);
        String url = "https://www.priludestudio.com/apps/pelatihan/Mahasiswa/login";
        Log.d("Test", "login: ");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject result = jsonObject.getJSONObject("prilude");
                String status = result.getString("status");
                String message = result.getString("message");
                if (status.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(MainActivity.this, BiodataActivity.class);
                    startActivity(intent);
                    Log.d("test", "Berhasil");
                } else {
                    Log.e("test", "error");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nim", etUsername.getText().toString());
                params.put("password", etPassword.getText().toString());
                return params;
            }
            @Override
            public Map<String,String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }
        };
        final int DEFAULT_MAX_RETRIES = 1;
        final float DEFAULT_BACKOFF_MULT=1f;
        jsonObjReq.setRetryPolicy(
                new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(20),DEFAULT_MAX_RETRIES,DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);
    }
}
