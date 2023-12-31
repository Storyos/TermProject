package com.example.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "MyDatabase";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 데이터베이스 테이블 생성 쿼리 실행
        String createTableQuery = "CREATE TABLE users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, nickname TEXT,"
                + "userId TEXT, password TEXT)";
        db.execSQL(createTableQuery);

        String createTableQuery1 = "CREATE TABLE MARKERS ("
                +"id INTEGER PRIMARY KEY AUTOINCREMENT, userId TEXT, latitude REAL," +
                "longitude REAL, title TEXT)";
        db.execSQL(createTableQuery1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 데이터베이스 업그레이드 시 수행할 작업
        // (예: 테이블 재생성 또는 업그레이드)
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS MARKERS");
        onCreate(db);
    }
}
