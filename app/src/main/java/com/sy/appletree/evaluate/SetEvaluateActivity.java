package com.sy.appletree.evaluate;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sy.appletree.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 评价指标的页面
 */
public class SetEvaluateActivity extends AppCompatActivity {

    @Bind(R.id.base_left)
    RelativeLayout mBaseLeft;
    @Bind(R.id.evaluate_grid)
    GridView mEvaluateGrid;

    private EvaluateAdapter mEvaluateAdapter;
    private List<String> mList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_evaluate);
        ButterKnife.bind(this);

        for (int i = 0; i <= 12; i++) {
            mList.add("评价指标"+i);
        }
        mEvaluateAdapter=new EvaluateAdapter(mList);
        mEvaluateGrid.setAdapter(mEvaluateAdapter);

        mEvaluateGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ((mList.size()-1)==position){//添加评价指标
                    Intent intent=new Intent(SetEvaluateActivity.this,CreateEvaluateActivity.class);

                    startActivityForResult(intent,100);
                }else {
                    if ( getIntent().getStringExtra("tag")!=null&&getIntent().getStringExtra("tag").equals("task")){
                        //把选中的评价指标回传
                        Intent intent=new Intent();
                        intent.putExtra("pingjia",position);
                        setResult(11,intent);
                        finish();
                    }else {
                        //当不是从添加评价页面进来时进入评价指标的详情页面展示评价指标
                        initPopWindow();
                        openPopWindow();
                    }

                }
            }
        });

    }

    @OnClick(R.id.base_left)
    public void onClick() {
        finish();
    }


    public class EvaluateAdapter extends BaseAdapter{
        private List<String> mList;

        public EvaluateAdapter(List<String> list) {
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
            String s = mList.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.evaluate_item, null);
                holder.mTextView = (TextView) convertView.findViewById(R.id.eva_item_txt);
                holder.mImageView= (ImageView) convertView.findViewById(R.id.eva_item_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (position==mList.size()-1){
                holder.mTextView.setVisibility(View.INVISIBLE);
                holder.mImageView.setImageResource(R.mipmap.btn_tjzb);
            }else {
                holder.mTextView.setVisibility(View.VISIBLE);
                holder.mTextView.setText(s);
            }


            return convertView;
        }

        class ViewHolder {
            TextView mTextView;
            ImageView mImageView;
        }

    }


    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }
    private View contentView;
    private PopupWindow popupWindow;
    private void initPopWindow() {
        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(this).inflate(
                R.layout.popeva_details, null);
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
        TextView title= (TextView) contentView.findViewById(R.id.xinjianbanji);
       TextView zhengNumber= (TextView) contentView.findViewById(R.id.zheng_fen);
       TextView fuNumber= (TextView) contentView.findViewById(R.id.fu_fen);
        ImageView icon= (ImageView) contentView.findViewById(R.id.eva_icon_detail);
        TextView detail= (TextView) contentView.findViewById(R.id.eva_details);


        ImageView close= (ImageView) contentView.findViewById(R.id.close);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
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
        WindowManager.LayoutParams params = SetEvaluateActivity.this.getWindow().getAttributes();
        params.alpha = 1f;
        SetEvaluateActivity.this.getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = SetEvaluateActivity.this.getWindow().getAttributes();
        params.alpha = 0.5f;
        SetEvaluateActivity.this.getWindow().setAttributes(params);
    }
}
