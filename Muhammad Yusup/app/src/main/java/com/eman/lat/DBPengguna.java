package com.eman.lat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


public class DBPengguna extends SQLiteAssetHelper {
    private static final String DATABASE_NAME="testdb.sqlite";
    private static final int DATABASE_VERSION=1;
    private static final String TABLE_NAME="pengguna";
    private static final String KEY_ID="id_pengguna";
    private static final String KEY_USERNAME="username";

    public DBPengguna(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public boolean isNull()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String count = "SELECT count(*) FROM "+TABLE_NAME+"";
        Cursor mcursor = db.rawQuery(count,null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        db.close();
        return  icount <= 0;
    }

    public void save(Pengguna pengguna)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID,pengguna.getId_pengguna());
        values.put(KEY_USERNAME,pengguna.getUsername());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Pengguna findPengguna()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,new String[]{KEY_ID,KEY_USERNAME},null,null,null,null,null);

        Pengguna p = new Pengguna();
        if(cursor!=null && cursor.moveToFirst())
        {
            cursor.moveToFirst();
            p.setId_pengguna(cursor.getInt(cursor.getColumnIndex("id_pengguna")));
            p.setUsername(cursor.getString(cursor.getColumnIndex("username")));
        }else
        {
            p.setId_pengguna(0);
            p.setUsername("");
        }

        db.close();
        return p;
    }
    public void delete()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
    public void update(Pengguna pengguna){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_ID,pengguna.getId_pengguna());
        values.put(KEY_ID,pengguna.getUsername());
        db.update(TABLE_NAME,values, KEY_ID+"=", new String[] {String.valueOf(pengguna.getId_pengguna())});
        db.close();
    }
}
