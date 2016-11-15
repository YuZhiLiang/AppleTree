package com.sy.appletree.login_register;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.bean.AreaListBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 选择城市
 */

public class ChooseCityActivity2 extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.search_city_edit)
    EditText mSearchCityEdit;
    @Bind(R.id.search_city)
    ImageView mSearchCity;
    @Bind(R.id.city_history)
    LinearLayout mCityHistory;
    @Bind(R.id.city_list)
    ListView mCityList;
    @Bind(R.id.confrim)
    TextView mConfrim;
    private int mIntExtra;//页面从哪开启的识
    private ArrayList<AreaListBean.DataBean> mCityListBeans = new ArrayList<>();
    private ArrayList<AreaListBean.DataBean> mCityListBeansBackups = new ArrayList<>();
    int mCurrentListState = 1;//设置当前的选择列表的状态，默认是选择省
    int mCityListState = 1;
    int mAreaListState = 2;
    private String mPhoneNum;
    private String mName;
    private String mSex;
    private String mCurrentCityName;
    private String mCurrentCityID;
    private TextView mCurrentSelectView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city2);
        ButterKnife.bind(this);
        initView();
        getIntentData();
        getDataFromeService();
        initEvent();
    }

    private void initEvent() {
        mCityList.setOnItemClickListener(new OnCityItemClickListener());
        mSearchCityEdit.addTextChangedListener(new SearchTextChangeListener());
    }

    private void initView() {
        mCityList.setAdapter(mCityListAdapter);
    }

    private void getDataFromeService() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetCityList.PROTOCOL);
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new ChooseCityCallBack());
    }

    class ChooseCityCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            AreaListBean areaListBean = gson.fromJson(response, AreaListBean.class);
            if (areaListBean.getStatus().equals("y")) {
                mCityListBeans.clear();
                mCityListBeans.addAll(areaListBean.getData());
                mCityListAdapter.notifyDataSetChanged();
            } else {
                toast("获取城市列表失败");
            }
        }
    }

    private void toast(String message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mPhoneNum = intent.getStringExtra("phoneNum");
        mName = intent.getStringExtra("name");
        mSex = intent.getStringExtra("sex");
    }

    @OnClick({R.id.back, R.id.search_city, R.id.confrim})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search_city:
                SearchCity();
                break;
            case R.id.confrim:
                jump2ChooseSchool();
                break;
        }
    }

    private void SearchCity() {
        if (mSearchCityEdit.getText().toString().trim().length() == 0) {
            getDataFromeService();
        } else {
            filterCity();
        }
    }

    private void filterCity() {
        String trim = mSearchCityEdit.getText().toString().trim();
        ArrayList<AreaListBean.DataBean> mSearchCityListBeans = new ArrayList<>();
        for (AreaListBean.DataBean cityListBean : mCityListBeans) {
            if (cityListBean.getAreaName().startsWith(trim)) {
                mSearchCityListBeans.add(cityListBean);
            }
        }
        mCityListBeans.clear();
        mCityListBeans.addAll(mSearchCityListBeans);
        mCityListAdapter.notifyDataSetChanged();
    }

    private void jump2ChooseSchool() {
        //TODO jump2SchoolChoose 带上区的ID
        if (mCurrentCityName == null || mCurrentCityID == null) {
            toast("请选择地区");
        } else {
            Intent intent = new Intent(ChooseCityActivity2.this, ChoosePlaceActivity.class);
            intent.putExtra("phoneNum", mPhoneNum);
            intent.putExtra("name", mName);
            intent.putExtra("sex", mSex);
            intent.putExtra("tag", 0);
            intent.putExtra("city", mCurrentCityName);
            intent.putExtra("cityID", mCurrentCityID);
            startActivity(intent);
            finish();
        }

    }

    class SearchTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (s.toString().length() == 0) {
                mCityListBeansBackups.addAll(mCityListBeans);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().length() == 0) {
                mCityListBeans.clear();
                mCityListBeans.addAll(mCityListBeansBackups);
                mCityListAdapter.notifyDataSetChanged();
            }else {
                ArrayList<AreaListBean.DataBean> mSearchCityListBeans = new ArrayList<>();
                for (AreaListBean.DataBean cityListBean : mCityListBeansBackups) {
                    if (cityListBean.getAreaName().startsWith(s.toString())) {
                        mSearchCityListBeans.add(cityListBean);
                    }
                }
                mCityListBeans.clear();
                mCityListBeans.addAll(mSearchCityListBeans);
                mCityListAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 0) {
                mCityListBeans.clear();
                mCityListBeans.addAll(mCityListBeansBackups);
                mCityListBeansBackups.clear();
                mCityListAdapter.notifyDataSetChanged();
            }
        }
    }

    class OnCityItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mCurrentCityName = mCityListBeans.get(position).getAreaName();
            mCurrentCityID = String.valueOf(mCityListBeans.get(position).getId());
            if (mCurrentSelectView != null && mCurrentSelectView != view) {
                mCurrentSelectView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
            mCurrentSelectView = (TextView) view;
            mCurrentSelectView.setBackgroundColor(Color.parseColor("#50000000"));
        }
    }

    CityListAdapter mCityListAdapter = new CityListAdapter();

    class CityListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCityListBeans.size() == 0 ? 0 : mCityListBeans.size();
        }

        @Override
        public AreaListBean.DataBean getItem(int position) {
            return mCityListBeans.get(position);
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
            holder.mTextView.setText(mCityListBeans.get(position).getAreaName());
            return convertView;
        }
    }

    class ViewHolder {
        TextView mTextView;
    }
}
