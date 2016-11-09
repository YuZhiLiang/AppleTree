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

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sy.appletree.R;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.preparelessons.BeiKeActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private List<String> mStrings=new ArrayList<>();

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

        for (int i = 0; i < 5; i++) {
            mStrings.add(""+i);
        }
        mTankAdapter=new TankAdapter(mStrings);
        mZhikuList.setAdapter(mTankAdapter);

        mZhikuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//position会比平常的listView多一
                Intent intent=new Intent(getActivity(), BeiKeActivity.class);
                intent.putExtra("yuLan",true);
                //判断教材为自定义，自定义的话就是false，否则为true.
                intent.putExtra("单选",false);
                intent.putExtra("科目","智库科目");
                intent.putExtra("教材","智库教材");
                intent.putExtra("年级","智库年级");
                intent.putExtra("课程","智库课程");
                startActivity(intent);
            }
        });

        //个人中心
        mBaseLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyOnClickListener.onClick();
            }
        });
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({ R.id.tank_search_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tank_search_btn:

                Toast.makeText(getActivity(),"点击了搜索",Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public class TankAdapter extends BaseAdapter {

        private List<String> mlist;

        public TankAdapter(List<String> mlist) {
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
            viewHolder.shijian.setText(""+mlist.get(position));
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
