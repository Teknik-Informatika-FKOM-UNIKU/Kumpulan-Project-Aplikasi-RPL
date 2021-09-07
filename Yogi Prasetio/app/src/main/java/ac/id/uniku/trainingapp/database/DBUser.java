package ac.id.uniku.trainingapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import ac.id.uniku.trainingapp.model.Users;

public class DBUser extends SQLiteAssetHelper {

    private static final String DATABASE_NAME="db_uniku.sqlite";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="users";
    private static final String KEY_ID="id_pengguna";
    private static final String KEY_USERNAME="username";
    private static final String KEY_PASSWORD="password";
    private static final String KEY_NIM="nim";
    private static final String KEY_NAMA="nama";
    private static final String KEY_PRODI="prodi";
    private static final String KEY_TAHUN="tahun";
    private static final String KEY_STATUS="status";
    private static final String KEY_SEMESTER="semester";

    public DBUser(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //untuk cek apakah data ada isinya atau tidak
    public boolean isNull()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM "+TABLE_NAME+"";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        db.close();
        return icount <= 0;
    }

    //untuk mendapatkan data
    public Users findUser(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_ID,KEY_USERNAME,KEY_PASSWORD,KEY_NIM,KEY_NAMA,KEY_PRODI,KEY_TAHUN,KEY_STATUS,KEY_SEMESTER},null,null,null,null,null);

        Users u=new Users();
        if (cursor!=null && cursor.moveToFirst()) {
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
        } else {
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

    //untuk simpan data
    public void save(Users user)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID, user.getUserId());
        values.put(KEY_USERNAME,user.getUsername());
        values.put(KEY_PASSWORD,user.getUsername());
        values.put(KEY_NIM,user.getUsername());
        values.put(KEY_NAMA,user.getUsername());
        values.put(KEY_PRODI,user.getUsername());
        values.put(KEY_TAHUN,user.getUsername());
        values.put(KEY_STATUS,user.getUsername());
        values.put(KEY_SEMESTER,user.getUsername());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //untuk bersihkan atau hapus semua data
    public void delete()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
    public void update(Users user){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID,user.getUserId());
        values.put(KEY_USERNAME,user.getUsername());
        values.put(KEY_PASSWORD,user.getUsername());
        values.put(KEY_NIM,user.getUsername());
        values.put(KEY_NAMA,user.getUsername());
        values.put(KEY_PRODI,user.getUsername());
        values.put(KEY_TAHUN,user.getUsername());
        values.put(KEY_STATUS,user.getUsername());
        values.put(KEY_SEMESTER,user.getUsername());
        db.update(TABLE_NAME, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getUserId()) });
        db.close();
    }
}
