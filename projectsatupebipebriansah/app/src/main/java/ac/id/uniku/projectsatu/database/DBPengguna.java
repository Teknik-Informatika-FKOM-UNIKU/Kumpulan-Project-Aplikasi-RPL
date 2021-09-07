package ac.id.uniku.projectsatu.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import ac.id.uniku.projectsatu.model.Pengguna;

public class DBPengguna extends SQLiteAssetHelper{
    private static final String DATABASE_NAME = "project_baru.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "pengguna";
    private static final String KEY_ID = "id_pengguna";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NIM="nim";
    private static final String KEY_NAMA="nama";
    private static final String KEY_PRODI="prodi";
    private static final String KEY_TAHUN="tahun";
    private static final String KEY_STATUS="status";
    private static final String KEY_SEMESTER="semester";
    public DBPengguna(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public boolean isNull(){
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count (*) FROM"+TABLE_NAME+"";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        db.close();
        return  icount <= 0 ;
    }
    public Pengguna findUser(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_NIM,KEY_NAMA,KEY_PRODI,KEY_TAHUN,KEY_STATUS,KEY_SEMESTER}, null,null,null,null,null);
        Pengguna u = new Pengguna();
        if(cursor!=null && cursor.moveToFirst()){
            cursor.moveToFirst();
            u.setUserId(cursor.getInt(cursor.getColumnIndex("id_pengguna")));
            u.setUsername(cursor.getString(cursor.getColumnIndex("username")));
            u.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            u.setNim(cursor.getString(cursor.getColumnIndex("nim")));
            u.setNama(cursor.getString(cursor.getColumnIndex("nama")));
            u.setProdi(cursor.getString(cursor.getColumnIndex("prodi")));
            u.setTahun(cursor.getString(cursor.getColumnIndex("tahun")));
            u.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            u.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
        }else{
            u.setUserId(0);
            u.setUsername("");
            u.setPassword("");
            u.setNim("");
            u.setNama("");
            u.setProdi("");
            u.setTahun("");
            u.setStatus("");
            u.setSemester("");
        }
        db.close();
        return u;
    }
}
