package com.sy.appletree.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sy.appletree.R;
import com.sy.appletree.utils.ModleBean.BeikeBean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/1.
 */
public class CourseAdapter extends BaseAdapter {

    private List<BeikeBean> mBeikeBeans;
    private boolean isZhiku;

    public CourseAdapter(List<BeikeBean> beikeBeans,boolean isZhiku) {
        mBeikeBeans = beikeBeans;
        this.isZhiku=isZhiku;
    }

    @Override
    public int getCount() {
        return mBeikeBeans.size();
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

        BeikeBean beikeBean = mBeikeBeans.get(position);
        String name = beikeBean.getName();
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (isZhiku){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item_zk, null);
            }else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_item, null);
            }

            viewHolder.mTextView = (TextView) convertView.findViewById(R.id.course_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.mTextView.setText(name);

        return convertView;
    }


    class ViewHolder {
        TextView mTextView;

    }
}
