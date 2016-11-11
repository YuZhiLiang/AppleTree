package com.sy.appletree.preparelessons;

import android.content.Context;
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
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 新增课程
 */

public class AddLessonsActivity2 extends AppCompatActivity {
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
    private ArrayList<CheckBox> mLessonList;
    private boolean mLessonVersion;
    private StringBuffer mGradUrl;
    private String mSubjectName1Display;
    private boolean mIsCustom;
    private String SingleSubjectName;
    private String VersionDisplayName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lessons2);
        ButterKnife.bind(this);
        mLessonList = new ArrayList<>();
        addLessonList();

    }


    private void addLessonList() {
        mLessonList.add(mLessons1);
        mLessonList.add(mLessons2);
        mLessonList.add(mLessons3);
        mLessonList.add(mLessons4);
        mLessonList.add(mLessons5);
        mLessonList.add(mLessons6);
        mLessonList.add(mLessons7);
        mLessonList.add(mLessons8);
        mLessonList.add(mLessons9);
    }

    ArrayList<String> mSubjectList = new ArrayList<>();
    ArrayList<String> mGradList = new ArrayList<>();
    ArrayList<String> mGradDisplayNameList = new ArrayList<>();
    static String mLessons;
    private boolean isCustom = false;

    @OnClick({R.id.back, R.id.lessons_1, R.id.lessons_2, R.id.lessons_3, R.id.lessons_4, R.id.lessons_5, R.id.lessons_6, R.id.lessons_7, R.id.lessons_8, R.id.lessons_9, R.id.subject_1, R.id.subject_2, R.id.subject_3, R.id.subject_4, R.id.subject_5, R.id.subject_6, R.id.subject_7, R.id.subject_8, R.id.grade_1, R.id.grade_2, R.id.grade_3, R.id.grade_4, R.id.grade_5, R.id.grade_6, R.id.grade_7, R.id.grade_8, R.id.grade_9, R.id.grade_10, R.id.grade_11, R.id.grade_12, R.id.save})
    public void onClick(View view) {
        hideKeyBord();
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.lessons_1:
                clearCheckedAndUpdataUi(R.id.lessons_1);
                isCustom = false;
                break;
            case R.id.lessons_2:
                clearCheckedAndUpdataUi(R.id.lessons_2);
                isCustom = false;
                break;
            case R.id.lessons_3:
                clearCheckedAndUpdataUi(R.id.lessons_3);
                isCustom = false;
                break;
            case R.id.lessons_4:
                clearCheckedAndUpdataUi(R.id.lessons_4);
                isCustom = false;
                break;
            case R.id.lessons_5:
                clearCheckedAndUpdataUi(R.id.lessons_5);
                isCustom = false;
                break;
            case R.id.lessons_6:
                clearCheckedAndUpdataUi(R.id.lessons_6);
                isCustom = false;
                break;
            case R.id.lessons_7:
                clearCheckedAndUpdataUi(R.id.lessons_7);
                isCustom = false;
                break;
            case R.id.lessons_8:
                clearCheckedAndUpdataUi(R.id.lessons_8);
                isCustom = false;
                break;
            case R.id.lessons_9:
                clearCheckedAndUpdataUi(R.id.lessons_9);
                isCustom = true;
                break;
            case R.id.subject_1:
                addSubject(R.id.subject_1);
                break;
            case R.id.subject_2:
                addSubject(R.id.subject_2);
                break;
            case R.id.subject_3:
                addSubject(R.id.subject_3);
                break;
            case R.id.subject_4:
                addSubject(R.id.subject_4);
                break;
            case R.id.subject_5:
                addSubject(R.id.subject_5);
                break;
            case R.id.subject_6:
                addSubject(R.id.subject_6);
                break;
            case R.id.subject_7:
                addSubject(R.id.subject_7);
                break;
            case R.id.subject_8:
                addSubject(R.id.subject_8);
                break;
            case R.id.grade_1:
                addGrade(R.id.grade_1);
                break;
            case R.id.grade_2:
                addGrade(R.id.grade_2);
                break;
            case R.id.grade_3:
                addGrade(R.id.grade_3);
                break;
            case R.id.grade_4:
                addGrade(R.id.grade_4);
                break;
            case R.id.grade_5:
                addGrade(R.id.grade_5);
                break;
            case R.id.grade_6:
                addGrade(R.id.grade_6);
                break;
            case R.id.grade_7:
                addGrade(R.id.grade_7);
                break;
            case R.id.grade_8:
                addGrade(R.id.grade_8);
                break;
            case R.id.grade_9:
                addGrade(R.id.grade_9);
                break;
            case R.id.grade_10:
                addGrade(R.id.grade_10);
                break;
            case R.id.grade_11:
                addGrade(R.id.grade_11);
                break;
            case R.id.grade_12:
                addGrade(R.id.grade_12);
                break;
            case R.id.save:
                add2Service();
                break;
        }
    }

    private void hideKeyBord() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSubjectName.getWindowToken(), 0);

    }

    private void addSubject(int id) {
        CheckBox checkBox = (CheckBox) findViewById(id);
        if (isCustom) {
            mSubjectList.add(checkBox.getTag().toString());
            SingleSubjectName = null;
        } else {
            clearSubjectChecked();
            checkBox.setChecked(true);
            SingleSubjectName = checkBox.getText().toString();
            mSubjectList.add(checkBox.getTag().toString());
        }

    }

    private void addGrade(int id) {
        if (isCustom) {
            mGradList.add(findViewById(id).getTag().toString());
            mGradDisplayNameList.clear();
        } else {
            CheckBox checkBox = (CheckBox) findViewById(id);
            clearGradChecked();
            checkBox.setChecked(true);
            mGradList.add(checkBox.getTag().toString());
            mGradDisplayNameList.add(checkBox.getText().toString());
        }
    }


    private void clearCheckedAndUpdataUi(int id) {

        CheckBox box = (CheckBox) findViewById(id);
        VersionDisplayName = box.getText().toString();
        mLessons = box.getTag().toString();
        clearLessonChecked(box);
        clearSubjectChecked();
        clearGradChecked();
    }

    private void clearLessonChecked(CheckBox box) {
        for (CheckBox checkBox : mLessonList) {
            if (checkBox != box) {
                checkBox.setChecked(false);
            }
        }
    }

    private void clearSubjectChecked() {
        mSubjectList.clear();
        mSubject1.setChecked(false);
        mSubject2.setChecked(false);
        mSubject3.setChecked(false);
        mSubject4.setChecked(false);
        mSubject5.setChecked(false);
        mSubject6.setChecked(false);
        mSubject7.setChecked(false);
        mSubject8.setChecked(false);
    }

    private void clearGradChecked() {
        mGradList.clear();
        mGradDisplayNameList.clear();
        mGrade1.setChecked(false);
        mGrade2.setChecked(false);
        mGrade3.setChecked(false);
        mGrade4.setChecked(false);
        mGrade5.setChecked(false);
        mGrade6.setChecked(false);
        mGrade7.setChecked(false);
        mGrade8.setChecked(false);
        mGrade9.setChecked(false);
        mGrade10.setChecked(false);
        mGrade11.setChecked(false);
        mGrade12.setChecked(false);
    }

    private void toast(String toast) {
        Toast.makeText(AddLessonsActivity2.this, toast, Toast.LENGTH_SHORT).show();
    }

    private void add2Service() {
        mSubjectName1Display = mSubjectName.getText().toString().trim();
        if ((TextUtils.isEmpty(mSubjectName1Display)) || mLessons == null || mSubjectList.isEmpty() || mGradList.isEmpty()) {
            toast("请正确填写");
            return;
        }
        appendUrl(mSubjectName1Display);
    }

    private void appendUrl(String subjectName) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl);
        url.append(AppleTreeUrl.AddCoursePackage.PROTOCOL);
        //添加Session
        url.append(AppleTreeUrl.sSession + "=");
        url.append(SPUtils.getSession() + "&");
        //添加课程名字
        url.append(AppleTreeUrl.AddCoursePackage.PARAMS_NAME + "=");
        url.append(subjectName + "&");
        //添加教材版本
        url.append(AppleTreeUrl.AddCoursePackage.PARAMS_VERSION + "=");
        url.append(mLessons + "&");
        //添加科目
        url.append(AppleTreeUrl.AddCoursePackage.PARAMS_USUBJECT + "=");
        url.append(getSubjectUrl() + "&");
        //添加年级
        url.append(AppleTreeUrl.AddCoursePackage.PARAMS_USE_GRADE + "=");
        url.append(getGradUrl());
        Log.e(getClass().getSimpleName(), url.toString());
        clientService(url.toString());

    }

    public StringBuffer getSubjectUrl() {
        StringBuffer subjectUrl = new StringBuffer();
        String lastObject = mSubjectList.get(mSubjectList.size() - 1);
        for (String s : mSubjectList) {
            if (!s.equals(lastObject)) {
                subjectUrl.append(s + ",");
            } else {
                subjectUrl.append(s);
            }
        }
        return subjectUrl;
    }

    public StringBuffer getGradUrl() {
        StringBuffer subjectUrl = new StringBuffer();
        String lastObject = mGradList.get(mGradList.size() - 1);
        for (String s : mGradList) {
            if (!s.equals(lastObject)) {
                subjectUrl.append(s + ",");
            } else {
                subjectUrl.append(s);
            }
        }
        return subjectUrl;
    }


    private void clientService(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new mStringCallBack());
    }

    public boolean getIsSingle() {
        return !mIsCustom;
    }

    class mStringCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            //复用bean类
            NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
            if (numberVavlibleBean.getStatus().equals("y")) {

                /**
                 *  Intent intent = getIntent();
                 mDanxuan = intent.getBooleanExtra("isCustom", true);
                 kecheng = intent.getStringExtra("Name");
                 jiaocai = intent.getStringExtra("Book");
                 kemu = intent.getStringExtra("Subject");
                 nianji = intent.getStringExtra("Grad");
                 String ID = intent.getStringExtra("ID");
                 */

                //是不是单选（标准课程都是单选）
                boolean isSingle = getIsSingle();
                //课程的名称
                String keCheng = mSubjectName1Display;
                //标准课程的情况下的科目名称
                if (SingleSubjectName != null) {
                    String Subject = SingleSubjectName;
                }
                //教材版本
                String Verson = VersionDisplayName;
                //年级的拼接
                String Grad = getGradDisplay();
                //创建的课程的ID
                String LessonID = (String) numberVavlibleBean.getData();
                startstartBeiKeActivity();

            } else {
                toast(numberVavlibleBean.getInfo());
            }
        }
    }

    private void startstartBeiKeActivity() {

    }

    private String getGradDisplay() {
        StringBuffer gradDisplay = new StringBuffer();
        String lastGrad = mGradDisplayNameList.get(mGradDisplayNameList.size() - 1);
        for (String s : mGradDisplayNameList) {
            if (!s.equals(lastGrad)) {
                gradDisplay.append(s).append("/");
            }else {
                gradDisplay.append(s);
            }
        }
        return gradDisplay.toString();
    }
}