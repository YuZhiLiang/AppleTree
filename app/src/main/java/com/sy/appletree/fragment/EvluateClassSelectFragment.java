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
import android.widget.ListView;
import android.widget.TextView;

import com.sy.appletree.R;
import com.sy.appletree.base.BaseApplication;
import com.sy.appletree.evaluate.EvaluateActivity;
import com.sy.appletree.utils.Const;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 郁智良
 * on 2016/11/12 0012.
 * des
 */

public class EvluateClassSelectFragment extends Fragment {

    @Bind(R.id.select_class_list_view)
    ListView mSelectClassListView;
    @Bind(R.id.evaluate_empty)
    TextView mEvaluateEmpty;
    private ArrayList<String> mClassBeans = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evaluate_class_select, container, false);
        ButterKnife.bind(this, view);
        getData();
        initEvent();
        return view;
    }

    private void initEvent() {
        mSelectClassListView.setOnItemClickListener(new SelectClassListItemOnClickListener());
    }

    class SelectClassListItemOnClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //跳转到评价界面
            jump2EvaluateActivity();
        }
    }

    private void jump2EvaluateActivity() {
        Intent intent = new Intent(getActivity(), EvaluateActivity.class);
        startActivity(intent);
    }

    private void getData() {
        if (Const.isDeBug) {
            mockData();
        } else {
            getDataFromeService();
        }
    }

    private void getDataFromeService() {

    }

    private void mockData() {
        mSelectClassListView.setVisibility(View.VISIBLE);
        mEvaluateEmpty.setVisibility(View.GONE);
        for (int i = 0; i < 15; i++) {
            mClassBeans.add("班级" + i);
        }
        mSelectClassListView.setAdapter(new ClassSelectAdapter());
    }

    class ClassSelectAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mClassBeans.size() == 0 ? 0 : mClassBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return mClassBeans.get(position);
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
                convertView = View.inflate(BaseApplication.getContext(), R.layout.class_select_item, null);
                holder.mTextView = (TextView) convertView.findViewById(R.id.select_class_list_item_text);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextView.setText(mClassBeans.get(position));
            return convertView;
        }
    }

    private class ViewHolder {
        public TextView mTextView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
