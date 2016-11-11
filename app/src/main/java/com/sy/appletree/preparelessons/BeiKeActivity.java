package com.sy.appletree.preparelessons;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.adapter.CourseAdapter;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.ModleBean.BeikeBean;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 备课
 */
public class BeiKeActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_title)
    TextView mBaseTitle;
    @Bind(R.id.base_center)
    RelativeLayout mBaseCenter;
    @Bind(R.id.base_right_icon)
    TextView mBaseRightIcon;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.base_content)
    LinearLayout mBaseContent;
    @Bind(R.id.bk_kemu)
    Button mBkKemu;
    @Bind(R.id.bk_name)
    TextView mBkName;
    @Bind(R.id.bk_nianji)
    TextView mBkNianji;
    @Bind(R.id.bk_jiaocai)
    TextView mBkJiaocai;
    @Bind(R.id.base_top_bg)
    RelativeLayout mBaseTopBg;
    @Bind(R.id.course_list)
    ListView mCourseList;
    @Bind(R.id.course_content)
    LinearLayout mCourseContent;
    private String kemu;
    private String jiaocai;
    private String nianji;
    private String kecheng;
    private boolean mDanxuan;
    private CourseAdapter mCourseAdapter;

    private List<BeikeBean> mBeikeBeans = new ArrayList<>();
    private List<BeikeBean> mBeikeBeans1 = new ArrayList<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 1:
                    mCourseAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
    private boolean mYuLan;
    private String mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bei_ke);
        mYuLan = getIntent().getBooleanExtra("yuLan", false);
        ButterKnife.bind(this);
        getChuanZhi();
        setView();
        getDtaaFromService();
        if (mYuLan) {
            mCourseAdapter = new CourseAdapter(mBeikeBeans1, true);
        } else {
            mCourseAdapter = new CourseAdapter(mBeikeBeans1, false);
        }

        View foot = getLayoutInflater().inflate(R.layout.footview, null);
        if (!mYuLan) {
            mCourseList.addFooterView(foot);
            RelativeLayout relativeLayout = (RelativeLayout) foot.findViewById(R.id.course_add);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initPopWindow();
                    openPopWindow();
                }
            });
        }
        mCourseList.setAdapter(mCourseAdapter);

        mCourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //点击课程跳转到预览任务列表
                Intent intent = new Intent(BeiKeActivity.this, TaskListActivity.class);
                if (mYuLan) {
                    intent.putExtra("yuLan", true);
                } else {
                    intent.putExtra("yuLan", false);
                }
                startActivity(intent);
            }
        });


    }

    private void getDtaaFromService() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.getCourse.PROTOCOL)
                .append(AppleTreeUrl.getCourse.PARAMS_COURSE_PKG_ID + "=")
                .append(mID + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(mStringCallback);
    }

    StringCallback mStringCallback = new StringCallback() {
        @Override
        public void onError(Call call, Exception e, int id) {

        }

        @Override
        public void onResponse(String response, int id) {

        }
    };

    /**
     * 赋初值
     */
    private void setView() {
        if (mDanxuan) {
            String substring = kemu.substring(0, 1);
            mBkKemu.setText(substring);
            mBkName.setText(kecheng);
            mBkNianji.setText(nianji);
            mBkJiaocai.setText(jiaocai);
        } else {
            mBkKemu.setText("综");
            mBkName.setText(kecheng);
            mBkNianji.setText(nianji);
            mBkJiaocai.setText(jiaocai);
        }

        //智库开启改变一些值
        if (mYuLan) {
            mBaseTitle.setText("预览课程");
            mBaseRight.setVisibility(View.INVISIBLE);
            mBaseRight.setClickable(false);

        }

    }

    private void getChuanZhi() {
        Intent intent = getIntent();
        mDanxuan = intent.getBooleanExtra("isSingle", true);
        kecheng = intent.getStringExtra("Name");
        kemu = intent.getStringExtra("Subject");
        jiaocai = intent.getStringExtra("Book");
        nianji = intent.getStringExtra("Grad");
        mID = intent.getStringExtra("ID");
    }

    @OnClick({R.id.base_left, R.id.base_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                finish();
                break;
            case R.id.base_right:
                Intent intent = new Intent(BeiKeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    private void screenBrighter() {
        WindowManager.LayoutParams params = BeiKeActivity.this.getWindow().getAttributes();
        params.alpha = 1f;
        BeiKeActivity.this.getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = BeiKeActivity.this.getWindow().getAttributes();
        params.alpha = 0.5f;
        BeiKeActivity.this.getWindow().setAttributes(params);
    }

    private View contentView;
    private PopupWindow popupWindow;

    private void initPopWindow() {

        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(BeiKeActivity.this).inflate(
                R.layout.pop_lesson, null);
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

        final EditText name = (EditText) contentView.findViewById(R.id.pop_lesson_name);
        final EditText mubiao = (EditText) contentView.findViewById(R.id.pop_lesson_mubiao);
        Button btn = (Button) contentView.findViewById(R.id.pop_lesson_btn);
        ImageView colse = (ImageView) contentView.findViewById(R.id.close);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(name.getText().toString())) {
                    Toast.makeText(BeiKeActivity.this, "请填写课程名称", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    BeikeBean beikeBean = new BeikeBean();
                    beikeBean.setName(name.getText().toString());
                    if (TextUtils.isEmpty(mubiao.getText().toString())) {
                        beikeBean.setMuBiao("");
                    } else {
                        beikeBean.setMuBiao(mubiao.getText().toString());
                    }
                    addBiKeBean(beikeBean);
                    mBeikeBeans.add(beikeBean);
                    mBeikeBeans1.clear();
                    mBeikeBeans1.addAll(mBeikeBeans);
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);
                    //关闭
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
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

    //添加小课程到课程包
    private void addBiKeBean(BeikeBean beikeBean) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.addCourse.PROTOCOL)
                .append(AppleTreeUrl.addCourse.PARAMS_COURSE_PACKAGE_ID + "=")
//                .append(mID)
                .append("70" + "&")
                .append(AppleTreeUrl.addCourse.PARAMS_NAME + "=")
                .append(beikeBean.getName() + "&")
                .append(AppleTreeUrl.addCourse.PARAMS_TARGET)
                .append(beikeBean.getMuBiao() + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());
        addBeiKeBean2Service(url.toString(), beikeBean);
    }

    private void addBeiKeBean2Service(String url, BeikeBean beikeBean) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new ThisStringCallBack(beikeBean));
    }

    class ThisStringCallBack extends StringCallback{
        BeikeBean mBeikeBean;

        public ThisStringCallBack(BeikeBean beikeBean) {
            this.mBeikeBean = beikeBean;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
            if (numberVavlibleBean.getStatus().equals("y")) {
                mBeikeBean.setID(numberVavlibleBean.getData().toString());
                addSeccess(mBeikeBean);
            }else {
//                addFaild();
            }
        }
    }

    private void addSeccess(BeikeBean beikeBean) {

    }

    private void toast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
