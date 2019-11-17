package com.duykhanh.employeemanager.view.addEmployee;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.duykhanh.employeemanager.R;
import com.duykhanh.employeemanager.model.Employee;
import com.duykhanh.employeemanager.presenter.addemployee.AddEmployeePresenter;
import com.duykhanh.employeemanager.presenter.addemployee.AddEmployeePresenterListener;
import com.duykhanh.employeemanager.view.addEmployee.image.ImagePickerActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.duykhanh.employeemanager.utils.Constants.*;

public class AddEmployeeActivity extends AppCompatActivity implements View.OnClickListener, AddEmployeeViewListener{

    View mLayoutAddEmployee;

    ImageView imgAddEmployee;
    ImageView imgEmployee;

    TextInputEditText edCodeEmployee,
            edFullname,
            edBirthday,
            edAddress,
            edGender,
            edPhoneNumber,
            edEmail,
            edSalary,
            edPosition;
    Button btnAddEmployee,
            btnClearInfoEmployee;

    String codeEmployee,fullname, birthday, address, gender, phonenumber, email, salary, position;

    private static final int REQUEST_IMAGE = 1;

    private AddEmployeePresenterListener presenter;

    String realPath = "";

    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        setTitle("Add employee");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Khởi tạo thành phần giao diện
        initUI();

        // Đăng ký sự kiện lắng nghe các hành động củ view
        registerListener();


        //Load ảnh mặc định
        loadProfileDefault();
    }

    private void initUI() {
        mLayoutAddEmployee = findViewById(R.id.include_layout_add_employee);

        imgAddEmployee  = mLayoutAddEmployee.findViewById(R.id.image_employee_config_add_photo);
        imgEmployee     = mLayoutAddEmployee.findViewById(R.id.image_employee_config_item_photo);

        edCodeEmployee  = mLayoutAddEmployee.findViewById(R.id.edCodeEmployee);
        edFullname      = mLayoutAddEmployee.findViewById(R.id.edFullname);
        edBirthday      = mLayoutAddEmployee.findViewById(R.id.edBirthDay);
        edAddress       = mLayoutAddEmployee.findViewById(R.id.edAddress);
        edGender        = mLayoutAddEmployee.findViewById(R.id.edGender);
        edPhoneNumber   = mLayoutAddEmployee.findViewById(R.id.edPhone);
        edEmail         = mLayoutAddEmployee.findViewById(R.id.edEmail);
        edSalary        = mLayoutAddEmployee.findViewById(R.id.edSalary);
        edPosition      = mLayoutAddEmployee.findViewById(R.id.edPosition);

        btnAddEmployee  = findViewById(R.id.btnAddEmployee);
        btnClearInfoEmployee = findViewById(R.id.btnClearEmployee);

        // Khởi tạo presenter
        presenter = new AddEmployeePresenter(this);

    }

    private void registerListener(){

        imgAddEmployee.setOnClickListener(this);

        btnAddEmployee.setOnClickListener(this);
        btnClearInfoEmployee.setOnClickListener(this);

        edBirthday.setOnClickListener(this);
        edGender.setOnClickListener(this);
    }

    private void loadProfileDefault() {
        Glide.with(this).load(R.drawable.noneimg).into(imgEmployee);
    }

    // TODO Phương thức chọn ảnh
    public void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {

            @Override
            public void onCameraSelected() {
                launchCamera();
            }

            @Override
            public void onGallerySelected() {
                launchGallery();
            }
        });
    }

    // TODO Chọn ảnh từ camera
    public void launchCamera() {
        Intent intent = new Intent(AddEmployeeActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.REQUEST_CODE_TYPE, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // TODO Gán tỉ lệ khóa là 1x1
        intent.putExtra(ImagePickerActivity.EXTRA_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.EXTRA_ASPECT_RATIO_X, 1);
        intent.putExtra(ImagePickerActivity.EXTRA_ASPECT_RATIO_Y, 1);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    //TODO Chọn ảnh từ thư viện
    private void launchGallery() {
        Intent intent = new Intent(AddEmployeeActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.REQUEST_CODE_TYPE, ImagePickerActivity.REQUEST_IMAGE_GALLERY);

        //TODO Gắn kích thước tối đa cho ảnh khi hiển thị
        intent.putExtra(ImagePickerActivity.EXTRA_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.EXTRA_BITMAP_MAX_WIDTH, 480);
        intent.putExtra(ImagePickerActivity.EXTRA_BITMAP_MAX_HEIGHT, 640);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void loadImageProfile(String url) {
        Glide.with(this).load(url)
                .into(imgEmployee);
        imgEmployee.setColorFilter(ContextCompat.getColor(this, android.R.color.transparent));
    }

    //TODO trả về hình ảnh sau khi đã cắt
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("checkActivityResult", "onActivityResult: " + requestCode);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                /*
                * Gọi hàm xử lý đường dẫn và truyền kết quả vào biến realPath
                */
                realPath = getRealPathFromURI(uri);
                Log.d("URI", "onActivityResult: " + uri);
                try {
                    InputStream inputStream = getContentResolver().openInputStream(uri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    imgEmployee.setImageBitmap(bitmap);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //TODO Nút back về
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image_employee_config_add_photo:
                showImagePickerOptions();
                break;
            case R.id.edBirthDay:
                dialogCalender();
                break;
            case R.id.edGender:
                popupMenuChooseGender();
                break;
            case R.id.btnAddEmployee:
                getDataForm();
                sendDataEmployee();
                break;
            case R.id.btnClearEmployee:
                clearFormInfomationEmployee();
                break;
        }
    }

    // TODO Làm mới thông tin trên form
    private void clearFormInfomationEmployee(){
        edCodeEmployee.setText("");
        edFullname.setText("");
        edBirthday.setText("");
        edAddress.setText("");
        edGender.setText("");
        edPhoneNumber.setText("");
        edEmail.setText("");
        edSalary.setText("");
        edPosition.setText("");
    }

    // TODO Hiển thị dialog cho phép chọn ngày giờ
    private void dialogCalender() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edBirthday.setText(i2 + "-" + (i1 + 1) + "-" + i);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.show();
    }

    // TODO hiển thị menu chọn giới tính
    private void popupMenuChooseGender(){

        PopupMenu popGender = new PopupMenu(AddEmployeeActivity.this,edGender);

        popGender.getMenuInflater().inflate(R.menu.menu_gender,popGender.getMenu());

        popGender.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                edGender.setText(menuItem.getTitle());
                return true;
            }
        });
        popGender.show();
    }

    //TODO Lấy dữ liệu người dùng nhập
    private void getDataForm(){
        codeEmployee = edCodeEmployee.getText().toString();
        fullname = edFullname.getText().toString();
        birthday = edBirthday.getText().toString();
        address = edAddress.getText().toString();
        gender = edGender.getText().toString();
        phonenumber = edPhoneNumber.getText().toString();
        email = edEmail.getText().toString();
        salary = edSalary.getText().toString();
        position = edPosition.getText().toString();
    }

    //TODO Gửi dữ liệu người dùng nhập lên server
    private void sendDataEmployee(){
        Employee employee = new Employee();
        employee.setEplCode(codeEmployee);
        employee.setFullName(fullname);
        employee.setDateOfBirth(birthday);
        employee.setAddress(address);
        employee.setGender(gender);
        employee.setPhoneNumber(phonenumber);
        employee.setEmail(email);
        employee.setSalary(salary);
        employee.setPosition(position);

        /*
        * Kiểm tra ảnh đã được chọn
        * Nếu có -> cho phép gửi dữ liệu lên server.
        * Nếu không -> thông báo cho người dùng chọn ảnh
        */
        if(realPath.equals("")){
            Toast.makeText(this, "Please choose image", Toast.LENGTH_SHORT).show();
        }
        else {
            presenter.requestDataFormServer(employee);
        }
    }

    //TODO Lấy đường dẫn thực tế tù Uri
    public String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null,
                null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            try {
                int idx = cursor
                        .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            } catch (Exception e) {
                Toast.makeText(this,
                        "error", Toast.LENGTH_SHORT).show();

                result = "";
            }
            cursor.close();
        }
        return result;
    }

    // TODO Xử lý đường dẫn ảnh và gửi lên server
    private void getRealPath(){
            File file = new File(realPath);
            String file_path = file.getAbsolutePath();
            /*
            * Thay đổi tên hình ảnh mỗi lần gửi lên server
            */
            String[] arrayNameFile = file_path.split("\\.");
            file_path = arrayNameFile[0] + "." + arrayNameFile[1] + "." + arrayNameFile[2] + System.currentTimeMillis() + "." + arrayNameFile[3];

            /*
            * Khai báo kiểu dữ liệu và đường dẫn ảnh khi gửi lên server
            */
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("avatar", file_path, requestBody);

            // Gửi ảnh lên server
            presenter.requestFileFormServer(body, edCodeEmployee.getText().toString());
    }


    @Override
    public void doSuccess(int request) {
        if(request == REQUEST_DATA_FORM_SERVER) {
            getRealPath();
        }
        else if(request == REQUEST_IMAGE_FORM_SERVER){
            finish();
        }
    }

    @Override
    public void doFaild(int request) {
        if(request == REQUEST_DATA_FORM_SERVER) {
            Toast.makeText(this, getString(R.string.add_employee_faild), Toast.LENGTH_SHORT).show();
        }
        else if(request == REQUEST_IMAGE_FORM_SERVER){
            Toast.makeText(this, getString(R.string.add_employee_faild), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void doFaildThrowable(Throwable t) {
        Log.d(TAG, "doFaildThrowable: " + t.getMessage());
    }
}
