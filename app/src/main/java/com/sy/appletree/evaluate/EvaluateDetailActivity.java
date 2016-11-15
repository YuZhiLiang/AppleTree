package com.sy.appletree.evaluate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.views.RadarData;
import com.sy.appletree.views.RadarView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 显示详细评价的界面
 */

public class EvaluateDetailActivity extends AppCompatActivity {

    @Bind(R.id.back)
    ImageView mBack;
    @Bind(R.id.evaluate_object)
    TextView mEvaluateObject;
    @Bind(R.id.detail_content_view)
    ListView mDetailContentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_detail);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mDetailContentView.setAdapter(new DetailContentViewAdapter());
    }

    class DetailContentViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(BaseApplication.getContext(), R.layout.evaluate_detail_view_item, null);
                RadarView radar = (RadarView) convertView.findViewById(R.id.evaluate_deteil_radar_view);
                TextView title = (TextView) convertView.findViewById(R.id.evaluate_detail_item_title);
                title.setText("" + position);
                List<RadarData> list = new ArrayList<>();
                for (int i = 0; i < 8; i++) {
                    RadarData data = new RadarData("评价标准" + i, i * 10);
                    list.add(data);
                }
                radar.setDataList(list);
            } else {

            }
            return convertView;
        }
    }

    private class ViewHolder {
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
