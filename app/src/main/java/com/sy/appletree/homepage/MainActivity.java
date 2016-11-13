package com.sy.appletree.homepage;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.sy.appletree.R;
import com.sy.appletree.evaluate.SetEvaluateActivity;
import com.sy.appletree.fragment.CourseFragment;
import com.sy.appletree.fragment.EvluateClassSelectFragment;
import com.sy.appletree.fragment.TankFragment;
import com.sy.appletree.login_register.ChoosePlaceActivity;
import com.sy.appletree.login_register.LoginAvtivity;
import com.sy.appletree.mycollection.MyCollectionActivity;
import com.sy.appletree.mygroup.GroupMannagerActivity;
import com.sy.appletree.personal_center.SetActivity;
import com.sy.appletree.views.CircleImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.main_content)
    FrameLayout mMainContent;
    @Bind(R.id.main_tab_course)
    RadioButton mMainTabCourse;
    @Bind(R.id.main_tab_tank)
    RadioButton mMainTabTank;
    @Bind(R.id.main_tab_evaluate)
    RadioButton mMainTabEvaluate;
    @Bind(R.id.main_tab_group)
    RadioGroup mMainTabGroup;
    @Bind(R.id.menu_head_icon)
    CircleImageView mMenuHeadIcon;
    @Bind(R.id.menu_set)
    ImageButton mMenuSet;
    @Bind(R.id.menu_head)
    RelativeLayout mMenuHead;//侧滑菜单
    @Bind(R.id.menu_classes)
    RelativeLayout mMenuClasses;
    @Bind(R.id.menu_Group)
    RelativeLayout mMenuGroup;
    @Bind(R.id.menu_index)
    RelativeLayout mMenuIndex;
    @Bind(R.id.menu_collect)
    RelativeLayout mMenuCollect;
    @Bind(R.id.menu_exit)
    RelativeLayout mMenuExit;
    @Bind(R.id.main_menu_layout)
    LinearLayout mMainMenuLayout;
    @Bind(R.id.main_drawlayout)
    DrawerLayout mMainDrawlayout;

    private FragmentManager mFragmentManager;
    private CourseFragment mCourseFragment;
    private TankFragment mTankFragment;
    private EvluateClassSelectFragment mEvaluateFragment;
    private Fragment currentFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        closeActivity();
        //初始化Fragment
        if (savedInstanceState != null) {
            mFragmentManager = getFragmentManager();
            mCourseFragment = (CourseFragment) mFragmentManager.findFragmentByTag("course");
            mTankFragment = (TankFragment) mFragmentManager.findFragmentByTag("tank");
            mEvaluateFragment = (EvluateClassSelectFragment) mFragmentManager.findFragmentByTag("evaluate");
        } else {
            initFragment();
        }
        //相应个人中心的回调
        mCourseFragment.setMyOnClickListener(new MyOnClickListener() {
            @Override
            public void onClick() {
                mMainDrawlayout.openDrawer(mMainMenuLayout);
            }
        });
        mTankFragment.setMyOnClickListener(new MyOnClickListener() {
            @Override
            public void onClick() {
                mMainDrawlayout.openDrawer(mMainMenuLayout);
            }
        });
        addFragment();
        mMainTabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.main_tab_course:
                        if (currentFragment != mCourseFragment) {
                            mFragmentManager.beginTransaction()
                                    .show(mCourseFragment)
                                    .hide(currentFragment)
                                    .commit();
                            currentFragment = mCourseFragment;
                        }
//                        Log.e("回调运行了","vvvvvv");
                        break;
                    case R.id.main_tab_tank:
                        if (currentFragment != mTankFragment) {
                            mFragmentManager.beginTransaction()
                                    .show(mTankFragment)
                                    .hide(currentFragment)
                                    .commit();
                            currentFragment = mTankFragment;
                        }


                        break;
                    case R.id.main_tab_evaluate:
                        if (currentFragment != mEvaluateFragment) {
                            mFragmentManager.beginTransaction()
                                    .show(mEvaluateFragment)
                                    .hide(currentFragment)
                                    .commit();
                            currentFragment = mEvaluateFragment;
                        }

                        break;
                    default:
                        break;
                }
            }
        });
        //设置点击事件
        setListener();
        mMainDrawlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void setListener() {
        mMenuHeadIcon.setOnClickListener(this);
        mMenuSet.setOnClickListener(this);
        mMenuClasses.setOnClickListener(this);
        mMenuCollect.setOnClickListener(this);
        mMenuGroup.setOnClickListener(this);
        mMenuIndex.setOnClickListener(this);
        mMenuExit.setOnClickListener(this);
    }


    private void closeActivity() {
        Intent intent = new Intent();
        intent.setAction("finish");
        sendBroadcast(intent);
    }

    private void addFragment() {
            mFragmentManager.beginTransaction()
                    .add(R.id.main_content, mCourseFragment, "course")
                    .add(R.id.main_content, mTankFragment, "tank")
                    .add(R.id.main_content, mEvaluateFragment, "evaluate")
                    .show(mCourseFragment)
                    .hide(mEvaluateFragment)
                    .hide(mTankFragment)
                    .commit();
        currentFragment = mCourseFragment;
    }

    private void initFragment() {
        mCourseFragment = new CourseFragment();
        mEvaluateFragment = new EvluateClassSelectFragment();
        mTankFragment = new TankFragment();
        mFragmentManager = getFragmentManager();

    }

    private long exitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //头像
            case R.id.menu_head_icon:

                break;
            //设置
            case R.id.menu_set:
                Intent intent=new Intent(MainActivity.this, SetActivity.class);
                startActivity(intent);
                break;
            //我的班级
            case R.id.menu_classes:
                Intent intent1=new Intent(MainActivity.this, ChoosePlaceActivity.class);//到选择地区页面
                intent1.putExtra("tag",1);
                startActivity(intent1);

            break;
            //我的分组
            case R.id.menu_Group:
                Intent intent2=new Intent(MainActivity.this, GroupMannagerActivity.class);//设置分组页面
                startActivity(intent2);

                break;
            //评价指标
            case R.id.menu_index:
                Intent intent3=new Intent(MainActivity.this, SetEvaluateActivity.class);//设置评价页面
                startActivity(intent3);
                break;
            //我的收藏
            case R.id.menu_collect:
                Intent intent5=new Intent(MainActivity.this, MyCollectionActivity.class);
                startActivity(intent5);

                break;
            //退出登录
            case R.id.menu_exit:
                Intent intent4=new Intent(MainActivity.this, LoginAvtivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
                finish();
                break;
            default:
                break;
        }
    }


    public interface MyOnClickListener {
        void onClick();
    }

    public interface OCListener {
        void onClick(boolean isOpen);
    }
}
