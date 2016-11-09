package com.sy.appletree.evaluate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sy.appletree.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评价指标——》选择图标
 */

public class ChooseEvaluateIconActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.choose_grid)
    GridView mChooseGrid;

    private ChooseEvaluateAdapter mChooseEvaluateAdapter;
    private List<String> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_evaluate_icon);
        ButterKnife.bind(this);


        for (int i = 0; i < 20; i++) {
            mList.add(""+i);
        }
        mChooseEvaluateAdapter=new ChooseEvaluateAdapter(mList);
        mChooseGrid.setAdapter(mChooseEvaluateAdapter);

        mChooseGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent=new Intent();

                setResult(101);
                finish();
            }
        });
    }

    @OnClick(R.id.base_left)
    public void onClick() {
        finish();
    }


    public class ChooseEvaluateAdapter extends BaseAdapter {
        private List<String> mList;

        public ChooseEvaluateAdapter(List<String> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
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
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluate_item, null);
                holder.mTextView = (TextView) convertView.findViewById(R.id.eva_item_txt);
                holder.mImageView= (ImageView) convertView.findViewById(R.id.eva_item_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            
            holder.mTextView.setVisibility(View.GONE);

            return convertView;
        }

        class ViewHolder {
            TextView mTextView;
            ImageView mImageView;
        }

    }
}
