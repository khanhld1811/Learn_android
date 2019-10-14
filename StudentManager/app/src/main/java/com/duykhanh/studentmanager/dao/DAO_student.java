package com.duykhanh.studentmanager.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.duykhanh.studentmanager.MainActivity;
import com.duykhanh.studentmanager.Model.Student;
import com.duykhanh.studentmanager.data.DatabaseHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DAO_student {

    private DatabaseHandler dbHandler;
    private SQLiteDatabase db;
    private Context context;


    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public DAO_student(Context context){
        this.context = context;
        dbHandler = new DatabaseHandler(context);
        db = dbHandler.getWritableDatabase();
    }

    public void insertStudent(Student student){
        ContentValues values = new ContentValues();
        values.put(DatabaseHandler.KEY_CODESV,student.getCodeSV());
        values.put(DatabaseHandler.KEY_FULLNAME,student.getFullName());
        values.put(DatabaseHandler.KEY_BIRTHDAY, sdf.format(student.getBirthday()));
        values.put(DatabaseHandler.KEY_ADDRESS,student.getAddress());
        values.put(DatabaseHandler.KEY_GENDER,student.getGender());
        values.put(DatabaseHandler.KEY_POSITION_GENDER,student.getPositionGender());

        long resultInsert = db.insert(DatabaseHandler.TABLE_NAME,null,values);

        if(resultInsert == -1){
            Toast.makeText(context, "Insert student failed!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Insert student completed!", Toast.LENGTH_SHORT).show();
            Intent iMain = new Intent(context, MainActivity.class);
            iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(iMain);
        }
        db.close();
    }

    public List<Student> getAllStudent() {
        db = dbHandler.getWritableDatabase();
        List<Student> listStudent = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast() == false){
            Student student = new Student();
            student.setIdSV(Integer.parseInt(cursor.getString(0)));
            student.setCodeSV(cursor.getString(1));
            student.setFullName(cursor.getString(2));
            try {
                student.setBirthday(sdf.parse(cursor.getString(3)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            student.setAddress(cursor.getString(4));
            student.setGender(cursor.getString(5));
            student.setPositionGender(Integer.parseInt(cursor.getString(6)));
            listStudent.add(student);
            cursor.moveToNext();
        }
        cursor.close();
        return listStudent;

    }

    public void updateStudent(Student student){
        db = dbHandler.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseHandler.KEY_CODESV,student.getCodeSV());
        values.put(DatabaseHandler.KEY_FULLNAME,student.getFullName());
        values.put(DatabaseHandler.KEY_BIRTHDAY,sdf.format(student.getBirthday()));
        values.put(DatabaseHandler.KEY_ADDRESS,student.getAddress());
        values.put(DatabaseHandler.KEY_GENDER,student.getGender());
        values.put(DatabaseHandler.KEY_POSITION_GENDER,student.getPositionGender());

        int resultUpate = db.update(DatabaseHandler.TABLE_NAME,values,DatabaseHandler.KEY_ID + "=?",new String[]{String.valueOf(student.getIdSV())});
        if(resultUpate == 0){
            Toast.makeText(context, "Upate failed!", Toast.LENGTH_SHORT).show();
        }
        else{
            Intent iMain = new Intent(context,MainActivity.class);
            iMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(iMain);
            Toast.makeText(context, "Update completed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteStudent(int studentId){
        db = dbHandler.getWritableDatabase();
        int resultDelete = db.delete(DatabaseHandler.TABLE_NAME,DatabaseHandler.KEY_ID + "=?",new String[]{String.valueOf(studentId)});
        if(resultDelete == 0){
            Toast.makeText(context, "Delete student failed!", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Delte student complted!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}
