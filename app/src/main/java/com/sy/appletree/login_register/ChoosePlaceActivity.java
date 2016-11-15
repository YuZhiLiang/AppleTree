package com.sy.appletree.login_register;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseActivity;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.bean.SchoolListBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.myclasses.CreateAndAddClassesActivity;
import com.sy.appletree.utils.Const;
import com.sy.appletree.utils.SharePreferenceUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 选择地区
 */
public class ChoosePlaceActivity extends BaseActivity {
    private PullToRefreshListView mListView;
    private Button mButton_btn;
    private ArrayList<SchoolListBean.DataBean> mSchoolbeans = new ArrayList<>();
    private PlaceChooseAdapter mPlaceChooseAdapter;
    private int ClickPosition = -1;
    private int tag;
    private String mSelectSchoolString;
    private String mSelectSchoolID;
    private String mPhoneNum;
    private String mName;
    private String mSex;
    private String mCityID;

    @Override
    protected void findViews() {
        Intent intent = getIntent();
        tag = intent.getIntExtra("tag", 0);
        String city = intent.getStringExtra("city");
        setTitleText("选择所在机构", true);
        setPlaceSearchVillable(true);
        if (tag == 1) {//标识从我的班级中跳转过来的
            String param = (String) SharePreferenceUtils.getParam(ChoosePlaceActivity.this, Const.CACHE_CITY, "");
            setBasePlaceCityText(param);
        } else {//登陆普通流程过来
            setBasePlaceCityText(city);
            getDataFromeService();
        }

        mListView = (PullToRefreshListView) findViewById(R.id.place_list);
        mButton_btn = (Button) findViewById(R.id.place_btn);

        mPlaceChooseAdapter = new PlaceChooseAdapter(this);
        mListView.setAdapter(mPlaceChooseAdapter);
    }

    private void getDataFromeService() {
        getIntentData();
        getSchoolListDataFromeService();
    }

    private void getSchoolListDataFromeService() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetSchoolList.PROTOCOL)
                .append(AppleTreeUrl.GetSchoolList.PARAMS_AREA_ID)
//                .append(mCityID)
                .append("5");
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new ChoosePlaceStringCallback());
    }

    class ChoosePlaceStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            SchoolListBean schoolListBean = gson.fromJson(response, SchoolListBean.class);
            if (schoolListBean.getStatus().equals("y")) {
                getSchoolFromServiverSuccess(schoolListBean.getData());
            } else {
                toast(schoolListBean.getInfo());
            }
        }
    }

    private void getSchoolFromServiverSuccess(List<SchoolListBean.DataBean> data) {
        mSchoolbeans.clear();
        mSchoolbeans.addAll(data);
        mPlaceChooseAdapter.notifyDataSetChanged();
    }

    void toast(String message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mPhoneNum = intent.getStringExtra("phoneNum");
        mName = intent.getStringExtra("name");
        mSex = intent.getStringExtra("sex");
        mCityID = intent.getStringExtra("cityID");
    }

    @Override
    protected void getData() {

    }

    @Override
    protected void setListener() {

        //地区搜索按钮
        setBasePlaceSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //选择地区确认按钮
        mButton_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickPosition == -1) {//未选
                    Toast.makeText(ChoosePlaceActivity.this, "请选择机构", Toast.LENGTH_SHORT).show();
                } else {//已选
                    SharePreferenceUtils.setParam(ChoosePlaceActivity.this, Const.CACHE_SCHOOL, mSelectSchoolString);
                    if (tag == 1) {
                        //去创建班级流程页面
                        CreatNewClass();
                    } else {
                        //注册的流程
                        Log.e(getClass().getSimpleName(), "注册的流程");
                        register();
                    }
                }


            }
        });
        //重新选择城市按钮
        setOnReSelcectCityListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChoosePlaceActivity.this, ChooseCityActivity.class);
                intent.putExtra(Const.PLACETOCITY, 1);
                startActivityForResult(intent, Const.CPTAG);

            }
        });
    }

    private void register() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.Register.PROTOCOL)
                .append(AppleTreeUrl.Register.PARAMS_SEX)
                .append(mSex + "&")
                .append(AppleTreeUrl.Register.PARAMS_MOBLIE)
                .append(mPhoneNum + "&")
                .append(AppleTreeUrl.Register.PARAMS_USER_NAME)
                .append(mName + "&")
                .append(AppleTreeUrl.Register.PARAMS_SCHOOL_ID)
                .append(mSelectSchoolID + "&")
                .append(AppleTreeUrl.Register.PARAMS_AREA_ID)
                .append(mCityID);
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        toast("网络错误");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e(getClass().getSimpleName(), "注册" + response);
                        Gson gson = new Gson();
                        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
                        if (numberVavlibleBean.getStatus().equals("y")) {
                            //TODO 登陆逻辑
                            Intent intent = new Intent(ChoosePlaceActivity.this, RegisterSuccessActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            toast(numberVavlibleBean.getInfo());
                        }

                    }
                });

    }

    private void CreatNewClass() {
        Intent intent = new Intent(ChoosePlaceActivity.this, CreateAndAddClassesActivity.class);
        startActivity(intent);
    }

    /**
     * 接受回掉页面数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.CPTAG && resultCode == Const.PCTAG) {
            String city = data.getStringExtra("city");
            setBasePlaceCityText(city);
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_choose_place2;
    }

    /**
     * 地区列表适配器
     */
    public class PlaceChooseAdapter extends BaseAdapter {
        private Context mContext;

        public PlaceChooseAdapter(Context context) {
            mContext = context;
        }

        @Override
        public int getCount() {
            return mSchoolbeans.size() == 0 ? 0 : mSchoolbeans.size();
        }

        @Override
        public SchoolListBean.DataBean getItem(int position) {
            return mSchoolbeans.get(position);
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.place_item, null);
                viewHolder.mCheckBox = (RadioButton) convertView.findViewById(R.id.place_check);
                viewHolder.mRadioGroup = (RadioGroup) convertView.findViewById(R.id.place_radio);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (mSchoolbeans.size() != 0) {
                viewHolder.mCheckBox.setText(mSchoolbeans.get(position).getSchoolName());
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
                    mSelectSchoolString = mSchoolbeans.get(position).getSchoolName();
                    mSelectSchoolID = String.valueOf(mSchoolbeans.get(position).getSchoolId());
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
}
