package com.sy.appletree.mycollection;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.sy.appletree.R;
import com.sy.appletree.bean.CollectionListBean;
import com.sy.appletree.bean.NumberVavlibleBean;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.preparelessons.BeiKeActivity;
import com.sy.appletree.swipemenulistview.SwipeMenu;
import com.sy.appletree.swipemenulistview.SwipeMenuCreator;
import com.sy.appletree.swipemenulistview.SwipeMenuItem;
import com.sy.appletree.swipemenulistview.SwipeMenuListView;
import com.sy.appletree.utils.Number2Textutils;
import com.sy.appletree.utils.ToastUtils;
import com.sy.appletree.utils.http_about_utils.SPUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class MyCollectionActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_right_icon)
    ImageView mBaseRightIcon;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.collection_list)
    SwipeMenuListView mCollectionList;
    final int INIT_DATA = 1;
    final int DELETE_COLLECTION_ITEM = 2;
    final int DOWNLOAD_FILE = 3;

    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {

            // 创建“删除”项
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    MyCollectionActivity.this);
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            deleteItem.setWidth(dp2px(90));
            deleteItem.setIcon(R.mipmap.btn_icon_close_white_n);
            // 将创建的菜单项添加进菜单中
            menu.addMenuItem(deleteItem);
        }
    };

    // 将dp转换为px
    private int dp2px(int value) {
        // 第一个参数为我们待转的数据的单位，此处为 dp（dip）
        // 第二个参数为我们待转的数据的值的大小
        // 第三个参数为此次转换使用的显示量度（Metrics），它提供屏幕显示密度（density）和缩放信息
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }

    private CollectionAdapter mCollectionAdapter;
    private ArrayList<CollectionListBean.DataBean> mCollectionListBeans = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        getDataFromeService();
        mCollectionList.setMenuCreator(creator);
        mCollectionAdapter = new CollectionAdapter();
        mCollectionList.setAdapter(mCollectionAdapter);


        /**
         * menu点击事件
         */
        mCollectionList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                DeleteCollection(position);

            }
        });

        /**
         * 点击事件
         */
        mCollectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CollectionListBean.DataBean dataBean = mCollectionListBeans.get(position);
                Intent intent = new Intent(MyCollectionActivity.this, BeiKeActivity.class);
                intent.putExtra("yuLan", false);
                if (dataBean.getVersion().equals("9")) {
                    //是自定义教材
                    intent.putExtra("isSingle", false);//判断教材为自定义，自定义的话就是false，否则为true.
                } else {
                    //是标准教材
                    intent.putExtra("isSingle", true);//判断教材为自定义，非自定义的话就是rue，教材多选
                }

                intent.putExtra("Name", dataBean.getName());
                intent.putExtra("Subject", Number2Textutils.paraseSubject(dataBean.getSubject()));
                intent.putExtra("Book", Number2Textutils.paraseVersion(dataBean.getVersion()));
                intent.putExtra("Grad", Number2Textutils.paraseGrade(dataBean.getUseGrade()));
                intent.putExtra("courseID", String.valueOf(dataBean.getCourseId()));
                intent.putExtra("isNewCourse", false);
                startActivity(intent);

            }
        });
    }

    private void DeleteCollection(int position) {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.DeleteCollect.PROTOCOL)
                .append(AppleTreeUrl.DeleteCollect.PARAMS_ID)
                .append(mCollectionListBeans.get(position).getCollectId() + "&")
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());
        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new CollectionCallBack(DELETE_COLLECTION_ITEM, position));
    }

    private void getDataFromeService() {
        StringBuffer url = new StringBuffer();
        url.append(AppleTreeUrl.sRootUrl)
                .append(AppleTreeUrl.ListCollect.PROTOCOL)
                .append(AppleTreeUrl.sSession + "=")
                .append(SPUtils.getSession());
        Log.e(getClass().getSimpleName(), url.toString());

        OkHttpUtils
                .get()
                .url(url.toString())
                .build()
                .execute(new CollectionCallBack(INIT_DATA));
    }

    class CollectionCallBack extends StringCallback {
        int mEventType;
        int position;

        public CollectionCallBack(int eventType) {
            mEventType = eventType;
        }

        public CollectionCallBack(int eventType, int position) {
            this.mEventType = eventType;
            this.position = position;
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
            Gson gson = new Gson();
            switch (mEventType) {
                case 1:
                    initDataFromService(response, gson);
                    break;
                case 2:
                    ParaseDeleteCollectionResponse(response, gson, position);
                    break;
            }
        }

        private void ParaseDeleteCollectionResponse(String response, Gson gson, int position) {
            NumberVavlibleBean numberVavlibleBean = gson.fromJson(response, NumberVavlibleBean.class);
            if (numberVavlibleBean.getStatus().equals("y")) {
                onDeleteCollectionSuccess(position);
            } else {
                ToastUtils.toast(numberVavlibleBean.getInfo());
            }
        }

        private void initDataFromService(String response, Gson gson) {
            CollectionListBean collectionListBean = gson.fromJson(response, CollectionListBean.class);
            if (collectionListBean.getStatus().equals("y")) {
                initDataFromServiceSuccess(collectionListBean.getData());
            } else {
                ToastUtils.toast(collectionListBean.getInfo());
            }
        }
    }

    private void onDeleteCollectionSuccess(int position) {
        mCollectionListBeans.remove(position);
        ToastUtils.toast("删除成功");
        mCollectionAdapter.notifyDataSetChanged();
    }

    private void initDataFromServiceSuccess(List<CollectionListBean.DataBean> data) {
        if (!mCollectionListBeans.isEmpty()) {
            mCollectionListBeans.clear();
        }
        mCollectionListBeans.addAll(data);
        mCollectionAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.base_left)
    public void onClick() {
        finish();
    }


    public class CollectionAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mCollectionListBeans.isEmpty() ? 0 : mCollectionListBeans.size();
        }

        @Override
        public CollectionListBean.DataBean getItem(int position) {
            if (mCollectionListBeans.isEmpty()) {
                return null;
            }
            return mCollectionListBeans.get(position);
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

            CollectionListBean.DataBean dataBean = mCollectionListBeans.get(position);

            viewHolder.kemu.setText(Number2Textutils.paraseSubject(dataBean.getSubject()).substring(0, 1));
            if (dataBean.getAllowDownload().equals("Y")) {
                //可以下载
                viewHolder.xiazai.setText("下载");
            } else {
                //已经下载完成
                viewHolder.xiazai.setText("完成");
            }
            viewHolder.mingcheng.setText(dataBean.getName());
            viewHolder.nianji.setText(Number2Textutils.paraseGrade(dataBean.getUseGrade()));
            viewHolder.jiaocai.setText(Number2Textutils.paraseVersion(dataBean.getSubject()));
            viewHolder.shijian.setText(dataBean.getCreateDateStr());
            viewHolder.shoucang.setVisibility(View.VISIBLE);
            viewHolder.shoucang.setImageDrawable(getResources().getDrawable(R.mipmap.btn_icon_sc_s));
            return convertView;
        }

        class ViewHolder {
            Button kemu;
            Button xiazai;
            TextView mingcheng;
            TextView nianji;
            TextView jiaocai;
            TextView shijian;
            ImageView shoucang;
        }
    }

}
