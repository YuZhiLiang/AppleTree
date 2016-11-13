package com.sy.appletree.evaluate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.views.EvaluateItemView;
import com.sy.appletree.views.PieView;
import com.sy.appletree.views.RadarData;
import com.sy.appletree.views.RadarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.inflate;

public class EvaluateActivity extends AppCompatActivity {


    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.pingjia_kecheng_txt)
    TextView mPingjiaKechengTxt;
    @Bind(R.id.pingjia_kecheng)
    RelativeLayout mPingjiaKecheng;
    @Bind(R.id.pingjia_yizhou_txt)
    TextView mPingjiaYizhouTxt;
    @Bind(R.id.pingjia_yizhou)
    RelativeLayout mPingjiaYizhou;
    @Bind(R.id.pingjia_viewpager)
    ViewPager mPingjiaViewpager;
    @Bind(R.id.group_evaluate)
    Button mGroupEvaluate;
    @Bind(R.id.student_evaluate)
    Button mStudentEvaluate;
    @Bind(R.id.group_or_personal)
    ViewPager mGroupOrPersonal;
    private View mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evaluate_fragment_layout_new);
        ButterKnife.bind(this);
        initDate();
        initEvent();
    }

    private void initDate() {
        //上部分的图示轮播图设置Adapter
        mPingjiaViewpager.setAdapter(PFTadapter);

        //下部分的小组或个人设置Adapter
        mGroupOrPersonal.setAdapter(mGroupOrPersonalAdapter);

    }


    private void initEvent() {
        //底部展示小组和成员的页面监听，现在主要用来切换指示器
        mGroupOrPersonal.setOnPageChangeListener(onGroupOrPersonalPageChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    //底部展示小组和成员的页面监听，现在主要用来切换指示器
    ViewPager.OnPageChangeListener onGroupOrPersonalPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    //评分图的适配器
    PagerAdapter PFTadapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            View view = null;
            if (position == 0) {
                //圆形评分图
                view = new PieView(EvaluateActivity.this);
            } else {
                //雷达评分图
                view = inflate(BaseApplication.getContext(), R.layout.layout_radar_view, null);
                RadarView radar = (RadarView) view.findViewById(R.id.radar_view);
                List<RadarData> list = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    RadarData data = new RadarData("评价标准" + i, i * 10);
                    list.add(data);
                }
                radar.setDataList(list);
            }
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    //小组或个人的适配器
    PagerAdapter mGroupOrPersonalAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ListView view = new ListView(getApplicationContext());
            if (position == 0) {
                //需要展示小组时

            } else {
                //需要展示个人时

            }
            view.setAdapter(new EvaluateBottomAdapter());
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    };

    class EvaluateBottomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = new EvaluateItemView(BaseApplication.getContext());
            return v;
        }
    }

    private Dialog dialog;
    boolean isExpand = false;
    private String[] levelArray1 = {"2016小学语文三年级上", "2016小学语文三年级中", "2018小学语文三年级下"};
    private String[] levelArray2 = {"一天内", "一周内", "一年内"};

    @OnClick({R.id.pingjia_kecheng, R.id.pingjia_yizhou, R.id.back, R.id.student_evaluate, R.id.group_evaluate})
//三个自定义下拉框
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.pingjia_kecheng:
                onPingiakecChengClick();
                break;
            case R.id.pingjia_yizhou:
                onPingJiaYiZhouClick();
                break;
            case R.id.student_evaluate:
                onStudentEvaluateBtnClick();
                break;
            case R.id.group_evaluate:
                onGroupEvaluateBtnClick();
                break;
        }
    }

    //点击了学生评价
    private void onStudentEvaluateBtnClick() {
        CurrentEvaluatePageIsStudent();
        mGroupOrPersonal.setCurrentItem(0);
    }

    private void CurrentEvaluatePageIsStudent() {
        mStudentEvaluate.setBackgroundResource(R.color.myWhite);
        mStudentEvaluate.setTextColor(getResources().getColor(R.color.textColor));

        mGroupEvaluate.setBackgroundResource(R.color.textColor);
        mGroupEvaluate.setTextColor(getResources().getColor(R.color.myWhite));
    }

    //点击了小组评价
    private void onGroupEvaluateBtnClick() {
        CurrentEvaluatePageIsGroup();
        mGroupOrPersonal.setCurrentItem(1);
    }

    private void CurrentEvaluatePageIsGroup() {
        mGroupEvaluate.setBackgroundResource(R.color.myWhite);
        mGroupEvaluate.setTextColor(getResources().getColor(R.color.textColor));

        mStudentEvaluate.setBackgroundResource(R.color.textColor);
        mStudentEvaluate.setTextColor(getResources().getColor(R.color.myWhite));
    }

    private void onPingJiaYiZhouClick() {
        if (!isExpand) {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ListView mListView = new ListView(this);
            mListView.setCacheColorHint(Color.TRANSPARENT);
            mListView.setAdapter(new LevelAdapter(levelArray2));
            mListView.setBackgroundColor(Color.parseColor("#ffffff"));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    mPingjiaYizhouTxt.setText(levelArray2[position]);
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                        isExpand = false;
                    }
                }
            });
            dialog.setContentView(mListView);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    isExpand = false;
                }
            });
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
            dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.dimAmount = 0f;
            int[] location = new int[2];
            mPingjiaYizhou.getLocationOnScreen(location);
            Rect out = new Rect();
            mPingjiaYizhou.getWindowVisibleDisplayFrame(out);
            lp.x = location[0];
            lp.y = location[1] - out.top + mPingjiaYizhou.getHeight();
            lp.width = mPingjiaYizhou.getWidth();
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
            dialog.show();
            isExpand = true;
        } else {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }

    private void onPingiakecChengClick() {
        if (!isExpand) {
            dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            ListView mListView = new ListView(this);
            mListView.setCacheColorHint(Color.TRANSPARENT);
            mListView.setAdapter(new LevelAdapter(levelArray1));
            mListView.setBackgroundColor(Color.parseColor("#ffffff"));
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // TODO Auto-generated method stub
                    mPingjiaKechengTxt.setText(levelArray1[position]);
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                        isExpand = false;
                    }
                }
            });
            dialog.setContentView(mListView);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                @Override
                public void onDismiss(DialogInterface dialog) {
                    // TODO Auto-generated method stub
                    isExpand = false;
                }
            });
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.RED));
            dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.dimAmount = 0f;
            int[] location = new int[2];
            mPingjiaKecheng.getLocationOnScreen(location);
            Rect out = new Rect();
            mPingjiaKecheng.getWindowVisibleDisplayFrame(out);
            lp.x = location[0];
            lp.y = location[1] - out.top + mPingjiaKecheng.getHeight();
            lp.width = mPingjiaKecheng.getWidth();
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            dialogWindow.setAttributes(lp);
            dialog.show();
            isExpand = true;
        } else {
            if (dialog != null) {
                dialog.dismiss();
                dialog = null;
            }
        }
    }


    /**
     * 自定义下拉框的适配器
     */

    public class LevelAdapter extends BaseAdapter {

        private String[] data;

        public LevelAdapter(String[] data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
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

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_spinner_item1, null);
            TextView textView = (TextView) convertView.findViewById(R.id.diaspin_txt);

            textView.setText(data[position]);
            return convertView;
        }
    }

    /**
     * 能力分组适配器
     */
    public class NengLiAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
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
            return null;
        }
    }
}
