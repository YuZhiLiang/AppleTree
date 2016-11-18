package com.sy.appletree.myclasses;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.bean.StudentsBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.ToastUtils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.sy.appletree.views.MyGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class AddStudentActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.student_search)
    EditText mStudentSearch;
    @Bind(R.id.base_search_btn)
    ImageButton mBaseSearchBtn;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.student_list)
    ListView mStudentList;

    private ArrayList<StudentsBean.DataBean> mStudentsBeans = new ArrayList<>();
    private ArrayList<StudentsBean.DataBean> mStudentsBeansBackUps = new ArrayList<>();
    private ArrayList<ArrayList<StudentsBean.DataBean>> mGroupList = new ArrayList<>();

    private StudentAdapter mStudentAdapter;
    private List<String> mList = new ArrayList<>();
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private String mClassID;
    private int GET_STUDENT_LIST = 1;
    private int ADD_STUDENT = 2;
    private int EDIT_STUDENT = 3;
    private int GET_STUDENT_INFO = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ButterKnife.bind(this);
        getIntentData();
        getDataFromService();
        initEvent();
    }

    private void initEvent() {
        mStudentAdapter = new StudentAdapter();
        mStudentList.setAdapter(mStudentAdapter);
        mStudentSearch.addTextChangedListener(new SearchStudentListener());
    }

    private void getDataFromService() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetStudents.PROTOCOL)
                .append(AppleTreeUrl.GetStudents.PARAMS_CLASS_ID)
                .append(mClassID + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());

        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new addStudentCallBack(GET_STUDENT_LIST));
    }

    class addStudentCallBack extends StringCallback {
        int mEventType;
        EditText name;
        EditText studentID;
        EditText mobile;


        public addStudentCallBack(int eventType) {
            this.mEventType = eventType;
        }

        public addStudentCallBack(int eventType, EditText name, EditText studentID, EditText mobile) {
            this.mEventType = eventType;
            this.name = name;
            this.studentID = studentID;
            this.mobile = mobile;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            switch (mEventType) {
                case 1:
                    paraseGetStudentsResponse(response, gson);
                    break;
                case 2:
                    paraseAddStudentResponse(response, gson);
                    break;
                case 3:
                    paraseEditStudentResponse(response, gson);
                    break;
                case 4:
                    paraseGetStudentInfoResponse(response, gson);
                    break;
            }
        }

        private void paraseEditStudentResponse(String response, Gson gson) {
            Log.e(getClass().getSimpleName(), response);
            NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
            if (numberVavlibleBean.getStatus().equals("y")) {
                upDateStudentSuccess();
            } else {
                ToastUtils.toast(numberVavlibleBean.getInfo());
            }
        }

        private void paraseGetStudentInfoResponse(String response, Gson gson) {

        }

        private void paraseAddStudentResponse(String response, Gson gson) {

            NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
            if (numberVavlibleBean.getStatus().equals("y")) {
                onAddStudentSuccess();
            } else {
                onAddStudentFailed(numberVavlibleBean);

            }
        }

        private void paraseGetStudentsResponse(String response, Gson gson) {
            Log.e(getClass().getSimpleName(), response);
            if (!response.contains("[]")) {
                StudentsBean studentsBean = gson.fromJson(response, StudentsBean.class);
                if (studentsBean.getStatus().equals("y")) {
                    onGetStudentsFromServieSuccess(studentsBean.getData());
                } else {
                    toast(studentsBean.getInfo());
                }
            } else {
                toast("该班级暂无学生");
            }
        }
    }

    private void upDateStudentSuccess() {
        popupWindow.dismiss();
        getDataFromService();
    }

    private void onAddStudentFailed(NumberVavlibleBean numberVavlibleBean) {
        toast(numberVavlibleBean.getInfo());
    }

    private void onAddStudentSuccess() {
        getDataFromService();
        popupWindow.dismiss();
    }

    private void onGetStudentsFromServieSuccess(List<StudentsBean.DataBean> data) {
        if (!mStudentsBeans.isEmpty()) {
            mStudentsBeans.clear();
        }
        if (!mGroupList.isEmpty()) {
            mGroupList.clear();
        }
        if (!data.isEmpty()) {
            mStudentsBeans.addAll(data);
            sortGroup();
        }
    }

    //排序
    private void sortGroup() {
        if (mStudentsBeans.size() != 0) {
            Collections.sort(mStudentsBeans, new Comparator<StudentsBean.DataBean>() {
                @Override
                public int compare(StudentsBean.DataBean lhs, StudentsBean.DataBean rhs) {
                    return lhs.getStartChar().compareTo(rhs.getStartChar());
                }
            });
            cuttingGroup();
        }
    }

    //分组
    private void cuttingGroup() {
        if (!mGroupList.isEmpty()) {
            mGroupList.clear();
        }
        for (int i = 0; i < mStudentsBeans.size(); i++) {
            if (mGroupList.isEmpty()) {
                ArrayList<StudentsBean.DataBean> studentStartCharList = new ArrayList<>();
                studentStartCharList.add(mStudentsBeans.get(i));
                mGroupList.add(studentStartCharList);
            } else {
                ArrayList<StudentsBean.DataBean> dataBeen = mGroupList.get(mGroupList.size() - 1);
                String startChar = dataBeen.get(0).getStartChar();
                String startChar1 = mStudentsBeans.get(i).getStartChar();
                if (startChar.equals(startChar1)) {
                    dataBeen.add(mStudentsBeans.get(i));
                } else {
                    ArrayList<StudentsBean.DataBean> studentStartCharList = new ArrayList<>();
                    studentStartCharList.add(mStudentsBeans.get(i));
                    mGroupList.add(studentStartCharList);
                }
            }
        }
        mStudentAdapter.notifyDataSetChanged();
        print();
    }

    //打印测试
    private void print() {
        for (ArrayList<StudentsBean.DataBean> dataBeen1 : mGroupList) {
            for (StudentsBean.DataBean dataBean2 : dataBeen1) {
                Log.e(dataBean2.getStartChar() + "=", dataBean2.getStudentName());
            }
        }
    }

    class SearchStudentListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (s.toString().length() == 0) {
                Log.e(getClass().getSimpleName(), "检测到改动前");
                mStudentsBeansBackUps.addAll(mStudentsBeans);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e(getClass().getSimpleName(), "检测到改动");
            if (s.toString().trim().length() == 0) {
                mStudentsBeans.clear();
                mStudentsBeans.addAll(mStudentsBeansBackUps);
                cuttingGroup();
                mStudentAdapter.notifyDataSetChanged();
            } else {
                ArrayList<StudentsBean.DataBean> mSearchSchoolListBeans = new ArrayList<>();
                for (StudentsBean.DataBean studentBean : mStudentsBeansBackUps) {
                    if (studentBean.getStudentName().startsWith(s.toString())) {
                        mSearchSchoolListBeans.add(studentBean);
                    }
                }
                mStudentsBeans.clear();
                mStudentsBeans.addAll(mSearchSchoolListBeans);
                cuttingGroup();
                mStudentAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.e(getClass().getSimpleName(), "改动后");
            if (s.toString().trim().length() == 0) {
                mStudentsBeans.clear();
                mStudentsBeans.addAll(mStudentsBeansBackUps);
                mStudentsBeansBackUps.clear();
                cuttingGroup();
                mStudentAdapter.notifyDataSetChanged();
            }
        }
    }

    public void toast(String message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mClassID = intent.getStringExtra("classID");
    }

    @OnClick({R.id.base_left, R.id.base_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                Intent intent = new Intent();
                intent.putExtra("xuesheng", mStudentsBeans.size());
                setResult(91, intent);
                finish();
                break;
            case R.id.base_right:
                initPopWindow("add");
                openPopWindow();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("xuesheng", mStudentsBeans.size());
        setResult(91, intent);
        finish();
        super.onBackPressed();
    }

    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    private View contentView;
    private PopupWindow popupWindow;

    private void initPopWindow(String tag) {
        initPopWindow(tag, null);
    }

    private void initPopWindow(final String tag, final String ServiceStudentID) {
        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(AddStudentActivity.this).inflate(
                R.layout.pop_student, null);
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
        Button save = (Button) contentView.findViewById(R.id.save);
        TextView textView = (TextView) contentView.findViewById(R.id.querentuichu);
        final EditText editText1 = (EditText) contentView.findViewById(R.id.stu_name);
        final EditText editText2 = (EditText) contentView.findViewById(R.id.stu_xueji);
        final EditText editText3 = (EditText) contentView.findViewById(R.id.stu_phone);
        if (tag.equals("add")) {
            textView.setText("新增学生");
        } else if (tag.equals("edit")) {
            textView.setText("修改学生信息");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                String name = editText1.getText().toString();
                String studentID = editText2.getText().toString();
                String mobile = editText3.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(AddStudentActivity.this, "请填写学生姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (name.matches("[0-9]+.*")) {
                    toast("请输入合法的姓名");
                    return;
                }
                if (TextUtils.isEmpty(studentID)) {
                    Toast.makeText(AddStudentActivity.this, "请填写学籍号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    Toast.makeText(AddStudentActivity.this, "请填写手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (tag.equals("add")) {
                    //新增接口
                    addStudent2Class(name, studentID, mobile);
                } else if (tag.equals("edit")) {
                    //修改接口
                    editStudent(name, studentID, mobile, ServiceStudentID);//第一个ID是学号，第二个ID是服务器上学生的ID
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

    private void editStudent(String name, String studentID, String mobile, String serviceStudentID) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.UpdateStudent.PROTOCOL)
                .append(AppleTreeUrl.UpdateStudent.PARAMS_NAME)
                .append(name + "&")
                .append(AppleTreeUrl.UpdateStudent.PARAMS_MOBILE)
                .append(mobile + "&")
                .append(AppleTreeUrl.UpdateStudent.PARAMS_STUDENT_ID)
                .append(serviceStudentID + "&")
                .append(AppleTreeUrl.UpdateStudent.PARAMS_STUDENT_NUM)
                .append(studentID + "&")
                .append(AppleTreeUrl.UpdateStudent.PARAMS_CLASS_ID)
                .append(mClassID + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());

        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new addStudentCallBack(EDIT_STUDENT));
    }

    private void getStudentInfoAndSetHint(EditText editText1, EditText editText2, EditText editText3, String studentID) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                /*.append(AppleTreeUrl.GetStudent.PROTOCOL)
                .append(AppleTreeUrl.GetStudent.PARAMS_STUDENT_ID)*/
                .append(studentID + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new addStudentCallBack(GET_STUDENT_INFO, editText1, editText2, editText3));
    }

    private void addStudent2Class(String name, String studentID, String mobile) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.AddStudent.PROTOCOL)
                .append(AppleTreeUrl.AddStudent.PARAMS_NAME)
                .append(name + "&")
                .append(AppleTreeUrl.AddStudent.PARAMS_CLASS_ID)
                .append(mClassID + "&")
                .append(AppleTreeUrl.AddStudent.PARAMS_STUDENT_NUM)
                .append(studentID + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession() + "&")
                .append(AppleTreeUrl.AddStudent.PARAMS_MOBILE)
                .append(mobile);
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new addStudentCallBack(ADD_STUDENT));
    }

    private void screenBrighter() {
        WindowManager.LayoutParams params = AddStudentActivity.this.getWindow().getAttributes();
        params.alpha = 1f;
        AddStudentActivity.this.getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = AddStudentActivity.this.getWindow().getAttributes();
        params.alpha = 0.5f;
        AddStudentActivity.this.getWindow().setAttributes(params);
    }

    public class StudentAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mGroupList.size() == 0 ? 0 : mGroupList.size();//分组的数量
        }

        @Override
        public ArrayList<StudentsBean.DataBean> getItem(int position) {
            if (mGroupList.isEmpty()) {
                return null;
            }
            return mGroupList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_group_item, null);
                holder.mTextView = (TextView) convertView.findViewById(R.id.shouZiMu);
                holder.mMyGridView = (MyGridView) convertView.findViewById(R.id.myGrid);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextView.setText(mGroupList.get(position).get(0).getStartChar());
            MyGridAdapter myGridAdapter = new MyGridAdapter(mGroupList.get(position));
            holder.mMyGridView.setAdapter(myGridAdapter);
            holder.mMyGridView.setOnItemClickListener(new onStudentClickListener());
            Log.e("getview", "getView");
            return convertView;
        }

        class ViewHolder {
            TextView mTextView;
            MyGridView mMyGridView;
        }

    }

    public class MyGridAdapter extends BaseAdapter {

        private List<StudentsBean.DataBean> mStringList;

        public MyGridAdapter(List<StudentsBean.DataBean> stringList) {
            mStringList = stringList;
        }

        @Override
        public int getCount() {
            return mStringList.size();
        }

        @Override
        public StudentsBean.DataBean getItem(int position) {
            return mStringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return mStringList.get(position).getStudentId();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gridstu_item, null);
                viewHolder.mTextView = (TextView) convertView.findViewById(R.id.stu_btn);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.mTextView.setText(mStringList.get(position).getStudentName());
            viewHolder.mTextView.setTag(String.valueOf(mStringList.get(position).getStudentId()));
            return convertView;
        }

        class ViewHolder {
            TextView mTextView;
        }
    }

    //学生条目的点击事件监听
    class onStudentClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e(getClass().getSimpleName(), "接收到点击事件,学生得ID是：" + id);
            initPopWindow("edit", String.valueOf(id));
            openPopWindow();
        }
    }
}
