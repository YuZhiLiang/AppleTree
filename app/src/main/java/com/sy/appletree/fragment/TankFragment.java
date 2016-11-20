package com.sy.appletree.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sy.appletree.R;
import com.sy.appletree.base.WisomLibListBean;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.preparelessons.BeiKeActivity;
import com.sy.appletree.utils.Number2Textutils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/10/12.
 */

/**
 * 智库
 */
public class TankFragment extends Fragment {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.tank_search)
    EditText mTankSearch;
    @Bind(R.id.tank_search_btn)
    ImageButton mTankSearchBtn;
    @Bind(R.id.zhiku_list)
    PullToRefreshListView mZhikuList;
    private View mView;
    final int INIT_DATA = 1;
    final int REFLASH_DATA = 2;
    final int COLLECT = 3;
    final int CANCEL_COLLECT = 4;

    private TankAdapter mTankAdapter;
    private ArrayList<WisomLibListBean.DataBean> mWisomBeans = new ArrayList<>();
    private ArrayList<WisomLibListBean.DataBean> mWisomBeansBackups = new ArrayList<>();

    private MainActivity.MyOnClickListener mMyOnClickListener;

    public MainActivity.MyOnClickListener getMyOnClickListener() {
        return mMyOnClickListener;
    }

    public void setMyOnClickListener(MainActivity.MyOnClickListener myOnClickListener) {
        mMyOnClickListener = myOnClickListener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.tank_fragment_layout, container, false);

        ButterKnife.bind(this, mView);
        getData();

        mTankAdapter = new TankAdapter();
        mZhikuList.setAdapter(mTankAdapter);

        initEvent();

        //个人中心
        mBaseLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyOnClickListener.onClick();
            }
        });
        return mView;
    }

    private void initEvent() {
        mZhikuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//position会比平常的listView多一
                Intent intent = new Intent(getActivity(), BeiKeActivity.class);

                WisomLibListBean.DataBean dataBean = mWisomBeans.get(position - 1);
                Log.e(getClass().getSimpleName(), "pkgID = " + dataBean.getCourseId());
                intent.putExtra("yuLan", true);//是不是预览
                intent.putExtra("isSingle", true);//是不是标准教材（非多选）
                intent.putExtra("Name", dataBean.getName());//课程包名字
                intent.putExtra("Subject", Number2Textutils.paraseSubject(dataBean.getSubject()));//科目
                intent.putExtra("Book", Number2Textutils.paraseVersion(dataBean.getVersion()));//哪个版本
                intent.putExtra("Grad", Number2Textutils.paraseGrade(dataBean.getUseGrade()));//年级
                intent.putExtra("courseID", String.valueOf(dataBean.getCourseId()));//课程包的ID
                intent.putExtra("isNewCourse", false);//是不是新创建的
                startActivity(intent);
            }
        });

        mZhikuList.setOnRefreshListener(new ZKOnRefreshListener());
        mTankSearch.addTextChangedListener(new TankFragmentSearchWatch());
    }

    class ZKOnRefreshListener implements PullToRefreshBase.OnRefreshListener {

        @Override
        public void onRefresh(PullToRefreshBase refreshView) {
            getRefreshData();
        }
    }

    private void getRefreshData() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetWisomLibList.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new TankFragmentStringCallBack(REFLASH_DATA));

    }

    private void getData() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.GetWisomLibList.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new TankFragmentStringCallBack(INIT_DATA));
    }

    class TankFragmentStringCallBack extends StringCallback {
        int eventType;
        ImageView v;

        public TankFragmentStringCallBack(int eventType) {
            this.eventType = eventType;
        }

        public TankFragmentStringCallBack(int eventType, ImageView v) {
            this.eventType = eventType;
            this.v = v;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
            mZhikuList.onRefreshComplete();
        }

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            switch (eventType) {
                case 1://初始化数据
                    getDataFromServiceSuccess(response, gson);
                    break;
                case 2://刷新数据
                    referenceDataSuccess(response, gson);
                    break;
                case 3://收藏
                    collcetionGetResultFromService(response, gson, v);
                    break;
                case 4://取消收藏
                    collcetionGetResultFromService(response, gson, v);
                    break;

            }
        }
    }

    private void collcetionGetResultFromService(String response, Gson gson, ImageView v) {
        Log.e(getClass().getSimpleName(), response);
        NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
        if (numberVavlibleBean.getStatus().equals("y")) {
            onCollectSuccess(v);
        } else {
            toast(numberVavlibleBean.getInfo());
        }
    }

    private void onCollectSuccess(ImageView v) {
        String tag = (String) v.getTag();
        int position = (int) v.getTag(R.id.shou_can);
        WisomLibListBean.DataBean dataBean = mWisomBeans.get(position);
        //收藏
        v.setImageDrawable(getResources().getDrawable(R.mipmap.btn_icon_sc_s));
        v.setTag("Y");
        dataBean.setHavaCollect("Y");
    }

    private void referenceDataSuccess(String response, Gson gson) {
        WisomLibListBean wisomLibListBean = gson.fromJson(response, WisomLibListBean.class);
        if (wisomLibListBean.getStatus().equals("y")) {
            if (wisomLibListBean.getData() != null) {
                mWisomBeans.clear();
                mWisomBeans.addAll(wisomLibListBean.getData());
                mTankAdapter.notifyDataSetChanged();
                mZhikuList.onRefreshComplete();
            }
        } else {
            toast(wisomLibListBean.getInfo());
        }

    }

    private void getDataFromServiceSuccess(String response, Gson gson) {
        Log.e(getClass().getSimpleName(), response);
        WisomLibListBean wisomLibListBean = gson.fromJson(response, WisomLibListBean.class);
        if (wisomLibListBean.getStatus().equals("y")) {
            if (wisomLibListBean.getData() != null) {
                mWisomBeans.addAll(wisomLibListBean.getData());
                mTankAdapter.notifyDataSetChanged();
            }
        } else {
            toast(wisomLibListBean.getInfo());
        }


    }

    private void toast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tank_search_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tank_search_btn:
                Toast.makeText(getActivity(), "点击了搜索", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public class TankAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mWisomBeans.size() == 0 ? 0 : mWisomBeans.size();
        }

        @Override
        public WisomLibListBean.DataBean getItem(int position) {
            return mWisomBeans.get(position);
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tank_main_item, null);
                viewHolder.kemu = (Button) convertView.findViewById(R.id.tank_kemu);
                viewHolder.xiazai = (Button) convertView.findViewById(R.id.tank_zhiku);
                viewHolder.mingcheng = (TextView) convertView.findViewById(R.id.tank_mingcheng);
                viewHolder.nianji = (TextView) convertView.findViewById(R.id.tank_nianji);
                viewHolder.jiaocai = (TextView) convertView.findViewById(R.id.tank_banben);
                viewHolder.shijian = (TextView) convertView.findViewById(R.id.tank_shijian);
                viewHolder.shoucang = (ImageView) convertView.findViewById(R.id.tank_collection);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            WisomLibListBean.DataBean dataBean = mWisomBeans.get(position);
            viewHolder.mingcheng.setText(dataBean.getName());
            viewHolder.jiaocai.setText(Number2Textutils.paraseVersion(dataBean.getVersion()));
            viewHolder.kemu.setText(Number2Textutils.paraseSubject(dataBean.getSubject()).substring(0, 1));
            viewHolder.nianji.setText(Number2Textutils.paraseGrade(dataBean.getUseGrade()));
            viewHolder.shijian.setText(dataBean.getCreateDateStr());
            //收藏
            if (dataBean.getHavaCollect().equals("Y")) {
                viewHolder.shoucang.setImageDrawable(getResources().getDrawable(R.mipmap.btn_icon_sc_s));
                viewHolder.shoucang.setTag("Y");
            } else {
                viewHolder.shoucang.setImageDrawable(getResources().getDrawable(R.mipmap.btn_icon_sc_n));
                viewHolder.shoucang.setTag("C");
            }
            viewHolder.shoucang.setTag(R.id.shou_can, position);
            viewHolder.shoucang.setOnClickListener(new onCollectClickListener());
            //下载
            if (dataBean.getAllowDownload().equals("Y")) {
                viewHolder.xiazai.setTag("下载");
            } else {
                viewHolder.xiazai.setText("完成");
            }
            return convertView;
        }

        class ViewHolder {
            Button kemu;
            Button xiazai;
            TextView mingcheng;
            TextView nianji;
            TextView jiaocai;
            TextView shijian;
            TextView tigong;
            ImageView shoucang;


        }
    }

    class TankFragmentSearchWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (s.toString().length() == 0) {
                mWisomBeansBackups.addAll(mWisomBeans);
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().length() == 0) {
                mWisomBeans.clear();
                mWisomBeans.addAll(mWisomBeansBackups);
                mTankAdapter.notifyDataSetChanged();
            }else {
                ArrayList<WisomLibListBean.DataBean> mSearchWisomBeans = new ArrayList<>();
                for (WisomLibListBean.DataBean WisomBean : mWisomBeansBackups) {
                    if (WisomBean.getName().startsWith(s.toString())) {
                        mSearchWisomBeans.add(WisomBean);
                    }
                }
                mWisomBeans.clear();
                mWisomBeans.addAll(mSearchWisomBeans);
                mTankAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() == 0) {
                mWisomBeans.clear();
                mWisomBeans.addAll(mWisomBeansBackups);
                mWisomBeansBackups.clear();
                mTankAdapter.notifyDataSetChanged();
            }
        }
    }

    class onCollectClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(R.id.shou_can);
            WisomLibListBean.DataBean dataBean = mWisomBeans.get(position);
            collcetionOrCancel(v, dataBean, position);
        }
    }

    private void collcetionOrCancel(View v, WisomLibListBean.DataBean dataBean, int position) {
        StringBuffer url = new StringBuffer();
        if (v.getTag().equals("Y")) {
            //之前收藏了，取消收藏
            url.append(AppleTreeUrl.sRootUrl)
                    .append(AppleTreeUrl.DeleteCollect.PROTOCOL)
                    .append(AppleTreeUrl.DeleteCollect.PARAMS_ID);
//                    .append(dataBean.ge)
            toast("暂不支持此处取消收藏，请前往收藏列表");
        } else {
            //之前没收藏，收藏
            url.append(AppleTreeUrl.sRootUrl)
                    .append(AppleTreeUrl.CollectWisomLib.PROTOCOL)
                    .append(AppleTreeUrl.CollectWisomLib.PARAMS_COURSE_PKG_ID)
                    .append(dataBean.getCourseId() + "&")
                    .append(AppleTreeUrl.sSession + "=")
                    .append(SPUtils.getSession());
            Log.e(getClass().getSimpleName(), url.toString());
            ciientServiceForCollectOrCancel(COLLECT, (ImageView) v, url);
        }
    }

    private void ciientServiceForCollectOrCancel(int eventType, ImageView v, StringBuffer url) {
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new TankFragmentStringCallBack(CANCEL_COLLECT, v));
    }
}
