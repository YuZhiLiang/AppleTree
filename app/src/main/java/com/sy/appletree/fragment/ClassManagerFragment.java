package com.sy.appletree.fragment;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.myclasses.AddStudentActivity;
import com.sy.appletree.mygroup.GroupMannagerActivity;
import com.sy.appletree.utils.ToastUtils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

import static com.sy.appletree.R.id.yushe_number;

/**
 * Created by Administrator on 2016/10/27.
 */


public class ClassManagerFragment extends Fragment {
    @Bind(R.id.banji_img)
    ImageView mBanjiImg;
    @Bind(R.id.xuexiao)
    TextView mXuexiao;
    @Bind(R.id.banji)
    TextView mBanji;
    @Bind(R.id.canzhao)
    LinearLayout mCanzhao;
    @Bind(R.id.chengyuan_number)
    TextView mChengyuanNumber;
    @Bind(R.id.bianji)
    LinearLayout mBianji;
    @Bind(yushe_number)
    TextView mYusheNumber;
    @Bind(R.id.yushe)
    LinearLayout mYushe;
    @Bind(R.id.close)
    ImageView mClose;
    private View mView;
    private String mXuexiao1;
    private String mClassName;
    private String mSchoolName;
    private String mClassID;
    private String mStudentNum;
    private String mGroupNum;
    private String mClassType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();//
    }

    /**
     * 请求班级数据
     */
    private void getData() {
        if (getArguments() != null) {
            mClassName = getArguments().getString("className");
            mSchoolName = getArguments().getString("schoolName");
            mClassID = getArguments().getString("classID");
            mStudentNum = getArguments().getString("studentNum");
            mGroupNum = getArguments().getString("groupNum");
            mClassType = getArguments().getString("classType");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.classview, container, false);
        ButterKnife.bind(this, mView);
        initView();

        //编辑
        mBianji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddStudentActivity.class);
                intent.putExtra("classID", mClassID);
                startActivityForResult(intent, 90);
            }
        });
        //预设分组
        mYushe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupMannagerActivity.class);
                intent.putExtra("yushe", true);
                intent.putExtra("classID", mClassID);
                startActivityForResult(intent, 100);
            }
        });
        //退出班级管理
        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopWindow();
                openPopWindow();
            }
        });
        return mView;
    }

    private void initView() {
        mXuexiao.setText(mSchoolName);
        mBanji.setText(mClassName);
        mChengyuanNumber.setText(mStudentNum);
        mYusheNumber.setText(mGroupNum);
        mBanjiImg.setImageResource(R.mipmap.class_img);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) {
            int fenzushu = data.getIntExtra("fenzushu", 0);
            mYusheNumber.setText(fenzushu + "");
        } else if (requestCode == 90 && resultCode == 91) {
            int xuesheng = data.getIntExtra("xuesheng", 0);
            mChengyuanNumber.setText(xuesheng + "");
        }
    }

    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    private View contentView;
    private PopupWindow popupWindow;

    private void initPopWindow() {
        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(getActivity()).inflate(
                R.layout.pop_exit_class, null);
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
        Button abandon = (Button) contentView.findViewById(R.id.abandon);
        Button save = (Button) contentView.findViewById(R.id.save);
        TextView school = (TextView) contentView.findViewById(R.id.pop_school);
        TextView classes = (TextView) contentView.findViewById(R.id.pop_banji);
        school.setText(mSchoolName);
        classes.setText(mClassName);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer url = new StringBuffer();
                url.append(AppleTreeUrl.sRootUrl)
                        .append(AppleTreeUrl.QuitClass.PROTOCOL)
                        .append(AppleTreeUrl.QuitClass.PARAMS_CLASS_ID)
                        .append(mClassID + "&")
                        .append(AppleTreeUrl.sSession + "=")
                        .append(SPUtils.getSession());
                Log.e(getClass().getSimpleName(), url.toString());

                OkHttpUtils
                        .get()
                        .url(url.toString())
                        .build()
                        .execute(new classManagerCallBack());

            }
        });

        abandon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
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

    class classManagerCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
            if (numberVavlibleBean.getStatus().equals("y")) {
                onQuitClassSuccess();
            }else {
                ToastUtils.toast(numberVavlibleBean.getInfo());
            }
        }
    }

    private void onQuitClassSuccess() {
        popupWindow.dismiss();
        ToastUtils.toast("退出成功");
        EventBus.getDefault().post(mClassID);
    }

    private void screenBrighter() {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 1f;
        getActivity().getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
        params.alpha = 0.5f;
        getActivity().getWindow().setAttributes(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
