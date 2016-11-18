package com.sy.appletree.mycollection;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.sy.appletree.R;
import com.sy.appletree.info.AppleTreeUrl;
import com.sy.appletree.preparelessons.BeiKeActivity;
import com.sy.appletree.swipemenulistview.SwipeMenu;
import com.sy.appletree.swipemenulistview.SwipeMenuCreator;
import com.sy.appletree.swipemenulistview.SwipeMenuItem;
import com.sy.appletree.swipemenulistview.SwipeMenuListView;
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

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int arg1 = msg.arg1;
                    mStrings.remove(mStrings.get(arg1));
                    CollectionAdapter collectionAdapter = new CollectionAdapter(mStrings);
                    mCollectionList.setAdapter(collectionAdapter);
                    break;
                default:
                    break;
            }
        }
    };

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
    private List<String> mStrings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection);
        ButterKnife.bind(this);
        getDataFromeService();
        for (int i = 0; i < 5; i++) {
            mStrings.add("时间" + i);
        }
        mCollectionList.setMenuCreator(creator);
        mCollectionAdapter = new CollectionAdapter(mStrings);
        mCollectionList.setAdapter(mCollectionAdapter);


        /**
         * menu点击事件
         */
        mCollectionList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {

                Message message = new Message();
                message.what = 1;
                message.arg1 = position;
                mHandler.sendMessage(message);

            }
        });

        /**
         * 点击事件
         */
        mCollectionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyCollectionActivity.this, BeiKeActivity.class);
                intent.putExtra("yuLan", true);
                //判断教材为自定义，自定义的话就是false，否则为true.
                intent.putExtra("单选", false);
                intent.putExtra("科目", "收藏科目");
                intent.putExtra("教材", "收藏教材");
                intent.putExtra("年级", "收藏年级");
                intent.putExtra("课程", "收藏课程");
                startActivity(intent);

            }
        });
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
                .execute(new CollectionCallBack());
    }

    class CollectionCallBack extends StringCallback {

        @Override
        public void onError(Call call, Exception e, int id) {
            ToastUtils.toast("网络错误");
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(getClass().getSimpleName(), response);
        }
    }

    @OnClick(R.id.base_left)
    public void onClick() {
        finish();
    }


    public class CollectionAdapter extends BaseAdapter {

        private List<String> mlist;

        public CollectionAdapter(List<String> mlist) {
            this.mlist = mlist;
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
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
            viewHolder.shoucang.setVisibility(View.GONE);
            viewHolder.shijian.setText("" + mlist.get(position));
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
