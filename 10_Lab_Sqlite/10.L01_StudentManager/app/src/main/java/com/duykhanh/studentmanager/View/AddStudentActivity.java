package com.duykhanh.studentmanager.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.duykhanh.studentmanager.Model.Student;
import com.duykhanh.studentmanager.R;
import com.duykhanh.studentmanager.dao.DAO_student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddStudentActivity extends AppCompatActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener{

    private EditText ed_code_student,
            ed_fullname,
            ed_birthday,
            ed_address;

    private String codeStudent,
            fullName,birthday,address;

    private ImageButton btn_add_birthday;
    private Button btn_add_student,
            btn_clear_student;

    // Khởi tạo biến cho spinner
    private Spinner spn_gender;
    /*
    * Tạo danh sách giới tính sinh viên
    */
    private List<String> listGender;
    /*
    * Biến lưu trữ khi người dùng chọn giới tính
    */
    private String mGender;
    /*
    * Biến lưu vị trí spinner người dùng chọn
    */
    private int mPositionSpinner;
    // Khởi tạo đối tượng định dạng ngày tháng
    private SimpleDateFormat sdf;
    // Khởi tạo đối tượng thao tác với CSDL
    private DAO_student daoStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Add student");

        initializationObject();

        initView();
        onClickView();
        initializationSpinnerGender();
    }

    /*
    * Khởi tạo các đối tượng cần thết
    */
    private void initializationObject(){
        daoStudent = new DAO_student(AddStudentActivity.this);
        sdf        = new SimpleDateFormat("dd-MM-yyyy");
        listGender = new ArrayList<>();
    }

    /*
    * Sự kiện click spinner
    */
    private void initializationSpinnerGender(){
        listGender.add("Nam");
        listGender.add("Nữ");
        listGender.add("Giới tính khác");

        ArrayAdapter<String> adapterSpiner = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listGender);
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_gender.setAdapter(adapterSpiner);
        spn_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mGender = spn_gender.getSelectedItem().toString();
                mPositionSpinner = i;
                Toast.makeText(AddStudentActivity.this, ""+i, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    /*
    * Ánh xạ view
    */
    private void initView() {
        ed_code_student     = findViewById(R.id.ed_code_student);
        ed_fullname         = findViewById(R.id.ed_fullname);
        ed_birthday         = findViewById(R.id.ed_birthday);
        ed_address          = findViewById(R.id.ed_address);
        spn_gender          = findViewById(R.id.spn_gender);

        btn_add_birthday    = findViewById(R.id.btn_add_birthday);

        btn_add_student     = findViewById(R.id.btnAddStudent);
        btn_clear_student   = findViewById(R.id.btnClearStudent);
    }

    /*
    * Bắt sự kiện click cho view
    */
    private void onClickView(){
        btn_add_birthday.setOnClickListener(this);
        btn_add_student.setOnClickListener(this);
        btn_clear_student.setOnClickListener(this);
    }

    /*
    * Ham lấy dữ liệu người dùng nhập
    */
    private void getDataInputStudent(){
        codeStudent = ed_code_student.getText().toString();
        fullName    = ed_fullname.getText().toString();
        birthday    = ed_birthday.getText().toString();
        address     = ed_address.getText().toString();
    }

    /*
    * Gán hành động cho các button view
    */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_birthday:
                showDatePickerDialog();
                Toast.makeText(this, "date picker", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnAddStudent:
                getDataInputStudent();
                insertNewStudent();
                break;
            case R.id.btnClearStudent:
                onClearText();
                break;
        }
    }

    /*
    *  TODO (CLEAR DATA) làm mới dữ liệu người dùng
    *  Làm mới dữ liệu trường văn bản người dùng nhật
    */
    private void onClearText(){
        ed_code_student.setText("");
        ed_fullname.setText("");
        ed_birthday.setText("");
        ed_address.setText("");
        spn_gender.setSelection(0);
    }

    /*
    * Sử lý sự kiện calendar
    */
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /*
     * Button quay về trang chủ
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    * Giá trị được trả về sau khi người dùng chọn calendar
    */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int moth, int dayOfMoth) {
        String brtd = dayOfMoth + "-" + (moth +1) + "-" + year;
        ed_birthday.setText(brtd);
    }

    // TODO (INSERT) thêm sinh viên
    /*
    * Hàm thêm sinh viên vào database
    */
    public void insertNewStudent(){
        if(checkValidationFrom() == true) {
            Student studentAdd = new Student();
            studentAdd.setCodeSV(codeStudent);
            studentAdd.setFullName(fullName);
            try {
                studentAdd.setBirthday(sdf.parse(birthday));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            studentAdd.setAddress(address);
            studentAdd.setGender(mGender);
            studentAdd.setPositionGender(mPositionSpinner);

            daoStudent.insertStudent(studentAdd);
        }
        else{

        }
    }

    //TODO (VALIDATION) Kiểm tra hợp lệ from nhập
    private boolean checkValidationFrom(){
        if(ed_code_student.equals("")){
            Toast.makeText(this, "Please input code student", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(ed_fullname.equals("")){
            Toast.makeText(this, "Please input name", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(ed_birthday.equals("")){
            Toast.makeText(this, "Please input birthday", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(ed_address.equals("")){
            Toast.makeText(this, "Please input address", Toast.LENGTH_SHORT).show();
            return false;
        }else
        return true;
    }
}
