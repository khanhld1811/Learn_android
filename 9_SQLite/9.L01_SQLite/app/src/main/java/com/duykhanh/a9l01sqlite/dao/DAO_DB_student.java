package com.duykhanh.a9l01sqlite.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.duykhanh.a9l01sqlite.data.DatabaseHandler;
import com.duykhanh.a9l01sqlite.model.Student;

import java.util.ArrayList;
import java.util.List;

public class DAO_DB_student {
    private DatabaseHandler dbHandler;
    private SQLiteDatabase database;
    Student student;
    Context context;

    public DAO_DB_student(Context context) {
        this.context = context;
        /*
         * Khởi tạo một đối tượng DatabaseHandler
         */
        student = new Student();
        dbHandler = new DatabaseHandler(context);
        /*
         *
         */
        database = dbHandler.getWritableDatabase();
    }

    // Hàm dùng để thêm dữ liệu vào database
    public void addStudent(Student student) {
        /*
         * Mở DB để them student
         */

        ContentValues values = new ContentValues();
        Log.d("ID", "addStudent: " + values);
        values.put(DatabaseHandler.KEY_NAME, student.getName());
        values.put(DatabaseHandler.KEY_ADDRESS, student.getAddress());
        values.put(DatabaseHandler.KEY_PHONE_NUMBER, student.getPhone_number());

        /*
         * add dữ liệu vào CSDL
         */

        if (database.insert(DatabaseHandler.TABLE_NAME, null, values) == -1) {
            Toast.makeText(context, "Add data failed!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Add data completed!", Toast.LENGTH_SHORT).show();
        }

        /*
         * Sau khi thêm xong tiến hành đóng DB
         */
    }

    public List<Student> getAllStudent() {
        List<Student> listStudent = new ArrayList<>();
        String query = "SELECT * FROM " + DatabaseHandler.TABLE_NAME;

        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {

            student.setId(Integer.parseInt(cursor.getString(0)));
            student.setName(cursor.getString(1));
            student.setAddress(cursor.getString(2));
            student.setPhone_number(cursor.getString(3));
            listStudent.add(student);
            cursor.moveToNext();
        }
        cursor.close();
        return listStudent;
    }

    public void deleteStudent(int studentId) {
        database = dbHandler.getWritableDatabase();
        Log.d("student", "deleteStudent: " + studentId);
        int result = database.delete(DatabaseHandler.TABLE_NAME, DatabaseHandler.KEY_ID + "=?", new String[]{String.valueOf(studentId)});
        if (result == 0) {
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete completed!", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateStudent(Student student) {
     ContentValues values = new ContentValues();
        Log.d("AA", "updateStudent: "+student.getName());

     values.put(DatabaseHandler.KEY_NAME,student.getName());
     values.put(DatabaseHandler.KEY_ADDRESS,student.getAddress());
     values.put(DatabaseHandler.KEY_PHONE_NUMBER,student.getPhone_number());

     int restultUpdate = database.update(DatabaseHandler.TABLE_NAME,values,DatabaseHandler.KEY_ID + "=?",new String[]{String.valueOf(student.getId())});
     if(restultUpdate == 0){
         Toast.makeText(context, "Update failed", Toast.LENGTH_SHORT).show();
     }else{
         Toast.makeText(context, "Update completed!", Toast.LENGTH_SHORT).show();
     }
    }
}
