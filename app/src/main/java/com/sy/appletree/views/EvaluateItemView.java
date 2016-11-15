package com.sy.appletree.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郁智良
 * on 2016/11/13 0013.
 * des
 */

public class EvaluateItemView extends LinearLayout {
    @Bind(R.id.evaluate_detail_item_title)
    TextView mEvaluateDetailItemTitle;
    @Bind(R.id.evaluate_detail_item_group_num)
    TextView mEvaluateDetailItemGroupNum;
    @Bind(R.id.evaluate_detail_item_fraction)
    TextView mEvaluateDetailItemFraction;
    @Bind(R.id.evaluate_detail_item_grid_view)
    GridView mEvaluateDetailItemGridView;
    ArrayList Datas = new ArrayList();
    onEvaluateDetailItemOnClickListener mOnEvaluateDetailItemOnClickListener;

    public EvaluateItemView(Context context) {
        this(context, null);
    }

    public EvaluateItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(BaseApplication.getContext(), R.layout.evaluate_item_view, this);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mockData();
        mEvaluateDetailItemTitle.setText("测试分组条目");
        mEvaluateDetailItemGroupNum.setText("007");
        mEvaluateDetailItemFraction.setText("+5");
        mEvaluateDetailItemGridView.setAdapter(new EvaluateDetailItemGridViewAdapter());
        mEvaluateDetailItemGridView.setOnItemClickListener(mOnGridClickListener);
    }

    AdapterView.OnItemClickListener mOnGridClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //点击了分组或学生的条目
            if (mOnEvaluateDetailItemOnClickListener != null) {
                mOnEvaluateDetailItemOnClickListener.onEvaluateDetailItemOnClick(position);
            }
        }
    };

    public void setOnEvaluateDetailItemOnClickListener(onEvaluateDetailItemOnClickListener onEvaluateDetailItemOnClickListener) {
        mOnEvaluateDetailItemOnClickListener = onEvaluateDetailItemOnClickListener;
    }

    //分组或学生的条目的的点击监听
    public interface onEvaluateDetailItemOnClickListener {
        void onEvaluateDetailItemOnClick(int position);
    }

    public void toast(String message) {
        Toast.makeText(BaseApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void mockData() {
        for (int i = 0; i < 9; i++) {
            Datas.add("学生" + i);
        }
    }

    class EvaluateDetailItemGridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Datas.size() == 0 ? 0 : Datas.size();
        }

        @Override
        public Object getItem(int position) {
            return Datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tx = (TextView) LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.name_text_view, parent, false);
            tx.setText(Datas.get(position).toString());
            return tx;
        }
    }
}
