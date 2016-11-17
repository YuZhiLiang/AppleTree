package com.sy.appletree.myclasses;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseActivity;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.bean.SchoolClassListBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.proxy.TextChangeListener;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class XZAndXQClassActivity extends BaseActivity {

    private int mClassTypeTag;
    private PullToRefreshListView mListView;
    private Button mButton;
    private ClassesChooseAdapter mClassesChooseAdapter;
    private int ClickPosition = -1;
    private ArrayList<SchoolClassListBean.DataBean> mClassBeans = new ArrayList<>();

    //spinner的数据
    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();
    private List<String> mList3 = new ArrayList<>();

    private Handler mHandler = new Handler();

    private boolean isJop = false;//是否跳转
    private String mSchoolID;
    int GET_CLASS_LIST = 1;
    int JOIN2CLASS = 2;
    int CREAT_CLASS = 3;
    private TextView mSpinner1;
    private TextView mSpinner2;
    private TextView mSpinner3;
    private ListView mMListView;
    int mSchoolLevel = -1;
    int mGradLevel = -1;
    int mClassLevel = -1;
    private String mClassType;

    @Override
    protected void findViews() {
        getDataFromIntent();
        initView();
    }

    @Override
    protected void setListener() {
        initEvent();
    }

    @Override
    protected void getData() {
        getDataFromService();
    }

    private void getDataFromService() {
        if (mClassTypeTag == 1) {
            //行政班
            creatUrlAndGetDataFromService("A");
        } else {
            //兴趣班
            creatUrlAndGetDataFromService("B");
        }
    }

    private void creatUrlAndGetDataFromService(String classType) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetSchoolClass.PROTOCOL)
                .append(AppleTreeUrl.GetSchoolClass.PARAMS_SCHOOL_ID)
                .append(mSchoolID + "&")
                .append(AppleTreeUrl.GetSchoolClass.PARAMS_TYPE)
                .append(classType + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new XZAndXQClassCallBack(GET_CLASS_LIST));
    }

    class XZAndXQClassCallBack extends StringCallback {
        int eventType;
        LinearLayout mLayout;

        public XZAndXQClassCallBack(int eventType) {
            this.eventType = eventType;
        }

        public XZAndXQClassCallBack(int eventType, LinearLayout layout_success) {
            this.eventType = eventType;
            this.mLayout = layout_success;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            switch (eventType) {
                case 1:
                    parasGetClassResponse(response, gson);
                    break;
                case 2:
                    parasJoin2ClassResponse(response, gson);
                    break;
                case 3:
                    parasCreatClassResponse(response, gson, mLayout);
                    break;
            }

        }
    }

    //解析创建班级的请求
    private void parasCreatClassResponse(String response, Gson gson, LinearLayout layout) {
        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
        if (numberVavlibleBean.getStatus().equals("y")) {
            Log.e("parasCreatClassResponse", "parasCreatClassResponse");
            onCreatClassSuccess(layout);
        }else {
            toast(numberVavlibleBean.getInfo());
            dialog.dismiss();
        }
    }

    private void onCreatClassSuccess(LinearLayout layout) {
        ChuangJian(layout);
    }

    //解析加入班级的请求
    private void parasJoin2ClassResponse(String response, Gson gson) {
        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
        if (numberVavlibleBean.getStatus().equals("y")) {
            onJoin2ClassSuccess();
        } else {
            toast(numberVavlibleBean.getInfo());
        }
    }

    //加入班级成功
    private void onJoin2ClassSuccess() {
        //TODO 到下一页面中搞定用户加入的班级的列表和相关信息
        Log.e(getClass().getSimpleName(), "加入班级成功");
        Intent intent1 = new Intent(XZAndXQClassActivity.this, ClassManegerActivity.class);
        String className = mClassBeans.get(ClickPosition).getClassName();
        intent1.putExtra("BanJi", className);
        startActivity(intent1);
    }

    //解析获取班级列表的请求
    private void parasGetClassResponse(String response, Gson gson) {
        SchoolClassListBean schoolClassListBean = gson.fromJson(response, SchoolClassListBean.class);
        if (schoolClassListBean.getStatus().equals("y")) {
            getSchoolClassSuccess(schoolClassListBean.getData());
        } else {
            toast(schoolClassListBean.getInfo());
        }
    }

    private void getSchoolClassSuccess(List<SchoolClassListBean.DataBean> data) {
        if (!mClassBeans.isEmpty()) {
            mClassBeans.clear();
        }
        mClassBeans.addAll(data);
        mClassesChooseAdapter.notifyDataSetChanged();
    }

    private void toast(String message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void initEvent() {
        mClassesChooseAdapter = new ClassesChooseAdapter();
        mListView.setAdapter(mClassesChooseAdapter);

        //搜索事件
        setClassesSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //右侧添加点击事件
        setBaseRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popwindow
                if (mClassTypeTag == 1) {
                    //行政班
                    initPopWindow("0");
                } else {
                    //兴趣班
                    initPopWindow("1");
                }
                openPopWindow();

            }
        });

        //加入事件
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点了加入
                if (ClickPosition == -1) {
                    Toast.makeText(XZAndXQClassActivity.this, "请选择班级,或自行添加", Toast.LENGTH_SHORT).show();
                } else {
                    client2ServiceForJoin2Class();

                }
            }
        });
    }

    //加入班级
    private void client2ServiceForJoin2Class() {
        String classID = String.valueOf(mClassBeans.get(ClickPosition).getClassId());
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.JointClass.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession() + "&")
                .append(AppleTreeUrl.JointClass.PARAMS_CLASS_ID)
                .append(classID);
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new XZAndXQClassCallBack(JOIN2CLASS));
    }


    private void initView() {
        //设置标题
        if (mClassTypeTag == 1) {//行政班
            setTitleText("行政班", true);
        } else {//兴趣班
            setTitleText("兴趣班", true);
        }
        //设置右侧添加可见
        setOnRightVillable(true);
        //设置右侧图标不可见
        setBaseRightIcon(1, false);
        //设置搜索可见
        setSeacherVillable(true);

        mListView = (PullToRefreshListView) findViewById(R.id.classes_list);
        mButton = (Button) findViewById(R.id.classes_btn);
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        mClassTypeTag = intent.getIntExtra("tag", 1);
        mSchoolID = intent.getStringExtra("schoolID");
        Log.e(getClass().getSimpleName(), "TAG=" + mClassTypeTag);
        if (mClassTypeTag == 1) {
            mClassType = "A";
            Log.e(getClass().getSimpleName(), "TAG=" + mClassType);
        } else {
            mClassType = "B";
            Log.e(getClass().getSimpleName(), "TAG=" + mClassType);
        }
    }

    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }

    private View contentView;
    private PopupWindow popupWindow;
    boolean isExpand = false;
    private Dialog dialog;

    private void initPopWindow(String tag) {
        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_layout, null);
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
        TextView title = (TextView) contentView.findViewById(R.id.xinjianbanji);
        TextView tishi = (TextView) contentView.findViewById(R.id.yicunzai);
        mSpinner1 = (TextView) contentView.findViewById(R.id.spinner1);
        mSpinner2 = (TextView) contentView.findViewById(R.id.spinner2);
        mSpinner3 = (TextView) contentView.findViewById(R.id.spinner3);
        final Button button = (Button) contentView.findViewById(R.id.pop_chuangjian);
        ImageView close = (ImageView) contentView.findViewById(R.id.close);

        final LinearLayout layout_success = (LinearLayout) contentView.findViewById(R.id.pop_success);
        final LinearLayout layout_add = (LinearLayout) contentView.findViewById(R.id.pop_success_add);
        if (tag.equals("0")) {
            title.setText("新建班级");
        } else if (tag.equals("1")) {
            title.setText("新建兴趣班");
        }

        mSpinner1.addTextChangedListener(new TextChangeListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().equals("初中")) {
                    mSpinner2.setText("七年级");
                    mSchoolLevel = 7;
                } else if (s.toString().trim().equals("小学")) {
                    mSpinner2.setText("一年级");
                    mSchoolLevel = 1;
                } else if (s.toString().trim().equals("高中")) {
                    mSpinner2.setText("一年级");
                    mSchoolLevel = 10;
                } else {
                    mSchoolLevel = -1;
                }
            }
        });

        mSpinner1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    dialog = new Dialog(XZAndXQClassActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ListView mListView = new ListView(XZAndXQClassActivity.this);
                    mListView.setCacheColorHint(Color.TRANSPARENT);
                    LevelAdapter schoollevelAdapter = new LevelAdapter(SchoolLevel);
                    mListView.setAdapter(schoollevelAdapter);
                    mListView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // TODO Auto-generated method stub
                            mSpinner1.setText(SchoolLevel[position]);
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
                    dialogWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.RED));
                    dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.dimAmount = 0f;
                    int[] location = new int[2];
                    mSpinner1.getLocationOnScreen(location);
                    Rect out = new Rect();
                    mSpinner1.getWindowVisibleDisplayFrame(out);
                    lp.x = location[0];
                    lp.y = location[1] - out.top + mSpinner1.getHeight();
                    lp.width = mSpinner1.getWidth();
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
        });
        mSpinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    dialog = new Dialog(XZAndXQClassActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    mMListView = new ListView(XZAndXQClassActivity.this);
                    mMListView.setCacheColorHint(Color.TRANSPARENT);
                    LevelAdapter gradlevelAdapter;
                    if (mSpinner1.getText().toString().trim().equals("小学")) {
                        //如果选择了小学级别的学校
                        gradlevelAdapter = new LevelAdapter(GradeLevel);
                    } else if (mSpinner1.getText().toString().trim().equals("初中")) {
                        //如果选择了初中级别的学校
                        gradlevelAdapter = new LevelAdapter(GradeLeve2);
                    } else if (mSpinner1.getText().toString().trim().equals("高中")) {
                        //如果选择了高中级别的学校
                        gradlevelAdapter = new LevelAdapter(GradeLeve3);
                    } else {
                        gradlevelAdapter = new LevelAdapter(GradeLevel);
                    }

                    mMListView.setAdapter(gradlevelAdapter);
                    mMListView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mMListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // TODO Auto-generated method stub
                            if (mSpinner1.getText().toString().trim().equals("小学")) {
                                //如果选择了小学级别的学校
                                mSpinner2.setText(GradeLevel[position]);
                            } else if (mSpinner1.getText().toString().trim().equals("初中")) {
                                //如果选择了初中级别的学校
                                mSpinner2.setText(GradeLeve2[position]);
                            } else if (mSpinner1.getText().toString().trim().equals("高中")) {
                                //如果选择了高中级别的学校
                                mSpinner2.setText(GradeLeve3[position]);
                            } else {
                                mSpinner2.setText(GradeLevel[position]);
                            }
                            mGradLevel = position;

                            if (dialog != null) {
                                dialog.dismiss();
                                dialog = null;
                                isExpand = false;
                            }
                        }
                    });
                    dialog.setContentView(mMListView);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // TODO Auto-generated method stub
                            isExpand = false;
                        }
                    });
                    Window dialogWindow = dialog.getWindow();
                    dialogWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.RED));
                    dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.dimAmount = 0f;
                    int[] location = new int[2];
                    mSpinner2.getLocationOnScreen(location);
                    Rect out = new Rect();
                    mSpinner2.getWindowVisibleDisplayFrame(out);
                    lp.x = location[0];
                    lp.y = location[1] - out.top + mSpinner2.getHeight();
                    lp.width = mSpinner2.getWidth();
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
        });
        mSpinner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    dialog = new Dialog(XZAndXQClassActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ListView mListView = new ListView(XZAndXQClassActivity.this);
                    mListView.setCacheColorHint(Color.TRANSPARENT);
                    LevelAdapter classlevelAdapter = new LevelAdapter(ClassLevel);
                    mListView.setAdapter(classlevelAdapter);
                    mListView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // TODO Auto-generated method stub
                            mSpinner3.setText(ClassLevel[position]);
                            if (dialog != null) {
                                dialog.dismiss();
                                dialog = null;
                                isExpand = false;
                            }
                            mClassLevel = position + 1;
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
                    dialogWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.RED));
                    dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.dimAmount = 0f;
                    int[] location = new int[2];
                    mSpinner3.getLocationOnScreen(location);
                    Rect out = new Rect();
                    mSpinner3.getWindowVisibleDisplayFrame(out);
                    lp.x = location[0];
                    lp.y = location[1] - out.top + mSpinner3.getHeight();
                    lp.width = mSpinner3.getWidth();
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
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("请选择学校".equals(mSpinner1.getText().toString())) {
                    Toast.makeText(XZAndXQClassActivity.this, "请选择教育阶段", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("请选择年级".equals(mSpinner2.getText().toString())) {
                    Toast.makeText(XZAndXQClassActivity.this, "请选择年级", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("请选择班级".equals(mSpinner3.getText().toString())) {
                    Toast.makeText(XZAndXQClassActivity.this, "请选择班级", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //请求接口
                    if (button.getText().toString().equals("创建")) {
                        addStandardClass2Service(layout_success);
                    } else {
                        ChuangJian(layout_add);
                    }

                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                mSchoolLevel = -1;
                mGradLevel = -1;
                mClassLevel = -1;
            }

        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                //亮屏
                screenBrighter();
                mSchoolLevel = -1;
                mGradLevel = -1;
                mClassLevel = -1;
                if (isJop) {
                    isJop = false;
                    Intent intent = new Intent(XZAndXQClassActivity.this, ClassManegerActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    private void addStandardClass2Service(LinearLayout layout_success) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.crateClass.PROTOCOL)
                .append(AppleTreeUrl.crateClass.PARAMS_SCHOOL_ID)
                .append(mSchoolID + "&")
                .append(AppleTreeUrl.crateClass.PARAMS_TYPE)
                .append(mClassType + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession() + "&")
                .append(AppleTreeUrl.crateClass.PARAMS_NAME)
                .append(mSpinner1.getText().toString() + mSpinner2.getText().toString() + mSpinner3.getText().toString() + "&")
                .append(AppleTreeUrl.crateClass.PARAMS_GRADE)
                .append(String.valueOf(mSchoolLevel + mGradLevel) + "&")
                .append(AppleTreeUrl.crateClass.PARAMS_BAN_JI)
                .append(String.valueOf(mClassLevel));
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new XZAndXQClassCallBack(CREAT_CLASS, layout_success));

    }

    /**
     * 创建或加入班级
     */
    private void ChuangJian(LinearLayout layout) {
        layout.setVisibility(View.VISIBLE);
        isJop = true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        }, 500);
    }


    private void screenBrighter() {
        WindowManager.LayoutParams params = XZAndXQClassActivity.this.getWindow().getAttributes();
        params.alpha = 1f;
        XZAndXQClassActivity.this.getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = XZAndXQClassActivity.this.getWindow().getAttributes();
        params.alpha = 0.5f;
        XZAndXQClassActivity.this.getWindow().setAttributes(params);
    }


    @Override
    protected int setContentView() {
        return R.layout.activity_xing_zheng_class;
    }

    /**
     * 班级列表适配器
     */
    public class ClassesChooseAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mClassBeans.size() == 0 ? 0 : mClassBeans.size();
        }

        @Override
        public SchoolClassListBean.DataBean getItem(int position) {
            if (!mClassBeans.isEmpty()) {
                return mClassBeans.get(position);
            }
            return null;
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
                convertView = LayoutInflater.from(XZAndXQClassActivity.this).inflate(R.layout.place_item, null);
                viewHolder.mCheckBox = (RadioButton) convertView.findViewById(R.id.place_check);
                viewHolder.mRadioGroup = (RadioGroup) convertView.findViewById(R.id.place_radio);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (mClassBeans.size() != 0) {
                viewHolder.mCheckBox.setText(mClassBeans.get(position).getClassName());//TODO 设置的时候是不是要减一？
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

    /**
     * 下拉适配器
     */
    final String[] SchoolLevel = {"小学", "初中", "高中"};
    final String[] GradeLevel = {"一年级", "二年级", "三年级", "四年级", "五年级", "六年级"};
    final String[] GradeLeve2 = {"七年级", "八年级", "九年级"};
    final String[] GradeLeve3 = {"一年级", "二年级", "三年级"};
    final String[] ClassLevel = {"(1)班", "(2)班", "(3)班", "(4)班", "(5)班"};

    public class LevelAdapter extends BaseAdapter {

        private String[] data;

        //动态改变
        public void setData(String[] data) {
            this.data = null;
            this.data = data;
            notifyDataSetChanged();
        }

        public LevelAdapter(String[] data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public String getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_spinner_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.diaspin_txt);
            textView.setText(this.data[position]);
            return convertView;
        }
    }

    @Override
    protected void onDestroy() {
        if (mHandler != null) {
            mHandler = null;
        }
        super.onDestroy();
    }
}
