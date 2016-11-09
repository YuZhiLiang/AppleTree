package com.sy.appletree.personal_center;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.sy.appletree.R;
import com.sy.appletree.utils.Const;
import com.sy.appletree.views.CircleImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 个人中心
 */

public class SetActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_right_icon)
    ImageView mBaseRightIcon;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.view)
    View mView;
    @Bind(R.id.right_icon1)
    ImageView mRightIcon1;
    @Bind(R.id.set_head_icon)
    CircleImageView mSetHeadIcon;
    @Bind(R.id.set_head)
    RelativeLayout mSetHead;
    @Bind(R.id.right_icon2)
    ImageView mRightIcon2;
    @Bind(R.id.set_name_txt)
    TextView mSetNameTxt;
    @Bind(R.id.set_name)
    RelativeLayout mSetName;
    @Bind(R.id.right_icon3)
    ImageView mRightIcon3;
    @Bind(R.id.set_sex_txt)
    TextView mSetSexTxt;
    @Bind(R.id.set_sex)
    RelativeLayout mSetSex;
    @Bind(R.id.right_icon4)
    ImageView mRightIcon4;
    @Bind(R.id.set_email_txt)
    TextView mSetEmailTxt;
    @Bind(R.id.set_email)
    RelativeLayout mSetEmail;
    @Bind(R.id.right_icon5)
    ImageView mRightIcon5;
    @Bind(R.id.set_phone_txt)
    TextView mSetPhoneTxt;
    @Bind(R.id.set_phone)
    RelativeLayout mSetPhone;
    @Bind(R.id.right_icon6)
    ImageView mRightIcon6;
    @Bind(R.id.set_changepwd)
    RelativeLayout mSetChangepwd;
    @Bind(R.id.set_wifidown)
    RelativeLayout mSetWifidown;
    @Bind(R.id.right_icon7)
    ImageView mRightIcon7;
    @Bind(R.id.set_about)
    RelativeLayout mSetAbout;
    @Bind(R.id.set_wifi_offon)
    ToggleButton mSetWifiOffon;

    static final int PICTURE = 100;
    static final int CAMERA = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.base_left, R.id.base_right, R.id.set_head, R.id.set_name, R.id.set_sex, R.id.set_email, R.id.set_phone, R.id.set_changepwd, R.id.set_wifidown, R.id.set_about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                finish();
                break;
            case R.id.set_head:
                openDig();
                break;
            case R.id.set_name:
                Intent intent = new Intent(SetActivity.this, SetDetailActivity.class);
                intent.putExtra("tag", "name");
                startActivityForResult(intent, Const.SETTO);
                break;
            case R.id.set_sex:
                Intent intent1 = new Intent(SetActivity.this, SetDetailActivity.class);
                intent1.putExtra("tag", "sex");
                startActivityForResult(intent1, Const.SETTO);
                break;
            case R.id.set_email:
                Intent intent2 = new Intent(SetActivity.this, SetDetailActivity.class);
                intent2.putExtra("tag", "email");
                startActivityForResult(intent2, Const.SETTO);
                break;
            case R.id.set_phone:
                Intent intent3 = new Intent(SetActivity.this, SetDetailActivity.class);
                intent3.putExtra("tag", "phone");
                startActivityForResult(intent3, Const.SETTO);
                break;
            case R.id.set_changepwd:
                Intent intent4 = new Intent(SetActivity.this, SetDetailActivity.class);
                intent4.putExtra("tag", "pwd");
                startActivityForResult(intent4, Const.SETTO);
                break;
            case R.id.set_wifidown:

                break;
            case R.id.set_about:
                Intent intent5 = new Intent(SetActivity.this, SetDetailActivity.class);
                intent5.putExtra("tag", "about");
                startActivityForResult(intent5, Const.SETTO);
                break;
            case R.id.base_right://保存


                break;
            default:
                break;
        }
    }

    private AlertDialog alertDialog;

    private void openDig() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.phone_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this,
                R.style.Dialog);
        alertDialog = builder.create();
        if (!alertDialog.isShowing()) {
            alertDialog.show();
        }
        Window window = alertDialog.getWindow();
        window.setContentView(view);

        Button cancel_btn = (Button) window.findViewById(R.id.btn_cancel);
        Button confirm_btn = (Button) window.findViewById(R.id.btn_confirm);
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                alertDialog.dismiss();
                Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, PICTURE);
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                alertDialog.dismiss();
                //相机
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, CAMERA);
            }
        });

        WindowManager.LayoutParams layoutParams = window.getAttributes();
        window.setBackgroundDrawableResource(android.R.color.transparent);

        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);

    }

    private File mFile;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICTURE && resultCode == Activity.RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            mFile = new File(picturePath);
            Bitmap bitmap = getDiskBitmap(picturePath);
            mSetHeadIcon.setImageBitmap(bitmap);
            //上传头像

            UpLoading(mFile);//上传头像方法

        }
        if (requestCode == CAMERA && resultCode == Activity.RESULT_OK && null != data) {
            String sdState = Environment.getExternalStorageState();
            if (!sdState.equals(Environment.MEDIA_MOUNTED)) {
                return;
            }
            new DateFormat();
            String name = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
            Bundle bundle = data.getExtras();
            //获取相机返回的数据，并转换为图片格式
            Bitmap bitmap = (Bitmap) bundle.get("data");
            FileOutputStream fout = null;
            File file = new File("/sdcard/pintu/");
            file.mkdirs();
            String filename = file.getPath() + name;
            try {
                fout = new FileOutputStream(filename);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fout);

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fout.flush();
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //显示图片
            mSetHeadIcon.setImageBitmap(bitmap);

            creat(filename);

        }
    }

    private void UpLoading(File file) {

    }

    //创建文件
    public void creat(String path) {
        mFile = new File(path);
        UpLoading(mFile);
    }

    private Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }


        return bitmap;
    }


    private Dialog dialog;

    public void initDialog() {
        if (dialog == null) {
            dialog = new Dialog(SetActivity.this,
                    R.style.progress_dialog);
        }
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent);
        TextView msg = (TextView) dialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("Loading...");
    }

}
