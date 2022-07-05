package com.example.goodtaxi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Класс БД
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "good_taxi.db";
    private static final int SCHEMA = 1;

    // Таблицы
    // Users
    public static final String TABLE_USERS = "Users";
    public static final String COLUMN_USER_ID = "UserID";
    public static final String COLUMN_USER_PASSWORD = "Password";
    public static final String COLUMN_USER_EMAIL = "Email";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    //Создание через запрос
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_USER_EMAIL + " TEXT NOT NULL UNIQUE," +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL);"
        );
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
}
