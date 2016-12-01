package com.example.gd.ex8;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gd on 16/11/16.
 */
public class myDB extends SQLiteOpenHelper {

    private static final String DB_NAME = "Memorandum.db";
    private static final String TABLE_NAME = "info";
    private static final int DB_VERSION = 1;

    public myDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public myDB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists " + TABLE_NAME + " (_id INTEGER PRIMARY KEY, name TEXT, birth TEXT, gift TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insert(String name, String birth, String gift) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("birth", birth);
        cv.put("gift", gift);
        db.insert(TABLE_NAME, null, cv);
        db.close();
    }

    public void update(String name, String birth, String gift) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("birth", birth);
        cv.put("gift", gift);
        String whereClause = "name=?";
        String[] whereArgs = {name};
        db.update(TABLE_NAME, cv, whereClause, whereArgs);
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = "name=?";
        String[] whereArgs = {name};
        db.delete(TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public Cursor query() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }

    //通过名字查询是否已存在
    public boolean query(String name) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[] {"name"}, "name=?", new String[] {name}, null, null, null);
        if (cursor.getCount() > 0) return true;
        return false;
    }
}














