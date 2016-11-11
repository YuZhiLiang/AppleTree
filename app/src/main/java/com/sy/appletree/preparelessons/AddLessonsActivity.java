package com.sy.appletree.preparelessons;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.http_about_utils.HttpUtils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.sy.appletree.views.MyGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 新增课程
 */

public class AddLessonsActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.lesson_edit)
    EditText mLessonEdit;
    @Bind(R.id.lesson_textbook)
    MyGridView mLessonTextbook;
    @Bind(R.id.lesson_subject)
    MyGridView mLessonSubject;
    @Bind(R.id.lesson_grade)
    MyGridView mLessonGrade;
    @Bind(R.id.lesson_save)
    RelativeLayout mLessonSave;

    private boolean isCustom = false;

    private ArrayList<String> mListTextBooks = new ArrayList<>();
    private ArrayList<String> mListSubject = new ArrayList<>();
    private ArrayList<String> mListGrade = new ArrayList<>();
    private LessonAdapter mLessonAdapter;
    private SubjectAdapter mSubjectAdapter;
    private GradeAdapter mGradeAdapter;
    private Object mParams;
    private Object mMetarielVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lessons);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();

        mLessonAdapter = new LessonAdapter(mListTextBooks);
        mLessonTextbook.setAdapter(mLessonAdapter);

        mSubjectAdapter = new SubjectAdapter(mListSubject);
        mLessonSubject.setAdapter(mSubjectAdapter);

        mGradeAdapter = new GradeAdapter(mListGrade);
        mLessonGrade.setAdapter(mGradeAdapter);

    }

    private void initView() {

    }

    private void initData() {
        //添加教材版本
        mListTextBooks.add("人教版");
        mListTextBooks.add("苏教版");
        mListTextBooks.add("北师大版");
        mListTextBooks.add("湘教版");
        mListTextBooks.add("长春版");
        mListTextBooks.add("西师大版");
        mListTextBooks.add("沪教版");
        mListTextBooks.add("河南豫教版");

        //添加科目
        mListSubject.add("语文");
        mListSubject.add("数学");
        mListSubject.add("英语");
        mListSubject.add("思想品德");
        mListSubject.add("科学");
        mListSubject.add("化学");
        mListSubject.add("物理");
        mListSubject.add("政治");

        mListTextBooks.add("自定义版");

        //添加年级
        mListGrade.add("小学一年级");
        mListGrade.add("小学二年级");
        mListGrade.add("小学三年级");
        mListGrade.add("小学四年级");
        mListGrade.add("小学五年级");
        mListGrade.add("小学六年级");

        mListGrade.add("初中七年级");
        mListGrade.add("初中八年级");
        mListGrade.add("初中九年级");

        mListGrade.add("高中一年级");
        mListGrade.add("高中二年级");
        mListGrade.add("高中三年级");

    }

    private void initEvent() {

    }


    @OnClick({R.id.base_left, R.id.base_right, R.id.lesson_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                finish();
                break;
            case R.id.base_right:
                break;
            case R.id.lesson_save://保存
                whetherEmpty();
                break;
        }
    }

    /**
     * 判断课程，教材等空值
     */
    private void whetherEmpty() {
        boolean empty = TextUtils.isEmpty(mLessonEdit.getText().toString().trim());
        if (empty) {
            Toast.makeText(AddLessonsActivity.this, "请输入课程名称", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean empty1 = mMap_Lesson.isEmpty();
        if (empty1) {
            Toast.makeText(AddLessonsActivity.this, "请选择教材", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isCustom) {//自定义
            if (mList_Subjects.isEmpty()) {
                Toast.makeText(AddLessonsActivity.this, "请选择科目", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (mList_Grades.isEmpty()) {
                    Toast.makeText(AddLessonsActivity.this, "请选择适用年级", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StringBuffer sub = new StringBuffer();
                    List list = deleteEmpty(mList_Subjects);
                    for (int i = 0; i < list.size(); i++) {
                        sub.append(list.get(i));
                    }
                    StringBuffer grade = new StringBuffer();
                    List list1 = deleteEmpty(mList_Grades);
                    for (int i = 0; i < list1.size(); i++) {
                        grade.append(list1.get(i));
                    }
                    //多选
                    toast("暂时不支持自定义教材版本");
                    return;
//                    addCoursePackage2Service();
//                    Intent intent = new Intent(AddLessonsActivity.this, BeiKeActivity.class);
//                    intent.putExtra("yuLan", false);
//                    intent.putExtra("单选", false);
//                    intent.putExtra("科目", sub.toString());
//                    intent.putExtra("教材", mMap_Lesson.get("lesson"));
//                    intent.putExtra("年级", grade.toString());
//                    intent.putExtra("课程", mLessonEdit.getText().toString());
//                    startActivity(intent);
                }
            }
        } else {
            if (mMap_Subject.isEmpty()) {
                Toast.makeText(AddLessonsActivity.this, "请选择科目", Toast.LENGTH_SHORT).show();
                return;
            } else {
                if (mMap_Grade.isEmpty()) {
                    Toast.makeText(AddLessonsActivity.this, "请选择适用年级", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(AddLessonsActivity.this, "课程名：" + mLessonEdit.getText().toString() + "---教材：" + mMap_Lesson.get("lesson") + "----科目：" + mMap_Subject.get("subject") + "----年级：" + mMap_Grade.get("grade"),
                            Toast.LENGTH_LONG).show();//输出结果
                    //单选,增加课程
                    addCoursePackage2Service();
                }
            }
        }
    }

    //添加课程到服务器
    private void addCoursePackage2Service() {
        if (isCustom) {
            //如果是添加自定义教材
            addCustonMaterial();
        } else {
            //如果是添加标准教材
            addStandordMaterial();

        }

    }

    private void addCustonMaterial() {
        String url = AppleTreeUrl.sRootUrl + AppleTreeUrl.AddCoursePackage.PROTOCOL
                + AppleTreeUrl.sSession + "=" + SPUtils.getSession()
                //拼接参数Url，这一步及其的坑
                + getParams();
        Log.e(getClass().getSimpleName(), "添加自定义课程的请求的URL" + url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "添加失败ing", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        toast("请求服务器成功");
                        Gson gson = new Gson();
                        //数据类型一样Bean类拿来复用；
                        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
                        Log.e(getClass().getSimpleName(), numberVavlibleBean.toString());
                        if (numberVavlibleBean.getStatus().equals("y")) {
                            //添加成功
                            Intent intent = new Intent(AddLessonsActivity.this, BeiKeActivity.class);
                            intent.putExtra("单选", false);
                            intent.putExtra("课程", mLessonEdit.getText().toString());
                            intent.putExtra("教材", mMap_Lesson.get("lesson"));
                            intent.putExtra("科目", getMultiselectParams(mList_Subjects, 1).toString());
                            intent.putExtra("年级", getMultiselectParams(mList_Grades, 2).toString());
                            intent.putExtra("ID", numberVavlibleBean.getData().toString());
                            startActivity(intent);
                        } else {
                            //添加失败
                            toast("添加失败");
                        }

                    }
                });

    }


    private void addStandordMaterial() {
        String url = AppleTreeUrl.sRootUrl + AppleTreeUrl.AddCoursePackage.PROTOCOL
                + AppleTreeUrl.sSession + "=" + SPUtils.getSession()
                //拼接参数Url，这一步及其的坑
                + getParams();
        Log.e(getClass().getSimpleName(), "添加标准课程的请求的URL" + url);
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(getApplicationContext(), "添加失败ing", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        toast("请求服务器成功");
                        Gson gson = new Gson();
                        //数据类型一样Bean类拿来复用；
                        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
                        Log.e(getClass().getSimpleName(), numberVavlibleBean.toString());
                        if (numberVavlibleBean.getStatus().equals("y")) {
                            //添加成功
                            Intent intent = new Intent(AddLessonsActivity.this, BeiKeActivity.class);
                            intent.putExtra("单选", true);
                            intent.putExtra("课程", mLessonEdit.getText().toString());
                            intent.putExtra("教材", mMap_Lesson.get("lesson"));
                            intent.putExtra("科目", mMap_Subject.get("subject"));
                            intent.putExtra("年级", mMap_Grade.get("grade"));
//                            intent.putExtra("ID", numberVavlibleBean.getData().toString());
                            startActivity(intent);
                        } else {
                            //添加失败

                        }

                    }
                });

    }

    /**
     * 去除空值
     */
    private List deleteEmpty(List list) {

        if (list.size() > 1) {
            for (int i = 0; i < list.size() - 1; i++) {

                for (int j = list.size() - 1; j > i; j--) {
                    if (list.get(j).equals(list.get(i))) {
                        list.remove(j);
                    }
                }
            }
        }
        return list;
    }

    private int ClickPosition = -1;
    //记录选中的教材
    private Map<String, String> mMap_Lesson = new HashMap<>();

    //根据是不是自定教材版本拼接发送到服务器的Url
    public String getParams() {
        String paramsUrl = null;
        if (isCustom) {
            //自定义的
            /*课程名：mLessonEdit.getText().toString()
            教材：" + mMap_Lesson.get("lesson")
            科目：" + sub.toString()
           年级：" + grade.toString());*/
            StringBuffer tempUrl = new StringBuffer();
            //拿到课程名字
            tempUrl.append(AppleTreeUrl.AddCoursePackage.PARAMS_NAME).append("=").append(mLessonEdit.getText().toString().trim())
                    //拿到教材版本
                    .append("&")
                    .append(AppleTreeUrl.AddCoursePackage.PARAMS_VERSION)
                    .append("=")
                    .append(mMap_Lesson.get("lesson"))
                    //拿到所有科目
                    .append("&")
                    .append(AppleTreeUrl.AddCoursePackage.PARAMS_USUBJECT)
                    .append("=")
                    .append(getMultiselectParams(mList_Subjects, 1))
                    //拿到所有年级
                    .append("&")
                    .append(AppleTreeUrl.AddCoursePackage.PARAMS_USE_GRADE)
                    .append("=")
                    .append(getMultiselectParams(mList_Grades, 2));

            return tempUrl.toString();
        } else {
            //单选的
/*            "课程名：mLessonEdit.getText().toString()
            教材：" + mMap_Lesson.get("lesson")
            科目：" + mMap_Subject.get("subject")
            年级：" + mMap_Grade.get("grade")*/

            HashMap<String, Object> params = new HashMap<>();
            //添加课程名字
            params.put(AppleTreeUrl.AddCoursePackage.PARAMS_NAME, mLessonEdit.getText().toString().trim());
            //添加教材版本
            String lesson = getMetarielVersion(mListTextBooks, mMap_Lesson.get("lesson"));
            params.put(AppleTreeUrl.AddCoursePackage.PARAMS_VERSION, lesson);
            //添加科目代号
            String subject = getMetarielVersion(mListSubject, mMap_Subject.get("subject"));
            params.put(AppleTreeUrl.AddCoursePackage.PARAMS_USUBJECT, subject);
            //添加年级代号
            String grade = getMetarielVersion(mListGrade, mMap_Grade.get("grade"));
            params.put(AppleTreeUrl.AddCoursePackage.PARAMS_USE_GRADE, grade);
            //添加Session
            params.put(AppleTreeUrl.sSession, SPUtils.getSession());

            paramsUrl = HttpUtils.getUrlParamsByMap(params);

        }
        return paramsUrl;
    }

    //拼接多选时的url参数,c代表拼什么参数，1代表拼科目， 2代表拼年级
    private StringBuffer getMultiselectParams(ArrayList<String> paramsList, int c) {
        StringBuffer tempUrl = new StringBuffer();
        if (c == 1) {
            for (String s : paramsList) {
                switch (s) {
                    case "语文":
                        tempUrl.append("1,");
                    case "数学":
                        tempUrl.append("2,");
                    case "英语":
                        tempUrl.append("3,");
                    case "思想品德":
                        tempUrl.append("4,");
                    case "科学":
                        tempUrl.append("5,");
                    case "化学":
                        tempUrl.append("6,");
                    case "物理":
                        tempUrl.append("7,");
                    case "政治":
                        tempUrl.append("8,");
                }
            }
        } else {
            for (String s : paramsList) {
                switch (s) {
                    case "小学一年级":
                        tempUrl.append("1,");
                        break;
                    case "小学二年级":
                        tempUrl.append("2,");
                        break;
                    case "小学三年级":
                        tempUrl.append("3,");
                        break;
                    case "小学四年级":
                        tempUrl.append("4,");
                        break;
                    case "小学五年级":
                        tempUrl.append("5,");
                        break;
                    case "小学六年级":
                        tempUrl.append("6,");
                        break;
                    case "初中七年级":
                        tempUrl.append("7,");
                        break;
                    case "初中八年级":
                        tempUrl.append("8,");
                        break;
                    case "初中九年级":
                        tempUrl.append("9,");
                        break;
                    case "高中一年级":
                        tempUrl.append("10,");
                        break;
                    case "高中二年级":
                        tempUrl.append("11,");
                        break;
                    case "高中三年级":
                        tempUrl.append("12,");
                        break;
                }
            }
        }

        return spliteLast(tempUrl);
    }

    //去掉最后一个逗号
    private StringBuffer spliteLast(StringBuffer buffer) {
        StringBuffer replace = buffer.replace(buffer.length() - 1, buffer.length(), "");
        return replace;
    }

    //拿到单选教材版本代号，年级代号或者科目代号，尼玛好坑的方法，没办法之前的程序员留下的坑，填不平只好继续往下挖
    public String getMetarielVersion(List list, String lesson) {
        int count = 1;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).equals(lesson)) {
                count++;
            } else {
                return String.valueOf(count);
            }
        }
        return String.valueOf(count);
    }

    /**
     * 教材的适配器
     */
    public class LessonAdapter extends BaseAdapter {

        private List<String> mList;

        public LessonAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;

            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, parent, false);
                viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.lesson_check);
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (mList.size() != 0) {
                viewHolder.mCheckBox.setText(mList.get(position));
            }
            if (ClickPosition == position) {
                viewHolder.mCheckBox.setChecked(true);
            } else {
                viewHolder.mCheckBox.setChecked(false);
            }
            viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickPosition = position;
                    notifyDataSetChanged();
                    //如果点击的最后一个自定义教材版本，将自定义置为true
                    if (position == mListTextBooks.size() - 1) {
                        isCustom = true;
                        mList_Subjects.clear();
                        mList_Grades.clear();
                        mSubjectAdapter.notifyDataSetChanged();
                        mGradeAdapter.notifyDataSetChanged();

                    } else {
                        isCustom = false;
                        mMap_Subject.clear();
                        mMap_Grade.clear();
                        mSubjectAdapter.notifyDataSetChanged();
                        mGradeAdapter.notifyDataSetChanged();
                    }
                    //记录选中的教材,只有一个教材版本能被选中
                    mMap_Lesson.put("lesson", mListTextBooks.get(position));
                }
            });


            return convertView;
        }

        class ViewHolder {
            CheckBox mCheckBox;
        }
    }

    /**
     * 科目的适配器
     */
    private Map<String, String> mMap_Subject = new HashMap<>();//单选记录器
    private ArrayList<String> mList_Subjects = new ArrayList<>();//多选记录器

    private int ClickPosition1 = -1;

    public class SubjectAdapter extends BaseAdapter {

        private List<String> mList;

        public SubjectAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (isCustom) {

                ViewHolder viewHolder;
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, parent, false);
                    viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.lesson_check);
                    convertView.setTag(viewHolder);

                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                if (mList.size() != 0) {
                    viewHolder.mCheckBox.setText(mList.get(position));
                }
                boolean checked = viewHolder.mCheckBox.isChecked();
                if (checked) {
                    mList_Subjects.add(mListSubject.get(position));
                }
                viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mList_Subjects.size() != 0) {
                            for (int i = 0; i < mList_Subjects.size(); i++) {
                                if (mList_Subjects.get(i).equals(buttonView.getText())) {
                                    if (!isChecked) {
                                        mList_Subjects.remove(i);
                                    }
                                }
                            }
                        } else {
                            if (isChecked) {

                                //往一个ArrayList里存选中的科目，多选时自动清除，
                                mList_Subjects.add(mListSubject.get(position));

                            }
                        }

                    }
                });
            } else {

                ViewHolder viewHolder;
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, parent, false);
                    viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.lesson_check);
                    convertView.setTag(viewHolder);

                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                if (mList.size() != 0) {
                    viewHolder.mCheckBox.setText(mList.get(position));
                }
                if (ClickPosition1 == position) {
                    viewHolder.mCheckBox.setChecked(true);
                    mMap_Subject.put("subject", mListSubject.get(position));
                } else {
                    viewHolder.mCheckBox.setChecked(false);
                }
                viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClickPosition1 = position;
                        notifyDataSetChanged();

                    }
                });
            }

            return convertView;
        }

        class ViewHolder {
            CheckBox mCheckBox;
        }
    }

    /**
     * 年级的适配器
     */
    private Map<String, String> mMap_Grade = new HashMap<>();
    private ArrayList<String> mList_Grades = new ArrayList<>();//多选记录器
    private int ClickPosition2 = -1;

    public class GradeAdapter extends BaseAdapter {

        private List<String> mList;

        public GradeAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (isCustom) {

                ViewHolder viewHolder;
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, parent, false);
                    viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.lesson_check);
                    convertView.setTag(viewHolder);

                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                if (mList.size() != 0) {
                    viewHolder.mCheckBox.setText(mList.get(position));
                }
                boolean checked = viewHolder.mCheckBox.isChecked();

                if (checked) {
                    mList_Grades.add(mListGrade.get(position));
                }
                viewHolder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (mList_Grades.size() != 0) {
                            for (int i = 0; i < mList_Grades.size(); i++) {
                                if (mList_Grades.get(i).equals(buttonView.getText())) {
                                    if (!isChecked) {
                                        mList_Grades.remove(i);
                                    }
                                }
                            }
                        } else {
                            if (isChecked) {
                                mList_Grades.add(mListGrade.get(position));
                            }
                        }
                    }
                });
            } else {

                ViewHolder viewHolder;
                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_item, parent, false);
                    viewHolder.mCheckBox = (CheckBox) convertView.findViewById(R.id.lesson_check);
                    convertView.setTag(viewHolder);

                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
                if (mList.size() != 0) {
                    viewHolder.mCheckBox.setText(mList.get(position));
                }
                if (ClickPosition2 == position) {
                    viewHolder.mCheckBox.setChecked(true);
                    mMap_Grade.put("grade", mListGrade.get(position));
                } else {
                    viewHolder.mCheckBox.setChecked(false);
                }
                viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClickPosition2 = position;
                        notifyDataSetChanged();
                    }
                });
            }


            return convertView;
        }

        class ViewHolder {
            CheckBox mCheckBox;
        }
    }

    void toast(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage, Toast.LENGTH_SHORT).show();
    }

}
