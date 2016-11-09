package com.sy.appletree.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sy.appletree.R;
import com.sy.appletree.homepage.MainActivity;
import com.sy.appletree.preparelessons.AddLessonsActivity;
import com.sy.appletree.preparelessons.BeiKeActivity;
import com.sy.appletree.swipemenulistview.SwipeMenu;
import com.sy.appletree.swipemenulistview.SwipeMenuCreator;
import com.sy.appletree.swipemenulistview.SwipeMenuItem;
import com.sy.appletree.swipemenulistview.SwipeMenuListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/12.
 */

/**
 * 课程
 */
public class CourseFragment extends Fragment {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.base_title)
    TextView mBaseTitle;
    @Bind(R.id.base_center)
    RelativeLayout mBaseCenter;
    @Bind(R.id.base_right_icon)
    ImageView mBaseRightIcon;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.base_content)
    LinearLayout mBaseContent;
    @Bind(R.id.course_search)
    EditText mCourseSearch;
    @Bind(R.id.course_search_btn)
    ImageButton mCourseSearchBtn;
    @Bind(R.id.base_search_content)
    RelativeLayout mBaseSearchContent;
    @Bind(R.id.base_top_bg)
    RelativeLayout mBaseTopBg;
    @Bind(R.id.course_list)
    SwipeMenuListView mCourseList;
    @Bind(R.id.course_content)
    RelativeLayout mCourseContent;
    private View mView;

    private MainActivity.MyOnClickListener mMyOnClickListener;
    private MainActivity.OCListener mOCListener;

    public void setOCListener(MainActivity.OCListener OCListener) {
        mOCListener = OCListener;
    }

    public void setMyOnClickListener(MainActivity.MyOnClickListener myOnClickListener) {
        mMyOnClickListener = myOnClickListener;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int arg1 = msg.arg1;
                    mStrings.remove(mStrings.get(arg1));
                    mKeChengAdapter = new KeChengAdapter(mStrings);
                    mCourseList.setAdapter(mKeChengAdapter);
                    break;
                default:
                    break;
            }
        }
    };


    SwipeMenuCreator creator = new SwipeMenuCreator() {

        @Override
        public void create(SwipeMenu menu) {
            // 创建“发布”项
            SwipeMenuItem openItem = new SwipeMenuItem(
                    getActivity());
            openItem.setBackground(new ColorDrawable(Color.rgb(0x51, 0xC4,
                    0xB2)));
            openItem.setWidth(dp2px(90));
            openItem.setTitle("发表到智库");
            openItem.setTitleSize(13);
            openItem.setTitleColor(Color.WHITE);
            openItem.setIcon(R.drawable.icon_upload);
            menu.addMenuItem(openItem);

            // 创建“删除”项
            SwipeMenuItem deleteItem = new SwipeMenuItem(
                    getActivity());
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                    0x3F, 0x25)));
            deleteItem.setWidth(dp2px(90));
            deleteItem.setIcon(R.mipmap.btn_icon_close_white_n);
            deleteItem.setTitle("永久删除");
            deleteItem.setTitleSize(13);
            deleteItem.setTitleColor(Color.WHITE);
            // 将创建的菜单项添加进菜单中
            menu.addMenuItem(deleteItem);
        }
    };

    private KeChengAdapter mKeChengAdapter;
    private List<String> mStrings = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.course_fragment_layout, container, false);
        ButterKnife.bind(this, mView);

        initView();
        initDate();
        initEvent();


        mCourseList.setMenuCreator(creator);
        //添加下部的添加课程条目
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.footview, null);
        mCourseList.addFooterView(view);

        for (int i = 0; i < 5; i++) {
            mStrings.add("" + i);
        }
        mKeChengAdapter = new KeChengAdapter(mStrings);
        mCourseList.setAdapter(mKeChengAdapter);

        RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.course_add);
        /**
         * menu点击事件
         */
        mCourseList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {

                switch (index) {
                    case 0:
                        Toast.makeText(getActivity(), "假设发表了", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Message message = new Message();
                        message.what = 1;
                        message.arg1 = position;
                        mHandler.sendMessage(message);
                        break;
                    default:
                        break;
                }

            }
        });

        /**
         * 点击事件
         */
        mCourseList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "条目点击了", Toast.LENGTH_SHORT).show();
                //主页第一页的条目被点击跳转到具体的备课页面
                Intent intent = new Intent(getActivity(), BeiKeActivity.class);
                intent.putExtra("单选", true);
                intent.putExtra("yuLan", true);
                intent.putExtra("科目", "假装有课目");
                intent.putExtra("教材", "假装选了教材");
                intent.putExtra("年级", "假装是小学生");
                intent.putExtra("课程", "就当是体育课吧");

//                intent.putExtra("科目",mMap_Subject.get("subject"));
//                intent.putExtra("教材",mMap_Lesson.get("lesson"));
//                intent.putExtra("年级", mMap_Grade.get("grade"));
//                intent.putExtra("课程",mLessonEdit.getText().toString());
                startActivity(intent);
            }
        });
        /**
         * 侧滑监听
         */
        mCourseList.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {
            @Override
            public void onSwipeStart(int position) {

            }

            @Override
            public void onSwipeEnd(int position) {

            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddLessonsActivity.class);

                startActivity(intent);
            }
        });


        return mView;


    }


    private void initView() {

    }

    private void initDate() {

    }

    private void initEvent() {
        //光标可见
        mCourseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCourseSearch.setCursorVisible(true);
            }
        });


        //个人中心
        mBaseLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyOnClickListener.onClick();
            }
        });

        mBaseRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddLessonsActivity.class);

                startActivity(intent);
            }
        });
    }


    // 将dp转换为px
    private int dp2px(int value) {
        // 第一个参数为我们待转的数据的单位，此处为 dp（dip）
        // 第二个参数为我们待转的数据的值的大小
        // 第三个参数为此次转换使用的显示量度（Metrics），它提供屏幕显示密度（density）和缩放信息
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                getResources().getDisplayMetrics());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public class KeChengAdapter extends BaseAdapter {

        private List<String> mlist;

        public KeChengAdapter(List<String> mlist) {
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_main_item, null);
                viewHolder.kemu = (Button) convertView.findViewById(R.id.shouye_kemu);
                viewHolder.zhiku = (Button) convertView.findViewById(R.id.shouye_zhiku);
                viewHolder.mingcheng = (TextView) convertView.findViewById(R.id.shouye_mingcheng);
                viewHolder.nianji = (TextView) convertView.findViewById(R.id.shouye_nianji);
                viewHolder.jiaocai = (TextView) convertView.findViewById(R.id.shouye_banben);
                viewHolder.shijian = (TextView) convertView.findViewById(R.id.shouye_shijian);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.shijian.setText("" + mlist.get(position));
            return convertView;
        }

        class ViewHolder {
            Button kemu;
            Button zhiku;
            TextView mingcheng;
            TextView nianji;
            TextView jiaocai;
            TextView shijian;

        }
    }

}
