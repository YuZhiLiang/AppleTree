package com.sy.appletree.preparelessons;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sy.appletree.R;
import com.sy.appletree.evaluate.SetEvaluateActivity;
import com.sy.appletree.views.MyGridView;
import com.sy.appletree.views.MyListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建任务或编辑任务
 */
public class CreateTaskActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.create_task_title)
    TextView mCreateTaskTitle;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.creat_task_name)
    EditText mCreatTaskName;
    @Bind(R.id.creat_task_content)
    EditText mCreatTaskContent;
    @Bind(R.id.link_list)
    MyListView mLinkList;
    @Bind(R.id.tianjia_pj_list)
    MyListView mTianjiaPjList;
    @Bind(R.id.creat_task_addFJ)
    RelativeLayout mCreatTaskAddFJ;
    @Bind(R.id.creat_task_addPJ)
    RelativeLayout mCreatTaskAddPJ;
    @Bind(R.id.creat_task_del)
    Button mCreatTaskDel;
    @Bind(R.id.link_text)
    MyGridView mLinkText;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){

                case 1://更新图片
                    mPicAdapter.notifyDataSetChanged();
                break;
                case 2://更新链接
                    mLinkAdapter.notifyDataSetChanged();
                break;
                case 3://

                    break;
                default:
                    break;
            }
        }
    };
    private String mTAG;


    private PicAdapter mPicAdapter;
    private LinkAdapter mLinkAdapter;
    private ZhiBiaoAdapter mZhiBiaoAdapter;
    private List<Bitmap> mBitmaps=new ArrayList<>();//图片源
    private List<Bitmap> mBitmaps1=new ArrayList<>();//图片源==
    private List<String> mStrings=new ArrayList<>();//链接数据源
    private List<String> mStrings1=new ArrayList<>();//链接数据源==
    private List<String> mStrings2=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        ButterKnife.bind(this);
        getTAG();
        setView();

        //图片
        mPicAdapter=new PicAdapter(mBitmaps1);
        mLinkList.setAdapter(mPicAdapter);
        //链接
        mLinkAdapter=new LinkAdapter(mStrings1);
        mLinkText.setAdapter(mLinkAdapter);

        //指标
        mZhiBiaoAdapter=new ZhiBiaoAdapter(mStrings2);
        mTianjiaPjList.setAdapter(mZhiBiaoAdapter);
    }

    private void setView() {
        if (mTAG.equals("add")) {//添加任务
            Toast.makeText(CreateTaskActivity.this, "添加任务", Toast.LENGTH_SHORT).show();


            mCreatTaskDel.setVisibility(View.GONE);
        } else if (mTAG.equals("edit")) {//修改任务
            Toast.makeText(CreateTaskActivity.this, "修改任务", Toast.LENGTH_SHORT).show();


            mCreatTaskDel.setVisibility(View.VISIBLE);
        }
    }

    private void getTAG() {
        Intent intent = getIntent();
        mTAG = intent.getStringExtra("tag");
    }

    @OnClick({R.id.base_left, R.id.base_right, R.id.creat_task_addFJ, R.id.creat_task_addPJ, R.id.creat_task_del})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                finish();
                break;
            case R.id.base_right:

                initBackPopWindow();
                openPopWindowCenter();
                break;
            case R.id.creat_task_addFJ:
                //启动附件页面添加评价
                initPopWindow();
                openPopWindow();
                break;
            case R.id.creat_task_addPJ:
                //启动评价页面添加评价
                Intent intent=new Intent(CreateTaskActivity.this, SetEvaluateActivity.class);
                intent.putExtra("tag","task");
                startActivityForResult(intent,10);
                break;
            case R.id.creat_task_del:
                //编辑页才会有删除
                //删除执行请求删除该任务id的任务
                Intent intent1 = getIntent();
                DelTask();
                setResult(2,intent1);
                finish();
                break;
        }
    }

    /**
     * 删除任务接口
     */
    private void DelTask() {

    }


    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    public void openPopWindowCenter() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }
    private void screenBrighter() {
        WindowManager.LayoutParams params = CreateTaskActivity.this.getWindow().getAttributes();
        params.alpha = 1f;
        CreateTaskActivity.this.getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = CreateTaskActivity.this.getWindow().getAttributes();
        params.alpha = 0.5f;
        CreateTaskActivity.this.getWindow().setAttributes(params);
    }

    private View contentView;
    private PopupWindow popupWindow;

    private void initPopWindow() {

        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(CreateTaskActivity.this).inflate(
                R.layout.pop_task, null);
        //设置弹出框的宽度和高度
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        TextView title = (TextView) contentView.findViewById(R.id.pop_task_title);
        final EditText link = (EditText) contentView.findViewById(R.id.pop_task_link);
        ImageButton imageButton = (ImageButton) contentView.findViewById(R.id.pop_task_img);
        Button btn = (Button) contentView.findViewById(R.id.pop_task_btn);
        Button zhanTie = (Button) contentView.findViewById(R.id.zhantie);
        ImageView colse = (ImageView) contentView.findViewById(R.id.close);


        zhanTie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);//剪切板
                ClipData.Item item = null;
                // 无数据时
                if (!clipboard.hasPrimaryClip()) {
                    Toast.makeText(getApplicationContext(), "剪贴板中无数据",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 如果是文本信息
                if (clipboard.getPrimaryClipDescription().hasMimeType(
                        ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                    ClipData cdText = clipboard.getPrimaryClip();
                    item = cdText.getItemAt(0);
                    // 此处是TEXT文本信息
                    if (item.getText() == null) {
                        Toast.makeText(getApplicationContext(), "剪贴板中无内容",
                                Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String s = item.getText().toString();
                        link.setText(s);
                    }
                }
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDig();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(link.getText().toString())){
                    mStrings.add(link.getText().toString());
                    mStrings1.clear();
                    mStrings1.addAll(mStrings);
                    Message message=new Message();
                    message.what=2;
                    mHandler.sendMessage(message);
                }
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

        colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                //亮屏
                screenBrighter();

            }
        });
    }

    private void initBackPopWindow() {

        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(CreateTaskActivity.this).inflate(
                R.layout.pop_back, null);
        //设置弹出框的宽度和高度
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        Button button1= (Button) contentView.findViewById(R.id.back_save);
        Button button2= (Button) contentView.findViewById(R.id.back_abnden);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 保存数据到服务器,并讲数据回传给预览任务列表
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                SaveData();
            }
        });

        //点击了取消保存
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                //亮屏
                screenBrighter();

            }
        });
    }

    /**
     * 保存数据
     */
    private void SaveData() {



        //完成之后
        finish();
    }

    private AlertDialog alertDialog;


    static final int PICTURE = 100;
    static final int CAMERA = 200;

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
                alertDialog.dismiss();
                Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, PICTURE);
            }
        });
        confirm_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

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

    //当从评价页面回来时
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
//            mSetHeadIcon.setImageBitmap(bitmap);
            //上传头像
            UpLoading(mFile,bitmap);//上传头像方法

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
//            mSetHeadIcon.setImageBitmap(bitmap);

            creat(filename,bitmap);

        }


        if (requestCode==10&&resultCode==11){
            int pingjia = data.getIntExtra("pingjia", -1);
            mStrings2.add("");
            Toast.makeText(CreateTaskActivity.this,"选择了指标"+pingjia,Toast.LENGTH_SHORT).show();

            mZhiBiaoAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 图片上传
     * @param file
     */
    private void UpLoading(File file,Bitmap bitmap) {
        Toast.makeText(CreateTaskActivity.this, "拿到图片了", Toast.LENGTH_SHORT).show();

        mBitmaps.add(bitmap);
        mBitmaps1.clear();
        mBitmaps1.addAll(mBitmaps);
        Message message=new Message();
        message.what=1;
        mHandler.sendMessage(message);

    }

    //创建文件
    public void creat(String path,Bitmap bitmap) {
        mFile = new File(path);
        UpLoading(mFile,bitmap);
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

    /**
     * 图片的适配器
     */
    public class PicAdapter extends BaseAdapter {

        private List<Bitmap> mBitmaps;

        public PicAdapter(List<Bitmap> bitmaps) {
            mBitmaps = bitmaps;
        }

        @Override
        public int getCount() {
            return mBitmaps.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_pic_item, null);
                viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.task_img);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mImageView.setImageBitmap(mBitmaps.get(position));

            return convertView;
        }

        class ViewHolder {
            ImageView mImageView;
        }
    }

    /**
     * 链接的适配器
     */
    public class LinkAdapter extends BaseAdapter {

        private List<String> mStringList;

        public LinkAdapter(List<String> stringList) {
            mStringList = stringList;
        }

        @Override
        public int getCount() {
            return mStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_link_item, null);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            return convertView;
        }

        class ViewHolder {
            TextView mTextView;

        }

    }

    /**
     * 指标的适配器
     */
    public class ZhiBiaoAdapter extends BaseAdapter {

        private List<String> mStrings;

        public ZhiBiaoAdapter(List<String> strings) {
            mStrings = strings;
        }

        @Override
        public int getCount() {
            return mStrings.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_pingjia_item, null);
                viewHolder.mImageView_icon= (ImageView) convertView.findViewById(R.id.pingjia_icon);
                viewHolder.mImageView_close= (ImageView) convertView.findViewById(R.id.pingjia_close);
                viewHolder.name= (TextView) convertView.findViewById(R.id.pingjia_name);
                viewHolder.content= (TextView) convertView.findViewById(R.id.pingjia_content);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            return convertView;
        }

        class ViewHolder {
            TextView name;
            TextView content;
            ImageView mImageView_close;
            ImageView mImageView_icon;

        }

    }
}