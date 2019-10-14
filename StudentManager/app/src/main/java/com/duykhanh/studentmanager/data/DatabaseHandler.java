package com.duykhanh.studentmanager.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "studentManager";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "students";
    public static final String KEY_ID = "idSV";
    public static final String KEY_CODESV = "codeSV";
    public static final String KEY_FULLNAME = "fullName";
    public static final String KEY_BIRTHDAY = "birthday";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_POSITION_GENDER = "positionGender";

    public DatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table_student = "CREATE TABLE \"students\" (\n" +
                "\t\"idSV\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"codeSV\"\tTEXT,\n" +
                "\t\"fullName\"\tTEXT,\n" +
                "\t\"birthday\"\tREAL,\n" +
                "\t\"address\"\tTEXT,\n" +
                "\t\"gender\"\tTEXT,\n" +
                "\t\"positionGender\"\tTEXT\n" +
                ");";
        sqLiteDatabase.execSQL(create_table_student);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String drop_students_table = "DROP TABLE IF EXISTS " + TABLE_NAME;
        sqLiteDatabase.execSQL(drop_students_table);

        onCreate(sqLiteDatabase);
    }
}
