package com.example.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDataManager {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public MyDataManager(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void insertData(String username, String password) {
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password",password);

        database.insert("users", null, values);
    }

}
