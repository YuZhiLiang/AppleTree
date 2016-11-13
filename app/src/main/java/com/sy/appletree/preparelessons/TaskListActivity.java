package com.sy.appletree.preparelessons;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.TaskListBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.swipemenulistview.SwipeMenu;
import com.sy.appletree.swipemenulistview.SwipeMenuCreator;
import com.sy.appletree.swipemenulistview.SwipeMenuItem;
import com.sy.appletree.swipemenulistview.SwipeMenuListView;
import com.sy.appletree.utils.Const;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 预览课目任务列表
 */

public class TaskListActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.swip_task_list)
    SwipeMenuListView mSwipTaskList;
    @Bind(R.id.task_have)
    LinearLayout mTaskHave;
    @Bind(R.id.task_empty)
    RelativeLayout mTaskEmpty;
    @Bind(R.id.task_btn)
    Button mTaskBtn;
    @Bind(R.id.task_title)
    TextView mTaskTitle;

    private SwipeTaskAdapter mSwipeTaskAdapter;
    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
//            // 创建“打开”项
//            SwipeMenuItem openItem = new SwipeMenuItem(
//                    getApplicationContext());
//            openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
//                    0xCE)));
//            openItem.setWidth(dp2px(90));
//            openItem.setTitle("Open");
//            openItem.setTitleSize(18);
//            openItem.setTitleColor(Color.WHITE);
            // 将创建的菜单项添加进菜单中
//            menu.addMenuItem(openItem);

            // 创建“删除”项
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getApplicationContext());
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            deleteItem.setWidth(dp2px(90));
            deleteItem.setIcon(R.mipmap.btn_icon_close_white_n);
            // 将创建的菜单项添加进菜单中
            menu.addMenuItem(deleteItem);
        }
    };

    private ArrayList<TaskListBean.DataBean> DataBeans = new ArrayList<>();
    private boolean mYuLan;
    private boolean mIsNewCourse;
    private String mCourseID;
    private int mRequestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        ButterKnife.bind(this);
        getIntentData();
        getData();
        if (!mYuLan) {
            mTaskTitle.setText("预览任务列表");
            mSwipTaskList.setMenuCreator(creator);
        }
        mSwipeTaskAdapter = new SwipeTaskAdapter(DataBeans);
        mSwipTaskList.setAdapter(mSwipeTaskAdapter);
        initEvent();

    }

    private void initEvent() {
        /**
         * menu点击事件
         */
        mSwipTaskList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                Toast.makeText(TaskListActivity.this, "假设删除了", Toast.LENGTH_SHORT).show();
            }
        });
        mSwipTaskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });
        /**
         * 编辑事件,当预览任务列表的条目被点击时，跳转到编辑该任务条目页面（备课主题，可添加附件和评价标准）
         */
        mSwipTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TaskListActivity.this, CreateTaskActivity.class);
                intent.putExtra("tag", "edit");
                intent.putExtra("weizhi", position);
                //传值过去

                startActivityForResult(intent, 1);
            }
        });
        /**
         * 侧滑监听
         */
        mSwipTaskList.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
    }

    private void getData() {
        if (mIsNewCourse) {
            initEmptyData();
        } else {
            mTaskEmpty.setVisibility(View.GONE);
            mTaskHave.setVisibility(View.VISIBLE);
            getDataFromeService();
        }
    }

    private void initEmptyData() {
        mTaskEmpty.setVisibility(View.VISIBLE);
        mTaskHave.setVisibility(View.GONE);
        Log.e(getClass().getSimpleName(), "空页面");
    }

    private void getDataFromeService() {
        Log.e(getClass().getSimpleName(), "来拿服务器数据");
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetTaskList.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession() + "&")
                .append(AppleTreeUrl.GetTaskList.PARAMS_COURSE_ID + "=")
                .append(mCourseID);
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new TaskListActivityStringCallBack());
    }

    //请求服务器的监听回调
    class TaskListActivityStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            TaskListBean taskListBean = gson.fromJson(response, TaskListBean.class);
            DataBeans.addAll(taskListBean.getData());
            mSwipeTaskAdapter.notifyDataSetChanged();
        }
    }

    private void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mYuLan = intent.getBooleanExtra("yuLan", false);
        //是不是新任务
        mIsNewCourse = intent.getBooleanExtra("isNewCourse", false);
        //拿到小课程ID
        if(Const.isDeBug) {
            mCourseID = "68";
        }else {
            mCourseID = intent.getStringExtra("courseID");
        }
    }

    @OnClick({R.id.base_left, R.id.task_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                finish();
                break;
            case R.id.task_btn:
                addNewTask();
                break;
        }
    }

    //添加新任务
    private void addNewTask() {
        Intent intent = new Intent(TaskListActivity.this, CreateTaskActivity.class);
        intent.putExtra("tag", "add");
        intent.putExtra("courseID", mCourseID);
        startActivityForResult(intent, mRequestCode);
    }


    // 将dp转换为px
    private int dp2px(int value) {
        // 第一个参数为我们待转的数据的单位，此处为 dp（dip）
        // 第二个参数为我们待转的数据的值的大小
        // 第三个参数为此次转换使用的显示量度（Metrics），它提供屏幕显示密度（density）和缩放信息
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }

    //另一种将dp转换为px的方法
    private int dp2px(float value) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }


    public class SwipeTaskAdapter extends BaseAdapter {

        private ArrayList<TaskListBean.DataBean> mList;

        public SwipeTaskAdapter(ArrayList<TaskListBean.DataBean> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
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
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, null);
                viewHolder.mTextView = (TextView) convertView.findViewById(R.id.taskitem_txt);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.mTextView.setText(mList.get(position).getTaskName());

            return convertView;
        }

        class ViewHolder {
            TextView mTextView;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(getClass().getSimpleName(), requestCode + "-" + resultCode + data);
        if (requestCode == mRequestCode && resultCode == RESULT_OK) {
            Log.e(getClass().getSimpleName(), "拿到ActivityResult响应");
//            Intent intent = getIntent();
            String taskName = data.getStringExtra("getTaskName");
            String taskID = data.getStringExtra("getTaskID");
            TaskListBean.DataBean taskbean = new TaskListBean.DataBean();
            taskbean.setTaskName(taskName);
            taskbean.setTaskId(Integer.valueOf(taskID));
            mTaskEmpty.setVisibility(View.GONE);
            mTaskHave.setVisibility(View.VISIBLE);
            DataBeans.add(taskbean);
            mSwipeTaskAdapter.notifyDataSetChanged();

        } else {
            Log.e(getClass().getSimpleName(), "获取返回值失败");
        }


//            int weizhi = data.getIntExtra("weizhi", -1);
//            mStrings.remove(weizhi);
//            mSwipeTaskAdapter.notifyDataSetChanged();
//        }

    }
}
