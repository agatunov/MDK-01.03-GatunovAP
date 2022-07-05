package com.example.goodtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static DatabaseHelper databaseHelper; // Экземпляр бд
    EditText loginET, passwordET; // Поля с формы
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        loginET = findViewById(R.id.loginBox);
        passwordET = findViewById(R.id.passwordBox);
    }

    // Переход на окно рег.
    public void regButtonOnClick(View view) {
        Intent i = new Intent(MainActivity.this, RegActivity.class);
        startActivity(i);
    }

    //Вход через БД
    public void signInOnClick(View view) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        String email = loginET.getText().toString();
        String password = passwordET.getText().toString();
        boolean check = false;
        Cursor cursor = database.query("Users", null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL);
            int passwordIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USER_PASSWORD);
            String emailDb = cursor.getString(emailIndex);
            String passwordDb = cursor.getString(passwordIndex);
            if (email.equals(emailDb) && password.equals(passwordDb)) {
                databaseHelper.close();
              Intent i = new Intent(MainActivity.this, StartScreen.class);
              startActivity(i);
              check = true;
            }
        }
        if (!check) {
            Toast toast = Toast.makeText(getApplicationContext(), "Пользователь не найден", Toast.LENGTH_LONG);
            toast.show();
        }
        cursor.close();
        databaseHelper.close();
    }

}