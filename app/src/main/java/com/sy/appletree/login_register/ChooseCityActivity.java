package com.sy.appletree.login_register;


import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseActivity;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.bean.AreaListBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.utils.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * 选择城市
 */

public class ChooseCityActivity extends BaseActivity implements View.OnClickListener {

    //    private Button mButton_btn;
    private LinearLayout mLayout_history;
    private int mIntExtra;//页面从哪开启的识
    private ListView mCityList;
    private ArrayList<AreaListBean.DataBean> mCityLiseBeans = new ArrayList<>();
    int mCurrentListState = 1;//设置当前的选择列表的状态，默认是选择省
    int mCityListState = 1;
    int mAreaListState = 2;
    private String mPhoneNum;
    private String mName;
    private String mSex;

    @Override
    protected void findViews() {
        mIntExtra = getIntent().getIntExtra(Const.PLACETOCITY, 0);
        setTitleText("选择城市", true);
        //右侧图标不可见
        setBaseRightIcon(1, false);
        //城市搜索可见
        setCitySearchVillable(false);
        mCityList = (ListView) findViewById(R.id.city_list);
//        mButton_btn= (Button) findViewById(R.id.city_btn);
        mLayout_history = (LinearLayout) findViewById(R.id.city_history);
        initEvent();
    }

    private void initEvent() {
        getIntentData();
        getData();

        mCityList.setAdapter(mCityListAdapter);
        mCityList.setOnItemClickListener(new OnCityItemClickListener());
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mPhoneNum = intent.getStringExtra("phoneNum");
        mName = intent.getStringExtra("name");
        mSex = intent.getStringExtra("sex");
    }

    class OnCityItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (mCurrentListState) {
                case 3:
                    //TODO jump2SchoolChoose 带上区的ID
                    Intent intent = new Intent(ChooseCityActivity.this, ChoosePlaceActivity.class);
                    intent.putExtra("phoneNum", mPhoneNum);
                    intent.putExtra("name", mName);
                    intent.putExtra("sex", mSex);
                    intent.putExtra("tag", 0);
                    intent.putExtra("city", mCityLiseBeans.get(position).getAreaName());
                    intent.putExtra("cityID", String.valueOf(mCityLiseBeans.get(position).getId()));
                    startActivity(intent);
                    break;
            }
        }
    }

    CityListAdapter mCityListAdapter = new CityListAdapter();

    class CityListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCityLiseBeans.size() == 0 ? 0 : mCityLiseBeans.size();
        }

        @Override
        public AreaListBean.DataBean getItem(int position) {
            return mCityLiseBeans.get(position);
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
                convertView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.city_item_view, parent, false);
                holder.mTextView = (TextView) convertView;
                holder.mTextView.setGravity(Gravity.CENTER);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextView.setText(mCityLiseBeans.get(position).getAreaName());
            return convertView;
        }
    }

    class ViewHolder {
        TextView mTextView;
    }

    @Override
    protected void getData() {
        getPrinceListFromeService();
    }

    private void getPrinceListFromeService() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetPrinceList.PROTOCOL);
        ClientService(url.toString());
    }

    private void ClientService(String url) {
        Log.e(getClass().getSimpleName(), url);
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new ChooseCityStringCallback());
    }

    class ChooseCityStringCallback extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
           toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            AreaListBean areaListBean = gson.fromJson(response, AreaListBean.class);
            switch (mCurrentListState) {
                case 1:
                    paraseCityListData(areaListBean);
                    break;
            }
        }
    }

    private void paraseCityListData(AreaListBean areaListBean) {

    }

    private void toast (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void setListener() {
        //城市搜索按钮
        setBaseCitySearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ChooseCityActivity.this, "点击搜索", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected int setContentView() {
        return R.layout.activity_choose_city;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
        }
    }
}
