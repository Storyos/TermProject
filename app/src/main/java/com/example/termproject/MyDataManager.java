package com.example.termproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.daum.mf.map.api.MapPOIItem;

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
    public boolean loginUser(String userId, String password)
    {
        String[] columns = {"userId", "password"};
        String selection = "userId = ? AND password = ?";
        String[] selectionArgs = {userId, password};

        Cursor cursor = database.query("users",columns,selection,selectionArgs,null,null,null);

        boolean loginSuccess = cursor.getCount() > 0;
        cursor.close();
        return loginSuccess;
    }
    public void insertData(String nickname, String userId, String password) {
        ContentValues values = new ContentValues();
        values.put("nickname",nickname);
        values.put("userId",userId);
        values.put("password",password);

        database.insert("users", null, values);
    }
    public void saveMarker(String userId, double latitude, double longitude, String title) {
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("latitude", latitude);
        values.put("longitude", longitude);
        values.put("title", title);

        database.insert("Markers", null, values);
        database.close();
    }
    public Cursor loadMarkers(String userId) {
        String[] columns = {"userId","latitude", "longitude", "title"};
        String selection = "userId = ?";
        String[] selectionArgs = {userId};

        return database.query("markers", columns, selection, selectionArgs, null, null, null);
    }
}
