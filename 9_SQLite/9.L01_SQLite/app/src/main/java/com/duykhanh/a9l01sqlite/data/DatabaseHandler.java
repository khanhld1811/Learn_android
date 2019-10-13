package com.duykhanh.a9l01sqlite.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "schoolManager";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "students";

    public static final String KEY_ID = "id";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_PHONE_NUMBER = "phone_number";


    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_student_table = "CREATE TABLE \"students\" (\n" +
                "\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"name\"\tTEXT,\n" +
                "\t\"address\"\tTEXT,\n" +
                "\t\"phone_number\"\tTEXT\n" +
                ");";
        /*
        * Phương thức thực hiện một câu lệnh SQL
        */
        db.execSQL(create_student_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String drop_students_table = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(drop_students_table);

        onCreate(db);
    }
}
