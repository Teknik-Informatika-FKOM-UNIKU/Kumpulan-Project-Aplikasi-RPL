package com.example.pelatihangatauapa;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBPengguna extends SQLiteAssetHelper {

    private static final String DATABASE_NAME="db_baru.sqlite";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="tb_user";
    private static final String KEY_NAMA="nama";
    private static final String KEY_NIM="nim";
    private static final String KEY_PRODI="prodi";
    private static final String KEY_TAHUN="tahun";
    private static final String KEY_STATUS="status";
    private static final String KEY_SEMESTER="semester";



    public DBPengguna(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

    public Pengguna findUser()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_NIM,KEY_NAMA,KEY_PRODI,KEY_TAHUN,KEY_STATUS,KEY_SEMESTER},null,null,null,null,null);

        Pengguna u = new Pengguna();
        if (cursor!=null && cursor.moveToFirst())
        {
            cursor.moveToFirst();
            u.setNim(cursor.getString(cursor.getColumnIndex("nim")));
            u.setNama_nama(cursor.getString(cursor.getColumnIndex("nama")));
            u.setProgramstudi(cursor.getString(cursor.getColumnIndex("prodi")));
            u.setTahunmasuk(cursor.getString(cursor.getColumnIndex("tahun")));
            u.setStatus(cursor.getString(cursor.getColumnIndex("status")));
            u.setSemester(cursor.getString(cursor.getColumnIndex("semester")));
        }else
        {
            u.setNim("");
            u.setNama_nama("");
            u.setProgramstudi("");
            u.setTahunmasuk("");
            u.setStatus("");
            u.setSemester("");
        }

        db.close();
        return u;
    }
}
