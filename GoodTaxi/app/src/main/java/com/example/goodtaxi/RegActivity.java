package com.example.goodtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity {
    EditText mailET, passwordET, repeatPasswortET;
    CheckBox term;
    private static DatabaseHelper databaseHelper;

    // Паттерн для регуляоных выражений для проверки почты/Валидации
    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mailET = findViewById(R.id.mailRegBox);
        passwordET = findViewById(R.id.passwordRegBox);
        repeatPasswortET = findViewById(R.id.repeatPasswordRegBox);
    }

    // Валидация полей и создание аккаунта
    public void signUpButton(View view) {
        String email = mailET.getText().toString();
        String password = passwordET.getText().toString();
        String passwordRepeat = repeatPasswortET.getText().toString();

        boolean validation = true;
        while(validation)
        {
            if (email.equals(""))
            {
                Toast t = Toast.makeText(this, "Поле почта пусто", Toast.LENGTH_LONG);
                t.show();
                break;
            }
            if (password.equals("") || passwordRepeat.equals(""))
            {
                Toast t = Toast.makeText(this, "Поля паролей не заполненны", Toast.LENGTH_LONG);
                t.show();
                break;
            }
            if (!password.equals(passwordRepeat))
            {
                Toast t = Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_LONG);
                t.show();
                break;
            }
            if (!emailValidate(email))
            {
                Toast t = Toast.makeText(this, "Почта введена некорректно", Toast.LENGTH_LONG);
                t.show();
                break;
            }
            if (createAccount(password, email, getApplicationContext()))
            {
                Toast t = Toast.makeText(this, "Успешно", Toast.LENGTH_LONG);
                t.show();

            }
            else
            {
                Toast t = Toast.makeText(this, "Что-то пошло не так", Toast.LENGTH_LONG);
                t.show();
                break;
            }
            Intent i = new Intent(RegActivity.this, MainActivity.class);
            startActivity(i);
            break;
        }
    }

    public static boolean emailValidate(String login)
    {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(login);
        return matcher.matches();
    }

    public static boolean createAccount(String password, String email, Context context) {
        databaseHelper = new DatabaseHelper(context);
        if (isRegUnique(email, databaseHelper)) {
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.COLUMN_USER_PASSWORD, password);
            values.put(DatabaseHelper.COLUMN_USER_EMAIL, email);
            db.insert(DatabaseHelper.TABLE_USERS, null, values);
            databaseHelper.close();
            return true;
        } else {
            return false;
        }

    }
    public static boolean isRegUnique(String email, DatabaseHelper databaseHelper) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor usersCursor = db.query(DatabaseHelper.TABLE_USERS, null, null, null, null, null, null);
        while (usersCursor.moveToNext()) {
            int emailIndex = usersCursor.getColumnIndex(DatabaseHelper.COLUMN_USER_EMAIL);
            String emailDb = usersCursor.getString(emailIndex);
            if (emailDb.equals(email)) {
                usersCursor.close();
                db.close();
                return false;
            }
        }
        usersCursor.close();
        db.close();
        return true;
    }
}