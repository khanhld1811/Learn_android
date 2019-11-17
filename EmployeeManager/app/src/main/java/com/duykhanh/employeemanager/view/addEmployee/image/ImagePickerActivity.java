package com.duykhanh.employeemanager.view.addEmployee.image;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.Toast;

import com.duykhanh.employeemanager.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.List;

import static androidx.core.content.FileProvider.getUriForFile;

public class ImagePickerActivity extends AppCompatActivity {

    /* TODO Các key truyền khi gọi activity*/
    // Key phương thức chọn ảnh từ camera hay gallery
    public static final String EXTRA_IMAGE_PICKER_OPTION = "image_picker_option";

    //Key tỷ lệ ảnh khung cắt
    public static final String EXTRA_ASPECT_RATIO_X = "aspect_ratio_x"; // Tỉ lệ rộng
    public static final String EXTRA_ASPECT_RATIO_Y = "aspect_rario_y";// Tỉ lệ cao

    // Key có khóa tỉ lệ hay không, nếu true tức là chỉ cắt ảnh theo tỉ lệ cho trước, ngược lại cắt theo tỉ lệ khác
    public static final String EXTRA_LOCK_ASPECT_RATIO = "lock_aspect_ratio";

    //Key chất lượng ảnh
    public static final String EXTRA_IMAGE_COMPRESSION_QUALITY = "compression_quality";

    // Key có giới hạn kich thước ảnh hay không, nếu true sẽ giới hạn theo chiều rộng và cao truyền vào, ngược lại thì không
    public static final String EXTRA_SET_BITMAP_MAX_WIDTH_HEIGHT = "set_bitmap_max_width_height";

    // Key có chiều rộng tối đa
    public static final String EXTRA_BITMAP_MAX_WIDTH = "max_width";

    // Key có chiều cao tối đã
    public static final String EXTRA_BITMAP_MAX_HEIGHT = "max_width";

    /* Định nghĩa các biến default khi không được truyền vào lúc gọi activity
     *  ASPECT_RATIO_X là tỷ lệ khung hình theo chiều ngang khi cắt ảnh
     *  ASPECT_RATIO_Y là tỷ lệ khung hình theo chiều dọc khi cắt ảnh
     *  bitmapMaxWidth là kích thước hình ảnh theo chiều ngang
     *  bitmapMaxHeight là kích thước hình ảnh theo chiều dọc
     *  IMAGE_COMPRESSION là chất lượng hình ảnh hiển thị
     *
     * */
    private int ASPECT_RATIO_X = 16, ASPECT_RATIO_Y = 9, bitmapMaxWidth = 1000, bitmapMaxHeight = 1000;
    private boolean lockAspectRatio = false, setBitmapMaxWidthHeight = false;
    private int IMAGE_COMPRESSION = 100;

    public static final String REQUEST_CODE_TYPE = "request_code";

    /* Định nghĩa 2 loại request */
    public static final int REQUEST_IMAGE_CAPTURE = 0;
    public static final int REQUEST_IMAGE_GALLERY = 1;

    String fileName;

    public interface PickerOptionListener {
        void onCameraSelected();    // khi chọn ảnh từ camera

        void onGallerySelected();   // khi chọn lấy ảnh từ gallery
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (intent == null) {
            Toast.makeText(this, "No intent", Toast.LENGTH_SHORT).show();
            return;
        }

        ASPECT_RATIO_X = intent.getIntExtra(EXTRA_ASPECT_RATIO_X, ASPECT_RATIO_X);
        ASPECT_RATIO_Y = intent.getIntExtra(EXTRA_ASPECT_RATIO_Y, ASPECT_RATIO_Y);
        IMAGE_COMPRESSION = intent.getIntExtra(EXTRA_IMAGE_COMPRESSION_QUALITY, IMAGE_COMPRESSION);
        lockAspectRatio = intent.getBooleanExtra(EXTRA_LOCK_ASPECT_RATIO, lockAspectRatio);
        setBitmapMaxWidthHeight = intent.getBooleanExtra(EXTRA_SET_BITMAP_MAX_WIDTH_HEIGHT, setBitmapMaxWidthHeight);
        bitmapMaxWidth = intent.getIntExtra(EXTRA_BITMAP_MAX_WIDTH, bitmapMaxWidth);
        bitmapMaxHeight = intent.getIntExtra(EXTRA_BITMAP_MAX_HEIGHT, bitmapMaxHeight);

        // TODO Xử lý request
        int request = intent.getIntExtra(REQUEST_CODE_TYPE, REQUEST_IMAGE_GALLERY);

        if (request == REQUEST_IMAGE_CAPTURE) {
            takeCameraImage();
        } else {
            takeGalleryImage();
        }

    }

    // TODO Hàm này dùng để show ra dialog lựa chọn phương thức lấy ảnh cho user, trả về một callback là
    // PickerOptionListener

    public static void showImagePickerOptions(Context context, final PickerOptionListener listener) {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.lbl_set_profile_photo));

        // Add chose item to dialog
        String[] animals = {context.getString(R.string.lbl_take_camera_picture), context.getString(R.string.lbl_choose_from_gallery)};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        listener.onCameraSelected();
                        break;
                    case 1:
                        listener.onGallerySelected();
                        break;
                }
            }
        });

        // create and show the alert dialog
        AlertDialog dialogd = builder.create();
        dialogd.show();
    }

    //TODO Hàm lấy hình ảnh từ camera
    private void takeCameraImage() {
        //  Ở đây chúng ta thực hiện request quyền camera và ghi vào bộ nhớ
        //  Quyên camera dùng để lấy ảnh và quyền ghi bộ nhớ dùng để lưu ảnh sau khi chụp từ camera
        //  vào bộ nhớ máy

        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {// MultiplePermissionsReport dùng khi muốn yêu cầu nhiều quyền
                        Log.d("checkPermissionChecked", "onPermissionsChecked: "+report.areAllPermissionsGranted());
                        if (report.areAllPermissionsGranted()) {
                            fileName = System.currentTimeMillis() + ".jpg";
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName));
                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    //TODO Hình ảnh lấy từ gallery
    private void takeGalleryImage() {
        // Tương tự với việc lấy ảnh từ camera, chugs ta cần request quyền trước khi thực hiệ
        Dexter.withActivity(this).withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            Intent pickIntent = new Intent(Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(pickIntent, REQUEST_IMAGE_GALLERY);
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    //TODO Xử lý result sau khi lấy ảnh và cắt ảnh

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_IMAGE_CAPTURE:
                // Xử lý cho trường hợp sau khi lấy ảnh từ camera, nếu lấy thành công sẽ, tiến hành cắt ảnh ,
                // ngược lại sẽ trả về kết quả cancel
                if (resultCode == RESULT_OK) {
                    cropImage(getCacheImagePath(fileName));
                } else {
                    setResultCancelled();
                }
                break;
            case REQUEST_IMAGE_GALLERY:
                // Tương tự với trường hợp REQUEST_IMAGE_CAPTURE nhưng ở đây sẽ lấy ảnh từ gallery
                if (resultCode == RESULT_OK) {

                    Uri imageUri = data.getData();
                    Log.d("IMAGEURI", "onActivityResult: " + imageUri);
                    cropImage(imageUri);
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.REQUEST_CROP:
                // Nhận và xử lý hình ảnh sau khi cắt xong, nếu cắt thành công sẽ trả về cho user là hình
                // ảnh sau khi đã cắt, ngược lại sẽ trả về cancel

                if (resultCode == RESULT_OK) {
                    handleUCropResult(data);
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.RESULT_ERROR:
                // Xử lý trong trường hợp cắt bị lỗi, log error và trả về cancel cho user
                final Throwable cropError = UCrop.getError(data);
                Log.e("ImagePicker", "Crop error: " + cropError);
                setResultCancelled();
                break;
            default:
                setResultCancelled();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //TODO Xử lý hình ảnh sau khi thực hiện cắt , nếu dât truyền vao null thì sẽ cancel kết quả
    // ngược lại sẽ trả về đường dẫn ủi của hình ảnh và result OK
    private void handleUCropResult(Intent data) {
        if (data == null) {
            setResultCancelled();
            return;
        }
        final Uri resultUri = UCrop.getOutput(data);
        setResultOk(resultUri);
    }

    //TODO Thự hiện trả về result OK cho đối tượng kèm theo là đường dẫn hình sau khi đã xử lý
    private void setResultOk(Uri imagePath) {
        Intent intent = new Intent();
        intent.putExtra("path", imagePath);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    //TODO Thực hiện trả về result CANCEL cho đối tượng có thể là do không thể căt ảnh hoặc bất kỳ nguyên nhân nào khác
    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        finish();
    }

    //TODO Thực hiện lấy đường dẫn hình lưu trong bộ nhớ cache với provider đã định nghĩa ở trên và fileName được truyền vào
    public Uri getCacheImagePath(String fileName) {
        File path = new File(getExternalCacheDir(), "camera");
        if (!path.exists()) path.mkdirs();
        File image = new File(path, fileName);
        return getUriForFile(ImagePickerActivity.this, getPackageName() + ".provider", image);
    }

    //TODO Hàm gọi xử lý cắt ảnh
    public void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), queryName(getContentResolver(), sourceUri)));
        UCrop.Options options = new UCrop.Options();

        // Set chất lượng hình ảnh sau khi cắt
        options.setCompressionQuality(IMAGE_COMPRESSION);

        options.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.colorPrimary));

        // Nếu là lock tỉ lệ sẽ gán tỉ lệ truyền vào
        if (lockAspectRatio)
            options.withAspectRatio(ASPECT_RATIO_X, ASPECT_RATIO_Y);

        /// Nếu là lock kích thước thì sẽ gán mã kich thước truyền vào
        if (setBitmapMaxWidthHeight)
            options.withMaxResultSize(bitmapMaxWidth, bitmapMaxHeight);

        //Start activity để tiến hành cắt ảnh, ảnh sau khi cắt xong sẽ được lưu ở cùng thư mục với ảnh truyền vào
        // tức là sẽ ghi đè lên ảnh đã truyền vào
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(this);
    }

    //TODO Lấy tên file thông qua uri của file
    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor = resolver.query(uri, null, null, null, null);

        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        Log.d("URI", "queryName: " + name);
        return name;
    }
}
