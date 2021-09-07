package ac.id.uniku.projectsatu;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class BiodataActivity extends AppCompatActivity {
    private TextView tvName;
    private TextView tvNim;
    private TextView tvProdi;
    private TextView tvTahun;
    private TextView tvStatus;
    private TextView tvJenisPendaftaran;
    private TextView tvJalurPendaftaran;
    private TextView tvGelombang;
    private TextView tvTanggalMasuk;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biodata);
        tvNim = findViewById(R.id.nim);
        tvName = findViewById(R.id.name);
        tvProdi = findViewById(R.id.prodi);
        tvTahun = findViewById(R.id.tahun);
        tvStatus = findViewById(R.id.status);
        tvJenisPendaftaran = findViewById(R.id.jenis_pendaftaran);
        tvJalurPendaftaran = findViewById(R.id.jalur_pendaftaran);
        tvGelombang = findViewById(R.id.gelombang);
        tvTanggalMasuk = findViewById(R.id.tanggal);
        ambilData();
    }
    public void ambilData(){
        queue = Volley.newRequestQueue(BiodataActivity.this);
        String url = "https://www.priludestudio.com/apps/pelatihan/Mahasiswa/data";
        Log.d("Test", "login: ");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject result = jsonObject.getJSONObject("prilude");
                JSONObject data = result.getJSONObject("data");
                String status = result.getString("status");
                String message = result.getString("message");
                if (status.equalsIgnoreCase("success")) {
                    Log.d("test", "Berhasil");
                    tvNim.setText(data.getString("nim"));
                    tvName.setText(data.getString("nama_mahasiswa"));
                    tvProdi.setText(data.getString("program_studi"));
                    tvTahun.setText(data.getString("tahun_masuk"));
                    tvStatus.setText(data.getString("status"));
                    tvJenisPendaftaran.setText(data.getString("jenis_pendaftaran"));
                    tvJalurPendaftaran.setText(data.getString("jalur_pendaftaran"));
                    tvGelombang.setText(data.getString("gelombang"));
                    tvTanggalMasuk.setText(data.getString("tanggal_masuk"));
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
                params.put("nim", "02183207001");
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
                new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(0),DEFAULT_MAX_RETRIES,DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjReq);
    }
}
