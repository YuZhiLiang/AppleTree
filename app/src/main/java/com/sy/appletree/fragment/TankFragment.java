package com.sy.appletree.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.preparelessons.BeiKeActivity;
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

    private TankAdapter mTankAdapter;
    private ArrayList<WisomLibListBean.DataBean> mWisomBeans = new ArrayList<>();

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
                intent.putExtra("yuLan", true);//是不是预览
                intent.putExtra("isSingle", true);//是不是标准教材（非多选）
                intent.putExtra("Name", dataBean.getName());//课程包名字
                intent.putExtra("Subject", dataBean.getSubject());//科目
                intent.putExtra("Book", dataBean.getVersion());//哪个版本
                intent.putExtra("Grad", dataBean.getUseGrade());//年级
                intent.putExtra("ID", dataBean.getCourseId());//课程包的ID
                intent.putExtra("isNewCourse", false);//是不是新创建的
                startActivity(intent);
            }
        });

        mZhikuList.setOnRefreshListener(new ZKOnRefreshListener());
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
                .execute(new TankFragmentStringCallBack("refresh"));

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
                .execute(new TankFragmentStringCallBack("init"));
    }

    class TankFragmentStringCallBack extends StringCallback {
        String what;

        public TankFragmentStringCallBack(String what) {
            this.what = what;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            toast("网络错误");
            mZhikuList.onRefreshComplete();
        }

        @Override
        public void onResponse(String response, int id) {
            Gson gson = new Gson();
            WisomLibListBean wisomLibListBean = gson.fromJson(response, WisomLibListBean.class);
            if (wisomLibListBean.getStatus().equals("y")) {
                if(what.equals("init")) {
                    getDataFromServiceSuccess(wisomLibListBean);
                }else {
                    referenceDataSuccess(wisomLibListBean);
                }

            } else {
                toast(wisomLibListBean.getInfo());
            }


        }
    }

    private void referenceDataSuccess(WisomLibListBean wisomLibListBean) {
        if (wisomLibListBean.getData() != null) {
            mWisomBeans.clear();
            mWisomBeans.addAll(wisomLibListBean.getData());
            mTankAdapter.notifyDataSetChanged();
            mZhikuList.onRefreshComplete();
        }
    }

    private void getDataFromServiceSuccess(WisomLibListBean wisomLibListBean) {
        if (wisomLibListBean.getData() != null) {
            mWisomBeans.addAll(wisomLibListBean.getData());
            mTankAdapter.notifyDataSetChanged();
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
                viewHolder.tigong = (TextView) convertView.findViewById(R.id.tank_tigong);
                viewHolder.shoucang = (ImageView) convertView.findViewById(R.id.tank_collection);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            WisomLibListBean.DataBean dataBean = mWisomBeans.get(position);
            viewHolder.mingcheng.setText(dataBean.getName());
            viewHolder.jiaocai.setText(dataBean.getVersion());
            viewHolder.kemu.setText(dataBean.getSubject());
            viewHolder.nianji.setText(dataBean.getUseGrade());
            viewHolder.shijian.setText(dataBean.getCreateDateStr());
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
}
