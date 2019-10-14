package com.duykhanh.studentmanager.View;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
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

public class DetailStudentActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = DetailStudentActivity.class.getSimpleName();
    private EditText ed_codeStudentDetail,
            ed_nameStudentDetail,
            ed_birthdayDetail,
            ed_addressDetail;
    private ImageButton imgAddBirthdayDetail;
    private Button btnUpdateStudent, btnClearStudent;
    private Student student;

    private Spinner spn_genderDetail;
    /*
     * Tạo danh sách giới tính sinh viên
     */
    private List<String> listGende;
    /*
     * Biến lưu trữ khi người dùng chọn giới tính
     */
    private String mGenderDetail;
    /*
     * Biến lưu vị trí spinner người dùng chọn
     */
    private int mPositionSpinnerDetail;

    private DAO_student daoStudent;

    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_student);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Detail Student");

        initializationObject();
        initView();
        setOnClickView();
        setInfomationStudent();
        initializationSpinnerGender();

    }

    //TODO (1) Khởi tạo object
    private void initializationObject(){
        student = new Student();
        daoStudent = new DAO_student(this);
        listGende = new ArrayList<>();
        Intent intent = getIntent();
        student = (Student) intent.getSerializableExtra("InfoStudent");
    }

    //TODO (2) Ánh xạ view
    private void initView() {
        ed_codeStudentDetail    = findViewById(R.id.ed_code_studentDetail);
        ed_nameStudentDetail    = findViewById(R.id.ed_fullnameDetail);
        ed_birthdayDetail       = findViewById(R.id.ed_birthdayDetail);
        ed_addressDetail        = findViewById(R.id.ed_addressDetail);
        spn_genderDetail        = findViewById(R.id.spn_genderDetail);

        imgAddBirthdayDetail             = findViewById(R.id.btn_add_birthdayDetail);

        btnUpdateStudent        = findViewById(R.id.btnUpdateStudentDetail);
        btnClearStudent         = findViewById(R.id.btnClearStudentDetail);
    }

    //TODO(3) Bắt sự kiện click vào view
    private void setOnClickView(){
        imgAddBirthdayDetail.setOnClickListener(this);
        btnUpdateStudent.setOnClickListener(this);
        btnClearStudent.setOnClickListener(this);

    }

    //TODO (4) Show thông tin chi tiết student
    private void setInfomationStudent(){
        ed_codeStudentDetail.setText(student.getCodeSV());
        ed_nameStudentDetail.setText(student.getFullName());
        ed_birthdayDetail.setText(sdf.format(student.getBirthday()));
        ed_addressDetail.setText(student.getAddress());
    }

    //TODO (5) Sự kiện click spinner
    private void initializationSpinnerGender(){
        listGende.add("Nam");
        listGende.add("Nữ");
        listGende.add("Giới tính khác");

        ArrayAdapter<String> adapterSpiner = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,listGende);
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_genderDetail.setAdapter(adapterSpiner);
        spn_genderDetail.setSelection(student.getPositionGender());
        spn_genderDetail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mGenderDetail = spn_genderDetail.getSelectedItem().toString();
                mPositionSpinnerDetail = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //TODO (6) Gán hành động cho button
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_birthdayDetail:
                showDatePickerDialog();
                break;
            case R.id.btnUpdateStudentDetail:
                updateInfomationStudent();
                break;
            case R.id.btnClearStudentDetail:
                onClearText();
                break;
        }
    }

     //TODO (CLEAR DATA) làm mới dữ liệu người dùng
    private void onClearText(){
        ed_codeStudentDetail.setText("");
        ed_nameStudentDetail.setText("");
        ed_birthdayDetail.setText("");
        ed_addressDetail.setText("");
        spn_genderDetail.setSelection(0);
    }

    //TODO (7) Button quay về trang chủ
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

    //TODO (8) Cập nhật thông tin sinh viên
    private void updateInfomationStudent(){

        String code_studentDetail = ed_codeStudentDetail.getText().toString();
        String name_studentDetail = ed_nameStudentDetail.getText().toString();
        String birthday_studentDetail = ed_birthdayDetail.getText().toString();
        String address_studentDeatil = ed_addressDetail.getText().toString();

        Student studentUpdate = new Student();

        studentUpdate.setIdSV(student.getIdSV());
        studentUpdate.setCodeSV(code_studentDetail);
        studentUpdate.setFullName(name_studentDetail);
        try {
            studentUpdate.setBirthday(sdf.parse(birthday_studentDetail));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        studentUpdate.setAddress(address_studentDeatil);
        studentUpdate.setGender(mGenderDetail);
        studentUpdate.setPositionGender(mPositionSpinnerDetail);

        daoStudent.updateStudent(studentUpdate);
    }

    //TODO (9) Sử lý sự kiện calendar
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int moth, int dayOfMoth) {
        String brthd = dayOfMoth + "-" + (moth +1)  + "-" + year;
        ed_birthdayDetail.setText(brthd);
    }
}
