package com.sy.appletree.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sy.appletree.R;
import com.sy.appletree.mygroup.GroupMannagerActivity;
import com.sy.appletree.views.GroupDetailItemView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/31.
 */

/**
 * 我的分组非空
 */
public class GroupMannerFragment extends Fragment {

    @Bind(R.id.group_people_num)
    TextView mGroupPeopleNum;
    @Bind(R.id.group_name)
    TextView mGroupName;
    @Bind(R.id.group_set)
    ImageView mGroupSet;
    @Bind(R.id.group_content_view)
    ListView mGroupContentView;
    private View mView;
    private List<String> mStudentData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.groupview, container, false);
        ButterKnife.bind(this, mView);
        initDate();
        initEvent();
        return mView;
    }

    private void initDate() {
        Bundle arguments = getArguments();
        if (arguments != null && arguments.get(GroupMannagerActivity.GROUP_NAME) != null) {
            String groupName = (String) arguments.get(GroupMannagerActivity.GROUP_NAME);
            int groupStudentsNumber = (int) arguments.get(GroupMannagerActivity.GROUP_PEOPLE_NUM);
            //给页面设置数据
            mGroupPeopleNum.setText(groupStudentsNumber + "人");
            mGroupName.setText("groupName");
        }else {
            //给view设置模拟内容
            mockDate();
        }
    }

    private void mockDate() {
        mGroupPeopleNum.setText("25人");
        mGroupName.setText("好厉害学习小组");
        mockStudent();
        mGroupContentView.setAdapter(new GroupDetailAdapter());
    }

    private void mockStudent() {
        mStudentData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mStudentData.add("学生" + i);
        }
    }


    private void initEvent() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.group_set)
    public void onClick() {
        Toast.makeText(getContext(), "biu~ 跳去改名了", Toast.LENGTH_SHORT).show();
    }

    class GroupDetailAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mStudentData.size();
        }

        @Override
        public Object getItem(int position) {
            return mStudentData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e(getClass().getSimpleName(), "getView了");
            View view = new GroupDetailItemView(getContext());
            return view;
        }
    }
}
