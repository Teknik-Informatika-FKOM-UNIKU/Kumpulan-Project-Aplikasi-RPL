package ac.id.uniku.trainingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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
import ac.id.uniku.trainingapp.model.Users;
import androidx.appcompat.app.AppCompatActivity;

public class Biodata extends AppCompatActivity {
    private DBUser dbUser;
    private Users user;
    private EditText etName;
    private EditText etNim;
    private EditText etProdi;
    private EditText etTahun;
    private EditText etStatus;
    private EditText etMasuk;
    private EditText etJenis;
    private EditText etJalur;
    private EditText etGelombang;
    private EditText etTanggal;

//    private String nama="";
//    private String nim="";
//    private String prodi="";
//    private String tahun="";
//    private String status="";
//    private String semester="";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);

        etName = findViewById(R.id.etName);
        etNim = findViewById(R.id.etNim);
        etProdi = findViewById(R.id.etProdi);
        etTahun = findViewById(R.id.etTahun);
        etStatus = findViewById(R.id.etStatus);
        etMasuk = findViewById(R.id.etMasuk);
        etJenis = findViewById(R.id.etJenis);
        etJalur = findViewById(R.id.etJalur);
        etGelombang = findViewById(R.id.etGelombang);
        etTanggal = findViewById(R.id.etTanggal);

//        dbUser = new DBUser(this);
//        if (dbUser.isNull()){
//            Log.d("test", "DB is Empty!");
//        } else {
//            user = dbUser.findUser();
//            nama = user.getNama();
//            nim = user.getNim();
//            prodi = user.getProdi();
//            tahun = user.getTahun();
//            status = user.getStatus();
//            semester = user.getSemester();
//        }

//        etName.setText(nama);
//        etNim.setText(nim);
//        etProdi.setText(prodi);
//        etTahun.setText(tahun);
//        etSemester.setText(semester);
//        etStatus.setText(status);
        loadData();
    }

    public void loadData(){
        RequestQueue queue = Volley.newRequestQueue(Biodata.this);
        String url = "http://www.priludestudio.com/apps/pelatihan/Mahasiswa/data";
        Log.d("test","Data Mahasiswa");

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                url, response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject result = jsonObject.getJSONObject("prilude");
                        String status = result.getString("status");
                        String message = result.getString("message");

                        if (status.equalsIgnoreCase("success")) {
                            JSONObject result_two = result.getJSONObject("data");
                            etNim.setText(result_two.getString("nim"));
                            etName.setText(result_two.getString("nama_mahasiswa"));
                            etProdi.setText(result_two.getString("program_studi"));
                            etTahun.setText(result_two.getString("tahun_masuk"));
                            etStatus.setText(result_two.getString("status"));
                            etMasuk.setText(result_two.getString("periode_masuk"));
                            etJenis.setText(result_two.getString("jenis_pendaftaran"));
                            etJalur.setText(result_two.getString("jalur_pendaftaran"));
                            etGelombang.setText(result_two.getString("gelombang"));
                            etTanggal.setText(result_two.getString("tanggal_masuk"));

                            Toast.makeText(getApplicationContext(), "Load data success!", Toast.LENGTH_SHORT).show();
                            Log.d("test", message);
                        } else {
                            Toast.makeText(getApplicationContext(), "Login data failed!", Toast.LENGTH_SHORT).show();
                            Log.e("test", message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("nim", "02183207001");
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