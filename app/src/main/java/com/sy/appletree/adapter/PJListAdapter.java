package com.sy.appletree.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.sy.appletree.R;

import java.util.List;

/**
 * Created by Administrator on 2016/11/4.
 */
public class PJListAdapter extends BaseAdapter {

    private List<String> mStrings;
    private boolean isXZ;

    public PJListAdapter(List<String> strings, boolean isXZ) {
        mStrings = strings;
        this.isXZ = isXZ;
    }

    @Override
    public int getCount() {
        return mStrings.size();
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
        if (convertView==null){
            holder=new ViewHolder();
            if (isXZ){
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.pingjia_xiaozu_iten,null);
                holder.biaoqian= (TextView) convertView.findViewById(R.id.item_xiaozu_bianhao);
                holder.renyuan= (TextView) convertView.findViewById(R.id.item_xiaozu_renyuan);
                holder.fenshu= (TextView) convertView.findViewById(R.id.item_xiaozu_fenshu);
            }else {
                convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.pingjia_chengyuan_item,null);
                holder.name= (Button) convertView.findViewById(R.id.item_chengyuan);
                holder.pingjiashuliang= (TextView) convertView.findViewById(R.id.item_chengyuan_fenshu);
            }


            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        if (isXZ){
            //按小组赋值

        }else {
            //按成员赋值

        }

        return convertView;
    }
    class ViewHolder{
        //分组变量
        TextView biaoqian;
        TextView renyuan;
        TextView fenshu;

        //成员变量
        Button name;
        TextView pingjiashuliang;
    }
}
