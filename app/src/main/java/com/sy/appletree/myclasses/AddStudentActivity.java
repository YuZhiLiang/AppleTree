package com.sy.appletree.myclasses;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.sy.appletree.R;
import com.sy.appletree.utils.CharacterParser;
import com.sy.appletree.utils.PinyinComparator;
import com.sy.appletree.utils.SortModel;
import com.sy.appletree.utils.StudentSortBean;
import com.sy.appletree.views.MyGridView;
import com.sy.appletree.views.RadioGroupEx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddStudentActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.student_search)
    EditText mStudentSearch;
    @Bind(R.id.base_search_btn)
    ImageButton mBaseSearchBtn;
    @Bind(R.id.base_right)
    RelativeLayout mBaseRight;
    @Bind(R.id.student_list)
    ListView mStudentList;

    private CharacterParser characterParser;
    private List<SortModel> SourceDateList=new ArrayList<>();

    private List<StudentSortBean> mStudentSortBeans=new ArrayList<>();//最终的数据源

    private StudentAdapter mStudentAdapter;
    private List<String> mList = new ArrayList<>();
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        ButterKnife.bind(this);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        getData();
        mStudentAdapter=new StudentAdapter(mStudentSortBeans);
        mStudentList.setAdapter(mStudentAdapter);
    }

    /**
     * 所有学生的数据
     */
    private void getData() {
        SourceDateList = filledData(getResources().getStringArray(R.array.date));
        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        if (SourceDateList.size()>1){
            for (int i = 0; i <SourceDateList.size()-1; i++) {

                int a=i+1;
                if (SourceDateList.get(i).getSortLetters().equals(SourceDateList.get(a).getSortLetters())){
                    mList.add(SourceDateList.get(i).getName());


                }else {
                    StudentSortBean studentSortBean=new StudentSortBean();
                    studentSortBean.setLetter(SourceDateList.get(i).getSortLetters());
                    mList.add(SourceDateList.get(i).getName());
                    Log.e("对象", mList.size() + "");
                    List<String> list=new ArrayList<>();
                    list.addAll(mList);
                    studentSortBean.setList(list);
                    mStudentSortBeans.add(studentSortBean);
                    mList.clear();
                    Log.e("duixiang",studentSortBean.getList().toString());
                }
            }
        }

    }

    private List<SortModel> filledData(String [] date){
        List<SortModel> mSortList = new ArrayList<>();

        for(int i=0; i<date.length; i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        SortModel sortModel = new SortModel();
        sortModel.setName("占位名");
        sortModel.setSortLetters("#");
        mSortList.add(sortModel);
        return mSortList;

    }
    @OnClick({R.id.base_left, R.id.base_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.base_left:
                Intent intent =new Intent();
                intent.putExtra("xuesheng",50);
                setResult(91, intent);
                finish();
                break;
            case R.id.base_right:
                initPopWindow("add");
                openPopWindow();
                break;
        }
    }



    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    private View contentView;
    private PopupWindow popupWindow;

    private void initPopWindow(final String tag) {
        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(AddStudentActivity.this).inflate(
                R.layout.pop_student, null);
        //设置弹出框的宽度和高度
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);
        //进入退出的动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);
        Button save = (Button) contentView.findViewById(R.id.save);
        TextView textView= (TextView) contentView.findViewById(R.id.querentuichu);
        final EditText editText1 = (EditText) contentView.findViewById(R.id.stu_name);
        final EditText editText2 = (EditText) contentView.findViewById(R.id.stu_xueji);
        final EditText editText3 = (EditText) contentView.findViewById(R.id.stu_phone);
        if (tag.equals("add")){
            textView.setText("新增学生");
        }else if (tag.equals("edit")){
            textView.setText("修改学生信息");
        }

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                if (TextUtils.isEmpty(editText1.getText().toString())) {
                    Toast.makeText(AddStudentActivity.this, "请填写学生姓名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(editText2.getText().toString())) {
                    Toast.makeText(AddStudentActivity.this, "请填写学籍号", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(editText3.getText().toString())) {
                    Toast.makeText(AddStudentActivity.this, "请填写手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (tag.equals("add")){
                        //新增接口

                    }else if (tag.equals("edit")){
                        //修改接口

                    }
                }
            }


        });
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                //亮屏
                screenBrighter();

            }
        });

    }

    private void screenBrighter() {
        WindowManager.LayoutParams params = AddStudentActivity.this.getWindow().getAttributes();
        params.alpha = 1f;
        AddStudentActivity.this.getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = AddStudentActivity.this.getWindow().getAttributes();
        params.alpha = 0.5f;
        AddStudentActivity.this.getWindow().setAttributes(params);
    }




    public class StudentAdapter extends BaseAdapter {

        private List<StudentSortBean> mStudentSortBeans;

        public StudentAdapter(List<StudentSortBean> studentSortBeans) {
            mStudentSortBeans = studentSortBeans;
        }

        @Override
        public int getCount() {
            return mStudentSortBeans.size();//分组的数量
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
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_group_item, null);
                holder.mTextView = (TextView) convertView.findViewById(R.id.shouZiMu);
                holder.mMyGridView = (MyGridView) convertView.findViewById(R.id.myGrid);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.mTextView.setText(mStudentSortBeans.get(position).getLetter());
            List<String> list = mStudentSortBeans.get(position).getList();
            MyGridAdapter myGridAdapter=new MyGridAdapter(list);
            holder.mMyGridView.setAdapter(myGridAdapter);
            Log.e("getview", "getView");
            return convertView;
        }

        class ViewHolder {
            TextView mTextView;
            MyGridView mMyGridView;
        }

    }
    public class MyGridAdapter extends BaseAdapter{

        private List<String> mStringList;

        public MyGridAdapter(List<String> stringList) {
            mStringList = stringList;
        }

        @Override
        public int getCount() {
            return mStringList.size();
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
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.gridstu_item,null);
                viewHolder.mTextView= (TextView) convertView.findViewById(R.id.stu_btn);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }

            viewHolder.mTextView.setText(mStringList.get(position));

            return convertView;
        }
        class ViewHolder{
            TextView mTextView;

        }
    }

}
