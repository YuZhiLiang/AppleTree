package com.sy.appletree.myclasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.bean.ClasssSummaryBean;
import com.sy.appletree.fragment.ClassManagerFragment;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.login_register.ChoosePlaceActivity;
import com.sy.appletree.utils.Const;
import com.sy.appletree.utils.SharePreferenceUtils;
import com.sy.appletree.utils.ToastUtils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ClassManegerActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.classmanager_pager)
    ViewPager mClassmanagerPager;
    @Bind(R.id.bottom_id)
    LinearLayout mLinearLayout;
    @Bind(R.id.base_right_icon)
    ImageView mBaseRightIcon;
    @Bind(R.id.viewpager_box)
    LinearLayout mViewpagerBox;

    private String School;
    private String Banji;
    private List<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<ClasssSummaryBean.DataBean> mClassSummaryBeans = new ArrayList<>();
    private ClassPagerAdapter mClassPagerAdapter;

    //小圆点相关
    private LinearLayout.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_maneger);
        ButterKnife.bind(this);
        initData();

        //获取屏幕当前密度系数
        float density = getResources().getDisplayMetrics().density;
        //将px转成dp
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) (3 * density), 0, (int) (3 * density), 0);
        addDotView(mFragmentList.size());


        mClassPagerAdapter = new ClassPagerAdapter(getSupportFragmentManager());
        mClassmanagerPager.setAdapter(mClassPagerAdapter);
        //Viewpager 的 父View收到的事件 传递给 viewpager
        mViewpagerBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mClassmanagerPager.dispatchTouchEvent(event);
            }
        });
//        mClassmanagerPager.setCurrentItem(1);
        mClassmanagerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < mFragmentList.size(); i++) {
                    ImageView dotImageView = (ImageView) mLinearLayout.getChildAt(i);
                    if (i == position) {
                        dotImageView.setImageResource(R.drawable.shape_fullcricle);
                    } else {
                        dotImageView.setImageResource(R.drawable.shape_whitecricle);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    private ClassManagerFragment setPager(ClasssSummaryBean.DataBean classSummaryBean) {
        ClassManagerFragment classManagerFragment = new ClassManagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("schoolName", classSummaryBean.getSchoolName());
        bundle.putString("className", classSummaryBean.getClassName());
        bundle.putString("classID", String.valueOf(classSummaryBean.getClassId()));
        bundle.putString("studentNum" ,String.valueOf(classSummaryBean.getStudentNum()));
        bundle.putString("groupNum", String.valueOf(classSummaryBean.getGroupNum()));
        bundle.putString("classType", classSummaryBean.getClassType());
        classManagerFragment.setArguments(bundle);
        return classManagerFragment;
    }

    /**
     * 拿到学校班级数据
     */
    private void initData() {
        School = (String) SharePreferenceUtils.getParam(ClassManegerActivity.this, Const.CACHE_SCHOOL, "");
        Banji = getIntent().getStringExtra("BanJi");
        getDataFromService();
    }

    private void getDataFromService() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.ClassSummary.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());

        Log.e(getClass().getSimpleName(), url.toString());

        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new ClassManagerCallBack());

    }

    class ClassManagerCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            ClasssSummaryBean classsSummaryBeans = gson.fromJson(response, ClasssSummaryBean.class);
            if (classsSummaryBeans.getStatus().equals("y")) {
                onGetClassSummaryBeansSuccess(classsSummaryBeans.getData());
            }else {
                toast(classsSummaryBeans.getInfo());
            }
        }
    }

    private void onGetClassSummaryBeansSuccess(List<ClasssSummaryBean.DataBean> data) {
        if (!mClassSummaryBeans.isEmpty()) {
            mClassSummaryBeans.clear();
        }
        mClassSummaryBeans.addAll(data);
        creatFragmentAndAdd2Activity();
    }

    private void creatFragmentAndAdd2Activity() {
        for (ClasssSummaryBean.DataBean classSummaryBean : mClassSummaryBeans) {
            mFragmentList.add(setPager(classSummaryBean));
            mClassPagerAdapter.notifyDataSetChanged();
            mLinearLayout.removeAllViews();
            addDotView(mFragmentList.size());
        }
    }

    private void toast(String message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void addDotView(int size) {
        ImageView dotImageView;
        //根据视图个数添加导航小图标
        for (int i = 0; i < size; i++) {
            dotImageView = new ImageView(this);
            dotImageView.setTag(i);
            if (i == 0) {
                dotImageView.setImageResource(R.drawable.shape_fullcricle);
            } else {
                dotImageView.setImageResource(R.drawable.shape_whitecricle);
            }
            //将小图标添加到导航布局中
            mLinearLayout.addView(dotImageView, params);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String classID) {
        ToastUtils.toast("拿到响应");
        getDataFromService();
    };

    @OnClick({R.id.base_left, R.id.base_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                Intent intent1 = new Intent(ClassManegerActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.base_right:
                Intent intent = new Intent(ClassManegerActivity.this, ChoosePlaceActivity.class);
                intent.putExtra("tag", 1);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    class ClassPagerAdapter extends FragmentPagerAdapter {

        public void addFragment(List<Fragment> FragmentList) {
            mFragmentList.addAll(FragmentList);
            notifyDataSetChanged();
        }

        public ClassPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }
}
