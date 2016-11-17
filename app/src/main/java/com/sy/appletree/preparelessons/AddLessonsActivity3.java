package com.sy.appletree.preparelessons;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.bean.CoursePkgListBean;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 新增课程
 */

public class AddLessonsActivity3 extends AppCompatActivity {
    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.subject_name)
    EditText mSubjectName;
    @Bind(R.id.textView)
    TextView mTextView;
    @Bind(R.id.lessons_1)
    CheckBox mLessons1;
    @Bind(R.id.lessons_2)
    CheckBox mLessons2;
    @Bind(R.id.lessons_3)
    CheckBox mLessons3;
    @Bind(R.id.lessons_4)
    CheckBox mLessons4;
    @Bind(R.id.lessons_5)
    CheckBox mLessons5;
    @Bind(R.id.lessons_6)
    CheckBox mLessons6;
    @Bind(R.id.lessons_7)
    CheckBox mLessons7;
    @Bind(R.id.lessons_8)
    CheckBox mLessons8;
    @Bind(R.id.lessons_9)
    CheckBox mLessons9;
    @Bind(R.id.subject_1)
    CheckBox mSubject1;
    @Bind(R.id.subject_2)
    CheckBox mSubject2;
    @Bind(R.id.subject_3)
    CheckBox mSubject3;
    @Bind(R.id.subject_4)
    CheckBox mSubject4;
    @Bind(R.id.subject_5)
    CheckBox mSubject5;
    @Bind(R.id.subject_6)
    CheckBox mSubject6;
    @Bind(R.id.subject_7)
    CheckBox mSubject7;
    @Bind(R.id.subject_8)
    CheckBox mSubject8;
    @Bind(R.id.grade_1)
    CheckBox mGrade1;
    @Bind(R.id.grade_2)
    CheckBox mGrade2;
    @Bind(R.id.grade_3)
    CheckBox mGrade3;
    @Bind(R.id.grade_4)
    CheckBox mGrade4;
    @Bind(R.id.grade_5)
    CheckBox mGrade5;
    @Bind(R.id.grade_6)
    CheckBox mGrade6;
    @Bind(R.id.grade_7)
    CheckBox mGrade7;
    @Bind(R.id.grade_8)
    CheckBox mGrade8;
    @Bind(R.id.grade_9)
    CheckBox mGrade9;
    @Bind(R.id.grade_10)
    CheckBox mGrade10;
    @Bind(R.id.grade_11)
    CheckBox mGrade11;
    @Bind(R.id.grade_12)
    CheckBox mGrade12;
    @Bind(R.id.linearLayout)
    LinearLayout mLinearLayout;
    @Bind(R.id.save)
    TextView mSave;
    private CheckBox mCurrentVersion;
    private ArrayList<CheckBox> mSubjectList = new ArrayList<>();
    private ArrayList<CheckBox> mGradList = new ArrayList<>();
    private boolean mIsCustom;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lessons2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.lessons_1, R.id.lessons_2, R.id.lessons_3, R.id.lessons_4, R.id.lessons_5, R.id.lessons_6, R.id.lessons_7, R.id.lessons_8, R.id.lessons_9, R.id.subject_1, R.id.subject_2, R.id.subject_3, R.id.subject_4, R.id.subject_5, R.id.subject_6, R.id.subject_7, R.id.subject_8, R.id.grade_1, R.id.grade_2, R.id.grade_3, R.id.grade_4, R.id.grade_5, R.id.grade_6, R.id.grade_7, R.id.grade_8, R.id.grade_9, R.id.grade_10, R.id.grade_11, R.id.grade_12, R.id.save})
    public void onClick(View view) {
        hideKeyBord();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.lessons_1:
                standardVersionChecked(view);
                break;
            case R.id.lessons_2:
                standardVersionChecked(view);
                break;
            case R.id.lessons_3:
                standardVersionChecked(view);
                break;
            case R.id.lessons_4:
                standardVersionChecked(view);
                break;
            case R.id.lessons_5:
                standardVersionChecked(view);
                break;
            case R.id.lessons_6:
                standardVersionChecked(view);
                break;
            case R.id.lessons_7:
                standardVersionChecked(view);
                break;
            case R.id.lessons_8:
                standardVersionChecked(view);
                break;
            case R.id.lessons_9:
                customVersionChecked(view);
                break;
            case R.id.subject_1:
                addSubject(view);
                break;
            case R.id.subject_2:
                addSubject(view);
                break;
            case R.id.subject_3:
                addSubject(view);
                break;
            case R.id.subject_4:
                addSubject(view);
                break;
            case R.id.subject_5:
                addSubject(view);
                break;
            case R.id.subject_6:
                addSubject(view);
                break;
            case R.id.subject_7:
                addSubject(view);
                break;
            case R.id.subject_8:
                addSubject(view);
                break;
            case R.id.grade_1:
                addGrade(view);
                break;
            case R.id.grade_2:
                addGrade(view);
                break;
            case R.id.grade_3:
                addGrade(view);
                break;
            case R.id.grade_4:
                addGrade(view);
                break;
            case R.id.grade_5:
                addGrade(view);
                break;
            case R.id.grade_6:
                addGrade(view);
                break;
            case R.id.grade_7:
                addGrade(view);
                break;
            case R.id.grade_8:
                addGrade(view);
                break;
            case R.id.grade_9:
                addGrade(view);
                break;
            case R.id.grade_10:
                addGrade(view);
                break;
            case R.id.grade_11:
                addGrade(view);
                break;
            case R.id.grade_12:
                addGrade(view);
                break;
            case R.id.save:
                add2Service();
                break;
        }
    }


    //标准教材被选中时
    private void standardVersionChecked(View view) {
        if (mCurrentVersion != null && mCurrentVersion != view) {
            mCurrentVersion.setChecked(false);
        }
        mCurrentVersion = (CheckBox) view;
        mIsCustom = false;
        setLastItemChecked(mSubjectList);
        setLastItemChecked(mGradList);
    }


    //保留科目和年级最好一次被选中的条目
    private void setLastItemChecked(ArrayList<CheckBox> checkedList) {
        if (checkedList.size() > 1) {
            CheckBox checkBox = checkedList.get(checkedList.size() - 1);
            for (CheckBox box : checkedList) {
                if (box != checkBox) {
                    box.setChecked(false);
                }
            }
            checkedList.clear();
            checkedList.add(checkBox);
        }
    }


    //自定义教材被选中时
    private void customVersionChecked(View view) {
        if (mCurrentVersion != null && mCurrentVersion != view) {
            mCurrentVersion.setChecked(false);
        }
        mCurrentVersion = (CheckBox) view;
        mIsCustom = true;
    }


    //点击了选择科目
    private void addSubject(View view) {
        CheckBox box = (CheckBox) view;
        if (!mIsCustom) {
            for (CheckBox checkBox : mSubjectList) {
                checkBox.setChecked(false);
            }
            mSubjectList.clear();
        }
        box.setChecked(true);
        mSubjectList.add((CheckBox) view);
    }


    //点击了选择年级
    private void addGrade(View view) {
        CheckBox box = (CheckBox) view;
        if (!mIsCustom) {
            for (CheckBox checkBox : mGradList) {
                checkBox.setChecked(false);
            }
            mGradList.clear();
        }
        box.setChecked(true);
        mGradList.add((CheckBox) view);
    }

    private void hideKeyBord() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSubjectName.getWindowToken(), 0);

    }

    private void toast(String message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private StringBuffer getAppend(ArrayList<CheckBox> list) {
        StringBuffer url = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != list.get(list.size() - 1)) {
                url.append(list.get(i).getTag() + ",");
            } else {
                url.append(list.get(i).getTag());
            }
        }
        return url;
    }

    private void add2Service() {
        String title = mSubjectName.getText().toString().trim();
        if (TextUtils.isEmpty(title)) {
            toast("请输入课程名称");
            return;
        }
        if (mCurrentVersion == null) {
            toast("请选择教材版本");
        }
        if (mSubjectList.isEmpty()) {
            toast("请选择科目");
        }
        if (mGradList.isEmpty()) {
            toast("请选择年级");
        }
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.AddCoursePackage.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession() + "&")
                .append(AppleTreeUrl.AddCoursePackage.PARAMS_NAME + "=")
                .append(title + "&")
                .append(AppleTreeUrl.AddCoursePackage.PARAMS_VERSION + "=")
                .append(mCurrentVersion.getTag() + "&")
                .append(AppleTreeUrl.AddCoursePackage.PARAMS_USUBJECT + "=")
                .append(getAppend(mSubjectList) + "&")
                .append(AppleTreeUrl.AddCoursePackage.PARAMS_USE_GRADE + "=")
                .append(getAppend(mGradList));
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new AddlesssonCallBack());
    }

    class AddlesssonCallBack extends StringCallback {

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
                AddLessonsSuccess(numberVavlibleBean.getData().toString());
                CoursePkgListBean.DataBean coursePkgBean = new CoursePkgListBean.DataBean();
                EventBus.getDefault().post(coursePkgBean);
            } else {
                toast(numberVavlibleBean.getInfo());
            }
        }
    }

    private void AddLessonsSuccess(String coursePkgID) {
        //是不单选（标准课程都是单选）
        boolean isSingle = !mIsCustom;
        //课程的名称
        String keCheng = mSubjectName.getText().toString().trim();
        //教材版本
        String Verson = mCurrentVersion.getText().toString();
        //年级的拼接
        String Grad = getDisplayString(mGradList);
        //创建的课程的ID
        String LessonID = coursePkgID;

        Intent intent = new Intent(this, BeiKeActivity.class);
        intent.putExtra("isSingle", isSingle);
        Log.e(getClass().getSimpleName(), isSingle ? "single" : "coumost");
        //标准课程的情况下的科目名称
        intent.putExtra("Subject", getDisplayString(mSubjectList));
        intent.putExtra("Name", keCheng);
        intent.putExtra("Book", Verson);
        intent.putExtra("Grad", getDisplayString(mGradList));
        intent.putExtra("ID", LessonID);
        intent.putExtra("isNewCourse", true);
        startActivity(intent);
        finish();
    }

    private String getDisplayString(ArrayList<CheckBox> list) {
        StringBuffer name = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != list.get(list.size() - 1)) {
                name.append(list.get(i).getText().toString() + "/");
            } else {
                name.append(list.get(i).getText().toString());
            }
        }
        return name.toString();
    }
}