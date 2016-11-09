package com.sy.appletree.myclasses;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sy.appletree.R;
import com.sy.appletree.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class XZAndXQClassActivity extends BaseActivity {

    private int mIntExtra;
    private PullToRefreshListView mListView;
    private Button mButton;
    private ClassesChooseAdapter mClassesChooseAdapter;
    private int ClickPosition=-1;
    private List<String> mListClass=new ArrayList<>();

    //spinner的数据
    private List<String> mList1=new ArrayList<>();
    private List<String> mList2=new ArrayList<>();
    private List<String> mList3=new ArrayList<>();

    private Handler mHandler=new Handler();

    private boolean isJop=false;//是否跳转
    @Override
    protected void findViews() {
        Intent intent = getIntent();
        mIntExtra = intent.getIntExtra("tag",0);
        //设置标题
        if (mIntExtra==1){//行政班
            setTitleText("行政班", true);
        }else {//兴趣班
            setTitleText("兴趣班", true);
        }
        //设置右侧添加可见
        setOnRightVillable(true);
        //设置右侧图标不可见
        setBaseRightIcon(1, false);
        //设置搜索可见
        setSeacherVillable(true);

        mListView= (PullToRefreshListView) findViewById(R.id.classes_list);
        mButton= (Button) findViewById(R.id.classes_btn);

        for (int i = 0; i < 6; i++) {
            mListClass.add("小学一年级"+"("+i+")"+"班");
            mList3.add(i+"班");
        }
       mList1.add("小学");
       mList1.add("初中");
       mList1.add("高中");
        for (int i = 0; i < 3; i++) {
            mList2.add(i+"年级");
        }
        mClassesChooseAdapter=new ClassesChooseAdapter(this,mListClass);
        mListView.setAdapter(mClassesChooseAdapter);

        //搜索事件
        setClassesSearchListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //右侧添加点击事件
        setBaseRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popwindow
                if (mIntExtra==1){//行政班
                    initPopWindow("0");
                }else {//兴趣班
                    initPopWindow("1");
                }
                openPopWindow();

            }
        });

        //加入事件
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点了加入
                if (ClickPosition==-1){
                    Toast.makeText(XZAndXQClassActivity.this,"请选择班级,或自行添加",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent1=new Intent(XZAndXQClassActivity.this,ClassManegerActivity.class);
                    String s = mListClass.get(ClickPosition);
                    intent1.putExtra("BanJi",s);
                    startActivity(intent1);
                }
            }
        });
    }

    public void openPopWindow() {
        //底部显示
        popupWindow.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
    }
    private View contentView;
    private PopupWindow popupWindow;
    boolean isExpand=false;
    private Dialog dialog;
    private void initPopWindow(String tag) {
        screenDimmed();//暗屏

        //加载弹出框的布局
        contentView = LayoutInflater.from(this).inflate(
                R.layout.pop_layout, null);
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
        TextView tishi= (TextView) contentView.findViewById(R.id.yicunzai);
        final TextView spinner1= (TextView) contentView.findViewById(R.id.spinner1);
        final TextView spinner2= (TextView) contentView.findViewById(R.id.spinner2);
        final TextView spinner3= (TextView) contentView.findViewById(R.id.spinner3);
        final Button button= (Button) contentView.findViewById(R.id.pop_chuangjian);
        ImageView close= (ImageView) contentView.findViewById(R.id.close);

        final LinearLayout layout_success= (LinearLayout) contentView.findViewById(R.id.pop_success);
        final LinearLayout layout_add= (LinearLayout) contentView.findViewById(R.id.pop_success_add);
        if (tag.equals("0")){
            title.setText("新建班级");
        }else if (tag.equals("1")){
            title.setText("新建兴趣班");
        }


       spinner1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!isExpand) {
                   dialog = new Dialog(XZAndXQClassActivity.this);
                   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                   ListView mListView = new ListView(XZAndXQClassActivity.this);
                   mListView.setCacheColorHint(Color.TRANSPARENT);
                   mListView.setAdapter(new LevelAdapter(levelArray1));
                   mListView.setBackgroundColor(Color.parseColor("#ffffff"));
                   mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                           // TODO Auto-generated method stub
                           spinner1.setText(levelArray1[position]);
                           if (dialog != null) {
                               dialog.dismiss();
                               dialog = null;
                               isExpand = false;
                           }
                       }
                   });
                   dialog.setContentView(mListView);
                   dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                       @Override
                       public void onDismiss(DialogInterface dialog) {
                           // TODO Auto-generated method stub
                           isExpand = false;
                       }
                   });
                   Window dialogWindow = dialog.getWindow();
                   dialogWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.RED));
                   dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                   WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                   lp.dimAmount = 0f;
                   int[] location = new int[2];
                   spinner1.getLocationOnScreen(location);
                   Rect out = new Rect();
                   spinner1.getWindowVisibleDisplayFrame(out);
                   lp.x = location[0];
                   lp.y = location[1] - out.top + spinner1.getHeight();
                   lp.width = spinner1.getWidth();
                   lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                   dialogWindow.setAttributes(lp);
                   dialog.show();
                   isExpand = true;
               } else {
                   if (dialog != null) {
                       dialog.dismiss();
                       dialog = null;
                   }
               }
           }
       });
        spinner2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    dialog = new Dialog(XZAndXQClassActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ListView mListView = new ListView(XZAndXQClassActivity.this);
                    mListView.setCacheColorHint(Color.TRANSPARENT);
                    mListView.setAdapter(new LevelAdapter(levelArray2));
                    mListView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // TODO Auto-generated method stub
                            spinner2.setText(levelArray2[position]);
                            if (dialog != null) {
                                dialog.dismiss();
                                dialog = null;
                                isExpand = false;
                            }
                        }
                    });
                    dialog.setContentView(mListView);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // TODO Auto-generated method stub
                            isExpand = false;
                        }
                    });
                    Window dialogWindow = dialog.getWindow();
                    dialogWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.RED));
                    dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.dimAmount = 0f;
                    int[] location = new int[2];
                    spinner2.getLocationOnScreen(location);
                    Rect out = new Rect();
                    spinner2.getWindowVisibleDisplayFrame(out);
                    lp.x = location[0];
                    lp.y = location[1] - out.top + spinner2.getHeight();
                    lp.width = spinner2.getWidth();
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialogWindow.setAttributes(lp);
                    dialog.show();
                    isExpand = true;
                } else {
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                }
            }
        });
        spinner3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isExpand) {
                    dialog = new Dialog(XZAndXQClassActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    ListView mListView = new ListView(XZAndXQClassActivity.this);
                    mListView.setCacheColorHint(Color.TRANSPARENT);
                    mListView.setAdapter(new LevelAdapter(levelArray3));
                    mListView.setBackgroundColor(Color.parseColor("#ffffff"));
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // TODO Auto-generated method stub
                            spinner3.setText(levelArray3[position]);
                            if (dialog != null) {
                                dialog.dismiss();
                                dialog = null;
                                isExpand = false;
                            }
                        }
                    });
                    dialog.setContentView(mListView);
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            // TODO Auto-generated method stub
                            isExpand = false;
                        }
                    });
                    Window dialogWindow = dialog.getWindow();
                    dialogWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.RED));
                    dialogWindow.setGravity(Gravity.LEFT | Gravity.TOP);
                    WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                    lp.dimAmount = 0f;
                    int[] location = new int[2];
                    spinner3.getLocationOnScreen(location);
                    Rect out = new Rect();
                    spinner3.getWindowVisibleDisplayFrame(out);
                    lp.x = location[0];
                    lp.y = location[1] - out.top + spinner3.getHeight();
                    lp.width = spinner3.getWidth();
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialogWindow.setAttributes(lp);
                    dialog.show();
                    isExpand = true;
                } else {
                    if (dialog != null) {
                        dialog.dismiss();
                        dialog = null;
                    }
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("请选择学校".equals(spinner1.getText().toString())) {
                    Toast.makeText(XZAndXQClassActivity.this, "请选择教育阶段", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("请选择年级".equals(spinner2.getText().toString())) {
                    Toast.makeText(XZAndXQClassActivity.this, "请选择年级", Toast.LENGTH_SHORT).show();
                    return;
                } else if ("请选择班级".equals(spinner3.getText().toString())) {
                    Toast.makeText(XZAndXQClassActivity.this, "请选择班级", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    //请求接口
                    if (button.getText().toString().equals("创建")){
                        ChuangJian(layout_success);
                    }else {
                        ChuangJian(layout_add);
                    }

                }
            }
        });

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
                if (isJop){
                    isJop=false;
                    Intent intent=new Intent(XZAndXQClassActivity.this,ClassManegerActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    /**
     * 创建或加入班级
     */
    private void ChuangJian(LinearLayout layout) {
        layout.setVisibility(View.VISIBLE);
        isJop=true;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    popupWindow = null;
                }
            }
        },1000);
    }


    private void screenBrighter() {
        WindowManager.LayoutParams params = XZAndXQClassActivity.this.getWindow().getAttributes();
        params.alpha = 1f;
        XZAndXQClassActivity.this.getWindow().setAttributes(params);
    }

    private void screenDimmed() {
        WindowManager.LayoutParams params = XZAndXQClassActivity.this.getWindow().getAttributes();
        params.alpha = 0.5f;
        XZAndXQClassActivity.this.getWindow().setAttributes(params);
    }


    @Override
    protected void getData() {

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected int setContentView() {
        return R.layout.activity_xing_zheng_class;
    }

    /**
     * 班级列表适配器
     */
    public class ClassesChooseAdapter extends BaseAdapter {
        private Context mContext;
        private List<String> mList;

        public ClassesChooseAdapter(Context context, List<String> list) {
            mContext = context;
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView==null){
                viewHolder=new ViewHolder();
                convertView= LayoutInflater.from(mContext).inflate(R.layout.place_item,null);
                viewHolder.mCheckBox= (RadioButton) convertView.findViewById(R.id.place_check);
                viewHolder.mRadioGroup= (RadioGroup) convertView.findViewById(R.id.place_radio);
                convertView.setTag(viewHolder);
            }else {
                viewHolder= (ViewHolder) convertView.getTag();
            }
            if (mList.size()!=0){
                viewHolder.mCheckBox.setText(mList.get(position));
                viewHolder.mRadioGroup.clearCheck();
            }
            if (ClickPosition==position){//条目为刚才点击的条目

                viewHolder.mCheckBox.setChecked(true);
            }else {
                viewHolder.mCheckBox.setChecked(false);
            }
            viewHolder.mCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClickPosition=position;
                    notifyDataSetChanged();
                    Log.e("刷新执行了", position + "");
                }
            });


            return convertView;
        }

        class ViewHolder{
            RadioButton mCheckBox;
            RadioGroup mRadioGroup;
        }
    }

    /**
     * 下拉适配器
     */
    final String[] levelArray1={"小学","中学","高中","大学"};
    final String[] levelArray2={"一年级","二年级","三年级","四年级","五年级","六年级"};
    final String[] levelArray3={"1班","2班","3班","4班","5班"};
    public class LevelAdapter extends BaseAdapter{

        private String[] data;

        public LevelAdapter(String[] data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.length;
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

            convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.dialog_spinner_item,null);
            TextView textView= (TextView) convertView.findViewById(R.id.diaspin_txt);

            textView.setText(data[position]);
            return convertView;
        }
    }

    @Override
    protected void onDestroy() {
        if (mHandler!=null){
            mHandler=null;
        }
        super.onDestroy();
    }
}
