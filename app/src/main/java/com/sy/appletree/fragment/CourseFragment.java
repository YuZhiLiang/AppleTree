package com.sy.appletree.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.CoursePkgListBean;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.preparelessons.AddLessonsActivity3;
import com.sy.appletree.preparelessons.BeiKeActivity;
import com.sy.appletree.swipemenulistview.SwipeMenu;
import com.sy.appletree.swipemenulistview.SwipeMenuCreator;
import com.sy.appletree.swipemenulistview.SwipeMenuItem;
import com.sy.appletree.swipemenulistview.SwipeMenuListView;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/10/12.
 */

/**
 * 课程
 */
public class CourseFragment extends Fragment {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_title)
    TextView mBaseTitle;
    @Bind(R.id.base_center)
    RelativeLayout mBaseCenter;
    @Bind(R.id.base_right_icon)
    ImageView mBaseRightIcon;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.base_content)
    LinearLayout mBaseContent;
    @Bind(R.id.course_search)
    EditText mCourseSearch;
    @Bind(R.id.course_search_btn)
    ImageButton mCourseSearchBtn;
    @Bind(R.id.base_search_content)
    RelativeLayout mBaseSearchContent;
    @Bind(R.id.base_top_bg)
    RelativeLayout mBaseTopBg;
    @Bind(R.id.course_list)
    SwipeMenuListView mCourseList;
    @Bind(R.id.course_content)
    RelativeLayout mCourseContent;
    private View mView;
    public static final int FROME_GET_DATA_FROM_SERVICE = 1;
    public static final int FROME_RELESE_TO_WISOM_LIB = 2;
    public static final int FROME_DELETE_COURSE = 3;

    private MainActivity.MyOnClickListener mMyOnClickListener;
    private MainActivity.OCListener mOCListener;
    private KeChengAdapter mKeChengAdapter;
    private ArrayList<CoursePkgListBean.DataBean> mCourseBeans = new ArrayList<>();
    private ArrayList<CoursePkgListBean.DataBean> mCourseBeansBackups = new ArrayList<>();

    public void setOCListener(MainActivity.OCListener OCListener) {
        mOCListener = OCListener;
    }

    public void setMyOnClickListener(MainActivity.MyOnClickListener myOnClickListener) {
        mMyOnClickListener = myOnClickListener;
    }

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // 创建“发布”项
            SwipeMenuItem openItem = new SwipeMenuItem(
                    getActivity());
            openItem.setBackground(new ColorDrawable(Color.rgb(0x51, 0xC4,
                    0xB2)));
            openItem.setWidth(dp2px(90));
            openItem.setTitle("发表到智库");
            openItem.setTitleSize(13);
            openItem.setTitleColor(Color.WHITE);
            openItem.setIcon(R.drawable.icon_upload);
            menu.addMenuItem(openItem);

            // 创建“删除”项
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getActivity());
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            deleteItem.setWidth(dp2px(90));
            deleteItem.setIcon(R.mipmap.btn_icon_close_white_n);
            deleteItem.setTitle("永久删除");
            deleteItem.setTitleSize(13);
            deleteItem.setTitleColor(Color.WHITE);
            // 将创建的菜单项添加进菜单中
            menu.addMenuItem(deleteItem);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.course_fragment_layout, container, false);
        ButterKnife.bind(this, mView);
        EventBus.getDefault().register(this);

        initView();
        initDate();
        initEvent();


        mCourseList.setMenuCreator(creator);
        //添加下部的添加课程条目
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.footview, null);
        mCourseList.addFooterView(view);
        mKeChengAdapter = new KeChengAdapter();
        mCourseList.setAdapter(mKeChengAdapter);

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.course_add);
        /**
         * menu点击事件
         */
        mCourseList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        ReleaseToWisomLib(position);
                        break;
                    case 1:
                        delteCoursePackage(position);
                        break;
                    default:
                        break;
                }

            }
        });

        /**
         * 点击事件
         */
        mCourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "条目点击了", Toast.LENGTH_SHORT).show();
                //主页第一页的条目被点击跳转到具体的备课页面
                Intent intent = new Intent(getActivity(), BeiKeActivity.class);
                CoursePkgListBean.DataBean dataBean = mCourseBeans.get(position);

                /*mYuLan = intent.getBooleanExtra("yuLan", false);//是不是预览
                mDanxuan = intent.getBooleanExtra("isSingle", true);//是不是标准教材（非多选）
                kecheng = intent.getStringExtra("Name");////课程包名字
                kemu = intent.getStringExtra("Subject");//科目
                jiaocai = intent.getStringExtra("Book");//哪个版本
                nianji = intent.getStringExtra("Grad");//年级
                mID = intent.getStringExtra("courseID");//课程包的ID
                mIsNewCourse = intent.getBooleanExtra("isNewCourse", false);//是不是新创建的*/

                intent.putExtra("yuLan", false);
                intent.putExtra("isSingle", true);
                intent.putExtra("Name", dataBean.getName());
                intent.putExtra("Subject", dataBean.getSubject());
                intent.putExtra("Book", dataBean.getVersion());
                intent.putExtra("Grad", dataBean.getUseGrade());
                intent.putExtra("courseID", String.valueOf(dataBean.getCourseId()));
                intent.putExtra("isNewCourse", false);
                startActivity(intent);
            }
        });
        /**
         * 侧滑监听
         */
        mCourseList.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddLessonsActivity3.class);
                startActivity(intent);
            }
        });


        return mView;


    }

    //拿到添加课程成功的回传事件刷新UI
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CoursePkgListBean.DataBean coursePkgBean) {
        getDataFromServer();
    }

    private void delteCoursePackage(int position) {
        int courseId = mCourseBeans.get(position).getCourseId();
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.DelteCoursePackage.PROTOCOL)
                .append(AppleTreeUrl.DelteCoursePackage.PARAMS_COURSE_PKG_ID + "=")
                .append(String.valueOf(courseId) + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());

        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new CourseFragmentStringCallBack(FROME_DELETE_COURSE, position));

    }

    private void ReleaseToWisomLib(int position) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.ReleaseToWisomLib.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession() + "&")
                .append(AppleTreeUrl.ReleaseToWisomLib.PARAMS_COURSE_PKG_ID + "=")
                .append(mCourseBeans.get(position).getCourseId());
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new CourseFragmentStringCallBack(FROME_RELESE_TO_WISOM_LIB, 0));
    }


    private void initView() {

    }

    private void initDate() {
        getDataFromServer();
    }

    private void getDataFromServer() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetCoursePackage.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new CourseFragmentStringCallBack(FROME_GET_DATA_FROM_SERVICE, 0));
    }

    class CourseFragmentStringCallBack extends StringCallback {
        int fromWhere;
        //位置，只有删除的时候才用得上，其他的两个方法用0就好了
        private int position;

        public CourseFragmentStringCallBack(int fromWhere, int position) {
            this.fromWhere = fromWhere;
            this.position = position;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            switch (fromWhere) {
                case 1:
                    parseGetDataFromService(response, gson);
                    break;
                case 2:
                    parasrReleaseToWisomLib(response, gson);
                    break;
                case 3:
                    paraseDeleteCoursePkg(response, gson, position);
                    break;
            }
        }
    }

    //删除课程包
    private void paraseDeleteCoursePkg(String response, Gson gson, int position) {
        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
        Log.e(getClass().getSimpleName(), numberVavlibleBean.toString());
        if (numberVavlibleBean.getStatus().equals("y")) {
            toast("删除成功");
            mCourseBeans.remove(position);
            mKeChengAdapter.notifyDataSetChanged();
        } else {
            toast(numberVavlibleBean.getInfo());
        }
    }

    //发表到智库
    private void parasrReleaseToWisomLib(String response, Gson gson) {
        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);

        if (numberVavlibleBean.getStatus().equals("y")) {
            toast("发表成功");
        } else {
            toast(numberVavlibleBean.getInfo());
        }
    }

    private void parseGetDataFromService(String response, Gson gson) {

        CoursePkgListBean coursePkgListBean = gson.fromJson(response, CoursePkgListBean.class);
        if (coursePkgListBean.getStatus().equals("y")) {
            getDataFromServerSuccess(coursePkgListBean);
        } else {
            getDataFromServerFaild(coursePkgListBean);
        }
    }

    private void getDataFromServerSuccess(CoursePkgListBean coursePkgListBean) {
        if (coursePkgListBean.getData() != null) {
            if (!mCourseBeans.isEmpty()) {
                mCourseBeans.clear();
            }
            mCourseBeans.addAll(coursePkgListBean.getData());
            mKeChengAdapter.notifyDataSetChanged();
        }

    }

    private void getDataFromServerFaild(CoursePkgListBean coursePkgListBean) {
        toast(coursePkgListBean.getInfo());
    }

    public void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private void initEvent() {
        //光标可见
        /*mCourseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCourseSearch.setCursorVisible(true);
            }
        });*/
        mCourseSearch.addTextChangedListener(new CourseSearchWatcher());

        //个人中心
        mBaseLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyOnClickListener.onClick();
            }
        });

        mBaseRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddLessonsActivity3.class);
                Log.e(getClass().getSimpleName(), "跳转到新增课程页面");
                startActivity(intent);
            }
        });
    }


    // 将dp转换为px
    private int dp2px(int value) {
        // 第一个参数为我们待转的数据的单位，此处为 dp（dip）
        // 第二个参数为我们待转的数据的值的大小
        // 第三个参数为此次转换使用的显示量度（Metrics），它提供屏幕显示密度（density）和缩放信息
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    public class KeChengAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCourseBeans.size() == 0 ? 0 : mCourseBeans.size();
        }

        @Override
        public CoursePkgListBean.DataBean getItem(int position) {
            return mCourseBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_main_item, null);
                viewHolder.kemu = (Button) convertView.findViewById(R.id.shouye_kemu);
                viewHolder.zhiku = (Button) convertView.findViewById(R.id.shouye_zhiku);
                viewHolder.mingcheng = (TextView) convertView.findViewById(R.id.shouye_mingcheng);
                viewHolder.nianji = (TextView) convertView.findViewById(R.id.shouye_nianji);
                viewHolder.jiaocai = (TextView) convertView.findViewById(R.id.shouye_banben);
                viewHolder.shijian = (TextView) convertView.findViewById(R.id.shouye_shijian);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CoursePkgListBean.DataBean dataBean = mCourseBeans.get(position);
            viewHolder.jiaocai.setText(dataBean.getVersion());
            viewHolder.kemu.setText(dataBean.getSubject());
            viewHolder.nianji.setText(dataBean.getUseGrade());
            viewHolder.shijian.setText(dataBean.getCreateDateStr());
            viewHolder.mingcheng.setText(dataBean.getName());
            if (dataBean.getResource().equals("C")) {
                viewHolder.zhiku.setText("原创");
            } else {
                viewHolder.zhiku.setText("智库");
            }

            return convertView;
        }

        class ViewHolder {
            Button kemu;
            Button zhiku;
            TextView mingcheng;
            TextView nianji;
            TextView jiaocai;
            TextView shijian;

        }
    }

    class CourseSearchWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.e(getClass().getSimpleName(), "检测到变化前 = " + s.toString());
            if (s.toString().length() == 0) {
                mCourseBeansBackups.addAll(mCourseBeans);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e(getClass().getSimpleName(), "检测到变化 = " + s.toString());
            if (s.toString().trim().length() == 0) {
                mCourseBeans.clear();
                mCourseBeans.addAll(mCourseBeansBackups);
            }else {
                ArrayList<CoursePkgListBean.DataBean> mSearchPkgListBeans = new ArrayList<>();
                for (CoursePkgListBean.DataBean PkgListBean : mCourseBeansBackups) {
                    if (PkgListBean.getName().startsWith(s.toString())) {
                        Log.e(getClass().getSimpleName(), "PkgListBean.getName() = " + PkgListBean.getName());
                        mSearchPkgListBeans.add(PkgListBean);
                    }
                }
                mCourseBeans.clear();
                mCourseBeans.addAll(mSearchPkgListBeans);
            }
            mKeChengAdapter.notifyDataSetChanged();
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.e(getClass().getSimpleName(), "检测到变化后 = " + s.toString());
            if (s.toString().trim().length() == 0) {
                mCourseBeans.clear();
                mCourseBeans.addAll(mCourseBeansBackups);
                mCourseBeansBackups.clear();
                mKeChengAdapter.notifyDataSetChanged();
            }
        }
    }

}
