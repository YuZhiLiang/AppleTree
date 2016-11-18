package com.sy.appletree.mygroup;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sy.appletree.R;
import com.sy.appletree.adapter.GroupPagerAdapter;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.fragment.GroupEmptyFragment;
import com.sy.appletree.fragment.GroupMannerFragment;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.FenZuListener;
import com.sy.appletree.utils.ToastUtils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class GroupMannagerActivity extends AppCompatActivity implements GroupEmptyFragment.onGroupChangeListener {

    public static final String GROUP_NAME = "groupName";
    public static final String GROUP_PEOPLE_NUM = "groupPeopleNum";
    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.groupmanager_pager)
    ViewPager mGroupmanagerPager;
    @Bind(R.id.bottom_id)
    LinearLayout mLinearLayout;
    @Bind(R.id.group_grid)
    GridView mGroupGrid;
    @Bind(R.id.group_wancheng)
    Button mGroupWancheng;
    @Bind(R.id.viewpager_box)
    LinearLayout mViewpagerBox;
    private static boolean mCurrentPagerIsEmptry = true;


    //小圆点相关
    private LinearLayout.LayoutParams params;

    //要显示的分组Fragment
    private List<Fragment> mFragmentList = new ArrayList<>();
    private GroupPagerAdapter mGroupPagerAdapter;
    private GroupEmptyFragment mEmptyFragment;
    private DaiFenAdapter mDaiFenAdapter;

    private boolean yushe;

    private List<String> mStringList = new ArrayList<>();
    private String mClassID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_mannager);
        ButterKnife.bind(this);
        getDatafromIntent();

        getDataFromService();
        for (int i = 0; i < 5; i++) {
            mList.add("小学" + i + "班");
        }
        mEmptyFragment = new GroupEmptyFragment();
        //给第一个空页面设置新增分组时的监听，根据回传数据添加新的分组Fragment
        mEmptyFragment.setOnGroupChangeListener(this);
        //给Viewpager添加默认的添加分组页面
        mFragmentList.add(mEmptyFragment);
        //TODO 添加网络请求到的分组Fragment
        initFragment();


        //获取屏幕当前密度系数
        float density = getResources().getDisplayMetrics().density;
        //将px转成dp
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins((int) (3 * density), 0, (int) (3 * density), 0);
        addDotView(mFragmentList.size());
        //给班级分组管理设置Adapter
        mGroupPagerAdapter = new GroupPagerAdapter(getSupportFragmentManager(), mFragmentList);
        //↑这里初始化的适配器
        mGroupmanagerPager.setAdapter(mGroupPagerAdapter);

        //Viewpager 的 父View收到的事件 传递给 viewpager
        mViewpagerBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGroupmanagerPager.dispatchTouchEvent(event);
            }
        });

        /**
         * 加小圆点
         */
        mGroupmanagerPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    mCurrentPagerIsEmptry = true;
                } else {
                    mCurrentPagerIsEmptry = false;
                }
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
        mEmptyFragment.setFenZuListener(new FenZuListener() {
            @Override
            public void ChangePosition(int position) {
                mGroupmanagerPager.setCurrentItem(position);
            }
        });
        for (int i = 0; i < 20; i++) {
            mStringList.add("赵日天" + i);
        }
        mDaiFenAdapter = new DaiFenAdapter(mStringList);
        mGroupGrid.setAdapter(mDaiFenAdapter);
        mGroupGrid.setOnItemClickListener(DFZItemclickListener);

    }

    private void getDatafromIntent() {
        //是否预设,就是从班级管理进来的
        Intent intent = getIntent();
        yushe = intent.getBooleanExtra("yushe", false);
        mClassID = intent.getStringExtra("classID");
    }

    private void getDataFromService() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetClassGroup.PROTOCOL)
                .append(AppleTreeUrl.GetClassGroup.PARAMS_CLASS_ID)
                .append(mClassID + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());

        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new GroupManagerCallBack());
    }

    class GroupManagerCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
        }
    }

    private void initFragment() {
        //循环添加分组Fragment
        for (int i = 0; i < 5; i++) {
            mFragmentList.add(setPager());
        }
    }

    //添加单个分组的Fragment,在此处初始化分组数据
    private GroupMannerFragment setPager() {
        GroupMannerFragment groupMannerFragment = new GroupMannerFragment();
        Bundle bundle = new Bundle();

        groupMannerFragment.setArguments(bundle);
        return groupMannerFragment;
    }

    @OnClick({R.id.base_left, R.id.base_right, R.id.group_wancheng})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left://退出
                finish();
                break;
            case R.id.base_right://选班
                initPopWindow();
                openPopWindow();

                break;
            case R.id.group_wancheng://完成
                if (yushe) {
                    Intent intent = new Intent();
                    intent.putExtra("fenzushu", 6);
                    setResult(101, intent);
                    finish();
                } else {
                    Intent intent = new Intent(GroupMannagerActivity.this, MainActivity.class);

                    startActivity(intent);
                }
                break;
            default:
                break;
        }
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


    /**
     * 当用户新增分组时的回回调
     *
     * @param groupName           分组的名字
     * @param groupStudentsNumber 分组人数
     */
    @Override
    public void onAddNewGroupListener(String groupName, int groupStudentsNumber) {
        //TODO 将回传的新建分组根据组名和人数新建Fragment
        Bundle bundle = new Bundle();
        bundle.putString(GROUP_NAME, groupName);
        bundle.putInt(GROUP_PEOPLE_NUM, groupStudentsNumber);
        //新建一个分组的Fragment并设置数据
        GroupMannerFragment mannerFragment = new GroupMannerFragment();
        mannerFragment.setArguments(bundle);
        mFragmentList.add(mannerFragment);
        //跟新分组Fragment
        mGroupPagerAdapter.notifyDataSetChanged();
        //更新ViewPager指示器和位置
        mLinearLayout.removeAllViews();
        addDotView(mFragmentList.size());
        mGroupmanagerPager.setCurrentItem(mFragmentList.size() - 1);
    }

    public class DaiFenAdapter extends BaseAdapter {
        private List<String> mStrings;

        public DaiFenAdapter(List<String> strings) {
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

            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daifenzu_item, null);
                holder.mText = (TextView) convertView.findViewById(R.id.daifen_student);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mText.setText(mStrings.get(position));


            return convertView;
        }

        class ViewHolder {
            TextView mText;
        }
    }


    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    private void screenBrighter() {
        WindowManager.LayoutParams params = GroupMannagerActivity.this.getWindow().getAttributes();
        params.alpha = 1f;
        GroupMannagerActivity.this.getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = GroupMannagerActivity.this.getWindow().getAttributes();
        params.alpha = 0.5f;
        GroupMannagerActivity.this.getWindow().setAttributes(params);
    }

    private View contentView;
    private PopupWindow popupWindow;

    private void initPopWindow() {

        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(GroupMannagerActivity.this).inflate(
                R.layout.pop_xuanban, null);
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
        Button btn = (Button) contentView.findViewById(R.id.fenzu_btn);
        ImageView colse = (ImageView) contentView.findViewById(R.id.close);
        ListView listView = (ListView) contentView.findViewById(R.id.fenzu_list);

        mXuanZuAdapter = new XuanZuAdapter(mList);
        listView.setAdapter(mXuanZuAdapter);

        colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickPosition == -1) {
                    Toast.makeText(GroupMannagerActivity.this, "请选择班级", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    Toast.makeText(GroupMannagerActivity.this, "选择班级" + mList.get(ClickPosition), Toast.LENGTH_SHORT).show();
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                        popupWindow = null;
                    }
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

    private List<String> mList = new ArrayList<>();
    private XuanZuAdapter mXuanZuAdapter;
    private int ClickPosition = -1;

    public class XuanZuAdapter extends BaseAdapter {
        private List<String> mStrings;

        public XuanZuAdapter(List<String> strings) {
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
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xuanban_item, null);
                viewHolder.mCheckBox = (RadioButton) convertView.findViewById(R.id.xuanban_check);
                viewHolder.mRadioGroup = (RadioGroup) convertView.findViewById(R.id.xuanban_radio);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (mStrings.size() != 0) {
                viewHolder.mCheckBox.setText(mStrings.get(position));
                viewHolder.mRadioGroup.clearCheck();
            }
            if (ClickPosition == position) {//条目为刚才点击的条目

                viewHolder.mCheckBox.setChecked(true);
            } else {
                viewHolder.mCheckBox.setChecked(false);
            }
            viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickPosition = position;
                    notifyDataSetChanged();
                    Log.e("刷新执行了", position + "");
                }
            });


            return convertView;
        }

        class ViewHolder {
            RadioButton mCheckBox;
            RadioGroup mRadioGroup;
        }
    }

    //待分组的学生的点击监听
    AdapterView.OnItemClickListener DFZItemclickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mCurrentPagerIsEmptry) {
                Toast.makeText(BaseApplication.getContext(), "请选择一个分组", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BaseApplication.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
